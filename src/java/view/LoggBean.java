package view;

import controller.LoginController;
import controller.MainController;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.Notification;
import model.User;

@Named(value = "loggBean")
@SessionScoped
public class LoggBean implements Serializable {

    @EJB
    private MainController controller;
    @EJB
    private LoginController loginController;
    @Inject
    private UserBean bean;
    private User sessionUser;

    public LoggBean(){}

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
                
        this.sessionUser = sessionUser;
    }

    @PostConstruct
    public void initializer() throws UnknownHostException {
        setSessionUser(new User());
    }

    public String login() throws UnknownHostException {

        User holdUser = (User) controller.findOneByAttr(User.class, "email", sessionUser.getEmail());
                
        if (holdUser != null) {
            if (canLogin(sessionUser, holdUser)) {
                
                Collections.reverse( holdUser.getNotifications() );
                
                setSessionUser(holdUser);
                return "index.xhtml?faces-redirect=true";
            } 
            else{
                setSessionUser( new User() );
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Erro", "Senha incorreta!"));
            }
        } 
        else{
            setSessionUser(new User());

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", "Usuário inexistente!"));
        }

        return null;
    }

    public boolean canLogin(User set, User found) {
        return set.getPassword().equals(found.getPassword())
                && set.getEmail().equals(found.getEmail());
    }
    
    public User save(User user) throws UnknownHostException{
        bean.save( user );
        return user;
    }
    
    public String logout() throws IOException{
        return loginController.logout();
    }
    
    public String setRead( Notification notif ) throws UnknownHostException{
        
        for(Notification n : sessionUser.getNotifications()){
            
            System.out.println("Id na notif:" + n.getId());
            System.out.println("Id da n.pag:" + notif.getId());
            
            try{                
                if( n.getId().equals(notif.getId()) ){
                    n.setRead(true);
                    break;
                }
            }catch( NullPointerException e ){
                e.getMessage();
            }
        }
        
        bean.save( sessionUser );
        return notif.getLink();
    }
    
    public List<Notification> getLastNotifications(){
        Collections.reverse( sessionUser.getNotifications() );
        
        return sessionUser.getNotifications();
    }
}