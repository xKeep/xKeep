package controller.servlets;

import controller.service.ServiceUsers;
import dao.entity.Note;
import dao.entity.User;
import util.setting.Settings;
import view.htmlrenderer.HTMLRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletUserLogin", urlPatterns = "/UserLogin")
public class ServletUserLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String htmlLoginPage = "";
        HttpSession session = request.getSession();
        String nameUserInSession = (String) session.getAttribute("username");
        String selectedFolder = Settings.ACTUAL_NOTES;
        String selectedColor = Settings.COLOR_ALL;

        if (request.getParameter("login")!=null){
            String login = new String(request.getParameter("login").getBytes("iso-8859-1"), "UTF-8");
            String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");

            String nameRegisteredUser = new ServiceUsers().getNameRegisteredUser(login, password);

            if (nameRegisteredUser!=null){

                session.setAttribute("username", nameRegisteredUser);

                List<Note> allNotesFromUser = new ServiceUsers().getAllNotesFromUser(nameRegisteredUser, "actualNotes", "colorAll");

                String  htmlDivNotes = new HTMLRenderer().getHTMLDivNotes(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);
                String  htmlDivNotesModal = new HTMLRenderer().getHTMLDivNotesModal(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);

                htmlLoginPage = new HTMLRenderer().getCorrectLoginPage(getServletContext().getRealPath("") + Settings.UserPage, nameRegisteredUser);
                htmlLoginPage = new HTMLRenderer().addDivNotesOnHTML(htmlLoginPage , htmlDivNotes , htmlDivNotesModal);
                htmlLoginPage = new HTMLRenderer().putStatusAndColorOnHTML(htmlLoginPage , selectedFolder ,selectedColor);

            }else {
                User user = new User(login, password, login);
                htmlLoginPage = new HTMLRenderer().getIncorrectLoginPage(getServletContext().getRealPath("") + Settings.StartPage, user);
            }

            response.getWriter().print(htmlLoginPage);
            return;
        }


        if (nameUserInSession!=null){
            if (new ServiceUsers().isPresentUserName(nameUserInSession)){

                List<Note> allNotesFromUser = new ServiceUsers().getAllNotesFromUser(nameUserInSession, selectedFolder, selectedColor);

                String  htmlDivNotes = new HTMLRenderer().getHTMLDivNotes(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);
                String  htmlDivNotesModal = new HTMLRenderer().getHTMLDivNotesModal(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);

                htmlLoginPage = new HTMLRenderer().getCorrectLoginPage(getServletContext().getRealPath("") + Settings.UserPage, nameUserInSession);
                htmlLoginPage = new HTMLRenderer().addDivNotesOnHTML(htmlLoginPage , htmlDivNotes , htmlDivNotesModal);
                htmlLoginPage = new HTMLRenderer().putStatusAndColorOnHTML(htmlLoginPage , selectedFolder ,selectedColor);

                response.getWriter().print(htmlLoginPage);

                return;
            }
        }

        htmlLoginPage = new HTMLRenderer().getLoginPage(getServletContext().getRealPath("") + Settings.StartPage);
        response.getWriter().print(htmlLoginPage);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
