package controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import model.JongoCollection;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

@Stateless
public class MainController {
    private final String DATABASE_NAME = "Hazard";
    private DB db;
    private Jongo jongo;
    
    private Jongo getConnection() throws UnknownHostException {
        
        MongoClient client = new MongoClient( "localhost" );
        
        if( db == null ) {
            db = client.getDB( DATABASE_NAME );
        }
        
        if( jongo == null ) {
            jongo = new Jongo( db );
        }
        
        return jongo;
    }
    
    private String getDocumentName( Class type ) {
        JongoCollection annotation = ( JongoCollection ) type.getAnnotation( JongoCollection.class );
        
        return annotation.value();
    }
    
    private MongoCollection getCollection( Class type ) throws UnknownHostException {
        Jongo connection = getConnection();
        String documentName = getDocumentName( type );
        
        return connection.getCollection( documentName );
    }
    
    public List<Object> getDocuments( Class type ) throws UnknownHostException {
        List<Object> objects = new ArrayList<>();

        MongoCollection listRetrieved = getCollection( type );
        Iterable<Object> all = listRetrieved.find().as( type );
        
        for( Object obj : all ) {
            objects.add( obj );
        }
        
        return objects;
    }
    
    public Object findOne( Class type, String objectId ) throws UnknownHostException {
        MongoCollection collection = getCollection( type );
        
        return collection.findOne( new ObjectId( objectId ) ).as( type );
    }
    
    public Object findOneByAttr( Class type, String attr, String value ) throws UnknownHostException {
        MongoCollection collection = getCollection( type );
        
        return collection.findOne( "{" + attr + ": '" + value + "'}" ).as( type );
    }
    
    public Object saveDocument( Class type, Object obj ) throws UnknownHostException {
        MongoCollection collection = getCollection( type );
                
        collection.save( obj );
        
        return obj;
    }
    
    public void insertDocument( Class type, Object obj ) throws UnknownHostException {
        MongoCollection collection = getCollection( type );
        
        collection.insert( obj );
    }
    
    public void removeDocument( Class type, Object obj ) throws UnknownHostException {
        MongoCollection collection = getCollection( type );
        
        collection.remove( "" );
    }
}