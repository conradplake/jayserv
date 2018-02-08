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

public class Link extends AbstractHtmlElement{

  public Link(){
  	super();
  }

  public Link(String targetURL, String linkText){
  	super();
  	setTargetURL(targetURL);
  	setLinkText(linkText);
  }

  public void setTargetURL(String targetURL){
  	this.targetURL = targetURL;
	parameters     = 0;
  }
  
  public void setLinkText(String linkText){
  	this.linkText = linkText;
  }
  
  public void addParameter(String key, String val){
  	if(parameters==0){
 	  targetURL = targetURL + "?"+key+"="+val;
  	}else{
	  targetURL = targetURL + "&"+key+"="+val;
  	}
	parameters++;
  }

  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<A HREF=\""+targetURL+"\">"+linkText+"</A>\n");  	
  	return buffer;
  }
    
  protected String linkText  = "";
  protected String targetURL = "";
  private int parameters; 
}
