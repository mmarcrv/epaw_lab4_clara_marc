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

@WebServlet("/AdminRight")
public class AdminRight extends HttpServlet {

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
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("stats", reportService.getStats());

        @SuppressWarnings("unchecked")
        List<String> recentActivity = (List<String>) session.getAttribute("recentActivity");
        if (recentActivity == null) {
            recentActivity = new ArrayList<>();
            recentActivity.add("Benvingut al Panell d'Administració");
            session.setAttribute("recentActivity", recentActivity);
        }
        request.setAttribute("recentActivity", recentActivity);

        request.getRequestDispatcher("AdminRight.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
