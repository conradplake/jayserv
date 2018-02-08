package jayserv.example.shop.handler;

import java.util.Enumeration;

import jayserv.example.shop.comp.Basket;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.forms.DefaultFormMap;
import jayserv.html.forms.FormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class Collector extends DefaultShopHandler implements ShopInputElements{
  
  
  public void handleSecured(ServiceContext ctx) throws ServiceException{
    ShopUser user = identifyShopUser(ctx.getSession());	  
    if(user==null){
      handleDenied(ctx);
    }
    else{	   
      FormMap formmap = new DefaultFormMap();
      formmap.map( ctx.getRequest() );	    		
	  collect(user.getBasket(), formmap);		
	  ctx.addHandler( new GetShopCatalog() );			  
    }
  }    
 
  private void collect(Basket basket, FormMap formmap){    	
  	ShopDB ins = ShopDB.getInstance();
  	Enumeration e = formmap.keys();
    while(e.hasMoreElements()){
	  String productId = (String) e.nextElement();
	  if( ins.getEntity("products", productId) == null ){
	    continue; // no valid productId!!
	  }
	  String amount = formmap.getValue(productId);				
	  try{		
	    Integer i = new Integer(amount);
	    if(i.intValue() > 0){	      
	      basket.put(productId, i.intValue());
	    }
	  }
	  catch(NumberFormatException nfe_ignore){
	  }
    }
  }
  
}
