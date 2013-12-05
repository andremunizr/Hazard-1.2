package view;

import controller.MainController;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import model.Task;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named(value = "chartBean")
@RequestScoped
public class ChartBean {

    @Inject
    private LoggBean loggBean;
    @EJB
    private MainController controller;
    private CartesianChartModel taskModel;

    public ChartBean() {
        taskModel = new CartesianChartModel();
    }

    @PostConstruct
    public void loadTask() throws UnknownHostException {
        loadAllTasks();
        loadDoneTasks();
    }

    public void loadAllTasks() {

        ChartSeries tasks = new ChartSeries();
        List<Task> taskList = loggBean.getSessionUser().getTasks();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDate;
        int index = 1;

        tasks.setLabel("Tarefas");

        for (Task t : taskList) {

            formatedDate = dateFormat.format(t.getReleaseDate());

            tasks.set(formatedDate, index++);
        }

        taskModel.addSeries(tasks);
    }

    public void loadDoneTasks() throws UnknownHostException{

        ChartSeries doneTasks = new ChartSeries();
        List<Task> taskList = loggBean.getSessionUser().getTasks();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDoneDate;
        HashMap<String,Integer> taskMap = new HashMap<>();
        
        doneTasks.setLabel("Tarefas concluídas");

        for( Task t : taskList ){
            
            if( "concluída".equals( t.getStatus() ) ){                
                System.out.println("Entrou no if...");
            
                Date completeDate = ((Task) controller.findOne( Task.class, t.getId() )).getCompleteDate();            
                formatedDoneDate = dateFormat.format( completeDate );
                
                if( !taskMap.containsKey( formatedDoneDate ) ){                    
                
                    System.out.println("Não tem " + formatedDoneDate + ", vou criar...");
                    taskMap.put( formatedDoneDate, 0 );                    
                }
                System.out.println("Adicionando ao dia " + formatedDoneDate + ".");
                taskMap.put( formatedDoneDate, ( taskMap.get( formatedDoneDate ) + 1 ) );                
            }
        }
        
        Iterator it = taskMap.entrySet().iterator();
      
        while( it.hasNext() ) {
            Map.Entry pairs = (Map.Entry)it.next();
            
            doneTasks.set( pairs.getKey() , ( ( Integer ) pairs.getValue()).intValue() );
            
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        taskModel.addSeries( doneTasks );
    }

    public CartesianChartModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(CartesianChartModel taskModel) {
        this.taskModel = taskModel;
    }
}
