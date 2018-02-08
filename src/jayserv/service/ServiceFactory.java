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

public class ServiceFactory
{

	public static Service newServiceInstance(String classname)
	{
		Service service = null;
  		try{
  	  	  service = (Service) Class.forName(classname).newInstance();
  		}
  		catch(ClassNotFoundException cnfe){
  	  	  //System.out.println("ServiceFactory: newServiceInstance -> class '"+classname+"' not found!");
  	  	  //cnfe.printStackTrace();
		}
		catch(InstantiationException ie){
	  	  //System.out.println("ServiceFactory: newServiceInstance -> instantiation failed!");
  	  	  //ie.printStackTrace();
	 	}
		catch(IllegalAccessException iae){
	  	  //System.out.println("ServiceFactory: newServiceInstance -> illegal access!");
  	  	  //iae.printStackTrace();
  		}
  		return service;
	}
	
	public static ServiceHandler newServiceHandlerInstance(String classname){
  	  ServiceHandler handler = null;
  	  try{
  	    handler = (ServiceHandler) Class.forName(classname).newInstance();
  	  }
  	  catch(ClassNotFoundException cnfe){
  	    //System.out.println("ServiceFactory: newHandlerInstance -> class '"+classname+"' not found!");
  	    //cnfe.printStackTrace();
	  }
	  catch(InstantiationException ie){
	    //System.out.println("ServiceFactory: newHandlerInstance -> instantiation failed!");
  	    //ie.printStackTrace();
 	  }
	  catch(IllegalAccessException iae){
	    //System.out.println("ServiceFactory: newHandlerInstance -> illegal access!");
  	    //iae.printStackTrace();
  	  }
  	  return handler;
    }  
	
	public static Class newServiceHandlerClass(String classname){
  	  Class c = null;
  	  try{
  	    c = Class.forName(classname);
  	  }
  	  catch(ClassNotFoundException cnfe){
  	    //System.out.println("ServiceFactory: newHandlerInstance -> class '"+classname+"' not found!");
  	    //cnfe.printStackTrace();
	  }	  
  	  return c;
    }  
}