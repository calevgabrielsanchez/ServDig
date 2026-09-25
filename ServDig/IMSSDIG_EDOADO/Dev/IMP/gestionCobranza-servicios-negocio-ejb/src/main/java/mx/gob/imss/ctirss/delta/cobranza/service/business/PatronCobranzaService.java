package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.enums.TipoPatronCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.PatronEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.PatronCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;

@Stateless(name = "patronCobranzaService", mappedName = "patronCobranzaService")
public class PatronCobranzaService extends AbstractServiceBusiness
implements PatronCobranzaServiceRemote , PatronCobranzaServiceLocal{

	@EJB PatronEntityLocal patronEntityLocal;
	
	@Override
	public Patron getPatron(String regPat, String modalidad) {
		Patron patron = patronEntityLocal.getPatron(regPat, modalidad);
		
		if(patron != null) {
			try {
				Map<String, String> patrones = patronEntityLocal.findAsociados(regPat, modalidad);
			
				if(patrones != null && patrones.size() > 0) {
					patron.setCveTipoPatron(TipoPatronCobranzaEnum.CORPORATIVO.getId());
				} 
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return patron;
	}

	public boolean tieneAdeudos(String regPatron){
		if(regPatron != null){
			if (regPatron.length() > 8){
				regPatron = regPatron.substring(0, 8);
			}
			int totalAdeudos = patronEntityLocal
				.obtenerTotalAdeudosPorRegistroPatronal(regPatron);		
			return (totalAdeudos>0 ? true : false);
		}
		return false;
	}
	
}
