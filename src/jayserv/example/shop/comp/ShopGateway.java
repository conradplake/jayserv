package jayserv.example.shop.comp;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import jayserv.service.GlobalCache;
import jayserv.service.ServiceGateway;

public class ShopGateway extends ServiceGateway{

  public void init() throws ServletException{
	super.init();    
    try {     
      String configfile = getInitParameter(CONFIG_PARAMETER);
	  String basedir_templates  = getInitParameter(TEMPLATES_BASEDIR);
	  String dbschema_url = getInitParameter(DBSCHEMA_URL);
	  
	  GlobalCache.getInstance().put(TEMPLATES_BASEDIR, basedir_templates, null);
	  GlobalCache.getInstance().put(DBSCHEMA_URL, dbschema_url, null);
	  
      File file = new File(configfile);
  	  SAXBuilder builder = new SAXBuilder();
      Document serviceDocument = builder.build(file.toURL());          
	  
      loadServices(serviceDocument);          
	  
	  System.out.println("** ShopGateway initialized");
    }
   	catch (JDOMException e){
      System.out.println("** ShopGateway: could not load xmlFile - init failed!");
      System.out.println("** Exception was: "+e.getMessage() );            
    }
    catch (IOException ioe){
      System.out.println("** ShopGateway: could not load xmlFile - init failed!");
      System.out.println("** Exception was: "+ioe.getMessage() );
    }
  }     
  
  public static final String CONFIG_PARAMETER   = "configfile";
  public static final String TEMPLATES_BASEDIR  = "basedir_templates";
  public static final String DBSCHEMA_URL       = "dbschema_url";
  
}