package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jayserv.example.shop.comp.ProductTable;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.PlainText;
import jayserv.service.GlobalCache;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetPayDeskPage extends ResponseHandler implements ShopInputElements{

  public GetPayDeskPage(){    
	super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"paydesk.html");	
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{
  	  ShopUser user = identifyShopUser(ctx.getRequest().getSession());
	  if(user!=null){ 
	    buildPage(user);
		writePage(ctx.getResponse());
	  }
	  else{
	    handleDenied(ctx);
	  }
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}	
  }
  
  private void buildPage(ShopUser user){    
    Set productSet = user.getBasket().getProducts();
		
	ProductTable prodTable = new ProductTable("paydesk_productTable");
	  		
	int summa = 0;
	Iterator it = productSet.iterator();
  	while(it.hasNext()){
	  String productId = (String) it.next();
  	  Map product = ShopDB.getInstance().getMappedEntity("products", productId);
  	  String name   = (String) product.get("name");
  	  int amount = user.getBasket().getAmount(productId);
	  Integer price = new Integer( (String) product.get("price") );
	  summa += amount * price.intValue();
  	  PlainText nameElem   = new PlainText(name);
	  PlainText priceElem  = new PlainText(""+price.intValue());
	  PlainText amountElem = new PlainText(""+amount);
	  prodTable.addProduct(nameElem, priceElem, amountElem);
  	}	
	PlainText totalSum = new PlainText(""+summa);
	totalSum.setId("totalSum");
	prodTable.addHtmlElement( totalSum );
	
  	page.putHtmlComponent(prodTable);
	
  }      
   
}
