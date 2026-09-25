package mx.gob.imss.ctirss.correccion.solicitud.service.ejb.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Query;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtAnexoRPPK;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.DeteccionServiceRemote;
import mx.gob.imss.ctirss.correccion.folio.service.ejb.FoliadorServiceLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.model.CgcCatStatus;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoRP;
import mx.gob.imss.ctirss.correccion.model.CgtCorreccion;
import mx.gob.imss.ctirss.correccion.model.CrcDiaInhabil;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SolicitudCorreccionDAO;
import mx.gob.imss.ctirss.correccion.service.ejb.CatalogoServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.PatronesServiceRemote;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.SolicitudServiceRemote;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.DomiciliosInegiServiceRemote;

@Stateless(name="solicitudService", mappedName = "solicitudService")
public class SolicitudServiceBean<T extends AbstractModel> extends AbstractService implements SolicitudServiceRemote<T> {

	private static int DIAS_FECHA_LIMITE_PRESENTA_CORRECION = 40;
	
	@EJB SolicitudCorreccionDAOLocal daoSolicitudCorreccion;
	@EJB DomiciliosInegiServiceRemote domicilioInegiService;
	@EJB PatronesServiceRemote patronesService;
	@EJB DeteccionServiceRemote deteccionService;
	@EJB CatalogoServiceRemote catalogoService;
	@EJB FoliadorServiceLocal foliador;
	@EJB SolicitudCorreccionDAO solCorrDao;

	
	public SolicitudCorreccionDAOLocal getDaoSolicitudCorreccion() {
		return daoSolicitudCorreccion;
	}

	public void setDaoSolicitudCorreccion(
			SolicitudCorreccionDAOLocal daoSolicitudCorreccion) {
		this.daoSolicitudCorreccion = daoSolicitudCorreccion;
	}

	@Override
	public DatosSalidaPaginador<T> pagina(List patrones) {
		
		if(patrones==null) patrones = new ArrayList<CrtAnexosolcorrpat>();
		
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = null;
		
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = patrones.size();
		

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = patrones.size();

		 
		 result = patrones;
	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}

	@Override
	public List addPatron(List patrones, SatPatron patron) {
		if(patron!=null)
		{
			if(patrones.size()>0)
			{
				Iterator i= patrones.iterator();
				while(i.hasNext())
				{
					SatPatron p = (SatPatron)i.next();
					if(p.getRegistroPatronal().equals(patron.getRegistroPatronal()))
						return null;
				}
				
			}
			patrones.add(patron);
			return patrones;
		}
		return patrones;
	}

	@Override
	public List delPatron(List patrones, String patron) {
		List result = new ArrayList();
		if(patron!=null)
		{
			if(patrones.size()>0)
			{
				Iterator i= patrones.iterator();
				while(i.hasNext())
				{
					SatPatron p = (SatPatron)i.next();
					if(p.getRegistroPatronal().indexOf(patron)<0)
					{
						result.add(p);
					}
				}
				
			}
		}
		return result;
	}

	public CrtSolicitudcorr guardar(CrtSolicitudcorr solicitud) {
		
		solicitud.setCveSubdelegacion(solicitud.getPatronCorregir().getCveSubdelegacion().longValue());
		solicitud.setNumTrabajadores(new Integer(solicitud.getNumeroTrabajadores()));
		solicitud.setTxRepresentanteLegal(solicitud.getRepresentante());
		solicitud.setCveStatus(1); //Solicitada
		solicitud.setFecFechaRegistro(new Date());
		solicitud.setCvePatron(solicitud.getPatronCorregir().getCvePK());
//		solicitud.setFecFechaLimite(Functions.addHabilesToDate(new Date(),DIAS_FECHA_LIMITE_PRESENTA_CORRECION));
		
		TipoCorreccion tipoCorreccion = null;
		
		if(solicitud.getIdTipoSolicitud().equals(CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION))
		{
			if(solicitud.getNumeroObra()!=null && !solicitud.getNumeroObra().equals("")){
				solicitud.setCveNumeroRegObra(new BigDecimal(solicitud.getNumeroObra()));
			}
			
			tipoCorreccion = TipoCorreccion.SOLICITUD_CORRECCION_ESPONTANEA_CONSTRUCCION;
		}
		else
		{
			tipoCorreccion = TipoCorreccion.SOLICITUD_CORRECCION_ESPONTANEA;
		}
		
//		if(solicitud.getInvitacion()==null)
//			solicitud.setNuFolio(generaFolio(solicitud,tipoCorreccion));
//		else
//			solicitud.setNuFolio(solicitud.getInvitacion().getNuFolioInvitacion());
		
		
		
		
		if(solicitud.getInvitacion()!=null){
			solicitud.setNuFolio(solicitud.getInvitacion().getNuFolioInvitacion());
		}else{
			solicitud.setNuFolio(generaFolio(solicitud,tipoCorreccion));
		}

		//Se compara el folio previo generado y el recuperado
		String datos[]=solicitud.getCadenaOriginal().split("\\|");
		System.out.println("Previo "+datos[5]+" Recuperado "+solicitud.getNuFolio());
		if(!datos[5].equals(solicitud.getNuFolio()) && solicitud.getInvitacion()==null && !datos[5].contains("EX")){			
			System.out.println("No se puede generar el folio");
			return null;
		}
				
		solicitud.setPeriodos(obtenPeriodos(solicitud));
		solicitud.setCorreccionPatronesGestion(new ArrayList());
		
		List patronesAGuardar = new ArrayList();
		
		
		setTipoPatron(solicitud, solicitud.getPatronCorregir(), "C",patronesAGuardar);
		setTipoPatron(solicitud, solicitud.getPatronPrincipal(), "F",patronesAGuardar);
		
		if(solicitud.getPatrones()!=null&&solicitud.getPatrones().size()>0){
			Iterator i = solicitud.getPatrones().iterator();
			while(i.hasNext())
			{
				SatPatron patIns = (SatPatron)i.next();
				if(!patIns.getRegistroPatronalSD().equals(solicitud.getPatronCorregir().getRegistroPatronalSD()))
				{
					CrtAnexosolcorrpat patAuxIns = new CrtAnexosolcorrpat();
					patAuxIns.setCvePatron(patIns.getCvePK());
					patAuxIns.setCvePatronPr(solicitud.getPatronPrincipal().getCvePK());
					patAuxIns.setTxRazonSocial(solicitud.getRazonSocialPatronCorregir());
					patAuxIns.setTxRepresentanteLegal(solicitud.getRepresentante());
			
					patAuxIns.setTipoPatron("I"); //INDICADOR DE RPS INSCRITOS
					patAuxIns.setTxActividad(patIns.getActividad());
					patAuxIns.setTxClase(patIns.getClase());
					patAuxIns.setTxFraccion(patIns.getFraccion());
					patAuxIns.setTxPrima(patIns.getPrima());
					if(patIns.getTrabajadores()!=null&&patIns.getTrabajadores().length()>0)
						patAuxIns.setNumTrabajadores(new Integer(patIns.getTrabajadores()));
					
					patAuxIns.setTxCurp(solicitud.getCurpPatronCorregir());
					patAuxIns.setTxRfc(solicitud.getRfcPatronCorregir());
					patAuxIns.setTxEmail(solicitud.getEmailPatron());
					patAuxIns.setTxTelefono(solicitud.getTelefonoPatron());
					patAuxIns.setCveSubdelegacionOrig(new Integer(patIns.getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()));
					patAuxIns.setCveDelegacionOrig(new Integer(patIns.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()));
					patAuxIns.setFecFechaRegistro(new Date());
					patAuxIns.setCveUsuario(solicitud.getCveUsuario());
					
					DgDomicilioGeografico domIns = (DgDomicilioGeografico)domicilioInegiService.agregar((DgDomicilioGeografico)solicitud.getDomicilios().get(patIns.getRegistroPatronalSD()));
					patAuxIns.setDomicilioGeografico(domIns);
					patAuxIns.setRegistroPatronal(patIns.getRegistroPatronalSD());
					patAuxIns.setDirreccionInegi(domIns);
					solicitud.getCorreccionPatronesGestion().add(generaPatronGestion(solicitud,patAuxIns));
					patronesAGuardar.add(patAuxIns);
				}
			}
		}
		
		if(solicitud.getDomicilios().get("DOM_OBRA")!=null){
			
			CrtAnexosolcorrpat patAuxIns = new CrtAnexosolcorrpat();
			
			DgDomicilioGeografico domObra = (DgDomicilioGeografico)domicilioInegiService.agregar(
					(DgDomicilioGeografico)solicitud.getDomicilios().get("DOM_OBRA"));
			
			
			patAuxIns.setCvePatronPr(solicitud.getPatronPrincipal().getCvePK());
			patAuxIns.setCveSubdelegacionOrig(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()));
			patAuxIns.setCveDelegacionOrig(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()));
			
			patAuxIns.setTipoPatron("O"); //OBRA
			
			patAuxIns.setFecFechaRegistro(new Date());
			patAuxIns.setCveUsuario(solicitud.getCveUsuario());
			
			
			patAuxIns.setDomicilioGeografico(domObra);
			
			patAuxIns.setDirreccionInegi(domObra);
			
			patronesAGuardar.add(patAuxIns);

		}
				
		solicitud.setPatrones(patronesAGuardar);
		solicitud.setCorreccionGestion(generaSolicitudGestion(solicitud));
		return daoSolicitudCorreccion.save(solicitud);
	}

	private CgtCorreccion generaSolicitudGestion(CrtSolicitudcorr solicitud) {
		CgtCorreccion solGestion = new CgtCorreccion();
		solGestion.setFolio(solicitud.getNuFolio());
		solGestion.setSacSubdelegacion(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion());
//		solGestion.set
//		solGestion.set
		solGestion.setCvePatron(solicitud.getPatronCorregir().getRegistroPatronal());
		solGestion.setDv(new BigDecimal(2));
		solGestion.setNombre(solicitud.getPatronCorregir().getRazonSocial());
		solGestion.setPeriododel(solicitud.getFecFechaPeriodoIni());
		solGestion.setPeriodoal(solicitud.getFecFechaPeriodoFin());
		solGestion.setFecFechareg(new Date());
		solGestion.setCveUsuario(solicitud.getCveUsuario());
		
		CgcCatTipo tipo = new CgcCatTipo();
		tipo.setIdTipo(new Long(solicitud.getCveTipoCorreccion()));
		solGestion.setCgcCatTipo(tipo);
		
		CgcCatStatus status = new CgcCatStatus();
		status.setIdStatus(new Long(1));
		solGestion.setCgcCatStatus(status);
		
		return solGestion;
	}

	private CgtAnexoRP generaPatronGestion(CrtSolicitudcorr solicitud,CrtAnexosolcorrpat patCorrecgir) {
		CgtAnexoRP anexoPatronGestion = new CgtAnexoRP();
		AbstractCgtAnexoRPPK pk = new AbstractCgtAnexoRPPK();
		pk.setFolio(solicitud.getNuFolio());
		pk.setRp(patCorrecgir.getRegistroPatronal());
		anexoPatronGestion.setAtrabomisos(new BigDecimal(0));
		anexoPatronGestion.setAtrabrevisados(new BigDecimal(0));
		anexoPatronGestion.setAtrabsubdeclarados(new BigDecimal(0));
		anexoPatronGestion.setCveUsuario(solicitud.getCveUsuario());
		anexoPatronGestion.setDv(new BigDecimal(solicitud.getPatronCorregir().getRegistroPatronal().substring(solicitud.getPatronCorregir().getRegistroPatronal().length()-2, solicitud.getPatronCorregir().getRegistroPatronal().length()-1)));
		anexoPatronGestion.setFecFechareg(new Date());
		anexoPatronGestion.setId(pk);
		anexoPatronGestion.setRtrabomisos(new BigDecimal(0));
		anexoPatronGestion.setRtrabrevisados(new BigDecimal(0));
		anexoPatronGestion.setRtrabsubdeclarados(new BigDecimal(0));
		return anexoPatronGestion;
	}

	public CrtSolicitudcorr consultarPatrones(CrtSolicitudcorr patrones){
		List<CrtAnexosolcorrpat> anexos = null;
				
		CrtSolicitudcorr resultado =  (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorFolio(patrones);
		
		if(resultado!=null){
			anexos = daoSolicitudCorreccion.consultarAnexoSolicitudes(resultado.getCveSolicitudCorr(), patrones.getPeriodo(),patrones);
			if(anexos!=null){
				resultado.setLstAnexoSolicitudesCorr(anexos);
				resultado.setPeriodo(patrones.getPeriodo());
				
				Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) anexos.iterator();
				CrtAnexosolcorrpat currentItem = null;
				
				while(iter.hasNext()){
					currentItem = iter.next();
					if(currentItem.getCvePatronPr()==null){
						System.out.println("PATRON PRINCIPAL: " + currentItem.getTxRazonSocial() +  " ID " + currentItem.getCvePatronPr());
						resultado.setRazonSocialPatronPrin(currentItem.getTxRazonSocial());
					}		
				}
				
			}else resultado = null; // Encontramos el folio de la correccion pero no coincide con el ejercicio que se ha introducido
				 
		}
		
		return resultado;
		
	}
	
	private List obtenPeriodos(CrtSolicitudcorr solicitud) {
		List periodos = new ArrayList();
		int inicio = Functions.getYear(solicitud.getFecFechaPeriodoIni());
		int fin = Functions.getYear(solicitud.getFecFechaPeriodoFin());
		while(inicio<=fin)
		{
			periodos.add(inicio+"");
			inicio++;
		}
		return periodos;
	}
	
	public String generaFolio(CrtSolicitudcorr solicitud,TipoCorreccion tipoCorreccion)
	{
		String folio="";
        String idDelegacionStr = ""; 
        String idSubDelegacionStr = "";

        if(solicitud.getUnoVariosRp().intValue()== CrtSolicitudcorr.SOLICITUD_UN_RP.intValue()){
        	idDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()).intValue());
            idSubDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir	().getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()).intValue());
        }else{
        	idDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronPrincipal().getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()).intValue());
            idSubDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronPrincipal().getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()).intValue());
        }
        
        
        Calendar c = Calendar.getInstance();
		
		folio = foliador.recuperarSiguienteFolio(idDelegacionStr, idSubDelegacionStr, c.get(Calendar.YEAR), tipoCorreccion);
		
		return folio;
	}
	
	@Override
	public String generaFolioTemporal(CrtSolicitudcorr solicitud,TipoCorreccion tipoCorreccion)
	{
		String folio="";
        String idDelegacionStr = ""; 
        String idSubDelegacionStr = "";

        if(solicitud.getUnoVariosRp().intValue()== CrtSolicitudcorr.SOLICITUD_UN_RP.intValue()){
        	idDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()).intValue());
            idSubDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir	().getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()).intValue());
        }else{
        	idDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()).intValue());
            idSubDelegacionStr = new DecimalFormat("00").format(new Integer(solicitud.getPatronCorregir().getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()).intValue());
        }
        
        
        Calendar c = Calendar.getInstance();
				
        folio=foliador.recuperarFolioSiguienteSinActualizar(Long.valueOf(idDelegacionStr), Long.valueOf(idSubDelegacionStr), c.get(Calendar.YEAR), tipoCorreccion);
		return folio;
	}
	
	

	public boolean validaSolicitud(CrtSolicitudcorr solicitud) {
		return daoSolicitudCorreccion.validaSolicitud(solicitud);
	}

	public List validaSolicitudResult(CrtSolicitudcorr solicitud) {
		return daoSolicitudCorreccion.validaSolicitudResult(solicitud);
	}

	public CrtSolicitudcorr consultarFolio(CrtSolicitudcorr solicitud) {
		
		return (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorFolio(solicitud);
	}
	
	
	public CrtSolicitudcorr consultarFolioRegPat(CrtSolicitudcorr solicitud) {
		
		return (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorFolioRegPat(solicitud);
	}
	@Override
	public DatosSalidaPaginador<T> paginaSolicitudes(List solicitudes) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			
			
			int iTotalRecords = 0;
			/*Se debe de obtener el numero total de registros en la base de datos*/
			iTotalRecords = solicitudes.size();
			

			
			int iTotalDisplayRecords = 0;
			
			iTotalDisplayRecords = solicitudes.size();

			 
			response.setAaData(solicitudes);
			response.setiTotalDisplayRecords(iTotalDisplayRecords);
			response.setiTotalRecords(iTotalRecords);
		}
		else
		{
			response.setAaData(new ArrayList());
			response.setiTotalDisplayRecords(0);
			response.setiTotalRecords(0);
			response.setsEcho("undefined");
			response.setsColumns(null);
		}
		return response;
			
	}
	
	

	public List consultaSolicitudesPendientes(Long codigoDelegacion,Long codigoSubDelegacion, Long idSubdelegacion) {
		System.out.println("Consultas Pendientes");
		List solicitudes = daoSolicitudCorreccion.consultarSolicitudesPendientes(codigoDelegacion, codigoSubDelegacion,idSubdelegacion);
		
		if(solicitudes!=null&&solicitudes.size()>0)
		{   System.out.println("Solicitudes pendientes "+solicitudes.size());
			List solicitudesRs = new ArrayList();
			Iterator i = solicitudes.iterator();
			CrtSolicitudcorr solicitud = null;
			while(i.hasNext())
			{
				solicitud = (CrtSolicitudcorr)i.next();
				solicitud.setPatrones(new ArrayList());
				solicitud.setPatronCorregir(patronesService.getById(solicitud.getCvePatron()));
				
				
				List listaPatrones = daoSolicitudCorreccion.getSolicitudDetalles(solicitud, CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
				CrtAnexosolcorrpat anexo = null;
				if(!listaPatrones.isEmpty()){
					Iterator iterPatrones = listaPatrones.iterator();					
					while(iterPatrones.hasNext()){
						anexo = (CrtAnexosolcorrpat)iterPatrones.next();
						if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL)){	
							
							DgDomicilioGeografico dom = new DgDomicilioGeografico();
							dom.setDomicilioId(anexo.getCveDomicilio().longValue());
							dom = (DgDomicilioGeografico)domicilioInegiService.consultaPorClave(dom);
							
							solicitud.setPatronPrincipal(new SatPatron());							
							solicitud.getPatronPrincipal().setDomicilioGeografico(dom);
							
						}
						
					}
				}
				
				
				
				if(solicitud.getCveTipoCorreccion()==1)
					solicitud.setTipo("ESPONTANEA");
				else if(solicitud.getCveTipoCorreccion()==2)
					solicitud.setTipo("INVITACION");
				else if(solicitud.getCveTipoCorreccion()>2)
					solicitud.setTipo("PROMOCION");
				
				solicitud=ejecutaAfirmativaFicta(solicitud);
				if(solicitud!=null){
					solicitudesRs.add(solicitud);
				}
			}
			return solicitudesRs;
		}
		return solicitudes;
	}
	
	
	public CrtSolicitudcorr ejecutaAfirmativaFicta(CrtSolicitudcorr correccion){
		
		Date fecha=agregaDias(correccion.getFecFechaElacoracionCorreccion(), 15);
		System.out.println();
		System.out.println("Fecha IncialAA "+correccion.getFecFechaElacoracionCorreccion());
		System.out.println("Fecha Calculada "+fecha);
		System.out.println(fecha.compareTo(Calendar.getInstance().getTime()));
		if(fecha.compareTo(Calendar.getInstance().getTime())<0 || fecha.compareTo(Calendar.getInstance().getTime())==0 ){
			correccion.setFecFechaAutorizacionCorreccion(fecha);
			correccion.setCveStatus(CrtSolicitudcorr.SOLICITUD_AUTORIZADA);		
			correccion.setFecFechaLimite(agregaDias(fecha, 40));
			daoSolicitudCorreccion.save(correccion);
			System.out.println("Afirmativa ficta "+correccion.getNuFolio());
			correccion=null;
		}
		return correccion;
	}
	
	
	
	
	//fdfsgsdfg
	
	
	public Date agregaDias(Date fecha,int dias) {
		
		// TODO Auto-generated method stub                   
		  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	      Calendar cal = Calendar.getInstance();
	  	  List<CrcDiaInhabil> diasInhabiles = solCorrDao.obtenerDiasInhabiles();
	      
	      try {
	       
			cal.setTime(fecha);
			int diasAgregados=0;			
			while(diasAgregados<dias){
				cal.add(Calendar.DATE, 1);
				if(!esDiaInhabil(cal.getTime(),diasInhabiles) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
					diasAgregados++;
				}
			}
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return cal.getTime();
	}

	
	
		private boolean esDiaInhabil(Date fecha,List<CrcDiaInhabil> diasInhabiles){				
				for(CrcDiaInhabil di:diasInhabiles){
					if(di.getFecha().compareTo(fecha)==0){
						System.out.println(fecha+" es inhabil ");
						return true;
					}
				}		
				return false;
			}

	
	
	
	@SuppressWarnings("rawtypes")
	public CrtSolicitudcorr getSolicitudDetalles(CrtSolicitudcorr solicitud, String tipoPatron){
		
		List listaPatrones = daoSolicitudCorreccion.getSolicitudDetalles(solicitud,tipoPatron);
				
		CrtAnexosolcorrpat anexo = null;
		
		if(!listaPatrones.isEmpty()){
			Iterator iterPatrones = listaPatrones.iterator();
			
			while(iterPatrones.hasNext()){
				anexo = (CrtAnexosolcorrpat)iterPatrones.next();
				
				if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_OBRA)){

				}else{
					anexo.setPatron(patronesService.getById(anexo.getCvePatron()));
				}
				
				//Domicilio  Centro de Trabajo
				DgDomicilioGeografico dom = new DgDomicilioGeografico();
				dom.setDomicilioId(anexo.getCveDomicilio().longValue());
				dom = (DgDomicilioGeografico)domicilioInegiService.consultaPorClave(dom);
				

				if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_CENTRO_TRABAJO)){
					solicitud.getPatronCorregir().setDomicilioGeografico(dom);
					solicitud.getPatronCorregir().setCurp(anexo.getTxCurp());
					solicitud.setActividad(anexo.getTxActividad());
					solicitud.setClasePatronCorregir(anexo.getTxClase());
					solicitud.setFraccionPatronCorregir(anexo.getTxFraccion());
					solicitud.setPrimaPatronCorregir(anexo.getTxPrima());
					solicitud.setRepresentante(anexo.getTxRepresentanteLegal());
					
				}else if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL)){	
					
					solicitud.setPatronPrincipal(anexo.getPatron());
					
					solicitud.getPatronPrincipal().setDomicilioGeografico(dom);
					solicitud.getPatronPrincipal().setCurp(anexo.getTxCurp());
					solicitud.getPatronPrincipal().setTrabajadores(anexo.getNumTrabajadores()+"");
					solicitud.setEmailPatron(anexo.getTxEmail());
					solicitud.setTelefonoPatron(anexo.getTxTelefono());
					
				}else if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_INSCRITO)){	
					
					anexo.setDirreccionInegi(dom);
					anexo.setDomicilioGeografico(dom);
					
					if(solicitud.getLstAnexoSolicitudesCorr()==null)
						solicitud.setLstAnexoSolicitudesCorr(new ArrayList<CrtAnexosolcorrpat>());
					
								
					solicitud.getLstAnexoSolicitudesCorr().add(anexo);
				
				}else if(anexo.getTipoPatron().equalsIgnoreCase(CrtAnexosolcorrpat.TIPO_REGISTRO_RP_OBRA)){	
					
					solicitud.setPatronObra(new SatPatron());
					solicitud.getPatronObra().setDomicilioGeografico(dom);
					
				
				}					
			}
		
			solicitud.setPatrones(solicitud.getLstAnexoSolicitudesCorr());
		}
		
		
		return solicitud;

	}
	
	public List consultarSolicitudesById(Integer id) {
		
	
		return (List) daoSolicitudCorreccion.consultarSolicitudesById(id);
		
	}

	public List consultarSolicitudesById(Integer id,Long idSubdelegacion) {
		
		
		return (List) daoSolicitudCorreccion.consultarSolicitudesById(id,idSubdelegacion);
		
	}
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer claveSolicitud, Long cveEjercicio) {
		return daoSolicitudCorreccion.consultarAnexoSolicitudes(claveSolicitud, cveEjercicio);
	}

	public CrtSolicitudcorr actualizar(CrtSolicitudcorr solicitud) {
		return daoSolicitudCorreccion.update(solicitud);
	}

//	@Override
	public void autorizarSolicitudes() {
		List solicitudes = consultaSolicitudesPendientes(new Long(0),new Long(0),new Long(0));
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			Iterator i = solicitudes.iterator();
			while(i.hasNext())
			{
				CrtSolicitudcorr sol = (CrtSolicitudcorr)i.next();
				Calendar c = Calendar.getInstance();
				Date fechaLim = Functions.addHabilesToDate(sol.getFecFechaRegistro(), 15);
				if(fechaLim.getTime()<c.getTime().getTime())
				{
					sol.setCveStatus(2);
					daoSolicitudCorreccion.update(sol);
				}
			}
		}
	}
	
	private void setTipoPatron(CrtSolicitudcorr solicitud, SatPatron patron, String tipoPatron,List patronesAGuardar){
		CrtAnexosolcorrpat patCorrecgir = new CrtAnexosolcorrpat();
		patCorrecgir.setCvePatron(patron.getCvePK());
		patCorrecgir.setTxRazonSocial(solicitud.getRazonSocialPatronCorregir());
		patCorrecgir.setTxRepresentanteLegal(solicitud.getRepresentante());
		patCorrecgir.setTipoPatron(tipoPatron); //RP A CORREGIR
		
		
		patCorrecgir.setTxActividad(solicitud.getActividad());
		patCorrecgir.setTxClase(solicitud.getClasePatronCorregir());
		patCorrecgir.setTxFraccion(solicitud.getFraccionPatronCorregir());
		patCorrecgir.setTxPrima(solicitud.getPrimaPatronCorregir());
		patCorrecgir.setNumTrabajadores(new Integer(solicitud.getNumeroTrabajadores()));
		patCorrecgir.setCveUsuario(solicitud.getCveUsuario());
		patCorrecgir.setTxCurp(solicitud.getCurpPatronCorregir());
		patCorrecgir.setTxRfc(solicitud.getRfcPatronCorregir());
		patCorrecgir.setTxEmail(solicitud.getEmailPatron());
		patCorrecgir.setTxTelefono(solicitud.getTelefonoPatron());
		patCorrecgir.setCveSubdelegacionOrig(new Integer(patron.getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo()));
		patCorrecgir.setCveDelegacionOrig(new Integer(patron.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo()));
		patCorrecgir.setFecFechaRegistro(new Date());
		
		DgDomicilioGeografico dom = (DgDomicilioGeografico)domicilioInegiService.agregar((DgDomicilioGeografico)solicitud.getDomicilios().get(patron.getRegistroPatronalSD()+tipoPatron));
		//patron.setDomicilioGeografico(dom);
		//solicitud.getPatronPrincipal().setDomicilioGeografico(dom);

		patCorrecgir.setDomicilioGeografico(dom);
		patCorrecgir.setRegistroPatronal(patron.getRegistroPatronalSD());
		patCorrecgir.setDirreccionInegi(dom);
		solicitud.getCorreccionPatronesGestion().add(generaPatronGestion(solicitud,patCorrecgir));
		patronesAGuardar.add(patCorrecgir);

	}
	
	public CrtSolicitudcorr consultaPorFolioAuditorAsignado(CrtSolicitudcorr solicitud) {
		
		return (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorFolioAuditorAsignado(solicitud);
	}

	@Override
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudesReport(Integer claveSolicitud) {
		return daoSolicitudCorreccion.consultarAnexoSolicitudesReport(claveSolicitud);
	}

	public CrtSolicitudcorr consultarPorId(Integer cveSolicitud) {
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(cveSolicitud);
		return (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorClave(solicitud);
	}

	@Override
	public List<CrtAnexosolcorrpat> consultarAnexos(Integer claveSolicitud,
			String tipoPatron) {
		// TODO Auto-generated method stub
		CrtSolicitudcorr sol=new CrtSolicitudcorr();
		sol.setCveSolicitudCorr(claveSolicitud);
		List listaPatrones = daoSolicitudCorreccion.getSolicitudDetalles(sol,tipoPatron);
		return listaPatrones;
	}

	

}
 

