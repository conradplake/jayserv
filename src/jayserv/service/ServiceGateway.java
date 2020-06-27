/*

JayServ

Copyright (C) 2001, Author: Conrad Plake.

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation; either version 2 of the License, or (at your option) any later 
version. This program is distributed in the hope that it will be useful, but 
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; 
if not, write to the 
Free Software Foundation, Inc., 
59 Temple Place, 
Suite 330, 
Boston, 
MA 02111-1307 
USA

*/

package jayserv.service;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;

public class ServiceGateway extends HttpServlet{     

  public void setSessionGuard(SessionGuard guard){
  	this.sessionGuard = guard;
  }
  
  public SessionGuard getSessionGuard(){
  	return sessionGuard;
  }

  public String registerService(Service service, Class handlerClass){  	
  	String servicename = getServiceName( service.getClass() );
  	serviceTable.put( servicename, service );
  	handlerTable.put( servicename, handlerClass );
  	return servicename;
  }
  
  
  public void setServiceAccessPrivilege(String servicename, Privilege priv){
  	privilegeTable.put(servicename, priv);
  }
  
  
  public Service deregisterService(String servicename){
  	handlerTable.remove(servicename);
  	return (Service)serviceTable.remove(servicename);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException{  	
   	dispatch(req, resp);
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException{
    	dispatch(req, resp);
  }
  
  public void dispatch(HttpServletRequest req, HttpServletResponse resp){  	
  	String servicename = extractServicename(req);  	
  	Service service = (Service) serviceTable.get(servicename);	
  	if(service!=null){	
  	  ServiceContext ctx = new ServiceContext(service, req, resp);
	  ctx.setHandlerChain( buildHandlerChain(service) );
  	  try{
  	    serve(ctx);
  	  }
  	  catch(ServiceException se){  	   
  	    se.printStackTrace();
  	    writeServiceExceptionMessage(resp, se.getMessage());
  	  }
  	}
  	else{
  	  writeUnknownServiceMessage(resp, servicename);
  	}
  }

  private HandlerChain buildHandlerChain(Service service){
	HandlerChain chain = new HandlerChain();	
	Iterator it = getServiceChain(service.getClass()).iterator();
	while( it.hasNext() ){
	  String servname = (String)it.next();
	  Service serv = (Service) serviceTable.get(servname);	  
	  if(serv!=null){
	    Class handlerClass = (Class) handlerTable.get(servname);
		try{
		  ServiceHandler handler = (ServiceHandler) handlerClass.newInstance();
		  Privilege priv 		 = (Privilege) privilegeTable.get(servname);
		  if(priv!=null){
		    ((ServiceHandler)handler).setAccessPrivilege(priv);
		  }
	      chain.addHandler(handler);
		}catch(InstantiationException ie){
		  System.out.println("ServiceGateway.buildHanldlerChain(): "+ie.getMessage());
		  System.out.println("Handler skipped for Service:"+servname);
		}catch(IllegalAccessException iae){
		  System.out.println("ServiceGateway.buildHanldlerChain(): "+iae.getMessage());
		  System.out.println("Handler skipped for Service:"+servname);
		}catch(ClassCastException cce){
		  System.out.println("ServiceGateway.buildHanldlerChain(): "+cce.getMessage());
		  System.out.println("Handler skipped for Service:"+servname);
		}
	  }
	}
	return chain;
  }     
  
  public void serve(ServiceContext ctx) throws ServiceException{
  	ServiceHandler handler = ctx.nextHandler();
  	while( handler!=null && !ctx.handled() ){
  	  handler.handle(ctx, sessionGuard);
  	  handler = ctx.nextHandler();
  	}
  }
         
  public String extractServicename(HttpServletRequest req){
  	String servletpath = req.getServletPath();  	
  	String servicename;
  	if(servletpath.indexOf('.')>0){ 
  	  servicename = servletpath.substring( servletpath.lastIndexOf('/')+1, servletpath.lastIndexOf('.') );
  	}
  	else{
  	  servicename = servletpath.substring( servletpath.lastIndexOf('/')+1 );
   	}  	
  	return servicename;
  }         
 
  public void init() throws ServletException{    	
   	serviceTable   = new Hashtable();
   	handlerTable   = new Hashtable();
	privilegeTable = new Hashtable();
   	sessionGuard   = new SessionGuard();    	
  }
  
  public List<String> loadServices(Document serviceDocument){  	
  	List<String> result = new LinkedList();
  	Iterator<Element> it = serviceDocument.getRootElement().getChildren(SERVICE_TAG).iterator();
  	while(it.hasNext()){	
  	  Element serviceElem    = it.next();
	  String serviceclass	 = serviceElem.getChild(SERVICECLASS_TAG).getText().trim();	
	  String handlerclass    = serviceElem.getChild(HANDLERCLASS_TAG).getText().trim();	  
	  Element privilegeElem  = serviceElem.getChild(PRIVILEGECLASS_TAG);	  	 
	  Service service        = ServiceFactory.newServiceInstance(serviceclass);
	  Class<ServiceHandler> handlerClass     = ServiceFactory.newServiceHandlerClass(handlerclass);	  
	  if(service==null){
	    System.out.println("** ServiceGateway: Could not load service class  '"+serviceclass+"'. Discarding service.");
	    continue;
	  }
	  if(handlerClass==null){
	    System.out.println("** ServiceGateway: Could not load service handler '"+handlerclass+"'. Discarding service.");
	    continue;
	  }	  	  
 	  String handle = registerService(service, handlerClass);
	  
	  if(privilegeElem!=null){
	    String classname = privilegeElem.getText().trim();
	    Privilege privilege	= PrivilegeFactory.newPrivilegeInstance(classname);
	    setServiceAccessPrivilege( handle, privilege );
	  }
	  result.add(handle);
  	}
  	return result;
  }    
      
  protected List<String> getServiceChain(Class serviceclass){
      LinkedList<String> servicechain = new LinkedList();
      LinkedList<Class> superclasses = new LinkedList();
      Class superclass = serviceclass.getSuperclass();
      while(superclass!=null){
	  superclasses.add(superclass);
	  superclass = superclass.getSuperclass();
      }
      while(superclasses.size()>0){
	  servicechain.add( getServiceName((Class)superclasses.removeLast()) );
      }
      servicechain.add( getServiceName(serviceclass) );
      return servicechain;
  }

  protected String getServiceName(Class serviceclass){
	return getServiceName(serviceclass.getName());
  }

  protected String getServiceName(String serviceclassname){	
	int dotIdx = serviceclassname.lastIndexOf('.');
	if(dotIdx>0){
	  return serviceclassname.substring(dotIdx+1);
	}
	else{
  	  return serviceclassname;
	}
  }
  
  protected void writeUnknownServiceMessage(HttpServletResponse resp, String servicename){  	
  	try{  
  	  ServletOutputStream os = resp.getOutputStream();
  	  os.println("<html>");
  	  os.println("<title>");
  	  os.println("ServiceGateway");
  	  os.println("</title>");  	
  	  os.println("<body>");
  	  os.println("ServiceGateway: No handler available for service '"+servicename+"'.");
  	  os.println("<br>");
  	  os.println("<br>");
  	  os.println("Available Services:");
  	  Enumeration e = serviceTable.keys();
  	  while(e.hasMoreElements()){
  	    os.println("<br>");
  	    os.println(" - "+(String)e.nextElement());
  	  }
  	  os.println("</body>");
  	  os.println("</html>");
  	}
  	catch(java.io.IOException ioe){
  	  System.out.println("** ServiceGateway: could not write to client");
  	  ioe.printStackTrace();
  	}
  }  
  
  protected void writeServiceExceptionMessage(HttpServletResponse resp, String exceptionmessage){  	
  	try{  
  	  ServletOutputStream os = resp.getOutputStream();
  	  os.println("<html>");
  	  os.println("<title>");
  	  os.println("ServiceGateway");
  	  os.println("</title>");  	
  	  os.println("<body>");
  	  os.println("ServiceGateway: An internal service exception occured while processing your request.");
  	  os.println("<br>");  	  
  	  os.println("<br>");  	  
  	  os.println("Exception was: "+exceptionmessage);
  	  os.println("</body>");
  	  os.println("</html>");
  	}
  	catch(java.io.IOException ioe){
  	  System.out.println("** ServiceGateway: could not write to client");
  	  ioe.printStackTrace();
  	}
  }  
  
  public static String SERVICE_TAG 		= "service";
  public static String HANDLERCLASS_TAG 	= "handler-class";
  public static String SERVICECLASS_TAG 	= "service-class";
  public static String PRIVILEGECLASS_TAG  = "privilege-class";
    
  private Hashtable serviceTable;
  private Hashtable handlerTable;
  private Hashtable privilegeTable;
  private SessionGuard sessionGuard;
}
