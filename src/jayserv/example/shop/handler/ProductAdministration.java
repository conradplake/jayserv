package jayserv.example.shop.handler;


import org.jdom.Element;

import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.forms.RepopulationFormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;


public class ProductAdministration extends DefaultShopHandler implements ShopInputElements{

  public ProductAdministration(){   
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{  		  	  
        
	RepopulationFormMap formMap = new RepopulationFormMap();
	formMap.map( ctx.getRequest() );
	  
	String prodId      = formMap.getValue( PRODUCT_ID );	  
	String prodName    = formMap.getValue( PRODUCT_NAME );	  
	String prodPrice   = formMap.getValue( PRODUCT_PRICE );
	String categoryId  = formMap.getValue( CATEGORY );
	String option      = formMap.getValue( OPTION );
				
	GetProductAdminPage respPage = new GetProductAdminPage();			
		
	if(option.equals(GetProductAdminPage.ADD_UPDATE_BUTTON_LABEL)){
	  if(prodId!=null){
	    if( !updateProduct(prodId, prodName, prodPrice, categoryId) ){
		  respPage.setInfoText("Update Failed!");
		  respPage.repopulate(formMap);
		}
		respPage.fillForm(prodId);		
	  }
	  else{
	    if(prodName!=null && prodPrice!=null && categoryId!=null){
	      String id = addProduct(prodName, prodPrice, categoryId);
		  respPage.fillForm(id);
	    }else{
		  respPage.setInfoText("Please fill out all form elements.");
		  respPage.repopulate(formMap);
	    }
	  }
	}
	
	else if(option.equals(GetProductAdminPage.SEARCH_BUTTON_LABEL)){
	  respPage.fillSearchResultList( prodId, prodName, prodPrice, categoryId );
	  respPage.repopulate(formMap);
	}
	
	else if(option.equals(GetProductAdminPage.DELETE_BUTTON_LABEL)){	  
	  if( !deleteProduct(prodId) ){ 
	    respPage.setInfoText("Delete Failed!");
		respPage.repopulate(formMap);
	  }
	}
	
	ctx.addHandler( respPage );	
  }
  
  
  
  public String addCategory(String newCategory){
  	ShopDB ins = ShopDB.getInstance();
	    	
    String id = ins.getNextId("categories");
	Element idAttr = new Element("id");
	idAttr.setText(id);
	
	Element nameAttr = new Element("name");
	nameAttr.setText(newCategory);			
	
	Element catEntity = new Element("category");
	catEntity.addContent(idAttr);
	catEntity.addContent(nameAttr);
	
	ins.addEntity( "categories", catEntity );
	ins.save();
	
	return id;
  }
  
  
  public boolean deleteProduct(String prodId){
  	boolean success = false;
	if(prodId!=null){
	  ShopDB ins = ShopDB.getInstance();	
      success = ins.removeEntity("products", prodId);
	  ins.save();
	} 
	return success; 	
  }
  
  
  public boolean updateProduct(String prodId, String prodName, String prodPrice, String categoryId){
  	boolean success = false;
    Element prodElem = ShopDB.getInstance().getEntity("products", prodId);
	if(prodElem!=null && prodName!=null && prodPrice!=null && categoryId!=null){
	  Element name  = prodElem.getChild("name");
	  Element price = prodElem.getChild("price");
	  Element catId = prodElem.getChild("categoryID");
	  
	  name.setText( prodName );
	  price.setText( prodPrice );
	  catId.setText( categoryId );
	  
	  ShopDB.getInstance().save();
	  success = true;
	}
	return success;
  }
  
  
  public String addProduct(String prodName, String prodPrice, String categoryId){  
  	ShopDB ins = ShopDB.getInstance();
	    	
    String id = ins.getNextId("products");
	Element idAttr = new Element("id");
	idAttr.setText(id);
	
	Element nameAttr = new Element("name");
	if(prodName!=null){
 	  nameAttr.setText(prodName);
	}
	
	Element priceAttr = new Element("price");
	if(priceAttr!=null){
 	  priceAttr.setText(prodPrice);
	}
	
	Element catIdAttr = new Element("categoryID");
	if(categoryId!=null){
 	  catIdAttr.setText(categoryId);
	}
	
	Element productEntity = new Element("product");
	productEntity.addContent(idAttr);
	productEntity.addContent(nameAttr);
	productEntity.addContent(priceAttr);
	productEntity.addContent(catIdAttr);
	
	ins.addEntity( "products", productEntity );
	ins.save();
	
	return id;
  }        
}