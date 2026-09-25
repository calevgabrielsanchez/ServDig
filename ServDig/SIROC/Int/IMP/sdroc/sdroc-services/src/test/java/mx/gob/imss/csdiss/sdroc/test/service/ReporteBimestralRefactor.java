package mx.gob.imss.csdiss.sdroc.test.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReporteBimestralRefactor {

	public static void main(String[] args) {

		// obra viva
		GregorianCalendar fechaInicio = new GregorianCalendar(2017, 01, 15);
		GregorianCalendar fechaTermino = new GregorianCalendar(2017, 03, 10);

		calculoReporteBimestralPresentar(fechaInicio, fechaTermino);
		// el inicio de la obra es de una fecha pasada
		// la obra ya cuenta con reanudaciones ?
		// se debe validar que la obra no cuente con reportes bimestrales
		// reportados
		// la fecha de inicio de obra que se debe tomar es la ultima fecha de
		// reanudacion

		// la obra ya cuenta con reportes bimestrales presentados ?
		// si la obra ya cuenta con reportes bimestrales se valida que tenga el
		// ultimo

		// el inicio de la obra es de una fecha a futuro
		// la obra ya tiene incidencias reportadas ?
		// de las incidencias que tiene reportadas se tiene reprotes bimestrales
		// reportados ?
		// si tiene reportes bimestrales reportados entonces se toma la ultima
		// fecha de presentacion de ese reporte bimestral

		// abra que calcular los periodos que debe

	}

	private static void calculoReporteBimestralPresentar(GregorianCalendar fechaInicio,
			GregorianCalendar fechaTermino) {
		GregorianCalendar fechaSistema = new GregorianCalendar();

		if (fechaInicio.after(fechaSistema)) {
		} else {
			if (fechaTermino.after(fechaSistema)) {
				fechaTermino = new GregorianCalendar();
			} 
		}
	}

}
