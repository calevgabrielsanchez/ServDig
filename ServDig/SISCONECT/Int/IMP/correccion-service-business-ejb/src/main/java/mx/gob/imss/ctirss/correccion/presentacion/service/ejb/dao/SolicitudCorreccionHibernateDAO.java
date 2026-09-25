package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.EstudioSolicitudCorreccionVO;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrcDiaInhabil;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

@Stateless
public class SolicitudCorreccionHibernateDAO 
			extends GenericHibernateDAO<CrtSolicitudcorr, Integer>
			implements SolicitudCorreccionDAO{

	public void setPersistentClass(Class<CrtSolicitudcorr> p) {
		super.setPersistentClass(p);
	}
	
	public SolicitudCorreccionHibernateDAO() {
		setPersistentClass(CrtSolicitudcorr.class);
	}
	
	public Collection<CrtSolicitudcorr> findAll(boolean onlyRootCategories) {
		if(onlyRootCategories){
			return findByCriteria(Restrictions.isNull("parent"));
		}else{
			return findAll();
		}
	}

	public List<ErrorValidation> isFolioCorreccionValidoParaPresentacion(Integer solicitudCorreccion) {
		List<ErrorValidation> lista = new ArrayList<ErrorValidation>();
		CrtSolicitudcorr sol = findConcreteByCriteria(Restrictions.eq("cveSolicitudCorr", solicitudCorreccion));
		Query qry = getSession().createQuery("from CrtProrroga prorroga where prorroga.cveSolicitudcorr = :solicitud");
		qry.setParameter("solicitud", new BigDecimal(solicitudCorreccion));
		
		Date fechaLimite = recalculaFechaLimite(sol.getFecFechaLimite());
		
		@SuppressWarnings("unchecked")
		List<CrtProrroga> prorrogas = qry.list();
		
		if(sol != null){
			Calendar cal = Calendar.getInstance();
			if(sol.getCveTipoCorreccion() == null){
				ErrorValidation ev = new ErrorValidation();
				ev.setError(true);
				ev.setError("No existe \"Tipo de Correccion\" en la solicitud de correccion");
				lista.add(ev);
			}
			if(sol.getFecFechaLimite() == null){
				ErrorValidation ev = new ErrorValidation();
				ev.setError(true);
				ev.setError("No existe \"Fecha Limite\" en la solicitud de correccion");
				lista.add(ev);
			}
			
			//Se borra que se valide la fecha limite debido al cambio solicitado por los usuarios de correccion, la presentacion puede realizarse aunque se halla vencido la fecha limite
//			if(fechaLimite != null && 
//					!ConstantesBusiness.isSameDay(fechaLimite, cal.getTime()) && 
//					fechaLimite.before(cal.getTime())){
//				
//				/**Checaremos si existe alguna prorroga*/
//				if(prorrogas == null ||
//						prorrogas.size() <= 0){
//					ErrorValidation ev = new ErrorValidation();
//					ev.setError(true);
//					ev.setError("La \"Fecha Limite\" en la solicitud de correccion ha expirado (Dia/Mes/Anio): " + ConstantesBusiness.dateToStringFormat(fechaLimite, ConstantesBusiness.DD_MM_YYYY));
//					lista.add(ev);
//				}else{
//					/**Existe mas de una prorroga*/
//					if(prorrogas.size() > 1){
//						StringBuffer buffer = new StringBuffer();
//						buffer.append("Existen varias prorrogas para este folio de correccion");
//						for(CrtProrroga prorroga : prorrogas){
//							validaProrroga(prorroga, fechaLimite, lista);
//						}
//					}else{
//						/**Solo existe una prorroga camino mas directo*/
//						CrtProrroga prorroga = prorrogas.get(0);
//						validaProrroga(prorroga, fechaLimite, lista);
//					}
//				}
//			}
			/**
			 * Validacion de cédulas completas corrección
			 */
			
			//Obtenermos los periodos disponibles para la solicitud de corrección
			@SuppressWarnings("unchecked")
			List<BigDecimal> periodos =  (List<BigDecimal>)this.getSession().createSQLQuery(" select CVE_EJERCICIO from CRT_RP_EJERCICIO "
																+ " where CVE_ANEXOSOLCORRPAT IN (select CVE_ANEXOSOLCORRPAT from CRT_ANEXOSOLCORRPAT "
																+ "	where CVE_SOLICITUDCORR = "+solicitudCorreccion+") group by CVE_EJERCICIO").list();
			
			if(periodos!=null && !periodos.isEmpty()){
				for(BigDecimal periodo : periodos){
					
					//Obtenermos las cédulas presentadaas en la solicitud de corrección
					@SuppressWarnings("unchecked")
					List<BigDecimal> cedulasPresentadas = (List<BigDecimal>) this.getSession()
																				.createSQLQuery("select count(CVE_CEDULA) from CRT_CONTROL_FLUJO_CEDULAS "
																						 + " where CVE_SOLICITUDCORR = "+solicitudCorreccion+" "
																						 + " AND  CVE_ESTATUS = 3"
																						 + " AND  CVE_EJERCICIO = " + periodo
																						 + " AND  CVE_CEDULA IN (1,2,3,4,5,6,7)"
																		   ).list();
					
					if(cedulasPresentadas!=null && !cedulasPresentadas.isEmpty()){
						
						BigDecimal cantCedulasPresentadas = cedulasPresentadas.get(0);
						
						if(new Integer(cantCedulasPresentadas.toString()).intValue()<7){//Total de céudlas obligatorias 7
							ErrorValidation ev = new ErrorValidation();
							ev.setError(true);
							ev.setError("Las c\u00e9dulas del periodo "+periodo+" no están completas, favor de terminar el proceso [A,G,H,I,O,Q,R]");
							lista.add(ev);
						}else{
							
							//Verificamos que existan céudlas R para todos los periodos y Rps
							
							@SuppressWarnings("unchecked")
							List<BigDecimal> cantidadRPs =  (List<BigDecimal>)this.getSession()
									.createSQLQuery(" select CVE_ANEXOSOLCORRPAT from CRT_ANEXOSOLCORRPAT "
									+ "	where CVE_SOLICITUDCORR = "+solicitudCorreccion+" AND IN_TP_PATRON <> 'F'").list();
							
							@SuppressWarnings("unchecked")
							List<BigDecimal> cantidadCedulaR =  (List<BigDecimal>)this.getSession()
									.createSQLQuery(" select CVE_DETBASECOTOMIT FROM CRT_DETBASECOT_OMITIDA "
													+" WHERE CVE_ANEXOSOLCORRPAT IN (select CVE_ANEXOSOLCORRPAT from CRT_ANEXOSOLCORRPAT anexo "
																				+ "	where anexo.CVE_SOLICITUDCORR = "+solicitudCorreccion+" AND anexo.IN_TP_PATRON <> 'F')" 
													+ " AND CVE_EJERCICIO ="+periodo).list();
							
							if((cantidadRPs!=null && !cantidadRPs.isEmpty())  && cantidadCedulaR!=null && !cantidadCedulaR.isEmpty()){
								
								if(cantidadCedulaR.size()!=cantidadRPs.size()){
//									ErrorValidation ev = new ErrorValidation();
//									ev.setError(true);
//									ev.setError("No se han encontrado todas las c\u00e9dulas R del periodo "+periodo+" , favor de terminar el proceso");
//									lista.add(ev);
								}
							}else{
								ErrorValidation ev = new ErrorValidation();
								ev.setError(true);
								ev.setError("No se han encontrado todas las c\u00e9dulas R del periodo "+periodo+" , favor de terminar el proceso");
								lista.add(ev);
							}

						}
							
					}
				}
			}else{
				ErrorValidation ev = new ErrorValidation();
				ev.setError(true);
				ev.setError("No se encontraron periodos registrados para esta solicitud de correci\u00f3n");
				lista.add(ev);
			}
			//
			
		}
		return lista;
	}

	private Date recalculaFechaLimite(Date fecha) {
		Calendar fechaTmp = Calendar.getInstance();
		fechaTmp.setTime(fecha);
		
		List<CrcDiaInhabil> dias = obtenerDiasInhabiles();
		for(CrcDiaInhabil dia : dias){
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(dia.getFecha());
			
			//Dia inhabil
			if(ConstantesBusiness.isSameDay(fechaTmp, tmp)){
				fechaTmp.add(Calendar.DATE, 1);
			}
		}
		
		if(fechaTmp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			fechaTmp.add(Calendar.DATE, 2);
		}else if(fechaTmp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			fechaTmp.add(Calendar.DATE, 1);
		}
		
//		System.out.println("Vemos: " + ConstantesBusiness.dateToStringFormat(fechaTmp.getTime(), ConstantesBusiness.DD_MM_YYYY));
		
		return fechaTmp.getTime();
	}
	
	private void validaProrroga(CrtProrroga prorroga, Date fechaLimite, List<ErrorValidation> lista) {
		if(prorroga.getCveStatus().intValue() == ConstantesBusiness.ESTATUS_SOLICITADA){
			lista.add(new ErrorValidation("La prorroga para este folio de correccion esta en estatus de \"SOLICITADA\"", true));
		}else if(prorroga.getCveStatus().intValue() == ConstantesBusiness.ESTATUS_RECHAZADO){
			lista.add(new ErrorValidation("La prorroga para este folio de correccion esta en estatus de \"RECHAZADA\" y el motivo es: ".concat(prorroga.getTxMotivorazon()), true));
		}else if(prorroga.getCveStatus().intValue() == ConstantesBusiness.ESTATUS_APROBADA){
			int dias = 0;
			Calendar fechaLimita10 = Calendar.getInstance();
			Calendar today = Calendar.getInstance();
			fechaLimita10.setTime(fechaLimite);
			do{
				fechaLimita10.add(Calendar.DATE, 1);
				if(fechaLimita10.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
						fechaLimita10.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
					fechaLimita10.setTime(recalculaFechaLimite(fechaLimita10.getTime()));
					dias++;
				}
			}while(dias < 10);
			fechaLimita10.setTime(recalculaFechaLimite(fechaLimita10.getTime()));
			System.out.println("Es un dia inhabil la prorroga ?: " + ConstantesBusiness.dateToStringFormat(fechaLimita10.getTime(), ConstantesBusiness.DD_MM_YYYY));
			
			if(fechaLimita10.before(today) && 
					!ConstantesBusiness.isSameDay(fechaLimita10, today)){
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("La fecha l&iacute;mite para presentar este folio de correcci&oacute;n es (Dia/Mes/Anio):  ");
				buffer.append(ConstantesBusiness.dateToStringFormat(fechaLimite, ConstantesBusiness.DD_MM_YYYY));
				buffer.append(", tiene una prorroga \"APROBADA\", pero el tiempo de gracia de 10 d&iacute;as para presentar la correcci&oacute;n ha terminado: ");
				buffer.append(ConstantesBusiness.dateToStringFormat(fechaLimita10.getTime(), ConstantesBusiness.DD_MM_YYYY));
				
				lista.add(new ErrorValidation(buffer.toString(), true));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<SelectBean> tiposPromocionCarga(Integer idFlujo, Integer idTipo) {
		StringBuffer buffer = new StringBuffer();
		Query qry = null;
		buffer.append("select new mx.gob.imss.ctirss.correccion.bean.SelectBean(tipo.idTipo, tipo.descripcion) from mx.gob.imss.ctirss.correccion.model.CgcCatTipo tipo ");
		if(idTipo.intValue() != -1){
			buffer.append("where tipo.cgcCatflujo.idFlujo = :idFlujo and tipo.idTipo = :idtipo order by tipo.descripcion");
			qry = getSession().createQuery(buffer.toString());
			qry.setParameter("idFlujo", idFlujo);
			qry.setParameter("idtipo", idTipo);
		}else{
			buffer.append("where tipo.cgcCatflujo.idFlujo = :idFlujo order by tipo.descripcion");
			qry = getSession().createQuery(buffer.toString());
			qry.setParameter("idFlujo", idFlujo);
		}
		List<SelectBean> ops = qry.list();
		return ops;
	}

	@SuppressWarnings("unchecked")
	public List<SelectBean> origenesPromocion() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select new mx.gob.imss.ctirss.correccion.bean.SelectBean(origen.idOrigen, origen.descOrigen) from mx.gob.imss.ctirss.correccion.model.CgcCatOrigen origen");
		Query qry = getSession().createQuery(buffer.toString());
		return qry.list();
	}

	@SuppressWarnings("unchecked")
	public List<SelectBean> criterioSeleccionPromocion(Integer idTipo, Integer idOrigen) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select new mx.gob.imss.ctirss.correccion.bean.SelectBean(criterio.idCriterioseleccion, criterio.descCriterioseleccion) from mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion criterio ");
		buffer.append("where criterio.idTipo = :idTipo and criterio.idOrigen = :idOrigen");
		Query qry = getSession().createQuery(buffer.toString());
		qry.setParameter("idTipo", idTipo);
		qry.setParameter("idOrigen", idOrigen);
		return qry.list();
	}

	@SuppressWarnings("unchecked")
	public List<CrcDiaInhabil> obtenerDiasInhabiles() {
		Query qry = getSession().createQuery("from CrcDiaInhabil dia order by dia.fecha asc");
		List<CrcDiaInhabil> dias = qry.list();
		return dias;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> obtnerSolSeguimientoCorreccion(EstudioSolicitudCorreccionVO solicitudCorr) {

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT sc.cveSolicitudCorr, sc.nuFolio, sc.fecFechaRegistro, sc.cveStatus, st.txDescripcion ,")
				.append("pa.razonSocial, pa.registroPatronal")
				.append(" FROM CrtSolicitudcorr sc, CrtPresentacorr pc, CrtAnexosolcorrpat ac, SatPatron pa , CrcStatus st")
				.append(" WHERE ")
				.append(" sc.cveSubdelegacion=").append(solicitudCorr.getIdSubDelegacion())
				.append(" AND sc.cveSolicitudCorr=pc.cveSolicitudcorr")				
				.append(" AND sc.cveSolicitudCorr=ac.cveSolicitudCorr")
				.append(" AND ac.tipoPatron='C'").append(" AND ac.cvePatron=pa.cvePK")
				.append(" AND sc.cveStatus=st.cveStatus");
		
		if(solicitudCorr.getIdEstadoSel()!= -1){
			queryString.append(" AND sc.cveStatus=").append(solicitudCorr.getIdEstadoSel());
		}else{
			//queryString.append(" AND sc.cveStatus=").append(solicitudCorr.getCveEstatus());
		}
		
		if (solicitudCorr.getFolioCorr()!=null) {
			queryString.append(" AND sc.nuFolio='").append(solicitudCorr.getFolioCorr())
					   .append("'");
		} else if (solicitudCorr.getRegPatronal()!=null && solicitudCorr.getEjercicioAnio()!= null) {
			queryString.append(" AND SUBSTR(pa.registroPatronal,0,10)='").append(solicitudCorr.getRegPatronal())
						.append("'")
			            .append(" AND to_char(pc.fecElaborapre,'YYYY') = '").append(solicitudCorr.getEjercicioAnio())
			            .append("'");
		} else if (solicitudCorr.getFechaPresentaIni()!=null && solicitudCorr.getFechaPresentaFin()!=null && solicitudCorr.getIdEstadoSel()!= null) {
			//TODO OGBO cambiar por un between 
			queryString.append(" AND TO_NUMBER(to_char(pc.fecElaborapre,'YYMMDD')) >=").append(Functions.dateToNumberAsString(solicitudCorr.getFechaPresentaIni()))
					   .append(" AND TO_NUMBER(to_char(pc.fecElaborapre,'YYMMDD')) <=").append(Functions.dateToNumberAsString(solicitudCorr.getFechaPresentaFin()));
					 // .append(" AND cveStatus=").append(solicitudCorr.getIdEstadoSel());
					   
		}
		
		Query query = getSession().createQuery(queryString.toString());		
		return query.list();
	}

	@Override
	public Object[] obtenerDetalleSolCorr(Integer idSolicitud) {
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT sc.cveSolicitudCorr, sc.nuFolio, pc.fecElaborapre, ")
				.append("pa.razonSocial, pa.registroPatronal, sc.fecFechaPeriodoIni, ")
				.append("sc.fecFechaPeriodoFin, sc.idTipoSolicitud, sc.cveNumeroRegObra, ")
				.append("pc.nuComprobantepago, pc.nuCompromovafil, pc.nuDoctosustento, pc.cvePresentacorr")
				.append(" FROM CrtSolicitudcorr sc, CrtPresentacorr pc, CrtAnexosolcorrpat ac, SatPatron pa")
				.append(" WHERE sc.cveSolicitudCorr=").append(idSolicitud)
				.append(" AND sc.cveSolicitudCorr=pc.cveSolicitudcorr")				
				.append(" AND sc.cveSolicitudCorr=ac.cveSolicitudCorr")
				.append(" AND ac.tipoPatron='C'").append(" AND ac.cvePatron=pa.cvePK");
		
		Query query = getSession().createQuery(queryString.toString());
		return (Object[]) query.uniqueResult();
	}

	/**
	 * Obtienen las solicitudes de correccion de una invitacion.
	 * @author CesarAgustin
	 * @version 1.0.0
	 * @since 16/07/2012
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CrtSolicitudcorr> obtenerCorrInvitacionPorCveInvitacion(
			BigDecimal cveInvitacion) {
		
		StringBuffer queryString =  new StringBuffer();
		queryString.append("SELECT ci.crtSolicitudcorr ")
					.append("FROM CrtCorrPromInvita ci ")
					.append("WHERE ci.crtInvitacion.cveInvitacion=")
					.append(cveInvitacion);
		
		Query query = getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	public CrtSolicitudcorr getByClaveSolCorr(Integer claveSolCorr) {
		// TODO Auto-generated method stub
		StringBuffer qu=new StringBuffer();
		qu.append("FROM CrtSolicitudcorr co where co.cveSolicitudCorr=:cveSolicitud ");
		Query query=getSession().createQuery(qu.toString());
		query.setParameter("cveSolicitud", claveSolCorr);
		return (CrtSolicitudcorr) query.uniqueResult();
	}
}
