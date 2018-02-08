package jayserv.example.shop.handler;


import org.jdom.Element;

import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.html.forms.RepopulationFormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;


public class UserAdministration extends DefaultShopHandler implements ShopInputElements{

  public UserAdministration(){   
  }

  public void handleSecured(ServiceContext ctx) throws ServiceException{  		  	  
        
	RepopulationFormMap formMap = new RepopulationFormMap();
	formMap.map( ctx.getRequest() );
	  
	String userId       = formMap.getValue( USER_ID );	  
	String userName     = formMap.getValue( USER_NAME );	  
	String userPassword = formMap.getValue( USER_PASSWORD );	
	String privId       = formMap.getValue( USER_PRIVILEGE );
	String option       = formMap.getValue( OPTION );
		
	GetUserAdminPage respPage = new GetUserAdminPage();
	
	if(option.equals(GetUserAdminPage.ADD_UPDATE_BUTTON_LABEL)){
	  if(userId!=null && userId.length()>0){
	    if( !updateUser(userId, userName, userPassword, privId) ){
		  respPage.setInfoText("Update Failed!");
		}
		respPage.fillForm(userId);		
	  }
	  else{
	    String id = addUser(userName, userPassword, privId);
		respPage.fillForm(id);
	  }
	}
	else if(option.equals(GetUserAdminPage.SEARCH_BUTTON_LABEL)){
	  respPage.fillSearchResultList( userId, userName, userPassword, privId );
	  respPage.repopulate(formMap);
	}
	else if(option.equals(GetUserAdminPage.DELETE_BUTTON_LABEL)){	  
	  if( !deleteUser(userId) ){ 
	    respPage.setInfoText("Delete Failed!");
		respPage.repopulate(formMap);
	  }
	}
	
	ctx.addHandler( respPage );	
  }
  
  
  public boolean deleteUser(String userId){
  	boolean success = false;
	if(userId!=null && userId.length()>0){
	  ShopDB ins = ShopDB.getInstance();	
      success = ins.removeEntity("users", userId);
	  ins.save();
	} 
	return success; 	
  }
  
  
  public boolean updateUser(String userId, String userName, String userPassword, String privId){
  	boolean success = false;
    Element userElem = ShopDB.getInstance().getEntity("users", userId);
	if(userElem!=null){
	  Element name   = userElem.getChild("name");
	  Element passwd = userElem.getChild("password");
	  Element priv   = userElem.getChild("privilegeID");
	  name.setText( userName );
	  passwd.setText( userPassword );	 	  	  
	  priv.setText( privId );
	  ShopDB.getInstance().save();
	  success = true;
	}
	return success;
  }
  
  
  public String addUser(String userName, String userPassword, String privId){  
  	ShopDB ins = ShopDB.getInstance();
	    	
    String id = ins.getNextId("users");
	Element idAttr = new Element("id");
	idAttr.setText(id);
	
	Element nameAttr = new Element("name");
	if(userName!=null){
 	  nameAttr.setText(userName);
	}
	
	Element passwdAttr = new Element("password");
	if(userPassword!=null){
 	  passwdAttr.setText(userPassword);
	}
	
	Element privAttr = new Element("privilegeID");
	if(privId!=null){
 	  privAttr.setText(privId);
	}
		
	Element userEntity = new Element("user");
	userEntity.addContent(idAttr);
	userEntity.addContent(nameAttr);
	userEntity.addContent(passwdAttr);	
	userEntity.addContent(privAttr);
	
	ins.addEntity( "users", userEntity );
	ins.save();
	
	return id;
  }        
}