/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.print.attribute.HashAttributeSet;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CabezaGrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MovimientoAseguradoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.ProrrogaDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.ProrrogaParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.GrupoFamiliarUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoProrroga;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoProrrogaEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.enums.CaracterEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoProrrogaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

/**
 * @author ghdolores
 * 
 */
/**
 * @author juan.salinas
 *
 */
@Stateless(name = "prorrogaService", mappedName = "prorrogaService")
public class ProrrogaService  extends AbstractServiceBusiness implements ProrrogaServiceRemote, ProrrogaServiceLocal{
	
	@EJB(name = "solicitudService") 
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDao;
	@EJB
	private ProrrogaDaoLocal prorrogaDao;
	@EJB
	private MovimientoAseguradoDaoLocal movimientoAseguradoDao;
	@EJB
	private CabezaGrupoFamiliarDaoLocal cabezaDao;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@EJB(mappedName = "firmaDigitalBusiness", name = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private ProrrogaParserServiceLocal prorrogaParserServiceLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private ProrrogaDerechohabienteEntityLocal prorrogaEntity;
	@EJB
	private FinalizaSolicitudServiceRemote finalizaSolicitudService;
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteServiceLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	
	
	@Override
	public Solicitud saveProrrogaEstudios(ConstanciaEstudio constancia,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		
		
		TramiteProrroga prorroga = new TramiteProrroga();
		Date fechaFinPeriodo = constancia.getFechaFinPeriodo();
		Date fechaNacimiento = grupoFamiliar.getDerechohabiente().getFechaNacimiento();
		
		
		if( fechaNacimiento == null ){
			
			// --------------------------------------------
			// Primer día del mes de nacimiento
			// --------------------------------------------
			fechaNacimiento = DateUtils.getPrimerDiaSiguienteMes(
					grupoFamiliar.getDerechohabiente().getAnioRegistroNac(), grupoFamiliar.getDerechohabiente().getMesRegistroNac());
		}
		
		
		Date fechaFinVigencia = DateUtils.getFechaFinVigencia(fechaNacimiento, 25);
		Date fechaNac25 = DateUtils.sumaAnios(fechaNacimiento, 25);

		// Verifica que la fecha fin de la prorroga no sea mayor a la fecha de
		// vencimiento de vigencia
		if (fechaFinVigencia.before(fechaFinPeriodo)) {
			fechaFinPeriodo = fechaFinVigencia;
		}

		// Asigna la fecha de fin 30 dias naturales despues de la fecha

		prorroga.setFechaInicioProrroga(constancia.getFechaInicioPeriodo());

		prorroga.setFechaFinProrroga(DateUtils.sumarDiasFecha(fechaFinPeriodo,
				30));
		if (prorroga.getFechaFinProrroga().after(fechaNac25)) {
			prorroga.setFechaFinProrroga(fechaNac25);
		}
		
		GrupoFamiliar grupo = grupoFamiliarDao.getIntegranteGrupoFamiliar(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(), grupoFamiliar.getAsignacionNSS().getIdPersona());
		if (grupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()) {
			if(grupo.getFechaFinVigencia() != null){
				prorroga.setFechaFinProrroga(grupo.getFechaFinVigencia());
			}
		}
		
		
		prorroga.setObservacion(constancia.getObservaciones());

		Solicitud solicitudProrroga = saveProrroga(constancia, prorroga,
				CaracterEnum.PROVISIONAL, TipoTramiteEnum.PRORROGA_ESTUDIOS,
				constancia.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);

		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
		
		
	}

	
	
	
	@Override
	public Solicitud saveProrrogaEnfermedad(
			DictamenIntegranteIncapacitado dictamen,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {

		CaracterEnum caracter = CaracterEnum.PROVISIONAL;
		Solicitud solicitudProrroga = null;

		if (dictamen.getProrroga().getCaracter().getIdCaracter() == CaracterEnum.DEFINITIVO
				.getId()) {
			caracter = CaracterEnum.DEFINITIVO;
			// Si es prooroga permanente la fecha de fin es indefinida
			dictamen.getProrroga().setFechaFinProrroga(
					fechaDefaultVigenciaPermanente());
		}

		solicitudProrroga = saveProrroga(dictamen, dictamen.getProrroga(),
				caracter, TipoTramiteEnum.PRORROGA_ENFERMEDAD,
				dictamen.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
	}

	@Override
	public Solicitud saveProrrogaAcuerdos(Acuerdo acuerdo,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		TramiteProrroga prorroga = acuerdo.getProrroga();
		Solicitud solicitudProrroga = null;
		this.log.debug("el tramite es " + prorroga.toString());
		CaracterEnum caracter = CaracterEnum.PROVISIONAL;
		if (prorroga.getCaracter().getIdCaracter() == CaracterEnum.DEFINITIVO
				.getId()) {
			caracter = CaracterEnum.DEFINITIVO;
			// Si es prooroga permanente la fecha de fin es indefinida

			prorroga.setFechaFinProrroga(fechaDefaultVigenciaPermanente());
		}
		solicitudProrroga = saveProrroga(acuerdo, prorroga, caracter,
				TipoTramiteEnum.PRORROGA_ACUERDOS,
				acuerdo.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);
		/*
		 * tramite.setPersona(usuario.getFisica());
		 * solicitudDao.insertBitacoraSegTramite(tramite);
		 */
		return solicitudProrroga;
	}

	@Override
	public Solicitud saveProrrogaVigenciaTemporal(VigenciaTemporal pension,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		Date fechaFin = DateUtils.sumarDiasFecha(new Date(), 50);
		TramiteProrroga prorroga = new TramiteProrroga();
		Solicitud solicitudProrroga = null;

		prorroga.setFechaFinProrroga(fechaFin);
		prorroga.setFechaInicioProrroga(pension.getFechaExpedicion());
		prorroga.setObservacion(pension.getObservaciones());

		solicitudProrroga = saveProrroga(pension, prorroga,
				CaracterEnum.PROVISIONAL,
				TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL,
				pension.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
	}

	@Override
	public Solicitud saveProrrogaVigenciaPermanente(Acta acta,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {

		Solicitud solicitudProrroga = null;
		TramiteProrroga prorroga = new TramiteProrroga();
		prorroga.setFechaInicioProrroga(acta.getFechaSuceso());
		prorroga.setFechaFinProrroga(fechaDefaultVigenciaPermanente());
		prorroga.setObservacion(acta.getObservaciones());

		solicitudProrroga = saveProrroga(acta, prorroga,
				CaracterEnum.DEFINITIVO,
				TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE,
				acta.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
	}

	@Override
	public Solicitud saveProrrogaServiciosObstetricos(Obstetrico obstetrico,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		Solicitud solicitudProrroga = null;

		TramiteProrroga prorroga = obstetrico.getProrroga();
		solicitudProrroga = saveProrroga(obstetrico, prorroga,
				CaracterEnum.PROVISIONAL, TipoTramiteEnum.PRORROGA_OBSTETRICOS,
				obstetrico.getFechaExpedicion(), grupoFamiliar, usuario, idOrigenSolicitud);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
	}

	@Override
	public Solicitud saveProrrogaLaudos(Acuerdo acuerdo,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		Solicitud solicitudProrroga = null;
		TramiteProrroga prorroga = acuerdo.getProrroga();
		CaracterEnum caracter = CaracterEnum.PROVISIONAL;

		if (prorroga.getCaracter().getIdCaracter() == CaracterEnum.DEFINITIVO
				.getId()) {
			caracter = CaracterEnum.DEFINITIVO;
			// Si es prooroga permanente la fecha de fin es indefinida
			prorroga.setFechaFinProrroga(fechaDefaultVigenciaPermanente());
		}
		solicitudProrroga = saveProrroga(acuerdo, prorroga, caracter,
				TipoTramiteEnum.PRORROGA_LAUDOS, acuerdo.getFechaExpedicion(),
				grupoFamiliar, usuario, idOrigenSolicitud);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		return solicitudProrroga;
	}

	private Solicitud saveProrroga(DocumentoProbatorio documento,
			TramiteProrroga prorroga, CaracterEnum caracter,
			TipoTramiteEnum tipoTramite, Date fechaExpedicion,
			GrupoFamiliar grupoFamiliar, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, DocumentoException, SolicitudNoValidaException, Exception  {

		Solicitud solicitud = null;
		GrupoFamiliar integrante = null;
		Date fechaHoy = new Date(); 
		
		solicitud = getSolicitud(grupoFamiliar, usuario);
		solicitud.setObservacion(prorroga.getObservacion());
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(idOrigenSolicitud);
		
		// Establecemos la persona interesada en el tramite
		PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
		personaIntSol.setIdSolicitud(solicitud.getSolicitudId());
		Fisica fisica = new Fisica();
		fisica.setIdPersona(grupoFamiliar.getAsignacionNSS().getIdPersona());
		personaIntSol.setPersona(fisica);
		TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
		tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
		
		
		solicitud.setPersonaInteresadaSolicitud(personaIntSol);


		this.setTramiteProrroga(grupoFamiliar.getDerechohabiente()
				.getIdPersona(), tipoTramite.getCodigo().longValue(), prorroga,
				caracter);

		solicitud.setTramites(new ArrayList<Tramite>());
		prorroga.setPersona(grupoFamiliar.getDerechohabiente());
		
			
			Long idTipoProrroga = prorrogaParserServiceLocal.getTipoProrrogaPorTipoTramite(tipoTramite.getCodigo());
			log.debug("tipo prorroga por tipo tramite");
			prorroga.setIdTipoProrroga(idTipoProrroga);
			prorroga.setFechaPresentacion(new Date());
			solicitud.getTramites().add(prorroga);

			solicitud = solicitudBusinessRemote.crear(solicitud);
			
			if(idOrigenSolicitud.longValue() != OrigenSolicitudEnum.INTERNET_TSPI.getId().longValue()){
				log.debug("tipo prorroga por tipo tramite");
				TramiteProrroga prorrogaPaso = this.getProrrogaFromSolicitud(solicitud);
				prorrogaPaso.setGrupoFamiliar(grupoFamiliar);
				prorrogaDao.saveProrroga(prorrogaPaso);
				//grupoFamiliarDao.updateIntegrante(integrante);
			
				String observaciones = null;
				if (StringUtils.isNotBlank(solicitud.getObservacion())) {
					observaciones = solicitud.getObservacion().length() > 500 ? solicitud.getObservacion().substring(0,500) : solicitud.getObservacion();
				}
				solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), observaciones, null);
				
			
			// ------------------------------------------------------------------------------
			// FIRMA ELECTRONICA
			//
			// Se necesita para firmar los documentos resultantes
			// Si no existe la firma la crea; necesita el id de solicitud
			// ------------------------------------------------------------------------------
				log.debug(" ========== PRORROGA FIRMA =====================");
			
				// ----------------------------------------------------------------------------
				// La firma necesita una descripción de trámite
				// ----------------------------------------------------------------------------
				TipoTramite tipoTramiteModel = catalogosDaoLocal.getTipoTramite(tipoTramite.getCodigo().longValue());
				solicitud.getTramites().get(0).setTipoTramite(tipoTramiteModel);
				
				FirmaElectronica firmaElectronica = tramiteDocumentosServiceLocal.generaFirmaElectronica(
						grupoFamiliar.getAsignacionNSS(), solicitud, tipoTramiteModel.getDescripcion());
				
				solicitud.setFirmaElectronica(firmaElectronica);
				solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
				solicitud.setSelloDigital(firmaElectronica.getRecibo());
				solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
			
			
				//Se manda llamar al WS
				try {
					finalizaSolicitudService.finalizaSolicitud(solicitud);
					log.debug("sali del servicio para afectar");
				} catch (IllegalArgumentException e) {
					log.error(e);
					new SolicitudException(e.getMessage());
				} catch (Exception e) {
					log.error(e);
					new SolicitudException(e.getMessage());
				}
			}

		return solicitud;
	}

	private Solicitud getSolicitud(GrupoFamiliar grupo, Usuario usuario) {

		Date fechaHoy = new Date();
		Solicitud solicitud = new Solicitud();
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.REGISTRADA.getCodigo());
		
	
		solicitud.setFechaPresentacion(fechaHoy);
		solicitud.setFechaSolicitud(fechaHoy);
		
		UMFTurno umfTurno = new UMFTurno();

		umfTurno.setTurno(grupo.getMedicoEnTurno().getTurno());
		umfTurno.setUnidadMedicaFamiliar(grupo.getMedicoEnTurno()
				.getUnidadMedicaFamiliar());

		CitaSolicitud cita = new CitaSolicitud();
		cita.setTurno(umfTurno.getTurno());
		cita.setUmf(umfTurno.getUnidadMedicaFamiliar());
		//cita.setFechaHora(fechaHoy);

		solicitud.setCitaSolicitud(cita);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(
				TipoSolicitudEnum.PRORROGA.getId());
		solicitud.setSolicitante(usuario);
		//solicitud.setNoFolioSolicitud("1");

		return solicitud;
	}

	// Metodo que llena un objeto tramite
	private void setTramiteProrroga(Long idPersona, Long idTipoTramite,
			TramiteProrroga tramite, CaracterEnum caracter) {
		Date fechaHoy  = new Date();
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.setFechaTramite(fechaHoy);

		if (idTipoTramite.longValue() == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo().longValue() || idTipoTramite.longValue() == TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo().longValue()) {

			tramite.getEstadoTramite().setIdEstadoTramitePersona(
					EstadoTramiteEnum.CERRADO.getCodigo());

		} else {
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
		}

		tramite.setPersona(new Fisica());
		tramite.getPersona().setIdPersona(idPersona);
		tramite.setRazonResultado(new RazonResultado());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.setResultado(true);
		tramite.setFechaPresentacion(fechaHoy);
		tramite.setFechaTramite(fechaHoy);
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(idTipoTramite.intValue());
		tramite.setCaracter(new Caracter());
		tramite.getCaracter().setIdCaracter(caracter.getId());
		EstadoProrroga ep = new EstadoProrroga();
		ep.setIdEstadoProrroga(EstadoProrrogaEnum.ACTIVA.getId());
		tramite.setEstadoProrroga(ep);

	}

	private boolean contieneIdModalidad(List<Modalidad> modalidades, Long idModalidadbuscada) {
		boolean encontrada = false;
		
		for(Modalidad modalidad: modalidades) {
			if(modalidad.getIdModalidad().equals(idModalidadbuscada)) {
				encontrada = true;
				break;
			}
		}
		
		return encontrada;
	}
	
	
	private void validateModalidades(CabezaGrupoFamiliar cabeza, Long idAsignacioNSS, Integer tipoProrrog) throws DerechohabientesBusinessException{
		
		List<Modalidad> modalidadesActivas = null;
		//Informacion de la cabeza de grupo gamiliar		
		ParentescoEnum parentescoCabezaGrupo = ParentescoEnum.obternerEnumById(cabeza.getCalidadParentesco().getIdParentesco());
		Boolean isPensionado = parentescoCabezaGrupo == ParentescoEnum.PENSIONADO;
		
		//Se obtienen las modalidades activas del asegurado pensionado
		
			//se valida primero la pension para evitar consultar modalidades si no es necesario
			if(isPensionado) {
				if(tipoProrrog == TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo() ||
						tipoProrrog == TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL.getCodigo() ||
								tipoProrrog == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo() ||
								tipoProrrog == TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo() )  {
					
					throw new DerechohabientesBusinessException(
							"No se puede registrar la pr\u00F3rroga solicitada" +
							" debido a que el pensionado no puede solicitar este tipo de prorroga.",
							"No existe informaci\u00F3n para mostrar."	);
				}
			}else{
				try {
					modalidadesActivas = grupoFamiliarServiceLocal.getModalidadesActivas(idAsignacioNSS);

					if (modalidadesActivas == null
							&& cabeza != null
							&& cabeza.getEstadoDerechohabiente() != null
							&& cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != null
							&& cabeza.getPatronSujetoObligado() != null
							&& cabeza.getPatronSujetoObligado().getModalidad() != null
							&& (cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()
							|| (cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()
							&& parentescoCabezaGrupo == ParentescoEnum.ASEGURADO))) {
						modalidadesActivas = new ArrayList<Modalidad>();
						modalidadesActivas.add(cabeza.getPatronSujetoObligado().getModalidad());
					}
				} catch(Exception e) {
					log.error("Error al consultar las modalidades de los patrones del asegurado", e);
				}
				if (modalidadesActivas == null) {
					throw new DerechohabientesBusinessException(
							"No es posible solictar la pr\u00F3rroga " +
									" debido a que el asegurado - pensionado no cuenta con la modalidad requerida.",
							"No existe informaci\u00F3n para mostrar." );
				} else if(!this.esModalidadValida(modalidadesActivas, tipoProrrog.longValue())) {
					throw new DerechohabientesBusinessException(
							"No es posible solictar la pr\u00F3rroga " +
									" debido a que el asegurado - pensionado no cuenta con la modalidad requerida.",
							"No existe informaci\u00F3n para mostrar." );
				}
			}
	}

	/**
	 * @see ProrrogaServiceRemote
	 */
	
	
	public String validaIntegrantePrrogaEstudiosTSPI(GrupoFamiliar integrante, Long idAsignacioNSS) throws DerechohabientesBusinessException, Exception{
		//String mensajeValidacion = null;
		return validateIntegranteProrrogaEstudios(integrante, idAsignacioNSS);
	}
	
	private String validateIntegranteProrrogaEstudios(GrupoFamiliar integrante, Long idAsignacioNSS) throws DerechohabientesBusinessException, Exception{
		String mensaje = "";
		Long edad = 0L;
		TramiteProrroga p = null;
		
		edad = DateUtils.getEdadPorFechaOPorMesAnio(integrante.getDerechohabiente());
		
		
		GrupoFamiliar integranteLocal = grupoFamiliarDao.getIntegranteSinVigencia(idAsignacioNSS, integrante.getDerechohabiente().getIdPersona());
		
		if(integranteLocal.getParentesco().getIdParentesco().longValue() != ParentescoEnum.HIJOS.getId()) {
			mensaje = "El integrante no tiene el parentesco indicado para realizar el tr\u00E1mite";	
			return mensaje;
		}
		
		log.debug("la edad del integrantes " + integrante.getDerechohabiente().getCurp() + " es [" + edad+"]");
		if(!(edad >= 16 && edad < 25)){
			mensaje = "El integrante no tiene la edad requerida para realizar el tr\u00E1mite";	
			return mensaje;
		}
		// Valida que el integrante tenga entre 16 y 25 anios
		if (integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE.getId()) {
			// Busca en los integrantes vigentes si hay una prorrogaproxima a vencer
			List<TramiteProrroga> prorrogas = prorrogaDao.getProrrogasActivas(idAsignacioNSS, integrante.getDerechohabiente().getIdPersona(), TipoProrrogaEnum.ESTUDIOS.getId());
			
			if (prorrogas != null && prorrogas.size() == 1) {
				log.debug("se encontraron: " + prorrogas.size() + " prorrogas de estudio activas para : " + integrante.getDerechohabiente().getIdPersona() + 
						" con IdAsignacionNSS: " + idAsignacioNSS);
				//obtenemos la primera prorroga para ver sus fechas de vencimiento
				p = prorrogas.get(0);
				
				log.debug("*********************  pr\u00F3rroga activa "+ p.getFechaFinProrroga());
				log.debug("*********************  pr\u00F3rroga activa "+ Math.abs(DateUtils.getDaysBetweenDates(p.getFechaFinProrroga(), new Date())));
				
				// Si hay una prorroga verifica que falten 30 dias o menos para vencer
				if (p == null || Math.abs(DateUtils.getDaysBetweenDates(p.getFechaFinProrroga(), new Date())) > 30) {
					mensaje = "El integrante es vigente pero su prorroga no est\u00E1 por finalizar";
				}
			}else{
				mensaje = "El integrante est\u00E1 vigente y no tiene prorrogas";
			}
			
		}else if(!(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.BAJA.getId())){
			mensaje = "El integrante no est\u00E1 en baja o vigente";
		}
		
		
		log.debug("El integrante "+integrante.getDerechohabiente().getCurp()+": [" + mensaje +"]");
		return mensaje;
	}
	
	private boolean validateEstadoDerechohabiente(Integer tipoProrrog, CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException{
		boolean resp = false;
		// Estado validos para la cabeza del grupo familiar
		List<EstadoDerechohabienteEnum> estadosValidos = Arrays.asList(EstadoDerechohabienteEnum.VIGENTE, 
																		EstadoDerechohabienteEnum.CONSERVACION_DERECHOS,
																		EstadoDerechohabienteEnum.CON_DERECHO,
																		EstadoDerechohabienteEnum.FALLECIDO);
		EstadoDerechohabienteEnum estadoCabezaGrupoFamiliar = EstadoDerechohabienteEnum.getById(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
		//Informacion de la cabeza de grupo gamiliar		
		ParentescoEnum parentescoCabezaGrupo = ParentescoEnum.obternerEnumById(cabeza.getCalidadParentesco().getIdParentesco());
		Boolean isPensionado = parentescoCabezaGrupo == ParentescoEnum.PENSIONADO;
		
		if(tipoProrrog == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()) {
			//verificamos si la cabeza de grupo familiar contiene un estado valido, en caso de no contar con el regresamos una excepcion
			if (!estadosValidos.contains(estadoCabezaGrupoFamiliar)) {
				throw new DerechohabientesBusinessException(
						"El asegurado o pensionado no cuenta con un estado v\u00E1lido para una pr\u00F3rroga por estudios","error.prorroga.msg36.estudios");
			}
			
			// ---------------------------------------------------------------------------
			// Si es FALLECIDO debe ser PENSIONADO
			// ---------------------------------------------------------------------------		
			//en caso de que el estado de la cabeza de grupo familiar sea fallecido, el parentesco debe ser pensionado
			if( (estadoCabezaGrupoFamiliar == EstadoDerechohabienteEnum.FALLECIDO) && !isPensionado) {
				throw new DerechohabientesBusinessException(
						"El asegurado o pensionado no cuenta con un estado v\u00E1lido para una pr\u00F3rroga por estudios","error.prorroga.msg36.estudios");

			}
		}else if(tipoProrrog == TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()) {

			//verificamos si la cabeza de grupo familiar contiene un estado valido, en caso de no contar con el regresamos una excepcion
			if (!estadosValidos.contains(estadoCabezaGrupoFamiliar)) {
				throw new DerechohabientesBusinessException(
						"El asegurado no cuenta con un estado v\u00E1lido  para solicitar una pr\u00F3rroga por enfermedad",
						"error.prorroga.msg36.enfermedad");
			}
		}else if (tipoProrrog == TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE.getCodigo()) {
			//estados validos para prorroga permanente
			estadosValidos = Arrays.asList(EstadoDerechohabienteEnum.BAJA, EstadoDerechohabienteEnum.FALLECIDO);
			
			//Validamos que la cabeza de grupo familiar tenga un estado valido para la prorroga permanente
			if (!estadosValidos.contains(estadoCabezaGrupoFamiliar)) {
				throw new DerechohabientesBusinessException(
						"El asegurado no cuenta con un estado v\u00E1lido para solicitar una pr\u00F3rroga de vigencia permanente",
						"error.prorroga.msg36.vigenciaPermanente");
			}
		}
		
		return resp;																
	}

	@Override
	public Map<String,Object> validateProrrogaEstudios(GrupoFamiliar integrante, Long idAsignacioNSS, Usuario usuario)
			throws DerechohabientesBusinessException, Exception {
		boolean valido = false;
		Map<String,Object> resp = new HashMap<String,Object>();
		Integer tipoProrrog = TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo();
		String mensaje = "";
		CabezaGrupoFamiliar cabeza = grupoFamiliarDao.getCabezaGrupoFamiliar(idAsignacioNSS);
		
		try {
			
			//Se validan las modalidades del de
			validateModalidades(cabeza, idAsignacioNSS, tipoProrrog);
			
			validateEstadoDerechohabiente(tipoProrrog, cabeza);
			if((mensaje = validateIntegranteProrrogaEstudios(integrante, idAsignacioNSS)).isEmpty()){
				List<GrupoFamiliar> integrantes = new ArrayList<GrupoFamiliar>();
				integrantes.add(integrante);

				integrantes = validatePerfilDefuncionesBajas(integrantes, usuario, tipoProrrog);
				if(!integrantes.isEmpty()){
					valido = true;					
				}else{
					mensaje = "El integrante tiene baja por defuncíon";
				}
			}
		} catch (DerechohabientesBusinessException e) {
			mensaje = e.getMessage();
		}
		resp.put("valido", valido);
		resp.put("mensaje", mensaje);
		return resp;
	}
	
	/**
	 * Obtiene los candidatos para las prorrogas
	 * 
	 * @param tipoProrroga
	 * @param idAsignacioNSS
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<GrupoFamiliar> getCandidatosProrroga(
			CabezaGrupoFamiliar cabeza, GrupoFamiliar asegurado,
			Long tipoProrroga, Long idAsignacioNSS, Usuario usuario, boolean saltaSolicitudes)
			throws DerechohabientesBusinessException, Exception {
		
		//Se utiliza el valor entero del Enum ya que tiene elmentos con Id repetido, pues lo usan otras areas
		Integer tipoProrrog = tipoProrroga.intValue();
		List<GrupoFamiliar> integrantes = null;
		List<GrupoFamiliar> integrantesFinales = new ArrayList<GrupoFamiliar>();		
		String mensajeException = null;
		String situacionException = null;
		Long edad = 0L;		
		
		// Si cabeza == null consultar informacion
		if (cabeza == null) {
			cabeza = grupoFamiliarDao.getCabezaGrupoFamiliar(idAsignacioNSS);
			log.debug("consulta la informacion para la prorroga por obstetricos");
		}				

		//Se validan las modalidades exceptuando prorroga por laudo y acuerdo
		if (tipoProrrog != TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo() && tipoProrrog != TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()) {
			validateModalidades(cabeza, idAsignacioNSS, tipoProrrog);
		}
		
		// Si grupoFamiliar == null consultar informacion
		
		// Si el tipo de prorroga es por estudios verifica que
		if (tipoProrrog == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()) {
			validateEstadoDerechohabiente(tipoProrrog, cabeza);
			// Obtiene los integrantes del grupo familiar
			try {
				integrantes = grupoFamiliarDao.findGrupoFamiliarByParentesco(idAsignacioNSS, ParentescoEnum.HIJOS.getId());
			} catch (Exception e) {
				log.debug("El asegurado no tiene hijos registrados " + e);
				throw new DerechohabientesBusinessException("El asegurado no cuenta con hijos registrados","error.prorroga.msg36.noIntegramtes_hijos");
			}
			//Si no hay integrantes mandamos una excepcion
			if (integrantes == null || integrantes.isEmpty()) {
				throw new DerechohabientesBusinessException("El asegurado no cuenta con hijos registrados","error.prorroga.msg36.noIntegramtes_hijos");
			}
			//verificamos que integrante cuenta con las caracteristicas necesarias para realizar la prorroga
			String strValid = "";
			for(GrupoFamiliar integrante: integrantes) {
				strValid = validateIntegranteProrrogaEstudios(integrante, idAsignacioNSS);				
				if(strValid.isEmpty()){
					integrantesFinales.add(integrante);
				}
			}
			
			mensajeException = "Los hijos registrados no cuentan con los requisitos para solicitar una pr\u00F3rroga";
			situacionException = "error.prorroga.msg36.estudios";
		
		}
		// Fin prorroga estudios
		// Inicia prorroga por enfermedad
		else if (tipoProrrog == TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()) {

			validateEstadoDerechohabiente(tipoProrrog, cabeza);

			try {
				integrantes = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,ParentescoEnum.HIJOS.getId(),EstadoDerechohabienteEnum.BAJA.getId());
			} catch (Exception e) {
				log.debug(e.getMessage());
				throw new DerechohabientesBusinessException(
						"El asegurado no cuenta con hijos registrados en estado de baja.",
						"error.prorroga.msg36.noIntegramtes_hijos");
			}
			
			//se valida si existen candidatos
			if (integrantes == null || integrantes.isEmpty()) {
				throw new DerechohabientesBusinessException(
						"El asegurado no cuenta con hijos registrados en estado de baja.","error.prorroga.msg36.noIntegramtes_hijos");
			}
			
			for(GrupoFamiliar integrante: integrantes) {
				//se calcula la edad del integrante del grupo, ya sea que tenga fecha de nacimiento o que solo tenga el mes y anio
				edad = DateUtils.getEdadPorFechaOPorMesAnio(integrante.getDerechohabiente());
				//si la edad es mayor o igual a 16 se agrag al integrante
				if (edad != null && edad.intValue() >= 16) {
					integrantesFinales.add(integrante);
				}
			}
			
			mensajeException = "Los hijos registados no cumplen con los requisitos para solicitar una pr\u00F3rroga.";
			situacionException = "error.prorroga.msg36.enfermedad";

		}// Termina prorroga por enfermedad

		// Inicia prorroga por Fallecimiento
		else if (tipoProrrog == TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE.getCodigo()) {
			//posibles mensajes de error que se pueden presentar
			String sinCandidatosPermanente = "El asegurado no cuenta con padres registrados o en estado de baja.";
			String errorRequisitosPermante = "Los padres registrados no cuentan con los requisitos para solicitar una pr\u00F3rroga de vigencia permanenete.";
			String codigoError = "error.prorroga.msg36.noIntegramtes_padres";			
			
			validateEstadoDerechohabiente(tipoProrrog, cabeza);
			
			//Obtiene a los integrantes padres del grupo familiar
			try {
				integrantes = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,ParentescoEnum.PADRES.getId(),	EstadoDerechohabienteEnum.BAJA.getId());
			} catch (Exception e) {
				log.error("Ocurrio excepcion no se encontraron padres candidatos "  + e.getMessage());
				throw new DerechohabientesBusinessException(sinCandidatosPermanente,codigoError);
			}
			//se valida si los candidatos estan vacios
			GrupoFamiliarUtil.validarIntegrantesVacios(integrantes, sinCandidatosPermanente, codigoError);
			
			integrantesFinales = integrantes;
			mensajeException = errorRequisitosPermante;
			situacionException = "error.prorroga.msg36.vigenciaPermanente";
			saltaSolicitudes = false;
			
		}// Fin prorroga por fallecimiento
		// Inicia prorroga por Pension
		else if (tipoProrrog == TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL.getCodigo()) {
			// Obtiene los mienbros del grupo familiar
			integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,ParentescoEnum.ASEGURADO.getId(),
							EstadoDerechohabienteEnum.BAJA.getId());
			//Validamos que se haya encontrado integrantes
			GrupoFamiliarUtil.validarIntegrantesVacios(integrantesFinales,"El asegurado no se encuentra en estado de baja.","error.prorroga.msg36.vigenciaTemporal");
			
			mensajeException = "El asegurado no se encuentra en estado de baja.";
			situacionException = "error.prorroga.msg36.vigenciaTemporal";

		}// Termina prorroga por Pension
		// Inicia prorroga por acuerdo
		else if (tipoProrrog == TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo() || tipoProrrog == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()) {
			
			boolean laudo = tipoProrrog == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo();
			if(asegurado == null || asegurado.getEstadoDerechohabiente() == null) {
				// Obtiene los mienbros del grupo familiar
				asegurado = grupoFamiliarServiceLocal.getCabezaGrupaFamilarRegistrada(idAsignacioNSS);
				log.debug("Obtiene los mienbros del grupo familiar");
			}
			
			if(asegurado != null && asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
				integrantesFinales.add(asegurado);
			}
			
			mensajeException = "Los derechohabientes registrados no cumplen con las condiciones necesarias para solicitar una pr\u00F3rroga por " 
					+ (laudo ? "laudo" : "acuerdo");
			GrupoFamiliarUtil.validarIntegrantesVacios(integrantesFinales, mensajeException, "No existe informaci\u00F3n para mostrar.");
			
			situacionException = "No existe informaci\u00F3n para mostrar.";
			
			saltaSolicitudes = false;
			
		}
		// Inicia prorroga por obstetricos
		
		else if (tipoProrrog == TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()) {
			log.debug("Comienza la prorroga por obstetricos");

			// Valida la vigencia de la cabeza familiar
			if (cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue()  != EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()) {
				log.debug("Valida la vigencia de la cabeza familiar");
				// Valida si pertenece a las modalidades 31 o 34, no cuentan con Conservacion de Derechos
				if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("31") || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("34")){
					log.debug("Pertenece a alguna de estas modalidades 31 o 34 : "+cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad());
				} else {
					log.debug("El asegurado no cuenta con estado valido para solicitar una prorroga por obstetricos");
					throw new DerechohabientesBusinessException(
							"El asegurado no cuenta con un estado v&aacute;lido para solicitar una pr&oacute;rroga por obst&eacute;tricos",
							"error.prorroga.msg36.obstetricos");
				}
				
			}

			// RNGD0111 Asegurado o pensionado- Verificar que es un Asegurado
			if (cabeza.getCalidadParentesco().getIdParentesco() == ParentescoEnum.ASEGURADO.getId()) {

				// Verifcar vigencia 8 semanas
				/*
				log.debug("Semanas "
						+ DateUtils.getDaysBetweenDates(
								cabeza.getFechaInicioVigencia(), new Date()));
				log.debug("inicio " + grupoFamiliar.getFechaInicioVigencia());
				int numDias = DateUtils.getDaysBetweenDates(new Date(),
						cabeza.getFechaInicioVigencia());

				if (numDias < 56) {
					throw new DerechohabientesBusinessException(
							"La vigencia del asegurado es menor a 8 semanas ("
									+ numDias + " dias).",
							"error.prorroga.msg36.obstetricos");

				}
				*/
				
				if (!prorrogaPorObtetricosModalidad(cabeza)) {
					throw new DerechohabientesBusinessException(
							"El asegurado no cuenta con la modalidad requerida para esta pr\u00F3rroga",
							"error.prorroga.msg36.obstetricos");
				}

			}else{
				throw new DerechohabientesBusinessException(
						"El asegurado con calidad pensionado no puede realizar este tipo de pr\u00F3rroga",
						"error.prorroga.msg36.obstetricos");
			}

			//  Obtiene los miembros del grupo familiar,  Busca a la conyugue
			integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
							ParentescoEnum.CONYUGE.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId());

			// Si no la encuentra busca a la concubina
			if (integrantesFinales == null || integrantesFinales.size() == 0) {
				integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
								ParentescoEnum.CONCUBINARIO.getId(),
								EstadoDerechohabienteEnum.VIGENTE.getId());
			}
			
			// Si no la encuentra busca a persona en union civil
			if (integrantesFinales == null || integrantesFinales.size() == 0) {
				integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
								ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId(),
								EstadoDerechohabienteEnum.VIGENTE.getId());  
			}
		
			if (integrantesFinales == null || integrantesFinales.size() == 0) {
				if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("31") || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("34")){
					integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
							ParentescoEnum.CONYUGE.getId(),
							EstadoDerechohabienteEnum.BAJA.getId());
				}
			}
			
			if (integrantesFinales == null || integrantesFinales.size() == 0) {
				if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("31") || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("34")){
					integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
							ParentescoEnum.CONCUBINARIO.getId(),
							EstadoDerechohabienteEnum.BAJA.getId());
				}
			}
			
			if (integrantesFinales == null || integrantesFinales.size() == 0) {
				if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("31") || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("34")){
					integrantesFinales = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,
							ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId(),
							EstadoDerechohabienteEnum.BAJA.getId());
				}
			}
			
			//Valida que sea mujer la conyugue, concubina o persona en union civil
			if (integrantesFinales != null && integrantesFinales.size() > 0) {
				if (integrantesFinales.get(0).getDerechohabiente().getSexo().getIdSexo() != SexoEnum.MUJER.getId()) {
					integrantesFinales.remove(0);
				}
			}

			// Busca si la asegurada puede hacersele la prorroga
			List<GrupoFamiliar> asegurada = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,ParentescoEnum.ASEGURADO.getId(),EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
			if (asegurada == null || asegurada.size() == 0) {
				if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("31") || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("34")) {
					asegurada = grupoFamiliarDao.findGrupoFamiliarParentescoEstado(idAsignacioNSS,ParentescoEnum.ASEGURADO.getId(),EstadoDerechohabienteEnum.BAJA.getId());
				}
			}
			if (asegurada != null && asegurada.size() > 0) {
				if (asegurada.get(0).getDerechohabiente().getSexo().getIdSexo() == SexoEnum.MUJER.getId()) {
					integrantesFinales.addAll(asegurada);
				}
			}
			
			GrupoFamiliarUtil.validarIntegrantesVacios(integrantesFinales,
					"Los integrantes registrados no cuentan con los requisitos para solictar una pr\u00F3rroga por obst\u00E9tricos.",
					"error.prorroga.msg36.noIntegramtes_conyugue");
			
			mensajeException = "Los integrantes registrados no cuentan con los requisitos para solictar una pr\u00F3rroga por obst\u00E9tricos.";
			situacionException = "error.prorroga.msg36.noIntegramtes_conyugue";
			
		}
		
		log.debug("Se buscaran a los integrantes de acuerdo al perfil, para el tipo de prorroga: " +  tipoProrroga);
		integrantesFinales = validatePerfilDefuncionesBajas(integrantesFinales, usuario, tipoProrrog);
		
		if (integrantesFinales.isEmpty()) {
			throw new DerechohabientesBusinessException(mensajeException,"No existe informaci\u00F3n para mostrar.");
		} else {
			verificarTramiteAbiertos(integrantesFinales,saltaSolicitudes);
		}

		return integrantesFinales;
	}
	
	private List<GrupoFamiliar> validatePerfilDefuncionesBajas(List<GrupoFamiliar> integrantesFinales, Usuario usuario, Integer tipoProrrog) throws DerechohabientesBusinessException{
		//Tipos de baja que no debe tener un candidato para que se le pueda aplicar una prorroga
		List<Long> tiposBaja = new ArrayList<Long>();
		tiposBaja.add(TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.SUSPENCION.getId());
		
		//filtramos a los integrantes de acuerdo al perfil del usuario, en caso de laudo o acuerdo no se valida por lo que se manda nulo el
		//usuario
		if (tipoProrrog == TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo() ||  tipoProrrog == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()){
			usuario = null;
		}
		integrantesFinales = GrupoFamiliarUtil.filtlarCandidatosPorPerfil(integrantesFinales, usuario);
		//quitamos las defunciones
		log.debug("Se quitaran las defunciones");
		integrantesFinales = GrupoFamiliarUtil.quitarDefunciones(integrantesFinales);
		log.debug("antes de quitar las bajas la lista de candidatos es de: " + integrantesFinales.size());
		
		if (tipoProrrog == TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE.getCodigo()) {
			
			//tipo de baja que no deben tener los padrss
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
			
		}else if (tipoProrrog == TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo()) {
			
			//tipos de baja que no deben tener los integrantes
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
		
			
		}else if (tipoProrrog == TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()) {
			
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL.getId());
			
		}else if (tipoProrrog == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()) {
			
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
			tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
			
		}
		integrantesFinales = this.getIntegrantesSinBaja(integrantesFinales, tiposBaja);

        return integrantesFinales;
	}
	
	private List<GrupoFamiliar> getIntegrantesSinBaja(List<GrupoFamiliar> integrantesFinales, List<Long> tiposBaja) {
		List<GrupoFamiliar> integrantesAux = new ArrayList<GrupoFamiliar>();
		log.debug("Entro al validar tramites de baja con: " + tiposBaja );
		if(integrantesFinales != null && !integrantesFinales.isEmpty()) {
			for(GrupoFamiliar inte: integrantesFinales) {
				List<Long> idPersonas = new ArrayList<Long>();
				idPersonas.add(inte.getDerechohabiente().getIdPersona());
				log.debug("Se buscaran bajas de : " + tiposBaja + " para la persona " + inte.getDerechohabiente().getIdPersona());
				List<BajaDerechohabienteDto> bajas = bajaDerechohabienteServiceLocal.getBajaDerechohabiente(
						inte.getAsignacionNSS().getIdAsignacionNSS(), idPersonas, tiposBaja, true);
				
				if(bajas == null || bajas.isEmpty()) {
					log.debug("No se encontraron bajas");
					integrantesAux.add(inte);
				} else {
					log.debug("Se encontraron : "+  bajas.size() + " bajas");
				}
			}
		}
		
		return integrantesAux;
	}

	private void verificarTramiteAbiertos(List<GrupoFamiliar> integrantesFinales, Boolean saltarSolicitudes) throws DerechohabientesBusinessException{
		
		List<Tramite> otrosTramites = null;
		List<Long> idCandidatos = new ArrayList<Long>();
		
		for (GrupoFamiliar gf : integrantesFinales) {
			idCandidatos.add(gf.getDerechohabiente().getIdPersona());
		}
		
		if(!saltarSolicitudes) {
			try {
				otrosTramites = solicitudTramiteBusinessRemote.getTramitesAbierto(idCandidatos, integrantesFinales.get(0).getAsignacionNSS().getIdAsignacionNSS());
			} catch(Exception e) {
				otrosTramites = null;
				log.error("No fue posible localizar los tramites abiertos de la persona",e);
			}
			
			if(otrosTramites != null && otrosTramites.size() > 0 ){
				throw new DerechohabientesBusinessException("Es necesario concluir sus tramites pendientes - "
								+ otrosTramites.get(0).getTipoTramite()
										.getDescripcion(),
						"error.prorroga.msg36.vigenciaPermanente");
			}
		}
			
	}
	/**
	 * 
	 * @param TipoProrroga
	 * @param idPersona
	 * @param idUMFTramite
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public GrupoFamiliar getIntegranteProrroga(Long tipoProrroga,
			Long idPersona, Long idUMFTramite, AsignacionNSS asignacionNss,
			Boolean autorizacion) throws DerechohabientesBusinessException,
			Exception {

		Tramite tramitePersona = null;
		Solicitud solicitudPersona = null;
		GrupoFamiliar integrante = null;
		
		try {
			integrante = grupoFamiliarDao.getIntegranteGrupoFamiliar(asignacionNss.getIdAsignacionNSS(), idPersona);
		} catch (Exception e) {
			log.error("No se encontro al beneficiario"+ e.getMessage());
			DerechohabientesBusinessException.throwException("No fue posible localizar al integrante","No fue posible localizar al integrante");
		}

		// RNGD1118 UMF desde donde se realiza el tramite
		if (tipoProrroga == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo().longValue()
				|| tipoProrroga == TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo().longValue()) {

			// --------------------------------------------------------------------
			// Si no existen trámites de cambio de clínica o circunscripción, el 
			// derechohabiente debe estar en la clínica actual
			// --------------------------------------------------------------------
			if (!(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().equals(idUMFTramite))) {
				log.debug("La UMF del derechohabiente ("+ integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto()
						+ ") no coincide con la UMF donde fue registrado el tramite ("+ idUMFTramite + ").");
				throw new DerechohabientesBusinessException("La UMF del derechohabiente ("+ integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
						.getNombreCorto()+ ") no coincide con la UMF donde fue registrado el tramite.","error.prorroga.msg40");
			}
		}

		
		// En cualquier otro tipo de tramite se valida que no tenga un tramite
		// de prorroga abierto

		if (!autorizacion) {
			// Validar que tenga tramite de cambio de clinica o circuncripcion
			// foranea
			List<Long> tiposTramites = new ArrayList<Long>();
			tiposTramites.add(TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo()
					.longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()
					.longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()
					.longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()
					.longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()
					.longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
					.getCodigo().longValue());
			tiposTramites.add(TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL
					.getCodigo().longValue());

			try {
				solicitudPersona = solicitudTramiteBusinessRemote
						.getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(
								idPersona, tiposTramites,
								EstadoTramiteEnum.EN_ESPERA_AUTORIZACION
										.getCodigo().longValue());
			} catch (Exception e) {
				log.error("No se pudo obtener el Ã¯Â¿Â½ltimo trÃ¯Â¿Â½mite relacionado al derechohabiente. "
						+ e.getMessage());
				throw e;
			}

			// Si existe un tramite de prorroga en espera de autorizacion
			if (solicitudPersona != null) {
				String mensajeError = "";

				tramitePersona = solicitudPersona.getTramites().get(0);

				if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_ACUERDOS
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.acuerdo";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_ENFERMEDAD
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.enfermedad";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_ESTUDIOS
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.estudios";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_LAUDOS
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.laudo";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_OBSTETRICOS
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.obstetricos";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.vigenciaPermanente";
				} else if (tipoProrroga.longValue() == TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL
						.getCodigo().longValue()) {
					mensajeError = "error.prorroga.msg36.vigenciaTemporal";
				}

				log.debug("No se puede continuar debido a que se cuenta con una prorroga pendiente de autorizar");

				throw new DerechohabientesBusinessException(
						"El derechohabiente ya tiene registrada una prorroga ("
								+ tramitePersona.getTipoTramite()
										.getDescripcion()
								+ ") pendiente de autorizar.", mensajeError);
			}
		}// Fin autorizacion

		return integrante;
	}

	/**
	 * @param movimientoAseguradoDao
	 *            the movimientoAseguradoDao to set
	 */
	public void setMovimientoAseguradoDao(
			MovimientoAseguradoDaoLocal movimientoAseguradoDao) {
		this.movimientoAseguradoDao = movimientoAseguradoDao;
	}

	private Boolean prorrogaPorObtetricosModalidad(CabezaGrupoFamiliar cabeza) {
		/*
		 * 10, 13, 14, 17 y 30 Concubinas - 10, 13, 14, 30, 34, 35, 36, 38, 42,
		 * 43, 44, PensiÃ¯Â¿Â½n Conyugue - 10, 13, 14, 30, 34, 35, 36, 38, 42,
		 * 43, 44, PensiÃ¯Â¿Â½n
		 */

		Boolean resultado = Boolean.FALSE;

		/*
		if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad()
				.equals("10")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("13")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("14")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("17")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("30")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("34")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("35")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("36")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("38")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("42")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("43")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("44")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("27"))// Pension
						*/
		
		if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad()
				.equals("10")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("13")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("14")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("17")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("30")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("42")
				|| cabeza.getPatronSujetoObligado().getModalidad() // Se agregaron las siguiente lineas para las modalidades 31 y 34
						.getNumModalidad().equals("31")
				|| cabeza.getPatronSujetoObligado().getModalidad()
						.getNumModalidad().equals("34"))
		{
			resultado = Boolean.TRUE;
		}

		return resultado;
	}

	@Override
	public Tramite validaTramite(Solicitud solicitud, AsignacionNSS nss,
			Usuario usuario) throws DerechohabientesBusinessException,
			Exception {

		Date fechaHoy = new Date();
		
		// Tramite tramite = null;
		GrupoFamiliar integrante = null;
		TramiteProrroga prorroga = null;
		CabezaGrupoFamiliar cabeza;
		Fisica fisica = null;
		// Buscar el tramite
		// tramite = tramiteDao.getTramite(idTramite);
		Tramite tramite = solicitud.getTramites().get(0);

		// Cambiar a Cerrado
		tramite.getEstadoTramite().setIdEstadoTramitePersona(
				EstadoTramiteEnum.CERRADO.getCodigo());
		// cambiar el resultado a Si
		tramite.setResultado(true);
		// Razon resultado Normal
		tramite.getRazonResultado().setIdRazonResultado(
				RazonResultadoEnum.NORMAL.getCodigo().longValue());
		// Cambiar la fecha del tramite a la actual
		tramite.setFechaTramite(new Date());
		// TODO verificar fecha de actualizacion o ponerla directamente donde se
		// actualiza el tramie
		// tramite.setFechaRegistroActualizacion(new Date());

		// tramite.getSolicitud().getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId());

		// Guardar el tramite
		// tramiteDao.updateTramite(tramite);

		// TODO obtener la solicitud a partir del tramite
		// Actualizar la solicitud
		// tramite.getSolicitud();
		EstadoSolicitud estadoSol = new EstadoSolicitud();
		estadoSol
				.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitud.setEstadoSolicitud(estadoSol);
		solicitud.setFechaActualizacion(fechaHoy);

		// solicitudDao.updateSolicitud(solicitud);

		// Actualiza al integrante del grupo familiar
		integrante = grupoFamiliarDao.getIntegranteGrupoFamiliarByEstados(
				tramite.getPersona().getIdPersona(), null,
				nss.getIdAsignacionNSS());
		integrante.setEstadoDerechohabiente(new EstadoDerechohabiente());
		integrante.getEstadoDerechohabiente().setIdEstadoDerechohabiente(
				EstadoDerechohabienteEnum.VIGENTE.getId());

		// Buscar la prorroga
		prorroga = (TramiteProrroga) tramite;// JAXB_UTIL.xmlToObject(tramite.getDetalleTramiteXml());

		integrante.setFechaInicioVigencia(prorroga.getFechaInicioProrroga());
		integrante.setFechaFinVigencia(prorroga.getFechaFinProrroga());
		integrante.setFechaRegistroActualizacion(fechaHoy);
		integrante.setSubEstadoDerechohabiente(new SubEstadoDerechohabiente());
		if (prorroga.getCaracter().getIdCaracter()
				.equals(CaracterEnum.PROVISIONAL.getId())) {
			integrante.getSubEstadoDerechohabiente()
					.setIdSubEstadoDerechohabiente(
							SubestadoDerechohabienteEnum.TEMPORAL.getId());
		} else if (prorroga.getCaracter().getIdCaracter()
				.equals(CaracterEnum.DEFINITIVO.getId())) {
			integrante.getSubEstadoDerechohabiente()
					.setIdSubEstadoDerechohabiente(
							SubestadoDerechohabienteEnum.PERMANENTE.getId());
		}

		if (integrante.getParentesco().getIdParentesco()
				.equals(ParentescoEnum.ASEGURADO.getId())
				|| integrante.getParentesco().getIdParentesco()
						.equals(ParentescoEnum.PENSIONADO.getId())) {

			// System.out.println("Actualizando cabeza ******************************");
			// Se obtiene la cabeza del grupo familiar
			cabeza = cabezaDao.getCabezaGrupoFamiliar(integrante
					.getAsignacionNSS().getIdAsignacionNSS());

			cabeza.setFechaInicioVigencia(prorroga.getFechaInicioProrroga());
			cabeza.setFechaFinVigencia(prorroga.getFechaFinProrroga());
			cabeza.setEstadoDerechohabiente(new EstadoDerechohabiente());
			cabeza.getEstadoDerechohabiente().setIdEstadoDerechohabiente(
					EstadoDerechohabienteEnum.VIGENTE.getId());
			cabeza.setSubEstadoDerechohabiente(new SubEstadoDerechohabiente());
			cabeza.getSubEstadoDerechohabiente().setIdSubEstadoDerechohabiente(
					SubestadoDerechohabienteEnum.TEMPORAL.getId());

			// Se actualiza la cabeza del grupo familiar
			cabezaDao.updateCabezaGrupoFamiliar(cabeza);

		}

		prorroga.setGrupoFamiliar(integrante);
		prorroga.setFisica(integrante.getDerechohabiente());
		Long tipoProrroga = prorrogaParserServiceLocal.getTipoProrrogaPorTipoTramite(prorroga.getTipoTramite().getIdTipoTramite());
		prorroga.setIdTipoProrroga(tipoProrroga);
		prorrogaDao.saveProrroga(prorroga);

		// Actualiza al integrante del grupo familiar
		grupoFamiliarDao.updateIntegrante(integrante);
		
		fisica = tramite.getPersona();
		tramite.setPersona(usuario.getFisica());
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(prorroga);
		// solicitudDao.insertBitacoraSegTramite(tramite);
		solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), "",
				usuario.getFisica());
	
		//Se manda llamar al WS
		try {
			finalizaSolicitudService.finalizaSolicitud(solicitud);
			log.debug("sali del servicio para afectar");
			
		} catch (IllegalArgumentException e) {
			log.error(e);
			new SolicitudException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			new SolicitudException(e.getMessage());
		}

		tramite.setPersona(fisica);
		return tramite;
	}

	/**
	 * @param cabezaDao
	 *            the cabezaDao to set
	 */
	public void setCabezaDao(CabezaGrupoFamiliarDaoLocal cabezaDao) {
		this.cabezaDao = cabezaDao;
	}

	private Date fechaDefaultVigenciaPermanente() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 01);
		calendar.set(Calendar.MONTH, 00);
		calendar.set(Calendar.YEAR, 3000);

		return calendar.getTime();
	}

	@Override
	public void actalizaProrroga(Solicitud solProrroga) throws Exception {

		TramiteProrroga prorroga = this.getProrrogaFromSolicitud(solProrroga);

		prorrogaDao.updateProrroga(prorroga);
	}

	@Override
	public void bajaProrroga(TramiteProrroga prorroga) {
		/*
		 * TramiteProrroga prorroga = null;
		 * 
		 * prorroga = this.getProrrogaFromSolicitud(solProrroga);
		 */

		prorroga.getEstadoProrroga().setIdEstadoProrroga(
				EstadoProrrogaEnum.CANCELADA.getId());
		prorroga.setFechaConclusion(new Date());
		// TODO checar decha de actualizacion
		// prorroga.setFechaRegistroActualizacion(new Date());
		prorroga.setFechaFinProrroga(new Date());

		try {
			prorrogaDao.updateProrroga(prorroga);
		} catch (Exception e) {
			log.error("No se pudo cancelar la prorroga ", e);
		}

	}

	@Override
	public TramiteProrroga getProrrogaActiva(Long idAsignacionNss,Long idPersona){

		TramiteProrroga prorroga = null;
		
		try {
			prorroga = prorrogaDao.getProrrogaActivaPersona(idAsignacionNss, idPersona);
		} catch (Exception e) {
			log.error("error al obtener la prorroga", e);
		}

		return prorroga;
	}

	private TramiteProrroga getProrrogaFromSolicitud(Solicitud solicitud) {

		TramiteProrroga prorroga = null;

		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteProrroga) {
				prorroga = (TramiteProrroga) tramite;
				break;
			}
		}

		return prorroga;
	}

	@Override
	public CabezaGrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacioNSS)
			throws DerechohabientesBusinessException {
		try {
			CabezaGrupoFamiliar cabeza = grupoFamiliarDao
					.getCabezaGrupoFamiliar(idAsignacioNSS);
			return cabeza;
		} catch (Exception e) {
			throw new DerechohabientesBusinessException(e.getMessage());
		}
	}

	private boolean esModalidadValida(List<Modalidad> modalidadesActivas, Long idTipoProrroga) {
		
		List<String> modalidadesPermitidas = this.getModalidadesParaProrroga(idTipoProrroga);
		Boolean modalidadValida = false;
		
		
			log.debug("Se encontraron modalidades");
			for(Modalidad mod: modalidadesActivas) {
				log.debug("las modalidad encontrada es: " + mod.getNumModalidad());
				log.debug("La modalidad " + mod.getNumModalidad() + " es permitida ? : " + modalidadesPermitidas.contains(mod.getNumModalidad()));
				if(modalidadesPermitidas.contains(mod.getNumModalidad())) {
					log.debug("Modalidades permitidas" + modalidadValida);
					modalidadValida = true;
					break;
				}
			
		}
		
		return modalidadValida;

	}
	
	private List<String> getModalidadesParaProrroga(Long tipoProrroga) {
		List<String> listaModalidadesPermitidas = new ArrayList<String>();
		Boolean isLaudo = tipoProrroga.equals(TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo().longValue());
				
		Boolean isAcuerdo = tipoProrroga.equals(TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo().longValue());
		listaModalidadesPermitidas.add("10");
		listaModalidadesPermitidas.add("13");
		listaModalidadesPermitidas.add("14");
		listaModalidadesPermitidas.add("30");
		
		if(isLaudo) {
			listaModalidadesPermitidas.add("32");
			listaModalidadesPermitidas.add("33");
			listaModalidadesPermitidas.add("34");
		//	listaModalidadesPermitidas.add("27");
			listaModalidadesPermitidas.add("00");
		}
		if(isAcuerdo) {
			listaModalidadesPermitidas.add("00");
		}
		
		
		listaModalidadesPermitidas.add("35");
		listaModalidadesPermitidas.add("36");
		listaModalidadesPermitidas.add("38");
		listaModalidadesPermitidas.add("42");
		listaModalidadesPermitidas.add("43");
		listaModalidadesPermitidas.add("44");
		// Agregar modalidades 31 y 34
		listaModalidadesPermitidas.add("31");
		listaModalidadesPermitidas.add("34");
		
		
		
		return listaModalidadesPermitidas;
	}

	/*
	 * Metodo para el registro de tramite de prorroga desde el portal
	 * 
	 */
	
	/**
	 * Este metodo es usado para crear un tramite y solictud de tipo prorroga.
	 * Actualmente es usado desde WizardProrrogaDerechohabienteController 
	 * @author juan.osorioal
	 * @param idDerechohabiente
	 * @param usuario
	 * @param aseguradoPensionado
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	
	@Override
	public Solicitud guardaTramiteProrroga(Long idDerechohabiente,
			Usuario usuario, AsignacionNSS asignacionNSS,
			OrigenSolicitudEnum origen, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum tipoProrroga) throws DerechohabientesBusinessException {
		
		
		GrupoFamiliar derechohabiente = null;
		try {
			derechohabiente = grupoFamiliarDao
					.getIntegranteGrupoFamiliar(
							asignacionNSS.getIdAsignacionNSS(),
							idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(e1.getCause()
					.getMessage(), "error.busqueda.integrante");
		}

		if (derechohabiente == null)
			DerechohabientesBusinessException.throwException(
					"No se encontro al integrante del grupo familiar",
					"exception.RNGD0003");
		
		if(derechohabiente.getDerechohabiente().getFechaNacimiento() != null) {
			Long edad = DateUtils.getEdadRedondeadaEnAnios(derechohabiente.getDerechohabiente().getFechaNacimiento());
			if(edad > 25) {
				DerechohabientesBusinessException.throwException("No es posible aplicar la prorroga ya que el beneficiario tiene mas de 25 a&ntilde;os", 
						"No es posible aplicar la prorroga ya que el beneficiario tiene mas de 25 a&ntilde;os");
			}
		}

		Solicitud solicitud = null;

		try {
			solicitud = tramiteServiceLocal.guardarProrroga(derechohabiente, tipoProrroga, usuario, 
					asignacionNSS, new TramiteProrroga(), origen);
			
		} catch (Exception e) {
			log.error("Error al guardar la solicitud", e);
			DerechohabientesBusinessException
					.throwException("Error al guardar la solicitud"
							+ e.getCause().getMessage(),
							ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}

		return solicitud;
	}
	
	/**
	 * Metodo para finalizar una solicitud de prorroga
	 * @param solicitud - Solicitud , el objeto debe contener al menos el id de la solicitud
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 */
	@Override
	public Solicitud finalizarSolicitudProrroga(Solicitud solicitud)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException {
		
		if(solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
		}
		
		//Consultamos la solicitud a finalizar
		solicitud = solicitudBusinessRemote.consultar(solicitud);
		
		//Si la solicitud no es nula 
		if(solicitud != null) {
			//Verificamos que la solicitud contenga al menos un tramite
			if(solicitud.getTramites().isEmpty()) {
				log.debug("La solicitud no contiene tramites");
				throw new SolicitudNoValidaException("La solicitud no contiene tramites");
			} else {
				log.debug("La solicitud no contiene tramites");
				TramiteProrroga tramiteProrroga = null;
				//Verificamos que la solicitud contenga tramites de tipo prorroga
				for(Tramite tramite: solicitud.getTramites()) {
					if(tramite instanceof TramiteProrroga) {
						tramiteProrroga = (TramiteProrroga) tramite;
						break;
					}
				}
				
				//Si no existe algun tramite de prorroga lanzamos una excepcion
				if(tramiteProrroga == null) {
					throw new SolicitudNoValidaException("No existen tramites de prorroga en la solicitud");
				}
				
				//Se inserta un registro en DIT_PRORROGA
				EstadoProrroga  estado = new EstadoProrroga();
				estado.setIdEstadoProrroga( EstadoProrrogaEnum.ACTIVA.getId() );
//				tramiteProrroga.setGrupoFamiliar(grupoFamiliar);
				tramiteProrroga.setEstadoProrroga(estado);
				Long tipoProrroga = prorrogaParserServiceLocal.getTipoProrrogaPorTipoTramite(tramiteProrroga.getTipoTramite().getIdTipoTramite());
				tramiteProrroga.setIdTipoProrroga(tipoProrroga);
				
				
				prorrogaEntity.insert(tramiteProrroga);
				
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(tramiteProrroga);
				//guardamos los documentos probatorios del tramite
				try {
					if(solicitud.getOrigenSolicitud().getIdOrigenSolicitud().longValue() != OrigenSolicitudEnum.INTERNET_TSPI.getId().longValue()){
						documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
						log.error("Oguardamos los documentos probatorios del tramite");
					}
				} catch(DocumentoProbatorioException e) {
					log.error("Ocurrió un error al guardar los documentos",e);
				} catch (TramiteNoEncontradoException e) {
					log.error("No se encontro tramite", e);
				}
				
				//mandamos a llamar al servicio local para que se establezcan el resultado y la razon del resultado
				try{
					
					String observaciones = null;
					if (StringUtils.isNotBlank(tramiteProrroga.getObservacion())) {
						observaciones = tramiteProrroga.getObservacion().length() > 500 ? tramiteProrroga.getObservacion().substring(0,500) : tramiteProrroga.getObservacion();
					}
					solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), observaciones, null);
				} catch (DerechohabientesBusinessException e) {
					log.error("Ocurrio un error al actulizar la solicitud");
					throw new SolicitudException();
				}
				//Se manda llamar al WS
				try {
					finalizaSolicitudService.finalizaSolicitud(solicitud);
				} catch (IllegalArgumentException e) {
					log.error(e);
					new SolicitudException(e.getMessage());
				} catch (Exception e) {
					log.error(e);
					new SolicitudException(e.getMessage());
				}
			}
		} else {
			throw new SolicitudNoValidaException("La solicitud no puede ser nula");
		}		
		return solicitud;
	}
	
}
