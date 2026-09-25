package mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoRP;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedulaPK;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtEstatusFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacionRP;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class SolicitudCorreccionDAOBean <T extends AbstractModel> extends AbstractRespository implements SolicitudCorreccionDAOLocal<T>{

	@SuppressWarnings("rawtypes")
	@Override
	public CrtSolicitudcorr save(CrtSolicitudcorr model) {
		List patronesGuardados = new ArrayList();
		CrtCorrPromInvita crtCorrPromInvita = new CrtCorrPromInvita(); 
		try{
			this.getSession().save(model);
			
			if(model!=null && model.getPromocion()!=null && model.getPromocion().getCvePromocion()!=null && model.getPromocion().getCvePromocion().intValue() >0 ){
				
				this.getSession().saveOrUpdate(model.getPromocion());
				
				crtCorrPromInvita.setCrtPromocion(model.getPromocion());
				crtCorrPromInvita.setCrtInvitacion(null);
				crtCorrPromInvita.setCrtSolicitudcorr(model);
				crtCorrPromInvita.setCveStatusCorreccion(model.getCveStatus());
				crtCorrPromInvita.setUsuarioFirmado(model.getUsuarioFirmado());
				
				this.getSession().saveOrUpdate(crtCorrPromInvita);
				
			}else if(model!=null && model.getInvitacion()!=null && model.getInvitacion().getCveInvitacion()!=null && model.getInvitacion().getCveInvitacion().intValue() >0 ){
				
				this.getSession().saveOrUpdate(model.getInvitacion());
				
				crtCorrPromInvita.setCrtInvitacion(model.getInvitacion());
				crtCorrPromInvita.setCrtPromocion(null);
				crtCorrPromInvita.setCrtSolicitudcorr(model);
				crtCorrPromInvita.setCveStatusCorreccion(model.getCveStatus());
				crtCorrPromInvita.setUsuarioFirmado(model.getUsuarioFirmado());
				
				this.getSession().saveOrUpdate(crtCorrPromInvita);
				
			}
				
			List patrones = model.getPatrones();
			List<String> periodosCorreccion=new ArrayList<String>();
			if(patrones!=null&&patrones.size()>0)
			{
				Iterator i = patrones.iterator();
				while(i.hasNext())
				{
					CrtAnexosolcorrpat aux = (CrtAnexosolcorrpat)i.next();
					aux.setCveSolicitudCorr(model.getCveSolicitudCorr());
					aux.setCveDomicilio(new Integer(aux.getDomicilioGeografico().getDomicilioId()+"").intValue());
					this.getSession().save(aux);
					patronesGuardados.add(aux);
					String periodo;
					if(model.getPeriodos()!=null&&model.getPeriodos().size()>0)
					{
						Iterator j = model.getPeriodos().iterator();
						while(j.hasNext()){
							CrcEjercicio ejercicio = new CrcEjercicio();
							periodo = (String)j.next();
							if(!buscaPeriodo(periodosCorreccion, periodo)){
								periodosCorreccion.add(periodo);
							}
							
							ejercicio.setCveEjercicio(new Long(periodo));
							ejercicio.setCveAcexoCorrPat(aux.getCveAnexoSolicitudCorrPat().longValue());
							
							this.getSession().save(ejercicio);
							//Se agrega los elementos de monitor
							//for(int s=1;s<=8;s++){
								
							//}
						}
						
						
						
						
						
					}
				}
				
				CrtControlFlujoCedulaPK pkFlujo=null;
				CrtControlFlujoCedula flujo=null;
				CrtEstatusFlujoCedula estatus=new CrtEstatusFlujoCedula();
				estatus.setCveEstatus(ConstantesBusiness.ESTATUS_SIN_OPERACION);
				
				System.out.println("Total periodos "+periodosCorreccion.size());
				for(String per:periodosCorreccion){					
					System.out.println("Periodo "+per);
					
				
					for(int e=1;e<=8;e++){
						flujo=new CrtControlFlujoCedula();
						pkFlujo=new CrtControlFlujoCedulaPK();
						
						flujo.setCrtEstatusFlujoCedula(estatus);
						
						pkFlujo.setCveCedula(e);
						pkFlujo.setCveEjercicio(new Integer(per));
						pkFlujo.setCveSolicitudcorr(model.getCveSolicitudCorr());
						
						flujo.setId(pkFlujo);
						flujo.setPorcentajeAvance(0.0f);					
						this.getSession().save(flujo);
					}
					
					
				}
				
			}
			
			model.setPatrones(patronesGuardados);
/*			
			if(model.getCorreccionGestion()!=null)
			{
				try{
					this.getSession().save(model.getCorreccionGestion());
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			*/
		/*	if(model.getCorreccionPatronesGestion()!=null&&model.getCorreccionPatronesGestion().size()>0)
			{
				Iterator i = model.getCorreccionPatronesGestion().iterator();
				String patronesG ="";
				while(i.hasNext())
				{
					CgtAnexoRP rp = (CgtAnexoRP)i.next();
					try{
						if(!patronesG.contains(rp.getRegPats())){
							this.getSession().save(rp);
							patronesG+="-"+rp.getRegPats()+"-";
						}
						
					}catch(Exception e){
						e.printStackTrace();	
					}
					
				}
			}
			*/
			System.out.println("la session es "+this.getSession());
			this.getSession().flush();
			return model;
		}catch(Exception re){
			re.printStackTrace();
			throw new PersistenceException();
		}
	}

	
	public boolean buscaPeriodo(List<String> periodos,String periodo){
		
		
		for(String per:periodos){
			if(per.equals(periodo)){
				return true;
			}
		}		
		return false;
		
	}
	
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer nuFolio, Integer periodo,CrtSolicitudcorr patrones) {
		
		// , int iDisplayStart,int iDisplayLength
		//String registroPatronal,  String strSubdelegacion, String strDelegacion, Integer cvePatronPr, String txRazonSocial
		List<CrtAnexosolcorrpat> resultado;

		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT  new mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat(patron.registroPatronal, subdelegacion.nomNombre," +
				" delegacion.nomNombre, anexo.cvePatronPr, anexo.txRazonSocial, ejercicio, anexo.cveAnexoSolicitudCorrPat)  " +
				" from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud, mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat anexo, " +
				" mx.gob.imss.ctirss.correccion.model.SatPatron patron, mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio ejercicio, " +
				" mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion subdelegacion,  mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion delegacion " +
				" WHERE patron.cvePK = anexo.cvePatron " +
				" AND anexo.tipoPatron <> 'F' " +	
				" AND solicitud.cveSolicitudCorr = anexo.cveSolicitudCorr "  +
				" AND ejercicio.cveAcexoCorrPat = anexo.cveAnexoSolicitudCorrPat " + 
				" AND solicitud.cveSolicitudCorr = :clave " +
				" AND ejercicio.cveEjercicio = :periodo " +
				" AND subdelegacion.cvePk = solicitud.cveSubdelegacion " +
				" AND subdelegacion.sacDelegacion.cvePk = delegacion.cvePk " +
				" ORDER BY anexo.cveAnexoSolicitudCorrPat " 
				);
		System.out.println("************ QueryPatrones:  "+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
				
		query.setParameter("clave",nuFolio);
		query.setParameter("periodo",periodo);
		
		if(query.list().isEmpty()){
			resultado = null;
		}else{
			resultado = (List<CrtAnexosolcorrpat>) query.list();
			System.out.println("Total de Numero Anexos  "+resultado.size());
			Hashtable<String,CrtAnexosolcorrpat> resultadoFiltrado = new Hashtable<String,CrtAnexosolcorrpat>();
			
//			for(CrtAnexosolcorrpat currentAnexo:resultado){
//				resultadoFiltrado.put(currentAnexo.getRegistroPatronal(), currentAnexo);
//			}
//			
//			Enumeration<String> e = resultadoFiltrado.keys();
//			
//			resultado.clear();
//			
//			while(e.hasMoreElements()){
//				resultado.add(resultadoFiltrado.get(e.nextElement()));
//			}
			
		}
		
		return resultado;
	}
	
	public T consultaPorFolio(T filtro) {
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT solicitud from   mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud " +
				"  WHERE solicitud.nuFolio = '" +   ((CrtSolicitudcorr)filtro).getNuFolio() + "'");
		System.out.println("************ Query:  "+hql.toString());
		
		System.out.println("***************************FOLIO: " + ((CrtSolicitudcorr)filtro).getNuFolio());
		Query query = this.getSession().createQuery(hql.toString());
		
		if( query.list().isEmpty())
			filtro = null;
		else
		filtro = (T) query.list().get(0);

		System.out.println("RESULTADO: " + filtro);
		
		return filtro;
	}
	
public T consultaPorFolioRegPat(T filtro) {
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT solicitud from   mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud " +
				"  WHERE solicitud.nuFolio = '" +   ((CrtSolicitudcorr)filtro).getNuFolio() + "' and solicitud.cvePatron = "+((CrtSolicitudcorr)filtro).getCvePatron());
		System.out.println("************ Query:  "+hql.toString());
		
		System.out.println("***************************FOLIO: " + ((CrtSolicitudcorr)filtro).getNuFolio());
		Query query = this.getSession().createQuery(hql.toString());
		
		if( query.list().isEmpty())
			filtro = null;
		else
		filtro = (T) query.list().get(0);

		System.out.println("RESULTADO: " + filtro);
		
		return filtro;
	}
	

	/**
	 * Metodo que busca por folio, subdelegacion del usuario y que cveAuditorAsignado != 0
	 * @author Enrique Duran JImenez
	 * @since 05/06/2012
	 */
	
	public T consultaPorFolioAuditorAsignado(T filtro) {
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT solicitud from   mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud " +
				"  WHERE solicitud.nuFolio = '" +   ((CrtSolicitudcorr)filtro).getNuFolio() + "'" +
				"  AND   solicitud.cveSubdelegacion =" + ((CrtSolicitudcorr)filtro).getCveSubdelegacion()  +
				"  AND   solicitud.cveAuditorAsignado is not null");
		System.out.println("************ Query:  "+hql.toString());
		
		System.out.println("***************************FOLIO: " + ((CrtSolicitudcorr)filtro).getNuFolio());
		Query query = this.getSession().createQuery(hql.toString());
		
		if( query.list().isEmpty())
			filtro = null;
		else
		filtro = (T) query.list().get(0);

		System.out.println("RESULTADO: " + filtro);
		
		return filtro;
	}

	@Override
	public int obtenConsecutivoFolio(String idDelegacion,String idSubdelegacion, int anio) {
        String idDelegacionStr = new DecimalFormat("00").format(new Integer(idDelegacion).intValue());
        String idSubDelegacionStr = new DecimalFormat("00").format(new Integer(idSubdelegacion).intValue());

		String sql = " SELECT  new java.lang.String(sol.nuFolio) from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr sol " +
				" WHERE sol.cveSolicitudCorr in ( select  max(sol2.cveSolicitudCorr)  from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr sol2 " +
				" WHERE substr(sol2.nuFolio,1,2) = '"+idDelegacionStr+"' " +
				" AND substr(sol2.nuFolio,3,2)= '"+idSubDelegacionStr+"' " +
				" AND TO_CHAR(sol2.fecFechaElacoracionCorreccion,'YYYY') = "+anio+")";
		List result = this.getSession().createQuery(sql).list();
		if(result!=null&&result.size()>0)
		{
			String folio = (String)result.get(0);
			String consecutivo = folio.substring(folio.length()-4,folio.length());
			return new Integer(consecutivo).intValue()+1;
		}
		else
		return 1;
	}

	@Override
	public boolean validaSolicitud(CrtSolicitudcorr solicitud) {
		String sql = " SELECT new java.lang.String(sol.nuFolio) FROM mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr sol " +
				" WHERE sol.cvePatron = " +solicitud.getPatronCorregir().getCvePK()+" AND sol.cveStatus!=6  AND sol.cveStatus!=25  AND sol.cveStatus!=22 "+    //se agrega estatus de dictamen y cancelacion
				" AND ((sol.fecFechaPeriodoIni >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoIni <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
				" OR (sol.fecFechaPeriodoFin >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
				" OR (sol.fecFechaPeriodoIni <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
				" OR (sol.fecFechaPeriodoIni >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY')))";

		if(solicitud.getTipoObra().equals("true") && solicitud.getNumeroObra()!=null){
			sql+=" AND sol.cveNumeroRegObra="+solicitud.getNumeroObra();
		}
		
		
		
		System.out.println("Query Consulta Numero Obra "+sql);
		List result = this.getSession().createQuery(sql).list();
		boolean flagCEValido=true;
		//Caso CE
		if(solicitud.getNuFolio().contains("CE") && (result!=null&&result.size()>0)){			
				for(Object ob:result){
					System.out.println("Antecedente CE con folio "+ob.toString());
					if(ob.toString().contains("CCE")){
						flagCEValido=false;
					}else{
						flagCEValido=true;
						break;
					}
				}	
				return flagCEValido;
		}
		
		
		
		if(result!=null&&result.size()>0)
			return true;
		else
			return false;
	}

	public List validaSolicitudResult(CrtSolicitudcorr solicitud) {
		StringBuffer sql=new StringBuffer();
		
//		 sql.append(" SELECT sol FROM mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr sol " +
//				" WHERE sol.cvePatron = " +solicitud.getPatronCorregir().getCvePK()+
//				" AND ((sol.fecFechaPeriodoIni >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoIni <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
//				" OR (sol.fecFechaPeriodoFin >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
//				" OR (sol.fecFechaPeriodoIni <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))" +
//				" OR (sol.fecFechaPeriodoIni >= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoIni())+"','DD-MM-YYYY') AND  sol.fecFechaPeriodoFin <= to_date('"+Functions.dateToString2(solicitud.getFecFechaPeriodoFin())+"','DD-MM-YYYY'))) ");

		
		 sql.append(" SELECT sol FROM mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr sol " +
					" WHERE sol.cvePatron = " +solicitud.getPatronCorregir().getCvePK()+" AND sol.cveStatus!=6 ");
		System.out.println("Tipo Obra "+solicitud.getTipoObra());
		System.out.println("Numero Obra "+solicitud.getNumeroObra());
		if(solicitud.getTipoObra().equals("true") && solicitud.getNumeroObra()!=null){
			sql.append(" AND sol.cveNumeroRegObra="+solicitud.getNumeroObra());
		}
		System.out.println("SQLCorreccion "+sql.toString());
		return this.getSession().createQuery(sql.toString()).list();
	}

	@Override
	public List consultarSolicitudesPendientes(Long codigoDel,Long codigoSubDel,Long idSubDelegacion) {
		
		String delegacion,subdelegacion;
		
		
		delegacion = (codigoDel<0) ? codigoDel.toString() : "0"+codigoDel.toString();
		subdelegacion = (codigoSubDel<0) ? codigoSubDel.toString() : "0"+codigoSubDel.toString();
		
		Criteria criteria = this.getSession().createCriteria(CrtSolicitudcorr.class);
		criteria.add(Restrictions.eq("cveStatus", new Integer(1)));
		criteria.add(Restrictions.eq("cveSubdelegacion", idSubDelegacion));
		criteria.addOrder(Order.asc("nuFolio"));
		
									//.add(Restrictions.like("nuFolio", delegacion+subdelegacion+"%"));
		
		
		return criteria.list();  
	}
	
	public DatosSalidaPaginador<CrtSolicitudcorr> paginarSolicitudesPendientes(DatosEntradaPaginador<CrtSolicitudcorr> input, Long idSubdelegacion){
		DatosSalidaPaginador<CrtSolicitudcorr> response = new DatosSalidaPaginador<CrtSolicitudcorr>();		

		Criteria criteria = this.getSession().createCriteria(CrtSolicitudcorr.class).add(Restrictions.eq("cveStatus", new Integer(1)))
				.add(Restrictions.eq("cveSubdelegacion", idSubdelegacion));	
		List<CrtSolicitudcorr> result = null;
		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
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
		 
		 result = criteria.setFirstResult(input.getiDisplayStart())
				.setMaxResults(input.getiDisplayLength()).list();
	     
		 response.setAaData(result);
		 response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}

	
	@Override
	public List consultarSolicitudesById(Integer id) {
		

		Criteria criteria = this.getSession().createCriteria(CrtSolicitudcorr.class).add(Restrictions.eq("cveSolicitudCorr", id));
		List result = new ArrayList();
		List solicitudes = criteria.list();
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			Iterator i = solicitudes.iterator();
			while(i.hasNext())
			{
				CrtSolicitudcorr solicitud = (CrtSolicitudcorr)i.next();
				Criteria criteria2 = this.getSession().createCriteria(CrtAnexosolcorrpat.class).add(Restrictions.eq("cveSolicitudCorr", solicitud.getCveSolicitudCorr()));
				solicitud.setLstAnexoSolicitudesCorr(criteria2.list());
				result.add(solicitud);
			}
			return result;
		}
		return null;  
	}
	
	@Override
	public List consultarSolicitudesById(Integer id,Long idSubdelegacion) {
		

		Criteria criteria = this.getSession().createCriteria(CrtSolicitudcorr.class).add(Restrictions.eq("cveSolicitudCorr", id)).add(Restrictions.eq("cveSubdelegacion", idSubdelegacion));
		List result = new ArrayList();
		List solicitudes = criteria.list();
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			Iterator i = solicitudes.iterator();
			while(i.hasNext())
			{
				CrtSolicitudcorr solicitud = (CrtSolicitudcorr)i.next();
				Criteria criteria2 = this.getSession().createCriteria(CrtAnexosolcorrpat.class).add(Restrictions.eq("cveSolicitudCorr", solicitud.getCveSolicitudCorr()));
				solicitud.setLstAnexoSolicitudesCorr(criteria2.list());
				result.add(solicitud);
			}
			return result;
		}
		return null;  
	}

	public List getSolicitudDetalles(CrtSolicitudcorr solicitud, String tipoPatron){
		
		Criteria criteria = this.getSession().createCriteria(CrtAnexosolcorrpat.class)
										.add(Restrictions.eq("cveSolicitudCorr", solicitud.getCveSolicitudCorr()))
										.add(Restrictions.eq("tipoPatron", tipoPatron))
										.addOrder(Order.asc("cveAnexoSolicitudCorrPat"));
		
		return criteria.list();
		
	}
	
	@Override
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer claveSolicitud, Long cveEjercicio) {
		
		List result = new ArrayList();
		//Criteria criteria = this.getSession().createCriteria(CrtAnexosolcorrpat.class).add(Restrictions.eq("cveSolicitudCorr", claveSolicitud));
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat(anexo.cveAnexoSolicitudCorrPat, anexo.cvePatron, anexo.cvePatronPr, " );
		hql.append( "  anexo.txRazonSocial, patron.registroPatronal )  " );
		hql.append( " from  mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat anexo, " );
		hql.append( " mx.gob.imss.ctirss.correccion.model.SatPatron patron " );
		hql.append(	" WHERE anexo.cveSolicitudCorr =:clave " );
		hql.append(	" AND patron.cvePK = anexo.cvePatron " );
		hql.append( " ORDER BY anexo.cveAnexoSolicitudCorrPat " );
	
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("clave",claveSolicitud);
	
	
		
		List solicitudes = query.list();
		
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			Iterator i = solicitudes.iterator();
			String auxAnexo ="";
			while(i.hasNext())
			{
				CrtAnexosolcorrpat solicitud = (CrtAnexosolcorrpat)i.next();
				
				if(!auxAnexo.contains(solicitud.getRegistroPatronal())){
					auxAnexo+="-"+solicitud.getRegistroPatronal()+"-";
					
					Criteria criteria2 = null;
					if(cveEjercicio!=null&&cveEjercicio>0){
						criteria2 = this.getSession().createCriteria(CrcEjercicio.class).add(Restrictions.eq("cveAcexoCorrPat", new Long(solicitud.getCveAnexoSolicitudCorrPat())))
								.add(Restrictions.eq("cveEjercicio", cveEjercicio));
					
					}else{
					
						criteria2 = this.getSession().createCriteria(CrcEjercicio.class).add(Restrictions.eq("cveAcexoCorrPat", new Long(solicitud.getCveAnexoSolicitudCorrPat())));
					}			
				
					solicitud.setEjerciciosSolicitud(criteria2.list());
					result.add(solicitud);
				}
				
			}
			return result;
		}
		
		return null;
		
	}



	@Override
	public CrtSolicitudcorr update(CrtSolicitudcorr model) {
		
		if(model.getInvitacion()!=null || model.getPromocion()!=null){
			
//			CrtCorrPromInvita crtCorrPromInvita = null;
//			
//			Criteria criteria = this.getSession().createCriteria(CrtCorrPromInvita.class)
//					 .add(Restrictions.eq("crtSolicitudcorr.cveSolicitudCorr", ((CrtSolicitudcorr)model).getCveSolicitudCorr()));
//			if(criteria.list() != null && criteria.list().size() > 0){
//				crtCorrPromInvita = (CrtCorrPromInvita) criteria.list().get(0);
//				crtCorrPromInvita.setCveStatusCorreccion(model.getCveStatus());
//				this.getSession().update(crtCorrPromInvita);
//				
//			}else{
//				crtCorrPromInvita = null;
//			}
		}
		
		
		this.getSession().update(model);
		this.getSession().flush();
		return model;
	}


	@Override
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cveSolicitudCorr", ((CrtSolicitudcorr)filtro).getCveSolicitudCorr()));
		if(criteria.list() != null && criteria.list().size() > 0){
			filtro = (T) criteria.list().get(0);
		}else{
			filtro = null;
		}
		return filtro;
	}
	
	@Override
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudesReport(Integer claveSolicitud) {
		
		List result = new ArrayList();
		//Criteria criteria = this.getSession().createCriteria(CrtAnexosolcorrpat.class).add(Restrictions.eq("cveSolicitudCorr", claveSolicitud));
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat(anexo.cveAnexoSolicitudCorrPat, anexo.cvePatron, anexo.cvePatronPr, " );
		hql.append( "  anexo.txRazonSocial, patron.registroPatronal, anexo.tipoPatron, anexo.cveDomicilio , anexo.numTrabajadores, anexo.txActividad, anexo.txClase, anexo.txFraccion, anexo.txPrima, anexo.txTelefono)  " );
		hql.append( " from  mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat anexo , " );
		hql.append( " mx.gob.imss.ctirss.correccion.model.SatPatron patron " );
		hql.append(	" WHERE anexo.cveSolicitudCorr =:clave " );
		hql.append(	" AND patron.cvePK(+) = anexo.cvePatron " );
		hql.append( " ORDER BY anexo.cveAnexoSolicitudCorrPat " );
	
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("clave",claveSolicitud);
	
	
		
		List solicitudes = query.list();
		
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			Iterator i = solicitudes.iterator();
			while(i.hasNext())
			{
				CrtAnexosolcorrpat solicitud = (CrtAnexosolcorrpat)i.next();
				Criteria criteria2 = null;
				criteria2 = this.getSession().createCriteria(CrcEjercicio.class).add(Restrictions.eq("cveAcexoCorrPat", new Long(solicitud.getCveAnexoSolicitudCorrPat())));
							
			
				solicitud.setEjerciciosSolicitud(criteria2.list());
				result.add(solicitud);
			}
			return result;
		}
		
		return null;
		
	}


}
