package epaw.lab4.controller;

import epaw.lab4.model.User;
import epaw.lab4.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminPanel")
public class AdminPanel extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReportService reportService;

    @Override
    public void init() throws ServletException {
        reportService = ReportService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accés denegat.");
            return;
        }

        request.setAttribute("reportedPosts", reportService.getReportedPosts());
        request.setAttribute("reportedComments", reportService.getReportedComments());

        List<String> recentActivity = getRecentActivity(session);
        request.setAttribute("recentActivity", recentActivity);

        request.getRequestDispatcher("AdminPanel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accés denegat.");
            return;
        }

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        String authorName = request.getParameter("authorName");

        List<String> recentActivity = getRecentActivity(session);

        if (action != null && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                if ("deletePost".equals(action) || "deleteComment".equals(action)) {
                    reportService.deleteTweet(id);
                    String type = "deletePost".equals(action) ? "Post" : "Comentari";
                    recentActivity.add(0, type + " eliminat (ID: " + id + ")");
                } else if ("ignoreReport".equals(action)) {
                    reportService.ignoreReport(id);
                    recentActivity.add(0, "Report ignorat (ID: " + id + ")");
                } else if ("banUser".equals(action)) {
                    String tweetIdStr = request.getParameter("tweetId");
                    int tweetId = (tweetIdStr != null) ? Integer.parseInt(tweetIdStr) : 0;
                    reportService.banUser(id, tweetId);
                    recentActivity.add(0, "Usuari bannejat (" + (authorName != null ? authorName : "ID: " + id) + ")");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (recentActivity.size() > 5) recentActivity.subList(5, recentActivity.size()).clear();
        session.setAttribute("recentActivity", recentActivity);

        doGet(request, response);
    }

    @SuppressWarnings("unchecked")
    private List<String> getRecentActivity(HttpSession session) {
        List<String> activity = (List<String>) session.getAttribute("recentActivity");
        if (activity == null) {
            activity = new ArrayList<>();
            activity.add("Benvingut al Panell d'Administració");
            session.setAttribute("recentActivity", activity);
        }
        return activity;
    }
}
