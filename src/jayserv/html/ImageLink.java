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

public class ImageLink extends Link{

  public ImageLink(String targetURL, Img img){
  	super();
  	setTargetURL(targetURL);  	
	this.img = img;
  }
  

  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<A HREF=\""+targetURL+"\">");
	img.generateHtmlCode(buffer);
	buffer.append("</A>\n");
  	return buffer;
  }
    
	
  private Img img;
}
