
package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.User;

@Named(value = "userBean")
@RequestScoped
public class UserBean {

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
        
        controller.saveDocument( User.class, user );        
    }
    
    public User findOne( String objectId ) throws UnknownHostException{
        return (User) controller.findOne( User.class, objectId );
    }
    
}
