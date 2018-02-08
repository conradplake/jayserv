package jayserv.example.shop.handler;

import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.PlainText;
import jayserv.html.SingleList;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetTestPage extends ResponseHandler implements ShopInputElements{

  public GetTestPage(){
  	super("../webapps/jayserv/src/jayserv/example/shop/templates/testarea.html");
  	initPage();
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{	    	
		writePage(ctx.getResponse());
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}	
  }
  
  private void initPage(){  	
    SingleList sl1 = new SingleList("sl1");
	for(int i=0;i<10;i++){
	  PlainText text = new PlainText("sl_1:"+i);
	  sl1.addHtmlElement(text);
	}
	sl1.iterationKey = "next";
	
	SingleList sl2 = new SingleList("sl2");
	for(int i=0;i<5;i++){
	  PlainText text = new PlainText("sl_2:"+i);
	  sl2.addHtmlElement(text);
	}
	sl2.iterationKey = "next";
	
	page.putHtmlComponent(sl1);
	page.putHtmlComponent(sl2);
	
  }  
}
