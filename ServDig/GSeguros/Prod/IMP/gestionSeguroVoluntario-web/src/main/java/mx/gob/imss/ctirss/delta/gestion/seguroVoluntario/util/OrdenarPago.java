package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import java.util.Comparator;

import mx.gob.imss.digital.modelo.cobranza.Pago;

public class OrdenarPago implements Comparator<Pago>{

	@Override
	public int compare(Pago o1, Pago o2) {
		
		if(o1.getFechaLimitePago() != null && o2.getFechaLimitePago() != null){
			int diffFecLim = o1.getFechaLimitePago().compareTo(
					o2.getFechaLimitePago());
			
			if (diffFecLim != 0) {
				return diffFecLim;
			}
		}
		
		if(o1.getFechaInicioPeriodo() != null && o2.getFechaInicioPeriodo() != null){
			int diffFechaInicio = o1.getFechaInicioPeriodo().compareTo(
					o2.getFechaInicioPeriodo());
			
			if (diffFechaInicio != 0) {
				return diffFechaInicio;
			}
		}
		
		int diffIdPago = o1.getIdPago().compareTo(o2.getIdPago());
		
		return diffIdPago;

	}

}
