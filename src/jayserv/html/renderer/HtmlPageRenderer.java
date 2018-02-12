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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jayserv.html.HtmlComponent;
import jayserv.html.HtmlElement;
import jayserv.html.Iterative;
import jayserv.html.TemplatedHtmlPage;

public class HtmlPageRenderer{

  public HtmlPageRenderer(TemplatedHtmlPage page) {
  	this.page = page;
  }

	
	/*
		TemplateExample:
				
		...
		...
		
		<table>
		
		  ~DIR:ITERATE:comp1~
		  <tr>
		   
		   <td> ~JHC:comp1:id1~ </td>   <td> ~JHC:comp1:id2~ </td>
		   
		  </tr>
		  ~DIR:END~
				
		</table>
		
		...		
	*/


  public StringBuffer render() throws java.io.IOException{    
  	StringBuffer buffer	   = new StringBuffer();
  	BufferedReader bReader = page.getTemplateFileReader();
  	String line 		   = bReader.readLine();
	
  	while(line!=null){  	  
   	  Directive dir = filterDirective(line);
	  if (dir==null) {
	    buffer.append( doReplacement(line) );
	  } else{
		processDirective( dir, buffer, bReader );
	  }
  	  line = bReader.readLine();
   	}
  	bReader.close();
  	
  	return buffer;  	  	
  }
  
  
  public Directive filterDirective(String htmlCodeLine) {
  	Directive dir = null;
	int dirBegIdx = htmlCodeLine.indexOf(DIR_TAG_OPEN);
	if(dirBegIdx>0){
  	  int dirEndIdx = htmlCodeLine.indexOf(DIR_TAG_CLOSE, dirBegIdx+1);
  	  if(dirEndIdx>0 && dirBegIdx<dirEndIdx){
  	    String dirString = htmlCodeLine.substring(dirBegIdx+DIR_TAG_OPEN_LEN, dirEndIdx).trim();
		int sepIdx = dirString.indexOf(':');
		if (sepIdx < 0){
		  dir = new Directive(dirString, null);
		} else {
		  dir = new Directive(dirString.substring(0,sepIdx), dirString.substring(sepIdx+1));
		}
  	  }  	  
  	}  		
	return dir;
  }
  
  
  private void processDirective(Directive dir, StringBuffer buffer, BufferedReader reader) throws java.io.IOException{      
	//System.out.println( "HtmlPageRenderer: processing directive: "+dir.getDirective()+" for jhc: "+dir.getJHCId() );    
  	List codeSnip = new LinkedList();
	String line = reader.readLine();  	
  	while(line!=null){
	  Directive d = filterDirective(line);
	  if (d==null) {
	  	codeSnip.add(line);
	  	line = reader.readLine();
	  } else {
	    if (d.getDirective().equals(Directive.DIR_END)){
		  break;
		}		
	  }	  
  	}
	
	if ( dir.getDirective().equals(Directive.DIR_ITERATE) ) {
	  processIterationDirective( dir, buffer, codeSnip );
	}
	
  }
  
    
  private void processIterationDirective(Directive dir, StringBuffer buffer, List codeSnip) throws RenderException{
    Iterative iterative;
	
  	try{
	  iterative = (Iterative) page.getHtmlComponent( dir.getTargetJHCId() );	  	  
  	} 
	catch(ClassCastException cce){	
	  throw new RenderException( "JHC:"+dir.getTargetJHCId()+" is not an iterative component!" );
  	}
	
	if (iterative==null){
	  return; // skip
	}
	
	iterative.reset();
	while (iterative.hasNext()) {	
	  Iterator it = codeSnip.iterator();
	  while (it.hasNext()) {
	    buffer.append( doReplacement( (String)it.next() ) );
	  }
	  iterative.iterate();
	}
  }
  
  
  private String doReplacement(String htmlCodeLine){
  	int jhcBegIdx = htmlCodeLine.indexOf(JHC_TAG_OPEN);
	if(jhcBegIdx<0){
  	  return htmlCodeLine;	// nothing found
  	}
  	int jhcEndIdx = htmlCodeLine.indexOf(JHC_TAG_CLOSE, jhcBegIdx+1);
  	if(jhcEndIdx<0 || jhcBegIdx>jhcEndIdx){
  	  return htmlCodeLine;	// no valid tag found
  	} 
  	String tagString = htmlCodeLine.substring(jhcBegIdx+JHC_TAG_OPEN_LEN, jhcEndIdx).trim();
	int sepIdx = tagString.indexOf(':');
	if(sepIdx<0){
	  return htmlCodeLine;  // no valid tag found
	}
	
  	String componentId = tagString.substring(0,sepIdx).trim();
	String elementId   = tagString.substring(sepIdx+1).trim();
  	StringBuffer sb    = new StringBuffer();
  	sb.append( htmlCodeLine.substring(0,jhcBegIdx) );
  	HtmlComponent jhc = page.getHtmlComponent(componentId);
  	if(jhc!=null){
	  HtmlElement elem = jhc.getHtmlElement(elementId);
	  if(elem!=null){
  	    elem.generateHtmlCode(sb);
	  }
  	}  	
  	sb.append( doReplacement(htmlCodeLine.substring(jhcEndIdx+1)) );  // continue with rest of html code line
    return sb.toString();
  }
  
  
  public  static final String JHC_TAG_OPEN  = "~JHC:";
  public  static final String JHC_TAG_CLOSE = "~";
  private static final int JHC_TAG_OPEN_LEN = JHC_TAG_OPEN.length();
  
  
  public static final String DIR_TAG_OPEN   = "~DIR:";
  public static final String DIR_TAG_CLOSE  = "~";
  private static final int DIR_TAG_OPEN_LEN = DIR_TAG_OPEN.length();
  
  private TemplatedHtmlPage page;
}