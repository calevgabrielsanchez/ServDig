package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HistPatronesConvenioImssrcv;


@Remote
public interface IConsultaCreditosSiscobServiceRemote {
	
	List<CptCreinc14ImssRcv> getCptCreinc14ImssRcv(String nrp, Long numCredito, Long periodoCredito)  
			throws ServiciosRestException;
	List<HistPatronesConvenioImssrcv> getHistPatronesConvenioImssrcv(String nrp, Long numCredito, Long periodoCredito)
			throws ServiciosRestException;
	List<HCopEstadoCuenta> getHCopEstadoCuenta(String nrp) throws ServiciosRestException;	
	List<HRcvEstadoCuenta> getHRcvEstadoCuenta(String nrp) throws ServiciosRestException;

}
