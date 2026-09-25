package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AsignacionDomicilioServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/asignacionDomicilio")
public class AsignacionDomicilioController extends AbstractController{

	@Autowired
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private FinalizaSolicitudServiceRemote finalizaSolicitudService;
	@Autowired
	private AsignacionDomicilioServiceRemote asignacionDomicilioServiceRemote;
	@Autowired
	private CambioClinicaServiceRemote cambioClinicaServiceRemote;
	@Autowired
	TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
	
	/**
	 * Este metodo se encarga de guardar el domicilio capturado y asignarlo al derechohabiente, una vez guardado el domicilio
	 * se redirección a la pantalla que indica que el tramite ha finalizado
	 * @param correccion
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping( value = "/finalizar", method = RequestMethod.POST)
	public String guardaAsignacionDomicilio(
			@ModelAttribute("derechohabiente") TramiteCorreccionDerechohabiente correccion,
			HttpSession session, HttpServletRequest request, Model model) {
		boolean verDocumentos = false;

		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		Long idSolicitudCorreccion = null;
		log.debug("Se guardara el tramite de asignacion de domicilio");
		try {
			
			log.debug("Comienza la asignacion del domicilio para el nss: " + asignacionNSS.getNssStr() + " a las " + new Date() );

			TramiteCorreccionDerechohabiente datosActuales = this.convetirGrupoCorreccion(asegurado);
			datosActuales.setDomicilio(correccion.getDomicilio());
			datosActuales.setIdAsignacionNss( asignacionNSS.getIdAsignacionNSS() );
			datosActuales.setNss(asignacionNSS.getNssStr());
			datosActuales.setPersona(asegurado.getDerechohabiente());
			datosActuales.setIdPersona(asegurado.getDerechohabiente().getIdPersona());
			datosActuales.setObservacion(correccion.getObservacion());
			
			if(asegurado.getMedicoEnTurno() != null && asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
				datosActuales.setIdUmfOrigen(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			}
			//se hacen validaciones para saber si hay fecha de cambio y si se necesita hacer cambio de medico 
			//el cual se hara una vez que termine el cambio de clinica
			//Map<String, Object> validacionesFechasCambioMedico = this.getValidacionesCambio(asegurado, correccion, listasPadresConcubinas);
			
			if(correccion.getMedicoEnTurno() != null) {
				datosActuales.setMedicoEnTurno(correccion.getMedicoEnTurno());
			}
			
			Solicitud solicitudCorreccion = correccionDerechohabienteServiceRemote.saveCorreccionDatosDerechohabiente(correccion.getIdPersona(),null, usuario, asignacionNSS, datosActuales,OrigenSolicitudEnum.VENTANILLA, new Boolean(true));
			idSolicitudCorreccion = solicitudCorreccion.getSolicitudId();
			datosActuales = this.obtenerCorreccion(solicitudCorreccion);
			
			log.debug("Se procede a guardar la validacion de asignacion de domicilio");
			log.debug("el folio de la solicitud es: " + solicitudCorreccion.getNoFolioSolicitud());
			
			try {
				tramiteDocumentosServiceRemote.generaFirmaElectronica(asignacionNSS, solicitudCorreccion, solicitudCorreccion.getTramites().get(0).getTipoTramite().getDescripcion());
			} catch(DocumentoException e) {
				log.error("No fue posible guardar la firma digital",e);
			}
			//solicitudCorreccion = correccionDerechohabienteServiceRemote.finalizarSolicitudDomicilioClinicaCircunscripcion(solicitudCorreccion);
			Map<String, Object> validacionesCambios = this.cambioClinica(asegurado, datosActuales);
			List<GrupoFamiliar> padres = null;
			
			Boolean existeCambioClinica = (Boolean) validacionesCambios.get("existeCambioClinica");
			
			if(existeCambioClinica) {
				verDocumentos = true;
			}
			
			padres = this.getPadresConcubina(asignacionNSS, cabeza);
			
			Solicitud solicitud = asignacionDomicilioServiceRemote.guardaAsignacionDeDomicilioSimplificado(solicitudCorreccion.getSolicitudId(), asignacionNSS, cabeza, asegurado,
					datosActuales, padres, validacionesCambios);
		
			if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
				solicitud.setNoFolioSolicitud(solicitudCorreccion.getNoFolioSolicitud());
				solicitudCorreccion = solicitud;
			}
			
			try {
				log.debug("Se manda el movimiento de asignacion de domicilio para el nss: " + asignacionNSS.getNssStr() + " as las " + new Date());
				finalizaSolicitudService.finalizarSolicitudTramites(solicitudCorreccion, usuario.getCveIdUsuario(), asignacionNSS);
				log.debug("El ws para el movimiento de asignacion de domicilio para el nss:" +  asignacionNSS.getNssStr() +" termino a las " + new Date());
				log.debug("Se concluye tramite se asignacion de domicilio para el nss: " + asignacionNSS.getNssStr() + " a las " + new Date());
			} catch(Exception e) {
				log.error("Error al generar los documentos",e);
			}
			//correccionDerechohabienteServiceRemote.guardarValidacionCorreccionDatos(solicitudCorreccion,usuario.getFisica());
			model.addAttribute("solicitud", solicitudCorreccion);
			model.addAttribute("verDocumentos", verDocumentos);
			
			return "finalizaAsignacionDomicilio";
			
		} catch (DerechohabientesBusinessException e) {
			cancelarSolicitud(idSolicitudCorreccion);
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		} catch (SolicitudNoEncontradaException e) {
			cancelarSolicitud(idSolicitudCorreccion);
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		} catch (Exception e) {
			cancelarSolicitud(idSolicitudCorreccion);
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error",e.getCause().getMessage());
			return "internalError";
		}
	}
	
	private void cancelarSolicitud(Long idSolicitud) {
		if(idSolicitud != null){
			try {
				solicitudBusinessRemote.actualizaAConcluida(new Solicitud(idSolicitud));
			} catch (SolicitudNoEncontradaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Map<String, Object> cambioClinica(GrupoFamiliar antiguo, TramiteCorreccionDerechohabiente correccion) {
		Map<String, Object> validacionesClinica = new HashMap<String, Object>();
		Boolean existeCambio = false;
		Boolean existeCambioConsultorio = false;
		
		if(antiguo.getMedicoEnTurno() == null && correccion.getMedicoEnTurno() != null) {
			existeCambio =  true;
		}
		
		MedicoEnTurno morigen = antiguo.getMedicoEnTurno();
		MedicoEnTurno mdestino = correccion.getMedicoEnTurno();
		
		if(morigen != null && mdestino != null) {
			UnidadMedicaFamiliar origen = morigen.getUnidadMedicaFamiliar();
			UnidadMedicaFamiliar destino = mdestino.getUnidadMedicaFamiliar();
			
			if(origen == null && destino != null) {
				existeCambio =  true;
			}
			
			if((origen != null && destino != null) && (!origen.getIdUMF().equals(destino.getIdUMF()))) {
				existeCambio =  true;
			}
			
			if(!existeCambio && (morigen != null && mdestino != null && !morigen.getIdMedicoContultorioTurno().equals(mdestino.getIdMedicoContultorioTurno()))) {
				existeCambioConsultorio = true;
			}
		}
		
		validacionesClinica.put("existeCambioClinica", existeCambio);
		validacionesClinica.put("existeCambioConsultorio", existeCambioConsultorio);
		
		return validacionesClinica;
	}
	
	private List<GrupoFamiliar> getPadresConcubina(AsignacionNSS asignacion, CabezaGrupoFamiliar cabeza) {
		
		List<GrupoFamiliar> grupoPadres = null;
		List<Long> parentescos = new ArrayList<Long>();
		parentescos.add(ParentescoEnum.PADRES.getId());
		parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		
		Long numeroIntegrantes = grupoFamiliarService.getNumeroDeIntegrantesPorListParentesco(asignacion.getIdAsignacionNSS(), parentescos);
		
		if(numeroIntegrantes != null && numeroIntegrantes.intValue() > 0) {
			try {
				grupoPadres =asignacionDomicilioServiceRemote.getPadresConcubinasParaCambio(asignacion, cabeza.getPatronImss());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return grupoPadres;
	}
	
	private Map<String, Object> getValidacionesCambio(GrupoFamiliar asegurado, TramiteCorreccionDerechohabiente correccion, List<GrupoFamiliar> idsPersonas)	{
		List<Long> idsPersonasExcluir = new ArrayList<Long>();
		idsPersonasExcluir.add(asegurado.getDerechohabiente().getIdPersona());
		
		if(idsPersonas != null && !idsPersonas.isEmpty()) {
			for(GrupoFamiliar integrante: idsPersonas) {
				idsPersonasExcluir.add(integrante.getDerechohabiente().getIdPersona());
			}
		}
		
		//se hacen validaciones para saber si hay fecha de cambio y si se necesita hacer cambio de medico 
		//el cual se hara una vez que termine el cambio de clinica
		Map<String, Object> validacionesFechasCambioMedico = null;
		try {
			validacionesFechasCambioMedico = cambioClinicaServiceRemote.getFechaCambioYDatosCambioMedico(asegurado.getAsignacionNSS(), 
					correccion.getMedicoEnTurno(), idsPersonasExcluir, true, null, true);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return validacionesFechasCambioMedico;
		
	}
	
	/**
	 * Metodo para pasar los datos de un objeto de tipo GrupoFamiliar aun CorreccionDatoDerechohabiente
	 * @param integrante
	 * @return
	 */
	private TramiteCorreccionDerechohabiente convetirGrupoCorreccion(GrupoFamiliar integrante) {
		TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
		
		correccion.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		correccion.setNombre(integrante.getDerechohabiente().getNombre());
		correccion.setPrimerApellido(integrante.getDerechohabiente().getPrimerApellido());
		correccion.setSegundoApellido(integrante.getDerechohabiente().getSegundoApellido());
		correccion.setCurpCap(integrante.getDerechohabiente().getCurp());
		if(integrante.getDerechohabiente().getSexo().getIdSexo().equals(SexoEnum.MUJER.getId()) && integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
			correccion.setSexo(new Sexo());
			correccion.getSexo().setIdSexo(Integer.parseInt(""+ParentescoEnum.MADRE.getId()));
		} else {
			correccion.setSexo(integrante.getDerechohabiente().getSexo());
		}
		correccion.setFechaNacimiento(integrante.getDerechohabiente().getFechaNacimiento());
		correccion.setLugarNacimiento(integrante.getDerechohabiente().getLugarNacimiento());
		correccion.setParentesco(integrante.getParentesco());
		correccion.setCalidad(integrante.getCalidad().toString());
		correccion.setNss(integrante.getAsignacionNSS().getNssStr());
		correccion.setDomicilio(integrante.getDomicilio());
		correccion.setMedicoEnTurno(integrante.getMedicoEnTurno());
		correccion.setEstadoCivil(integrante.getDerechohabiente().getEstadoCivil());
		correccion.setCorreoElectronico(integrante.getDerechohabiente().getCorreoElectronico());
		correccion.setFacebook(integrante.getDerechohabiente().getFacebook());
		correccion.setTwitter(integrante.getDerechohabiente().getTwitter());
		correccion.setTelefonoFijo(integrante.getDerechohabiente().getTelefonoFijo());
		correccion.setTelefonoMovil(integrante.getDerechohabiente().getTelefonoMovil());
		correccion.setMesRegistroNac(integrante.getDerechohabiente().getMesRegistroNac());
		correccion.setAnioRegistroNac(integrante.getDerechohabiente().getAnioRegistroNac());
		
		if(integrante.getMedicoEnTurno() != null && integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
			correccion.setIdUmfOrigen(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
		}
		
		if( integrante.getEstadoDerechohabiente() != null )
			correccion.setIdEstadoDerechohabiente(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
		
		correccion.setParentescoActual(integrante.getParentesco());
		correccion.setIndicadorRN(integrante.getIndRecienNacido());
		
		
		return correccion;
	}
	
	private TramiteCorreccionDerechohabiente obtenerCorreccion(Solicitud solicitud){
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteCorreccionDerechohabiente) {
				return (TramiteCorreccionDerechohabiente) tramite;
			}
		}
		return null;
	}
}
