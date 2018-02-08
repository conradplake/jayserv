package jayserv.example.shop.comp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Basket{

  public Basket(){    	
    products = new HashMap();
  }

  public void put(String productId, int amount){  	
  	int oldamount = getAmount(productId);
  	int newamount = oldamount + amount;
  	if(newamount<0){
  	  newamount = 0;
  	}
  	products.put(productId, new Integer(newamount));
  }
  
  public void remove(String productId){
  	products.remove(productId);
  }
  
  public void removeAll(){
  	products.clear();
  }
  
  public Set getProducts(){
  	return products.keySet();
  }
  
  
  public int products(){
  	int amount = 0;
  	Iterator it = products.keySet().iterator();
	while(it.hasNext()){
	  Integer amo = (Integer) products.get( it.next() );
	  amount += amo.intValue();
	}
  	return amount;
  }
  
  public int getAmount(String productId){
  	int a = 0;
  	Integer amount = (Integer) products.get(productId);
  	if(amount!=null){
  	  a = amount.intValue();
  	}
  	return a;
  }

  private HashMap products;

}