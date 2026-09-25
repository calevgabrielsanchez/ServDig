package mx.gob.imss.cit.cda.service.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

@Local
public interface BitacoraMovimientosSINDOLocal {

	Map<String, List<BitacoraMovimientoSindoCDA>> obtenerBitacoraMovimientos(Date fechaMovimientos);
	
}
