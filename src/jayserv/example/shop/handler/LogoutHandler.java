package jayserv.example.shop.handler;

import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;
import jayserv.service.SessionGuard;

public class LogoutHandler extends DefaultShopHandler {

	public void handle(ServiceContext ctx, SessionGuard sessionGuard) throws ServiceException {
		sessionGuard.unregister(ctx.getSession());
		ctx.addHandler(new GetShopStartPage());
	}

}