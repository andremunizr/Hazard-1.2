
package util;

import java.util.Calendar;
import java.util.Date;

public enum BadgeEnum {
    
    FIRST_TASK( "Primeira Tarefa", "united-kingdom-badge.png", Calendar.getInstance().getTime() );
 
    private String name;
    private String image;
    private Date dateAcquired;
    
    BadgeEnum(String name, String image, Date dateAcquired){
        this.name = name;
        this.image = image;
        this.dateAcquired = dateAcquired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }
    
}