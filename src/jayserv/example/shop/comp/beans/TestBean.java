package jayserv.example.shop.comp.beans;

public class TestBean implements SessionBean{   


  public void setSessionContext(SessionContext ctx){
    this.ctx = ctx;
  }
  
  public SessionContext getSessionContext(){
    return ctx;  
  }
  
  public void ejbCreate(){    
  }
  
  
  public void ejbActivate(){
  }
  
  public void ejbRemove(){  
  }
  
  public void ejbPassivate(){
  }

  public void test() {
    System.out.println("test() invoked!!");
  
  }

  private SessionContext ctx;


}