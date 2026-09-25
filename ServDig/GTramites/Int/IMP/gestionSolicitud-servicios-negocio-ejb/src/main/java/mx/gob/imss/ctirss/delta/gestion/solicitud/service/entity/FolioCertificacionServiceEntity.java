package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieAgotadaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.EstadoFolioCertificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.FolioCertificacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.SolicitudFolioCertificacion;
import mx.gob.imss.ctirss.delta.persistence.DitFolioCertificacion;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudCertificacion;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "folioCertificacionServiceEntity")
public class FolioCertificacionServiceEntity extends AbstractServiceEntity
		implements FolioCertificacionServiceEntityLocal {

	@SuppressWarnings("unchecked")
	@Override
	public FolioCertificacion obtenerFolio(
			EstadoFolioCertificacionEnum estado, int anioRegistro) {

		FolioCertificacion folio = null;

		/*
		 * Un folio se encuentra inactivo cuando las fechas de alta,
		 * actualizacion y baja son iguales; un folio se encuentra activo cuando
		 * la fecha de baja es nula; un folio se encuentra en baja cuando la
		 * fecha de baja no es nula y diferente a la de alta y a la de
		 * actualización
		 */

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CVE_ID_FOLIO_CERTIFICACION AS \"idFolioCertificacionAux\", ");
		sql.append("NUM_DELEGACION AS \"numDelegacion\", ");
		sql.append("NUM_ANIO_REGISTRO AS \"anioRegistroAux\", ");
		sql.append("NOM_SECUENCIA AS \"secuencia\"  ");
		sql.append("FROM DIT_FOLIO_CERTIFICACION ");
		sql.append("WHERE NUM_ANIO_REGISTRO = :anioRegistro ");

		if (estado.getId() == EstadoFolioCertificacionEnum.ACTIVO.getId()) {
			sql.append("AND FEC_REGISTRO_BAJA IS NULL ");
		} else if (estado.getId() == EstadoFolioCertificacionEnum.INACTIVO
				.getId()) {
			sql.append("AND TRUNC(FEC_REGISTRO_ALTA) = TRUNC(FEC_REGISTRO_ACTUALIZADO) ");
			sql.append("AND TRUNC(FEC_REGISTRO_ALTA) = TRUNC(FEC_REGISTRO_BAJA) ");
		}

		sql.append("ORDER BY NUM_DELEGACION ASC");

		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger("anioRegistro", anioRegistro);
		query.setResultTransformer(Transformers
				.aliasToBean(FolioCertificacion.class));

		List<FolioCertificacion> folios = query.list();

		if (!CollectionUtils.isEmpty(folios)) {
			this.log.debug("Se encontraron " + folios.size()
					+ " folios para certificacion " + estado.getDesc());
			/*
			 * Tanto para folios activos como inactivos se toma la primera
			 * posición, ya que para los activos sólo debe existir un sólo folio
			 * y para los inactivos se toma el que tenga el numDelegacion más
			 * bajo
			 */
			folio = folios.get(0);
		}

		return folio;
	}
	
	@Override
	public void activarFolio(long idFolio) {
		
		this.log.debug("Se va a activar el folio para la certificación con el id " + idFolio);
		
		DitFolioCertificacion entity = this.em.find(DitFolioCertificacion.class, idFolio);
		
		entity.setFecRegistroBaja(null);
		entity.setFecRegistroActualizado(new Date());
		
		this.log.debug("Se activó exitosamente el folio para la certificación con el id " + idFolio);

	}
	
	@Override
	public void darBajaFolio(long idFolio) {
		
		this.log.debug("Se va a dar de baja el folio para la certificación con el id " + idFolio);
		
		DitFolioCertificacion entity = this.em.find(DitFolioCertificacion.class, idFolio);
		
		entity.setFecRegistroBaja(new Date());
		
		this.log.debug("Se dio de baja exitosamente el folio para la certificación con el id " + idFolio);
		
	}
	
	@Override
	public Long obtenerFolio(String secuencia) throws SerieAgotadaException {
		
		Long folio = null;
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT ").append(secuencia).append(".NEXTVAL ");
		sqlQuery.append("AS folio FROM DUAL");

		org.hibernate.Query query = this.getSession()
				.createSQLQuery(sqlQuery.toString())
				.addScalar("folio", StandardBasicTypes.LONG);
		
		try {
			folio = (Long) query.uniqueResult();
		} catch (HibernateException e) {
			/*
			 * Se busca el código ORA-08004, que es el que Oracle lanza cuando
			 * la secuenca ha llegado a su límite
			 */
			if(e.getCause().getMessage().contains("ORA-08004")) {
				this.log.error("La secuencia " + secuencia + " ha llegado a su límite");
				throw new SerieAgotadaException(secuencia);
			} else {
				this.log.error(e);
			}
		}
		
		return folio;
	}
	
	@Override
	public void guardarRelacionSolicitudFolioCertificacion(
			SolicitudFolioCertificacion model) {
		
		DitSolicitud ditSolicitud = new DitSolicitud();
		ditSolicitud.setCveIdSolicitud(model.getSolicitud().getSolicitudId());
		
		DitSolicitudCertificacion entity = new DitSolicitudCertificacion();
		entity.setDitSolicitud(ditSolicitud);
		entity.setNumFolioCertificacion(model.getFolioCertificacion());
		
		entity.setFecRegistroAlta(new Date());
		
		this.em.persist(entity);

	}

}
