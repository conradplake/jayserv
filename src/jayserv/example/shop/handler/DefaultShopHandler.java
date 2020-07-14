package jayserv.example.shop.handler;

import javax.servlet.http.HttpSession;

import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.Links;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.renderer.HtmlFileRenderer;
import jayserv.service.ServiceHandler;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class DefaultShopHandler extends ServiceHandler implements Links {

	@Override
	public void handleDenied(ServiceContext ctx) throws ServiceException {
		try {
			HtmlFileRenderer renderer = new HtmlFileRenderer(
					GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR) + "accessdenied.html");
			ctx.getResponse().getOutputStream().println(renderer.render().toString());
			ctx.setHandled(true);
		} catch (java.io.IOException ioe) {
			throw new ServiceException(ioe.getMessage(), ioe);
		}
	}

	public ShopUser identifyShopUser(HttpSession session) {
		return (ShopUser) session.getAttribute(ATTR_SHOPUSER);
	}

	public static final String ATTR_SHOPUSER = "ShopUser";

}