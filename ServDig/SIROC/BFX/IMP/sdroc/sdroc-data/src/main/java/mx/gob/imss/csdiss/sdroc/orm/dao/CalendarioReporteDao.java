package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface CalendarioReporteDao extends AbstractDao<RocCalendarioReporte, Long> {
	
	CalendarioReporteDTO findMesPresentacionDto(String mes);
	
	RocCalendarioReporte findMesPresentacion(String mes);
	
	RocCalendarioReporte findMesDeclarar(String mes);
	
	List<RocCalendarioReporte> calendariosOrdenadosAsc();
	
	RocCalendarioReporte findCveBimCalendario(Long cveBimCalendario);

}
