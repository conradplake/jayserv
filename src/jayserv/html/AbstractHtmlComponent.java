
package jayserv.html;

public abstract class AbstractHtmlComponent implements HtmlComponent{

  public AbstractHtmlComponent(String id){
	this.id  = id;   
  }

  public void setId(String id){
    this.id = id;
  }
  public String getId(){
    return id;
  }  
    
  private String id;  

}