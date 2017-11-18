package controller.servlets;

import controller.service.ServiceNote;
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

@WebServlet(name = "ServletNoteChangeColor" ,urlPatterns = "/changeColorNote")
public class ServletNoteChangeColor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String htmlLoginPage = "";
        HttpSession session = request.getSession();
        String nameUserInSession = (String) session.getAttribute("username");
        String selectedFolder = new String(request.getParameter("selectedFolder").getBytes("iso-8859-1"), "UTF-8");
        String selectedColor = new String(request.getParameter("selectedColor").getBytes("iso-8859-1"), "UTF-8");
        String noteID = new String(request.getParameter("noteID").getBytes("iso-8859-1"), "UTF-8");
        String setColorNote = new String(request.getParameter("setColorNote").getBytes("iso-8859-1") , "UTF-8");

        new ServiceNote().changeNoteColor(noteID , setColorNote);

        List<Note> allNotesFromUser = new ServiceUsers().getAllNotesFromUser(nameUserInSession, selectedFolder, selectedColor);
        String  htmlDivNotes = new HTMLRenderer().getHTMLDivNotes(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);
        String  htmlDivNotesModal = new HTMLRenderer().getHTMLDivNotesModal(getServletContext().getRealPath(""), allNotesFromUser, selectedFolder, selectedColor);

        htmlLoginPage = new HTMLRenderer().getCorrectLoginPage(getServletContext().getRealPath("") + Settings.UserPage, nameUserInSession);
        htmlLoginPage = new HTMLRenderer().addDivNotesOnHTML(htmlLoginPage , htmlDivNotes , htmlDivNotesModal);
        htmlLoginPage = new HTMLRenderer().putStatusAndColorOnHTML(htmlLoginPage , selectedFolder ,selectedColor);

        response.getWriter().print(htmlLoginPage);
    }

}
