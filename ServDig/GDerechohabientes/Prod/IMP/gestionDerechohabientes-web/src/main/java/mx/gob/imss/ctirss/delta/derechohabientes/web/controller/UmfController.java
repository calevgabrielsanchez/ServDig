package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.BusquedaDelegacion;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.enums.MedicoEspecialidadEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/umf/*")
public class UmfController {
	
	private static final Logger logger = Logger.getLogger(UmfController.class);
	
	@Autowired
	UmfServiceRemote umfService;
	
	@RequestMapping(value = "/getUMFs",method=RequestMethod.GET)
	public @ResponseBody RespuestaJSON<List<UnidadMedicaFamiliar>> getUMFBySubdelegacion(@RequestParam(value="idSubdelegacion")  Long idSubdelegacion){
		RespuestaJSON<List<UnidadMedicaFamiliar>> respuesta = new RespuestaJSON<List<UnidadMedicaFamiliar>>();
		List<UnidadMedicaFamiliar> unidades= null;
		try {
			unidades = umfService.findUmfbySubDelagacionDelegacion(idSubdelegacion);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		respuesta.setModelo(unidades);
		
		return respuesta;
	}
	
	@RequestMapping(value = "/getMatriculas",method=RequestMethod.GET)
	public @ResponseBody RespuestaJSON<List<MedicoEnTurno>> getMatriculasMedicas(@RequestParam(value="idUmf")  Long idUmf){
		RespuestaJSON<List<MedicoEnTurno>> respuesta = new RespuestaJSON<List<MedicoEnTurno>>();
		List<MedicoEnTurno> unidades= null;
		
		try {
			unidades = umfService.getMedicosByUmf(idUmf);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		respuesta.setModelo(unidades);
		
		return respuesta;
	}
	
	@RequestMapping(value = "/getCodigosPostales", method = RequestMethod.POST)
	public @ResponseBody List<CodigoPostal> getCodigosPostales(@RequestBody UnidadMedicaFamiliar umf) {
		
		List<CodigoPostal> codigos = null;
		
		try {
			codigos = umfService.findCodigosPostalByUmf(umf.getIdUMF());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codigos;
	}
	
	@RequestMapping( value = "/getAsentamientos",method = RequestMethod.POST)
	public @ResponseBody List<Asentamiento> getAsentamientos(@RequestBody UnidadMedicaFamiliar umf) {
		
		List<Asentamiento> asentamientos = null;
		
		try {
			asentamientos = umfService.findAsentamientosByUmg(umf.getIdUMF());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return asentamientos;
	}
	
	/**
	 * Metodo para obtener los consultorios disponibles en un turno en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getConsultoriosEsp", method = RequestMethod.POST)
	public @ResponseBody List<Consultorio> getConsultoriosEsp(@RequestBody MedicoEnTurno umfTurno) {
		long idMedicoEspecialidad = MedicoEspecialidadEnum.GENERAL.getId();
		List<Consultorio> consultorios = null;
		try {
			consultorios = umfService.findConsultorioByUmfTurnoMedicoEsp(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(), umfTurno.getTurno().getIdTurno(), 
					idMedicoEspecialidad);			
		} catch (Exception e) {
			logger.error("Ocurrio un error al buscar consultorios", e);
		}
		
		return consultorios;
	}
	
	/**
	 * Metodo para obtener los medicos que atienden un consultorio, en un turno, en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getMedicosUmfT", method = RequestMethod.POST)
	public @ResponseBody List<MedicoEnTurno> getMedicosUmfTurno(@RequestBody MedicoEnTurno umfTurno) {
		
		List<MedicoEnTurno> medicos = null;
		
		try {
			//medicos = umfService.findMedicosByUmfTurno(umfTurno.getUnidadMedicaFamiliar().getIdUMF(), umfTurno.getTurno().getIdTurno());
			medicos = umfService.findMedicosPoblacionByUmfTurno(umfTurno.getUnidadMedicaFamiliar().getIdUMF(), umfTurno.getTurno().getIdTurno());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return medicos;
	}
	
	/**
	 * Metodo para obtener los medicos que atienden un consultorio, en un turno, en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getMedicosUmfTurnoMedicoEsp", method = RequestMethod.POST)
	public @ResponseBody List<MedicoEnTurno> getMedicosUmfTurnoConsultorioMedicoEsp(@RequestBody MedicoEnTurno umfTurno) {
		
		List<MedicoEnTurno> medicos=null;
		try {
			medicos = umfService.findMedicosByUmfTurnoConsultorioMedicoEsp(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(),
					umfTurno.getTurno().getIdTurno(),
					umfTurno.getConsultorio().getIdConsultorio(),
					MedicoEspecialidadEnum.GENERAL.getId());
		} catch (Exception e) {			
			logger.error("Ocurrio un error al buscar medicos", e);
		}
		
		return medicos;
	}
	
	/**
	 * Metodo para obtener las entidades federativas de una delegacion
	 * @param delegacion
	 * @return
	 */
	@RequestMapping( value = "/getEstadosByDelegacion", method = RequestMethod.POST)
	public @ResponseBody List<EntidadFederativa> getEstadosByDelegacion(@RequestBody BusquedaDelegacion busqueda) {
		
		List<EntidadFederativa> estados = null;
		
		try {
			estados = umfService.findEstadosByDelegacion(busqueda.getIdDelegacion());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return estados;
	}
	
	@RequestMapping( value = "/getMunicipiosByDelegacionEstado")
	public @ResponseBody List<Municipio> getMunicipiosByDelegacionEstado(@RequestBody BusquedaDelegacion busqueda) {
		
		List<Municipio> municipios = null;
		try {
			municipios = umfService.findMunicipiosByDelegacionEstado(busqueda.getIdDelegacion(), busqueda.getIdEstado());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return municipios;
	}
	
	@RequestMapping( value = "/getAsentamientosByDelEstMun")
	public @ResponseBody List<Asentamiento> getAsentamientoByDelegacionEstadoMunicipio(@RequestBody BusquedaDelegacion busqueda) {
		List<Asentamiento> asentamientos = null;
		try {
			asentamientos = umfService.finAsentamientosByDelegacionEstadoMunicipio(busqueda.getIdDelegacion(), busqueda.getIdEstado(), busqueda.getIdMunicipio());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return asentamientos;
	}
	
}
