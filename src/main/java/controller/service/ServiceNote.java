package controller.service;

import dao.repository.CRUDNotesRepositoryImplementation;

public class ServiceNote {

    public void  changeNoteStatus(String noteID ,String targetFolder){
        long id = Long.parseLong(noteID);
        new CRUDNotesRepositoryImplementation().changeNoteStatus(id , targetFolder);
    }



    public void changeNoteColor(String noteID ,String setColorNote){
        long id = Long.parseLong(noteID);
        new CRUDNotesRepositoryImplementation().changeNoteColor(id ,setColorNote );
    }




    public void editTextNote(String noteID, String headNote, String textNote) {
        long id = Long.parseLong(noteID);
        System.out.println("in Service" + headNote + " " + textNote);
        new CRUDNotesRepositoryImplementation().editTextNote(id,headNote, textNote);
    }




    public void createNewNote(String nameUserInSession, String headNote, String textNote){
        new CRUDNotesRepositoryImplementation().createNewNote(nameUserInSession, headNote, textNote);
    }



    public void deleteNote(String noteID){
        long id = Long.parseLong(noteID);
        new CRUDNotesRepositoryImplementation().deleteNote(id);
    }
}
