package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.TweetService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@MultipartConfig
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
                    String parentIdStr = request.getParameter("parentId");
                    if (parentIdStr != null && !parentIdStr.isEmpty()) {
                        tweet.setParentId(Integer.parseInt(parentIdStr));
                        tweet.setIsParent(false);
                    } else {
                        tweet.setIsParent(true);
                    }
                    TweetService.getInstance().add(tweet);
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) return;
        User user = (User) session.getAttribute("user");
        if (user == null) return;

        String title = request.getParameter("title");
        String textBody = request.getParameter("textBody");
        if (title == null || title.trim().isEmpty() || textBody == null || textBody.trim().isEmpty()) return;

        Tweet tweet = new Tweet();
        tweet.setUserId(user.getId());
        tweet.setUname(user.getName());
        tweet.setTitle(title.trim());
        tweet.setTextBody(textBody.trim());

        String parentIdStr = request.getParameter("parentId");
        if (parentIdStr != null && !parentIdStr.isEmpty()) {
            tweet.setParentId(Integer.parseInt(parentIdStr));
            tweet.setIsParent(false);
        } else {
            tweet.setIsParent(true);
        }

        String ct = request.getContentType();
        if (ct != null && ct.startsWith("multipart/")) {
            String picturePath = savePicture(request.getPart("picture"), user.getId());
            if (picturePath != null) tweet.setPicture(picturePath);
        }

        TweetService.getInstance().add(tweet);
    }

    private String savePicture(Part filePart, int userId) {
        if (filePart == null || filePart.getSize() <= 0) return null;
        try {
            String fileName = filePart.getSubmittedFileName();
            if (fileName == null || fileName.isEmpty()) return null;
            String ext = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = "tweet_" + userId + "_" + System.currentTimeMillis() + ext;
            Files.createDirectories(Paths.get("EXTERNAL_RESOURCES"));
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get("EXTERNAL_RESOURCES", newFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
