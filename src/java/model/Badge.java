
package model;

import java.util.Date;
import org.jongo.marshall.jackson.oid.ObjectId;

@JongoCollection( "badge" )
public class Badge {
    
    @ObjectId
    private String _id;
    private String name;
    private String image;
    private Date dateAcquired;

    public Badge(){};

    public Badge(String name, String image, Date dateAcquired) {
        this.name = name;
        this.image = image;
        this.dateAcquired = dateAcquired;
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_id != null ? _id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Badge)) {
            return false;
        }
        Badge other = (Badge) object;
        if ((this._id == null && other._id != null) || (this._id != null && !this._id.equals(other._id))) {
            return false;
        }
        return true;
    }
}
