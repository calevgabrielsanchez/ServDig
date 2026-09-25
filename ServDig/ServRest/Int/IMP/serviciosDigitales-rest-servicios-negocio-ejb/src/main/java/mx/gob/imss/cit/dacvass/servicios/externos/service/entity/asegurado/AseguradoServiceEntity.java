package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.asegurado;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.TipoPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AsignacionNssDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoCL;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.ResumenAseguradoTramiteCda;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;

@Local(value = AseguradoServiceEntityLocal.class)
@Stateless
public class AseguradoServiceEntity extends PersistenceUnitSISCOBServiceEntity implements  AseguradoServiceEntityLocal{

	private static final Logger log = LoggerFactory.getLogger(AseguradoServiceEntity.class);
	private final String IND_CAMBIO_PASO_CONFIR ="1";
	private final String IND_NO_CAMBIO_PASO_CONFIR ="0";
	private final String IND_PENDIENTE_CONFIRMAR="2";
	
	/**
	 * Metodo que consulta el idAsingacion y idPersona de la tabla de asegurados sin considerar las bajas logicas o indicadores
	 * @param nss, filtroBajaLogica true si la fecha de baja debe ser nula, false no se aplica filtro 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AsignacionNssDTO getDitAsignacionNSS(String nss, boolean filtroBajaLogica) throws Exception {

		log.debug("llege a la consulta con filtrBajaLogica" + filtroBajaLogica );
		StringBuffer strQueryAsegurado = new StringBuffer();
		strQueryAsegurado.append(" select nss.cve_id_asignacion_nss as \"cveIdAsignacionNSS\" , ");
		strQueryAsegurado.append(" nss.cve_id_persona as \"cveIdPersona\", "); 
		strQueryAsegurado.append(" to_char(nss.fec_registro_baja , 'yyyy/MM/dd') as \"fecRegistroBaja\" ");
		strQueryAsegurado.append(" from dit_asignacion_nss nss where nss.num_nss = :nss ");
		if (filtroBajaLogica) {
			strQueryAsegurado.append(" and nss.fec_registro_baja is null");
		}
		try {
			log.debug("el query a ejecutar es " + strQueryAsegurado );
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryAsegurado.toString());
			sqlQuery.setParameter("nss", nss);
			List<AsignacionNssDTO> listAseguradoConsulta =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(AsignacionNssDTO.class)).list(); 
			if(listAseguradoConsulta != null && !listAseguradoConsulta.isEmpty()) {
				log.debug("la lista tiene los siguientes registros " + listAseguradoConsulta.size());
				return listAseguradoConsulta.get(0);
			}else
				return null;
		}catch(Exception e) {
			log.error("ocurio un error al consultar el asegurado " + nss, e );
			throw e;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenAseguradoTramiteCda> getResumenAseguradoTramiteCda(String refCurp, String nss)
			throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT CORR.REF_CURP as \"refCurp\", ");
		strQuery.append(" (SELECT NVL(LISTAGG(ddncBIS_.num_nss,' , ')  WITHIN group (order by ddncBIS_.cve_id_correccion_datos_aseg),' ') as \"lstNssInvolucrados\" ");
		strQuery.append(" FROM dit_detalle_nss_cda ddncBIS_ ");
		strQuery.append(" WHERE ddncBIS_.cve_id_correccion_datos_aseg = corr.cve_id_correccion_datos_aseg ");
		strQuery.append(" AND ddncBIS_.fec_registro_baja IS NULL ");
		strQuery.append(" GROUP BY ddncBIS_.cve_id_correccion_datos_aseg) AS \"lstNssInvolucrados\", ");
	//	strQuery.append(" TRANSLATE(UPPER((xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./personaRENAPO/@nombre').getStringVal()  ||' '|| xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./personaRENAPO/@primerApellido').getStringVal() ||' '|| xmltype(DT.REF_DATOS_TRAMITE_XML).extract('//./personaRENAPO/@segundoApellido').getStringVal())), '������', '#AEIOU') as \"nombreCompletoAsegurado\", ");
		strQuery.append(" null as \"nombreCompletoAsegurado\", ");
		strQuery.append(" (CASE tra.cve_id_estado_tramite "); 
		strQuery.append(" WHEN 1 THEN 'EN REGISTRO' ");
		strQuery.append(" WHEN 2 THEN 'ATENDIDA' ");
		strQuery.append(" WHEN 3 THEN 'POR AUTORIZAR' "); 
		strQuery.append(" WHEN 4 THEN 'ASIGNADA'  ");
		strQuery.append(" WHEN 5 THEN 'INFORMACI�N SOLICITADA' "); 
		strQuery.append(" WHEN 7 THEN 'ABANDONADA'  ");
		strQuery.append(" WHEN 9 THEN 'CANCELADA'  ");
		strQuery.append(" WHEN 37 THEN 'ENVIADA SINDO'  ");
		strQuery.append(" WHEN 58 THEN 'PROCESO DE ATENCI�N' "); 
		strQuery.append(" WHEN 70 THEN 'RECHAZADA' ");
		strQuery.append(" WHEN 75 THEN 'AUTORIZADA' "); 
		strQuery.append(" WHEN 85 THEN 'ERROR SINDO' "); 
		strQuery.append(" WHEN 86 THEN 'VENCIDA' "); 
		strQuery.append(" WHEN 87 THEN 'SIN RESPONSABLE' "); 
		strQuery.append(" WHEN 88 THEN 'OPERADA'  ");
		strQuery.append(" WHEN 89 THEN 'INFORMACI�N ADICIONAL REQUERIDA' "); 
		strQuery.append(" ELSE 'OTRO' END) as \"descEstadoTramite\", ");
		strQuery.append(" tra.FEC_REGISTRO_ALTA as \"fechaRegistroAlta\"  ");
		strQuery.append(" FROM DIT_CORRECCION_DATOS_ASEG corr ");
		strQuery.append(" INNER JOIN DIT_TRAMITE tra ON tra.CVE_ID_TRAMITE = corr.CVE_ID_TRAMITE ");
		// strQuery.append(" INNER JOIN DIT_DETALLE_TRAMITE dt ON dt.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE ");
		strQuery.append(" WHERE TRA.CVE_ID_ESTADO_TRAMITE IN (1,2,3,4,5,7,9,37,58,70,75,85,86,87,88,89) ");
		strQuery.append(" AND(CORR.REF_CURP IN( :refCurp )) ");
		strQuery.append(" AND(TRA.CVE_ID_TIPO_TRAMITE=139 ");
		strQuery.append(" AND CORR.CVE_ID_TRAMITE=TRA.CVE_ID_TRAMITE) ");
		strQuery.append(" ORDER BY tra.FEC_REGISTRO_ALTA DESC ");
		
		log.debug("el query a ejecutar es : " + strQuery.toString());
		try {
		SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
		sqlQuery.setParameter("refCurp", refCurp);
		List<Object> lstResultado = (List<Object>)sqlQuery.list();
		if(lstResultado != null && !lstResultado.isEmpty()) {
			List<ResumenAseguradoTramiteCda> listResumenAseguradoCda =
					sqlQuery.setResultTransformer(Transformers.aliasToBean(ResumenAseguradoTramiteCda.class)).list(); 
			//
			log.debug("la lista tiene los siguientes registros " + listResumenAseguradoCda.size());
			return listResumenAseguradoCda;
		}else
			return null;
		}catch(Exception e) {
			log.error("ocurio un erro al consular los tramies de CDA para el asegurado " + nss, e );
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String isAseguradoPasoAlCambioAlPendiente(String nss) throws Exception {


		try {


			String 	strQueryPasoCambio= "select nss from DIV_NSSFTESIMSS_PASOACAMBIO " + 
					" where NSS = :nss";
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryPasoCambio);
			sqlQuery = getSession().createSQLQuery(strQueryPasoCambio);
			sqlQuery.setParameter("nss", nss);
			log.debug("el query de DIV_NSSFTESIMSS_PASOACAMBIO es " + strQueryPasoCambio );

			List<String> lstIndicador2 = (List<String>)sqlQuery.list();
			if(lstIndicador2 != null && lstIndicador2.size() > 0 && !StringUtils.isEmpty(lstIndicador2.get(0))) {
				return IND_CAMBIO_PASO_CONFIR;
			}	
			log.debug("se realiza la segundoa consulta ya que no se encontro en ASEGURADOS_TEMP_UNIF nss "  + nss );
			strQueryPasoCambio= "select ac.ID_CONFIRM as indicador " + 
								" from  ASEGURADOS_TEMP_UNIF ac" + 
								" where ac.CVE_NSS = :nss and ac.ID_CONFIRM =:indPendiente ";
			log.debug("el query de 	  es " + strQueryPasoCambio + " nss"+  nss );
			sqlQuery = getSession().createSQLQuery(strQueryPasoCambio);
			sqlQuery.setParameter("nss", nss);
			sqlQuery.setParameter("indPendiente", IND_PENDIENTE_CONFIRMAR);
			
			List<Object> lstIndicador = (List<Object>)sqlQuery.list();
			if(lstIndicador != null && lstIndicador.size() > 0) {
					return IND_CAMBIO_PASO_CONFIR;
			}
			return IND_NO_CAMBIO_PASO_CONFIR;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar el serviios de paso o cambio al con nss " + nss , e);
			throw e;
		}


	}


	@SuppressWarnings("unchecked")
	@Override
	public TipoPension getTipoPensionAsegurado(String nss) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT TP.CVE_ID_TIPO_PENSION as \"cveIdTIpoPension\", TP.DES_TIPO_PENSION as \"desTipoPension\", "); 
		strQuery.append(" TP.REF_MARCA_PENSION as \"refMMarcaPension\" ");
		strQuery.append(" from DIT_LLAVE_ASEGURADO llave, DIT_ASEGURADO_PENSION ape, DIC_TIPO_PENSION tp ");
		strQuery.append(" where  LLAVE.CVE_ID_ASEGURADO_PENSION = APE.CVE_ID_ASEGURADO_PENSION ");
		strQuery.append(" and APE.CVE_ID_TIPO_PENSION = TP.CVE_ID_TIPO_PENSION ");
		strQuery.append(" and LLAVE.REF_BUSCA = :nss ");
		log.debug("el query para consutlar la pension es " +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("nss", nss);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<TipoPension> listTipoPension =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(TipoPension.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + listTipoPension.size());
				return listTipoPension.get(0);
			}else
				return null;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar el serviios de tipo de pension para el  nss " + nss , e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosGeneralesAsegurado getDatosGeneralesAseguradoByNssDitPersona(
			Long idPersona) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" Select P.NOM_NOMBRE as \"nombre\" , P.NOM_PRIMER_APELLIDO as \"primerApellido\" , P.NOM_SEGUNDO_APELLIDO  as \"segundoApellido\",  ");
		strQuery.append(" to_char(P.FEC_NACIMIENTO, 'yyyy-MM-dd') as \"fechaNacimiento\" ,  p.curp as  \"curp\", ");
		strQuery.append(" P.RFC as \"rfc\" , P.CVE_ID_PERSONA as \"cveIdPersona\" ");
		strQuery.append(" from dit_persona p ");
		strQuery.append(" where p.CVE_ID_PERSONA =  :idPersona");

		log.debug("el query para consutlar la lista de asegurados es"
				+ strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession()
					.createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("idPersona", idPersona);
			List<Object> lstResultado = (List<Object>) sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAsegurado> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAsegurado.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados.get(0);
			} else
				return null;
		} catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados", e);
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatosGeneralesAsegurado> getDatosGeneralesAseguradoByNss(List<String> lstNss) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" Select P.NOM_NOMBRE as \"nombre\" , P.NOM_PRIMER_APELLIDO as \"primerApellido\" , P.NOM_SEGUNDO_APELLIDO  as \"segundoApellido\",  ");
		strQuery.append(" to_char(P.FEC_NACIMIENTO, 'yyyy-MM-dd') as \"fechaNacimiento\" ,  p.curp as  \"curp\" , nss.num_nss as \"nss\", ");
		strQuery.append(" P.RFC as \"rfc\" , NSS.CVE_ID_ASIGNACION_NSS as \"cveIdAsignacionNss\" , P.CVE_ID_PERSONA as \"cveIdPersona\" ");
		strQuery.append(" from dit_persona p, dit_asignacion_nss nss ");
		strQuery.append(" where p.CVE_ID_PERSONA = NSS.CVE_ID_PERSONA ");
		strQuery.append(" and NSS.NUM_NSS in (:lstNss) ");
		
		log.debug("el query para consutlar la lista de asegurados es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameterList("lstNss", lstNss);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAsegurado> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAsegurado.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados;
			}else
				return null;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados" , e);
			throw e;
		}
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<DatosGeneralesAsegurado> getCanseDatosGeneralesAseguradoByNss(List<String> lstNss) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" Select P.NOMBRE as \"nombre\" , P.ap_paterno as \"primerApellido\" , P.ap_materno as \"segundoApellido\", ");  
		strQuery.append(" null as \"fechaNacimiento\" ,  p.curp as  \"curp\" , p.nss as \"nss\" ,  ");
		strQuery.append(" null as \"rfc\" , null as \"cveIdAsignacionNss\" ,  null as \"cveIdPersona\" "); 
		strQuery.append(" from D_CANASE_PPCANA01 p  ");
		strQuery.append(" where p.nss in (:lstNss) ");
		  		
		log.debug("el query para consutlar la lista de asegurados es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameterList("lstNss", lstNss);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAsegurado> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAsegurado.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados;
			}else
				return null;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados" , e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosGeneralesAseguradoCL getCubetaUnoAseguradoByNss(String numNSS) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT NUM_NSS AS \"nss\", CURP AS \"curp\", NOM_PRIMER_APELLIDO AS \"primerApellido\", NOM_SEGUNDO_APELLIDO AS \"segundoApellido\", ");
		strQuery.append(" NOM_NOMBRE AS \"nombre\", DELEG.CLAVE_DELEGACION \"cveDelegacion\", SUBDEL.CLAVE_SUBDELEGACION \"cveSubdelegacion\", SUBDEL.CVE_ID_SUBDELEGACION \"cveSubdelegacionBigDEcimal\", UMF.NUM_ECONOM \"noEconomico\", UMF.CVE_ID_UMF \"cveUMF\" ");
		strQuery.append(" FROM MGPBDTU9X.DIT_ASIGNACION_NSS_CL2 CL2                                    ");                                                
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_GRUPO_FAMILIAR_CL2 GFCL2         ON GFCL2.CVE_ID_ASIGNACION_NSS = CL2.CVE_ID_ASIGNACION_NSS  ");             
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_PERSONA PER                      ON PER.CVE_ID_PERSONA = CL2.CVE_ID_PERSONA   ");                
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_UMF_CONSULTORIO_TURNO CONSTUR    ON GFCL2.CVE_ID_UMF_CONS_TURNO_MED = CONSTUR.CVE_ID_UMF_CONS_TURNO     ");   
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_CONSULTORIO_UMF CONS             ON CONSTUR.CVE_ID_UMF_CONSULTORIO = CONS.CVE_ID_UMF_CONSULTORIO   ");        
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_UMF UMF                          ON CONS.CVE_ID_UMF = UMF.CVE_ID_UMF           ");                            
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION SUBDEL             ON UMF.CVE_ID_SUBDELEGACION = SUBDEL.CVE_ID_SUBDELEGACION   ");              
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION DELEG                 ON DELEG.CVE_ID_DELEGACION = SUBDEL.CVE_ID_DELEGACION  ");                   
		strQuery.append(" WHERE (CVE_ID_CALIDAD_PARENTESCO	IN (5,6) OR CVE_ID_CALIDAD_PARENTESCO IS NULL)");
		strQuery.append(" AND CL2.NUM_NSS = :numNSS ");
		strQuery.append(" AND NOT EXISTS (SELECT 1 FROM MGPBDTU9X.DIT_ASIGNACION_NSS ASIG_NSS WHERE ASIG_NSS.NUM_NSS = CL2.NUM_NSS) ");
		  		
		log.debug("el query para consutlar la lista de asegurados es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("numNSS", numNSS);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAseguradoCL> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAseguradoCL.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados.get(0);
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados" , e);
			throw e;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosGeneralesAseguradoCL getAseguradoBajaByNss(String numNSS) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT NUM_NSS AS \"nss\", CURP AS \"curp\", NOM_PRIMER_APELLIDO AS \"primerApellido\", NOM_SEGUNDO_APELLIDO AS \"segundoApellido\", ");
		strQuery.append(" NOM_NOMBRE AS \"nombre\", DELEG.CLAVE_DELEGACION \"cveDelegacion\", SUBDEL.CLAVE_SUBDELEGACION \"cveSubdelegacion\", SUBDEL.CVE_ID_SUBDELEGACION \"cveSubdelegacionBigDEcimal\", ");
		strQuery.append(" UMF.NUM_ECONOM \"noEconomico\", UMF.CVE_ID_UMF \"cveUMF\" ");
		strQuery.append("            FROM MGPBDTU9X.DIT_ASIGNACION_NSS ASIG_NSS ");                                                                              
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_GRUPO_FAMILIAR GF                ON GF.CVE_ID_ASIGNACION_NSS = ASIG_NSS.CVE_ID_ASIGNACION_NSS ");            
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_PERSONA PER                      ON ASIG_NSS.CVE_ID_PERSONA = PER.CVE_ID_PERSONA      ");               
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_UMF_CONSULTORIO_TURNO CONSTUR    ON GF.CVE_ID_UMF_CONS_TURNO_MED = CONSTUR.CVE_ID_UMF_CONS_TURNO    ");      
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_CONSULTORIO_UMF CONS             ON CONSTUR.CVE_ID_UMF_CONSULTORIO = CONS.CVE_ID_UMF_CONSULTORIO ");         
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_UMF UMF                          ON CONS.CVE_ID_UMF = UMF.CVE_ID_UMF     ");                                 
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION SUBDEL             ON UMF.CVE_ID_SUBDELEGACION = SUBDEL.CVE_ID_SUBDELEGACION ");               
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION DELEG                 ON DELEG.CVE_ID_DELEGACION = SUBDEL.CVE_ID_DELEGACION  ");                  
		strQuery.append(" WHERE CVE_ID_CALIDAD_PARENTESCO	IN (5,6) ");
		strQuery.append(" AND ASIG_NSS.NUM_NSS = :numNss ");
		strQuery.append(" AND ASIG_NSS.FEC_REGISTRO_BAJA IS NOT null ");
		  		
		log.debug("el query para consutlar la lista de asegurados es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("numNss", numNSS);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAseguradoCL> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAseguradoCL.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados.get(0);
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados" , e);
			throw e;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosGeneralesAseguradoCL getCubetaDosAseguradoByNss(String numNSS) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" SELECT NUM_NSS AS \"nss\", CURP AS \"curp\", NOM_PRIMER_APELLIDO AS \"primerApellido\", NOM_SEGUNDO_APELLIDO AS \"segundoApellido\", ");
		strQuery.append(" NOM_NOMBRE AS \"nombre\", DELEG.CLAVE_DELEGACION \"cveDelegacion\", SUBDEL.CLAVE_SUBDELEGACION \"cveSubdelegacion\", SUBDEL.CVE_ID_SUBDELEGACION \"cveSubdelegacionBigDEcimal\",  ");
		strQuery.append(" UMF.NUM_ECONOM \"noEconomico\", UMF.CVE_ID_UMF \"cveUMF\" ");
		strQuery.append("            FROM MGPBDTU9X.DIT_ASIGNACION_NSS_CL3 CL3             ");                                                                        
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_GRUPO_FAMILIAR_CL3 GFCL3         ON GFCL3.CVE_ID_ASIGNACION_NSS = CL3.CVE_ID_ASIGNACION_NSS  ");              
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_PERSONA PER                      ON PER.CVE_ID_PERSONA = CL3.CVE_ID_PERSONA    ");              
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIT_UMF_CONSULTORIO_TURNO CONSTUR    ON GFCL3.CVE_ID_UMF_CONS_TURNO_MED = CONSTUR.CVE_ID_UMF_CONS_TURNO    ");    
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_CONSULTORIO_UMF CONS             ON CONSTUR.CVE_ID_UMF_CONSULTORIO = CONS.CVE_ID_UMF_CONSULTORIO  ");         
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_UMF UMF                          ON CONS.CVE_ID_UMF = UMF.CVE_ID_UMF   ");                                    
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_SUBDELEGACION SUBDEL             ON UMF.CVE_ID_SUBDELEGACION = SUBDEL.CVE_ID_SUBDELEGACION  ");               
		strQuery.append(" LEFT OUTER JOIN MGPBDTU9X.DIC_DELEGACION DELEG                 ON DELEG.CVE_ID_DELEGACION = SUBDEL.CVE_ID_DELEGACION ");                    
		strQuery.append(" WHERE (CVE_ID_CALIDAD_PARENTESCO	IN (5,6)  OR CVE_ID_CALIDAD_PARENTESCO IS NULL)");
		strQuery.append(" AND CL3.NUM_NSS = :numNSS ");
		strQuery.append(" AND NOT EXISTS (SELECT 1 FROM MGPBDTU9X.DIT_ASIGNACION_NSS ASIG_NSS WHERE ASIG_NSS.NUM_NSS = CL3.NUM_NSS) ");
		  		
		log.debug("el query para consutlar la lista de asegurados es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("numNSS", numNSS);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosGeneralesAseguradoCL> lstAsegurados =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosGeneralesAseguradoCL.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstAsegurados.size());
				return lstAsegurados.get(0);
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consultar la lista de asegurados" , e);
			throw e;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliares(String curp) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" select P.CVE_ID_PERSONA as \"cveIdPersona\", P.NOM_NOMBRE as \"nombre\", P.NOM_PRIMER_APELLIDO as \"primerApellido\", P.NOM_SEGUNDO_APELLIDO as \"segundoApellido\", NSS.CVE_ID_ASIGNACION_NSS as \"cveIdAsignacionNss\" ,  ");
		strQuery.append(" P.CURP as \"refCurp\",NSS.NUM_NSS as \"numNssPersona\",  pd.CVE_EXPEDIENTE_ELECTRONICO as \"refIDEE\", GF.REF_AGREGADO_MEDICO as \"refAgregadoMedico\", GF.CVE_ID_ASIGNACION_NSS as \"cveIdAsignacionNssGrupo\" , ");
		strQuery.append(" GF.REF_AGREGADO_AFILIACION as \"refAgregadoAfiliacion\", nssgf.num_nss as \"numNssGrpoFamiliar\", CP.CVE_ID_CALIDAD_PARENTESCO as \"cveIdParentescoGpoFamilar\", CP.DES_PARENTESCO as \"descParentescoGpoFamilar\" , ");
		strQuery.append(" pres.CVE_PRESUPUESTAL as \"clavePresupuestal\" ");
		strQuery.append(" from dit_persona p, dit_grupo_familiar gf ,  dit_persona_derechohabiente pd,  ");
		strQuery.append(" dit_asignacion_nss nss, dit_asignacion_nss nssgf , DIC_CALIDAD_PARENTESCO cp, ");
		strQuery.append(" dic_umf umf, dic_clave_presupuestal pres,  ");
		strQuery.append(" DIC_CONSULTORIO_UMF uc, DIT_UMF_CONSULTORIO_TURNO uct,  DIT_UMF_CONS_TURNO_MEDICO uctm ");
		strQuery.append(" where p.curp = :curp ");
		strQuery.append(" and P.CVE_ID_PERSONA = nss.cve_Id_persona(+) ");
		strQuery.append(" and p.cve_id_persona = gf.cve_id_persona_integrante(+) ");
		strQuery.append(" and p.cve_Id_persona  = pd.cve_Id_persona(+) ");
		strQuery.append(" and gf.cve_Id_asignacion_nss = nssgf.cve_Id_asignacion_nss(+) ");
		strQuery.append(" and GF.CVE_ID_CALIDAD_PARENTESCO = cp.CVE_ID_CALIDAD_PARENTESCO(+) ");
		strQuery.append(" and umf.cve_id_clave_presupuestal =pres.cve_id_clave_presupuestal(+) ");
		strQuery.append(" and uc.cve_id_umf = umf.cve_id_umf(+) ");
		strQuery.append(" and uct.cve_id_umf_consultorio = uc.cve_id_umf_consultorio(+) ");
		strQuery.append(" and uctm.CVE_ID_UMF_CONS_TURNO = uct.CVE_ID_UMF_CONS_TURNO (+) ");
		strQuery.append(" and gf.CVE_ID_UMF_CONS_TURNO_MED = uctm.CVE_ID_UMF_CONS_TURNO_MED (+) ");
		log.debug("el query para consutlar a la persona y grupos familiares es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			sqlQuery.setParameter("curp", curp);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosBasicosPersonaGrupoFamiliar> lstPerGrpoFam =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosBasicosPersonaGrupoFamiliar.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstPerGrpoFam.size());
				return lstPerGrpoFam;
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consutlar a la persona y grupos familiares" , e);
			throw e;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliaresByIdee(String idee) throws Exception {
		
		StringBuffer strQueryIdeeCurp =new StringBuffer();
		strQueryIdeeCurp.append("SELECT P.CURP "); 
		strQueryIdeeCurp.append("FROM DIT_PERSONA P, dit_persona_derechohabiente pd ");
		strQueryIdeeCurp.append("WHERE P.CVE_ID_PERSONA = PD.CVE_ID_PERSONA ");
		strQueryIdeeCurp.append("AND pd.CVE_EXPEDIENTE_ELECTRONICO = :idee");
		
		
		
		log.debug("el query para consutlar a la CURP por idee es" +  strQueryIdeeCurp.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQueryIdeeCurp.toString());
			sqlQuery.setParameter("idee", idee);
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				String refCurp = (String)sqlQuery.list().get(0);
				log.debug("el CURP encontrado es" + refCurp);
				return getDatosBasicosPersonaGruposFamiliares(refCurp);
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consutlar a la persona y grupos familiares" , e);
			throw e;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosBasicosPersonaGfHistLab getDatosBasicosPersonaAseguradoGF(ConsultaPersonaGfHistLab personaBusqueda) throws Exception {
		StringBuffer strQuery = new StringBuffer();
		strQuery.append(" select P.CVE_ID_PERSONA as \"cveIdPersona\", P.NOM_NOMBRE as \"nombre\", P.NOM_PRIMER_APELLIDO as \"primerApellido\", P.NOM_SEGUNDO_APELLIDO as \"segundoApellido\", NSS.CVE_ID_ASIGNACION_NSS as \"cveIdAsignacionNss\" ,  ");
		strQuery.append(" P.CURP as \"refCurp\",NSS.NUM_NSS as \"numNssPersona\",  ");
		strQuery.append(" CP.DES_PARENTESCO as \"calidad\" , ");
		strQuery.append(" pres.CVE_PRESUPUESTAL as \"clavePresupuestal\",  ");
		strQuery.append(" umf.NOM_UNIDAD as \"nomUmf\", ");
		strQuery.append(" del.DES_DELEG as \"nomDelegacion\", ");
		strQuery.append(" NSS.FEC_REGISTRO_ALTA as \"fechaAltaAsegurado\", ");
		strQuery.append(" fdom.DOMICILIO_ID as \"domicilioId\"  ");
		strQuery.append(" from dit_persona p, dit_grupo_familiar gf,  ");
		strQuery.append(" dit_asignacion_nss nss,  DIC_CALIDAD_PARENTESCO cp, ");
		strQuery.append(" dic_umf umf, dic_clave_presupuestal pres,  ");
		strQuery.append(" DIC_CONSULTORIO_UMF uc, DIT_UMF_CONSULTORIO_TURNO uct,  DIT_UMF_CONS_TURNO_MEDICO uctm , ");
		strQuery.append(" dic_delegacion del, dic_subdelegacion sdel, "  );
		strQuery.append(" DIT_PERSONAF_DOM fdom "  );
		strQuery.append(" where 1=1 ");
		if(StringUtils.isNotBlank(personaBusqueda.getRefCurp()))
			strQuery.append(" and p.curp = :curp  ");
		if(StringUtils.isNotBlank(personaBusqueda.getRefRfc()))
			strQuery.append(" and p.rfc = :rfc  ");
		if(StringUtils.isNotBlank(personaBusqueda.getNumNss()))
			strQuery.append(" and nss.num_nss = :nss  ");
		strQuery.append(" and P.CVE_ID_PERSONA = nss.cve_Id_persona(+) ");
		// strQuery.append(" and p.cve_id_persona = gf.cve_id_persona_integrante(+) ");
		// strQuery.append(" and p.cve_Id_persona  = pd.cve_Id_persona(+) ");
		strQuery.append(" and nss.cve_Id_asignacion_nss = gf.cve_Id_asignacion_nss(+) ");
		strQuery.append(" and GF.CVE_ID_CALIDAD_PARENTESCO = cp.CVE_ID_CALIDAD_PARENTESCO(+) ");
		strQuery.append(" and umf.cve_id_clave_presupuestal =pres.cve_id_clave_presupuestal(+) ");
		strQuery.append(" and uc.cve_id_umf = umf.cve_id_umf(+) ");
		strQuery.append(" and uct.cve_id_umf_consultorio = uc.cve_id_umf_consultorio(+) ");
		strQuery.append(" and uctm.CVE_ID_UMF_CONS_TURNO = uct.CVE_ID_UMF_CONS_TURNO (+) ");
		strQuery.append(" and gf.CVE_ID_UMF_CONS_TURNO_MED = uctm.CVE_ID_UMF_CONS_TURNO_MED (+) ");
		strQuery.append(" and umf.CVE_ID_SUBDELEGACION = sdel.CVE_ID_SUBDELEGACION(+) ");
		strQuery.append(" and sdel.CVE_ID_DELEGACION = del.CVE_ID_DELEGACION(+) ");
		strQuery.append(" and gf.CVE_ID_PERSONAF_DOM = fdom.CVE_ID_PERSONAF_DOM (+) ");
		
		log.debug("el query para consutlar a la persona y grupos familiares es" +  strQuery.toString());
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
			if(StringUtils.isNotBlank(personaBusqueda.getRefCurp()))
				sqlQuery.setParameter("curp", personaBusqueda.getRefCurp());
			if(StringUtils.isNotBlank(personaBusqueda.getRefRfc()))
				sqlQuery.setParameter("rfc", personaBusqueda.getRefRfc());
			if(StringUtils.isNotBlank(personaBusqueda.getNumNss()))
				sqlQuery.setParameter("nss", personaBusqueda.getNumNss());
			
			List<Object> lstResultado = (List<Object>)sqlQuery.list();
			if(lstResultado != null && !lstResultado.isEmpty()) {
				List<DatosBasicosPersonaGfHistLab> lstPerGrpoFam =
						sqlQuery.setResultTransformer(Transformers.aliasToBean(DatosBasicosPersonaGfHistLab.class)).list(); 
				log.debug("la lista tiene los siguientes registros " + lstPerGrpoFam.size());
				return lstPerGrpoFam.get(0);
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consutlar a la persona y grupos familiares" , e);
			throw e;
		}
		return null;
	}
	
}
