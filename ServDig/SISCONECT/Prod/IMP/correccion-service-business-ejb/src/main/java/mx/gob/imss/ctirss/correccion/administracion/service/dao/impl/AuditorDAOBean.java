package mx.gob.imss.ctirss.correccion.administracion.service.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.administracion.service.dao.AuditorDAOLocal;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.model.CrtAuditorAsignado;
import mx.gob.imss.ctirss.correccion.model.SsoUsuarios;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

@Stateless
public class AuditorDAOBean <T extends AbstractModel> extends AbstractRespository implements AuditorDAOLocal<T>{

	@Override
	public DatosSalidaPaginador<T> paginaPromocion(	DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		
		String sql = "";
		CrtPromocion parametros = (CrtPromocion) params.getModelo();
		sql = "select new mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion(p.cvePromocion, p.nuFoliopromocion, "
			    + " p.cveFkPatron, p.fecInicialDictamen ,p.fecFinalDictamen,p.fecFechaoficiopro) "
				+ "from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p "
				+ "where p.cveAuditorAsignado is null "					
				+ " and  p.cveEstatus = 28" +
				"   and p.cvePromocion not in (select aa.cvePromocion from CrtAuditorAsignado aa where aa.cvePromocion is not null)";
		if(parametros.getSdelegOrig() != null){
			sql += " and p.sdelegOrig = " + parametros.getSdelegOrig();
		}
		if(parametros.getFolioTemp() == null || parametros.getFolioTemp().equals("")){
			if(parametros.getIdOrigen() != null){
				sql += " and p.cveTipocorr = " + parametros.getIdOrigen();
			}
			if (parametros.getFechaIncial() != null
					&& !parametros.getFechaIncial().equals("")) {
				
				sql += " and TO_NUMBER(to_char(p.fecFechaemisionpro,'YYMMDD')) >= " +  Functions.dateToNumberAsStringD(parametros.getFechaIncial());
			}
			if (parametros.getFechaFinal() != null
					&& !parametros.getFechaFinal().equals("")) {
				sql += " and TO_NUMBER(to_char(p.fecFechaemisionpro,'YYMMDD')) <= " +  Functions.dateToNumberAsStringD(parametros.getFechaFinal());
			}
		}else{
			sql += " and p.nuFoliopromocion = '" + parametros.getFolioTemp() + "'";
		}
		
		sql += " order by p.nuFoliopromocion, p.fecFechaemisionpro";
		System.out.println(sql);		
		
		Query query = this.getSession().createQuery(sql);

		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		if(query.list().size()>0)
			result = query.list();

        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		
		response.setAaData(result);
		
		return response;
		
		
	}

	@Override
	public DatosSalidaPaginador<T> paginaInvitacion(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result = new ArrayList<T>();
		String sql = "";
		CrtPromocion parametros = (CrtPromocion) params.getModelo();
		sql = "select new mx.gob.imss.ctirss.correccion.model.CrtInvitacion(i.cveInvitacion, i.nuFolioInvitacion, "
			    + " i.satPatron, i.fecPeriodoIni, i.fecPeriodoFin, i.fecFechaemision) "
				+ "from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i "
				+ "where i.cveAuditorAsignado is null "					
				+ " and  i.cveEstatus = 28"
				+ " and i.cveInvitacion not in (select aa.cveInvitacion from CrtAuditorAsignado aa where aa.cveInvitacion is not null)";
		if(parametros.getSdelegOrig() != null){
			sql += " and i.cveFkSubdelegacion = " + parametros.getSdelegOrig();
		}
		if(parametros.getFolioTemp() == null || parametros.getFolioTemp().equals("")){
			if(parametros.getIdOrigen() == 10){
				sql += " and i.nuFolioInvitacion like '%/CCI/%'" ;
			}
			if(parametros.getIdOrigen() == 11){
				sql += " and i.nuFolioInvitacion like '%/CI/%'" ;
			}
			if (parametros.getFechaIncial() != null
					&& !parametros.getFechaIncial().equals("")) {
				
				sql += " and TO_NUMBER(to_char(i.fecFechaemision,'YYMMDD')) >= " +  Functions.dateToNumberAsStringD(parametros.getFechaIncial());
			}
			if (parametros.getFechaFinal() != null
					&& !parametros.getFechaFinal().equals("")) {
				sql += " and TO_NUMBER(to_char(i.fecFechaemision,'YYMMDD')) <= " +  Functions.dateToNumberAsStringD(parametros.getFechaFinal());
			}
		}else{
			sql += " and i.nuFolioInvitacion = '" + parametros.getFolioTemp() + "'";
		}
		
		sql += " order by i.nuFolioInvitacion, i.fecFechaemision";
		System.out.println(sql);		
		
		Query query = this.getSession().createQuery(sql);

		if(query.list().size()>0)
			result = query.list();
        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		response.setAaData(result);
		return response;
		
		
	}

	@Override
	public DatosSalidaPaginador<T> paginaSolicitud(	DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result = new ArrayList<T>();
		String sql = "";
		CrtPromocion parametros = (CrtPromocion) params.getModelo();
		sql = "select new mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr(s.cveSolicitudCorr,s.nuFolio, "
			    + " s.cvePatron, s.fecFechaPeriodoIni, s.fecFechaPeriodoFin, s.fecFechaElacoracionCorreccion) "
				+ "from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr s "
				+ "where s.cveAuditorAsignado is null "					
				+ " and  s.cveStatus in (1,2,3,4)";
//				+ " and s.cveSolicitudCorr not in (select aa.cveSolicitudCorr from CrtAuditorAsignado aa where aa.cveSolicitudCorr is not null)";
		if(parametros.getSdelegOrig() != null){
			sql += " and s.cveSubdelegacion = " + parametros.getSdelegOrig();
		}
		if(parametros.getFolioTemp() == null || parametros.getFolioTemp().equals("")){
			if(parametros.getIdOrigen() == 1){
				sql += " and s.nuFolio like '%/CE/%'" ;
			}
			if(parametros.getIdOrigen() == 12){
				sql += " and s.nuFolio like '%/CCE/%'" ;
			}
			if(parametros.getIdOrigen() == 2){
//				sql += " and s.nuFolio like '%/CI/%' or s.nuFolio like '%/CCI/%'" ;
				sql += " and s.cveTipoCorreccion = 2" ;
			}
			if (parametros.getFechaIncial() != null
					&& !parametros.getFechaIncial().equals("")) {
				
				sql += " and TO_NUMBER(to_char(s.fecFechaElacoracionCorreccion,'YYMMDD')) >= " +  Functions.dateToNumberAsStringD(parametros.getFechaIncial());
			}
			if (parametros.getFechaFinal() != null
					&& !parametros.getFechaFinal().equals("")) {
				sql += " and TO_NUMBER(to_char(s.fecFechaElacoracionCorreccion,'YYMMDD')) <= " +  Functions.dateToNumberAsStringD(parametros.getFechaFinal());
			}
		}else{
			sql += " and s.nuFolio = '" + parametros.getFolioTemp() + "'";
		}
		
		sql += " order by s.nuFolio, s.fecFechaElacoracionCorreccion";
		System.out.println(sql);		
		
		Query query = this.getSession().createQuery(sql);

		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		if(query.list().size()>0)
			result = query.list();
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(result!=null)
			iTotalRecords = result.size();
		
		/*Se debe de obtener el numero total de registros en la base de datos*/
//		TODO VAP meter el count
        //query.setFirstResult(0);
        //query.setMaxResults(-1);
        logger.debug("contando...");
        final List temp = query.list();
        logger.debug("temp.size() :: " + result.size());
        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		
		response.setAaData(result);
		
		return response;
		
	}

//	@SuppressWarnings("rawtypes")
//	@Override
//	public DatosSalidaPaginador<T> paginaAuditoresDisponibles(DatosEntradaPaginador<T> params) {
//		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
//		List<T> result = new ArrayList<T>();
//		List<SegUsuario> lstSegUsuario = new ArrayList<SegUsuario>();
//		String sql = "";
//		SegUsuario parametros = (SegUsuario) params.getModelo();
//		logger.debug("paginaAuditoresDisponibles");
//		sql =   " select u.CVE_ID_USUARIO, u.NOM_PATERNO, u.NOM_MATERNO, u.NOM_NOMBRE, " +
//				"        (select count(aa.CVE_AUDITOR_ASIGNADO) from CRT_AUDITOR_ASIGNADO aa where aa.CVE_AUDITOR = u.CVE_ID_USUARIO and aa.FEC_FECHASIGNACIONFIN is null) " +
//				" from SEG_USUARIO u" +
//				" inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO " +
//				" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL " +
//				" inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO " +
//				" where pu.CVE_ROL = 2 " +
//				" and u.CVE_ID_USUARIO >= 10000 " +
//				" and uf.CVE_ID_SUBDELEGACION = " + parametros.getSubDelegacion();		
//		sql += "  order by u.NOM_PATERNO,u.NOM_MATERNO,u.NOM_NOMBRE";
//		logger.debug(sql);
//		
//		List<?> query = this.getSession().createSQLQuery(sql).list();
//
//		if(query.size()>0){			
//			Iterator<?> itera = query.iterator();			
//			while(itera.hasNext()){
//				SegUsuario segUsuario = new SegUsuario();
//				Object[] obj = (Object[])itera.next();
//				segUsuario.setCveIdUsuario(obj[0] != null ? ((BigDecimal)obj[0]).longValue() : null);
//				segUsuario.setNomPaterno((String)obj[1]);
//				segUsuario.setNomMaterno((String)obj[2]);
//				segUsuario.setNomNombre((String)obj[3]);
//				segUsuario.setTotal(obj[4] != null ? ((BigDecimal)obj[4]).toString() : null);
//				lstSegUsuario.add(segUsuario);
//			}
//			result = (List)lstSegUsuario;
//		}
//        response.setiTotalRecords(result.size());
//        response.setiTotalDisplayRecords(result.size());
//		response.setAaData(result);
//		return response;
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public DatosSalidaPaginador<T> paginaAuditoresDisponibles(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result = new ArrayList<T>();
		List<SsoUsuarios> lstSegUsuario = new ArrayList<SsoUsuarios>();
		StringBuilder stringBuilder = new StringBuilder();
		SsoUsuarios parametros = (SsoUsuarios) params.getModelo();
		logger.debug("paginaAuditoresDisponibles");
		
		stringBuilder.append("select ssoUsr.NOM_NOMBRE, ssoUsr.NOM_PATERNO, ssoUsr.NOM_MATERNO, ssoUsr.DES_USR_CURP, ");
		stringBuilder.append("(select count(aa.CVE_AUDITOR_ASIGNADO) ");
		stringBuilder.append("from CRT_AUDITOR_ASIGNADO aa ");
		stringBuilder.append("where aa.CVE_USUARIO_ASIGNADO = ssoUsr.DES_USR_CURP and aa.FEC_FECHASIGNACIONFIN is null) ");
		stringBuilder.append("from SSO_USUARIOS ssoUsr ");
		stringBuilder.append("where ssoUsr.CVE_ID_DELEGACION = "+parametros.getCveIdDelegacion());
		stringBuilder.append(" and ssoUsr.CVE_ID_SUBDELEGACION = "+parametros.getCveIdSubDelegacion());
		stringBuilder.append(" and ssoUsr.CVE_SSODEPTO = 31 ");
		stringBuilder.append("order by ssoUsr.NOM_PATERNO, ssoUsr.NOM_MATERNO, ssoUsr.NOM_NOMBRE");
		System.out.println("Query Llena Auditores: " +stringBuilder.toString());
		
		List<?> query = this.getSession().createSQLQuery(stringBuilder.toString()).list();

		if(query.size()>0){			
			Iterator<?> itera = query.iterator();			
			while(itera.hasNext()){
				SsoUsuarios ssoUsuarios = new SsoUsuarios();
				Object[] obj = (Object[])itera.next();
				ssoUsuarios.setNomNombre((String)obj[0]);
				ssoUsuarios.setNomPaterno((String)obj[1]);
				ssoUsuarios.setNomMaterno((String)obj[2]);
				ssoUsuarios.setDesUsrCurp((String)obj[3]);
				ssoUsuarios.setTotal(obj[4] != null ? ((BigDecimal)obj[4]).toString() : null);
				lstSegUsuario.add(ssoUsuarios);
			}
			result = (List)lstSegUsuario;
		}
        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		response.setAaData(result);
		return response;
	}

	@Override
	public DatosSalidaPaginador<T> paginaCarga(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result = new ArrayList<T>();
		List<CrtAuditorAsignado> lstCrtAuditorAsignado = new ArrayList<CrtAuditorAsignado>();
		String sql = "";
		CrtAuditorAsignado parametros = (CrtAuditorAsignado) params.getModelo();
		sql =   " select aa.CVE_AUDITOR_ASIGNADO,aa.CVE_AUDITOR,aa.CVE_USUARIO_ASIGNADO,aa.CVE_INVITACION,aa.CVE_PROMOCION,aa.CVE_SOLICITUDCORR,aa.FEC_FECHASIGNACIONINI, " +
				"        aa.FEC_FECHASIGNACIONFIN, (trunc(sysdate) - trunc (aa.FEC_FECHASIGNACIONINI)) " +
				" from CRT_AUDITOR_ASIGNADO aa" +
//				" where aa.CVE_AUDITOR =" + parametros.getCveAuditor();
				" where aa.CVE_USUARIO_ASIGNADO = '" + parametros.getCveAuditorUsuarioAsignado()+"'";
		sql += "  and aa.FEC_FECHASIGNACIONFIN is null " +
				" order by aa.FEC_FECHASIGNACIONINI desc ";
		System.out.println(sql);		
		
		List<?> query = this.getSession().createSQLQuery(sql).list();

		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		if(query.size()>0){			
			Iterator<?> itera = query.iterator();			
			while(itera.hasNext()){
				CrtAuditorAsignado crtAuditorAsignado = new CrtAuditorAsignado();
				Object[] obj = (Object[])itera.next();
				crtAuditorAsignado.setCveAuditorAsignado(obj[0] != null ? ((BigDecimal)obj[0]).longValue() : null);
				crtAuditorAsignado.setCveAuditor(obj[1] != null ? ((BigDecimal)obj[1]).intValue() : null);
				crtAuditorAsignado.setCveAuditorUsuarioAsignado(obj[2] != null ? ((String)obj[2]) : null);
				crtAuditorAsignado.setCveInvitacion(obj[3] != null ? ((BigDecimal)obj[3]).longValue() : null);
				crtAuditorAsignado.setCvePromocion(obj[4] != null ? ((BigDecimal)obj[4]).longValue() : null);
				crtAuditorAsignado.setCveSolicitudCorr(obj[5] != null ? ((BigDecimal)obj[5]).longValue() : null);
				crtAuditorAsignado.setFecFechaAsignacionIni(obj[6] != null ? ((Date)obj[6]) : null);
				crtAuditorAsignado.setFecFechaAsignacionFin(obj[7] != null ? ((Date)obj[7]) : null);
				crtAuditorAsignado.setTotal(obj[8] != null ? ((BigDecimal)obj[8]).toString() : null);
				lstCrtAuditorAsignado.add(crtAuditorAsignado);
			}
			result = (List<T>)lstCrtAuditorAsignado;
		}	
		
        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		response.setAaData(result);
		return response;
	}

	@Override
	public T agregar(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}

	}

	@Override
	public Integer buscaPatronAnexo(Integer id) {

		Integer cve_patronAnexo = null;
		String sql = "";
		sql =   " SELECT C.CVE_FK_PATRON  " +
				" FROM CRT_ANEXOSOLCORRPAT C, CRT_SOLICITUDCORR D " +
				" WHERE C.CVE_SOLICITUDCORR =D.CVE_SOLICITUDCORR " +
				" AND C.CVE_FK_PATRON IS NOT NULL " +
				" AND C.CVE_FK_PATRON_PR IS NULL " +
				" and C.CVE_SOLICITUDCORR =" + id;
		
		List<?> query = this.getSession().createSQLQuery(sql).list();

		if(query.size()>0){			
			Iterator<?> itera = query.iterator();			
			while(itera.hasNext()){
				
				BigDecimal obj = (BigDecimal)itera.next();
				cve_patronAnexo = obj != null ? ((BigDecimal)obj).intValue() : null;				
			}
		}	
		return cve_patronAnexo;
	}

	@Override
	public T buscaPorCveUsuario(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass()).add(Restrictions.eq("desUsrCurp", ((SsoUsuarios)model).getDesUsrCurp()));
		if(criteria.list() != null && criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
		
		}

//	@Override
//	public DatosSalidaPaginador<T> paginaReasignarAuditoresDisponibles(DatosEntradaPaginador<T> params) {
//		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
//		List<T> result = new ArrayList<T>();
//		List<SegUsuario> lstSegUsuario = new ArrayList<SegUsuario>();
//		String sql = "";
//		SegUsuario parametros = (SegUsuario) params.getModelo();
//		sql =   " select u.CVE_ID_USUARIO, u.NOM_PATERNO, u.NOM_MATERNO, u.NOM_NOMBRE, " +
//				"        (select count(aa.CVE_AUDITOR_ASIGNADO) from CRT_AUDITOR_ASIGNADO aa where aa.CVE_AUDITOR = u.CVE_ID_USUARIO and aa.FEC_FECHASIGNACIONFIN is null) " +
//				" from SEG_USUARIO u" +
//				" inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO " +
//				" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL " +
//				" inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO " +
//				" where pu.CVE_ROL = 2 " +
//				" and u.CVE_ID_USUARIO != " + parametros.getCveAuditorAsignado() +
//				" and u.CVE_ID_USUARIO >= 10000 " +
//				" and uf.CVE_ID_SUBDELEGACION = " + parametros.getSubDelegacion();		
//		sql += "  order by u.NOM_PATERNO,u.NOM_MATERNO,u.NOM_NOMBRE";
//		System.out.println(sql);		
//		
//		List<?> query = this.getSession().createSQLQuery(sql).list();
//
//		if(query.size()>0){			
//			Iterator<?> itera = query.iterator();			
//			while(itera.hasNext()){
//				SegUsuario segUsuario = new SegUsuario();
//				Object[] obj = (Object[])itera.next();
//				segUsuario.setCveIdUsuario(obj[0] != null ? ((BigDecimal)obj[0]).longValue() : null);
//				segUsuario.setNomPaterno((String)obj[1]);
//				segUsuario.setNomMaterno((String)obj[2]);
//				segUsuario.setNomNombre((String)obj[3]);
//				segUsuario.setTotal(obj[4] != null ? ((BigDecimal)obj[4]).toString() : null);
//				lstSegUsuario.add(segUsuario);
//			}
//			result = (List<T>)lstSegUsuario;
//		}
//		
//        response.setiTotalRecords(result.size());
//        response.setiTotalDisplayRecords(result.size());
//		response.setAaData(result);
//		return response;
//	}
	
	@Override
	public DatosSalidaPaginador<T> paginaReasignarAuditoresDisponibles(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		List<T> result = new ArrayList<T>();
		List<SsoUsuarios> listSsoUsuarios = new ArrayList<SsoUsuarios>();
		StringBuilder stringBuilder = new StringBuilder();
		SsoUsuarios parametros = (SsoUsuarios) params.getModelo();
		
		stringBuilder.append("select ssoUsr.NOM_NOMBRE, ssoUsr.NOM_PATERNO, ssoUsr.NOM_MATERNO, ssoUsr.DES_USR_CURP, ");
		stringBuilder.append("(select count(aa.CVE_AUDITOR_ASIGNADO) ");
		stringBuilder.append("from CRT_AUDITOR_ASIGNADO aa ");
		stringBuilder.append("where aa.CVE_USUARIO_ASIGNADO = ssoUsr.DES_USR_CURP and aa.FEC_FECHASIGNACIONFIN is null) ");
		stringBuilder.append("from SSO_USUARIOS ssoUsr ");
		stringBuilder.append("where ssoUsr.CVE_ID_DELEGACION = "+parametros.getCveIdDelegacion());
		stringBuilder.append(" and ssoUsr.CVE_ID_SUBDELEGACION = "+parametros.getCveIdSubDelegacion());
		stringBuilder.append(" and ssoUsr.DES_USR_CURP != '" +parametros.getDesUsrCurp()+"'");
		stringBuilder.append(" and ssoUsr.CVE_SSODEPTO = 31 ");
		stringBuilder.append("order by ssoUsr.NOM_PATERNO, ssoUsr.NOM_MATERNO, ssoUsr.NOM_NOMBRE");
		System.out.println("Query Llena Auditores de ReAsignación: " +stringBuilder.toString());
		
		List<?> query = this.getSession().createSQLQuery(stringBuilder.toString()).list();

		if(query.size()>0){			
			Iterator<?> itera = query.iterator();			
			while(itera.hasNext()){
				SsoUsuarios ssoUsuarios = new SsoUsuarios();
				Object[] obj = (Object[])itera.next();
				ssoUsuarios.setNomNombre((String)obj[0]);
				ssoUsuarios.setNomPaterno((String)obj[1]);
				ssoUsuarios.setNomMaterno((String)obj[2]);
				ssoUsuarios.setDesUsrCurp((String)obj[3]);
				ssoUsuarios.setTotal(obj[4] != null ? ((BigDecimal)obj[4]).toString() : null);
				listSsoUsuarios.add(ssoUsuarios);
			}
			result = (List)listSsoUsuarios;
		}
		
        response.setiTotalRecords(result.size());
        response.setiTotalDisplayRecords(result.size());
		response.setAaData(result);
		return response;
	}

	@Override
	public T buscarAsignado(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.isNull("fecFechaAsignacionFin"));
//		criteria.add(Restrictions.eq("cveAuditor", ((CrtAuditorAsignado)model).getCveUsuarioAsignado().intValue()));
		criteria.add(Restrictions.eq("cveAuditorUsuarioAsignado", ((CrtAuditorAsignado)model).getCveUsuarioAsignado()));
		if(((CrtAuditorAsignado)model).getCveInvitacion() != null){
			criteria.add(Restrictions.eq("cveInvitacion", ((CrtAuditorAsignado)model).getCveInvitacion()));
		}
		if(((CrtAuditorAsignado)model).getCvePromocion() != null){
			criteria.add(Restrictions.eq("cvePromocion", ((CrtAuditorAsignado)model).getCvePromocion()));
		}
		if(((CrtAuditorAsignado)model).getCveSolicitudCorr() != null){
			criteria.add(Restrictions.eq("cveSolicitudCorr", ((CrtAuditorAsignado)model).getCveSolicitudCorr()));
		}
		if(criteria.list() != null && criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
		
		}

	@Override
	public T buscarAuditorAsignado(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.isNull("fecFechaAsignacionFin"));
		if(((CrtAuditorAsignado)model).getCveInvitacion() != null){
			criteria.add(Restrictions.eq("cveInvitacion", ((CrtAuditorAsignado)model).getCveInvitacion()));
		}
		if(((CrtAuditorAsignado)model).getCvePromocion() != null){
			criteria.add(Restrictions.eq("cvePromocion", ((CrtAuditorAsignado)model).getCvePromocion()));
		}
		if(((CrtAuditorAsignado)model).getCveSolicitudCorr() != null){
			criteria.add(Restrictions.eq("cveSolicitudCorr", ((CrtAuditorAsignado)model).getCveSolicitudCorr()));
		}
		if(criteria.list() != null && criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
		
		}

}
