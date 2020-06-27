package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.Map;

import jayserv.example.shop.comp.Basket;
import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.ProductTable;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.Input;
import jayserv.html.PlainText;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetBasketPage extends ResponseHandler{

  public GetBasketPage(){
  	super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"basket.html");	
  }    

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	ShopUser user = identifyShopUser( ctx.getSession() );
  	if(user==null){
  	  handleDenied(ctx);
	}
	else{	  
	  buildPage(user);	
  	  try{	    
	  	writePage(ctx.getResponse());
	  }
	  catch(java.io.IOException ioe){
	    throw new ServiceException( "IOException: "+ioe.getMessage() );
	  }	  
	}
  }
  
  private void buildPage(ShopUser user){ 
    Basket basket = user.getBasket();
	 	    	
  	int products = basket.products();  	  	  	
  	
	ProductTable productTable = new ProductTable( "productTable" );			
	
	PlainText username = new PlainText( user.getUsername() );
	username.setId("username");
	productTable.addHtmlElement(username);
	
  	Iterator it = basket.getProducts().iterator();
  	while(it.hasNext()){
  	  String productId = (String) it.next();
  	  Map product = ShopDB.getInstance().getMappedEntity("products", productId);
  	  PlainText name = new PlainText( (String) product.get("name") );
  	  int amount = basket.getAmount(productId);
  	  Input input = new Input( productId, Input.TYPE_TEXT, String.valueOf(amount) );
  	  input.setSize( 2 );  	  
	  productTable.addProduct(name, null, input);
  	}
	if(productTable.products()>0){
	  Input updateSubmit = new Input();
	  updateSubmit.setId("updateSubmit");
	  updateSubmit.setType( Input.TYPE_SUBMIT );
	  updateSubmit.setValue("update");	  
	  productTable.addHtmlElement(updateSubmit);	  
	}
	else{
	  PlainText info = new PlainText("Basket is empty");
	  info.setId("infoText");
	  productTable.addHtmlElement(info);
	}
	page.putHtmlComponent(productTable);	    	
  }    
    
}
