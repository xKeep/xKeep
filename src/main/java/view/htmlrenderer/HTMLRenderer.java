package view.htmlrenderer;

import dao.entity.Note;
import dao.entity.User;
import util.setting.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HTMLRenderer {

    private String getStartPage(String fullFileName){

        String htmlStartPage = "";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fullFileName), Charset.forName("UTF-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                htmlStartPage = htmlStartPage + line + "\n";
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return htmlStartPage;
    }



    private String getScript(){
        return "<script>" + "$(document).ready(function () {" +
                "$(\"#answerFromServlet\").modal(\"show\")" + "});" +
                "</script>";
    }



    public String addDivNotesOnHTML(String htmlPage ,String htmlDivNotes ,String htmlDivNotesModal){
        htmlPage = htmlPage.replace("<!--divAllNote-->", htmlDivNotes);
        htmlPage = htmlPage.replace("<!--modalWindowsForAllNotes-->", htmlDivNotesModal);
        return htmlPage;
    }



    public String putStatusAndColorOnHTML(String htmlPage ,String selectedFolder ,String selectedColor){

        htmlPage = htmlPage.replace("<!--InsertFolderSelected-->" , selectedFolder);
        htmlPage = htmlPage.replace("<!--InsertColorSelected-->" , selectedColor);

        String selectedFolderxKEEP = "";
        if (selectedFolder.equals("actualNotes")) selectedFolderxKEEP = "Актуальні";
        if (selectedFolder.equals("finishedNotes")) selectedFolderxKEEP = "Завершені";
        if (selectedFolder.equals("removedNotes")) selectedFolderxKEEP = "Корзина";
        htmlPage = htmlPage.replace("<!--selectedFolderxKEEP-->", selectedFolderxKEEP);

        return htmlPage;
    }



    public String getLoginPage(String fullFileName){
        String html = getStartPage(fullFileName);
        html = html.replace("loginActive", "active");
        html = html.replace("inLoginActive", "in active");
        return  html;
    }



    public String getCorrectRegistrationPage(String fullFileName){
        String html = getStartPage(fullFileName);
        html = html.replace("registrationActive", "active");
        html = html.replace("inRegistrationActive", "in active");
        html = html.replace("<!--comment for registration-->","КОРИСТУВАЧА ДОДАНО!");
        html = html.replace("<!--scriptShowModalWindow-->", getScript());
        return html;
    }



    public String getIncorrectRegistrationPage(String fullFileName, User user){
        String html = getStartPage(fullFileName);
        html = html.replace("><!--user registration name-->", " value=\"" + user.getUserName() + "\">");
        html = html.replace("><!--user registration password-->", " value=\"" + user.getPassword() + "\">");
        html = html.replace("><!--user registration email-->", " value=\"" + user.getEmail() + "\">");
        html = html.replace("registrationActive", "active");
        html = html.replace("inRegistrationActive", "in active");
        html = html.replace("<!--comment for registration-->","УВАГА! НЕКОРЕКТНІ ДАНІ!");
        html = html.replace("<!--scriptShowModalWindow-->", getScript());
        return html;
    }



    public String getCorrectLoginPage(String fullFileName, String nameRegisteredUser){
        String html = getStartPage(fullFileName);
        html = html.replace("<!--LoginUserName-->", nameRegisteredUser);
        return html;
    }



    public String getIncorrectLoginPage(String fullFileName, User user){
        String html = getStartPage(fullFileName);
        html = html.replace("><!--user login-->", " value=\"" + user.getUserName() + "\">");
        html = html.replace("><!--user password-->", " value=\"" + user.getPassword() + "\">");
        html = html.replace("loginActive", "active");
        html = html.replace("inLoginActive", "in active");
        html = html.replace("<!--comment for bad login-->","УВАГА!  НЕКОРЕКТНІ ДАНІ!");
        html = html.replace("<!--scriptShowModalWindow-->", getScript());
        return html;
    }



    public String getCorrectRestorePage(String fullFileName, String email){
        String html = getStartPage(fullFileName);
        html = html.replace("><!--user restore email-->", " value=\"" + email + "\">");
        html = html.replace("restoreActive", "active");
        html = html.replace("inRestoreActive", "in active");
        html = html.replace("<!--comment for restore-->","ДАНІ НАДІСЛАНО НА E-MAIL!");
        html = html.replace("<!--scriptShowModalWindow-->", getScript());
        return html;
    }



    public String getIncorrectRestorePage(String fullFileName, String email){
        String html = getStartPage(fullFileName);
        html = html.replace("><!--user restore email-->", " value=\"" + email + "\">");
        html = html.replace("restoreActive", "active");
        html = html.replace("inRestoreActive", "in active");
        html = html.replace("<!--comment for restore-->","УВАГА! НЕКОРЕКТНІ ДАНІ!");
        html = html.replace("<!--scriptShowModalWindow-->", getScript());
        return html;
    }



    public String getHTMLDivNotes(String path, List<Note> allNotesFromUser, String folderSelected, String colorSelected) {

        String realPath = path + Settings.BlankOneNote;
        String totalHTMLDivNote = "";

        for (int i = 0; i < allNotesFromUser.size(); i++) {
            Note note = allNotesFromUser.get(i);

            String oneHTMLDivNote = "";
            oneHTMLDivNote = getStartPage(realPath);
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--InsertNoteID-->", "" + note.getNoteID());
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--colorNote-->", "" + note.getColorNote());
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--headerNote-->", note.getHeaderNote());
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--InsertHeaderNote-->", note.getHeaderNote());
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--InsertTextNote-->", note.getTextNote());

            String textNote = note.getTextNote();
            if (textNote.length() > 110) {
                textNote = textNote.substring(0, 110);
                textNote = textNote + "...";
            }
            oneHTMLDivNote = oneHTMLDivNote.replace("<!--textNote-->", "<div style=\"text-align:right; font-size:12px;\"><i>Створено: " + note.getDateCreateNote() + "</i></div>" + textNote);
            oneHTMLDivNote = oneHTMLDivNote.trim();
            totalHTMLDivNote = totalHTMLDivNote + oneHTMLDivNote;
        }

        totalHTMLDivNote = totalHTMLDivNote.replace("<!--InsertFolderSelected-->", folderSelected);
        totalHTMLDivNote = totalHTMLDivNote.replace("<!--InsertColorSelected-->", colorSelected);

        return totalHTMLDivNote;
    }



    public String getHTMLDivNotesModal(String  path, List<Note> allNotesFromUser, String folderSelected, String colorSelected){

        String totalHTMLDivNoteModal = "";
        String realPathModal = path + Settings.BlankOneNoteModal;

        for (int i=0; i<allNotesFromUser.size(); i++){
            Note note = allNotesFromUser.get(i);

            String oneHTMLDivNoteModal = "";
            oneHTMLDivNoteModal = getStartPage(realPathModal);
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.replace("<!--InsertNoteID-->", ""+ note.getNoteID());
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.replace("<!--colorNote-->", ""+note.getColorNote());
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.replace("<!--headerNote-->", note.getHeaderNote());
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.replace("<!--InsertHeaderNote-->", note.getHeaderNote());
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.replace("<!--InsertTextNote-->", note.getTextNote());
            oneHTMLDivNoteModal = oneHTMLDivNoteModal.trim();
            totalHTMLDivNoteModal = totalHTMLDivNoteModal + oneHTMLDivNoteModal;
        }

        totalHTMLDivNoteModal = totalHTMLDivNoteModal.replace("<!--InsertFolderSelected-->", folderSelected);
        totalHTMLDivNoteModal = totalHTMLDivNoteModal.replace("<!--InsertColorSelected-->", colorSelected);

        return totalHTMLDivNoteModal;
    }

}
