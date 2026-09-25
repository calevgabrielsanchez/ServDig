package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.CatalogosServiceRemote;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao.CatalogosDAOLocal;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;

@Stateless(name="catalogosService", mappedName = "catalogosService")
public class CatalogosServiceBean extends AbstractService implements CatalogosServiceRemote{
	
	@EJB CatalogosDAOLocal catalogoDao;
	
	public String getTipoObraById(Integer id){
		return catalogoDao.getTipoObraById(id);
	}
	
	public String getFaseObraById(Integer id){
		return catalogoDao.getFaseObraById(id);
	}
	
	public String getClaseObraById(Integer id){
		return catalogoDao.getClaseObraById(id);
	}
	
	public Long getIdPatByRegPat(String regPat){
		return catalogoDao.getIdPatByRegPat(regPat);
	}
	
	public String getNombreIncidenciaById(Integer id){
		return catalogoDao.getNombreIncidenciaById(id);
	}

	public CgcCatcriterioseleccion getIdTipoAndIdOrigenByIdCriterioSeleccion(Integer criterio){
		return catalogoDao.getIdTipoAndIdOrigenByIdCriterioSeleccion(criterio);
	}

	@Override
	public List<CrcTipoCorr> getTiposCorreccion(Long tipoCorr) {
		return catalogoDao.getTiposCorreccion(tipoCorr);
	}

}
