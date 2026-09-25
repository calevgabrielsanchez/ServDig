package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;


public interface CatalogosDAO {
	
	public String getTipoObraById(Integer id);
	public String getFaseObraById(Integer id);
	public String getClaseObraById(Integer id);
	public Long getIdPatByRegPat(String regPat);
	public String getNombreIncidenciaById(Integer id);
	public CgcCatcriterioseleccion getIdTipoAndIdOrigenByIdCriterioSeleccion(Integer criterio);
	List<CrcTipoCorr> getTiposCorreccion(Long tipoCorr);
}
