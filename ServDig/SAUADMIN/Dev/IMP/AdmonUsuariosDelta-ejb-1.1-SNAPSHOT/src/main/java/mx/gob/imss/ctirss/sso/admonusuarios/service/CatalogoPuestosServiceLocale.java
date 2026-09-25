package mx.gob.imss.ctirss.sso.admonusuarios.service;

import java.util.List;
import javax.ejb.Local;
//import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestosDTO;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestosDTO;

@Local
public interface CatalogoPuestosServiceLocale {
	List<PuestosDTO> findAll(long idDepto) throws Exception;
	PuestosDTO findOne(PuestosDTO clase) throws Exception;
	void update(PuestosDTO clase, long Iddep) throws Exception;
	void create(PuestosDTO clase, long Iddep) throws Exception;
	void delete(PuestosDTO clase, long Iddep) throws Exception; 
}
