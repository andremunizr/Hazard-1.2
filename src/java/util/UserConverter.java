
package util;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import model.User;

@Named(value = "userConverter")
@RequestScoped
public class UserConverter implements Converter{

    @EJB
    private MainController controller;
    
    public UserConverter(){}

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value){
        try {            
            
            System.out.println("String: " + value);
            
            return controller.findOne( User.class , value );
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(UserConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value){
        
        System.out.println("Object: " + ( ( User ) value ).getId());
        
        return ( ( User ) value ).getId().toString();
    }
}