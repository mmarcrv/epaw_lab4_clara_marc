package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.User;
import epaw.lab4.model.ReportedItem;
import epaw.lab4.repository.ReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminPanel")
public class AdminPanel extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReportRepository reportRepository;

    @Override
    public void init() throws ServletException {
        reportRepository = ReportRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accés denegat.");
            return;
        }

        Map<String, Integer> stats = reportRepository.getStats();
        List<ReportedItem> reportedPosts = reportRepository.getReportedPosts();
        List<ReportedItem> reportedComments = reportRepository.getReportedComments();

        @SuppressWarnings("unchecked")
        List<String> recentActivity = (List<String>) session.getAttribute("recentActivity");
        if (recentActivity == null) {
            recentActivity = new ArrayList<>();
            recentActivity.add("Benvingut al Panell d'Administració");
            session.setAttribute("recentActivity", recentActivity);
        }

        request.setAttribute("stats", stats);
        request.setAttribute("reportedPosts", reportedPosts);
        request.setAttribute("reportedComments", reportedComments);
        request.setAttribute("recentActivity", recentActivity);

        request.getRequestDispatcher("AdminPanel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accés denegat.");
            return;
        }

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        String authorName = request.getParameter("authorName");
        
        @SuppressWarnings("unchecked")
        List<String> recentActivity = (List<String>) session.getAttribute("recentActivity");
        if (recentActivity == null) {
            recentActivity = new ArrayList<>();
            session.setAttribute("recentActivity", recentActivity);
        }

        if (action != null && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                if ("deletePost".equals(action) || "deleteComment".equals(action)) {
                    reportRepository.deleteTweet(id);
                    String itemType = "deletePost".equals(action) ? "Post" : "Comentari";
                    recentActivity.add(0, itemType + " eliminat (ID: " + id + ") fa 1 min");
                } else if ("ignoreReport".equals(action)) {
                    reportRepository.ignoreReport(id);
                    recentActivity.add(0, "Report ignorat (ID: " + id + ") fa 1 min");
                } else if ("banUser".equals(action)) {
                    reportRepository.banUser(id);
                    recentActivity.add(0, "Usuari bannejat (" + (authorName != null ? authorName : "ID: " + id) + ") fa 1 min");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (recentActivity.size() > 5) {
            recentActivity.subList(5, recentActivity.size()).clear();
        }
        session.setAttribute("recentActivity", recentActivity);

        doGet(request, response);
    }
}
