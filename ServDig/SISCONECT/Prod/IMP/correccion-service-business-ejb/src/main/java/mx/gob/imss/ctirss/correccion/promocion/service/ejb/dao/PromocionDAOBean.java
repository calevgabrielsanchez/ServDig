package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacTipoObra;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatIncidencia;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatReltrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.CatalogosServiceRemote;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.DeteccionServiceRemote;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoPago;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.model.ViewRelaciones;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.RegularizacionServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.CatalogoServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.PatronesServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.DomiciliosInegiServiceRemote;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless
public class PromocionDAOBean<T extends AbstractModel> extends AbstractRespository implements PromocionDAOLocal<T>{
	
	@EJB DomiciliosInegiServiceRemote<T> domiciliosService;
	
	@EJB CatalogosServiceRemote catalogosService;	
	
	@EJB CatalogoServiceRemote<T> catalogoService;
	
	@EJB PatronesServiceRemote patronesService;
	
	@EJB DeteccionServiceRemote<T> deteccionService;
	
	@EJB RegularizacionServiceRemote<T> regularizacionService;
	
	@EJB RegularizacionServiceRemote<T> regularizacionDetService;
	
	@EJB CatalogoDAOLocal<T> daoDelta;
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(PromocionDAOBean.class);
	public T agrega(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = PromocionDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		
		return resultados;
	}
	
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cvePromocion", ((CrtPromocion)filtro).getCvePromocion()));
		if(criteria.list() != null && criteria.list().size() > 0){
			filtro = (T) criteria.list().get(0);
		}else{
			filtro = null;
		}
		return filtro;
	}		
	
	public T consultaCriterioPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("idCriterioseleccion", ((CgcCatcriterioseleccion)filtro).getIdCriterioseleccion()));
		
		List lista=criteria.list();
		if(!lista.isEmpty())
			filtro = (T) lista.get(0);
		return filtro;
	}
	public T consultaPagoReplicadoPorFolio(T filtro) {
		
		CgtAnexoPago pago = (CgtAnexoPago)filtro;
		
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());

		if(pago.getFolio()!=null)
			criteria.add(Restrictions.eq("folio", pago.getFolio()));
		if(pago.getCopact()!=null)
			criteria.add(Restrictions.eq("copact", pago.getCopact()));
		if(pago.getCoprec()!=null)
			criteria.add(Restrictions.eq("coprec", pago.getCoprec()));
		if(pago.getCopsp()!=null)
			criteria.add(Restrictions.eq("copsp", pago.getCopsp()));
		if(pago.getCopperiodo()!=null)
			criteria.add(Restrictions.eq("copperiodo", pago.getCopperiodo()));
		if(pago.getCopmultas()!=null)
			criteria.add(Restrictions.eq("copmultas", pago.getCopmultas()));
		if(pago.getRcvact()!=null)
			criteria.add(Restrictions.eq("rcvact", pago.getRcvact()));
		if(pago.getRcvrec()!=null)
			criteria.add(Restrictions.eq("rcvrec", pago.getRcvrec()));
		if(pago.getRcvsp()!=null)
			criteria.add(Restrictions.eq("rcvsp", pago.getRcvsp()));
		if(pago.getRcvperiodo()!=null)
			criteria.add(Restrictions.eq("rcvperiodo", pago.getRcvperiodo()));
		if(pago.getRcvmultas()!=null)
			criteria.add(Restrictions.eq("rcvmultas", pago.getRcvmultas()));
		if(pago.getCvePatron()!=null)
			criteria.add(Restrictions.eq("cvePatron", pago.getCvePatron()));
		if(pago.getFecFechareg()!=null)
			criteria.add(Restrictions.eq("fecFechareg", pago.getFecFechareg()));
		if(pago.getFechapago()!=null)
			criteria.add(Restrictions.eq("fechapago", pago.getFechapago()));
		if(pago.getIdProceso()!=null)
			criteria.add(Restrictions.eq("idProceso", pago.getIdProceso()));
		
		filtro = (T) criteria.list().get(0);
		return filtro;
	}		
	
	public T modificar(T model) {
		this.getSession().update(model);
		this.getSession().flush();
		return model;
	}
	
	public void elimina(T model) {
		model = (this.consultaPagoReplicadoPorFolio(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}
	
	public CrtDeteccion obtieneDeteccionporClave(CrtDeteccion filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cveDeteccion", ((CrtDeteccion)filtro).getCveDeteccion()));
		filtro = (CrtDeteccion) criteria.list().get(0);
		return filtro;
	}
	
	public CrtDeteccion obtieneObraporNumeroRegistro(CrtDeteccion obra){
		List<SatIncidencia> lstIncidencias = new ArrayList<SatIncidencia>();
		List query = this.getSession().createSQLQuery("SELECT " +
				"RF.CVE_PK, RF.FEC_FECHAINICIO_FC, RF.FEC_FECHATERMINO_FC, RF.CVE_NROREGOBRA, " +
				"RF.CVE_FK_PATRON, RF.CVE_FK_UBICACION, RF.NOM_RAZONSOCIAL, RF.NUM_REGISTROPATRONAL, " +
				"RF.FEC_INICIO_2, RF.FEC_TERMINO_2, RF.CVE_FK_INCIDENCIA, RF.INCIDENCIA, RF.RELTRABCVE_FK_OBRA, " +
				"RF.NUM_PERIODO, RF.DOM_CALLE, RF.NUM_CODIGOPOSTAL, RF.REF_COLONIA, RF.NUM_NROEXT, RF.CVE_FK_MUNICIPIO, " +
				"RF.CVE_SUBDELEG, RF.CVE_CODIGO, RF.CVE_FK_DELEGACION " +
				"FROM SATIC_OBRASENPROCESO RF WHERE " +
				"RF.CVE_NROREGOBRA = '"+obra.getNumRegObra()+"' AND RF.CVE_SUBDELEG = "+obra.getSdelegOrig()+" " +
				"ORDER BY RF.FEC_FECHAINICIO_FC DESC").list();
		
		if(query.size()>0){			
			Iterator itera = query.iterator();			
			while(itera.hasNext()){				
				ViewRelaciones rel = new ViewRelaciones((Object[])itera.next());
				obra.setCveFkPatron(rel.getCVE_FK_PATRON().longValue());
				obra.setNumRegObra(rel.getCVE_NROREGOBRA().toString());
				obra.setRegPatron(rel.getNUM_REGISTROPATRONAL());
				obra.setFechaIncial(rel.getFEC_FECHAINICIO_FC().toString().length()>=10?rel.getFEC_FECHAINICIO_FC().toString().substring(0, 10):"");
				obra.setFechaFinal(rel.getFEC_FECHATERMINO_FC().toString().length()>=10?rel.getFEC_FECHATERMINO_FC().toString().substring(0, 10):"");
				obra.setFechaEstimIncio(rel.getFEC_INICIO_2().toString().length()>=10?rel.getFEC_INICIO_2().toString().substring(0, 10):"");
				obra.setFechaEstTerm(rel.getFEC_TERMINO_2().toString().length()>=10?rel.getFEC_TERMINO_2().toString().substring(0, 10):"");				
				obra.setNumRegObra(rel.getCVE_NROREGOBRA().toString()!=null?rel.getCVE_NROREGOBRA().toString():"");
				obra.setDomCalle(rel.getDOM_CALLE()!=null?rel.getDOM_CALLE():"");
				obra.setRefColonia(rel.getREF_COLONIA()!=null?rel.getREF_COLONIA():"");
				obra.setNumNroext(rel.getNUM_NROEXT()!=null?rel.getNUM_NROEXT():"");
				obra.setNumCodigopostal(rel.getNUM_CODIGOPOSTAL()!=null?rel.getNUM_CODIGOPOSTAL():"");
				if(rel.getCVE_FK_INCIDENCIA()!=null && rel.getCVE_FK_INCIDENCIA().intValue()!=0){
					obra.setIncidencia(this.catalogosService.getNombreIncidenciaById(new Integer(rel.getCVE_FK_INCIDENCIA().intValue())));
				}else{
					obra.setIncidencia("SIN INCIDENCIA");
				}	
			}			
		}
//		SatObra obraAux = new SatObra();
//		obraAux.setCveNroregobra(Long.valueOf(obra.getNumRegObra()));
//		obraAux = this.obtieneObraporNumObraLocal(obraAux);
//		if(obraAux != null){
//			SatIncidencia satIncidencia = new SatIncidencia();
//			satIncidencia.setCveFkObra(obraAux.getCvePk());
//			lstIncidencias = this.obtieneIncidenciasporCveObraLocal(satIncidencia);
//			if(lstIncidencias != null && lstIncidencias.size() > 0){
//				obra.setFechaFinal(Functions.dateToString(lstIncidencias.get(0).getFecFechaterminoFc()));
//			}
//		}
		return obra;
	}
	
	/**
	 * Metodo que busca por numero de obra, la obra
	 * @author Enrique Duran JImenez
	 * @since 24/07/2012
	 * @return SatObra
	 */
	private SatObra obtieneObraporNumObraLocal(SatObra model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveNroregobra", ((SatObra)model).getCveNroregobra()));
		model = (SatObra) criteria.uniqueResult();
		return model;
	}
	
	/**
	 * Metodo que busca incidencias por cveFkObra
	 * @author Enrique Duran JImenez
	 * @since 24/07/2012
	 * @return List<satIncidencias>
	 */
	public List<SatIncidencia> obtieneIncidenciasporCveObraLocal(SatIncidencia model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveFkObra", ((SatIncidencia)model).getCveFkObra()));
		criteria.addOrder(Order.desc("fecFecharegistroFc"));
		return criteria.list();
		}
	
	public List<CrtDeteccion> obtieneObraporNumReg(CrtDeteccion obra){
		List<CrtDeteccion> lstDet = new ArrayList<CrtDeteccion>();
		List query = this.getSession().createSQLQuery("SELECT " +
				"RF.CVE_PK, FEC_FECHAINICIO_FC, RF.FEC_FECHATERMINO_FC, RF.CVE_NROREGOBRA, " +
				"RF.CVE_FK_PATRON, RF.CVE_FK_UBICACION, RF.NOM_RAZONSOCIAL, RF.NUM_REGISTROPATRONAL, " +
				"RF.FEC_INICIO_2, RF.FEC_TERMINO_2, RF.CVE_FK_INCIDENCIA, RF.INCIDENCIA, RF.RELTRABCVE_FK_OBRA, " +
				"RF.NUM_PERIODO, RF.DOM_CALLE, RF.NUM_CODIGOPOSTAL, RF.REF_COLONIA, RF.NUM_NROEXT, RF.CVE_FK_MUNICIPIO, " +
				"RF.CVE_SUBDELEG, RF.CVE_CODIGO, RF.CVE_FK_DELEGACION " + 
				"FROM SATIC_REL_TRAB_FALTANTES RF WHERE " +
				"RF.CVE_NROREGOBRA = '"+obra.getNumRegObra()+"' AND RF.CVE_SUBDELEG = "+obra.getSdelegOrig()+" ORDER BY RF.FEC_FECHAINICIO_FC DESC").list();
		
		if(query.size()>0){			
			Iterator itera = query.iterator();			
			while(itera.hasNext()){				
				ViewRelaciones rel = new ViewRelaciones((Object[])itera.next());
				CrtDeteccion obraSatic = new CrtDeteccion(); 
				obraSatic.setCveFkPatron(rel.getCVE_FK_PATRON().longValue());
				obraSatic.setNumRegObra(rel.getCVE_NROREGOBRA().toString());
				obraSatic.setRegPatron(rel.getNUM_REGISTROPATRONAL());
				obraSatic.setFechaIncial(rel.getFEC_FECHAINICIO_FC().toString().length()>10?rel.getFEC_FECHAINICIO_FC().toString().substring(0, 10):"");
				obraSatic.setFechaFinal(rel.getFEC_FECHATERMINO_FC().toString().length()>10?rel.getFEC_FECHATERMINO_FC().toString().substring(0, 10):"");
				obraSatic.setFechaEstimIncio(rel.getFEC_INICIO_2().toString().length()>10?rel.getFEC_INICIO_2().toString().substring(0, 10):"");
				obraSatic.setFechaEstTerm(rel.getFEC_TERMINO_2().toString().length()>10?rel.getFEC_TERMINO_2().toString().substring(0, 10):"");				
				obraSatic.setNumRegObra(rel.getCVE_NROREGOBRA().toString()!=null?rel.getCVE_NROREGOBRA().toString():"");
				obraSatic.setDomCalle(rel.getDOM_CALLE()!=null?rel.getDOM_CALLE():"");
				obraSatic.setRefColonia(rel.getREF_COLONIA()!=null?rel.getREF_COLONIA():"");
				obraSatic.setNumNroext(rel.getNUM_NROEXT()!=null?rel.getNUM_NROEXT():"");
				obraSatic.setNumCodigopostal(rel.getNUM_CODIGOPOSTAL()!=null?rel.getNUM_CODIGOPOSTAL():"");
				obraSatic.setPeriodo(rel.getNUM_PERIODO()!=null?rel.getNUM_PERIODO().toString():"");
				if(rel.getCVE_FK_INCIDENCIA()!=null && rel.getCVE_FK_INCIDENCIA().intValue()!=0){
					obraSatic.setIncidencia(this.catalogosService.getNombreIncidenciaById(new Integer(rel.getCVE_FK_INCIDENCIA().intValue())));
				}else{
					obraSatic.setIncidencia("SIN INCIDENCIA");
				}
				lstDet.add(obraSatic);
			}			
		}
		
		return lstDet;
	}
	
	public DatosSalidaPaginador<T> paginaDeteccion(DatosEntradaPaginador<T> params) {
		
		String numInterior = "";
		String numExterior = "";
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultFinal = new ArrayList<CrtDeteccion>();
		Long cvePatron = null;
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		if(det.getRegPatron() != null){
			cvePatron = this.getIdPatByRegPat(det.getRegPatron());
		}
		if(cvePatron != null){
			det.setCveFkPatron(cvePatron);
		}
		
		logger.debug(params.getModelo().getClass());
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"+
				"b.nuFoliodeteccion, b.fecFechadeteccionFc,"+
				"b.nuReportectrlobra, b.nomRazonsocial, b.txCurppatron,"+
				"b.txRfcpatron, b.sdelegOrig, "+
				"b.tipClaseobra, b.cvePkTipObra,"+
				"b.cvePkFaseConst, b.desDependenciapub,"+
				"b.desDepcontratante, b.canSuperficie,"+
				"b.impCostoobra, b.fecFechainicioEst,"+
				"b.fecFechaterminoEst, b.porAvanceobraEst,"+
				"b.txTelefono, b.txEmail, b.idPromovido,"+
				"b.fecFechareg, b.cveUsuario, b.domicilioId, b.cveFkPatron,b.idMotivocancelacion) " +
				"from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b where " +
				" b.idPromovido = 1 and b.idMotivocancelacion is null " +
				" and b.cveDeteccion not in ( select p.cveDeteccion from CrtPromocion p where p.cveDeteccion is not null ) " +
				" and b.sdelegOrig = "+ det.getSdelegOrig();
		
		if(det.getNuFoliodeteccion()!=null){
			sql += " and b.nuFoliodeteccion = '"+det.getNuFoliodeteccion()+"'";						
		}if(det.getCveFkPatron()!=null){
			sql += " and b.cveFkPatron = "+det.getCveFkPatron();						
		}if(det.getFecFechainicioEst()!=null){
			sql += " and  TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) >= "+Functions.dateToNumberAsString(det.getFecFechainicioEst());
		}if(det.getFecFechaterminoEst()!=null){
			sql += " and  TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) <= "+Functions.dateToNumberAsString(det.getFecFechaterminoEst());
		}
		logger.debug("ordernar por " + params.getiSortCol_0() + " " + params.getsSortDir_0());
		sql += " order by b.fecFechareg desc";
		Query query = null;
		
			logger.debug(sql);
			query = this.getSession().createQuery(sql);
			query.setFirstResult(params.getiDisplayStart());
			query.setMaxResults(params.getiDisplayLength());
			
			if((det.getFecFechainicioEst() != null && det.getFecFechaterminoEst() != null) || det.getNuFoliodeteccion() != null || det.getCveFkPatron() != null){
				result = query.list();
			}else{
				result = new ArrayList<T>();
			}
		
		
		if(!result.isEmpty()){
			Iterator<?> iterator = result.iterator();
			while(iterator.hasNext()){
				CrtDeteccion deteccion = (CrtDeteccion)iterator.next();
				DgDomicilioGeografico dg = new DgDomicilioGeografico();
				dg.setDomicilioId(deteccion.getDomicilioId());
				dg = (DgDomicilioGeografico)domiciliosService.consultaPorClave((T)dg);
				if(dg==null){
					System.out.println("Domicilio geografico no encontrado "+deteccion.getCveDeteccion());
					continue;
				}
				deteccion.setRefColonia(dg.getDgAsentamiento()!=null ? dg.getDgAsentamiento().getNomAsen():"");
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
					deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
					deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
				if(dg.getNomvial()!=null){
					if(!dg.getNomvial().equalsIgnoreCase(""))
						deteccion.setDomCalle(dg.getNomvial());
				}else{
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
				}
				if(dg.getNumextnum() != null){
					numExterior = dg.getNumextnum().toString();					
				}
				if(dg.getNumextalf() != null){
					numExterior += " " +dg.getNumextalf();
				}
				deteccion.setNumNroext(numExterior);
				if(dg.getNumintnum()!=null){
					numInterior = dg.getNumintnum().toString();
				}
				if(dg.getNumintalf() != null){
					numInterior += " " + dg.getNumintalf();
				}
				deteccion.setNumNroint(numInterior);
				deteccion.setNumCodigopostal(dg.getDgCodigosPostales().getId().getCodigo());
				resultFinal.add(deteccion);
			}
			/*Se debe de obtener el numero total de registros en la base de datos*/
	            query.setFirstResult(0);
	            query.setMaxResults(-1);
	            final List temp = query.list();
	            logger.debug("temp.size() :: " + temp.size());
	            response.setiTotalRecords(temp.size());
	            response.setiTotalDisplayRecords(temp.size());
		}
		
			
		response.setAaData((List<T>)resultFinal);
		
		return response;
	}
	
	public DatosSalidaPaginador<T> paginaExConstruccion(DatosEntradaPaginador<T> params) {
		
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultFinal = new ArrayList<CrtDeteccion>();
		Long cvePatron = null;
		String numExt = "";
		String numInt = "";
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		if(det.getRegPatron() != null){
			cvePatron = this.getIdPatByRegPat(det.getRegPatron());
		}
		if(cvePatron != null){
			det.setCveFkPatron(cvePatron);
		}
		
		logger.debug(params.getModelo().getClass());
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"+
			"b.nuFoliodeteccion, b.fecFechadeteccionFc,"+
			"b.nuReportectrlobra, b.nomRazonsocial, b.txCurppatron,"+
			"b.txRfcpatron, b.sdelegOrig, "+
			"b.tipClaseobra, b.cvePkTipObra,"+
			"b.cvePkFaseConst, b.desDependenciapub,"+
			"b.desDepcontratante, b.canSuperficie,"+
			"b.impCostoobra, b.fecFechainicioEst,"+
			"b.fecFechaterminoEst, b.porAvanceobraEst,"+
			"b.txTelefono, b.txEmail, b.idPromovido,"+
			"b.fecFechareg, b.cveUsuario, b.domicilioId, b.cveFkPatron,b.idMotivocancelacion) " +
			"from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b where " +
			" b.idPromovido = 0 and b.idMotivocancelacion is null " +
			" and b.cveDeteccion not in ( select p.cveDeteccion from CrtPromocion p where p.cveDeteccion is not null ) " +
			" and b.cveDeteccion not in ( select i.cveDeteccion from CrtInvitacion i where i.cveDeteccion is not null ) " +
			" and b.sdelegOrig = "+ det.getSdelegOrig();
		
		if(det.getNuFoliodeteccion()!=null){
			sql += " and b.nuFoliodeteccion = '"+det.getNuFoliodeteccion()+"'";	
			det.setCveFkPatron(null);
			det.setFecFechainicioEst(null);
			det.setFecFechaterminoEst(null);
		}if(det.getCveFkPatron()!=null){
			sql += " and b.cveFkPatron = "+det.getCveFkPatron();
			det.setFecFechainicioEst(null);
			det.setFecFechaterminoEst(null);
		}if(det.getFecFechainicioEst()!=null){
			sql += " and  TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) >= "+Functions.dateToNumberAsString(det.getFecFechainicioEst());
		}if(det.getFecFechaterminoEst()!=null){
			sql += " and  TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) <= "+Functions.dateToNumberAsString(det.getFecFechaterminoEst());
		}

		sql += " order by b.nuFoliodeteccion desc";
		
		System.out.println("Tamaño : " + sql.length());
		if(sql.length() > 707){
			logger.debug(sql);
			Query query = this.getSession().createQuery(sql);
				
			result = query.list();
		}
		
		if(result.size()>0){
			Iterator<?> iterator = result.iterator();
			while(iterator.hasNext()){
				CrtDeteccion deteccion = (CrtDeteccion)iterator.next();
				DgDomicilioGeografico dg = new DgDomicilioGeografico();
				dg.setDomicilioId(deteccion.getDomicilioId());
				dg = (DgDomicilioGeografico)domiciliosService.consultaPorClave((T)dg);
				deteccion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
					deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
					deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
				if(dg.getNomvial()!=null){
					if(!dg.getNomvial().equalsIgnoreCase(""))
						deteccion.setDomCalle(dg.getNomvial());
				}else{
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
				}
				if(dg.getNumextnum() != null){
					numExt = dg.getNumextnum().toString();
				}
				if(dg.getNumextalf() != null && !dg.getNumextalf().equals("")){
					numExt += " - " + dg.getNumextalf();
				}
				deteccion.setNumNroext(numExt);
				if(dg.getNumintnum() != null){
					numInt = dg.getNumintnum().toString();
				}
				if(dg.getNumintalf() != null && !dg.getNumintalf().equals("")){
					numInt += " - " + dg.getNumintalf();
				}
				deteccion.setNumNroint(numInt);
				deteccion.setNumCodigopostal(dg.getDgCodigosPostales().getId().getCodigo());
				if(deteccion.getCveFkPatron()!=null){
					SatPatron patron = this.patronesService.getById(new Long(deteccion.getCveFkPatron()));					
					deteccion.setRegPatron(patron.getRegistroPatronal());
				}
				resultFinal.add(deteccion);
			}
		}
		
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(!resultFinal.isEmpty()){
			iTotalRecords = result.size();
			iTotalDisplayRecords = result.size();
		}
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */

		response.setAaData((List<T>)resultFinal);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	/**
	 * 
	 */
	public DatosSalidaPaginador<T> paginaSaticB(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultTemp = new ArrayList<CrtDeteccion>();
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		if(det.getNuFoliodeteccion()==null){
			det.setNuFoliodeteccion("");
		}		
		
		logger.debug(params.getModelo().getClass());
		
		String queryStr = "SELECT RF.CVE_PK, RF.FEC_FECHAINICIO_FC, RF.FEC_FECHATERMINO_FC, RF.CVE_NROREGOBRA, " +
				"RF.CVE_FK_PATRON, RF.CVE_FK_UBICACION, RF.NOM_RAZONSOCIAL, RF.NUM_REGISTROPATRONAL, " +
				"RF.RELTRABCVE_FK_OBRA, RF.DOM_CALLE, RF.NUM_CODIGOPOSTAL, RF.REF_COLONIA, RF.NUM_NROEXT, RF.CVE_FK_MUNICIPIO, " +
				"RF.CVE_SUBDELEG, RF.CVE_CODIGO, RF.CVE_FK_DELEGACION, RF.DES_TIPOOBRA, RF.INCIDENCIA, RF.CAN_SUPERFICIE, " +
				"RF.IMP_IMPORTE, RF.TIP_CLASEOBRA, RF.CVE_FK_OBRAPRINCIPAL " + 
				" FROM SATIC_OBRASENPROCESO RF WHERE " +
				"RF.CVE_NROREGOBRA NOT IN(SELECT P.CVE_NROREGOBRA_SATIC " +
				"FROM CRT_PROMOCION P WHERE substr(P.NU_FOLIOPROMOCION,6,6) = 'SATICB')" +
				" AND RF.CVE_SUBDELEG = "+ det.getSdelegOrig()+
				//" AND (RF.INCIDENCIA IS NULL OR RF.INCIDENCIA NOT IN('TERMINACION','REGULARIZACION DE AVANCE'))"+ 
				" AND TO_NUMBER(to_char(RF.FEC_FECHATERMINO_FC,'YYMMDD')) >= "+ Functions.dateToNumberAsString(det.getFecFechainicioEst())+ 
				" AND TO_NUMBER(to_char(RF.FEC_FECHATERMINO_FC,'YYMMDD')) <= "+ Functions.dateToNumberAsString(det.getFecFechaterminoEst())+
				" GROUP BY RF.CVE_PK, RF.FEC_FECHAINICIO_FC, RF.FEC_FECHATERMINO_FC, RF.CVE_NROREGOBRA, RF.CVE_FK_PATRON, RF.CVE_FK_UBICACION," +
				" RF.NOM_RAZONSOCIAL, RF.NUM_REGISTROPATRONAL, RF.RELTRABCVE_FK_OBRA, RF.DOM_CALLE, RF.NUM_CODIGOPOSTAL, RF.REF_COLONIA, NUM_NROEXT," +
				" RF.CVE_FK_MUNICIPIO, RF.CVE_SUBDELEG, RF.CVE_CODIGO, RF.CVE_FK_DELEGACION , RF.DES_TIPOOBRA, RF.INCIDENCIA, RF.CAN_SUPERFICIE, " +
				" RF.IMP_IMPORTE, RF.TIP_CLASEOBRA, RF.CVE_FK_OBRAPRINCIPAL  ";
		queryStr = addOrderBy(params, queryStr);
		Query query = this
				.getSession()
				.createSQLQuery(queryStr);
		
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		logger.debug("pre");
		List datos =query.list();
		logger.debug("post");
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		
		
		if(datos.size()>0){			
			Iterator itera = datos.iterator();			
			while(itera.hasNext()){
				CrtDeteccion deteccion = new CrtDeteccion();
				Object[] obj = (Object[])itera.next();
				String fechaIn = ((Timestamp)obj[1]+"").substring(0, 10);
				String fechaFin =  ((Timestamp)obj[2]+"").substring(0, 10);
				deteccion.setNumRegObra(obj[3]!=null ? (BigDecimal)obj[3]+"" : "");
				deteccion.setRegPatron(obj[7]!=null ? (String)obj[7] : "");
				deteccion.setFechaIncial(fechaIn!=null ? fechaIn.substring(8, 10)+"-"+fechaIn.substring(5, 7)+"-"+fechaIn.substring(0, 4) : "");
				deteccion.setFechaFinal(fechaFin!=null ? fechaFin.substring(8, 10)+"-"+fechaFin.substring(5, 7)+"-"+fechaFin.substring(0, 4) : "");								
				if(obj[4]!=null){
					SatPatron patron = this.patronesService.getById(((BigDecimal)obj[4]).longValue());
					if(patron!=null)
						deteccion.setNomRazonsocial(patron.getRazonSocial());
				}	
				deteccion.setDesTipoObra(obj[17] != null ? (String)obj[17] : "");
				//deteccion.setDesFaseCostruccion(obj[18] != null ? (String)obj[18] : "");
				deteccion.setIncidencia(obj[18] != null ? (String)obj[18] : "");
				deteccion.setCanSuperficie(obj[19] != null ? (BigDecimal)obj[19] : null);
				deteccion.setImpCostoobra(obj[20] != null ? (BigDecimal)obj[20] : null);
				deteccion.setTipClaseobra(obj[21] != null ? (String)obj[21] : "");
				deteccion.setBandera(obj[22] != null ? "SATIC 2" : "SATIC 1");
				resultTemp.add(deteccion);
			}
			result = (List<T>)resultTemp;
		}
							
		/*Se debe de obtener el numero total de registros en la base de datos*/
        query.setFirstResult(0);
        query.setMaxResults(-1);
        logger.debug("contando...");
        final List temp = query.list();
        logger.debug("temp.size() :: " + temp.size());
        response.setiTotalRecords(temp.size());
        response.setiTotalDisplayRecords(temp.size());
		
		response.setAaData(result);
		
		return response;
	}

	/**
	 * @param params
	 * @param queryStr
	 * @return
	 */
	private String addOrderBy(DatosEntradaPaginador<T> params, String queryStr) {

		if (params.getiSortCol_0().equals("4")) {
			queryStr += " ORDER BY RF.DES_TIPOOBRA " + params.getsSortDir_0();
		} else {
			if (params.getiSortCol_0().equals("5")) {
				queryStr += " ORDER BY RF.DES_FASECONSTRUCCION "
						+ params.getsSortDir_0();
			} else {
				if (params.getiSortCol_0().equals("6")) {
					queryStr += " ORDER BY RF.CAN_SUPERFICIE "
							+ params.getsSortDir_0();
				} else {
					if (params.getiSortCol_0().equals("7")) {
						queryStr += " ORDER BY RF.IMP_IMPORTE "
								+ params.getsSortDir_0();
					} else {
						if (params.getiSortCol_0().equals("8")) {
							queryStr += " ORDER BY RF.TIP_CLASEOBRA "
									+ params.getsSortDir_0();
						} else {
							if (params.getiSortCol_0().equals("0")) {
								queryStr += " ORDER BY RF.CVE_NROREGOBRA ";
							} else {
								queryStr += " ORDER BY RF.CVE_NROREGOBRA ";
							}
						}
					}
				}
			}
		}
		return queryStr;
	}
	
	public DatosSalidaPaginador<T> consultaPromocion(DatosEntradaPaginador<T> params){
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		List<T> result = new ArrayList<T>();
		List<CrtPromocion> resultFinal = new ArrayList<CrtPromocion>();
		
		CrtPromocion  pro = (CrtPromocion) params.getModelo();		
		long rolUsr=pro.getUsuarioFirmado().getCveRol();
		String idUsr=pro.getUsuarioFirmado().getCurpUsuario();
		
		logger.debug(params.getModelo().getClass());
		
		String sql = "select new mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion(p.cvePromocion, p.nuFoliopromocion,"+
				" p.nuOficiopro, p.fecFechaoficiopro, p.fecFechanotif,"+
				" p.cveFkPatron, p.cveUsuario, p.cveDeteccion, p.txObservaciones, p.cveSelector, p.idCriterioSeleccion,p.cveAuditorAsignado) " +
				" from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p where p.sdelegOrig ="+pro.getSdelegOrig()+ " ";

			// Se agrega filtro por usuario exceptuando roles de jefe
			// solo pueden consultar los folios que tengan asignados		
			if (rolUsr!=ConstantesBusiness.ROL_JEFE_DEPARTAMENTO_AUDITORIA_A_PATRONES
				&& rolUsr!=ConstantesBusiness.ROL_JEFE_OFICINA_CORRECCION_Y_DICTAMEN
				&& rolUsr!=ConstantesBusiness.JEFE_OF_CORRECCION){
				sql += " and p.cveAuditorAsignado='" + idUsr+"'";
			}
			if( null != pro.getTipoPromocion() && !pro.getTipoPromocion().equals("")){ 
					sql += " and p.cveTipocorr = '"+pro.getTipoPromocion()+"'";						
				}
			if(pro.getFechaIncial()!=null && pro.getFechaFinal()!=null){
				sql += " and p.fecFechapai is null"+
				" and TO_NUMBER(to_char(p.fecFechaoficiopro,'YYMMDD')) >= "+Functions.dateToNumberAsString(pro.getFechaIncial())+
				" and TO_NUMBER(to_char(p.fecFechaoficiopro,'YYMMDD')) <= "+Functions.dateToNumberAsString(pro.getFechaFinal());			
			}else
			
			if(pro.getNuFoliopromocion()!=null && !pro.getNuFoliopromocion().equalsIgnoreCase("")){
				sql += " and p.nuFoliopromocion = '"+pro.getNuFoliopromocion()+"'";
			}if(pro.getCveFkPatron()!=null){
				sql += " and p.cveFkPatron = "+pro.getCveFkPatron();
			}if(pro.getCveEstatus()!=null){
				sql += " and p.cveEstatus in (" + CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId() + "," + CatEstatus.PROMOCION_REGULARIZADA.getId() +  "," + CatEstatus.EN_PROCESO_ATENCION_OFICIO_PROMOCION.getId() +")" ;
			}
			
			sql += " and p.idMotivoCancelacion is null "; 
			sql += " and p.cvePromocion not in (select pi.crtPromocion.cvePromocion from CrtCorrPromInvita pi where pi.crtPromocion.cvePromocion is not null)";  // Ninguna solicitud asociada
			sql += " and p.cvePromocion not in (select i.cvePromocion from CrtInvitacion i where i.cvePromocion is not null)";  // Ninguna invitacion asociada
			sql += " order by p.nuFoliopromocion desc "; 
			logger.debug("sql :: " + sql);
			Query query = this.getSession().createQuery(sql);	
			query.setFirstResult(params.getiDisplayStart());
			query.setMaxResults(params.getiDisplayLength());
			if(query.list().size()>0){
				result = query.list();
				Iterator<T> iterator = result.iterator();
				while(iterator.hasNext()){
					CrtPromocion promocion = (CrtPromocion)iterator.next();
					if(promocion.getCveFkPatron()!=null){
						SatPatron pat = this.patronesService.getById(promocion.getCveFkPatron());
						promocion.setRegPatron(pat.getRegistroPatronal());
						promocion.setRazonSocial(pat.getRazonSocial());
					}
					resultFinal.add(promocion);
				}
			}
		
				
		
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
//		/*Se debe de obtener el numero total de registros en la base de datos*/
//		if(!resultFinal.isEmpty()){
//			query.setFirstResult(0);
//			query.setMaxResults(-1);
//			final List temp = query.list();
//			logger.debug("temp.size() :: " + temp.size());
//			response.setiTotalRecords(temp.size());
//			response.setiTotalDisplayRecords(temp.size());
//		}
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */					

//		response.setAaData((List<T>)resultFinal);
//		response.setiTotalDisplayRecords(iTotalDisplayRecords);
//		response.setiTotalRecords(iTotalRecords);
		
			query.setFirstResult(0);
	        query.setMaxResults(-1);
	        logger.debug("contando...");
	        final List temp = query.list();
	        logger.debug("temp.size() :: " + temp.size());
	        response.setiTotalRecords(temp.size());
	        response.setiTotalDisplayRecords(temp.size());			
			response.setAaData(result);
			
		
		return response;		
	}
	/**
	 * @deprecated
	 */
	public DatosSalidaPaginador<T> paginaSaticBSinIncidencia(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		if(det.getNuFoliodeteccion()==null){
			det.setNuFoliodeteccion("");
		}
						
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(result!=null)
			iTotalRecords = result.size();
		
		logger.debug(params.getModelo().getClass());
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(P.registroPatronal, P.razonSocial, O.cveNroregobra, U.calle, " + 
            "U.numeroInterior, U.numeroExterior, U.colonia, U.codigoPotal, O.tipClaseobra, O.cveFkFaseconstruccion, " +
            "O.canSuperficie, O.impImporte, O.fecFechainicioFc, O.fecFechaterminoFc,I.fecFechainicioFc, I.fecFechaterminoFc, TI.desTipincidenc ) " +
            "from mx.gob.imss.ctirss.correccion.catalogos.model.SatObra O, mx.gob.imss.ctirss.correccion.model.SatPatron P, mx.gob.imss.ctirss.correccion.model.SatUbicacion U, " +
	    	"mx.gob.imss.ctirss.correccion.model.SacMunicipio M, mx.gob.imss.ctirss.correccion.catalogos.model.SacEntidadfed EF, mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion D, mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion SD, " +
	    	"mx.gob.imss.ctirss.correccion.catalogos.model.SatIncidencia I,mx.gob.imss.ctirss.correccion.catalogos.model.SacTipincidenc TI " + 
	    	"where SD.cvePk = "+det.getSdelegOrig()+" and O.cveFkPatron=P.cvePK and O.cveFkUbicacion=U.cvePK " +
            "and U.fkMunicipio = M.cvePK and M.fkEntidadFederativa = EF.cvePk " + 
            "and M.fkSubdelegacion = SD.cvePk and D.cvePk = SD.cveFkDelegacion " +
            "and O.cvePk in(select INC.cveFkObra from mx.gob.imss.ctirss.correccion.catalogos.model.SatIncidencia INC) " +
            "and O.cvePk not in(select RL.cveFkObra from mx.gob.imss.ctirss.correccion.catalogos.model.SatReltrabajadores RL) " +
            "and I.cveFkIncidencia = TI.cvePk and I.cveFkObra = O.cvePk " +
            "and to_date(to_char(O.fecFechainicioFc,'DD-MM-YYYY')) = to_date('"+Functions.dateToString2(det.getFecFechainicioEst())+"','DD-MM-YYYY')" +
            "and to_date(to_char(O.fecFechaterminoFc,'DD-MM-YYYY')) = to_date('"+Functions.dateToString2(det.getFecFechaterminoEst())+"','DD-MM-YYYY')" +
            "and to_date(to_char(O.fecFechaterminoFc,'DD-MM-YYYY')) < to_date('"+Functions.dateToString2(new Date())+"','DD-MM-YYYY')" +
            "and O.cveNroregobra not in (select PR.cveNroregobraSatic from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion PR)";


		logger.debug(sql);
		Query query = this.getSession().createQuery(sql);
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		
		if(query.list().size()>0)
			result = query.list();
		
		int iTotalDisplayRecords = 0;
		
		if(result!=null)
			iTotalDisplayRecords = result.size();

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
//	
//	public String obtieneFolioPromocion(Long del,Long subDel, String cad, String fecha){				
//		String sql = "select new java.lang.String(c.nuFoliopromocion) from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion c" +
//				" where c.cvePromocion in ( select  max(b.cvePromocion) from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion b" +
//				" where  substr(b.nuFoliopromocion,1,2)= '"+(del<10?"0"+del.toString():del.toString())+"'";
//		
//				if(subDel.intValue()>99){
//					sql += " and substr(b.nuFoliopromocion,3,3)= '"+(subDel<10?"0"+subDel.toString():subDel.toString())
//							+"' and substr(b.nuFoliopromocion,7,"+cad.length()+")= '"+cad;
//				}else{
//					sql += " and substr(b.nuFoliopromocion,3,2)= '"+(subDel<10?"0"+subDel.toString():subDel.toString())
//					+"' and substr(b.nuFoliopromocion,6,"+cad.length()+")= '"+cad;
//				}
//				sql += "' and to_char(b.fecFechareg,'YYYY') ="+fecha+")";				
//		Query query = this.getSession().createQuery(sql);
//		logger.debug("Resultados: "+query.list().size());		
//		
//		return (query.list().size()>0?(String)query.list().get(0):"");
//	}
	
	public String obtieneFolioInvitacion(Long del,Long subDel, String cad, String fecha){		
		String sql = "select new java.lang.String(c.nuFolioInvitacion) from mx.gob.imss.ctirss.correccion.model.CrtInvitacion c" +
				" where c.cveInvitacion in ( select  max(b.cveInvitacion) from mx.gob.imss.ctirss.correccion.model.CrtInvitacion b" +
				" where  substr(b.nuFolioInvitacion,1,2)= '"+(del<10?"0"+del.toString():del.toString())+"'";
		
				if(subDel.intValue()>99){
					sql += " and substr(b.nuFolioInvitacion,3,3)= '"+(subDel<10?"0"+subDel.toString():subDel.toString())
							+"' and substr(b.nuFolioInvitacion,7,"+cad.length()+")= '"+cad;
				}else{
					sql += " and substr(b.nuFolioInvitacion,3,2)= '"+(subDel<10?"0"+subDel.toString():subDel.toString())
					+"' and substr(b.nuFolioInvitacion,6,"+cad.length()+")= '"+cad;
				}
				sql += "' and to_char(b.fecFechareg,'YYYY') ="+fecha+")";				
		Query query = this.getSession().createQuery(sql);
		logger.debug("Resultados: "+query.list().size());		
		
		return (query.list().size()>0?(String)query.list().get(0):"");
	}

	@Override
	public CrtPromocion validaPromocionExistente(Long patronPK,	Date periodoInicial,Date periodoFinal) {
		Criteria criteria = this.getSession().createCriteria(CrtPromocion.class).
				 add(Restrictions.eq("cveFkPatron", patronPK)).
				 add(Restrictions.isNull("idMotivoCancelacion")).				 
				 add(Restrictions.or(Restrictions.between("fecFechanotif", periodoInicial, periodoFinal), Restrictions.between("fecFechaAtencion", periodoInicial, periodoFinal))).
				 add(Restrictions.or(Restrictions.eq("cveEstatus", 28L), Restrictions.eq("cveEstatus", 29L))).
				 //add(Restrictions.between("fecFechanotif", periodoInicial, periodoFinal)).				 
		         //add(Restrictions.eq("cveEstatus", 28L)).		         
		         add(Restrictions.isNull("fecFechaCancela")).
				 add(Restrictions.isNull("fecFechapai")).
				 addOrder(Order.desc("nuFoliopromocion"));
				
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			return (CrtPromocion)l.get(0);
		}
		return null;
		
	}
	
	public List<T> consultarSelector(T model) {
		// , int iDisplayStart,int iDisplayLength
		List<T> resultado;
		
		CrtSelector  selector = (CrtSelector) model;
		
		logger.debug("Estas es la Delegacion : " + selector.getSacDelegacion().getCvePk());
	
		
		String sql = "select new mx.gob.imss.ctirss.correccion.model.CrtSelector(s.cveSelector,s.cgcCatcriterioseleccion.idCriterioseleccion, " +
				"s.satPatron.cvePK,s.satPatron.registroPatronal,s.satPatron.razonSocial) " +
				"from mx.gob.imss.ctirss.correccion.model.CrtSelector s " +
				"where s.cgcCatcriterioseleccion.idCriterioseleccion = :lnCriterioSeleccion and s.sacDelegacion.cvePk = :inDelegacion and " +
				"s.sacSubdelegacion.cvePk = :inSubDelegacion and s.idPromocionado = 0";
		
		
		Query query = this.getSession().createQuery(sql);
		query.setParameter("inDelegacion",selector.getSacDelegacion().getCvePk());
		query.setParameter("inSubDelegacion",selector.getSacSubdelegacion().getCvePk());
		query.setParameter("lnCriterioSeleccion",selector.getCgcCatcriterioseleccion().getIdCriterioseleccion());

		
		if(query.list().isEmpty())
			resultado = new ArrayList<T>();
		else
			resultado = (List<T>) query.list();
		
		return resultado;
	}
	
	public DatosSalidaPaginador<T> paginaSelector(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();		
		
		CrtSelector  selector = (CrtSelector) params.getModelo();
		
		logger.debug("Estas es la Delegacion : " + selector.getSacDelegacion().getCvePk());
	
		
		String sql = "select new mx.gob.imss.ctirss.correccion.model.CrtSelector(s.cveSelector,s.cgcCatcriterioseleccion.idCriterioseleccion, " +
				"s.satPatron.cvePK, s.satPatron.registroPatronal,s.satPatron.razonSocial) " +
				"from mx.gob.imss.ctirss.correccion.model.CrtSelector s " +
				"where s.cgcCatcriterioseleccion.idCriterioseleccion = :lnCriterioSeleccion and s.sacDelegacion.cvePk = :inDelegacion and " +
				"s.sacSubdelegacion.cvePk = :inSubDelegacion and s.idPromocionado = 0";
		
		
		Query query = this.getSession().createQuery(sql);
		query.setParameter("inDelegacion",selector.getSacDelegacion().getCvePk());
		query.setParameter("inSubDelegacion",selector.getSacSubdelegacion().getCvePk());
		query.setParameter("lnCriterioSeleccion",selector.getCgcCatcriterioseleccion().getIdCriterioseleccion());
		
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		logger.debug("pre");
		List datos =query.list();
		logger.debug("post");
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		
		
		/*Se debe de obtener el numero total de registros en la base de datos*/
        query.setFirstResult(0);
        query.setMaxResults(-1);
        logger.debug("contando...");
        final List temp = query.list();
        logger.debug("temp.size() :: " + temp.size());
        response.setiTotalRecords(temp.size());
        response.setiTotalDisplayRecords(temp.size());
		
		response.setAaData(datos);
		
		return response;
	}
	
	public CrtPromocion verificaDuplicidad(Long subDelegacion, Long idCriterioseleccion, Long patron){
		
		CrtPromocion resultado = new CrtPromocion();

		String sql = "from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p " +
				"where p.idCriterioSeleccion= :idCriterioseleccion and p.sdelegOrig= :subDelegacion and p.cveFkPatron= :patron";		
		Query query = this.getSession().createQuery(sql);
		query.setParameter("subDelegacion",subDelegacion);
		query.setParameter("patron",patron);		
		query.setParameter("idCriterioseleccion",idCriterioseleccion);

		
		if(query.list().isEmpty())
			resultado = null;
		else
			resultado = (CrtPromocion) query.uniqueResult();
		
		return resultado;
	}
	
	public CrtSelector obtieneSelectorporClave(Long cveSelector){
		CrtSelector resultado = new CrtSelector();

		String sql = "from mx.gob.imss.ctirss.correccion.model.CrtSelector s " +
				"where s.cveSelector= :cveSelector";		
		Query query = this.getSession().createQuery(sql);
		query.setParameter("cveSelector",cveSelector);		
		if(query.list().isEmpty())
			resultado = null;
		else
			resultado = (CrtSelector) query.uniqueResult();
		
		return resultado;
		
	}
	
	public void modificaSelector(CrtSelector selector) {
		try{
		this.getSession().saveOrUpdate(selector);
		this.getSession().flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public T consultaPorClaveCS(T filtro) {
		
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		criteria.add(Restrictions.eq("idCriterioseleccion", ((CgcCatcriterioseleccion)filtro).getIdCriterioseleccion()));
		filtro = (T) criteria.list().get(0);
		return filtro;
		
	}

	@Override
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del, Long SubDel,
			String cad, String fecha) {
		
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
	
	public void replicaPromocion(T model){
		
		CrtPromocion promocion = (CrtPromocion)model;
		String domicilioConcat = promocion.getDomicilio();
		model = this.consultaPorClave(model);
		CgtPromocion promocionReplica = new CgtPromocion();
		CrtDeteccion deteccion = new CrtDeteccion();
		CrtRegulapagos regularizacion = new CrtRegulapagos();
		
		String tipoPromocion = promocion.getNuFoliopromocion().substring(5, promocion.getNuFoliopromocion().length()-10);
								
		if( TipoCorreccion.SATIC_A.getPrefijoFolio().equalsIgnoreCase(tipoPromocion) || TipoCorreccion.EXHORTO_DE_CONSTRUCCION.getPrefijoFolio().equalsIgnoreCase(tipoPromocion)){			
			deteccion.setCveDeteccion(promocion.getCveDeteccion().longValue());
			deteccion = (CrtDeteccion)deteccionService.consultaPorClave((T)deteccion);
			// Cambia el id por el codigo de sac_tipoObra  EDJ  14/05/2012
			String codigo = this.buscaCodigoTipoObra(deteccion);
			if(!codigo.equals("")){
				deteccion.setCveFkPatron(Long.valueOf(codigo));
			}
			regularizacion.setCvePromocion(promocion.getCvePromocion());
			regularizacion = (CrtRegulapagos)regularizacionService.consultaPorClavePromocion((T)regularizacion);
			if(deteccion!=null){
				//SE OBTIENEN LOS CAMPOS NECESARIO DE LA PROMOCION
				promocionReplica.setFolio(promocion.getNuFoliopromocion()!=null?promocion.getNuFoliopromocion():null);
				promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
				promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig()!=null?promocion.getSdelegOrig():null);				
				promocionReplica.setCgtCatCriterioSeleccion(new CgtCatCriterioSeleccion());
				promocionReplica.getCgtCatCriterioSeleccion().setIdCriterioseleccion(promocion.getIdCriterioSeleccion()!=null?promocion.getIdCriterioSeleccion():null);
				List list = this.catalogoService.consultaLibrePorClave(0L, "from CgcCatcriterioseleccion c where c.idCriterioseleccion = "+promocionReplica.getCgtCatCriterioSeleccion().getIdCriterioseleccion());
				if(list!=null && list.size()>0){
					CgcCatcriterioseleccion cgcCatCriterio = (CgcCatcriterioseleccion)list.get(0);
					promocionReplica.setCgcCatTipo(new CgcCatTipo());
					promocionReplica.getCgcCatTipo().setIdTipo(cgcCatCriterio.getIdTipo());
					promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
					promocionReplica.getCgcCatOrigen().setIdOrigen(cgcCatCriterio.getIdOrigen());
				}
				if(promocion.getCveFkPatron()!=null){
					SatPatron patron = patronesService.getById(promocion.getCveFkPatron());				
					promocionReplica.setCvePatron(patron.getRegistroPatronal());
					promocionReplica.setNombre(patron.getRazonSocial());
				}
				promocionReplica.setDv(new BigDecimal(1));
				promocionReplica.setAfil15(promocion.getCveNroregobraSatic()!=null?promocion.getCveNroregobraSatic().toString():"");
				promocionReplica.setOpe(promocion.getFecFechaemisionpro()!=null?promocion.getFecFechaemisionpro():null);
				promocionReplica.setRnooficioope(promocion.getNuOficiopro());
				promocionReplica.setNop(promocion.getFecFechanotif()!=null?promocion.getFecFechanotif():null);
				promocionReplica.setAop(promocion.getFecFechaAtencion()!=null?promocion.getFecFechaAtencion():null);
				promocionReplica.setPai(promocion.getFecFechapai()!=null?promocion.getFecFechapai():null);
				promocionReplica.setPr(promocion.getFecFecharegulariza()!=null?promocion.getFecFecharegulariza():null);
				promocionReplica.setObservaciones(promocion.getTxObservaciones()!=null?promocion.getTxObservaciones():null);
				promocionReplica.setC(promocion.getFecFechaCancela()!=null?promocion.getFecFechaCancela():null);
				promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion()!=null?promocion.getIdMotivoCancelacion():null);
				promocionReplica.setNooficioc(promocion.getNuVolanteCancela()!=null?promocion.getNuVolanteCancela():null);
				promocionReplica.setFecFechareg(promocion.getFecFechareg()!=null?promocion.getFecFechareg():null);
				promocionReplica.setCveUsuario(promocion.getCveUsuario()!=null?promocion.getCveUsuario():null);
				//SE OBTIENEN LOS CAMPOS NECESARIOS DE LA DETECCION
				DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
				domicilio.setDomicilioId(deteccion.getDomicilioId());
				domicilio = (DgDomicilioGeografico)domiciliosService.consultaPorClave((T)domicilio);
				promocionReplica.setUbicacion(domicilio.getNomvial()!=null?domicilio.getNomvial():domicilio.getDgVialidadByCveViaPrin().getNomVia()+" "+domicilio.getNumextnum().toString()+" "+domicilio.getDgAsentamiento().getNomAsen()+" CP "+domicilio.getDgCodigosPostales().getId().getCodigo());
				promocionReplica.setEsttrabajreg(deteccion.getNumTrabajdores()!=null?new BigDecimal(deteccion.getNumTrabajdores()):null);
				if(codigo != null && !codigo.equals("")){
					promocionReplica.setIdCodigoobra(new BigDecimal(codigo));
				}else{
					promocionReplica.setIdCodigoobra(null);
				}
				promocionReplica.setSuperficieestimada(deteccion.getCanSuperficie()!=null?deteccion.getCanSuperficie():null);
				promocionReplica.setCostototalcontratado(deteccion.getImpCostoobra()!=null?deteccion.getImpCostoobra():null);
				promocionReplica.setPorcavanceestimado(deteccion.getPorAvanceobraEst()!=null?deteccion.getPorAvanceobraEst():null);
				//SE OBTIENEN LOS CAMPOS NECESARIOS DE LA REGULARIZACION
				if(regularizacion!=null){
					promocionReplica.setPeriododel(regularizacion.getFecPeridodini()!=null?regularizacion.getFecPeridodini():null);
					promocionReplica.setPeriodoal(regularizacion.getFecPeriodofin()!=null?regularizacion.getFecPeriodofin():null);
					promocionReplica.setNoconvenio(regularizacion.getNumConvenio()!=null?regularizacion.getNumConvenio():null);
					promocionReplica.setNoparcialidades(regularizacion.getNumParcialidades()!=null?new BigDecimal(regularizacion.getNumParcialidades()):null);
					promocionReplica.setPorcregularizado(regularizacion.getPorRegularizado()!=null?regularizacion.getPorRegularizado():null);
					promocionReplica.setPorcavance(regularizacion.getPorAvance()!=null?regularizacion.getPorAvance():null);
					promocionReplica.setTrabrevisados(regularizacion.getNumTrabrevisados()!=null?regularizacion.getNumTrabrevisados():null);
					promocionReplica.setTrabomisos(regularizacion.getNumTrabomisos()!=null?regularizacion.getNumTrabomisos():null);
					promocionReplica.setTrabsubddeclarados(regularizacion.getNumTrabsubdclara()!=null?regularizacion.getNumTrabsubdclara():null);
					this.agrega((T)promocionReplica);
//					regularizacionDetPago.setCrtRegulapagos(new CrtRegulapagos());
//					regularizacionDetPago.getCrtRegulapagos().setCveRegulapagos(regularizacion.getCveRegulapagos());
//					regularizacionDet = (List<CrtRegulapagosdet>)regularizacionDetService.consultaPagosDetPorCvePago((T)regularizacionDetPago);					
//					if(regularizacionDet.size()>0){
//						Iterator iterator = regularizacionDet.iterator();
//						while(iterator.hasNext()){
//							CgtAnexoPago anexoPago = new CgtAnexoPago();
//							CrtRegulapagosdet regulaDet = (CrtRegulapagosdet)iterator.next();
//							anexoPago.setFolio(promocionReplica.getFolio());
//							if(promocionReplica.getFolio().substring(5, promocionReplica.getFolio().length()-10).equalsIgnoreCase("SATICA")){
//								anexoPago.setIdProceso(4);
//							}else{
//								anexoPago.setIdProceso(3);
//							}
//							anexoPago.setCvePatron(promocionReplica.getCvePatron());
//							anexoPago.setFoliosua(regulaDet.getNumFoliosua()!=null?regulaDet.getNumFoliosua().toString():null);
//							anexoPago.setFolioordeningreso(regulaDet.getNumOrdeningreso()!=null?regulaDet.getNumOrdeningreso():null);
//							anexoPago.setNocredito(regulaDet.getNumCredito()!=null?regulaDet.getNumCredito():null);
//							anexoPago.setFechapago(regulaDet.getFecFechapago()!=null?regulaDet.getFecFechapago():null);
//							anexoPago.setCopperiodo(regulaDet.getNumPeriodoCop()!=null?new BigDecimal(regulaDet.getNumPeriodoCop()):null);
//							anexoPago.setCopsp(regulaDet.getImpCopsp()!=null?regulaDet.getImpCopsp():null);
//							anexoPago.setCopact(regulaDet.getImpCopact()!=null?regulaDet.getImpCopact():null);
//							anexoPago.setCoprec(regulaDet.getImpCoprec()!=null?regulaDet.getImpCoprec():null);
//							anexoPago.setCopmultas(regulaDet.getImpMultasCop()!=null?regulaDet.getImpMultasCop():null);
//							anexoPago.setRcvsp(regulaDet.getImpRcvsp()!=null?regulaDet.getImpRcvsp():null);
//							anexoPago.setRcvact(regulaDet.getImpRcvact()!=null?regulaDet.getImpRcvact():null);
//							anexoPago.setRcvrec(regulaDet.getImpRcvrec()!=null?regulaDet.getImpRcvrec():null);
//							anexoPago.setRcvmultas(regulaDet.getImpMultasRcv()!=null?regulaDet.getImpMultasRcv():null);
//							anexoPago.setCveUsuario(promocionReplica.getCveUsuario());
//							this.agrega((T)anexoPago);
//						}
//					}
				}
			}
		}else if( TipoCorreccion.SATIC_B.getPrefijoFolio().equalsIgnoreCase(tipoPromocion)){
			deteccion.setNumRegObra(promocion.getCveNroregobraSatic().toString());
			deteccion = this.obtieneObraporNumeroRegistro(deteccion);
			if(deteccion!=null){
				promocionReplica.setFolio(promocion.getNuFoliopromocion()!=null?promocion.getNuFoliopromocion():null);
				promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
				promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig()!=null?promocion.getSdelegOrig():null);
				promocionReplica.setCgtCatCriterioSeleccion(new CgtCatCriterioSeleccion());
				promocionReplica.getCgtCatCriterioSeleccion().setIdCriterioseleccion(promocion.getIdCriterioSeleccion()!=null?promocion.getIdCriterioSeleccion():null);
				List list = this.catalogoService.consultaLibrePorClave(0L, "from CgcCatcriterioseleccion c where c.idCriterioseleccion = "+promocionReplica.getCgtCatCriterioSeleccion().getIdCriterioseleccion());				
				if(list!=null && list.size()>0){
					CgcCatcriterioseleccion cgcCatCriterio = (CgcCatcriterioseleccion)list.get(0);
					promocionReplica.setCgcCatTipo(new CgcCatTipo());
					promocionReplica.getCgcCatTipo().setIdTipo(TipoCorreccion.SATIC_B.getEquivalenciaCaratula());
					promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
					promocionReplica.getCgcCatOrigen().setIdOrigen(cgcCatCriterio.getIdOrigen());
				}
				if(promocion.getCveFkPatron()!=null){
//					CrtDeteccion det = new CrtDeteccion();
//					det.setNumRegObra(promocion.getCveFkPatron().toString());
//					det = this.obtieneObraporNumeroRegistro(det);	
					SatPatron patron = patronesService.getById(promocion.getCveFkPatron());
					promocionReplica.setCvePatron(patron.getRegistroPatronal());
					promocionReplica.setNombre(patron.getRazonSocial());
					//DATOS NECESARIO DE LA VISTA DE RELACION DE TRABAJADORES
					promocionReplica.setUbicacion(deteccion.getDomCalle()+" "+deteccion.getRefColonia()+" "+deteccion.getNumNroext()+" CP "+deteccion.getNumCodigopostal());
					promocionReplica.setEsttrabajreg(null);
					promocionReplica.setIdCodigoobra(null);
					promocionReplica.setSuperficieestimada(null);
					promocionReplica.setCostototalcontratado(null);
					promocionReplica.setPorcavanceestimado(null);
				}
				promocionReplica.setDv(new BigDecimal(1));
				promocionReplica.setAfil15(promocion.getCveNroregobraSatic()!=null?promocion.getCveNroregobraSatic().toString():"");
				promocionReplica.setOpe(promocion.getFecFechaemisionpro()!=null?promocion.getFecFechaemisionpro():null);
				promocionReplica.setRnooficioope(promocion.getNuOficiopro());
				promocionReplica.setNop(promocion.getFecFechanotif()!=null?promocion.getFecFechanotif():null);
				promocionReplica.setAop(promocion.getFecFechaAtencion()!=null?promocion.getFecFechaAtencion():null);
				promocionReplica.setPai(promocion.getFecFechapai()!=null?promocion.getFecFechapai():null);
				promocionReplica.setPr(promocion.getFecFecharegulariza()!=null?promocion.getFecFecharegulariza():null);
				promocionReplica.setObservaciones(promocion.getTxObservaciones()!=null?promocion.getTxObservaciones():null);
				promocionReplica.setC(promocion.getFecFechaCancela()!=null?promocion.getFecFechaCancela():null);
				promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion()!=null?promocion.getIdMotivoCancelacion():null);
				promocionReplica.setNooficioc(promocion.getNuVolanteCancela()!=null?promocion.getNuVolanteCancela():null);
				promocionReplica.setFecFechareg(promocion.getFecFechareg()!=null?promocion.getFecFechareg():null);
				promocionReplica.setCveUsuario(promocion.getCveUsuario()!=null?promocion.getCveUsuario():null);
				this.agrega((T)promocionReplica);
			}
		}else if(TipoCorreccion.SALARIO_BASE_DE_COTIZACION.getPrefijoFolio().equalsIgnoreCase(tipoPromocion)){
				
				promocionReplica.setFolio(promocion.getNuFoliopromocion()!=null?promocion.getNuFoliopromocion():null);
				promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
				promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig()!=null?promocion.getSdelegOrig():null);
				promocionReplica.setUbicacion(domicilioConcat);
				promocionReplica.setCgtCatCriterioSeleccion(new CgtCatCriterioSeleccion());
				promocionReplica.getCgtCatCriterioSeleccion().setIdCriterioseleccion(102L);
				List list = this.catalogoService.consultaLibrePorClave(0L, "from CgcCatcriterioseleccion c where c.idCriterioseleccion = "+promocionReplica.getCgtCatCriterioSeleccion().getIdCriterioseleccion());
				if(list!=null && list.size()>0){
					CgcCatcriterioseleccion cgcCatCriterio = (CgcCatcriterioseleccion)list.get(0);
					promocionReplica.setCgcCatTipo(new CgcCatTipo());
					promocionReplica.getCgcCatTipo().setIdTipo(6L);
					promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
					promocionReplica.getCgcCatOrigen().setIdOrigen(2L);
				}
				if(promocion.getCveFkPatron()!=null){
					SatPatron patron = patronesService.getById(promocion.getCveFkPatron());				
					promocionReplica.setCvePatron(patron.getRegistroPatronal());
					promocionReplica.setNombre(patron.getRazonSocial());
				}
				promocionReplica.setDv(new BigDecimal(7));
				promocionReplica.setAfil15(promocion.getCveNroregobraSatic()!=null?promocion.getCveNroregobraSatic().toString():"");
				promocionReplica.setOpe(promocion.getFecFechaemisionpro()!=null?promocion.getFecFechaemisionpro():null);
				promocionReplica.setRnooficioope(promocion.getNuOficiopro());
				promocionReplica.setNop(promocion.getFecFechanotif()!=null?promocion.getFecFechanotif():null);
				promocionReplica.setAop(promocion.getFecFechaAtencion()!=null?promocion.getFecFechaAtencion():null);
				promocionReplica.setPai(promocion.getFecFechapai()!=null?promocion.getFecFechapai():null);
				promocionReplica.setPr(promocion.getFecFecharegulariza()!=null?promocion.getFecFecharegulariza():null);
				promocionReplica.setObservaciones(promocion.getTxObservaciones()!=null?promocion.getTxObservaciones():null);
				promocionReplica.setC(promocion.getFecFechaCancela()!=null?promocion.getFecFechaCancela():null);
				promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion()!=null?promocion.getIdMotivoCancelacion():null);
				promocionReplica.setNooficioc(promocion.getNuVolanteCancela()!=null?promocion.getNuVolanteCancela():null);
				promocionReplica.setFecFechareg(promocion.getFecFechareg()!=null?promocion.getFecFechareg():null);
				promocionReplica.setCveUsuario(promocion.getCveUsuario()!=null?promocion.getCveUsuario():null);
				this.agrega((T)promocionReplica);
		}else if(TipoCorreccion.EXHORTO_DE_LO_ORDINARIO.getPrefijoFolio().equalsIgnoreCase(tipoPromocion)){
			//SE OBTIENEN LOS CAMPOS NECESARIO DE LA PROMOCION
			List listaPromocion = (List)daoDelta.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '" + promocion.getNuFoliopromocion() + "'") ;
			 if(listaPromocion!=null && listaPromocion.size() > 0){
				 promocionReplica = (CgtPromocion)listaPromocion.get(0);
			 }
			promocionReplica.setFolio(promocion.getNuFoliopromocion()!=null?promocion.getNuFoliopromocion():null);
			promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
			promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig()!=null?promocion.getSdelegOrig():null);				
			promocionReplica.setCgtCatCriterioSeleccion(new CgtCatCriterioSeleccion());
			promocionReplica.getCgtCatCriterioSeleccion().setIdCriterioseleccion(promocion.getIdCriterioSeleccion()!=null?promocion.getIdCriterioSeleccion():null);
			
			List list = this.catalogoService.consultaLibrePorClave(0L, "from CgcCatcriterioseleccion c where c.idCriterioseleccion = "+promocionReplica.getCgtCatCriterioSeleccion().getIdCriterioseleccion());
			if(list!=null && list.size()>0){
				CgcCatcriterioseleccion cgcCatCriterio = (CgcCatcriterioseleccion)list.get(0);
				promocionReplica.setCgcCatTipo(new CgcCatTipo());
				promocionReplica.getCgcCatTipo().setIdTipo(cgcCatCriterio.getIdTipo());
				promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
				promocionReplica.getCgcCatOrigen().setIdOrigen(cgcCatCriterio.getIdOrigen());
			}
			if(promocion.getCveFkPatron()!=null){
				SatPatron patron = patronesService.getById(promocion.getCveFkPatron());				
				promocionReplica.setCvePatron(patron.getRegistroPatronal());
				promocionReplica.setNombre(patron.getRazonSocial());
			}
			
			
			promocionReplica.setDv(BigDecimal.valueOf(ConstantesBusiness.EXHORTO_ORDINARIO));
			promocionReplica.setOpe(promocion.getFecFechaemisionpro()!=null?promocion.getFecFechaemisionpro():null);
			
			if(CatEstatus.FOLIO_CANCELADO.getId().equals(promocion.getCveEstatus())){
				promocionReplica.setAop(null);	
			}else{
				promocionReplica.setAop(promocion.getFecFechaAtencion()!=null?promocion.getFecFechaAtencion():null);
			}
			promocionReplica.setNop(promocion.getFecFechanotif()!=null?promocion.getFecFechanotif():null);
			
			promocionReplica.setObservaciones(promocion.getTxObservaciones()!=null?promocion.getTxObservaciones():null);
			
			promocionReplica.setC(promocion.getFecFechaCancela()!=null?promocion.getFecFechaCancela():null);
			promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion()!=null?promocion.getIdMotivoCancelacion():null);
			promocionReplica.setNooficioc(promocion.getNuVolanteCancela()!=null?promocion.getNuVolanteCancela():null);
			
			promocionReplica.setFecFechareg(new Date());
			promocionReplica.setCveUsuario(promocion.getCveUsuario()!=null?promocion.getCveUsuario():null);
			promocionReplica.setIdStatus(BigDecimal.valueOf(promocion.getCveEstatus()));
			
			
			this.agrega((T)promocionReplica);
		}
		
	}
	
	public void replicaPago(CrtRegulapagosdet regulaDet){
		CgtAnexoPago anexoPago = new CgtAnexoPago();
		CrtRegulapagos regulaPago = new CrtRegulapagos();
		regulaPago.setCveRegulapagos(regulaDet.getCrtRegulapagos().getCveRegulapagos());
		regulaPago = (CrtRegulapagos)this.regularizacionService.consultaPorClave((T)regulaPago);
		CrtPromocion promocionReplica = new CrtPromocion();
		promocionReplica.setCvePromocion(regulaPago.getCvePromocion());
		promocionReplica = (CrtPromocion)this.consultaPorClave((T)promocionReplica);
		if(promocionReplica!=null){
			if(promocionReplica.getNuFoliopromocion()!=null){
				if(promocionReplica.getNuFoliopromocion().substring(5, promocionReplica.getNuFoliopromocion().length()-10).equalsIgnoreCase("SATICA") ||
						promocionReplica.getNuFoliopromocion().substring(5, promocionReplica.getNuFoliopromocion().length()-10).equalsIgnoreCase("EX")){
					anexoPago.setFolio(promocionReplica.getNuFoliopromocion());
					if(promocionReplica.getNuFoliopromocion().substring(5, promocionReplica.getNuFoliopromocion().length()-10).equalsIgnoreCase("SATICA")){
						anexoPago.setIdProceso(4);
					}else{
						anexoPago.setIdProceso(3);
					}
					if(regulaDet.getIdPagoCaratula()>0){
						anexoPago.setIdPago(regulaDet.getIdPagoCaratula());
					}
					if(promocionReplica.getCveFkPatron()!=null){
						SatPatron patron = patronesService.getById(promocionReplica.getCveFkPatron());				
						anexoPago.setCvePatron(patron.getRegistroPatronal());			
					}		
					anexoPago.setFoliosua(regulaDet.getNumFoliosua()!=null?regulaDet.getNumFoliosua().toString():null);
					anexoPago.setFolioordeningreso(regulaDet.getNumOrdeningreso()!=null?regulaDet.getNumOrdeningreso():null);
					anexoPago.setNocredito(regulaDet.getNumCredito()!=null?regulaDet.getNumCredito():null);
					anexoPago.setFechapago(regulaDet.getFecFechapago()!=null?regulaDet.getFecFechapago():null);
					anexoPago.setCopperiodo(regulaDet.getNumPeriodoCop()!=null?new BigDecimal(regulaDet.getNumPeriodoCop()):null);
					anexoPago.setCopsp(regulaDet.getImpCopsp()!=null?regulaDet.getImpCopsp():null);
					anexoPago.setCopact(regulaDet.getImpCopact()!=null?regulaDet.getImpCopact():null);
					anexoPago.setCoprec(regulaDet.getImpCoprec()!=null?regulaDet.getImpCoprec():null);
					anexoPago.setCopmultas(regulaDet.getImpMultasCop()!=null?regulaDet.getImpMultasCop():null);
					anexoPago.setRcvsp(regulaDet.getImpRcvsp()!=null?regulaDet.getImpRcvsp():null);
					anexoPago.setRcvact(regulaDet.getImpRcvact()!=null?regulaDet.getImpRcvact():null);
					anexoPago.setRcvrec(regulaDet.getImpRcvrec()!=null?regulaDet.getImpRcvrec():null);
					anexoPago.setRcvmultas(regulaDet.getImpMultasRcv()!=null?regulaDet.getImpMultasRcv():null);
					anexoPago.setFecFechareg(Functions.stringToDate(Functions.dateToString(new Date())));
					anexoPago.setCveUsuario(promocionReplica.getCveUsuario());
					this.agrega((T)anexoPago);
				}
			}
		}
	}
	
	public void replicaEliminaPago(CrtRegulapagosdet regulaDet){
		regulaDet = (CrtRegulapagosdet)this.regularizacionDetService.consultaPorClavePago((T)regulaDet);
		CgtAnexoPago anexoPago = new CgtAnexoPago();
		CrtRegulapagos regulaPago = new CrtRegulapagos();
		regulaPago.setCveRegulapagos(regulaDet.getCrtRegulapagos().getCveRegulapagos());
		regulaPago = (CrtRegulapagos)this.regularizacionService.consultaPorClave((T)regulaPago);
		CrtPromocion promocionReplica = new CrtPromocion();
		promocionReplica.setCvePromocion(regulaPago.getCvePromocion());
		promocionReplica = (CrtPromocion)this.consultaPorClave((T)promocionReplica);
		if(promocionReplica.getNuFoliopromocion().substring(5, 11).equalsIgnoreCase("SATICA") ||
				(promocionReplica.getNuFoliopromocion().length()==17 && promocionReplica.getNuFoliopromocion().substring(5, 7).equalsIgnoreCase("EX"))){
			anexoPago.setFolio(promocionReplica.getNuFoliopromocion());
			if(promocionReplica.getCveFkPatron()!=null){
				SatPatron patron = patronesService.getById(promocionReplica.getCveFkPatron());				
				anexoPago.setCvePatron(patron.getRegistroPatronal());			
			}
			anexoPago.setFoliosua(regulaDet.getNumFoliosua()!=null?regulaDet.getNumFoliosua().toString():null);
			anexoPago.setFolioordeningreso(regulaDet.getNumOrdeningreso()!=null?regulaDet.getNumOrdeningreso():null);
			anexoPago.setNocredito(regulaDet.getNumCredito()!=null?regulaDet.getNumCredito():null);
			anexoPago.setFechapago(regulaDet.getFecFechapago()!=null?regulaDet.getFecFechapago():null);
			anexoPago.setCopperiodo(regulaDet.getNumPeriodoCop()!=null?new BigDecimal(regulaDet.getNumPeriodoCop()):null);
			anexoPago.setCopsp(regulaDet.getImpCopsp()!=null?regulaDet.getImpCopsp():null);
			anexoPago.setCopact(regulaDet.getImpCopact()!=null?regulaDet.getImpCopact():null);
			anexoPago.setCoprec(regulaDet.getImpCoprec()!=null?regulaDet.getImpCoprec():null);
			anexoPago.setCopmultas(regulaDet.getImpMultasCop()!=null?regulaDet.getImpMultasCop():null);
			anexoPago.setRcvsp(regulaDet.getImpRcvsp()!=null?regulaDet.getImpRcvsp():null);
			anexoPago.setRcvact(regulaDet.getImpRcvact()!=null?regulaDet.getImpRcvact():null);
			anexoPago.setRcvrec(regulaDet.getImpRcvrec()!=null?regulaDet.getImpRcvrec():null);
			anexoPago.setRcvmultas(regulaDet.getImpMultasRcv()!=null?regulaDet.getImpMultasRcv():null);
			anexoPago.setFecFechareg(Functions.stringToDate(Functions.dateToString(new Date())));
			anexoPago.setCveUsuario(promocionReplica.getCveUsuario());
			this.elimina((T)anexoPago);
		}
	}

	@Override
	public T consultaCorrPromInvita(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		CrtCorrPromInvita corrProminvita; 
		corrProminvita = (CrtCorrPromInvita) model;
		BigDecimal cveInvitacion = null;
		Long cvePromocion = null;
		
		if (corrProminvita.getCrtInvitacion() != null && corrProminvita.getCrtInvitacion().getCveInvitacion() != null) {
			cveInvitacion = corrProminvita.getCrtInvitacion().getCveInvitacion();
			criteria.createCriteria("crtInvitacion").add(Restrictions.eq("cveInvitacion", cveInvitacion));
		} else if (corrProminvita.getCrtPromocion() != null  && corrProminvita.getCrtPromocion().getCvePromocion() != null){
			cvePromocion=corrProminvita.getCrtPromocion().getCvePromocion();
			criteria.createCriteria("crtPromocion").add(Restrictions.eq("cvePromocion", cvePromocion));
		}
		
		if( criteria.list() != null &&  criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		} else {
			model = null;
		}
		return model;
	}
	
	@Override
	public T consultaPromoInvita(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.createCriteria("crtPromocion").add(Restrictions.eq("cvePromocion", ((CrtCorrPromInvita)model).getCrtPromocion().getCvePromocion()));
		if( criteria.list() != null &&  criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
	}

	@Override
	public T consultaInvitacionPromocion(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cvePromocion", ((CrtInvitacion)model).getCvePromocion()));
		if( criteria.list() != null &&  criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
	}
	
	@Override
	public T guardar(T model) {
		CrtPromocion promocion = (CrtPromocion)model;
		try{
			
			this.getSession().saveOrUpdate(promocion);
			this.getSession().flush();
			
			
		}catch(RuntimeException re){
			
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
			
		}catch( Exception e){
			e.printStackTrace();
		}
		
		model = (T) promocion;
		return model;

	}
	
	private String buscaCodigoTipoObra(CrtDeteccion det){
		
		String codigoTipoObra = "";
		if(det.getCvePkTipObra() != null){
			Criteria criteria = this.getSession().createCriteria(SacTipoObra.class);
			criteria.add(Restrictions.eq("idTipoObra", ((CrtDeteccion)det).getCvePkTipObra().longValue()));
			if( criteria.uniqueResult() != null ){
				SacTipoObra tipoObra = (SacTipoObra) criteria.uniqueResult();
				codigoTipoObra = tipoObra.getCveCodigo();
			}
		}
		
		return codigoTipoObra;	
	}

	@Override
	public DatosSalidaPaginador<T> obtenerCriterioSelector(	DatosEntradaPaginador<T> params, Long delegacion, Long subDelegacion, Long criterio) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		List<T> result = new ArrayList<T>();
		StringBuilder builder = new StringBuilder();
		builder.append("select s from CrtSelector s,SatPatron patron ");
		builder.append("where s.cgcCatcriterioseleccion.idCriterioseleccion = :lnCriterioSeleccion and s.sacDelegacion.cvePk = :inDelegacion and ");		
		builder.append("s.sacSubdelegacion.cvePk = :inSubDelegacion and s.idPromocionado = 0  ");
		builder.append(" and patron.registroPatronal=s.nuRegistroPatronal and  patron.cveSubdelegacion=:cveSubdelegacionPatron ");
		System.out.println(builder.toString());	
		Query query = this.getSession().createQuery(builder.toString());
		query.setParameter("inDelegacion", delegacion);
		query.setParameter("inSubDelegacion", subDelegacion);
		query.setParameter("lnCriterioSeleccion", criterio);
		query.setParameter("cveSubdelegacionPatron", subDelegacion);
		logger.debug("delegacion="+delegacion);
		logger.debug("inSubDelegacion="+subDelegacion);
		logger.debug("criterio="+criterio);
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		if(query.list().size()>0)
			result = query.list();
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(result!=null)
			iTotalRecords = result.size();
		
		/*Se debe de obtener el numero total de registros en la base de datos*/
        query.setFirstResult(0);
        query.setMaxResults(-1);
        logger.debug("contando...");
        final List temp = query.list();
        logger.debug("temp.size() :: " + temp.size());
        response.setiTotalRecords(temp.size());
        response.setiTotalDisplayRecords(temp.size());
		
		response.setAaData(result);
		
		return response;
	}


	/**
	 * Metodo que busca por folio, subdelegacion del usuario y que cveAuditorAsignado != 0
	 * @author Enrique Duran JImenez
	 * @since 05/06/2012
	 */
	@Override
	public T consultaPorFolio(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("nuFoliopromocion", ((CrtPromocion)model).getNuFoliopromocion()));
		criteria.add(Restrictions.eq("sdelegOrig", ((CrtPromocion)model).getSdelegOrig()));
		criteria.add(Restrictions.isNotNull("cveAuditorAsignado"));
		if(criteria.list() != null && criteria.list().size() > 0){
			model = (T) criteria.list().get(0);
		}else{
			model = null;
		}
		return model;
		}

	/**
	 * Metodo que busca por numero de obra, la obra
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 * @return SatObra
	 */
	@Override
	public T obtieneObraporNumObra(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveNroregobra", ((SatObra)model).getCveNroregobra()));
		criteria.addOrder(Order.desc("indHistorico"));
		//criteria.add(Restrictions.eq("indHistorico", true));		
		model = (T) criteria.list().get(0);
		return model;
	}

	/**
	 * Metodo que busca incidencias por cveFkObra
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 * @return List<satIncidencias>
	 */
	@Override
	public List<T> obtieneIncidenciasporCveObra(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveFkObra", ((SatIncidencia)model).getCveFkObra()));
		criteria.addOrder(Order.asc("fecFecharegistroFc"));
		return criteria.list();
		}

	@Override
	public List<T> obtieneRelTrabajadoresporCveObra(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveFkObra", ((SatReltrabajadores)model).getCveFkObra()));
		criteria.addOrder(Order.asc("fecFecharegistroFc"));
		return criteria.list();
	}
	
	/**
	 * Metodo que busca satPatron con el registro patronal
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 */
	private Long getIdPatByRegPat(String regPat) {

		String sql = "select new java.lang.Long(c.cvePK) from mx.gob.imss.ctirss.correccion.model.SatPatron c where substr(c.registroPatronal,1,10) = '"
				+ regPat + "'";
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (Long) query.list().get(0): new Long(0));

	}

	@Override
	public T consultaSelectorPorClave(T model) {
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("cveSelector", ((CrtSelector)model).getCveSelector()));
		return (T) criteria.uniqueResult();
	}

	/**
	 * Metodo que busca si existe registrado el numero de folio
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 */
	@Override
	public List<T> consultaNumeroFolio(String model) {
		Criteria criteria = this.getSession().createCriteria(CrtPromocion.class);
		criteria.add(Restrictions.eq("nuOficiopro", model));
		List lstResult = criteria.list();
		return lstResult;
	}

}
