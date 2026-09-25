package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( value = "/umf")
public class DatosAdscripcionController extends AbstractController {

	@Autowired UmfServiceRemote umfService;
	@Autowired GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	private final String KEY_ASIGNACION_NSS = "asignacionNssSession";
	
	/**
	 * Metodo para obtener los consultorios disponibles en un turno en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getConsultorioMenorPoblacion", method = RequestMethod.POST)
	public @ResponseBody Consultorio getConsultorioMenorPoblacion(@RequestBody MedicoEnTurno umfTurno) {
		
		Consultorio consultorio = null;
		Integer mostrar = umfTurno.getConsultorio() != null ? (umfTurno.getConsultorio().getConsultorioVirtual() != null ? umfTurno.getConsultorio().getConsultorioVirtual() : 1) : 1;
		Boolean mostrarVirtuales = mostrar.intValue() == 1;
		try {
			
			consultorio = umfService.getConsultorioMenorPoblacion(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(),
					umfTurno.getTurno().getIdTurno(),
					mostrarVirtuales);
			
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return consultorio;
	}
	
	@RequestMapping( value = "/getUmfsByAsentamiento")
	public @ResponseBody List<UnidadMedicaFamiliar> getUmfsByAsentamiento(@RequestBody Asentamiento asentamiento, 
			@RequestParam(value="idDelegacionAsegurado",required=false)Long idDelegacionAsegurado  ,final HttpSession session) {
		List<UnidadMedicaFamiliar> umfs = null;
		try {
			
			umfs = umfService.findUmfsByAsentamiento(asentamiento, false);
			
		} catch (CodigoSinUmfException e) {
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return umfs;
	}
	
	@RequestMapping( value = "/getUmfsByCodigoPostalV")
	public @ResponseBody List<UnidadMedicaFamiliar> getUmfsByCodigoPostalV(@RequestBody CodigoPostal codigoPostal) {
		List<UnidadMedicaFamiliar> umfsCFE = new ArrayList<UnidadMedicaFamiliar>();
		List<UnidadMedicaFamiliar> umfsNoCFE = new ArrayList<UnidadMedicaFamiliar>();
		List<UnidadMedicaFamiliar> umfs = new ArrayList<UnidadMedicaFamiliar>();
		try {
			umfsNoCFE = umfService.findUmfByCodigoPostal(codigoPostal.getCodigoPostal().toString(),1);
		} catch (CodigoSinUmfException e) {
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			umfsCFE = umfService.findUmfByCodigoPostal(codigoPostal.getCodigoPostal().toString(),0);
		} catch (CodigoSinUmfException e) {
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(umfsNoCFE != null && !umfsNoCFE.isEmpty()) {
			umfs.addAll(umfsNoCFE);
		}
		
		if(umfsCFE != null && !umfsCFE.isEmpty()) {
			umfs.addAll(umfsCFE);
		}
		
		return umfs;
	}
	
	@RequestMapping( value = "/getUmfsByCodigoPostal")
	public @ResponseBody List<UnidadMedicaFamiliar> getUmfsByCodigoPostal(@RequestBody CodigoPostal codigoPostal) {
		List<UnidadMedicaFamiliar> umfs = null;
		try {
			umfs = umfService.findUmfByCodigoPostal(codigoPostal.getCodigoPostal().toString(),1);
		} catch (CodigoSinUmfException e) {
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return umfs;
	}
	
	@RequestMapping( value = "/getTurnosByUmf")
	public @ResponseBody List<Turno> getTurnosByUmf(@RequestBody UnidadMedicaFamiliar umf) {
		List<Turno> turnos = null;
		try {
			turnos = umfService.getTurnosDisponiblesPorUmf(umf.getIdUMF());
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return turnos;
	}
	
	/**
	 * Metodo para obtener los consultorios disponibles en un turno en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getConsultorios", method = RequestMethod.POST)
	public @ResponseBody List<Consultorio> getConsultorios(@RequestBody MedicoEnTurno umfTurno) {
		
		List<Consultorio> consultorios = new ArrayList<Consultorio>();
		try {
			
			Integer mostrar = umfTurno.getConsultorio() != null ? (umfTurno.getConsultorio().getConsultorioVirtual() != null ? umfTurno.getConsultorio().getConsultorioVirtual() : 1) : 1;
			Boolean mostrarVirtuales = mostrar.intValue() == 1;
			
			List<Consultorio> _consultorios = umfService.findConsultorioByUmfTurno(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(),
					umfTurno.getTurno().getIdTurno(), 
					mostrarVirtuales);
			
			
			for( Consultorio c : _consultorios ){
				consultorios.add(new Consultorio(c.getIdConsultorio(), c.getDescripcion()+" / ("+c.getPoblacion()+")"  ));
			}
			
			
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return consultorios;
	}
	
	/**
	 * Metodo para obtener los medicos que atienden un consultorio, en un turno, en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getMedicosUmfTurno", method = RequestMethod.POST)
	public @ResponseBody List<MedicoEnTurno> getMedicosUmfTurnoConsultorio(@RequestBody MedicoEnTurno umfTurno) {
		
		List<MedicoEnTurno> medicos = null;
		
		try {
			medicos = umfService.findMedicosByUmfTurnoConsultorio(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(),
					umfTurno.getTurno().getIdTurno(),
					umfTurno.getConsultorio().getIdConsultorio());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return medicos;
	}
	
	@RequestMapping( value = "/getMedicoEnTurnoActivo", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> buscarMedicoEnTurnoActivo(@RequestBody GrupoFamiliar integrante, Model model,
			HttpServletRequest request, HttpSession session) {
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		AsignacionNSS asignacionNss = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		//Si no encontramos el nss en sesion lo sacamos del integrante qude se esta mandando
		if(asignacionNss == null) {
			log.debug("No se encontro nss en sesion");
			asignacionNss = integrante.getAsignacionNSS();
		}
		
		if(asignacionNss == null || asignacionNss.getIdAsignacionNSS() == null) {
			result.put("error", true);
			result.put("mensaje", "No fue proporcionado el NSS para la busqueda");
			return result;
		}
		
		result = grupoFamiliarServiceRemote.buscarMedicoEnTurnoActivo(integrante);
		
		return result;
	}
	
}