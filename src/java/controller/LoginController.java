package controller;

import java.io.IOException;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Stateless
public class LoginController {

    public String logout() throws IOException{
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect( "login.xhtml" );
        return null;        
    } 
}