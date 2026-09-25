package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

@Local
public interface AsignacionDomicilioServiceLocal {

	List<GrupoFamiliar> actualizaDomPersonasSinDomicilioEnUmf(
			Long idAsignacionNSS, Long idUmfBusqueda, Domicilio nuevoDomicilio,
			List<Long> idsPersonasExcluir) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> actualizaDomicilioPadresConcubinas(AsignacionNSS nss, Integer patronIMSS, Domicilio nuevoDomicilio) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patronIMSS) throws Exception;
}
