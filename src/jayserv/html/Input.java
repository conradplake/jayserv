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

public class Input extends AbstractHtmlElement{

  public Input(){
  	super();
  }
  
  public Input(String name, String type, String value){
  	super(name);  	
  	setType(type);
  	setValue(value);
  }    
  
  public void setType(String type){
  	this.type = type;  	  	
  }
  
  public String getType(){
  	return type;
  }
  
  public void setValue(String value){
  	this.value = value;
	if(value.length()>size){
	  size = value.length();
	}
  }
  
  public String getValue(){
  	return value;
  }
  
  public void setSize(int size){
  	this.size = size;
  }
  
  public int getSize(){
  	return size;
  }
    
  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<Input name=\""+getName()+"\" type=\""+type+"\" value=\""+value+"\" size=\""+size+"\">\n");  
  	return buffer;
  }    
  
  private String type = "";
  private String value = "";
  private int size = 1;
  
  public static final String TYPE_TEXT 		= "text";
  public static final String TYPE_RADIO  	= "radio";
  public static final String TYPE_CHECKBOX 	= "checkbox";
  public static final String TYPE_PASSWORD 	= "password";
  public static final String TYPE_SUBMIT 	= "submit";
  public static final String TYPE_HIDDEN	= "hidden";  
}
