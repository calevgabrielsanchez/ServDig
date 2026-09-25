package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.enums.TotalesCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.modelo.ResumenEdoAdeudo;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.FactorEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;

@Stateless(name = "cobranzaService", mappedName = "cobranzaService")
public class CobranzaService extends AbstractService implements CobranzaServiceRemote{

	@EJB ReportesCobranzaServiceLocal reportesCobranzaServiceLocal;
	@EJB PatronCobranzaServiceLocal patronCobranzaServiceLocal;
	@EJB FactorEntityLocal factorEntityLocal;
	
	@Override
	public ResumenEdoAdeudo getAdeudo(String nrp) throws EstadoAdeudoException{
		
		ResumenEdoAdeudo resumen = new ResumenEdoAdeudo();
		Map<String, Object> imss = null;
		Map<String, Object> rcv = null;
		double adeudoImss = 0.0;
		double adeudoRcv = 0.0;
		double totalAdeudo = 0.0;
		boolean existeAdeudo = false;
		
		log.debug("Registro patronal con el que se hara la consulta de estado de adeudo: " + nrp);
		
		//Verificamos que el nrp tenga el formato adecuado
		if(nrp == null || nrp.trim().isEmpty() || nrp.trim().length() != 11) {
			throw new EstadoAdeudoException("El nrp no tiene el formato adecuado");
		} else{
			
			String rp = nrp.substring(0, 8);
			String modalidad = nrp.substring(8,10);
			
			Patron patron = null;
			
			try {
				patron = patronCobranzaServiceLocal.getPatron(rp, modalidad);
			} catch(Exception e) {
				log.error(e);
				throw new EstadoAdeudoException("Ocurrió un error al consultar el patron");
			}
			
			if(patron != null) {
				
				try {
					imss = this.getTotalesCreditosImss(patron);
				} catch (Exception e) {
					log.error("Error al buscar los creditos IMSS" , e);
					throw new EstadoAdeudoException("Ocurrio un error al consultar los creditos IMSS");
				}
				
				try {
					rcv = this.getTotalesCreditosRCVImss(patron);
				} catch (Exception e) {
					log.error("Error al buscar los creditos RCV" , e);
					throw new EstadoAdeudoException("Ocurrio un error al consultar los creditos RCV");
				}
				
				if(imss != null) {
					adeudoImss = (Double) imss.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey());
				} 
				
				if(rcv != null) {
					adeudoImss = (Double) rcv.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey());
				} 
				
				totalAdeudo = adeudoImss + adeudoRcv;
				
				if(totalAdeudo != 0.0) {
					existeAdeudo = true;
				} 
				
			} 
			
			resumen.setExisteAdeudo(existeAdeudo);
			resumen.setAdeudoImss(adeudoImss);
			resumen.setAdeudoRcv(adeudoRcv);
			resumen.setTotalAdeudo(totalAdeudo);
			
		}
		
		return resumen;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTotalesCreditosImss(Patron patron) {
		Map<String, Object> totalesIMSS = null;
		
		totalesIMSS = reportesCobranzaServiceLocal.getCreditos(patron);
		List<Credito> creditos = (List<Credito>) totalesIMSS.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(creditos != null && !creditos.isEmpty()){
			totalesIMSS.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		} else {
			totalesIMSS = null;
		}
		
		return totalesIMSS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTotalesCreditosRCVImss(Patron patron) {
		Map<String, Object> totalesIMSSRcv = null;
		
		totalesIMSSRcv = reportesCobranzaServiceLocal.getCreditosRcv(patron);
		
		List<CreditoRCV> creditos = (List<CreditoRCV>) totalesIMSSRcv.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(creditos != null && !creditos.isEmpty()){
			log.debug("Existen creditos RCV, tamaño de la lista de creditos: " + creditos.size());
			totalesIMSSRcv.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		} else {
			log.debug("No existen creditos RCV");
			totalesIMSSRcv = null;
		}
		
		return totalesIMSSRcv;
	}
	
	private String getNumeroFormateado(Double cantidad) {
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es","MX"));
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);
		DecimalFormat decim = (DecimalFormat) nf;
		
		return decim.format(cantidad);
	}
	
	public Factor getFactorByPeriodo(String periodo) {
		return this.factorEntityLocal.getFactorByPeriodo(periodo);
	}

}
