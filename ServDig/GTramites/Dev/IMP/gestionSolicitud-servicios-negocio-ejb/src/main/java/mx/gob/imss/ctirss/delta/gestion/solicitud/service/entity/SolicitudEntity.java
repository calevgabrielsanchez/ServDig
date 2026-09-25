package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.SolicitudConversorLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.TramiteConversorLocal;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.ObtCifrasSolicitudProceso;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite32D;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaPersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteConsultaVigencia;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteDictamen;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitCitaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoResultanteTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramiteDictamen;
import mx.gob.imss.ctirss.delta.persistence.DitTramiteDictamenPK;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePatSujObligado;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePatSujObligadoPK;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoralPK;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurnoPK;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;


/**
 * @author Cesar Garcia Mauricio 06 Junio 2012, 19:55
 */
@Stateless(mappedName = "solicitudEntity")
public class SolicitudEntity extends AbstractServiceEntity implements
        SolicitudEntityLocal {

    // TODO dividir en 2 auxiliares: uno para save + find y el otro para la
    // actualizacion...
    @EJB
    private transient SolicitudConversorLocal solicitudConversor;
    @EJB
    private /*transient*/ TramiteConversorLocal tramiteConversor;

    private static int MAXIMO_SOLICITUDES = 5;
    private static String TOTAL_REGISTROS = "TOTAL_REGISTROS";
    private static String REGISTROS = "REGISTROS";

	
	
	
	
	
	
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");

    private String querySqlSpec;

    private String querySql;
    private String queryConteo;
	
	
	
	
	
    /*
	 * Lista de tipos de tr�mites a los que no se les generar� detalle (XML) al
	 * momento de crearlos
     */
    private static final Set<Integer> TRAMITES_CREAR_SIN_DETALLE = new HashSet<Integer>(Arrays.asList(new Integer[]{
        TipoTramiteEnum.CERTIFICACION_RETIRO_DESEMPLEO.getCodigo()
    }));

    @Override
    public Solicitud crear(final Solicitud solicitud) {

        final Date fechaAlta = solicitud.getFechaSolicitud() == null ? new Date() : solicitud.getFechaSolicitud();

        final DitSolicitud ditSolicitud = new DitSolicitud();
        ditSolicitud.setFecRegistroAlta(fechaAlta);
        ditSolicitud.setFecRegistroActualizado(fechaAlta);
        ditSolicitud.setFecSolicitud(fechaAlta);
        ditSolicitud.setFecPresentacion(fechaAlta);

        // En caso de que se haya creado una solicitud CONCLUIDA desde cero
        if (solicitud.getFechaConclusion() != null) {
            ditSolicitud.setFecConclusion(solicitud.getFechaConclusion());
        }

        ditSolicitud.setDicEstadoSolicitud(new DicEstadoSolicitud(solicitud
                .getEstadoSolicitud().getIdEstadoSolicitud().longValue()));
        ditSolicitud.setRefFolio("0");
        System.err.println("Agregando el tipo de solicitud....");
        DicTipoSolicitud dicTipoSolicitud = new DicTipoSolicitud();
        dicTipoSolicitud.setCveIdTipoSolicitud(solicitud.getTipoSolicitud().getIdTipoSolicitud());
        ditSolicitud.setDicTipoSolicitud(dicTipoSolicitud);

        if (solicitud.getSubdelegacion() != null && solicitud.getSubdelegacion().getId() != null) {
            DicSubdelegacion dicSubdelegacion = em.find(DicSubdelegacion.class, solicitud.getSubdelegacion().getId());
            ditSolicitud.setDicSubdelegacion(dicSubdelegacion);
        }

        if (StringUtils.isNotBlank(solicitud.getObservacion())) {
            ditSolicitud.setRefObservacion(solicitud.getObservacion());
        }

        //seteo de la propiedad del usuario que realiza el tramite
        Usuario usuario = solicitud.getSolicitante();
        if (usuario != null && usuario.getUsuario() != null && !usuario.getUsuario().isEmpty()) {
            ditSolicitud.setCveIdUsuario(usuario.getUsuario());
        }

        if (solicitud.getCitaSolicitud() != null && solicitud.getCitaSolicitud().getTurno() != null && solicitud.getCitaSolicitud().getUmf() != null) {

            if (solicitud.getCitaSolicitud().getTurno().getIdTurno() == null) {
                Turno turno = solicitud.getCitaSolicitud().getTurno();
                turno.setIdTurno(1L);
                solicitud.getCitaSolicitud().setTurno(turno);
            }

            this.generarCita(ditSolicitud, solicitud.getCitaSolicitud());
        } else {
            log.debug(">>> No se realizar\u00E1 asignaci\u00F3n de cita para la solicitud con CveIdSolicitud: " + ditSolicitud.getCveIdSolicitud());
        }

        /*
		 * Guardado de la relacion One to One de DitSolicitudDocumento.
		 
		DitSolicitudDocumento ditSolicitudDocumento = ditSolicitud.getDitSolicitudDocumento();
		ditSolicitudDocumento = new DitSolicitudDocumento();
		ditSolicitudDocumento.setCveIdSolicitud(ditSolicitud.getCveIdSolicitud());
         */
        ditSolicitud.setDicOrigenSolicitud(solicitudConversor
                .convertirOrigenSolicitud(solicitud.getOrigenSolicitud()));

        ditSolicitud.setCveIdSolicitud(this.getCveIdSolicitudFronSequence().longValue());

        ditSolicitud.setRefFolio(calcularFolio(ditSolicitud.getCveIdSolicitud()));

        TipoSolicitud tipoSolicitud = solicitud.getTipoSolicitud();
        if (mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum.CORRECCION_PATRONAL
                .getId() == tipoSolicitud.getIdTipoSolicitud().longValue()) {
            if (solicitud.getNoFolioSolicitud() == null || solicitud.getNoFolioSolicitud().isEmpty()) {
                this.log.debug("No se recibio el datos de no de folio de solicitud, para la solicitud de correccion");
            }
            ditSolicitud.setRefFolio(solicitud.getNoFolioSolicitud());
        }

        em.persist(ditSolicitud);
        em.flush();

        this.log.debug("Calculando el folio de la solicitud...");

        /*
		 * Para los casos de las solicitudes de correccion
		 * el folio no se debe de calcular, este es enviado
		 * por los tramites de coreccion.
         */
        solicitud.setSolicitudId(ditSolicitud.getCveIdSolicitud());
        solicitud.setFechaSolicitud(fechaAlta);
        solicitud.setNoFolioSolicitud(ditSolicitud.getRefFolio());

        this.log.debug("Iniciando la creacion de los tramites ...");
        List<DitTramite> ditTramites = new ArrayList<DitTramite>();
        DitTramite ditTramite = null;
        List<Tramite> trams = new ArrayList<Tramite>();
        for (Tramite tramite : solicitud.getTramites()) {
            ditTramite = crear(fechaAlta, tramite, ditSolicitud, usuario);
            ditTramites.add(ditTramite);
            tramite.setTramiteId(ditTramite.getCveIdTramite());
            tramite.getTipoTramite().setDescripcion(ditTramite.getDicTipoTramite().getDesTipoTramite());
            tramite.getTipoTramite().setHomoclave(ditTramite.getDicTipoTramite().getRefHomoclave());
            trams.add(tramite);
        }
        solicitud.setTramites(trams);

        if (solicitud.getPersonaInteresadaSolicitud() != null) {
            List<DitPersonaInteresadaSol> ditPersonasInteresadas = null;
            DitPersonaInteresadaSol ditPersonaInteresadaSol = this.crearPersonaInteresada(solicitud.getPersonaInteresadaSolicitud(), ditSolicitud);

            /*
			if(ditPersonaInteresadaSol != null) {
				ditPersonasInteresadas = new ArrayList<DitPersonaInteresadaSol>();
				ditPersonasInteresadas.add(ditPersonaInteresadaSol);
				ditSolicitud.setDitPersonaInteresadaSols(ditPersonasInteresadas);
			}
             */
        }

        ditSolicitud.setDitTramites(ditTramites);
        em.flush();

        return solicitud;
    }

    // TODO corregir en caso de que se requiera para Mascara a 22 posiciones
    private String calcularFolio(final Long cveIdSolicitud) {
        final StringBuilder nFolioStB = new StringBuilder();
        // 13 posiciones, num milisegs desde January 1, 1970, 00:00:00 GMT
        nFolioStB.append(new Date().getTime());
        // El idSolicitud hasta 22 - 13 = 9 posiciones.
        nFolioStB.append(cveIdSolicitud);
        return nFolioStB.toString();
    }

    private DitTramite crear(final Date fechaAlta, final Tramite tramite,
            final DitSolicitud ditSolicitud, Usuario usuario) {
    	
    	//Consultamos el catalogo de tipo tramite para obtener la homoclave y que se guarde en el xml
    	DicTipoTramite dicTipoTramite = em.find(DicTipoTramite.class, tramite.getTipoTramite().getIdTipoTramite().longValue());
    	tramite.getTipoTramite().setHomoclave(dicTipoTramite.getRefHomoclave());
    	tramite.getTipoTramite().setDescripcion(dicTipoTramite.getDesTipoTramite());
        final DitTramite ditTramite = tramiteConversor.convertirModelToEntity(tramite);
         
        ditTramite.setDitSolicitud(ditSolicitud);
        ditTramite.setFecRegistroAlta(fechaAlta);
        ditTramite.setFecRegistroActualizado(fechaAlta);
        ditTramite.setFecEfecto(tramite.getFechaEfecto());
        ditTramite.setFecPresentacion(tramite.getFechaPresentacion());
        ditTramite.setFecConclusion(tramite.getFechaConclusion());
        ditTramite.setIndRatificado(tramite.getIndRatificado());
        ditTramite.setRefObservacion(tramite.getObservacion());
        ditTramite.setDicTipoTramite(dicTipoTramite);

        if (tramite.getResultado() != null) {
            ditTramite.setIndResultado(new BigDecimal(tramite.getResultado() ? 1 : 0));
        }
        em.persist(ditTramite);
        tramite.setTramiteId(ditTramite.getCveIdTramite());

        if (tramite.getTipoTramite() != null
                && !TRAMITES_CREAR_SIN_DETALLE.contains(tramite.getTipoTramite()
                        .getIdTipoTramite())) {
            final DitDetalleTramite ditDetalleTramite = tramiteConversor
                    .construirEntityDetalleTramite(tramite, ditTramite,
                            fechaAlta);
            em.persist(ditDetalleTramite);

            ditTramite.setDitDetalleTramite(ditDetalleTramite);
        } else {
            this.log.info("No se va a generar detalle del tr�mite con id -> "
                    + ditTramite.getCveIdTramite());
        }

        generarRelacionTramitePersona(tramite, ditTramite);

        /*
		//Creamos la bitacora de que usuario esta realizando el tramit
		
		/*
		final DitBitacoraSegTramite ditBitacoraSegTramite = generarBitacoraSeguimiento(tramite, ditTramite,usuario);
		
		if(ditBitacoraSegTramite != null) {
			List<DitBitacoraSegTramite> listaBitacoras = new ArrayList<DitBitacoraSegTramite>();
			listaBitacoras.add(ditBitacoraSegTramite);
			ditTramite.setDitBitacoraSegTramite(listaBitacoras);
		}*/
        return ditTramite;
    }

    /*
	private DitBitacoraSegTramite generarBitacoraSeguimiento(Tramite tramite, DitTramite ditTramite, Usuario usuario) {
		DitBitacoraSegTramite ditBitSegTramite = null;
		
		this.log.debug("Se va a generar la bitacora de seguimiento para el tramite "
				+ tramite.getTramiteId());
		
		if(usuario != null) {
			this.log.debug("Usuario: "+usuario.getUsuario());
			ditBitSegTramite = new DitBitacoraSegTramite();
			
			DitTramite unDitTramite = new DitTramite();
			unDitTramite.setCveIdTramite(tramite.getTramiteId());				
			DicEstadoTramite unDicEstadoTramite = new DicEstadoTramite();
			unDicEstadoTramite.setCveIdEstadoTramite(tramite.getEstadoTramite().getIdEstadoTramitePersona().longValue());	
			
			if(usuario.getFisica() != null && usuario.getFisica().getIdPersona() != null) {
				DitPersona unDitPersona = new DitPersona();		
				unDitPersona.setCveIdPersona(usuario.getFisica().getIdPersona());
				ditBitSegTramite.setDitPersona(unDitPersona);
			}
			
			ditBitSegTramite.setDitTramite(unDitTramite);
			ditBitSegTramite.setDicEstadoTramite(unDicEstadoTramite);
			
			ditBitSegTramite.setCuentaUsuario(usuario.getUsuario());
			ditBitSegTramite.setFecRegistroAlta(new Date());
			ditBitSegTramite.setIpTramite("");
			ditBitSegTramite.setRefObservaciones(tramite.getObservacion());
			
			em.persist(ditBitSegTramite);
		}
		
		this.log.debug("Se genero la bitacora de seguimiento para el tramite "
				+ tramite.getTramiteId());
		
		return ditBitSegTramite;
	}
	
     */
    private void generarCita(DitSolicitud ditSolicitud, CitaSolicitud cita) {
        DitUmfTurno umfTurno = new DitUmfTurno();
        DitUmfTurnoPK pk = new DitUmfTurnoPK();
        pk.setCveIdTurno(cita.getTurno().getIdTurno());
        pk.setCveIdUmf(cita.getUmf().getIdUMF());

        umfTurno.setId(pk);
        umfTurno.setDicUmf(new DicUmf());
        umfTurno.getDicUmf().setCveIdUmf(cita.getUmf().getIdUMF());
        umfTurno.setDicTurno(new DicTurno());
        umfTurno.getDicTurno().setCveIdTurno(cita.getTurno().getIdTurno());

        ditSolicitud.setDitUmfTurno(umfTurno);
        ditSolicitud.setFecCita(cita.getFechaHora());
    }

    private DitPersonaInteresadaSol crearPersonaInteresada(PersonaInteresadaSolicitud personaInt, DitSolicitud ditSolicitud) {
        DitPersonaInteresadaSol ditPersonaIntSol = new DitPersonaInteresadaSol();
        ditPersonaIntSol = new DitPersonaInteresadaSol();

        ditPersonaIntSol.setDitSolicitud(new DitSolicitud());
        log.debug("El id de la solicitud es: " + ditSolicitud.getCveIdSolicitud());
        ditPersonaIntSol.getDitSolicitud().setCveIdSolicitud(ditSolicitud.getCveIdSolicitud());
        ditPersonaIntSol.setDitPersona(new DitPersona());
        ditPersonaIntSol.getDitPersona().setCveIdPersona(personaInt.getPersona().getIdPersona());
        log.debug("El id de la persona interesada es: " + personaInt.getPersona().getIdPersona());
        ditPersonaIntSol.setDicTipoPersonaInteresadaSol(new DicTipoPerInteresadaSol());
        ditPersonaIntSol.getDicTipoPersonaInteresadaSol().setCveTipoInteresadaSol(personaInt.getTipoPersonaInteresadaSol().getCveTipoInteresadaSol());
        log.debug("El tipo de persona interesada es: " + personaInt.getTipoPersonaInteresadaSol().getCveTipoInteresadaSol());

        try {
            em.persist(ditPersonaIntSol);
            //em.flush();
        } catch (Exception e) {
            log.error("savePersonaInteresadaSolicitud", e);
        }

        return ditPersonaIntSol;
    }

    private void generarRelacionTramitePersona(final Tramite tramite,
            final DitTramite ditTramite) {
    	if(tramite instanceof TramiteDictamen){
        	TramiteDictamen tramiteDictamen = (TramiteDictamen) tramite;
        	
        	
        	if(tramiteDictamen.getSujetoObligado()  != null){
        		DitTramiteDictamen ditTramiteDictamen = new DitTramiteDictamen();
            	DitTramiteDictamenPK pk = new DitTramiteDictamenPK();
            	
        		pk.setCveIdEjercicioFiscal(tramiteDictamen.getIdEjercicioFiscal());
        		pk.setCveIdPatronSujetoObligado(tramiteDictamen.getSujetoObligado().getCveIdSujetoObligado());
	        	pk.setCveIdTramite(ditTramite.getCveIdTramite());
	        	
	        	ditTramiteDictamen.setId(pk);
	        	ditTramiteDictamen.setCveIdPatronDictamen(tramiteDictamen.getIdPatronDictamen());
	        	
	        	em.persist(ditTramiteDictamen);
        	}
        	
        } else if (tramite instanceof TramiteSujetoObligado) {

            final TramiteSujetoObligado tso = (TramiteSujetoObligado) tramite;
            if (tso.getSujetoObligado().getCveIdSujetoObligado() == null) {
                log.info("No se guarda en tabla de relacion a Sujeto Obligado debido a que actualmente no existe la entidad SujetoObligado.");
            } else {
                final DitTramitePatSujObligado entityTramiteSujetoObligado = new DitTramitePatSujObligado();
                final DitPatronSujetoObligado ditPat = em.find(
                        DitPatronSujetoObligado.class, tso.getSujetoObligado()
                                .getCveIdSujetoObligado());
//				entityTramiteSujetoObligado.setDitPatronSujetoObligado(ditPat);
//				entityTramiteSujetoObligado.setCveIdTramite(ditTramite
//						.getCveIdTramite());

                DitTramitePatSujObligadoPK pk = new DitTramitePatSujObligadoPK();
                pk.setCveIdPatronSujetoObligado(tso.getSujetoObligado().getCveIdSujetoObligado());
                pk.setCveIdTramite(ditTramite.getCveIdTramite());
                entityTramiteSujetoObligado.setId(pk);

                em.persist(entityTramiteSujetoObligado);
            }

        } else if (tramite instanceof TramiteFisica) {

            final TramiteFisica tFisica = (TramiteFisica) tramite;
            if (tFisica.getFisica().getIdPersona() == null) {
                log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
            } else {
                final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class, tFisica
                        .getFisica().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                pkTramPerFis
                        .setCveIdPersona(tFisica.getFisica().getIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(
                        DitTramitePersonaFisica.class, pkTramPerFis) != null;
                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }

        } else if (tramite instanceof TramiteActualizacionCorreo) {
            final TramiteActualizacionCorreo tFisica = (TramiteActualizacionCorreo) tramite;
            if (tFisica.getPersona().getIdPersona() == null) {
                log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
            } else {
                final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class, tFisica
                        .getPersona().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                pkTramPerFis
                        .setCveIdPersona(tFisica.getPersona().getIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(
                        DitTramitePersonaFisica.class, pkTramPerFis) != null;
                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }
        } else if (tramite instanceof TramiteMoral) {

            final TramiteMoral tMoral = (TramiteMoral) tramite;
            if (tMoral.getMoral().getIdPersona() == null) {
                log.info("No se guarda en tabla de relacion a persona moral debido a que actualmente no existe la entidad persona moral.");
            } else {
                final DitTramitePersonaMoralPK pkTramPersMoral = new DitTramitePersonaMoralPK();
                pkTramPersMoral.setCveIdPersonaMoral(tMoral.getMoral()
                        .getIdPersona());
                pkTramPersMoral.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(
                        DitTramitePersonaMoral.class, pkTramPersMoral) != null;
                if (!existeRelTramPersona) {
                    final DitTramitePersonaMoral entityTramitePersonaMoral = new DitTramitePersonaMoral();
                    entityTramitePersonaMoral.setId(pkTramPersMoral);
                    em.persist(entityTramitePersonaMoral);
                }
            }

        } else if (tramite instanceof TramiteAsegurado) {
            final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
            if (tramiteAsegurado.getFisica().getIdPersona() != null) {
                final DitTramitePersonaFisicaPK idTramFisica = new DitTramitePersonaFisicaPK();
                idTramFisica.setCveIdPersona(tramiteAsegurado.getFisica()
                        .getIdPersona());
                idTramFisica.setCveIdTramite(tramiteAsegurado.getTramiteId());
                final Boolean existeRelTramFisica = em.find(
                        DitTramitePersonaFisica.class, idTramFisica) != null;
                if (!existeRelTramFisica) {
                    final DitTramitePersonaFisica ditTramitePersonaFisica = new DitTramitePersonaFisica();
                    ditTramitePersonaFisica.setId(idTramFisica);
                    em.persist(ditTramitePersonaFisica);
                }
            }
        } else if (tramite instanceof TramiteRepresentanteLegal) {
            final TramiteRepresentanteLegal tramiteRL = (TramiteRepresentanteLegal) tramite;
            if (tramiteRL.getFisica().getIdPersona() == null) {
                log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
            } else {
                final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class, tramiteRL
                        .getFisica().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                pkTramPerFis
                        .setCveIdPersona(tramiteRL.getFisica().getIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(
                        DitTramitePersonaFisica.class, pkTramPerFis) != null;
                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }
        } else if (tramite instanceof TramitePersonaAutorizada) {
            final TramitePersonaAutorizada tramitePA = (TramitePersonaAutorizada) tramite;

            if (tramitePA.getPersonaFisica() != null) {
                if (tramitePA.getPersonaFisica().getIdPersona() == null) {
                    log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
                } else {
                    final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                    final DitPersona ditPersona = em.find(DitPersona.class, tramitePA.getPersonaFisica().getIdPersona());
                    entityTramitePersonaFisica.setDitPersona(ditPersona);
                    final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                    pkTramPerFis.setCveIdPersona(tramitePA.getPersonaFisica().getIdPersona());
                    pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                    final Boolean existeRelTramPersona = em.find(
                            DitTramitePersonaFisica.class, pkTramPerFis) != null;
                    if (!existeRelTramPersona) {
                        entityTramitePersonaFisica.setId(pkTramPerFis);
                        em.persist(entityTramitePersonaFisica);
                    }
                }
            } else {
                if (tramitePA.getPersonaMoral().getIdPersona() == null) {
                    log.info("No se guarda en tabla de relacion a persona moral debido a que actualmente no existe la entidad persona moral.");
                } else {
                    final DitTramitePersonaMoralPK pkTramPersMoral = new DitTramitePersonaMoralPK();
                    pkTramPersMoral.setCveIdPersonaMoral(tramitePA.getPersonaMoral().getIdPersona());
                    pkTramPersMoral.setCveIdTramite(ditTramite.getCveIdTramite());
                    final Boolean existeRelTramPersona = em.find(DitTramitePersonaMoral.class, pkTramPersMoral) != null;
                    if (!existeRelTramPersona) {
                        final DitTramitePersonaMoral entityTramitePersonaMoral = new DitTramitePersonaMoral();
                        entityTramitePersonaMoral.setId(pkTramPersMoral);
                        em.persist(entityTramitePersonaMoral);
                    }
                }
            }
        } else if (tramite instanceof TramiteBajaPersonaAutorizada) {
            TramiteBajaPersonaAutorizada tramiteBPA = (TramiteBajaPersonaAutorizada) tramite;
            if (tramiteBPA.getFisica() != null) {
                if (tramiteBPA.getFisica().getIdPersona() == null) {
                    log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
                } else {
                    final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                    final DitPersona ditPersona = em.find(DitPersona.class, tramiteBPA.getFisica().getIdPersona());
                    entityTramitePersonaFisica.setDitPersona(ditPersona);
                    final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                    pkTramPerFis.setCveIdPersona(tramiteBPA.getFisica().getIdPersona());
                    pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                    final Boolean existeRelTramPersona = em.find(
                            DitTramitePersonaFisica.class, pkTramPerFis) != null;
                    if (!existeRelTramPersona) {
                        entityTramitePersonaFisica.setId(pkTramPerFis);
                        em.persist(entityTramitePersonaFisica);
                    }
                }
            } else {
                if (tramiteBPA.getMoral().getIdPersona() == null) {
                    log.info("No se guarda en tabla de relacion a persona moral debido a que actualmente no existe la entidad persona moral.");
                } else {
                    final DitTramitePersonaMoralPK pkTramPersMoral = new DitTramitePersonaMoralPK();
                    pkTramPersMoral.setCveIdPersonaMoral(tramiteBPA.getMoral().getIdPersona());
                    pkTramPersMoral.setCveIdTramite(ditTramite.getCveIdTramite());
                    final Boolean existeRelTramPersona = em.find(DitTramitePersonaMoral.class, pkTramPersMoral) != null;
                    if (!existeRelTramPersona) {
                        final DitTramitePersonaMoral entityTramitePersonaMoral = new DitTramitePersonaMoral();
                        entityTramitePersonaMoral.setId(pkTramPersMoral);
                        em.persist(entityTramitePersonaMoral);
                    }
                }
            }

        } else if (tramite instanceof TramiteCorreccionDerechohabiente) {
            TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente) tramite;

            DitTramitePersonaFisica entityTramitePersonaFisica = null;

            if (tramiteCorreccion.getPersona() != null) {

                log.debug("crear solicitud de correccion para una sola persona");
                entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class, tramiteCorreccion.getPersona().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();

                pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

                final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            } else {
                log.debug("crear solicitud de correccion para varias personas");
                List<Fisica> personas = tramiteCorreccion.getPersonas();

                for (Fisica persona : personas) {
                    entityTramitePersonaFisica = new DitTramitePersonaFisica();
                    final DitPersona ditPersona = em.find(DitPersona.class, persona.getIdPersona());
                    entityTramitePersonaFisica.setDitPersona(ditPersona);
                    final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();

                    pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
                    pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

                    log.debug("El tramite que se relacionara es: " + ditTramite.getCveIdTramite());
                    log.debug("Con la persona: " + ditPersona.getCveIdPersona());

                    final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

                    if (!existeRelTramPersona) {
                        entityTramitePersonaFisica.setId(pkTramPerFis);
                        em.persist(entityTramitePersonaFisica);
                    }

                    entityTramitePersonaFisica = null;
                }

            }
        } else if (tramite instanceof TramiteBajaDerechohabiente || tramite instanceof TramiteReactivacionDerechohab
        		|| tramite instanceof TramiteConsultaVigencia) {
            //se compara para saber si es de baja o reactivacion para sabrer de donde sacar a la persona
            Long cveIdPersona = new Long(0L);
            if (tramite instanceof TramiteBajaDerechohabiente) {
                TramiteBajaDerechohabiente tramiteBaja = (TramiteBajaDerechohabiente) tramite;
                cveIdPersona = tramiteBaja.getPersona().getIdPersona();
            } else if(tramite instanceof TramiteReactivacionDerechohab) {
                TramiteReactivacionDerechohab tramiteBaja = (TramiteReactivacionDerechohab) tramite;
                cveIdPersona = tramiteBaja.getPersona().getIdPersona();
            } else if(tramite instanceof TramiteConsultaVigencia) {
            	TramiteConsultaVigencia tramiteConsultaV = (TramiteConsultaVigencia) tramite;
                cveIdPersona = tramiteConsultaV.getNss().getIdPersona();
            }

            final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
            final DitPersona ditPersona = em.find(DitPersona.class, cveIdPersona);
            entityTramitePersonaFisica.setDitPersona(ditPersona);
            final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();

            pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
            pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

            final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

            if (!existeRelTramPersona) {
                entityTramitePersonaFisica.setId(pkTramPerFis);
                em.persist(entityTramitePersonaFisica);
            }

        } else if (tramite instanceof TramiteCircunscripcionForanea) {
            TramiteCircunscripcionForanea tramiteCir = (TramiteCircunscripcionForanea) tramite;

            final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
            final DitPersona ditPersona = em.find(DitPersona.class, tramiteCir.getPersona().getIdPersona());
            entityTramitePersonaFisica.setDitPersona(ditPersona);
            final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();

            pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
            pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

            final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

            if (!existeRelTramPersona) {
                entityTramitePersonaFisica.setId(pkTramPerFis);
                em.persist(entityTramitePersonaFisica);
            }
        } else if (tramite instanceof TramiteProrroga) {
            TramiteProrroga tramiteProrroga = (TramiteProrroga) tramite;

            final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
            final DitPersona ditPersona = em.find(DitPersona.class, tramiteProrroga.getPersona().getIdPersona());
            entityTramitePersonaFisica.setDitPersona(ditPersona);

            final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
            pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
            pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

            final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

            if (!existeRelTramPersona) {
                entityTramitePersonaFisica.setId(pkTramPerFis);
                em.persist(entityTramitePersonaFisica);
            }
        } else if (tramite instanceof TramiteRiss) {
            if (((TramiteRiss) tramite).getFisica() != null
                    && ((TramiteRiss) tramite).getFisica().getIdPersona() != null) {
                //Dar de alta relacion DitTramitePersonaFisica
                final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class,
                        ((TramiteRiss) tramite).getFisica().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;
                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }
            if (!CollectionUtils.isEmpty(((TramiteRiss) tramite).getListaCveIdSujetosObligados())) {
                for (Long id : ((TramiteRiss) tramite).getListaCveIdSujetosObligados()) {
                    DitPatronSujetoObligado ditPatron = em.find(DitPatronSujetoObligado.class, id);
                    if (ditPatron != null && ditPatron.getCveIdPatronSujetoObligado() > 0) {

                        DitTramitePatSujObligadoPK pk = new DitTramitePatSujObligadoPK();
                        pk.setCveIdPatronSujetoObligado(ditPatron.getCveIdPatronSujetoObligado());
                        pk.setCveIdTramite(ditTramite.getCveIdTramite());

                        final Boolean existeRelTramPersona = em.find(DitTramitePatSujObligado.class, pk) != null;
                        if (!existeRelTramPersona) {
                            DitTramitePatSujObligado entityTramitePatron = new DitTramitePatSujObligado();
                            entityTramitePatron.setId(pk);
                            em.persist(entityTramitePatron);
                        }
                    }
                }
            }
        } else if (tramite instanceof TramiteSocios) {
            final TramiteSocios tramiteSocios = (TramiteSocios) tramite;
            if (tramiteSocios.getPatron().getIdPersona() == null) {
                log.info("No se guarda en tabla de relacion a persona moral debido a que actualmente no existe la entidad persona moral.");
            } else {
                final DitTramitePersonaMoralPK pkTramPersMoral = new DitTramitePersonaMoralPK();
                pkTramPersMoral.setCveIdPersonaMoral(tramiteSocios.getPatron().getIdPersona());
                pkTramPersMoral.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(DitTramitePersonaMoral.class, pkTramPersMoral) != null;
                if (!existeRelTramPersona) {
                    final DitTramitePersonaMoral entityTramitePersonaMoral = new DitTramitePersonaMoral();
                    entityTramitePersonaMoral.setId(pkTramPersMoral);
                    em.persist(entityTramitePersonaMoral);
                }
            }
        } else if (tramite instanceof Tramite32D) {
            final Tramite32D tramite32D = (Tramite32D) tramite;

            if (tramite32D.getPersonaFM() != null) {
                if (tramite32D.getPersonaFM() instanceof Fisica) {
                    Fisica fisica = (Fisica) tramite32D.getPersonaFM();

                    if (fisica.getIdPersona() == null) {
                        log.info("No se guarda en tabla de relacion a persona debido a que actualmente no existe la entidad persona.");
                    } else {
                        final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();

                        final DitPersona ditPersona = em.find(DitPersona.class, fisica.getIdPersona());
                        entityTramitePersonaFisica.setDitPersona(ditPersona);

                        final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                        pkTramPerFis.setCveIdPersona(fisica.getIdPersona());
                        pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

                        final Boolean existeRelTramPersona = em.find(
                                DitTramitePersonaFisica.class, pkTramPerFis) != null;

                        if (!existeRelTramPersona) {
                            entityTramitePersonaFisica.setId(pkTramPerFis);
                            em.persist(entityTramitePersonaFisica);
                        }
                    }
                } else if (tramite32D.getPersonaFM() instanceof Moral) {
                    Moral moral = (Moral) tramite32D.getPersonaFM();

                    if (moral.getIdPersona() == null) {
                        log.info("No se guarda en tabla de relacion a persona moral debido a que actualmente no existe la entidad persona moral.");
                    } else {
                        final DitTramitePersonaMoralPK pkTramPersMoral = new DitTramitePersonaMoralPK();
                        pkTramPersMoral.setCveIdPersonaMoral(moral.getIdPersona());
                        pkTramPersMoral.setCveIdTramite(ditTramite.getCveIdTramite());

                        final Boolean existeRelTramPersona = em.find(DitTramitePersonaMoral.class, pkTramPersMoral) != null;

                        if (!existeRelTramPersona) {
                            final DitTramitePersonaMoral entityTramitePersonaMoral = new DitTramitePersonaMoral();
                            entityTramitePersonaMoral.setId(pkTramPersMoral);
                            em.persist(entityTramitePersonaMoral);
                        }
                    }
                }
            }
        } else if (tramite instanceof TramiteCorreccionCurp || (tramite.getTipoTramite() != null
                && tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo().intValue()
                && tramite.getPersona() != null)) {
            Tramite tramiteCorreccion = (Tramite) tramite;

            DitTramitePersonaFisica entityTramitePersonaFisica = null;

            if (tramiteCorreccion.getPersona() != null) {

                log.debug("crear solicitud de correccion para una sola persona");
                entityTramitePersonaFisica = new DitTramitePersonaFisica();
                final DitPersona ditPersona = em.find(DitPersona.class, tramiteCorreccion.getPersona().getIdPersona());
                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();

                pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());

                final Boolean existeRelTramPersona = em.find(DitTramitePersonaFisica.class, pkTramPerFis) != null;

                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }
        }else if(tramite instanceof TramiteEscritoDesacuerdo){
        	TramiteEscritoDesacuerdo ted = (TramiteEscritoDesacuerdo) tramite;
        	
            if (ted.getPatron().getIdPatronSujetoObligado() == null) {
                log.info("No se guarda en tabla de relacion a Sujeto Obligado debido a que actualmente no existe la entidad SujetoObligado.");
            } else {
                final DitTramitePatSujObligado entityTramiteSujetoObligado = new DitTramitePatSujObligado();
                
                DitTramitePatSujObligadoPK pk = new DitTramitePatSujObligadoPK();
                pk.setCveIdPatronSujetoObligado(ted.getPatron().getIdPatronSujetoObligado());
                pk.setCveIdTramite(ditTramite.getCveIdTramite());
                entityTramiteSujetoObligado.setId(pk);

                em.persist(entityTramiteSujetoObligado);
            }
        } else if (tramite.getTipoTramite() != null
                && Utilerias.isNotBlank(tramite.getTipoTramite().getIdTipoTramite())) {
            Integer idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();
            EstadoTramite estadoTramite = tramite.getEstadoTramite();

            if ((idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo())
                    || idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo())
                    || idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo()))
                    && estadoTramite != null && estadoTramite.getIdEstadoTramitePersona() != null
                    && estadoTramite.getIdEstadoTramitePersona().longValue() == mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum.RECHAZADO.getId()) {
                final DitTramitePersonaFisica entityTramitePersonaFisica = new DitTramitePersonaFisica();

                Fisica fisica = tramite.getPersona();
                DitPersona ditPersona = null;
                if (fisica.getIdPersona() != null) {
                    ditPersona = em.find(DitPersona.class, fisica.getIdPersona());
                } else {
                    StringBuffer query = new StringBuffer().append("select p from DitPersona p ");
                    query.append(" join p.ditAsignacionNsses anss ");
                    query.append(" where anss.numNss =:numNSS ");
                    javax.persistence.Query qry = em.createQuery(query.toString());
                    qry.setParameter("numNSS", fisica.getNss());
                    @SuppressWarnings("unchecked")
                    List<DitPersona> persona = (List<DitPersona>) qry.getResultList();
                    ditPersona = persona.get(0);
                }

                entityTramitePersonaFisica.setDitPersona(ditPersona);
                final DitTramitePersonaFisicaPK pkTramPerFis = new DitTramitePersonaFisicaPK();
                pkTramPerFis.setCveIdPersona(ditPersona.getCveIdPersona());
                pkTramPerFis.setCveIdTramite(ditTramite.getCveIdTramite());
                final Boolean existeRelTramPersona = em.find(
                        DitTramitePersonaFisica.class, pkTramPerFis) != null;
                if (!existeRelTramPersona) {
                    entityTramitePersonaFisica.setId(pkTramPerFis);
                    em.persist(entityTramitePersonaFisica);
                }
            }
        }
    }

    @Override
    public Solicitud consultarPorIdTramite(Long idTramite)
            throws SolicitudNoEncontradaException {
        Solicitud solicitudFound = null; // NOPMD
        if (idTramite != null) {

            log.debug("Realiza consulta de solicitud");
            StringBuffer sb = new StringBuffer();//Se ambia el find pues no encontraba el tr�mite
            sb.append("select tramite from DitTramite tramite where tramite.cveIdTramite = " + idTramite);
            DitTramite ditTramite = (DitTramite) em.createQuery(sb.toString()).getSingleResult();

//			final DitTramite ditTramite = em.find(DitTramite.class,
//					idTramite);
            log.debug("Termina consulta de solicitud");
            if (ditTramite == null) {
                throw new SolicitudNoEncontradaException(
                        idTramite);
            } else {
                log.debug("Realiza conversion de solicitud");
                solicitudFound = solicitudConversor
                        .convertirEntityToModel(ditTramite.getDitSolicitud(), true);
                log.debug("Termina conversion de solicitud");
            }
        }
        return solicitudFound;
    }

    @Override
    public Solicitud consultar(final Solicitud solicitud, boolean obtenerDatosXML)
            throws SolicitudNoEncontradaException {
        Solicitud solicitudFound = null; // NOPMD
        if (solicitud != null) {
            log.debug("Realiza consulta de solicitud");
            final DitSolicitud ditSolicitud = em.find(DitSolicitud.class,
                    solicitud.getSolicitudId());
            log.debug("Termina consulta de solicitud");
            if (ditSolicitud == null) {
                throw new SolicitudNoEncontradaException(
                        solicitud.getSolicitudId());
            } else {
                log.debug("Realiza conversion de solicitud");
                solicitudFound = solicitudConversor
                        .convertirEntityToModel(ditSolicitud, obtenerDatosXML);
                log.debug("Termina conversion de solicitud");
            }
        }
        return solicitudFound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Solicitud consultarFolio(final Solicitud solicitud, boolean obtenerDatosXML)
            throws SolicitudNoEncontradaException {
        Solicitud solicitudFound = null;

        if (solicitud != null) {
            StringBuffer sql = new StringBuffer();
            sql.append(" from DitSolicitud solicitud ");
            sql.append(" where solicitud.refFolio = :folio");

            Query query = this.getSession().createQuery(sql.toString());
            query.setParameter("folio", solicitud.getNoFolioSolicitud());

            DitSolicitud ditSolicitud = (DitSolicitud) query.uniqueResult();

            if (ditSolicitud == null) {
                throw new SolicitudNoEncontradaException(
                        solicitud.getSolicitudId());
            } else {
                log.debug("Realiza conversion de solicitud");
                solicitudFound = solicitudConversor
                        .convertirEntityToModel(ditSolicitud, obtenerDatosXML);
                log.debug("Termina conversion de solicitud");												
            }
        }

        return solicitudFound;
    }
    
	public String consultarDescripcionEstadoSolicitud(String folioSolicitud) throws SolicitudNoEncontradaException {
		StringBuffer sqldescripcion = new StringBuffer();
		sqldescripcion.append(" select det.des_estado_tramite from dit_solicitud ds ");
		sqldescripcion.append(" inner join dit_tramite dt on ds.cve_id_solicitud = dt.cve_id_solicitud ");
		sqldescripcion.append(" inner join dit_detalle_tramite ddt on ddt.cve_id_tramite = dt.cve_id_tramite ");
		sqldescripcion.append(" inner join dic_estado_tramite det on det.cve_id_estado_tramite = dt.cve_id_estado_tramite ");
		sqldescripcion.append(" where ds.ref_folio = :folio ");
		
		javax.persistence.Query queryDescripcion = em.createNativeQuery(sqldescripcion.toString());
		queryDescripcion.setParameter("folio", folioSolicitud);
		
		System.out.println("queryDescripcion---CDA" + sqldescripcion.toString());
		log.debug("folio: " + folioSolicitud);		
		
		String estatus = (String) queryDescripcion.getSingleResult();		
		log.debug("estado solicitud: " + estatus);
		return estatus;
	}
    
    public String obtener_CVECDA(String folio)
    {
    	
    	log.debug("Inicia la consulta  " + Calendar.getInstance().getTime() );
    	StringBuffer sql = new StringBuffer();
		

		sql.append(" SELECT DCDA.CVE_ID_CORRECCION_DATOS_ASEG ");
		sql.append(" FROM DIT_SOLICITUD DS, "); 
		sql.append(" DIT_TRAMITE DT, ");
		sql.append(" DIT_DETALLE_TRAMITE DDT, ");
		sql.append(" DIT_CORRECCION_DATOS_ASEG DCDA ");
		sql.append(" WHERE DS.CVE_ID_SOLICITUD= DT.CVE_ID_SOLICITUD "); 
		sql.append(" AND DCDA.CVE_ID_TRAMITE = DT.CVE_ID_TRAMITE ");
		sql.append(" AND DT.CVE_ID_TRAMITE = DDT.CVE_ID_TRAMITE ");
		sql.append(" AND DS.REF_FOLIO = :folio ");
		
		javax.persistence.Query queryDescripcion = em.createNativeQuery(sql.toString());
		queryDescripcion.setParameter("folio", folio);
		
		BigDecimal cve_CDA =  (BigDecimal)queryDescripcion.getSingleResult();		
		log.debug("Termina la consulta  " + Calendar.getInstance().getTime() );
		return cve_CDA.toString();
    }
    
    @Override
    public ArrayList<String> obteneCatalogo()
    {
    	ArrayList<String> descripcion;
    	log.debug("Inicia la consulta  " + Calendar.getInstance().getTime() );
    	StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DES_TIPO_NSS_ACLARACION FROM DIC_TIPO_NSS_ACLARACION ");
		
		javax.persistence.Query queryDescripcion = em.createNativeQuery(sql.toString());
		 descripcion = (ArrayList<String>) queryDescripcion.getResultList();		
		 log.debug("Termina la consulta  " + Calendar.getInstance().getTime() );
		return descripcion;
    	
    }
    
    @Override
    public void actualizaAConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException {

        if (solicitud != null && solicitud.getSolicitudId() != null) {
            String observaciones = "";

            if (StringUtils.isNotBlank(solicitud.getObservacion())) {
                //observaciones = solicitud.getObservacion().length() > 255 ? solicitud.getObservacion().substring(0, 250) : solicitud.getObservacion();
            	observaciones = solicitud.getObservacion().length() > 1055 ? solicitud.getObservacion().substring(0, 1055) : solicitud.getObservacion();
            }

            String querySolicitud = "update dit_solicitud set cve_id_estado_solicitud = " + EstadoSolicitudEnum.ATENDIDA.getCodigo() + "";
            querySolicitud += ", fec_conclusion = sysdate, fec_registro_actualizado = sysdate, ref_observacion = '" + observaciones + "'";
            querySolicitud += " where cve_id_solicitud = " + solicitud.getSolicitudId();

            String queryTramite = "update dit_tramite set CVE_ID_ESTADO_TRAMITE = " + EstadoTramiteEnum.CERRADO.getCodigo() + ",";
            queryTramite += "IND_RESULTADO = 1, CVE_ID_RAZON_RESULTADO = " + RazonResultadoEnum.NORMAL.getCodigo() + ", FEC_CONCLUSION = sysdate,";
            queryTramite += "FEC_REGISTRO_ACTUALIZADO = sysdate, REF_OBSERVACION = '" + observaciones + "' where CVE_ID_SOLICITUD = " + solicitud.getSolicitudId();

            this.em.createNativeQuery(querySolicitud).executeUpdate();
            this.em.createNativeQuery(queryTramite).executeUpdate();

            /*
			this.getSession().createSQLQuery(querySolicitud).executeUpdate();
			this.getSession().createSQLQuery(queryTramite).executeUpdate();*/
        }
    }

    @Override
    public void actualizaAConcluidaDH(Solicitud solicitud) throws SolicitudNoEncontradaException {

        if (solicitud != null && solicitud.getSolicitudId() != null) {
            String observaciones = "";

            if (StringUtils.isNotBlank(solicitud.getObservacion())) {
                observaciones = solicitud.getObservacion();
            }

            String querySolicitud = "update dit_solicitud set cve_id_estado_solicitud = " + EstadoSolicitudEnum.ATENDIDA.getCodigo() + "";
            querySolicitud += ", fec_conclusion = sysdate, fec_registro_actualizado = sysdate, ref_observacion = '" + observaciones + "'";
            querySolicitud += " where cve_id_solicitud = " + solicitud.getSolicitudId();

            String queryTramite = "update dit_tramite set CVE_ID_ESTADO_TRAMITE = " + EstadoTramiteEnum.CERRADO.getCodigo() + ",";
            queryTramite += "IND_RESULTADO = 1, CVE_ID_RAZON_RESULTADO = " + RazonResultadoEnum.NORMAL.getCodigo() + ", FEC_CONCLUSION = sysdate,";
            queryTramite += "FEC_REGISTRO_ACTUALIZADO = sysdate, REF_OBSERVACION = '" + observaciones + "' where CVE_ID_SOLICITUD = " + solicitud.getSolicitudId();

            this.em.createNativeQuery(querySolicitud).executeUpdate();
            this.em.createNativeQuery(queryTramite).executeUpdate();

            /*
			this.getSession().createSQLQuery(querySolicitud).executeUpdate();
			this.getSession().createSQLQuery(queryTramite).executeUpdate();*/
        }
    }

    @Override
    public void cancelarSolicitud(Long idSolicitud, Long idRazonRechazo, Long idRazonCancelacion, String usuarioCancelacion, String observacionesCancelacion) {
        if (idSolicitud != null) {
            String observaciones = "";

            if (StringUtils.isNotBlank(observacionesCancelacion)) {
                observaciones = observacionesCancelacion.length() > 255 ? observacionesCancelacion.substring(0, 250) : observacionesCancelacion;
            }

            String querySolicitud = "update dit_solicitud set cve_id_estado_solicitud = " + EstadoSolicitudEnum.CANCELADA.getCodigo() + "";
            querySolicitud += ", fec_conclusion = sysdate, fec_registro_actualizado = sysdate";

            if (idRazonCancelacion != null) {
                querySolicitud += ", CVE_ID_RAZON_CANCELACION = " + idRazonCancelacion;
            }

            if (StringUtils.isNotBlank(usuarioCancelacion)) {
                querySolicitud += ", CVE_ID_USUARIO='" + usuarioCancelacion + "'";
            }
            if (StringUtils.isNotBlank(observaciones)) {
                querySolicitud += ", ref_observacion = '" + observaciones + "'";
            }

            querySolicitud += " where cve_id_solicitud = " + idSolicitud;

            String queryTramite = "update dit_tramite set CVE_ID_ESTADO_TRAMITE = " + EstadoTramiteEnum.CANCELADO.getCodigo() + ",";
            queryTramite += "IND_RESULTADO = 0";

            if (idRazonRechazo != null && idRazonRechazo.intValue() != 0) {
                queryTramite += ", CVE_ID_RAZON_RESULTADO = " + idRazonRechazo + "";
            } else {
                queryTramite += ", CVE_ID_RAZON_RESULTADO = " + RazonResultadoEnum.NORMAL.getCodigo() + "";
            }

            if (StringUtils.isNotBlank(observaciones)) {
                queryTramite += ", REF_OBSERVACION = '" + observaciones + "'";
            }

            queryTramite += ", FEC_CONCLUSION = sysdate,";
            queryTramite += "FEC_REGISTRO_ACTUALIZADO = sysdate where CVE_ID_SOLICITUD = " + idSolicitud;

            int numSolicitudes = this.em.createNativeQuery(querySolicitud).executeUpdate();
            int numTramites = this.em.createNativeQuery(queryTramite).executeUpdate();

            log.debug("Termino de cancelar " + numSolicitudes + " solicitud y " + numTramites + " tramites");
            /*
			this.getSession().createSQLQuery(querySolicitud).executeUpdate();
			this.getSession().createSQLQuery(queryTramite).executeUpdate();*/
        }
    }

    @Override
    public void actuliazaUsuarioSolicitud(Solicitud solicitud)
            throws IllegalArgumentException {
        if (solicitud != null && solicitud.getSolicitudId() != null) {

            if (solicitud.getSolicitante() != null) {
                String querySolicitud = "update dit_solicitud set CVE_ID_USUARIO  = '" + solicitud.getSolicitante().getCveIdUsuario() + "'";
                querySolicitud += " where cve_id_solicitud = " + solicitud.getSolicitudId();
                //this.getSession().createSQLQuery(querySolicitud).executeUpdate();
                this.em.createNativeQuery(querySolicitud).executeUpdate();
            } else {
                throw new IllegalArgumentException("Es necesario el ida de la solicitud");
            }

        } else {
            throw new IllegalArgumentException("Es necesario el ida de la solicitud");
        }
    }

    @Override
    public void actualizaTipoTramite(Long idTramite, Long idTipoTramite) {

        if (idTramite != null && idTipoTramite != null) {

            String querySolicitud = "update dit_tramite set CVE_ID_TIPO_TRAMITE  = " + idTipoTramite + "";
            querySolicitud += " where CVE_ID_TRAMITE = " + idTramite;
            this.em.createNativeQuery(querySolicitud).executeUpdate();
            //this.getSession().createSQLQuery(querySolicitud).executeUpdate();
        }
    }

    @Override
    public Solicitud actualizarEstados(final Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        Solicitud solicitudUpdated = solicitud; // NOPMD

        if (solicitud != null && solicitud.getSolicitudId() != null) {
            final DitSolicitud ditSolicitud = em.find(DitSolicitud.class, solicitud.getSolicitudId());
            if (ditSolicitud == null) {
                throw new SolicitudNoEncontradaException(solicitud.getSolicitudId());
            } else {

                boolean cambiarEstadoTramites = false;

                actualizarEstadoSolicitud(solicitud.getEstadoSolicitud(), ditSolicitud);
                actualizarRazonCancelacion(solicitud.getRazonCancelacion(), ditSolicitud);

                /*
				 * Se checa si la solicitud recibida tiene tramites, en caso de
				 * no tener tr�mites, se transforma la lista de ditTramite
				 * reci�n obtenida de la consulta de la solicitud a una lista de
				 * tramites
                 */
                if (CollectionUtils.isEmpty(solicitud.getTramites())) {
                    Tramite tramiteAux = null;
                    for (DitTramite ditTramite : ditSolicitud.getDitTramites()) {
                        tramiteAux = new Tramite();
                        tramiteAux.setTramiteId(ditTramite.getCveIdTramite());
                        solicitud.getTramites().add(tramiteAux);
                    }
                    cambiarEstadoTramites = true;
                }

                for (Tramite tramite : solicitud.getTramites()) {
                    /*
					 * Se agrega validaci�n que checa el estado de la solicitud,
					 * en caso de tener estado de CANCELADA|RECHAZADA se revisa
					 * si cada uno de los tr�mites traen estado, de no traer
					 * estado se les agrega o en caso de que reci�n se hayan
					 * obtenido de la consulta anterior
                     */
                    if (tramite.getEstadoTramite() == null || cambiarEstadoTramites) {
                        EstadoTramite estadoTramite = new EstadoTramite();
                        EstadoSolicitud estadoSolicitud = solicitud.getEstadoSolicitud();

                        if (estadoSolicitud != null) {
                            if (estadoSolicitud.getIdEstadoSolicitud().equals(EstadoSolicitudEnum.CANCELADA.getCodigo())) {
                                estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
                            } else if (estadoSolicitud.getIdEstadoSolicitud().equals(EstadoSolicitudEnum.RECHAZADA.getCodigo())) {
                                estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
                            } else if (estadoSolicitud.getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
                                estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
                            }
                        }

                        tramite.setEstadoTramite(estadoTramite);
                    }

                    actualizarEstadoTramite(tramite);
                }
                if (StringUtils.isNotBlank(solicitud.getObservacion())) {
                    String observaciones = solicitud.getObservacion().length() > 255 ? solicitud.getObservacion().substring(0, 250) : solicitud.getObservacion();
                    ditSolicitud.setRefObservacion(observaciones);
                }
                if (solicitud.getFechaConclusion() != null) {
                    ditSolicitud.setFecConclusion(solicitud.getFechaConclusion());
                }
            }
        }
        return solicitudUpdated;
    }

    private void actualizarRazonCancelacion(RazonCancelacion razon, DitSolicitud ditSolicitud) {

        if (razon != null && razon.getIdRazonCancelacion() != null) {
            DicRazonCancelacion dicRazon = em.find(DicRazonCancelacion.class, razon.getIdRazonCancelacion());
            ditSolicitud.setDicRazonCancelacion(dicRazon);
        }
    }

    private void actualizarEstadoTramite(final Tramite tramite) {

        final DitTramite ditTramite = em.find(DitTramite.class, tramite.getTramiteId());

        final DicEstadoTramite dicEstadoTramiteActual = actualizarEstadoTramite(tramite.getEstadoTramite(), ditTramite.getDicEstadoTramite());

        if (dicEstadoTramiteActual != null) {
            ditTramite.setFecRegistroActualizado(Calendar.getInstance().getTime());
            ditTramite.setDicEstadoTramite(dicEstadoTramiteActual);
            // ACTUALIZACION DEL XML: CONTEMPLA SOLO ACTUALIZAR
            // ESTADO DEL TRAMITE

            log.info("XML anterior: " + ditTramite.getDitDetalleTramite().getRefDatosTramiteXml());
            tramite.getEstadoTramite().setDescripcion(dicEstadoTramiteActual.getDesEstadoTramite());

            if (tramite.getRazonResultado() != null && tramite.getRazonResultado().getIdRazonResultado() != null) {
                DicRazonResultado dicRazonRes = em.find(DicRazonResultado.class, tramite.getRazonResultado().getIdRazonResultado());
                ditTramite.setDicRazonResultado(dicRazonRes);

            }

            try {
                final Tramite tramiteActual = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(ditTramite.getDitDetalleTramite().getRefDatosTramiteXml());
                tramiteActual.setEstadoTramite(tramite.getEstadoTramite());
                tramiteActual.setRazonResultado(tramite.getRazonResultado());
                ditTramite.getDitDetalleTramite().setRefDatosTramiteXml(mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramiteActual));
            } catch (ClassCastException e) {
                log.warn("Error al comvertir el xml del tramite no se actualiza el detalle, tramite no definido ", e);
            }

            log.info("XML actual: " + ditTramite.getDitDetalleTramite().getRefDatosTramiteXml());

            if (StringUtils.isNotBlank(tramite.getObservacion())) {
                String observaciones = tramite.getObservacion().length() > 255 ? tramite.getObservacion().substring(0, 250) : tramite.getObservacion();
                ditTramite.setRefObservacion(observaciones);

            }

            if (tramite.getResultado() != null) {
                ditTramite.setIndResultado(tramite.getResultado() ? BigDecimal.ONE : BigDecimal.ZERO);
            }

        }
    }

    private void actualizarEstadoSolicitud(final EstadoSolicitud estadoSolicitud, final DitSolicitud ditSolicitud) {
        Date fechaActualizacion = Calendar.getInstance().getTime();
        ditSolicitud.setFecRegistroActualizado(fechaActualizacion);
        final DicEstadoSolicitud dicEdoSolicitud = ditSolicitud.getDicEstadoSolicitud();
        if (estadoSolicitud != null && Utilerias.isNotBlank(estadoSolicitud.getIdEstadoSolicitud())
                && (dicEdoSolicitud == null
                || estadoSolicitud.getIdEstadoSolicitud().longValue() != dicEdoSolicitud.getCveIdEstadoSolicitud().longValue())) {
            Long idEstadoSolicitud = estadoSolicitud.getIdEstadoSolicitud().longValue();
            DicEstadoSolicitud nuevoEstado = this.em.find(DicEstadoSolicitud.class, idEstadoSolicitud);
            ditSolicitud.setDicEstadoSolicitud(nuevoEstado);

            // Se actualiza fecha de conclusion de solicitud si el estatus es CANCELADA
            log.debug("Estado Solicitud: " + idEstadoSolicitud);
            if (idEstadoSolicitud.equals(EstadoSolicitudEnum.CANCELADA.getCodigo().longValue())) {
                ditSolicitud.setFecRegistroBaja(fechaActualizacion);
            }
            if (idEstadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue())) {
                ditSolicitud.setFecConclusion(Calendar.getInstance().getTime());
            }
//            fillFechasSolicitud(idEstadoSolicitud, ditSolicitud);
        }
    }

//    private void fillFechasSolicitud(Long idEstadoSolicitud, DitSolicitud ditSolicitud){
//        if (EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue() == idEstadoSolicitud) {
//            ditSolicitud.setFecConclusion(new Date());
//        }
//        else {
//            ditSolicitud.setFecPresentacion(new Date());
//        }
//    }
    
    @Override
    public Solicitud actualizaTramite(final Solicitud solicitud, final Tramite tramite)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
            
            Solicitud solicitudUpdated = solicitud; 
            actualizarTramiteExistente(solicitud, tramite);
        return solicitudUpdated;
    }
    
    @Override
    public Solicitud actualizarTramites(final Solicitud solicitud)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        Solicitud solicitudUpdated = solicitud; // NOPMD
        if (solicitud != null && solicitud.getSolicitudId() != null) {
            log.error("Ini busqueda de solicitud x id: " + solicitud.getSolicitudId());
            final DitSolicitud ditSolicitud = em.find(DitSolicitud.class,
                    solicitud.getSolicitudId());
            if (ditSolicitud == null) {
                throw new SolicitudNoEncontradaException(
                        solicitud.getSolicitudId());
            } else {
                actualizarTramitesSolicitud(solicitud, ditSolicitud);
            }
        }
        return solicitudUpdated;
    }

    private void actualizarTramitesSolicitud(final Solicitud solicitud, final DitSolicitud ditSolicitud)
            throws TramiteNoEncontradoException {
        final List<Tramite> tramites = solicitud.getTramites();

        log.debug("La solicitud " + solicitud.getNoFolioSolicitud()
                + " cuenta con " + tramites.size() + " tramites a actualizar");

        if (tramites != null && !tramites.isEmpty()) {
            final Date fechaActual = new Date(); // NOPMD Classic: 'DU'-anomaly

            for (Tramite tramite : tramites) {
                log.error("tramite.getTramiteId(): " + tramite.getTramiteId());

                if (tramite.getTramiteId() == null) { // TRAMITES NUEVOS
                    crear(fechaActual, tramite, ditSolicitud, solicitud.getSolicitante());
                    log.error("termina crear nuevo tramite: " + tramite.getTramiteId());
                } else { // TRAMITES PRE EXISTENTES
                    actualizarTramiteExistente(solicitud, tramite);
                    log.error("termina actualizarTramiteExistente: " + tramite.getTramiteId());
                }

            }
        }
    }

    private void actualizarTramiteExistente(final Solicitud solicitud,
            final Tramite tramite) throws TramiteNoEncontradoException {

        this.log.debug("Se va a actualizar el tramite -> "
                + tramite.getTramiteId());

        final DitTramite ditTramite = em.find(DitTramite.class,
                tramite.getTramiteId());
        if (ditTramite == null) {
            throw new TramiteNoEncontradoException(tramite.getTramiteId());
        } else {
            if (ditTramite.getDitSolicitud().getCveIdSolicitud() != solicitud
                    .getSolicitudId().longValue()) {
                log.error("Se est\u00E1 tratando actualizar un tr\u00E1mite que no corresponde a esta solicitud! No se realiz\u00F3 la actualizaci\u00F3n.");
                return;
            }

            this.log.debug("Se inicia la actualizacion del tramite -> "
                    + tramite.getTramiteId());

            ditTramite.setFecRegistroActualizado(new Date());
            ditTramite.setFecConclusion(tramite.getFechaConclusion());
            ditTramite.setFecEfecto(tramite.getFechaEfecto());
            ditTramite.setFecPresentacion(tramite.getFechaPresentacion());
            ditTramite.setIndRatificado(tramite.getIndRatificado());
            if (tramite.getFechaTramite() != null
                    && !tramite.getFechaTramite().equals(
                            ditTramite.getFecTramite())) {
                ditTramite.setFecTramite(tramite.getFechaTramite());
            }

            if (tramite.getObservacion() != null
                    && !tramite.getObservacion().equals(
                            ditTramite.getRefObservacion())) {
                ditTramite.setRefObservacion(tramite.getObservacion());
            }

            final DicEstadoTramite dicEstadoTramiteActual = actualizarEstadoTramite(
                    tramite.getEstadoTramite(),
                    ditTramite.getDicEstadoTramite());
            if (dicEstadoTramiteActual != null) {
                ditTramite.setDicEstadoTramite(dicEstadoTramiteActual);
                if (dicEstadoTramiteActual.getCveIdEstadoTramite()
                        .intValue() == EstadoTramiteEnum.CANCELADO.getCodigo().intValue()) {
                    ditTramite.setFecRegistroBaja(new Date());
                }
            }

            final DicTipoTramite dicTipoTramiteActual = actualizarTipoTramite(
                    tramite.getTipoTramite(), ditTramite.getDicTipoTramite());
            if (dicTipoTramiteActual != null) {
                ditTramite.setDicTipoTramite(dicTipoTramiteActual);
            }

            final DicRazonResultado dicRazonResultadoNuevo = actualizarRazonResultado(
                    tramite.getRazonResultado(),
                    ditTramite.getDicRazonResultado());
            if (dicRazonResultadoNuevo != null) {
                ditTramite.setDicRazonResultado(dicRazonResultadoNuevo);
            }

            if (!(tramite instanceof TramiteSujetoObligado)) {
                generarRelacionTramitePersona(tramite, ditTramite);
            }

            /*
			 * Detail is updated using the new objects' graph: note que esta
			 * seccion del codigo borrara el estado anterior de la grafica de
			 * estados codificada en el XML
             */
            log.debug("Se va a actualizar el detalle del tramite -> "
                    + tramite.getTramiteId());

            final DitDetalleTramite ditDetalleTramite = ditTramite
                    .getDitDetalleTramite();
            try {
                Tramite xmlActual = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(ditDetalleTramite.getRefDatosTramiteXml());

                log.debug("El tramite " + tramite.getTramiteId()
                        + " tiene documentos probatorios: "
                        + xmlActual.getDocumentosProbatorios());

                if (xmlActual.getDocumentosProbatorios() != null && tramite.getDocumentosProbatorios() == null) {
                    tramite.setDocumentosProbatorios(xmlActual.getDocumentosProbatorios());
                }
            } catch (ClassCastException e) {
                log.warn("EL tramite no es del tipo esperado, esto es por compatibilidad de modelos", e);
            }

            if (StringUtils.isNotBlank(tramite.getDetalleTramiteXml())) {
                log.debug("Se va actualiza el xml proporcionado previamente para el tramite -> " + tramite.getTramiteId());
                ditDetalleTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
                log.debug("Se actualizo el xml proporcionado previamente para el tramite -> " + tramite.getTramiteId());
            } else {
                log.debug("Se va a transformar el obj tramite para guardarlo como detalle con id -> "
                        + tramite.getTramiteId());
                ditDetalleTramite.setRefDatosTramiteXml(mx.gob.imss.distss.digital.jaxb.util.JaxbUtil
                        .objectToXml(tramite));
                log.debug("Se transformo el obj tramite para guardarlo como detalle con id -> "
                        + tramite.getTramiteId());
            }

            //this.generarBitacoraSeguimiento(tramite, ditTramite,solicitud.getSolicitante());
            this.log.debug("Se finaliza la actualizacion del tramite -> "
                    + tramite.getTramiteId());
        }
    }

    @Override
    public Tramite actualizarXMLTramite(Tramite tramite)
            throws TramiteNoEncontradoException, IllegalArgumentException {

        if (tramite != null && tramite.getTramiteId() != null) {
            Criteria queryDetalle = this.getSession().createCriteria(DitDetalleTramite.class);
            queryDetalle.createAlias("ditTramite", "tramite");
            queryDetalle.add(Restrictions.eq("tramite.cveIdTramite", tramite.getTramiteId()));

            DitDetalleTramite ditDetalle = (DitDetalleTramite) queryDetalle.uniqueResult();

            if (ditDetalle != null) {
                Tramite xmlActual = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(ditDetalle.getRefDatosTramiteXml());
                if (xmlActual != null && xmlActual.getDocumentosProbatorios() != null && tramite.getDocumentosProbatorios() == null) {
                    tramite.setDocumentosProbatorios(xmlActual.getDocumentosProbatorios());
                }

                ditDetalle.setFecRegistroActualizado(new Date());
                ditDetalle.setRefDatosTramiteXml(mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite));
            }
        } else {
            throw new IllegalArgumentException("El tramite o su id tienen valor null");
        }

        return tramite;
    }

    @Override
    public mx.gob.imss.digital.modelo.tramite.Tramite actualizarXMLTramite(
            mx.gob.imss.digital.modelo.tramite.Tramite tramite)
            throws TramiteNoEncontradoException, IllegalArgumentException {

        if (tramite != null && tramite.getTramiteId() != null) {
            Criteria queryDetalle = this.getSession().createCriteria(
                    DitDetalleTramite.class);
            queryDetalle.createAlias("ditTramite", "tramite");
            queryDetalle.add(Restrictions.eq("tramite.cveIdTramite",
                    tramite.getTramiteId()));

            DitDetalleTramite ditDetalle = (DitDetalleTramite) queryDetalle
                    .uniqueResult();

            if (ditDetalle != null) {
                try {
                    mx.gob.imss.digital.modelo.tramite.Tramite xmlActual = (mx.gob.imss.digital.modelo.tramite.Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil
                            .xmlToObject(ditDetalle.getRefDatosTramiteXml());

                    if (xmlActual.getDocumentosProbatorios() != null
                            && tramite.getDocumentosProbatorios() == null) {
                        tramite.setDocumentosProbatorios(xmlActual
                                .getDocumentosProbatorios());
                    }

                    ditDetalle.setFecRegistroActualizado(new Date());
                    ditDetalle.setRefDatosTramiteXml(JaxbUtil.marshaller(tramite));
                } catch (JAXBException e) {
                    this.log.error(e);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "El tramite o su id tienen valor null");
        }

        return tramite;
    }

    private DicTipoTramite actualizarTipoTramite(final TipoTramite tipoTramite,
            final DicTipoTramite dicTipoTramite) {
        DicTipoTramite dicTipoTramiteNuevo = null; // NOPMD
        if (tipoTramite != null
                && Utilerias.isNotBlank(tipoTramite.getIdTipoTramite())
                && (dicTipoTramite == null || !tipoTramite.getIdTipoTramite()
                        .equals(Utilerias.convertir(dicTipoTramite
                                .getCveIdTipoTramite())))) {
            dicTipoTramiteNuevo = em.find(DicTipoTramite.class, tipoTramite
                    .getIdTipoTramite().longValue());
            if (dicTipoTramiteNuevo == null) {
                log.info("No existe el tipo de tramite correspondiente al id "
                        + tipoTramite.getIdTipoTramite());
            }
        }
        return dicTipoTramiteNuevo;
    }

    private DicRazonResultado actualizarRazonResultado(
            final RazonResultado razonResultado,
            final DicRazonResultado dicRazonResultadoAnterior) {
        DicRazonResultado dicRazonResultadoNuevo = null; // NOPMD
        if (razonResultado != null
                && Utilerias.isNotBlank(razonResultado.getIdRazonResultado())
                && (dicRazonResultadoAnterior == null || !razonResultado
                        .getIdRazonResultado().equals(
                                dicRazonResultadoAnterior
                                        .getCveIdRazonResultado()))) {
            dicRazonResultadoNuevo = em.find(DicRazonResultado.class,
                    razonResultado.getIdRazonResultado());
            if (dicRazonResultadoNuevo == null) {
                log.info("No existe la razon resultado correspondiente al id "
                        + razonResultado.getIdRazonResultado());
            }
        }
        return dicRazonResultadoNuevo;
    }

    private DicEstadoTramite actualizarEstadoTramite(final EstadoTramite estadoTramite, final DicEstadoTramite dicEstadoTramiteAnterior) { // NOPMD

        DicEstadoTramite dicEstadoTramiteNuevo = null; // NOPMD
        if (estadoTramite != null && Utilerias.isNotBlank(estadoTramite.getIdEstadoTramitePersona()) && (dicEstadoTramiteAnterior == null || !estadoTramite.getIdEstadoTramitePersona().equals(Utilerias.convertir(dicEstadoTramiteAnterior.getCveIdEstadoTramite())))) {

            dicEstadoTramiteNuevo = em.find(DicEstadoTramite.class, estadoTramite.getIdEstadoTramitePersona().longValue());
            if (dicEstadoTramiteNuevo == null) {
                log.info("No existe el estado de tramite correspondiente al id " + estadoTramite.getIdEstadoTramitePersona());
            } else {
                // ACTUALIZA LA DESCRIPCION:
                dicEstadoTramiteNuevo.getDesEstadoTramite();
            }

        }
        return dicEstadoTramiteNuevo;
    }

    /**
     * 191807 020712 Este metodo consulta el estatus de una Solicitud
     *
     * @param solicitud
     * @return
     */
    public Solicitud consultarEstatus(Solicitud solicitud) {
        this.log.warn("consultarEstatus" + solicitud);
        Solicitud solicitudResultado = new Solicitud();
        try {

            StringBuffer sql = new StringBuffer();
            sql.append("select new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud(solicitud.refFolio, ");
            sql.append("solicitud.cveIdSolicitud, solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud)");
            sql.append(" from DitSolicitud solicitud ");
            sql.append(" where 1 = 1");
            if (StringUtils.isNotBlank(solicitud.getNoFolioSolicitud())) {
                sql.append(" and solicitud.refFolio = :folio");
            }
            if (solicitud.getSolicitudId() != null) {
                sql.append(" and solicitud.cveIdSolicitud = :cveIdSolicitud");
            }

            Query query = this.getSession().createQuery(sql.toString());
            if (StringUtils.isNotBlank(solicitud.getNoFolioSolicitud())) {
                query.setParameter("folio", solicitud.getNoFolioSolicitud());
            }
            if (solicitud.getSolicitudId() != null) {
                query.setParameter("cveIdSolicitud", solicitud.getSolicitudId().longValue());
            }

            solicitudResultado = (Solicitud) query.uniqueResult();

            this.log.warn(" Solicitud encontrada :" + solicitudResultado.getEstadoSolicitud().getIdEstadoSolicitud());

        } catch (Exception e) {
            this.log.error(e);
        }
        return solicitudResultado;
    }

    @Override
    public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona,
            TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud,
            EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados) {
        log.debug(" Obteniendo solicitud activa de la persona :::" + idPersona);

        StringBuffer bfr = new StringBuffer();

        if (tipo.equals(TipoPersonaFiscal.FISICA)) {
            bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
            bfr.append(" join tpf.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpf.id.cveIdPersona = :idPersona ");
            if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
                bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
            }
            if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
                bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
            }
        } else {
            bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
            bfr.append(" join tpm.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona ");
            if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
                bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
            }
            if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
                bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
            }
        }

        //Ordenamiento 
        bfr.append(" order by solicitud.fecSolicitud desc");

        Query query = this.getSession().createQuery(bfr.toString());
        query.setParameter("idPersona", idPersona);
        if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
            query.setParameter("idTipoSolicitud", tipoSolicitud.getValor().longValue());
        }
        if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
            query.setParameter("idEstadoSolicitud", estadoSolicitud.getCodigo().longValue());
        }

        List<DitSolicitud> listDitSolicitud = null;
        try {

            if (maximoResultados) {
                query.setMaxResults(MAXIMO_SOLICITUDES);
            }
            listDitSolicitud = query.list();

        } catch (NoResultException nre) {
            return new ArrayList<Solicitud>();
        }

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {
            for (DitSolicitud ditSolicitud : listDitSolicitud) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModel(ditSolicitud, true);
                if (solicitud != null) {
                    listSolicitud.add(solicitud);
                }
            }
        }

        return listSolicitud;
    }

    @Override
    public List<Solicitud> obtenerSolicitudConDatosBasePorPersona(
            Long idPersona, TipoPersonaFiscal tipo,
            TipoSolicitudEnum tipoSolicitud,
            EstadoSolicitudEnum estadoSolicitud) {
        log.debug(" Obteniendo solicitud activa de la persona :::" + idPersona);

        Criteria querySol = this.getSession().createCriteria(DitSolicitud.class);
        Criteria queryTram = querySol.createCriteria("ditTramites");

        if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
            querySol.createAlias("dicTipoSolicitud", "tipoSol");
            querySol.add(Restrictions.eq("tipoSol.cveIdTipoSolicitud", tipoSolicitud.getValor().longValue()));
        }

        if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
            querySol.createAlias("dicEstadoSolicitud", "edoSol");
            querySol.add(Restrictions.eq("edoSol.cveIdEstadoSolicitud ", estadoSolicitud.getCodigo().longValue()));
        }

        if (tipo.equals(TipoPersonaFiscal.FISICA)) {
            Criteria queryTF = queryTram.createCriteria("ditTramitePersonaFisica");
            queryTF.createAlias("ditPersona", "persona");
            queryTF.add(Restrictions.eq("persona.cveIdPersona", idPersona));
        } else {
            Criteria queryTM = queryTram.createCriteria("ditTramitePersonaMoral");
            queryTM.createAlias("ditPersonaMoral", "moral");
            queryTM.add(Restrictions.eq("moral.cveIdPersonaMoral", idPersona));
        }

        querySol.setMaxResults(MAXIMO_SOLICITUDES);
        querySol.addOrder(Order.desc("fecSolicitud"));

        List<DitSolicitud> listDitSolicitud = null;

        listDitSolicitud = querySol.list();

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {
            for (DitSolicitud ditSolicitud : listDitSolicitud) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModelDatosBase(ditSolicitud);
                if (solicitud != null) {
                    listSolicitud.add(solicitud);
                }
            }
        }

        return listSolicitud;
    }

    @Override
    public List<Solicitud> obtenerSolicitudPorPersonaTramite(Long idPersona,
            TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
            EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados) {
        return obtenerSolicitudPorPersonaTramite(idPersona, tipo, tipoSolicitud, tipoTramite, estadoSolicitud, maximoResultados, true);
    }

    @Override
    public List<Solicitud> obtenerSolicitudPorPersonaTramite(Long idPersona,
            TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
            EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados, Boolean convertXml) {
        log.debug(" Obteniendo solicitud activa de la persona :::" + idPersona);

        StringBuffer bfr = new StringBuffer();

        if (tipo.equals(TipoPersonaFiscal.FISICA)) {
            bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
            bfr.append(" join tpf.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpf.id.cveIdPersona = :idPersona ");
            if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
                bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
            }
            if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
                bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
            }

        } else {
            bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
            bfr.append(" join tpm.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona ");
            if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
                bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
            }
            if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
                bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
            }
        }

        bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite");
        //Ordenamiento 
        bfr.append(" order by solicitud.fecSolicitud desc");

        Query query = this.getSession().createQuery(bfr.toString());
        query.setParameter("idPersona", idPersona);
        if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
            query.setParameter("idTipoSolicitud", tipoSolicitud.getValor().longValue());
        }
        if (!estadoSolicitud.equals(EstadoSolicitudEnum.TODOS)) {
            query.setParameter("idEstadoSolicitud", estadoSolicitud.getCodigo().longValue());
        }

        query.setParameter("idTipoTramite", tipoTramite.getCodigo().longValue());

        List<DitSolicitud> listDitSolicitud = null;
        try {

            if (maximoResultados) {
                query.setMaxResults(MAXIMO_SOLICITUDES);
            }
            listDitSolicitud = query.list();

        } catch (NoResultException nre) {
            return new ArrayList<Solicitud>();
        }

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {
            for (DitSolicitud ditSolicitud : listDitSolicitud) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModel(ditSolicitud, convertXml);
                if (solicitud != null) {
                    listSolicitud.add(solicitud);
                }
            }
        }

        return listSolicitud;
    }

    @Override
    public Tramite crearTramiteASolicitud(Tramite tramite, Long idSolicitud) {

        DitSolicitud ditSolicitud = new DitSolicitud();
        ditSolicitud.setCveIdSolicitud(idSolicitud.longValue());

        DitTramite ditTramite = crear(new Date(), tramite, ditSolicitud, null);

        tramite.setTramiteId(ditTramite.getCveIdTramite());

        return tramite;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(
            DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros,
            boolean mostrarSolicInternet) {

        this.log.debug("Ejecutando consulta con folio: " + filtros.getFolio());

        List<Solicitud> solicitudes = null;
        Integer totalResultados = null;
        Map<Long, Solicitud> solicitudesMap = null;
        Map<String, Object> resultadoFiltrosPart = null;
        Set<Long> cveSolicitudes = null;
        List<Object[]> resultList = null;

        List<Modulo> lstModulo = filtros.getListaModulos();
        boolean agregarFiltroModulo = false;

        // Se checa si se tiene que agregar el filtro por m�dulo
        if (lstModulo != null && !lstModulo.isEmpty()) {
            this.log.debug("entre al filtro de modulos");

            StringBuffer strModulos = new StringBuffer();
            int lstSize = lstModulo.size();

            for (int i = 0; i < lstModulo.size(); i++) {
                Modulo modulo = (Modulo) lstModulo.get(i);
                strModulos.append(modulo.getIdModulo());
                if ((lstSize - 1) > i) {
                    strModulos.append(", ");
                }
            }
            agregarFiltroModulo = true;
        }

        StringBuffer queryDetalleSolic = new StringBuffer();
        queryDetalleSolic.append("SELECT sol.cve_id_solicitud, sol.cve_id_tipo_solicitud, sol.ref_folio, ");
        queryDetalleSolic.append("tsol.des_tipo_solicitud, origsol.des_origen_solicitud, ");
        queryDetalleSolic.append("DECODE(per.curp, NULL, perSO.curp, per.curp), ");
        queryDetalleSolic.append("DECODE(per.rfc, NULL, DECODE(pm.rfc, NULL, DECODE(perSO.rfc, NULL, moralso.rfc, perSO.rfc), pm.rfc), per.rfc), ");
        queryDetalleSolic.append("sol.fec_solicitud, sol.fec_conclusion, ");
        queryDetalleSolic.append("deleg.des_deleg, subdeleg.des_subdelegacion, ");
        queryDetalleSolic.append("edosol.des_estado_solicitud, sol.cve_id_estado_solicitud, ");
        queryDetalleSolic.append("pgral.reg_patron, mod.num_modalidad, pgral.dig_ver, ");
        queryDetalleSolic.append("sol.fec_presentacion ");
        queryDetalleSolic.append("FROM dit_solicitud sol ");
        queryDetalleSolic.append("INNER JOIN dit_tramite tram ");
        queryDetalleSolic.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
        queryDetalleSolic.append("INNER JOIN dic_tipo_solicitud tsol ");
        queryDetalleSolic.append("ON sol.cve_id_tipo_solicitud = tsol.cve_id_tipo_solicitud ");
        queryDetalleSolic.append("INNER JOIN dic_origen_solicitud origsol ");
        queryDetalleSolic.append("ON sol.cve_id_origen_solicitud = origsol.cve_id_origen_solicitud ");
        queryDetalleSolic.append("INNER JOIN dic_tipo_tramite ttram ");
        queryDetalleSolic.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
        if (agregarFiltroModulo) {
            queryDetalleSolic.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
            queryDetalleSolic.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
        }
        queryDetalleSolic.append("LEFT JOIN dic_subdelegacion subdeleg ");
        queryDetalleSolic.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
        queryDetalleSolic.append("LEFT JOIN dic_delegacion deleg ");
        queryDetalleSolic.append("ON subdeleg.cve_id_delegacion = deleg.cve_id_delegacion ");
        queryDetalleSolic.append("INNER JOIN dic_estado_solicitud edosol ");
        queryDetalleSolic.append("ON sol.cve_id_estado_solicitud = edosol.cve_id_estado_solicitud ");
        queryDetalleSolic.append("LEFT JOIN dit_tramite_persona_fisica tpf ");
        queryDetalleSolic.append("ON tram.cve_id_tramite = tpf.cve_id_tramite ");
        queryDetalleSolic.append("LEFT JOIN dit_persona per ");
        queryDetalleSolic.append("ON tpf.cve_id_persona = per.cve_id_persona ");
        queryDetalleSolic.append("LEFT JOIN dit_tramite_persona_moral tpm ");
        queryDetalleSolic.append("ON tram.cve_id_tramite = tpm.cve_id_tramite ");
        queryDetalleSolic.append("LEFT JOIN dit_persona_moral pm ");
        queryDetalleSolic.append("ON tpm.cve_id_persona_moral = pm.cve_id_persona_moral ");
        queryDetalleSolic.append("LEFT JOIN dit_tramite_pat_suj_obligado tpso ");
        queryDetalleSolic.append("ON tram.cve_id_tramite = tpso.cve_id_tramite ");
        queryDetalleSolic.append("LEFT JOIN dit_patron_sujeto_obligado pso ");
        queryDetalleSolic.append("ON tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
        queryDetalleSolic.append("LEFT JOIN dit_patron_general pgral ");
        queryDetalleSolic.append("ON pso.cve_id_patron_sujeto_obligado = pgral.cve_id_patron_sujeto_obligado ");
        queryDetalleSolic.append("LEFT JOIN dic_modalidad mod ");
        queryDetalleSolic.append("ON pso.cve_id_modalidad = mod.cve_id_modalidad ");
        queryDetalleSolic.append("LEFT JOIN dit_persona_fisica fisicaSO ");
        queryDetalleSolic.append("ON pso.cve_id_persona_fisica = fisicaSO.cve_id_persona_fisica ");
        queryDetalleSolic.append("LEFT JOIN dit_persona perSO ");
        queryDetalleSolic.append("ON fisicaSO.cve_id_persona = perSO.cve_id_persona ");
        queryDetalleSolic.append("LEFT JOIN dit_persona_moral moralSO ");
        queryDetalleSolic.append("ON pso.cve_id_persona_moral = moralSO.cve_id_persona_moral ");
        queryDetalleSolic.append("WHERE sol.cve_id_solicitud IN (:cveSolicitudes) ");
        queryDetalleSolic.append("GROUP BY sol.cve_id_solicitud, sol.cve_id_tipo_solicitud, sol.ref_folio, ");
        queryDetalleSolic.append("tsol.des_tipo_solicitud, origsol.des_origen_solicitud, ");
        queryDetalleSolic.append("DECODE(per.curp, NULL, perSO.curp, per.curp), ");
        queryDetalleSolic.append("DECODE(per.rfc, NULL, DECODE(pm.rfc, NULL, DECODE(perSO.rfc, NULL, moralso.rfc, perSO.rfc), pm.rfc), per.rfc), ");
        queryDetalleSolic.append("sol.fec_solicitud, sol.fec_conclusion, ");
        queryDetalleSolic.append("deleg.des_deleg, subdeleg.des_subdelegacion, ");
        queryDetalleSolic.append("edosol.des_estado_solicitud, sol.cve_id_estado_solicitud, ");
        queryDetalleSolic.append("pgral.reg_patron, mod.num_modalidad, pgral.dig_ver, ");
        queryDetalleSolic.append("sol.fec_presentacion ");
        queryDetalleSolic.append("ORDER BY sol.cve_id_solicitud DESC ");

        StringBuffer queryTramitesSol = new StringBuffer();
        queryTramitesSol.append("SELECT tram.cve_id_solicitud, ttram.des_tipo_tramite ");
        queryTramitesSol.append("FROM dit_tramite tram ");
        queryTramitesSol.append("INNER JOIN dic_tipo_tramite ttram ");
        queryTramitesSol.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
        queryTramitesSol.append("WHERE tram.cve_id_solicitud IN (:cveSolicitudes) ");
        queryTramitesSol.append("ORDER BY tram.cve_id_solicitud DESC");

        resultadoFiltrosPart = obtenerSolicitudesParaDetalle(filtros, input,
                agregarFiltroModulo, mostrarSolicInternet, lstModulo);
        cveSolicitudes = (Set<Long>) resultadoFiltrosPart.get(REGISTROS);
        totalResultados = (Integer) resultadoFiltrosPart.get(TOTAL_REGISTROS);

        if (cveSolicitudes != null && !cveSolicitudes.isEmpty()
                && totalResultados > 0) {

            this.log.debug("Se va a obtener el detalle de las solicitudes obtenidas a trav�s de alguno de los filtros particulares");

            javax.persistence.Query queryResultados = this.em.createNativeQuery(queryDetalleSolic.toString());
            queryResultados.setParameter("cveSolicitudes", cveSolicitudes);

            resultList = queryResultados.getResultList();

            solicitudesMap = new LinkedHashMap<Long, Solicitud>();
            Solicitud solicitud = null;

            for (Object[] result : resultList) {
                solicitud = solicitudesMap.get(((BigDecimal) result[0]).longValue());

                if (solicitud == null) {
                    solicitud = new Solicitud();

                    solicitud.setSolicitudId(((BigDecimal) result[0]).longValue());

                    TipoSolicitud tipoSolicitud = new TipoSolicitud();
                    tipoSolicitud.setIdTipoSolicitud(((BigDecimal) result[1]).longValue());
                    tipoSolicitud.setDescripcion(result[3].toString());
                    solicitud.setTipoSolicitud(tipoSolicitud);

                    solicitud.setNoFolioSolicitud(result[2].toString());

                    OrigenSolicitud origenSolicitud = new OrigenSolicitud();
                    origenSolicitud.setDescripcion(result[4].toString());
                    solicitud.setOrigenSolicitud(origenSolicitud);

                    SujetoObligado so = new SujetoObligado();

                    Fisica fisica = new Fisica();
                    fisica.setCurp(result[5] != null ? result[5].toString() : null);
                    fisica.setRfc(result[6] != null ? result[6].toString() : null);
                    so.setFisica(fisica);

                    Moral moral = new Moral();
                    moral.setRfc(result[6] != null ? result[6].toString() : null);
                    so.setMoral(moral);

                    if (result[13] != null && StringUtils.isNotBlank(result[13].toString())) {
                        so.setNumeroRegistroPatronal(result[13].toString());
                        Modalidad modalidad = new Modalidad();
                        modalidad.setNumModalidad(result[14] != null ? result[14].toString() : "");
                        so.setModalidad(modalidad);
                        so.setDigVerificador(result[15] != null ? result[15].toString() : "");
                    }

                    solicitud.setSujetoObligado(so);
                    solicitud.setFechaSolicitud((Date) result[7]);
                    solicitud.setFechaConclusion((Date) result[8]);
                    solicitud.setFechaPresentacion((Date) result[16]);

                    if (result[9] != null) {
                        Subdelegacion subdelegacion = new Subdelegacion();
                        subdelegacion.setDescripcion(result[10].toString());
                        Delegacion delegacion = new Delegacion();
                        delegacion.setDescripcion(result[9].toString());
                        subdelegacion.setDelegacion(delegacion);
                        solicitud.setSubdelegacion(subdelegacion);
                    }

                    EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                    estadoSolicitud.setIdEstadoSolicitud(((BigDecimal) result[12]).intValue());
                    estadoSolicitud.setDescripcion(result[11].toString());
                    solicitud.setEstadoSolicitud(estadoSolicitud);

                    solicitudesMap.put(solicitud.getSolicitudId(), solicitud);
                }
            }

            javax.persistence.Query queryTramites = this.em.createNativeQuery(queryTramitesSol.toString());
            queryTramites.setParameter("cveSolicitudes", solicitudesMap.keySet());

            resultList = queryTramites.getResultList();

            for (Object[] result : resultList) {
                solicitud = solicitudesMap.get(((BigDecimal) result[0]).longValue());

                Tramite tramite = new Tramite();
                TipoTramite tipoTramite = new TipoTramite();
                tipoTramite.setDescripcion(result[1].toString());
                tramite.setTipoTramite(tipoTramite);
                solicitud.getTramites().add(tramite);
            }

        } else {
            this.log.debug("No se obtuvieron resultados a trav�s de alguno de los filtros particulares");
        }

        if (solicitudesMap != null && solicitudesMap.values() != null
                && !solicitudesMap.values().isEmpty()) {
            solicitudes = new ArrayList<Solicitud>(solicitudesMap.values());
        } else {
            solicitudes = new ArrayList<Solicitud>();
        }

        DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
        output.setAaData(solicitudes);
        output.setiTotalRecords(totalResultados == null ? 0 : totalResultados);
        output.setiTotalDisplayRecords(totalResultados == null ? 0 : totalResultados);

        return output;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron(DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros) {
        StringBuffer bfr = new StringBuffer();
        System.err.println("Ejecutando consulta con folio: " + filtros.getFolio());
        String truncatedInitDate = null;
        String truncatedFinalDate = null;
        String truncatedConclusionInitDate = null;
        String truncatedConclusionFinalDate = null;

        //SUBQUERIES
        StringBuffer bfrTramitePersonaFisica = new StringBuffer();
        bfrTramitePersonaFisica.append("select tpf.id.cveIdTramite from DitTramitePersonaFisica tpf ");
        bfrTramitePersonaFisica.append("join tpf.ditPersona pf ");
        bfrTramitePersonaFisica.append("where pf.rfc like '%" + filtros.getRfc() + "%'");

        StringBuffer bfrTramitePersonaMoral = new StringBuffer();
        bfrTramitePersonaMoral.append("select tpm.id.cveIdTramite from DitTramitePersonaMoral tpm ");
        bfrTramitePersonaMoral.append("join tpm.ditPersonaMoral pm ");
        bfrTramitePersonaMoral.append("where pm.rfc like '%" + filtros.getRfc() + "%'");

        StringBuffer bfrTramiteRPFisica = new StringBuffer();
        bfrTramiteRPFisica.append("select pso2.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso2 ");
        bfrTramiteRPFisica.append("join pso2.ditPersonaFisica pf2 ");
        bfrTramiteRPFisica.append("where pf2.rfc like '% " + filtros.getRfc() + "%'");

        StringBuffer bfrTramiteRPMoral = new StringBuffer();
        bfrTramiteRPMoral.append("select pso3.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso3 ");
        bfrTramiteRPMoral.append("join pso3.ditPersonaMoral pm2 ");
        bfrTramiteRPMoral.append("where pm2.rfc like '%" + filtros.getRfc() + "%'");

        StringBuffer bfrTramiteSujetoObligado = new StringBuffer();
        bfrTramiteSujetoObligado.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
        bfrTramiteSujetoObligado.append("join tso.ditPatronSujetoObligado pso ");
        bfrTramiteSujetoObligado.append("where pso.cveIdPatronSujetoObligado in ( ");
        bfrTramiteSujetoObligado.append(bfrTramiteRPFisica.toString());
        bfrTramiteSujetoObligado.append(" ) or pso.cveIdPatronSujetoObligado in ( ");
        bfrTramiteSujetoObligado.append(bfrTramiteRPMoral.toString());
        bfrTramiteSujetoObligado.append(" ) ");

        StringBuffer bfrTramiteRP = new StringBuffer();
        bfrTramiteRP.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
        bfrTramiteRP.append("join tso.ditPatronSujetoObligado pso ");
        bfrTramiteRP.append("join pso.ditPatronGenerals patronGeneral ");
        bfrTramiteRP.append("where patronGeneral.regPatron = :regPatronal ");
        if (filtros.getRp().length() > 8) {
            bfrTramiteRP.append("and pso.dicModalidad.numModalidad = :numModalidad ");
        }
        if (filtros.getRp().length() > 10) {
            bfrTramiteRP.append("and patronGeneral.digVer = :digVerificador ");
        }

        boolean condicionPrevia = false;

        //QUERY BASE
        bfr.append(" Select DISTINCT solicitud from DitSolicitud solicitud ");
        bfr.append(" join solicitud.ditTramites ditTramites ");

        /*
		//where de filtro de modulos de el filtro de modulos
		List<Modulo> lstModulo =filtros.getListaModulos();
		if(lstModulo!= null && !lstModulo.isEmpty()){
			this.log.debug("entre al filtro de modulos");
			agregarCondicional(condicionPrevia, bfr);
			StringBuffer strModulos = new StringBuffer();
			int lstSize = lstModulo.size();
			for(int i=0; i<lstModulo.size(); i++){
				Modulo modulo = (Modulo)lstModulo.get(i);
				strModulos.append(modulo.getIdModulo());
				if( (lstSize-1) < i){
					strModulos.append(", ");
				}
			}
			bfr.append(" ditTramites.dicTipoTramite.dicModulos.cveIdModulo in("+strModulos + ")");
			condicionPrevia=true;
		}
         */
        if (!StringUtils.isBlank(filtros.getRfc()) && StringUtils.isBlank(filtros.getRp())) {
            System.err.println("Ejecutando consulta por rfc: " + filtros.getRfc());
            agregarCondicional(condicionPrevia, bfr);
            condicionPrevia = true;
            bfr.append(" ( ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramitePersonaFisica.toString());
            bfr.append(" ) or ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramitePersonaMoral.toString());
            bfr.append(" ) or ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramiteSujetoObligado.toString());
            bfr.append(" ) )");
        } else if (!StringUtils.isBlank(filtros.getRfc()) && !StringUtils.isBlank(filtros.getRp())) {
            agregarCondicional(condicionPrevia, bfr);
            condicionPrevia = true;
            bfr.append(" ( ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramitePersonaFisica.toString());
            bfr.append(" ) or ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramitePersonaMoral.toString());
            bfr.append(" ) or ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramiteRP.toString());
            bfr.append(" ) )");
        } else if (StringUtils.isBlank(filtros.getRfc()) && !StringUtils.isBlank(filtros.getRp())) {
            agregarCondicional(condicionPrevia, bfr);
            condicionPrevia = true;
            bfr.append(" ( ditTramites.cveIdTramite in ( ");
            bfr.append(bfrTramiteRP.toString());
            bfr.append(" ) )");
        }

        if (filtros.getFechaInicioPresentacion() != null && filtros.getFechaFinPresentacion() != null) {
            agregarCondicional(condicionPrevia, bfr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            truncatedInitDate = sdf.format(filtros.getFechaInicioPresentacion());
            truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
            bfr.append(" trunc( solicitud.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");
            condicionPrevia = true;
        }

        if (filtros.getFechaInicioConclusion() != null && filtros.getFechaFinConclusion() != null) {
            agregarCondicional(condicionPrevia, bfr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            truncatedConclusionInitDate = sdf.format(filtros.getFechaInicioConclusion());
            truncatedConclusionFinalDate = sdf.format(filtros.getFechaFinConclusion());
            bfr.append(" trunc ( solicitud.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion,'yyyy-MM-dd') ");
            condicionPrevia = true;
        }

        if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
            agregarCondicional(condicionPrevia, bfr);
            bfr.append(" ditTramites.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
            condicionPrevia = true;
        }

        if (filtros.getIdEstadoSolicitud() != null && filtros.getIdEstadoSolicitud() > 0) {
            agregarCondicional(condicionPrevia, bfr);
            bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
            condicionPrevia = true;
        } else if (filtros.getIdEstadoSolicitud() == null || (filtros.getIdEstadoSolicitud() != null && filtros.getIdEstadoSolicitud() <= 0)) {
            agregarCondicional(condicionPrevia, bfr);
            bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idEstadoSolicitud ");
            condicionPrevia = true;
        }

        if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
            agregarCondicional(condicionPrevia, bfr);
            bfr.append(" ditTramites.cveIdTramite IN ( ");
            bfr.append("		select tso.cveIdTramite from DitTramitePatSujObligado tso ");
            bfr.append("			join tso.ditPatronSujetoObligado pso ");
            bfr.append("			join pso.ditSubdelPatSujOblig ditSubdelPatSujOblig ");
            bfr.append("			join ditSubdelPatSujOblig.dicSubdelegacion dicSubdelegacion ");
            bfr.append("			join dicSubdelegacion.dicDelegacion dicDelegacion ");
            bfr.append("		where dicDelegacion.cveIdDelegacion = :idDelegacion");
            bfr.append("	)	");
            condicionPrevia = true;
        }

        if (filtros.getIdSubdelegacion() != null && filtros.getIdSubdelegacion() > 0) {
            agregarCondicional(condicionPrevia, bfr);
            bfr.append(" solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
            condicionPrevia = true;
        }

        //Se agrega para limitar los tipos de solicitud a mostrar
        agregarCondicional(condicionPrevia, bfr);
        bfr.append(" ( solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ").append(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue());
        bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ").append(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue());
        bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ").append(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue());
        bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ").append(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue()).append(" ) ");
        bfr.append(" order by solicitud.refFolio asc");

        javax.persistence.Query query = this.em.createQuery(bfr.toString());
        javax.persistence.Query queryTotal = this.em.createQuery(bfr.toString());

        if (!StringUtils.isBlank(filtros.getRp())) {
            if (filtros.getRp().length() >= 8) {
                queryTotal.setParameter("regPatronal", filtros.getRp().substring(0, 8));
                query.setParameter("regPatronal", filtros.getRp().substring(0, 8));
            }
            if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
                queryTotal.setParameter("numModalidad", filtros.getRp().substring(8, 10));
                query.setParameter("numModalidad", filtros.getRp().substring(8, 10));
            }
            if (filtros.getRp().length() == 11) {
                queryTotal.setParameter("digVerificador", filtros.getRp().substring(filtros.getRp().length() - 1));
                query.setParameter("digVerificador", filtros.getRp().substring(filtros.getRp().length() - 1));
            }

        }
        if (filtros.getFechaInicioPresentacion() != null && filtros.getFechaFinPresentacion() != null) {
            queryTotal.setParameter("fechaInicioPresentacion", truncatedInitDate);
            query.setParameter("fechaInicioPresentacion", truncatedInitDate);

            queryTotal.setParameter("fechaFinPresentacion", truncatedFinalDate);
            query.setParameter("fechaFinPresentacion", truncatedFinalDate);
        }

        if (filtros.getFechaInicioConclusion() != null && filtros.getFechaFinConclusion() != null) {
            queryTotal.setParameter("fechaInicioConclusion", truncatedConclusionInitDate);
            query.setParameter("fechaInicioConclusion", truncatedConclusionInitDate);

            queryTotal.setParameter("fechaFinConclusion", truncatedConclusionFinalDate);
            query.setParameter("fechaFinConclusion", truncatedConclusionFinalDate);
        }

        if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
            queryTotal.setParameter("idTipoTramite", filtros.getTramiteId());
            query.setParameter("idTipoTramite", filtros.getTramiteId());
        }

        if (filtros.getIdEstadoSolicitud() != null && filtros.getIdEstadoSolicitud() > 0) {
            queryTotal.setParameter("idEstadoSolicitud", filtros.getIdEstadoSolicitud());
            query.setParameter("idEstadoSolicitud", filtros.getIdEstadoSolicitud());
        } else if (filtros.getIdEstadoSolicitud() == null || (filtros.getIdEstadoSolicitud() != null && filtros.getIdEstadoSolicitud() <= 0)) {
            List<Long> idsEstadoValidos = new ArrayList<Long>();
            idsEstadoValidos.add(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue());
            idsEstadoValidos.add(EstadoSolicitudEnum.CANCELADA.getCodigo().longValue());
            idsEstadoValidos.add(EstadoSolicitudEnum.RECHAZADA.getCodigo().longValue());
            queryTotal.setParameter("idEstadoSolicitud", idsEstadoValidos);
            query.setParameter("idEstadoSolicitud", idsEstadoValidos);
        }

        if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
            queryTotal.setParameter("idDelegacion", filtros.getIdDelegacion());
            query.setParameter("idDelegacion", filtros.getIdDelegacion());
        }

        if (filtros.getIdSubdelegacion() != null && filtros.getIdSubdelegacion() > 0) {
            queryTotal.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
            query.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
        }

        List<DitSolicitud> entitiesTotales = queryTotal.getResultList();
        Integer totalresult = entitiesTotales.size();

        if (input != null) {
            query.setFirstResult(input.getiDisplayStart());
            query.setMaxResults(input.getiDisplayLength());
        }

        List<DitSolicitud> entitiesADesplegar = query.getResultList();

        List<Solicitud> solicitudes = new ArrayList<Solicitud>();
        System.err.println("solicitudes por persona encontradas: " + totalresult);
        if (entitiesADesplegar != null) {
            System.err.println("solicitudes por persona encontradas: " + entitiesADesplegar.size());
            solicitudes = new ArrayList<Solicitud>();
            for (DitSolicitud entity : entitiesADesplegar) {
                Solicitud nuevaSolicitud = solicitudConversor.convertirEntityToModelBasico(entity);
                SujetoObligado sujetoObligadoSolicitud = solicitudConversor.convertirEntityToModelSujetoObligadoSolicitud(entity);
                nuevaSolicitud.setSujetoObligado(sujetoObligadoSolicitud);
                solicitudes.add(nuevaSolicitud);
            }
        }

        DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
        output.setAaData(solicitudes);
        output.setiTotalRecords(totalresult);
        output.setiTotalDisplayRecords(totalresult);

        return output;
    }

    private void agregarCondicional(boolean condicionPrevia, StringBuffer bfr) {
        if (condicionPrevia) {
            bfr.append(" and ");
        } else {
            bfr.append(" where ");
        }

    }

    @Override
    public List<Solicitud> obtenerInfoBasicaSolicitudesPorPersona(
            Long idPersona, Long tipoPersona, TipoSolicitudEnum tipoSolicitud,
            mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud) {

        log.debug(" Obteniendo solicitudes de la persona -> " + idPersona);

        Criteria querySol = this.getSession().createCriteria(DitSolicitud.class)
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("cveIdSolicitud"), "cveIdSolicitud")
                        .add(Projections.groupProperty("fecCita"), "fecCita")
                        .add(Projections.groupProperty("fecRegistroActualizado"), "fecRegistroActualizado")
                        .add(Projections.groupProperty("fecRegistroAlta"), "fecRegistroAlta")
                        .add(Projections.groupProperty("fecRegistroBaja"), "fecRegistroBaja")
                        .add(Projections.groupProperty("fecSolicitud"), "fecSolicitud")
                        .add(Projections.groupProperty("refFolio"), "refFolio")
                        .add(Projections.groupProperty("fecConclusion"), "fecConclusion")
                        .add(Projections.groupProperty("fecPresentacion"), "fecPresentacion")
                        .add(Projections.groupProperty("dicTipoSolicitud"), "dicTipoSolicitud")
                        .add(Projections.groupProperty("dicEstadoSolicitud"), "dicEstadoSolicitud"))
                .setResultTransformer(Transformers.aliasToBean(DitSolicitud.class));

        Criteria queryTram = querySol.createCriteria("ditTramites");

        if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
            querySol.createAlias("dicTipoSolicitud", "tipoSol");
            querySol.add(Restrictions.eq("tipoSol.cveIdTipoSolicitud", tipoSolicitud.getValor().longValue()));
        }

        if (estadoSolicitud != null) {
            querySol.createAlias("dicEstadoSolicitud", "edoSol");
            querySol.add(Restrictions.eq("edoSol.cveIdEstadoSolicitud ", estadoSolicitud.getValor().intValue()));
        }

        if (tipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
            Criteria queryTF = queryTram.createCriteria("ditTramitePersonaFisica");
            queryTF.add(Restrictions.eq("ditPersona.cveIdPersona", idPersona));
        } else {
            Criteria queryTM = queryTram.createCriteria("ditTramitePersonaMoral");
            queryTM.createAlias("ditPersonaMoral", "moral");
            queryTM.add(Restrictions.eq("moral.cveIdPersonaMoral", idPersona));
        }

        querySol.addOrder(Order.desc("fecSolicitud"));

        List<DitSolicitud> listDitSolicitud = null;

        listDitSolicitud = querySol.list();

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {

            Solicitud solicitud = null;
            Tramite tramite = null;

            for (DitSolicitud ditSolicitud : listDitSolicitud) {

                solicitud = solicitudConversor.convertirEntityToModelDatosBase(ditSolicitud);

                if (solicitud.getTramites() == null) {
                    solicitud.setTramites(new ArrayList<Tramite>());
                }

                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.append("select tr.CVE_ID_TRAMITE, ttr.DES_TIPO_TRAMITE ");
                sqlQuery.append("from DIT_TRAMITE tr, DIC_TIPO_TRAMITE ttr ");
                sqlQuery.append("where tr.CVE_ID_TIPO_TRAMITE = ttr.CVE_ID_TIPO_TRAMITE ");
                sqlQuery.append("and tr.CVE_ID_SOLICITUD = :idSolicitud");

                Query query = this.getSession().createSQLQuery(sqlQuery.toString());
                query.setParameter("idSolicitud", ditSolicitud.getCveIdSolicitud());

                List<Object[]> entities = query.list();

                for (Object[] entity : entities) {
                    tramite = new Tramite();
                    tramite.setTramiteId(Long.valueOf(entity[0].toString()));

                    TipoTramite tipoTramite = new TipoTramite();
                    tipoTramite.setDescripcion(entity[1].toString().toUpperCase());
                    tramite.setTipoTramite(tipoTramite);

                    solicitud.getTramites().add(tramite);
                }

                listSolicitud.add(solicitud);
            }
        }

        return listSolicitud;
    }

    @Override
    public List<Solicitud> obtenerInfoBasicaSolicitudesPorPersonaPortal(
            Long idPersona, Long tipoPersona, TipoSolicitudEnum tipoSolicitud,
            mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud) {

        log.debug(" Obteniendo solicitudes de la persona -> " + idPersona);

        Criteria querySol = this.getSession().createCriteria(DitSolicitud.class)
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("cveIdSolicitud"), "cveIdSolicitud")
                        .add(Projections.groupProperty("fecCita"), "fecCita")
                        .add(Projections.groupProperty("fecRegistroActualizado"), "fecRegistroActualizado")
                        .add(Projections.groupProperty("fecRegistroAlta"), "fecRegistroAlta")
                        .add(Projections.groupProperty("fecRegistroBaja"), "fecRegistroBaja")
                        .add(Projections.groupProperty("fecSolicitud"), "fecSolicitud")
                        .add(Projections.groupProperty("refFolio"), "refFolio")
                        .add(Projections.groupProperty("fecConclusion"), "fecConclusion")
                        .add(Projections.groupProperty("fecPresentacion"), "fecPresentacion")
                        .add(Projections.groupProperty("dicTipoSolicitud"), "dicTipoSolicitud")
                        .add(Projections.groupProperty("dicEstadoSolicitud"), "dicEstadoSolicitud")
                        .add(Projections.groupProperty("dicOrigenSolicitud"), "dicOrigenSolicitud"))
                .setResultTransformer(Transformers.aliasToBean(DitSolicitud.class));

        Criteria queryTram = querySol.createCriteria("ditTramites");
        querySol.add(Restrictions.ne("dicOrigenSolicitud.cveIdOrigenSolicitud", (long)1));

        if (!tipoSolicitud.equals(TipoSolicitudEnum.TODAS)) {
            querySol.createAlias("dicTipoSolicitud", "tipoSol");
            querySol.add(Restrictions.eq("tipoSol.cveIdTipoSolicitud", tipoSolicitud.getValor().longValue()));
        }

        if (estadoSolicitud != null) {
            querySol.createAlias("dicEstadoSolicitud", "edoSol");
            querySol.add(Restrictions.eq("edoSol.cveIdEstadoSolicitud ", estadoSolicitud.getValor().intValue()));
        }

        if (tipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
            Criteria queryTF = queryTram.createCriteria("ditTramitePersonaFisica");
            queryTF.add(Restrictions.eq("ditPersona.cveIdPersona", idPersona));
        } else {
            Criteria queryTM = queryTram.createCriteria("ditTramitePersonaMoral");
            queryTM.createAlias("ditPersonaMoral", "moral");
            queryTM.add(Restrictions.eq("moral.cveIdPersonaMoral", idPersona));
        }

        querySol.addOrder(Order.desc("fecSolicitud"));

        List<DitSolicitud> listDitSolicitud = null;

        listDitSolicitud = querySol.list();

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {

            Solicitud solicitud = null;
            Tramite tramite = null;

            for (DitSolicitud ditSolicitud : listDitSolicitud) {

                solicitud = solicitudConversor.convertirEntityToModelDatosBase(ditSolicitud);

                if (solicitud.getTramites() == null) {
                    solicitud.setTramites(new ArrayList<Tramite>());
                }

                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.append("select tr.CVE_ID_TRAMITE, ttr.DES_TIPO_TRAMITE ");
                sqlQuery.append("from DIT_TRAMITE tr, DIC_TIPO_TRAMITE ttr ");
                sqlQuery.append("where tr.CVE_ID_TIPO_TRAMITE = ttr.CVE_ID_TIPO_TRAMITE ");
                sqlQuery.append("and tr.CVE_ID_SOLICITUD = :idSolicitud");

                Query query = this.getSession().createSQLQuery(sqlQuery.toString());
                query.setParameter("idSolicitud", ditSolicitud.getCveIdSolicitud());

                List<Object[]> entities = query.list();

                for (Object[] entity : entities) {
                    tramite = new Tramite();
                    tramite.setTramiteId(Long.valueOf(entity[0].toString()));

                    TipoTramite tipoTramite = new TipoTramite();
                    tipoTramite.setDescripcion(entity[1].toString().toUpperCase());
                    tramite.setTipoTramite(tipoTramite);

                    solicitud.getTramites().add(tramite);
                }

                listSolicitud.add(solicitud);
            }
        }

        return listSolicitud;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Solicitud> listarSolicitudesDeRegistroPatronalPorId(
            Long idPatronSujetoObligado) {
        this.log.debug(" Obteniendo solicitud en proceso del Patron :::"
                + idPatronSujetoObligado);

        StringBuffer bfr = new StringBuffer();
        bfr.append(" Select DISTINCT solicitud from DitTramitePatSujObligado tso ");
        bfr.append(" join tso.ditTramite as tramite ");
        bfr.append(" join tramite.ditSolicitud as solicitud ");
        bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
        bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado");

        javax.persistence.Query query = this.em.createQuery(bfr.toString());
        query.setParameter("idSujetoObligado", idPatronSujetoObligado);

        List<DitSolicitud> entities = null;
        entities = query.getResultList();
        List<Solicitud> solicitudes = null;
        if (entities != null) {
            solicitudes = new ArrayList<Solicitud>();
            for (DitSolicitud entity : entities) {
                Solicitud nuevaSolicitud = solicitudConversor.convertirEntityToModel(entity, false);
                SujetoObligado sujeto = new SujetoObligado();
                // Se agrega la informaci�n de registro patronal a la solicitud
                if (entity.getDitTramites() != null
                        && entity.getDitTramites().size() > 0) {

                    DitPatronSujetoObligado so = this.em.find(DitPatronSujetoObligado.class, idPatronSujetoObligado);
                    DicModalidad modalidad = so.getDicModalidad();
                    sujeto.setCveIdSujetoObligado(so
                            .getCveIdPatronSujetoObligado());
                    DitPatronGeneral patronGeneral = so.getDitPatronGenerals()
                            .get(0);

                    sujeto.setNumeroRegistroPatronal(patronGeneral
                            .getRegPatron());
                    sujeto.setDigVerificador(patronGeneral.getDigVer());
                    sujeto.setModalidad(new Modalidad());
                    sujeto.getModalidad().setIdModalidad(
                            modalidad.getCveIdModalidad());
                    sujeto.getModalidad().setNumModalidad(
                            modalidad.getNumModalidad());
                    sujeto.getModalidad().setDescripcion(
                            modalidad.getDesModalidad());

                }
                nuevaSolicitud.setSujetoObligado(sujeto);
                solicitudes.add(nuevaSolicitud);
            }
        }
        return solicitudes;
    }

    @Override
    public void asociarTramiteAltaARegistroPatronal(Tramite tramite,
            Long idPatronSujetoObligado) {
        DitTramitePatSujObligado entity = new DitTramitePatSujObligado();

        DitTramitePatSujObligadoPK pk = new DitTramitePatSujObligadoPK();
        pk.setCveIdTramite(tramite.getTramiteId());
        pk.setCveIdPatronSujetoObligado(idPatronSujetoObligado);
        entity.setId(pk);
//		entity.setCveIdTramite(tramite.getTramiteId());
//		DitPatronSujetoObligado psoEntity = this.em.find(DitPatronSujetoObligado.class, idPatronSujetoObligado);
//		entity.setDitPatronSujetoObligado(psoEntity);
        this.em.persist(entity);
    }

    @Override
    public void actualizarDatosGeneralesDeSolicitud(Solicitud solicitud) {
        DitSolicitud ditSolicitud = em.find(DitSolicitud.class,
                solicitud.getSolicitudId());
        if (solicitud.getRazonCancelacion() != null
                && solicitud.getRazonCancelacion().getIdRazonCancelacion() != null) {
            DicRazonCancelacion razonCancelacion = em.find(
                    DicRazonCancelacion.class, solicitud.getRazonCancelacion()
                            .getIdRazonCancelacion());
            ditSolicitud.setDicRazonCancelacion(razonCancelacion);
        }
        //ditSolicitud.setFecPresentacion(solicitud.getFechaPresentacion());
        ditSolicitud.setFecConclusion(solicitud.getFechaConclusion());
        if (solicitud.getFechaConclusion() != null) {
            ditSolicitud.setFecRegistroActualizado(solicitud.getFechaConclusion());
        } else {
            ditSolicitud.setFecRegistroActualizado(Calendar.getInstance().getTime());
        }
        if (solicitud.getSolicitante() != null) {
            this.log.debug("Se agrega usuario a las observaciones de la solicitud: "
                    + solicitud.getSolicitante().getUsuario());
            ditSolicitud.setCveIdUsuario(solicitud.getSolicitante().getUsuario());
            /*
			 * Se checa si la solicitud ya tiene observaciones, de ser as�, se
			 * concatena para no planchar lo anterior
             */
            if (StringUtils.isNotBlank(ditSolicitud.getRefObservacion())) {
                StringBuffer observacion = new StringBuffer();
                observacion.append(ditSolicitud.getRefObservacion());
                observacion.append("|").append(
                        solicitud.getSolicitante().getUsuario());
                ditSolicitud.setRefObservacion(observacion.toString());
            } else {
                if (solicitud.getTipoSolicitud() != null
                        && solicitud.getTipoSolicitud().getIdTipoSolicitud()
                                .intValue() != TipoSolicitudEnum.ASIGNACION_NSS
                                .getValor().intValue()) {
                    ditSolicitud.setRefObservacion(solicitud.getSolicitante()
                            .getUsuario());
                }
            }
        }

        if (solicitud.getSubdelegacion() != null && solicitud.getSubdelegacion().getId() != null) {
            DicSubdelegacion subdel = em.find(DicSubdelegacion.class, solicitud.getSubdelegacion().getId());
            ditSolicitud.setDicSubdelegacion(subdel);
        }

    }

    @Override
    public Object getDocumentoPorTipoIdTramite(Long idTramite,
            Long idDocumentoPorTipo) {
        Criteria consultaDocto = this.getSession().createCriteria(DitDoctoResultanteTramite.class);

        consultaDocto.createAlias("ditTramite", "tramite");
        consultaDocto.add(Restrictions.eq("tramite.cveIdTramite", idTramite));
        consultaDocto.createAlias("ditDocumentoPorTipo", "tipoDocto");
        consultaDocto.add(Restrictions.eq("tipoDocto.cveIdDoctoProbPorTipo", idDocumentoPorTipo));

        List<DitDoctoResultanteTramite> doctoRes = null;

        try {
            doctoRes = consultaDocto.list();
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch (NonUniqueResultException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doctoRes != null && doctoRes.size() > 0) {
            return doctoRes.get(0).getRefDocumentoResultante();
        }

        return null;
    }

    @Override
    public void actualizarDocumentosTramite(Long idTramite,
            Long idDocumentoTipo, Object bytes) {

        DitDoctoResultanteTramite doctoRes = new DitDoctoResultanteTramite();
        doctoRes.setDitDocumentoPorTipo(new DitDocumentoPorTipo());
        doctoRes.setDitTramite(new DitTramite());

        doctoRes.getDitDocumentoPorTipo().setCveIdDoctoProbPorTipo(idDocumentoTipo);
        doctoRes.getDitTramite().setCveIdTramite(idTramite);
        doctoRes.setRefDocumentoResultante((byte[]) bytes);

        doctoRes.setFecRegistroAlta(new Date());

        this.em.persist(doctoRes);
    }

    @Override
    public Subdelegacion getSubdelegacionById(Long idSubdelegacion) {

        Criteria criteria = this.getSession().createCriteria(DicSubdelegacion.class);
        criteria.add(Restrictions.eq("cveIdSubdelegacion", idSubdelegacion));
        criteria.add(Restrictions.isNull("fecRegistroBaja"));

        DicSubdelegacion dicSubdelegacion = (DicSubdelegacion) criteria.uniqueResult();
        Subdelegacion subdelegacion = new Subdelegacion();
        subdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());
        subdelegacion.setId(idSubdelegacion);
        subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
        subdelegacion.setDelegacion(new Delegacion());
        subdelegacion.getDelegacion().setId(dicSubdelegacion.getDicDelegacion().getCveIdDelegacion());
        subdelegacion.getDelegacion().setClave(dicSubdelegacion.getDicDelegacion().getClaveDelegacion());
        subdelegacion.getDelegacion().setDescripcion(dicSubdelegacion.getDicDelegacion().getDesDeleg());

        return subdelegacion;
    }
    
    @Override
    public Delegacion getDelegacionById(Long idDelegacion) {
        Criteria criteria = this.getSession().createCriteria(DicDelegacion.class);
        criteria.add(Restrictions.eq("cveIdDelegacion", idDelegacion));
        
        DicDelegacion dicDelegacion = (DicDelegacion) criteria.uniqueResult();
        Delegacion delegacion = new Delegacion();
        delegacion.setClave(dicDelegacion.getClaveDelegacion());
        delegacion.setDescripcion(dicDelegacion.getDesDeleg());
        delegacion.setId(dicDelegacion.getCveIdDelegacion());
        return delegacion;
    }

    @Override
    public void actualizarEstadoMensajeError(String folioSolicitud,
            Integer idEstadoParaAsignar, Integer idEstadoTramiteAsignar, String observacion) {
        log.error("Metodo notificacion modificado");
        StringBuffer querySolicitud = new StringBuffer();
        querySolicitud.append("select ditSolicitud from DitSolicitud ditSolicitud");
        querySolicitud.append(" where ditSolicitud.refFolio = '" + folioSolicitud + "'");

        javax.persistence.Query solQry = this.em.createQuery(querySolicitud.toString());
        DitSolicitud ditSolicitud = (DitSolicitud) solQry.getSingleResult();
        DicEstadoTramite dicEstadotramite = null;
        DicEstadoSolicitud dicEstadoSolicitud = null;
        if (idEstadoTramiteAsignar != null) {
            dicEstadotramite = this.em.find(DicEstadoTramite.class, idEstadoTramiteAsignar.longValue());
        }
        if (idEstadoParaAsignar != null) {
            dicEstadoSolicitud = this.em.find(DicEstadoSolicitud.class, idEstadoParaAsignar.longValue());
        }

        if (!ditSolicitud.getDicEstadoSolicitud().getCveIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue())) {
            if (dicEstadoSolicitud != null) {
                ditSolicitud.setDicEstadoSolicitud(dicEstadoSolicitud);
            }
            if (observacion != null && !observacion.equals("")) {
                ditSolicitud.setRefObservacion(observacion);
            }
            if (dicEstadotramite != null) {
                for (DitTramite ditTramite : ditSolicitud.getDitTramites()) {
                    ditTramite.setDicEstadoTramite(dicEstadotramite);
                }
            }
        }
    }

    @Override
    public void cancelarSolicitudPorFolio(String folio) {
        try {
            StringBuffer query = new StringBuffer();
            query.append("update DIT_SOLICITUD set CVE_ID_ESTADO_SOLICITUD = :idEstadoSolicitud");
            query.append(" where REF_FOLIO =:folio");
            javax.persistence.Query updSolQuery = em.createNativeQuery(query.toString());
            updSolQuery.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.CANCELADA.getCodigo());
            updSolQuery.setParameter("folio", folio);

            int numSolicitudesActualizadas = updSolQuery.executeUpdate();
            log.error("Se cancelaron el sig numero de solicitudes: " + numSolicitudesActualizadas);

            StringBuffer queryTr = new StringBuffer();
            queryTr.append("update DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = :idEstadoTramite");
            queryTr.append(" where CVE_ID_SOLICITUD IN ( SELECT CVE_ID_SOLICITUD FROM DIT_SOLICITUD WHERE REF_FOLIO =:folio )");
            javax.persistence.Query updTrQuery = em.createNativeQuery(queryTr.toString());
            updTrQuery.setParameter("idEstadoTramite", EstadoTramiteEnum.CANCELADO.getCodigo());
            updTrQuery.setParameter("folio", folio);

            int numTramitesCancelados = updTrQuery.executeUpdate();

            log.error("Se cancelaron el sig numero de tramites: " + numTramitesCancelados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelarTramitePorId(Long idTramite) throws TramiteNoEncontradoException {

        final DitTramite ditTramite = em.find(DitTramite.class, idTramite);
        if (ditTramite == null) {
            throw new TramiteNoEncontradoException(idTramite);
        } else {
            this.log.debug("Se inicia la actualizacion del tramite -> " + idTramite);

            DicEstadoTramite dicEstadotramite = this.em.find(
                    DicEstadoTramite.class, EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
            ditTramite.setDicEstadoTramite(dicEstadotramite);

            this.log.debug("Se finaliza la actualizacion del tramite -> " + idTramite);
        }
    }
    
     @Override
    public void cancelarTramite(Tramite tramite) throws TramiteNoEncontradoException {

        final DitTramite ditTramite = em.find(DitTramite.class, tramite.getTramiteId());
        if (ditTramite == null) {
            throw new TramiteNoEncontradoException(tramite.getTramiteId());
        } else {
            this.log.debug("Se inicia la actualizacion del tramite -> " + tramite.getTramiteId());

            DicEstadoTramite dicEstadotramite = this.em.find(
                    DicEstadoTramite.class, EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
            ditTramite.setDicEstadoTramite(dicEstadotramite);
            ditTramite.setFecRegistroBaja(new Date());
            ditTramite.getDitDetalleTramite().setFecRegistroBaja(new Date());

            this.log.debug("Se finaliza la actualizacion del tramite -> " + tramite);
        }
    }

    @Override
    public void concluirSolicitudPorFolio(String folio) {
        try {
            Calendar.getInstance().getTime();

            StringBuffer query = new StringBuffer();
            query.append("update DIT_SOLICITUD set CVE_ID_ESTADO_SOLICITUD = :idEstadoSolicitud, ");
            query.append("FEC_CONCLUSION = sysdate, FEC_REGISTRO_ACTUALIZADO = sysdate");
            query.append(" where REF_FOLIO =:folio");
            javax.persistence.Query updSolQuery = em.createNativeQuery(query.toString());
            updSolQuery.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.ATENDIDA.getCodigo());
            updSolQuery.setParameter("folio", folio);

            int numSolicitudesActualizadas = updSolQuery.executeUpdate();
            log.error("Se concluyeron el sig numero de solicitudes: " + numSolicitudesActualizadas);

            StringBuffer queryTr = new StringBuffer();
            queryTr.append("update DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = :idEstadoTramite, ");
            queryTr.append("CVE_ID_RAZON_RESULTADO = :idRazonResultado, ");
            queryTr.append("FEC_CONCLUSION = sysdate, FEC_REGISTRO_ACTUALIZADO = sysdate");
            queryTr.append(" where CVE_ID_SOLICITUD IN ( SELECT CVE_ID_SOLICITUD FROM DIT_SOLICITUD WHERE REF_FOLIO =:folio )");
            javax.persistence.Query updTrQuery = em.createNativeQuery(queryTr.toString());
            updTrQuery.setParameter("idEstadoTramite", EstadoTramiteEnum.CERRADO.getCodigo());
            updTrQuery.setParameter("folio", folio);
            updTrQuery.setParameter("idRazonResultado", RazonResultadoEnum.NORMAL.getCodigo());

            int numTramitesCancelados = updTrQuery.executeUpdate();

            log.error("Se concluyeron el sig numero de tramites: " + numTramitesCancelados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existeSolicitud(Solicitud solicitud) {

        boolean existeSolicitud = false;

        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select count(*) from DIT_SOLICITUD sol ");

        if (StringUtils.isNotBlank(solicitud.getNoFolioSolicitud())) {
            sqlQuery.append("where sol.REF_FOLIO = :folioIdSolic");
        } else {
            sqlQuery.append("where sol.CVE_ID_SOLICITUD = :folioIdSolic");
        }

        javax.persistence.Query query = this.em.createNativeQuery(sqlQuery.toString());
        query.setParameter("folioIdSolic", StringUtils.isNotBlank(solicitud.getNoFolioSolicitud()) ? solicitud.getNoFolioSolicitud()
                : solicitud.getSolicitudId());

        BigDecimal count = (BigDecimal) query.getSingleResult();

        if (count.intValue() > 0) {
            existeSolicitud = true;
        }

        return existeSolicitud;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, Object> obtenerSolicitudesParaDetalle(FiltroSolicitud filtros,
            DatosEntradaPaginador<Solicitud> input,
            boolean agregarFiltroModulo, boolean mostrarSolicInternet,
            List<Modulo> modulos) {

        List<Long> cveSolicitudesAux = new ArrayList<Long>();
        javax.persistence.Query query = null;
        List resultList = null;
        Set<Long> cveSolicitudes = null;
        Integer totalRegistros = null;

        StringBuffer querySolDetalle = new StringBuffer();
        querySolDetalle.append("SELECT solDetalle.cve_id_solicitud ");
        querySolDetalle.append("FROM (");

        StringBuffer queryResultadosTotal = new StringBuffer();
        queryResultadosTotal.append("SELECT COUNT(solDetalle.cve_id_solicitud) ");
        queryResultadosTotal.append("FROM (");

        if (StringUtils.isNotBlank(filtros.getCurp())) {

            StringBuffer querySolFisicaByCurp = new StringBuffer();
            querySolFisicaByCurp.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolFisicaByCurp.append("FROM dit_solicitud sol ");
            querySolFisicaByCurp.append("INNER JOIN dit_tramite tram ");
            querySolFisicaByCurp.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            querySolFisicaByCurp.append("INNER JOIN dit_tramite_persona_fisica tpf ");
            querySolFisicaByCurp.append("ON tram.cve_id_tramite = tpf.cve_id_tramite ");
            querySolFisicaByCurp.append("INNER JOIN dit_persona per ");
            querySolFisicaByCurp.append("ON tpf.cve_id_persona = per.cve_id_persona ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolFisicaByCurp.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolFisicaByCurp.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolFisicaByCurp.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolFisicaByCurp.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolFisicaByCurp.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolFisicaByCurp.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolFisicaByCurp.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
            querySolFisicaByCurp.append("AND per.curp = :curp ");

            StringBuffer querySolSujObligByCurp = new StringBuffer();
            querySolSujObligByCurp.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolSujObligByCurp.append("FROM dit_solicitud sol ");
            querySolSujObligByCurp.append("INNER JOIN dit_tramite tram ");
            querySolSujObligByCurp.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            querySolSujObligByCurp.append("INNER JOIN dit_tramite_pat_suj_obligado tpso ");
            querySolSujObligByCurp.append("ON tram.cve_id_tramite = tpso.cve_id_tramite ");
            querySolSujObligByCurp.append("INNER JOIN dit_patron_sujeto_obligado pso ");
            querySolSujObligByCurp.append("ON tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
            querySolSujObligByCurp.append("INNER JOIN dit_persona_fisica pf ");
            querySolSujObligByCurp.append("ON pso.cve_id_persona_fisica = pf.cve_id_persona_fisica ");
            querySolSujObligByCurp.append("INNER JOIN dit_persona per ");
            querySolSujObligByCurp.append("ON pf.cve_id_persona = per.cve_id_persona ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolSujObligByCurp.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolSujObligByCurp.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolSujObligByCurp.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolSujObligByCurp.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolSujObligByCurp.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolSujObligByCurp.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolSujObligByCurp.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
            querySolSujObligByCurp.append("AND per.curp = :curp ");

            querySolDetalle.append(querySolFisicaByCurp);
            querySolDetalle.append("UNION ");
            querySolDetalle.append(querySolSujObligByCurp);

            queryResultadosTotal.append(querySolFisicaByCurp);
            queryResultadosTotal.append("UNION ");
            queryResultadosTotal.append(querySolSujObligByCurp);

        } else if (StringUtils.isNotBlank(filtros.getNss())) {

            StringBuffer querySolFisicaByNss = new StringBuffer();
            querySolFisicaByNss.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolFisicaByNss.append("FROM dit_solicitud sol ");
            querySolFisicaByNss.append("INNER JOIN dit_tramite tram ");
            querySolFisicaByNss.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            querySolFisicaByNss.append("INNER JOIN dit_tramite_persona_fisica tpf ");
            querySolFisicaByNss.append("ON tram.cve_id_tramite = tpf.cve_id_tramite ");
            querySolFisicaByNss.append("INNER JOIN dit_asignacion_nss nss ");
            querySolFisicaByNss.append("ON tpf.cve_id_persona = nss.cve_id_persona ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolFisicaByNss.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolFisicaByNss.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolFisicaByNss.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolFisicaByNss.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolFisicaByNss.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolFisicaByNss.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolFisicaByNss.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
            querySolFisicaByNss.append("AND nss.num_nss = :nss ");

            StringBuffer querySolSujObligByNss = new StringBuffer();
            querySolSujObligByNss.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolSujObligByNss.append("FROM dit_solicitud sol ");
            querySolSujObligByNss.append("INNER JOIN dit_tramite tram ");
            querySolSujObligByNss.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            querySolSujObligByNss.append("INNER JOIN dit_tramite_pat_suj_obligado tpso ");
            querySolSujObligByNss.append("ON tram.cve_id_tramite = tpso.cve_id_tramite ");
            querySolSujObligByNss.append("INNER JOIN dit_patron_sujeto_obligado pso ");
            querySolSujObligByNss.append("ON tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
            querySolSujObligByNss.append("INNER JOIN dit_persona_fisica pf ");
            querySolSujObligByNss.append("ON pso.cve_id_persona_fisica = pf.cve_id_persona_fisica ");
            querySolSujObligByNss.append("INNER JOIN dit_asignacion_nss nss ");
            querySolSujObligByNss.append("ON pf.cve_id_persona = nss.cve_id_persona ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolSujObligByNss.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolSujObligByNss.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolSujObligByNss.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolSujObligByNss.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolSujObligByNss.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolSujObligByNss.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolSujObligByNss.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
            querySolSujObligByNss.append("AND nss.num_nss = :nss ");

            querySolDetalle.append(querySolFisicaByNss);
            querySolDetalle.append("UNION ");
            querySolDetalle.append(querySolSujObligByNss);

            queryResultadosTotal.append(querySolFisicaByNss);
            queryResultadosTotal.append("UNION ");
            queryResultadosTotal.append(querySolSujObligByNss);

        } else if (StringUtils.isNotBlank(filtros.getRfc())) {

            StringBuffer querySolFisicaByRfc = new StringBuffer();
            querySolFisicaByRfc.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolFisicaByRfc.append("FROM dit_solicitud sol ");
            querySolFisicaByRfc.append("INNER JOIN dit_tramite tram ");
            querySolFisicaByRfc.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolFisicaByRfc.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolFisicaByRfc.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolFisicaByRfc.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolFisicaByRfc.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolFisicaByRfc.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolFisicaByRfc.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            if (filtros.getRfc().length() == 13) {
                querySolFisicaByRfc.append("INNER JOIN dit_tramite_persona_fisica tpf ");
                querySolFisicaByRfc.append("ON tram.cve_id_tramite = tpf.cve_id_tramite ");
                querySolFisicaByRfc.append("INNER JOIN dit_persona per ");
                querySolFisicaByRfc.append("ON tpf.cve_id_persona = per.cve_id_persona ");
                querySolFisicaByRfc.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
                querySolFisicaByRfc.append("AND per.rfc = :rfc ");
            } else if (filtros.getRfc().length() == 12) {
                querySolFisicaByRfc.append("INNER JOIN dit_tramite_persona_moral tpm ");
                querySolFisicaByRfc.append("ON tram.cve_id_tramite = tpm.cve_id_tramite ");
                querySolFisicaByRfc.append("INNER JOIN dit_persona_moral pm ");
                querySolFisicaByRfc.append("ON tpm.cve_id_persona_moral = pm.cve_id_persona_moral ");
                querySolFisicaByRfc.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
                querySolFisicaByRfc.append("AND pm.rfc = :rfc ");
            }

            StringBuffer querySolSujObligByRfc = new StringBuffer();
            querySolSujObligByRfc.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolSujObligByRfc.append("FROM dit_solicitud sol ");
            querySolSujObligByRfc.append("INNER JOIN dit_tramite tram ");
            querySolSujObligByRfc.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolSujObligByRfc.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolSujObligByRfc.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolSujObligByRfc.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolSujObligByRfc.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolSujObligByRfc.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolSujObligByRfc.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolSujObligByRfc.append("INNER JOIN dit_tramite_pat_suj_obligado tpso ");
            querySolSujObligByRfc.append("ON tram.cve_id_tramite = tpso.cve_id_tramite ");
            querySolSujObligByRfc.append("INNER JOIN dit_patron_sujeto_obligado pso ");
            querySolSujObligByRfc.append("ON tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
            if (filtros.getRfc().length() == 13) {
                querySolSujObligByRfc.append("INNER JOIN dit_persona_fisica pf ");
                querySolSujObligByRfc.append("ON pso.cve_id_persona_fisica = pf.cve_id_persona_fisica ");
                querySolSujObligByRfc.append("INNER JOIN dit_persona per ");
                querySolSujObligByRfc.append("ON pf.cve_id_persona = per.cve_id_persona ");
                querySolSujObligByRfc.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
                querySolSujObligByRfc.append("AND per.rfc = :rfc ");
            } else if (filtros.getRfc().length() == 12) {
                querySolSujObligByRfc.append("INNER JOIN dit_persona_moral pm ");
                querySolSujObligByRfc.append("ON pso.cve_id_persona_moral = pm.cve_id_persona_moral ");
                querySolSujObligByRfc.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));
                querySolSujObligByRfc.append("AND pm.rfc = :rfc ");
            }

            querySolDetalle.append(querySolFisicaByRfc);
            querySolDetalle.append("UNION ");
            querySolDetalle.append(querySolSujObligByRfc);

            queryResultadosTotal.append(querySolFisicaByRfc);
            queryResultadosTotal.append("UNION ");
            queryResultadosTotal.append(querySolSujObligByRfc);

        } else if (StringUtils.isNotBlank(filtros.getRp())) {

            StringBuffer querySolSujObligByNrp = new StringBuffer();
            StringBuffer querySolSujObligByNrpCount = new StringBuffer();
            querySolSujObligByNrp.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolSujObligByNrpCount.append("SELECT COUNT(DISTINCT(sol.cve_id_solicitud)) ");

            StringBuffer querySolSujObligByNrpCommon = new StringBuffer();
            querySolSujObligByNrpCommon.append("FROM dit_solicitud sol ");
            querySolSujObligByNrpCommon.append("INNER JOIN dit_tramite tram ");
            querySolSujObligByNrpCommon.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolSujObligByNrpCommon.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolSujObligByNrpCommon.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolSujObligByNrpCommon.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolSujObligByNrpCommon.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolSujObligByNrpCommon.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolSujObligByNrpCommon.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolSujObligByNrpCommon.append("INNER JOIN dit_tramite_pat_suj_obligado tpso ");
            querySolSujObligByNrpCommon.append("ON tram.cve_id_tramite = tpso.cve_id_tramite ");
            querySolSujObligByNrpCommon.append("INNER JOIN dit_patron_sujeto_obligado pso ");
            querySolSujObligByNrpCommon.append("ON tpso.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
            querySolSujObligByNrpCommon.append("INNER JOIN dit_patron_general pgral ");
            querySolSujObligByNrpCommon.append("ON pso.cve_id_patron_sujeto_obligado = pgral.cve_id_patron_sujeto_obligado ");
            querySolSujObligByNrpCommon.append("INNER JOIN dic_modalidad MOD ");
            querySolSujObligByNrpCommon.append("ON pso.cve_id_modalidad = MOD.cve_id_modalidad ");
            querySolSujObligByNrpCommon.append("LEFT JOIN dit_persona_fisica pf ");
            querySolSujObligByNrpCommon.append("ON pso.cve_id_persona_fisica = pf.cve_id_persona_fisica ");
            querySolSujObligByNrpCommon.append("LEFT JOIN dit_persona per ");
            querySolSujObligByNrpCommon.append("ON pf.cve_id_persona = per.cve_id_persona ");
            querySolSujObligByNrpCommon.append("LEFT JOIN dit_tramite_persona_fisica tpf ");
            querySolSujObligByNrpCommon.append("ON tpf.cve_id_persona = per.cve_id_persona AND tpf.cve_id_tramite = tram.cve_id_tramite ");
            querySolSujObligByNrpCommon.append("LEFT JOIN dit_persona_moral pm ");
            querySolSujObligByNrpCommon.append("ON pso.cve_id_persona_moral = pm.cve_id_persona_moral ");
            querySolSujObligByNrpCommon.append("LEFT JOIN dit_tramite_persona_moral tpm ");
            querySolSujObligByNrpCommon.append("ON tpm.cve_id_persona_moral = pm.cve_id_persona_moral AND tpm.cve_id_tramite = tram.cve_id_tramite ");
            querySolSujObligByNrpCommon.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));

            if (filtros.getRp().length() >= 8) {
                querySolSujObligByNrpCommon.append("AND pgral.reg_patron = :nrp ");
            }
            if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
                querySolSujObligByNrpCommon.append("AND MOD.num_modalidad = :modalidad ");
            }
            if (filtros.getRp().length() == 11) {
                querySolSujObligByNrpCommon.append("AND pgral.dig_ver = :digVerif ");
            }

            querySolSujObligByNrpCount.append(querySolSujObligByNrpCommon);

            querySolSujObligByNrp.append(querySolSujObligByNrpCommon);
            querySolSujObligByNrp.append("ORDER BY sol.cve_id_solicitud DESC");

            query = this.em.createNativeQuery(querySolSujObligByNrpCount.toString());
            agregarCondicionesQuery(filtros, null, query, modulos);

            if (filtros.getRp().length() >= 8) {
                query.setParameter("nrp", filtros.getRp().substring(0, 8));
            }
            if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
                query.setParameter("modalidad", filtros.getRp().substring(8, 10));
            }
            if (filtros.getRp().length() == 11) {
                query.setParameter("digVerif", filtros.getRp().substring(filtros.getRp().length() - 1));
            }

            totalRegistros = ((BigDecimal) query.getSingleResult()).intValue();

            this.log.debug("Resultados totales filtros particulares -> " + totalRegistros);

            if (totalRegistros > 0) {
                query = this.em.createNativeQuery(querySolSujObligByNrp.toString());
                if (input != null) {
                    query.setFirstResult(input.getiDisplayStart());
                    query.setMaxResults(input.getiDisplayLength());
                }
                agregarCondicionesQuery(filtros, null, query, modulos);

                if (filtros.getRp().length() >= 8) {
                    query.setParameter("nrp", filtros.getRp().substring(0, 8));
                }
                if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
                    query.setParameter("modalidad", filtros.getRp().substring(8, 10));
                }
                if (filtros.getRp().length() == 11) {
                    query.setParameter("digVerif", filtros.getRp().substring(filtros.getRp().length() - 1));
                }

                resultList = query.getResultList();
            }
        } else {

            StringBuffer querySol = new StringBuffer();
            StringBuffer querySolCount = new StringBuffer();
            querySol.append("SELECT DISTINCT(sol.cve_id_solicitud) ");
            querySolCount.append("SELECT COUNT(DISTINCT(sol.cve_id_solicitud)) ");

            StringBuffer querySolCommon = new StringBuffer();
            querySolCommon.append("FROM dit_solicitud sol ");
            querySolCommon.append("INNER JOIN dit_tramite tram ");
            querySolCommon.append("ON sol.cve_id_solicitud = tram.cve_id_solicitud ");
            if ((filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) || (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0)) {
                querySolCommon.append("LEFT JOIN dic_subdelegacion subdeleg ");
                querySolCommon.append("ON subdeleg.cve_id_subdelegacion = sol.cve_id_subdelegacion ");
            }
            if (agregarFiltroModulo) {
                querySolCommon.append("INNER JOIN dic_tipo_tramite ttram ");
                querySolCommon.append("ON tram.cve_id_tipo_tramite = ttram.cve_id_tipo_tramite ");
                querySolCommon.append("LEFT JOIN dic_modulo_tipo_tramite mttram ");
                querySolCommon.append("ON ttram.cve_id_tipo_tramite = mttram.cve_id_tipo_tramite ");
            }
            querySolCommon.append(generarCondicionesBusquedaSolicitudes(filtros, agregarFiltroModulo, mostrarSolicInternet));

            querySolCount.append(querySolCommon);

            querySol.append(querySolCommon);
            querySol.append("ORDER BY sol.cve_id_solicitud DESC");

            query = this.em.createNativeQuery(querySolCount.toString());
            agregarCondicionesQuery(filtros, null, query, modulos);
            totalRegistros = ((BigDecimal) query.getSingleResult()).intValue();

            this.log.debug("Resultados totales filtros particulares -> " + totalRegistros);

            if (totalRegistros > 0) {
                query = this.em.createNativeQuery(querySol.toString());
                agregarCondicionesQuery(filtros, null, query, modulos);
                if (input != null) {
                    query.setFirstResult(input.getiDisplayStart());
                    query.setMaxResults(input.getiDisplayLength());
                }
                resultList = query.getResultList();
            }
        }

        if (StringUtils.isNotBlank(filtros.getCurp())
                || StringUtils.isNotBlank(filtros.getRfc())
                || StringUtils.isNotBlank(filtros.getNss())) {

            String alias = ") solDetalle ";

            queryResultadosTotal.append(alias);

            querySolDetalle.append(alias);
            querySolDetalle.append("ORDER BY solDetalle.cve_id_solicitud desc");

            query = this.em.createNativeQuery(queryResultadosTotal.toString());
            agregarCondicionesQuery(filtros, null, query, modulos);
            if (StringUtils.isNotBlank(filtros.getCurp())) {
                query.setParameter("curp", filtros.getCurp());
            } else if (StringUtils.isNotBlank(filtros.getNss())) {
                query.setParameter("nss", filtros.getNss());
            } else if (StringUtils.isNotBlank(filtros.getRfc())) {
                query.setParameter("rfc", filtros.getRfc());
            } else if (StringUtils.isNotBlank(filtros.getRp())) {
                log.debug("Filtro RP");
            }
            totalRegistros = ((BigDecimal) query.getSingleResult()).intValue();

            this.log.debug("Resultados totales filtros particulares -> " + totalRegistros);

            if (totalRegistros > 0) {
                query = this.em.createNativeQuery(querySolDetalle.toString());
                if (input != null) {
                    query.setFirstResult(input.getiDisplayStart());
                    query.setMaxResults(input.getiDisplayLength());
                }
                agregarCondicionesQuery(filtros, null, query, modulos);

                if (StringUtils.isNotBlank(filtros.getCurp())) {
                    query.setParameter("curp", filtros.getCurp());
                } else if (StringUtils.isNotBlank(filtros.getNss())) {
                    query.setParameter("nss", filtros.getNss());
                } else if (StringUtils.isNotBlank(filtros.getRfc())) {
                    query.setParameter("rfc", filtros.getRfc());
                } else if (StringUtils.isNotBlank(filtros.getRp())) {
                    log.debug("Filtro RP");
                }
                resultList = query.getResultList();
            }
        }

        if (resultList != null && resultList.size() > 0) {
            if (resultList.get(0) instanceof BigDecimal) {
                cveSolicitudesAux.addAll(resultList);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    cveSolicitudesAux.add(((BigDecimal) ((Object[]) resultList.get(i))[0]).longValue());
                }
            }
        }

        cveSolicitudes = new HashSet<Long>(cveSolicitudesAux);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put(TOTAL_REGISTROS, totalRegistros);
        resultado.put(REGISTROS, cveSolicitudes);

        return resultado;
    }

    private String generarCondicionesBusquedaSolicitudes(
            FiltroSolicitud filtros, boolean agregarFiltroModulos,
            boolean mostrarSolicInternet) {

        StringBuffer condiciones = new StringBuffer();

        condiciones.append("WHERE 1 = 1 ");

        if (agregarFiltroModulos) {
            condiciones.append("AND mttram.cve_id_modulo IN (:cveIdModulos) ");
        }

        if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
            condiciones.append("AND tram.cve_id_tipo_tramite = :idTipoTramite ");
        }

        if (filtros.getIdEstadoSolicitud() != null
                && filtros.getIdEstadoSolicitud() > 0) {
            condiciones.append("AND sol.cve_id_estado_solicitud = :idEdoSolicitud ");
        }

        if (filtros.getFechaInicioPresentacion() != null
                && filtros.getFechaFinPresentacion() != null) {
            if (filtros.getIndFechaPresentacionExacta()) {
                condiciones.append("AND TRUNC(sol.fec_presentacion) BETWEEN TO_DATE(:fechaInicioPresentacion,'yyyy-MM-dd HH24:MI') ");
                condiciones.append("AND TO_DATE(:fechaFinPresentacion,'yyyy-MM-dd HH24:MI') ");
            } else {
                condiciones.append("AND TRUNC(sol.fec_presentacion) BETWEEN TO_DATE(:fechaInicioPresentacion,'yyyy-MM-dd') ");
                condiciones.append("AND TO_DATE(:fechaFinPresentacion,'yyyy-MM-dd') ");
            }
        }

        if (filtros.getFechaInicioConclusion() != null && filtros.getFechaFinConclusion() != null) {

            if (filtros.getIndFechaPresentacionExacta()) {
                condiciones.append("AND TRUNC(sol.fec_conclusion) BETWEEN TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd HH24:MI') ");
                condiciones.append("AND TO_DATE(:fechaFinConclusion,'yyyy-MM-dd HH24:MI') ");
            } else {
                condiciones.append("AND TRUNC(sol.fec_conclusion) BETWEEN TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') ");
                condiciones.append("AND TO_DATE(:fechaFinConclusion,'yyyy-MM-dd') ");
            }
        }

        if (filtros.getIdOrigenSolicitud() != null
                && filtros.getIdOrigenSolicitud() > 0) {
            condiciones.append("AND sol.cve_id_origen_solicitud = :idOrigenSolicitud ");
        }

        if (mostrarSolicInternet
                && (filtros.getIdOrigenSolicitud() != null
                && filtros.getIdOrigenSolicitud().longValue() == -1L
                || filtros.getIdOrigenSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId()))
                && (StringUtils.isNotBlank(filtros.getCurp())
                || StringUtils.isNotBlank(filtros.getRfc())
                || StringUtils.isNotBlank(filtros.getRp())
                || StringUtils.isNotBlank(filtros.getNss()))) {
            boolean condicionPevia = false;
            condiciones.append("AND (");
            if (filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0
                    || filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0) {

                condiciones.append("(");

                if (filtros.getIdDelegacion() != null
                        && filtros.getIdDelegacion() > 0) {
                    condiciones.append("subdeleg.cve_id_delegacion = :idDelegacion ");
                }
                if (filtros.getIdSubdelegacion() != null
                        && filtros.getIdSubdelegacion() > 0) {
                    condiciones.append("AND subdeleg.cve_id_subdelegacion = :idSubdelegacion ");
                }
                condiciones.append(")");
                condicionPevia = true;
            }

            if (condicionPevia) {
                condiciones.append(" OR ");
            }

            condiciones.append("sol.cve_id_origen_solicitud = ");
            condiciones.append(OrigenSolicitudEnum.INTERNET.getId().intValue());

            condiciones.append(") ");
        } else {
            if (filtros.getIdDelegacion() != null
                    && filtros.getIdDelegacion() > 0) {
                condiciones.append("AND subdeleg.cve_id_delegacion = :idDelegacion ");
            }

            if (filtros.getIdSubdelegacion() != null
                    && filtros.getIdSubdelegacion() > 0) {
                condiciones.append("AND subdeleg.cve_id_subdelegacion = :idSubdelegacion ");
            }
        }

        return condiciones.toString();
    }

    private void agregarCondicionesQuery(FiltroSolicitud filtros,
            Set<Long> cveSolicitudes, javax.persistence.Query query,
            List<Modulo> modulos) {

        if (modulos != null && !modulos.isEmpty()) {
            List<Long> idModulos = new ArrayList<Long>();
            for (Modulo modulo : modulos) {
                idModulos.add(modulo.getIdModulo());
            }
            query.setParameter("cveIdModulos", idModulos);
        }

        if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
            query.setParameter("idTipoTramite", filtros.getTramiteId());
        }

        if (filtros.getIdEstadoSolicitud() != null
                && filtros.getIdEstadoSolicitud() > 0) {
            query.setParameter("idEdoSolicitud", filtros.getIdEstadoSolicitud());
        }

        if (filtros.getFechaInicioPresentacion() != null
                && filtros.getFechaFinPresentacion() != null) {
            String truncatedInitDate = null;
            String truncatedFinalDate = null;

            if (filtros.getIndFechaPresentacionExacta()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                truncatedInitDate = sdf.format(filtros.getFechaInicioPresentacion());
                truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                truncatedInitDate = sdf.format(filtros.getFechaInicioPresentacion());
                truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
            }

            query.setParameter("fechaInicioPresentacion", truncatedInitDate);
            query.setParameter("fechaFinPresentacion", truncatedFinalDate);
        }

        if (filtros.getFechaInicioConclusion() != null
                && filtros.getFechaFinConclusion() != null) {
            String truncatedConclusionInitDate = null;
            String truncatedConclusionFinalDate = null;

            if (filtros.getIndFechaPresentacionExacta()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                truncatedConclusionInitDate = sdf.format(filtros.getFechaInicioConclusion());
                truncatedConclusionFinalDate = sdf.format(filtros.getFechaFinConclusion());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                truncatedConclusionInitDate = sdf.format(filtros.getFechaInicioConclusion());
                truncatedConclusionFinalDate = sdf.format(filtros.getFechaFinConclusion());
            }

            query.setParameter("fechaInicioConclusion",
                    truncatedConclusionInitDate);
            query.setParameter("fechaFinConclusion",
                    truncatedConclusionFinalDate);
        }

        if (filtros.getIdOrigenSolicitud() != null
                && filtros.getIdOrigenSolicitud() > 0) {
            query.setParameter("idOrigenSolicitud",
                    filtros.getIdOrigenSolicitud());
        }

        if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
            query.setParameter("idDelegacion", filtros.getIdDelegacion());
        }

        if (filtros.getIdSubdelegacion() != null
                && filtros.getIdSubdelegacion() > 0) {
            query.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
        }
    }

    @Override
    public void agregarPersonaATramite(Long idTramite, Long idPersona)
            throws TramiteNoEncontradoException {

        DitTramite ditTramite = em.find(DitTramite.class, idTramite);

        if (ditTramite == null) {
            throw new TramiteNoEncontradoException(idTramite);
        } else {
            DitTramitePersonaFisica salida = new DitTramitePersonaFisica();

            salida.setId(new DitTramitePersonaFisicaPK());

            salida.getId().setCveIdPersona(idPersona);
            salida.getId().setCveIdTramite(ditTramite.getCveIdTramite());

            //DitTramitePersonaFisica existente = em.find(DitTramitePersonaFisica.class, salida.getId());
            salida.setDitPersona(new DitPersona());
            salida.getDitPersona().setCveIdPersona(idPersona);
            salida.setDitTramite(ditTramite);

            em.merge(salida);

        }

    }

    /**
     * Metodo para obtener las solicitudes de un grupo familiar
     *
     * @param String nss - el nss del grupo familiar
     * @param List<Long> estadosSolicitud- estados de la solicitud a buscas ,
     * puede ser nulo y no se comparara el estado
     * @param Long idIntegrante - el id del integrante del que se quiere buscar
     * las solicitudes, puede ser nulo, en caso de ser nulo se buscaran todas
     * las solicitudes del grupo familiar sin importar las persona afectada
     * @param Long maxResult - El numero de resultados a obtener, puede ir nulo
     * @param Long idOrigenSolicitud - El origen de la solicitud, puede ser
     * nulo, en caso de ser nulo obtendra tanto solicitudes realizadas por
     * internet como en ventanilla
     */
    @Override
    public List<Solicitud> getSolicitudesPorNss(String nss,
            List<Long> estadosSolicitud, Long idIntegrante, Long maxResult, Long idOrigenSolicitud) {

        List<Solicitud> solicitudes = null;

        Criteria querySolicitud = this.getSession().createCriteria(DitSolicitud.class);
        querySolicitud.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        querySolicitud.addOrder(Order.desc("fecSolicitud"));
        Criteria personaInteresada = querySolicitud.createCriteria("ditPersonaInteresadaSols");
        Criteria persona = personaInteresada.createCriteria("ditPersona");
        Criteria asignacionNss = persona.createCriteria("ditAsignacionNsses");
        asignacionNss.add(Restrictions.eq("numNss", nss));

        if (idOrigenSolicitud != null) {
            querySolicitud.createAlias("dicOrigenSolicitud", "origen");
            querySolicitud.add(Restrictions.eq("origen.cveIdOrigenSolicitud", idOrigenSolicitud));
        }

        if (estadosSolicitud != null && !estadosSolicitud.isEmpty()) {
            querySolicitud.createAlias("dicEstadoSolicitud", "estado");
            if (estadosSolicitud.size() == 1) {
                querySolicitud.add(Restrictions.eq("estado.cveIdEstadoSolicitud", estadosSolicitud.get(0)));
            } else {
                querySolicitud.add(Restrictions.in("estado.cveIdEstadoSolicitud", estadosSolicitud));
            }
        }

        Criteria tramites = querySolicitud.createCriteria("ditTramites");
        Criteria queryTipoTramite = tramites.createCriteria("dicTipoTramite");
        queryTipoTramite.createAlias("dicModulos", "modulo");
        queryTipoTramite.add(Restrictions.eq("modulo.cveIdModulo", ModuloEnum.DERECHOHABIENTES.getCodigo().longValue()));

        if (idIntegrante != null) {
            Criteria tramitePersona = tramites.createCriteria("ditTramitePersonaFisica");
            tramitePersona.createAlias("ditPersona", "persona");
            tramitePersona.add(Restrictions.eq("persona.cveIdPersona", idIntegrante));
        }

        if (maxResult != null) {
            querySolicitud.setMaxResults(maxResult.intValue());
        }

        List<DitSolicitud> listDitSolicitudes = querySolicitud.list();

        if (!listDitSolicitudes.isEmpty()) {
            solicitudes = new ArrayList<Solicitud>();
            for (DitSolicitud ditSolicitud : listDitSolicitudes) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModelDatosBase(ditSolicitud);

                if (solicitud.getTramites() == null) {
                    solicitud.setTramites(new ArrayList<Tramite>());
                }

                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.append("select tr.CVE_ID_TRAMITE, ttr.DES_TIPO_TRAMITE, ttr.CVE_ID_TIPO_TRAMITE ");
                sqlQuery.append("from DIT_TRAMITE tr, DIC_TIPO_TRAMITE ttr ");
                sqlQuery.append("where tr.CVE_ID_TIPO_TRAMITE = ttr.CVE_ID_TIPO_TRAMITE ");
                sqlQuery.append("and tr.CVE_ID_SOLICITUD = :idSolicitud");

                Query query = this.getSession().createSQLQuery(sqlQuery.toString());
                query.setParameter("idSolicitud", ditSolicitud.getCveIdSolicitud());

                List<Object[]> entities = query.list();

                for (Object[] entity : entities) {
                    Tramite tramite = new Tramite();
                    tramite.setTramiteId(Long.valueOf(entity[0].toString()));

                    TipoTramite tipoTramite = new TipoTramite();
                    tipoTramite.setIdTipoTramite(Integer.valueOf(entity[2].toString()));
                    tipoTramite.setDescripcion(entity[1].toString().toUpperCase());
                    tramite.setTipoTramite(tipoTramite);

                    solicitud.getTramites().add(tramite);
                }

                solicitudes.add(solicitud);
            }
        }

        return solicitudes;
    }

    @Override
    public List<Solicitud> getSolicitudesPorIdNssTipoYEstadoTramite(
            Long idAsignacionNss, Long idTipoSolicitud, Long idOrigenSolicitud, List<Long> estadosSolicitud, Long registrosAObtener) {

        List<Solicitud> solicitudes = null;

        Criteria querySolicitud = this.getSession().createCriteria(DitSolicitud.class);
        querySolicitud.addOrder(Order.desc("fecSolicitud"));
        Criteria personaInteresada = querySolicitud.createCriteria("ditPersonaInteresadaSols");
        Criteria persona = personaInteresada.createCriteria("ditPersona");
        Criteria asignacionNss = persona.createCriteria("ditAsignacionNsses");
        asignacionNss.add(Restrictions.eq("cveIdAsignacionNss", idAsignacionNss));

        if (estadosSolicitud != null) {
            querySolicitud.createAlias("dicEstadoSolicitud", "estado");
            if (estadosSolicitud.size() == 1) {
                querySolicitud.add(Restrictions.eq("estado.cveIdEstadoSolicitud", estadosSolicitud.get(0)));
            } else {
                querySolicitud.add(Restrictions.in("estado.cveIdEstadoSolicitud", estadosSolicitud));
            }
        }

        if (idTipoSolicitud != null) {
            querySolicitud.createAlias("dicTipoSolicitud", "tipoSolicitud");
            querySolicitud.add(Restrictions.eq("tipoSolicitud.cveIdTipoSolicitud", idTipoSolicitud));

        }

        if (idOrigenSolicitud != null) {
            querySolicitud.createAlias("dicOrigenSolicitud", "origen");
            querySolicitud.add(Restrictions.eq("origen.cveIdOrigenSolicitud", idOrigenSolicitud));
        }

        if (registrosAObtener != null) {
            querySolicitud.setMaxResults(registrosAObtener.intValue());
        }

        List<DitSolicitud> listDitSolicitudes = querySolicitud.list();

        if (!listDitSolicitudes.isEmpty()) {
            solicitudes = new ArrayList<Solicitud>();
            for (DitSolicitud ditSolicitud : listDitSolicitudes) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModel(ditSolicitud, true);

                solicitudes.add(solicitud);
            }
        }

        return solicitudes;
    }

    public List<ObtCifrasSolicitudProceso> obtenerCifrasSolicitudProceso() {
        Query queryCifras = this.getSession().getNamedQuery("DicTipoSolicitud.cifrasSolicitudProceso");
        return (List<ObtCifrasSolicitudProceso>) queryCifras.list();
    }

    @Override
    public List<Solicitud> obtenerSolicitudes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona, TipoPersonaFiscal tipo,
            List<Long> tiposSolicitud, List<Long> estadosSolicitud, Boolean maximoResultados) {

        log.debug(" Obteniendo solicitud activa de la persona :::" + idPersona);

        StringBuffer bfr = new StringBuffer();

        if (tipo.equals(TipoPersonaFiscal.FISICA)) {

            bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
            bfr.append(" join tpf.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpf.id.cveIdPersona = :idPersona ");
            bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud in(:idTipoSolicitud)");
            bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud in(:idEstadoSolicitud) ");

        } else {

            bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
            bfr.append(" join tpm.ditTramite as tramite ");
            bfr.append(" join tramite.ditSolicitud as solicitud ");
            bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona ");
            bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud in(:idTipoSolicitud)");
            bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud in (:idEstadoSolicitud) ");

        }

        //Ordenamiento 
        bfr.append(" order by solicitud.fecSolicitud desc, solicitud.dicEstadoSolicitud desc");

        Query query = this.getSession().createQuery(bfr.toString());
        query.setParameter("idPersona", idPersona);
        query.setParameterList("idTipoSolicitud", tiposSolicitud);
        query.setParameterList("idEstadoSolicitud", estadosSolicitud);

        List<DitSolicitud> listDitSolicitud = null;

        try {

            if (maximoResultados) {
                query.setMaxResults(MAXIMO_SOLICITUDES);
            }

            listDitSolicitud = query.list();

        } catch (NoResultException nre) {
            return new ArrayList<Solicitud>();
        }

        List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
        if (listDitSolicitud != null && !listDitSolicitud.isEmpty()) {
            for (DitSolicitud ditSolicitud : listDitSolicitud) {
                Solicitud solicitud = solicitudConversor.convertirEntityToModel(ditSolicitud, true);
                if (solicitud != null) {
                    listSolicitud.add(solicitud);
                }
            }
        }

        return listSolicitud;
    }

    private Long getCveIdSolicitudFronSequence() {

        Long folioNuevo = null;

        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT SEQ_DITSOLICITUD.NEXTVAL ");
        sqlQuery.append("AS folio FROM DUAL");

        org.hibernate.Query query = this.getSession()
                .createSQLQuery(sqlQuery.toString())
                .addScalar("folio", StandardBasicTypes.LONG);

        try {
            folioNuevo = (Long) query.uniqueResult();
        } catch (HibernateException e) {
            this.log.error("error al calcular el id de la solicitud de la solicutd", e);
            throw e;
        }

        return folioNuevo;
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
    							throws SolicitudNoEncontradaException{
           DitSolicitud ditSolicitud= em.find(DitSolicitud.class, cveIdSolicitud);
        if (ditSolicitud == null) {
            throw new SolicitudNoEncontradaException(cveIdSolicitud);
        } 
        
        PersonaInteresadaSolicitud personaInt = new PersonaInteresadaSolicitud();
        Persona personaIntersada = new Persona();
        personaIntersada.setIdPersona(cveIdPersonaInteresada);
        TipoPerInteresadaSol tipoPer = new TipoPerInteresadaSol();
        tipoPer.setCveTipoInteresadaSol(cveTipoPersonaInteres);
        personaInt.setTipoPersonaInteresadaSol(tipoPer);
        personaInt.setPersona(personaIntersada);
        this.crearPersonaInteresada(personaInt, ditSolicitud);
    	
    }

    @Override
    public void asociarSolicitudSubdelegacion(Long idSolicitud, Long idSubDelegacion) {
        log.debug(" Obteniendo solicitud con id :::" + idSolicitud );        
        DitSolicitud ditSolicitud = em.find(DitSolicitud.class, idSolicitud);
        if(ditSolicitud!=null){
            DicSubdelegacion dicSubdelegacion = em.find(DicSubdelegacion.class,idSubDelegacion);
            if(dicSubdelegacion != null){
                ditSolicitud.setDicSubdelegacion(dicSubdelegacion);
                em.persist(ditSolicitud);
            }
        }
    }

	@Override
	public String getHomoclaveSolicitud(String folioSolicitud) {
		
		return getHomoclaveSolicitud(null, folioSolicitud);
	}

	@Override
	public String getHomoclaveSolicitud(Long idsolicitud) {
		
		return getHomoclaveSolicitud(idsolicitud, null);
	}
	
   
	private String getHomoclaveSolicitud(Long idSolicitud, String folioSolicitud){
		
		Session session = this.getSession();
		String homoclave = null;
		
		String query = "select tipotram.REF_HOMOCLAVE from dit_solicitud sol";
		query += " join dit_tramite tram on sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD";
		query += " inner join dic_tipo_tramite tipotram on tram.cve_id_tipo_tramite = tipotram.cve_id_tipo_tramite where ";
		
		if(idSolicitud != null) {
			query += "sol.CVE_ID_SOLICITUD = " + idSolicitud;
		} else {
			query += "sol.ref_folio = '"+folioSolicitud+"'";
		}
		
		query += " and tipotram.REF_HOMOCLAVE is not null";
		
		SQLQuery queryNSS = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<String> resultado = (List<String>)queryNSS.list();

		if(!resultado.isEmpty()) {

			homoclave = resultado.get(0);
		}
		
		return homoclave;
	}
	
	@Override
    public Solicitud obtenerPorFolioSolicitud(String folioSolicitud) {

        log.info("obtenerPorFolioSolicitud");
        Criteria criteria = this.getSession().createCriteria(DitSolicitud.class);
        criteria.add(Restrictions.eq("refFolio", folioSolicitud));
        DitSolicitud ditSolicitud = (DitSolicitud) criteria.uniqueResult();
		
		return solicitudConversor.convertirEntityToModel(ditSolicitud, false); 
    }
	
	    @SuppressWarnings("unchecked")
    @Override
    public List<Long> encontrarSolicitudesPorPersonaYEstados(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud) {

        String cadenaEstados = null;
        if (!Utilerias.isEmpty(idsEstados)) {
            cadenaEstados = idsEstados.toString();
        }
        log.error("obtener solictudes para la persona con id: " + idPersona + ", estados:" + cadenaEstados
                + " y solicitud:" + idTipoSolicitud);
        List<Long> idsSolicitud = null;
        try {
            StringBuilder sb = new StringBuilder().append("SELECT sol.cveIdSolicitud FROM DitSolicitud sol ");
            sb.append("JOIN sol.ditPersonaInteresadaSols per ");
            sb.append("WHERE sol.dicEstadoSolicitud.cveIdEstadoSolicitud  IN (:idsEstados)") ;
            sb.append("AND per.ditPersona.cveIdPersona = :idPersona ");
            sb.append("AND sol.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
            Query query = this.getSession().createQuery(sb.toString());
            query.setParameter("idPersona", idPersona);
            query.setParameter("idTipoSolicitud", idTipoSolicitud);
            query.setParameterList("idsEstados", idsEstados);
            idsSolicitud = query.list();
        } catch (Exception e) {
            log.error("Error al consultar las solicitudes", e);
        }

        return idsSolicitud;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long obtenerIdPersonaInteresada(Long solicitudId) {

        Long resultado = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT per.ditPersona.cveIdPersona FROM DitPersonaInteresadaSol per ");
        sb.append("WHERE per.ditSolicitud.cveIdSolicitud = :idSolicitud");
        Query query = this.getSession().createQuery(sb.toString());
        query.setParameter("idSolicitud", solicitudId);
        List<Long> personas = query.list();
        if (Utilerias.isNotEmpty(personas)) {
            resultado = personas.get(0);
        }
        return resultado;
    }

    @SuppressWarnings("unchecked")
    @Override 
    public List<Long> obtenerTramitesNoCanceladosPorPersonaYTipo(Long idPersona,
           Long tipoSolicitud) {

        List<Long> resultado ;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.cveIdTramite FROM  DitPersonaInteresadaSol per ");
        sb.append("JOIN per.ditSolicitud sol ");
        sb.append("JOIN sol.ditTramites t ");
        sb.append("WHERE sol.dicEstadoSolicitud.cveIdEstadoSolicitud != :estadoSolicitudCancelada ");
        sb.append("AND per.ditPersona.cveIdPersona = :idPersona ");
        sb.append("AND sol.dicTipoSolicitud.cveIdTipoSolicitud= :idTipoSolicitud");
        Query query = this.getSession().createQuery(sb.toString());
        query.setParameter("estadoSolicitudCancelada",
                Long.valueOf(String.valueOf(EstadoSolicitudEnum.CANCELADA.getCodigo())));
        query.setParameter("idPersona", idPersona);
          query.setParameter("idTipoSolicitud", tipoSolicitud);
        
        resultado = query.list();
        return resultado;
    }

	@Override
    public List<Map<String, String>> buscaSolicitudesHsql(Map<String, String> datosBusqueda, Integer primerResultado,
            Integer resultadosPorPagina) {

        try {
            return (List<Map<String, String>>) lanzaConsulta(datosBusqueda, false, primerResultado,
                    resultadosPorPagina);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<Map<String, String>>();
        }

    }

    @Override
    public Long buscaConteoSolicitudesHsql(Map<String, String> datosBusqueda) {

        try {
            return (Long) lanzaConsulta(datosBusqueda, true, 0, 10);
        } catch (ParseException e) {
            return (long) 0;
        }
    }

    @SuppressWarnings("unchecked")
    private Object lanzaConsulta(Map<String, String> datosBusqueda, boolean conteo, Integer primerResultado,
            Integer resultadosPorPagina) throws ParseException {

        List<Map<String, String>> retorno;

        List<Object> parametros = new ArrayList<Object>();

        queryConteo = "select count(*) ";

        querySql = "  select ";

        String querycampos = "  ditsolicit0_.REF_FOLIO, "
							+ " ditsolicit0_.FEC_SOLICITUD,  "
							+ " dittramite8_.FEC_REGISTRO_ACTUALIZADO,"
                + " dicestadot9_.DES_ESTADO_TRAMITE, " 
				+ " dictiposol4_.DES_TIPO_SOLICITUD, "
				+ " ditpersona3_.NOM_NOMBRE, "
                + " ditpersona3_.NOM_PRIMER_APELLIDO, "
				+ " ditpersona3_.NOM_SEGUNDO_APELLIDO, " 
				+ " ditpersona3_.CURP, "
				+ " ditasignac5_.NUM_NSS,"
                + " dicsubdele6_.CLAVE_SUBDELEGACION, " 
				+ " dicsubdele6_.DES_SUBDELEGACION,  " 
				+ " dicdelegac7_.CLAVE_DELEGACION,"
                + " dicdelegac7_.DES_DELEG, "
				+ " dicestadot9_.CVE_ID_ESTADO_TRAMITE "; 

        querySqlSpec = " from "
        +     " DIT_SOLICITUD ditsolicit0_ "
        + "inner join "
        +     "DIC_ESTADO_SOLICITUD dicestados1_ "
        +     "    on ditsolicit0_.CVE_ID_ESTADO_SOLICITUD=dicestados1_.CVE_ID_ESTADO_SOLICITUD "
        + "inner join "
        +     "DIT_PERSONA_INTERESADA_SOL ditpersona2_  "
        +     "    on ditsolicit0_.CVE_ID_SOLICITUD=ditpersona2_.CVE_ID_SOLICITUD  "
        + "inner join "
        +     "DIT_PERSONA ditpersona3_  "
        +      "   on ditpersona2_.CVE_ID_PERSONA=ditpersona3_.CVE_ID_PERSONA  "
        + "inner join "
        +     "DIC_TIPO_SOLICITUD dictiposol4_  "
        +     "    on ditsolicit0_.CVE_ID_TIPO_SOLICITUD=dictiposol4_.CVE_ID_TIPO_SOLICITUD  "
        + "inner join "
        +     "DIT_ASIGNACION_NSS ditasignac5_  "
        +     "    on ditpersona3_.CVE_ID_PERSONA=ditasignac5_.CVE_ID_PERSONA  "
        +     "    and ( "
        +     "        ditasignac5_.FEC_REGISTRO_BAJA is null "
        +     "    )   "
        + "inner join "
        +    "DIC_SUBDELEGACION dicsubdele6_  "
        +    "    on ditsolicit0_.CVE_ID_SUBDELEGACION=dicsubdele6_.CVE_ID_SUBDELEGACION  "
        + "inner join "
        +    "DIC_DELEGACION dicdelegac7_  "
        +    "    on dicsubdele6_.CVE_ID_DELEGACION=dicdelegac7_.CVE_ID_DELEGACION  "
        + "inner join "
        +    "DIT_TRAMITE dittramite8_  "
        +    "    on ditsolicit0_.CVE_ID_SOLICITUD=dittramite8_.CVE_ID_SOLICITUD  "
        + "inner join "
        +    "DIC_ESTADO_TRAMITE dicestadot9_  "
        +    "    on dittramite8_.CVE_ID_ESTADO_TRAMITE=dicestadot9_.CVE_ID_ESTADO_TRAMITE  "
        + "INNER JOIN SCT_COMPLEMENTO_SOL sct  "
        +	"ON sct.CVE_ID_SOLICITUD=ditsolicit0_.CVE_ID_SOLICITUD  "
				+ " where ditasignac5_.cve_id_asignacion_nss=sct.CVE_ID_ASIGNACION_NSS ";

        armaQuerys(datosBusqueda, querycampos, parametros);

        //Query queryCount = this.getSession().createQuery(queryConteo);
        //Query query = this.getSession().createQuery(querySql);

		SQLQuery queryCount = this.getSession().createSQLQuery(queryConteo);
		SQLQuery query = this.getSession().createSQLQuery(querySql);
		
		
		
        int indice = 0;
		BigDecimal bg1;
        for (Object obj : parametros) {
            query.setParameter(indice, obj);
            queryCount.setParameter(indice, obj);
            indice++;
        }

        if (conteo) {
			bg1 = (BigDecimal) queryCount.uniqueResult();
            return  bg1.longValue();
        }

        if (primerResultado > 1) {
            query.setFirstResult(primerResultado != 0 ? primerResultado : 1);
        }

        query.setMaxResults(resultadosPorPagina != 0 ? resultadosPorPagina : 10);

        List<Object[]> rows = query.list();

        retorno = armaMovimiento(rows);

        return retorno;
    }

    private void armaQuerys(Map<String, String> datosBusqueda, String querycampos, List<Object> parametros)
            throws ParseException {

        filtraTipoCertificacion(parametros, datosBusqueda);
        filtraCamposObligatorios(parametros, datosBusqueda);
        filtraFechas(parametros, datosBusqueda);

        if (!"-1".equals(datosBusqueda.get("tipoEstatus"))) {
            querySqlSpec += " AND dicestadot9_.CVE_ID_ESTADO_TRAMITE = ?";
            parametros.add(datosBusqueda.get("tipoEstatus"));

        }else{
			 querySqlSpec += " AND dicestadot9_.CVE_ID_ESTADO_TRAMITE <> 7 ";
		}

        if (!"-1".equals(datosBusqueda.get("comboEntidades"))) {
            querySqlSpec += " AND dicdelegac7_.CLAVE_DELEGACION = ?  ";
            parametros.add(datosBusqueda.get("comboEntidades"));
        }
        if (datosBusqueda.get("comboSubdelegacion") != null && !"-1".equals(datosBusqueda.get("comboSubdelegacion"))
                && !"".equals(datosBusqueda.get("comboSubdelegacion"))) {
            querySqlSpec += " AND dicsubdele6_.CLAVE_SUBDELEGACION = ?  ";
            parametros.add(datosBusqueda.get("comboSubdelegacion"));
        }

        querySql += querycampos;
        querySql += querySqlSpec;

        defineOrden(datosBusqueda);

        queryConteo += querySqlSpec;
    }

    private void filtraFechas(List<Object> parametros, Map<String, String> datosBusqueda) throws ParseException {

        if (!"".equals(datosBusqueda.get("fechaSolicitud"))) {
            querySqlSpec += " AND ditsolicit0_.FEC_SOLICITUD >= ?  ";
            parametros.add(formatter.parse(datosBusqueda.get("fechaSolicitud")));
            querySqlSpec += " AND ditsolicit0_.FEC_SOLICITUD < ?  ";
            Date maxDate = new Date(
                    formatter.parse(datosBusqueda.get("fechaSolicitud")).getTime() + TimeUnit.DAYS.toMillis(1));
            parametros.add(maxDate);
        }
        if (!"".equals(datosBusqueda.get("fechaInicial"))) {
            querySqlSpec += " AND ditsolicit0_.FEC_SOLICITUD >= ?  ";
            parametros.add(formatter.parse(datosBusqueda.get("fechaInicial")));

            querySqlSpec += " AND ditsolicit0_.FEC_SOLICITUD < ?  ";
            Date maxDate = new Date(
                    formatter.parse(datosBusqueda.get("fechaFinal")).getTime() + TimeUnit.DAYS.toMillis(1));
            parametros.add(maxDate);
        }

    }

    private void filtraTipoCertificacion(List<Object> parametros, Map<String, String> datosBusqueda) {

        if (!"-1".equals(datosBusqueda.get("tipoCertificacion"))) {
            querySqlSpec += " AND dictiposol4_.CVE_ID_TIPO_SOLICITUD=? ";
            parametros.add(datosBusqueda.get("tipoCertificacion"));

        } else {
            querySqlSpec += " AND (dictiposol4_.CVE_ID_TIPO_SOLICITUD=55 OR dictiposol4_.CVE_ID_TIPO_SOLICITUD=56) ";
        }
    }

    private void defineOrden(Map<String, String> datosBusqueda) {

        if ("1".equals(datosBusqueda.get("orden"))) {
            querySql += " order by ditsolicit0_.FEC_SOLICITUD asc";
        } else {
            querySql += " order by ditsolicit0_.FEC_SOLICITUD desc";
        }
    }

    private void filtraCamposObligatorios(List<Object> parametros, Map<String, String> datosBusqueda) {

        if (!"".equals(datosBusqueda.get("folio"))) {
            querySqlSpec += " AND ditsolicit0_.REF_FOLIO = ?  ";
            parametros.add(datosBusqueda.get("folio"));
        }
        if (!"".equals(datosBusqueda.get("nss"))) {
            querySqlSpec += " AND ditasignac5_.NUM_NSS = ?  ";
            parametros.add(datosBusqueda.get("nss"));
        }
        if (!"".equals(datosBusqueda.get("curp"))) {
            querySqlSpec += " AND ditpersona3_.CURP = ?  ";
            parametros.add(datosBusqueda.get("curp"));
        }
    }

    private List<Map<String, String>> armaMovimiento(List<Object[]> rows) throws ParseException {

        List<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
        for (Object[] object : rows) {

            Map<String, String> sol = new HashMap<String, String>();
            sol.put("folio", String.valueOf(object[0]));
            sol.put("fechaSolicitud", formatter.format(formatter2.parse(String.valueOf(object[1]))));

            sol.put("fechaEstatus", formatter.format(formatter3.parse(String.valueOf(object[2]))));
            sol.put("estatus", String.valueOf(object[3]));
            sol.put("tipoCertificacion", String.valueOf(object[4]));

            sol.put("nombreCompleto", (object[5]!=null?object[5]:"") + " " + (object[6]!=null?object[6]:"") + " " + (object[7]!=null?object[7]:""));
            sol.put("curp", String.valueOf(object[8]));
            sol.put("nss", String.valueOf(object[9]));
            sol.put("subdelegacion", String.valueOf(object[11]));
            sol.put("delegacion", String.valueOf(object[13]));
            retorno.add(sol);
        }
        return retorno;
    }

	@SuppressWarnings("unchecked")
    @Override
    public List<Long> encontrarSolicitudesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud) {

        String cadenaEstados = null;
        if (!Utilerias.isEmpty(idsEstados)) {
            cadenaEstados = idsEstados.toString();
        }
        log.error("obtener solictudes para la persona con id: " + idPersona + ", estados de tramite:" + cadenaEstados
                + " y solicitud:" + idTipoSolicitud);
        List<Long> idsSolicitud = null;
        try {
            StringBuilder sb = new StringBuilder().append("SELECT sol.cveIdSolicitud FROM DitSolicitud sol ");
            sb.append("JOIN sol.ditPersonaInteresadaSols per ");
			sb.append("JOIN sol.ditTramites trams ");
            sb.append("WHERE per.ditPersona.cveIdPersona = :idPersona ");
            sb.append("AND sol.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud ");
			sb.append("AND trams.dicEstadoTramite.cveIdEstadoTramite IN(:idsEstados)");
            Query query = this.getSession().createQuery(sb.toString());
            query.setParameter("idPersona", idPersona);
            query.setParameter("idTipoSolicitud", idTipoSolicitud);
            query.setParameterList("idsEstados", idsEstados);
            idsSolicitud = query.list();
        } catch (Exception e) {
            log.error("Error al consultar las solicitudes", e);
        }

        return idsSolicitud;
    }
	
	
	@SuppressWarnings("unchecked")
    @Override
    public List<Long> encontrarTramitesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstados) {

        String cadenaEstados = null;
        if (!Utilerias.isEmpty(idsEstados)) {
            cadenaEstados = idsEstados.toString();
        }
        log.error("obtener tramites para la persona con id: " + idPersona + ", estados de tramite:" + cadenaEstados);
        List<Long> idsTramite = null;
        try {
            StringBuilder sb = new StringBuilder().append("SELECT trams.cveIdTramite FROM DitSolicitud sol ");
            sb.append("JOIN sol.ditPersonaInteresadaSols per ");
            sb.append("JOIN sol.ditTramites trams ");
            sb.append("WHERE per.ditPersona.cveIdPersona = :idPersona ");
            sb.append("AND trams.dicEstadoTramite.cveIdEstadoTramite IN(:idsEstados)");
            Query query = this.getSession().createQuery(sb.toString());
            query.setParameter("idPersona", idPersona);
            query.setParameterList("idsEstados", idsEstados);
            idsTramite = query.list();
        } catch (Exception e) {
            log.error("Error al consultar los tramites", e);
        }

        return idsTramite;
    }
	
	public void actualizarTipoTramite(String Tramite, String idSolicitud, String TipoNSS)
	 {
		String queryTipoTramite = "UPDATE DIT_CORRECCION_DATOS_ASEG SET CVE_ID_TIPO_REGULACION = "+Integer.valueOf(TipoNSS)+
		" WHERE CVE_ID_tramite="+Integer.valueOf(Tramite);
         
        this.em.createNativeQuery(queryTipoTramite).executeUpdate();
	 }
	    
	  public  void actualizarTipoNSS(String nss, String TipoNSS){
	   StringBuffer sqlQuery = new StringBuffer();
	 
        sqlQuery.append("select count(*) from DIT_CORRECCION_DATOS_ASEG a, DIT_DETALLE_NSS c  WHERE c.CVE_ID_CORRECCION_DATOS_ASEG=a.CVE_ID_CORRECCION_DATOS_ASEG and c.CVE_NSS="+nss+"");

        javax.persistence.Query query = this.em.createNativeQuery(sqlQuery.toString());
       
        BigDecimal count = (BigDecimal) query.getSingleResult();

        if (count.intValue() > 0) 
		  {
  			     String queryTipoNSS = "update DIT_DETALLE_NSS  set CVE_ID_TIPO_CERTIFICACION="+TipoNSS+" where CVE_NSS="+nss;         
			
        			this.em.createNativeQuery(queryTipoNSS).executeUpdate();
      	  }
	  		
	    }
	  
	    @Override
	    public Solicitud actualizarTramitesMarcaOSB(final Solicitud solicitud)
	            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
	        Solicitud solicitudUpdated = solicitud; // NOPMD
            log.error("Ini busqueda de solicitud x id: " + solicitud.getSolicitudId());
            final DitSolicitud ditSolicitud = em.find(DitSolicitud.class,
                    solicitud.getSolicitudId());
            if (ditSolicitud == null) {
                throw new SolicitudNoEncontradaException(
                        solicitud.getSolicitudId());
            } else {
                actualizarTramitesSolicitudMarcaOSB(solicitud, ditSolicitud);
            }
	        return solicitudUpdated;
	    }

	    private void actualizarTramitesSolicitudMarcaOSB(final Solicitud solicitud, final DitSolicitud ditSolicitud)
	            throws TramiteNoEncontradoException {
	        final List<Tramite> tramites = solicitud.getTramites();
	        log.debug("::: La solicitud " + solicitud.getNoFolioSolicitud()
	                + " cuenta con " + tramites.size() + " tramites a actualizar");
	        if (tramites != null && !tramites.isEmpty()) {
	            for (Tramite tramite : tramites) {
	                log.error("tramite.getTramiteId(): " + tramite.getTramiteId());
	                actualizarTramiteExistenteMarcaOSB(solicitud, tramite);
	                log.error("termina actualizarTramiteExistente: " + tramite.getTramiteId());
	            }
	        }
	    }

		@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	    private void actualizarTramiteExistenteMarcaOSB(final Solicitud solicitud,
	            final Tramite tramite) throws TramiteNoEncontradoException {
	        this.log.debug("::: Se va a actualizar el tramite -> "
	                + tramite.getTramiteId());
	        final DitTramite ditTramite = em.find(DitTramite.class,
	                tramite.getTramiteId());
	        if (ditTramite == null) {
	            throw new TramiteNoEncontradoException(tramite.getTramiteId());
	        } else {
	            if (ditTramite.getDitSolicitud().getCveIdSolicitud() != solicitud
	                    .getSolicitudId().longValue()) {
	                log.error("Se est\u00E1 tratando actualizar un tr\u00E1mite que no corresponde a esta solicitud! No se realiz\u00F3 la actualizaci\u00F3n.");
	                return;
	            }
	            this.log.debug("::: Se inicia la actualizacion del tramite -> "
	                    + tramite.getTramiteId());
	            	            
	            ditTramite.setFecRegistroActualizado(new Date());
	            ditTramite.setFecConclusion(tramite.getFechaConclusion());
	            ditTramite.setFecEfecto(tramite.getFechaEfecto());
	            ditTramite.setFecPresentacion(tramite.getFechaPresentacion());
	            ditTramite.setIndRatificado(tramite.getIndRatificado());
	            if (tramite.getFechaTramite() != null
	                    && !tramite.getFechaTramite().equals(
	                            ditTramite.getFecTramite())) {
	                ditTramite.setFecTramite(tramite.getFechaTramite());
	            }

	            if (tramite.getObservacion() != null
	                    && !tramite.getObservacion().equals(
	                            ditTramite.getRefObservacion())) {
	                ditTramite.setRefObservacion(tramite.getObservacion());
	            }

	            final DicEstadoTramite dicEstadoTramiteActual = actualizarEstadoTramite(
	                    tramite.getEstadoTramite(),
	                    ditTramite.getDicEstadoTramite());
	            if (dicEstadoTramiteActual != null) {
	                ditTramite.setDicEstadoTramite(dicEstadoTramiteActual);
	                if (dicEstadoTramiteActual.getCveIdEstadoTramite()
	                        .intValue() == EstadoTramiteEnum.CANCELADO.getCodigo().intValue()) {
	                    ditTramite.setFecRegistroBaja(new Date());
	                }
	            }

	            final DicTipoTramite dicTipoTramiteActual = actualizarTipoTramite(
	                    tramite.getTipoTramite(), ditTramite.getDicTipoTramite());
	            if (dicTipoTramiteActual != null) {
	                ditTramite.setDicTipoTramite(dicTipoTramiteActual);
	            }

	            final DicRazonResultado dicRazonResultadoNuevo = actualizarRazonResultado(
	                    tramite.getRazonResultado(),
	                    ditTramite.getDicRazonResultado());
	            if (dicRazonResultadoNuevo != null) {
	                ditTramite.setDicRazonResultado(dicRazonResultadoNuevo);
	            }

	            if (!(tramite instanceof TramiteSujetoObligado)) {
	                generarRelacionTramitePersona(tramite, ditTramite);
	            }

	            /*
				 * Detail is updated using the new objects' graph: note que esta
				 * seccion del codigo borrara el estado anterior de la grafica de
				 * estados codificada en el XML
	             */
	            log.debug("::: Se va a actualizar el detalle del tramite -> "
	                    + tramite.getTramiteId());

	            final DitDetalleTramite ditDetalleTramite = ditTramite
	                    .getDitDetalleTramite();
	            try {
	                Tramite xmlActual = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(ditDetalleTramite.getRefDatosTramiteXml());

	                log.debug("::: El tramite " + tramite.getTramiteId()
	                        + " tiene documentos probatorios: "
	                        + xmlActual.getDocumentosProbatorios());

	                if (xmlActual.getDocumentosProbatorios() != null && tramite.getDocumentosProbatorios() == null) {
	                    tramite.setDocumentosProbatorios(xmlActual.getDocumentosProbatorios());
	                }
	            } catch (ClassCastException e) {
	                log.warn("EL tramite no es del tipo esperado, esto es por compatibilidad de modelos", e);
	            }

	            if (StringUtils.isNotBlank(tramite.getDetalleTramiteXml())) {
	                log.debug("::: Se va actualiza el xml proporcionado previamente para el tramite -> " + tramite.getTramiteId());
	                ditDetalleTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
	                log.debug("::: Se actualizo el xml proporcionado previamente para el tramite -> " + tramite.getTramiteId());
	            } else {
	                log.debug("::: Se va a transformar el obj tramite para guardarlo como detalle con id -> "
	                        + tramite.getTramiteId());
	                ditDetalleTramite.setRefDatosTramiteXml(mx.gob.imss.distss.digital.jaxb.util.JaxbUtil
	                        .objectToXml(tramite));
	                log.debug("::: Se transformo el obj tramite para guardarlo como detalle con id -> "
	                        + tramite.getTramiteId());
	            }

	            em.persist(ditTramite);
	            em.flush();

	            //this.generarBitacoraSeguimiento(tramite, ditTramite,solicitud.getSolicitante());
	            this.log.debug("::: Se finaliza la actualizacion del tramite -> "
	                    + tramite.getTramiteId());
	        }
	    }

		@Override
		public CitaSolicitud guardaCitaSolicitud(CitaSolicitud cita) throws SolicitudException {
			log.debug("llegue a guardar la cita " + cita.getFechaHora());
			try {
				DitCitaSolicitud citaBd = new  DitCitaSolicitud();
				citaBd.setFecCita(cita.getFechaHora());
				citaBd.setFecRegistroAlta(new Date());
				DicSubdelegacion subdelBd = new DicSubdelegacion();
				subdelBd.setCveIdSubdelegacion(cita.getSubdelegacion().getId());
				citaBd.setDicSubdelegacion(subdelBd);
				DitSolicitud solBd = new DitSolicitud();
				solBd.setCveIdSolicitud(cita.getCveIdSolicitud());
				citaBd.setDitSolicitud(solBd);
				citaBd.setRefFolioCita(cita.getRefFolioCita());
				citaBd.setIndCitaActiva(true);
				citaBd.setNumContadorCambioCita(0);
				em.persist(citaBd);
				em.flush();
				cita.setIdCitaSolicitud(citaBd.getCveIdCitaSolicitud());
				cita.setIndCitaActiva(citaBd.getIndCitaActiva());
				cita.setNumContadorCambioCita(citaBd.getNumContadorCambioCita());
				return cita;
			}catch (Exception e) {
				log.error("courrio un error al actualziar la cita " ,e);
				throw new SolicitudException(e.getMessage());
			}
			
		}

		@Override
		public CitaSolicitud actualizaCitaSolicitud(CitaSolicitud cita) throws SolicitudException {
			log.debug("llegue a actualizar la cita " + cita.getFechaHora());
			try {
				DitCitaSolicitud citaBd = em.find(DitCitaSolicitud.class, cita.getIdCitaSolicitud());
				citaBd.setFecCita(cita.getFechaHora());
				citaBd.setFecRegistroActualizado(new Date());
				citaBd.setIndCitaActiva(cita.getIndCitaActiva());
				DicSubdelegacion subdelBd = citaBd.getDicSubdelegacion();
				subdelBd.setCveIdSubdelegacion(cita.getSubdelegacion().getId());
				citaBd.setNumContadorCambioCita(citaBd.getNumContadorCambioCita()+1);
				em.merge(citaBd);
				em.flush();
				cita.setNumContadorCambioCita(citaBd.getNumContadorCambioCita());
				return cita;
			}catch (Exception e) {
				log.error("courrio un error al actualziar la cita " ,e);
				throw new SolicitudException(e.getMessage());
			}
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public CitaSolicitud calculaFechaCita(CitaSolicitud cita) throws SolicitudException {
			log.debug("llegando al metodo para calcular la fecha de cita");
	
			StringBuffer strQuery = new StringBuffer();	
			strQuery.append(" select  MIN(diaSiguiente) as diaSiguiente from ( ");
			strQuery.append(" SELECT  TRUNC(SYSDATE)  + rownum AS diaSiguiente ");
			strQuery.append(" FROM all_objects ");
			strQuery.append(" where TRUNC(SYSDATE)  + rownum <= sysdate + :numDiasMaxCita ) ");
			strQuery.append(" WHERE to_char(diaSiguiente,\'DY\',\'NLS_DATE_LANGUAGE=ENGLISH\') NOT IN (\'SAT\',\'SUN\')  ");
			strQuery.append(" and diaSiguiente not in (  ");
			strQuery.append(" select trunc(s.FEC_CITA) ");
			strQuery.append(" from dit_cita_solicitud s  ");
			strQuery.append(" where trunc(s.FEC_CITA) BETWEEN trunc(sysdate +1)  and trunc(sysdate + :numDiasMaxCita )  ");
			strQuery.append(" and s.CVE_ID_SUBDELEGACION = :cveIdSubdelegacion  ");
			strQuery.append(" group by trunc(s.FEC_CITA)  ");
			strQuery.append(" having count(trunc(s.FEC_CITA))= :numMaxCitasPorDia  ");
			strQuery.append(" union  ");
			strQuery.append(" select trunc(fes.fec_dia_festivo)   ");
			strQuery.append(" from DIC_DIAS_FESTIVOS fes  ");
			strQuery.append(" where to_char(fes.fec_dia_festivo, 'yyyy')  = to_Char(sysdate, 'yyyy'))  ");
			log.debug("el query a ejecutar para calculo de fecha es : " + strQuery.toString());
			try {
				SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
				sqlQuery.setLong("numDiasMaxCita", cita.getNumDiasMaximaCita().longValue());
				sqlQuery.setLong("cveIdSubdelegacion", cita.getSubdelegacion().getId());
				sqlQuery.setLong("numMaxCitasPorDia", cita.getNumMaximoCitas().longValue());
				List<Object> lstResultado = (List<Object>) sqlQuery.list();
				if(lstResultado.isEmpty()) {
					log.error("No se pudo calcular la fecha de cita " );
					throw new SolicitudException("No se pudo calcular la fecha de cita ");
				}else {
					Date fechaCita = (Date)lstResultado.get(0);
					cita.setFechaHora(fechaCita);
					return cita;
				}
				
			}catch(SolicitudException e) {
				throw e;
			}catch(Exception e) {
				log.error("OCurrio un error al valiar la fecha de cita" ,e);
				throw new SolicitudException(e.getMessage());
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean validaFechaCita(CitaSolicitud cita) throws SolicitudException {
			log.debug("llegando a validar la fecha de cita");
			StringBuffer strQuery = new StringBuffer();	
			strQuery.append(" select count(*) as citas "); 
			strQuery.append(" from DIT_CITA_SOLICITUD cita ");
			strQuery.append(" where trunc(cita.fec_cita) = :fecCita ");
			strQuery.append(" and cita.IND_CITA_ACTIVA = 1 ");
			strQuery.append(" and cita.CVE_ID_SUBDELEGACION = :cveIdSubdelegacion");
			strQuery.append(" group by  trunc(cita.fec_cita) ");
			log.debug("el querya de valida cita es: " + strQuery.toString());
			try {
				SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
				sqlQuery.setDate("fecCita", cita.getFechaHora());
				sqlQuery.setLong("cveIdSubdelegacion", cita.getSubdelegacion().getId());
				List<Object> lstResultado = (List<Object>) sqlQuery.list();
				if(lstResultado.isEmpty()) {
					log.debug("la consulta no encontro registros para la fecha " + cita.getRefFolioCita());
					return true;
				}else {
					Long numCitas = ((BigDecimal)lstResultado.get(0)).longValue();
					if(numCitas < cita.getNumMaximoCitas())
						return true;
					else 
						return false;
				}
			}catch(Exception e) {
				log.error("OCurrio un error al valiar la fecha de cita" ,e);
				throw new SolicitudException(e.getMessage());
			}
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public CitaSolicitud consultaCItaSOlicitud(CitaSolicitud cita) throws SolicitudException {
			log.debug("llegue a consultar la cita");
			try {
				if(cita.getIdCitaSolicitud()!= null) {
					DitCitaSolicitud citaBd = em.find(DitCitaSolicitud.class, cita.getIdCitaSolicitud());
					return solicitudConversor.parserCitaEntityToModel(citaBd);
				}else if(!StringUtils.isEmpty(cita.getRefFolioCita())) {
					Criteria queryCita = this.getSession().createCriteria(DitCitaSolicitud.class);
					queryCita.add(Restrictions.eq("refFolioCita", cita.getRefFolioCita()));
					List<DitCitaSolicitud> lstCita = queryCita.list();
					if(lstCita != null && lstCita.size()>0) 
						return solicitudConversor.parserCitaEntityToModel(lstCita.get(0));
					else 
						throw new SolicitudException("No se encontro cita con el folio " + cita.getRefFolioCita());
				}else if(cita.getCveIdSolicitud() != null) {
					Criteria queryCita = this.getSession().createCriteria(DitCitaSolicitud.class);
					queryCita.createAlias("ditSolicitud", "solicitud");
					queryCita.add(Restrictions.eq("solicitud.cveIdSolicitud", cita.getCveIdSolicitud()));
					List<DitCitaSolicitud> lstCita = queryCita.list();
					if(lstCita != null && lstCita.size()>0) 
						return solicitudConversor.parserCitaEntityToModel(lstCita.get(0));
					else 
							throw new SolicitudException("No se encontro cita con el folio " + cita.getRefFolioCita());
				}	
			}catch (SolicitudException e) {
				throw e;
			}catch (Exception e) {
				log.error("error al consulta ra cita" ,e);
				throw new SolicitudException(e.getMessage());
			}
			
			
			return null;
		}	    
		
}
