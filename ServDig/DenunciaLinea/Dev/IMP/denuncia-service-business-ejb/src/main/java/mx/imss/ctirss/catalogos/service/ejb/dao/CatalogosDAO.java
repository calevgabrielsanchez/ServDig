package mx.imss.ctirss.catalogos.service.ejb.dao;

import java.util.List;




public interface CatalogosDAO {
	
	public String getTipoObraById(Integer id);
	public String getFaseObraById(Integer id);
	public String getClaseObraById(Integer id);
	public Long getIdPatByRegPat(String regPat);
	public String getNombreIncidenciaById(Integer id);
	
	
}
