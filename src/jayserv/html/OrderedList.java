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

public class OrderedList extends AbstractHtmlElement{
	
  public OrderedList(){
    super();
    entries = new LinkedList();
  }

  public void setType(String type){
    this.type = type;
  }

  public String getType(){
    return type;
  }

  public void setStart(int start){
    this.start = start;
  }

  public int getStart(){
    return start;
  }
	
  public void addEntry(HtmlElement entry){
    entries.add(entry);
  }

  public StringBuffer generateHtmlCode(StringBuffer buffer){
	buffer.append("<OL type=\""+type+"\" start=\""+start+"\">\n");
	Iterator it = entries.iterator();
	while(it.hasNext()){
	  buffer.append("<LI>\n");
	  ( (HtmlElement)it.next() ).generateHtmlCode(buffer);
	  buffer.append("</LI>\n");
	}
	buffer.append("</OL>\n");
	return buffer;
  }

    private String type = "";
    private int start   = 1;    
	private List entries;  
}