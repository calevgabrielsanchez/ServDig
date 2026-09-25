package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ValidacionIdentidadTramite;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.portal.web.model.DatosEntradaDetalleIdentidadSujeto;
import mx.gob.imss.ctirss.delta.portal.web.model.TipoSujetoEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/detalle")
public class DetalleIdentidadSujetoController extends AbstractController {

	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired 
	private DomicilioServiceBusinessRemote domicilios;

	@RequestMapping(value = "/identidad", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> obtenerJsonDetalleIdentidad(
			@RequestBody DatosEntradaDetalleIdentidadSujeto datosEntrada,
			Model model, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		Long idPersona = new Long(datosEntrada.getIdPersona());
		Long idTipoPersona = new Long(datosEntrada.getIdTipoPersona());
		
		Persona persona = null;
		TipoPersona tipoPersona = new TipoPersona();
				
		// Datos Generales de la Persona
		if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {

			Fisica fisica = null;
			try {
				fisica = this.serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
			} catch (PersonaFisicaNoEncontradaException e) {
				this.log.warn("La persona no cuenta con caracter fiscal:"
						+ e.getMessage());
			}

			try {
				String nss = personaBusiness.obtenerNssPersona(fisica
						.getIdPersona());

				fisica.setNss(nss);
				
				this.log.debug("La persona " + fisica.getIdPersona()
						+ " ya cuenta con NSS [" + nss + "]");

			} catch (PersonaConVariosNSSException e) {
				this.log.warn("La persona cuenta con varios NSS: "
						+ e.getMessage());
			} catch (PersonaSinNSSException e) {
				this.log.warn(e);
			}
			
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			persona = fisica;
			
		} else {
			Moral moral = personaMoralBusiness.getPersonaMoral(idPersona);
			moral.setCveMoral(moral.getIdPersona());
			
			persona = moral;
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		}

		persona.setTipoPersona(tipoPersona);
		
		ValidacionIdentidadTramite validacionIdentidad = this.personaBusiness
				.validarIdentidad(persona, datosEntrada.getIdTramite());
		
		response.put("persona", persona);
		response.put("validacionIdentidad", validacionIdentidad);

		return response;

	}
	
	@RequestMapping(value = "/identidad/mostrar", method = RequestMethod.POST)
	public String obtenerDetalleIdentidad(
			@RequestBody DatosEntradaDetalleIdentidadSujeto datosEntrada,
			Model model, HttpServletRequest request) {

		Long idPersona = new Long(datosEntrada.getIdPersona());
		Long idTipoPersona = new Long(datosEntrada.getIdTipoPersona());
		
		// Datos de persona
		Persona persona = null;

		
		// Datos Generales de la Persona
		if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			Fisica fisica = null;
			
			try {
				fisica = this.serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
			} catch (PersonaFisicaNoEncontradaException e) {
				this.log.warn("La persona no cuenta con caracter fiscal:"
						+ e.getMessage());
			}

			model.addAttribute("fisica", fisica);
			
			try {
				String nss = personaBusiness.obtenerNssPersona(fisica
						.getIdPersona());

				this.log.debug("La persona " + fisica.getIdPersona()
						+ " ya cuenta con NSS [" + nss + "]");
				model.addAttribute("NSS_RECUPERADO", nss);

			} catch (PersonaConVariosNSSException e) {
				this.log.warn("La persona cuenta con varios NSS: "
						+ e.getMessage());
			} catch (PersonaSinNSSException e) {
				this.log.warn(e);
			}
			
			// Domicilios
			List<Domicilio> domicilios = fisica.getDomicilios();
			
			for (Domicilio domicilio : domicilios) {
				if (domicilio.getDicTipoDomicilio().getClave().intValue() == Long
						.valueOf(TipoDomicilioEnum.PARTICULAR.getId()).intValue()) {
					request.setAttribute("domicilio", domicilio);
					break;
				}
			}

			request.setAttribute("mediosContacto", formatearMedios(fisica.getMediosContacto()));

			persona = fisica;
		
		} else {
			Moral moral = personaMoralBusiness.getPersonaMoral(idPersona);
			moral.setCveMoral(moral.getIdPersona());
			model.addAttribute("moral", moral);
			try {
				DomicilioFiscal domFiscalMoral = this.domicilios.consultarDomicilioFiscalPersona(moral);
				if(domFiscalMoral!=null)
					moral.setDomicilioFiscal(domFiscalMoral);
			} catch (DomicilioNoLocalizadoException ex) {
				this.log.warn(" -- La persona moral no cuenta con domicilio fiscal: "+ex.getMessage());
			}
			
			try {
				List<MedioContacto> listMedioContactoFiscal = this.mediosContactoServiceBusiness.consultarMediosFiscalesPersona(moral);
				if (listMedioContactoFiscal != null) 
					moral.setMediosContactoFiscales(listMedioContactoFiscal);
			} catch (PersonaSinMedioDeContactoException ex) {
				this.log.warn(" -- Persona Moral no cuenta con medios fiscales:::::: "+ex.getMessage());
			}
			persona = moral;
		}
		
		
		if (persona.getDomicilioFiscal() != null) {
			model.addAttribute("domFiscal", persona.getDomicilioFiscal());
		}
		
		request.setAttribute("mediosContactoFiscales", formatearMedios(persona.getMediosContactoFiscales()));
		
		return "detalleIdentidad";

	}
	
	
	
	/**
	 * Obtiene el detalle de un sujeto, recibe el idSujeto que dependiendo del
	 * caso puede ser un NSS o un NRP y el idTipoSujeto indica qué es, es decir,
	 * un patrón, asegurado.
	 * 
	 * @param idSujeto
	 * @param idTipoSujeto
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sujeto", method = RequestMethod.POST)
	public String obtenerDetalleSujeto(
			@RequestBody DatosEntradaDetalleIdentidadSujeto datosEntrada, Model model,
			HttpServletRequest request) throws GestionPatronalBusinessException {

		String vista = null;

		if (datosEntrada.getIdTipoSujeto() == TipoSujetoEnum.PATRON.getId()) {
			vista = obtenerDetalleNrp(datosEntrada.getNrp(), model);
		} else if (datosEntrada.getIdTipoSujeto() == TipoSujetoEnum.ASEGURADO.getId()) {
			vista = obtenerDetalleNss(datosEntrada.getNss(),
					datosEntrada.getIdPersona(),
					datosEntrada.getIdAsignacionNss(), model, request);
		} else if(datosEntrada.getIdPersona()!=null && datosEntrada.getIdTipoPersona()!=0)
			vista = obtenerListaNrp(datosEntrada.getIdPersona(), datosEntrada.getIdTipoPersona(), model, request);

		return vista;
	}
	
	
	private List<MedioContacto> formatearMedios(List<MedioContacto> mediosContacto) {
		
		for (MedioContacto medio : mediosContacto) {
			if (medio instanceof CorreoElectronico) {
				medio.setDesFormaContacto(((CorreoElectronico) medio)
						.getCorreo());
			} else if (medio instanceof TelefonoMovil) {
				medio.setDesFormaContacto(((TelefonoMovil) medio)
						.getNumero());
			} else if (medio instanceof TelefonoFijo) {
				StringBuffer numTelFijo = new StringBuffer();
				TelefonoFijo telFijo = (TelefonoFijo) medio;

				if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
					numTelFijo.append(telFijo.getClaveLada())
							.append(" ");
				}

				if (StringUtils.isNotBlank(telFijo.getNumero())) {
					numTelFijo.append(telFijo.getNumero()).append(
							" ");
				}

				if (StringUtils.isNotBlank(telFijo.getExtension())) {
					numTelFijo.append(telFijo.getExtension());
				}

				medio.setDesFormaContacto(numTelFijo.toString());
			} else if (medio instanceof Facebook) {
				medio.setDesFormaContacto(((Facebook) medio)
						.getCuenta());
			} else if (medio instanceof Twitter) {
				medio.setDesFormaContacto(((Twitter) medio)
						.getCuenta());
			}
		}
		
		return mediosContacto;
	}
	
	private String obtenerDetalleNrp(String numeroRegistroPatronal, Model model) {

		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = this.sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		CentroTrabajo centroTrabajo = patron.getCntroTrabajo();
		List<MedioContacto> mediosCentroTrabajo = centroTrabajo != null ? centroTrabajo
				.getMediosContacto() : null;

		if (centroTrabajo == null || centroTrabajo.getMediosContacto() == null
				|| centroTrabajo.getMediosContacto().isEmpty()) {
			if (centroTrabajo == null) {
				centroTrabajo = new CentroTrabajo();
			}
			Long idSujetoObligado = patron.getCveIdSujetoObligado();
			centroTrabajo.setCveIdPatronSujetoObligado(idSujetoObligado);

			mediosCentroTrabajo = mediosContactoServiceBusiness
					.consultarMedioContactoDeCentroTrabajo(centroTrabajo);
		}

		if (mediosCentroTrabajo != null) {
			for (MedioContacto medio : mediosCentroTrabajo) {
				if (medio
						.getTipoMedioContacto()
						.getIdTipoMedioContacto()
						.equals(TipoContactoEnum.TELEFONO_FIJO.getCodigo()
								.longValue())) {
					String descFormaContacto = medio.getDesFormaContacto();
					String telefonoFormateado = parseTelefono(descFormaContacto);
					medio.setDesFormaContacto(telefonoFormateado);
				}
				if (medio
						.getTipoMedioContacto()
						.getIdTipoMedioContacto()
						.equals(TipoContactoEnum.TELEFONO_MOVIL.getCodigo()
								.longValue())) {
					String descFormaContacto = medio.getDesFormaContacto();
					medio.setDesFormaContacto(descFormaContacto.replaceAll(
							"\\|", " "));
				}

				log.debug("Medio Titulo: "
						+ medio.getTipoMedioContacto().getDescripcion());
				log.debug("Medio Desc: " + medio.getDesFormaContacto());
			}
		}

		centroTrabajo.setMediosContacto(mediosCentroTrabajo);

		model.addAttribute("patron", patron);
		model.addAttribute("sujetoObligado", patron);

		return "detalleNRP";

	}
	
	private String obtenerDetalleNss(String nss, Long idPersona,
			Long idAsignacionNss, Model model, HttpServletRequest request) {

		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;

		try {
			GrupoFamiliar derechohabiente = this.derechohabienteServiceRemote
					.detalleDerechohabienteGrupoFamiliar(idAsignacionNss,
							idPersona);
			cabezaGrupoFamiliar = this.grupoFamiliarServiceRemote
					.cabezaGrupoFamiliar(idAsignacionNss);
			
			/*
			 * Agregamos al modelo los datos del derechohabiente y el domicilio
			 * particular
			 */
			model.addAttribute("derechohabiente", derechohabiente);
			model.addAttribute("patronImss", cabezaGrupoFamiliar
					.getPatronImss().equals(1));
			model.addAttribute("isAsegurado", false);
		} catch (DerechohabientesBusinessException e) {
			log.error("ocurrio un erro de derechohabientes", e);
			request.setAttribute("error", e.getSituacion());

		} catch (Exception e) {
			log.error("ocurrio un erro no cachado", e);
			request.setAttribute("error", e.getMessage());
		}

		return "detalleNSS";

	}
	
	private String parseTelefono(String telefono) {
		if (telefono == null || (telefono != null && telefono.equals(""))
				|| (telefono != null && telefono.equals("||")))
			return "";

		String[] telefonoSeccion = telefono.split("\\|");

		StringBuffer telefonoFormateado = new StringBuffer();
		telefonoFormateado.append("(").append(telefonoSeccion[0]).append(")");
		telefonoFormateado.append(" ").append(telefonoSeccion[1]);
		if (telefonoSeccion.length > 2)
			telefonoFormateado.append("-").append(telefonoSeccion[2]);

		return telefonoFormateado.toString();
	}
	private String obtenerListaNrp(Long idPersona,
			int idTipoPersona, Model model, HttpServletRequest request) throws GestionPatronalBusinessException{
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		
		TipoPersona tp = new TipoPersona();
		tp.setIdTipoPersona(Long.valueOf(idTipoPersona));
		persona.setTipoPersona(tp);
		
		try{
		List<SujetoObligado> lista = 
				this.sujetoObligadoServiceBusiness.listarRegistrosPatronalesPorPersonaDatosBasicosPatron(persona);
		model.addAttribute("patronesAsociados", lista);
		model.addAttribute("totalNRP", lista.size());
		}catch(GestionPatronalBusinessException ex){
			this.log.info(" -- No se puede procesar el detalle del sujeto:::  "+ex.getMessage());
			model.addAttribute("errorListaNRP", "Datos insuficientes para mostrar el detalle.");
		}
		return "listaNrp";
	}
}
