package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoSolicitudParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SubdelegacionIMSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoSolicitudParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;

import org.apache.log4j.Logger;

@Stateless(name = "solicitudParserUtility", mappedName = "solictudParserUtility")
public class SolicitudParser extends AbstractServiceUtility implements SolicitudParserLocal{

	private static final Logger logger = Logger.getLogger(SolicitudParser.class);

	public DitSolicitud modelToPersist(Solicitud entrada) throws DerechohabientesBusinessException{
		DitSolicitud salida=null;
		if(entrada!=null){
			try {
				salida=new DitSolicitud();
				//salida.setCveIdSolicitud(entrada.getNoFolioSolicitud());
				if(entrada.getSolicitudId()!=null)
					salida.setCveIdSolicitud(entrada.getSolicitudId());
				if(entrada.getRazonCancelacion()!=null){
					salida.setDicRazonCancelacion(new DicRazonCancelacion());
					salida.getDicRazonCancelacion().setCveIdRazonCancelacion(entrada.getRazonCancelacion().getIdRazonCancelacion());
				}
				salida.setFecCita(entrada.getFechaCita());
				salida.setFecSolicitud(entrada.getFechaSolicitud());
				salida.setFecRegistroAlta(entrada.getFechaPresentacion());
				salida.setFecPresentacion(entrada.getFechaPresentacion());
				salida.setFecRegistroActualizado(entrada.getFechaActualizacion());
				salida.setFecRegistroBaja(entrada.getFechaBaja());
				salida.setRefFolio(entrada.getNoFolioSolicitud());
				salida.setRefObservacion(entrada.getObservacion());	
				//salida.setDitUmfTurno(UmfTurnoParser.modelToPersist(entrada.getUmfTurno()));

				if(entrada.getEstadoSolicitud()!=null){
					salida.setDicEstadoSolicitud(new DicEstadoSolicitud());
					salida.getDicEstadoSolicitud().setCveIdEstadoSolicitud(entrada.getEstadoSolicitud().getIdEstadoSolicitud().longValue());
				}
				if(entrada.getTipoSolicitud()!=null){
					salida.setDicTipoSolicitud(new DicTipoSolicitud());
					salida.getDicTipoSolicitud().setCveIdTipoSolicitud(entrada.getTipoSolicitud().getIdTipoSolicitud());
				}
				//TODO esta parte se tiene que modificar cuando se arregle la relacion con usuario
				/*if(entrada.getSolicitante()!=null){
					salida.setDitUsuario(new DitUsuario());
					salida.getDitUsuario().setCveIdUsuario(11L);
				}*/
				salida.setDitTramites(new ArrayList<DitTramite>());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_SOLICITUD, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SOLICITUD+" | "+e.getMessage());
			}

		}
		return salida;	
	}

	public Solicitud persisToModel(DitSolicitud entrada) throws DerechohabientesBusinessException{
		Solicitud salida=null;
		if(entrada!=null){
			try {
				salida=new Solicitud();
				salida.setSolicitudId(entrada.getCveIdSolicitud());
				salida.setFechaCita(entrada.getFecCita());
				salida.setFechaSolicitud(entrada.getFecSolicitud());
				salida.setFechaPresentacion(entrada.getFecPresentacion());
				salida.setFechaActualizacion(entrada.getFecRegistroActualizado());
				salida.setFechaBaja(entrada.getFecRegistroAlta());
				salida.setNoFolioSolicitud(entrada.getRefFolio());
				salida.setObservacion(entrada.getRefObservacion());
				if(entrada.getDitUmfTurno() != null) {
					log.debug("Se encontraron dados de cita");
					DitUmfTurno ditUmfTurno = entrada.getDitUmfTurno();
					CitaSolicitud cita = new CitaSolicitud();
					cita.setUmf(this.convertirUmf(ditUmfTurno.getDicUmf()));
					cita.setTurno(this.convertirTurno(ditUmfTurno.getDicTurno()));
					cita.setFechaHora(entrada.getFecCita());

					salida.setCitaSolicitud(cita);
					salida.setFechaCita(cita.getFechaHora());
				}

				String cveUsuario = entrada.getCveIdUsuario();
				if(cveUsuario != null && !cveUsuario.isEmpty()){
					Usuario objUsuario = new Usuario();
					objUsuario.setUsuario(cveUsuario);
					salida.setSolicitante(objUsuario);	
				}

				if(entrada.getDicOrigenSolicitud() != null) {
					salida.setOrigenSolicitud(new OrigenSolicitud());
					salida.getOrigenSolicitud().setIdTipoSolicitud(entrada.getDicOrigenSolicitud().getCveIdOrigenSolicitud());
					salida.getOrigenSolicitud().setDescripcion(entrada.getDicOrigenSolicitud().getDesOrigenSolicitud());
				}
				salida.setTipoSolicitud(TipoSolicitudParser.persisToModel(entrada.getDicTipoSolicitud()));
				salida.setEstadoSolicitud(EstadoSolicitudParser.persisToModel(entrada.getDicEstadoSolicitud()));			
				if(entrada.getDicRazonCancelacion()!=null){
					salida.setRazonCancelacion(new RazonCancelacion());
					salida.getRazonCancelacion().setIdRazonCancelacion(entrada.getDicRazonCancelacion().getCveIdRazonCancelacion());
					salida.getRazonCancelacion().setDescripcion(entrada.getDicRazonCancelacion().getDesRazonCancelacion());
				}
				//				salida.setAseguradoPensionado(PersonaParser.persisToModel(entrada.getDitPersona()));
				List<Tramite> tramites = new ArrayList<Tramite>();
				for(DitTramite tramitePersona : entrada.getDitTramites())
					tramites.add(TramiteParser.persistTomodelSimple(tramitePersona));
				salida.setTramites(tramites);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SOLICITUD+" | "+e.getMessage());
			}	

		}
		return salida;
	}

	public Solicitud persisToModelSimple(DitSolicitud entrada) throws DerechohabientesBusinessException{
		Solicitud salida=null;
		if(entrada!=null){
			try {
				salida=new Solicitud();
				salida.setSolicitudId(entrada.getCveIdSolicitud());
				salida.setFechaCita(entrada.getFecCita());
				salida.setFechaSolicitud(entrada.getFecSolicitud());
				salida.setFechaPresentacion(entrada.getFecPresentacion());
				salida.setFechaSolicitud(entrada.getFecRegistroAlta());
				salida.setNoFolioSolicitud(entrada.getRefFolio());
				salida.setObservacion(entrada.getRefObservacion());
				if(entrada.getDitUmfTurno() != null) {
					log.debug("Se encontraron dados de cita");
					DitUmfTurno ditUmfTurno = entrada.getDitUmfTurno();
					CitaSolicitud cita = new CitaSolicitud();
					cita.setUmf(this.convertirUmf(ditUmfTurno.getDicUmf()));
					cita.setTurno(this.convertirTurno(ditUmfTurno.getDicTurno()));
					cita.setFechaHora(entrada.getFecCita());

					salida.setCitaSolicitud(cita);
					salida.setFechaCita(cita.getFechaHora());
				}

				String cveUsuario = entrada.getCveIdUsuario();
				if(cveUsuario != null && !cveUsuario.isEmpty()){
					Usuario objUsuario = new Usuario();
					objUsuario.setUsuario(cveUsuario);
					salida.setSolicitante(objUsuario);	
				}

				if(entrada.getDicOrigenSolicitud() != null) {
					salida.setOrigenSolicitud(new OrigenSolicitud());
					salida.getOrigenSolicitud().setIdTipoSolicitud(entrada.getDicOrigenSolicitud().getCveIdOrigenSolicitud());
					salida.getOrigenSolicitud().setDescripcion(entrada.getDicOrigenSolicitud().getDesOrigenSolicitud());
				}
				salida.setTipoSolicitud(TipoSolicitudParser.persisToModel(entrada.getDicTipoSolicitud()));
				salida.setEstadoSolicitud(EstadoSolicitudParser.persisToModel(entrada.getDicEstadoSolicitud()));			
				if(entrada.getDicRazonCancelacion()!=null){
					salida.setRazonCancelacion(new RazonCancelacion());
					salida.getRazonCancelacion().setIdRazonCancelacion(entrada.getDicRazonCancelacion().getCveIdRazonCancelacion());
					salida.getRazonCancelacion().setDescripcion(entrada.getDicRazonCancelacion().getDesRazonCancelacion());
				}
				//				salida.setAseguradoPensionado(PersonaParser.persisToModel(entrada.getDitPersona()));
				List<Tramite> tramites = new ArrayList<Tramite>();
				for(DitTramite tramitePersona : entrada.getDitTramites())
					tramites.add(TramiteParser.persistTomodelSimple(tramitePersona));
				salida.setTramites(tramites);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SOLICITUD+" | "+e.getMessage());
			}	

		}
		return salida;
	}

	public List<Solicitud> PersistToModelList(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException{
		List<Solicitud> salidaList=new ArrayList<Solicitud>();
		if(entradaList.size() > 0){
			for(DitSolicitud entrada:entradaList){
				salidaList.add(persisToModel(entrada));
			}
		}

		return salidaList;
	}
	//solo parsea la informacion basica de las listas
	public List<Solicitud> PersistToModelListPartial(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException{
		List<Solicitud> salidaList=new ArrayList<Solicitud>();
		if(entradaList.size() > 0){
			for(DitSolicitud entrada:entradaList){
				salidaList.add(persisToModelPartial(entrada));
			}
		}

		return salidaList;
	}

	//solo parsea la informacion basica de las listas
	public List<SolicitudNssDto> PersistToModelListPartialNss(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException{
		List<SolicitudNssDto> salidaList=new ArrayList<SolicitudNssDto>();
		if(entradaList.size() > 0){
			for(DitSolicitud entrada:entradaList){
				salidaList.add(persisToModelPartialNss(entrada));
			}
		}

		return salidaList;
	}

	//parsea solo la info basica
	public SolicitudNssDto persisToModelPartialNss(DitSolicitud entrada) throws DerechohabientesBusinessException{
		SolicitudNssDto salida=null;
		if(entrada!=null){
			try {
				salida=new SolicitudNssDto();
				salida.setSolicitudId(entrada.getCveIdSolicitud());
				salida.setFechaCita(entrada.getFecCita());
				salida.setFechaSolicitud(entrada.getFecSolicitud());
				salida.setNoFolioSolicitud(entrada.getRefFolio());
				
				if(entrada.getDitUmfTurno() != null) {
					log.debug("Se encontraron dados de cita");
					DitUmfTurno ditUmfTurno = entrada.getDitUmfTurno();
					CitaSolicitud cita = new CitaSolicitud();
					cita.setUmf(this.convertirUmf(ditUmfTurno.getDicUmf()));
					cita.setTurno(this.convertirTurno(ditUmfTurno.getDicTurno()));
					cita.setFechaHora(entrada.getFecCita());

					salida.setCitaSolicitud(cita);
					salida.setFechaCita(cita.getFechaHora());
				}

				String cveUsuario = entrada.getCveIdUsuario();
				if(cveUsuario != null && !cveUsuario.isEmpty()){
					Usuario objUsuario = new Usuario();
					objUsuario.setUsuario(cveUsuario);
					salida.setSolicitante(objUsuario);	
				}

				if(entrada.getDicOrigenSolicitud() != null) {
					salida.setOrigenSolicitud(new OrigenSolicitud());
					salida.getOrigenSolicitud().setIdTipoSolicitud(entrada.getDicOrigenSolicitud().getCveIdOrigenSolicitud());
					salida.getOrigenSolicitud().setDescripcion(entrada.getDicOrigenSolicitud().getDesOrigenSolicitud());
				}
				salida.setTipoSolicitud(TipoSolicitudParser.persisToModel(entrada.getDicTipoSolicitud()));
				salida.setEstadoSolicitud(EstadoSolicitudParser.persisToModel(entrada.getDicEstadoSolicitud()));
				salida.setTramites(TramiteParser.persisToModelListPendAut(entrada.getDitTramites()));
				salida.setFechaActualizacion(entrada.getFecRegistroActualizado());
				salida.setFechaPresentacion(entrada.getFecRegistroAlta());
				String nss = entrada.getDitPersonaInteresadaSols() != null && !entrada.getDitPersonaInteresadaSols().isEmpty() ? entrada.getDitPersonaInteresadaSols().get(0).getDitPersona().getDitAsignacionNsses().get(0).getNumNss() : "";
				salida.setNumNss(nss);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SOLICITUD+" | "+e.getMessage());
			}	

		}
		return salida;
	}

	//parsea solo la info basica
	public Solicitud persisToModelPartial(DitSolicitud entrada) throws DerechohabientesBusinessException{
		Solicitud salida=null;
		if(entrada!=null){
			try {
				salida=new Solicitud();
				salida.setSolicitudId(entrada.getCveIdSolicitud());
				salida.setFechaCita(entrada.getFecCita());
				salida.setFechaSolicitud(entrada.getFecSolicitud());
				salida.setNoFolioSolicitud(entrada.getRefFolio());
				salida.setFechaCita(entrada.getFecCita());
				if(entrada.getDitUmfTurno() != null) {
					log.debug("Se encontraron dados de cita");
					DitUmfTurno ditUmfTurno = entrada.getDitUmfTurno();
					CitaSolicitud cita = new CitaSolicitud();
					cita.setUmf(this.convertirUmf(ditUmfTurno.getDicUmf()));
					cita.setTurno(this.convertirTurno(ditUmfTurno.getDicTurno()));
					cita.setFechaHora(entrada.getFecCita());

					salida.setCitaSolicitud(cita);
					salida.setFechaCita(cita.getFechaHora());
				}

				String cveUsuario = entrada.getCveIdUsuario();
				if(cveUsuario != null && !cveUsuario.isEmpty()){
					Usuario objUsuario = new Usuario();
					objUsuario.setUsuario(cveUsuario);
					salida.setSolicitante(objUsuario);	
				}

				if(entrada.getDicOrigenSolicitud() != null) {
					salida.setOrigenSolicitud(new OrigenSolicitud());
					salida.getOrigenSolicitud().setIdTipoSolicitud(entrada.getDicOrigenSolicitud().getCveIdOrigenSolicitud());
					salida.getOrigenSolicitud().setDescripcion(entrada.getDicOrigenSolicitud().getDesOrigenSolicitud());
				}
				
				salida.setTipoSolicitud(TipoSolicitudParser.persisToModel(entrada.getDicTipoSolicitud()));
				salida.setEstadoSolicitud(EstadoSolicitudParser.persisToModel(entrada.getDicEstadoSolicitud()));
				salida.setTramites(TramiteParser.persisToModelListPendAut(entrada.getDitTramites()));
				salida.setFechaPresentacion(entrada.getFecPresentacion());
				salida.setFechaActualizacion(entrada.getFecRegistroActualizado());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_SOLICITUD+" | "+e.getMessage());
			}	

		}
		return salida;
	}

	private UnidadMedicaFamiliar convertirUmf(DicUmf dicUmf) {
		UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
		umf.setIdUMF(dicUmf.getCveIdUmf());
		umf.setNombreCorto(dicUmf.getNomCorto());
		umf.setDescripcion(dicUmf.getNomUnidad());
		umf.setNoEconomico(dicUmf.getNumEconom());
		try {
			umf.setSubdelegacion(SubdelegacionIMSSParser.persisToModel(dicUmf.getDicSubdelegacion()));
		} catch (DerechohabientesBusinessException e) {
			log.error("no se pudo convertir la subdelegacion", e);
		}

		return umf;
	}

	private Turno convertirTurno(final DicTurno dicTurno) {
		Turno turno = null; // NOPMD
		if (dicTurno != null) {
			turno = new Turno();
			turno.setIdTurno(dicTurno.getCveIdTurno());
			turno.setDescripcion(dicTurno.getDesDescripcion());
			turno.setHoraInicioTurno(dicTurno.getRefHoraInicioTurno());
			turno.setHoraFinTurno(dicTurno.getRefHoraFinTurno());
		}
		return turno;
	}

}
