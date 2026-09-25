package mx.gob.imss.cit.cda.service.autorizar.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.autorizar.entity.AutorizarSolicitudLocal;
import mx.gob.imss.cit.cda.service.autorizar.utility.AutorizarSolicitudUtilityLocal;
import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.interfaces.AutorizarSolicitudRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityRemote;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "autorizarSolicitudBusiness", mappedName = "autorizarSolicitudBusiness")
public class AutorizarSolicitudBusiness extends OperacionesSolicitudBusiness implements AutorizarSolicitudRemote{
    
    @EJB
    private AutorizarSolicitudUtilityLocal autorizarSolicitudUtility;
    
    @EJB
    private AutorizarSolicitudLocal autorizarSolicitudEntity;
    
    @EJB(name = "serviceBusiness", mappedName = "serviceBusiness")
    private ServiceBusinessRemote serviceBusiness;
    
    @EJB(name = "localizarPersonaFisicaEnRENAPOServiceBusiness", mappedName = "localizarPersonaFisicaEnRENAPOServiceBusiness")
    private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
    
    @EJB(mappedName = "afectarDatosPersonaUtility")
    private AfectarDatosPersonaUtilityRemote afectarDatosPersonaUtility;
    
    @EJB(mappedName = "movimiento06CorreccionAsegurado")
    private Movimiento06CorreccionBusinessRemote movimiento06Correccion;
    
    private static final String ORIGEN_APLICACION_CDA = "CDA";
    
   
    @Override
    public void enviaCertificacionSINDO(Solicitud solicitud, Map<String, String> tramitesTareas,
            String usuario) throws SolicitudNoValidaException,
            SolicitudNoEncontradaException, TramiteNoEncontradoException,
            CURPNoLocalizadoEnEntidadExternaException,
            ClienteWebserviceRenapoCurpException,
            ErrorValidacionDatosConsultaEnEntidaExternaException,
            NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException {
        
        getSolicitudBusiness().actualizarEstados(solicitud);

        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            
            tramiteCda.setListaNSS( new ArrayList<String>() );
            for( CorreccionNSS correccionNSS : tramiteCda.getListaNssCorreccion() ){
              tramiteCda.getListaNSS().add( correccionNSS.getNss() );
            }
            
            // Se esta utilizando la personaRENAPO para guardar los datos capturados por el solicitante
            if(tramiteCda.getPersonaRENAPO()!=null){
                
                Fisica fisicaTramite = tramiteCda.getPersonaRENAPO();
                
                // Se consultan los antecedentes para guardarlos en base de datos en el XML de como se encontraba la persona en los origenes
                List<Fisica> fisicasHistoricas = getServiceBusiness().getAseguradoByNSSLegadosyBDTU(tramiteCda.getListaNSS().get(0), true);
                
                fisicaTramite.setNss(tramiteCda.getListaNSS().get(0));
                
                // Se busca la persona de renapo para recuperar el acta si es que cuenta con una
                Fisica fisicaActualizar = null;
                if (fisicaTramite.getCurp() != null && StringUtils.isNotBlank(fisicaTramite.getCurp())) {
                    fisicaActualizar = getLocalizarPersonaFisicaEnRENAPOServiceBusiness().localizarPersonaFisicaEnRENAPOxCURP(fisicaTramite.getCurp());
                } else {
                    fisicaActualizar = fisicaTramite;
                }
                
                // Se setea la informacion del tramite e historicos
                fisicaActualizar.setMediosContacto(fisicaTramite.getMediosContacto());
                fisicaActualizar.setNss(tramiteCda.getListaNSS().get(0));

                // No guarda el domicilio de la persona
                fisicaActualizar.setDomicilios(fisicaTramite.getDomicilios());
                
                tramiteCda.setPersonaRENAPO(fisicaActualizar);

                // Eliminar caracteres especiales de las personas a persistir en el detalle tramite
                tramite.setPersonas(getAutorizarSolicitudUtility().normalizarFisicasHistoricas(fisicasHistoricas));
            }
            
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            
            Iterator<Map.Entry<String, String>> entries = tramitesTareas.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                if (tramiteCda.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
                    getLog().error("Tarea {} del tramite {} ",entry.getValue(), entry.getKey());
                    // Asignar tarea a autorizador si esta como BPMADMIN
                    getFlujoTrabajoRemote().actualizarBDocInstancia(Long.parseLong(entry.getValue()),getAutorizarSolicitudUtility().crearMensajeTarea(usuario), usuario);
                }
            }
        }
    }
    
    @Deprecated
    public void enviarSindo(String folio , Fisica fisicaActualizar){
        MovCorreccionesDatosAseguradoType movCorrecion = getAfectarDatosPersonaUtility().
                generarMovimientoActualizacionAseguradoSINDO(fisicaActualizar,folio, ORIGEN_APLICACION_CDA);
        getLog().debug("Movimiento 06 generado -> " + movCorrecion);

        // Comentar esta linea en desarrollo para evitar el intento de encolar los mensajes.
        getMovimiento06Correccion().encolarMovimiento06CorrecconAsegurado(movCorrecion);
    }

    public Movimiento06CorreccionBusinessRemote getMovimiento06Correccion() {
        return movimiento06Correccion;
    }

    public AfectarDatosPersonaUtilityRemote getAfectarDatosPersonaUtility() {
        return afectarDatosPersonaUtility;
    }


    public AutorizarSolicitudUtilityLocal getAutorizarSolicitudUtility() {
        return autorizarSolicitudUtility;
    }


    public AutorizarSolicitudLocal getAutorizarSolicitudEntity() {
        return autorizarSolicitudEntity;
    }

    public ServiceBusinessRemote getServiceBusiness() {
        return serviceBusiness;
    }

    public LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote getLocalizarPersonaFisicaEnRENAPOServiceBusiness() {
        return localizarPersonaFisicaEnRENAPOServiceBusiness;
    }

}
