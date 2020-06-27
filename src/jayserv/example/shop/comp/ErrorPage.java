package jayserv.example.shop.comp;

import jayserv.html.DefaultHtmlComponent;
import jayserv.html.Link;
import jayserv.html.PlainText;
import jayserv.html.TemplatedHtmlPage;

public class ErrorPage extends TemplatedHtmlPage{

  public ErrorPage(String errormessage, Link forwardLink){
  	setTemplateURL(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"error.html");
	
	DefaultHtmlComponent comp = new DefaultHtmlComponent( "errorCmp" );
	
  	PlainText errorText = new PlainText(errormessage);
  	errorText.setId("message");  	
  	forwardLink.setId("forwardlink");
	
	comp.addHtmlElement( errorText );  		
	comp.addHtmlElement( forwardLink );
	
	super.putHtmlComponent( comp );
	
  }

}