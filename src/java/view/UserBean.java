package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Badge;
import model.User;

@Named(value = "userBean")
@RequestScoped
public class UserBean {

    @Inject
    private LoggBean loggBean;
    @EJB
    private MainController controller;
    private User user;
    private List<User> users;
    
    public UserBean(){}
    
    public List<User> getUsers() throws UnknownHostException {
        
        if( users == null ) {
            setUsers( ( List<User> ) ( List<?> ) controller.getDocuments( User.class ) );
        }
        
        return users;
    }
    
    public List<User> getUsersNot( User user ) throws UnknownHostException {
        
        if( users == null ) {
            setUsers( getUsers() );
        }
        
        for( User u : users ){
            if( u.getId().equals( loggBean.getSessionUser().getId() )){
                users.remove( u ); break;
            }
        }
        
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() throws UnknownHostException {
        setUsers( ( List<User> ) ( List<?> ) controller.getDocuments( User.class ) );
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setUsers( ( List<User> ) ( List<?> ) controller.getDocuments( User.class ) );
        setUser( new User() );
    }
    
    public void save() throws UnknownHostException {
                        
        controller.saveDocument( User.class, user );
        
        setUser( new User() );
    }
    
    public void save(User user) throws UnknownHostException{
        
        Collections.reverse( user.getNotifications() );
        
        controller.saveDocument( User.class, user );        
        
        Collections.reverse( user.getNotifications() );
    }
    
    public User findOne( String objectId ) throws UnknownHostException{
        return (User) controller.findOne( User.class, objectId );
    }    
    
    public String getMembersThatAcquiredBadge( String badge ) throws UnknownHostException{
        
        int members = 0;
        
        for( User u : getUsers() ){            
            for( Badge b : u.getBadges() ){
                
                if( b.getName().equals( badge ) ){
                    
                    System.out.println("Insígnia " + badge + ", o " + u.getName() + " tem.");
                    
                    members += 1;
                }                
            }            
        }
        
        return members != 1 ? members + " membros conquistaram"
                            : "1 membro conquistou";
    }    
}