package mx.gob.imss.csdiss.sdroc.service.util;

import java.util.Date;

import mx.gob.imss.csdiss.sdroc.dto.*;
import mx.gob.imss.csdiss.sdroc.entity.*;

public final class ConvertUtil {
	
	public static RotInformacionObra toConvertRotInformacionObra(InformacionObraDTO informacionObraDTO){
		
		RotInformacionObra rotInformacionObra = new RotInformacionObra();
		
		rotInformacionObra.setCveRegistroObra(informacionObraDTO.getCveRegistroObra());
		//TODO corregir esto
		
		if(informacionObraDTO.getCveRegistroObraPrincipal() != null && !informacionObraDTO.getCveRegistroObraPrincipal().equals(0L))
			rotInformacionObra.setCveRegistroObraPrincipal(informacionObraDTO.getCveRegistroObraPrincipal());
		if (informacionObraDTO.isAplicaIncRemplazo()) {
			rotInformacionObra.setCveInformacionObra(informacionObraDTO.getCveInformacionObra());
			rotInformacionObra.setFecRegistroActualizado(new Date());
		}

		rotInformacionObra.setFecFinContrato(informacionObraDTO.getFecFinContrato());
		rotInformacionObra.setFecFinObra(informacionObraDTO.getFecFinObra());
		rotInformacionObra.setFecIniContrato(informacionObraDTO.getFecIniContrato());
		rotInformacionObra.setFecIniObra(informacionObraDTO.getFecIniObra());
		rotInformacionObra.setImpContratado(informacionObraDTO.getImpContratado());
		rotInformacionObra.setImpEjercido(informacionObraDTO.getImpEjercido());
		rotInformacionObra.setImpObra(informacionObraDTO.getImpObra());
		rotInformacionObra.setNumLicitacion(informacionObraDTO.getNumLicitacion());
		rotInformacionObra.setNumProcedimiento(informacionObraDTO.getNumProcedimiento());
		rotInformacionObra.setRefAcuseReg(informacionObraDTO.getRefAcuseReg());
		rotInformacionObra.setRefObservacion(informacionObraDTO.getRefObservacion());
		rotInformacionObra.setRefSupConstruccion(informacionObraDTO.getRefSupConstruccion());
		rotInformacionObra.setCveIdTramite(informacionObraDTO.getCveIdTramite());
		rotInformacionObra.setNumSeqNotaria(informacionObraDTO.getNumSeqNotaria());
		rotInformacionObra.setNumActualiza(0);
		rotInformacionObra.setRefOtroObjetoContrato(informacionObraDTO.getRefOtroObjetoContrato());
		rotInformacionObra.setNumAproxTrabajadores(informacionObraDTO.getNumAproxTrabajadores());
		rotInformacionObra.setNumRegStps(informacionObraDTO.getNumRegStps());

		rotInformacionObra.setFecRegistroAlta(new Date());
		rotInformacionObra.setRocEstatusObra(toConvertRocEstatusObra(informacionObraDTO.getEstatusObraDTO()));
		rotInformacionObra.setRocSubdelegacion(toConvertRocSubdelegacion(informacionObraDTO.getSubDelegacionDTO()));
		rotInformacionObra.setRocTipoObra(toConvertRocTipoObra(informacionObraDTO.getTipoObraDTO()));
		rotInformacionObra.setRocObjetoContrato(toConvertRocObjetoContrato(informacionObraDTO.getObjetoContratoDTO()));
		rotInformacionObra.setRefObjetoContratoSubEsp(informacionObraDTO.getDesObjetoContratoSubEsp());
		rotInformacionObra.setRotInformacionPatron(toConvertRotInformacionPatron(informacionObraDTO.getInformacionPatronDTO()));
		rotInformacionObra.setRotUbicacionObras(toConvertRotUbicacionObra(informacionObraDTO.getUbicacionObraDTO()));
		rotInformacionObra.setCveObraRemplazado(informacionObraDTO.getCveObraRemplazado());
		return rotInformacionObra;
	}
	
	public static RocEstatusObra toConvertRocEstatusObra(EstatusObraDTO estatusObraDTO){
		
		RocEstatusObra rocEstatusObra = new RocEstatusObra();
		rocEstatusObra.setCveEstatusObra(estatusObraDTO.getCveEstatusObra());		
		return rocEstatusObra;
	}
	
	public static RocSubdelegacion toConvertRocSubdelegacion(SubDelegacionDTO subDelegacionDTO){
		
		RocSubdelegacion rocSubdelegacion = new RocSubdelegacion();
		rocSubdelegacion.setCveSubdelegacion(subDelegacionDTO.getCveSubdelegacion());
		return rocSubdelegacion;
	}
	
	public static RocTipoObra toConvertRocTipoObra(TipoObraDTO tipoObraDTO){
		
		RocTipoObra rocTipoObra = new RocTipoObra();		
		rocTipoObra.setCveTipoObra(tipoObraDTO.getCveTipoObra());
		
		return rocTipoObra;
		
	}
	
	public static RocObjetoContrato toConvertRocObjetoContrato(ObjetoContratoDTO objetoContratoDTO){
		
		RocObjetoContrato objetoContrato = new RocObjetoContrato();		
		objetoContrato.setCveObjetoContrato(objetoContratoDTO.getCveObjetoContrato());
		
		return objetoContrato;		
		
	}

	public static RotInformacionPatron toConvertRotInformacionPatron(InformacionPatronDTO informacionPatronDTO){
		RotInformacionPatron rotInformacionPatron = new RotInformacionPatron();
		
		RocTipoPersona rocTipoPersona = new RocTipoPersona();
		rocTipoPersona.setCveTipoPersona(informacionPatronDTO.getTipoPersonaDTO().getCveTipoPersona());
		
		RocTipoPatron rocTipoPatron = new RocTipoPatron();
		rocTipoPatron.setCveTipoPatron(informacionPatronDTO.getTipoPatronDTO().getCveTipoPatron());
		
		rotInformacionPatron.setRocTipoPatron(rocTipoPatron);
		rotInformacionPatron.setRocTipoPersona(rocTipoPersona);;
		rotInformacionPatron.setCveRfc(informacionPatronDTO.getCveRfc());
		rotInformacionPatron.setCveRegPatronal(informacionPatronDTO.getCveRegPatronal());
		rotInformacionPatron.setRefRazonSocial(informacionPatronDTO.getRefRazonSocial());
		
		return rotInformacionPatron;
	}
	
	public static RotUbicacionObra toConvertRotUbicacionObra(UbicacionObraDTO ubicacionObraDTO){
		RotUbicacionObra rotUbicacionObra = new RotUbicacionObra();
		rotUbicacionObra.setCalle(ubicacionObraDTO.getCalle());
		rotUbicacionObra.setCodigoPostal(ubicacionObraDTO.getCodigoPostal());
		if(ubicacionObraDTO.getNumExterior() != null)
			rotUbicacionObra.setNumExterior(Integer.parseInt(ubicacionObraDTO.getNumExterior()));
		if(ubicacionObraDTO.getNumExteriorDos() != null)
			rotUbicacionObra.setNumExteriorDos(Integer.parseInt(ubicacionObraDTO.getNumExteriorDos()));
		rotUbicacionObra.setNumExteriorAlf(ubicacionObraDTO.getNumExteriorAlf());
		rotUbicacionObra.setCveEntidad(ubicacionObraDTO.getCveEntidad());
		rotUbicacionObra.setCveLocalidad(ubicacionObraDTO.getCveLocalidad());
		rotUbicacionObra.setCveMunicipio(ubicacionObraDTO.getCveMunicipio());
		if(ubicacionObraDTO.getNumInterior() != null)
			rotUbicacionObra.setNumInterior(Integer.parseInt(ubicacionObraDTO.getNumInterior()));
		rotUbicacionObra.setNumInteriorAlf(ubicacionObraDTO.getNumInteriorAlf());
		rotUbicacionObra.setRefEntidad(ubicacionObraDTO.getRefEntidad());
		rotUbicacionObra.setRefMunicipio(ubicacionObraDTO.getRefMunicipio());
		rotUbicacionObra.setRefColonia(ubicacionObraDTO.getRefColonia().toUpperCase());
		rotUbicacionObra.setRefObservacion(ubicacionObraDTO.getRefObservacion());
		
		return rotUbicacionObra;
	}
	
	public static RotInformacionIncidencia toConvertInformacionIncidencia(InformacionIncidenciaDTO incidenciaDTO){
		
		RotInformacionIncidencia rotInformacionIncidencia = new RotInformacionIncidencia();
		
		rotInformacionIncidencia.setCveInformacionObra(incidenciaDTO.getCveInformacionObra());
		rotInformacionIncidencia.setFecFinObra(incidenciaDTO.getFecFinObra());
		rotInformacionIncidencia.setFecActualizacion(incidenciaDTO.getFecActualizacion());
		rotInformacionIncidencia.setFecCanObra(incidenciaDTO.getFecCanObra());
		rotInformacionIncidencia.setFecReanudacion(incidenciaDTO.getFecReanudacion());
		rotInformacionIncidencia.setFecSuspencion(incidenciaDTO.getFecSuspencion());
		rotInformacionIncidencia.setImpEjercido(incidenciaDTO.getImpEjercido());
		rotInformacionIncidencia.setImpObra(incidenciaDTO.getImpObra());
		rotInformacionIncidencia.setRefAcuseInc(incidenciaDTO.getRefAcuseInc());
		rotInformacionIncidencia.setRefSupConstruccion(incidenciaDTO.getRefSupConstruccion());
		rotInformacionIncidencia.setFecActualizacion(incidenciaDTO.getFecActualizacion());
		rotInformacionIncidencia.setStpRegIncidencia(new Date());
		
		RocTipoRegistro  rocTipoRegistro = new RocTipoRegistro();		
		rocTipoRegistro.setCveTipoRegistro(incidenciaDTO.getTipoRegistroDTO().getCveTipoRegistro());
		rotInformacionIncidencia.setRocTipoRegistro(rocTipoRegistro);
		
		
		RocMotivoIncidencia rocMotivoTipoIncidencia = new RocMotivoIncidencia();

		RocMotivo rocMotivo = new RocMotivo();
		rocMotivo.setCveMotivo(incidenciaDTO.getMotivoTipoIncidenciaDTO().getMotivoDTO().getCveMotivo());					
		rocMotivoTipoIncidencia.setRocMotivo(rocMotivo);
		
		RocTipoIncidencia rocTipoIncidencia = new RocTipoIncidencia();
		rocTipoIncidencia.setCveTipoIncidencia(incidenciaDTO.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia());
		
		rocMotivoTipoIncidencia.setRocTipoIncidencia(rocTipoIncidencia);
		rotInformacionIncidencia.setRocMotivoTipoIncidencia(rocMotivoTipoIncidencia);
		
		rotInformacionIncidencia.setCveIdTramite(incidenciaDTO.getCveIdTramite());
		rotInformacionIncidencia.setSecNotaria(incidenciaDTO.getNumSeqNotaria());
		
		return rotInformacionIncidencia;
		
		
	}
	

	public static RotAvisoObra toConvertRotAvisoObra(AvisoObraDTO avisoObraDTO){
		
		RotAvisoObra rotAvisoObra = new RotAvisoObra();
		
		rotAvisoObra.setCveRegistroAvisoObra(avisoObraDTO.getCveRegistroAvisoObra());
		rotAvisoObra.setCveRfcPatron(avisoObraDTO.getCveRfcPatron());
		rotAvisoObra.setFecFinObra(avisoObraDTO.getFecFinObra());
		rotAvisoObra.setFecIniObra(avisoObraDTO.getFecIniObra());
		rotAvisoObra.setImpObra(avisoObraDTO.getImpObra());
		rotAvisoObra.setCveIdTramite(avisoObraDTO.getCveIdTramite());
		rotAvisoObra.setNumSeqNotaria(avisoObraDTO.getNumSeqNotaria());
		rotAvisoObra.setRefEstadoReg(Boolean.TRUE);
		
		rotAvisoObra.setRocSubdelegacion(toConvertRocSubdelegacion(avisoObraDTO.getSubDelegacionDTO()));
		rotAvisoObra.setRotInformacionPatron(toConvertRotInformacionPatron(avisoObraDTO.getInformacionPatronDTO()));
		rotAvisoObra.setRotUbicacionObras(toConvertRotUbicacionObra(avisoObraDTO.getUbicacionObraDTO()));
		
		return rotAvisoObra;
	}
	
	
}
