package dao.repository;

import dao.entity.Note;
import dao.entity.User;
import util.setting.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CRUDUsersRepositoryImplementation implements ExcessToDatabase , CRUDUsersRepository {

    static {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void addUser(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        String email = user.getEmail();

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("INSERT INTO user (userName, password, email) " +
                    "VALUES(\"" + userName + "\", \"" + password + "\", \"" + email + "\")");

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean isPresentUserByNameOrEmail(User user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String query = "SELECT * FROM user WHERE userName=\"" + userName + "\" OR email=\"" + email + "\";";

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) return true;
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    @Override
    public User getUserByName(User user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String query = "SELECT * FROM user WHERE userName=\"" + userName + "\" OR email=\"" + email + "\";";

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                User userFromDB = new User();
                userFromDB.setUserID(resultSet.getInt(1));
                userFromDB.setUserName(resultSet.getString(2));
                userFromDB.setPassword(resultSet.getString(3));
                userFromDB.setEmail(resultSet.getString(4));
                System.out.println("");
                return userFromDB;

            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public User getUserByEmail(String email) {

        String query = "SELECT * FROM user WHERE email=\"" + email + "\";";

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                User userFromDB = new User();
                userFromDB.setUserID(resultSet.getInt(1));
                userFromDB.setUserName(resultSet.getString(2));
                userFromDB.setPassword(resultSet.getString(3));
                userFromDB.setEmail(resultSet.getString(4));
                return userFromDB;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<Note> getAllNotesFromUser(String nameUser, String selectedFolder, String selectedColor){

        List<Note> notes = new ArrayList<>();
        String query = "SELECT userID FROM user WHERE userName=\"" + nameUser + "\";";
        long userID = 0;

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                userID = resultSet.getLong(1);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        if(selectedColor.equals(Settings.COLOR_ALL)){
            query = "SELECT * FROM note WHERE userID=\"" + userID + "\" AND statusNote=\"" + selectedFolder + "\";";
        }else{
            query = "SELECT * FROM note WHERE userID=\"" + userID + "\" AND statusNote=\"" + selectedFolder + "\" AND colorNote=\"" + selectedColor + "\";";
        }

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                Note noteFromDB = new Note();
                noteFromDB.setNoteID(resultSet.getLong(1));
                noteFromDB.setUserID(resultSet.getLong(2));
                noteFromDB.setHeaderNote(resultSet.getString(3));
                noteFromDB.setTextNote(resultSet.getString(4));
                noteFromDB.setColorNote(resultSet.getString(5));
                noteFromDB.setDateCreateNote(resultSet.getDate(6));
                noteFromDB.setDateUpdateNote(resultSet.getDate(7));
                noteFromDB.setStatusNote(resultSet.getString(8));
                noteFromDB.setPositionNote(resultSet.getInt(9));
                notes.add(noteFromDB);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.reverse(notes);
        return notes;
    }

}
