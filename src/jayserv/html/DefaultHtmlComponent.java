package jayserv.html;

import java.util.Hashtable;

public class DefaultHtmlComponent extends AbstractHtmlComponent{

  public DefaultHtmlComponent(String id){
	super(id);
    elements = new Hashtable();
  }  

  public void addHtmlElement(HtmlElement element){
    elements.put( element.getId(), element );
  }
  
  
  public HtmlElement getHtmlElement(String id){
    return (HtmlElement) elements.get(id);
  }
  
    
  private Hashtable elements;

}