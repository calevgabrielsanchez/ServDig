package mx.gob.imss.cit.cda.service.utility;

import javax.ejb.Local;

@Local
public interface FiltrosUtilityLocal <T>{
	
	public boolean shouldRemove(T t);
	
	public boolean shouldRemoveLast(T t);

}
