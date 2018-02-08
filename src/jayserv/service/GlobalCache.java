package jayserv.service;


public class GlobalCache extends Cache{

  private GlobalCache(){
    super();
  }

  public static GlobalCache getInstance(){
  	if(instance == null){
	  synchronized(GlobalCache.class){
	    if(instance == null){
		  instance = new GlobalCache();
		}
	  }
	}
  	return instance;
  }

  private static GlobalCache instance;

}