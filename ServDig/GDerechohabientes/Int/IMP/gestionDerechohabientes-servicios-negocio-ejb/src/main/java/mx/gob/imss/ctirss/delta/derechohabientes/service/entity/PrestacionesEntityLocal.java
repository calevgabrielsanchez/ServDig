package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Prestacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PrestacionPorModalidad;

@Local
public interface PrestacionesEntityLocal {

	Prestacion getPrestacion(Long idPrestacion);
	List<Prestacion> getCatalogoPrestaciones();
	List<PrestacionPorModalidad> getPrestacionesPorModalidad(Long idModalidad);
	List<PrestacionPorModalidad> getPrestacionesPorModalidades(List<Long> idsModalidades);
}
