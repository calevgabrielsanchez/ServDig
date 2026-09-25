package mx.gob.imss.ctirss.correccion.invitacion.service.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.invitacion.service.dao.InvitacionDAOLocal;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


@Stateless
public class InvitacionDAOBean<T extends AbstractModel> extends AbstractRespository implements InvitacionDAOLocal<T> {

	
	public CrtInvitacion validaInvitacion(Long patron,Date periodoInicial, Date periodoFinal) {
		Criterion crFechaInicio= Restrictions.between("fecPeriodoIni", periodoInicial, periodoFinal);
		Criterion crFechaFin= Restrictions.between("fecPeriodoFin", periodoInicial, periodoFinal);
				
		Criterion perIniInter=Restrictions.le("fecPeriodoIni", periodoInicial);
		Criterion perFinInter=Restrictions.ge("fecPeriodoFin", periodoFinal);
		
		Criteria criteria = this.getSession().createCriteria(CrtInvitacion.class).
	            add(Restrictions.eq("satPatron.cvePK", patron)).
	            add(Restrictions.isNotNull("fecFechanotifi")).
	            add(Restrictions.or(Restrictions.or(crFechaInicio, crFechaFin), Restrictions.and(perIniInter, perFinInter))).
	            add(Restrictions.isNull("fecCancelacion")).
	            add(Restrictions.eq("cveEstatus", 28L)).addOrder(Order.desc("nuFolioInvitacion"));
		
		
		List<?> l = criteria.list();
		System.out.println("Total de registrosAntece "+l.size());
		if(!l.isEmpty()){			
			for(int i=0;i<l.size();i++){
				CrtInvitacion invitacion=(CrtInvitacion)l.get(i);
				if(invitacion.getNuFolioInvitacion().contains("CCI") || invitacion.getNuFolioInvitacion().contains("CI")){
					return invitacion;
				}
			}			
			return (CrtInvitacion)l.get(0);	
		}		
		return null;
	}
	
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		CrtPromocion promocion = new CrtPromocion();
		CrtDeteccion deteccion = new CrtDeteccion();
		List<T> result = new ArrayList<T>();
		if(params.getModelo() instanceof CrtPromocion){
			promocion = (CrtPromocion) params.getModelo();
		}else if (params.getModelo() instanceof CrtDeteccion){
			deteccion = (CrtDeteccion) params.getModelo();
		}
		
		String idTipoPrograma = "";
		String tipo = "";
		if(promocion  != null && promocion.getCveTipocorr() != null){
			idTipoPrograma = promocion.getCveTipocorr().toString();
			tipo = promocion.getFolioTemp();
			
		}else if(deteccion != null && deteccion.getCveTipocorr() != null){
			idTipoPrograma = deteccion.getCveTipocorr().toString();
			tipo = deteccion.getFolioTemp();
		}
				
		String sql = "";
		String tipoPromocion = "";
		
		if (idTipoPrograma.equals("8") || idTipoPrograma.equals("9")) { // Deteccion
			CrtDeteccion parametros = deteccion;
			
			
			sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"
					+ "b.nuFoliodeteccion, b.fecFechadeteccionFc,"
					+ "b.nomRazonsocial,"
					+ "b.sdelegOrig, b.desDependenciapub,"
					+ "b.desDepcontratante, b.fecFechainicioEst,"
					+ "b.fecFechaterminoEst, b.idPromovido,"
					+ "b.fecFechareg, b.cveUsuario,b.domicilioId, "
					+ "b.cveFkPatron) "
					+ "from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b "
					+ "where b.idMotivocancelacion is null "
					+ " and b.cveDeteccion not in ( select p.cveDeteccion from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p where p.cveDeteccion is not null ) "
					+ " and b.cveDeteccion not in ( select i.cveDeteccion from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i where i.cveDeteccion is not null 	)";
			if(parametros.getSdelegOrig() != null){
				sql += " and b.sdelegOrig = " + parametros.getSdelegOrig(); 
			}
			if(parametros.getFolioTemp() == null || parametros.getFolioTemp().equals("")){
				if (parametros.getFechaIncial() != null	&& !parametros.getFechaIncial().equals("")) {
					
					sql += " and TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) >= " +  Functions.dateToNumberAsStringD(parametros.getFechaIncial());
				}
				if (parametros.getFechaFinal() != null && !parametros.getFechaFinal().equals("")) {
					
					sql += " and TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) <= " +  Functions.dateToNumberAsStringD(parametros.getFechaFinal());
					
				}
			}else{
				sql += " and b.nuFoliodeteccion = '" + parametros.getFolioTemp() +"' ";
			}
			sql += " and b.cveTipocorr = '" + parametros.getCveTipocorr() +"' ";
			sql += " order by b.nuFoliodeteccion";
			System.out.println(sql);
		} else if (idTipoPrograma.equals("4") || idTipoPrograma.equals("5") || idTipoPrograma.equals("6") || idTipoPrograma.equals("7")){
			if (idTipoPrograma.equals("4")) { // Static B
				tipoPromocion = "/SATICB/";
			} else if (idTipoPrograma.equals("5")) { // Exhorto de construccion
				tipoPromocion = "/EX/";
			} else if (idTipoPrograma.equals("6")) { // Exhorto de lo Ordinario
				tipoPromocion = "/EXO/";
			} else if (idTipoPrograma.equals("7")) { // Salario Base de Cotizacion
				tipoPromocion = "/SBC/";
			}

			CrtPromocion parametros = promocion;
			
			sql = "select new mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion(p.cvePromocion, p.nuFoliopromocion, "
				    + "p.nuOficiopro, p.fecFechaoficiopro, p.fecFechanotif, p.cveFkPatron, p.cveUsuario ,p.cveDeteccion) "
					+ "from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p "
					+ "where p.idMotivoCancelacion is null "					
					+ " and  p.fecFechanotif is not null"
					+ " and  p.cveTipocorr = " + idTipoPrograma 
					+ " and  p.cvePromocion not in ( "
					+ " select i.cvePromocion from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i where i.cvePromocion is not null)";
			if(parametros.getSdelegOrig() != null){
				sql += " and p.sdelegOrig = " + parametros.getSdelegOrig();
			}
			if(parametros.getFolioTemp() == null || parametros.getFolioTemp().equals("")){
				if (parametros.getFechaIncial() != null
						&& !parametros.getFechaIncial().equals("")) {
					
					sql += " and TO_NUMBER(to_char(p.fecFechaoficiopro,'YYMMDD')) >= " +  Functions.dateToNumberAsStringD(parametros.getFechaIncial());
				}
				if (parametros.getFechaFinal() != null
						&& !parametros.getFechaFinal().equals("")) {
					sql += " and TO_NUMBER(to_char(p.fecFechaoficiopro,'YYMMDD')) <= " +  Functions.dateToNumberAsStringD(parametros.getFechaFinal());
				}
			}else{
				sql += " and p.nuFoliopromocion = '" + parametros.getFolioTemp() + "'";
			}
			
			sql += " order by p.nuFoliopromocion";
			System.out.println(sql);

		}else if (idTipoPrograma.equals("-1")){
			String[] split = null;
			if(tipo != null){
				split = tipo.split("/");
				if(split[1].equals("SATICB") || split[1].equals("EX") || split[1].equals("EXO") || split[1].equals("SBC")){
					
					CrtPromocion parametros = promocion;
					
					sql = "select new mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion(p.cvePromocion, p.nuFoliopromocion, "
						    + "p.nuOficiopro, p.fecFechaoficiopro, p.fecFechanotif, p.cveFkPatron, p.cveUsuario ,p.cveDeteccion) "
							+ "from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p "
							+ "where p.idMotivoCancelacion is null "					
							+ " and  p.fecFechanotif is not null"
							+ " and p.nuFoliopromocion = '" + parametros.getFolioTemp() + "'"
							+ " and  p.cvePromocion not in ( "
							+ " select i.cvePromocion from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i where i.cvePromocion is not null)";
					if(parametros.getSdelegOrig() != null){
						sql += " and p.sdelegOrig = " + parametros.getSdelegOrig();
					}
					sql += " order by p.nuFoliopromocion";
					System.out.println(sql);				
					
				}else if (split[1].equals("DET")){
					
					CrtDeteccion parametros = deteccion;				
					
					sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"
							+ "b.nuFoliodeteccion, b.fecFechadeteccionFc,"
							+ "b.nomRazonsocial,"
							+ "b.sdelegOrig, b.desDependenciapub,"
							+ "b.desDepcontratante, b.fecFechainicioEst,"
							+ "b.fecFechaterminoEst, b.idPromovido,"
							+ "b.fecFechareg, b.cveUsuario,b.domicilioId, "
							+ "b.cveFkPatron) "
							+ " from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b "
							+ " where b.idMotivocancelacion is null "
							+ " and b.nuFoliodeteccion = '" + parametros.getFolioTemp() +"' "
							+ " and b.cveDeteccion not in ( select p.cveDeteccion from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p where p.cveDeteccion is not null ) "
							+ " and b.cveDeteccion not in ( select i.cveDeteccion from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i where i.cveDeteccion is not null 	)";
					if(parametros.getSdelegOrig() != null){
						sql += " and b.sdelegOrig = " + parametros.getSdelegOrig(); 
					}
					
					sql += " order by b.nuFoliodeteccion";
					System.out.println(sql);
					
				}
			}
			
		
		}

		if (!sql.equals("")){
			Query query = this.getSession().createQuery(sql);

		if (query != null && query.list() != null && query.list().size() > 0)
			result =  query.list();
		
		}

		int iTotalRecords = 0;
		/* Se debe de obtener el numero total de registros en la base de datos */
		if (result != null)
			iTotalRecords = result.size();

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;

		if (result != null)
			iTotalDisplayRecords = result.size();

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
	
		return response;
	}

	@Override
	public CrtInvitacion guardar(CrtInvitacion invitacion) {
		try{
			
			this.getSession().saveOrUpdate(invitacion);
			this.getSession().flush();			
			
		}catch(RuntimeException re){
			
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
			
		}catch( Exception e){
			e.printStackTrace();
		}
		return invitacion;

	}

	@Override
	public String buscaFolio(String tipoPrograma) {
		
		List<CrtInvitacion> lstResult = new ArrayList<CrtInvitacion>();
		String folio = "";
		String sql = "";
		Calendar cal = Calendar.getInstance();
		Integer anio = cal.get(Calendar.YEAR);
		String anioSis = anio.toString();
		try{			
		
			if(tipoPrograma != null && !tipoPrograma.equals("")){
				if(tipoPrograma.equals("6") || tipoPrograma.equals("7")){  // CI
					// Buscamos el ultimo registro insertado CI
					sql = "select new mx.gob.imss.ctirss.correccion.model.CrtInvitacion(i.cveInvitacion, i.satPatron, "
							+ "i.cvePromocion, i.cveDeteccion, i.cveFkSubdelegacion, i.nuOficioinv, i.fecFechaoficioinv," +
							  "i.fecFechaemision, i.fecFechanotifi, i.fecFechareg, i.cveUsuario, i.nuFolioInvitacion)"
							+ "from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i "
							+ "where i.nuFolioInvitacion like '%/CI/" + anioSis + "/%'" +
							  "order by i.cveInvitacion desc";
					System.out.println(sql);
					
					Query query = this.getSession().createQuery(sql);
					lstResult = query.list();
					
					if(lstResult != null && lstResult.size() > 0){
						folio = lstResult.get(0).getNuFolioInvitacion();
						
					}
				}else if(tipoPrograma.equals("3") || tipoPrograma.equals("4") || tipoPrograma.equals("5")
						 || tipoPrograma.equals("8") || tipoPrograma.equals("9")){  // CCI
					// Buscamos el ultimo registro insertado CCI
					
					sql = "select new mx.gob.imss.ctirss.correccion.model.CrtInvitacion(i.cveInvitacion, "
							+ "i.cvePromocion, i.cveDeteccion, i.cveFkSubdelegacion, i.nuOficioinv, i.fecFechaoficioinv," +
							  "i.fecFechaemision, i.fecFechanotifi, i.fecFechareg, i.cveUsuario, i.nuFolioInvitacion)"
							+ "from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i "
							+ "where i.nuFolioInvitacion like '%/CCI/" + anioSis + "/%'" +
							  "order by i.cveInvitacion desc";
					
					System.out.println(sql);
					
					Query query = this.getSession().createQuery(sql);
					lstResult = query.list();
					
					if(lstResult != null && lstResult.size() > 0){
						folio = lstResult.get(0).getNuFolioInvitacion();						
					}
				}
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return folio;
	}
	
	@Override
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del, Long SubDel, String cad, String fecha) {
		
		List<CrtNroFolio> lstRegresa = new ArrayList<CrtNroFolio>();
		Criteria criteria = this.getSession().createCriteria(CrtNroFolio.class);
		criteria.add(Restrictions.eq("numAnio", BigDecimal.valueOf(Long.valueOf(fecha))));
		criteria.add(Restrictions.eq("cveDelegacion", BigDecimal.valueOf(Del)));
		criteria.add(Restrictions.eq("cveSubdelegacion", BigDecimal.valueOf(SubDel)));
		criteria.createCriteria("crcTipoCorr").add(Restrictions.eq("cveTipocorr", Long.valueOf(cad)));
		criteria.addOrder(Order.desc("cvePkFolio"));
		
		lstRegresa =  criteria.list();
		
		return lstRegresa;
	}

	@Override
	public DatosSalidaPaginador<T> paginaInvitacion(DatosEntradaPaginador<T> params) {
		
		CrtInvitacion model = (CrtInvitacion)params.getModelo();
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		String tipo = params.getsSearch();
		
		List<T> result = null;
		if(model != null){
			if(model.getFechaIncial() != null && model.getFechaFinal() != null && !tipo.equals("")){
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date ini = null;
				Date fin = null;
				
				try {
					ini = formatter.parse(model.getFechaIncial());
					fin = formatter.parse(model.getFechaFinal());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());
				//criteria.add(Restrictions.between("fecFechaemision", new Date(ini.getTime()), new Date(fin.getTime())));
				criteria.add(Restrictions.isNull("fecAtencion"));
				criteria.add(Restrictions.isNull("idMotivoCancelacion"));
				criteria.add(Restrictions.isNull("fecCancelacion"));				
				criteria.add(Restrictions.ge("fecFechaemision", ini));
				criteria.add(Restrictions.le("fecFechaemision", fin));
				if(tipo.equals("10")){
					criteria.add(Restrictions.like("nuFolioInvitacion", "/CCI/", MatchMode.ANYWHERE));
				}else if(tipo.equals("11")){
					criteria.add(Restrictions.like("nuFolioInvitacion", "/CI/" , MatchMode.ANYWHERE));
				}
				criteria.addOrder(Order.asc("nuFolioInvitacion"));
				result = criteria.list();
					

				/**
				 * Total records, before filtering (i.e. the total number of records in
				 * the database)
				 */
				int iTotalRecords = 0;
				/*Se debe de obtener el numero total de registros en la base de datos*/
				iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
				
				criteria.setProjection(null);

				/**
				 * Total records, after filtering (i.e. the total number of records
				 * after filtering has been applied - not just the number of records
				 * being returned in this result set)
				 */
				int iTotalDisplayRecords = 0;
				
				iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
				 criteria.setProjection(null);

				 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
				 
				 result = criteria.setFirstResult(params.getiDisplayStart()).setMaxResults(params.getiDisplayLength()).list();
			     
				response.setAaData(result);
				response.setiTotalDisplayRecords(iTotalDisplayRecords);
				response.setiTotalRecords(iTotalRecords);
				
				return response;
			}
		}
		return response;
		
	}

	@Override
	public T consultaPorClave(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass())
				 .add(Restrictions.eq("cveInvitacion", ((CrtInvitacion)model).getCveInvitacion()));
		model = (T) criteria.list().get(0);
		return model;
		
	}

	@Override
	public T obtieneSATIC(String numObra) {
		
		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultTemp = new ArrayList<CrtDeteccion>();
		List<?> query = this
				.getSession()
				.createSQLQuery("SELECT RF.CVE_PK, RF.FEC_FECHAINICIO_FC, RF.FEC_FECHATERMINO_FC" +  
				" FROM SATIC_REL_TRAB_FALTANTES RF WHERE " +
				"RF.CVE_NROREGOBRA = " + Long.valueOf(numObra)).list();
		
		if(query.size()>0){			
			Iterator<?> itera = query.iterator();			
			while(itera.hasNext()){
				CrtDeteccion deteccion = new CrtDeteccion();
				Object[] obj = (Object[])itera.next();
				String fechaIn = ((Timestamp)obj[1]+"").substring(0, 10);
				String fechaFin =  ((Timestamp)obj[2]+"").substring(0, 10);
				deteccion.setFechaIncial(fechaIn!=null ? fechaIn.substring(8, 10)+"-"+fechaIn.substring(5, 7)+"-"+fechaIn.substring(0, 4) : "");
				deteccion.setFechaFinal(fechaFin!=null ? fechaFin.substring(8, 10)+"-"+fechaFin.substring(5, 7)+"-"+fechaFin.substring(0, 4) : "");								
				
				resultTemp.add(deteccion);
			}
			result = (List<T>)resultTemp;
		}
			
		if(!result.isEmpty()){
			return result.get(0);
		}else
			return null;
		
	}

	/**
	 * Metodo que busca por folio, subdelegacion del usuario y que cveAuditorAsignado != 0
	 * @author Enrique Duran JImenez
	 * @since 05/06/2012
	 */
	@Override
	public T consultaPorFolio(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("nuFolioInvitacion", ((CrtInvitacion)model).getNuFolioInvitacion()));
		criteria.add(Restrictions.eq("cveFkSubdelegacion", ((CrtInvitacion)model).getCveFkSubdelegacion()));
//		criteria.add(Restrictions.gt("cveAuditorAsignado", 0L));
		criteria.add(Restrictions.isNotNull("cveAuditorAsignado"));
		
		List<T> ls = criteria.list();
		
		if (ls != null && !ls.isEmpty()){
			model = ls.get(0);
		}else{
			model = null;
		}
		return model;
		
	}

	/**
	 * @author CesarAgustin	
	 * @version 1.0.0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CrtInvitacion> consultaInvitacionesSeguimiento(InvitacionSeguimientoVO filtros) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM CrtInvitacion ci")
					.append(" WHERE ci.cveFkSubdelegacion=").append(filtros.getIdSubDelegacion())
					.append(" AND ci.cveEstatus=28");
		long rolUsr=filtros.getCveRol();
		// Se agrega filtro por usuario exceptuando roles de jefe
		// solo pueden consultar los folios que tengan asignados		
		if (rolUsr!=ConstantesBusiness.ROL_JEFE_DEPARTAMENTO_AUDITORIA_A_PATRONES
			&& rolUsr!=ConstantesBusiness.ROL_JEFE_OFICINA_CORRECCION_Y_DICTAMEN
			&& rolUsr!=ConstantesBusiness.JEFE_OF_CORRECCION){
			queryString.append( " AND ci.cveAuditorAsignado!='" + filtros.getCveIdUsuario()+"'");
		}

		if (filtros.getIdTipoCorr()!=null && !filtros.getIdTipoCorr().equals("0")) {
			queryString.append(" AND substr(ci.nuFolioInvitacion,6,").append(filtros.getIdTipoCorr().length()).append(")='")
						.append(filtros.getIdTipoCorr()).append("' AND ")
						.append(" TO_NUMBER(to_char(ci.fecFechaemision,'YYMMDD')) >=").append(Functions.dateToNumberAsString(filtros.getFechaEmisionIni()))
						.append(" AND TO_NUMBER(to_char(ci.fecFechaemision,'YYMMDD')) <=").append(Functions.dateToNumberAsString(filtros.getFechaEmisionFin()));
		} else if (filtros.getFolioInvitacion()!=null) {
			queryString.append(" AND ci.nuFolioInvitacion='")
						.append(filtros.getFolioInvitacion()).append("'");
		}
			queryString.append(" order by ci.nuFolioInvitacion");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public T obtieneUbicacionPatron(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cvePK", ((SatUbicacion)model).getPatron().getFkUbicacion()));
		return (T) criteria.uniqueResult();
	}

	@Override
	public List<CrtInvitacion> consultaInvitacionPorParametros(
			CrtInvitacion crtInvitacion) {		
		StringBuilder query=new StringBuilder();
		query.append("FROM CrtInvitacion where CVE_FK_PATRON=:fkPatron");
		Query que = super.getSession().createQuery(query.toString());
		que.setParameter("fkPatron", crtInvitacion.cveFkPatron);
		return que.list();
	}
		
}
