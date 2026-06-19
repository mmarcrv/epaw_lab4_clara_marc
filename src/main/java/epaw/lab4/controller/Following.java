package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.LikeService;
import epaw.lab4.service.TweetService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/Following")
public class Following extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> tweets = null;
        User user = null;
        HttpSession session = request.getSession(false);

        Set<Integer> likedIds = new HashSet<>();
        if (session != null) {
            user = (User) session.getAttribute("user");
            if (user != null) {
                tweets = TweetService.getInstance().getFollowingFeed(user.getId(), 0, 20);
                likedIds = LikeService.getInstance().getLikedTweetIds(user.getId(), tweets);
            }
        }

        request.setAttribute("tweets", tweets);
        request.setAttribute("user", user);
        request.setAttribute("likedIds", likedIds);
        request.getRequestDispatcher("Tweets.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
