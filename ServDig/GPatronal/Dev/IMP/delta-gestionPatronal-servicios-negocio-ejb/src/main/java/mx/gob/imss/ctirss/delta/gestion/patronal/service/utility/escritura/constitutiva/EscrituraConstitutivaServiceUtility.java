package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.escritura.constitutiva;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Stateless
public class EscrituraConstitutivaServiceUtility extends ServiceUtility implements
		EscrituraConstitutivaServiceUtilityLocal {

	@Override
	public DitActaConstitutiva convertirModelToEntity(
			EscrituraConstitutiva model) throws Exception {
		DitActaConstitutiva ditActaConstitutiva = new DitActaConstitutiva();
		DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
		
		ditPersonaMoral.setCveIdPersonaMoral(model.getCveIdPersonaMoral());
		
		ditActaConstitutiva.setCveIdActConstitutiva(model.getCveEscrituraConstitutiva() != null ? model.getCveEscrituraConstitutiva().intValue() : 1);
		ditActaConstitutiva.setFecExpedicionActa(model.getFechaExpedicion());
		ditActaConstitutiva.setNumFolioMercantil(model.getFolioMercantil());
		ditActaConstitutiva.setNumEscritura(model.getNumEscritura() != null ? model.getNumEscritura() : "1");
		ditActaConstitutiva.setNumNotaria(model.getNumNotaria() != null ? model.getNumNotaria() : "1");
		ditActaConstitutiva.setDitPersonaMoral(ditPersonaMoral);
		
		if(model.getLugarExpedicion() != null){
			if (model.getLugarExpedicion().getEntidadFederativa() != null){
				if (model.getLugarExpedicion().getEntidadFederativa().getClave() != null){
					ditActaConstitutiva.setCveEnt(model.getLugarExpedicion().getEntidadFederativa().getClave());
				}
			}
		}
		
		if (model.getLugarExpedicion() != null){
			if (model.getLugarExpedicion().getClave() != null){
				ditActaConstitutiva.setCveMun(model.getLugarExpedicion().getClave());
			}
		}
		
		return ditActaConstitutiva;
	}

	@Override
	public EscrituraConstitutiva convertirEntityToModel(
			DitActaConstitutiva entity) {
		EscrituraConstitutiva escrituraConstitutiva = new EscrituraConstitutiva();
		
		System.err.println("Id escritura a setear: "+entity.getNumFolioMercantil());
		escrituraConstitutiva.setCveEscrituraConstitutiva(Long.valueOf( entity.getCveIdActConstitutiva()));
		escrituraConstitutiva.setFechaExpedicion(entity.getFecExpedicionActa());
		System.err.println("Folio mercantil a setear: "+entity.getNumFolioMercantil());
		escrituraConstitutiva.setFolioMercantil(entity.getNumFolioMercantil());
		System.err.println("Numero EscriturA a setear: "+entity.getNumEscritura());
		escrituraConstitutiva.setNumEscritura(entity.getNumEscritura() != null ? entity.getNumEscritura() : "");
		System.err.println("Numero EscriturA a setear: "+entity.getNumNotaria());
		escrituraConstitutiva.setNumNotaria(entity.getNumNotaria());
		System.err.println("Numero EscriturA a setear: "+entity.getDitPersonaMoral().getCveIdPersonaMoral());
		escrituraConstitutiva.setCveIdPersonaMoral(entity.getDitPersonaMoral() != null ? entity.getDitPersonaMoral().getCveIdPersonaMoral() : 0L);
		
		escrituraConstitutiva.setFoja(entity.getNumFoja());
		escrituraConstitutiva.setPartida(entity.getNumPartida());
		escrituraConstitutiva.setSeccion(entity.getNumSeccion());
		escrituraConstitutiva.setVolumen(entity.getNumVolumen());
		
		if(escrituraConstitutiva.getLugarExpedicion() == null){
			Municipio municipio = new Municipio();
			EntidadFederativa entidad = new EntidadFederativa();
			municipio.setEntidadFederativa(entidad);
			escrituraConstitutiva.setLugarExpedicion(municipio);
		}
		
		
		System.err.println("Numero entidad: "+entity.getCveEnt());
		System.err.println("Numero Mun: "+entity.getCveMun());
		escrituraConstitutiva.getLugarExpedicion().getEntidadFederativa().setClave(entity.getCveEnt());
		
		if (entity.getDgCatMunicipio() != null) {
			escrituraConstitutiva.getLugarExpedicion().setNombre(entity.getDgCatMunicipio().getNomMun());
			
			if (entity.getDgCatMunicipio().getDgCatEstado() != null) {
				escrituraConstitutiva.getLugarExpedicion().getEntidadFederativa().setNombre(entity.getDgCatMunicipio().getDgCatEstado().getNomEnt());
			}
		}
		escrituraConstitutiva.getLugarExpedicion().setClave(entity.getCveMun());
		
				
		return escrituraConstitutiva;
	}
	
	@Override
	public DitActaConstitutiva asignarvaloresFaltantes(
			EscrituraConstitutiva escrituraConstitutiva,
			DitActaConstitutiva ditActaConstitutiva) throws Exception {
		Date fechaActualizacion=Calendar.getInstance().getTime();
		if (ditActaConstitutiva == null){
			ditActaConstitutiva = new DitActaConstitutiva();
		}
		
		if (escrituraConstitutiva.getNumEscritura() != null){
			ditActaConstitutiva.setNumEscritura(escrituraConstitutiva.getNumEscritura().toString());
		}
		
		if (escrituraConstitutiva.getNumNotaria() != null){
			ditActaConstitutiva.setNumNotaria(escrituraConstitutiva.getNumNotaria().toString());
		}
		
		ditActaConstitutiva.setNumFolioMercantil(escrituraConstitutiva.getFolioMercantil());
		
		if (escrituraConstitutiva.getLugarExpedicion().getEntidadFederativa().getClave() != null){
			ditActaConstitutiva.setCveEnt(escrituraConstitutiva.getLugarExpedicion().getEntidadFederativa().getClave());
		}
		
		if (escrituraConstitutiva.getLugarExpedicion().getClave() != null){
			ditActaConstitutiva.setCveMun(escrituraConstitutiva.getLugarExpedicion().getClave());
		}
		
		if (ditActaConstitutiva.getFecRegistroAlta()== null){
			ditActaConstitutiva.setFecRegistroAlta(fechaActualizacion);
		}
		
		DitPersonaMoral pMoral = new DitPersonaMoral();
		pMoral.setCveIdPersonaMoral(escrituraConstitutiva.getCveIdPersonaMoral());
		
		ditActaConstitutiva.setDitPersonaMoral(pMoral);
		
		ditActaConstitutiva.setNumSeccion(escrituraConstitutiva.getSeccion());
		ditActaConstitutiva.setNumPartida(escrituraConstitutiva.getPartida());
		ditActaConstitutiva.setNumVolumen(escrituraConstitutiva.getVolumen());
		ditActaConstitutiva.setNumFoja(escrituraConstitutiva.getFoja());
		ditActaConstitutiva.setFecExpedicionActa(escrituraConstitutiva.getFechaExpedicion());
		ditActaConstitutiva.setFecRegistroActualizado(fechaActualizacion);
		
		return ditActaConstitutiva;
		
	}

}
