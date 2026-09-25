/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jaime Ramirez Niño
 *  @Proyecto: smod-web
 *  @Archivo: MateriaPrimaMaterialController.java
 *  @Paquete:mx.gob.imss.ctirss.smod.solicitud.controller
 *  @Fecha:30/11/2011
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.solicitud.firma;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.ModelAccessException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma.FirmaElectronicaBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



/**
 * @author Alfredo
 *
 */
@Controller
@RequestMapping(value="/solicitud/{idSolicitud}/firmarsolicitud")
public class FirmaSolicitudController extends AbstractController {
	
	@EJB
	private FirmaElectronicaBusinessRemote firmaElectronicaBusiness;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@EJB
	private ActividadEcServiceRemote clasificacionServiceBusiness;
	
	@Autowired
	private ManejadorReportesRemote manejadorReportes;
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	/**
	 * Metodo para obtener los datos capturados por el usuario para ser firmados.
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
    @RequestMapping(method=RequestMethod.GET)
	public String getDatosFirmaSolicitud (Model model, @PathVariable String idSolicitud, @ModelAttribute SujetoObligado sujetoObligado, HttpSession session) {

    	FirmaElectronica firma = new FirmaElectronica();
    	Solicitud solicitud = new Solicitud();
    	sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
    	log.debug("<OTIKA>"+sujetoObligado);
    	idSolicitud=""+session.getAttribute("idSolicitud");
    	log.debug("<OTIKA>El id de la solicitud es:"+idSolicitud);
    	try {
            // obtengo la solicitud con todos sus datos.
    		
        	firma.setCadenaOriginal(firmaElectronicaBusiness.obtenerCadenaAFirmar(solicitud,sujetoObligado));
        	firma.setIdSolicitud(new BigDecimal(idSolicitud));
        	                				        
        	model.addAttribute("firmaElectronicaModel", firma);
        	model.addAttribute("solicitudModel", solicitud);
        	
        	
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("<OTIKA>"+e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
		}
    	log.debug("<OTIKA>La cadena original es:"+firma.getCadenaOriginal());
    	session.setAttribute("firmaDigital",firma);
		return "captura.datos.firma";
	}
    /**
     * Metodo que despliega la cadena firmada junto con el pdf.
     * @param idSolicitud 
     * @return
     */
    
    @RequestMapping(value = "/procesodefirma", method = RequestMethod.POST)
	public ModelAndView procesodefirma(
			//@ModelAttribute FirmaElectronica firmaElectronicaModel, LoginSujetoObligado login,
			@ModelAttribute FirmaElectronica firmaElectronicaModel, SujetoObligado sujetoObligado,
			BindingResult result, Model model, HttpSession session) {

    	Errors errors = new BindException(firmaElectronicaModel, "model");
		Map <String, String> parametros = new HashMap<String, String> ();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("captura.datos.firma");
		Solicitud solicitud = new Solicitud();
		String nrpat = firmaElectronicaModel.getRegistroPatronal();		
		try {
			parametros.put("idSolicitud", firmaElectronicaModel.getIdSolicitud().toString());
			//TODO CAMBIAR CUANDO HAGAN PRUEBAS CON CERTIFICADOS VALIDOS
			firmaElectronicaModel.setRegistroPatronal(nrpat);
			//firmaElectronicaModel.setRegistroPatronal("A222222310");
			log.debug("##### VALIDANDO");
			validate(firmaElectronicaModel, errors);
			
			if( errors.hasErrors()){
				log.debug("##### DESPUES DEL VALIDATE TUVO ERRORES..");
				//return new ModelAndView("redirect:/solicitud/"+firmaElectronicaModel.getIdSolicitud()+"/firmarsolicitud/");
				mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
				return mav;
			}
					
			model.addAttribute("firmaElectronicaModel", firmaElectronicaModel);
			model.addAttribute("solicitudModel", solicitud);
			
			log.debug("(JARN) ...El proceso de firma ha comenzado.");
			solicitud=firmaElectronicaBusiness.firmarSolicitud(firmaElectronicaModel);
			log.debug("(JARN) ...El proceso de firma ha TERMINADO - AHORA REDIRECCIONA A --> redirect:/exitoSolicitudFirma?idSolicitud="
					+ firmaElectronicaModel.getIdSolicitud());						
			Solicitud solicitudActualizada= new Solicitud(); 
			solicitudActualizada.setSolicitudId( new Long( (String.valueOf(firmaElectronicaModel.getIdSolicitud()))));
			solicitudActualizada = solicitudServiceBusiness.consultarSolicitudPorId(new Long(firmaElectronicaModel.getIdSolicitud().toString()));
			solicitudActualizada.setCadenaOriginal(solicitud.getCadenaOriginal());
			solicitudActualizada.setSecuenciaDeNotaria(solicitud.getSecuenciaDeNotaria());
			solicitudActualizada.setSelloDigital(solicitud.getSelloDigital());
			model.addAttribute("firmaElectronicaModel", firmaElectronicaModel);
			model.addAttribute("solicitudModel", solicitudActualizada);			
			session.setAttribute("solicitudActualizada", solicitudActualizada);

		} catch (RecursoRemotoNoDisponibleException e) {
			log.error("##### ERROR RecursoRemotoNoDisponibleException: " + e.getMessage());
			e.printStackTrace();
			result.addError(new ObjectError("errorRegistroPatronal", "Ocurrio un error al firmar la solicitud."));
			mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
			return mav;
		} catch (ErrorEnInvocacionRecursoRemotoException e) {
			log.error("##### ERROR ErrorEnInvocacionRecursoRemotoException: " + e.getMessage());
			e.printStackTrace();
			result.addError(new ObjectError("errorRegistroPatronal", "Ocurrio un error al firmar la solicitud."));
			mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
			return mav;
		} catch (ModelAccessException e) {
			log.error("##### ERROR ModelAccessException: " + e.getMessage());
			e.printStackTrace();
			result.addError(new ObjectError("errorRegistroPatronal", "Ocurrio un error al firmar la solicitud."));
			mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
			return mav;
		} catch (RegistroPatronalInvalidoEnCertificadoException e) {
			log.error("##### ERROR RegistroPatronalInvalidoEnCertificadoException: " + e.getMessage());
			log.debug("##### OCURRIO LA EXCEPTION RegistroPatronalInvalidoEnCertificadoException");
			parametros.put("idSolicitud", firmaElectronicaModel.getIdSolicitud().toString());
			result.addError(new ObjectError("errorRegistroPatronal", "El registro patronal del certificado no corresponde con el de la solicitud."));
			result.reject("errorFirmaElectronica", "El registro patronal del certificado no corresponde con el de la solicitud.");
			mav.addObject("loginSujetoObligado", sujetoObligado);
			mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
			return mav;
		} catch (Exception e) {
			log.error("##### ERROR Exception: " + e.getMessage());
			log.debug("##### OCURRIO LA EXCEPTION RegistroPatronalInvalidoEnCertificadoException");
			e.printStackTrace();
			result.addError(new ObjectError("errorRegistroPatronal", "Ocurrio un error al firmar la solicitud."));
			mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
			return mav;
		}
		log.debug("##### TODO TERMINO BIEN");
		mav.setViewName("exito.Firma.Solicitud");
		mav.addObject("firmaElectronicaModel", firmaElectronicaModel);
		return mav;
	}
    
    /**
     * Metodo que despliega la pagina de éxito de para la firma y en la cual se descargara el PDF.
     * @param idSolicitud Identificador de la solicitud.
     * @return ModelAndView - Vista a desplegar junto con parametros ligados.
     */
    @RequestMapping(value="/exitoSolicitudFirma", method=RequestMethod.GET)
    public String exitoFirmaSolicitud( @RequestParam String idSolicitud) {
    	ModelAndView mv = new ModelAndView();
    	FirmaElectronica firmaElectronica = new FirmaElectronica();
    	
    	try {
    		log.debug("(JARN) ...El proceso de EXITO firma ha comenzado.");
    		mv.setViewName("exito.Firma.Solicitud");
    		mv.addObject("firmaElectronicaModel", firmaElectronica);
    		log.debug("(JARN) ...El proceso redirecciona a exitoFirmaSolicitud.");

		} catch (Exception e) { 
			e.printStackTrace();
		}
    	
		return "exito.Firma.Solicitud";
    }
    
    
    @RequestMapping(value = "/finalizarClasificacion", method = RequestMethod.GET)
	public String finalizarClasificacion(
			@RequestParam String idSolicitud,
			HttpServletResponse response, HttpSession session) {					
		log.debug("CLASIFICACION VIA FIRMA DIGITAL->[]  ");		
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");		
		try{
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(new Long(idSolicitud));
			clasificacionServiceBusiness.afectarClasificacionActividadEconomica(sujetoTramite, solicitud);		
			//session.setAttribute("sujetoTramite", null);
			//session.setAttribute("tipoTramite", null);																
			}catch(Exception e){							
				log.error(e);
			}				
		return "exito.Firma.Terminada";
	}
    
    @RequestMapping(value = "/presentarAviso", method = {RequestMethod.GET, RequestMethod.POST})
	public void presentarAcuse(HttpServletResponse response, HttpSession session){
    	log.debug("<OTIKA>Se presenta el aviso de moficación");
		Long idSolicitud = (Long) session.getAttribute("idSolicitud");
		SujetoObligado sujeto = (SujetoObligado) session.getAttribute("sujetoObligado");
		session.removeAttribute("idSolicitud");		
		sujeto=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Tramite tramite = solicitud.getTramites().get(0);
		Map<String, Object> parametros = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		parametros.put("P_CVE_TRAMITE", "D");
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		parametros.put("P_REGPATRONAL", sujeto.getNumeroRegistroPatronal());
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			Fisica fisica = sujeto.getFisica();
			String nombreCompleto = fisica.getNombre();
			nombreCompleto += " "+fisica.getPrimerApellido();
			nombreCompleto += " "+fisica.getSegundoApellido();
			parametros.put("P_RAZONSOCIAL", " ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
		}else{
			Moral moral = sujeto.getMoral();
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
		}
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			parametros.put("P_DES_DELEGAC", "");
			parametros.put("P_CVE_SUB_DEL", "");
			parametros.put("P_DES_SUB_DEL", "");
		}
		 try {
			String nombreArchivo = "";
			byte[] reporte = null;
			Solicitud solicitudActualizada=(Solicitud) session.getAttribute("solicitudActualizada");
			parametros.put("P_CAD_ORIG", solicitudActualizada.getCadenaOriginal());
			parametros.put("P_SELLO", solicitudActualizada.getSelloDigital());
			parametros.put("P_SEC_NOT", solicitudActualizada.getSecuenciaDeNotaria());
			log.debug("<OTIKA>CADENAORIGINAL:"+solicitudActualizada.getCadenaOriginal());
			log.debug("<OTIKA>SELLODIGITAL:"+solicitudActualizada.getSelloDigital());
			log.debug("<OTIKA>SECNOTARIA:"+solicitudActualizada.getSecuenciaDeNotaria());
			
			nombreArchivo = "AvisoModificacion_"+solicitud.getNoFolioSolicitud()+".pdf";
			List<SujetoObligado> sujetosObligados = new ArrayList<SujetoObligado>();
			sujetosObligados.add((SujetoObligado) session.getAttribute("sujetoObligado"));
			reporte = manejadorReportes.ejecutaAvisoDeModificacion(parametros,sujetosObligados);			
			if(reporte != null){
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR ");
			}else{
				log.debug("EL REPORTE ES NULO Y NO SE DEBE IMPRIMIR ");
			}
			response.setContentType("application/pdf"); 
			response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo); 
			response.getOutputStream().write(reporte);
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {  
            try { 
               if (response.getOutputStream() != null) 
            	   response.getOutputStream().close(); 
            } catch (IOException ioe) {  
                    ioe.printStackTrace(); 
            }

		}
	}
    
    
    public void validate(Object model, Errors errors) {
		
		FirmaElectronica firmaElectronica = (FirmaElectronica) model;
		
		if (firmaElectronica.getsPKCS7() == null || firmaElectronica.getsPKCS7().equals("")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sPKCS7", "field.required");
			log.debug("El PKCS7 no existe en el validate....");
		}

//		if (firmaElectronica.getRegistroPatronal()== null || firmaElectronica.getRegistroPatronal().equals(""))
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registroPatronal", "field.required");
	
		if (firmaElectronica.getIdSolicitud()== null || firmaElectronica.getIdSolicitud().equals("")){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idSolicitud", "field.required");
			log.debug("El id de la solicitud no existe en el validate....");
		}
	}
    
    
    
}