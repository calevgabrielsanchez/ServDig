/**
 * 
 */
package mx.gob.imss.mail.test;

import java.util.Calendar;

/**
 * @author daniel.hernandez
 *
 */
public class Bimestral {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar date1 = Calendar.getInstance(); 
		Calendar date2 = Calendar.getInstance(); 
		date2.set(2011, 9, 11); 
		Calendar aux = Calendar.getInstance(); 
		aux.setTimeInMillis(date2.getTimeInMillis() - date1.getTimeInMillis()); 
		date1.get(Calendar.DAY_OF_MONTH)); 
		date2.get(Calendar.DAY_OF_MONTH)); 
		aux.get(Calendar.DAY_OF_MONTH)); 


	}

}
