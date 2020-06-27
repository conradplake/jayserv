package jayserv.example.shop.comp;

import java.util.LinkedList;
import java.util.List;

import jayserv.html.DefaultHtmlComponent;
import jayserv.html.HtmlElement;
import jayserv.html.Iterative;
import jayserv.html.Link;
import jayserv.html.PlainText;

public class ProductSearchResult extends DefaultHtmlComponent implements Iterative, ShopInputElements, Links{


  public ProductSearchResult(String id){
    super(id);
	productNames = new LinkedList<>();
	productIds   = new LinkedList<>();	
  }
  
  public boolean hasNext(){
  	return (pointer < products);
  }
  
  public HtmlElement getHtmlElement(String id){  	
    if (id.equals(NEXT_NAME) ){
	  return new PlainText( (String)productNames.get(pointer) );
	}
	else if (id.equals(NEXT_ID) ){
	  //return new PlainText( (String)productIds.get(pointer) );
	  String prodId = (String) productIds.get(pointer);
	  Link link = new Link(TO_PRODUCTADMINISTRATION, prodId);
	  link.addParameter(FILL_BY_ID, prodId);
	  return link;
	}
	else if (id.equals(TOTAL) ){
	  return new PlainText(""+products);
	}	
	else return super.getHtmlElement(id);
  }
  
  public void iterate(){  	
	pointer++;
  }
  
  public void reset(){
  	pointer = 0;
  }
      
  public void addProduct(String id, String name){	  	
  	productIds.add(id);
	productNames.add(name);
	products++;
  }
  
  public int products(){
    return products;
  }
  
  
  public static final String NEXT_NAME = "nextName";
  public static final String NEXT_ID   = "nextId";
  public static final String TOTAL     = "total";
  
  public static String FILL_BY_ID = "fill_by_id";
  
  private List<String> productIds, productNames;
  private int pointer;
  private int products;
}
