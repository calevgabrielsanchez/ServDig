/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: JSM
 *  @Proyecto: IMSS DIGITAL
 *  @Archivo:FirmaClemServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:14/07/2020
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.SQLQuery;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClemVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.persistence.DivSolicitudConcluida;

@Stateless
public class FirmaClemServiceEntity  extends AbstractServiceEntity  implements FirmaClemServiceEntityLocal{
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<FirmaClemDTO> consultarClemPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada) {
		DatosSalidaPaginador<FirmaClemDTO> salida = new DatosSalidaPaginador<FirmaClemDTO>();
		List<FirmaClemDTO> clemList = null;
		FirmaClemDTO filtros = datosEntrada.getModelo();
		Integer inicio = datosEntrada.getiDisplayStart();
		Integer fin = datosEntrada.getiDisplayLength();
		
		int iTotalDisplayRecords = 0;
		
		Map<String, Object> resultado = this.buscarClemPaginado(filtros,inicio, fin);
		clemList = (List<FirmaClemDTO>) resultado.get("clemList");
		iTotalDisplayRecords = (Integer) resultado.get("total");
		log.debug("El inicio es: " + datosEntrada.getiDisplayStart());
		log.debug("Y se mostraran: " + datosEntrada.getiDisplayLength());
		
		salida.setAaData(clemList);
		salida.setiTotalDisplayRecords(iTotalDisplayRecords);
		salida.setiTotalRecords(iTotalDisplayRecords);
		
		return salida;
	}		
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> buscarClemPaginado(FirmaClemDTO filtros,Integer paginarInicio, Integer paginarFin) {
		Map<String, Object> salida = new HashMap<String, Object>();
		Long idDelegacion = filtros.getIdDelegacion();
		idDelegacion = idDelegacion != null && !idDelegacion.equals(-1L) ? idDelegacion : null;
		Long idSubdelegacion = filtros.getIdSubDelegacion();
		idSubdelegacion = idSubdelegacion != null && !idSubdelegacion.equals(-1L) ? idSubdelegacion : null;

		Integer total = 0;
		List<FirmaClemDTO> clemList = new ArrayList<FirmaClemDTO>();
		String queryClems = "";
		String queryListado = "";
		String condiciones = "";
		String queryCount = "";
		//Select para obtener el listado de rectificaciones por firmar
		String selectPrincipal = 
				"SELECT DSC.CVE_ID_SOLICITUD, DSC.CVE_ID_ESTATUS_ANALISIS, DSC.DES_CAUSAS_ANALISIS, DSC.CVE_ID_ANALISIS, " +
			           "DSC.CVE_ID_GRUPO_ANALISIS_CE, DSC.CVE_ID_DELEGACION, DSC.CVE_ID_SUBDELEGACION, DSC.DES_SUBDELEGACION, " + 
					   "DSC.DES_DELEG, TO_CHAR(DSC.FEC_PRESENTACION, 'DD/MM/YYYY'), DSC.REG_PATRON_COMPLETO, " + 
			           "DECODE(DSC.CVE_ID_TIPO_PERSONA, 1, DSC.NOM_NOMBRE, 2, DSC.DES_RAZON_SOCIAL) NOM_NOMBRE, " + 
					   "DSC.CVE_ID_TIPO_TRAMITE, DSC.DES_TIPO_TRAMITE, " + 
			           "1 IDMARCA, " +
			           "'SIN FIRMA' MARCA, " +
			           "DECODE(SUBSTR(DDC.NUM_FOLIO_RESOLUCION, LENGTH(DDC.NUM_FOLIO_RESOLUCION), LENGTH(DDC.NUM_FOLIO_RESOLUCION)),'D','DELEGACIONAL','SUBDELEGACIONAL') TIPOCLEM, "+
			            "CVE_ID_CLEM IDCLEM "
			           ;

		String fromPrincipal =  "FROM DIV_SOLICITUDCONCLUIDAS DSC " +
				                "JOIN DIT_DATOS_CLEM DDC ON DSC.CVE_ID_ANALISIS = DDC.CVE_ID_ANALISIS " +
				               "WHERE DSC.CVE_ID_ESTATUS_ANALISIS = 6 AND DDC.FEC_REGISTRO_BAJA IS NULL AND DDC.ACUSE IS NULL ";
				               
		if (filtros.getTipoClem().equals(Constantes.CLEM_INSCRIP_DEL) || filtros.getTipoClem().equals(Constantes.CLEM_INSCRIP_SUBDEL)) {
				       fromPrincipal += "AND DSC.CVE_ID_GRUPO_ANALISIS_CE = 1 ";
		}else{
		       		   fromPrincipal += "AND DSC.CVE_ID_GRUPO_ANALISIS_CE = 2 ";
		}
		
		if(filtros.getTipoClem().trim().equals(Constantes.CLEM_INSCRIP_DEL) || filtros.getTipoClem().trim().equals(Constantes.CLEM_MOD_DEL)){
			           fromPrincipal += "AND SUBSTR(DDC.NUM_FOLIO_RESOLUCION, LENGTH(DDC.NUM_FOLIO_RESOLUCION), LENGTH(DDC.NUM_FOLIO_RESOLUCION)) = 'D' ";
		}else{
					   fromPrincipal += "AND SUBSTR(DDC.NUM_FOLIO_RESOLUCION, LENGTH(DDC.NUM_FOLIO_RESOLUCION), LENGTH(DDC.NUM_FOLIO_RESOLUCION)) = 'S' ";
		}
							
		//Select para realizar el count
		String selectCount = "SELECT COUNT(*) ";

		if (null != filtros.getStrPeriodoInicio() && null != filtros.getStrPeriodoFin()) {
			condiciones += " AND TRUNC(FEC_PRESENTACION) BETWEEN TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPeriodoInicio())+"', 'yyyy-mm-dd') " +
					"AND TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPeriodoFin())+"', 'yyyy-mm-dd') ";
		}

		if(idDelegacion != null  && idDelegacion.intValue() != 0) {
			condiciones += " AND DSC.CVE_ID_DELEGACION = " + idDelegacion + " ";
		}		
		
		if(idSubdelegacion != null && idSubdelegacion.intValue() != 0) {
			condiciones += " AND DSC.CVE_ID_SUBDELEGACION = " + idSubdelegacion+ " ";
		}
		
		queryListado += selectPrincipal + " " + fromPrincipal + condiciones;
				
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryClems+="SELECT * FROM ("
						+ "SELECT RESULTADOS_.*,"
						+ "ROWNUM ROWNUM_ FROM (";
			}else if(paginarFin != null && paginarFin.intValue() != 0){
				queryClems +="SELECT * FROM (";
			}
		}
		
		queryClems += " " + queryListado;
		
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryClems+=" ORDER BY DSC.FEC_PRESENTACION ASC ) RESULTADOS_ "
						+ "WHERE ROWNUM <=" + (paginarInicio + paginarFin ) +""
								+ " ) WHERE ROWNUM_ > " + paginarInicio; 
			} else if(paginarFin != null && paginarFin.intValue() != 0){
				queryClems+=" ORDER BY DSC.FEC_PRESENTACION ASC) WHERE ROWNUM <= " + paginarFin +  " ";
			}
		} else {
			queryClems+=  " ORDER BY DSC.FEC_PRESENTACION ASC";
		}
		
		SQLQuery queryEjercicios = this.getSession().createSQLQuery(queryClems);
		
		List<Object[]> resultado = (List<Object[]>)queryEjercicios.list();
		if(!resultado.isEmpty()) {
			for(Object[] clem: resultado) {

				FirmaClemDTO fm = new FirmaClemDTO();
				fm.setIdSolicitud(((BigDecimal)clem[0]).longValue());
				fm.setIdStatus(((BigDecimal)clem[1]).longValue());
				fm.setStatus((String) clem[2]);
				fm.setCveIdAnalisis(((BigDecimal)clem[3]).longValue());
				fm.setCveIdGrupoAnalisisCe(((BigDecimal)clem[4]).longValue());
				fm.setIdDelegacion(((BigDecimal)clem[5]).longValue());
				fm.setIdSubDelegacion(((BigDecimal)clem[6]).longValue());
				fm.setSubdelegacion((String) clem[7]);
				fm.setDelegacion((String) clem[8]);
				fm.setFecPresentacion((String) clem[9]);
				fm.setRegistroPatronal((String) clem[10]);
				fm.setNombreRS((String) clem[11]);
				fm.setIdTipoTramite(((BigDecimal)clem[12]).longValue());
				fm.setTipoTramite((String) clem[13]);
				fm.setIdEstadoFirma(((BigDecimal)clem[14]).longValue());
				fm.setEstadoFirma((String) clem[15]);
				fm.setTipoClem((String) clem[16]);
				fm.setIdClem(((BigDecimal) clem[17]).longValue());			
				clemList.add(fm);
			}
		}
		
		if(paginarInicio != null || paginarFin != null) {
			queryCount = selectCount + fromPrincipal+condiciones;
			SQLQuery resCount = this.getSession().createSQLQuery(queryCount);
			total = ((BigDecimal) resCount.uniqueResult()).intValue();
		}
		salida.put("total", total);
		salida.put("clemList", clemList);
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<FirmaClemDTO> consultarClemCFPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada) {

		DatosSalidaPaginador<FirmaClemDTO> salida = new DatosSalidaPaginador<FirmaClemDTO>();
		List<FirmaClemDTO> clemList = null;
		FirmaClemDTO filtros = datosEntrada.getModelo();
		Integer inicio = datosEntrada.getiDisplayStart();
		Integer fin = datosEntrada.getiDisplayLength();
		
		int iTotalDisplayRecords = 0;
		
		Map<String, Object> resultado = this.buscarClemCFPaginado(filtros,inicio, fin);
		clemList = (List<FirmaClemDTO>) resultado.get("clemList");
		iTotalDisplayRecords = (Integer) resultado.get("total");
		log.debug("El inicio es: " + datosEntrada.getiDisplayStart());
		log.debug("Y se mostraran: " + datosEntrada.getiDisplayLength());
		
		salida.setAaData(clemList);
		salida.setiTotalDisplayRecords(iTotalDisplayRecords);
		salida.setiTotalRecords(iTotalDisplayRecords);
		
		return salida;

	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> buscarClemCFPaginado(FirmaClemDTO filtros,Integer paginarInicio, Integer paginarFin) {
		Map<String, Object> salida = new HashMap<String, Object>();
		Long idDelegacion = filtros.getIdDelegacion();
		idDelegacion = idDelegacion != null && !idDelegacion.equals(-1L) ? idDelegacion : null;
		Long idSubdelegacion = filtros.getIdSubDelegacion();
		idSubdelegacion = idSubdelegacion != null && !idSubdelegacion.equals(-1L) ? idSubdelegacion : null;

		Integer total = 0;
		List<FirmaClemDTO> clemList = new ArrayList<FirmaClemDTO>();
		String queryClems = "";
		String queryListado = "";
		String condiciones = "";
		String queryCount = "";
		//Select para obtener el listado de rectificaciones firmadas
		String selectPrincipal = 
				"SELECT DSC.CVE_ID_SOLICITUD, DSC.CVE_ID_ESTATUS_ANALISIS, DSC.DES_CAUSAS_ANALISIS, DSC.CVE_ID_ANALISIS, " +
			           "DSC.CVE_ID_GRUPO_ANALISIS_CE, DSC.CVE_ID_DELEGACION, DSC.CVE_ID_SUBDELEGACION, DSC.DES_SUBDELEGACION, " + 
					   "DSC.DES_DELEG, TO_CHAR(DSC.FEC_PRESENTACION, 'DD/MM/YYYY'), DSC.REG_PATRON_COMPLETO, " + 
			           "DECODE(DSC.CVE_ID_TIPO_PERSONA, 1, DSC.NOM_NOMBRE, 2, DSC.DES_RAZON_SOCIAL) NOM_NOMBRE, " + 
					   "DSC.CVE_ID_TIPO_TRAMITE, DSC.DES_TIPO_TRAMITE, " + 
			           "DDC.ACUSE ACUSE "
			           ;

		String fromPrincipal =  "FROM DIV_SOLICITUDCONCLUIDAS DSC " +
				                "JOIN DIT_DATOS_CLEM DDC ON DSC.CVE_ID_ANALISIS = DDC.CVE_ID_ANALISIS " +
				               "WHERE DSC.CVE_ID_ESTATUS_ANALISIS = 6 AND DDC.ACUSE IS NOT NULL AND DDC.FEC_REGISTRO_BAJA IS NULL ";
				               
							
		//Select para realizar el count
		String selectCount = "SELECT COUNT(*) ";

		if (null != filtros.getStrPeriodoInicio() && null != filtros.getStrPeriodoFin()) {
			condiciones += " AND TRUNC(DSC.FEC_PRESENTACION) BETWEEN TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPeriodoInicio())+"', 'yyyy-mm-dd') " +
					"AND TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPeriodoFin())+"', 'yyyy-mm-dd') ";
		}

		if (null != filtros.getStrPerIniF() && null != filtros.getStrPerFinF()) {
			condiciones += " AND TRUNC(DDC.FEC_REGISTRO_ACTUALIZADO) BETWEEN TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPerIniF())+"', 'yyyy-mm-dd') " +
					"AND TO_DATE('"+Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtros.getStrPerFinF())+"', 'yyyy-mm-dd') ";
		}

		if(null != filtros.getRegistroPatronal() && 0 != filtros.getRegistroPatronal().trim().length()){
			condiciones +=  " AND DSC.REG_PATRON_COMPLETO = '" + filtros.getRegistroPatronal() + "' ";
		}
		
		if(idDelegacion != null  && idDelegacion.intValue() != 0) {
			condiciones += " AND DSC.CVE_ID_DELEGACION = " + idDelegacion + " ";
		}		
		
		if(idSubdelegacion != null && idSubdelegacion.intValue() != 0) {
			condiciones += " AND DSC.CVE_ID_SUBDELEGACION = " + idSubdelegacion+ " ";
		}
		
		queryListado += selectPrincipal + " " + fromPrincipal + condiciones;
				
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryClems+="SELECT * FROM ("
						+ "SELECT RESULTADOS_.*,"
						+ "ROWNUM ROWNUM_ FROM (";
			}else if(paginarFin != null && paginarFin.intValue() != 0){
				queryClems +="SELECT * FROM (";
			}
		}
		
		queryClems += " " + queryListado;
		
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryClems+=" ORDER BY DSC.FEC_PRESENTACION ASC ) RESULTADOS_ "
						+ "WHERE ROWNUM <=" + (paginarInicio + paginarFin ) +""
								+ " ) WHERE ROWNUM_ > " + paginarInicio; 
			} else if(paginarFin != null && paginarFin.intValue() != 0){
				queryClems+=" ORDER BY DSC.FEC_PRESENTACION ASC) WHERE ROWNUM <= " + paginarFin +  " ";
			}
		} else {
			queryClems+=  " ORDER BY DSC.FEC_PRESENTACION ASC";
		}
		
		SQLQuery queryEjercicios = this.getSession().createSQLQuery(queryClems);
		
		List<Object[]> resultado = (List<Object[]>)queryEjercicios.list();
		if(!resultado.isEmpty()) {
			for(Object[] clem: resultado) {
				FirmaClemDTO fm = new FirmaClemDTO();
				fm.setIdSolicitud(((BigDecimal)clem[0]).longValue());
				fm.setIdStatus(((BigDecimal)clem[1]).longValue());
				fm.setStatus((String) clem[2]);
				fm.setCveIdAnalisis(((BigDecimal)clem[3]).longValue());
				fm.setCveIdGrupoAnalisisCe(((BigDecimal)clem[4]).longValue());
				fm.setIdDelegacion(((BigDecimal)clem[5]).longValue());
				fm.setIdSubDelegacion(((BigDecimal)clem[6]).longValue());
				fm.setSubdelegacion((String) clem[7]);
				fm.setDelegacion((String) clem[8]);
				fm.setFecPresentacion((String) clem[9]);
				fm.setRegistroPatronal((String) clem[10]);
				fm.setNombreRS((String) clem[11]);
				fm.setIdTipoTramite(((BigDecimal)clem[12]).longValue());
				fm.setTipoTramite((String) clem[13]);
				fm.setAcuse((String) clem[14]);
				clemList.add(fm);
			}
		}
		
		if(paginarInicio != null || paginarFin != null) {
			queryCount = selectCount + fromPrincipal+condiciones;
			SQLQuery resCount = this.getSession().createSQLQuery(queryCount);
			total = ((BigDecimal) resCount.uniqueResult()).intValue();
		}
		salida.put("total", total);
		salida.put("clemList", clemList);
		
		return salida;
	}	 
	
			@SuppressWarnings("unchecked")
			@Override
	public List<ResolucionVO> obtenerDatosClemFirmaMasiva(List<Long> idAnalisis){
			
		String queryListado = "";
		String condiciones = "";
		String SEPARATOR = ",";
		List<ResolucionVO> datos = new ArrayList<ResolucionVO>(); 
		StringBuilder registrosPat = new StringBuilder();

		String selectPrincipal = "SELECT DISTINCT cm.NUM_FOLIO_RESOLUCION, d.DES_DELEG, sd.DES_SUBDELEGACION,"
				+ "CASE WHEN SL.CVE_ID_TIPO_PERSONA = '1' THEN SL.NOM_NOMBRE ELSE DECODE(dts.DES_TIPO_SOCIEDAD_ABREV, NULL, sl.DES_RAZON_SOCIAL, sl.DES_RAZON_SOCIAL || ', ' || dts.DES_TIPO_SOCIEDAD_ABREV) END DES_RAZON_SOCIAL,"
				+ "dgdg.NOMVIAL || CASE  WHEN dgdg.NUMEXTNUM IS NOT NULL  THEN ', N° EXT. ' || dgdg.NUMEXTNUM ELSE '' END || CASE WHEN dgdg.NUMEXTALF IS NOT NULL THEN ' ' || dgdg.NUMEXTALF END "
				+ "|| CASE  WHEN dgdg.NUMINTNUM IS NOT NULL  THEN ', N° INT. ' || dgdg.NUMINTNUM ELSE '' END || CASE WHEN dgdg.NUMINTALF IS NOT NULL THEN ' ' || dgdg.NUMINTALF  END"
				+ "||CASE  WHEN DGASEN.NOM_ASEN IS NOT NULL  THEN ', COLONIA ' || DGASEN.NOM_ASEN ELSE '' END||CASE  WHEN dgdg.CODIGO IS NOT NULL  THEN ', CP '  ||dgdg.CODIGO || ', ' ELSE '' END|| DGESt.NOM_ENT  DIRECCION,"
				+ "DGMUN.NOM_MUN, sl.REG_PATRON_COMPLETO, TO_CHAR(SYSDATE,'DD/MM/YYYY') FECHA_ACTUAL, "
				+ "				   div.NUM_DIVISION, grup.NUM_GRUPO, frac.NUM_FRACCION, frac.DES_FRACCION, clas.DES_CLASE, clas.NUM_PRIMA_MEDIA, TO_CHAR(tf.FEC_PRESENTACION,'DD/MM/YYYY') FEC_PRESENTACION,"
				+ "				   REPLACE(cm.DES_MOTIVOS, chr(13)||chr(10), '&#13;') DES_MOTIVOS,"
								  //REPLACE(cm.DES_MOTIVOS, chr(13)||chr(10), '\\\\u000D') DES_MOTIVOS, \" +\r\n"
							   //REPLACE(cm.DES_MOTIVOS, chr(13)||chr(10), '\\\\r\\\\n') DES_MOTIVOS, \" +\r\n"
							   //REPLACE(cm.DES_MOTIVOS, chr(13)||chr(10), ' ') DES_MOTIVOS, \" +\r\n"
				+ "				   divpro.NUM_DIVISION NUM_DIVISION_PRO, fracpro.NUM_FRACCION NUM_FRACCION_PRO, gruppro.NUM_GRUPO NUM_GRUPO_PRO, DIVPRO.DES_DIVISION DES_DIVISION_PRO, gruppro.DES_GRUPO DES_GRUPO_PRO,"
				+ "				   claspro.DES_CLASE DES_CLASE_PRO, claspro.NUM_PRIMA_MEDIA NUM_PRIMA_MEDIA_PRO, fracpro.DES_FRACCION DES_FRACCION_PRO, cm.DES_TITULAR, cm.DES_SUPLENTE, cm.PUESTO, cm.DES_LUGAR_FECHA_EXP,"
				+ "				   CASE WHEN sl.CVE_ID_TIPO_PERSONA = '1' THEN 'física' ELSE 'moral' END TIPO_PERSONA, d.CVE_ID_DELEGACION, sd.CVE_ID_SUBDELEGACION, cm.CVE_ID_CLEM, t.CVE_ID_TIPO_TRAMITE, ce.CVE_ID_ANALISIS,"
				+ "                TO_CHAR(tf.FEC_EFECTO,'DD/MM/YYYY') FEC_EFECTO, sl.IND_PRESTA_SERVICIO_PERSONAL, dsr.DES_DELEG DES_DELEG_RISS, dsr.DES_SUBDELEGACION DES_SUBDELEGACION_RISS, dsr.TIPO , pro.PRIMA_SUGERIDA, pso.CVE_ID_PATRON_SUJETO_OBLIGADO";
		
		String fromPrincipal = "FROM DIT_ANALISIS_CE ce " +
				  "JOIN DIT_TRAMITE t ON (t.FEC_REGISTRO_BAJA IS NULL AND t.CVE_ID_ESTADO_TRAMITE = 2 AND t.CVE_ID_TIPO_TRAMITE = 74 " +
				  					     "AND ce.CVE_ID_ESTATUS_ANALISIS = 6 AND ce.CVE_ID_SOLICITUD = t.CVE_ID_SOLICITUD) " +
				  "JOIN DIT_TRAMITE tf ON (tf.FEC_REGISTRO_BAJA IS NULL AND tf.CVE_ID_ESTADO_TRAMITE = 2 AND ce.CVE_ID_SOLICITUD = tf.CVE_ID_SOLICITUD) " +
				  "JOIN DIC_MODULO_TIPO_TRAMITE mtt ON(mtt.CVE_ID_MODULO = 5 AND mtt.CVE_ID_TIPO_TRAMITE = tf.CVE_ID_TIPO_TRAMITE) " +
				  "JOIN DIT_GRUPO_TRAMITE_ANALISIS_CE gtace ON(gtace.CVE_ID_MODULO = 5 AND gtace.CVE_ID_TIPO_TRAMITE = tf.CVE_ID_TIPO_TRAMITE) " +				               
				  "JOIN DIV_SOLICITUDCONCLUIDAS SL ON (SL.CVE_ID_ANALISIS = ce.CVE_ID_ANALISIS) " +
				  "JOIN DIT_TRAMITE_PAT_SUJ_OBLIGADO tpso ON (tpso.CVE_ID_TRAMITE = t.CVE_ID_TRAMITE) " +
				  "JOIN DIT_PATRON_SUJETO_OBLIGADO pso ON(pso.CVE_ID_PATRON_SUJETO_OBLIGADO = tpso.CVE_ID_PATRON_SUJETO_OBLIGADO) " +
				  "LEFT JOIN DIT_PERSONA_MORAL dpm ON(dpm.FEC_REGISTRO_BAJA IS NULL AND pso.CVE_ID_PERSONA_MORAL = dpm.CVE_ID_PERSONA_MORAL) " +
				  "LEFT JOIN DIC_TIPO_SOCIEDAD dts ON(dpm.CVE_ID_TIPO_SOCIEDAD = dts.CVE_ID_TIPO_SOCIEDAD) " +
				  "LEFT JOIN DIT_PAT_SUJ_OBLIG_DOMICILIO psod ON (pso.CVE_ID_PATRON_SUJETO_OBLIGADO = psod.CVE_ID_PATRON_SUJETO_OBLIGADO) " +
				  "LEFT JOIN DG_DOMICILIO_GEOGRAFICO dgdg ON (psod.DOMICILIO_ID = dgdg.DOMICILIO_ID) " +
				  "LEFT JOIN DG_ASENTAMIENTO dgasen ON (dgasen.CVE_ASEN = dgdg.CVE_ASEN) " +
				  "LEFT JOIN DG_CAT_ESTADO dgest ON (dgest.CVE_ENT = DGDG.CVE_ENT) " +
				  "LEFT JOIN DG_CAT_MUNICIPIO dgmun ON (dgmun.CVE_ENT = dgdg.CVE_ENT AND dgmun.CVE_MUN = DGDG.CVE_MUN) " +
				  "LEFT JOIN DIT_DELSUB_PAT_SUJ_OBLIG dspso ON(dspso.FEC_REGISTRO_BAJA IS NULL AND dspso.CVE_ID_PATRON_SUJETO_OBLIGADO = tpso.CVE_ID_PATRON_SUJETO_OBLIGADO) " +
				  "JOIN DIC_SUBDELEGACION sd ON(sd.CVE_ID_SUBDELEGACION = dspso.CVE_ID_SUBDELEGACION) " +
				  "JOIN DIC_DELEGACION d ON(d.CVE_ID_DELEGACION = sd.CVE_ID_DELEGACION) " +
				  "JOIN DIT_DATOS_CLEM CM ON (cm.FEC_REGISTRO_BAJA is null AND cm.CVE_ID_ANALISIS = ce.CVE_ID_ANALISIS) " +
				  "JOIN DIT_CLASIFICACION_PROPUESTA pro ON (pro.CVE_ID_ANALISIS = ce.CVE_ID_ANALISIS) " +
				  "JOIN DIT_HIST_ESTATUS_ANALISIS hist ON (hist.CVE_ID_ANALISIS = ce.CVE_ID_ANALISIS AND hist.CVE_ID_ESTATUS_ANALISIS = 1 AND hist.CVE_USUARIO_SSO IS NULL) " +
				  "JOIN DIC_FRACCION frac ON (frac.CVE_ID_FRACCION = hist.CVE_ID_FRACCION_DEC) " +
				  "JOIN DIC_FRACCION_CLASE fclas ON (fclas.FEC_FIN IS NULL AND fclas.CVE_ID_FRACCION = frac.CVE_ID_FRACCION) " +
				  "JOIN DIC_CLASE clas ON (clas.CVE_ID_CLASE = fclas.CVE_ID_CLASE) " +
				  "JOIN DIC_GRUPO grup ON (grup.CVE_ID_GRUPO = frac.CVE_ID_GRUPO) " +
				  "JOIN DIC_DIVISION div ON (div.CVE_ID_DIVISION = grup.CVE_ID_DIVISION) " +
				  "JOIN DIC_FRACCION fracpro ON (fracpro.CVE_ID_FRACCION = pro.CVE_ID_FRACCION) " +
				  "JOIN DIC_FRACCION_CLASE fclaspro ON (fclaspro.FEC_FIN IS NULL AND fclaspro.CVE_ID_FRACCION = fracpro.CVE_ID_FRACCION) " +
				  "JOIN DIC_CLASE claspro ON (claspro.CVE_ID_CLASE = fclaspro.CVE_ID_CLASE) " +
				  "JOIN DIC_GRUPO gruppro ON (gruppro.CVE_ID_GRUPO = fracpro.CVE_ID_GRUPO) " +
				  "JOIN DIC_DIVISION divpro ON (divpro.CVE_ID_DIVISION = gruppro.CVE_ID_DIVISION) " +
				  "LEFT JOIN DIC_SUBDELEGACION_RIMSS dsr ON(d.CLAVE_DELEGACION=dsr.CVE_DELEGACION AND sd.CLAVE_SUBDELEGACION = dsr.CVE_SUBDELEGACION) ";		
		
		
		for(Long id: idAnalisis){
			registrosPat.append(id);
			registrosPat.append(SEPARATOR);
		}
		
		String patronesCondicion = registrosPat.toString();
		patronesCondicion = patronesCondicion.substring(0, patronesCondicion.length() - 1);
		System.out.println("PATRONEEEEESSSSS:  IN " + patronesCondicion);
		
		condiciones = "WHERE 1=1 " +
			"AND CE.CVE_ID_ANALISIS IN("  + patronesCondicion +  ")";
			
		queryListado += selectPrincipal + " " + fromPrincipal + " " + condiciones;
				
		SQLQuery queryEjercicios = this.getSession().createSQLQuery(queryListado);
		
		List<Object[]> resultado = (List<Object[]>)queryEjercicios.list();
		
		if(!resultado.isEmpty()) {
			for(Object[] clem: resultado) {
				ClemVO dat = new ClemVO();
				dat.setFolioClem(clem[0] != null ?(String) clem[0] : null);
				dat.setDelegacion(clem[1] != null ? (String) clem[1] : null);
				dat.setSubdelegacion(clem[2] != null ? (String) clem[2] : null);
				String razonSocial= clem[3] != null ?(String) clem[3] : null;
				dat.setRazonSocial(covertirHTMLaString (razonSocial));
				//Validacion si el domicilio esta en BDTU, de lo contrario consultaria Domicilio Migrado.
				if(clem[4] != null) {
					dat.setDomicilio((String) clem[4]);
				}
				else {
					String domMigrado = sujetoObligadoService.obtenerDomicilioMigrado(Long.parseLong(clem[40].toString()));
					dat.setDomicilio(domMigrado);
				}				
				dat.setDomicilio(dat.getDomicilio().toUpperCase());
				dat.setMunicipioDelegacion(clem[5] != null ? (String) clem[5] : " ");
				dat.setRegPatronal(clem[6] != null ? (String) clem[6] : null);
				dat.setFechaAviso(clem[7] != null ? (String) clem[7].toString() : null);
				
				dat.setIdDivisionPatron(clem[8] != null ? clem[8].toString() : null);
				dat.setIdGrupoPatron(clem[9] != null ? dat.getIdDivisionPatron() + (String) clem[9].toString() : null);
				dat.setIdFraccionPatron(clem[10] != null ? dat.getIdGrupoPatron() + (String) clem[10].toString() : null);
				dat.setDenominacionFraccion(clem[11] != null ? (String) clem[11].toString() : null);
				dat.setClase(clem[12] != null ? (String) clem[12].toString() : null);
				dat.setPrima(clem[13] != null ? (String) clem[13].toString() : null);
				
				dat.setFechaTramite(clem[14] != null ? (String) clem[14].toString() : null);
				dat.setMotivos(clem[15] != null ? (String) clem[15] : null);
				dat.setMotivos(dat.getMotivos().toUpperCase());

				dat.setIdDivisionPropuesta(clem[16] != null ? (String) clem[16].toString() : null);
				dat.setIdGrupoPropuesta(clem[18] != null ? dat.getIdDivisionPropuesta()+ (String) clem[18].toString() : null);
				dat.setIdFraccionPropuesta(clem[17] != null ? dat.getIdGrupoPropuesta() + (String) clem[17].toString() : null);
				dat.setDivisionPropuesta(clem[19] != null ?(String) clem[19].toString() : null);
				dat.setGrupoPropuesta(clem[20] != null ? (String) clem[20].toString() : null);
				dat.setClasePropuesta(clem[21] != null ? (String) clem[21].toString() : null);
				dat.setPrimaPropuesta(clem[22] != null ? (String) clem[22].toString() : null);
				dat.setFraccionPropuesta(clem[23] != null ? (String) clem[23].toString() : null);

				dat.setTitular(clem[24] != null ? (String) clem[24] : "");
				dat.setSuplente(clem[25] != null ? (String) clem[25] : null);
				dat.setPuesto(clem[26] != null ? (String) clem[26] : "");
				dat.setLugarFechaExpedicion(clem[27] != null ? (String) clem[27] : null);
				dat.setLugarFechaExpedicion(dat.getLugarFechaExpedicion().toUpperCase());
				dat.setTipoPersona(clem[28] != null ? (String) clem[28].toString() : null);
				dat.setIdDelegacion(clem[29] != null ? (String) clem[29].toString() : null);
				dat.setIdSubdelegacion(clem[30] != null ? (String) clem[30].toString() : null);
				dat.setIdClem(clem[31] != null ? (String) clem[31].toString() : null);
				dat.setTipoTramite(clem[32] != null ? (String) clem[32].toString() : null);
				dat.setFechaSurteEfecto(clem[34] != null ? (String) clem[34].toString() : null);
				
//AQUI REVISAR AQUI VALOR DE PSP A 2				
				String indPsp = clem[35] != null ? (String) clem[35].toString() : "";
				dat.setPspArt15A(indPsp.trim().equals("1") ? "15-A, " : "");
				dat.setPspArt19(indPsp.trim().equals("1") ? "19, " : "");
				dat.setTipo(clem[38] != null ? (String) clem[38] : null);
				
				if(clem[36] != null &&  clem[37]!= null){
					dat.setDelegacion((String) clem[38] + " " + (String) clem[36]);
					dat.setSubdelegacion((String) clem[37]);
				}
				//Se verifica si prima sugerida es nulo o no
				if (clem[39]!=null) {
					log.info("-------------Entro a la condicion de prima sugerida");
					dat.setPrimaPropuesta(clem[39] != null ? (String) clem[39].toString() : null);
				}
				
				
				ResolucionVO resolucionVO = new ResolucionVO();
				System.out.println("idddd analisis " + clem[33].toString());
				resolucionVO.setIdAnalisis(clem[33].toString());
				resolucionVO.setClemVO(dat);
				datos.add(resolucionVO);				
			}
		}
		
		return datos;	
	} 
			private String covertirHTMLaString (String texto) {
				if(texto == null) {
				return null;
				}
				String textoConvertido="";
				String[][] caracteresHTML = {{"&Ntilde;","Ñ"}, {"&ntilde;","ñ"}, {"&aacute;","á"}, {"&Aacute;","Á"}, {"&eacute;","é"}, {"&Eacute;","É"},
				{"&amp;","&"},{"&iacute;","í"}, {"&Iacute;","Í"}, {"&Oacute;","Ó"}, {"&oacute;","ó"}, {"&Uacute;","Ú"}, {"&uacute;","ú"}, {"&quot;","\""} };
				for (int i=0; i< caracteresHTML.length; i++) {
				textoConvertido = texto.replace(caracteresHTML[i][0],caracteresHTML[i][1]);
				}
				return textoConvertido;
				}

}