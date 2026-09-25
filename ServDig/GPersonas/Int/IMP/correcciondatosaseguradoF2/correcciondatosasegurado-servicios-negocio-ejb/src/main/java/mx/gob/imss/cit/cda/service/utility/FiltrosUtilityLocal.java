package mx.gob.imss.cit.cda.service.utility;

import javax.ejb.Local;

@Local
public interface FiltrosUtilityLocal<T> {

    boolean shouldRemove(T t);

    boolean shouldRemoveLast(T t);

}
