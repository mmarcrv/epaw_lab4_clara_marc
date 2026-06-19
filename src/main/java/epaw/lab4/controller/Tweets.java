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

@WebServlet("/Tweets")
public class Tweets extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession(false);
        if (session != null) user = (User) session.getAttribute("user");

        String categoryFilter = request.getParameter("category");
        int uid = (user != null) ? user.getId() : 0;
        List<Tweet> tweets = TweetService.getInstance().getTimeline(uid, 0, 20);

        if (categoryFilter != null && !categoryFilter.isEmpty()) {
            tweets = tweets.stream()
                .filter(t -> categoryFilter.equals(t.getCategory()))
                .collect(java.util.stream.Collectors.toList());
        }

        Set<Integer> likedIds = new HashSet<>();
        if (user != null && tweets != null) {
            likedIds = LikeService.getInstance().getLikedTweetIds(user.getId(), tweets);
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
