package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

//import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.resultantes.CartillaResultanteBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.RazonResultadoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AcuseTramitesVentanillaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudSIEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.EmailServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.ObtCifrasSolicitudProceso;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.EmailDataWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Cesar Garcia Mauricio
 * 
 */
@Stateless(name = "solicitudBusiness", mappedName = "solicitudBusiness")
public class SolicitudBusiness extends AbstractServiceBusiness implements SolicitudBusinessRemote {
    @EJB
    private transient SolicitudEntityLocal solicitudEntity;
    @EJB
    private SolicitudQueueProducerRemote solicitudProducer;
    @EJB
    ActividadEcServiceRemote clasificacionService;
    @EJB
    AfiliacionServiceBusinessRemote afiliacionService;
    @EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
    @EJB
    private ArpBusinessRemote arpBusinessRemote;
    @EJB
    private ReporteBusinessRemote reporteBusinessRemote;
    @EJB
    private SolicitudSIEQueueProducerRemote solicitudSIEProducer;
    @EJB
    private ReportesBeneficiosBusinessRemote reportesBeneficiosBusinessRemote;
    @EJB
    private AcuseTramitesVentanillaBusinessRemote acuseTramitesVentanillaBusinessRemote;
    @EJB
    private SolicitudServiceBusinessRemote solicitudServiceBusiness;
    @EJB(name = "documentosService", mappedName = "documentosService")
	private DocumentosServiceRemote documentosServiceRemote; 
    @EJB(name = "grupoFamiliarService", mappedName = "grupoFamiliarService")
    private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
    @EJB
    private EmailServiceUtilityLocal emailServiceUtility;
    
    @EJB
    private RazonResultadoEntityLocal razonResultadoEntity;
    
    @EJB
    private AfiliacionGlobalServiceRemote afiliacionGlobalServiceRemote;
    
//    @EJB(name = "bovedaBusiness", mappedName = "bovedaBusiness")
//	private BovedaRemote bovedaRemote;
    
    @Override
    public Solicitud crear(final Solicitud solicitud) throws SolicitudNoValidaException {
        Solicitud solicitudNueva = null; // NOPMD
        validarSolicitud(solicitud);
        if (solicitud != null) {
//            solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
//            solicitud.getEstadoSolicitud().setDescripcion(EstadoSolicitudEnum.REGISTRADA.getDescripcion());
            final List<Tramite> listaTramites = solicitud.getTramites();
            for (Tramite tramite : listaTramites) {
                // INICIALIZAMOS CADA TRAMITE A SU ESTADO INICIAL UNICAMENTE SI NO SE PROPORCIONA UN ESTADO
            	if(tramite.getEstadoTramite()== null){
            		tramite.setEstadoTramite(new EstadoTramite());
            	}
            	if(tramite.getEstadoTramite().getIdEstadoTramitePersona()==null){
            		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
                    tramite.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
            	}
                
                /*
                 * ESTADO & CALIFICACION DE LA PERSONA DEL TRAMITE DEBEN
                 * VENIR DEBIDAMENTE CONFIGURADOS DESDE EL CLIENTE.
                 */
                if (tramite instanceof TramiteFisica) {
                    ((TramiteFisica) tramite).getFisica().asignarPais();
                } else if(tramite instanceof TramiteRepresentanteLegal) {
                	((TramiteRepresentanteLegal) tramite).getFisica().asignarPais();
                }
            }
            solicitudNueva = solicitudEntity.crear(solicitud);
        }

        return solicitudNueva;
    }

    @Override
	public Solicitud crearSolicitudInicial(EstadoSolicitud estadoSolicitud,
			TipoSolicitud tipoSolicitud, OrigenSolicitud origenSolicitud,
			Usuario usuario) throws SolicitudNoValidaException {
		Date fechaActual = new Date();

		Solicitud solicitud = new Solicitud();

		// Datos generales
		solicitud.setFechaSolicitud(fechaActual);

		// Estado de la solicitud
		if (estadoSolicitud != null) {
			Integer idEstadoSolicitud = estadoSolicitud.getIdEstadoSolicitud();

			if (idEstadoSolicitud != null
					&& idEstadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
				solicitud.setFechaConclusion(fechaActual);
			}
		} else {
			estadoSolicitud = new EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		}
		solicitud.setEstadoSolicitud(estadoSolicitud);

		// Tipo solicitud
		if (tipoSolicitud != null) {
			solicitud.setTipoSolicitud(tipoSolicitud);
		} else {
			log.warn("la solicitud no tiene definido el tipo de solicitud.");
		}

		// Origen de Solicitud
		if (origenSolicitud == null) {
			origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		}
		solicitud.setOrigenSolicitud(origenSolicitud);

		// Usuario asociado
		if (usuario != null) {
			solicitud.setSolicitante(usuario);
		}

		return solicitud;
	}

    @Override
	public Solicitud crearSolicitudInicialPorEnum(EstadoSolicitudEnum estadoSolicitudInicial,
			TipoSolicitudEnum tipoSolicitudInicial, OrigenSolicitudEnum origenSolicitudInicial, Usuario usuario)
			throws SolicitudNoValidaException {

		// Estado de la solicitud
		EstadoSolicitud estadoSolicitud;
		if (estadoSolicitudInicial != null) {
			estadoSolicitud = new EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(estadoSolicitudInicial.getCodigo());
		} else {
			estadoSolicitud = null;
		}

		// Tipo solicitud
		TipoSolicitud tipoSolicitud = null;
		if (tipoSolicitudInicial != null) {
			tipoSolicitud = new TipoSolicitud();
			tipoSolicitud.setIdTipoSolicitud(tipoSolicitudInicial.getValor().longValue());
		} else {
			log.warn("la solicitud no tiene definido el tipo de solicitud.");
		}

		// Origen de Solicitud
		OrigenSolicitud origenSolicitud;
		if (origenSolicitudInicial != null) {
			origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(origenSolicitudInicial.getId());
		} else {
			origenSolicitud = null;
		}

		return crearSolicitudInicial(estadoSolicitud, tipoSolicitud, origenSolicitud, usuario);
	}

    @Override
	public Tramite inicializarTramite(Tramite tramite,
			EstadoTramite estadoTramite, TipoTramite tipoTramite)
			throws SolicitudNoValidaException {
		Date fechaActual = new Date();

		// Datos generales
		tramite.setFechaTramite(fechaActual);
		tramite.setFechaPresentacion(fechaActual);

		// Estado del tramite
		if (estadoTramite != null) {
			Integer idEstadoTramite = estadoTramite.getIdEstadoTramitePersona();

			if (idEstadoTramite != null
					&& idEstadoTramite.equals(EstadoTramiteEnum.CERRADO.getCodigo())) {
				tramite.setFechaConclusion(fechaActual);
			}
		} else {
			estadoTramite = new EstadoTramite();
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		}
		tramite.setEstadoTramite(estadoTramite);

		// Tipo de tramite
		if (tipoTramite != null) {
			tramite.setTipoTramite(tipoTramite);
		} else {
			log.warn("al menos uno de sus tramites no tiene definido el tipo de tramite.");
		}

		return tramite;
	}

    @Override
	public Tramite inicializarTramitePorEnum(Tramite tramite,
			TipoTramiteEnum tipoTramiteInicial, EstadoTramiteEnum estadoTramiteInicial)
			throws SolicitudNoValidaException {

		// Estado del tramite
		EstadoTramite estadoTramite;
		if (estadoTramiteInicial != null) {
			estadoTramite = new EstadoTramite();
			estadoTramite.setIdEstadoTramitePersona(estadoTramiteInicial.getCodigo());
			estadoTramite.setDescripcion(estadoTramiteInicial.getDescripcion());
		} else {
			estadoTramite = null;
		}

		// Tipo de tramite
		TipoTramite tipoTramite = null;
		if (tipoTramiteInicial != null) {
			tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(tipoTramiteInicial.getCodigo());
			tramite.setTipoTramite(tipoTramite);
		} else {
			log.warn("al menos uno de sus tramites no tiene definido el tipo de tramite.");
		}

		return inicializarTramite(tramite, estadoTramite, tipoTramite);
	}

	@Override
	public Solicitud asociarTramiteSolicitud(Solicitud solicitud,
			Tramite tramite, EstadoTramite estadoTramite,
			TipoTramite tipoTramite) throws SolicitudNoValidaException {
		Tramite tramiteInicial = inicializarTramite(tramite, estadoTramite, tipoTramite);

		// Asociacion de solicitudes
		if (solicitud.getTramites() == null) {
			List<Tramite> listTramite = new ArrayList<Tramite>();
			listTramite.add(tramiteInicial);

			solicitud.setTramites(listTramite);
		} else {
			solicitud.getTramites().add(tramiteInicial);
		}

		return solicitud;
	}

	@Override
	public Solicitud asociarTramiteSolicitudPorEnum(Solicitud solicitud,
			Tramite tramite, TipoTramiteEnum tipoTramiteInicial,
			EstadoTramiteEnum estadoTramiteInicial)
			throws SolicitudNoValidaException {

		Tramite tramiteInicial = inicializarTramitePorEnum(tramite,
				tipoTramiteInicial, estadoTramiteInicial);

		// Asociacion de solicitudes
		if (solicitud.getTramites() == null) {
			List<Tramite> listTramite = new ArrayList<Tramite>();
			listTramite.add(tramiteInicial);

			solicitud.setTramites(listTramite);
		} else {
			solicitud.getTramites().add(tramiteInicial);
		}

		return solicitud;
	}

	/**
     * Valida que la solicitud: tenga definido tipo, al menos un tramite y que
     * los tramites tengan tipo de tramite definido.
     * 
     * @param solicitud
     * @return
     * @throws SolicitudNoValidaException 
     */
    private void validarSolicitud(final Solicitud solicitud) throws SolicitudNoValidaException {
        if (solicitud == null) {
            throw new SolicitudNoValidaException("la solicitud no debe ser nula!.");
        } else if (solicitud.getTipoSolicitud() == null || Utilerias.isBlank(solicitud.getTipoSolicitud().getIdTipoSolicitud())) {
            throw new SolicitudNoValidaException("la solicitud no tiene definido el tipo de solicitud.");
        } else if (solicitud.getTramites() == null || solicitud.getTramites().isEmpty()) {
            throw new SolicitudNoValidaException("la solicitud no tiene tramites.");
        } else {
            for (Tramite tramite : solicitud.getTramites()) {
                if(tramite.getTipoTramite() == null || Utilerias.isBlank(tramite.getTipoTramite().getIdTipoTramite())) {
                    throw new SolicitudNoValidaException("al menos uno de sus tramites no tiene definido el tipo de tramite.");
                }
            }
        }
        
        //SI NO SE PROPORCIONA ESTADO DE SOLICITUD SE INICIALIZA COMO REGISTRADA
        if(solicitud.getEstadoSolicitud()==null){
        	solicitud.setEstadoSolicitud(new EstadoSolicitud());
        	solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
        			EstadoSolicitudEnum.REGISTRADA.getCodigo());
        	solicitud.getEstadoSolicitud().setDescripcion(
        			EstadoSolicitudEnum.REGISTRADA.getDescripcion());
        }else if(solicitud.getEstadoSolicitud()!=null 
    			&& solicitud.getEstadoSolicitud().getIdEstadoSolicitud()==null){
        	solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
        			EstadoSolicitudEnum.REGISTRADA.getCodigo());
        	solicitud.getEstadoSolicitud().setDescripcion(
        			EstadoSolicitudEnum.REGISTRADA.getDescripcion());
        }
        
    }

    @Override
    public Solicitud consultar(Solicitud solicitud) throws SolicitudNoEncontradaException {
        solicitud = solicitudEntity.consultar(solicitud,true);
        
        
        long idTipoTramite = 0;
		for (Tramite tramite : solicitud.getTramites()) {
			
			idTipoTramite = tramite.getTipoTramite().getIdTipoTramite()
					.longValue();
			
			tramite.setDocumentoPorTipos(this.solicitudServiceBusiness
					.obtenerDocumentosResultantesPorTipoTramite(idTipoTramite));
		}
		
		//this.verificarDocumentosRegistro(solicitud);
		
		return solicitud;
    }

    
	@Override
	public Solicitud consultarPorIdTramite(Long idTramite)
			throws SolicitudNoEncontradaException {
		return solicitudEntity.consultarPorIdTramite(idTramite);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Solicitud consultarPorFolioSolicitud(String folio)
			throws SolicitudNoEncontradaException {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folio);
		Solicitud sol = solicitudEntity.consultarFolio(solicitud, true);
		if (sol.getTramites()
				.get(0)
				.getTipoTramite()
				.getIdTipoTramite()
				.equals(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo()
						.longValue())) {
			String descripcion = solicitudEntity
					.consultarDescripcionEstadoSolicitud(folio);
			sol.getEstadoSolicitud().setDescripcion(descripcion);
		}
		return sol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Solicitud consultarFolio(Solicitud solicitud)
			throws SolicitudNoEncontradaException {		
		solicitud = solicitudEntity.consultarFolio(solicitud,true); 
		long idTipoTramite = 0;
		for (Tramite tramite : solicitud.getTramites()) {			
			idTipoTramite = tramite.getTipoTramite()
				.getIdTipoTramite().longValue();			
			tramite.setDocumentoPorTipos(this.solicitudServiceBusiness
				.obtenerDocumentosResultantesPorTipoTramite(idTipoTramite));
		}		
		this.verificarDocumentosRegistro(solicitud);
		removerDocumentosTramite(solicitud);
		return solicitud;
	}
	
	/**
	 * Para tramites de VENTANILLA remover documento CARTA DE TERMINOS Y CONDICIONES
	 * Para tramites de INTERNET remover documento ACUSE TRAMITE (este documento es Acuse de Ventanilla)
	 * 
	 * @param solicitud
	 */
	private void removerDocumentosTramite(Solicitud solicitud) {
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum
			.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		for(Tramite tramite: solicitud.getTramites()) {
			if(origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA)){
				if(!CollectionUtils.isEmpty(tramite.getDocumentoPorTipos())){
					Iterator<DocumentoPorTipo> it = tramite.getDocumentoPorTipos().iterator();
					while (it.hasNext()) {
						DocumentoPorTipo documento = (DocumentoPorTipo)it.next();
						if(documento.getIdDocumentoPorTipo()
								.equals(DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId())){
							it.remove();
						}
					}
				}
			}else if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET)){
				if(!CollectionUtils.isEmpty(tramite.getDocumentoPorTipos())){
					Iterator<DocumentoPorTipo> it = tramite.getDocumentoPorTipos().iterator();
					while (it.hasNext()) {
						DocumentoPorTipo documento = (DocumentoPorTipo)it.next();
						if(documento.getIdDocumentoPorTipo()
								.equals(DocumentoPorTipoEnum.ACUSE_TRAMITE.getId())){
							it.remove();
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Metodo para modificar los documentos en una solicitud de registro de derechohabiente, ya que cuando 
	 * la solicitud tambien incluye un tramite de circunscripcio, cambio de clinica o cambio de medico
	 * algunos documentos los pone dobles, como el sav002, la cartilla y el 4305a que son los documentos que 
	 * los tres tramites 
	 * @param solicitud
	 */
	private void verificarDocumentosRegistro(Solicitud solicitud) {
		Long tipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
		
		if(tipoSolicitud.equals(TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue())) {
			
			TramiteRegistroDerechohabiente tramiteRegistro = null;
			Map<Long,DocumentoPorTipo> pilaDocumentos = new HashMap<Long, DocumentoPorTipo>();
			
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteRegistroDerechohabiente) {
					tramiteRegistro = (TramiteRegistroDerechohabiente) tramite;
				}
			}
			
			for(DocumentoPorTipo doc: tramiteRegistro.getDocumentoPorTipos()) {
				pilaDocumentos.put(doc.getIdDocumentoPorTipo(), doc);
			}
			
			for(Tramite tramite: solicitud.getTramites()) {
				if(!tramite.getTramiteId().equals(tramiteRegistro.getTramiteId())) {
					List<DocumentoPorTipo> aux = new ArrayList<DocumentoPorTipo>();
					
					for(DocumentoPorTipo doc: tramite.getDocumentoPorTipos()) {
						if(!pilaDocumentos.containsKey(doc.getIdDocumentoPorTipo())) {
							aux.add(doc);
						}
					}
					
					for(DocumentoPorTipo docA : aux) {
						pilaDocumentos.put(docA.getIdDocumentoPorTipo(), docA);
					}
					
					tramite.setDocumentoPorTipos(aux);
				}
			}
			
		}
		
	}
	
	@Override
    public Solicitud actualizarTramites(final Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        return solicitudEntity.actualizarTramites(solicitud);
    }

	@Override
    public Solicitud actualizarTramitesMarcaOSB(final Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        return solicitudEntity.actualizarTramitesMarcaOSB(solicitud);
    }

    @Override
    public Solicitud actualizaTramite(final Solicitud solicitud, Tramite tramite) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        return solicitudEntity.actualizaTramite(solicitud,tramite);
    }
    
    @Override
    public void cancelarTramite(final Tramite tramite) throws TramiteNoEncontradoException {
         solicitudEntity.cancelarTramite(tramite);
    }

    @Override
    public Solicitud actualizarEstados(final Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        return solicitudEntity.actualizarEstados(solicitud);
    }
    
    /**
     * 191807 020712
     * Este metodo consulta el estatus de una Solicitud. Si el estatus es EN_PROCESO(5) de 'EstadoSolicitudEnum.PENDIENTE_AUTORIZACION', entonces se lanzara 
     * una excepcion. Si no, no hara nada
     * @param solicitud
     * @throws SolicitudEnProcesoException
     */
    public void isEnProceso(Solicitud solicitud) throws SolicitudEnProcesoException{
    	
    	Solicitud solicitudResultado = solicitudEntity.consultarEstatus(solicitud);
    	this.log.warn("Estado de la solicitud : "  +solicitudResultado.getEstadoSolicitud().getIdEstadoSolicitud());
    	
    	
    	if(solicitudResultado != null){
    		if( solicitudResultado.getEstadoSolicitud() != null){
    			if(solicitudResultado.getEstadoSolicitud().getIdEstadoSolicitud().equals( EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
    	    		this.log.warn("El estado de la solicitud esta en proceso...");
    				throw new SolicitudEnProcesoException();
    	    	}
    		}
    	}
    	
    	this.log.warn(" so far so good..");
    	
    }

	@Override
	public List<Solicitud> obtenerSolicitudPorPersonaFisica(Long idPersonaFisica) {
		return solicitudEntity.obtenerSolicitudPorPersona(idPersonaFisica,
				TipoPersonaFiscal.FISICA, TipoSolicitudEnum.TODAS,
				EstadoSolicitudEnum.TODOS, true);
	}

	@Override
	public List<Solicitud> obtenerSolicitudPorPersonaMoral(Long idPersonaMoral) {
		return solicitudEntity.obtenerSolicitudPorPersona(idPersonaMoral,
				TipoPersonaFiscal.MORAL, TipoSolicitudEnum.TODAS,
				EstadoSolicitudEnum.TODOS, true);
	}
	
	
	
	@Override
	public List<Solicitud> obtenerSolicitudConDatosBasePorPersonaFisica(
			Long idPersonaFisica) {
		return solicitudEntity.obtenerSolicitudConDatosBasePorPersona(idPersonaFisica, TipoPersonaFiscal.FISICA, 
				TipoSolicitudEnum.TODAS,EstadoSolicitudEnum.TODOS);
	}

	@Override
	public List<Solicitud> obtenerSolicitudConDatosBasePorPersonaMoral(
			Long idPersonaMoral) {
		return solicitudEntity.obtenerSolicitudConDatosBasePorPersona(idPersonaMoral, TipoPersonaFiscal.MORAL, 
				TipoSolicitudEnum.TODAS,EstadoSolicitudEnum.TODOS);
	}

	@Override
	public Tramite crearTramiteASolicitud(Tramite tramite, Long idSolicitud) {
		
		tramite = this.solicitudEntity.crearTramiteASolicitud(tramite, idSolicitud);
		
		return tramite;
		
	}


	@Override
	public Solicitud obtenerSolicitudDePersonaPorTipoyEstado(Long idPersona,
			TipoPersonaEnum tipoPersona, TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {
		
		TipoPersonaFiscal tipoPersonaFiscal = null;
		if(tipoPersona.equals(TipoPersonaEnum.FISICA))
			tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
		else 
			tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
		
		List<Solicitud> solicitudes = solicitudEntity.obtenerSolicitudPorPersona(idPersona, tipoPersonaFiscal, tipoSolicitud, estadoSolicitud, false);
		
		if(solicitudes!=null && solicitudes.size()>1)
			throw new SolicitudException("Existen m�ltiples solicitudes que cumplen con el criterio");
		
		if(solicitudes!=null && solicitudes.size()==1)
			return solicitudes.get(0);
		
		return null;
	}
	
	

	@Override
	public Solicitud obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(
			Long idPersona, TipoPersonaEnum tipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
			EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {
		
		return obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado( idPersona,  tipoPersona,
			 tipoSolicitud,  tipoTramite,
			 estadoSolicitud, true);
	}
	
	@Override
	public Solicitud obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(
			Long idPersona, TipoPersonaEnum tipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
			EstadoSolicitudEnum estadoSolicitud, Boolean covertXml) throws SolicitudException {
		
		TipoPersonaFiscal tipoPersonaFiscal = null;
		if(tipoPersona.equals(TipoPersonaEnum.FISICA))
			tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
		else 
			tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
		
		List<Solicitud> solicitudes = solicitudEntity.obtenerSolicitudPorPersonaTramite(idPersona, tipoPersonaFiscal, tipoSolicitud, tipoTramite,estadoSolicitud, false, covertXml);
		
		if(solicitudes!=null && solicitudes.size()>1)
			throw new SolicitudException("Existen m�ltiples solicitudes que cumplen con el criterio");
		
		if(solicitudes!=null && solicitudes.size()==1)
			return solicitudes.get(0);
		
		return null;
	}
	
	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtro,
			boolean mostrarSolicInternet) {
		DatosSalidaPaginador<Solicitud> output = solicitudEntity
				.listarSolicitudesPorFiltro(input, filtro, mostrarSolicInternet);
		if (output.getAaData() != null && output.getAaData().size() > 0) {
			for (Solicitud solicitud : output.getAaData()) {
				if (solicitud.getSujetoObligado().getSubdelegacion() == null) {
					Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
					solicitud.getSujetoObligado().setSubdelegacion(
							subdelegacion);
				}
			}
		}

		return output;
	}
	
	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron( DatosEntradaPaginador<Solicitud> input,  FiltroSolicitud filtro) {
		DatosSalidaPaginador<Solicitud> output = solicitudEntity.listarSolicitudesPorFiltroParaPatron(input, filtro);
		if(output.getAaData()!= null && output.getAaData().size() >0){
			for(Solicitud solicitud : output.getAaData()){
				if(solicitud.getSujetoObligado().getSubdelegacion() == null){
					Subdelegacion subdelegacion = obtenerSubdelegacionDeSolicitud(solicitud);
					solicitud.getSujetoObligado().setSubdelegacion(subdelegacion);
				}
			}
		}
		
		
		return output;
	}
	
	
	private Subdelegacion obtenerSubdelegacionDeSolicitud(Solicitud solicitud){
		if(solicitud.getSubdelegacion()!=null)
			return solicitud.getSubdelegacion();
		else
			return crearSubdelegacionVacia();
	}
	
	private Subdelegacion crearSubdelegacionVacia(){
		Subdelegacion subdelegacion = new Subdelegacion();
		subdelegacion.setDelegacion(new Delegacion());
		return subdelegacion;
	}

	@Override
	public void actualizarSolicitudAEstatusConcluida(Long idSolicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		Solicitud solicitudEncontrada = consultar(solicitud);
		actualizarSolicitudAEstatusConcluida(solicitudEncontrada);
	}
	
	private Solicitud actualizarEstadoTramitesAConcluido(Solicitud solicitud){
		List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.CANCELADO.getCodigo()))
				continue;
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramitesActualizados.add(tramite);
		}
		solicitud.setTramites(tramitesActualizados);
		
		return solicitud;
	}

	private Solicitud actualizarEstadoTramitesAActivo(Solicitud solicitud){
		List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.CANCELADO.getCodigo()))
				continue;
			tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
			tramitesActualizados.add(tramite);
		}
		solicitud.setTramites(tramitesActualizados);
		
		return solicitud;
	}
	
	@Override
	public byte[] obtenerDocumento(Solicitud solicitud, Integer idTipoDocumento){
		
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue());
		log.error("Tipo de solicitud: "+solicitud.getTipoSolicitud().getIdTipoSolicitud());
		log.error("Enum Tipo de solicitud: "+tipoSolicitud);
		byte[] documento=null;
		switch(tipoSolicitud){
			case ACTUALIZACION_DE_CLASIFICACION:
				try{
					documento = clasificacionService.obtenerDocumentoModificacionSRTPorSolicitud(solicitud, idTipoDocumento);
				}catch(GestionPatronalBusinessException gpbe){
					gpbe.printStackTrace();
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]");
				}
				break;
			case ACTUALIZACION_DATOS_GENERALES:
				try {
					documento = afiliacionService.obtenerDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
				} catch (GestionPatronalBusinessException e) {
					e.printStackTrace();
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]");
				}
			case ACTUALIZACION_DATOS_PATRONALES:
				try {
					documento = afiliacionService.obtenerDocumentoModificacionDatosPatronales(solicitud, idTipoDocumento);
				} catch (GestionPatronalBusinessException e) {
					e.printStackTrace();
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]");
				}
			case ACTUALIZACION_CENTRO_TRABAJO:
				try{
					documento = clasificacionService.obtenerDocumentoModificacionSRTPorSolicitud(solicitud, idTipoDocumento);
				}catch(GestionPatronalBusinessException gpbe){
					gpbe.printStackTrace();
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]");
				}
				break;
			default:
				super.log.error("No existen instrucciones para este tipo de solicitud");
		}
		
		return documento;
	}

	@Override
	public byte[] obtenerDocumento(Long idSolicitud, Integer idTipoDocumento) {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		try {
			solicitud = consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			super.log.error("No se encontro la solicitud");
			e.printStackTrace();
			return null;
		}
		
		return obtenerDocumento(solicitud, idTipoDocumento);
	}
	
	
	@Override
	public void guardarDocumentosResultantesPorSolicitud(Solicitud solicitud) throws SolicitudNoValidaException{
		
//		Set<Long> documentosGenerados = new HashSet<Long>(); 
		Set<String> documentosGenerados = new HashSet<String>(); 
		Boolean isSolicitudDerechohabientes = false;
		
		
		CartillaResultanteBusiness cartillaBusiness = new CartillaResultanteBusiness(solicitudEntity, grupoFamiliarServiceRemote, documentosServiceRemote, firmaDigitalBusinessRemote);
		
		if(solicitud != null) {
			isSolicitudDerechohabientes = isSolicitudDerechohabientes(solicitud);
			
			if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()){
				log.debug("La solicitud contiene " + solicitud.getTramites().size() + "tramites");
				for(Tramite tramite: solicitud.getTramites()){
					if(tramite.getDocumentoPorTipos() != null && !tramite.getDocumentoPorTipos().isEmpty()) {

						log.debug("::: El tramite de " + tramite.getTipoTramite().getDescripcion() + " tiene " + tramite.getDocumentoPorTipos().size() + " documentos resultantes");
						for(DocumentoPorTipo documentos: tramite.getDocumentoPorTipos()) {
							try{
								
								Long idTipoDocumento = documentos.getIdDocumentoPorTipo();
								log.debug("::: Se va generar el documento " + documentos.getDocumento().getDesDocumento() + " para el tramite de " + tramite.getTipoTramite().getDescripcion());
								
								String idTipoDocumentoAGenerar = isSolicitudDerechohabientes ? (idTipoDocumento+"") : (idTipoDocumento+"_"+tramite.getTramiteId());
								if( idTipoDocumento.equals(DocumentoPorTipoEnum.CARTILLA.getId()) ){
									
									cartillaBusiness.obtenerDocumento(tramite,solicitud);
									log.debug("Se genero el documento: " + documentos.getDocumento().getDesDocumento());
									
								}else if( documentosGenerados.add(idTipoDocumentoAGenerar)  ){
									// si el tipo de tramite es de clasificacion y se genera el AMSRT
									Long idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
									if(idTipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().longValue())
											|| idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().longValue())											
													) {
										log.debug(":::: El tramite es de clasificacion, " + idTipoTramite);
										log.debug(":::: Voy a generar el documento: " + documentos.getIdDocumentoPorTipo());
										if(documentos.getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId())){
											this.obtenerDocumentoResultante(solicitud.getSolicitudId(), tramite.getTramiteId(),documentos.getIdDocumentoPorTipo().intValue());										
										}else if(documentos.getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.TIP.getId())){
											this.obtenerDocumentoResultante(solicitud, tramite.getTramiteId(), documentos.getIdDocumentoPorTipo().intValue());
										}
									}else{
										this.obtenerDocumentoResultante(solicitud, tramite.getTramiteId(), documentos.getIdDocumentoPorTipo().intValue());
									}
									
									log.debug("Se genero el documento: " + documentos.getDocumento().getDesDocumento());
									
								}
								
								
							}catch( NullPointerException e ){
								e.printStackTrace();
								// ---------------------------------------------
								// Si el tipo de documento es null
								// ---------------------------------------------
							}
						}
					} else {
						log.warn("El tramite de " + tramite.getTipoTramite().getDescripcion() + " no contiene ningun documento a generar");
					}
				}
			} else {
				log.error("la solicitud no contiene tramites");
				throw new SolicitudNoValidaException("La solictud no contiene tramites");
			}
		} else {
			log.error("La solicitud viene nula");
			throw new SolicitudNoValidaException("La solictud viene nula");
		}
	}
	
	private boolean isSolicitudDerechohabientes(Solicitud solicitud) {
		Long idTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
		
		if(idTipoSolicitud.equals(TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue())
			|| idTipoSolicitud.equals(TipoSolicitudEnum.BAJA_DERECHOHABIENTES.getValor().longValue()) ||
			idTipoSolicitud.equals(TipoSolicitudEnum.PRORROGA.getValor().longValue()) ||
			idTipoSolicitud.equals(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue())) {
			return true;
		}
		return false;
	}

	@Override
	public byte[] obtenerDocumentoResultante(Long idSolicitud, Long idTramite,Integer idTipoDocumento) {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		try {
			solicitud = consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			super.log.error("No se encontro la solicitud");
			e.printStackTrace();
			return null;
		}
		
		return obtenerDocumentoResultante(solicitud,idTramite, idTipoDocumento);
	}
	
	
	@Override
	public byte[] obtenerDocumentoResultante(Solicitud solicitud,Long idTramite, Integer idTipoDocumento){
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue());
		OrigenSolicitudEnum solicitudOrigen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		log.debug("Tipo de solicitud: "+solicitud.getTipoSolicitud().getIdTipoSolicitud() + "  Enum Tipo de solicitud: "+tipoSolicitud
				+ "   Tipo de documento: "+idTipoDocumento +  "  Id Tramite: "+ idTramite  + " Origen solicitud: " + solicitudOrigen != null ? solicitudOrigen.getDesc() : "");
		byte[] documento=null;
		Tramite tramite = null;
		Long idTipoTramite = null;
		
		//Verificamos si el id del tramite es nulo estamos pidiendo el comprobante de asignacion
		if(idTramite != null) {
			for(Tramite t: solicitud.getTramites()) {
				if(t.getTramiteId().equals(idTramite)) {
					if(!(t.getEstadoTramite().getIdEstadoTramitePersona()
							.equals(EstadoTramiteEnum.CANCELADO.getCodigo()))){
						tramite = t;
						idTipoTramite = t.getTipoTramite().getIdTipoTramite().longValue();
					} 

					break;
				}
			}
			
			if(tramite == null) {
				for(Tramite t : solicitud.getTramites()){
					if(!(t.getEstadoTramite().getIdEstadoTramitePersona()
							.equals(EstadoTramiteEnum.CANCELADO.getCodigo()))){
						tramite = t;
						idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
						break;
					}
				}
			}
		} else {
			//Verificamos que el tipo de solicitud sea de asignacion de nss
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue())) {
				//buscamos el tramite de asignacion ya que tambien puede traer uno de registro de persona
				tramites: for(Tramite tramiteA: solicitud.getTramites()) {
					if(tramiteA instanceof TramiteAsegurado) {
						//obtenemos el id del tramite para guardar el comprobante
						tramite = tramiteA;
						idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
						idTramite = tramite.getTramiteId();
						break tramites;
					}
				}
				
			} else {
				//En caso de que el tramite no sea del tipo de asignacion de nss el id del tramite sera del primer tramite que se encuentre
				//q no este cancelado
				for(Tramite t : solicitud.getTramites()){
					if(!(t.getEstadoTramite().getIdEstadoTramitePersona()
							.equals(EstadoTramiteEnum.CANCELADO.getCodigo()))){
						tramite = t;						
						idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
						idTramite = tramite.getTramiteId();
						break;
					}
				}
			}
		}
		
		if(idTipoDocumento.equals(DocumentoPorTipoEnum.ACUSE_TRAMITE.getId().intValue())) {
			if(tipoSolicitud.equals(TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO)){
//				try {
//					documento = bovedaRemote.recuperarDocumento(solicitud,TipoDocumentoCDAEnum.ACUSE,recuperarIdDocumento(solicitud, TipoDocumentoCDAEnum.ACUSE));
//				} catch (SolicitudNoEncontradaException e) {
//					super.log.error("Error al generar documento de ACUSE CDA  "+solicitud.getSolicitudId(), e);
//				} catch (BovedaCDAException bce){
//					super.log.error("Error al generar documento de ACUSE CDA  "+solicitud.getSolicitudId(), bce);
//				}
			}else if(solicitudOrigen.equals(OrigenSolicitudEnum.VENTANILLA)){
				documento = acuseTramitesVentanillaBusinessRemote.generarAcuseTramiteVentanilla(solicitud, tramite, idTipoTramite);
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId().intValue()) &&
            (solicitudOrigen.equals(OrigenSolicitudEnum.INTERNET) || solicitudOrigen.equals(OrigenSolicitudEnum.ECONOMIA))) {
			try{
				documento = clasificacionService.obtenerCartaTerminosFiel(solicitud);
			}catch(GestionPatronalBusinessException gpbe){
				super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]",gpbe);
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.ARP.getId().intValue())){
			
			try {
				documento = (byte[])arpBusinessRemote.getReporteArpPersona(solicitud, tramite);
			} catch (Exception e) {
				log.error("ocurrio un error al generar arp",e);
			}
				
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.TIP.getId().intValue())) {
				
			try {
				documento = (byte[])arpBusinessRemote.getReporteTipPersona(solicitud, tramite);
			} catch (Exception e) {
				log.error("Ocurrio un error al generar tip",e);
			}
			
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId().intValue())) {
			if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo().longValue())) {
				try {
					documento = afiliacionService.obtenerDocumentoModificacionDatosPatronales(solicitud, TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo());
				} catch (GestionPatronalBusinessException e) {
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]",e);
				}
			} else {
				try{
					documento = clasificacionService.obtenerDocumentoModificacionSRTPorSolicitud(solicitud,TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo());
				}catch(GestionPatronalBusinessException gpbe){
					super.log.error("Error al generar documento de modificacion SRT [Tipo: "+ idTipoDocumento+" solicitud: "+solicitud.getSolicitudId()+"]",gpbe);
				}
			}
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue())
				|| idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_LOCALIZACION_NSS.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = null;
			
			if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue())) {
				docPorTipo =  DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION;
			} else {
				docPorTipo =  DocumentoPorTipoEnum.COMPROBANTE_LOCALIZACION_NSS;
			}
			
			//documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), docPorTipo.getId());
			
			if(documento == null) {
				try {
					if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue())) {
						documento = reporteBusinessRemote.getComprobanteAsignacion(solicitud);
					} else {
						documento = reporteBusinessRemote.getComprobanteRecuperacion(solicitud);
					}
					//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), docPorTipo.getId(), documento);
				} catch (SolicitudNoEncontradaException e) {
					log.error("ocurrio un error al generar el comprobante del nss",e);
				}
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue())) {
			//documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId());
			
			if(documento == null) {
				documento = reporteBusinessRemote.getComprobanteAsignacionSimpleConQR(solicitud);
				if(documento != null) {
					//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId(), documento);
				}
			}
		}else if(idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ALTA_BENEFICIO_RISS.getId().intValue())) {
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
				tramite.getTramiteId(), DocumentoPorTipoEnum.COMPROBANTE_ALTA_BENEFICIO_RISS.getId());			
			if(documento == null) {
				documento = reportesBeneficiosBusinessRemote.generarReporteBeneficioRiss(solicitud);
				solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), 
					DocumentoPorTipoEnum.COMPROBANTE_ALTA_BENEFICIO_RISS.getId(), documento);				
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.SAV002.getId().intValue()) ){
			AsignacionNSS nss = null;
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.SAV002.getId());	
					*/
			if(documento == null) {
				Boolean registro = true;
				
				try {
					Long idOrigenSolicitud = null;
					Usuario usuario = null;
					
					if(solicitud != null && solicitud.getOrigenSolicitud() != null) {
						idOrigenSolicitud = solicitud.getOrigenSolicitud().getIdTipoSolicitud();
					} else {
						idOrigenSolicitud = OrigenSolicitudEnum.INTERNET.getId();
					}
					
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());

					if(tramite instanceof TramiteBajaDerechohabiente)
					{
						TramiteBajaDerechohabiente tramiteB = (TramiteBajaDerechohabiente) tramite;
						Derechohabiente dere = (Derechohabiente) tramiteB.getPersona();
						nss = dere.getAsignacionNSS();
						registro = false;

					} else if(tramite instanceof TramiteRegistroDerechohabiente) {
						TramiteRegistroDerechohabiente tramiteR = (TramiteRegistroDerechohabiente) tramite;
						nss = tramiteR.getDatosAsegurado();
					} else if(tramite instanceof TramiteCorreccionDerechohabiente) {
						TramiteCorreccionDerechohabiente tramiteC = (TramiteCorreccionDerechohabiente) tramite;
						Derechohabiente der = null;
						
						if(tramiteC.getPersona() != null) {
							der = (Derechohabiente) tramiteC.getPersona();
						} else if(tramiteC.getPersonas() != null)  {
							List<Fisica> personas = tramiteC.getPersonas();
							der = (Derechohabiente) personas.get(0);
						} else {
							der = new Derechohabiente();
							der.setAsignacionNSS(new AsignacionNSS(tramiteC.getIdAsignacionNss()));
						}
						
						nss = der.getAsignacionNSS();
					}else if( tramite instanceof TramiteCircunscripcionForanea ){
						Derechohabiente der = (Derechohabiente)((TramiteCircunscripcionForanea) tramite).getPersona();
						nss = der.getAsignacionNSS();
					}
					
					if(idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId())){
						usuario = solicitud.getSolicitante();
					}

					documento = (byte[]) documentosServiceRemote.generaSav002(nss, firma, usuario, tramite.getTramiteId(), idOrigenSolicitud,registro);
					if(documento != null) {

						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "SAV_002.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.SAV002.getId(), documento);		

					}
				}catch(Exception e) {
					e.printStackTrace();
					log.debug(e);
					log.error("Ocurrio un error al generar sav002", e);
				}
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.ACUSE_ACTUALIIZACION_CORREO_ELECTRONICO.getId().intValue()) ){
			try {
				
			documento = (byte[]) documentosServiceRemote.generarAcuseReciboElectronicoVentanilla(solicitud);

			}catch(Exception e) {
				e.printStackTrace();
				log.debug(e);
				log.error("Ocurrio un error al generar el acuse de recibo electronico", e);
			}
			
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.CARTILLA.getId().intValue()) ){

			CartillaResultanteBusiness cartillaBusiness = new CartillaResultanteBusiness(solicitudEntity, grupoFamiliarServiceRemote, documentosServiceRemote, firmaDigitalBusinessRemote); 
			List<ByteArrayOutputStream> listByteArray = cartillaBusiness.obtenerDocumento(tramite, solicitud);
			
			documento = ((ByteArrayOutputStream) documentosServiceRemote.concatenarByteStream(listByteArray, true)).toByteArray();
			
			
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.D_4305A.getId().intValue()) ){
			AsignacionNSS nss = null;
			Usuario usuario = null;
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.D_4305A.getId());
			*/
			if(documento == null) {
				try {
					Long idPersona = null;
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
				    usuario = new Usuario();
				    if(solicitud.getSolicitante() != null){
				    	usuario = solicitud.getSolicitante();
				    }
				    
					if(tramite instanceof TramiteRegistroDerechohabiente) {
						TramiteRegistroDerechohabiente tramiteR = (TramiteRegistroDerechohabiente) tramite;
						nss = tramiteR.getDatosAsegurado();
						//usuario = tramiteR.getUsuario();
						
						usuario.setIdUmf(tramiteR.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
					}
					else if( tramite instanceof TramiteFisica ){
						
						Fisica fisica = ((TramiteFisica) tramite).getFisica();
						log.debug("entre al tramite fisicca " + tramite.getTipoTramite().getIdTipoTramite() );
						//Si no se cuenta con el NSS se debe consulyar en base al idPersona
						List<AsignacionNSS> listaNSS = grupoFamiliarServiceRemote.getAsignacionNss( fisica.getIdPersona() );
						nss = listaNSS.get(0);
						idPersona = fisica.getIdPersona();
						GrupoFamiliar afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(nss.getIdAsignacionNSS(), nss.getIdPersona());
						usuario.setIdUmf(afectado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
					}
					else if( tramite instanceof TramiteCorreccionDerechohabiente ){
						
						Derechohabiente der = null;
						GrupoFamiliar afectado = null;
						
						
						TramiteCorreccionDerechohabiente correccion = (TramiteCorreccionDerechohabiente) tramite;
						Long idAsignacionNSS = correccion.getIdAsignacionNss();
						nss = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(idAsignacionNSS);
						
						
						if( correccion.getPersonas() != null && !correccion.getPersonas().isEmpty() ){
							Fisica persona = correccion.getPersonas().get(0);
							
							if(persona instanceof Derechohabiente) 
								der = (Derechohabiente) persona;
							
						}else{
							
							if(correccion.getPersona() instanceof Derechohabiente) {
								der = (Derechohabiente) correccion.getPersona();
							}
							
						}
						if(der == null) {
							afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(idAsignacionNSS, nss.getIdPersona());
						}else{
							afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(idAsignacionNSS, der.getIdPersona());
						}
							
							usuario.setIdUmf(afectado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
						
                        //usuario.setUsuario(nss.getCurp());
					
					}else if( tramite instanceof TramiteCircunscripcionForanea ){
						Derechohabiente der = (Derechohabiente)((TramiteCircunscripcionForanea) tramite).getPersona();
						nss = der.getAsignacionNSS();
						usuario.setIdUmf(((TramiteCircunscripcionForanea) tramite).getMedicoEnTurnoDestino().getUnidadMedicaFamiliar().getIdUMF());
					}
					
					documento = (byte[]) documentosServiceRemote.getDocumentoReporte4305A(nss, firma, usuario, solicitud.getOrigenSolicitud().getIdTipoSolicitud());
					
					if(documento != null) {
						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "D4305A.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.D_4305A.getId(), documento);
					}
				}catch(Exception e) {
					log.error("Ocurrio un error al generar 4305a", e);
				}
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.SAV007.getId().intValue()) ){

			AsignacionNSS nss = null;
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.SAV007.getId());
			*/
			if(documento == null) {
				try {
					Long idPersona = null;
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
					Derechohabiente der = null;
					
					if(tramite instanceof TramiteProrroga) {
						TramiteProrroga tramiteC = (TramiteProrroga) tramite;
						der = (Derechohabiente) tramiteC.getPersona();
						nss = der.getAsignacionNSS();
					}
					
					if(der != null) {
						documento = (byte[]) documentosServiceRemote.getDocumentoSav007(nss, firma, der.getIdPersona());
						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "SAV_007.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.SAV007.getId(), documento);
					}
				}catch(Exception e) {
					log.error("Ocurrio un error al generar SAV007", e);
				}
			}
			
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.SAV017.getId().intValue()) ){
			AsignacionNSS nss = null;
			boolean fecFinCircunscripcionNull = true;
			Long origenSolicitud = OrigenSolicitudEnum.INTERNET.getId();
			
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.SAV017.getId());
			�*/
			if(documento == null) {
				try {
					Long idPersona = null;
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
					Derechohabiente der = null;
					
					if(tramite instanceof TramiteCircunscripcionForanea) {
						TramiteCircunscripcionForanea tramiteC = (TramiteCircunscripcionForanea) tramite;
						der = (Derechohabiente) tramiteC.getPersona();
						nss = der.getAsignacionNSS();
						
						if( tramiteC.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo()) ){
							fecFinCircunscripcionNull = false;
						}
						
					}
					
					
					if( solicitud.getOrigenSolicitud() != null ){
						origenSolicitud = solicitud.getOrigenSolicitud().getIdTipoSolicitud(); 
					}
					
					if(der != null) {
						documento = (byte[]) documentosServiceRemote.getDocumentoSav017(der.getIdPersona(), nss, firma, fecFinCircunscripcionNull, null, origenSolicitud);
						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "SAV_017.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(),DocumentoPorTipoEnum.SAV017.getId(), documento);
					}
				}catch(Exception e) {
					log.error("Ocurrio un error al generar SAV017", e);
				}
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.SAV005.getId().intValue()) ){
			AsignacionNSS nss = null;
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.SAV005.getId());
					*/
			
			if(documento == null) {
				try {
					Long idPersona = null;
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
					Derechohabiente der = null;
					Usuario usuario = null;
					
					if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
						usuario = solicitud.getSolicitante();
					}
					
					if(tramite instanceof TramiteCorreccionDerechohabiente) {
						TramiteCorreccionDerechohabiente tramiteC = (TramiteCorreccionDerechohabiente) tramite;
						
						if(tramiteC.getPersona() != null) {
							der = (Derechohabiente) tramiteC.getPersona();
							nss = der.getAsignacionNSS();
							idPersona = der.getIdPersona();
						} else {
							List<Long> idPersonas = tramiteC.getCandidatosCambioClinica();
							List<Fisica> personas = tramiteC.getPersonas();
							idPersona = idPersonas.get(0);
							der = (Derechohabiente) personas.get(0);
							nss = der.getAsignacionNSS();
						}
						
					}
					
					if(der != null) {
						documento = (byte[]) documentosServiceRemote.getDocumentoSav005(nss, firma,idPersona, EstadoTramiteEnum.CERRADO.getCodigo().longValue(), solicitud.getOrigenSolicitud().getIdTipoSolicitud(), usuario);
						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "SAV_005.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(),DocumentoPorTipoEnum.SAV005.getId(), documento);
					} else {
						log.debug("El id de la persona es nula en la correccion");
					}
				}catch(Exception e) {
					e.printStackTrace();
					log.error("Ocurrio un error al generar SAV005", e);
				}
			}
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId().intValue()) ){
			
			AsignacionNSS nss = null;
			/*
			documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId());
			*/
			if(documento == null) {
				try {
					Long idPersona = null;
					FirmaElectronica firma = new FirmaElectronica();
					firma.setCadenaOriginal(solicitud.getCadenaOriginal());
					firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
					firma.setRecibo(solicitud.getSelloDigital());
					firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
					
					Usuario usuario = solicitud.getSolicitante();
					Derechohabiente der = null;
					
					if(tramite instanceof TramiteBajaDerechohabiente)
					{
						TramiteBajaDerechohabiente tramiteB = (TramiteBajaDerechohabiente) tramite;
						Derechohabiente dere = (Derechohabiente) tramiteB.getPersona();
						nss = dere.getAsignacionNSS();
					} 
					
					if(nss != null) {
						//documento = (byte[]) documentosServiceRemote.getComprobanteVigenciaDerechos(nss, firma,usuario);
						
						documento = (byte[]) documentosServiceRemote.getConstanciaVigenciaInternetRecortado(nss, firma,usuario);
						
						firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "COMPROBANTE_VIGENCIA_DERECHOS.pdf", documento);
						//solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documento);
					}
				}catch(Exception e) {
					log.error("Ocurrio un error al generar comprobante de vigencia de derechos", e);
				}
			}
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_IVRO.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_IVRO;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_IVRO.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_IVRO;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		}  else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_SSF.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_SSF;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_SEGURO_FAMILIAR.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_SEGURO_FAMILIAR;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_CVRO.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_CVRO;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_CVRO.getId().intValue())) {
			
			DocumentoPorTipoEnum docPorTipo = DocumentoPorTipoEnum.COMPROBANTE_RENOVACION_CVRO;
						
			documento = (byte[]) solicitudEntity.getDocumentoPorTipoIdTramite(
					tramite.getTramiteId(), docPorTipo.getId());
		} else if(idTipoDocumento.equals(DocumentoPorTipoEnum.CONSTANCIA_TRAMIE_ADMIN.getId().intValue())) {
			try {
				documento = (byte[]) documentosServiceRemote.generarComprobanteTramiteAdministrativo(solicitud);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if (idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_DE_CORRECCION_DATOS_ASEGURADO.getId().intValue())){
//			try {
//				documento = bovedaRemote.recuperarDocumento(solicitud,TipoDocumentoCDAEnum.CERTIFICADO,recuperarIdDocumento(solicitud, TipoDocumentoCDAEnum.ACUSE));
//			} catch (SolicitudNoEncontradaException e) {
//				super.log.error("Error al generar documento de ACUSE CDA  "+solicitud.getSolicitudId(), e);
//			}  catch (BovedaCDAException bce){
//				super.log.error("Error al generar documento de ACUSE CDA  "+solicitud.getSolicitudId(), bce);
//			}
		}
		 
		return documento;
	}
	
	@Override
	public Map<String, Object> obtenerDocumentoResultanteNss(Long idSolicitud,
			Long idTramite, Integer idTipoDocumento) {
				
		Map<String, Object> resultado = null;
		StringBuffer fileName = new StringBuffer();
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		try {
			solicitud = consultar(solicitud);
			resultado = new HashMap<String, Object>();
			
			if(idTipoDocumento.equals(DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue())) {
				fileName.append("Comprobante Simple NSS");
			} else {
				fileName.append("Comprobante NSS");
			}
			if (solicitud.getTramites() != null
					&& !solicitud.getTramites().isEmpty()) {
				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteAsegurado) {
						TramiteAsegurado tramAseg = (TramiteAsegurado) tramite;
						
						if (tramAseg.getFisica() != null
								&& StringUtils.isNotBlank(tramAseg.getFisica().getNss())) {
							fileName.append(" - ");
							fileName.append(tramAseg.getFisica().getNss());
							break;
						}
					}
				}
			}
			fileName.append(".pdf");
			
			resultado.put("FILE", obtenerDocumentoResultante(solicitud,idTramite, idTipoDocumento));
			resultado.put("FILE_NAME", fileName.toString());
		} catch (SolicitudNoEncontradaException e) {
			log.error("No se encontro la solicitud: ", e);
		}

		return resultado;
	}
	
	@Override
	public void actualizarSolicitudAEstatusConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		final Date fechaConclusion = new Date();
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitud= actualizarEstadoTramitesAConcluido(solicitud);
		solicitud.setFechaConclusion(fechaConclusion);
		actualizarEstados(solicitud);
		solicitudEntity.actualizarDatosGeneralesDeSolicitud(solicitud);
	
//		try {
//			solicitudHandler.publicarFinProcesamientoSolicitud(solicitud.getNoFolioSolicitud(), true, "");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public List<Solicitud> obtenerInfoBasicaSolicitudesPorPersona(
			Long idPersona,
			Long tipoPersona,
			TipoSolicitudEnum tipoSolicitud,
			mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud) {
		
		return this.solicitudEntity.obtenerInfoBasicaSolicitudesPorPersona(idPersona, tipoPersona, tipoSolicitud, estadoSolicitud);
	}

	@Override
	public List<Solicitud> obtenerInfoBasicaSolicitudesPorPersonaPortal(
			Long idPersona,
			Long tipoPersona,
			TipoSolicitudEnum tipoSolicitud,
			mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud) {
		
		return this.solicitudEntity.obtenerInfoBasicaSolicitudesPorPersonaPortal(idPersona, tipoPersona, tipoSolicitud, estadoSolicitud);
	}

	@Override
	public Solicitud consultarSinDatosTramite(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
		return solicitudEntity.consultar(solicitud, false);
	}

	@Override
	public Solicitud consultarFolioSinDatosTramite(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
		return solicitudEntity.consultarFolio(solicitud, false);
	}

	@Override
	public void enviarSolicitudAProceso(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException{		
		String folio = solicitud.getNoFolioSolicitud();
		solicitudEntity.actualizarDatosGeneralesDeSolicitud(solicitud);
		this.log.debug("Id de la solicitud ..." + solicitud.getSolicitudId());
		if (firmaElectronica != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		}
		
		//En caso de que venga algo en la firma, quiere decir que la firma del representado esta presente y se guarda
		if(solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
		}
		
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
			throw new SolicitudException("La solicitud se esta procesando actualmente");
		}
		
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		solicitud = actualizarEstadoTramitesAActivo(solicitud);
		actualizarEstados(solicitud);

		log.error("Folio a encolar:"+folio);
		
		
		if(esTramiteAsignacionMasiva(solicitud))
			solicitudSIEProducer.encolarSolicitudAConcluir(folio);
		else
			solicitudProducer.encolarSolicitudAConcluir(folio);
	}

	private boolean esTramiteAsignacionMasiva(Solicitud solicitud){
		boolean esSIE = false;
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue())){
			for(Tramite tramite : solicitud.getTramites()){
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ASIGNACION_MASIVA_NSS_SIE.getCodigo()))
					esSIE = true;
			}
		}
		return esSIE;
	}
	
	@Override
	public List<Solicitud> obtenerSolicitudesPorRegistroPatronal(Long idPatronSujetoObligado) {
		return solicitudEntity.listarSolicitudesDeRegistroPatronalPorId(idPatronSujetoObligado);
	}

	@Override
	public void asociarTramiteAltaARegistroPatronal(Tramite tramite, Long idPatronSujetoObligado) {
		solicitudEntity.asociarTramiteAltaARegistroPatronal(tramite, idPatronSujetoObligado);
	}
	
	@Override
	public Solicitud obtenerEstados (Solicitud solicitud) {
		return this.solicitudEntity.consultarEstatus(solicitud);
	}

	@Override
	public void finalizarCapturaSolicitud(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		
		this.log.debug("finalizarCapturaSolicitud ..."+ solicitud);
		
		solicitud = actualizarTramites(solicitud);
//		solicitudEntity.actualizarDatosGeneralesDeSolicitud(solicitud);
		enviarSolicitudAProceso(solicitud, firmaElectronica);
	}

	@Override
	public Subdelegacion getDatosSubdelegacion(Long idSubdelegacion) {
		
		Subdelegacion sub = solicitudEntity.getSubdelegacionById(idSubdelegacion);
		
		return sub;
	}
	
	@Override
    public Delegacion getDatosDelegacion(Long idDelegacion) {
        
        return solicitudEntity.getDelegacionById(idDelegacion);

    }
	
	/**
	 * V�lida que la solicitud contenga el estado o conjunto de estados v�lidos proporcionados
	 * @param solicitud
	 * @param estadoValido
	 * @throws SolicitudNoValidaException
	 */
	@Override
	public void validarEstadoProcesamiento(Solicitud solicitud, List<EstadoSolicitudEnum> estadosValidos) throws SolicitudNoValidaException{
		boolean seEncuentraEnEstadoValido=false;
		for(EstadoSolicitudEnum estoValido : estadosValidos)
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(estoValido.getCodigo()))
				seEncuentraEnEstadoValido=true;
		if(!seEncuentraEnEstadoValido)
			throw new SolicitudNoValidaException("La solicitud no se escuentra en un estado v\u00E1lido para realizar el proceso");
	}

	@Override
	public void reportarErrorProcesamiento(Long idSolicitud, String folio,
			String mensajeError) {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folio);

		try {
			solicitud = consultarFolio(solicitud);
			
			if (mensajeError == null || (mensajeError != null && StringUtils.isBlank(mensajeError))) {
				mensajeError = solicitud.getObservacion() != null ? solicitud.getObservacion() : "";
			}

			Long idPersona = null;

			if (isSolicitudACancelar(solicitud)) {
				solicitudEntity.cancelarSolicitudPorFolio(folio);
			} else {
				solicitudEntity.actualizarEstadoMensajeError(folio,
						EstadoSolicitudEnum.REGISTRADA.getCodigo(),
						EstadoTramiteEnum.INICIADO.getCodigo(), mensajeError);

				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteFisica) {
						TramiteFisica tf = (TramiteFisica) tramite;
						idPersona = tf.getFisica().getIdPersona();
					} else if (tramite instanceof TramiteSujetoObligado) {
						TramiteSujetoObligado tso = (TramiteSujetoObligado) tramite;
						SujetoObligado sujeto = tso.getSujetoObligado();
						if (sujeto.getFisica() != null)
							idPersona = sujeto.getFisica().getIdPersona();
						else if (sujeto.getMoral() != null)
							idPersona = sujeto.getMoral().getIdPersona();
					}
				}

				if (idPersona != null) {
					Fisica fisica = new Fisica();
					fisica.setIdPersona(idPersona);
					fisica.setCveFisica(idPersona);

					/*
					 * TODO: COMET - Sustituir publicarSolicitudesPersona por llamado a
					 * WS del comet
					 * portletServiceHandler.publicarSolicitudesPersona(fisica);
					 */
				}
			}

			/*
			 * TODO: COMET - Sustituir publicarSolicitudesPersona por llamado a
			 * WS del comet
			 * solicitudHandler.publicarFinProcesamientoSolicitud(folio, false, mensajeError);
			 */
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
		} catch (Exception e) {
			this.log.error(e);
		}
	}

	@Override
	public void actualizarMensajeNotificacion(String folio, String mensajeError) {
		solicitudEntity.actualizarEstadoMensajeError(folio, null, null,mensajeError);		
	}
	
	@Override
	public boolean existeSolicitud(Solicitud solicitud)
			throws SolicitudException {
		
		boolean existeSolicitud;
		
		this.log.info("Se checa si existe la solicitud [folio: "
				+ solicitud.getNoFolioSolicitud() + ", idSolicitud: "
				+ solicitud.getSolicitudId() + "]");
		
		if (StringUtils.isNotBlank(solicitud.getNoFolioSolicitud()) || solicitud.getSolicitudId() != null) {
			existeSolicitud = this.solicitudEntity.existeSolicitud(solicitud);
		} else {
			throw new SolicitudException(
					"No se puede comprobar si existe la solicitud ya que no se cuenta con el folio o id");
		}
		
		return existeSolicitud;
	}

	@Override
	public void actualizarDocumentosTramite(Long idTramite,
			Long idDocumentoTipo, byte[] bytes) {
		solicitudEntity.actualizarDocumentosTramite(idTramite, idDocumentoTipo, bytes);
	}
	
	@Override
	public Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo) {
	     return solicitudEntity.getDocumentoPorTipoIdTramite(idTramite, idDocumentoPorTipo);
	 }
	@Override
	public void agregarPersonaATramite(Long idTramite, Long idPersona) throws TramiteNoEncontradoException{
		
		solicitudEntity.agregarPersonaATramite(idTramite, idPersona);
	}

	
	/**
	 * Obtiene todas las solicitudes de un grupo familiar a partir del nss
	 */
	@Override
	public List<Solicitud> getSolicitudesGrupoFamiliar(String nss, Long idOrigenSolicitud) {
		List<Solicitud> solicitudes = null;
		
		solicitudes = solicitudEntity.getSolicitudesPorNss(nss, null, null, null,idOrigenSolicitud);
		
		return solicitudes;
	}

	/**
	 * Obtiene solo las solicitudes activas
	 */
	@Override
	public List<Solicitud> getSolicitudesActivasGrupoFamiliar(String nss, Long idOrigenSolicitud) {
		List<Solicitud> solicitudes = null;
		List<Long> estadosSolicitud = new ArrayList<Long>();
		estadosSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		estadosSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue() );
		
		solicitudes = solicitudEntity.getSolicitudesPorNss(nss, estadosSolicitud, null, null,idOrigenSolicitud);
		
		return solicitudes;
	}

	/**
	 * Obtiene las solicitudes para un integrante de un grupo familiar
	 */
	@Override
	public List<Solicitud> getSolicitudesGrupoFamiliarEIntegrante(String nss,
			Long idIntegrante, Long idOrigenSolicitud) {
		
		List<Solicitud> solicitudes = null;
		
		solicitudes = solicitudEntity.getSolicitudesPorNss(nss, null, idIntegrante, null, idOrigenSolicitud);
		
		return solicitudes;
	}
	
	/**
	 * Obtiene las solicitudes que no han sido cerradas para un integrante de un grupo familiar
	 */
	@Override
	public List<Solicitud> getSolicitudeActivasGrupoFamiliarEIntegrante(String nss,
			Long idIntegrante, Long numeroResultados, Long idOrigenSolicitud) {
		
		List<Solicitud> solicitudes = null;
		List<Long> estadosSolicitud = new ArrayList<Long>();
		estadosSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		estadosSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue() );
		
		solicitudes = solicitudEntity.getSolicitudesPorNss(nss, estadosSolicitud, idIntegrante, numeroResultados,idOrigenSolicitud);
		
		return solicitudes;
	}

	
	private boolean isSolicitudACancelar(Solicitud solicitud) {
		
		boolean isSolicitudaACancelar = false;
		
		Integer cveTipoSolic = solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue();		
		
		if (cveTipoSolic.equals(TipoSolicitudEnum.ASIGNACION_NSS.getValor())
				|| cveTipoSolic.equals(TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor())) {
			isSolicitudaACancelar = true;
		}
		
		
		return isSolicitudaACancelar;
		
	}

	@Override
	public Solicitud getUltimaSolicitudActivaDeRegistroPorIdAsignacionNss(
			Long idAsignacionNss) {
		List<Solicitud> solicitudes = null;
		Solicitud solicitudeRegistro = null;
		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		estados.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		
		solicitudes = solicitudEntity.getSolicitudesPorIdNssTipoYEstadoTramite(idAsignacionNss, 
				TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor().longValue(), 
				null, 
				estados, 1L);
		
		if(solicitudes != null && !solicitudes.isEmpty()) {
			solicitudeRegistro = solicitudes.get(0);
		}
		
		return solicitudeRegistro;
	}
	
	
	@Override
	public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona,TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados){
		return solicitudEntity.obtenerSolicitudPorPersona(idPersona,tipo,tipoSolicitud,estadoSolicitud,maximoResultados);
	}
	

/**
	 * Metodo para obtener las solicitudes de un grupo familiar
	 * @param String nss - el nss del grupo familiar
	 * @param List<Long> estadosSolicitud- estados de la solicitud a buscas , puede ser nulo y no se comparara el estado 
	 * nulo se buscaran todas las solicitudes del grupo familiar sin importar las persona afectada
	 * @param Long maxResult - El numero de resultados a obtener, puede ir nulo
	 * @param Long idOrigenSolicitud - El origen de la solicitud, puede ser nulo, en caso de ser nulo obtendra tanto solicitudes
	 * realizadas por internet como en ventanilla
	 */
	@Override
	public List<Solicitud> getSolicitudesbyNSSEstadosOrigen(String nss,
			List<Long> estadosSolicitud,  Long idOrigenSolicitud, Long maxResult){
		List<Solicitud> solicitudes = null;
		this.log.debug("**********************************JUAN LLAMA A CASA");
		solicitudes = solicitudEntity.getSolicitudesPorNss(nss, estadosSolicitud, null, maxResult, idOrigenSolicitud);
		
		return solicitudes;
	}

	@Override
	public List<Solicitud> obtenerSolicitudes(){
		return solicitudEntity.obtenerSolicitudes();		
	}
	
	@Override
	public void obtenerCifrasSolicitudProceso(){
		
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<style>	table, th, td {border: 1px solid black;}</style>");
		mensaje.append ("Buena Tarde:<br>");
		mensaje.append("Se envia la Tabla con el Total de Solicitudes con estado en Proceso <br>");
		mensaje.append("<table>");
		mensaje.append("	<tr>");
		mensaje.append("		<td width='100'>");
		mensaje.append("			<b> Tipo Tr&aacute;mite</b>");
		mensaje.append("		</td>");
		mensaje.append("		<td>");
		mensaje.append("			<b>Folio</b>");
		mensaje.append("		</td>");
		mensaje.append("		<td>");
		mensaje.append("			<b>Fecha</b>");
		mensaje.append("		</td>");
		mensaje.append("	</tr>");

		Locale locMEX = new Locale("es", "MX");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", locMEX);
		
		List<ObtCifrasSolicitudProceso> listaCifras = solicitudEntity.obtenerCifrasSolicitudProceso();
		
		for(ObtCifrasSolicitudProceso cifras : listaCifras){
			mensaje.append("<tr> ");
			mensaje.append("	<td>" +cifras.getTipoSolicitud()+"</td>");
			mensaje.append("	<td>" +cifras.getFolio()+"</td>");
			mensaje.append("	<td>" +dateFormat.format(cifras.getFecha())+"</td>");
			mensaje.append("</tr>");
		}
		mensaje.append("</table>");
		
		EmailDataWrapper emailData = new EmailDataWrapper();
		emailData.setAsunto("Total de Solicitudes en Proceso");
		emailData.setMensaje(mensaje.toString());
		emailData.setToAddress("ignacio.espinosav@imss.gob.mx");
		
		this.emailServiceUtility.enviarCorreoSimple(emailData);
		
    }
	
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RazonResultado> obtenerRazonesResultado(){
		return razonResultadoEntity.obtenerRazones();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RazonResultado> obtenerRazonesResultado(List<Long> idRazones) throws Exception{
		return razonResultadoEntity.obtenerRazones(idRazones);
	}
	
	
	@Override
	public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona, TipoPersonaFiscal tipo, 
			List<Long> tiposSolicitud, List<Long> estadosSolicitud, Boolean maximoResultados){
		return solicitudEntity.obtenerSolicitudPorPersona(idPersona,tipo,tiposSolicitud,estadosSolicitud,maximoResultados);
	}

	@Override
	public Tramite actualizarXmlTramite(Tramite tramite)  throws TramiteNoEncontradoException, IllegalArgumentException{
		return solicitudEntity.actualizarXMLTramite(tramite);
	}
	
	@Override
	public mx.gob.imss.digital.modelo.tramite.Tramite actualizarXmlTramite(
			mx.gob.imss.digital.modelo.tramite.Tramite tramite)
			throws TramiteNoEncontradoException, IllegalArgumentException {
		return solicitudEntity.actualizarXMLTramite(tramite);
	}

	@Override
	public void actualizaAConcluida(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
		solicitudEntity.actualizaAConcluida(solicitud);
	}

	
	@Override
	public void cancelarSolicitud(Long idSolicitud, Long idRazonRechazo, Long idRazonCancelacion, String usuarioCancelacion, String observacionesCancelacion) throws SolicitudException {
		try {
			solicitudEntity.cancelarSolicitud(idSolicitud, idRazonRechazo, idRazonCancelacion, usuarioCancelacion, observacionesCancelacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SolicitudException("Ocurrio un error al cancelar la solicitud");
		}
	}

	@Override
	public void actualizarUsuarioSolicitud(Solicitud solicitud)
			throws IllegalArgumentException {
		solicitudEntity.actuliazaUsuarioSolicitud(solicitud);
	}

	@Override
	public void actualizaTipoTramite(Long idTramite, Long tipoTramite) {
		solicitudEntity.actualizaTipoTramite(idTramite, tipoTramite);
	}

	@Override
	public Solicitud agregarListadosDocumentosATramites(Solicitud solicitud) {
		if(solicitud != null) {
			long idTipoTramite = 0;
			for (Tramite tramite : solicitud.getTramites()) {			
				//obtenemos el tipo de tramite
				idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();			
				//seteamos los documentos resultantes
				tramite.setDocumentoPorTipos(this.solicitudServiceBusiness.obtenerDocumentosResultantesPorTipoTramite(idTipoTramite));
			}		
			this.verificarDocumentosRegistro(solicitud);
			removerDocumentosTramite(solicitud);
		}
		return solicitud;
	}
	
	@Override
	public void actualizaAConcluidaDH(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
		solicitudEntity.actualizaAConcluidaDH(solicitud);
	}
	
	@Override
	public void finalizarSolicitud(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		
		this.log.debug("finalizarSolicitud ..."+ solicitud);
		
		solicitud = actualizarTramites(solicitud);
		
		enviarSolicitudAFinalizar(solicitud, firmaElectronica);
	}
	
	public void enviarSolicitudAFinalizar(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException{		
		solicitudEntity.actualizarDatosGeneralesDeSolicitud(solicitud);
		this.log.debug("Id de la solicitud ..." + solicitud.getSolicitudId());
		if (firmaElectronica != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		}
		
		//En caso de que venga algo en la firma, quiere decir que la firma del representado esta presente y se guarda
		if(solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
		}
		
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
			throw new SolicitudException("La solicitud se esta procesando actualmente");
		}
		
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		solicitud = actualizarEstadoTramitesAActivo(solicitud);
		actualizarEstados(solicitud);
		
		try {
			afiliacionGlobalServiceRemote.concluirSolicitudDatosPatronales(solicitud.getSolicitudId());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SolicitudException("Ocurrio un error al procesar la solicitud");
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SolicitudException("Ocurrio un error al procesar la solicitud");
		}

	}
	
	/**
	 * Metodo que asocia una persona a la solicitud
	 * @param cveIdSolicitud
	 * @param cveIdPersonaInteresada
	 * @param cveTipoPersonaInteres
	 * @throws SolicitudNoEncontradaException
	 */
	@Override
	public void insertaPersonaInteresadaSolicitud(Long cveIdSolicitud, Long cveIdPersonaInteresada, Long cveTipoPersonaInteres) 
			throws SolicitudNoEncontradaException, IllegalArgumentException{
			if(cveIdSolicitud == null || cveIdPersonaInteresada == null || cveTipoPersonaInteres == null){
				throw new IllegalArgumentException("Todos los parametros son requeridos");
			}
			this.solicitudEntity.insertaPersonaInteresadaSolicitud(cveIdSolicitud, cveIdPersonaInteresada, cveTipoPersonaInteres);
		
	}

	@Override
	public String getHomoclaveSolicitud(String folioSolicitud) {
		return solicitudEntity.getHomoclaveSolicitud(folioSolicitud);
	}

	@Override
	public String getHomoclaveSolicitud(Long idSolicitud) {
		return solicitudEntity.getHomoclaveSolicitud(idSolicitud);
	}
	
	private String recuperarIdDocumento(Solicitud sol, TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws SolicitudNoEncontradaException{
		TramiteCorreccionCurp tramite = null;
		if(sol.getTramites() != null){
			log.debug("---CDA--- el tramite no es null " + sol.getTramites().get(0).getTramiteId() );
			tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
			Solicitud solicitud = consultarPorIdTramite(tramite.getTramiteId());
			
			tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		}	
		return tramite != null ?(tipoDocumentoCDAEnum.equals(TipoDocumentoCDAEnum.ACUSE)?tramite.getIdDocumentoAcuse():tramite.getIdDocumentoCertificacion()):null;
	}
	
		@Override
	public Solicitud obtenerPorFolioSolicitud(String folioSolicitud){
		return this.solicitudEntity.obtenerPorFolioSolicitud(folioSolicitud);
	}
	
	@Override
	public List<Long> encontrarSolicitudesPorPersonaYEstados(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud){
		return this.solicitudEntity.encontrarSolicitudesPorPersonaYEstados(idPersona, idsEstados,
            idTipoSolicitud);
	}
	
	@Override
	public Long obtenerIdPersonaInteresada(Long solicitudId){
		return this.solicitudEntity.obtenerIdPersonaInteresada(solicitudId);
	}
	
	@Override
	public List<Long> obtenerTramitesNoCanceladosPorPersonaYTipo(Long idPersona,
            Long tipoSolicitud){
		return this.solicitudEntity.obtenerTramitesNoCanceladosPorPersonaYTipo(idPersona,
            tipoSolicitud);
	}

	@Override
	public List<Long> encontrarSolicitudesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstadoTramite,
            Long tipoSolicitud){
		return this.solicitudEntity.encontrarSolicitudesPorPersonaYEstadosDeTramite(idPersona,idsEstadoTramite,
            tipoSolicitud);
	}
	
	@Override
	public List<Map<String, String>> buscaSolicitudesHsqlRemoto(Map<String, String> datosBusqueda, Integer primerResultado,Integer resultadosPorPagina){
				return this.solicitudEntity.buscaSolicitudesHsql(datosBusqueda,primerResultado,resultadosPorPagina);
			}
	@Override
    public Long buscaConteoSolicitudesHsqlRemoto(Map<String, String> datosBusqueda){
		return this.solicitudEntity.buscaConteoSolicitudesHsql(datosBusqueda);
	}
	
	@Override
    public List<Long> encontrarTramitesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstadoTramite){
        return this.solicitudEntity.encontrarTramitesPorPersonaYEstadosDeTramite(idPersona,idsEstadoTramite);
    }
		
	@Override
    public void actualizarTipoTramite(String Tramite, String idSolicitud, String TipoNSS)
    {
		 this.solicitudEntity.actualizarTipoTramite( Tramite, idSolicitud, TipoNSS);       
    }
    
	@Override
    public void actualizarTipoNSS(String nss, String TipoNSS)
    {
    		 this.solicitudEntity.actualizarTipoNSS( nss, TipoNSS);
    }
	
	public ArrayList<String> obtenercatalogo()
    {
    		return this.solicitudEntity.obteneCatalogo();
    }

	@Override
	public String obtenerCVECDA(String folio) {
		
		return this.solicitudEntity.obtener_CVECDA(folio);
		
	}
}
