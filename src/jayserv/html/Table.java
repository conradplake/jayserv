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

public class Table extends AbstractHtmlElement{

  public Table(){
  	super();
  }

  public Table(int rows, int cols){  	
  	super();
    	init(rows, cols);
  }
  
  public void init(int rows, int cols){
  	this.rows = rows;
  	this.cols = cols;
    	table = new HtmlElement[rows][cols];
	alignTable = new String[rows][cols];
  } 
  
  public void setWidth(int width){
  	this.width = width;
  }
  
  public void setHeight(int height){
  	this.height = height;
  }
   
  public HtmlElement getElementAt(int row, int col){
  	return table[row][col];
  }
  
  public void setElementAt(HtmlElement element, int row, int col){
  	table[row][col] = element;
  } 

  public void alignTableCell(int row, int col, String alignStmt){
        alignTable[row][col] = alignStmt;
  }
  
  public StringBuffer generateHtmlCode(StringBuffer buffer){
  	buffer.append("<TABLE width=\""+width+"%\" height=\""+height+"%\" rows=\""+rows+"\" cols=\""+cols+"\">\n");
  	for(int r=0;r<rows;r++){
  	  buffer.append("<TR>\n");
  	  for(int c=0;c<cols;c++){
  	    buffer.append("<TD");
	    if(alignTable[r][c]!=null){
	      buffer.append( " align=\""+alignTable[r][c]+"\"");
	    }
	    buffer.append(">");
  	    if(table[r][c]!=null){ 
  	      table[r][c].generateHtmlCode(buffer);
  	    }
  	    buffer.append("</TD>\n");
  	  }
  	  buffer.append("</TR>\n");
  	}
  	buffer.append("</Table>\n");  	
  	return buffer;	
  }
 
  private int rows;
  private int cols;
  
  private int width;
  private int height;
  
  private HtmlElement[][] table;
  private String[][] alignTable;
}
