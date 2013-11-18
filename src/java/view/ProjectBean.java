package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import model.Project;

@Named(value = "projectBean")
@ViewScoped
public class ProjectBean {
    
    @EJB
    private MainController controller;
    private Project project;
    private List<Project> availableProjects;
    
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
    
    public void save() throws UnknownHostException {
        
        if( project.getName() != null ) {
            controller.saveDocument( Project.class, project );
        }
        else {
            System.out.println( "Nome do projeto está nulo." );
        }
        
    }
    
}
