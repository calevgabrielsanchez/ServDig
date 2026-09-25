package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.dictamen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;


@Stateless(name = "dictamenEntity", mappedName="dictamenEntity")
public class DictamenEntity extends AbstractServiceEntity implements DictamenEntityLocal{
	
	/*@PersistenceContext(unitName="dictamenPersistenceUnit")
	protected EntityManager emDictamen;*/

	@Override
	@SuppressWarnings("unchecked")
	public List<EjercicioDictamen> getPeriodosDictamen() {
		List<EjercicioDictamen> ejercicios = new ArrayList<EjercicioDictamen>();
		String query ="select CVE_ID_EJER_FISCAL, DES_EJER_FISCAL from NDC_EJERCICIO_FISCAL order by DES_EJER_FISCAL desc";

		SQLQuery queryEjercicios = this.getSession().createSQLQuery(query);
		
		List<Object[]> resultado = (List<Object[]>)queryEjercicios.list();
		if(!resultado.isEmpty()) {
			for(Object[] ejercicio: resultado) {
				Long idEjercicio = ((BigDecimal)ejercicio[0]).longValue();
				String desEjercicio = (String) ejercicio[1];
				ejercicios.add(new EjercicioDictamen(idEjercicio, desEjercicio));
			}
		}
		
		return ejercicios;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DictamenDTO> buscarDictamentes(Long idDelegacion,
			Long idSubdelegacion, Long idPeriodo) {
		List<DictamenDTO> dictamenes = null;
		DictamenDTO filtros = new DictamenDTO();
		filtros.setIdDelegacion(idDelegacion);
		filtros.setIdSubDelegacion(idSubdelegacion);
		filtros.setIdEjercicio(idPeriodo);
		Map<String, Object> result = this.buscarDictamentesPaginado(filtros, null, null);
		dictamenes = (List<DictamenDTO>) result.get("dictamenes");
		return dictamenes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<DictamenDTO> consultarDictamentesPaginado(
			DatosEntradaPaginador<DictamenDTO> datosEntrada) {
		DatosSalidaPaginador<DictamenDTO> salida = new DatosSalidaPaginador<DictamenDTO>();
		List<DictamenDTO> dictamenes = null;
		DictamenDTO filtros = datosEntrada.getModelo();
		Integer inicio = datosEntrada.getiDisplayStart();
		Integer fin = datosEntrada.getiDisplayLength();
		
		int iTotalDisplayRecords = 0;
		
		Map<String, Object> resultado = this.buscarDictamentesPaginado(filtros,inicio, fin);
		dictamenes = (List<DictamenDTO>) resultado.get("dictamenes");
		iTotalDisplayRecords = (Integer) resultado.get("total");
		log.debug("el inicio es: " + datosEntrada.getiDisplayStart());
		log.debug("Y se modtraran: " + datosEntrada.getiDisplayLength());
		
		salida.setAaData(dictamenes);
		salida.setiTotalDisplayRecords(iTotalDisplayRecords);
		salida.setiTotalRecords(iTotalDisplayRecords);
		
		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictamenDTO> buscarDictamentes(DictamenDTO filtros) {
		// TODO Auto-generated method stub
		Map<String, Object> salida = this.buscarDictamentesPaginado(filtros, null, null);
		List<DictamenDTO> datosDictamen = (List<DictamenDTO>) salida.get("dictamenes");
		return datosDictamen;
	}

	//Se modifica para buscar y regresar el RP a 10 dogitos 5041454 / WO1920294
	@SuppressWarnings("unchecked")
	private Map<String, Object> buscarDictamentesPaginado(DictamenDTO filtros,Integer paginarInicio, Integer paginarFin) {
		Map<String, Object> salida = new HashMap<String, Object>();
		String nrp = filtros.getRegistroPatronal();
		Long idDelegacion = filtros.getIdDelegacion();
		idDelegacion = idDelegacion != null && !idDelegacion.equals(-1L) ? idDelegacion : null;
		Long idSubdelegacion = filtros.getIdSubDelegacion();
		idSubdelegacion = idSubdelegacion != null && !idSubdelegacion.equals(-1L) ? idSubdelegacion : null;
		Long idEjercicio = filtros.getIdEjercicio();
		idEjercicio = idEjercicio != null && !idEjercicio.equals(-1L) ? idEjercicio : null;
		Long idEstatusMac = filtros.getIdStatus();
		idEstatusMac = idEstatusMac != null && !idEstatusMac.equals(-1L) ? idEstatusMac : null;
		boolean isQueryPendiente = idEstatusMac != null && idEstatusMac.equals(1L);
		
		Integer total = 0;
		List<DictamenDTO> dictamenes = new ArrayList<DictamenDTO>();
		String queryDictamenes = "";
		String queryListado = "";
		String condiciones = "";
		String queryPendiente = "";
		String queryCount = "";
		//Select para obtener el listado de dictamenes
		String selectPrincipal = "SELECT DICT.CVE_ID_PATRON_DICTAMEN CVE_ID_PATRON_DICTAMEN, "+
		           "EJER.DES_EJER_FISCAL PERIODO, "+
		           "DICT.DES_RFC, "+
		           "DICT.DES_NOMBRE_RAZON_SOCIAL, "+
		           "SUBSTR(PATRON_ASOCIADO.REG_PATRON_ASOCIADO,0,10), "+
		           "SUBDEL.CVE_ID_SUBDELEGACION, "+
		           "SUBDEL.DES_SUBDELEGACION, "+
		           "DELEG.CVE_ID_DELEGACION, "+
		           "DELEG.DES_DELEG, "+
		           "llave.CVE_ID_PATRON_SUJETO_OBLIGADO, "+
		           "TRAM.CVE_ID_SOLICITUD, "+
		           "ESTATUS.CVE_ID_ESTATUS_ANALISIS, "+
		           "ESTATUS.DES_CAUSAS_ANALISIS, "+
		           "ANALISIS.CVE_USUARIO_SSO ";
		//Select para realizar el count
		String selectCount = "SELECT COUNT(*) ";
		//From para las consultas
		String fromPrincipal = "FROM   NDT_PATRON_DICTAMEN DICT "+
       "JOIN NDT_PATRON_ASOCIADO PATRON_ASOCIADO ON PATRON_ASOCIADO.CVE_ID_PATRON_DICTAMEN = DICT.CVE_ID_PATRON_DICTAMEN "+
       "JOIN NDC_EJERCICIO_FISCAL EJER ON DICT.CVE_ID_EJER_FISCAL = EJER.CVE_ID_EJER_FISCAL "+
       "JOIN DIT_LLAVE_PATRON llave on llave.REF_BUSCA = SUBSTR(PATRON_ASOCIADO.REG_PATRON_ASOCIADO,1,10) "+
       "JOIN DIT_PATRON_SUJETO_OBLIGADO sujeto on sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO = llave.CVE_ID_PATRON_SUJETO_OBLIGADO "+
       "JOIN DIT_DELSUB_PAT_SUJ_OBLIG del_pat on sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO = del_pat.CVE_ID_PATRON_SUJETO_OBLIGADO "+
       "JOIN DIC_SUBDELEGACION SUBDEL ON SUBDEL.CVE_ID_SUBDELEGACION = del_pat.CVE_ID_SUBDELEGACION "+
       "JOIN DIC_DELEGACION DELEG ON SUBDEL.CVE_ID_DELEGACION = DELEG.CVE_ID_DELEGACION "+
       "LEFT OUTER JOIN  DIT_TRAMITE_DICTAMEN TRAM_SO ON TRAM_SO.CVE_ID_PATRON_SUJETO_OBLIGADO = sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO " + 
       "AND TRAM_SO.CVE_ID_PATRON_DICTAMEN= DICT.CVE_ID_PATRON_DICTAMEN "+
       "LEFT OUTER JOIN   DIT_TRAMITE TRAM ON TRAM_SO.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE  "+
       "LEFT OUTER JOIN  DIT_ANALISIS_CE ANALISIS ON TRAM.CVE_ID_SOLICITUD = ANALISIS.CVE_ID_SOLICITUD  "+
       "LEFT OUTER JOIN   DIC_ESTATUS_ANALISIS_CE ESTATUS ON ESTATUS.CVE_ID_ESTATUS_ANALISIS = ANALISIS.CVE_ID_ESTATUS_ANALISIS  "+
       "WHERE DICT.CVE_ID_ESTADO_DICTAMEN = 6 AND DICT.CVE_ID_EJER_FISCAL =" + idEjercicio;
		
		
		queryPendiente += selectPrincipal +  " " +fromPrincipal;
		
		if(idEstatusMac != null) {
			if(idEstatusMac.equals(1L)) {
				queryPendiente += " AND TRAM.CVE_ID_SOLICITUD IS NULL ";
			}
			fromPrincipal += " AND ANALISIS.CVE_ID_ESTATUS_ANALISIS = " + idEstatusMac;
		}
		
		if(idSubdelegacion != null) {
			condiciones += " AND  SUBDEL.CVE_ID_SUBDELEGACION=" + idSubdelegacion+ " ";
		}
		
		if(idDelegacion != null) {
			condiciones += " AND DELEG.CVE_ID_DELEGACION=" + idDelegacion + " ";
		}
		
		
		if(!StringUtils.isBlank(nrp)) {
			condiciones += " AND SUBSTR(PATRON_ASOCIADO.REG_PATRON_ASOCIADO,0,10)='"+nrp+"' ";
		}
		queryListado += selectPrincipal + " " +fromPrincipal+condiciones;
		queryPendiente+=""+condiciones;
		
		
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryDictamenes+="select * from ("
						+ "select resultados_.*,"
						+ "rownum rownum_ from (";
			}else if(paginarFin != null && paginarFin.intValue() != 0){
				queryDictamenes +="select * from (";
			}
		}
		
		queryDictamenes += " " + queryListado;
		if(isQueryPendiente) {
			queryDictamenes += " UNION " + queryPendiente;
		}
		
		if(paginarInicio != null) {
			if(paginarInicio.intValue() != 0 && (paginarFin != null && paginarFin.intValue() != 0)) {
				queryDictamenes+=" order by CVE_ID_PATRON_DICTAMEN asc ) resultados_ "
						+ "where rownum <=" + (paginarInicio + paginarFin ) +""
								+ " ) where rownum_ > " + paginarInicio; 
			} else if(paginarFin != null && paginarFin.intValue() != 0){
				queryDictamenes+=" order by CVE_ID_PATRON_DICTAMEN asc) where rownum <= " + paginarFin +  " ";
			}
		} else {
			queryDictamenes+=  " order by CVE_ID_PATRON_DICTAMEN asc";
		}
		SQLQuery queryEjercicios = this.getSession().createSQLQuery(queryDictamenes);
		
		List<Object[]> resultado = (List<Object[]>)queryEjercicios.list();
		if(!resultado.isEmpty()) {
			for(Object[] dictamen: resultado) {
				Long patronDictamen = ((BigDecimal)dictamen[0]).longValue();
				String desEjercicio = (String) dictamen[1];
				String rfc = (String) dictamen[2];
				String razonSocial = (String) dictamen[3];
				String registroPatronal = (String) dictamen[4];
				Long idSubdel = ((BigDecimal)dictamen[5]).longValue();
				String desSubdel = (String) dictamen[6];
				Long idDel = ((BigDecimal)dictamen[7]).longValue();
				String desDel = (String) dictamen[8];
				Long cveIdPatronSO = ((BigDecimal)dictamen[9]).longValue();
				BigDecimal cveIdSolicitud = ((BigDecimal)dictamen[10]);
				Long idSolicitud = cveIdSolicitud != null ? cveIdSolicitud.longValue() : null;				
				BigDecimal id_estatus = (BigDecimal) dictamen[11];
				String estatus = (String) dictamen[12];
				String usuario = (String) dictamen[13];
				
				DictamenDTO dictamenDto = new DictamenDTO(patronDictamen, registroPatronal, razonSocial, idEjercicio, desEjercicio, idDel, idSubdel, desSubdel, desDel, cveIdPatronSO,rfc);
				if(id_estatus != null) {
					dictamenDto.setIdStatus(new Long(id_estatus.toString()));
				}
				if(idSolicitud != null) {
					log.error("se busco la solicitud de dictamen para el patron " +cveIdPatronSO + " y se encontro la solicitud " + idSolicitud);
					dictamenDto.setIdSolicitud(idSolicitud);
					dictamenDto.setUsuario(usuario);
					dictamenDto.setStatus(estatus.replaceAll("RECTIFICADO", "ENVIAR A REVISION").replaceAll("RECTIFICACION", "ENVIAR A REVISION"));
				} else {
					dictamenDto.setStatus("PENDIENTE DE ANALISIS");
					dictamenDto.setUsuario("SIN ASIGNAR");
				}
				
				dictamenes.add(dictamenDto);
			}
		}
		
		if(paginarInicio != null || paginarFin != null) {
			queryCount = isQueryPendiente ? selectCount + " from (" + queryListado + " UNION " + queryPendiente + ")" : selectCount + fromPrincipal+condiciones;
			SQLQuery resCount = this.getSession().createSQLQuery(queryCount);
			total = ((BigDecimal) resCount.uniqueResult()).intValue();
		}
		salida.put("total", total);
		salida.put("dictamenes", dictamenes);
		
		return salida;
	}
	
	@Override
	public Clasificacion getClasificacionDictamen(Long idPatronDictamen, String regPatronal) {
		StringBuilder queryBuilder =  new StringBuilder("SELECT ");
		queryBuilder.append(" NUM_FRACCION_EDICT, DES_CLASE_EDICT, IMP_PRIMA_2_EDICT ");
		queryBuilder.append(" FROM NDT_A7_6_CE_ACT_COMP ");
		queryBuilder.append(" WHERE CVE_ID_PATRON_DICTAMEN = ").append(idPatronDictamen);
		queryBuilder.append(" AND SUBSTR(REG_PATRONAL,1,10) = '").append(regPatronal).append("'");
		queryBuilder.append(" AND FEC_REGISTRO_BAJA IS NULL ").append(" ORDER BY FEC_REGISTRO_ACTUALIZADO DESC");
		
		SQLQuery queryClasificacion = this.getSession().createSQLQuery(queryBuilder.toString());
		
		List<Object[]> resultado = (List<Object[]>)queryClasificacion.list();
		Clasificacion clasificacion = new Clasificacion();
		
		if(!resultado.isEmpty()) {
			for(Object[] clasificacionEntity: resultado) {
				
				String clasificacionString = (String)clasificacionEntity[0];
				String descripcionClase = (String) clasificacionEntity[1];
				BigDecimal prima = (BigDecimal) clasificacionEntity[2];
				
				if(clasificacionString == null && descripcionClase == null && prima == null) {
					return null;
				}
				
				String div = "";
				String grp = "";
				String frc = "";
				
				if(clasificacionString.length() == 4){
					div = clasificacionString.substring(0,1);
					grp = clasificacionString.substring(1,2);
					frc = clasificacionString.substring(2,4);
				}else if(clasificacionString.length() == 3){
					div = clasificacionString.substring(0,1);
					grp = clasificacionString.substring(1,2);
					frc = clasificacionString.substring(2,3);					
				}
				
				clasificacion.setFraccion(new Fraccion());
				clasificacion.getFraccion().setNumFraccion(frc);
				clasificacion.getFraccion().setClase(new Clase());
				clasificacion.getFraccion().getClase().setDescripcion(descripcionClase);
				clasificacion.setPrimaSRTActual(prima);
				
				clasificacion.getFraccion().setGrupo(new Grupo());
				clasificacion.getFraccion().getGrupo().setNumGrupo(grp);
				clasificacion.getFraccion().getGrupo().setDivision(new Division());
				clasificacion.getFraccion().getGrupo().getDivision().setNumDivision(div);				
								
			}
		}else{
			clasificacion = null;
		}

		return clasificacion;
	}
	
	
	/*
	private DictamenDTO getIdSolicitudDictamen(Long cveIdPatronSO) {
		DictamenDTO dictamen = null;
		Long idSolicitud = null;
		
		String querySolicitudDictamen = "select TRAM.CVE_ID_SOLICITUD, ANALISIS.CVE_USUARIO_SSO, ESTATUS.DES_CAUSAS_ANALISIS "
		+"from DIT_TRAMITE_PAT_SUJ_OBLIGADO TRAM_SO "
		+"INNER JOIN DIT_TRAMITE TRAM ON TRAM_SO.CVE_ID_TRAMITE = TRAM.CVE_ID_TRAMITE "
		+"INNER JOIN DIT_ANALISIS_CE ANALISIS ON TRAM.CVE_ID_SOLICITUD = ANALISIS.CVE_ID_SOLICITUD "
		+"INNER JOIN DIC_ESTATUS_ANALISIS_CE ESTATUS ON ESTATUS.CVE_ID_ESTATUS_ANALISIS = ANALISIS.CVE_ID_ESTATUS_ANALISIS "
		+"WHERE TRAM.CVE_ID_TIPO_TRAMITE = 167 AND TRAM_SO.CVE_ID_PATRON_SUJETO_OBLIGADO ="+ cveIdPatronSO;
		
		SQLQuery resCount = this.getSession().createSQLQuery(querySolicitudDictamen);
		Object[] resultado = (Object[]) resCount.uniqueResult();
		if(resultado != null) {
			idSolicitud = ((BigDecimal) resultado[0]).longValue();
			dictamen = new DictamenDTO();
			dictamen.setIdSolicitud(idSolicitud);
			dictamen.setUsuario((String) resultado[1]);
			dictamen.setStatus((String) resultado[2]);
			
		}
		
		
		return dictamen;
	}

	private Session getSessionDictamen(){

		//LA FORMA DE RECUPERAR LA SESION DE HIBERNATE CAMBIA ENTRE APLICATION SERVERS, NO EXISTE UNA FORMA UNIFICADA
		Session session = null;
		//LA SIGUIENTE FORMA DE RECUPERAR LA SESION LA UTILIZA WEBLOGIC
		session = (Session) emDictamen.getDelegate();
	    return session;
	}*/

}

