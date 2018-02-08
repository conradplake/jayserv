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



class SelectOption{

  public SelectOption(String option){
    this.option = option;
	this.value  = option;
  }
  
  public SelectOption(String option, String value){
    this.option = option;
	this.value  = value;
  }
  
  public void setValue(String value){
    this.value = value;
  }
  
  public void setSelected(boolean bool){
  	selected = bool;
  }
  
  
  boolean selected;  
  String option;
  String value;
}


public class Select extends AbstractHtmlElement{

  public Select(){
  	super();
  	optionList = new LinkedList();
  }

  public Select(String name, int size, boolean multiple){
  	super(name);
  	optionList = new LinkedList();  
  	setSize(size);
  	setMultiple(multiple);  	
  }
  
  public void setSize(int size){
  	this.size = size;  	  	
  }    
  
  public void setMultiple(boolean value){
  	this.multiple = value;  	  	
  }
   
  public int getSize(){
  	return size;
  }
  
  public boolean isMultiple(){
  	return multiple;
  }
  
  public void addOption(String option){
  	optionList.add( new SelectOption(option) );
  }
  
  public void addOption(String option, String val){
  	optionList.add( new SelectOption(option, val) );
  }
  
  public void selectByOption(String option){
  	Iterator it = optionList.iterator();
	while(it.hasNext()){
	  SelectOption so = (SelectOption) it.next();
	  if( option.equals(so.option) ){
	    so.setSelected( true );
		break;
	  }
	}
  }
  
  public void selectByValue(String val){
  	Iterator it = optionList.iterator();
	while(it.hasNext()){
	  SelectOption so = (SelectOption) it.next();
	  if( val.equals(so.value) ){
	    so.setSelected(true);
		break;
	  }
	}
  }
  
  public void setValue(String option, String val){
  	Iterator it = optionList.iterator();
	while(it.hasNext()){
	  SelectOption so = (SelectOption) it.next();
	  if( option.equals(so.option) ){
	    so.value = val;
		break;
	  }
	}
  }
  
  public List getOptions(){
  	return optionList;
  }
    
  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<SELECT name=\""+getName()+"\" size=\""+size+"\"");
  	if(isMultiple()){ 
  	  buffer.append(" multiple");
  	}
  	buffer.append(">\n");
  	Iterator it = getOptions().iterator();	
	while(it.hasNext()){  	  
	  SelectOption so = (SelectOption) it.next();	  
	  buffer.append("<OPTION");
	  if(so.selected){	    
		buffer.append(" selected");
	  }
	  buffer.append(" value=\""+so.value+"\">"+so.option+"\n");  	  
  	}
	
  	buffer.append("</SELECT>\n");  	
  	return buffer;
  }

  private int size = 1;
  private boolean multiple;
  private List optionList;
}