package jayserv.service;


import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cache{

  public Cache(){
    objects = new Hashtable();
	exdates = new Hashtable();
  }      

  
  public void put(String handle, Object obj, Date exDate){
    objects.put(handle, obj);    
	if (exDate!=null)
	{
	  exdates.put(handle, exDate);
	}
  }
  
  
  public Object get(String handle){
  	Object cached = objects.get(handle);
	if(cached!=null){
	  Date exdate = (Date) exdates.get(handle);
	  if(exdate!=null){
	    if( exdate.getTime() < System.currentTimeMillis() ){  // object expired?
		  cached = null;		  
		}
	  }
	}	
    return cached;
  }     
   
  
  public void remove(String handle){
    objects.remove(handle);
    exdates.remove(handle);
  }
  
  
  public boolean inUse(String handle){
  	return objects.containsKey(handle);
  }
  
  
  public List cleanup(){
    List expiredHandles = new LinkedList();	
	Enumeration handles = objects.keys();
	long currentTime    = System.currentTimeMillis();
		
	while(handles.hasMoreElements()){
	  String handle = (String) handles.nextElement();
	  Date exdate   = (Date)   exdates.get(handle);	  
	  if(exdate!=null){
	    if( exdate.getTime() < currentTime ){  // object expired?
		  expiredHandles.add(handle);
		}
	  }
	}
	
	Iterator it = expiredHandles.iterator();
	while(it.hasNext()){
	  remove( (String) it.next() );
	}	
	
	return expiredHandles;
  }        
  
  private Hashtable objects;
  private Hashtable exdates;
}
 