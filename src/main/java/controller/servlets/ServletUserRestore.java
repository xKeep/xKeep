package controller.servlets;

import controller.service.ServiceUsers;
import util.setting.Settings;
import view.htmlrenderer.HTMLRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletUserRestore", urlPatterns = "/UserRestore")
public class ServletUserRestore extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String htmlRestorePage = "";
        response.setContentType("text/html;charset=UTF-8");
        String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");

        if(new ServiceUsers().isPresentEmail(email)){
            new ServiceUsers().sendEmailToUser(email);
            htmlRestorePage = new HTMLRenderer().getCorrectRestorePage(getServletContext().getRealPath("") + Settings.StartPage, email);
        }else{
            htmlRestorePage = new HTMLRenderer().getIncorrectRestorePage(getServletContext().getRealPath("") + Settings.StartPage, email);
        }

        response.getWriter().print(htmlRestorePage);
    }

}
