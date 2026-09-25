package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
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

/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "catalogosService", mappedName = "catalogosService")
public class CatalogosService implements CatalogosServiceRemote{

@EJB
CatalogosDaoLocal catalogosDao;

@EJB
TurnoDaoLocal turnoDao;


@Override
public Sexo getCatalogoSexo(long idCatalogo)  throws DerechohabientesBusinessException{
	Sexo sexo = catalogosDao.getCatalogoSexo(idCatalogo);
	return sexo;
}



@Override
public Parentesco getCatalogoParentesco(long idCatalogo)  throws DerechohabientesBusinessException,Exception{
	Parentesco parentesco = catalogosDao.getCatalogoParentesco(idCatalogo);
	return parentesco;
}

@Override
public List<Parentesco> getCatalogoParentesco()  throws DerechohabientesBusinessException, Exception{
	return catalogosDao.getCatalogoParentescos();
}

@Override
public EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws DerechohabientesBusinessException,Exception{
	EntidadFederativa entidadFed = catalogosDao.getCatalogoEntidadFed(idCatalogo);
	return entidadFed;
}

@Override
public EstadoCivil getCatalogoEstadoCivil(long idCatalogo)  throws DerechohabientesBusinessException,Exception{
	EstadoCivil estadoCivil = catalogosDao.getCatalogoEstadoCivil(idCatalogo);
	return estadoCivil;
}

@Override
public RazonRegistro getCatalogoRazonRegistro(long idCatalogo)  throws DerechohabientesBusinessException,Exception{
	RazonRegistro razonRegistro = catalogosDao.getCatalogoRazonRegistro(idCatalogo);
	return razonRegistro;
}

@Override
public TipoTramite getCatalogoTipoTramite(Long idCatalogo) {
	TipoTramite tipoTramite = null;
	try {
		tipoTramite = catalogosDao.getTipoTramite(idCatalogo);
	} catch (DerechohabientesBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return tipoTramite;
}

	@Override
	public List<Caracter> getCatalogoCaracter() throws  DerechohabientesBusinessException {
			return catalogosDao.getCatalogoCaracter();
		
	 }
	
	
	/*adicion de nuveos catalogos para servicios externos*/
	@Override
	public List<Sexo> getCatalogoSexo()  throws DerechohabientesBusinessException{
			return catalogosDao.getCatalogoSexo();
	}
	
	@Override
	public List<EstadoCivil> getCatalogoEstadoCivil()  throws DerechohabientesBusinessException{
			return catalogosDao.getCatalogoEstadoCivil();
	}
	
	@Override
	public Pais getPaisById(Long idPais) throws DerechohabientesBusinessException{
			return catalogosDao.getPaisById(idPais);
	}
	
	@Override
	public List<Pais> getCatalogoPais() throws DerechohabientesBusinessException{
			return catalogosDao.getCatalogoPais();
	}
	
	@Override
	public List<EntidadFederativa> getCatalogoEntidadFed() throws DerechohabientesBusinessException{
			return catalogosDao.getCatalogoEntidadFed();
	}
	
	@Override
	 public Turno getTurnoByID(Long idTurno)throws DerechohabientesBusinessException{
			return turnoDao.getTurnoByID(idTurno);
	 }
	@Override
	public List<Turno> getTurnos() throws DerechohabientesBusinessException{
		return turnoDao.getTurnos();
	}


}
