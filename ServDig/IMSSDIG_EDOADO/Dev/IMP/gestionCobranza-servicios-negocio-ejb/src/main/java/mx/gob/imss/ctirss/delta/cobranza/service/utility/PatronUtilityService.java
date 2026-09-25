package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopPatrone;

@Stateless(name = "patronEdoCtaUtilityService", mappedName = "patronEdoCtaUtilityService")
public class PatronUtilityService implements PatronUtilityServiceLocal {

	@Override
	public Patron convertEntityToModel(DCopPatrone dPatron) {
		Patron patron = null;
		
		if(dPatron != null) {
			patron = new Patron();
			patron.setCp(dPatron.getCp());
			
			patron.setCveDelegacion(dPatron.getDCopSubdelegacion().getId().getCveDelegacion().toString());
			if(dPatron.getCveDelegacionAnt() != null) {
				patron.setCveDelegacionAnt(dPatron.getCveDelegacionAnt().toString());
			}
			patron.setCveModalidad(dPatron.getCveModalidad());
			if(dPatron.getDCopMovimientosPatronale() != null) {
				patron.setCveMovtoPatronal(dPatron.getDCopMovimientosPatronale().getCveMovPatronal());
				patron.setDescMovPatronal(dPatron.getDCopMovimientosPatronale().getDescMovPatronal());
			}
			
			if(dPatron.getDCopMovimientosPatronale() != null) {
				patron.setCveActEco(dPatron.getDCopActividadesEconomica().getClabeActEco().toString());
				patron.setDescActEconomica(dPatron.getDCopActividadesEconomica().getDescActEco());
			}
			patron.setCvePatron(dPatron.getCvePatron());
			patron.setRegPatronal(dPatron.getCvePatron());
			patron.setCveSubdelegacion(dPatron.getDCopSubdelegacion().getId().getCveSubdelegacion().toString());
			patron.setCveTipoEmpresa(dPatron.getCveTipoEmpresa().toString());
			patron.setDomicilio(dPatron.getDomicilio());
			patron.setFecMovto(dPatron.getFecMovto().toString().substring(0,10));
			patron.setLocalidad(dPatron.getLocalidad());
			patron.setNumTrabaja(dPatron.getNumTrabaja().toString());
			System.out.println("primar patron: " + dPatron.getPrimaRt());
			patron.setPrimaRT(dPatron.getPrimaRt().toString());
			patron.setRazonSocial(dPatron.getRazonSocial());
			patron.setRfc(dPatron.getRfc());
			patron.setTipoAportacion(dPatron.getTipoAportacion().toString());
			/* Campos para contener la descripcion de los catalogos dentro del bean patron*/
			patron.setDescDelegacion(dPatron.getDCopSubdelegacion().getdCopDelegacion().getDescDelegacion());
			patron.setDescSubdelegacion(dPatron.getDCopSubdelegacion().getDescSubdelegacion());
			
			
		}
		
		return patron;
	}

}
