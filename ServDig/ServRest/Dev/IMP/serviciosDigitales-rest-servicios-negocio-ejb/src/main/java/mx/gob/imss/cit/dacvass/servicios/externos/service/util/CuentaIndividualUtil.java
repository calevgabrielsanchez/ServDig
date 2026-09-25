package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoCuentaIndividual;

public class CuentaIndividualUtil {
	
	
	public static  void ordenarCuentasIndividualesFormato(
			List<AseguradoCuentaIndividual> periodos) {
		Collections.sort(periodos,
				new Comparator<AseguradoCuentaIndividual>() {

					@Override
					public int compare(AseguradoCuentaIndividual o1,
							AseguradoCuentaIndividual o2) {
						return -1
								* (compareByDate(o1.getFechaFinalMovimiento(),
										o2.getFechaFinalMovimiento()) == 0 ? compareByDate(
										o1.getFechaInicioMovimiento(),
										o2.getFechaInicioMovimiento())
										: compareByDate(
												o1.getFechaFinalMovimiento(),
												o2.getFechaFinalMovimiento()));
					}
				});
	}
	public static void ordenarCuentasIndividuales(
			List<AseguradoCuentaIndividual> periodos) {
		Collections.sort(periodos,
				new Comparator<AseguradoCuentaIndividual>() {

					@Override
					public int compare(AseguradoCuentaIndividual o1,
							AseguradoCuentaIndividual o2) {
						return compareByDate(o1.getFechaFinalMovimiento(),
								o2.getFechaFinalMovimiento()) == 0 ? compareByDate(
								o1.getFechaInicioMovimiento(),
								o2.getFechaInicioMovimiento()) 
										: compareByDate(
												o1.getFechaFinalMovimiento(),
												o2.getFechaFinalMovimiento());
					}
				});
	}

	
	public static  int compareByDate(Date date1, Date date2) {
		int fecha = date1 == null ? 1 : 0;
		int fecha2 = date2 == null ? 1 : 0;
		return (fecha + fecha2) == 0 ? date1.compareTo(date2) : fecha - fecha2;
	}
	
	

}
