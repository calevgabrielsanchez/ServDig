package mx.gob.imss.ctirss.correccion.catalogos.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;

public interface CatalogosService {
	
	public String getTipoObraById(Integer id);
	public String getFaseObraById(Integer id);
	public String getClaseObraById(Integer id);
	public Long getIdPatByRegPat(String regPat);
	public String getNombreIncidenciaById(Integer id);
	public CgcCatcriterioseleccion getIdTipoAndIdOrigenByIdCriterioSeleccion(Integer criterio);
	public List< CrcTipoCorr> getTiposCorreccion(Long tipoCorr);
}
