package mx.gob.imss.ctirss.delta.gestion.patronal.test;


import java.util.GregorianCalendar;


public class FechaPorCiclo {

public static void main(String[] argv) {
GregorianCalendar gc = new GregorianCalendar();
gc.set(GregorianCalendar.DAY_OF_MONTH, 16);
gc.set(GregorianCalendar.MONTH, GregorianCalendar.APRIL);
gc.set(GregorianCalendar.YEAR, 2021);
System.out.println(gc.get(GregorianCalendar.DAY_OF_YEAR));
}


}
