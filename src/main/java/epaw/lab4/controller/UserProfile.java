package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.FollowService;
import epaw.lab4.service.LikeService;
import epaw.lab4.service.TweetService;
import epaw.lab4.service.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/UserProfile")
public class UserProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = null;
        if (session != null) sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) { response.sendRedirect("Login"); return; }

        User profileUser = sessionUser;
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                User found = UserService.getInstance().getUserById(Integer.parseInt(idParam));
                if (found != null) profileUser = found;
            } catch (NumberFormatException e) { e.printStackTrace(); }
        }

        List<Tweet> tweets = TweetService.getInstance().getTweetsByUser(profileUser.getId(), 0, 20);
        Set<Integer> likedIds = new HashSet<>();
        if (tweets != null) likedIds = LikeService.getInstance().getLikedTweetIds(sessionUser.getId(), tweets);
        int following = FollowService.getInstance().countFollowing(profileUser.getId());
        int followers = FollowService.getInstance().countFollowers(profileUser.getId());

        request.setAttribute("profileUser", profileUser);
        request.setAttribute("sessionUser", sessionUser);
        request.setAttribute("tweets", tweets);
        request.setAttribute("likedIds", likedIds);
        request.setAttribute("following", following);
        request.setAttribute("followers", followers);
        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
