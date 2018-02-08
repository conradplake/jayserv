package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jayserv.example.shop.comp.Links;
import jayserv.example.shop.comp.ProductTable;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.Input;
import jayserv.html.PlainText;
import jayserv.html.Select;
import jayserv.html.forms.DefaultFormMap;
import jayserv.service.GlobalCache;
import jayserv.service.IncompatibleRequestException;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetShopCatalog extends ResponseHandler implements ShopInputElements, Links{  

  public GetShopCatalog(){    
	super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"shopcatalog.html");
	initPage();
  }
  
  private void initPage(){
	Select catSelect = new Select();    
	catSelect.setId("categorySelect");
	catSelect.setName(CATEGORY);
	Iterator it = ShopDB.getInstance().getMappedEntities("categories").iterator();
	while(it.hasNext()){
	  Map catMap = (Map) it.next();
	  String id   = (String) catMap.get("id");
	  String name = (String) catMap.get("name");
	  catSelect.addOption(name, id);
	}
	
	comp = new DefaultHtmlComponent("catalog");
	comp.addHtmlElement(catSelect);
	
	page.putHtmlComponent( comp );
  }   
 
  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	try{	  	  
	  ShopUser user = identifyShopUser(ctx.getSession());
	  setInfoText(user);
	  String catId = getCategoryId(ctx.getRequest());	  
	  if(catId!=null){
	  	loadCategory(catId);
		Select sel = (Select) comp.getHtmlElement("categorySelect");
		sel.selectByValue(catId);
	  }
	  writePage(ctx.getResponse());
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}
  }
  
  public void setInfoText(ShopUser user){
    PlainText text = new PlainText("Items in your basket: "+user.getBasket().products());
	text.setId("infoText");
	comp.addHtmlElement(text);	
  }
    
  private String getCategoryId(HttpServletRequest req){
  	String id = null;
	try{
	  DefaultFormMap formMap = new DefaultFormMap();
	  formMap.map(req);
  	  id = (String) formMap.getValue(CATEGORY);
    }catch(IncompatibleRequestException ire){
	  id = null;
    }
	return id;
  }
    
  private void loadCategory(String catId){
  	ProductTable pt = new ProductTable("productTable");
	Iterator it = ShopDB.getInstance().getMappedEntitiesWhere("products", "categoryID", catId).iterator();
	while(it.hasNext()){
	  Map prodMap = (Map) it.next();
	  PlainText name  = new PlainText( (String)prodMap.get("name") );
	  PlainText price = new PlainText( (String)prodMap.get("price") );	  
  	  Input amount    = new Input( (String)prodMap.get("id"), Input.TYPE_TEXT, "0" );
  	  amount.setSize(2);
	  pt.addProduct(name, price, amount);
	}	
	if(pt.products()>0){
	  page.putHtmlComponent(pt);
	  
	  Input collectInput = new Input( OPTION, Input.TYPE_SUBMIT, "collect" );
	  collectInput.setId("collectSubmit");
	  
	  PlainText nameColumnText = new PlainText("Name");
	  nameColumnText.setId("nameColumnText");
	  
	  PlainText priceColumnText = new PlainText("Price");
	  priceColumnText.setId("priceColumnText");
	  
	  PlainText amountColumnText = new PlainText("Amount");
	  amountColumnText.setId("amountColumnText");
	  
	  pt.addHtmlElement(collectInput);
	  pt.addHtmlElement(nameColumnText);
	  pt.addHtmlElement(priceColumnText);
	  pt.addHtmlElement(amountColumnText);
	}
  }    
  
  private DefaultHtmlComponent comp;
   
}
