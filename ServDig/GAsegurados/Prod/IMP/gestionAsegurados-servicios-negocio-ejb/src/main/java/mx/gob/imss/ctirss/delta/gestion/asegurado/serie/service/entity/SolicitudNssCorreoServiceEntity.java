package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SolicitudNssCorreoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssCorreoPK;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

@Stateless(mappedName = "solicitudNssCorreoServiceEntity")
public class SolicitudNssCorreoServiceEntity extends AbstractServiceEntity
		implements SolicitudNssCorreoServiceEntityLocal {

	@EJB
	private SolicitudNssCorreoServiceUtilityLocal solicitudNssCorreoServiceUtility;

	
	@Override
	public void actualizarCurpACorreo(String correo, String curp) {
		String queryActualizacionCurp = "update DIT_SOLICITUD_NSS_CORREO set REF_CURP = '" + curp + "', FEC_REGISTRO_ALTA = SYSDATE , FEC_REGISTRO_BAJA = NULL ";

    	queryActualizacionCurp += " where REF_CORREO_ELECTRONICO = '" + correo + "'";
		
		this.getSession().createSQLQuery(queryActualizacionCurp).executeUpdate();
	}
	
	
    private boolean esGmail(String correo){
    	return correo.contains("@gmail.com");
    }
    
    private String removerPuntos(String correo){
    	//SPLIT DEL ARROBA
    	String correoSinDominio = correo.split("@")[0];
    	return correoSinDominio.replace(".", "") + "@gmail.com";
    }
	
	@Override
	public void actualizarBajaCorreoPorCurp(String curp) {
	    String queryActualizacionFecha = "update DIT_SOLICITUD_NSS_CORREO set FEC_REGISTRO_BAJA = SYSDATE";
	    queryActualizacionFecha += " where REF_CURP = '" + curp + "'";

	    this.getSession().createSQLQuery(queryActualizacionFecha).executeUpdate();
	    log.debug("----SE DIERON DE BAJA CORRECTAMENTE LOS CORREOS VINCULADOS AL CURP -----");
	}
	
	@Override
	public void actualizarAltaCorreo(String correo) {
	    String queryActualizacionFecha = "update DIT_SOLICITUD_NSS_CORREO set FEC_REGISTRO_BAJA = NULL";

    	queryActualizacionFecha += " where REF_CORREO_ELECTRONICO = '" + correo + "'";
	    
	    this.getSession().createSQLQuery(queryActualizacionFecha).executeUpdate();
	    log.debug("----SE DIO DE ALTA CORRECTAMENTE EL CORREO -----");
	}
	
	@Override
	public void bajaCorreoExcepto(String curp, String correo) {
	    String queryActualizacionFecha = "update DIT_SOLICITUD_NSS_CORREO set FEC_REGISTRO_BAJA = SYSDATE";
	    queryActualizacionFecha += " where REF_CURP = '" + curp + "' and REF_CORREO_ELECTRONICO != '" + correo + "'";

	    this.getSession().createSQLQuery(queryActualizacionFecha).executeUpdate();
	    log.debug("----SE DIERON DE BAJA CORRECTAMENTE LOS CORREOS EXCEPTO -----" + correo);
	}
	
	@Override
	public List<String> consultarUMF (ArrayList<Integer> numerosGenerados) {
		log.debug("-----Entro a la consulta de las UMF ----");
	    Session session = this.getSession();
	    
	    StringBuilder inClause = new StringBuilder("(");
	    for (Integer numero : numerosGenerados) {
	        inClause.append(numero).append(",");
	    }
	    inClause.deleteCharAt(inClause.length() - 1); // Elimina la ?ltima coma
	    inClause.append(")");

	    String query = "SELECT UMF.CVE_ID_UMF, DES_DELEG || ' '|| DES_SUBDELEGACION || ' ' || nvl(umf.nom_corto, umf.nom_unidad) UMF " +
	            "FROM MGPBDTU9X.DIC_UMF UMF " +
	            "LEFT JOIN DIC_SUBDELEGACION SUBDEL ON UMF.CVE_ID_SUBDELEGACION = SUBDEL.CVE_ID_SUBDELEGACION " +
	            "LEFT JOIN DIC_DELEGACION DELEG ON DELEG.CVE_ID_DELEGACION = SUBDEL.CVE_ID_DELEGACION " +
	            "WHERE umf.cve_id_umf IN " + inClause.toString();
	    
	    log.debug("el query a ejecutar es: " + query);

	    SQLQuery queryUMF = session.createSQLQuery(query);
	    List<Object[]> resultados = queryUMF.list();

	    List<String> resultadoFinal = new ArrayList<String>();
	    for (Object[] row : resultados) {
	    	String umf = row[0] + "|" + (row[1] != null ? row[1].toString() : "");
	        resultadoFinal.add(umf);
	    }
	    
	    log.debug("el resultado final en el entity es: " + resultadoFinal );

	    return resultadoFinal;
	}
	
	@Override
	public String consultarSubdelegacion(Integer cveIdSubdelegacion) {
	    log.debug("-----Entro a la consulta de la subdelegacion----");
	    Session session = this.getSession();
	    
	    String queryStr = "SELECT DES_SUBDELEGACION " +
	                      "FROM MGPBDTU9X.DIC_SUBDELEGACION " +
	                      "WHERE cve_id_subdelegacion = :cveId";
	    
	    log.debug("El query a ejecutar es: " + queryStr);
	    
	    SQLQuery query = session.createSQLQuery(queryStr);
	    query.setParameter("cveId", cveIdSubdelegacion);

	    List<String> resultados = query.list();
	    
	    if (resultados == null || resultados.isEmpty()) {
	        log.debug("No se encontro subdelegacion con clave: " + cveIdSubdelegacion);
	        return null;
	    }

	    String resultadoFinal = resultados.get(0);
	    log.debug("El resultado final en el entity es: " + resultadoFinal);
	    
	    return resultadoFinal;
	}
	
	@Override
	public Map<String, String> consultarDatosUsuario(String curp) {
	    log.debug("----- Entro a la consulta de los datos de usuario ----");
	    Session session = this.getSession();

	    String query = "SELECT CVE_MATRICULA, NOM_NOMBRE, NOM_PATERNO, NOM_MATERNO " +
	            "FROM MGPSSOA1.SSO_SOLICITUD WHERE DES_USR_CURP = :curp";

	    log.debug("el query a ejecutar es: " + query);

	    SQLQuery queryUsuario = session.createSQLQuery(query);
	    queryUsuario.setParameter("curp", curp);
	    
	    Object[] resultado = (Object[]) queryUsuario.uniqueResult();

	    Map<String, String> resultadoFinal = new HashMap<String, String>();
	    if (resultado != null) {
	        resultadoFinal.put("CVE_MATRICULA", resultado[0] != null ? resultado[0].toString() : "");
	        resultadoFinal.put("NOM_NOMBRE", resultado[1] != null ? resultado[1].toString() : "");
	        resultadoFinal.put("NOM_PATERNO", resultado[2] != null ? resultado[2].toString() : "");
	        resultadoFinal.put("NOM_MATERNO", resultado[3] != null ? resultado[3].toString() : "");
	    }

	    log.debug("el resultado final en el entity es: " + resultadoFinal);

	    return resultadoFinal;
	}

	
	@Override
	public List<String> getListaRfcPatronesFisica (List<Long> ids) {
		log.debug("-----Entro a la consulta de la lista de los RFC's fisica----");
	    Session session = this.getSession();
	    
	    StringBuilder inClause = new StringBuilder("(");
	    for (Long numero : ids) {
	        inClause.append(numero).append(",");
	    }
	    inClause.deleteCharAt(inClause.length() - 1); // Elimina la ?ltima coma
	    inClause.append(")");

	    String query = "SELECT (SELECT RFC FROM DIT_PERSONA_FISICA WHERE CVE_ID_PERSONA_FISICA = DPSO.CVE_ID_PERSONA_FISICA) RFC_PF FROM MGPBDTU9X.DIT_PATRON_GENERAL DPG " +
	    		"INNER JOIN MGPBDTU9X.DIT_PATRON_SUJETO_OBLIGADO DPSO ON DPSO.CVE_ID_PATRON_SUJETO_OBLIGADO = DPG.CVE_ID_PATRON_SUJETO_OBLIGADO " +
	    		"INNER JOIN MGPBDTU9X.DIC_MODALIDAD DM ON DM.CVE_ID_MODALIDAD = DPSO.CVE_ID_MODALIDAD " +
	    		"WHERE DPG.CVE_ID_PATRON_GENERAL in " + inClause.toString();
	    
	    log.debug("el query a ejecutar es: " + query);

	    SQLQuery queryRFCfisica = session.createSQLQuery(query);
	    List<Object> resultados = queryRFCfisica.list();

	    List<String> resultadoFinal = new ArrayList<String>();
	    for (Object result : resultados) {
	        resultadoFinal.add(result != null ? result.toString() : "");
	    }

	    log.debug("el resultado final en el entity es: " + resultadoFinal);

	    return resultadoFinal;
	}
	
	@Override
	public List<String> getListaRfcPatronesMoral (List<Long> ids) {
		log.debug("-----Entro a la consulta de la lista de los RFC's moral----");
	    Session session = this.getSession();
	    
	    StringBuilder inClause = new StringBuilder("(");
	    for (Long numero : ids) {
	        inClause.append(numero).append(",");
	    }
	    inClause.deleteCharAt(inClause.length() - 1); // Elimina la ?ltima coma
	    inClause.append(")");

	    String query = "SELECT (SELECT RFC FROM DIT_PERSONA_MORAL WHERE CVE_ID_PERSONA_MORAL = DPSO.CVE_ID_PERSONA_MORAL) RFC_PM FROM MGPBDTU9X.DIT_PATRON_GENERAL DPG " +
	    		"INNER JOIN MGPBDTU9X.DIT_PATRON_SUJETO_OBLIGADO DPSO ON DPSO.CVE_ID_PATRON_SUJETO_OBLIGADO = DPG.CVE_ID_PATRON_SUJETO_OBLIGADO " +
	    		"INNER JOIN MGPBDTU9X.DIC_MODALIDAD DM ON DM.CVE_ID_MODALIDAD = DPSO.CVE_ID_MODALIDAD " +
	    		"WHERE DPG.CVE_ID_PATRON_GENERAL in " + inClause.toString();
	    
	    log.debug("el query a ejecutar es: " + query);

	    SQLQuery queryRFCfisica = session.createSQLQuery(query);
	    List<Object> resultados = queryRFCfisica.list();

	    List<String> resultadoFinal = new ArrayList<String>();
	    for (Object result : resultados) {
	        resultadoFinal.add(result != null ? result.toString() : "");
	    }

	    log.debug("el resultado final en el entity es: " + resultadoFinal);

	    return resultadoFinal;
	}
	
	@Override
	public List<String> getListaRegistroPatronal (List<Long> ids) {
		log.debug("-----Entro a la consulta de la lista de los registros patronales----");
	    Session session = this.getSession();
	    
	    StringBuilder inClause = new StringBuilder("(");
	    for (Long numero : ids) {
	        inClause.append(numero).append(",");
	    }
	    inClause.deleteCharAt(inClause.length() - 1); // Elimina la ?ltima coma
	    inClause.append(")");

	    String query = "SELECT REG_PATRON FROM MGPBDTU9X.DIT_PATRON_GENERAL DPG " +
	    		"INNER JOIN MGPBDTU9X.DIT_PATRON_SUJETO_OBLIGADO DPSO ON DPSO.CVE_ID_PATRON_SUJETO_OBLIGADO = DPG.CVE_ID_PATRON_SUJETO_OBLIGADO " +
	    		"INNER JOIN MGPBDTU9X.DIC_MODALIDAD DM ON DM.CVE_ID_MODALIDAD = DPSO.CVE_ID_MODALIDAD " +
	    		"WHERE DPG.CVE_ID_PATRON_GENERAL in " + inClause.toString();
	    
	    log.debug("el query a ejecutar es: " + query);

	    SQLQuery queryRFCfisica = session.createSQLQuery(query);
	    List<Object> resultados = queryRFCfisica.list();

	    List<String> resultadoFinal = new ArrayList<String>();
	    for (Object result : resultados) {
	        resultadoFinal.add(result != null ? result.toString() : "");
	    }

	    log.debug("el resultado final en el entity es: " + resultadoFinal);

	    return resultadoFinal;
	}

	@Override
	public SolicitudNssCorreo obtenerPorCorreo(String correo, Long idSolicitud) {
		DitSolicitudNssCorreo entity = null;
		
		if(idSolicitud != null){
			DitSolicitudNssCorreoPK pk = new DitSolicitudNssCorreoPK();
			pk.setCveIdTipoSolicitud(idSolicitud);
			pk.setRefCorreoElectronico(correo);
			
			entity = this.em.find(
					DitSolicitudNssCorreo.class, pk);
		}
		else{
			Query query = this.em.createNamedQuery("findByCorreo", DitSolicitudNssCorreo.class);
			query.setParameter("correo", correo);
			List<DitSolicitudNssCorreo> entityList = query.getResultList();
			if(entityList != null && !entityList.isEmpty()){
				entity = entityList.get(0);
			}
			
		}

		SolicitudNssCorreo model = null;

		try {
			model = this.solicitudNssCorreoServiceUtility
					.transformarFromEntity(entity);
		} catch (TransformacionException e) {
			this.log.error(e);
		}
		
		if(model != null){
			if(esGmail(model.getCorreo().getCorreo())){
				model.getCorreo().setCorreo(removerPuntos(model.getCorreo().getCorreo()));
			}			
		}

		return model;
	}
	
	@Override
	public SolicitudNssCorreo obtenerPorCurp(String curp, Long idSolicitud) {
		DitSolicitudNssCorreo entity = null;
		
		Query query = this.em.createQuery("FROM DitSolicitudNssCorreo dit WHERE dit.refCurp = :curp" + (idSolicitud != null? " AND dit.pk.cveIdTipoSolicitud = :idSolicitud": "")
				+ " ORDER BY dit.fecRegistroAlta DESC", DitSolicitudNssCorreo.class);
		if (idSolicitud != null) {
			query.setParameter("idSolicitud", idSolicitud);
		}
		query.setParameter("curp", curp);
		List<DitSolicitudNssCorreo> entityList = query.getResultList();
		if(entityList != null && !entityList.isEmpty()){
			entity = entityList.get(0);
		}

		SolicitudNssCorreo model = null;

		try {
			model = this.solicitudNssCorreoServiceUtility
					.transformarFromEntity(entity);
		} catch (TransformacionException e) {
			this.log.error(e);
		}
		
		if(model != null){
			if(esGmail(model.getCorreo().getCorreo())){
				model.getCorreo().setCorreo(removerPuntos(model.getCorreo().getCorreo()));
			}			
		}

		return model;
	}
		
	@Override
	public String obtieneMedioContactoCorreo(String idPersona) {
		Session session = this.getSession();
		String correo = "";
		String query ="select DES_FORMA_CONTACTO from DIT_PERSONAF_CONTACTO dpc "+
				"inner join DIT_FORMA_CONTACTO dfc on dpc.CVE_ID_FORMA_CONTACTO=dfc.CVE_ID_FORMA_CONTACTO "+
				"where dpc.CVE_ID_PERSONA="+ idPersona +" and CVE_ID_TIPO_CONTACTO=1 order by dfc.FEC_REGISTRO_ALTA DESC";
		
		SQLQuery queryNSS = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<String> resultado = (List<String>)queryNSS.list();
		if(!resultado.isEmpty()) {
			correo = resultado.get(0);
		}
		return correo;
	}

	@Override
	public Object[] obtenerSolicitudPorCurpPeriodo(String curp, int diasPeriodo) {

		return obtenerSolicitudPorCurpPeriodoCommon(curp, diasPeriodo, null);

	}

	@Override
	public Object[] obtenerSolicitudPorCurpPeriodo(String curp, int diasPeriodo, Long idTipoSolicitud) {

		return obtenerSolicitudPorCurpPeriodoCommon(curp, diasPeriodo, idTipoSolicitud);

	}

	private Object[] obtenerSolicitudPorCurpPeriodoCommon (String curp, int diasPeriodo, Long idTipoSolicitud) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date hoy = c.getTime();

		c.add(Calendar.DAY_OF_YEAR, -(diasPeriodo-1));

		Criteria criteria = this.getSession().createCriteria(DitSolicitudNssCorreo.class);
		criteria.add(Restrictions.eq("refCurp", curp));

		if (idTipoSolicitud != null) {
			criteria.add(Restrictions.eq("pk.cveIdTipoSolicitud", idTipoSolicitud));
		}

		criteria.add(Restrictions.between("fecConsulta", c.getTime(),hoy));

		criteria.setProjection(Projections.projectionList().add(Projections.sum("numConteoSolicitudPeriodo"))
				.add(Projections.max("fecConsulta")));

		log.info("Result: " + criteria.uniqueResult());

		Object[] result =(Object[]) criteria.uniqueResult();

		if (result != null && result[0] != null) {
			return new Object[] { ((Long) result[0]).intValue(), result[1] };
		} else {
			c.add(Calendar.DAY_OF_YEAR, -1);
			return new Object[] { 0, c.getTime() };
		}
	}

	@Override
	public void guardar(SolicitudNssCorreo model)
			throws SolicitudNssCorreoException {

		DitSolicitudNssCorreo entity = null;

		try {
			entity = this.solicitudNssCorreoServiceUtility
					.transformarFromModel(model);

			entity.setFecRegistroAlta(new Date());
			entity.setNumConteoSolicitudPeriodo(1L);

			this.em.persist(entity);
		} catch (TransformacionException e) {
			this.log.error(e);
			throw new SolicitudNssCorreoException(
					"No se pudo guardar la relacion correo-CURP");
		}
	}

	@Override
	public void registrarConsultaCorreoNSS(String correo, Long idSolicitud) {
		
		DitSolicitudNssCorreoPK pk = new DitSolicitudNssCorreoPK();
		pk.setCveIdTipoSolicitud(idSolicitud);
		pk.setRefCorreoElectronico(correo);
		
		DitSolicitudNssCorreo entity = this.em.find(
				DitSolicitudNssCorreo.class, pk);
		
		// Se registra la consulta del NSS
		entity.setNumConteoSolicitudPeriodo(entity.getNumConteoSolicitudPeriodo() + 1);
		entity.setFecConsulta(new Date());
	}
	
	@Override
	public void reiniciarConsultaCorreoNSS(String correo, Long idSolicitud) {
		
		DitSolicitudNssCorreoPK pk = new DitSolicitudNssCorreoPK();
		pk.setCveIdTipoSolicitud(idSolicitud);
		pk.setRefCorreoElectronico(correo);
		
		DitSolicitudNssCorreo entity = this.em.find(
				DitSolicitudNssCorreo.class, pk);
		
		// Se settea en 1, ya que este reinicio ya cuenta como consulta
		entity.setNumConteoSolicitudPeriodo(1L);
		entity.setFecConsulta(new Date());
		
	}

	@Override
	public void guardarConfirmacion(SolicitudNssConfirmacion model)
			throws SolicitudNssCorreoException {
		DitSolicitudNssConfirmacion entity = null;
		try {
			entity = this.transformarFromModelConf(model);
			this.em.persist(entity);
		} catch (TransformacionException e) {
			this.log.error(e);
			throw new SolicitudNssCorreoException(
					"No se pudo guardar la relacion correo-CURP");
		}
	}

	@Override
	public void actualizarConfirmacionCorreo(SolicitudNssConfirmacion model) {
		String queryActualizacionCurp = "update DIT_SOLICITUD_NSS_CONFIRMACION set REF_TOKEN = '"
				+ model.getToken()
				+ "', REF_CORREO_ELECTRONICO = '"
				+ model.getCorreo().getCorreo()
				+ "', IND_VIGENTE = 0"
				+ ", FEC_REGISTRO_ALTA = :fecha"	;
		queryActualizacionCurp += " where REF_CURP = '" + model.getCurp() + "'" + " and CVE_ID_TIPO_SOLICITUD = "
				+ model.getCveIdTipoSolicitud();
		SQLQuery query = this.getSession().createSQLQuery(queryActualizacionCurp);
		query.setParameter("fecha", new java.sql.Date(model.getFechaTokenActualizacion().getTime())).executeUpdate();
	}

	@Override
	public void actualizarConfirmacionCorreoVigencia(SolicitudNssConfirmacion model) {
		String queryActualizacionCurp = "update DIT_SOLICITUD_NSS_CONFIRMACION set IND_VIGENTE = "
				+ model.getVigente();
		queryActualizacionCurp += " where REF_CURP = '" + model.getCurp() + "'" + " and CVE_ID_TIPO_SOLICITUD = "
				+ model.getCveIdTipoSolicitud() + " AND REF_CORREO_ELECTRONICO = '"
						+ model.getCorreo().getCorreo()	+ "'";
		SQLQuery query = this.getSession().createSQLQuery(queryActualizacionCurp);
		query.executeUpdate();
	}

	@Override
	public SolicitudNssConfirmacion buscarConfirmacionCorreo(String curp,String correo, Long tipoSolicitud) throws TransformacionException{
		List<DitSolicitudNssConfirmacion> results = null;
		SolicitudNssConfirmacion result = null;
		Criteria criteria = this.getSession().createCriteria(DitSolicitudNssConfirmacion.class);
		criteria.add(Restrictions.eq("refCurp", curp));
		criteria.add(Restrictions.eq("cveIdTipoSolicitud", tipoSolicitud));
		if(correo != null){
			criteria.add(Restrictions.eq("refCorreoElectronico", correo));
		}
		results = criteria.list();

		if (results != null && results.size() > 0) {

			System.out.println(results.size() + "tama?o resultado");
			DitSolicitudNssConfirmacion sol = results.get(0);
			if(sol != null){
			result = this.transformarFromEntity(sol);
			}
			else{
				System.out.println("la curp no se encontro");
				return null;

			}
		}
		else{

			System.out.print("result es null");
		}
		return result;
	}
	
	@Override
	public List<SolicitudNssCorreo> obtenerPorCorreo(String correo) {			
		Query query = this.em.createNamedQuery("findByCorreo", DitSolicitudNssCorreo.class);
		query.setParameter("correo", correo);
		
		List<SolicitudNssCorreo> curps = null;
		List<DitSolicitudNssCorreo> entityList = query.getResultList();
		if(entityList != null && !entityList.isEmpty()){
			curps = new ArrayList<SolicitudNssCorreo>(entityList.size());
			
			for(DitSolicitudNssCorreo entity: entityList) {
				try {
					SolicitudNssCorreo model = this.solicitudNssCorreoServiceUtility
							.transformarFromEntity(entity);
					
					if(model != null){
						if(esGmail(model.getCorreo().getCorreo())){
							model.getCorreo().setCorreo(removerPuntos(model.getCorreo().getCorreo()));
						}			
					}
					
					curps.add(model);
				} catch (TransformacionException e) {
					this.log.error(e);
				}
			}
		}
		
		return curps;
	}
	
	@Override
	public List<SolicitudNssCorreo> obtenerPorCurp(String curp) {		
		
		Query query = this.em.createQuery("FROM DitSolicitudNssCorreo dit WHERE dit.refCurp = :curp"
//				+ " AND dit.fecRegistroBaja IS NULL" 
				+ " ORDER BY dit.fecRegistroAlta DESC", DitSolicitudNssCorreo.class);
		query.setParameter("curp", curp);
		
		List<SolicitudNssCorreo> correos = null;
		List<DitSolicitudNssCorreo> entityList = query.getResultList();
		if(entityList != null && !entityList.isEmpty()){
			correos = new ArrayList<SolicitudNssCorreo>(entityList.size());
			
			for(DitSolicitudNssCorreo entity: entityList) {
				try {
					SolicitudNssCorreo model = this.solicitudNssCorreoServiceUtility
							.transformarFromEntity(entity);
					
					if(model != null){
						if(esGmail(model.getCorreo().getCorreo())){
							model.getCorreo().setCorreo(removerPuntos(model.getCorreo().getCorreo()));
						}			
					}
					
					correos.add(model);
				} catch (TransformacionException e) {
					this.log.error(e);
				}
			}
		}
		
		return correos;
	}
	
	@Override
	public List<SolicitudNssCorreo> obtenerPorCurpCorreosActivos (String curp) {		
		
		Query query = this.em.createQuery("FROM DitSolicitudNssCorreo dit WHERE dit.refCurp = :curp"
				+ " AND dit.fecRegistroBaja IS NULL" 
				+ " ORDER BY dit.fecRegistroAlta DESC", DitSolicitudNssCorreo.class);
		query.setParameter("curp", curp);
		
		List<SolicitudNssCorreo> correos = null;
		List<DitSolicitudNssCorreo> entityList = query.getResultList();
		if(entityList != null && !entityList.isEmpty()){
			correos = new ArrayList<SolicitudNssCorreo>(entityList.size());
			
			for(DitSolicitudNssCorreo entity: entityList) {
				try {
					SolicitudNssCorreo model = this.solicitudNssCorreoServiceUtility
							.transformarFromEntity(entity);
					
					if(model != null){
						if(esGmail(model.getCorreo().getCorreo())){
							model.getCorreo().setCorreo(removerPuntos(model.getCorreo().getCorreo()));
						}			
					}
					
					correos.add(model);
				} catch (TransformacionException e) {
					this.log.error(e);
				}
			}
		}
		
		return correos;
	}

	private DitSolicitudNssConfirmacion transformarFromModelConf(SolicitudNssConfirmacion model)
			throws TransformacionException {
		if (model == null) {
			throw new TransformacionException();
		}
		DitSolicitudNssConfirmacion entity = new DitSolicitudNssConfirmacion();
		entity.setCveIdTipoSolicitud(model.getCveIdTipoSolicitud());
		entity.setRefCorreoElectronico(model.getCorreo().getCorreo());
		entity.setRefCurp(model.getCurp());
		entity.setRefCToken(model.getToken());
		entity.setVigente(model.getVigente());
		entity.setFechaAlta(new java.sql.Date(model.getFechaTokenActualizacion().getTime()));
		return entity;
	}

	private SolicitudNssConfirmacion transformarFromEntity(DitSolicitudNssConfirmacion entity)
			throws TransformacionException {
		if (entity == null) {
			throw new TransformacionException();
		}
		CorreoElectronico correo = new CorreoElectronico();
		correo.setCorreo(entity.getRefCorreoElectronico());
		SolicitudNssConfirmacion model = new SolicitudNssConfirmacion();
		model.setCorreo(correo);
		model.setCurp(entity.getRefCurp());
		model.setCveIdTipoSolicitud( entity.getCveIdTipoSolicitud());
		model.setToken(entity.getRefCToken());
		model.setId(entity.getCveIdNssConf());
		model.setVigente(entity.getVigente());
		model.setFechaTokenActualizacion(entity.getFechaAlta());
		return model;
	}

	@Override
	public boolean consultaDominioCorreo(String dominio) {

		log.info("Consultando si el dominio " + dominio + " no se encuentra bloqueado");

		String query = "SELECT count(*) FROM DIC_DOMINIO_CORREO_PERMITIDOS "
				+ "WHERE DOMINIO_CORREO = :dominio" ;
		SQLQuery querySQL = this.getSession().createSQLQuery(query);
		long count = ((Number) querySQL.setParameter("dominio", dominio).uniqueResult()).longValue();

		return count > 0;
	}
	
	@Override
	public boolean consultaCorreoDuplicado(String correo, String curp) {

		log.info("Consultando si el correo " + correo + "  esta en uso por otra CURP");
		String query =
				"SELECT ref_correo_electronico from MGPBDTU9X.dit_solicitud_nss_correo"
				+ " where ref_curp != :curp"
				+ " AND REPLACE(ref_correo_electronico, '.', '') = REPLACE(:correo, '.', '')"
				+ " AND ROWNUM = 1";
		
		
		SQLQuery querySQL = this.getSession().createSQLQuery(query);
		querySQL.setParameter("correo", correo);
		querySQL.setParameter("curp", curp);
		List<?> resultados = querySQL.list();
		
		if (resultados != null && !resultados.isEmpty()) {
		    // Hay al menos una fila
			log.info("Existe el correo registrado a otra CURP");
		    return true;
		} else {
		    // No hubo resultados
			log.info("No existe el correo registrado a otra CURP");
		    return false;
		}
	}

	@Override
	public String getToken() {
	    log.debug("-----Entro a la consulta del token----");
	    Session session = this.getSession();

	    String queryStr = "SELECT DES_TOKEN " +
	                      "FROM MGPBDTU9X.DIC_TOKEN_ACT_CORREO " +
	                      "WHERE FEC_REGISTRO_BAJA IS NULL";

	    log.debug("El query a ejecutar es: " + queryStr);

	    SQLQuery query = session.createSQLQuery(queryStr);

	    List<String> resultados = query.list();

	    if (resultados == null || resultados.isEmpty()) {
	        log.debug("No se encontro el token");
	        return null;
	    }

	    String resultadoFinal = resultados.get(0);
	    log.debug("El resultado final en el entity es: " + resultadoFinal);

	    return resultadoFinal;
	}

	@Override
	public void saveDitActualizacionCorreo(Long idTramite, TramiteActualizacionCorreo xml) {
	    log.debug("-----Entrando a insertar en DIT_ACTUALIZACION_CORREO----");

	    Session session = this.getSession();

	    String queryStr = "INSERT INTO MGPBDTU9X.DIT_ACTUALIZACION_CORREO " +
	                      "(CVE_ID_TRAMITE, CVE_ID_ORIGEN_APLICACION, CVE_ID_FLUJO_ACTUALIZACION, " +
	                      "CVE_ID_PREGUNTAS_ACTUALIZACION, CVE_ID_RESPUESTA_SELECCIONADA, FEC_REGISTRO_ALTA) " +
	                      "VALUES (:idTramite, :idOrigen, :idFlujo, :idPregunta, :idRespuesta, SYSDATE)";

	    log.debug("El query a ejecutar es: " + queryStr);

	    SQLQuery query = session.createSQLQuery(queryStr);
	    query.setParameter("idTramite", idTramite);
	    query.setParameter("idOrigen", xml.getOrigenAplicacion());
	    query.setParameter("idFlujo", xml.getFlujoActualizacion());

	    if (xml.getPreguntaSeleccionada() != null) {
	        query.setParameter("idPregunta", xml.getPreguntaSeleccionada(), StandardBasicTypes.INTEGER);
	    } else {
	        query.setParameter("idPregunta", null, StandardBasicTypes.INTEGER);
	    }

	    if (xml.getRespuestaSeleccionada() != null) {
	        query.setParameter("idRespuesta", xml.getRespuestaSeleccionada(), StandardBasicTypes.STRING);
	    } else {
	        query.setParameter("idRespuesta", null, StandardBasicTypes.STRING);
	    }

	    int filasAfectadas = query.executeUpdate();
	    log.debug("Filas insertadas: " + filasAfectadas);
	}

}
