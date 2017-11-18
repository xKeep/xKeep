package dao.repository;

public interface CRUDNotesRepository {
    public void changeNoteStatus(long noteID , String statusNote);
    public void changeNoteColor(long noteID , String setColorNote);
    public void editTextNote(long noteID, String headNote, String textNote);
    public void createNewNote(String nameUserInSession, String headNote, String textNote);
    public void deleteNote(long id);
}
