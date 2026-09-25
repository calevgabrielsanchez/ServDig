package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PatronServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/portlet")
public class PortletController extends AbstractController {
	
	
	@Autowired GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired SujetoObligadoServiceBusinessRemote sujetoObligadoServiceRemote;
	@Autowired PatronServiceRemote patronServiceRemote;	

	private static final String PATRON_JCF = "Y5845183325";
	private static final String DES_MODALIDAD_32_JCF = "PROGRAMA JOVENES CONSTRUYENDO EL FUTURO";
	private static final String DES_MODALIDAD_32_INST_EDUCATIVA = "SEGURO FACULTATIVO ESTUDIANTES";
	private static final String DES_MODALIDAD_32_CFE = "SEGURO FACULTATIVO IMSS / CFE";	
	private static final String[] RPS_17 = new String[] {"A7711544174","M6610218175","B3710738106"};
	
	private final String CONTEXTO_VENTANILLA = "/portalDerechohabiente-ventanilla";
	
	@RequestMapping(value = "/grupoFamiliar/{idAsignacionNss}/{mostrarOpciones}")
	public String initPortletGrupoFamiliar(Model model, HttpSession session, @PathVariable Long idAsignacionNss, @PathVariable Long mostrarOpciones) {
		
		AsignacionNSS nss = new AsignacionNSS();
		nss.setIdAsignacionNSS(idAsignacionNss);
		model.addAttribute("nss", nss);
		model.addAttribute("mostrarOpciones", mostrarOpciones);
		
		return "portletGrupoFamiliarInit";
	}
	
	@RequestMapping(value = "/grupoFamiliar/detalle/{idAsignacionNss}/{mostrarOpciones}")
	public String detallePortletGrupoFamiliar(Model model, HttpSession session, @PathVariable Long idAsignacionNss, @PathVariable Long mostrarOpciones) {
		
		List<GrupoFamiliar> grupoFamiliar = new ArrayList<GrupoFamiliar>();
		boolean modalidad17 = false;
		Boolean isPensionadoMod17Convenio = false;
		Boolean isMod17Convenio = false;
		GrupoFamiliar asegurado = null;
		//bandera para saber si mostrams las opciones en el portlet y si el link en el nombre
		//mandara al portal de derechohabiente o mostrara el wizard de detalle de derechohabiente
		model.addAttribute("mostrarOpciones", mostrarOpciones);
		
		try{
			grupoFamiliar = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByIdAsignacionNss(idAsignacionNss, true, false, false);
			CabezaGrupoFamiliar cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				modalidad17 = cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(ModalidadEnum.DIECISIETE.getNumModalidad());
			}
			//Verfificamos que no este vacio el grupo familiar
			if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
				asegurado = grupoFamiliarServiceRemote.getDatosVigenciaPorNss(grupoFamiliar.get(0).getAsignacionNSS().getNss());
				model.addAttribute("grupoFamiliar", grupoFamiliar);
				model.addAttribute("asignacionNSS", grupoFamiliar.get(0).getAsignacionNSS());
				model.addAttribute("asegurado", asegurado);
			}
			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
					for(String rpMod17: RPS_17) {
						if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
							log.error("Si es patron con convenio " + rpMod17);
							isPensionadoMod17Convenio = true;
						}
					}
				} else {
					if((cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
						for(String rpMod17: RPS_17) {
							if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isMod17Convenio = true;
							}
						}
					}	
				}
			}
			
			model.addAttribute("modalidad17", modalidad17);
			model.addAttribute("isPensionadoMod17Convenio", isPensionadoMod17Convenio);
			model.addAttribute("isMod17Convenio", isMod17Convenio);
		
		} catch(DerechohabientesBusinessException e){
			log.error("Ocurrio un error al obtener el grupo familiar", e);
			model.addAttribute("error", e.getSituacion());
		} catch(Exception e) {
			log.error("Ocurrio un error al consultar a los derechohabientes",e);
			model.addAttribute("error", "Ocurrio un error al recuperar al grupo familiar");
		}
		
		return "portletGrupoFamiliarContenido";
	}
	
	@RequestMapping( value = "/detalle/integrante/{nss}/{idPersona}/{idAsignacionNss}/{inconsistencia}")
	public String initPortletDetalleBeneficiario(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable String nss, @PathVariable Long idPersona,@PathVariable Long idAsignacionNss, @PathVariable Integer inconsistencia) {
		
		AsignacionNSS fisica = new AsignacionNSS();
		fisica.setIdPersona(idPersona);
		fisica.setNss(nss);
		fisica.setIdAsignacionNSS(idAsignacionNss);
		fisica.setEstadoInconsistencia(inconsistencia);
		model.addAttribute("persona", fisica);
		
		return "portletDetalleIntegranteInit";
	}
	
	@RequestMapping( value = "/detalle/integrante/detalle/{nss}/{idPersona}/{idAsignacionNss}/{inconsistencia}")
	public String detallePortletDetalleBeneficiario(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable String nss, @PathVariable Long idPersona,@PathVariable Long idAsignacionNss, @PathVariable Integer inconsistencia) {
		
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		GrupoFamiliar derechohabiente = null;
		Boolean isPatronJCF = false;
		
		try {
			if(inconsistencia.equals(EstadoInconsistenciaVigenciaEnum.ASEGURADO.getId()) || inconsistencia.equals(EstadoInconsistenciaVigenciaEnum.BENEFICIARIOS.getId()) ) {
				derechohabiente = grupoFamiliarServiceRemote.getAseguradoInconsistente(nss);
			} else {
				derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idPersona);
				cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
				
				try {
				List<SujetoObligado> sujetos = grupoFamiliarServiceRemote.getPatronesAsegurado(derechohabiente.getAsignacionNSS()); 
				if(sujetos != null && !sujetos.isEmpty()) {
					for(SujetoObligado sujeto: sujetos) {
						String rp = sujeto.getNumeroRegistroPatronal() + "" + sujeto.getModalidad().getNumModalidad() + "" + sujeto.getDigVerificador();
						log.error("El registro patronal para checar JCF es " + rp);
						if(rp.equals(PATRON_JCF)) {
							isPatronJCF = true;
						}
					}
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			try{
				if(derechohabiente.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
					derechohabiente.getAsignacionNSS().setTipoPension(grupoFamiliarServiceRemote.getAseguradoInconsistente(nss).getAsignacionNSS().getTipoPension());
					derechohabiente.getAsignacionNSS().setPensionado(true);
				}
			}catch(Exception e){
				log.error("ERROR al consultar al pensionado para identificar la pensi�n", e);
			}

			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			Boolean isPensionadoMod17Convenio = false;
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
					for(String rpMod17: RPS_17) {
							if(cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal().equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isPensionadoMod17Convenio = true;
							}
					}
				}
			}
			
			//Agregamos al modelo los datos del derechohabiente y el domicilio particular
			model.addAttribute("derechohabiente", derechohabiente);
			if(cabezaGrupoFamiliar !=  null) {
				model.addAttribute("patronImss", cabezaGrupoFamiliar.getPatronImss().equals(1));
			} else {
				model.addAttribute("patronImss", false);
			}
			
			model.addAttribute("isPatronJCF", isPatronJCF);
			model.addAttribute("isAsegurado",true);
			model.addAttribute("isPensionadoMod17Convenio", isPensionadoMod17Convenio);
		}
		catch(DerechohabientesBusinessException e) {
			log.error("ocurrio un erro de derechohabientes", e);
			request.setAttribute("errores", e.getMessage());
			
		}catch (Exception e){
			log.error("ocurrio un erro no cachado", e);
			request.setAttribute("errores", e.getMessage());	
		}
		
		return "portletDetalleIntegranteContenido";
	}
	@RequestMapping( value = "/datosPatron/{idAsignacionNss}/{nss}")
	public String initPortletDatosPatron(Model model, HttpServletRequest request,
			@PathVariable Long idAsignacionNss, @PathVariable String nss) {
		
		AsignacionNSS asignacionNss = new AsignacionNSS();
		asignacionNss.setIdAsignacionNSS(idAsignacionNss);
		asignacionNss.setNss(nss);
		
		model.addAttribute("nss", asignacionNss);
		
		return "portletDatosPatronInit";
	}
	
	@RequestMapping( value = "/datosPatron/detalle/{idAsignacionNss}/{nss}")
	public String detallePortletDatosPatron(Model model, HttpServletRequest request,
			@PathVariable Long idAsignacionNss, @PathVariable String nss) {
		
		List<SujetoObligado> patrones = null;
		CabezaGrupoFamiliar cabeza = null;
		AsignacionNSS asignacionNss = new AsignacionNSS();
		asignacionNss.setIdAsignacionNSS(idAsignacionNss);
		asignacionNss.setNss(nss);
		List<SujetoObligado> patronesConDomicilio =  new  ArrayList<SujetoObligado>();
		
		try {
			cabeza = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
		} catch (DerechohabientesBusinessException e) {
			model.addAttribute("error", e.getSituacion());
			
		}catch (Exception e) {
			log.error("error al consultar los patrones vigentes familiar para detalle patron", e);
			model.addAttribute("error", "No fue posible obtener los patrones del asegurado");
			return "portletDatosPatronContenido";
		}
		
		//Si la calidad es pensionado(6) y 
		//Si el asegurado tiene Preafiliacion (Modalidad=00) no se buscan patrones
		if(cabeza.getPatronSujetoObligado() != null){
			if(!(cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("00"))){

				cabeza.setPatronSujetoObligado(this.chageDescModalidad(cabeza.getPatronSujetoObligado()));

				try{
					patrones = grupoFamiliarServiceRemote.getPatronesAsegurado(asignacionNss);
			
					cabeza.setPatronSujetoObligado(this.seteaCentroTrabajo(cabeza.getPatronSujetoObligado()));
					if(patrones != null && !patrones.isEmpty()){
						for(SujetoObligado patron : patrones){
							patronesConDomicilio.add(this.chageDescModalidad(this.seteaCentroTrabajo(patron)));
						}
					}
				}catch (Exception e) {
					log.error("error al consultar los patrones vigentes familiar para detalle patron", e);
					//model.addAttribute("error", "No fue posible obtener los patrones del asegurado");
				}
			} else {
				patronesConDomicilio.add(new SujetoObligado());
			}
		}		
		model.addAttribute("cabezaGrupoFamiliar", cabeza);
		model.addAttribute("patrones", patronesConDomicilio);

		
		//secccion para mostrar los ulitmos patrones con movimiento de baja//
		
		List<DetallePeriodoMovimientoAfiliatorioPatron> lstMovimientos= null;
		List<DetallePeriodoMovimientoAfiliatorioPatron> lstMovimientosFinal= null;
		try{
			lstMovimientos= grupoFamiliarServiceRemote.getUltimosMovimientosPatronesAsegurado(nss);

			if(lstMovimientos != null && !lstMovimientos.isEmpty()){
				lstMovimientosFinal = new ArrayList<DetallePeriodoMovimientoAfiliatorioPatron>();
				//se itera la lsita para recuperar el domicilio del patron
				SujetoObligado patron = null;
				for(DetallePeriodoMovimientoAfiliatorioPatron movimientos:lstMovimientos ){
					patron = movimientos.getSujetoObligado();
					patron = this.chageDescModalidad(this.seteaCentroTrabajo(patron));
					movimientos.setSujetoObligado(patron);
					lstMovimientosFinal.add(movimientos);
				}
				model.addAttribute("ultimosMovimientosBaja", lstMovimientosFinal);
			}
		}catch (Exception e){
				log.error("Ocurrio un error al consultar los movimeintos patronales", e);
		}
		
		if(request.getContextPath().equalsIgnoreCase(CONTEXTO_VENTANILLA)){
			model.addAttribute("origenVentanilla", "true");
		}
		
		
		
		return "portletDatosPatronContenido";
	}
	
	private SujetoObligado seteaCentroTrabajo(SujetoObligado patron){
		CentroTrabajo domCentro = null;
		try{
			
			domCentro =sujetoObligadoServiceRemote.obtenerDomicilioCentroTrabajoNormaTecnicaOMigradoSindo(patron);
			if(domCentro == null){
				 domCentro = new CentroTrabajo();
				 domCentro.setDescripcion("Sin Domicilio Registrado");
				}
			domCentro.setDescripcion(StringUtils.replace(domCentro.getDescripcion(), "null", " "));   
		}catch(Exception e){
				log.error("error al consultar el domicilio del patron error cachado ya que es opcional");
				domCentro = new CentroTrabajo();
				domCentro.setDescripcion("Sin Domicilio Registrado");
		}
		patron.setCntroTrabajo(domCentro);
		return patron;
		
	}
	
	private SujetoObligado chageDescModalidad(SujetoObligado patron){
		String idMod = patron.getModalidad().getNumModalidad();
		if(idMod.equals(ModalidadEnum.TREINTAYDOS.getNumModalidad())) {
			patron.getModalidad().setDescripcion(DES_MODALIDAD_32_CFE);				
			String regPatronal = patron.getNumeroRegistroPatronal() + "" + patron.getModalidad().getNumModalidad() + "" + patron.getDigVerificador();
			if(regPatronal.equals(PATRON_JCF)){
				patron.getModalidad().setDescripcion(DES_MODALIDAD_32_JCF);
				return patron;
			}
			
			String validaNss = regPatronal + idMod;
			if (patronServiceRemote.isRegistroPatronalnstitucionEducativa(validaNss)) {
				patron.getModalidad().setDescripcion(DES_MODALIDAD_32_INST_EDUCATIVA);
				return patron;
			}
				
		}
		return patron;
	}
	
	@RequestMapping( value = "/solicitud/proceso/asegurado/{nss}")
	public String initPortletSolicitudesEnProceso(Model model, HttpServletRequest request, @PathVariable String nss) {
		
		model.addAttribute("nss", nss);
		
		return "portletSolicitudesProcesoInit";
	}
	
	@RequestMapping( value = "/solicitud/proceso/asegurado/detalle/{nss}")
	public String detallePortletSolicitudesEnProceso(Model model, HttpServletRequest request, @PathVariable String nss){
		List<Solicitud> solicitudes = null;
		
		try{
			solicitudes = solicitudBusinessRemote.getSolicitudesGrupoFamiliar(nss, OrigenSolicitudEnum.INTERNET.getId());
		} catch(Exception e) {
			log.error("error al consultar las solicitudes", e);
		}
		model.addAttribute("solicitudes", solicitudes);
		
		return "portletSolicitudesProcesoContenido";
	}
	
	@RequestMapping( value = "/solicitud/proceso/beneficiario/{nss}/{idIntegrante}")
	public String initPortletSolicitudesEnProceso(Model model, HttpServletRequest request, @PathVariable String nss, @PathVariable Long idIntegrante) {
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idIntegrante);
		fisica.setNss(nss);
		
		model.addAttribute("fisica", fisica);
		
		return "portletSolicitudesBeneficiarioInit";
	}
	
	@RequestMapping( value = "/solicitud/proceso/beneficiario/detalle/{nss}/{idIntegrante}")
	public String detallePortletSolicitudesEnProceso(Model model, HttpServletRequest request, @PathVariable String nss, @PathVariable Long idIntegrante){
		List<Solicitud> solicitudes = null;
		
		try{
			solicitudes = solicitudBusinessRemote.getSolicitudesGrupoFamiliarEIntegrante(nss,idIntegrante, OrigenSolicitudEnum.INTERNET.getId());
		} catch(Exception e) {
			log.error("error al consultar las solicitudes", e);
		}
		model.addAttribute("solicitudes", solicitudes);
		
		return "portletSolicitudesProcesoContenido";
	}
	
	@RequestMapping( value = "/buscar/gruposFamiliares/{nss}/{idIntegrante}")
	public String initPortletOtrosGruposfamiliares(Model model, HttpServletRequest request, @PathVariable String nss, @PathVariable Long idIntegrante){

		Fisica persona = new Fisica();
		persona.setIdPersona(idIntegrante);
		persona.setNss(nss);
		
		model.addAttribute("fisica", persona);
		
		return "portletOtrosGruposInit";
	}
	
	@RequestMapping( value = "/buscar/gruposFamiliares/detalle/{nss}/{idIntegrante}")
	public String portletPortletOtrosGruposfamiliares(Model model, HttpServletRequest request, @PathVariable String nss, @PathVariable Long idIntegrante){
		List<GrupoFamiliar> grupos = null;
		Boolean isModalidad17 = false;
		
		try{
			grupos = grupoFamiliarServiceRemote.getGruposFamiliaresPorPersona(idIntegrante,nss,false);
			if(grupos != null) {
				for(GrupoFamiliar grupo: grupos) {
					CabezaGrupoFamiliar cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(grupo.getAsignacionNSS().getIdAsignacionNSS());
					
					if(cabezaGrupoFamiliar != null && cabezaGrupoFamiliar.getPatronSujetoObligado() != null && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
						isModalidad17 = cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(ModalidadEnum.DIECISIETE.getNumModalidad());
						break;
					}
				}
			}
		} catch(DerechohabientesBusinessException e) {
			log.error("error al consultar las solicitudes", e);
			model.addAttribute("error", e.getSituacion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("modalidad17", isModalidad17);
		model.addAttribute("grupos", grupos);
		
		return "portletOtrosGruposContenido";
	}
	
	@RequestMapping( value = "/pensiones")
	public String initPortletPensiones(Model model, HttpServletRequest request) {
		
		return "portletPensionesInit";
	}
}
