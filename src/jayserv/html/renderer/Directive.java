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

public class Directive{
  
  public Directive(String dir, String jhcId){
  	this.dir   = dir;
	this.jhcId = jhcId;
  }
  
  
  public String getDirective(){
    return dir;  
  }
  
  
  public String getTargetJHCId() {
  	return jhcId;
  }
  
  public String toString(){
    return dir;
  }
  
  
  private String dir;
  private String jhcId;
  
  public static final String DIR_ITERATE = "ITERATE";
  public static final String DIR_END 	 = "END";

}