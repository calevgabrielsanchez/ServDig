package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.portal.web.model.DatosEntradaDetalleIdentidadSujeto;
import mx.gob.imss.ctirss.delta.portal.web.model.FiltrosBusqueda;
import mx.gob.imss.ctirss.delta.portal.web.model.TipoFiltroEnum;
import mx.gob.imss.ctirss.delta.portal.web.model.TipoSujetoEnum;
import mx.gob.imss.ctirss.delta.portal.web.validator.BusquedaValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/busqueda")
public class BusquedaController extends AbstractController {

	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	@RequestMapping(value = "/filtro", method = RequestMethod.POST)
	public String generarTemplateBusqueda(Model model,
			@RequestParam("ttc") String tipoTramiteCifrado,
			@RequestParam("tf") int tipoFiltro,
			HttpServletRequest request) {

		try {
			Long idTipoTramite = Long.valueOf(Base64Cipher
					.descrifrar(tipoTramiteCifrado));

			this.log.info("Tramite recibido -> " + idTipoTramite);
			
			FiltrosBusqueda filtros = new FiltrosBusqueda();
			filtros.setTipoFiltro(tipoFiltro);
			model.addAttribute("filtros", filtros);
		} catch (NumberFormatException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		} catch (IOException e) {
			this.log.error(e);
		}

		return "formularioBusqueda";
	}
	
	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> validarDatosBusqueda(Model model, 
			@RequestBody FiltrosBusqueda filtro, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Errors errors = new BindException(filtro, "model");
		new BusquedaValidator().validate(filtro, errors);

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
		} else {
			
			String tipoBusqueda = null;
			String tipoPersona = null;
			String valorBuscado = null;
			
			if (filtro.getTipoFiltro() == TipoFiltroEnum.CURP.getId()) {
				tipoBusqueda = "CURP";
				tipoPersona = Long.toString(TipoPersonaEnum.FISICA.getId());
				valorBuscado = filtro.getCurp();
			} else if (filtro.getTipoFiltro() == TipoFiltroEnum.RFC_FISICA.getId()) {
				tipoBusqueda = "RFC";
				tipoPersona = Long.toString(TipoPersonaEnum.FISICA.getId());
				valorBuscado = filtro.getRfc();
			} else if (filtro.getTipoFiltro() == TipoFiltroEnum.RFC_MORAL.getId()) {
				tipoBusqueda = "RFC";
				tipoPersona = Long.toString(TipoPersonaEnum.MORAL.getId());
				valorBuscado = filtro.getRfc();
			}
			
			result.put("tB", tipoBusqueda);
			result.put("tP", tipoPersona);
			result.put("vB", valorBuscado);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/sujeto", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> buscarSujeto(Model model, 
			@RequestBody FiltrosBusqueda filtro, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Errors errors = new BindException(filtro, "model");
		new BusquedaValidator().validate(filtro, errors);

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
		} else {
			DatosEntradaDetalleIdentidadSujeto datosEntrada = new DatosEntradaDetalleIdentidadSujeto();
	
			if (filtro.getTipoFiltro() == TipoFiltroEnum.NSS.getId()
					&& StringUtils.isNotBlank(filtro.getNss())) {
				AsignacionNSS asignacionNSS = this.serviceBusiness.obtenerAsignacionNss(filtro.getNss());
				
				if (asignacionNSS != null) {
					datosEntrada.setIdPersona(asignacionNSS.getIdPersona());
					datosEntrada.setIdTipoPersona(Long.valueOf(
							TipoPersonaEnum.FISICA.getId()).intValue());
					
					datosEntrada.setNss(filtro.getNss());
					datosEntrada.setIdAsignacionNss(asignacionNSS.getIdAsignacionNSS());
					datosEntrada.setIdTipoSujeto(TipoSujetoEnum.ASEGURADO.getId());
				} else {
					this.procesarErrorDeNegocio(
							new AsignacionNSSNoLocalizadoException(),
							result, response);
				}
			} else if (filtro.getTipoFiltro() == TipoFiltroEnum.NRP.getId() 
					&& StringUtils.isNotBlank(filtro.getNrp())) {
				SujetoObligado so = new SujetoObligado();
				so.setNumeroRegistroPatronal(filtro.getNrp());
				
				so = this.sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(so);
				
				if (so != null) {
					if (so.getFisica() != null) {
						datosEntrada.setIdPersona(so.getFisica().getIdPersona());
						datosEntrada.setIdTipoPersona(Long.valueOf(
								TipoPersonaEnum.FISICA.getId()).intValue());
					} else if (so.getMoral() != null) {
						datosEntrada.setIdPersona(so.getMoral().getCveMoral());
						datosEntrada.setIdTipoPersona(Long.valueOf(
								TipoPersonaEnum.MORAL.getId()).intValue());
					}
				} else {
					this.procesarErrorDeNegocio(
							new GestionPatronalBusinessException(
									"Número de Registro Patronal no encontrado"),
							result, response);
				}
				
				datosEntrada.setNrp(filtro.getNrp());
				datosEntrada.setIdTipoSujeto(TipoSujetoEnum.PATRON.getId());
			}
			
			result.put("datosEntrada", datosEntrada);
		}
		
		return result;
	}
}