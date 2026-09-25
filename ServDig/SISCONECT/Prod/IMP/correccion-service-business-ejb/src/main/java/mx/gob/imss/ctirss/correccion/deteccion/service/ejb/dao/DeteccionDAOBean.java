package mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.service.ejb.CatalogoServiceRemote;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless
public class DeteccionDAOBean<T extends AbstractModel> extends AbstractRespository implements DeteccionDAOLocal<T> {
	
	@EJB CatalogoServiceRemote<T> catalogoService;
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(DeteccionDAOBean.class);
	
	public T agrega(T model) throws PersistenceException{
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
	
	public void elimina(T model) {
		model = (this.consultaPorClave(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		
		CrtDeteccion det = (CrtDeteccion)filtro;
		
		List<T> resultados = new ArrayList<T>();
		
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"+
				"b.nuFoliodeteccion, b.fecFechadeteccionFc,"+
				"b.nuReportectrlobra, b.nomRazonsocial, b.txCurppatron,"+
				"b.txRfcpatron,b.sdelegOrig, b.tipClaseobra,"+
				"b.cvePkTipObra,b.cvePkFaseConst, b.desDependenciapub,"+			
				"b.desDepcontratante, b.canSuperficie,"+
				"b.impCostoobra, b.fecFechainicioEst,"+
				"b.fecFechaterminoEst, b.porAvanceobraEst,"+
				"b.txTelefono, b.txEmail, b.idPromovido,"+
				"b.fecFechareg, b.cveUsuario, b.domicilioId, b.cveFkPatron, b.idMotivocancelacion) " +
				"from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b where " +
				"b.domicilioId in (select d.domicilioId from mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico d)";
		
		if(det.getNomRazonsocial()!=null && !det.getNomRazonsocial().equalsIgnoreCase("")){
			sql += " and b.nomRazonsocial = '"+det.getNomRazonsocial()+"'";
		}if(det.getNomRazonsocial()!=null && !det.getTxRfcpatron().equalsIgnoreCase("")){
			sql += " and b.txRfcpatron = '"+det.getTxRfcpatron()+"'";
		}if(det.getCveFkPatron()!=null && det.getCveFkPatron()>0){
			sql += " and b.cveFkPatron = "+det.getCveFkPatron();
		}if(det.getFecFechainicioEst()!=null){
			sql += " and to_char(b.fecFechainicioEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechainicioEst())+"'";
		}if(det.getFecFechaterminoEst()!=null){
			sql += " to_char(b.fecFechaterminoEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechaterminoEst())+"'";
		}if(det.getCvePkTipObra()!=null){
			sql += " and b.cvePkTipObra = "+det.getCvePkTipObra();
		}if(det.getCvePkFaseConst()!=null){
			sql += " and b.cvePkFaseConst = "+det.getCvePkFaseConst();
		}
		
		logger.debug(sql);         
		
		Query query = this.getSession().createQuery(sql);
		
		logger.debug("resultados :"+query.list().size());
		
		if(query.list().size()>0)
			resultados = query.list();
		
		return resultados;
	}
	
	public T modifica(T model) {
		this.getSession().update(model);
		this.getSession().flush();
		return model;
	}
	
	public T consultaPorClave(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
											 .add(Restrictions.eq("cveDeteccion", ((CrtDeteccion)filtro).getCveDeteccion()));
		if(criteria.list() != null && criteria.list().size() > 0){
			filtro = (T) criteria.list().get(0);
		}else{
			filtro = null;
		}
		return filtro;
	}
	
	public CrtNroFolio obtieneFolios(Long del,Long subDel, String fecha, Integer tipo){				
		System.out.println("CodigoDelega "+del+" CodigoSubdel "+subDel+" fecha "+fecha+" tipo "+tipo);
		String queryDelegacion="select delega.cvePk  from SacDelegacion delega where delega.cveCodigo="+del;
		String querySubdelegacion="select sub.cvePk  from SacSubdelegacion sub where sub.cveCodigo="+subDel+" and sub.sacDelegacion.cvePk="+del;
		System.out.println("Query "+queryDelegacion);
		List<?> delegaciones=this.catalogoService.consultaLibrePorClave(0L, queryDelegacion);
		List<?> subdelegaciones=this.catalogoService.consultaLibrePorClave(0L, querySubdelegacion);
		System.out.println("CodigoDelegacion  "+del+" CVE_DELEGA "+delegaciones.get(0).toString());
		System.out.println("CodigoSubdelegacion"+subDel+" CVE_SUBDELEGA "+subdelegaciones.get(0).toString());
		System.out.println("TipoDocumento "+tipo);
		
		
//		
//		String sql = "from CrtNroFolio nf where nf.cveDelegacion = "+del.toString()
//				+" and nf.cveSubdelegacion = "+subDel+" and nf.numAnio = "+fecha+" and nf.crcTipoCorr = "+tipo;	
		
		String sql = "from CrtNroFolio nf where nf.cveDelegacion = "+delegaciones.get(0).toString()
				+" and nf.cveSubdelegacion = "+subdelegaciones.get(0)+" and nf.numAnio = "+fecha+" and nf.crcTipoCorr = "+tipo;	
		System.out.println(sql);
		List<?> lista = (ArrayList)this.catalogoService.consultaLibrePorClave(0L, sql);
		CrtNroFolio folio = new CrtNroFolio();
		if(lista.size()>0){
			folio = (CrtNroFolio) lista.get(0);
			System.out.println("Resultados: "+lista.size()+" valor "+folio.getCvePkFolio());			
		}else{			
			folio.setNumNumero(new BigDecimal(0));
			folio.setCveDelegacion(new BigDecimal(delegaciones.get(0).toString()));
			folio.setCveSubdelegacion(new BigDecimal(subdelegaciones.get(0).toString()));
			folio.setNumAnio(new BigDecimal(fecha));
			CrcTipoCorr tipoCor = new CrcTipoCorr();
			tipoCor.setCveTipocorr(tipo);
			folio.setCrcTipoCorr(tipoCor);			
		}			
		return folio;
	}
	
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultFinal = new ArrayList<CrtDeteccion>();
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		
		if(det.getNuFoliodeteccion() != null){
			CrtDeteccion detAux = this.consultaPorFolio(det);
			if(detAux != null){
				if(detAux.getIdMotivocancelacion() != null){
					det.setEstatus("3"); // canceladas
				}else if(detAux.getIdMotivocancelacion() == null && detAux.getIdPromovido() == null){
					det.setEstatus("4"); // sin domicilio
				}else if(detAux.getIdMotivocancelacion() == null && detAux.getIdPromovido().intValue() == 1){
					det.setEstatus("1"); // SATIC A
				}else if(detAux.getIdMotivocancelacion() == null && detAux.getIdPromovido().intValue() == 0){
					det.setEstatus("2"); // CONSTRUCCION
				}
			}
		}
		logger.debug(params.getModelo().getClass());
				
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(";
			if(det.getEstatus()!=null && !det.getEstatus().equalsIgnoreCase("4") && !det.getEstatus().equalsIgnoreCase("3")){
//				sql += "dg.nomvial,dg.dgAsentamiento.nomAsen,"+
//				"dg.numintalf,dg.numextnum,dg.numextalf,dg.numintnum,dg.dgCodigosPostales.id.codigo,";
			}
			sql += "b.cveDeteccion, b.cveTipocorr,"+
			"b.nuFoliodeteccion, b.fecFechadeteccionFc,"+
			"b.nuReportectrlobra, b.nomRazonsocial, b.txCurppatron,"+
			"b.txRfcpatron,b.sdelegOrig, b.tipClaseobra,"+
			"b.cvePkTipObra,b.cvePkFaseConst, b.desDependenciapub,"+			
			"b.desDepcontratante, b.canSuperficie,"+
			"b.impCostoobra, b.fecFechainicioEst,"+
			"b.fecFechaterminoEst, b.porAvanceobraEst,"+
			"b.txTelefono, b.txEmail, b.idPromovido,"+
			"b.fecFechareg, b.cveUsuario, b.domicilioId, b.cveFkPatron, b.idMotivocancelacion,mc.motivocancelacion,b.fecFechaCancela) " +
			"from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b, mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion mc ";
			if(det.getEstatus()!=null && !det.getEstatus().equalsIgnoreCase("4") && !det.getEstatus().equalsIgnoreCase("3")){
//				sql += ", mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico dg ";
			}
			sql += " where b.sdelegOrig = " + det.getSdelegOrig() + " and b.idMotivocancelacion = mc.idMotivocancelacion(+) ";
			if(det.getFechaIncial() != null && det.getFechaFinal() != null){
				sql += " and TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) >= "+Functions.dateToNumberAsString(det.getFechaIncial()) +
				" and TO_NUMBER(to_char(b.fecFechadeteccionFc,'YYMMDD')) <= "+Functions.dateToNumberAsString(det.getFechaFinal());
			}
		
		//Promover a SATIC A
		if(det.getEstatus()!=null && det.getEstatus().equalsIgnoreCase("1")){
			sql += " and b.idPromovido = 1 and b.idMotivocancelacion is null" +
				   " ";
		//Promover a EX
		}else if(det.getEstatus()!=null && det.getEstatus().equalsIgnoreCase("2")){
			sql += " and b.idPromovido = 0 and b.idMotivocancelacion is null" +
					   "  ";
		//Obras canceladas
		}else if(det.getEstatus()!=null && det.getEstatus().equalsIgnoreCase("3")){
			sql += " and b.idMotivocancelacion is not null ";
				   //" and b.domicilioId = dg.domicilioId"; 
		//Obras Sin Domicilio
		}else if(det.getEstatus()!=null && det.getEstatus().equalsIgnoreCase("4")){
			sql += " and b.idMotivocancelacion is null and b.idPromovido is null";
		}else if(det.getNuFoliodeteccion() == null){
			sql += "  and (b.idPromovido is not null or b.idMotivocancelacion is not null)"+
					"  ";
		}
		if(det.getNuFoliodeteccion()!=null){
			sql += " and b.nuFoliodeteccion = '"+det.getNuFoliodeteccion()+"'";						
		}if(det.getFecFechainicioEst()!=null){
			sql += " and to_char(b.fecFechainicioEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechainicioEst())+"'";
		}if(det.getFecFechaterminoEst()!=null){
			sql += " to_char(b.fecFechaterminoEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechaterminoEst())+"'";
		}if(det.getCveTipocorr()!=null){
			sql += " and b.cveTipocorr = "+det.getCveTipocorr();
		}if(det.getCvePkTipObra()!=null){
			sql += " and b.cvePkTipObra = "+det.getCvePkTipObra();
		}if(det.getCvePkFaseConst()!=null){
			sql += " and b.cvePkFaseConst = "+det.getCvePkFaseConst();
		}if(det.getDomCalle()!=null){
			sql += " and dg.nomvial like '%"+det.getDomCalle()+"%'";						
		}if(det.getRefColonia()!=null){
			sql += " and dg.dgAsentamiento.nomAsen like '%"+det.getRefColonia()+"%'";
		}if(det.getNumNroext()!=null){
			sql += " and dg.numextnum = "+det.getNumNroext(); 
		}if(det.getNumNroint()!=null){
			sql += " and dg.numintalf like '%"+det.getNumNroint()+"%'";
		}if(det.getNumCodigopostal()!=null && !det.getNumCodigopostal().equalsIgnoreCase("")){
			sql += " and dg.dgCodigosPostales.id.codigo = '"+det.getNumCodigopostal()+"'";
		}
		sql += " and b.cveDeteccion not in (select p.cveDeteccion from mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion p where p.cveDeteccion is not null)";
		sql += " and b.cveDeteccion not in (select i.cveDeteccion from mx.gob.imss.ctirss.correccion.model.CrtInvitacion i where i.cveDeteccion is not null)";
		sql += " order by b.fecFechareg desc";
		try{
		System.out.println("query "+sql);
		Query query = this.getSession().createQuery(sql);
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		
		
		resultFinal = query.list();
		
		
		/* Se debe de obtener el numero total de registros en la base de datos */
		if (resultFinal.size() > 0) {
			query.setFirstResult(0);
			query.setMaxResults(-1);
			final List temp = query.list();
			System.out.println("temp.size() :: " + temp.size());
			response.setiTotalRecords(temp.size());
			response.setiTotalDisplayRecords(temp.size());
		}
		
		response.setAaData((List<T>)resultFinal);
		}catch (Exception e) {
			System.out.println("--Existo un error--");
			e.printStackTrace();
			response.setAaData((List<T>)resultFinal);
			return response;
		}
		return response;
	}
	
	/**
	 * Metodo que consul por folio de deteccion una deteccion
	 * @author Enrique Duran Jimenez
	 * @date   02/08/2012
	 */
	private CrtDeteccion consultaPorFolio(CrtDeteccion filtro) {
		CrtDeteccion deteccion = new CrtDeteccion();
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		criteria.add(Restrictions.eq("nuFoliodeteccion", filtro.getNuFoliodeteccion()));
		if(criteria.list() != null && criteria.list().size() > 0){
			deteccion = (CrtDeteccion) criteria.list().get(0);
		}else{
			deteccion = null;
		}
		return deteccion;
	}
	
	public DatosSalidaPaginador<T> valida(DatosEntradaPaginador<T> params, List<DgDomicilioGeografico> domicilios) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = new ArrayList<T>();
		List<CrtDeteccion> resultFinal = new ArrayList<CrtDeteccion>();
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		
		logger.debug(params.getModelo().getClass());
				
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(b.cveDeteccion, b.cveTipocorr,"+
			"b.nuFoliodeteccion, b.fecFechadeteccionFc,"+
			"b.nuReportectrlobra, b.nomRazonsocial, b.txCurppatron,"+
			"b.txRfcpatron,b.sdelegOrig, b.tipClaseobra,"+
			"b.cvePkTipObra,b.cvePkFaseConst, b.desDependenciapub,"+			
			"b.desDepcontratante, b.canSuperficie,"+
			"b.impCostoobra, b.fecFechainicioEst,"+
			"b.fecFechaterminoEst, b.porAvanceobraEst,"+
			"b.txTelefono, b.txEmail, b.idPromovido,"+
			"b.fecFechareg, b.cveUsuario, b.domicilioId, b.cveFkPatron,b.idMotivocancelacion) " +
			"from mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion b where " +
			"b.domicilioId in (select d.domicilioId from mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico d)";
		
		if(det.getNuFoliodeteccion()!=null){
			sql += " and b.nuFoliodeteccion = '"+det.getNuFoliodeteccion()+"'";						
		}if(det.getFecFechainicioEst()!=null){
			sql += " and to_char(b.fecFechainicioEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechainicioEst())+"'";
		}if(det.getFecFechaterminoEst()!=null){
			sql += " and to_char(b.fecFechaterminoEst,'DD-MM-YYYY') = '"+Functions.dateToString2(det.getFecFechaterminoEst())+"'";
		}if(det.getCveTipocorr()!=null){
			sql += " and b.cveTipocorr = "+det.getCveTipocorr();
		}if(det.getCvePkTipObra()!=null){
			sql += " and b.cvePkTipObra = "+det.getCvePkTipObra();
		}if(det.getCvePkFaseConst()!=null){
			sql += " and b.cvePkFaseConst = "+det.getCvePkFaseConst();
		}
				
		logger.debug(sql);
		Query query = this.getSession().createQuery(sql);

		if(query.list().size()>0)
			result = query.list();
		
		if(domicilios.size()>0 && result.size()>0){
			Iterator<DgDomicilioGeografico> iterator = domicilios.iterator();
			
			while(iterator.hasNext()){
				Iterator<?> iteratorRes = result.iterator();
				DgDomicilioGeografico dom = iterator.next();				
				while(iteratorRes.hasNext()){					
					CrtDeteccion detRes = (CrtDeteccion)iteratorRes.next();
					if(new BigDecimal(dom.getDomicilioId()).intValue()==detRes.getDomicilioId().intValue()){
						detRes.setRefColonia(dom.getDgAsentamiento().getNomAsen());
						if(dom.getNomvial()!=null){
							if(!dom.getNomvial().equalsIgnoreCase(""))
								detRes.setDomCalle(dom.getNomvial());
						}else{
							detRes.setDomCalle(dom.getDgVialidadByCveViaPrin().getNomVia());
						}
						detRes.setNumNroext(dom.getNumextnum().toString());
						if(dom.getNumintalf()!=null){
							detRes.setNumNroint(dom.getNumintalf().toString());
						}
						detRes.setNumCodigopostal(dom.getDgCodigosPostales().getId().getCodigo());
//						detRes.setDomicilioInegi(dom);
						resultFinal.add(detRes);
					}
				}
			}
			
		}
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		if(resultFinal!=null)
			iTotalRecords = resultFinal.size();
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		if(resultFinal!=null)
			iTotalDisplayRecords = resultFinal.size();
	     
		response.setAaData((List<T>)resultFinal);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	public DatosSalidaPaginador<T> validacionObraSatic(DatosEntradaPaginador<T> params){
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		List<T> result = new ArrayList<T>();
		
		CrtDeteccion  det = (CrtDeteccion) params.getModelo();
		String sSearch = params.getsSearch();
		
		String sql = "select new mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion(P.registroPatronal, P.razonSocial, O.cveNroregobra, U.calle, " + 
            "U.numeroInterior, U.numeroExterior, U.colonia, U.codigoPostal, O.tipClaseobra, P.cvePK, " +
            "O.canSuperficie, O.impImporte, O.fecFechainicioFc, O.fecFechaterminoFc, O.cveNrocontrato) " +
			"from mx.gob.imss.ctirss.correccion.catalogos.model.SatObra O, mx.gob.imss.ctirss.correccion.model.SatPatron P, mx.gob.imss.ctirss.correccion.model.SatUbicacion U, " +
			"mx.gob.imss.ctirss.correccion.model.SacMunicipio M, mx.gob.imss.ctirss.correccion.catalogos.model.SacEntidadfed EF, mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion D, mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion SD " + 
			"where O.cveFkPatron=P.cvePK and O.cveFkUbicacion=U.cvePK " +
            "and U.fkMunicipio = M.cvePK and M.sacEntidadFederativa.cvePk = EF.cvePk " + 
            "and M.sacSubdelegacion.cvePk = SD.cvePk and D.cvePk = SD.sacDelegacion.cvePk " +
            "and SD.cvePk = "+det.getSdelegOrig();
		
			if(det.getNumCodigopostal()!=null && !det.getNumCodigopostal().equalsIgnoreCase("")){
				sql += " and U.codigoPostal = '"+det.getNumCodigopostal()+"'";
			}
			if(sSearch != null && !sSearch.equals("")){
				sql += " and U.calle like '%" + sSearch + "%'";
			}
	             
		logger.debug(sql);         
		
		Query query = this.getSession().createQuery(sql);
		query.setFirstResult(params.getiDisplayStart());
		query.setMaxResults(params.getiDisplayLength());
		logger.debug("resultados :"+query.list().size());
		
		if(query.list().size()>0)
			result = query.list();
		
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
		
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
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

	@Override
	public T consultarXIdDom(T model) {
	Criteria criteria = this.getSession().createCriteria(model.getClass());
	criteria.add(Restrictions.eq("domicilioId", ((CrtDeteccion)model).getDomicilioId()));
	if(criteria.list() != null && criteria.list().size() > 0){
		model = (T) criteria.list().get(0);
	}else{
		model = null;
	}
	
		return model;
	}

	@Override
	public List<T> validaNuReporte(T model) {
		List<T> listaDeteccion = null;
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.add(Restrictions.eq("nuReportectrlobra", ((CrtDeteccion)model).getNuReportectrlobra()));
		criteria.add(Restrictions.eq("sdelegOrig", ((CrtDeteccion)model).getSdelegOrig()));
		listaDeteccion = criteria.list();
		
		return listaDeteccion;
	}

	@Override
	public T buscaUbicacion(T model) {
		SatPatron patron = ((SatUbicacion)model).getPatron();
		Long idPatron = ((SatPatron)patron).getCvePK();
		List<SatObra> lstObras = new ArrayList<SatObra>();
		List<SatUbicacion> lstUbicacionObras = new ArrayList<SatUbicacion>();
		SatUbicacion ubicacion = new SatUbicacion();
		
		Criteria criteria = this.getSession().createCriteria(SatObra.class);
		criteria.add(Restrictions.eq("cveFkPatron", BigDecimal.valueOf(idPatron)));
		criteria.addOrder(Order.asc("cvePk"));
		lstObras = criteria.list();
		
		if(lstObras != null && lstObras.size() > 0){
			Criteria criteria2 = this.getSession().createCriteria(SatUbicacion.class);
			criteria2.add(Restrictions.eq("cvePK", lstObras.get(0).getCveFkUbicacion().intValue()));
			if(criteria2.list() != null && criteria2.list().size() > 0){
				ubicacion = (SatUbicacion) criteria2.list().get(0);
			}			
		}
		
		return (T) ubicacion;
		
	}
	
}
