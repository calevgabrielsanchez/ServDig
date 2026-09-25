package mx.gob.imss.ctirss.delta.gestion.domicilio.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.AsentamientoUMF;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

@Local
public interface DelegacionDaoLocal {

	List<EntidadFederativa> findEntidaFederativaByDelegacion(Long idDelegacion);
	List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion, Long idEstado);
	List<Asentamiento> findAsentamientoByDelegacionMunicipioEntidad(Long idDelegacion,Long idMunicipio, Long idEntidad);
	List<Asentamiento> findAsentamientosByUmf(Long idUmf);
	List<Asentamiento> findAsentamientosByUmfCodPos(Long idUmf, String codigoPostal);
	List<AsentamientoUMF> findAsentamientosUmfByCP(String codigoPostal);
	List<Asentamiento> findAsentamientoByDelegacionCp(List<Long> idDelegacion, String cp);
	/**
	 * Metodo para ubicar los asentamientos que se encuentren en uns delehacion
	 * y que se encuentren relacionados a la umf origen o destino
	 * ambos ids de umfs pueden ser opcionales y solo regresara los asentamientos
	 * @param idDelegacion
	 * @param cp
	 * @return
	 */
	List<Asentamiento> findAsentamientoByDelegacionCpYUmfsOrigenDestino(List<Long> idDelegacion, String cp, Long idUmfOrigen, Long idUmfDestino);
	
}