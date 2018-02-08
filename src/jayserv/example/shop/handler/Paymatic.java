package jayserv.example.shop.handler;

import jayserv.example.shop.comp.ShopUser;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class Paymatic extends DefaultShopHandler{

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	ShopUser user = identifyShopUser(ctx.getSession());	  
    if(user==null){
      handleDenied(ctx);
    }
	user.getBasket().removeAll();
    ctx.addHandler( new GetShopStartPage() );
  }
}
