package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class ReporteFormatterTest {

	final Calendar fechaCal = new GregorianCalendar();

	@Before
	public void fixture() {
		fechaCal.set(Calendar.DATE, 23);
		fechaCal.set(Calendar.MONTH, Calendar.FEBRUARY);
		fechaCal.set(Calendar.YEAR, 2012);
	}

	@Test
	public void testMes() {
		final String mesAsString = new ReporteFormatterBean().getMes(fechaCal
				.getTime());
		assertEquals("El mes no es el esperado", "FEBRERO", mesAsString);
	}

	@Test
	public void testAnio() {
		final int anio = new ReporteFormatterBean().getAnio(fechaCal
				.getTime());
		assertEquals("El anio no es el esperado", 2012, anio);
	}

}
