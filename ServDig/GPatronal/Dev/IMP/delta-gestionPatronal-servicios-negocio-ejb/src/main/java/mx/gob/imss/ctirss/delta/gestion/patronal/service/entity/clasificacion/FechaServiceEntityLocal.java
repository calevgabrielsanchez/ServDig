package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.fecha.FechaDto;

@Local
public interface FechaServiceEntityLocal {
	
	List<FechaDto> consultarFestivos(String mes,String year);

}
