package jayserv.example.shop.comp.beans;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeanClient extends HttpServlet{ 

  public void init() throws ServletException{    	   	
  }

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException{   	
  
    Hashtable env = new Hashtable();
	
	env.put(Context.PROVIDER_URL, "localhost:1099");
	env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
    
	try{
	  
	  Context ctx = new InitialContext(env);
	  Object obj  = ctx.lookup("Test");
	  TestHome home = (TestHome) javax.rmi.PortableRemoteObject.narrow(obj, TestHome.class);
	  Test test = home.create();
	  
	  test.test();	  	
	}
	catch(Exception e)	{
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
  }

}