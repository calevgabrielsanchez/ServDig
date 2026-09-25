package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.web.cuentaindividual.vo.CuentaIndividual;
import mx.gob.imss.cit.cda.web.cuentaindividual.vo.CuentaIndividualAsegurado;
import mx.gob.imss.cit.cda.web.cuentaindividual.vo.NssCuentaIndividual;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadCuentaIndividualUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCuentaIndividualUtils.class);
    
    @Autowired
    private ServiceBusinessRemote serviceBusiness;
    
   
    public CuentaIndividual crearEncabezadoRP(CuentaIndividualVO bandeja, List<String> listaNss) {
        CuentaIndividual cuenta = new CuentaIndividual();

        cuenta.setRegistroPatronal(bandeja.getRegistroPatronal());
        cuenta.setClaveCiz(bandeja.getClaveCiz());
        cuenta.setClaveDelegacionOrigen(bandeja.getClaveDelegacionOrigen());
        cuenta.setNombreDelegacionOrigen(bandeja.getNombreDelegacionOrigen());
        cuenta.setNssDestino(bandeja.getNss());
        cuenta.setListaNss(listaNss);
        cuenta.setNombreRP(bandeja.getNombreRP());
        cuenta.setNumeroRP(bandeja.getRegistroPatronal());

        return cuenta;

    }
    
   

    /**
     * Verifica si la lista de periodos contiene el registro patronal
     *
     * @param list lista de periodos sin agrupar
     * @param rp registro patronal
     * @return true en caso de que si lo contenga
     */
    private boolean contieneRP(List<CuentaIndividual> list, String rp) {
        boolean contieneRP = false;
        if (!list.isEmpty()) {
            for (CuentaIndividual cuenta : list) {
                if (cuenta.getRegistroPatronal().equals(rp)) {
                    contieneRP = true;
                    break;
                }
            }
        }
        return contieneRP;
    }


    
    
    public void crearCuentaIndividualModificacion(CuentaIndividualAsegurado request){
        
        List<NssCuentaIndividual> registrosPatronalesCertificador = request.getRegistrosPatronalesCertificador();
        List<NssCuentaIndividual> registrosPatronalesAsociados = request.getRegistrosPatronalesAsociados();
        List<NssCuentaIndividual> registrosPatronalesNoPertenece = request.getRegistrosPatronalesNoPertenece();
        
        if(registrosPatronalesAsociados!=null && registrosPatronalesNoPertenece !=null)
        {
            LOGGER.debug("registrosPatronalesAsociados size: " + registrosPatronalesAsociados.size());
            LOGGER.debug("registrosPatronalesNoPertenece size: " + registrosPatronalesNoPertenece.size());
        }
        
        
        //Cuando el “NSS Origen” y el “NSS Destino” asociados a un periodo, son diferentes, 
        //dicho periodo se debe marcar como Eliminado (E) del “NSS Origen” e Incluido (I) en el “NSS Destino”.
        
        for (NssCuentaIndividual nssCuentaIndividual : registrosPatronalesCertificador){
                    
             nssCuentaIndividual.getNss();
            
            
        }
        
        
        
        //Cuando el “NSS Origen” y el “NSS Destino” asociados a un periodo son iguales y 
        //no existen cambios en los datos del periodo, el sistema los marca sin cambios.
        
                
        
    }

    public List<String> obtenerListaNss(List<Tramite> listaTramites) {
        List<String> listaNss = new ArrayList<String>();

        for (Tramite tramite : listaTramites) {
            TramiteCorreccionCurp tcda = (TramiteCorreccionCurp) tramite;
            listaNss.add(tcda.getListaNSS().get(0));
        }
        return listaNss;
    }

    public ServiceBusinessRemote getServiceBusiness() {
        return serviceBusiness;
    }

    
}
