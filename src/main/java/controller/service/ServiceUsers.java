package controller.service;

import dao.entity.Note;
import dao.entity.User;
import dao.repository.CRUDUsersRepositoryImplementation;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.List;

public class ServiceUsers {



    private boolean isEmail(String email) {
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }



    public boolean isPresentUserName(String nameUserInSession){
        User user = new User(nameUserInSession, nameUserInSession, nameUserInSession);
        if (new CRUDUsersRepositoryImplementation().isPresentUserByNameOrEmail(user)) return true;
        return false;
    }



    public boolean isCorrectUserForRegistration(String userName, String password, String email){
        if (userName.length()<3) return false;
        if (password.length()<8) return false;
        if (!isEmail(email)) return false;
        if (isEmail(userName)&isEmail(email)&(!userName.equals(email))) return false;

        User user = new User(userName, password, email);
        if (new CRUDUsersRepositoryImplementation().isPresentUserByNameOrEmail(user)) return false;

        return true;
    }



    public void addUser(String userName, String password, String email){
        User user = new User(userName, password, email);
        new CRUDUsersRepositoryImplementation().addUser(user);
    }



    public boolean isPresentEmail(String email){

        if (!isEmail(email)) return false;
        User user = new CRUDUsersRepositoryImplementation().getUserByEmail(email);
        if (user==null) return false;

        return true;
    }



    public void sendEmailToUser(String userEmail)
    {
        User user = new CRUDUsersRepositoryImplementation().getUserByEmail(userEmail);
        String userName = user.getUserName();
        String password = user.getPassword();

        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("it.claster.xkeep", "it08082017"));
            email.setTLS(true);
            email.setFrom("it.claster.xkeep@gmail.com");
            email.setSubject("Restore xKEEP");
            email.setMsg("Hello from xKEEP ... :-)\n" + "You registration Name: " + userName + "\n" + "You password: " + password);
            email.addTo(userEmail);
            email.send();
        } catch (EmailException ex) {
            ex.printStackTrace();
        }
    }



    public String getNameRegisteredUser(String login, String password){

        User user = new User(login, password, login);

        if (new CRUDUsersRepositoryImplementation().isPresentUserByNameOrEmail(user)){

            User userFromDB = new CRUDUsersRepositoryImplementation().getUserByName(user);

            if ((user.getUserName().equals(userFromDB.getUserName())||(user.getUserName().equals(userFromDB.getEmail())))
                    &(user.getPassword().equals(userFromDB.getPassword()))){
                return userFromDB.getUserName();
            }
        }

        return null;
    }



    public List<Note> getAllNotesFromUser(String nameUser, String selectedFolder, String selectedColor){

        List<Note> allNotesFromUser = new CRUDUsersRepositoryImplementation().getAllNotesFromUser(nameUser, selectedFolder, selectedColor);

        return allNotesFromUser;
    }

}
