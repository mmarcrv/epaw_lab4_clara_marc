package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.model.User;
import epaw.lab4.service.UserService;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

@MultipartConfig
@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = null;
        if (session != null) {
            sessionUser = (User) session.getAttribute("user");
        }
        if (sessionUser == null) {
            response.sendRedirect("Login");
            return;
        }

        // Fetch full user details from DB to pre-fill the form
        User fullUser = userService.getUserById(sessionUser.getId());
        if (fullUser == null) {
            response.sendRedirect("Login");
            return;
        }

        request.setAttribute("user", fullUser);
        request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User sessionUser = null;
        if (session != null) {
            sessionUser = (User) session.getAttribute("user");
        }
        if (sessionUser == null) {
            response.sendRedirect("Login");
            return;
        }

        User user = new User();
        user.setId(sessionUser.getId());
        user.setRole(sessionUser.getRole());

        try {
            BeanUtils.populate(user, request.getParameterMap());
            
            // Handle picture upload
            String picturePath = userService.saveProfilePicture(request.getPart("picture"), user.getName());
            if (picturePath != null) {
                user.setPicture(picturePath);
            } else {
                // If no new picture was uploaded, retain the old one
                User fullUser = userService.getUserById(sessionUser.getId());
                if (fullUser != null) {
                    user.setPicture(fullUser.getPicture());
                } else {
                    user.setPicture(sessionUser.getPicture());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        Map<String, String> errors = userService.updateProfile(user, oldPassword, newPassword, confirmPassword, sessionUser);
        if (errors.isEmpty()) {
            // Update session user with new info
            sessionUser.setName(user.getName());
            if (user.getPicture() != null) {
                sessionUser.setPicture(user.getPicture());
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                sessionUser.setPassword(newPassword);
            }
            session.setAttribute("user", sessionUser);

            // Forward to UserProfile servlet to load profile details
            request.getRequestDispatcher("UserProfile").forward(request, response);
        } else {
            request.setAttribute("user", user);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
        }
    }
}
