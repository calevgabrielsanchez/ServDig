package mx.gob.imss.distss.delta.rtt.service.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.CausaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.persistence.DicCausaDesacuerdo;
import mx.gob.imss.ctirss.delta.persistence.DitEscritoDesacuerdo;
import mx.gob.imss.distss.delta.rtt.service.util.EscritoUtil;

@Stateless(name = "escritoDesacuerdoEntity", mappedName = "escritoDesacuerdoEntity")
public class EscritoDesacuerdoEntity extends AbstractServiceEntity implements EscritoDesacuerdoEntityLocal {

	@Override
	public TramiteEscritoDesacuerdo guardarEscrito(TramiteEscritoDesacuerdo tramiteEscritoDesacuerdo) {
		
		DitEscritoDesacuerdo ditEscritoDesacuerdo = EscritoUtil.escritoModelToEntity(tramiteEscritoDesacuerdo);
		
		if(ditEscritoDesacuerdo!= null) {
			ditEscritoDesacuerdo.setFecRegistroAlta(new Date());
			this.em.persist(ditEscritoDesacuerdo);
			tramiteEscritoDesacuerdo.setIdEscrito(ditEscritoDesacuerdo.getCveIdDesacuerdo());
		}

		return tramiteEscritoDesacuerdo;
	}

	@Override
	public String generarFolioRecepcion( PatronRiesgosTrabajo patronRiesgosTrabajo) throws SolicitudNoValidaException {
		
		String folioRecepcion = "ED-L-";
		Subdelegacion subPatron = patronRiesgosTrabajo.getSubdelegacion(); 
		folioRecepcion += numeroADosPociociones(subPatron.getDelegacion().getClave()); //clave de la delegacion 01
		folioRecepcion += numeroADosPociociones(subPatron.getClave())+"-"; //Clave de la subdelegacion 01
		folioRecepcion += this.getConsecutivoSubdelegacion(subPatron)+"/";//Consecutivo 0001
		folioRecepcion += (new SimpleDateFormat("yy")).format(new Date());//Anio a dos posiciones 18
		
		return folioRecepcion;
	}
	
	@Override
	public String getConsecutivoSubdelegacion(Subdelegacion subdelegacion) throws SolicitudNoValidaException {
		String consecutivo = null;
		String error = "Se han agotado los folios de recepcion para la subdelegacion " + subdelegacion.getDescripcion();
		StringBuffer queryConsecutivo = new StringBuffer("SELECT LPAD(SEQ_ID_SUBDEL_");
		queryConsecutivo.append(subdelegacion.getId()).append("_DESACUERDO.NEXTVAL,4,'0') AS CONSECUTIVO");
		queryConsecutivo.append(" FROM DUAL");
		
		Query query = this.getSession().createSQLQuery(queryConsecutivo.toString()).addScalar("CONSECUTIVO", StandardBasicTypes.STRING);
		
		try {
			consecutivo = (String) query.uniqueResult();
		} catch(HibernateException e) {
			if(e.getCause().getMessage().contains("ORA-08004")) {
				throw new SolicitudNoValidaException(error + " (ERR-1)");
			} else {
				this.log.error(e);
				throw new SolicitudNoValidaException("No fue posible obtener el folio para la la subdelegacion (ERR-3-"+subdelegacion.getId()+")");
			}
		}
		
		log.debug("El consecutivo obtenido para la subdelegacion ["+subdelegacion.getId() + ", " 
		+ subdelegacion.getClave() + ", " + subdelegacion.getDescripcion()+"] fue " + consecutivo);
		
		Integer longConsecutivo = new Integer(consecutivo);
		
		if(longConsecutivo > 9999) {
			throw new SolicitudNoValidaException(error + " (ERR-2)");
		}
		
		return consecutivo;
	}
	
	
	
	@Override
	public List<CausaDesacuerdo> getCausasDesacuerdo(Long idMateria) {
		
		Criteria query = this.getSession().createCriteria(DicCausaDesacuerdo.class);
		query.createAlias("dicMateriaDesacuerdo", "materia");
		query.add(Restrictions.eq("materia.cveIdMateriaDesacuerdo", idMateria));
		
		@SuppressWarnings("unused")
		List<DicCausaDesacuerdo> resultado = query.list();
		List<CausaDesacuerdo> causas = null;
		if(resultado != null && !resultado.isEmpty()) {
			causas = new ArrayList<CausaDesacuerdo>();
			
			for(DicCausaDesacuerdo dicCausa: resultado) {
				causas.add(EscritoUtil.causaEntityToModal(dicCausa));
			}
		}
		
		return causas;
	}

	/**
	 * Metodo para poner un 0 cuando la cve solo es de un digito
	 * ejemplo 1 lo convertiria a 01
	 * @param cve
	 * @return
	 */
	private String numeroADosPociociones(String cve) {
		if(cve.length() == 1) {
			cve = "0"+cve;
		}
		return cve;
	}

}