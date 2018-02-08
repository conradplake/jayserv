package jayserv.example.shop.handler;

import javax.servlet.http.HttpServletResponse;

import jayserv.html.TemplatedHtmlPage;
import jayserv.html.renderer.HtmlPageRenderer;

public class ResponseHandler extends DefaultShopHandler{

  public ResponseHandler(){
    page = new TemplatedHtmlPage();
  }
  
  public ResponseHandler(String templateURL){
    page = new TemplatedHtmlPage(templateURL);
  }
  
  
  protected void writePage(HttpServletResponse resp) throws java.io.IOException{
    HtmlPageRenderer renderer = new HtmlPageRenderer(page);
  	resp.getOutputStream().println( renderer.render().toString() );
  }
  
  protected TemplatedHtmlPage page;

}