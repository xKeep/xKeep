package dao.entity;

import java.util.Date;

public class Note {

    private long noteID;
    private long userID;
    private String headerNote;
    private String textNote;
    private String colorNote;
    private Date dateCreateNote;
    private Date dateUpdateNote;
    private String statusNote;
    private int positionNote;

    public Note (){

    }

    public Note(long noteID, long userID, String headerNote, String textNote, String colorNote, Date dateCreateNote, Date dateUpdateNote, String statusNote, int positionNote) {
        this.noteID = noteID;
        this.userID = userID;
        this.headerNote = headerNote;
        this.textNote = textNote;
        this.colorNote = colorNote;
        this.dateCreateNote = dateCreateNote;
        this.dateUpdateNote = dateUpdateNote;
        this.statusNote = statusNote;
        this.positionNote = positionNote;
    }

    public long getNoteID() {
        return noteID;
    }

    public void setNoteID(long noteID) {
        this.noteID = noteID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getHeaderNote() {
        return headerNote;
    }

    public void setHeaderNote(String headerNote) {
        this.headerNote = headerNote;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getColorNote() {
        return colorNote;
    }

    public void setColorNote(String colorNote) {
        this.colorNote = colorNote;
    }

    public Date getDateCreateNote() {
        return dateCreateNote;
    }

    public void setDateCreateNote(Date dateCreateNote) {
        this.dateCreateNote = dateCreateNote;
    }

    public Date getDateUpdateNote() {
        return dateUpdateNote;
    }

    public void setDateUpdateNote(Date dateUpdateNote) {
        this.dateUpdateNote = dateUpdateNote;
    }

    public String getStatusNote() {
        return statusNote;
    }

    public void setStatusNote(String statusNote) {
        this.statusNote = statusNote;
    }

    public int getPositionNote() {
        return positionNote;
    }

    public void setPositionNote(int positionNote) {
        this.positionNote = positionNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (noteID != note.noteID) return false;
        if (userID != note.userID) return false;
        if (positionNote != note.positionNote) return false;
        if (headerNote != null ? !headerNote.equals(note.headerNote) : note.headerNote != null) return false;
        if (textNote != null ? !textNote.equals(note.textNote) : note.textNote != null) return false;
        if (colorNote != null ? !colorNote.equals(note.colorNote) : note.colorNote != null) return false;
        if (dateCreateNote != null ? !dateCreateNote.equals(note.dateCreateNote) : note.dateCreateNote != null)
            return false;
        if (dateUpdateNote != null ? !dateUpdateNote.equals(note.dateUpdateNote) : note.dateUpdateNote != null)
            return false;
        return statusNote != null ? statusNote.equals(note.statusNote) : note.statusNote == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (noteID ^ (noteID >>> 32));
        result = 31 * result + (int) (userID ^ (userID >>> 32));
        result = 31 * result + (headerNote != null ? headerNote.hashCode() : 0);
        result = 31 * result + (textNote != null ? textNote.hashCode() : 0);
        result = 31 * result + (colorNote != null ? colorNote.hashCode() : 0);
        result = 31 * result + (dateCreateNote != null ? dateCreateNote.hashCode() : 0);
        result = 31 * result + (dateUpdateNote != null ? dateUpdateNote.hashCode() : 0);
        result = 31 * result + (statusNote != null ? statusNote.hashCode() : 0);
        result = 31 * result + positionNote;
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteID=" + noteID +
                ", userID=" + userID +
                ", headerNote='" + headerNote + '\'' +
                ", textNote='" + textNote + '\'' +
                ", colorNote='" + colorNote + '\'' +
                ", dateCreateNote=" + dateCreateNote +
                ", dateUpdateNote=" + dateUpdateNote +
                ", statusNote='" + statusNote + '\'' +
                ", positionNote=" + positionNote +
                '}';
    }
}
