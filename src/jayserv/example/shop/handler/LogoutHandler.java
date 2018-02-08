package jayserv.example.shop.handler;

import jayserv.example.shop.comp.ShopInputElements;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class LogoutHandler extends DefaultShopHandler implements ShopInputElements{


  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	doLogout(ctx);
	ctx.addHandler( new GetShopStartPage() );
  }  
  
  private void doLogout(ServiceContext ctx){  	
	ctx.getSessionGuard().unregister( ctx.getSession() );
  }
  
}