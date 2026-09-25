package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.impl.CatalogoDAOBean;

@Stateless
public class TrabajadoresDAOBean<T extends AbstractModel> extends AbstractRespository implements TrabajadoresDAOLocal<T>{

	public T agrega(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			System.out.println(".-.ERROR"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	public T elimina(T model) {
		model = (this.consultaPorClave(model));
		try{
			this.getSession().delete(model);
			this.getSession().flush();
			model.setError(ConstantesBusiness.NO_ERRROR);
		}catch(HibernateException e){
			e.printStackTrace();
			
			if(e.toString().contains("ConstraintViolationException")){
				model.setError("No se pudo eliminar el registro\n "
						      +"porque tiene referencias dentro del sistema");
			}
		}
			
		return model;	
	}

	public void eliminaHijos(List<T> model) {
		
		for (Iterator<T> iterator = model.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			this.getSession().delete(t);
			this.getSession().flush();
		}
	}
	
	public T modifica(T model) {
		this.getSession().merge(model);
		this.getSession().flush();
		return model;
	}

	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {

		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = TrabajadoresDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consultarTrabajadores(T filtro) {
	/**BigDecimal cveEjertrab, Long cveAcexoCorrPat,
		Long cveEjercicio, BigDecimal cveTrabajador, Date fecIngreso,
		BigDecimal nuAntiguedadAnios, String txDepartamento,
		String txCategoria, BigDecimal impSalariodiario,
		BigDecimal indPruebasel, BigDecimal indExcsaltop,
		BigDecimal indAnatiempext, BigDecimal indAnahon,
		String txActividad, Date fecFechareg, String cveUsuario,
		CrcTrabajadores crcTrabajadores	
	*/	
		
	List<T> resultado;
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador(ejercicioTra.cveEjertrab, ejercicioTra.cveAcexoCorrPat,  ");
		hql.append(" ejercicioTra.cveEjercicio, ejercicioTra.cveTrabajador, ejercicioTra.fecIngreso, ejercicioTra.nuAntiguedadAnios, ejercicioTra.txDepartamento, ");
		hql.append(" ejercicioTra.cveCategoria, ejercicioTra.impSalariodiario, ejercicioTra.indPruebasel, ejercicioTra.indExcsaltop, ejercicioTra.indAnatiempext, ");
		hql.append(" ejercicioTra.indAnahon, ejercicioTra.txActividad, ejercicioTra.fecFechareg, ejercicioTra.cveUsuario, traDatos )");
		hql.append("  from mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador ejercicioTra, ");
		hql.append("  mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores traDatos ");
		hql.append("  WHERE ejercicioTra.cveAcexoCorrPat = :cveAnexoSol ");
		hql.append("  AND ejercicioTra.cveEjercicio = :periodo");
		hql.append("  AND traDatos.cveTrabajador = ejercicioTra.cveTrabajador");
		
		if(((CrtEjertrabajador)filtro).getIndPruebasel()!=null&&((CrtEjertrabajador)filtro).getIndPruebasel().intValue()>0)
			hql.append("  AND ejercicioTra.indPruebasel = :pruebasSelectivas");
		
		if(((CrtEjertrabajador)filtro).getIndExcsaltop()!=null&&((CrtEjertrabajador)filtro).getIndExcsaltop().intValue()>0)
			hql.append("  AND ejercicioTra.indExcsaltop = :salariosTopados");
		
		if(((CrtEjertrabajador)filtro).getIndAnatiempext()!=null&&((CrtEjertrabajador)filtro).getIndAnatiempext().intValue()>0)
			hql.append("  AND ejercicioTra.indAnatiempext = :tiempoExtra");
		
		if(((CrtEjertrabajador)filtro).getIndAnahon()!=null&&((CrtEjertrabajador)filtro).getIndAnahon().intValue()>0){
			hql.append("  AND (ejercicioTra.indAnahon = :honorarios)");
			//hql.append("  OR traDatos.tipoTrabajador=2 ");//Caso de hOnorarios
		}
		
		
		hql.append("  order by traDatos.nuNss");	
		
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("cveAnexoSol",((CrtEjertrabajador)filtro).getCveAcexoCorrPat());
		query.setParameter("periodo",((CrtEjertrabajador)filtro).getCveEjercicio());
		
		if(((CrtEjertrabajador)filtro).getIndPruebasel()!=null&&((CrtEjertrabajador)filtro).getIndPruebasel().intValue()>0)
			query.setParameter("pruebasSelectivas",((CrtEjertrabajador)filtro).getIndPruebasel());
		
		if(((CrtEjertrabajador)filtro).getIndExcsaltop()!=null&&((CrtEjertrabajador)filtro).getIndExcsaltop().intValue()>0)
			query.setParameter("salariosTopados",((CrtEjertrabajador)filtro).getIndExcsaltop());
		
		if(((CrtEjertrabajador)filtro).getIndAnatiempext()!=null&&((CrtEjertrabajador)filtro).getIndAnatiempext().intValue()>0)
			query.setParameter("tiempoExtra",((CrtEjertrabajador)filtro).getIndAnatiempext());
		
		if(((CrtEjertrabajador)filtro).getIndAnahon()!=null&&((CrtEjertrabajador)filtro).getIndAnahon().intValue()>0)
			query.setParameter("honorarios",((CrtEjertrabajador)filtro).getIndAnahon());
			

		System.out.println("************ Query:  "+hql.toString());
		
		if(query.list().isEmpty())
			resultado = null;
		else
			resultado = (List<T>) query.list();
		
		return resultado;
		
	}
	
	public T consultaPorClave(T filtro) {
	
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
				.add(Restrictions.eq("cveTrabajador",((CrcTrabajadores)filtro).getCveTrabajador()));
		
		filtro = (T) criteria.list().get(0);
		return filtro;
	}
	

	public T consultaPorClaveDatos(T filtro) {
	
		//List<T> resultado;
		
		StringBuffer hql = new StringBuffer();
		hql.append( " SELECT new mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores(trabajadores.cveTrabajador,  trabajadores.cveSolicitudCorr,");
		hql.append(" trabajadores.nuNss, trabajadores.txRfc, trabajadores.nombreAsegurado, trabajadores.apPaternoAsegurado, trabajadores.apMaternoAsegurado, ");
		hql.append(" solicitud.nuFolio ) ");
		hql.append(" from mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores trabajadores, mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud ");
		hql.append(" WHERE trabajadores.cveTrabajador = " + ((CrcTrabajadores)filtro).getCveTrabajador());
		hql.append(" AND trabajadores.cveSolicitudCorr = solicitud.cveSolicitudCorr ");
	
		Query query = this.getSession().createQuery(hql.toString());
		
		filtro = (T) query.list().get(0);
		return filtro;
	
	}

	@Override
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());
		Example e = CatalogoDAOBean.createExampleOf(params.getModelo());
		criteria.add(e);
		
		List<String> lsFiltrosBajLogica = CatalogoDAOBean.getFiltrosBajaLogica(params.getModelo());
		for(String sFiltro:lsFiltrosBajLogica){
			criteria.add(Restrictions.isNull(sFiltro));
		}
		
		System.out.println("Parametro folio: " + ((CrcTrabajadores)params.getModelo()).getFolioCorreccion());
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
         int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		 
		 result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	public List<T> paginadorTrabajadoresSinPeriodo(T filtro) {
		
		//BigDecimal cveTrabajador, Long cveSolicitudCorr,
		//String nuNss, String txRfc, String nombreAsegurado,
		//String apPaternoAsegurado, String apMaternoAsegurado
		
		List<T> resultado;
		
		StringBuffer hql = new StringBuffer();
		hql.append( " SELECT new mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores(trabajadores.cveTrabajador,  trabajadores.cveSolicitudCorr,");
		hql.append(" trabajadores.nuNss, trabajadores.txRfc, trabajadores.nombreAsegurado, trabajadores.apPaternoAsegurado, trabajadores.apMaternoAsegurado, ");
		hql.append(" solicitud.nuFolio, patron.registroPatronal)");
		hql.append(" from mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores trabajadores, ");
		hql.append(" mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud,  ");
		hql.append(" mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat anexo,  ");
		hql.append(" mx.gob.imss.ctirss.correccion.model.SatPatron patron  ");
		hql.append(" WHERE trabajadores.cveSolicitudCorr = " + ((CrcTrabajadores)filtro).getCveSolicitudCorr());
		hql.append(" AND trabajadores.cveSolicitudCorr = solicitud.cveSolicitudCorr ");
		hql.append(" AND anexo.cveSolicitudCorr = solicitud.cveSolicitudCorr ");
		hql.append(" AND anexo.cvePatronPr is NULL ");
		hql.append(" AND anexo.tipoPatron <> 'F' ");
		hql.append(" AND anexo.cvePatron = patron.cvePK ");
		
		
		if(((CrcTrabajadores)filtro).getNombreAsegurado()!=null&&((CrcTrabajadores)filtro).getNombreAsegurado()!="")
			hql.append(" AND UPPER(trabajadores.nombreAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getNombreAsegurado() +"%')");
		
		if(((CrcTrabajadores)filtro).getApPaternoAsegurado()!=null&&((CrcTrabajadores)filtro).getApPaternoAsegurado()!="")
		  hql.append(" AND UPPER(trabajadores.apPaternoAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getApPaternoAsegurado() +"%')");
			
	    if(((CrcTrabajadores)filtro).getApMaternoAsegurado()!=null&&((CrcTrabajadores)filtro).getApMaternoAsegurado()!="")
	      hql.append(" AND UPPER(trabajadores.apMaternoAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getApMaternoAsegurado() +"%')");
	    
		if(((CrcTrabajadores)filtro).getNuNss()!=null&&((CrcTrabajadores)filtro).getNuNss()!="")
	     hql.append(" AND UPPER(trabajadores.nuNss) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getNuNss() +"%')");
		
		if(((CrcTrabajadores)filtro).getTxRfc()!=null&&((CrcTrabajadores)filtro).getTxRfc()!="")
		     hql.append(" AND UPPERtrabajadores.txRfc) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getTxRfc() +"%')");
			
		hql.append(" order by trabajadores.nuNss");
		Query query = this.getSession().createQuery(hql.toString());
		
		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		
		if(query.list().isEmpty()){
			resultado = null;
		}else{
			resultado = (List<T>) query.list();
			
		}
			
	
		return resultado;
	}

	
	public List<T> paginadorTrabajadoresPeriodo(T filtro, Long cveAnexo, Long periodo) {
		
	
		/**
		 * BigDecimal cveTrabajador, Long cveSolicitudCorr,
			String nuNss, String txRfc, String nombreAsegurado,
			String apPaternoAsegurado, String apMaternoAsegurado,
			BigDecimal indPruebasel, BigDecimal indExcsaltop,
			BigDecimal indAnatiempext, BigDecimal indAnahon
		 * 
		 */
		
		// <option value="1">Prueba Selectiva</option>
		//	<option value="2">Salarios Topados</option>
		//	<option value="3">An&aacute;lisis Tiempo Extra</option>
		//	<option value="4">An&aacute;lisis Honorarios</option>
		
		List<T> resultado;
		
		StringBuffer hql = new StringBuffer();
		hql.append( " SELECT new mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores(trabajadores.cveTrabajador,  trabajadores.cveSolicitudCorr,");
		hql.append(" trabajadores.nuNss, trabajadores.txRfc, trabajadores.nombreAsegurado, trabajadores.apPaternoAsegurado, trabajadores.apMaternoAsegurado, ");
		hql.append("  ejercicioTra.indPruebasel, ejercicioTra.indExcsaltop, ejercicioTra.indAnatiempext, ejercicioTra.indAnahon )");
		hql.append(" from mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores trabajadores, ");
		hql.append("  mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador ejercicioTra,  ");
		hql.append("  mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat anexo  ");		
		hql.append("  WHERE ejercicioTra.cveAcexoCorrPat = " + cveAnexo);
		hql.append("  AND anexo.tipoPatron <> 'F' ");
		hql.append("  AND anexo.cveAnexoSolicitudCorrPat= ejercicioTra.cveAcexoCorrPat");
		hql.append("  AND ejercicioTra.cveEjercicio = " + periodo) ;
		hql.append("  AND trabajadores.cveTrabajador = ejercicioTra.cveTrabajador");

		
		
		if(((CrcTrabajadores)filtro).getNombreAsegurado()!=null&&((CrcTrabajadores)filtro).getNombreAsegurado()!="")
			hql.append(" AND UPPER(trabajadores.nombreAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getNombreAsegurado() +"%')");
		
		if(((CrcTrabajadores)filtro).getApPaternoAsegurado()!=null&&((CrcTrabajadores)filtro).getApPaternoAsegurado()!="")
		  hql.append(" AND UPPER(trabajadores.apPaternoAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getApPaternoAsegurado() +"%')");
			
	    if(((CrcTrabajadores)filtro).getApMaternoAsegurado()!=null&&((CrcTrabajadores)filtro).getApMaternoAsegurado()!="")
	      hql.append(" AND UPPER(trabajadores.apMaternoAsegurado) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getApMaternoAsegurado() +"%')");
	    
		if(((CrcTrabajadores)filtro).getNuNss()!=null&&((CrcTrabajadores)filtro).getNuNss()!="")
	     hql.append(" AND UPPER(trabajadores.nuNss) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getNuNss() +"%')");
		
		if(((CrcTrabajadores)filtro).getTxRfc()!=null&&((CrcTrabajadores)filtro).getTxRfc()!="")
		     hql.append(" AND UPPERtrabajadores.txRfc) LIKE UPPER('%" + ((CrcTrabajadores)filtro).getTxRfc() +"%')");
			
		
		if(((CrcTrabajadores)filtro).getIndicadorTrabajador()!=null&&((CrcTrabajadores)filtro).getIndicadorTrabajador().equals("1"))
			hql.append(" AND ejercicioTra.indPruebasel = " + new BigDecimal(1));
		
		if(((CrcTrabajadores)filtro).getIndicadorTrabajador()!=null&&((CrcTrabajadores)filtro).getIndicadorTrabajador().equals("2"))
			hql.append(" AND ejercicioTra.indExcsaltop = " + new BigDecimal(1));

		if(((CrcTrabajadores)filtro).getIndicadorTrabajador()!=null&&((CrcTrabajadores)filtro).getIndicadorTrabajador().equals("3"))
			hql.append(" AND ejercicioTra.indAnatiempext = " + new BigDecimal(1));
		
		if(((CrcTrabajadores)filtro).getIndicadorTrabajador()!=null&&((CrcTrabajadores)filtro).getIndicadorTrabajador().equals("4"))
			hql.append(" AND ejercicioTra.indAnahon = " + new BigDecimal(1));

		System.out.println("QUERY*********** " + hql.toString());
		
		Query query = this.getSession().createQuery(hql.toString());
		
		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		
		if(query.list().isEmpty()){
			resultado = null;
		}else{
			
			resultado = (List<T>) query.list();
			
		}
			
			
	
		return resultado;
	}
	
	public List<T> obtenerHijos(T filtro) {
		
		List<T> result;
		
		StringBuffer hql = new StringBuffer();
		
		hql.append( "SELECT ejercicioTra  from mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador ejercicioTra ");
		hql.append("  WHERE  ejercicioTra.cveTrabajador = :cveTrabajdor ");

		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("cveTrabajdor",((CrtEjertrabajador)filtro).getCveTrabajador());
		
		if(query.list().isEmpty())
			result = null;
		else
			result = (List<T>) query.list();
	
		return result;
	}
	

}
