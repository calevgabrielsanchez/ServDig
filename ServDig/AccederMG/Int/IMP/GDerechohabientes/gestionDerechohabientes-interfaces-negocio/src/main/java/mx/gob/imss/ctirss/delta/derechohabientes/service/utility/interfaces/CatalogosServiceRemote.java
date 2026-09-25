package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;


@Remote
public interface CatalogosServiceRemote {
	
	public Sexo getCatalogoSexo(long idCatalogo) throws DerechohabientesBusinessException;
	public Parentesco getCatalogoParentesco(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public EstadoCivil getCatalogoEstadoCivil(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	public RazonRegistro getCatalogoRazonRegistro(long idCatalogo)  throws DerechohabientesBusinessException, Exception;
	TipoTramite getCatalogoTipoTramite(Long idCatalogo);
	List<Caracter> getCatalogoCaracter() throws  DerechohabientesBusinessException ;
	
	
	/*adicion de nuveos catalogos para servicios externos*/
	List<Sexo> getCatalogoSexo()  throws DerechohabientesBusinessException;
	List<EstadoCivil> getCatalogoEstadoCivil()  throws DerechohabientesBusinessException;
	
	Pais getPaisById(Long idPais) throws DerechohabientesBusinessException;
	List<Pais> getCatalogoPais() throws DerechohabientesBusinessException;
	List<EntidadFederativa> getCatalogoEntidadFed() throws DerechohabientesBusinessException;
	
	 List<Parentesco> getCatalogoParentesco()  throws DerechohabientesBusinessException, Exception;
 

	 Turno getTurnoByID(Long idTurno)throws DerechohabientesBusinessException;
	 List<Turno> getTurnos() throws DerechohabientesBusinessException;

	
	
	
}
