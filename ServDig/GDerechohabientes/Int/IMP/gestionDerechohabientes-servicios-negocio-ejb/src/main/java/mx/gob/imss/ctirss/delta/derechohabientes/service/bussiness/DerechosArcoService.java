package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.DerechosArcoEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechosArcoServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.GenericDerechohabientesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteDerechosArco;
import mx.gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceResponsablesSubdelegacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless(name = "derechosArcoService", mappedName = "derechosArcoService")
public class DerechosArcoService extends AbstractServiceBusiness implements DerechosArcoServiceRemote {

    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusinessRemote;

    @EJB(name = "serviceBusiness", mappedName = "serviceBusiness")
    private ServiceBusinessRemote serviceBusinessRemote;

    @EJB
    private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;

    @EJB
    private CatalogosDaoLocal catalogosDaoLocal;

    @EJB
    private DocumentosServiceLocal documentosServiceLocal;

    @EJB
    private DerechosArcoEntityLocal derechosArcoEntityLocal;

    @Override
    public byte [] bloquearDerechosArco(String nss, Usuario usuario, String motivos) throws Exception {

        AsignacionNSS asegurado = serviceBusinessRemote.obtenerAseguradoPorNss(nss);

        ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();
        usuario.setFisica(clienteWebserviceResponsablesSubdelegacion.recuperaUsuarioEsquemaSeguridadByCURP(usuario.getCveIdUsuario()).getFisica());
        Solicitud solicitud = this.generarSolicitud(asegurado, TipoTramiteEnum.SOLICITUD_BLOQUEO_DERECHOS_ARCO, usuario, motivos);

        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoEntityLocal.consultarAseguradoBloqueado(asegurado.getIdAsignacionNSS(), mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());

        if (bloqueoDerechosArco == null) {
            bloqueoDerechosArco = new BloqueoDerechosArco();
            bloqueoDerechosArco.setIdAsignacionNss(asegurado.getIdAsignacionNSS());
            bloqueoDerechosArco.setMotivos(motivos);
            bloqueoDerechosArco.setIdUsuario(usuario.getUsuario());
            bloqueoDerechosArco.setIndBloqueo(true);
            bloqueoDerechosArco.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());
            bloqueoDerechosArco.setFecRegistroAlta(new Date());
            if (usuario.getCveIdSubdelegacion() != null) {
                setDelegacionSubdelegacion(usuario.getUsuarioFuncionario(), bloqueoDerechosArco);
            }
            derechosArcoEntityLocal.bloquear(bloqueoDerechosArco);
        } else {
            bloqueoDerechosArco.setMotivos(motivos);
            bloqueoDerechosArco.setIndBloqueo(true);
            bloqueoDerechosArco.setIdUsuario(usuario.getUsuario());
            if (usuario.getCveIdSubdelegacion() != null) {
                setDelegacionSubdelegacion(usuario.getUsuarioFuncionario(), bloqueoDerechosArco);
            }
            derechosArcoEntityLocal.actualizar(bloqueoDerechosArco);
        }

        Derechohabiente derechohabiente = new Derechohabiente();
        derechohabiente.setAsignacionNSS(asegurado);
        solicitud.getTramites().get(0).setPersona(derechohabiente);
        return (byte[]) documentosServiceLocal.generarComprobanteTramiteARCO(solicitud);

    }

    @Override
    public byte [] desbloquearDerechosArco(String nss, Usuario usuario, String motivos) throws Exception {

        AsignacionNSS asegurado = serviceBusinessRemote.obtenerAseguradoPorNss(nss);

        ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();
        usuario.setFisica(clienteWebserviceResponsablesSubdelegacion.recuperaUsuarioEsquemaSeguridadByCURP(usuario.getCveIdUsuario()).getFisica());
        Solicitud solicitud = this.generarSolicitud(asegurado, TipoTramiteEnum.SOLICITUD_DESBLOQUEO_DERECHOS_ARCO, usuario, motivos);

        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoEntityLocal.consultarAseguradoBloqueado(asegurado.getIdAsignacionNSS(), mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());

        if (bloqueoDerechosArco != null) {
            if (bloqueoDerechosArco.isIndBloqueo()) {
                bloqueoDerechosArco.setMotivos(motivos);
                bloqueoDerechosArco.setIndBloqueo(false);
                bloqueoDerechosArco.setIdUsuario(usuario.getUsuario());
                if (usuario.getCveIdSubdelegacion() != null) {
                    setDelegacionSubdelegacion(usuario.getUsuarioFuncionario(), bloqueoDerechosArco);
                }
                derechosArcoEntityLocal.actualizar(bloqueoDerechosArco);
            } else {
                throw new Exception("El tramite ya se encuentra desbloqueado");
            }
        } else {
            throw new Exception("No se encontro ningun registro de bloqueo para el asegurado");
        }
        
        Derechohabiente derechohabiente = new Derechohabiente();
        derechohabiente.setAsignacionNSS(asegurado);
        solicitud.getTramites().get(0).setPersona(derechohabiente);
        return (byte[]) documentosServiceLocal.generarComprobanteTramiteARCO(solicitud);
    }

    private void setDelegacionSubdelegacion(UsuarioFuncionario usuarioFuncionario, BloqueoDerechosArco bloqueoDerechosArco){

        bloqueoDerechosArco.setIdDelegacion(usuarioFuncionario != null ? usuarioFuncionario.getDelegacion().getId() : null);
        bloqueoDerechosArco.setIdSubdelegacion(usuarioFuncionario != null ? usuarioFuncionario.getSubdelegacion().getId() : null);
    }

    private Solicitud generarSolicitud(AsignacionNSS asegurado, TipoTramiteEnum tipoTramiteEnum, Usuario usuario, String observacion) {

        Solicitud solicitudCreada = null;

        try {
            Date fechaActual = new Date();
            Solicitud solicitud = new Solicitud();
            solicitud.setFechaSolicitud(fechaActual);
            solicitud.setEstadoSolicitud(new EstadoSolicitud());
            solicitud.setFechaPresentacion(fechaActual);
            solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
            solicitud.setTipoSolicitud(new TipoSolicitud());
            solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.SOLICITUD_DERECHOS_ARCO.getId());
            solicitud.setOrigenSolicitud(new OrigenSolicitud());
            solicitud.getOrigenSolicitud().setIdOrigenSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
            solicitud.setFechaConclusion(new Date());
            solicitud.setSolicitante(usuario);
            solicitud.setObservacion(observacion);
            PersonaInteresadaSolicitud personaIntSol = new PersonaInteresadaSolicitud();
            Fisica fisica = new Fisica();
            fisica.setIdPersona(asegurado.getIdPersona());
            personaIntSol.setPersona(fisica);
            TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
            tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
            personaIntSol.setTipoPersonaInteresadaSol(tipoPersona);
            solicitud.setPersonaInteresadaSolicitud(personaIntSol);
            if (usuario.getCveIdSubdelegacion() != null) {
                Subdelegacion sub = new Subdelegacion();
                sub.setId(usuario.getCveIdSubdelegacion());
                solicitud.setSubdelegacion(sub);
            }

            TipoTramite tipoTramite = catalogosDaoLocal.getTipoTramite(tipoTramiteEnum.getCodigo().longValue());
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());

            TramiteDerechosArco tramite = new TramiteDerechosArco();
            tramite.setObservacion(observacion);
            tramite.setIndRatificado(false);
            tramite.setMotivo(observacion);
            tramite.setPersona(asegurado);

            solicitud = solicitudBusinessRemote.asociarTramiteSolicitud(solicitud, tramite, estadoTramite, tipoTramite);

            solicitudCreada = solicitudBusinessRemote.crear(solicitud);
            solicitudCreada.setTramites(solicitud.getTramites());
            solicitudCreada.setSolicitante(usuario);

            log.info("Creando firma digital para la solicitud de derechos arco");
            FirmaElectronica firma = null;
            try {
                firma = tramiteDocumentosServiceLocal.generaFirmaElectronica(asegurado, solicitudCreada, tipoTramite.getDescripcion());
            } catch(Exception e) {
                log.error("ocurrio un error al generar la firma digital relacionada a la solicitud");
            }

            if(firma != null) {
                solicitudCreada.setCadenaOriginal(firma.getCadenaOriginal());
                solicitudCreada.setSecuenciaDeNotaria(firma.getSecuenciaNotaria());
                solicitudCreada.setSelloDigital(firma.getRecibo());
                solicitudCreada.setNumeroSerieCertificado(firma.getSerialCertificado());
                solicitudCreada.setFirmaElectronica(firma);
            }


            log.debug("Se genero la solicitud con id: " + solicitudCreada.getSolicitudId() + " folio: " + solicitudCreada.getNoFolioSolicitud());
        } catch (Exception e) {
            log.error("Error al crear la solicitud:", e);
        }
        return solicitudCreada;

    }

    @Override
    public BloqueoDerechosArco consultaBloqueo(String nss, Long idTipoTramite) {

        try {
            AsignacionNSS asegurado = serviceBusinessRemote.obtenerAseguradoPorNss(nss);
            return derechosArcoEntityLocal.consultarAseguradoBloqueado(asegurado.getIdAsignacionNSS(), idTipoTramite);
        } catch (GenericDerechohabientesException e) {
            log.error("No se encontro informacion: " + e);
            return null;
        }
    }

}
