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

public class TextArea extends AbstractHtmlElement{
	
  public TextArea(){
	super();
  }
    
  public TextArea(String name){
   	super(name);
  }
  
  public TextArea(String name, String defaultText){
   	super(name);
   	setText(defaultText);
  }
   
  public void setText(String text){
  	this.text = text;
  }
    
  public String getText(){
  	return text;
  }
  
  public void setRows(int rows){
  	this.rows = rows;
  }
  
  public int getRows(){
  	return rows;
  }
  
  public void setCols(int cols){
  	this.cols = cols;
  }
  
  public int getCols(){
  	return cols;
  }

  public StringBuffer generateHtmlCode(StringBuffer buffer){
	buffer.append("<TEXTAREA name=\""+getName()+"\" rows=\""+rows+"\" cols=\""+cols+"\">\n");
	buffer.append(text);
	buffer.append("</TEXTAREA\n");	
	return buffer;
  }
  
  private String text = "";
  private int rows = 1;
  private int cols = 1;

}
