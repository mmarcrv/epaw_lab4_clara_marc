package epaw.lab4.controller;

import epaw.lab4.model.User;
import epaw.lab4.repository.ReportRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ReportTweet")
public class ReportTweet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ReportRepository reportRepository;

    @Override
    public void init() throws ServletException {
        reportRepository = ReportRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        String tweetIdStr = request.getParameter("tweetId");
        String reason = request.getParameter("reason");

        if (tweetIdStr == null || reason == null || reason.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int tweetId = Integer.parseInt(tweetIdStr);
            reportRepository.saveReport(tweetId, user.getId(), reason.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
