package mx.gob.imss.ctirss.correccion.prorroga.service.ejb.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcSubDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrcDiaInhabil;
import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SolicitudCorreccionDAO;
import mx.gob.imss.ctirss.correccion.prorroga.service.ejb.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao.ProrrogaDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao.DomiciliosInegiDAOLocal;

@Stateless(name="prorrogaService", mappedName = "prorrogaService")
public class ProrrogaServiceBean <T extends AbstractModel> extends AbstractService implements ProrrogaServiceRemote<T> {

	@EJB ProrrogaDAOLocal<AbstractModel> daoProrroga;
	
	@EJB CatalogoDAOLocal<T> daoDelta;
	
	@EJB DomiciliosInegiDAOLocal<T> daoInegi;
	
	@EJB SolicitudCorreccionDAO solCorrDao;


	public DatosSalidaPaginador<T> pagina(String regPatronal) {
		
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result;
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
		
		result = (List<T>) daoProrroga.consultar(regPatronal);
		
		if(result!= null){
	
			/*Se debe de obtener el numero total de registros en la base de datos*/
			iTotalRecords = result.size();
			

			/**
			 * Total records, after filtering (i.e. the total number of records
			 * after filtering has been applied - not just the number of records
			 * being returned in this result set)
			 */
			
			iTotalDisplayRecords = result.size();
		}else{
			result = new ArrayList<T>();
		}

	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}
	
	public Map agregar(T model) {
	
		SacSubdelegacion sub = new SacSubdelegacion();
//		ArrayList listaCorrecciones = (ArrayList) daoDelta.consultaLibrePorClave(0L, "from CrtSolicitudcorr c where c.nuFolio = '"+ ((CrtAnexosolcorrpat)model).getNuFolio() +"'" );
		ArrayList listaCorrecciones = (ArrayList) daoDelta.consultaLibrePorClave(0L, "from CrtSolicitudcorr c where c.cveSolicitudCorr = '"+ ((CrtAnexosolcorrpat)model).getCveSolicitudCorr() +"'" );
		CrtSolicitudcorr correccion = new CrtSolicitudcorr(); 
		if(listaCorrecciones !=null && listaCorrecciones.size()>0){
			correccion = (CrtSolicitudcorr) listaCorrecciones.get(0);
			ArrayList listaSubdelegaciones = (ArrayList) daoDelta.consultaLibrePorClave(0L, "from SacSubdelegacion s where s.cvePk = "+ correccion.getCveSubdelegacion() );
			if(listaSubdelegaciones!=null && listaSubdelegaciones.size()>0){
					sub = (SacSubdelegacion) listaSubdelegaciones.get(0);
			}
			
			correccion.setFecFechaLimite(addHabilesToDate(correccion.getFecFechaLimite(),10));
			daoDelta.actualiza((T) correccion);
			System.out.println("Actualiza");
		}
		
		 
		
		CrtProrroga prorroga = new CrtProrroga();
		prorroga.setCveSolicitudcorr(new BigDecimal(((CrtAnexosolcorrpat)model).getCveSolicitudCorr()));
		prorroga.setFecElaborasolpro(Calendar.getInstance().getTime());
		prorroga.setFecFechareg(Calendar.getInstance().getTime());
		prorroga.setTxLugar(((CrtAnexosolcorrpat)model).getLugar());
		prorroga.setTxMotivorazon(((CrtAnexosolcorrpat)model).getMotivo());
		prorroga.setTxRepLegalElab(((CrtAnexosolcorrpat)model).getTxRepresentanteLegal());
		prorroga.setCveStatus(CrcStatus.SOLICITADA.longValue());
		prorroga.setCveUser(((CrtAnexosolcorrpat)model).getCveUsuario());
		prorroga.setFecFechareg(new Date());
		
		System.out.println("*******************************Valor de la solicitud" + ((CrtAnexosolcorrpat)model).getCveSolicitudCorr() );
		
		 daoProrroga.agregar(prorroga);
		 CrtAnexosolcorrpat modelo = (CrtAnexosolcorrpat)model;
		 modelo.setStrDelegacion(sub.getSacDelegacion().getNomNombre());
		 modelo.setStrSubdelegacion(sub.getNomNombre());
		return  acuseRegistro(modelo);
	}


    public  Date addHabilesToDate(Date fecha, int dias) {
    	System.out.println("fecha "+fecha+" mas "+dias);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        List<CrcDiaInhabil> diasInhabil=solCorrDao.obtenerDiasInhabiles();
        while(dias>0)
        {
            cal.add(cal.DAY_OF_MONTH, 1);
            if(cal.get(cal.DAY_OF_WEEK)!=cal.SATURDAY&&cal.get(cal.DAY_OF_WEEK)!=cal.SUNDAY && !esInhabil(cal.getTime(),diasInhabil)){
            	dias = dias -1;
            }
            	
        }
        Date retorno = cal.getTime();
        System.out.println("Fecha Final "+retorno);
        return retorno;
    }
	
	
	public boolean esInhabil(Date fecha,List<CrcDiaInhabil> diasInhabil){	
		boolean flag=false;
		for(CrcDiaInhabil dia:diasInhabil){
			if(dia.getFecha().equals(fecha)){
				flag=true;
			}
		}		
		return flag;
	}
    
    
	public List<T> consultar(T model) {
		
		return (List<T>) daoProrroga.consultar(((CrtAnexosolcorrpat)model).getRegistroPatronal());
	}

	public T consultarPorFolio(T model) {
		
		CrtSolicitudcorr solicitud = (CrtSolicitudcorr) daoProrroga.consultarPorFolio(model);
		T resultado = null;
		

		if(solicitud!=null){
			CrtAnexosolcorrpat filtro = new CrtAnexosolcorrpat();
			
			if(daoProrroga.validaPresentacionCorr(solicitud)){ // la solicitud de la correcion ya fue Presentada
				filtro.setEstadoFolioCorr(4);
				resultado = (T) filtro;
				System.out.println("la solicitud de la correcion ya fue Presentada");
			}
			else if (daoProrroga.validaProrroga(solicitud)) { // la solicitud de la correcion ya tiene una prorroga
				filtro.setEstadoFolioCorr(3);
				resultado = (T) filtro;
				System.out.println("la solicitud de la correcion ya tiene una prorroga");
			 }else if(solicitud.getCveStatus()==25){
				 System.out.println("Folio derivado a dictamen");
					filtro.setEstadoFolioCorr(5);
					resultado = (T) filtro;	
			 }else if(solicitud.getCveStatus()==22){
				 filtro.setEstadoFolioCorr(6);
					resultado = (T) filtro;	
			 }else if(solicitud.getCveStatus()!=2){ // la solicitud de la correcion no fue aceptada dado que tiene un estatus diferente a Aceptada
				System.out.println("Correccion no aceptada");
				filtro.setEstadoFolioCorr(1);
				resultado = (T) filtro;	
			}else if(solicitud.getFecFechaLimite()==null || compareDateNoHours( Calendar.getInstance().getTime(), solicitud.getFecFechaLimite())){
				filtro.setEstadoFolioCorr(2); // la fecha limite de la correccion se ha vencido
				System.out.println("Fecha vencida");
				resultado = (T) filtro;}
			else {
				filtro.setCveSolicitudCorr(solicitud.getCveSolicitudCorr());
				filtro.setNuFolio(solicitud.getNuFolio());
				CrtAnexosolcorrpat aux = (CrtAnexosolcorrpat) daoProrroga.consultarPorClaveAnexoSol(filtro);
				 if(aux!=null){
					 aux.setNuFolio(solicitud.getNuFolio());
					 String patronal = daoProrroga.obtenerRegPatronal(solicitud.getCveSolicitudCorr());
					 aux.setRegistroPatronal(patronal.length()>=10?patronal.substring(0,10):"");
					 aux.setDigitoVerificador(patronal.length()>=11?patronal.substring(10):"");
					 aux.setSolicitudCorreccion(solicitud);
					 
					 if(solicitud.getCveTipoCorreccion()!= null){ // Tipo de Correccion
					 CrcTipoCorr tipoCorr = new CrcTipoCorr();
					 tipoCorr.setCveTipocorr(solicitud.getCveTipoCorreccion());
					 aux.setTipoCorreccion(((CrcTipoCorr)daoDelta.consultaPorClave((T) tipoCorr)).getTxDescripcion());
					 }
					 
					 		if(aux.getCveDomicilio()!=null){ // SI EXISTE EL DOMICILIO LO VAMOS A BUSCAR
					 			
					 			DgDomicilioGeografico buscar = new DgDomicilioGeografico(); // Datos del Domicilio
						 		buscar.setDomicilioId(aux.getCveDomicilio());
						 		DgDomicilioGeografico dgDomicilio =  (DgDomicilioGeografico) daoInegi.consultaPorClave((T) buscar);
			
						 		//Se recupera de BDTU
						 		dgDomicilio=daoInegi.getDomicilioBDTU(dgDomicilio);
						 
						 		if(dgDomicilio!=null){
						 			aux.setCalle(dgDomicilio.getNomvial()); // calle
						 			aux.setCodigoPostal(dgDomicilio.getDgCodigosPostales().getId().getCodigo());
						 			aux.setColonia(dgDomicilio.getDgAsentamiento().getNomAsen());
						 			aux.setEntidadFederativa(dgDomicilio.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
						 			aux.setNumExterior(dgDomicilio.getNumextnum());
						 			aux.setNumInterior(dgDomicilio.getNumintnum());
						 			aux.setNumExteriorAlfa(dgDomicilio.getNumextalf());
						 			aux.setNumInteriorAlfa(dgDomicilio.getNumintalf());
						 			aux.setMunicipio(dgDomicilio.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
						 			aux.setLocalidad(dgDomicilio.getDgCatLocalidad().getNomLoc());
						 		}
					 	}
				 }
					
				resultado = (T) aux;
			}
		}


		return resultado;
	}

	private Calendar getCalNoHours(final Date fecha) {
		
		final Calendar retVal = GregorianCalendar.getInstance();
		retVal.setTime(fecha);
		retVal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		retVal.clear(GregorianCalendar.MINUTE);
		retVal.clear(GregorianCalendar.SECOND);
		retVal.clear(GregorianCalendar.MILLISECOND);
		retVal.getTime();
		
		return retVal;
	}

	
	private boolean compareDateNoHours(final Date fecha1, final Date fecha2) {
		boolean retVal = false;
		
		final Calendar cal1 = getCalNoHours(fecha1);		
		final Calendar cal2 = getCalNoHours(fecha2);
		
		if (cal1.getTimeInMillis() > cal2.getTimeInMillis()) {
			retVal= true;
		}
		
		return retVal;
	}

	private Map acuseRegistro(CrtAnexosolcorrpat acuse){
	  Map parameters = new HashMap();
	  
	  parameters.put("txDelegacion", acuse.getStrDelegacion());
	  parameters.put("txSubDelegacion", acuse.getStrSubdelegacion());
	  parameters.put("razonSocial", acuse.getTxRazonSocial());
	  parameters.put("nuFolio",acuse.getNuFolio());
	  parameters.put("registroPatronal", acuse.getRegistroPatronal()+acuse.getDigitoVerificador());
	  parameters.put("txDigitoVerificador",acuse.getDigitoVerificador());
	  parameters.put("txCurp",acuse.getTxCurp());
	  parameters.put("txRFC",acuse.getTxRfc());
	  parameters.put("calleDomicilio",acuse.getCalle());
	  parameters.put("numeroExtDomicilio",acuse.getNumExterior()==null?acuse.getNumExteriorAlfa():acuse.getNumExterior().toString());
	  parameters.put("numeroIntDomicilio",acuse.getNumInterior()==null?acuse.getNumInteriorAlfa():acuse.getNumInterior().toString());
	  parameters.put("coloniaDomicilio",acuse.getColonia());
	  parameters.put("municipioDomicilio",acuse.getMunicipio());
	  parameters.put("codigoPostalDomicilio",acuse.getCodigoPostal());
	  parameters.put("telefonoDomicilio",acuse.getTxTelefono());
	  parameters.put("correoElectronico",acuse.getTxEmail());
	  parameters.put("txMotivo",acuse.getMotivo());
	  parameters.put("txRepresentanteLeg",acuse.getTxRepresentanteLegal());
	  parameters.put("txLugar",acuse.getLugar());
	  parameters.put("entidadFederativa",acuse.getEntidadFederativa());
	  parameters.put("localidadDomicilio",acuse.getLocalidad());
	  parameters.put("txFechaReg", new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Calendar.getInstance().getTime()));
	  
	  return parameters;
	}

	@Override
	public DatosSalidaPaginador<T> paginaAP(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		
		List<T> result;
		int iTotalRecords = 0;
		int iTotalDisplayRecords = 0;
		CrtProrroga model = (CrtProrroga) params.getModelo();
		result = (List<T>) daoProrroga.consultarAP(model);
		
		if(result!= null){
	
			/*Se debe de obtener el numero total de registros en la base de datos*/
			iTotalRecords = result.size();
			

			/**
			 * Total records, after filtering (i.e. the total number of records
			 * after filtering has been applied - not just the number of records
			 * being returned in this result set)
			 */
			
			iTotalDisplayRecords = result.size();
		}else{
			result = new ArrayList<T>();
		}

	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}

	@Override
	public List<T> llenarStatus() {
		
		return (List<T>) daoProrroga.llenarStatus();
	}

	@Override
	public T buscaTipoCorr(String cveTipoCorr) {
		Long id = Long.valueOf(cveTipoCorr);
		return (T) daoProrroga.buscaTipoCorr(id);
	}

	@Override
	public void guardar(List<T> lst) {
		daoProrroga.guardaStatus((List<AbstractModel>) lst);
		
	}
	
	
	
}
