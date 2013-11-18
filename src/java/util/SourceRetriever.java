
package util;

import controller.MainController;
import java.net.UnknownHostException;
import model.User;

public class SourceRetriever {

    public static String sourcePic( Enum srcType, String id, MainController ctr ) throws UnknownHostException{

        if( srcType == NotificationSource.TASK )
            return ( (User) ctr.findOne( User.class, id )).getImage();
        
        else 
        if( srcType == BadgeEnum.FIRST_TASK )
            return BadgeEnum.FIRST_TASK.getImage();
        
        return null;
    }
    
    public static String sourceText( Enum srcType ){
        
        if( srcType == NotificationSource.TASK )
            return Messenger.NOVA_TAREFA.getMsg();
        
        else
        if( srcType == BadgeEnum.FIRST_TASK )
            return Messenger.NOVA_INSIGNIA.getMsg();
        
        return null;        
    }
    
    public static String sourceLink( Enum srcType ){
        
        if( srcType == NotificationSource.TASK )
            return Messenger.NOVA_TAREFA.getLink();
        
        else
        if( srcType == BadgeEnum.FIRST_TASK )
            return Messenger.NOVA_INSIGNIA.getLink();
        
        return null;
    }
    
}
