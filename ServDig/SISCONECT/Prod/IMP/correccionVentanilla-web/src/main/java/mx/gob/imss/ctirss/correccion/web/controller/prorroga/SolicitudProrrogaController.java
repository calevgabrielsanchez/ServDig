package mx.gob.imss.ctirss.correccion.web.controller.prorroga;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcMes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtAnexosolcorrpatWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.FirmaElectronicaService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.exception.comun.ServicioRemotoNoDisponibleException;
import mx.gob.imss.ctirss.correccion.framework.exception.firma.CertificadoInvalidoException;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacionRP;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.servlets.enviaArchivo.EnviaArchivoServlet;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/solicitud/prorroga")
public class SolicitudProrrogaController extends AbstractController{
	private final static Logger logger = Logger.getLogger(SolicitudProrrogaController.class);
	@Autowired
	private ProrrogaService<CrtAnexosolcorrpat> prorrogaServiceBean;
	
	@Autowired
	private FirmaElectronicaService firmaElectronicaService;
	
	@Autowired
	private InvitacionService<CrtInvitacion> invitacionService;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private SolicitudService solicitudService;
	

	@Autowired
	private ICatalogoService<CrtAnexosolcorrpat> anexoServiceBean;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		model.addAttribute(new CrtAnexosolcorrpat());
		return determinaURL(request, "solicitudProrroga/mainProrroga", "solicitudProrroga/mainProrroga/patron");
	}
	
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrtAnexosolcorrpat> consultar(@RequestBody CrtAnexosolcorrpat anexoSol) {
		
		logger.debug("REGISTRO PATRONAL A BUSCAR:"+ anexoSol.getRegistroPatronal());
		return  this.prorrogaServiceBean.consultar(anexoSol);		
	}
	
	@RequestMapping(value="/consultarPorFolio" , method=RequestMethod.POST)
	public @ResponseBody CrtAnexosolcorrpat consultarPorFolio(@RequestBody CrtAnexosolcorrpat anexoSol, HttpServletResponse response, HttpServletRequest request) {
		
		logger.debug("FOLIO  A BUSCAR:"+ anexoSol.getNuFolio());
		
		CrtAnexosolcorrpat resultado  = this.prorrogaServiceBean.consultarPorFolio(anexoSol);		
		SimpleDateFormat sdf = new SimpleDateFormat(AbstractDocumentoElectronicoModel.FORMATO_FECHA_CADENA_ORIGINAL);
		if(resultado!=null){
		  Date hoy = new Date();
		  resultado.setFechaCadenaOriginal(sdf.format(hoy));
		  UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		  resultado.setUsuarioFirmado(user);
		  
		  CrtInvitacion modelo = new CrtInvitacion();
		  modelo.setNuFolioInvitacion(anexoSol.getNuFolio());
		  modelo.setCveFkSubdelegacion(new BigDecimal(user.getIdSubDelegacion()));
		  
		  List<?> ls = catalogoServiceBean.consultaSQL("select FEC_FECHANOTIFI from CRT_INVITACION" +
				  						  " where NU_FOLIOINVITACION = '"+anexoSol.getNuFolio()+"'");
		  
		  if(ls!=null && !ls.isEmpty()  && resultado.getSolicitudCorreccion()!=null){
			  resultado.getSolicitudCorreccion().setFechaRecepcionOficio(Functions.dateToString( (Date)ls.get(0)));
		  }
		  
		}
		return resultado;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
	public @ResponseBody DatosSalidaPaginador<CrtAnexosolcorrpat> pagina(@RequestBody CrtAnexosolcorrpatWrapperDataTable aoData,HttpServletRequest request) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtAnexosolcorrpat> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		HttpSession session = request.getSession();
		
		DatosSalidaPaginador<CrtAnexosolcorrpat> reply =  this.prorrogaServiceBean.pagina(send.getsSearch());
		logger.debug(".-.-controller realizo consulta) {");
	    reply.setsEcho(send.getsEcho());
	    
	    return reply;
	}
	
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrtAnexosolcorrpat create(@RequestBody CrtAnexosolcorrpat anexoSol, HttpServletResponse response,HttpServletRequest request) {
		   
		try {
			HttpSession session = request.getSession();
			
			anexoSol.setError(ConstantesBusiness.NO_ERRROR);
			UserSession user = getUsuarioFirmado(request);
			anexoSol.setCveUsuario(String.valueOf(user.getCurpUsuario()));
			anexoSol.setFecFechaRegistro(new Date());			
			
		
			Map parameters =   this.prorrogaServiceBean.agregar(anexoSol);
			if(user.isPatron()){
				parameters.put("internet","1");			
			}
			parameters.put("firmaElectronica",anexoSol.getFirmaElectronica());
			parameters.put("cadenaOriginal",anexoSol.getCadenaOriginal());
			parameters.put("selloIMSS", anexoSol.getSelloIMSS());
			
			String reporte=generaReporte(session, parameters);
			if(anexoSol.getFirmaElectronica()!=null && !anexoSol.getFirmaElectronica().equals("")){
				Archivo file=new Archivo();
				file.setNombre("solicitudProrroga.pdf");
				file.setBuffer(reporte);
				System.out.println("El id "+anexoSol.getFirmaElectronica());
				firmaElectronicaService.guardarArchivoFirmado(anexoSol.getFirmaElectronica(), file);
			}
		
			CrcTramiteMensajes ms= anexoSol.getMensaje();
			CrtTramitePresentado pres=new CrtTramitePresentado();
			if(ms!=null){
				pres.setCveMensaje(ms.getCveMensaje());	
			}
			
			pres.setCveSolcorr(Long.valueOf(anexoSol.getCveSolicitudCorr()));
			if(ms!=null)
			pres.setCveTramite(ms.getCveTramite());
			pres.setIdTramiteRefNotaria(anexoSol.getFirmaElectronica());
			//pres.setCveUsuarioCurp("CURPSEssion");
			pres.setFecFechaReg(Calendar.getInstance().getTime());
			pres.setDesRegPatronal(anexoSol.getRegistroPatronal());
			pres.setDesRazonSocial(anexoSol.getTxRazonSocial());
			pres.setUrlAcuseFirma(anexoSol.getUrlAcuseFirma());
			firmaElectronicaService.guardarTramitePresentado(pres);
			CrtSolicitudcorr sol=solicitudService.consultarPorId(anexoSol.getCveSolicitudCorr());

			firmaElectronicaService.agregaNuevoTramite(Long.valueOf(sol.getCveIdSolicitudBDTU()), anexoSol.getRegistroPatronal(), firmaElectronicaService.generaConfiguracionProrroga());
		
		} catch (FileNotFoundException e) {
			anexoSol.setError(e.getMessage());
			e.printStackTrace();
		} catch (JRException e) {
			anexoSol.setError(e.getMessage());
			e.printStackTrace();
		}		
	   return  anexoSol;
	}
	
	
	/*
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrtAnexosolcorrpat create(@RequestBody CrtAnexosolcorrpat anexoSol, HttpServletResponse response,HttpServletRequest request) {
		   
		try {
			HttpSession session = request.getSession();
			
			anexoSol.setError(ConstantesBusiness.NO_ERRROR);
			
			if(anexoSol.getFirmaElectronica()!=null && !anexoSol.getFirmaElectronica().equals(ConstantesBusiness.EMPTY)){
				
				try {
					
					if(anexoSol.getTipoCertificado()!=null){
						anexoSol = (CrtAnexosolcorrpat) firmaElectronicaService.validarCertificado(anexoSol);
						
						if(anexoSol.getFolioNotarial()!=null && anexoSol.getFolioNotarial()>0){
							
							UserSession user = getUsuarioFirmado(request);
							anexoSol.setCveUsuario(String.valueOf(user.getCveIdUsuario()));
							anexoSol.setFecFechaRegistro(new Date());
							
							Map parameters =   this.prorrogaServiceBean.agregar(anexoSol);
							if(user.isPatron()){
								parameters.put("internet","1");			
							}
							parameters.put("txFirmaElectronica",anexoSol.getFirmaElectronica());
							parameters.put("cadenaOriginal",anexoSol.getCadenaOriginal());
							generaReporte(session, parameters);
						}else{
							anexoSol.setError("Folio Notarial no adquirido, intente de nuevo");
						}
					}else{
						anexoSol.setError("El tipo de certificado (STA/IDSE) no ha sido asignado correctamente");
					}
					
					
					
				} catch (ServicioRemotoNoDisponibleException e) {
					anexoSol.setError(e.getMessage());
					e.printStackTrace();
				} catch (CertificadoInvalidoException e) {
					anexoSol.setError(e.getMessage());
					e.printStackTrace();
				}
				 
			}else{
				anexoSol.setError("La firma utilzada contine errores o no es válida, intente de nuevo");
			}
			
		} catch (FileNotFoundException e) {
			anexoSol.setError(e.getMessage());
			e.printStackTrace();
		} catch (JRException e) {
			anexoSol.setError(e.getMessage());
			e.printStackTrace();
		}
		
	   return  anexoSol;
	}
*/	
	
	
	@RequestMapping(value="/muestraReporte" , method=RequestMethod.POST)
	public String muestraReporte(CrtAnexosolcorrpat anexoSol, HttpServletResponse response,HttpServletRequest request) {
		
		return determinaURL(request, "reportes/muestraPDF", "reportes/muestraPDF_Patron");
	}


	
	
	public String generaReporte(HttpSession session,  Map parameters) throws FileNotFoundException, JRException{
		byte[] bytes = null;
        logger.debug(session.getServletContext().getRealPath("/WEB-INF/views/formatos/prorrogaAcuse.jasper"));
        logger.debug("*********RUTAIMAGEN******* " + session.getServletContext().getRealPath("/resources/images/logo_imss.JPG"));
        parameters.put("rutaImagen",session.getServletContext().getRealPath("/resources/images/") + "/");
        InputStream reportStream = new FileInputStream(session.getServletContext().getRealPath("/WEB-INF/views/formatos/prorrogaAcuse.jasper"));
        bytes = JasperRunManager.runReportToPdf(reportStream, parameters,new JREmptyDataSource());
        final String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode( bytes );
        logger.debug("Archivo generado!");
        session.setAttribute("documento", acusePdf);
        session.setAttribute("nombreArchivo", "prorroga.pdf");
        session.setAttribute("tipoDescarga", "muestra");
        return acusePdf;
	}
	
	@RequestMapping(value="/obtenerUsuarioSession.do", method=RequestMethod.POST)
	public @ResponseBody UserSession obtenerFechaServidor(HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		return user;
	}
}
