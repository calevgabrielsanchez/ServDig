package mx.gob.imss.ctirss.delta.gestion.solicitud.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.firma.DatosInsuficientesFirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CartaTerminosDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/firma-digital")
public class FirmaDigitalController extends AbstractController {

	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusiness;
	
	@RequestMapping(value = "/initTest", method = RequestMethod.GET)
	public String initFirmaDigitalTest() {
		return "initFirmaDigitalTest";
	}

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initFirmaDigital(Model model) {

		model.addAttribute("firmaElectronica", new FirmaElectronica());

		return "initFirmaDigital";
	}

	@RequestMapping(value = "/seleccionar-tipo-firma", method = RequestMethod.POST)
	public String seleccionarTipoFirma(@ModelAttribute FirmaElectronica firmaElectronica, 
			Model model) {
		
		boolean errorFirmaFiel = false;
		boolean errorFirmaIMSS = false;
		
		if (StringUtils.isEmpty(firmaElectronica.getRfc())
				&& StringUtils.isEmpty(firmaElectronica.getRegistroPatronal())) {
			DatosInsuficientesFirmaDigitalException exception = new DatosInsuficientesFirmaDigitalException();
			this.log.error(exception);
			firmaElectronica.setErrorFormGeneral(exception.getMessage());
			model.addAttribute("firmaElectronicaFIEL" , firmaElectronica);
		} else {
			if (StringUtils.isNotEmpty(firmaElectronica.getRfc())) {
				// Firma con FIEL
				if(!firmaElectronica.isFirmarArchivo() && StringUtils.isNotEmpty(firmaElectronica.getCadenaOriginal())){
					// Firmar cadena de texto
					this.log.debug("Se recibieron parametros para firmar cadena de texto con FIEL");
					firmaElectronica.setRfc(firmaElectronica.getRfc().toUpperCase());
					model.addAttribute("firmaElectronicaFIEL" , firmaElectronica);
				} else if (firmaElectronica.isFirmarArchivo()) {
					// Firmar archivo
					this.log.debug("Se recibieron parametros para firmar un archivo con FIEL");
					firmaElectronica.setRfc(firmaElectronica.getRfc().toUpperCase());
					firmaElectronica.setCadenaOriginal(null);
					model.addAttribute("firmaElectronicaFIEL" , firmaElectronica);
				}  else {
					errorFirmaFiel = true;
				}
			}
			
			if (StringUtils.isNotEmpty(firmaElectronica.getRegistroPatronal())) {
				// Firma con IMSS
				if (!firmaElectronica.isFirmarArchivo() && StringUtils.isNotEmpty(firmaElectronica.getCadenaOriginal())) {
					// Firmar cadena de texto
					this.log.debug("Se recibieron parametros para firma cadena de texto con IMSS");
					firmaElectronica.setRegistroPatronal(firmaElectronica.getRegistroPatronal().toUpperCase());
					model.addAttribute("firmaElectronicaIMSS" , firmaElectronica);
				} else if (firmaElectronica.isFirmarArchivo()) {
					// Firmar archivo
					this.log.debug("Se recibieron parametros para firmar un archivo con IMSS");
					firmaElectronica.setRegistroPatronal(firmaElectronica.getRegistroPatronal().toUpperCase());
					firmaElectronica.setCadenaOriginal(null);
					model.addAttribute("firmaElectronicaIMSS" , firmaElectronica);
				}  else {
					errorFirmaIMSS = true;
				}
			}
		}
		
		if (errorFirmaFiel || errorFirmaIMSS) {
			DatosInsuficientesFirmaDigitalException exception = new DatosInsuficientesFirmaDigitalException();
			this.log.error(exception);
			firmaElectronica.setErrorFormGeneral(exception.getMessage());
			model.addAttribute("firmaElectronicaFIEL" , firmaElectronica);
		}
				
		return "seleccionTipoFirma";
	}

	@RequestMapping(value = "/firma-FIEL", method = RequestMethod.POST)
	public String initFirmaConFIEL(@ModelAttribute FirmaElectronica firmaElectronica, 
			Model model) {

		this.log.info("Datos recibidos para firma electronica con FIEL");
		this.log.info("RFC -> " + firmaElectronica.getRfc());
		this.log.info("Contenido -> " + firmaElectronica.getCadenaOriginal());
		this.log.info("Firmar archivo -> " + firmaElectronica.isFirmarArchivo());
			
		model.addAttribute("firmaElectronicaFIEL" , firmaElectronica);

		return "initFirmaConFIEL";
	}

	@RequestMapping(value = "/firma-IMSS", method = RequestMethod.POST)
	public String initFirmaConIMSS(@ModelAttribute FirmaElectronica firmaElectronica, 
			Model model) {
		
		this.log.info("Datos recibidos para firma electronica IMSS");
		this.log.info("NRP -> " + firmaElectronica.getRegistroPatronal());
		this.log.info("Contenido -> " + firmaElectronica.getCadenaOriginal());
		this.log.info("Firmar archivo -> " + firmaElectronica.isFirmarArchivo());
				
		model.addAttribute("firmaElectronicaIMSS" , firmaElectronica);
		
		return "initFirmaConIMSS";
	}

	@RequestMapping(value = "/procesa-firma-FIEL", method = RequestMethod.POST)
	public Object firmaConFIEL(@ModelAttribute FirmaElectronica firmaElectronica, 
			HttpSession session, Model model) {

		validate(firmaElectronica);
		
		if (StringUtils.isEmpty(firmaElectronica.getErrorFormGeneral())) {
			
			try {
				firmaElectronica = firmaDigitalBusiness.procesarFirmaConFIEL(firmaElectronica);
				firmaElectronica.setCadenaOriginal(firmaElectronica.getCadenaOriginal().replace("<", "&lt;"));
			} catch (FirmaDigitalException e) {
				this.log.error(e);
				firmaElectronica.setErrorFormGeneral(e.getMessage());
			}			
		}
		
		model.addAttribute("firmaElectronica", firmaElectronica);
		session.setAttribute("firmaElectronica", firmaElectronica);
		
		return "resultadoFirmaDigital";
	}

	@RequestMapping(value = "/procesa-firma-IMSS", method = RequestMethod.POST)
	public String firmaConIMSS(@ModelAttribute FirmaElectronica firmaElectronica,
			HttpSession session, Model model) {
			
		validate(firmaElectronica);
		
		if (StringUtils.isEmpty(firmaElectronica.getErrorFormGeneral())) {
			try {
				firmaElectronica = firmaDigitalBusiness.procesarFirmaConIMSS(firmaElectronica);
			} catch (RegistroPatronalInvalidoEnCertificadoException e) {
				this.log.error(e);
				firmaElectronica.setErrorFormGeneral(e.getMessage());
			} catch (RecursoRemotoNoDisponibleException e) {
				this.log.error(e);
				firmaElectronica.setErrorFormGeneral(e.getMessage());
			} catch (ErrorEnInvocacionRecursoRemotoException e) {
				this.log.error(e);
				firmaElectronica.setErrorFormGeneral(e.getMessage());
			} catch (FirmaDigitalException e) {
				this.log.error(e);
				firmaElectronica.setErrorFormGeneral(e.getMessage());
			}
		} 
		
		firmaElectronica.setCadenaOriginal(firmaElectronica.getCadenaOriginal().replace("<", "&lt;"));
		
		model.addAttribute("firmaElectronica", firmaElectronica);
		session.setAttribute("firmaElectronica", firmaElectronica);
		
		return "resultadoFirmaDigital";
	}
	
	@RequestMapping(value = "/generar-JSON-firma-digital")
	public @ResponseBody FirmaElectronica generarJSONFirmaDigital (HttpSession session) {
		
		FirmaElectronica firmaElectronica = (FirmaElectronica) session.getAttribute("firmaElectronica");
		
		if(firmaElectronica == null ) {
			this.log.warn("No se genera el JSON ya que no se cuenta con el objeto requerido");
		} else {
			firmaElectronica.setCadenaOriginal(firmaElectronica.getCadenaOriginal().replace("&lt;", "<"));
		}
		
		return firmaElectronica;
	}
	
	@RequestMapping(value = "/limpiar-firma-digital")
	public @ResponseBody FirmaElectronica limpiarSessionFirmaDigital (HttpSession session) {
		
		session.removeAttribute("firmaElectronica");
		return null;
	}
	
	@RequestMapping(value = "/validar-fiel")
	public @ResponseBody Map<String, ? extends Object> validarCertificado(
			@RequestBody String pkcs7, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		FirmaElectronica firmaElectronica = new FirmaElectronica();
		firmaElectronica.setsPKCS7(pkcs7);
		
		try {
			firmaElectronica = this.firmaDigitalBusiness.validarCertificadoFIEL(firmaElectronica);
		} catch (FirmaDigitalException e) {
			this.log.error(e);
			firmaElectronica.setErrorFormGeneral(e.getMessage());
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		result.put("firmaElectronica", firmaElectronica);
		
		return result;
		
	}
	
	@RequestMapping("/pruebaCarta")
	public String pruebaCartaTerminos() {
		
		return "pruebaCartaTerminos";
	}
	
	@RequestMapping(value = "/cartaTerminos", method = RequestMethod.POST)
	public String generarDocumentoCartaTerminos(@ModelAttribute CartaTerminosDto cartaTerminos, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		List<CartaTerminosDto> data = new ArrayList<CartaTerminosDto>();
		cartaTerminos.setFecha(new Date());
		data.add(cartaTerminos);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data, false);
		modelMap.put("datosKey", dataSource);
		
		response.setHeader("Content-type", "application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=\"recibo.pdf\"");
        
		return "reporteCartaTerminosFiel";
	}
	
	private void validate(FirmaElectronica firmaElectronica) {
		
		if (firmaElectronica.getsPKCS7() == null || firmaElectronica.getsPKCS7().equals("")){
			firmaElectronica.setErrorFormGeneral("El campo PKCS7 es requerido");
		}
		
		// TODO: MASE - VALIDAR RFC Y NRP
	}

	@RequestMapping(value = "/validarTipoTramite/{idTipoTramite}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarTipoSolicitud(@PathVariable String idTipoTramite) {
		log.info("-->Tramites: " + idTipoTramite);
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Integer> tiposTramite = new ArrayList<Integer>();
		String[] tiposTramites = idTipoTramite.split(",");
		
		for(String cveTipoT: tiposTramites) {
			tiposTramite.add(Integer.valueOf(cveTipoT));
		}

		if(tiposTramite.contains(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo())){
			result.put("requiereCartaTerminos", 1);
			log.info("-->Requiere Carta de Terminos Alta Usuario");
		} else if (tiposTramite.contains(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())) {
			result.put("requiereCartaTerminos", 2);
			log.info("-->Requiere Carta de Terminos Representante Legal");
		} else {
			result.put("requiereCartaTerminos", 0);
			log.info("-->No Requiere Ninguna Carta de Terminos");
		}
		result.put("mensajeExito", "Se valido la solicitud exitosamente");

		return result;
	}

	@RequestMapping(value = "/mostrarCartaTerminos", method = RequestMethod.POST)
	public String mostrarCartaTerminos(
			@RequestParam("params") String firmaDigitalParams, Model model,
			HttpSession session, HttpServletRequest request) {
		model.addAttribute("firmaDigitalParams", firmaDigitalParams);
		return "wizardCartaTerminos";
	}

	@RequestMapping(value = "/mostrarCartaTerminosRepresentanteLegal", method = RequestMethod.POST)
	public String mostrarCartaTerminosRepresentanteLegal(
			@RequestParam("params") String firmaDigitalParams, Model model,
			HttpSession session, HttpServletRequest request) {
		model.addAttribute("firmaDigitalParams", firmaDigitalParams);
		return "wizardCartaTerminosRepresentante";
	}
}
