package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    
    public HintBean() {
    }
    
    @PostConstruct
    public void initializer() {
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
        System.out.println( "Salvar dica: " + hint );
        // controller.saveDocument( Hint.class, hint );
    }
}
