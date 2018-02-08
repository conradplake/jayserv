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

package jayserv.service.security;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class HashedSessionGuard implements HttpSessionGuard{

  public HashedSessionGuard(){
  	sessionTable = new Hashtable();
  }  

  public void assignPrivilege(HttpSession session, Privilege priv){
  	sessionTable.put( session.getId(), priv );
  }
  
  public void deprivePrivilege(HttpSession session, Privilege priv){
  	sessionTable.remove( session.getId() );
  }
  
  public boolean hasPrivilege(HttpSession session, Privilege priv){  	
  	boolean has = false;
  	Privilege assignedPriv = (Privilege) sessionTable.get( session.getId() );
	if (assignedPriv!=null){		
	  List classChain = getClassChain( assignedPriv.getClass() );
	  has = classChain.contains( priv.getClass() );
	}
	return has;
  }
   
  protected List getClassChain(Class cls){
      LinkedList chain = new LinkedList();      
	  chain.add(cls);
      Class superclass = cls.getSuperclass();
      while(superclass!=null){
	    chain.add(superclass);
	    superclass = superclass.getSuperclass();
      }     
      return chain;
  }           
  
  public boolean isRegistered(HttpSession session){
  	return ( sessionTable.get(session.getId()) != null );
  }
  
  public void unregisterAll(){
  	sessionTable.clear();
  }   
  
  public void unregister(HttpSession session){
  	sessionTable.remove(session.getId());
  }   
  
  private Hashtable sessionTable;
  
}