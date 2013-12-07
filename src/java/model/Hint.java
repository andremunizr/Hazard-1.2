
package model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.jongo.marshall.jackson.oid.ObjectId;

@XmlRootElement
@JongoCollection( "hint" )
public class Hint{
    
    @ObjectId 
    private String _id;
    private String title;
    private String text;
    private String authorId;
    private Date date;
    
    public Hint(){}

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String author) {
        this.authorId = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }    
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_id != null ? _id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Hint)) {
            return false;
        }
        Hint other = (Hint) object;
        if ((this._id == null && other._id != null) || (this._id != null && !this._id.equals(other._id))) {
            return false;
        }
        return true;
    }
}
