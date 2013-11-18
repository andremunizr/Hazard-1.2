package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import model.Badge;
import model.Comment;
import model.Notification;
import model.Task;
import model.User;
import util.BadgeEnum;
import util.NotificationSource;
import util.SourceRetriever;

@Named(value = "taskBean")
@RequestScoped
public class TaskBean {

    @EJB
    private MainController controller;
    @Inject
    private NotificationBean notBean;
    @Inject
    private LoggBean logBean;
    private Task task;
    private Notification taskNotification;
    private List<Task> tasks;
    private User responsable;
    private String responsableId;
    private Comment comment;

    public TaskBean() {
    }

    public List<Task> getTasks() throws UnknownHostException {

        if (tasks == null) {
            setTasks((List<Task>) (List<?>) controller.getDocuments(Task.class));
        }

        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTask() throws UnknownHostException {
        setTasks((List<Task>) (List<?>) controller.getDocuments(Task.class));
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Task findOne(String id) throws UnknownHostException {
        return (Task) controller.findOne(Task.class, id);
    }

    @PostConstruct
    public void initializer() throws UnknownHostException {
        setTasks((List<Task>) (List<?>) controller.getDocuments(Task.class));
        setTask(new Task());
        setComment(new Comment());
    }

    public void save() throws UnknownHostException {
        
        responsable = findResponsable(responsableId);

        buildTask("save");
        controller.saveDocument(Task.class, task);

        buildNotification( NotificationSource.TASK, task.getId() );

        buildResponsable();
        controller.saveDocument(User.class, responsable);

        notBean.setNotification(taskNotification);
        notBean.save();

        setTask(new Task());
    }

    public void edit(String id) throws UnknownHostException {
        
        Task uTask = (Task) controller.findOne(Task.class, id);
        uTask.setStatus(task.getStatus());
        
        responsable = findResponsable( uTask.getResponsableId() );
        
        if (!("").equals(comment.getText()))
            uTask.getComments().add(buildComment(comment.getText()));

        if( testFirstTask( uTask )){      
            buildNotification( BadgeEnum.FIRST_TASK, id );
            notBean.setNotification( taskNotification );
            notBean.save();
            controller.saveDocument( User.class, responsable );
        }
        
        for (Task t : logBean.getSessionUser().getTasks()) {

            if (t.getId().equals(uTask.getId())) {
                t.setStatus(task.getStatus());
                t.getComments().add(buildComment(comment.getText()));
                break;
            }
        }

        controller.saveDocument(Task.class, uTask);

        try {
            controller.saveDocument(User.class, logBean.getSessionUser());
        } catch (EJBException e) {
            System.out.println("Erro ao persistir objeto.");
            System.out.println(e.getMessage());
        }
    }

    public boolean testFirstTask(Task testTask) throws UnknownHostException {

        Date today = Calendar.getInstance().getTime();
                
        if ( testTask.getStatus().equals("concluída") ){
            
            if ( !logBean.getSessionUser().getHaveFirstTaskComplete() ){
                
                if ( testTask.getFinishDate().after( today ) ){
                    
                    logBean.getSessionUser().setHaveFirstTaskComplete( true );
                    
                    Badge badge = new Badge( BadgeEnum.FIRST_TASK.getName(),
                                             BadgeEnum.FIRST_TASK.getImage(),
                                             BadgeEnum.FIRST_TASK.getDateAcquired());

                    logBean.getSessionUser().getBadges().add( badge );                    
                    return true;
                }
            }            
        }
        
        return false;
    }

    public User findResponsable(String id) throws UnknownHostException {
        return (User) controller.findOne(User.class, id);
    }

    public String buildLink(Enum srcType, String id){
        
        String base = SourceRetriever.sourceLink( srcType );
                        
        if( srcType == NotificationSource.TASK )
            return base + "?faces-redirect=true&id=" + id;
        
        return base + "?faces-redirect=true";
    }

    private void buildTask(String type) {
        task.setAuthor(logBean.getSessionUser().getName());
        task.setAuthorId(logBean.getSessionUser().getId().toString());
        task.setResponsableId( responsableId );

        if (!type.equals("save")) {
            if (comment != null) {
                if (!comment.getText().equals("")) {
                    task.getComments().add(buildComment(comment.getText()));
                }
            }
        }
    }

    private void buildNotification( Enum srcType, String id ) throws UnknownHostException {
                
        taskNotification = new Notification();
        String srcPic = SourceRetriever.sourcePic(srcType, task.getAuthorId(), controller);
        
        
        if (srcPic != null)
            taskNotification.setPicture(srcPic);
        
        taskNotification.setText( SourceRetriever.sourceText( srcType ));
        taskNotification.setSourceId( task.getAuthorId() );
        taskNotification.setSourceType( srcType.toString() );
        
        String link = buildLink( srcType , id );        
        taskNotification.setLink(link);        
        logBean.getSessionUser().getNotifications().add( taskNotification );        
    }

    private Comment buildComment(String text) {

        Comment cmt = new Comment();
        Calendar cal = Calendar.getInstance();

        cmt.setAuthor(logBean.getSessionUser().getName());
        cmt.setDate(cal.getTime());
        cmt.setText(text);

        return cmt;
    }

    private void buildResponsable() {

        responsable.getNotifications().add(taskNotification);
        responsable.getTasks().add(task);
    }
}