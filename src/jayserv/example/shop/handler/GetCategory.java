package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jayserv.example.shop.comp.ErrorPage;
import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.ProductTable;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.Input;
import jayserv.html.Link;
import jayserv.html.PlainText;
import jayserv.html.renderer.HtmlPageRenderer;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetCategory extends ResponseHandler implements ShopInputElements{

  public GetCategory(){  	
	super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"category.html");	
  }    

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{	  	  
	  String categoryID = ctx.getRequest().getParameter(CATEGORY);	  	 	  
  	  if(categoryID==null){
  	    ErrorPage errorpage = new ErrorPage( "No Category specified", new Link(TO_CATALOG,"Back") );  	    
		HtmlPageRenderer renderer = new HtmlPageRenderer(errorpage);   
  	    ctx.getResponse().getOutputStream().println( renderer.render().toString() );
  	  }
  	  else{
  	    buildPage(categoryID);  
		writePage(ctx.getResponse());
  	  }  	  	  
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}	
  }
  
  private void buildPage(String categoryID){
    List productList = ShopDB.getInstance().getMappedEntitiesWhere("products", "categoryID", categoryID);		
	
	ProductTable productTable = new ProductTable( "productTable" );			
	
  	Iterator it = productList.iterator();  		
  	while(it.hasNext()){
  	  Map product  = (Map) it.next();
  	  PlainText name  = new PlainText( (String) product.get("name") );
	  PlainText price = new PlainText( (String) product.get("price") );
  	  String id    = (String) product.get("id");  	  
  	  Input amount = new Input(id, Input.TYPE_TEXT, "0");
  	  amount.setSize(2);
	  productTable.addProduct(name, price, amount);
  	}
  	page.putHtmlComponent(productTable);  	
  }          
}
