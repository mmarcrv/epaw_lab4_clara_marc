package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.TweetService;

import java.io.IOException;
import java.util.List;

@WebServlet("/Comments")
public class Comments extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        List<Tweet> comments = null;

        HttpSession session = request.getSession(false);
        if (session != null) user = (User) session.getAttribute("user");

        int parentId = -1;
        try {
            parentId = Integer.parseInt(request.getParameter("id"));
            comments = TweetService.getInstance().getComments(parentId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        request.setAttribute("user", user);
        request.setAttribute("comments", comments);
        request.setAttribute("parentTweetId", parentId);
        request.getRequestDispatcher("Comments.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
