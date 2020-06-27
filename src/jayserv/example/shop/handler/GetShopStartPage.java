package jayserv.example.shop.handler;

import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.html.renderer.HtmlFileRenderer;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetShopStartPage extends ResponseHandler{

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{   	  
	  HtmlFileRenderer renderer = new HtmlFileRenderer(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"shopmain.html");
  	  ctx.getResponse().getOutputStream().println( renderer.render().toString() );
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( ioe.getMessage(), ioe);
	}
  }
}
