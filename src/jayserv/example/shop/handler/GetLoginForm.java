package jayserv.example.shop.handler;

import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.Input;
import jayserv.service.GlobalCache;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetLoginForm extends ResponseHandler implements ShopInputElements{

  public GetLoginForm(){  	
	super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"login.html");	
  	init();
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{	    	
		writePage(ctx.getResponse());
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}	
  }
  
  private void init(){  	
	Input input_username = new Input();
	input_username.setId(USERNAME);
	input_username.setName(USERNAME);
	input_username.setType(Input.TYPE_TEXT);
	input_username.setSize(10);
	
	Input input_password = new Input();
	input_password.setId(PASSWORD);
	input_password.setName(PASSWORD);	    
	input_password.setType(Input.TYPE_PASSWORD);
	input_password.setSize(10);		
	
	DefaultHtmlComponent comp = new DefaultHtmlComponent( "loginputs" );
	comp.addHtmlElement(input_username);
	comp.addHtmlElement(input_password);
		
	page.putHtmlComponent(comp);	
  }  
}
