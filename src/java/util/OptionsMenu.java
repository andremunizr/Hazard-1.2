
package util;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

@Named(value = "optionsMenu")
@RequestScoped
public class OptionsMenu {

    private List<SelectItem> items;
    
    public OptionsMenu(){}

    public List<SelectItem> getStatus() {
        
        items = new ArrayList<>();
        
        items.add( new SelectItem( "executando" ) );
        items.add( new SelectItem( "pausada" ) );
        items.add( new SelectItem( "concluída" ) );
        
        return items;
    }

    public List<SelectItem> getSexo(){
        items = new ArrayList<>();
        
        items.add( new SelectItem( "masculino" ) );
        items.add( new SelectItem( "feminino" ) );
        
        return items;
    }
}
