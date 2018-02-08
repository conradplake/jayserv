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

import javax.servlet.http.HttpSession;

import jayserv.service.DefaultServiceHandler;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class SecureServiceHandler extends DefaultServiceHandler{
  
  public final void handle(ServiceContext servicecontext) throws ServiceException{     
  	HttpSession session    = servicecontext.getSession();		
  	HttpSessionGuard guard = servicecontext.getSessionGuard();  
	if( accessPrivilege==null || (guard!=null && guard.hasPrivilege(session,accessPrivilege)) ){
  	  handleSecured(servicecontext);
  	}
  	else{		
  	  handleDenied(servicecontext);
  	}
  }
  
  /* overwrite */
  public void handleDenied(ServiceContext servicecontext) throws ServiceException{
  }
  
  /* overwrite */
  public void handleSecured(ServiceContext servicecontext) throws ServiceException{
  }
  
  public void setAccessPrivilege(Privilege privilege){
  	accessPrivilege = privilege;
  }  
  
  private Privilege accessPrivilege;
} 