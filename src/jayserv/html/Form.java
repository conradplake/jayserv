
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

package jayserv.html;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Form extends AbstractHtmlElement{ 

  public Form(){
  	super();
	formelements = new LinkedList();
  }
  
  public Form(String action, String method, String enctype){
  	super();
	formelements = new LinkedList();
  	setAction(action);
  	setMethod(method);
  	setEnctype(enctype);	
  }

  public void setEnctype(String enctype){
  	this.enctype = enctype;  	  	
  }
  
  public void setAction(String action){
  	this.action = action;  	  	
  }
  
  public void setMethod(String method){
  	this.method = method;  	  	
  }
  
  public String getEnctype(){
  	return enctype;
  }
  
  public String getAction(){
  	return action;
  }
  
  public String getMethod(){
  	return method;
  }
  
  public void addElement(HtmlElement element)
  {
  	formelements.add(element);
  }
  
  
  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<FORM action=\""+action+"\" method=\""+method+"\" enctype=\""+enctype+"\">\n");  	
	Iterator it = formelements.iterator();
	while (it.hasNext())
	{
		((HtmlElement)it.next()).generateHtmlCode(buffer);
	}
  	buffer.append("</FORM>\n");
  	return buffer;
  }

  private String enctype="text/plain";
  private String action="";
  private String method="post";
  private List formelements;
}