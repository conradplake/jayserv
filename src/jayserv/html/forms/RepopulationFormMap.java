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

package jayserv.html.forms;

import jayserv.html.Input;
import jayserv.html.Select;
import jayserv.html.TextArea;


public class RepopulationFormMap extends DefaultFormMap{
	
  public Input repopulateInput(Input input){
  	String name = input.getName();
  	String mappedValue = getValue(name);
  	if(mappedValue!=null){
  	  input.setValue(mappedValue);
  	}
  	return input;
  }   
  
  
  public Select repopulateSelect(Select select){
  	String name = select.getName();
  	String mappedValue = getValue(name);
  	if(mappedValue!=null){
  	  select.selectByValue(mappedValue);
  	}
  	return select;
  }
  
  public TextArea repopulateTextArea(TextArea textarea){
  	String name = textarea.getName();
  	String mappedText = getValue(name);
  	if(mappedText!=null){
  	  textarea.setText(mappedText);
  	}
  	return textarea;
  }  
}