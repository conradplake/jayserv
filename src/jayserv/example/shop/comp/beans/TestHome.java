package jayserv.example.shop.comp.beans;

public interface TestHome extends EJBHome{   

  public Test create() throws java.rmi.RemoteException, javax.ejb.CreateException;

}