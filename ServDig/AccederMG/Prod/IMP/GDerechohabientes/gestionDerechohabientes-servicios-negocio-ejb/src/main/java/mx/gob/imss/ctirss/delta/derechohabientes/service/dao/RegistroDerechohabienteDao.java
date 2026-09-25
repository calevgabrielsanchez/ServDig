package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.DerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.GrupoFamiliarParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PersonaDomicilioParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PersonaInteresadaSolParserLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.RegistroParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.RazonRegistroParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 13/05/2012
 */
@Stateless(name = "registroDerechohabienteDao", mappedName = "registroDerechohabienteDao")
public class RegistroDerechohabienteDao extends AbstractServiceEntity implements RegistroDerechohabientesDaoLocal{
	
	private static final Logger logger = Logger.getLogger(RegistroDerechohabienteDao.class);
	@EJB GrupoFamiliarParserServiceLocal grupoFamiliarParserServiceLocal;
	@EJB PersonaDomicilioParserServiceLocal personaDomicilioParserServiceLocal;
	@EJB RegistroParserServiceLocal registroParserServiceLocal;
	@EJB DerechohabienteParserServiceLocal derechohabienteParserServiceLocal;
	
	@EJB
	private transient PersonaInteresadaSolParserLocal personaInteresadaSolParser;


	@Override
	public Boolean saveRegistroDerechohabiente(
			TramiteRegistroDerechohabiente miRegistroDerechohabiente) throws DerechohabientesBusinessException,Exception {
		
		List<DitRegistroDerechohabiente> ditRegistros = null;
		
		Criteria query = this.getSession().createCriteria(DitRegistroDerechohabiente.class);
		query.createAlias("ditTramite", "tramite");
		query.add(Restrictions.eq("tramite.cveIdTramite", miRegistroDerechohabiente.getTramiteId()));
		ditRegistros = query.list();
		
		if(ditRegistros != null && !ditRegistros.isEmpty()) {
			
			for(DitRegistroDerechohabiente ditr: ditRegistros) {
				DitRegistroDerechohabiente ditAc = registroParserServiceLocal.modelToPersist(miRegistroDerechohabiente);
				ditAc.setCveIdRegDerechohabiente(ditr.getCveIdRegDerechohabiente());
				ditAc.setFecRegistroActualizado(new Date());
				
				this.em.merge(ditAc);
			}
			
			return true;
		} else {
			DitRegistroDerechohabiente unDitRegistro = registroParserServiceLocal.modelToPersist(miRegistroDerechohabiente);
			this.em.persist(unDitRegistro);
			return false;
		}
		

			
	}
	
	@Override
	public void actualizaRegistroDerechohabiente(
			TramiteRegistroDerechohabiente miRegistroDerechohabiente) throws DerechohabientesBusinessException,Exception {
		DitRegistroDerechohabiente unDitRegistro = new DitRegistroDerechohabiente();
		unDitRegistro = registroParserServiceLocal.modelToPersist(miRegistroDerechohabiente);
		try {
			em.merge(unDitRegistro);
			em.flush();
		} catch (Exception e) {
			logger.error("Error - actualizaRegistroDerechohabiente", e);
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Derechohabiente saveDerechohabiente(Derechohabiente miDerechohabiente) throws DerechohabientesBusinessException,Exception{
		
		Criteria criteria = this.getSession().createCriteria(DitPersonaDerechohabiente.class);
		DitPersonaDerechohabiente unDitDerechohabiente = new DitPersonaDerechohabiente();
		try {
			criteria.createAlias("ditPersona", "persona");
			criteria.add(Restrictions.eq("persona.cveIdPersona", miDerechohabiente.getIdPersona()));
			criteria.add(Restrictions.eq("indRegActivo", 1));
			
			List<DitPersonaDerechohabiente> derechohabientes = criteria.list();
			
			/*if(!derechohabientes.isEmpty()) {
				DitPersonaDerechohabiente ditPd = derechohabientes.get(0);
				miDerechohabiente.setIdPersonaDerechohabiente(ditPd.getCveIdPerDerechohabiente());
				ditPd.setFecRegistroActualizado(new Date());
				
				// ---------------------------------------------------------------------------------
				// Actualizamos el identificador de expediente electr�nico
				// ---------------------------------------------------------------------------------
				if( miDerechohabiente.getExpedienteElectronico() != null && !miDerechohabiente.getExpedienteElectronico().isEmpty() )
					ditPd.setCveExpedienteElectronico(miDerechohabiente.getExpedienteElectronico());
				
			} else */
			//solo si no se encuentra idee se insertara uno
			if(derechohabientes == null || derechohabientes.isEmpty()){
				miDerechohabiente.setFechaRegistroAlta(new Date());
				unDitDerechohabiente = derechohabienteParserServiceLocal.modelToPersist(miDerechohabiente);
				unDitDerechohabiente.setIndRegActivo(1);
				em.merge(unDitDerechohabiente);
				em.flush();
				
				//seteamos el id que nos da cuando guardamos
				miDerechohabiente.setIdPersonaDerechohabiente(unDitDerechohabiente.getCveIdPerDerechohabiente());
				
			}
			
		} catch (Exception e) {
			logger.error("Error - saveDerechohabiente", e);
			throw e;
		}	
		
		return miDerechohabiente;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public PersonaDomicilio savePersonaDomicilio(PersonaDomicilio miPersonaDomicilio) throws DerechohabientesBusinessException,Exception {
		DitPersonafDom unDitPersonafDom = null;
		try {
			if(miPersonaDomicilio.getCvePersonaDomicilio() != null) {
				try {
					unDitPersonafDom = this.em.find(DitPersonafDom.class, miPersonaDomicilio.getCvePersonaDomicilio());
				}catch(NoResultException e) {
					log.debug("No se encontro la relacion de persona domicilio");
				}
			}
			
			if(unDitPersonafDom == null) {
				Criteria query = this.getSession().createCriteria(DitPersonafDom.class);
				query.createAlias("ditPersona", "persona");
				query.add(Restrictions.eq("persona.cveIdPersona", miPersonaDomicilio.getPersona().getIdPersona()));
				query.createAlias("dicTipoDomicilio", "tipoDom");
				query.add(Restrictions.eq("tipoDom.cveIdTipoDomicilio", miPersonaDomicilio.getTipoDomicilio().getClave().longValue()));
				
				List<DitPersonafDom> personasFDom = query.list();
				if(personasFDom != null && !personasFDom.isEmpty()) {
					unDitPersonafDom = personasFDom.get(0);
				}
			}
			
			if(unDitPersonafDom == null) {
				miPersonaDomicilio.setFechaRegistroAlta(new Date());
				unDitPersonafDom = personaDomicilioParserServiceLocal.modelToPersist(miPersonaDomicilio);
				em.persist(unDitPersonafDom);
			} else {
				unDitPersonafDom.setFecRegistroActualizado(new Date());
				unDitPersonafDom.setDgDomicilioGeografico(new DgDomicilioGeografico());
				unDitPersonafDom.getDgDomicilioGeografico().setDomicilioId(miPersonaDomicilio.getDomicilio().getClave().longValue());
				em.merge(unDitPersonafDom);
			}
			
			miPersonaDomicilio.setCvePersonaDomicilio(unDitPersonafDom.getCveIdPersonafDom());
		} catch (Exception e) {
			logger.error("Error - savePersonaDomicilio", e);
			throw e;
		}
		
		return miPersonaDomicilio;
	}

	@Override
	public void savePersonaInteresada(
			PersonaInteresadaSolicitud miPersonaInteresada) throws DerechohabientesBusinessException,Exception {
		DitPersonaInteresadaSol miDitPersonaIntSol = new DitPersonaInteresadaSol();
		miDitPersonaIntSol = personaInteresadaSolParser.modelToPersist(miPersonaInteresada);
		try {
			em.persist(miDitPersonaIntSol);
		} catch (Exception e) {
			logger.error("Error - savePersonaInteresada", e);
			throw e;
		}
		
	}
	
	@Override
	public void savePersonaContacto(MedioContacto medioContacto, long idPersona) throws Exception {
		DitPersonafContacto miDitPersonafContacto = new DitPersonafContacto();
		DitPersona miDitPersona = new DitPersona();
		try {
			miDitPersona.setCveIdPersona(idPersona);
			DitFormaContacto miDitFormaContacto = new DitFormaContacto();
			miDitFormaContacto.setCveIdFormaContacto(medioContacto.getClave());
			
			miDitPersonafContacto.setDitPersona(miDitPersona);
			miDitPersonafContacto.setDitFormaContacto(miDitFormaContacto);
			miDitPersonafContacto.setFecRegistroAlta(new Date());
			
			em.persist(miDitPersonafContacto);
			em.flush();
		} catch (Exception e) {
			logger.error("Error - savePersonaContacto", e);
			throw e;
		}
		
	}

	@Override
	public GrupoFamiliar saveGrupoFamiliar(GrupoFamiliar miGrupoFamiliar) throws DerechohabientesBusinessException,Exception {
		try {
			DitGrupoFamiliar unGrupoFamiliar = new DitGrupoFamiliar();
			unGrupoFamiliar = grupoFamiliarParserServiceLocal.modelToPersist(miGrupoFamiliar);
			em.persist(unGrupoFamiliar);
			em.flush();
		} catch (Exception e) {
			logger.error("Error - saveGrupoFamiliar", e);
			throw e;
		}	
		
		return miGrupoFamiliar;
	}
	
	@Override
	public void actualizaGrupoFamiliar(GrupoFamiliar miGrupoFamiliar) throws DerechohabientesBusinessException,Exception{
		DitGrupoFamiliar unGrupoFamiliar = new DitGrupoFamiliar();
		try {
			unGrupoFamiliar = grupoFamiliarParserServiceLocal.modelToPersist(miGrupoFamiliar);
			em.merge(unGrupoFamiliar);
			em.flush();
		} catch (Exception e) {
			logger.error("Error - actualizaGrupoFamiliar", e);
			throw e;
		}
		
	}
	
	@Override
	public TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException,Exception {
		TramiteRegistroDerechohabiente registroD = null;
		try {
			Criteria query = this.getSession().createCriteria(DitRegistroDerechohabiente.class);
			query.createAlias("ditTramite","tram");
			query.add(Restrictions.eq("tram.cveIdTramite", idTramite));
			DitRegistroDerechohabiente ditRegistroD =  (DitRegistroDerechohabiente) query.uniqueResult();
			registroD = registroParserServiceLocal.persisToModel(ditRegistroD);	
		} catch (Exception e) {
			logger.error("Error - getRegistroDerechohabiente", e);
			throw e;
		}
			
		return registroD;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public boolean existeRegistroDerechohabiente(Long idTramite)
			throws DerechohabientesBusinessException, Exception {
		
		Long numeroRegistros = 0L;
		Criteria query = this.getSession().createCriteria(DitRegistroDerechohabiente.class);
		query.setProjection(Projections.rowCount());
		query.createAlias("ditTramite","tram");
		query.add(Restrictions.eq("tram.cveIdTramite", idTramite));
		
		try{
			numeroRegistros = (Long) query.uniqueResult();
		}catch(Exception e) {
			log.error("No se pudo consultar la calidad mas alta",e);
		}
		
		if(!numeroRegistros.equals(0L)) {
			return true;
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonaDomicilio getPersonaDom(Long idPersona, Long tipoDomicilio) throws DerechohabientesBusinessException,Exception {
		PersonaDomicilio unaPersonaDom = new PersonaDomicilio();
		List<DitPersonafDom> unDitPersonafDom = new ArrayList<DitPersonafDom>();
		try {
			Query query = em.createNamedQuery("getPersonafDom");
			query.setParameter("idPersona", idPersona);
			query.setParameter("tipoDomicilio", tipoDomicilio);		
			unDitPersonafDom = query.getResultList();
			if(unDitPersonafDom.size() > 0){
				unaPersonaDom = personaDomicilioParserServiceLocal.persistToModel(unDitPersonafDom.get(0));
			}
		} catch (Exception e) {
			logger.error("Error - getPersonaDom", e);
			throw e;
		}
			
		return unaPersonaDom;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findModalidadParentesco(long idModalidad, String modalidades) throws DerechohabientesBusinessException,Exception{
		List<BigDecimal> modal = new ArrayList<BigDecimal>();
		boolean validado = false;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("SELECT CVE_ID_MODALIDAD FROM DIC_MODALIDAD WHERE NUM_MODALIDAD IN(");
			sb.append(" "+modalidades+")");
			Session session = em.unwrap(Session.class);
			SQLQuery q = session.createSQLQuery(sb.toString());
			modal =  q.list();
			for(BigDecimal m : modal){
				if(Long.parseLong(m.toString()) == idModalidad){
					validado = true;
				}
			}
		} catch (Exception e) {
			logger.error("getPersonaDom", e);
			throw e;
		}
		
		return validado;
	}
	
	/**
	 * Metodo encargado de actualizar la fecha de baja del registro en grupo familiar a null
	 * @param cveIdAsignacionNSS
	 * @param cveIdPersonaIntegrante
	 * @throws DerechohabientesBusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actualizaFechaBajaGrupoFamiliartoNull(long cveIdAsignacionNSS, long cveIdPersonaIntegrante) throws DerechohabientesBusinessException {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("UPDATE DIT_GRUPO_FAMILIAR GF SET GF.FEC_REGISTRO_BAJA = NULL, GF.FEC_REGISTRO_ACTUALIZADO = SYSDATE ");
					sb.append("WHERE GF.CVE_ID_ASIGNACION_NSS = " + cveIdAsignacionNSS +" ");
					sb.append("AND GF.CVE_ID_PERSONA_INTEGRANTE = " +cveIdPersonaIntegrante+" ");
			log.debug(" ************ EL QUERY A EJECUTAR PARA UPDATE ES ******** [" + sb.toString() +"]" );		
			Session session = em.unwrap(Session.class);
			SQLQuery q = session.createSQLQuery(sb.toString());
			q.executeUpdate();
			em.flush();
			
		} catch (Exception e) {
			logger.error("getPersonaDom", e);
			throw new DerechohabientesBusinessException("Error al actualizar la fecha de baja en el Grupo Familiar [" + e.getMessage() + "]" );
		}
		
		
	}

	public List<RazonRegistro> findRazonRegistro() {
		List<RazonRegistro> razones = null;
		
		Criteria query = this.getSession().createCriteria(DicRazonRegistro.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicRazonRegistro> dicRazones = query.list();
		
		razones = RazonRegistroParser.persisToModelList(dicRazones);
		
		return razones;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteRegistro> findRegistroConyugeConcubinario(String fechaInicio, String fechaFin, Long cveDelegacion, Long cveSubdelegacion) throws Exception{
		//List<BigDecimal> modal = new ArrayList<BigDecimal>();
		boolean validado = false;
		List<ReporteRegistro> resultado = new ArrayList<ReporteRegistro>();
		List<Object[]> resultadoo;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("select ANSS.NUM_NSS, PA.NOM_NOMBRE NOMBRE_ASEGURADO, PA.NOM_PRIMER_APELLIDO APELLIDO_PATERNO_ASEGURADO, PA.NOM_SEGUNDO_APELLIDO APELLIDO_MATERNO_ASEGURADO, PA.CURP CURP_ASEGURADO, ");
			sb.append("CASE PA.CVE_ID_SEXO WHEN 1 THEN 'HOMBRE' ELSE 'MUJER' END SEXO_ASEGURADO,");
			sb.append("DGA.NOMVIAL || ' ' || CASE WHEN DGA.NUMEXTNUM IS NOT NULL THEN TO_CHAR( DGA.NUMEXTNUM ) ELSE '' END || CASE WHEN DGA.NUMEXTALF IS NOT NULL THEN DGA.NUMEXTALF ELSE '' END || CASE WHEN DGA.NUMINTNUM IS NOT NULL THEN TO_CHAR( DGA.NUMINTNUM ) ELSE '' END || ' - ' ||");
			sb.append("CASE WHEN DGA.NUMINTALF IS NOT NULL THEN DGA.NUMINTALF ELSE '' END || ' ' || ASNA.NOM_ASEN || ', ' || MPIOA.NOM_MUN || ', ' || EDOA.NOM_ENT || ' C.P. ' || DGA.CODIGO DOMICILIO_ASEGURADO_MOMENTO,");
			sb.append("DGAA.NOMVIAL || ' ' || CASE WHEN DGAA.NUMEXTNUM IS NOT NULL THEN TO_CHAR( DGAA.NUMEXTNUM ) ELSE '' END || CASE WHEN DGAA.NUMEXTALF IS NOT NULL THEN DGAA.NUMEXTALF ELSE '' END || CASE WHEN DGAA.NUMINTNUM IS NOT NULL THEN TO_CHAR( DGAA.NUMINTNUM ) ELSE '' END || ' - ' ||");
			sb.append("CASE WHEN DGAA.NUMINTALF IS NOT NULL THEN DGAA.NUMINTALF ELSE '' END || ' ' || ASNAA.NOM_ASEN || ', ' || MPIOAA.NOM_MUN || ', ' || EDOAA.NOM_ENT || ' C.P. ' || DGAA.CODIGO  DOMICILIO_ASEGURADO_ACTUAL,");
			sb.append("PB.NOM_NOMBRE NOMBRE_BENEFICIARIO, PB.NOM_PRIMER_APELLIDO APELLIDO_PATERNO_BENEFICIARIO, PB.NOM_SEGUNDO_APELLIDO APELLIDO_MATERNO_BENEFICIARIO, PB.CURP CURP_BENEFICIARIO, ");
			sb.append("CASE PB.CVE_ID_SEXO WHEN 1 THEN 'HOMBRE' ELSE 'MUJER' END SEXO_BENEFICIARIO, ");
			sb.append("DGB.NOMVIAL || ' ' || CASE WHEN DGB.NUMEXTNUM IS NOT NULL THEN TO_CHAR( DGB.NUMEXTNUM ) ELSE '' END || CASE WHEN DGB.NUMEXTALF IS NOT NULL THEN DGB.NUMEXTALF ELSE '' END || CASE WHEN DGB.NUMINTNUM IS NOT NULL THEN TO_CHAR( DGB.NUMINTNUM ) ELSE '' END || ' - ' ||");
			sb.append("CASE WHEN DGB.NUMINTALF IS NOT NULL THEN DGB.NUMINTALF ELSE '' END || ' ' || ASNB.NOM_ASEN || ', ' || MPIOB.NOM_MUN || ', ' || EDOB.NOM_ENT || ' C.P. ' || DGB.CODIGO DOMICILIO_BENEFICIARIO_MOMENTO,");
			sb.append("DGBA.NOMVIAL || ' ' || CASE WHEN DGBA.NUMEXTNUM IS NOT NULL THEN TO_CHAR( DGBA.NUMEXTNUM ) ELSE '' END || CASE WHEN DGBA.NUMEXTALF IS NOT NULL THEN DGBA.NUMEXTALF ELSE '' END || CASE WHEN DGBA.NUMINTNUM IS NOT NULL THEN TO_CHAR( DGBA.NUMINTNUM ) ELSE '' END || ' - ' ||");
			sb.append("CASE WHEN DGBA.NUMINTALF IS NOT NULL THEN DGBA.NUMINTALF ELSE '' END || ' ' || ASNBA.NOM_ASEN || ', ' || MPIOBA.NOM_MUN || ', ' || EDOBA.NOM_ENT || ' C.P. ' || DGBA.CODIGO  DOMICILIO_BENEFICIARIO_ACTUAL,");
			sb.append("TO_CHAR(DECODE(RD.FEC_REGISTRO_ALTA, NULL, RD.FEC_REGISTRO_ALTA, RD.FEC_REGISTRO_ALTA), 'dd-mm-yyyy') FECHA_TRAMITE, TT.CVE_ID_TIPO_TRAMITE ID_TIPO_TRAMITE, DM.CVE_ID_DELEGACION CLAVE_DEL_MOMENTO, DM.DES_DELEG DES_DEL_MOMENTO, SDM.CVE_ID_SUBDELEGACION CLAVE_SUB_MOMENTO, SDM.DES_SUBDELEGACION DES_SUB_MOMENTO,");
			sb.append("DA.CVE_ID_DELEGACION CLAVE_DEL_ACTUAL, DA.DES_DELEG DES_DEL_ACTUAL,SDA.CVE_ID_SUBDELEGACION CLAVE_SUB_ACTUAL, SDA.DES_SUBDELEGACION DES_SUB_ACTUAL,");
			sb.append("UM.CVE_ID_UMF CVE_UMF_MOMENTO, UM.NOM_CORTO DES_UMF_MOMENTO, CU.CVE_ID_UMF CVE_UMF_ACTUAL, UA.NOM_CORTO DES_UMF_ACTUAL, TT.DES_TIPO_TRAMITE TIPO_TRAMITE,");
			sb.append("EAS.DES_ESTADO_DERECHOHABIENTE VIGENCIA_ASEG_MOMENTO, EB.DES_ESTADO_DERECHOHABIENTE VIGENCIA_BENEF_MOMENTO,");
			sb.append("RD.IND_CONYUGE_MISMO_SEXO, RD.IND_CONCUBINARIO_MISMO_SEXO, SOL.CVE_ID_USUARIO CUENTA_USUARIO, OS.DES_ORIGEN_SOLICITUD ORIGEN_TRAMITE,");
			sb.append("'NUMERO DE ACTA = ' ||  A.NUM_ACTA || ' | NUMERO DE FOJA = ' || A.NUM_FOJA || ' | NUMERO DE LIBRO = ' || A.NUM_LIBRO || ' | ENTIDAD FEDERATIVA = ' || A.CVE_ENT || ' | MUNICIPIO = ' || A.CVE_MUN || ' | FECHA DE SUCESO = ' || TO_CHAR(DECODE(A.FEC_SUCESO, NULL, A.FEC_SUCESO, A.FEC_SUCESO), 'dd-mm-yyyy') || ' | TOMO = ' || A.REF_NUM_TOMO || ' | NUMERO DE JUZGADO U OFICIALIA = ' || A.NUM_JUZGADO DOCUMENTOS_PROBATORIOS ");
			sb.append("FROM MGPBDTU9X.DIT_REGISTRO_DERECHOHABIENTE RD ");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_TRAMITE TRAM     ON RD.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_SOLICITUD SOL ON TRAM.CVE_ID_SOLICITUD = SOL.CVE_ID_SOLICITUD");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_PERSONA_INTERESADA_SOL PIS ON SOL.CVE_ID_SOLICITUD = PIS.CVE_ID_SOLICITUD");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_TRAMITE_PERSONA_FISICA DTPF ON RD.CVE_ID_TRAMITE = DTPF.CVE_ID_TRAMITE");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_PERSONA PA ON PIS.CVE_ID_PERSONA = PA.CVE_ID_PERSONA");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_ASIGNACION_NSS ANSS ON PIS.CVE_ID_PERSONA = ANSS.CVE_ID_PERSONA");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_GRUPO_FAMILIAR GF ON ANSS.CVE_ID_ASIGNACION_NSS = GF.CVE_ID_ASIGNACION_NSS AND ANSS.CVE_ID_PERSONA = GF.CVE_ID_PERSONA_INTEGRANTE");
			sb.append("			LEFT JOIN MGPBDTU9X.DIT_PERSONAF_DOM PDA ON PA.CVE_ID_PERSONA = PDA.CVE_ID_PERSONA AND PDA.CVE_ID_PERSONAF_DOM = GF.CVE_ID_PERSONAF_DOM");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_DOMICILIO_GEOGRAFICO DGAA ON PDA.DOMICILIO_ID = DGAA.DOMICILIO_ID");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_ESTADO EDOAA ON DGAA.CVE_ENT = EDOAA.CVE_ENT");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_MUNICIPIO MPIOAA ON DGAA.CVE_ENT = MPIOAA.CVE_ENT AND DGAA.CVE_MUN = MPIOAA.CVE_MUN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_ASENTAMIENTO ASNAA ON DGAA.CVE_ENT = ASNAA.CVE_ENT AND DGAA.CVE_MUN = ASNAA.CVE_MUN AND DGAA.CVE_ASEN = ASNAA.CVE_ASEN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_DOMICILIO_GEOGRAFICO DGA ON RD.DOMICILIO_ID_ASEGURADO = DGA.DOMICILIO_ID");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_ESTADO EDOA ON DGA.CVE_ENT = EDOA.CVE_ENT");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_MUNICIPIO MPIOA ON DGA.CVE_ENT = MPIOA.CVE_ENT AND DGA.CVE_MUN = MPIOA.CVE_MUN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_ASENTAMIENTO ASNA ON DGA.CVE_ENT = ASNA.CVE_ENT AND DGA.CVE_MUN = ASNA.CVE_MUN AND DGA.CVE_ASEN = ASNA.CVE_ASEN");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_PERSONA PB ON DTPF.CVE_ID_PERSONA = PB.CVE_ID_PERSONA");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_GRUPO_FAMILIAR GFB ON ANSS.CVE_ID_ASIGNACION_NSS = GFB.CVE_ID_ASIGNACION_NSS AND PB.CVE_ID_PERSONA = GFB.CVE_ID_PERSONA_INTEGRANTE");
			sb.append("			LEFT JOIN MGPBDTU9X.DIT_PERSONAF_DOM PDB ON PB.CVE_ID_PERSONA = PDB.CVE_ID_PERSONA AND PDB.CVE_ID_PERSONAF_DOM = GFB.CVE_ID_PERSONAF_DOM");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_DOMICILIO_GEOGRAFICO DGBA ON PDB.DOMICILIO_ID = DGBA.DOMICILIO_ID");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_ESTADO EDOBA ON DGBA.CVE_ENT = EDOBA.CVE_ENT");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_MUNICIPIO MPIOBA ON DGBA.CVE_ENT = MPIOBA.CVE_ENT AND DGBA.CVE_MUN = MPIOBA.CVE_MUN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_ASENTAMIENTO ASNBA ON DGBA.CVE_ENT = ASNBA.CVE_ENT AND DGBA.CVE_MUN = ASNBA.CVE_MUN AND DGBA.CVE_ASEN = ASNBA.CVE_ASEN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_DOMICILIO_GEOGRAFICO DGB ON RD.DOMICILIO_ID_BENEFICIARIO = DGB.DOMICILIO_ID");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_ESTADO EDOB ON DGB.CVE_ENT = EDOB.CVE_ENT");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_CAT_MUNICIPIO MPIOB ON DGB.CVE_ENT = MPIOB.CVE_ENT AND DGB.CVE_MUN = MPIOB.CVE_MUN");
			sb.append("			LEFT JOIN MGPBDTU9X.DG_ASENTAMIENTO ASNB ON DGB.CVE_ENT = ASNB.CVE_ENT AND DGB.CVE_MUN = ASNB.CVE_MUN AND DGB.CVE_ASEN = ASNB.CVE_ASEN");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_TIPO_TRAMITE TT ON TRAM.CVE_ID_TIPO_TRAMITE = TT.CVE_ID_TIPO_TRAMITE");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_DELEGACION DM     ON RD.CVE_ID_DELEGACION = DM.CVE_ID_DELEGACION");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_SUBDELEGACION SDM ON RD.CVE_ID_DELEGACION = SDM.CVE_ID_DELEGACION AND RD.CVE_ID_SUBDELEGACION = SDM.CVE_ID_SUBDELEGACION");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_UMF UM            ON RD.CVE_ID_UMF= UM.CVE_ID_UMF");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_UMF_CONS_TURNO_MEDICO UCTM ON GF.CVE_ID_UMF_CONS_TURNO_MED = UCTM.CVE_ID_UMF_CONS_TURNO_MED");
			sb.append("			INNER JOIN MGPBDTU9X.DIT_UMF_CONSULTORIO_TURNO CT ON UCTM.CVE_ID_UMF_CONS_TURNO = CT.CVE_ID_UMF_CONS_TURNO");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_CONSULTORIO_UMF CU ON CT.CVE_ID_UMF_CONSULTORIO = CU.CVE_ID_UMF_CONSULTORIO");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_UMF UA ON CU.CVE_ID_UMF = UA.CVE_ID_UMF");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_SUBDELEGACION SDA ON UA.CVE_ID_SUBDELEGACION = SDA.CVE_ID_SUBDELEGACION");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_DELEGACION DA ON SDA.CVE_ID_DELEGACION = DA.CVE_ID_DELEGACION");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_ESTADO_DERECHOHABIENTE EAS ON RD.CVE_ESTADO_ASEGURADO = EAS.CVE_ESTADO_DERECHOHABIENTE");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_ESTADO_DERECHOHABIENTE EB ON RD.CVE_ESTADO_BENEFICIARIO = EB.CVE_ESTADO_DERECHOHABIENTE");
			sb.append("			INNER JOIN MGPBDTU9X.DIC_ORIGEN_SOLICITUD OS ON SOL.CVE_ID_ORIGEN_SOLICITUD=OS.CVE_ID_ORIGEN_SOLICITUD");
			sb.append("			LEFT JOIN MGPBDTU9X.DIT_DOCUMENTACION_TRAMITE DT ON RD.CVE_ID_TRAMITE = DT.CVE_ID_TRAMITE");
			sb.append("			LEFT JOIN MGPBDTU9X.DIT_ACTA A ON A.CVE_ID_DOCUMENTO_PROBATORIO = DT.CVE_ID_DOCUMENTO_PROBATORIO");
			sb.append("  where TRAM.CVE_ID_TIPO_TRAMITE    IN (46,47) ");
			sb.append("  AND RD.FEC_REGISTRO_ALTA >= TRUNC(TO_DATE('" + fechaInicio + "','dd/MM/yyyy')) AND RD.FEC_REGISTRO_ALTA <= TRUNC(TO_DATE('" + fechaFin + "','dd/MM/yyyy')) + 1");
			if(cveDelegacion != 0) {
				sb.append("  AND RD.CVE_ID_DELEGACION = " + cveDelegacion );
				if(cveSubdelegacion !=0 ){
					sb.append("  AND RD.CVE_ID_SUBDELEGACION = " + cveSubdelegacion );
				}
			}
			Session session = em.unwrap(Session.class);
			SQLQuery q = session.createSQLQuery(sb.toString());
			resultadoo =  (List<Object[]>)q.list();
			if(!resultadoo.isEmpty()) {
				for(Object[] reg : resultadoo) {
					ReporteRegistro rr = new ReporteRegistro();
					rr.setNUM_NSS((String) reg[0]);
					rr.setNOMBRE_ASEGURADO((String) reg[1]);
					rr.setAPELLIDO_PATERNO_ASEGURADO((String) reg[2]);
					rr.setAPELLIDO_MATERNO_ASEGURADO((String) reg[3]);
					rr.setCURP_ASEGURADO((String) reg[4]);
					rr.setSEXO_ASEGURADO((String) reg[5]);
					rr.setDOMICILIO_ASEGURADO_MOMENTO((String) reg[6]);
					rr.setDOMICILIO_ASEGURADO_ACTUAL((String) reg[7]);
					rr.setNOMBRE_BENEFICIARIO((String) reg[8]);
					rr.setAPELLIDO_PATERNO_BENEFICIARIO((String) reg[9]);
					rr.setAPELLIDO_MATERNO_BENEFICIARIO((String) reg[10]);
					rr.setCURP_BENEFICIARIO((String) reg[11]);
					rr.setSEXO_BENEFICIARIO((String) reg[12]);
					rr.setDOMICILIO_BENEFICIARIO_MOMENTO((String) reg[13]);
					rr.setDOMICILIO_BENEFICIARIO_ACTUAL((String) reg[14]);
					rr.setFECHA_TRAMITE((String) reg[15]);
					rr.setID_TIPO_TRAMITE(((BigDecimal) reg[16]).toString());
					rr.setCLAVE_DEL_MOMENTO(((BigDecimal) reg[17]).toString());
					rr.setDES_DEL_MOMENTO((String) reg[18]);
					rr.setCLAVE_DEL_ACTUAL(((BigDecimal) reg[21]).toString());
					rr.setDES_DEL_ACTUAL((String) reg[22]);
					rr.setCLAVE_SUB_MOMENTO(((BigDecimal) reg[19]).toString());
					rr.setDES_SUB_MOMENTO((String) reg[20]);
					rr.setCLAVE_SUB_ACTUAL(((BigDecimal) reg[23]).toString());
					rr.setDES_SUB_ACTUAL((String) reg[24]);
					rr.setCVE_UMF_MOMENTO(((BigDecimal) reg[25]).toString());
					rr.setDES_UMF_MOMENTO((String) reg[26]);
					rr.setCVE_UMF_ACTUAL(((BigDecimal) reg[27]).toString());
					rr.setDES_UMF_ACTUAL((String) reg[28]);
					rr.setTIPO_TRAMITE((String) reg[29]);
					rr.setVIGENCIA_ASEG_MOMENTO((String) reg[30]);
					rr.setVIGENCIA_BENEF_MOMENTO((String) reg[31]);
					if (reg[32] != null ) {
						rr.setIND_CONYUGE_MISMO_SEXO(((Character) reg[32]).toString());
					} else {
						rr.setIND_CONYUGE_MISMO_SEXO("");
					}
					if (reg[33] != null ) {
						rr.setIND_CONCUBINARIO_MISMO_SEXO(((Character) reg[33]).toString());
					} else {
						rr.setIND_CONCUBINARIO_MISMO_SEXO("");
					}
					rr.setCUENTA_USUARIO((String) reg[34]);
					rr.setORIGEN_TRAMITE((String) reg[35]);
					rr.setDOCUMENTOS_PROBATORIOS((String) reg[36]);
					resultado.add(rr);
				}
/*				Long idAsignacion = ((BigDecimal)nss[0]).longValue();
				String numNSS = (String) nss[1];
				Long idPersonaIntegrante = ((BigDecimal)nss[2]).longValue();
				String nombre = (String) nss[3];
				String primerApellido = (String) nss[4];
				String segundoApellido = (String) nss[5];
				Date fechaNacimiento = (Date) nss[6];
				Integer anioNacimiento = nss[7] != null ? ((BigDecimal)nss[7]).intValue() : null;
				Integer mesNacimiento =	nss[8] != null ? ((BigDecimal)nss[8]).intValue() : null;
				String curp = (String)nss[9];
				Integer idSexo = nss[10] != null ? ((BigDecimal)nss[10]).intValue() : null;
				String deSexo = (String) nss[11];
*/	
			}

		} catch (Exception e) {
			logger.error("getPersonaDom", e);
			throw e;
		}
		
		return resultado;
	}

}
