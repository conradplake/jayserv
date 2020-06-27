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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class DefaultFormMap implements FormMap{

  public DefaultFormMap(){
	mapTable = new Hashtable();
  }

  public void map(HttpServletRequest req){
  	try{	  
	  BufferedReader br = new BufferedReader( new InputStreamReader( req.getInputStream() ) );
	  String line = br.readLine();
	  while(line!=null){	    
		int equalSign = line.indexOf('=');
		String key    = line.substring(0, equalSign).trim();
		String val    = line.substring(equalSign+1).trim();
		if (val.length()>0){
		  putValue(key, val);
		}
	    line = br.readLine();
	  }      	  
  	}
  	catch(IOException ioe){
  	  throw new RuntimeException("Could not map request.", ioe);
  	}
  }
  
  public void putValue(String key, String val){
    List valList = (List) mapTable.get(key);
	if(valList==null){
	  valList = new LinkedList();
	  mapTable.put(key, valList);
	}
	valList.add(val);
  }
  
  public String getValue(String key){
  	String val = null;
  	List vals = (List) mapTable.get(key);
	if(vals!=null){
	  val = (String) vals.get(0);
	}	
  	return val;
  }
  
  public List getValues(String key){
  	List l = (List) mapTable.get(key);
	if(l==null){  	  
	  l = new LinkedList();
	}
	return l;
  }
  
  public Enumeration keys(){
  	return mapTable.keys();
  }
  
  private Hashtable mapTable;
  
}