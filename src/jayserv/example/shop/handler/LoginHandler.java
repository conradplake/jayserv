package jayserv.example.shop.handler;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jayserv.example.shop.comp.ErrorPage;
import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.Logger;
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
import jayserv.service.Privilege;
import jayserv.service.PrivilegeFactory;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;
import jayserv.service.SessionGuard;

public class LoginHandler extends ResponseHandler implements ShopInputElements {

	public LoginHandler() {
		super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR) + "loginsuccess.html");
	}

	@Override
	public final void handle(ServiceContext ctx, SessionGuard sessionGuard) throws ServiceException {
		try {
			FormMap formmap = new DefaultFormMap();
			formmap.map(ctx.getRequest());
			String username = formmap.getValue(USERNAME);
			String password = formmap.getValue(PASSWORD);
			Privilege privilege = getPrivilegeFrom(username, password);
			if (privilege != null) {
				HttpSession session = ctx.getSession();
				sessionGuard.assignPrivilege(session, privilege);
				ShopUser user = identifyShopUser(session);
				if (user == null) {
					user = new ShopUser();
				}
				user.setUsername(username);
				session.setAttribute(ATTR_SHOPUSER, user);
				buildPage(user);
				writePage(ctx.getResponse());
			} else {
				ErrorPage errorpage = new ErrorPage("Login failed", new Link(TO_LOGINFORM, "Back"));
				HtmlPageRenderer renderer = new HtmlPageRenderer(errorpage);
				ctx.getResponse().getOutputStream().println(renderer.render().toString());
			}
		} catch (java.io.IOException ioe) {
			throw new ServiceException("IOException: " + ioe.getMessage());
		}
	}

	/**
	 * Authentication for shop demo. Simply looks up user name and password from
	 * demo shop xml.
	 */
	private Privilege getPrivilegeFrom(String username, String password) {
		Privilege priv = null;
		String passwordHash = sha256(password);
		Iterator<Map<String, String>> it = ShopDB.getInstance().getMappedEntitiesWhere("users", "name", username)
				.iterator();
		while (it.hasNext()) {
			Map<String, String> user = it.next();
			String pw = user.get("password");
			if (pw.equals(passwordHash)) {
				String privID = user.get("privilegeID");
				Map<String, String> privMap = ShopDB.getInstance().getMappedEntity("privileges", privID);
				String privClassname = privMap.get("class");
				priv = PrivilegeFactory.newPrivilegeInstance(privClassname);
				break;
			}
		}
		return priv;
	}

	protected static String sha256(String base) {
		
		if(base==null) {
			return "";
		}
		
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuilder hexString = new StringBuilder();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception e){
	       Logger.log(e.getMessage(), e);
	       return "";
	    }
	}
	
	private void buildPage(ShopUser user) {
		String username = "";
		if (user == null) {
			username = "Unknown";
		} else {
			username = user.getUsername();
		}

		PlainText name = new PlainText(username);
		name.setId("username");

		DefaultHtmlComponent comp = new DefaultHtmlComponent("userinfoCmp");
		comp.addHtmlElement(name);
		page.putHtmlComponent(comp);
	}

}
