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
  
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jayserv.service.security.HttpSessionGuard;
  
public class ServiceContext{

  public ServiceContext(Service service, HttpServletRequest req, HttpServletResponse resp){  	
  	this.service = service;
  	this.req     = req;
  	this.resp    = resp;
  	handlerchain = new DefaultHandlerChain();  	
  	statemap     = new HashMap();
  }
  
  public Service getService(){
  	return service;
  }
  
  public HttpSession getSession(){
  	return req.getSession();
  }    
  
  public HttpServletRequest getRequest(){
  	return req;
  }
  
  public HttpServletResponse getResponse(){
  	return resp;
  }
  
  public void setHandlerChain(HandlerChain handlerchain){
  	this.handlerchain = handlerchain;
  }
  
  public HandlerChain getHandlerChain(){
  	return handlerchain;
  }  
    
  public void addHandler(ServiceHandler handler){
  	handlerchain.addHandler(handler);
  }    
  
  public void pushHandler(ServiceHandler handler){
  	handlerchain.insertHandlerAt(handler, 0);
  } 
  
  public ServiceHandler nextHandler(){  	
	return handlerchain.getNextHandler();
  } 

  protected void setSessionGuard(HttpSessionGuard guard){
  	sessionGuard = guard;
  }
  
  public HttpSessionGuard getSessionGuard(){
  	return sessionGuard;
  }
  
  public void setHandled(boolean bool){
  	handled = bool;    
  }
  
  public boolean handled(){
  	return handled;
  }
  
  public void putState(Object key, Object value){
        statemap.put(key, value);
  }

  public Object getState(Object key){
	return statemap.get(key);
  }
  
  private Service	      	  service;
  private HttpServletRequest  req;
  private HttpServletResponse resp;
  private HttpSessionGuard    sessionGuard;
  private HandlerChain	      handlerchain;
  private HashMap	      	  statemap;
  private boolean 	      	  handled = false;
}
