package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jayserv.example.shop.comp.ErrorPage;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.example.shop.comp.ShopUser;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.Link;
import jayserv.html.PlainText;
import jayserv.html.forms.DefaultFormMap;
import jayserv.html.forms.FormMap;
import jayserv.html.renderer.HtmlPageRenderer;
import jayserv.service.GlobalCache;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;
import jayserv.service.security.HttpSessionGuard;
import jayserv.service.security.Privilege;
import jayserv.service.security.PrivilegeFactory;

public class LoginHandler extends ResponseHandler implements ShopInputElements{

  
  public LoginHandler(){
    super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR)+"loginsuccess.html");	
  }
 

  public void handleSecured(ServiceContext ctx) throws ServiceException{
  	doLogin(ctx);
  }
  
  private void doLogin(ServiceContext ctx) throws ServiceException{
  	try{
  	  FormMap formmap = new DefaultFormMap();  	  
  	  formmap.map(ctx.getRequest());
  	  String username = formmap.getValue(USERNAME);
  	  String password = formmap.getValue(PASSWORD);  	  
	  Privilege privilege = getPrivilegeFrom(username, password);
  	  if(privilege!=null){
  	    HttpSession session    = ctx.getSession();
  	    HttpSessionGuard guard = ctx.getSessionGuard();				
  	    guard.assignPrivilege(session, privilege);
  	    ShopUser user = identifyShopUser(session);
  	    if(user==null){
  	      user = new ShopUser();
  	    }
  	    user.setUsername(username);
  	    session.setAttribute(ATTR_SHOPUSER, user);
  	    buildPage(user);
		writePage( ctx.getResponse() );
  	  }
  	  else{	  	
  	    ErrorPage errorpage = new ErrorPage( "Login failed", new Link(TO_LOGINFORM, "Back") );
		HtmlPageRenderer renderer = new HtmlPageRenderer(errorpage);
  	    ctx.getResponse().getOutputStream().println( renderer.render().toString() );
  	  }
  	}
  	catch(java.io.IOException ioe){
	  throw new ServiceException( "IOException: "+ioe.getMessage() );
	}		
  }
  
  private Privilege getPrivilegeFrom(String username, String password){
  	Privilege priv =  null;
  	Iterator it = ShopDB.getInstance().getMappedEntitiesWhere("users", "name", username).iterator();
  	while(it.hasNext()){
  	  Map user  = (Map) it.next();
  	  String pw = (String) user.get("password");
  	  if(pw.equals(password)){	  	
	  	String privID = (String)user.get("privilegeID");
		Map privMap   = ShopDB.getInstance().getMappedEntity("privileges", privID);
  	    String privClassname = (String) privMap.get("class");
	    priv = PrivilegeFactory.newPrivilegeInstance(privClassname);
	    break;
  	  }
  	}
  	return priv;
  }      
  
  
  private void buildPage(ShopUser user){  	
  	String username = "";
  	if(user==null){
  	  username = "Unknown";
	}
	else{
	  username = user.getUsername();
	}
	
	PlainText name = new PlainText(username);
	name.setId("username");
	
	DefaultHtmlComponent comp = new DefaultHtmlComponent( "userinfoCmp" );
	comp.addHtmlElement( name );
	page.putHtmlComponent(comp);
  } 
 
}

