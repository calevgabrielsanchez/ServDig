package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaConsultaDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.PeriodoDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadDTO;

public class MapeoRetroUtil {
	
	public static ValidaRetroactividadDTO mapeoResponseDTO( mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadDTO dto) {
		ValidaRetroactividadDTO response = new ValidaRetroactividadDTO();
		
		response.setIdCalculo(dto.getIdCalculo());
		response.setIdTramite(dto.getIdTramite());
		response.setNss(dto.getNss());
		response.setModalidad(dto.getModalidad());
		response.setAplicaRetroactividad(dto.getAplicaRetroactividad());
		response.setAplicaRenovacion(dto.getAplicaRenovacion());
		response.setFechaInicio(dto.getFechaInicio());
		response.setFechaFin(dto.getFechaFin());
		response.setNumeroMeses(dto.getNumeroMeses());
		response.setFechaCorteValidacion(dto.getFechaCorteValidacion());
		response.setFechaLimiteElegibilidad(dto.getFechaLimiteElegibilidad());
		response.setSalarioPiso(dto.getSalarioPiso());
		response.setSalarioTope(dto.getSalarioTope());
		response.setMensaje(dto.getMensaje());		
		
		return response;
	}

	public static CalculoDTO mapeoResponseDTO(
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoDTO origen) {
		if (origen == null) {
			return null;
		}
		mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoDTO destino =
				new mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoDTO();
		destino.setIdCalculo(origen.getIdCalculo());
		destino.setIdCotizacion(origen.getIdCotizacion());
		destino.setIdTramite(origen.getIdTramite());
		destino.setNss(origen.getNss());
		destino.setMunicipio(origen.getMunicipio());
		destino.setMoneda(origen.getMoneda());
		destino.setNumeroPeriodos(origen.getNumeroPeriodos());
		destino.setImporteTotal(origen.getImporteTotal());
		destino.setCalculoProvisional(origen.getCalculoProvisional());
		destino.setMensaje(origen.getMensaje());

		destino.setPeriodos(convertirPeriodos(origen.getPeriodos()));
		return destino;
	}

	private static List<PeriodoDTO> convertirPeriodos(
			List<mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.PeriodoDTO> origen) {
		if (origen == null) {
			return null;
		}
		List<PeriodoDTO> destino = new ArrayList<PeriodoDTO>();
		for (mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.PeriodoDTO periodo : origen) {
			destino.add(convertirPeriodoDTO(periodo));
		}
		return destino;
	}


	private static PeriodoDTO convertirPeriodoDTO(
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.PeriodoDTO origen) {
		if (origen == null) {
			return null;
		}
		PeriodoDTO destino = new PeriodoDTO();
		destino.setIdPagoPeriodo(origen.getIdPagoPeriodo());
		destino.setAnio(origen.getAnio());
		destino.setMes(origen.getMes());
		destino.setFechaInicio(origen.getFechaInicio());
		destino.setFechaFin(origen.getFechaFin());
		destino.setImporteBase(origen.getImporteBase());
		destino.setImporteActualizacion(origen.getImporteActualizacion());
		destino.setImporteRecargo(origen.getImporteRecargo());
		destino.setImportePago(origen.getImportePago());
		destino.setSalarioElegido(origen.getSalarioElegido());
		return destino;
	}


	public static GeneracionMultilineaDTO mapeoResponseDTO(
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaDTO origen) {

		if (origen == null) {
			return null;
		}

		GeneracionMultilineaDTO destino = new GeneracionMultilineaDTO();

		destino.setIdSolicitud(origen.getIdSolicitud());
		destino.setIdCalculo(origen.getIdCalculo());
		destino.setCorrelacion(origen.getCorrelacion());
		destino.setEstado(origen.getEstado());
		destino.setNumeroPeriodos(origen.getNumeroPeriodos());
		destino.setPeriodosProcesados(origen.getPeriodosProcesados());
		destino.setUrlConsulta(origen.getUrlConsulta());

		return destino;
	}

	public static GeneracionMultilineaConsultaDTO mapeoResponseDTO(
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaConsultaDTO origen) {
		if (origen == null) {
			return null;
		}
		GeneracionMultilineaConsultaDTO destino = new GeneracionMultilineaConsultaDTO();
		destino.setIdSolicitud(origen.getIdSolicitud());
		destino.setIdCalculo(origen.getIdCalculo());
		destino.setCorrelacion(origen.getCorrelacion());
		destino.setEstado(origen.getEstado());
		destino.setFinalizada(origen.getFinalizada());
		destino.setNumeroPeriodos(origen.getNumeroPeriodos());
		destino.setPeriodosProcesados(origen.getPeriodosProcesados());
		destino.setIntento(origen.getIntento());
		destino.setSiguienteIntento(origen.getSiguienteIntento());
		destino.setCodigoRespuesta(origen.getCodigoRespuesta());
		destino.setMensaje(origen.getMensaje());
		destino.setResultado(origen.getResultado());
		destino.setPdfBase64(origen.getPdfBase64());
		destino.setMultilinea(origen.getMultilinea());
		destino.setFechaRespuesta(origen.getFechaRespuesta());
		return destino;
	}
	
}
