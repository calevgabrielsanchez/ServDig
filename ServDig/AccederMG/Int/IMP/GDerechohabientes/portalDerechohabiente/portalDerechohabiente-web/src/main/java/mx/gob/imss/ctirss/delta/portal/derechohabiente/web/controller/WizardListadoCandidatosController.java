package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.bean.dto.CorreccionDatosDerechohabienteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( value = "/wizard/listado/")
public class WizardListadoCandidatosController extends AbstractController {

	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	
	@Autowired
	private BajaDerechohabienteServiceRemote bajaDerechohabienteServiceRemote;
	
	@Autowired
	private ProrrogaServiceRemote prorrogaService;
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired
	private CambioClinicaServiceRemote cambioClinicaServiceRemote;
	
	@RequestMapping( value = "/candidatos/{idAsignacionNss}/{nss}/{tipoTramite}/{seleccionMultiple}")
	public String initWizardBajaDerechohabiente(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss, @PathVariable String nss, @PathVariable Long tipoTramite, @PathVariable Long seleccionMultiple) {
		
		
		List<GrupoFamiliar> candidatos = null;
		Long origenSolicitud = OrigenSolicitudEnum.INTERNET.getId();
		String descTipoTramite = "";
		
		try {
			if(tipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue())) {
				descTipoTramite= "BAJA DE DERECHOHABIENTE POR DEFUNCI&Oacute;N".toLowerCase();
				candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaDefuncion(idAsignacionNss, origenSolicitud, null);
			} else if(tipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue())) {
				descTipoTramite= "BAJA DE DERECHOHABIENTE POR DIVORCIO".toLowerCase();
				candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaDivorcio(idAsignacionNss, origenSolicitud, null);
			} else if(tipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue())) {
				descTipoTramite= "BAJA DE DERECHOHABIENTE POR T&Eacute;RMINO DE CONCUBINATO".toLowerCase();
				candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaConcubinato(idAsignacionNss, origenSolicitud, null);
			} else if(tipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue())) {
				descTipoTramite= "BAJA DE DERECHOHABIENTE POR T&Eacute;RMINO DE CONVIVENCIA".toLowerCase();
				candidatos = bajaDerechohabienteServiceRemote.findGrupoFamiliarBajaConvivencia(idAsignacionNss, origenSolicitud, null);
			} else if( tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue() ) || 
					   tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo().longValue() ) ||
					   tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue() ) 
					){
				
				if( tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo().longValue() ) ){
					descTipoTramite= "PR&Oacute;RROGA DE DERECHOHABIENTE POR ESTUDIOS";
				}
				else if( tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo().longValue() ) ){
					descTipoTramite= "PR&Oacute;RROGA DE DERECHOHABIENTE POR INCAPACIDAD F&Iacute;SICA O PSIQUICA";
				}
				else if( tipoTramite.equals( TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo().longValue() ) ){
					descTipoTramite= "PR&Oacute;RROGA DE DERECHOHABIENTE POR INCAPACIDAD OBST&Eacute;TRICA";
				}
				
				Usuario usuario = this.getUsuarioSesion(this
						.procesarUsuarioSSO(request));
				candidatos = prorrogaService.getCandidatosProrroga(null, null, tipoTramite, idAsignacionNss, usuario, true);
			} else if(tipoTramite.equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo().longValue())) {
				descTipoTramite="Modificacion de datos personales".toLowerCase();
				log.error(">>> " + descTipoTramite + ", tipoTramite: " + tipoTramite);
				candidatos = correccionDerechohabienteServiceRemote.findGrupoFamiliarCorreccionDatos(idAsignacionNss, origenSolicitud, this.getUsuarioSesion(this.procesarUsuarioSSO(request)));
				log.debug(">>> candidatos size: " + candidatos.size());
			}
			else if( tipoTramite.equals( TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo().longValue() ) ){
				descTipoTramite= "actualizaci&oacute;n de domicilio particular y/o cambio de UMF";
				AsignacionNSS anss = new AsignacionNSS();
				anss.setNss(nss);
				anss.setIdAsignacionNSS(idAsignacionNss);
				candidatos = cambioClinicaServiceRemote.findGrupoFamiliarCambioUmf(anss, null, false, null);
			}
		} catch(DerechohabientesBusinessException e) {
			e.printStackTrace();
			log.error("Ocurrio un error al intentar recuperar a los candidatos " + tipoTramite, e);
			model.addAttribute("error", e.getSituacion());
		} catch(Exception e) {
			log.error("Ocurrio un error no esperado al consultar la lista de candidatos: " + e.getMessage(), e);
			model.addAttribute("error","Ocurrio un error al buscar a los integrantes del grupo familiar candidatos a: " + descTipoTramite);
		}
		
		model.addAttribute("descripcionTipoTramite", descTipoTramite);
		model.addAttribute("nss", nss);
		model.addAttribute("tipoTramite", tipoTramite);
		model.addAttribute("grupoFamiliar", candidatos);
		model.addAttribute("seleccionMultiple", seleccionMultiple);
		
		return "listadoCandidatos";
	}
	
	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.

		if (usuariosso.getDelegacion() != null
				&& usuariosso.getSubdelegacion() != null) {

			// LUDS Se agrego esta validacion para que si es en caso de un
			// usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(
					usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}

		usuario.setCveIdUsuario(usuariosso.getCurp());

		return usuario;
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {
		
		return null;
	}
	
	/**
	 * Verifica si la persona es asegurado o patron en base a su id de persona.
	 * Invocado desde listadoCandidatos.js#verificarExistenciaAsegurado
	 * @param correccionDatosDerechohabienteDto
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/verificarExistenciaAsegurado", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<CorreccionDatosDerechohabienteDto> verificarExistenciaAsegurado(
			@RequestBody CorreccionDatosDerechohabienteDto correccionDatosDerechohabienteDto, HttpServletRequest request,HttpSession session){
		
		//Creamos el objeto en el que mandaremos la respuesta a vista
		RespuestaJSON<CorreccionDatosDerechohabienteDto> respuesta = new RespuestaJSON<CorreccionDatosDerechohabienteDto>();
		
		try {
			log.debug(">>> Verificando si la persona con id de persona " +  correccionDatosDerechohabienteDto.getIdPersona() + " es Asegurado o Patr\u00F3n ...");
			correccionDatosDerechohabienteDto.setEsAseguradoOPatron(grupoFamiliarService.esAseguradoOPatronORepresentanteLegal(correccionDatosDerechohabienteDto.getIdPersona()));
			respuesta.setModelo(correccionDatosDerechohabienteDto);
		} catch (Exception e) {
			log.error("Ocurri\u00F3 un error al verificar si la persona con id " + correccionDatosDerechohabienteDto.getIdPersona() + "es patr\u00F3n o asegurado");
			e.printStackTrace();
			
			// se asigna valor por defecto
			correccionDatosDerechohabienteDto.setEsAseguradoOPatron(false);
			List<String> erroresList = new ArrayList<String>();
			erroresList.add(e.getMessage());
			
			respuesta.setErrores(erroresList);
		}	
		return respuesta;
	}
}
