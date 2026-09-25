package mx.gob.imss.cit.cda.web.common.helper;

import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.common.utils.ReadTramitesCommonUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.cit.cda.service.interfaces.ConsultaBandejaRemote;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ReadTramitesAsignadosCommonHelper {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
   
    @Autowired
    private PersonaBusinessRemote personaBusiness;  
    
    @Autowired
    @Qualifier("flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;
    

    @Autowired
    private ConsultaBandejaRemote consultaBandejaBusiness ;


    @Autowired
    private ReadTramitesCommonUtils readTramitesCommonUtils;
    
    protected abstract DataPage obtenerTareas(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent,DataPage dataPage, Long idProceso);
    
    public DataPage realizarBusqueda(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent, DataPage dataPage){
    
        return  obtenerTareas(requestReadEvent,dataPage,ProcesosNegocioEnum.CDA.getId());
    }
    
    public String getCurp(String curp){
        if(StringUtils.isNotBlank(curp)){ 
            return verificarCurpHistorica(curp);
        }
        return curp;
    }
    
    
    private String verificarCurpHistorica(String curpFiltro){
        String curp = null;
        
        try {
            Fisica personaRenapo = getPersonaBusiness().buscarPersonaFisicaPorCurpEnRenapo(curpFiltro);
            
            if (personaRenapo != null) {
                curp = personaRenapo.getCurp();
            }
            
        } catch (ClienteWebserviceRenapoCurpException e) {
            getLogger().error("Error Consulta Renapo para Curp Historica Filtro",e);       
        }
        
        return curp;
    }
    
    public PersonaBusinessRemote getPersonaBusiness() {
        return personaBusiness;
    }

    public FlujoTrabajoRemote getFlujoTrabajoBusiness() {
        return flujoTrabajoBusiness;
    }

    public ConsultaBandejaRemote getConsultaBandejaBusiness() {
        getLogger().info("[*] getConsultaBandejaBusiness ");       
        return consultaBandejaBusiness;
    }


    public ReadTramitesCommonUtils getReadTramitesCommonUtils() {
        return readTramitesCommonUtils;
    }

    public Logger getLogger() {
        return logger;
    }

}
