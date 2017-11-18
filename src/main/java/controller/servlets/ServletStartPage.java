package controller.servlets;

import util.setting.Settings;
import view.htmlrenderer.HTMLRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletStartPage", urlPatterns = "/")
public class ServletStartPage extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String htmlLoginPage = new HTMLRenderer().getLoginPage(getServletContext().getRealPath("") + Settings.StartPage);

        response.getWriter().print(htmlLoginPage);
    }

}
