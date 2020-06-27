package jayserv.example.shop.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jayserv.example.shop.comp.GlobalCache;
import jayserv.example.shop.comp.ShopDB;
import jayserv.example.shop.comp.ShopGateway;
import jayserv.example.shop.comp.ShopInputElements;
import jayserv.example.shop.comp.UserSearchResult;
import jayserv.html.DefaultHtmlComponent;
import jayserv.html.HtmlComponent;
import jayserv.html.Input;
import jayserv.html.PlainText;
import jayserv.html.Select;
import jayserv.html.forms.RepopulationFormMap;
import jayserv.service.ServiceContext;
import jayserv.service.ServiceException;

public class GetUserAdminPage extends ResponseHandler implements ShopInputElements {

	public GetUserAdminPage() {
		super(GlobalCache.getInstance().get(ShopGateway.TEMPLATES_BASEDIR) + "useradmin.html");
		initPage();
	}

	private void initPage() {
		Input userName = new Input(USER_NAME, Input.TYPE_TEXT, "");
		userName.setId("nameInput");
		userName.setSize(15);

		Input userId = new Input(USER_ID, Input.TYPE_TEXT, "");
		userId.setId("idInput");
		userId.setSize(3);

		Input userPasswd = new Input(USER_PASSWORD, Input.TYPE_TEXT, "");
		userPasswd.setId("passwordInput");
		userPasswd.setSize(15);

		Select privSelect = new Select();
		privSelect.setName(USER_PRIVILEGE);
		privSelect.setId("privSelect");

		Iterator it = ShopDB.getInstance().getMappedEntities("privileges").iterator();
		while (it.hasNext()) {
			Map privMap = (Map) it.next();
			String option = (String) privMap.get("name");
			String value = (String) privMap.get("id");
			privSelect.addOption(option, value);
		}

		DefaultHtmlComponent userForm = new DefaultHtmlComponent("userForm");
		userForm.addHtmlElement(userId);
		userForm.addHtmlElement(userName);
		userForm.addHtmlElement(userPasswd);
		userForm.addHtmlElement(privSelect);

		Input addUpateSubmit = new Input(OPTION, Input.TYPE_SUBMIT, ADD_UPDATE_BUTTON_LABEL);
		addUpateSubmit.setId("add/updateButton");
		Input searchSubmit = new Input(OPTION, Input.TYPE_SUBMIT, SEARCH_BUTTON_LABEL);
		searchSubmit.setId("searchButton");
		Input deleteSubmit = new Input(OPTION, Input.TYPE_SUBMIT, DELETE_BUTTON_LABEL);
		deleteSubmit.setId("deleteButton");

		userForm.addHtmlElement(deleteSubmit);
		userForm.addHtmlElement(addUpateSubmit);
		userForm.addHtmlElement(searchSubmit);

		UserSearchResult usr = (UserSearchResult) GlobalCache.getInstance().get("userSearchResult");
		if (usr != null) {
			page.putHtmlComponent(usr);
		}
		page.putHtmlComponent(userForm);
	}

	public void handleSecured(ServiceContext ctx) throws ServiceException {
		try {
			String userId = ctx.getRequest().getParameter(UserSearchResult.FILL_BY_ID);
			if (userId != null && userId.length() > 0) {
				fillForm(userId);
			}
			writePage(ctx.getResponse());
		} catch (java.io.IOException ioe) {
			throw new ServiceException("IOException: " + ioe.getMessage());
		}
	}

	public void setInfoText(String text) {
		PlainText ptext = new PlainText(text);
		ptext.setId("infoText");

		HtmlComponent comp = page.getHtmlComponent("userForm");
		comp.addHtmlElement(ptext);
	}

	public void fillForm(String userId) {
		Map userMap = ShopDB.getInstance().getMappedEntity("users", userId);

		if (userMap == null) {
			return;
		}

		HtmlComponent form = page.getHtmlComponent("userForm");

		Input idInput = (Input) form.getHtmlElement("idInput");
		idInput.setValue((String) userMap.get("id"));

		Input nameInput = (Input) form.getHtmlElement("nameInput");
		nameInput.setValue((String) userMap.get("name"));

		Input passwdInput = (Input) form.getHtmlElement("passwordInput");
		passwdInput.setValue((String) userMap.get("password"));

		Select privSelect = (Select) form.getHtmlElement("privSelect");
		privSelect.selectByValue((String) userMap.get("privilegeID"));
	}

	public void fillSearchResultList(String userId, String userName, String userPassword, String userPrivId) {
		UserSearchResult usr = new UserSearchResult("userSearchResult");
		List found = new LinkedList();

		if (userId != null && userId.length() > 0) {
			Map userMap = ShopDB.getInstance().getMappedEntity("users", userId);
			if (userMap != null) {
				found.add(userMap);
			}
		} else {
			Iterator<Map<String, String>> it = ShopDB.getInstance().getMappedEntities("users").iterator();
			while (it.hasNext()) {
				Map<String, String> userMap = it.next();
				String name = userMap.get("name");
				String password = userMap.get("password");
				String privId = userMap.get("privilegeID");

				if (userName != null && userName.length() > 0) {
					if (!userName.equals(name)) {
						continue;
					}
				}
				if (userPassword != null && userPassword.length() > 0) {
					if (!userPassword.equals(password)) {
						continue;
					}
				}
				if (userPrivId != null && userPrivId.length() > 0) {
					if (!userPrivId.equals(privId)) {
						continue;
					}
				}
				found.add(userMap);
			}
		}

		Iterator it = found.iterator();
		while (it.hasNext()) {
			Map userMap = (Map) it.next();
			String id = (String) userMap.get("id");
			String name = (String) userMap.get("name");
			usr.addUser(id, name);
		}

		page.putHtmlComponent(usr);
		GlobalCache.getInstance().put(usr.getId(), usr, null);
	}

	public void repopulate(RepopulationFormMap rfmap) {
		HtmlComponent comp = page.getHtmlComponent("userForm");
		rfmap.repopulateInput((Input) comp.getHtmlElement("idInput"));
		rfmap.repopulateInput((Input) comp.getHtmlElement("nameInput"));
		rfmap.repopulateInput((Input) comp.getHtmlElement("passwordInput"));
		rfmap.repopulateSelect((Select) comp.getHtmlElement("privSelect"));
	}

	public static String ADD_UPDATE_BUTTON_LABEL = "add/update";
	public static String SEARCH_BUTTON_LABEL = "search";
	public static String DELETE_BUTTON_LABEL = "delete";

}
