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

package jayserv.html.renderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class HtmlFileRenderer{
	
  public HtmlFileRenderer(){
  }	

  public HtmlFileRenderer(String fileURL){
  	setFileURL( fileURL );
  }
  
  public void setFileURL(String fileURL){
  	this.fileURL = fileURL;
  }
  
  public String getFileURL(){
  	return fileURL;
  }

  public StringBuffer render() throws IOException{  	  	  
  	StringBuffer buffer    = new StringBuffer();
  	BufferedReader bReader = new BufferedReader( new FileReader(fileURL) );
  	String line = bReader.readLine();
  	while(line!=null){  	  
   	  buffer.append(line);
  	  line = bReader.readLine();
  	}
  	bReader.close();
  	return buffer;  	  	
  }
 
  private String fileURL;    

}