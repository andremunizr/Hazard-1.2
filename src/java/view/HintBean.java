package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Hint;

/**
 *
 * @author daniel
 */
@Named(value = "hintBean")
@RequestScoped
public class HintBean {

    private Hint hint;
    
    @EJB
    private MainController controller;
    
    @Inject
    private LoggBean logBean;
    
    public HintBean() {
        hint = new Hint();
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }
    
    public List<Hint> findAll() throws UnknownHostException {
        return ( List<Hint> ) ( List<?> ) controller.getDocuments( Hint.class );
    }
    
    public void save() throws UnknownHostException {
        
        hint.setAuthorId( logBean.getSessionUser().getId() );
        
        controller.saveDocument( Hint.class, hint );
    }
}
