package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.ProductSearchResult;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.HtmlComponent;
import jayserv.html.Input;
import jayserv.html.PlainText;
import jayserv.html.Select;
import jayserv.html.forms.RepopulationFormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;


public class GetProductAdminPage extends ResponseHandler implements ShopInputElements{

  public GetProductAdminPage(){    
    super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"productadmin.html");	
	initPage();
  }
  
  private void initPage(){
	Input prodName = new Input( PRODUCT_NAME, Input.TYPE_TEXT, "" );
    prodName.setId( "nameInput" );	
	prodName.setSize(15);
	
	Input prodId = new Input( PRODUCT_ID, Input.TYPE_TEXT, "" );
    prodId.setId( "idInput" );	
	prodId.setSize(3);
	
	Input prodPrice = new Input( PRODUCT_PRICE, Input.TYPE_TEXT, "" );
    prodPrice.setId( "priceInput" );
	prodPrice.setSize(3);
	
	Select categorySelect = new Select();
	categorySelect.setName(CATEGORY);
	categorySelect.setId( "categorySelect" );
	List categories = ShopDB.getInstance().getMappedEntities("categories");		
	Iterator it = categories.iterator();
	while(it.hasNext()){
	  Map catMap = (Map) it.next();
	  String catName = (String) catMap.get("name");
	  String catId   = (String) catMap.get("id");
	  categorySelect.addOption(catName, catId);
	}
	
	DefaultHtmlComponent prodForm = new DefaultHtmlComponent("productForm");
	prodForm.addHtmlElement(prodId);
	prodForm.addHtmlElement(prodName);
	prodForm.addHtmlElement(prodPrice);
	prodForm.addHtmlElement(categorySelect);
	
	Input addUpateSubmit = new Input(OPTION, Input.TYPE_SUBMIT, ADD_UPDATE_BUTTON_LABEL);
	addUpateSubmit.setId("add/updateButton");	
	Input searchSubmit = new Input(OPTION, Input.TYPE_SUBMIT, SEARCH_BUTTON_LABEL);
	searchSubmit.setId("searchButton");	
	Input deleteSubmit = new Input(OPTION, Input.TYPE_SUBMIT, DELETE_BUTTON_LABEL);
	deleteSubmit.setId("deleteButton");	
	
	prodForm.addHtmlElement( deleteSubmit );
	prodForm.addHtmlElement( addUpateSubmit );
	prodForm.addHtmlElement( searchSubmit );	
			
	ProductSearchResult psr = (ProductSearchResult) GlobalCache.getInstance().get("productSearchResult");
	if(psr!=null){
	  page.putHtmlComponent(psr);
	}	
	page.putHtmlComponent( prodForm );
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{
    try{
	  String prodId = ctx.getRequest().getParameter(ProductSearchResult.FILL_BY_ID);
	  if(prodId!=null && prodId.length()>0){
	    fillForm(prodId);
	  }
	  writePage(ctx.getResponse());
	}
	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}
  }
  
  
  public void setInfoText(String text){
  	PlainText ptext = new PlainText( text );
	ptext.setId("infoText");
  
    HtmlComponent comp = page.getHtmlComponent("productForm");	
	comp.addHtmlElement( ptext );
  }
  
  
  public void fillForm(String productId){
  	Map productMap = ShopDB.getInstance().getMappedEntity("products", productId);
	
	if(productMap==null){
	  return;	
	}	
    HtmlComponent form = page.getHtmlComponent("productForm");
	
	Input idInput = (Input) form.getHtmlElement("idInput");
	idInput.setValue( (String)productMap.get("id") );
			
	Input nameInput = (Input) form.getHtmlElement("nameInput");	
	nameInput.setValue( (String)productMap.get("name") );	
	
	Input priceInput = (Input) form.getHtmlElement("priceInput");
	priceInput.setValue( (String)productMap.get("price") );
	
	Select catSelect = (Select) form.getHtmlElement("categorySelect");	
	catSelect.selectByValue( (String)productMap.get("categoryID") );
  }
  
  
  public void fillSearchResultList(String prodId, String prodName, String prodPrice, String categoryId){
  	ProductSearchResult psr = new ProductSearchResult("productSearchResult");
	List found = new LinkedList();
	
	if(prodId!=null && prodId.length()>0){
	  Map prodMap = ShopDB.getInstance().getMappedEntity("products", prodId);
	  if(prodMap!=null){
	    found.add(prodMap);
	  }
	}
	else{
	  Iterator it = ShopDB.getInstance().getMappedEntities("products").iterator();
	  while(it.hasNext()){
	    Map prodMap  = (Map) it.next();	    
	    String name  = (String) prodMap.get("name");
	    String price = (String) prodMap.get("price");
	    String catId = (String) prodMap.get("categoryID");
	    
	    if(prodName!=null && prodName.length()>0){
	      if(!prodName.equals(name)){
		    continue;
		  }
	    }	  
		if(prodPrice!=null && prodPrice.length()>0){
	      if(!prodPrice.equals(price)){
		    continue;
		  }
	    }	  
		if(categoryId!=null && categoryId.length()>0){
	      if(!categoryId.equals(catId)){
		    continue;
		  }
	    }	  
		found.add(prodMap);
	  }	  	  	  
	}
	
	Iterator it = found.iterator();
	while(it.hasNext()){
  	  Map prodMap  = (Map) it.next();
	  String id    = (String) prodMap.get("id");
	  String name  = (String) prodMap.get("name");	  
	  psr.addProduct( id, name );
	}
		
	page.putHtmlComponent(psr);	  
	GlobalCache.getInstance().put(psr.getId(), psr, null);
  }
  
  
  public void repopulate(RepopulationFormMap rfmap){
  	HtmlComponent comp = page.getHtmlComponent("productForm");
  	rfmap.repopulateInput( (Input)comp.getHtmlElement("idInput") );
	rfmap.repopulateInput( (Input)comp.getHtmlElement("nameInput") );
	rfmap.repopulateInput( (Input)comp.getHtmlElement("priceInput") );	
	rfmap.repopulateSelect( (Select)comp.getHtmlElement("categorySelect") );
  }
  
  public static String ADD_UPDATE_BUTTON_LABEL 	= "add/update";
  public static String SEARCH_BUTTON_LABEL 		= "search";
  public static String DELETE_BUTTON_LABEL 		= "delete";
}
