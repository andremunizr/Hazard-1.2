package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private List<Hint> hints;
    
    @EJB
    private MainController controller;
    
    @Inject
    private LoggBean logBean;
    
    public HintBean() throws UnknownHostException {
        hint = new Hint();
    }
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        hints = findAll();
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }
    
    public List<Hint> findAll() throws UnknownHostException {
        return ( List<Hint> ) ( List<?> ) controller.getDocuments( Hint.class );
    }
    
    public Hint findOne( String id ) throws UnknownHostException {
        return ( Hint ) controller.findOne( Hint.class, id );
    }
    
    public void save() throws UnknownHostException {
        
        hint.setAuthorId( logBean.getSessionUser().getId() );
        
        controller.saveDocument( Hint.class, hint );
    }
}
