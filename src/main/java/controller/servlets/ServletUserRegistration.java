package controller.servlets;

import controller.service.ServiceUsers;
import dao.entity.User;
import util.setting.Settings;
import view.htmlrenderer.HTMLRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletUserRegistration", urlPatterns = "/UserRegistration")
public class ServletUserRegistration extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String htmlRegistration = "";
        String userName = new String(request.getParameter("username").getBytes("iso-8859-1"), "UTF-8");
        String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
        String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");

        if(new ServiceUsers().isCorrectUserForRegistration(userName, password, email)){
            new ServiceUsers().addUser(userName, password, email);
            htmlRegistration = new HTMLRenderer().getCorrectRegistrationPage(getServletContext().getRealPath("") + Settings.StartPage);
        }else{
            User user = new User(userName, password, email);
            htmlRegistration = new HTMLRenderer().getIncorrectRegistrationPage(getServletContext().getRealPath("") + Settings.StartPage, user);
        }

        response.getWriter().print(htmlRegistration);

    }


}
