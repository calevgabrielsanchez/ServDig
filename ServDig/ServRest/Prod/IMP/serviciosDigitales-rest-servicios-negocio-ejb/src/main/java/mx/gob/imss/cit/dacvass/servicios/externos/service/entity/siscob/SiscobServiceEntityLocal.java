package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siscob;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HistPatronesConvenioImssrcv;

@Local
public interface SiscobServiceEntityLocal {
	

	/**
	 * Servicio de consulta de credito
	 * @param nrp
	 * @param numCredito
	 * @param periodoCredito
	 * @return
	 * @throws Exception
	 */
	List<CptCreinc14ImssRcv> getCptCreinc14ImssRcv(String nrp, Long numCredito, Long periodoCredito) throws Exception;
	
	List<HistPatronesConvenioImssrcv> getHistPatronesConvenioImssrcv(String nrp, Long numCredito, Long periodoCredito)
			throws Exception;

	List<HCopEstadoCuenta> getHCopEstadoCuenta(String nrp) throws Exception;
	
	List<HRcvEstadoCuenta> getHRcvEstadoCuenta(String nrp) throws Exception;
	
}
