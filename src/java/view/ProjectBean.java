package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Project;
import model.User;

@Named(value = "projectBean")
@RequestScoped
public class ProjectBean {
    
    @EJB
    private MainController controller;
    private Project project;
    private List<Project> availableProjects;
    
    @Inject
    private LoggBean logBean;
    
    @PostConstruct
    public void initializer() throws UnknownHostException {
        setProject( new Project() );
        setAvailableProjects( ( List<Project> ) ( List<?> ) controller.getDocuments( Project.class ) );
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getAvailableProjects() throws UnknownHostException {
        
        if( availableProjects == null ) {
            setAvailableProjects( ( List<Project> ) ( List<?> ) controller.getDocuments( Project.class ) );
        }
        
        return availableProjects;
    }

    public void setAvailableProjects(List<Project> availableProjects) {
        this.availableProjects = availableProjects;
    }
    
    public String save() throws UnknownHostException {
        
        if( project.getName() != null ) {
            
            logBean.getSessionUser().getProjects().add( project );
            
            controller.saveDocument( User.class, logBean.getSessionUser() );
            controller.saveDocument( Project.class, project );
        }
        
        return "meus-projetos.xhtml";
        
    }
    
    public Project findOne( String id ) throws UnknownHostException {
        return ( Project ) controller.findOne( Project.class, id );
    }
    
}
