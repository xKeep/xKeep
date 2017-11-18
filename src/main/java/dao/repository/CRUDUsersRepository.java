package dao.repository;

import dao.entity.Note;
import dao.entity.User;

import java.util.List;

public interface CRUDUsersRepository {

    public void addUser(User user);
    public boolean isPresentUserByNameOrEmail(User user);
    public User getUserByName(User user);
    public User getUserByEmail(String email);
    public List<Note> getAllNotesFromUser(String nameUser, String selectedFolder, String selectedColor);

}
