/*

JayServ

Copyright (C) 2001, Author: Conrad Plake.

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation; either version 2 of the License, or (at your option) any later 
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; 
if not, write to the 
Free Software Foundation, Inc., 
59 Temple Place, 
version. This program is distributed in the hope that it will be useful, but 
Suite 330, 
Boston, 
MA 02111-1307 
USA

*/

package jayserv.service;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
  
  public HandlerChain(){ 
  	chain = new ArrayList<>();
  }
  
  public int size(){
  	return chain.size();	
  }
  
  public ServiceHandler getNextHandler(){
  	ServiceHandler next = null;
  	if( chain.size()>0 ){
  	  next = (ServiceHandler) chain.remove(0);
  	}
  	return next;
  }
  
  public void addHandler(ServiceHandler handler){
  	chain.add(handler);
  }
  
  public void insertHandlerAt(ServiceHandler handler, int posidx){
  	chain.add(posidx, handler);
  }

  private List<ServiceHandler> chain;

}