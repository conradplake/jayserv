package jayserv.example.shop.comp;

import java.util.LinkedList;
import java.util.List;

import jayserv.html.DefaultHtmlComponent;
import jayserv.html.HtmlElement;
import jayserv.html.Iterative;

public class ProductTable extends DefaultHtmlComponent implements Iterative{


  public ProductTable(String id){
    super(id);
	productNames = new LinkedList();
	productPrices = new LinkedList();
	productAmounts = new LinkedList();
  }
  
  public boolean hasNext(){
  	return (pointer < products);
  }
  
  public HtmlElement getHtmlElement(String id){  	
    if (id.equals(PROD_NAME) ){
	  return (HtmlElement) productNames.get(pointer);
	}
	else if (id.equals(PROD_PRICE) ){
	  return (HtmlElement) productPrices.get(pointer);
	}
	else if (id.equals(PROD_AMOUNT) ){
	  return (HtmlElement) productAmounts.get(pointer);
	}	
	else return super.getHtmlElement(id);
  }
  
  public void iterate(){  	
	pointer++;
  }
  
  public void reset(){
  	pointer = 0;
  }
      
  public void addProduct(HtmlElement name, HtmlElement price, HtmlElement amount){	
  	productNames.add( name );
	productPrices.add( price );
	productAmounts.add( amount );
	products++;
  }
  
  
  public int products(){
    return products;
  }
  
  public static final String PROD_NAME   = "prod_name";
  public static final String PROD_PRICE  = "prod_price";
  public static final String PROD_AMOUNT = "prod_amount";
  
  private List productNames, productPrices, productAmounts;
  private int pointer;
  private int products;
}