package jayserv.example.shop.handler;

import java.util.Enumeration;

import jayserv.example.shop.comp.Basket;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.forms.DefaultFormMap;
import jayserv.html.forms.FormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class BasketUpdate extends DefaultShopHandler{
  
    public void handleSecured(ServiceContext ctx) throws ServiceException{
  	  ShopUser user = identifyShopUser(ctx.getSession());
  	  if(user==null){
  	    handleDenied(ctx);
  	  }
  	  else{	  
	    FormMap formmap = new DefaultFormMap();
	    formmap.map(ctx.getRequest());
	    Basket newBasket = toBasket(formmap);
	    user.setBasket(newBasket);
	    ctx.addHandler( new GetBasketPage() );	    	    
  	  }
    }    
 
    private Basket toBasket(FormMap formmap){	
      Basket basket = new Basket();
	  Enumeration e = formmap.keys();
	  while(e.hasMoreElements()){
	    String productId = (String) e.nextElement();
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
	  return basket;
    }
}
