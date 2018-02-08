package jayserv.example.shop.comp;

public class ShopUser{   

  public ShopUser(){
  	basket = new Basket();
  }
  
  public ShopUser(String username){
  	this();
  	setUsername(username);  	
  }
  
  public String getUsername(){
  	return username;
  }
  
  public void setUsername(String username){
  	this.username = username;
  }
  
  public Basket getBasket(){
  	return basket;
  }
  
  public void setBasket(Basket basket){
  	this.basket = basket;
  }
  
  private String username = "Anonymous";
  private Basket basket;
}