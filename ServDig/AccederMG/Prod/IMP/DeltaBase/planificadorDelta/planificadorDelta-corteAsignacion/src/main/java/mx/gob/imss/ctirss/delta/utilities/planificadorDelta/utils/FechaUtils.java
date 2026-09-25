package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class FechaUtils {
	private static final Locale LOCALE_MX;

	static {
		LOCALE_MX = new Locale("es", "mx");
	}

	private FechaUtils() {
	}

	public static Date getFechaSistema() {
		return new Date();
	}

	public static Date getFechaCeroHoras(Date fecha) {
		if (fecha == null) {
			return null;
		}

		Calendar cl = Calendar.getInstance();

		cl.setTimeInMillis(fecha.getTime());
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);

		return cl.getTime();
	}

	public static Date restaFechas(Date fecIni, Date fecFin) {
		if (fecIni == null || fecFin == null) {
			return null;
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		if (fecIni.after(fecFin)) {
			c1.setTime(fecIni);
			c2.setTime(fecFin);
		} else {
			c1.setTime(fecFin);
			c2.setTime(fecIni);
		}

		return new Date(c1.getTimeInMillis() - c2.getTimeInMillis());
	}

	public static Date sumaFechas(Date fecIni, Date fecFin) {
		if (fecIni == null || fecFin == null) {
			return null;
		}

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(fecFin);
		c2.setTime(fecIni);

		return new Date(c1.getTimeInMillis() + c2.getTimeInMillis());
	}

	public static Date sumaHoras(Date pOFecha, double piHoras) {
		Double myHoras = piHoras * (3600000L);
		Date fechaDias = new Date((pOFecha.getTime() + myHoras.longValue()));

		return fechaDias;
	}

	public static Date sumaMinutos(Date pOFecha, double piMinutos) {
		Double myMinutos = piMinutos * (60000L);
		Date fechaDias = new Date((pOFecha.getTime() + myMinutos.longValue()));

		return fechaDias;
	}

	public static String dateToStringConFormato(final Date fecha,
			final String formatoFecha) {
		String dateAsString = null;

		if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
			dateAsString = new SimpleDateFormat(formatoFecha, LOCALE_MX)
					.format(fecha);
		}

		return dateAsString;
	}

	public static boolean esFechaMenorOIgual(Date fechaInicio, Date fechaFin) {
		return (fechaInicio.getTime() <= fechaFin.getTime());
	}

	public static boolean esFechaMenor(Date fechaInicio, Date fechaFin) {
		return (fechaInicio.getTime() < fechaFin.getTime());
	}

	public static boolean esFechaMayorOIgual(Date fechaInicio, Date fechaFin) {
		return (fechaInicio.getTime() >= fechaFin.getTime());
	}

	public static boolean esFechaMayor(Date fechaInicio, Date fechaFin) {
		return (fechaInicio.getTime() > fechaFin.getTime());
	}
}
