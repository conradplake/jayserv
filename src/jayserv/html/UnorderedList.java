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

public class UnorderedList extends AbstractHtmlElement{
	
    public UnorderedList(){
      super();
	  entries = new LinkedList();
    }
	
    public void addEntry(HtmlElement entry){
		entries.add(entry);
    }

    public StringBuffer generateHtmlCode(StringBuffer buffer){
	buffer.append("<UL>\n");
	Iterator it = entries.iterator();
	while(it.hasNext()){
	  buffer.append("<LI>\n");
	  ( (HtmlElement)it.next() ).generateHtmlCode(buffer);
	  buffer.append("</LI>\n");
	}
	buffer.append("</UL\n");
	return buffer;
    }
  	
	private List entries;
}
