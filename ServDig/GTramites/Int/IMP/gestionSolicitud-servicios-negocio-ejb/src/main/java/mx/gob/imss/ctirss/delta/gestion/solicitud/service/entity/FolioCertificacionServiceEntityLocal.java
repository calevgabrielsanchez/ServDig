package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieAgotadaException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.EstadoFolioCertificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.FolioCertificacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.SolicitudFolioCertificacion;

@Local
public interface FolioCertificacionServiceEntityLocal {

	FolioCertificacion obtenerFolio(EstadoFolioCertificacionEnum estado,
			int anioRegistro);

	void activarFolio(long idFolio);

	void darBajaFolio(long idFolio);

	Long obtenerFolio(String secuencia) throws SerieAgotadaException;

	void guardarRelacionSolicitudFolioCertificacion(
			SolicitudFolioCertificacion model);

}