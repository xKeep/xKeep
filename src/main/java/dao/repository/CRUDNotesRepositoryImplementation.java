package dao.repository;

import util.setting.Settings;

import java.sql.*;


public class CRUDNotesRepositoryImplementation implements ExcessToDatabase, CRUDNotesRepository   {

    static {
        try{
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void changeNoteStatus(long noteID, String statusNote) {
        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ) {
            String query = "UPDATE note SET statusNote=\""+statusNote+"\" WHERE noteID=" + noteID;
            statement.executeUpdate(query);

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void changeNoteColor(long noteID, String setColorNote) {
        try(
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ){
            String query = "UPDATE note SET colorNote =\""+ setColorNote + "\" WHERE noteID=" + noteID;
            statement.executeUpdate(query);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public void editTextNote(long noteID, String headNote, String textNote) {
        try(
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ){
            String query = "UPDATE note SET headerNote=\"" +headNote+ "\" WHERE noteID="+ noteID;
            statement.executeUpdate(query);
            query = "UPDATE note SET textNote=\"" +textNote+ "\" WHERE noteID="+ noteID;
            statement.executeUpdate(query);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void createNewNote(String nameUserInSession,String headerNote, String textNote){

        String query = "SELECT userID FROM user WHERE userName=\"" + nameUserInSession + "\";";
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

        String colorNote = Settings.COLOR_FIRST;
        java.util.Date date = new java.util.Date();
        java.sql.Date dateCreateNote = new Date(date.getYear() , date.getMonth() , date.getDate());
        java.sql.Date dateUpdateNote = new Date(date.getYear() , date.getMonth() , date.getDate());
        String statusNote = Settings.ACTUAL_NOTES;
        int positionNote = 1;
        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ) {
            query = "INSERT INTO note (userID, headerNote, textNote ,colorNote ," +
                    " dateCreateNote , dateUpdateNote, statusNote , positionNote) " +
                    "VALUES(\"" + userID + "\",  \"" + headerNote + "\", \"" + textNote + "\", \"" + colorNote+ "\"," +
                    " \"" + dateCreateNote + "\", \"" + dateUpdateNote + "\", \"" + statusNote + "\", \"" + positionNote + "\")" ;
            statement.executeUpdate(query);

        }  catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void deleteNote(long id){

        String query = "DELETE FROM note WHERE noteID=\"" + id + "\";";
        long userID = 0;

        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                Statement statement = connection.createStatement();
        ) {
            System.out.println(query);
            statement.executeUpdate(query);

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
