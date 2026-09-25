package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;
@Local
public interface DelegacionDaoLocal {

	List<DitUmfCodPo> findUmfCodigoPostalListByDelegacion ( DicDelegacion dicDelegacion) throws Exception;

	DicDelegacion findDelegacion(Long idDelegacion) throws Exception;
	List<EntidadFederativa> findEntidaFederativaByDelegacion(Long idDelegacion) throws DerechohabientesBusinessException, Exception;
	List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion, Long idEstado) throws DerechohabientesBusinessException, Exception;
	List<Asentamiento> findAsentamientoByDelegacionMunicipioEntidad(Long idDelegacion,Long idMunicipio, Long idEntidad) throws DerechohabientesBusinessException, Exception;

}