/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.test.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author daniel.hernandez
 *
 */
public class CalculoMeses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date ini = new Date(2014-1900,8,01);
		Date fin = new Date(2015-1900, 0, 01);
	}
	
	
	public static Integer differenceInMonths(Date beginningDate, Date endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(beginningDate);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(endingDate);
        return differenceInMonths(cal1, cal2);
    }

    private static Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }
        int m1 = beginningDate.get(Calendar.YEAR) * 12 + beginningDate.get(Calendar.MONTH);
        int m2 = endingDate.get(Calendar.YEAR) * 12 + endingDate.get(Calendar.MONTH);
        return m2 - m1;
    }

}
