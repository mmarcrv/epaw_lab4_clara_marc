package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.User;
import epaw.lab4.repository.ReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminRight")
public class AdminRight extends HttpServlet {

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
        if (session != null) user = (User) session.getAttribute("user");

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Map<String, Integer> stats = reportRepository.getStats();

        @SuppressWarnings("unchecked")
        List<String> recentActivity = (List<String>) session.getAttribute("recentActivity");
        if (recentActivity == null) {
            recentActivity = new ArrayList<>();
            recentActivity.add("Benvingut al Panell d'Administració");
            session.setAttribute("recentActivity", recentActivity);
        }

        request.setAttribute("stats", stats);
        request.setAttribute("recentActivity", recentActivity);
        request.getRequestDispatcher("AdminRight.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
