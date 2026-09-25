package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HistPatronesConvenioImssrcv;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siscob.SiscobServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaCreditosSiscobServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserSiscobEntityToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;



@Stateless(name = "consultaCreditosSiscobService", mappedName = "consultaCreditosSiscobService")
public class ConsultaCreditosSiscobService extends AbstractServiceBusiness implements IConsultaCreditosSiscobServiceRemote{
	
	private static final Logger log = LoggerFactory
            .getLogger(ConsultaCreditosSiscobService.class);
	
	@EJB
	private SiscobServiceEntityLocal siscobServiceEntity;

	@Override
	public List<CptCreinc14ImssRcv> getCptCreinc14ImssRcv(String nrp, Long numCredito, Long periodoCredito)
			throws ServiciosRestException {
		log.debug("llegando a hacer la consulta de getCptCreinc14ImssRcv con nrp {}", nrp);
		nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		ValidacionesComunesUtil.validaIdCatalogo(numCredito, "numero de credito");
		ValidacionesComunesUtil.validaIdCatalogo(periodoCredito, "periodo de credito");
		try {
			return ParserSiscobEntityToRest.parserCptCreinc14ImssRcvEntityToRest(
						siscobServiceEntity.getCptCreinc14ImssRcv(nrp, numCredito, periodoCredito));
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch(Exception e) {
			ValidacionesComunesUtil.getExcepcionConsultaSiscob(e, nrp);
		}
		return null;
	}

	@Override
	public List<HistPatronesConvenioImssrcv> getHistPatronesConvenioImssrcv(String nrp, Long numCredito,
			Long periodoCredito) throws ServiciosRestException {
		log.debug("llegando a hacer la consulta de getHistPatronesConvenioImssrcv con nrp {}", nrp);
		nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		ValidacionesComunesUtil.validaIdCatalogo(numCredito, "numero de credito");
		ValidacionesComunesUtil.validaIdCatalogo(periodoCredito, "periodo de credito");
		try {
			return ParserSiscobEntityToRest.parserHistPatronesConvenioImssrcvEntityToRest(
					siscobServiceEntity.getHistPatronesConvenioImssrcv(nrp, numCredito, periodoCredito));
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch(Exception e) {
			ValidacionesComunesUtil.getExcepcionConsultaSiscob(e, nrp);
		}
		return null;
		
	}

	@Override
	public List<HCopEstadoCuenta> getHCopEstadoCuenta(String nrp) throws ServiciosRestException {
		log.debug("llegando a hacer la consulta de getHCopEstadoCuenta con nrp {}", nrp);
		nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		try {
			return ParserSiscobEntityToRest.parserHCopEstadoCuentaEntityToRest(
						siscobServiceEntity.getHCopEstadoCuenta(nrp));
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch(Exception e) {
			ValidacionesComunesUtil.getExcepcionConsultaSiscob(e, nrp);
		}
		return null;
		
	}

	@Override
	public List<HRcvEstadoCuenta> getHRcvEstadoCuenta(String nrp) throws ServiciosRestException {
		log.debug("llegando a hacer la consulta de getHRcvEstadoCuenta con nrp {}", nrp);
		nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);
		try {
			return ParserSiscobEntityToRest.parserHRcvEstadoCuentaEntityToRest(
						siscobServiceEntity.getHRcvEstadoCuenta(nrp));
		}catch (ServiciosRestException ex) {
			throw ex;
		}catch(Exception e) {
			ValidacionesComunesUtil.getExcepcionConsultaSiscob(e, nrp);
		}
		return null;
	}

}
