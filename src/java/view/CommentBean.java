
package view;

import controller.MainController;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import model.Comment;

@Named(value = "commentBean")
@RequestScoped
public class CommentBean {

    @EJB
    private MainController controller;
    private Comment comment;
        
    public CommentBean(){}
    
    
    
}