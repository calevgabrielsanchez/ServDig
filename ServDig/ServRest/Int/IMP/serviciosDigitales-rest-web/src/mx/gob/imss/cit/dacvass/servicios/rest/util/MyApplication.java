package mx.gob.imss.cit.dacvass.servicios.rest.util;

/**
@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        registerClasses(UsersPruebas.class);
        register(new JettisonFeature());
    }
}**/

public class  MyApplication {
	 /*
	   private Set<Object> singletons = new HashSet<Object>();
	   private Set<Class<?>> empty = new HashSet<Class<?>>();
	 
	   public MyApplication() {
	      singletons.add(new UsersPruebas());
	      singletons.add(IConsultaInfoPersonaServiceExternalLocal.class);
	   }
	 
	   @Override
	   public Set<Class<?>> getClasses() {
	      return empty;
	   }
	 
	   @Override
	   public Set<Object> getSingletons() {
	      return singletons;
	   }
	   */
	
	public static void main (String args) {
		System.out.println("hola mundo " + args);
	}
	
	
	
	}




