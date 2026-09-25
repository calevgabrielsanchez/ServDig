package mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoFormWrapper;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.web.validator.AdministracionMediosContactoValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/medios/fiscales/administrar")
public class AdministrarMediosContactoFiscalesController extends AbstractController {

	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;

	
	@RequestMapping(method = RequestMethod.GET)
	public String initAdmonMediosContactoFiscales(final HttpSession session) {

		return "initAdmonMediosContactoFiscales";
	}
	
	@RequestMapping(value = "/init-test", method = RequestMethod.GET)
	public String initAdmonMediosContactoParticularesTest(final HttpSession session) {

		return "admonMediosFiscalesTest";
	}
	
	@RequestMapping(value = "/init/{idPersona}", method = RequestMethod.POST)
	public String obtenerAdmonMediosContactoWrapper(final HttpServletRequest request,
			@PathVariable Long idPersona) {
		
		request.setAttribute("cveFisica", idPersona);
		
		return "admonMediosFiscalesWrapper";
	}
	
	@RequestMapping(value = "/init/retomar/{idSolicitud}/{idPersona}", method = RequestMethod.POST)
	public String retomarAdmonMediosContactoWrapper(final HttpServletRequest request,
			@PathVariable Long idSolicitud, @PathVariable Long idPersona) {
		
		request.setAttribute("idSolicitud", idSolicitud);
		request.setAttribute("cveFisica", idPersona);
		request.setAttribute("isRetomar", true);
		
		return "admonMediosFiscalesWrapper";
	}
	
	@RequestMapping(value = "/fisica/{idPersona}", method = RequestMethod.GET)
	public String obtenerMediosContactoFiscalesPersonaFisica(final HttpSession session,
			@PathVariable Long idPersona) {

		session.removeAttribute("mediosContactoFiscales");

		Fisica fisica = new Fisica();
		fisica.setCveFisica(idPersona);
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		fisica.setTipoPersona(tipoPersona);
		
		try {
			List<MedioContacto> mediosContactoFiscales = this.mediosContactoServiceBusiness
					.consultarMediosFiscalesPersona(fisica);
			
			/* Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y así poder mostrarlo en la vista
			 */
			for(MedioContacto medio : mediosContactoFiscales){
				if(medio instanceof CorreoElectronico){
					medio.setDesFormaContacto(((CorreoElectronico)medio).getCorreo());
				}else if(medio instanceof TelefonoMovil){
					medio.setDesFormaContacto(((TelefonoMovil)medio).getNumero());
				}else if(medio instanceof TelefonoFijo){
					medio.setDesFormaContacto(((TelefonoFijo)medio).getNumero());
				}else if(medio instanceof Facebook){
					medio.setDesFormaContacto(((Facebook)medio).getCuenta());
				}else if(medio instanceof Twitter){
					medio.setDesFormaContacto(((Twitter)medio).getCuenta());
				}
			}
						
			session.setAttribute("mediosContactoFiscales", mediosContactoFiscales);
		} catch (PersonaSinMedioDeContactoException e) {
			session.setAttribute("mediosContactoFiscales", new ArrayList<MedioContacto>());
			log.error(e);
		}

		return "initAdmonMediosContactoFiscales";
	}
	
	@RequestMapping(value = "/retomar/fisica/{idSolicitud}/{idPersona}", method = RequestMethod.GET)
	public String retomarMediosContactoFiscalesPersonaFisica(final HttpSession session,
			@PathVariable Long idSolicitud, @PathVariable Long idPersona) {

		session.removeAttribute("mediosContactoFiscales");
		
		try {
			List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness.retomarTramiteAdmonMedios(idSolicitud, true);
			List<MedioContacto> mediosContactoAux = null;
			
			
			/* Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y así poder mostrarlo en la vista
			 */
			if(!mediosContacto.isEmpty()){
				mediosContactoAux = new ArrayList<MedioContacto>();
				
				for(MedioContacto medio : mediosContacto){
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
						CorreoElectronico correo = new CorreoElectronico();
						correo.setClave(medio.getClave());
						correo.setDesFormaContacto(medio.getDesFormaContacto());
						correo.setCorreo(medio.getDesFormaContacto());
						correo.setTipoMedioContacto(medio.getTipoMedioContacto());
						correo.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(correo);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
						TelefonoMovil telMovil = new TelefonoMovil();
						telMovil.setClave(medio.getClave());
						telMovil.setDesFormaContacto(medio.getDesFormaContacto());
						telMovil.setNumero(medio.getDesFormaContacto());
						telMovil.setTipoMedioContacto(medio.getTipoMedioContacto());
						telMovil.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(telMovil);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
						TelefonoFijo telFijo = new TelefonoFijo();
						telFijo.setClave(medio.getClave());
						telFijo.setDesFormaContacto(medio.getDesFormaContacto());
						
						String[] telAux = medio.getDesFormaContacto().split("\\|");
						
						if(telAux.length != 3) {
							telFijo.setNumero(medio.getDesFormaContacto());
						} else {
							telFijo.setClaveLada(telAux[0]);
							telFijo.setNumero(telAux[1]);
							telFijo.setExtension(telAux[2]);
						}
						
						telFijo.setTipoMedioContacto(medio.getTipoMedioContacto());
						telFijo.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(telFijo);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_FACEBOOK)){
						Facebook facebook = new Facebook();
						facebook.setClave(medio.getClave());
						facebook.setDesFormaContacto(medio.getDesFormaContacto());
						facebook.setCuenta(medio.getDesFormaContacto());
						facebook.setTipoMedioContacto(medio.getTipoMedioContacto());
						facebook.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(facebook);
					} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TWITTER)){
						Twitter twitter = new Twitter();
						twitter.setClave(medio.getClave());
						twitter.setDesFormaContacto(medio.getDesFormaContacto());
						twitter.setCuenta(medio.getDesFormaContacto());
						twitter.setTipoMedioContacto(medio.getTipoMedioContacto());
						twitter.setEstadoAdministracionMedioContacto(medio.getEstadoAdministracionMedioContacto());
						mediosContactoAux.add(twitter);
					}
				}
			} else {
				/* Se buscan directamente a la base de datos los medios de la persona,
				 * ya que cuando ocurra este escenario es porque se creo una solicitud
				 * pero no se guardaron los cambios y la solicitud que se recupera
				 * está vacía
				 */
				
				Fisica fisica = new Fisica();
				fisica.setIdPersona(idPersona);
				
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
				fisica.setTipoPersona(tipoPersona);
				
				try {
					mediosContactoAux = this.mediosContactoServiceBusiness
							.consultarMediosFiscalesPersona(fisica);
					
					/* Se recorre la lista de medios de contacto, para settear el campo
					 * desFormaContacto y así poder mostrarlo en la vista
					 */
					for(MedioContacto medio : mediosContactoAux){
						if(medio instanceof CorreoElectronico){
							medio.setDesFormaContacto(((CorreoElectronico)medio).getCorreo());
						}else if(medio instanceof TelefonoMovil){
							medio.setDesFormaContacto(((TelefonoMovil)medio).getNumero());
						}else if(medio instanceof TelefonoFijo){
							medio.setDesFormaContacto(((TelefonoFijo)medio).getNumero());
						}else if(medio instanceof Facebook){
							medio.setDesFormaContacto(((Facebook)medio).getCuenta());
						}else if(medio instanceof Twitter){
							medio.setDesFormaContacto(((Twitter)medio).getCuenta());
						}
					}
					
					if(mediosContactoAux.isEmpty()) {
						session.setAttribute("mediosContactoFiscales", new ArrayList<MedioContacto>());
					}
				} catch (PersonaSinMedioDeContactoException e) {
					session.setAttribute("mediosContactoFiscales", new ArrayList<MedioContacto>());
					log.error(e);
				}
			}
			session.setAttribute("mediosContactoFiscales", mediosContactoAux);
		} catch (SolicitudNoEncontradaException e) {
			session.setAttribute("mediosContactoFiscales", new ArrayList<MedioContacto>());
			log.error(e);
		}

		return "initAdmonMediosContactoFiscales";
	}
	
	@RequestMapping(value = "/moral/init/{idPersona}", method = RequestMethod.POST)
	public String obtenerAdmonMediosContactoMoralWrapper(final HttpServletRequest request,
			@PathVariable Long idPersona) {
		
		request.setAttribute("isMoral", true);
		request.setAttribute("cveFisica", idPersona);
		
		return "admonMediosFiscalesWrapper";
	}
	
	@RequestMapping(value = "/moral/{idPersona}", method = RequestMethod.GET)
	public String obtenerMediosContactoFiscalesPersonaMoral(final HttpSession session,
			@PathVariable Long idPersona) {

		session.removeAttribute("mediosContactoFiscales");

		Moral moral = new Moral();
		moral.setCveMoral(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		moral.setTipoPersona(tipoPersona);

		try {
			List<MedioContacto> mediosContactoFiscales = this.mediosContactoServiceBusiness
					.consultarMediosFiscalesPersona(moral);
			
			/* Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y así poder mostrarlo en la vista
			 */
			for(MedioContacto medio : mediosContactoFiscales){
				if(medio instanceof CorreoElectronico){
					medio.setDesFormaContacto(((CorreoElectronico)medio).getCorreo());
				}else if(medio instanceof TelefonoMovil){
					medio.setDesFormaContacto(((TelefonoMovil)medio).getNumero());
				}else if(medio instanceof TelefonoFijo){
					medio.setDesFormaContacto(((TelefonoFijo)medio).getNumero());
				}else if(medio instanceof Facebook){
					medio.setDesFormaContacto(((Facebook)medio).getCuenta());
				}else if(medio instanceof Twitter){
					medio.setDesFormaContacto(((Twitter)medio).getCuenta());
				}
			}
						
			session.setAttribute("mediosContactoFiscales", mediosContactoFiscales);
		} catch (PersonaSinMedioDeContactoException e) {
			session.setAttribute("mediosContactoFiscales", new ArrayList<MedioContacto>());
			log.error(e);
		}

		return "initAdmonMediosContactoFiscales";
	}

	
	@RequestMapping(value = "/init-agregar", method = RequestMethod.GET)
	public String initAgregarMedioContactoFiscal(Model model) {

		MedioContactoFormWrapper medioContactoFormWrapper = new MedioContactoFormWrapper();
				
		model.addAttribute("medioContactoFormWrapper", medioContactoFormWrapper);
		
		return "admon.mediosContactoFiscales.captura";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> agregarMedioContactoFiscal(Model model,
			final HttpSession session, @RequestBody MedioContactoFormWrapper form, 
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");
		
		MedioContacto medioContactoFiscal = null;
		
		//Se valida que la persona no cuente con medios fiscales repetidos
		if (mediosContactoFiscales != null) {
			for (MedioContacto medio : mediosContactoFiscales) {
				if (medio.getTipoMedioContacto().getIdTipoMedioContacto().intValue() == 
						form.getTipoMedioContacto().getIdTipoMedioContacto().intValue()
						&& ( medio.getEstadoAdministracionMedioContacto() == null || (medio.getEstadoAdministracionMedioContacto() != null && medio
								.getEstadoAdministracionMedioContacto()
								.getClave() != EstadoAdministracionEnum.ELIMINADO.getClave()))) {
					
					RegistrarMedioContactoException e = new RegistrarMedioContactoException(
							"La persona ya cuenta con el medio de contacto que se est\u00E1 registrando.");
					
					log.error(e);
					
					form = new MedioContactoFormWrapper();
					form.setErrorFormGeneral(e.getMessage());
					this.procesarErrorDeNegocio(e, result, response);
					
					return result;
				}
			}
		} else {
			//Se ignora la validacion si no se selecciono ningun documento
			return result;
		}
		
		//Se valida el medio de contacto capturado		
		new AdministracionMediosContactoValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
			form.getCorreoElectronico().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContactoFiscal = form.getCorreoElectronico();
			medioContactoFiscal.setDesFormaContacto(form.getCorreoElectronico().getCorreo());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
			form.getTelefonoFijo().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContactoFiscal = form.getTelefonoFijo();
			medioContactoFiscal.setDesFormaContacto(form.getTelefonoFijo().getNumero());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
			form.getTelefonoMovil().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContactoFiscal = form.getTelefonoMovil();
			medioContactoFiscal.setDesFormaContacto(form.getTelefonoMovil().getNumero());
		}
				
		medioContactoFiscal.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
		mediosContactoFiscales.add(medioContactoFiscal);
		
		result.put("medioContactoFormWrapper", form);

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modificar/{indexMedioContacto}", method = RequestMethod.GET)
	public String modificiarMedioContactoFiscal(final HttpSession session,
			HttpServletRequest request, @PathVariable Integer indexMedioContacto,
			Model model) {

		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");
		
		MedioContacto medioContactoFiscal = mediosContactoFiscales.get(indexMedioContacto);

		MedioContactoFormWrapper formWrapper = new MedioContactoFormWrapper();
		
		if(medioContactoFiscal instanceof CorreoElectronico){
			formWrapper.setCorreoElectronico((CorreoElectronico) medioContactoFiscal);
		}else if(medioContactoFiscal instanceof TelefonoFijo){
			formWrapper.setTelefonoFijo((TelefonoFijo) medioContactoFiscal);
		}else if(medioContactoFiscal instanceof TelefonoMovil){
			formWrapper.setTelefonoMovil((TelefonoMovil) medioContactoFiscal);
		}
		
		formWrapper.setTipoMedioContacto(medioContactoFiscal.getTipoMedioContacto());
		
		request.setAttribute("indexMedioContacto", indexMedioContacto);
		model.addAttribute("medioContactoFormWrapper", formWrapper);

		return "admon.mediosContactoFiscales.captura";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmar-modificacion/{indexMedioContacto}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> confirmarModificacionMedioFiscal(final HttpSession session,
			@RequestBody MedioContactoFormWrapper form, HttpServletResponse response,
			@PathVariable Integer indexMedioContacto) {

		Map<String, Object> result = new HashMap<String, Object>();
    	Errors errors = new BindException(form, "model");
    	
		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");
		
		MedioContacto medioContacto = null;
				
		//Se valida el medio de contacto capturado		
		new AdministracionMediosContactoValidator().validate(form, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
			form.getCorreoElectronico().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getCorreoElectronico();
			medioContacto.setDesFormaContacto(form.getCorreoElectronico().getCorreo());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
			form.getTelefonoFijo().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoFijo();
			medioContacto.setDesFormaContacto(form.getTelefonoFijo().getNumero());
		}else if(form.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
			form.getTelefonoMovil().setTipoMedioContacto(form.getTipoMedioContacto());
			medioContacto = form.getTelefonoMovil();
			medioContacto.setDesFormaContacto(form.getTelefonoMovil().getNumero());
		}

		medioContacto.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);

		mediosContactoFiscales.set(indexMedioContacto.intValue(), medioContacto);
		
		result.put("medioContactoFormWrapper", form);

		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminar/{indexMedioContacto}", method = RequestMethod.GET)
	public String eliminarMedioContactoFiscal(final HttpSession session,
			@PathVariable Integer indexMedioContacto, Model model) {

		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");

		MedioContacto medio = mediosContactoFiscales.get(indexMedioContacto.intValue()); 
		
		medio.setEstadoAdministracionAnteriorMedioContacto(medio.getEstadoAdministracionMedioContacto());
		medio.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);

		return "initAdmonMediosContactoFiscales";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtener-medios", method = RequestMethod.POST)
	public @ResponseBody List<MedioContacto> obtenerListaMediosContactoAdministrados(
			final HttpSession session) {

		List<MedioContacto> mediosContactoAux = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");
		List<MedioContacto> mediosContactoFiscales = null;
		
		if(mediosContactoAux != null){
			mediosContactoFiscales = new ArrayList<MedioContacto>();
			
			// Se transforman las especificaciones de medio de contacto a la generalidad de medio de contacto
			MedioContacto medio = null;
			for(MedioContacto medioContacto : mediosContactoAux){
				medio = new MedioContacto();
				medio.setClave(medioContacto.getClave());
				medio.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
				medio.setEstadoAdministracionMedioContacto(medioContacto.getEstadoAdministracionMedioContacto());
				
				if(medioContacto instanceof CorreoElectronico){
					medio.setDesFormaContacto(((CorreoElectronico) medioContacto).getCorreo());
				}else if(medioContacto instanceof TelefonoFijo){
					StringBuffer numero = new StringBuffer();
					numero.append(((TelefonoFijo) medioContacto).getClaveLada()).append("|");
					numero.append(((TelefonoFijo) medioContacto).getNumero()).append("|");
					numero.append(((TelefonoFijo) medioContacto).getExtension());
					medio.setDesFormaContacto(numero.toString());
				}else if(medioContacto instanceof TelefonoMovil){
					medio.setDesFormaContacto(((TelefonoMovil) medioContacto).getNumero());
				}
				
				mediosContactoFiscales.add(medio);
			}
		}

		return mediosContactoFiscales;
	}
	
	@RequestMapping(value = "/limpiar-medios", method = RequestMethod.POST)
	public @ResponseBody MedioContacto limpiarListaMediosFiscalesAdministrados(
			final HttpSession session) {
	
		session.removeAttribute("mediosContactoFiscales");
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deshacer-eliminar/{indexMedioContacto}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> deshacerEliminarMedioContactoFiscal(final HttpSession session,
			@PathVariable Integer indexMedioContacto, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		MedioContactoFormWrapper form = null;
    	
		List<MedioContacto> mediosContactoFiscales = (List<MedioContacto>) session
				.getAttribute("mediosContactoFiscales");
		
		MedioContacto medioContactoFiscal = null;
		
		//Se valida que la persona no cuente con medios fiscales repetidos
		if (mediosContactoFiscales != null) {
			
			medioContactoFiscal = mediosContactoFiscales.get(indexMedioContacto.intValue());
			
			for (MedioContacto medio : mediosContactoFiscales) {
				if (medio.getTipoMedioContacto().getIdTipoMedioContacto().intValue() == 
						medioContactoFiscal.getTipoMedioContacto().getIdTipoMedioContacto().intValue()
						&& ( medio.getEstadoAdministracionMedioContacto() == null || (medio.getEstadoAdministracionMedioContacto() != null && medio
								.getEstadoAdministracionMedioContacto()
								.getClave() != EstadoAdministracionEnum.ELIMINADO.getClave()))) {
					
					RegistrarMedioContactoException e = new RegistrarMedioContactoException(
							"La persona ya cuenta con el medio de contacto que se est\u00E1 restaurando.");
					log.error(e);
					form = new MedioContactoFormWrapper();
					form.setErrorFormGeneral(e.getMessage());
					this.procesarErrorDeNegocio(e, result, response);
					
					return result;
				}
			}
		} else {
			//Se ignora la validacion si no se selecciono ningun documento
			return result;
		}
		
		medioContactoFiscal.setEstadoAdministracionMedioContacto(medioContactoFiscal.getEstadoAdministracionAnteriorMedioContacto());
		
		return result;
	} 
}