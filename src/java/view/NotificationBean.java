
package view;

import controller.MainController;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import model.Notification;

@Named(value = "notificationBean")
@SessionScoped
public class NotificationBean implements Serializable {

    @EJB
    private MainController controller;
    private Notification notification;
    private List<Notification> notifications;
    
    public NotificationBean(){}
    
    public List<Notification> getNotifications() throws UnknownHostException {
        
        if( notifications == null ) {
            setNotifications( ( List<Notification> ) ( List<?> ) controller.getDocuments( Notification.class ) );
        }
        
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Notification getNotification() throws UnknownHostException {
        setNotifications( ( List<Notification> ) ( List<?> ) controller.getDocuments( Notification.class ) );
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setNotifications( ( List<Notification> ) ( List<?> ) controller.getDocuments( Notification.class ) );
        setNotification( new Notification() );
    }
    
    public void save() throws UnknownHostException {
        
        controller.saveDocument( Notification.class, notification );
        
        setNotification( new Notification() );
    }
    
}