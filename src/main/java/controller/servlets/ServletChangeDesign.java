package controller.servlets;

import controller.service.ServiceUsers;
import dao.entity.Note;
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

@WebServlet(name = "ServletChangeDesign", urlPatterns = "/changeDesign")
public class ServletChangeDesign extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        if(Settings.TypeDesign==1){
            Settings.TypeDesign = 2;
            Settings.StartPage = "/html/start-page2.html";
            Settings.UserPage = "/html/user-page2.html";
            Settings.BlankOneNote = "/html/blank-one-note2.html";
            Settings.BlankOneNoteModal = "/html/blank-one-note-modal2.html";
        }else{
            Settings.TypeDesign = 1;
            Settings.StartPage = "/html/start-page1.html";
            Settings.UserPage = "/html/user-page1.html";
            Settings.BlankOneNote = "/html/blank-one-note1.html";
            Settings.BlankOneNoteModal = "/html/blank-one-note-modal1.html";
        }

        String htmlPage = "";
        HttpSession session = request.getSession();
        String nameUserInSession = (String) session.getAttribute("username");

        if(nameUserInSession!=null){
            if (new ServiceUsers().isPresentUserName(nameUserInSession)){
                String selectedFolder = new String(request.getParameter("selectedFolder").getBytes("iso-8859-1"), "UTF-8");
                String selectedColor = new String(request.getParameter("selectedColor").getBytes("iso-8859-1"), "UTF-8");

                List<Note> allNotesFromUser = new ServiceUsers().getAllNotesFromUser(nameUserInSession, selectedFolder, selectedColor);

                String  htmlDivNotes = new HTMLRenderer().getHTMLDivNotes(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);
                String  htmlDivNotesModal = new HTMLRenderer().getHTMLDivNotesModal(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);

                htmlPage = new HTMLRenderer().getCorrectLoginPage(getServletContext().getRealPath("") + Settings.UserPage, nameUserInSession);
                htmlPage = new HTMLRenderer().addDivNotesOnHTML(htmlPage , htmlDivNotes , htmlDivNotesModal);
                htmlPage = new HTMLRenderer().putStatusAndColorOnHTML(htmlPage , selectedFolder ,selectedColor);
                response.getWriter().print(htmlPage);

                return;
            }
        }else{
            String htmlLoginPage = new HTMLRenderer().getLoginPage(getServletContext().getRealPath("") + Settings.StartPage);
            response.getWriter().print(htmlLoginPage);
        }
    }

}
