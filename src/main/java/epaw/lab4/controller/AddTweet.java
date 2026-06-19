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

@WebServlet("/AddTweet")
public class AddTweet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String title = request.getParameter("title");
                String textBody = request.getParameter("textBody");
                if (title != null && !title.trim().isEmpty()
                        && textBody != null && !textBody.trim().isEmpty()) {
                    Tweet tweet = new Tweet();
                    tweet.setUserId(user.getId());
                    tweet.setUname(user.getName());
                    tweet.setTitle(title.trim());
                    tweet.setTextBody(textBody.trim());
                    tweet.setIsParent(true);
                    TweetService.getInstance().add(tweet);
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
