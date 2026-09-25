/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CaracterParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoProrrogaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteSimpleParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoProrrogaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoProrroga;

@Stateless(name = "prorrogaParserService", mappedName = "prorrogaParserService")
public class ProrrogaParserService extends AbstractServiceUtility implements ProrrogaParserServiceLocal{
	
	@EJB GrupoFamiliarParserServiceLocal grupoFamiliarParserServiceLocal;
	
	public DitProrroga modelToPersist(TramiteProrroga entrada) throws DerechohabientesBusinessException {
		DitProrroga salida = null;
		if (entrada != null) {
			try {
				salida = new DitProrroga();
				
				salida.setDitTramite(new DitTramite());
				salida.getDitTramite().setCveIdTramite(entrada.getTramiteId());
//				salida.setDitTramite(TramiteSimpleParser.modelToPersist(entrada.getTramite()));
				salida.setFecRegistroAlta(entrada.getFechaPresentacion());
				salida.setDicCaracter(CaracterParser.modelToPersist(entrada.getCaracter()));
				salida.setFecRegistroAlta(entrada.getFechaTramite());
				salida.setFecRegistroBaja(entrada.getFechaConclusion());
				salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
				salida.setFecInicioProrroga(entrada.getFechaInicioProrroga());
				salida.setFecFinProrroga(entrada.getFechaFinProrroga());
				salida.setDicEstadoProrroga(EstadoProrrogaParser.modelToPersist(entrada.getEstadoProrroga()));
				salida.setDitGrupoFamiliar(grupoFamiliarParserServiceLocal.modelToPersist(entrada.getGrupoFamiliar()));
				salida.setDicTipoProrroga(new DicTipoProrroga());
				
				Long tipoProrroga = entrada.getIdTipoProrroga();
				if(tipoProrroga == null) {
					tipoProrroga = this.getTipoProrrogaPorTipoTramite(entrada.getTipoTramite().getIdTipoTramite());
				}
				salida.getDicTipoProrroga().setCveIdTipoProrroga(tipoProrroga);
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_PRORROGA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PRORROGA+" | "+e.getMessage());
			}
			
		}
		
		
		return salida;

	}
	
	@Override
	public Long getTipoProrrogaPorTipoTramite(Integer idTipoTramite) {
		Long tipoProrroga = null;
		
		log.debug("Se parseara de tipo Tramite a tipo Prorroga");
		log.debug("El tipo de tramite es: " + idTipoTramite);
		if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo())) {
			log.debug(",  por estudios" );
			tipoProrroga = TipoProrrogaEnum.ESTUDIOS.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA.getCodigo())) {
			log.debug(",  por enfermedad" );
			tipoProrroga = TipoProrrogaEnum.ENFERMEDAD.getId();
		}  else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ENFERMEDAD_CRONICA_PSIQUICA_FISICA_25.getCodigo())) {
			log.debug(",  por enfermedad" );
			tipoProrroga = TipoProrrogaEnum.ENFERMEDAD.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_PERMANENTE.getCodigo())) {
			log.debug(",  permanente" );
			tipoProrroga = TipoProrrogaEnum.PERMANENTE.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_VIGENCIA_TEMPORAL.getCodigo())) {
			log.debug(",  temporal" );
			tipoProrroga = TipoProrrogaEnum.TEMPORAL.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_ACUERDOS_HCCD_HCT.getCodigo())) {
			log.debug(",  acuerdos" );
			tipoProrroga = TipoProrrogaEnum.ACUERDOS.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_SERVICIOS_OBSTETRICOS.getCodigo())) {
			log.debug(",  obstetricos" );
			tipoProrroga = TipoProrrogaEnum.OBSTETRICOS.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.PRORROGA_POR_LAUDO.getCodigo())) {
			log.debug(",  por laudo" );
			tipoProrroga = TipoProrrogaEnum.LAUDO.getId();
		}
		
		log.debug("el id tipo tramite es: " + idTipoTramite + " y el id tipo prorroga es: " + tipoProrroga);
		
		return tipoProrroga;
	}


	public TramiteProrroga persisToModel(DitProrroga entrada) throws DerechohabientesBusinessException {
		TramiteProrroga salida = null;
		if (entrada != null) {
			try {
				salida = new TramiteProrroga();
				salida.setCaracter(CaracterParser.persisToModel(entrada.getDicCaracter()));
				salida.setGrupoFamiliar(grupoFamiliarParserServiceLocal.persistToModel(entrada.getDitGrupoFamiliar()));
				
				if(entrada.getDitTramite() != null) {
					salida.setCveIdTramite(entrada.getDitTramite().getCveIdTramite());
					salida.setTramiteId(entrada.getDitTramite().getCveIdTramite());
					salida.setTramite(TramiteSimpleParser.persistToModel(entrada.getDitTramite()));
					salida.setTipoTramite(salida.getTramite() != null ? salida.getTramite().getTipoTramite() : null);
				} else {
					salida.setTipoTramite(new TipoTramite());
					salida.getTipoTramite().setDescripcion(entrada.getDicTipoProrroga().getDesTipoProrroga());
					salida.setTramite(new Tramite());
					salida.getTramite().setTipoTramite(salida.getTipoTramite());
				}
				
				salida.setPersona(salida.getGrupoFamiliar().getDerechohabiente());
				
				salida.setFechaTramite(entrada.getFecRegistroAlta());
				salida.setFechaPresentacion(entrada.getFecRegistroAlta());
				salida.setFechaConclusion(entrada.getFecRegistroBaja());
				salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
				salida.setFechaFinProrroga(entrada.getFecFinProrroga());
				salida.setFechaInicioProrroga(entrada.getFecInicioProrroga());
				salida.setPersona(new Fisica());
				salida.setEstadoProrroga(EstadoProrrogaParser.persisToModel(entrada.getDicEstadoProrroga()));
				
				
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PRORROGA+" | "+e.getMessage());
			}
			
			
		}

		return salida;

	}

	@Override
	public List<TramiteProrroga> persistToModelList(
			List<DitProrroga> ditProrrogas)
			throws DerechohabientesBusinessException {
		List<TramiteProrroga> prorrogas = new ArrayList<TramiteProrroga>();
		
		if(ditProrrogas != null && !ditProrrogas.isEmpty()) {
			for(DitProrroga ditProrroga : ditProrrogas) {
				prorrogas.add(this.persisToModel(ditProrroga));
			}
		}
		
		return prorrogas;
	}
}
