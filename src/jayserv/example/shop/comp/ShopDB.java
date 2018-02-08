package jayserv.example.shop.comp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import jayserv.service.GlobalCache;

public class ShopDB{

  private ShopDB(){
  	try {     
	  schema_url = (String)( GlobalCache.getInstance().get(ShopGateway.DBSCHEMA_URL) );  
      File file = new File(schema_url);
 	  SAXBuilder builder = new SAXBuilder();
      dbschema = builder.build(file.toURL());
  	}
   	catch (JDOMException e){
      System.out.println("** ShopDB: could not load xmlFile - init failed!");
      System.out.println(e.getMessage());
      //e.printStackTrace();            
    }
    catch (IOException ioe){
      System.out.println("** ShopDB: could not load xmlFile - init failed!");
      System.out.println(ioe.getMessage());
      //ioe.printStackTrace();            
    }
  }
  
  public static ShopDB getInstance(){
    	if(instance==null){
    	  synchronized(ShopDB.class){
    	    if(instance==null){
    	      instance = new ShopDB();			  
    	    }
       	  }
    	}
    	return instance;	
  }  
  
  public String getNextId(String tablename){
  	int maxId = 1;
    Iterator it = getEntities(tablename).iterator();
    while(it.hasNext()){
	  Element entity   = (Element) it.next();
	  Element entityId = entity.getChild("id");
	  Integer id = new Integer( entityId.getText().trim() );
	  if(id.intValue() > maxId){
	    maxId = id.intValue();
	  }
	}
  	return ( new Integer(maxId+1) ).toString();
  }
  
  public Map mapEntity(Element entity){
  	HashMap map = new HashMap();  	
  	Iterator it = entity.getChildren().iterator();
  	while(it.hasNext()){
  	  Element columnElem = (Element) it.next();
  	  map.put( columnElem.getName(), columnElem.getText().trim() );
  	}  	
  	return map;
  }
  
  public List getEntitiesWhere(String tablename, String key, String value){
    List entityList = new LinkedList();
  	Iterator it = getEntities(tablename).iterator();
  	while(it.hasNext()){
  	  Element entity = (Element) it.next();
  	  String valuefound  = entity.getChild(key).getText().trim();
  	  if(valuefound.equals(value)){
  	    entityList.add(entity);
  	  }
  	}
  	return entityList;
  }
  
  public List getMappedEntitiesWhere(String tablename, String key, String value){
  	List entityList = new LinkedList();
  	Iterator it = getEntities(tablename).iterator();
  	while(it.hasNext()){
  	  Element entity = (Element) it.next();
  	  String valuefound  = entity.getChild(key).getText().trim();
  	  if(valuefound.equals(value)){
  	    entityList.add( mapEntity(entity) );
  	  }
  	}
  	return entityList;
  }
  
  public Element getEntity(String tablename, String id){
    Element entity = null;
  	Iterator it = getEntities(tablename).iterator();
  	while(it.hasNext()){
  	  Element entityElem = (Element) it.next();
  	  String idfound  = entityElem.getChild("id").getText().trim();
  	  if(idfound.equals(id)){
  	    entity = entityElem;
  	    break;
  	  }
  	}
  	return entity;
  }          
  
  
  public void addEntity(String tablename, Element entity){
    Element table = dbschema.getRootElement().getChild(tablename);
	if(table!=null){	  
	  table.addContent(entity);
	}  
  }
  
  public boolean removeEntity(String tablename, String entityId){
  	boolean success = false;
    Element entity = getEntity(tablename, entityId);
	if(entity!=null){
	  Element table  = entity.getParentElement();
	  table.removeContent(entity);
	  success = true;
	}
	return success;
  }
  
  public Map getMappedEntity(String tablename, String id){
  	Map map = null;
  	Element entity = getEntity(tablename, id);
  	if(entity!=null){
  	  map = mapEntity(entity);
	}
	return map;
  }
  
  public List getEntities(String tablename){
  	List entities = new LinkedList();  	  	
  	Element table = dbschema.getRootElement().getChild(tablename);
  	if(table!=null){
  	  entities = table.getChildren();
  	}
  	return entities;  
  }        
  
  public List getMappedEntities(String tablename){
  	List entities = new LinkedList();  	  	
  	Element table = dbschema.getRootElement().getChild(tablename);
  	if(table!=null){
  	  Iterator it = table.getChildren().iterator();
	  while(it.hasNext()){
	    entities.add( mapEntity( (Element)it.next() ) );
	  }
  	}
  	return entities;  
  }   
  
  
  public void save(){
  	try{
  	  XMLOutputter writer = new XMLOutputter();
      writer.output( dbschema, new FileWriter(schema_url) );
  	}
	catch(java.io.IOException ioe){
	  System.out.println("ShopDB.save():"+ioe.getMessage());
	}
  }

 
  private Document dbschema;
  private String schema_url;
  private static ShopDB instance;  
}
