package mx.imss.ctirss.framework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConcurrentDateFormatAccess {

	private ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

		public DateFormat get() {
			return super.get();
		}

		protected DateFormat initialValue() {
			return new SimpleDateFormat("dd/MM/yyyy");
		}

		public void remove() {
			super.remove();
		}

		public void set(DateFormat dateFormat) {
			super.set(dateFormat);
		}
	};

	public Date convertStringToDate(String dateString) throws ParseException {
		return df.get().parse(dateString);
	}
	
	public String convertDateToString(Date fecha) {
		return df.get().format(fecha);
	}

}
