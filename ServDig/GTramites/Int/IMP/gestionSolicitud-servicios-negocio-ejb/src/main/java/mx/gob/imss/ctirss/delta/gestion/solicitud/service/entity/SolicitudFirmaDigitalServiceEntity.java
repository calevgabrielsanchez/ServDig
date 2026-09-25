/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudFirmaDigital;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud
 *  @Fecha: 10:29:18
 */
@Stateless
public class SolicitudFirmaDigitalServiceEntity extends AbstractServiceEntity implements SolicitudFirmaDigitalServiceEntityLocal{
	
	
	
	@Override
	public FirmaElectronica consultarFirmaElectronica(Solicitud solicitud) {
		FirmaElectronica firma = null;
		
		if(solicitud != null && solicitud.getSolicitudId() != null) {
			Criteria query = this.getSession().createCriteria(DitSolicitudFirmaDigital.class);
			query.createAlias("ditSolicitud", "solicitud");
			query.add(Restrictions.eq("solicitud.cveIdSolicitud", solicitud.getSolicitudId()));
			
			List<DitSolicitudFirmaDigital> firmas = query.list();
			
			if(firmas != null && !firmas.isEmpty()) {
				firma = new FirmaElectronica();
				DitSolicitudFirmaDigital dfirma = firmas.get(0);
				
				firma.setSecuenciaNotaria(dfirma.getNumSecNotaria());
				firma.setReciboNotarial(dfirma.getNumSecNotaria());
				firma.setCadenaOriginal(dfirma.getNumCadenaOriginal());
				firma.setSerialCertificado(dfirma.getRefNumSerieCertificado());
				firma.setRecibo(dfirma.getNumSelloDigital());
			}
			
		
		}
		
		return firma;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.SolicitudServiceEntityLocal#consultarSolicitud(java.lang.Long)
	 */
	@Override
	public void insertarSolicitudFirmaDigital(Solicitud solicitud, FirmaElectronica firma) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'Z'");
		System.out.println("Insertando solicitud - service entity: "+solicitud.toString());
		DitSolicitudFirmaDigital ditSolicitudFirmaDigital = null;
		List<DitSolicitudFirmaDigital> firmas = null;
		boolean tramiteRepresentante = false;
		boolean firmaEncontrada = false;
		
		//this.em.find(DitSolicitudFirmaDigital.class, solicitud.getSolicitudId());
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())) {
				tramiteRepresentante = true;
				break;
			}
		}
		
		if(!tramiteRepresentante) {
			Criteria consulta = this.getSession().createCriteria(DitSolicitudFirmaDigital.class);
			consulta.add(Restrictions.eq("cveIdSolicitud", solicitud.getSolicitudId()));
			
			firmas = consulta.list();
			
			if(firmas != null && firmas.size() != 0) {
				ditSolicitudFirmaDigital = firmas.get(0);
				firmaEncontrada = true;
			}
		}
		
		DitSolicitud ditSolicitud = this.em.find(DitSolicitud.class, solicitud.getSolicitudId());
		if(ditSolicitudFirmaDigital==null){
			ditSolicitudFirmaDigital = new DitSolicitudFirmaDigital();
			ditSolicitudFirmaDigital.setFecRegAlta(Calendar.getInstance().getTime());
		}
		
		if(firma.getRecibo()==null)
			firma.setRecibo("YVYIUBUGIGUYYGKJG565451564654654654654654");
		
		ditSolicitudFirmaDigital.setFecRegActualizado(Calendar.getInstance().getTime());
		ditSolicitudFirmaDigital.setDitSolicitud(ditSolicitud);
		
		if (StringUtils.isNotBlank(firma.getCadenaOriginal())) {
			if (firma.getCadenaOriginal().length() > 500) {
				ditSolicitudFirmaDigital.setNumCadenaOriginal(firma.getCadenaOriginal().substring(0, 500));
			} else {
				ditSolicitudFirmaDigital.setNumCadenaOriginal(firma.getCadenaOriginal());
			}
		} else {
			this.log.debug("La firma digital no trae cadena original");
		}
		
		// TODO validar longitud de datos
		ditSolicitudFirmaDigital.setNumSecNotaria(firma.getReciboNotarial());
		
		if (firma.getRecibo().length() > 1500) {
			ditSolicitudFirmaDigital.setNumSelloDigital(firma.getRecibo().substring(0, 1500)); //Falta cambiar algo
		} else {
			ditSolicitudFirmaDigital.setNumSelloDigital(firma.getRecibo());
		}
		
		if(!firmaEncontrada) {
			ditSolicitudFirmaDigital.setCveIdSolicitud(solicitud.getSolicitudId());
		}
		
		ditSolicitudFirmaDigital.setRefUrlAcuseFirma(firma.getUrlAcuseFirma());
		ditSolicitudFirmaDigital.setRefNumSerieCertificado(firma.getSerialCertificado());

		try{
			if (StringUtils.isNotBlank(firma.getStrIniciaVigenciaCertificado())) {
				firma.setIniciaVigenciaCertificado(dateFormat.parse(firma.getStrIniciaVigenciaCertificado()));
			}
			if (StringUtils.isNotBlank(firma.getStrFinVigenciaCertificado())) {
				firma.setFinVigenciaCertificado(dateFormat.parse(firma.getStrFinVigenciaCertificado()));
			}
		} catch (ParseException e) {
			log.error(e);
		}

		ditSolicitudFirmaDigital.setFecInicioVigenciaCert(firma.getIniciaVigenciaCertificado());
		ditSolicitudFirmaDigital.setFecFinVigenciaCert(firma.getFinVigenciaCertificado());
		
		this.em.merge(ditSolicitudFirmaDigital);
		this.em.flush();
	}
	
	
	@Override
	public void insertarSolicitudFirmaDigitalThrowError(Solicitud solicitud, FirmaElectronica firma) throws FirmaDigitalException{
		
		try{
		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'Z'");
			System.out.println("Insertando solicitud - service entity: "+solicitud.toString());
			DitSolicitudFirmaDigital ditSolicitudFirmaDigital = null;
			List<DitSolicitudFirmaDigital> firmas = null;
			boolean tramiteRepresentante = false;
			boolean firmaEncontrada = false;
			
			//this.em.find(DitSolicitudFirmaDigital.class, solicitud.getSolicitudId());
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())) {
					tramiteRepresentante = true;
					break;
				}
			}
			
			if(!tramiteRepresentante) {
				Criteria consulta = this.getSession().createCriteria(DitSolicitudFirmaDigital.class);
				consulta.add(Restrictions.eq("cveIdSolicitud", solicitud.getSolicitudId()));
				
				firmas = consulta.list();
				
				if(firmas != null && firmas.size() != 0) {
					ditSolicitudFirmaDigital = firmas.get(0);
					firmaEncontrada = true;
				}
			}
			
			DitSolicitud ditSolicitud = this.em.find(DitSolicitud.class, solicitud.getSolicitudId());
			if(ditSolicitudFirmaDigital==null){
				ditSolicitudFirmaDigital = new DitSolicitudFirmaDigital();
				ditSolicitudFirmaDigital.setFecRegAlta(Calendar.getInstance().getTime());
			}
			
			if(firma.getRecibo()==null)
				firma.setRecibo("YVYIUBUGIGUYYGKJG565451564654654654654654");
			
			ditSolicitudFirmaDigital.setFecRegActualizado(Calendar.getInstance().getTime());
			ditSolicitudFirmaDigital.setDitSolicitud(ditSolicitud);
			
			if (StringUtils.isNotBlank(firma.getCadenaOriginal())) {
				if (firma.getCadenaOriginal().length() > 500) {
					ditSolicitudFirmaDigital.setNumCadenaOriginal(firma.getCadenaOriginal().substring(0, 500));
				} else {
					ditSolicitudFirmaDigital.setNumCadenaOriginal(firma.getCadenaOriginal());
				}
			} else {
				this.log.debug("La firma digital no trae cadena original");
			}
			
			// TODO validar longitud de datos
			ditSolicitudFirmaDigital.setNumSecNotaria(firma.getReciboNotarial());
			
			if (firma.getRecibo().length() > 1500) {
				ditSolicitudFirmaDigital.setNumSelloDigital(firma.getRecibo().substring(0, 1500)); //Falta cambiar algo
			} else {
				ditSolicitudFirmaDigital.setNumSelloDigital(firma.getRecibo());
			}
			
			if(!firmaEncontrada) {
				ditSolicitudFirmaDigital.setCveIdSolicitud(solicitud.getSolicitudId());
			}
			
			ditSolicitudFirmaDigital.setRefUrlAcuseFirma(firma.getUrlAcuseFirma());
			ditSolicitudFirmaDigital.setRefNumSerieCertificado(firma.getSerialCertificado());
	
			
			if (StringUtils.isNotBlank(firma.getStrIniciaVigenciaCertificado())) {
				firma.setIniciaVigenciaCertificado(dateFormat.parse(firma.getStrIniciaVigenciaCertificado()));
			}
			if (StringUtils.isNotBlank(firma.getStrFinVigenciaCertificado())) {
				firma.setFinVigenciaCertificado(dateFormat.parse(firma.getStrFinVigenciaCertificado()));
			}
			
	
			ditSolicitudFirmaDigital.setFecInicioVigenciaCert(firma.getIniciaVigenciaCertificado());
			ditSolicitudFirmaDigital.setFecFinVigenciaCert(firma.getFinVigenciaCertificado());
			
			this.em.merge(ditSolicitudFirmaDigital);
			this.em.flush();
		
		} catch (ParseException e) {
			log.error("Ocurrio un error de parseo al insertar la solicitud de firma digital: "+e);
			throw new FirmaDigitalException(e.getMessage());
		} catch (Exception e) {
			log.error("Ocurrio un error al insertar la solicitud de firma digital: "+e);
			throw new FirmaDigitalException(e.getMessage());
		}
	}
}
