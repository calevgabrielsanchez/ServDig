package mx.gob.imss.ctirss.delta.gestion.individuo.util;

import java.util.Collection;

@Deprecated()
/**
 * Esta clase se eliminara pronto: favor de usar la clase de  mismo nombre en FW base. 
 * @author CGM
 *
 */
public class Utilerias {

    public static Boolean isNotBlank(final Number idEntity) {
        return !isBlank(idEntity);
    }

    public static Boolean isBlank(final Number idEntity) {
        Boolean isBlank = null;
        if (idEntity == null) {
            isBlank = Boolean.TRUE;
        } else {
            isBlank = idEntity.equals(0) || idEntity.equals(-1L) || idEntity.equals(-1);
        }
        return isBlank;
    }
    
    public static Long convertir(final Integer intValue) {
        Long longValue = null;
        if(intValue != null) {
            longValue = intValue.longValue();
        }
        return longValue;
    }

    public static Integer convertir(final Long longValue) {
        Integer intValue = null;
        if(longValue != null) {
            intValue = longValue.intValue();
        }
        return intValue;
    }
    
    public static Boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static Boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static void main(String[] args) {
        Number it = null;
        System.out.println("isBlank: " + isBlank(it));
        it = 0;
        System.out.println("isBlank: " + isBlank(it));
        it = -1l;
        System.out.println("isBlank: " + isBlank(it));
        it = -1;
        System.out.println("isBlank: " + isBlank(it));
        it = -2L;
        System.out.println("isBlank: " + isBlank(it));
    }

}
