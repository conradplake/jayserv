package jayserv.example.shop.comp;

import java.util.LinkedList;
import java.util.List;

import jayserv.example.shop.handler.GetUserAdminPage;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.HtmlElement;
import jayserv.html.Iterative;
import jayserv.html.Link;
import jayserv.html.PlainText;

public class UserSearchResult extends DefaultHtmlComponent implements Iterative, ShopInputElements, Links{


  public UserSearchResult(String id){
    super(id);
	userNames = new LinkedList();
	userIds   = new LinkedList();	
  }
  
  public boolean hasNext(){
  	return (pointer < users);
  }
  
  public HtmlElement getHtmlElement(String id){  	
    if (id.equals(NEXT_NAME) ){
	  return new PlainText( (String)userNames.get(pointer) );
	}
	else if (id.equals(NEXT_ID) ){
	  //return new PlainText( (String)productIds.get(pointer) );
	  String userId = (String) userIds.get(pointer);
	  Link link = new Link(TO_USERADMINISTRATION, userId);
	  link.addParameter(GetUserAdminPage.FILL_BY_ID, userId);
	  return link;
	}
	else if (id.equals(TOTAL) ){
	  return new PlainText(""+users);
	}	
	else return super.getHtmlElement(id);
  }
  
  public void iterate(){  	
	pointer++;
  }
  
  public void reset(){
  	pointer = 0;
  }
      
  public void addUser(String id, String name){	  	
  	userIds.add(id);
	userNames.add(name);
	users++;
  }
  
  public int users(){
    return users;
  }
  
  
  public static final String NEXT_NAME = "nextName";
  public static final String NEXT_ID   = "nextId";
  public static final String TOTAL     = "total";
  
  private List userIds, userNames;
  private int pointer;
  private int users;
}
