package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.DerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.RegistroParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless(name = "derechohabienteDao", mappedName = "derehohabienteDao")
public class DerechohabienteDao extends AbstractServiceEntity implements DerechohabienteDaoLocal {

	@EJB DerechohabienteParserServiceLocal derechohabienteParser;
	@EJB RegistroParserServiceLocal registroParserServiceLocal;
	
	//TODO Verificaar que se va a hacer con todo los relacionado a derechohabiente
	@Override
	public Derechohabiente getDerechohabiente(Long idDerechohabiente) throws DerechohabientesBusinessException,Exception {	
		Derechohabiente encontrado = null;
		/*
		DitDerechohabiente ditDerechohabiente = null;
		try {
			Query query = em.createNamedQuery("DitDerechohabiente.busqDerechohabienteByid");
			query.setParameter("idPersona", idDerechohabiente);
			ditDerechohabiente = (DitDerechohabiente) query.getSingleResult();
			
		}catch(NoResultException e){
			ditDerechohabiente = null;
		}catch (Exception e) {
			log.error("getDerechohabiente", e);
			throw e;			
		}			
		encontrado = derechohabienteParser.persisToModel(ditDerechohabiente);*/
		return encontrado;
	}
	
	@Override
	public TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException,Exception {

		DitRegistroDerechohabiente ditDerechohabiente=null;
		try {
			Criteria query = this.getSession().createCriteria(DitRegistroDerechohabiente.class);
			query.createAlias("ditTramite", "tram");
			query.add(Restrictions.eq("tram.cveIdTramite", idTramite));
			
			ditDerechohabiente= (DitRegistroDerechohabiente) query.uniqueResult();
			
		} catch(NoResultException e){
			ditDerechohabiente=null;
		}catch (Exception e) {
			log.error("getRegistroDerechohabiente", e);
			throw e;
		}
				
		TramiteRegistroDerechohabiente salida = registroParserServiceLocal.persisToModel(ditDerechohabiente);
		return salida;
	}

	@Override
	public void updateRegistroDerechohabiente(TramiteRegistroDerechohabiente registro) throws DerechohabientesBusinessException,Exception {
		DitRegistroDerechohabiente ditRegistro = registroParserServiceLocal.modelToPersist(registro);
		try {
			em.merge(ditRegistro);
		} catch (Exception e) {
			log.error("updateRegistroDerechohabiente", e);
			throw e;
		}
		
	}
	
	@Override
	public void updateDerechohabiente(Derechohabiente derechohabiente) throws DerechohabientesBusinessException,Exception {
		/*
		try {
			DitDerechohabiente ditRegistro = derechohabienteParser.modelToPersist(derechohabiente);
			em.merge(ditRegistro);
			em.flush();
		} catch (Exception e) {
			log.error("updateDerechohabiente", e);
			throw e;
		}*/
		
	}

	@Override
	public Long updateMedicoEnTurnoDerechohabientesbyDomicilio(
			Asentamiento asentamiento,MedicoEnTurno medicoEnTurno, MedicoEnTurno medicoEnTurnoV, Long idTramite, DitUmfCodPo origen, DitUmfCodPo destino) throws Exception {
		
		Long numColumnasAfectadas=0L;
		asentamiento.getLocalidad().getMunicipio().getClave();
		
		try {
			
				StringBuilder busquedaIntegrantesBase = new StringBuilder();
				busquedaIntegrantesBase.append(" from DIT_GRUPO_FAMILIAR gru, dit_personaf_dom pfdom, DG_DOMICILIO_GEOGRAFICO dom, ");
				busquedaIntegrantesBase.append(" dit_umf_cons_turno_medico umfcontu, DIT_UMF_CONSULTORIO_TURNO contur where ");
				busquedaIntegrantesBase.append(" gru.cve_id_personaf_dom = pfdom.cve_id_personaf_dom and pfdom.domicilio_id = dom.DOMICILIO_ID ");
				busquedaIntegrantesBase.append(" and gru.CVE_ID_UMF_CONS_TURNO_MED = umfcontu.CVE_ID_UMF_CONS_TURNO_MED ");
				busquedaIntegrantesBase.append(" and umfcontu.CVE_ID_UMF_CONS_TURNO = contur.CVE_ID_UMF_CONS_TURNO ");
				busquedaIntegrantesBase.append(" and dom.CVE_MUN = '"+asentamiento.getLocalidad().getMunicipio().getClave()+"'"); 
				busquedaIntegrantesBase.append(" and dom.CVE_ASEN = '"+asentamiento.getClave()+"'"); 
				busquedaIntegrantesBase.append(" and dom.CVE_ENT = '"+asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave()+"'");
				
				
				StringBuilder busquedaMasivo = new StringBuilder();
				busquedaMasivo.append(" SELECT SEQ_DITCAMBIOMASIVOCLINICA.nextval");
				busquedaMasivo.append(" , "+origen.getCveIdUmfCodPos());
				busquedaMasivo.append(" , "+destino.getCveIdUmfCodPos());
				busquedaMasivo.append(" ,"+idTramite);
				
				// -----------------
				// MATUTINO
				// -----------------
				StringBuilder bitacoraCambioMasivo = new StringBuilder();
				bitacoraCambioMasivo.append("INSERT INTO dit_cambio_masivo_clinica (cve_id_cambio_masivo_clinica, cve_id_umf_cod_pos_origen, ");
				bitacoraCambioMasivo.append("cve_id_umf_cod_pos_destino, cve_id_tramite,cve_id_umf_cons_turno_med_d, cve_id_umf_cons_turno_med_o) ");
				bitacoraCambioMasivo.append("WITH datos AS( ");
				bitacoraCambioMasivo.append("select distinct gru.CVE_ID_UMF_CONS_TURNO_MED ");
				bitacoraCambioMasivo.append(busquedaIntegrantesBase.toString());
				bitacoraCambioMasivo.append("and contur.CVE_ID_TURNO = 1 )");
				bitacoraCambioMasivo.append(busquedaMasivo.toString());
				bitacoraCambioMasivo.append(" ,"+medicoEnTurno.getIdMedicoContultorioTurno().intValue());
				bitacoraCambioMasivo.append(" ,datos.CVE_ID_UMF_CONS_TURNO_MED from datos");
				
						
				
				StringBuilder actualizaIntegrantes1 = new StringBuilder();
				actualizaIntegrantes1.append(" Update DIT_GRUPO_FAMILIAR grupo set grupo.CVE_ID_UMF_CONS_TURNO_MED="+ medicoEnTurno.getIdMedicoContultorioTurno().intValue() );
				actualizaIntegrantes1.append(" ,grupo.FEC_REGISTRO_ACTUALIZADO = sysdate, grupo.FEC_CAMBIO_TURNO_CONSULTORIO = null where grupo.CVE_ID_PERSONA_INTEGRANTE in ( ");
				actualizaIntegrantes1.append(" select gru.CVE_ID_PERSONA_INTEGRANTE");
				actualizaIntegrantes1.append(busquedaIntegrantesBase.toString());
				actualizaIntegrantes1.append(" and contur.CVE_ID_TURNO = 1)");
				
				
				
				
				
				// -----------------
				// VESPERTINO
				// -----------------
				StringBuilder bitacoraCambioMasivo2 = new StringBuilder();
				bitacoraCambioMasivo2.append("INSERT INTO dit_cambio_masivo_clinica (cve_id_cambio_masivo_clinica, cve_id_umf_cod_pos_origen, ");
				bitacoraCambioMasivo2.append("cve_id_umf_cod_pos_destino, cve_id_tramite,cve_id_umf_cons_turno_med_d, cve_id_umf_cons_turno_med_o) ");
				bitacoraCambioMasivo2.append("WITH datos AS( ");
				bitacoraCambioMasivo2.append("select distinct gru.CVE_ID_UMF_CONS_TURNO_MED ");
				bitacoraCambioMasivo2.append(busquedaIntegrantesBase.toString());
				bitacoraCambioMasivo2.append("and contur.CVE_ID_TURNO = 2 )");
				bitacoraCambioMasivo2.append(busquedaMasivo.toString());
				bitacoraCambioMasivo2.append(" ,"+medicoEnTurnoV.getIdMedicoContultorioTurno().intValue());
				bitacoraCambioMasivo2.append(" ,datos.CVE_ID_UMF_CONS_TURNO_MED from datos");
				
						
				
				StringBuilder actualizaIntegrantes2 = new StringBuilder();
				actualizaIntegrantes2.append(" Update DIT_GRUPO_FAMILIAR grupo set grupo.CVE_ID_UMF_CONS_TURNO_MED="+ medicoEnTurnoV.getIdMedicoContultorioTurno().intValue() );
				actualizaIntegrantes2.append(" ,grupo.FEC_REGISTRO_ACTUALIZADO = sysdate, grupo.FEC_CAMBIO_TURNO_CONSULTORIO = null where grupo.CVE_ID_PERSONA_INTEGRANTE in ( ");
				actualizaIntegrantes2.append(" select gru.CVE_ID_PERSONA_INTEGRANTE");
				actualizaIntegrantes2.append(busquedaIntegrantesBase.toString());
				actualizaIntegrantes2.append(" and contur.CVE_ID_TURNO = 2)");
				
				
				
				try{
				
					Session session = this.getSession();
					
					session.createSQLQuery(bitacoraCambioMasivo.toString()).executeUpdate();
					numColumnasAfectadas= new Integer(session.createSQLQuery(actualizaIntegrantes1.toString()).executeUpdate()).longValue();
				
					session.createSQLQuery(bitacoraCambioMasivo2.toString()).executeUpdate();
					numColumnasAfectadas+= new Integer(session.createSQLQuery(actualizaIntegrantes2.toString()).executeUpdate()).longValue();
				
				}catch(Exception e){
					log.error("updateDerechohabiente1", e);
				}
				
				
		} catch (Exception e) {
			log.error("updateDerechohabiente", e);
			throw e;
		}
		
		return numColumnasAfectadas;
	}
	

	
	
	
	
}
