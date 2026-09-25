package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieAgotadaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.FolioCertificacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FolioCertificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.EstadoFolioCertificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.FolioCertificacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.GenerarFolioCertificacionException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.SolicitudFolioCertificacion;

@Stateless(mappedName = "folioCertificacionServiceBusiness")
public class FolioCertificacionServiceBusiness extends AbstractServiceBusiness
		implements FolioCertificacionServiceBusinessRemote {

	private static final Long MAXIMO_DE_FOLIO_POR_ANIO = new Long(999999);

	@EJB
	private FolioCertificacionServiceEntityLocal folioCertificacionServiceEntity;

	@Override
	public String obtenerFolio() throws GenerarFolioCertificacionException {

		FolioCertificacion folioCert = null;
		String folio = null;

		DateFormat df = new SimpleDateFormat("yy");
		int anio = Integer.parseInt(df.format(new Date()));

		// Primero se deben buscar un folio activo
		folioCert = this.folioCertificacionServiceEntity.obtenerFolio(
				EstadoFolioCertificacionEnum.ACTIVO, anio);

		if (folioCert == null) {
			this.log.warn("No se encontraron folios para certificacion activos, se buscan inactivos");

			/*
			 * Si no se encontró algún folio activo, se busca que existan
			 * inactivos
			 */
			folioCert = this.folioCertificacionServiceEntity.obtenerFolio(
					EstadoFolioCertificacionEnum.INACTIVO, anio);

			if (folioCert != null) {
				this.log.debug("Se encontró un folio inactivo, se procede a activar");
				this.folioCertificacionServiceEntity.activarFolio(folioCert
						.getIdFolioCertificacion());
			}
		}

		if (folioCert != null) {
			String secuencia = folioCert.getSecuencia();

			this.log.debug("Se va a utilzar la secuencia " + secuencia
					+ " del folioCertificacion con id "
					+ folioCert.getIdFolioCertificacion()
					+ " para generar el folio para la certificación");

			try {
				Long consecutivo = this.folioCertificacionServiceEntity
						.obtenerFolio(secuencia);

				this.log.debug("Se obtuvo el consecutivo " + consecutivo
						+ " de la secuencia " + secuencia);

				NumberFormat nf = new DecimalFormat("000000");
				StringBuffer folioAux = new StringBuffer();
				folioAux.append(folioCert.getNumDelegacion());
				folioAux.append(nf.format(consecutivo));

				folio = folioAux.toString();

				this.log.debug("Folio generado " + folio);

				if (consecutivo >= MAXIMO_DE_FOLIO_POR_ANIO) {
					this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
					this.folioCertificacionServiceEntity.darBajaFolio(folioCert
							.getIdFolioCertificacion());
				}

			} catch (SerieAgotadaException e) {
				this.log.error(e.getMessage());
				throw new GenerarFolioCertificacionException(
						"Ocurrió un error al generar el folio, favor de intentarlo mas tarde", 2);
			}

		} else {
			throw new GenerarFolioCertificacionException(
					"No se encontraron folios disponibles para la certificación", 3);
		}

		return folio;
	}

	@Override
	public void guardarRelacionSolicitudFolioCertificacion(
			SolicitudFolioCertificacion solicFolioCertificacion) {
		
		this.log.debug("Se va a guardar la relación entre la solicitud de certificación ["
				+ solicFolioCertificacion.getSolicitud().getSolicitudId()
				+ "] y el folio de la certificacion generado ["
				+ solicFolioCertificacion.getFolioCertificacion() + "]");
		
		this.folioCertificacionServiceEntity.guardarRelacionSolicitudFolioCertificacion(solicFolioCertificacion);
		
		this.log.debug("Se guardó exitosamente la relación entre la solicitud de certificación ["
				+ solicFolioCertificacion.getSolicitud().getSolicitudId()
				+ "] y el folio de la certificacion generado ["
				+ solicFolioCertificacion.getFolioCertificacion() + "]");
		
	}
	
	@Override
	public void guardarRelacionSolicitudFolioCertificacion(
			Long idSolicitud, String numResolucion) {
		
		SolicitudFolioCertificacion solicFolioCertificacion = new SolicitudFolioCertificacion();
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		solicFolioCertificacion.setSolicitud(solicitud);
		solicFolioCertificacion.setFolioCertificacion(numResolucion);
		
		this.guardarRelacionSolicitudFolioCertificacion(solicFolioCertificacion);
	}
}
