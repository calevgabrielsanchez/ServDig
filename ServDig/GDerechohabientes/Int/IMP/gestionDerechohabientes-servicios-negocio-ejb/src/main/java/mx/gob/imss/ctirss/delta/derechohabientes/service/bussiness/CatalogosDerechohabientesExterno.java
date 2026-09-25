package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosDerechohabientesExternoRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.AsentamientoExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.ConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoExternoDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.enums.CodigoRespuestaServiciosExternosEnum;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.UmfResponse;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "catalogosDerechohabientesExterno", mappedName = "catalogosDerechohabientesExterno")
public class CatalogosDerechohabientesExterno extends AbstractServiceBusiness implements
		CatalogosDerechohabientesExternoRemote {

	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private UmfServiceLocal umfServiceLocal;
	
	@Override
	/**
	 * Metodo para obtener los antecedentes en una umf
	 * en caso de no existir ningun integrante en la umf se retornara un null
	 */
	public TurnoConsultorioResponse findAntecedentesEnUmf(Long idAsignacion,
			Long idUmf, Long idParentesco){
		
		if(idAsignacion == null || idUmf == null || idParentesco == null) {
			//throw new CatalogoException("Todos los datos son obligatorios (nss ,UMF, parentesco)",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return new TurnoConsultorioResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Todos los datos son obligatorios (nss ,UMF, parentesco)", null);
		}
		
		if(idAsignacion.intValue() == 0 || idUmf.intValue() == 0 || idParentesco.intValue() == 0) {
			//throw new CatalogoException("Ningun dato puede ser 0 (nss ,UMF, parentesco)",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return new TurnoConsultorioResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Ningun dato puede ser 0 (nss ,UMF, parentesco)", null);
		}
		
		TurnoConsultorioExternoDto respuesta = null;
		GrupoFamiliar busqueda = new GrupoFamiliar();
		busqueda.setAsignacionNSS(new AsignacionNSS(idAsignacion));
		busqueda.setParentesco(new Parentesco(idParentesco));
		busqueda.setMedicoEnTurno(new MedicoEnTurno());
		busqueda.getMedicoEnTurno().setUnidadMedicaFamiliar(new UnidadMedicaFamiliar(idUmf));
		
		Map<String, Object> resultado = null;
		try {
		resultado = grupoFamiliarServiceLocal.buscarMedicoEnTurnoActivo(busqueda);
		} catch(Exception e) {
			log.error("Ocurrio un error al buscar antecentes con el idAsignacion: " + idAsignacion + ", idUmf: " + idUmf + " y idParentesco: " + idParentesco);
			e.printStackTrace();
			//throw new CatalogoException("Ocurrio un error al buscar antecedentes", CatalogoException.ERROR_DE_SISTEMA);
			return new TurnoConsultorioResponse(CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), "Ocurrio un error al buscar antecedentes", null);
		}
		
		if(resultado != null) {
			Boolean encontrado = (Boolean)resultado.get("encontrado");
			
			if(encontrado) {
				MedicoEnTurno medico = (MedicoEnTurno) resultado.get("medico");
				
				respuesta = new TurnoConsultorioExternoDto(medico.getTurno().getIdTurno(), medico.getTurno().getDescripcion(), 
						medico.getConsultorio().getIdConsultorio(), medico.getIdMedicoContultorioTurno());
			}
		} 
		
		if(respuesta == null) {
			return new TurnoConsultorioResponse(CodigoRespuestaServiciosExternosEnum.NO_SE_ENCONTRARON_REGISTROS.getCodigo(), "No se lozalizaron antecedentes", null);
		}
		
		
		return new TurnoConsultorioResponse(respuesta);
	}

	/**
	 * Metodo para obtener los turnos disponibles en una UMF
	 * en caso de que no existan turnos se regresara null
	 */
	@Override
	public TurnoResponse findTurnosByUmf(Long idUmf){
		
		if(idUmf == null) {
			//throw new CatalogoException("La UMF es obligatoria para la consulta",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return new TurnoResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "La UMF es obligatoria para la consulta", null);
		}
		
		if(idUmf.intValue() == 0) {
			//throw new CatalogoException("La UMF no puede ser 0",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return new TurnoResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "La UMF no puede ser 0", null);
		}
		
		List<Turno> turnos = null;
		
		try {
			turnos = umfServiceLocal.getTurnosDisponiblesPorUmf(idUmf);
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al los turnos para el idUmf: " + idUmf);
			e.printStackTrace();
			//throw new CatalogoException("Ocurrio un error al buscar los turnos, " + e.getMessage(), CatalogoException.ERROR_DE_SISTEMA);
			return new TurnoResponse(CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), "Ocurrio un error al buscar los turnos, " + e.getMessage(), null);
		}
		
		if(turnos != null && !turnos.isEmpty()) {
			
			List<TurnoExternoDto> turnosDTO = new ArrayList<TurnoExternoDto>();
			for(Turno turno: turnos) {
				turnosDTO.add(new TurnoExternoDto(turno.getIdTurno(), turno.getDescripcion(), turno.getHoraInicioTurno(), turno.getHoraFinTurno()));
			}
			
			return new TurnoResponse(turnosDTO.toArray(new TurnoExternoDto[turnosDTO.size()]));
		}
		
		return new TurnoResponse(CodigoRespuestaServiciosExternosEnum.NO_SE_ENCONTRARON_REGISTROS.getCodigo(), "No se localizo ningun turno", null);
	}

	/**
	 * Metodo para obtener el consultorio con menor poblacion
	 */
	@Override
	public ConsultorioResponse getConsultorioMenorPoblacion(Long idUmf, Long idTurno) {
		ConsultorioResponse respuesta = null;
		
		if(idTurno == null || idUmf == null) {
			//throw new CatalogoException("Todos los datos son obligatorios (UMF, turno)",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return respuesta = new ConsultorioResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Todos los datos son obligatorios (UMF, turno)", null);
		}
		
		if(idUmf.intValue() == 0 || idTurno.intValue() == 0) {
			//throw new CatalogoException("Ningun dato puede ser 0 (UMF, turno)",CatalogoException.DATOS_ENTRADA_INVALIDOS);
			 return respuesta = new ConsultorioResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Ningun dato puede ser 0 (UMF, turno)", null);
		}
		
		try {
			Consultorio consultorio = umfServiceLocal.getConsultorioMenorPoblacion(idUmf, idTurno, false);
			if(consultorio != null){
				ConsultorioExternoDto consultorioDto = new ConsultorioExternoDto(consultorio.getIdConsultorio(), consultorio.getDescripcion(), consultorio.getPoblacion(), 
						consultorio.getIdUmfConsultorioTurnoMedico());
				respuesta = new ConsultorioResponse(consultorioDto);
			}else{
				return respuesta = new ConsultorioResponse(CodigoRespuestaServiciosExternosEnum.NO_SE_ENCONTRARON_REGISTROS.getCodigo(), "Sin registros", null);
			}
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al consultar el consultorio con menor poblacion para la umf : " + idUmf + " y el idTurno" + idTurno);
			e.printStackTrace();
			return respuesta = new ConsultorioResponse(CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), 
					"Ocurrio un error al buscar el consultorio con menor poblacion, " + e.getMessage(), null);

			//throw new CatalogoException("Ocurrio un error al buscar el consultorio con menor poblacion, " + e.getMessage(), CatalogoException.ERROR_DE_SISTEMA);
		}
		
		return respuesta;
	}
	
	@Override
	public UmfResponse consultarUMFAcentamiento(AsentamientoExternoDto asentamientoExterno){
		
		UmfResponse umfResponse = null;
		if (asentamientoExterno == null) {
			//throw new CatalogoException("El objeto no puede ser nulo", CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return  umfResponse = new UmfResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "El objeto no puede ser nulo", null);
		}
		
		if(StringUtils.isEmpty(asentamientoExterno.getCodigoPostal()) || StringUtils.isEmpty(asentamientoExterno.getCveAsentamiento()) ||
				StringUtils.isEmpty(asentamientoExterno.getCveEntidad()) || StringUtils.isEmpty(asentamientoExterno.getCveMunicipio())){
			//throw new CatalogoException("Todos los atributos son requeridos" , CatalogoException.DATOS_ENTRADA_INVALIDOS);
			return  umfResponse = new UmfResponse(CodigoRespuestaServiciosExternosEnum.DATOS_ENTRADA_INVALIDOS.getCodigo(), "Todos los atributos son requeridos", null);
			
		}
		
		
		List<UnidadMedicaFamiliar> lista = null;
		try {
			CodigoPostal codigo = new CodigoPostal();
			codigo.setCodigoPostal(asentamientoExterno.getCodigoPostal());
			
			EntidadFederativa entidad = new EntidadFederativa();
			entidad.setClave(asentamientoExterno.getCveEntidad());
		
			Municipio municipio = new Municipio();
			municipio.setClave(asentamientoExterno.getCveMunicipio());
			municipio.setEntidadFederativa(entidad);
			
			Asentamiento asentamiento = new Asentamiento();
			
		
			Localidad localidad = new Localidad();
			localidad.setMunicipio(municipio);
			
			asentamiento.setPeriodo(4L);
			asentamiento.setClave(asentamientoExterno.getCveAsentamiento());
			asentamiento.setCodigoPostal(codigo);
			asentamiento.setMunicipio(municipio);
			asentamiento.setLocalidad(localidad);
			
			

			
			lista = umfServiceLocal.findUmfsByAsentamiento(asentamiento, false);
			List<UnidadMedicaFamiliarTO> listaFinal = new ArrayList<UnidadMedicaFamiliarTO>();
			if(lista != null && !lista.isEmpty()){
				for(UnidadMedicaFamiliar umf :lista){
					UnidadMedicaFamiliarTO umfTO = new UnidadMedicaFamiliarTO();
					umfTO.setClavePresupuestal(umf.getClavePresupuestal());
					umfTO.setDescripcion(umf.getDescripcion());
					umfTO.setDesDireccion(umf.getDesDireccion());
					umfTO.setGeneracionCita(umf.getGeneracionCita());
					umfTO.setIdUMF(umf.getIdUMF());
					umfTO.setNivelAtencion(umf.getNivelAtencion());
					umfTO.setNoConsultorio(umf.getNoConsultorio());
					umfTO.setNoEconomico(umf.getNoEconomico());
					umfTO.setNombreCorto(umf.getNombreCorto());
					umfTO.setSubdelegacion(umf.getSubdelegacion());
					umfTO.setTipoUMF(umf.getTipoUMF());
					umfTO.setClavePresupuestal(umf.getClavePresupuestal());
					listaFinal.add(umfTO);
				}
				
				return umfResponse = new UmfResponse(listaFinal.toArray(new UnidadMedicaFamiliarTO[lista.size()]));
			}
			return  umfResponse = new UmfResponse(CodigoRespuestaServiciosExternosEnum.NO_SE_ENCONTRARON_REGISTROS.getCodigo(), "Sin registros", null);
			
			
		}catch (Exception e){
			log.error("Error en consultarUMFPorAsentamiento:", e);
			//throw new CatalogoException(e.getMessage(), CatalogoException.ERROR_DE_SISTEMA);
			return  umfResponse = new UmfResponse(CodigoRespuestaServiciosExternosEnum.ERROR_DE_SISTEMA.getCodigo(), "Error en consultarUMFPorAsentamiento:" 
			+ e.getMessage() , null);
			
		}

	}
	
}
