package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;


@Local
public interface CatalogosDaoLocal {

	public TipoTramite getTipoTramite(Long idTipoTramite) throws DerechohabientesBusinessException;
	public Sexo getCatalogoSexo(long idCatalogo)  throws DerechohabientesBusinessException;
	public List<Parentesco> getCatalogoParentescos()  throws DerechohabientesBusinessException, Exception;
	public Parentesco getCatalogoParentesco(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public EstadoCivil getCatalogoEstadoCivil(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public RazonRegistro getCatalogoRazonRegistro(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public List<Parentesco> findParentesco()  throws DerechohabientesBusinessException, Exception;
	public Caracter getCatalogoCaracter(Long idCaracter) throws DerechohabientesBusinessException,Exception;
	
	
	/*adicion de nuveos catalogos para servicios externos*/
	List<Sexo> getCatalogoSexo()  throws DerechohabientesBusinessException;
	List<EstadoCivil> getCatalogoEstadoCivil()  throws DerechohabientesBusinessException;
	
	Pais getPaisById(Long idPais) throws DerechohabientesBusinessException;
	List<Pais> getCatalogoPais() throws DerechohabientesBusinessException;
	List<EntidadFederativa> getCatalogoEntidadFed() throws DerechohabientesBusinessException;
	
	List<Caracter>  getCatalogoCaracter() throws DerechohabientesBusinessException;

	public boolean getCircunscripcion(Long asegurado, Long integrante) throws IllegalArgumentException;
	
}