package jayserv.html;


import java.util.LinkedList;
import java.util.List;

public class SingleList extends AbstractHtmlComponent implements Iterative{


  public SingleList(String id){
    super(id);
	elements = new LinkedList();
  }
  
  
  public void addHtmlElement(HtmlElement element){
    elements.add( element );
  }
    
  public HtmlElement getHtmlElement(String id){  	
	if( id.equals(iterationKey) ) {
	  return (HtmlElement) elements.get(pointer);
	}
	else{
	  return null;
	}
  }
  
  public void iterate() {
  	pointer++;
  }
  
  
  public void reset() {
  	pointer = 0;
  }
  
  public boolean hasNext() {
  	return (pointer < elements.size());
  }
  
  
  public String iterationKey = "";
  
  private List elements;
  private int pointer;
}


