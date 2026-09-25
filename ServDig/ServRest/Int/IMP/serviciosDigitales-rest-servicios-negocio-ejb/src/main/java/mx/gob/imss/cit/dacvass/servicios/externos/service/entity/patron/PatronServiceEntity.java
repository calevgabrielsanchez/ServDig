	package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.patron;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActividadEcononica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosEmpresaPermisoConvid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosSatDetallePatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DetallePatronClasifMovPatQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.MovimientoRegistroPatronal;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.NumTrabajadoresVigentes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronPlataformaResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronListaBlancaResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

@Local(value = IPatronServiceEntityLocal.class)
@Stateless
public class PatronServiceEntity extends PersistenceUnitSISCOBServiceEntity implements IPatronServiceEntityLocal {

	private static final Logger log = LoggerFactory.getLogger(PatronServiceEntity.class);
	private static final Long TIPO_CAUSA_BAJA_251 = 7L;
	private static final Long TIPO_MOVIMIENTO_BAJA = 2L;

	@SuppressWarnings("unchecked")
	@Override
	public List<MovimientoRegistroPatronal> consultaPatronBaja(List<String> lstRegPatronales, boolean indBaja251) throws Exception {

		log.debug("llegando a la consulta consultaPatronBajaArt251 subDel {cveIdSubDelegacion}" , lstRegPatronales.size() );
		StringBuffer strQuery = new StringBuffer();	
		strQuery.append(" SELECT MOV2.CVE_ID_MOVTO_PAT_SUJ_OBLIG AS \"cveIdMovtoPatSujetoObligado\", UM.REF_BUSCA AS \"registroPatronal\", ");
		strQuery.append(" MOV2.FEC_MOVIMIENTO AS \"fecMovimiento\", MOV2.CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG as \"cveIdTipoMovimiento\", MOV2.CVE_ID_CAUSA as \"cveIdCausa\" ");
		strQuery.append(" FROM DIT_MOVTO_PAT_SUJ_OBLIG MOV2 ");
		strQuery.append(" INNER JOIN(  ");
		strQuery.append(" SELECT max(MOV.CVE_ID_MOVTO_PAT_SUJ_OBLIG) AS MAXCVE_ID_MOVTO_PAT_SUJ_OBLIG, LLAVE.REF_BUSCA ");
		strQuery.append(" FROM DIT_LLAVE_PATRON llave ");
		strQuery.append(" INNER JOIN DIT_MOVTO_PAT_SUJ_OBLIG mov ON LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = MOV.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQuery.append(" WHERE LLAVE.REF_BUSCA in (:lstRegPatronales) ");
		strQuery.append(" GROUP BY LLAVE.REF_BUSCA  ");
		strQuery.append(" )UM ON MOV2.CVE_ID_MOVTO_PAT_SUJ_OBLIG = UM.MAXCVE_ID_MOVTO_PAT_SUJ_OBLIG ");
		strQuery.append(" WHERE MOV2.CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG = :tipoMovBaja ");
		if(indBaja251)
			strQuery.append(" and MOV2.CVE_ID_CAUSA= :tipoCausa251 ");
		
		try {
			log.debug("el query a ejecutar de movimientoBaja Patron es " + strQuery );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameterList("lstRegPatronales", lstRegPatronales);
			sqlQuery.setLong("tipoMovBaja", TIPO_MOVIMIENTO_BAJA);
			if(indBaja251)
				sqlQuery.setLong("tipoCausa251", TIPO_CAUSA_BAJA_251);
	
			
			List<MovimientoRegistroPatronal> listMovtosPat =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(MovimientoRegistroPatronal.class)).list();
			if(listMovtosPat!= null && !listMovtosPat.isEmpty())
				log.debug(" la cosulta de aviso si trae registros "+ listMovtosPat.size());
			
			return listMovtosPat;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el los movimietnos de patrones en baja " + lstRegPatronales.get(0), e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActividadEcononica getActividadEconocimaByRegPatronal(String nrp) throws Exception {
		
		log.debug("llegando a la consulta getActividadEconocimaByRegPatronal {nrp}" , nrp );
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT  DIV.NUM_DIVISION as \"numDivision\", GPO.NUM_GRUPO as \"numGrupo\", FRA.NUM_FRACCION as \"numFraccion\" ");
		strQuery.append(" FROM DIT_LLAVE_PATRON LLAV ");
		strQuery.append(" INNER JOIN DIT_CLASIFICACION CLA ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = CLA.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQuery.append(" INNER JOIN DIC_FRACCION_CLASE FC ON CLA.CVE_ID_FRACCION_CLASE = FC.CVE_ID_FRACCION_CLASE ");
		strQuery.append(" INNER JOIN DIC_FRACCION FRA ON FC.CVE_ID_FRACCION = FRA.CVE_ID_FRACCION ");
		strQuery.append(" INNER JOIN DIC_GRUPO GPO ON FRA.CVE_ID_GRUPO = GPO.CVE_ID_GRUPO ");
		strQuery.append(" INNER JOIN DIC_DIVISION DIV ON GPO.CVE_ID_DIVISION = DIV.CVE_ID_DIVISION ");
		strQuery.append(" WHERE LLAV.REF_BUSCA = :nrp ");
		try {
			log.debug("el query a ejecutar de actividadEconomica Patron es " + strQuery );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("nrp", nrp);
			List<ActividadEcononica> listActividadEconomica =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(ActividadEcononica.class)).list();
			if(listActividadEconomica!= null && !listActividadEconomica.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listActividadEconomica.size());
				return listActividadEconomica.get(0);
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getActividadEconocimaByRegPatronal " + nrp, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumTrabajadoresVigentes getTrabajadoresVigentesByRegPatronal(String nrp) throws Exception {
		
		log.debug("llegando a la consulta getTrabajadoresVigentesByRegPatronal {nrp}" , nrp );
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT XP.CAN_TRAB_VIG_PER as \"numTrabajadoresPermanentes\", XP.CAN_TRAB_VIG_EVE as \"numTrabajadoresEventuales\", XP.CAN_TRAB_VIG_CONS as \"numTrabajadoresConstruccion\" , ");
		strQuery.append(" XP.CAN_TRAB_MEX_EXTRAN as \"numTrabajadoresMexExtranjero\", ");
		strQuery.append(" TO_NUMBER(XP.CAN_TRAB_VIG_PER)+TO_NUMBER(XP.CAN_TRAB_VIG_EVE)+TO_NUMBER(XP.CAN_TRAB_VIG_CONS)+ ");
		strQuery.append(" TO_NUMBER(XP.CAN_TRAB_MEX_EXTRAN)+TO_NUMBER(XP.REF_ADIC_PENS) as \"numTotalTrabajadoresVigentes\",  ");
		strQuery.append(" to_number(nvl(XP.REF_ADIC_PENS,0)) as \"numRefAdicionlesPen\" ");
		strQuery.append(" FROM DIT_LLAVE_PATRON LLAV ");
		strQuery.append(" INNER JOIN DIT_DTS_EXTRA_PATRON XP ON LLAV.CVE_ID_PATRON_GENERAL = XP.CVE_ID_PATRON_GENERAL ");
		strQuery.append(" WHERE LLAV.REF_BUSCA = :nrp ");
		try {
			log.debug("el query a ejecutar de trabajadores vigentes  Patron es " + strQuery );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("nrp", nrp);
			List<NumTrabajadoresVigentes> listTrabajadoresVigentes =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(NumTrabajadoresVigentes.class)).list();
			if(listTrabajadoresVigentes!= null && !listTrabajadoresVigentes.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listTrabajadoresVigentes.size());
				return listTrabajadoresVigentes.get(0);
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getTrabajadoresVigentesByRegPatronal " + nrp, e );
			throw e;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosGeneralesPatronQuery getDatosGeneralesPatron(String nrp) throws Exception {
		log.debug("llegando a la consulta getDatosGeneralesPatron {nrp}" , nrp );
		StringBuffer strQueryPatron = new StringBuffer();
		strQueryPatron.append(" SELECT  substr(LLAVE.REF_BUSCA,0,8) as \"regPatron\",  substr(LLAVE.REF_BUSCA,9,2) as \"cveModalidad\", pm.rfc as \"rfc\", ");
		strQueryPatron.append(" pm.DENOMINACION_RAZON_SOCIAL as \"nombreRazonSocial\", pdom.domicilio_id as \"domicilioId\", dm.ref_codigo_postal as \"codigoPostal\", ");
		strQueryPatron.append(" dm.des_domicilio as \"desDomicilio\", dm.des_localidad as \"nombreLocalidad\" ,  ");
		strQueryPatron.append(" 'PERSONA MORAL' as \"tipoPersona\",  LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO as \"cveIdPatronSujetoObligado\", ");
		strQueryPatron.append(" obligado.FEC_REGISTRO_ALTA as \"fecRegistroAlta\" ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON llave, DIT_PERSONA_MORAL pm,  DIT_PAT_SUJ_OBLIG_DOMICILIO pdom, DIT_PAT_SUJ_OBLIG_DOM_MIGR dm, ");
		strQueryPatron.append(" DIT_PATRON_SUJETO_OBLIGADO obligado  where LLAVE.REF_BUSCA = :nrp ");
		strQueryPatron.append(" and llave.CVE_ID_PERSONA_MORAL = pm.CVE_ID_PERSONA_MORAL ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = obligado.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = PDOM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = DM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" union ");
		strQueryPatron.append(" SELECT  substr(LLAVE.REF_BUSCA,0,8) as \"regPatron\",  substr(LLAVE.REF_BUSCA,9,2) as \"cveModalidad\", pf.rfc as \"rfc\",  ");
		strQueryPatron.append(" p.nom_nombre || ' ' || nvl(p.nom_primer_apellido,'') || ' ' || nvl(p.nom_segundo_apellido,'')  as \"nombreRazonSocial\",  ");
		strQueryPatron.append(" pdom.domicilio_id as \"domicilioId\", dm.ref_codigo_postal as \"codigoPostal\", ");
		strQueryPatron.append(" dm.des_domicilio as \"desDomicilio\", dm.des_localidad as \"nombreLocalidad\" , ");
		strQueryPatron.append(" 'PERSONA FISICA' as \"tipoPersona\", LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO as \"cveIdPatronSujetoObligado\", ");
		strQueryPatron.append(" obligado.FEC_REGISTRO_ALTA as \"fecRegistroAlta\" ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON llave, DIT_PERSONA_FISICA pf,   DIT_PAT_SUJ_OBLIG_DOMICILIO pdom,  ");
		strQueryPatron.append(" dit_persona p, DIT_PAT_SUJ_OBLIG_DOM_MIGR dm,  DIT_PATRON_SUJETO_OBLIGADO obligado");
		strQueryPatron.append(" where LLAVE.REF_BUSCA = :nrp ");
		strQueryPatron.append(" and llave.CVE_ID_PERSONA_FISICA = pf.CVE_ID_PERSONA_FISICA ");
		strQueryPatron.append(" and pf.cve_id_persona = p.cve_id_persona ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = obligado.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = PDOM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = DM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		log.debug("el query a ejecutar de para datos generales del patronPatron es " + strQueryPatron );
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPatron.toString());
			sqlQuery.setParameter("nrp", nrp);
			List<DatosGeneralesPatronQuery> listDatosGeneralesPatronEntity =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesPatronQuery.class)).list();
			if(listDatosGeneralesPatronEntity!= null && !listDatosGeneralesPatronEntity.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listDatosGeneralesPatronEntity.size());
				return listDatosGeneralesPatronEntity.get(0);
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getTrabajadoresVigentesByRegPatronal " + nrp, e );
			throw e;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getFechaHuelgaPatron(String nrp) throws Exception {
	
		log.debug("llegando a la consulta getFechaHuelgaPatron {nrp}" , nrp );
		StringBuffer strQueryPatron = new StringBuffer();
		strQueryPatron.append("select EXTRA.FEC_INI_HUELGA ");
		strQueryPatron.append("from dit_llave_patron llave,  DIT_DTS_EXTRA_PATRON  extra ");
		strQueryPatron.append("where LLAVE.REF_BUSCA  = :nrp ");
		strQueryPatron.append("and LLAVE.CVE_ID_PATRON_GENERAL = extra.CVE_ID_PATRON_GENERAL ");
		log.debug("el query a ejecutar de para fecha de huelga es " + strQueryPatron );
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPatron.toString());
			sqlQuery.setParameter("nrp", nrp);
			
			List<Object> lstIndicador = (List<Object>)sqlQuery.list();
			if(lstIndicador!= null && !lstIndicador.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ lstIndicador.size());
				return (Date)lstIndicador.get(0);
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getTrabajadoresVigentesByRegPatronal " + nrp, e );
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosEmpresaPermisoConvid getDatosEmpresaPermisoCovid(String rfc) throws Exception {
		
		log.debug("llegando a la consulta getDatosEmpresaPermisoCovid {nrp}" , rfc );
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" Select EMP.RFC as \"rfc\" , EMP.FEC_ALTA as \"fecAlta\", EMP.CAN_TRABAJADORES as \"numTrabajadores\" ");
		strQuery.append(" from MGX_EMPRESA_DTSGRAL emp ");
		strQuery.append(" where emp.rfc = :rfc ");
		try {
			log.debug("el query a ejecutar de empresa con trabajadores es " + strQuery );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("rfc", rfc);
			List<DatosEmpresaPermisoConvid> listDatosEmpresaPermisoConvid =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosEmpresaPermisoConvid.class)).list();
			if(listDatosEmpresaPermisoConvid!= null && !listDatosEmpresaPermisoConvid.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listDatosEmpresaPermisoConvid.size());
				return listDatosEmpresaPermisoConvid.get(0);
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getDatosEmpresaPermisoCovid " + rfc, e );
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatosGeneralesPatronQuery> getDatosGeneralesPatron(List<Long> lstIdPatronGeneral) throws Exception {
		log.debug("llegando a la consulta getDatosGeneralesPatron {nrp}" , lstIdPatronGeneral  );
		StringBuffer strQueryPatron = new StringBuffer();
		strQueryPatron.append(" SELECT  substr(LLAVE.REF_BUSCA,0,8) as \"regPatron\",  substr(LLAVE.REF_BUSCA,9,2) as \"cveModalidad\", pm.rfc as \"rfc\", ");
		strQueryPatron.append(" pm.DENOMINACION_RAZON_SOCIAL as \"nombreRazonSocial\", pdom.domicilio_id as \"domicilioId\", dm.ref_codigo_postal as \"codigoPostal\", ");
		strQueryPatron.append(" dm.des_domicilio as \"desDomicilio\", dm.des_localidad as \"nombreLocalidad\" ,  ");
		strQueryPatron.append(" 'PERSONA MORAL' as \"tipoPersona\",  LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO as \"cveIdPatronSujetoObligado\", ");
		strQueryPatron.append(" obligado.FEC_REGISTRO_ALTA as \"fecRegistroAlta\" ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON llave, DIT_PERSONA_MORAL pm,  DIT_PAT_SUJ_OBLIG_DOMICILIO pdom, DIT_PAT_SUJ_OBLIG_DOM_MIGR dm, ");
		strQueryPatron.append(" DIT_PATRON_SUJETO_OBLIGADO obligado  where LLAVE.CVE_ID_PATRON_GENERAL in( :lstIdPatronGeneral ) ");
		strQueryPatron.append(" and llave.CVE_ID_PERSONA_MORAL = pm.CVE_ID_PERSONA_MORAL ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = obligado.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = PDOM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = DM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" union ");
		strQueryPatron.append(" SELECT  substr(LLAVE.REF_BUSCA,0,8) as \"regPatron\",  substr(LLAVE.REF_BUSCA,9,2) as \"cveModalidad\", pf.rfc as \"rfc\",  ");
		strQueryPatron.append(" p.nom_nombre || ' ' || nvl(p.nom_primer_apellido,'') || ' ' || nvl(p.nom_segundo_apellido,'')  as \"nombreRazonSocial\",  ");
		strQueryPatron.append(" pdom.domicilio_id as \"domicilioId\", dm.ref_codigo_postal as \"codigoPostal\", ");
		strQueryPatron.append(" dm.des_domicilio as \"desDomicilio\", dm.des_localidad as \"nombreLocalidad\" , ");
		strQueryPatron.append(" 'PERSONA FISICA' as \"tipoPersona\", LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO as \"cveIdPatronSujetoObligado\", ");
		strQueryPatron.append(" obligado.FEC_REGISTRO_ALTA as \"fecRegistroAlta\" ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON llave, DIT_PERSONA_FISICA pf,   DIT_PAT_SUJ_OBLIG_DOMICILIO pdom,  ");
		strQueryPatron.append(" dit_persona p, DIT_PAT_SUJ_OBLIG_DOM_MIGR dm,  DIT_PATRON_SUJETO_OBLIGADO obligado");
		strQueryPatron.append(" where LLAVE.CVE_ID_PATRON_GENERAL in ( :lstIdPatronGeneral ) ");
		strQueryPatron.append(" and llave.CVE_ID_PERSONA_FISICA = pf.CVE_ID_PERSONA_FISICA ");
		strQueryPatron.append(" and pf.cve_id_persona = p.cve_id_persona ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = obligado.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = PDOM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		strQueryPatron.append(" and LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = DM.CVE_ID_PATRON_SUJETO_OBLIGADO(+) ");
		log.debug("el query a ejecutar de para datos generales del patronPatron es " + strQueryPatron );
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPatron.toString());
			sqlQuery.setParameterList("lstIdPatronGeneral", lstIdPatronGeneral);
			List<DatosGeneralesPatronQuery> listDatosGeneralesPatronEntity =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesPatronQuery.class)).list();
			if(listDatosGeneralesPatronEntity!= null && !listDatosGeneralesPatronEntity.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ listDatosGeneralesPatronEntity.size());
				return listDatosGeneralesPatronEntity;
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getTrabajadoresVigentesByRegPatronal " + lstIdPatronGeneral, e );
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRegistrosPatronalesByRfc(String rfc) throws Exception{
		log.debug("llegando a la consulta getRegistrosPatronalesByRfc {rfc}" , rfc  );
		StringBuffer strQueryPatron = new StringBuffer();
		List<String> lstNRP = new ArrayList<String>();
		strQueryPatron.append(" select LLPAT.REF_BUSCA || PG.DIG_VER AS NRP ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON LLPAT, DIT_PERSONA_FISICA PF, DIT_PATRON_GENERAL PG ");
		strQueryPatron.append(" WHERE LLPAT.CVE_ID_PERSONA_FISICA = PF.CVE_ID_PERSONA_FISICA ");
		strQueryPatron.append(" AND PG.CVE_ID_PATRON_GENERAL = LLPAT.CVE_ID_PATRON_GENERAL ");
		strQueryPatron.append(" AND PF.RFC = :rfc ");
		strQueryPatron.append(" UNION ");
		strQueryPatron.append(" select LLPAT.REF_BUSCA || PG.DIG_VER AS NRP ");
		strQueryPatron.append(" from DIT_LLAVE_PATRON LLPAT, DIT_PERSONA_MORAL PM, DIT_PATRON_GENERAL PG ");
		strQueryPatron.append(" WHERE LLPAT.CVE_ID_PERSONA_MORAL = PM.CVE_ID_PERSONA_MORAL ");
		strQueryPatron.append(" AND PG.CVE_ID_PATRON_GENERAL = LLPAT.CVE_ID_PATRON_GENERAL ");
		strQueryPatron.append(" AND PM.RFC = :rfc ");
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPatron.toString());
			sqlQuery.setParameter("rfc", rfc);
			List<Object> lstResultset = (List<Object>)sqlQuery.list();
			if(lstResultset!= null && !lstResultset.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ lstResultset.size());
				for(Object registro: lstResultset) {
					lstNRP.add( String.valueOf(registro));
				}
				return lstNRP;
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getRegistrosPatronalesByRfc " + rfc, e );
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public
	List<String> getRfcPersonaRepresentadaByRfc(String rfc) throws Exception{
		log.debug("llegando a la consulta getRfcPersonaRepresentadaByRfc {rfc}" , rfc  );
		StringBuffer strQueryRfc = new StringBuffer();
		List<String> lstRfc = new ArrayList<String>();	
	
		strQueryRfc.append(" SELECT DISTINCT RFC FROM ( ");
		strQueryRfc.append(" select PFR.RFC AS RFC ");
		strQueryRfc.append(" from dit_representante_legal rp , dit_persona_fisica pf, dit_persona_fisica pfr ");
		strQueryRfc.append(" where pf.rfc = :rfc ");
		strQueryRfc.append(" and rp.cve_Id_persona = pf.cve_id_persona ");
		strQueryRfc.append(" and RP.CVE_ID_PERSONA_FISICA = PFR.CVE_ID_PERSONA_FISICA ");
		strQueryRfc.append(" UNION  ");
		strQueryRfc.append(" select PMR.RFC AS RFC  ");
		strQueryRfc.append(" from dit_representante_legal rp , dit_persona_fisica pf, dit_persona_MORAL PMR ");
		strQueryRfc.append(" where pf.rfc = :rfc ");
		strQueryRfc.append(" and rp.cve_Id_persona = pf.cve_id_persona ");
		strQueryRfc.append(" and RP.CVE_ID_PERSONA_MORAL = PMR.CVE_ID_PERSONA_MORAL ");
		strQueryRfc.append(" UNION ");
		strQueryRfc.append(" select PFR.RFC AS RFC ");
		strQueryRfc.append(" from dit_representante_legal rp , dit_persona_fisica pf, dit_patron_sujeto_obligado pso, dit_persona_fisica pfr ");
		strQueryRfc.append(" where pf.rfc = :rfc ");
		strQueryRfc.append(" and rp.cve_Id_persona = pf.cve_id_persona ");
		strQueryRfc.append(" and RP.CVE_ID_PATRON_SUJETO_OBLIGADO = pso.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryRfc.append(" and pso.CVE_ID_PERSONA_FISICA = PFR.CVE_ID_PERSONA_FISICA ");
		strQueryRfc.append(" UNION ");
		strQueryRfc.append(" select PMR.RFC AS RFC ");
		strQueryRfc.append(" from dit_representante_legal rp , dit_persona_fisica pf, dit_patron_sujeto_obligado pso, dit_persona_MORAL pmr ");
		strQueryRfc.append(" where pf.rfc = :rfc ");
		strQueryRfc.append(" and rp.cve_Id_persona = pf.cve_id_persona ");
		strQueryRfc.append(" and RP.CVE_ID_PATRON_SUJETO_OBLIGADO = pso.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		strQueryRfc.append(" and pso.CVE_ID_PERSONA_MORAL = PMR.CVE_ID_PERSONA_MORAL ");
		strQueryRfc.append(" ) REP ");
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryRfc.toString());
			sqlQuery.setParameter("rfc", rfc);
			List<Object> lstResultset = (List<Object>)sqlQuery.list();
			if(lstResultset!= null && !lstResultset.isEmpty()) {
				log.debug(" la cosulta de aviso si trae registros "+ lstResultset.size());
				for(Object registro: lstResultset) {
					lstRfc.add( String.valueOf(registro));
				}
				return lstRfc;
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getRfcPersonaRepresentadaByRfc " + rfc, e );
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SujetoObligado obtenerPatronPMC(String regPatronal) {
		StringBuilder consulta = new StringBuilder();
		consulta.append("SELECT DIV.NUM_DIVISION, ");
		consulta.append("  GPO.NUM_GRUPO, ");
		consulta.append("  CL.CVE_ID_CLASE, ");
		consulta.append("  CL.DES_CLASE, ");
		consulta.append("  FR.NUM_FRACCION, ");
		consulta.append("  FR.DES_FRACCION, ");
		consulta.append("  FR.CVE_ID_FRACCION, ");
		consulta.append("  D.CLAVE_DELEGACION, ");
		consulta.append("  D.DES_DELEG, ");
		consulta.append("  SD.CLAVE_SUBDELEGACION, ");
		consulta.append("  SD.DES_SUBDELEGACION, ");
		consulta.append("  CL.NUM_PRIMA_MEDIA, ");
		consulta.append("  pfm.rfc, ");
		consulta.append("  pfm.nombre, ");
		consulta.append("  llave.REF_BUSCA, ");
		consulta.append("  clas.NUM_PRIMA_PAGO, ");
		consulta.append("  pg.DIG_VER ");
		consulta.append("FROM MGPBDTU9X.DIT_LLAVE_PATRON llave, ");
		consulta.append("  MGPBDTU9X.dit_delsub_pat_suj_oblig patSub, ");
		consulta.append("  MGPBDTU9X.dic_subdelegacion sd, ");
		consulta.append("  MGPBDTU9X.dic_delegacion d, ");
		consulta.append("  MGPBDTU9X.dit_clasificacion clas, ");
		consulta.append("  MGPBDTU9X.DIC_FRACCION_CLASE fc, ");
		consulta.append("  MGPBDTU9X.DIC_CLASE cl, ");
		consulta.append("  MGPBDTU9X.DIC_FRACCION fr, ");
		consulta.append("  MGPBDTU9X.dic_grupo gpo, ");
		consulta.append("  MGPBDTU9X.dic_division div, ");
		consulta.append("  MGPBDTU9X.dit_patron_general pg, ");
		consulta.append("  (SELECT * ");
		consulta.append("  FROM ");
		consulta.append("    (SELECT llave.REF_BUSCA AS reg_patron, ");
		consulta.append("      pm.rfc, ");
		consulta.append("      pm.denominacion_razon_social AS nombre ");
		consulta.append("    FROM MGPBDTU9X.DIT_LLAVE_PATRON llave, ");
		consulta.append("      MGPBDTU9X.dit_persona_moral pm ");
		consulta.append("    WHERE LLAVE.REF_BUSCA        = :regPatronal ");
		consulta.append("    AND llave.cve_id_persona_moral = pm.cve_id_persona_moral ");
		consulta.append("    UNION ");
		consulta.append("    SELECT LLAVE.REF_BUSCA AS reg_patron , ");
		consulta.append("      PF.RFC, ");
		consulta.append("      P.NOM_NOMBRE ");
		consulta.append("      || ' ' ");
		consulta.append("      ||P.NOM_PRIMER_APELLIDO ");
		consulta.append("      || ' ' ");
		consulta.append("      ||P.NOM_SEGUNDO_APELLIDO AS nombre ");
		consulta.append("    FROM MGPBDTU9X.DIT_LLAVE_PATRON llave , ");
		consulta.append("      MGPBDTU9X.dit_persona_fisica pf, ");
		consulta.append("      MGPBDTU9X.dit_persona p ");
		consulta.append("    WHERE LLAVE.REF_BUSCA       = :regPatronal ");
		consulta.append("    AND LLAVE.CVE_ID_PERSONA_FISICA = PF.CVE_ID_PERSONA_FISICA ");
		consulta.append("    AND PF.CVE_ID_PERSONA           = P.CVE_ID_PERSONA ");
		consulta.append("    ) pfm ");
		consulta.append("  ) pfm ");
		consulta.append("WHERE LLAVE.REF_BUSCA                   = :regPatronal ");
		consulta.append("AND LLAVE.REF_BUSCA                     = pfm.reg_patron ");
		consulta.append("AND LLAVE.CVE_ID_PATRON_SUJETO_OBLIGADO = PATSUB.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		consulta.append("AND PATSUB.CVE_ID_SUBDELEGACION         = SD.CVE_ID_SUBDELEGACION ");
		consulta.append("AND SD.CVE_ID_DELEGACION                = D.CVE_ID_DELEGACION ");
		consulta.append("AND llave.CVE_ID_PATRON_SUJETO_OBLIGADO = CLAS.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		consulta.append("AND CLAS.CVE_ID_FRACCION_CLASE          = FC.CVE_ID_FRACCION_CLASE ");
		consulta.append("AND FC.CVE_ID_CLASE                     = CL.CVE_ID_CLASE ");
		consulta.append("AND FC.CVE_ID_FRACCION                  = FR.CVE_ID_FRACCION ");
		consulta.append("AND FR.CVE_ID_GRUPO                     = GPO.CVE_ID_GRUPO ");
		consulta.append("AND GPO.CVE_ID_DIVISION                 = div.CVE_ID_DIVISION ");
		consulta.append("AND LLAVE.CVE_ID_PATRON_GENERAL         = PG.CVE_ID_PATRON_GENERAL ");
		SujetoObligado sujetoObligado = null;
		try {
			Query sqlQuery = getEntityManager().createNativeQuery(consulta.toString());
			sqlQuery.setParameter("regPatronal", regPatronal);
			List<Object[]> result = sqlQuery.getResultList();
			if (result != null && !result.isEmpty()) {
				sujetoObligado = mapRow(result.get(0));
			}
		} catch (Exception e) {
			log.error("ocurio un error al consultar obtenerPatronPMC " + regPatronal, e);
		}
		return sujetoObligado;
	}
	
	private SujetoObligado mapRow(Object[] rs) {
		SujetoObligado sujeto = new SujetoObligado();
		Clasificacion clasificacion = new Clasificacion();
		Fraccion fraccion = new Fraccion();
		Clase clase = new Clase();
		Grupo grupo = new Grupo();
		Division division = new Division();
		Subdelegacion subdelegacion = new Subdelegacion();
		Delegacion delegacion = new Delegacion();
		Moral moral = null;
		Fisica fisica = null;
		clase.setClave(((BigDecimal) rs[2]).longValue());
		clase.setDescripcion((String) rs[3]);
		division.setNumDivision((String) rs[0]);
		grupo.setNumGrupo((String) rs[1]);
		grupo.setDivision(division);
		fraccion.setNumFraccion((String) rs[4]);
		fraccion.setDescripcion((String) rs[5]);
		fraccion.setGrupo(grupo);
		fraccion.setClase(clase);
		clasificacion.setFraccion(fraccion);
		clasificacion.setPrimaSRTActual((BigDecimal) rs[11]);
		delegacion.setClave((String) rs[7]);
		delegacion.setDescripcion((String) rs[8]);
		subdelegacion.setDelegacion(delegacion);
		subdelegacion.setClave((String) rs[9]);
		subdelegacion.setDescripcion((String) rs[10]);
		String rfc = (String) rs[12];
		String razonSocial = (String) rs[13];
		sujeto.setDigVerificador(String.valueOf((Character) rs[16]));
		if (rfc != null && rfc.trim().length() == 12) {
			moral = new Moral();
			moral.setRazonSocial(razonSocial);
			moral.setRfc(rfc);
			sujeto.setMoral(moral);
		} else {
			fisica = new Fisica();
			fisica.setNombre(razonSocial);
			fisica.setRfc(rfc);
			sujeto.setFisica(fisica);
		}
		sujeto.setClasificacion(clasificacion);
		sujeto.setSubdelegacion(subdelegacion);
		return sujeto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetallePatronClasifMovPatQuery> getDetallePatronClasifMovPat(ConsultaPatron consulta) throws Exception {
		
		StringBuffer strQueryDetallePat = new StringBuffer();
		strQueryDetallePat.append(" select LLAV.REF_BUSCA as \"regPatron\", DIV.DES_DIVISION as \"desDivision\",  GPO.DES_GRUPO  as \"desGrupo\", FRA.DES_FRACCION as \"desFraccion\" ,  ");
		strQueryDetallePat.append(" EDO.NOM_ENT as \"nomEntidadFederativa\", DELE.CLAVE_DELEGACION as \"cveDelegacion\", SUBD.CLAVE_SUBDELEGACION  as  \"cveSubDelegacion\" ,  ");
		strQueryDetallePat.append(" TMOV.DES_TIPO_MOVIMIENTO as \"desTipoMovimiento\",  DEP.FEC_MOVTO as \"fecMovimiento\" ,  ");
		strQueryDetallePat.append(" PDOM.domicilio_id as \"domicilioId\", DM.ref_codigo_postal as \"codigoPostal\",  ");
		strQueryDetallePat.append(" DM.des_domicilio as \"desDomicilio\", DM.des_localidad as \"nombreLocalidad\", EMP.RFC as \"rfc\",  ");
		strQueryDetallePat.append(" obligado.FEC_REGISTRO_ALTA as \"fecRegistroAlta\", LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO as \"cveIdPatronSujetoObligado\" ,  ");
		strQueryDetallePat.append(" DELE.DES_DELEG as \"nomDelegacion\", SUBD.DES_SUBDELEGACION as \"nomSubDelegacion\"  ");
		strQueryDetallePat.append(" FROM DIT_LLAVE_PATRON LLAV   ");
		if(consulta.getIdTipoPersona().intValue() == TipoPersonaFiscal.FISICA.getCodigo().intValue())
			strQueryDetallePat .append(" INNER JOIN DIT_PERSONA_FISICA  EMP ON LLAV.CVE_ID_PERSONA_FISICA = EMP.CVE_ID_PERSONA_FISICA  ");
		else
			strQueryDetallePat .append(" INNER JOIN DIT_PERSONA_MORAL  EMP ON  LLAV.CVE_ID_PERSONA_MORAL = EMP.CVE_ID_PERSONA_MORAL  ");
		strQueryDetallePat.append(" INNER JOIN DIT_PATRON_SUJETO_OBLIGADO obligado ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = obligado.CVE_ID_PATRON_SUJETO_OBLIGADO  ");
		strQueryDetallePat.append(" INNER JOIN DIT_CLASIFICACION CLA ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = CLA.CVE_ID_PATRON_SUJETO_OBLIGADO  ");
		strQueryDetallePat.append(" INNER JOIN DIC_FRACCION_CLASE FC ON CLA.CVE_ID_FRACCION_CLASE = FC.CVE_ID_FRACCION_CLASE  ");
		strQueryDetallePat.append(" INNER JOIN DIC_FRACCION FRA ON FC.CVE_ID_FRACCION = FRA.CVE_ID_FRACCION  ");
		strQueryDetallePat.append(" INNER JOIN DIC_GRUPO GPO ON FRA.CVE_ID_GRUPO = GPO.CVE_ID_GRUPO  ");
		strQueryDetallePat.append(" INNER JOIN DIC_DIVISION DIV ON GPO.CVE_ID_DIVISION = DIV.CVE_ID_DIVISION  ");
		strQueryDetallePat.append(" INNER JOIN  DIT_DTS_EXTRA_PATRON DEP ON LLAV.CVE_ID_PATRON_GENERAL = DEP.CVE_ID_PATRON_GENERAL  ");
		strQueryDetallePat.append(" INNER JOIN DIC_TIPO_MOVTO_PAT_SUJOBLIG TMOV ON  DEP.CVE_TIPO_MOVTO = tmov.CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG  ");
		strQueryDetallePat.append(" INNER JOIN DIT_MUNICIPIO_PAT_SUJ_OBLIG MUP ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = MUP.CVE_ID_PATRON_SUJETO_OBLIGADO  ");
		strQueryDetallePat.append(" INNER JOIN DIC_MUNICIPIO_IMSS MUN ON MUP.CVE_ID_MUNICIPIO_IMSS = MUN.CVE_ID_MUNICIPIO_IMSS  ");
		strQueryDetallePat.append(" INNER JOIN DG_CAT_ESTADO EDO ON EDO.CVE_ENT = MUN.CVE_ENT  ");
		strQueryDetallePat.append(" INNER JOIN DIT_MUNICIPIO_SUBDELEGACION MUNS ON MUNS.CVE_ID_MUNICIPIO_IMSS = MUN.CVE_ID_MUNICIPIO_IMSS  ");
		strQueryDetallePat.append(" INNER JOIN DIC_SUBDELEGACION SUBD ON SUBD.CVE_ID_SUBDELEGACION = MUNS.CVE_ID_SUBDELEGACION  ");
		strQueryDetallePat.append(" INNER JOIN DIC_DELEGACION DELE ON DELE.CVE_ID_DELEGACION = SUBD.CVE_ID_DELEGACION  ");
		strQueryDetallePat.append(" LEFT JOIN DIT_PAT_SUJ_OBLIG_DOMICILIO PDOM ON  LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = PDOM.CVE_ID_PATRON_SUJETO_OBLIGADO  ");
		strQueryDetallePat.append(" and PDOM.CVE_ID_TIPO_DOMICILIO IN (3,7)  ");
		strQueryDetallePat.append(" LEFT JOIN DIT_PAT_SUJ_OBLIG_DOM_MIGR DM ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = DM.CVE_ID_PATRON_SUJETO_OBLIGADO  ");
		
		if(!StringUtils.isEmpty(consulta.getRfc()))
			strQueryDetallePat.append(" WHERE  EMP.RFC = :rfc   ");
		else
			strQueryDetallePat.append(" WHERE LLAV.REF_BUSCA = :regPatron  ");
		log.debug("el query de detalle patron a  ejecutar es " + strQueryDetallePat.toString());
		
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryDetallePat.toString());
			if(!StringUtils.isEmpty(consulta.getRfc()))
				sqlQuery.setParameter("rfc", consulta.getRfc());
			else
				sqlQuery.setParameter("regPatron", consulta.getRegistroPatronal());
			
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DetallePatronClasifMovPatQuery> listDetallePatronClasifMovPat =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DetallePatronClasifMovPatQuery.class)).list();
				log.debug(" la cosulta de aviso si trae registros "+ listDetallePatronClasifMovPat.size());
				return listDetallePatronClasifMovPat;
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar getRegistrosPatronalesByRfc " + consulta, e );
			throw e;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSatDetallePatron getRfcTipoPersona(ConsultaPatron consulta) throws Exception {
		log.debug("llegando a la consulta getRfcTipoPersona {consulta}" , consulta  );
		StringBuffer strQueryPFM = new StringBuffer();
		strQueryPFM.append(" SELECT   PF.RFC, ");
		strQueryPFM.append(" CASE WHEN LLAV.CVE_ID_PERSONA_FISICA IS NOT NULL THEN 1  ");
		strQueryPFM.append(" ELSE 2  END as tipoPersona ");
		strQueryPFM.append(" FROM DIT_LLAVE_PATRON LLAV  ");
		strQueryPFM.append(" INNER JOIN DIT_PERSONA_FISICA  PF  ON LLAV.CVE_ID_PERSONA_FISICA = PF.CVE_ID_PERSONA_FISICA ");
		if(!StringUtils.isEmpty(consulta.getRfc()))
				strQueryPFM.append(" WHERE PF.RFC  = :rfc " ); 
		else
				strQueryPFM.append(" WHERE LLAV.REF_BUSCA  = :regPatron " );
		strQueryPFM.append(" UNION " );
		strQueryPFM.append(" SELECT  PM.RFC, ");
		strQueryPFM.append(" CASE WHEN LLAV.CVE_ID_PERSONA_MORAL IS NOT NULL THEN 2  ");
		strQueryPFM.append(" ELSE 1  END as tipoPersona ");
		strQueryPFM.append(" FROM DIT_LLAVE_PATRON LLAV  ");
		strQueryPFM.append(" INNER JOIN DIT_PERSONA_MORAL  PM  ON LLAV.CVE_ID_PERSONA_MORAL = PM.CVE_ID_PERSONA_MORAL ");
		if(!StringUtils.isEmpty(consulta.getRfc()))
				strQueryPFM.append(" WHERE PM.RFC  = :rfc " ); 
		else
			strQueryPFM.append(" WHERE LLAV.REF_BUSCA  = :regPatron " );
		
		log.debug("el query de tipo persona a ejecutar es " + strQueryPFM.toString());
		
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPFM.toString());
			if(!StringUtils.isEmpty(consulta.getRfc()))
				sqlQuery.setParameter("rfc", consulta.getRfc());
			else
				sqlQuery.setParameter("regPatron", consulta.getRegistroPatronal());
			
			List<Object[]>  lstResultset = sqlQuery.list();
			if(lstResultset!= null && !lstResultset.isEmpty()) {
				log.debug(" la cosulta de tipoPersona trae regosrps "+ lstResultset.size());
				DatosSatDetallePatron datos = new DatosSatDetallePatron();
				Object[] rs =lstResultset.get(0);
				datos.setRfc(String.valueOf(rs[0]));
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(((BigDecimal)rs[1]).longValue());
				if(TipoPersonaEnum.FISICA.getId() == tipoPersona.getIdTipoPersona().longValue())
					tipoPersona.setDescripcion(TipoPersonaEnum.FISICA.name());
				else
					tipoPersona.setDescripcion(TipoPersonaEnum.MORAL.name());
				
				datos.setTipiPersona(tipoPersona);
				return datos;
				
			}else 
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el rfc y tipo de persona " + consulta, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PatronPlataformaResponse validaPatronPlataforma(String nrp) throws Exception {
		log.debug("llegue al metodo para validar patron de plataforma NRP" + nrp );
		String query = "SELECT TO_CHAR(FEC_ALTA,'dd/MM/yyyy')FEC_ALTA FROM PPT_PATRON_PLATAFORMA "
				+ " WHERE CVE_REG_PATRON = :nrp AND CVE_MODAL = :mdalidad"
				+ " and FEC_BAJA is null";
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(query);
			sqlQuery.setParameter("nrp", nrp.substring(0,8));
			sqlQuery.setParameter("mdalidad", nrp.substring(8,10));
			PatronPlataformaResponse response = new PatronPlataformaResponse();
			response.setCodigoRespuesta("0");
			response.setDetalleRespuesta("EXITO");
			List<Object> lstPatron = (List<Object>)sqlQuery.list();
			if(lstPatron!= null && !lstPatron.isEmpty()) {
				log.debug(" la cosulta de patron plataforma si trae registros "+ lstPatron.size());
				response.setFechaRegistroAlta((String)lstPatron.get(0));
				response.setPatronPlataforma(true);
			}else 
				response.setPatronPlataforma(false);
			return response;
		}catch (Exception e) {
			log.error("error al consultar el patron de plataformas ", e);
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public PatronListaBlancaResponse validaPatronListaBlanca(String nrp) throws Exception {
		log.debug("llegue al metodo para validar patron de plataforma NRP" + nrp );
		String query = "SELECT TO_CHAR(FEC_ALTA,'dd/MM/yyyy')FEC_ALTA FROM PPT_PATRON_LISTA_BLANCA_ST "
				+ " WHERE CVE_REG_PATRON = :nrp AND CVE_MODAL = :mdalidad"
				+ " and FEC_BAJA is null";
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(query);
			sqlQuery.setParameter("nrp", nrp.substring(0,8));
			sqlQuery.setParameter("mdalidad", nrp.substring(8,10));
			PatronListaBlancaResponse response = new PatronListaBlancaResponse();
			response.setCodigoRespuesta("0");
			response.setDetalleRespuesta("EXITO");
			List<Object> lstPatron = (List<Object>)sqlQuery.list();
			if(lstPatron!= null && !lstPatron.isEmpty()) {
				log.debug(" la cosulta de patron plataforma si trae registros "+ lstPatron.size());
				response.setFechaRegistroAlta((String)lstPatron.get(0));
				response.setPatronListaBlanca(true);
			}else 
				response.setPatronListaBlanca(false);
			return response;
		}catch (Exception e) {
			log.error("error al consultar el patron de lista blanca ", e);
			throw e;
		}
	}
	@Override
	public String getNombreRazonSocial(String nrp) throws Exception {
		String nobreRazonSocial = null;
		log.debug("llegue al metodo para consultar nombre o razon social del  NRP" + nrp );
		String query = "SELECT  "
				+ " CASE WHEN LLAV.TIP_PERSONA = 1 THEN PER.NOM_NOMBRE||' '||PER.NOM_PRIMER_APELLIDO||' '||PER.NOM_SEGUNDO_APELLIDO\r\n "
				+ "     ELSE PM.DENOMINACION_RAZON_SOCIAL \r\n "
				+ " END AS NOMBRE_PATRON\r\n "
				+ " FROM MGPBDTU9X.DIT_LLAVE_PATRON LLAV\r\n "
				+ " LEFT OUTER JOIN MGPBDTU9X.DIT_PERSONA_MORAL PM 		ON LLAV.CVE_ID_PERSONA_MORAL = PM.CVE_ID_PERSONA_MORAL \r\n "
				+ " LEFT OUTER JOIN MGPBDTU9X.DIT_LLAVE_PERSONA PER 	ON LLAV.CVE_ID_PERSONA = PER.CVE_ID_PERSONA \r\n "
				+ " WHERE LLAV.REF_BUSCA = :nrp";
		try {
			log.debug("voy a ejecutar el query para nombre rs "+ query );
			SQLQuery sqlQuery = getSession().createSQLQuery(query);
			if(nrp.length()>10)
				sqlQuery.setParameter("nrp", nrp.substring(0,10));
			else
				sqlQuery.setParameter("nrp", nrp);	
			List<Object> lstPatron = (List<Object>)sqlQuery.list();
			if(lstPatron!= null && !lstPatron.isEmpty()) {
				log.debug(" la cosulta de patron para NRS trae registros"+ lstPatron.size());
				nobreRazonSocial = (String)lstPatron.get(0);
			}
			return nobreRazonSocial;
		}catch (Exception e) {
			log.error("error al consultar el NRS del patron  " + nrp, e);
			throw e;
		}
	}
	
	
}
