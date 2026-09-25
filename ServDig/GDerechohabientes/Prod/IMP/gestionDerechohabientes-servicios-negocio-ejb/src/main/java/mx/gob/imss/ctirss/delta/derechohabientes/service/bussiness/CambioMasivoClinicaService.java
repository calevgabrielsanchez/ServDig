package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CambioMasivoClinicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DelegacionDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DerechohabienteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudCitaEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfCodigoPostalDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MedicoEnTurnoParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioMasivoClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsentamientoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CodigoPostalParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.DomicilioParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TurnoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitCambioMasivoClinica;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;

import org.apache.log4j.Logger;

/**
 * @author Victor Manuel Camacho Guerra
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 21/04/2012
 */
@Stateless( name = "cambioMasivoClinicaService", mappedName = "cambioMasivoClinicaService")
public class CambioMasivoClinicaService extends AbstractServiceBusiness implements
		CambioMasivoClinicaServiceRemote {
	private static final String NOTIFICACION_CAMBIO_CITA="Notificación de Cambio de Unidad de Medicina Familiar";
	@EJB
	CambioMasivoClinicaDaoLocal cambioMasivoClinicaDao;
	@EJB
	EMailServiceLocal eMailService;
	@EJB
	DelegacionDaoLocal delegacionDao;
	@EJB
	DerechohabienteDaoLocal derechohabienteDao;
	@EJB
	MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	@EJB
	MedicoEnTurnoDaoLocal medicoEnTurnoDao;
	@EJB(mappedName = "domicilioServiceBusiness") DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	@EJB
	UmfCodigoPostalDaoLocal umfCodigoPostalDao;
	
	@EJB
	AgendarCitaServiceLocal agendarCitaService;
	@EJB
	SolicitudDaoLocal solicitudDao;
	@EJB
	TramitePersonaFisicaDaoLocal tramitePersonaFisica;
	@EJB
	GrupoFamiliarDaoLocal grupoFamiliarDao;
	@EJB
	UmfDaoLocal umfDao;
	
	@EJB
	private SolicitudCitaEntityLocal solicitudCitaEntity;
	
	
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBussines; 
	
	private static final Logger log = Logger.getLogger(CambioMasivoClinicaService.class);

	
	@Override
	public List<UnidadMedicaFamiliar> getUmfBySubDelegacionSinUmf(
			Long idSubdelgacion, Long idUmf) throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		List<UnidadMedicaFamiliar> salida = null;
		
		try {
			salida = umfDao.findUnidadesBySubdelegacionSinUmf(idSubdelgacion, idUmf);
		} catch (Exception e) {
			
		}
		return salida;
	}

	@Override
	public List<UnidadMedicaFamiliar> getUmfbySubDelegacion(Long idDelegacion) throws DerechohabientesBusinessException,Exception{
		
		List<UnidadMedicaFamiliar> salidaList;
		//parseo
		salidaList=UnidadMedicaFamiliarParser.persisToModelList(this.umfDao.findUmfbySubDelagacion(idDelegacion));
		return salidaList;
	}
	
	@Override
	public Domicilio getDomicilioByDelegacion(Long idDelegacion) throws Exception{
		DicDelegacion dicDelegacion=this.delegacionDao.findDelegacion(idDelegacion);
		
		Domicilio domicilio=new Domicilio();
		domicilio.setClave(dicDelegacion.getDgDomicilioGeografico().getDomicilioId().intValue());
		
		try {
			domicilio=this.domicilioServiceBusinessRemote.consultarDomicilio(domicilio);
			
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
			
		}
		return domicilio;
	}

	@Override
	public List<Asentamiento> getAsentamientosByMunicipio(Municipio municipio) {
		List<Asentamiento> asentamientos=null;
		try {
			asentamientos= this.domicilioServiceBusinessRemote.getAsentamientoPorMunicipio(municipio);
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}
		return asentamientos;	
	}
	@Override
	public List<MedicoEnTurno> medicoEnTurnos(Long idUmf) throws Exception{
		
		List<MedicoEnTurno> medicoEnTurnos;
		
		medicoEnTurnos= medicoEnTurnoParserServiceLocal.persisToModelList(this.medicoEnTurnoDao.getMedicosEnTurnobyUmf(idUmf));
		return medicoEnTurnos;
	}

	
	@Override
	public Long[] guardarCambioClinicaMasivo(List<Asentamiento> asentamientos,
			List<MedicoEnTurno> matutino, List<MedicoEnTurno> vespertino,
			Usuario usuario, Long idUmfDestino)
			throws DerechohabientesBusinessException, Exception {
		Long numeroDeDerechohabientesAfectados=null;
		Long numeroDeSolicitudesAfectadas=null;
		Long[] regRes=new Long[2];
		regRes[0] = new Long(0);
		regRes[1] = new Long(0);
		Date fechaCita=null;
		
		Solicitud solicitud = null;
		
		for(MedicoEnTurno medicoEnTurnoM : matutino) {
			medicoEnTurnoM.setTurno(new Turno());
			medicoEnTurnoM.getTurno().setIdTurno(TurnoEnum.MATUTINO.getId());
			medicoEnTurnoM.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
			medicoEnTurnoM.getUnidadMedicaFamiliar().setIdUMF(idUmfDestino);
		}
		
		for(MedicoEnTurno medicoEnTurnoV : vespertino) {
			medicoEnTurnoV.setTurno(new Turno());
			medicoEnTurnoV.getTurno().setIdTurno(TurnoEnum.VESPERTINO.getId());
			medicoEnTurnoV.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
			medicoEnTurnoV.getUnidadMedicaFamiliar().setIdUMF(idUmfDestino);
		}

		//guardar una solicitud y tramite
		//fecha de proxima cita
		fechaCita=this.fechaProxCita();
		//Obtener las Umf que tengan el id domicilio 
		//Obtener las Solicitudes que tengan esa umf y estado =1 con fecha de sita apartir del dia siguiente de hoy
		
		//acciones para guardar la solicitud
		//crear un tramite 
		solicitud = this.guardaSolicitud(usuario);
		
		Tramite tramite = (Tramite)solicitud.getTramites().get(0);
		
		int indice = 0;
		for(Asentamiento asentamiento: asentamientos) {
			//actualisarles la umf turno y fecha de cita
			MedicoEnTurno medicoEnTurnoM = matutino.get(indice);
			MedicoEnTurno medicoEnTurnoV = vespertino.get(indice);
			
			
			DitUmfCodPo objDitUmfCodPoOrigen = umfCodigoPostalDao.bajaLogicaUmfCodPo(usuario.getIdUmf(), asentamiento);
			DitUmfCodPo objDitUmfCodPoDestino = umfCodigoPostalDao.insertaUMFCodPos(idUmfDestino, asentamiento);
			
			
			// ----------------------------------------------
			// Actualiza los grupos familiares
			// Inserta en la bitacora de cambio masivo 
			// ----------------------------------------------
			numeroDeDerechohabientesAfectados=this.derechohabienteDao.updateMedicoEnTurnoDerechohabientesbyDomicilio(
					asentamiento,medicoEnTurnoM,medicoEnTurnoV, tramite.getTramiteId(),objDitUmfCodPoOrigen, objDitUmfCodPoDestino );
			
			
			numeroDeSolicitudesAfectadas= this.solicitudCitaEntity.updateCitaSolicitudesPorCambioMasivoClinica(asentamiento, medicoEnTurnoM, fechaCita);

		
			regRes[0]+=numeroDeDerechohabientesAfectados;
			regRes[1]+=numeroDeSolicitudesAfectadas;
			
			indice++;
		}
		return regRes;
	}

	/*
	private Tramite guardaTramite(Solicitud solicitud,Usuario usuario) throws Exception{
		Tramite tramite=new Tramite();
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.setFechaPresentacion(new Date());
		tramite.setFechaTramite(new Date());
		//TODO verificar el funcionamiento sin la solicitud
		//tramite.setSolicitud(solicitud);
		tramite.setResultado(true);
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA_MASIVO.getCodigo());
		tramite.setPersona(usuario.getFisica());
		return this.tramitePersonaFisica.saveTramite(tramite);
	}	
	*/
	private Solicitud guardaSolicitud(Usuario usuario) throws DerechohabientesBusinessException, Exception{
		Solicitud solicitud=new Solicitud();
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue());
		solicitud.setFechaPresentacion(new Date());
		solicitud.setFechaSolicitud(new Date());
		solicitud.setSolicitante(usuario);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CAMBIO_MASIVO.getId());
		
		Tramite tramite=new Tramite();
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.setFechaPresentacion(new Date());
		tramite.setFechaTramite(new Date());
		tramite.setResultado(true);
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA_MASIVO.getCodigo());
		List <Tramite> lstTramite = new ArrayList();
		lstTramite.add(tramite);
		
		solicitud.setTramites(lstTramite);
		solicitud=this.solicitudBussines.crear(solicitud);
		return solicitud;
	}
	
	
	private Date fechaProxCita() throws DerechohabientesBusinessException, Exception{
		List<Date> fechasInhabiles=this.agendarCitaService.getFechasInhabiles();
		Date fechaProxCita=DateUtils.recorrerUnDia(new Date());
		//Buscar proxima fecha habil
		fechaProxCita=DateUtils.getProximaFechaHAbil(fechaProxCita, fechasInhabiles);
		return fechaProxCita;
	}
	
	@Override
	public List<Asentamiento> getAsentamientosPorUmf(Long idUmf) throws Exception {
		List<Asentamiento> asentamientosList=null;
		List<DitUmfCodPo> ditUmfCodPosList=null;
		//busca en umf codigo postal los asentamientos del umf
		ditUmfCodPosList=this.umfCodigoPostalDao.getUmfCodPosByIdUmfList(idUmf);
		//parsea umfCodpos a asentamiento.
		
		
		asentamientosList=this.asentamientoCodigoParser(ditUmfCodPosList);
	
		return asentamientosList;
		
	}
	
	private List<Asentamiento> asentamientoCodigoParser(List<DitUmfCodPo> entradaList) throws DerechohabientesBusinessException{
		List<Asentamiento> salidaList=null;
		Asentamiento salida=null;
		if(entradaList!=null){
			salidaList=new ArrayList<Asentamiento>();
			for(DitUmfCodPo entrada:entradaList ){
				//asentamiento
				salida=AsentamientoParser.persisToModel(entrada.getDgCodigosPostale().getDgAsentamiento());
				//codigo postal
				salida.setCodigoPostal(CodigoPostalParser.persistToModel(entrada.getDgCodigosPostale()));
				salidaList.add(salida);
			}
		}
		return salidaList;
	}
	
	private void enviaCorreosAfectados(Asentamiento asen,String fechaCita) throws Exception{
		MailProperties mailProperties=this.eMailService.getDefaultMailProperties();
		mailProperties.setSubject(NOTIFICACION_CAMBIO_CITA);
		List<DitGrupoFamiliar> ditGrupoFamiliars=this.grupoFamiliarDao.findGrupoFamiliarbyAsentamiento(asen);
		for(DitGrupoFamiliar ditGrupoFamiliar:ditGrupoFamiliars){
			this.armaCorreo(ditGrupoFamiliar,mailProperties,fechaCita);
		}
	}
	
	private void armaCorreo(DitGrupoFamiliar ditGrupoFamiliar,MailProperties mailProperties,String fechaCita) throws DerechohabientesBusinessException{
		String correoE=this.getCorreoPersona(ditGrupoFamiliar);		
		if(correoE!=null){
			
			mailProperties.setTo(correoE);
			mailProperties.setBody(this.eMailBody(ditGrupoFamiliar,fechaCita));
			try {
				this.eMailService.sendSimpleMail(mailProperties);
			}catch(Exception e) {
				log.error("No se enviaron los correos", e);
			}
		}
	}
	
	private String eMailBody(DitGrupoFamiliar ditGrupoFamiliar,String fechaCita) throws DerechohabientesBusinessException{
		String sl="<br>";
		String sp=" ";
		StringBuffer body=new StringBuffer(sl);
		body.append(sl);
		body.append("Estimado Derechohabiente :");		
		body.append(sl);
		body.append(ditGrupoFamiliar.getDitPersona().getNomNombre());
		body.append(sp);
		body.append(ditGrupoFamiliar.getDitPersona().getNomPrimerApellido());
		body.append(sl);
		body.append("Debido a una reorganización de la UMF se le ha reasignado a la ");
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicConsultorioUmf().getDicUmf().getNomCorto());
		body.append(" la cual se ubica en:");
		body.append(sl);
		body.append(sl);
		body.append("Delegación IMSS: ");
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicConsultorioUmf().getDicUmf().getDicSubdelegacion().getDicDelegacion().getCveIdDelegacion());
		body.append(sp);
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicConsultorioUmf().getDicUmf().getDicSubdelegacion().getDicDelegacion().getDesDeleg());
		body.append(sl);
		body.append("Dirección de la UMF: ");
		body.append(DomicilioParser.persisToModelDesDireccion(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicConsultorioUmf().getDicUmf().getDgDomicilioGeografico()));
		body.append(sl);
		body.append(sl);
		body.append("Turno: ");
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicTurno().getDesDescripcion());
		body.append(sp);
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicTurno().getRefHoraInicioTurno());
		body.append(sp);
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitUmfConsultorioTurno().getDicTurno().getRefHoraFinTurno());
		body.append(sl);
		body.append("Medico: ");
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitMedicoEspecialidad().getDicMedico().getNomNombre());
		body.append(sp);
		body.append(ditGrupoFamiliar.getDitUmfConsTurnoMedico().getDitMedicoEspecialidad().getDicMedico().getNomPrimerApellido());
		body.append(sl);
		body.append("Fecha Próxima Cita: ");
		body.append(fechaCita);
		body.append(sl);
		body.append(sl);
		body.append("De tener alguna cita programada favor de presentarse en la fecha y lugar indicados en este correo.");
		body.append(sl);
		body.append("Lamentamos las molestias que esto le  pueda ocasionar.");
		body.append(sl);
		body.append("Este correo ha sido generado automáticamente favor de no responder.");
		return body.toString();
	}
	
	private String getCorreoPersona(DitGrupoFamiliar ditGrupoFamiliar){
		try{
		
			List<DitPersonafContacto> ditPersonafContactos=ditGrupoFamiliar.getDitPersona().getDitPersonafContactos();
			for(DitPersonafContacto ditPersonafContacto:ditPersonafContactos){
				if(ditPersonafContacto.getDitFormaContacto().getDitTipoContacto().getCveIdTipoContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())){
					return ditPersonafContacto.getDitFormaContacto().getDesFormaContacto();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	

}
