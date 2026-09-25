package mx.gob.imss.cit.cda.service.bandeja.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.bandeja.entity.ConsultaBandejaLocal;
import mx.gob.imss.cit.cda.service.interfaces.ConsultaBandejaRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para módulo de Consulta de la solicitud de CDA
 * 
 * @author STK
 * 
 */
@Stateless(name = "consultaBandejaBusiness", mappedName = "consultaBandejaBusiness")
public class ConsultaBandejaBusiness implements ConsultaBandejaRemote {

    private final Logger logger = LoggerFactory.getLogger(ConsultaBandejaBusiness.class);

    @EJB
    private ConsultaBandejaLocal consultaBandejaEntity;

    public String obtenerCurpPorFolio(String folio) {
        getLogger().debug("Consulta CURP por folio de solicitud {}", folio);
        String curp = getConsultaBandejaEntity().obtenerCurpPorFolio(folio);
        if (curp != null) {
            return curp;
        }
        return "N/D";
    }
    
    public String obtenerTipoTramite(Integer idTramite){
    	 String tipoTramite = getConsultaBandejaEntity().obtenerTipoTramite(idTramite.longValue());
    	 return tipoTramite;
    }

    public DataPage obtenerTareasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso) {


        System.out.println("[*] ConsultaBandejaBusiness: Tareas normales");
        dataPage = getConsultaBandejaEntity().consultaTareasCDA(dataPage, usuario, subdelegacion, idProceso);

        //LOGGERBPM.info("Se obtuvieron {} datos", dataPage.getData() != null ? dataPage.getData().size() : 0);
        //dataPage = prepararRespuestaBandejas(dataPage);
        return dataPage;
    }

    public DataPage obtenerTareasHistCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso) {


        System.out.println("[*] ConsultaBandejaBusiness: Historico");
        dataPage = getConsultaBandejaEntity().consultaTareasHistCDA(dataPage, usuario, subdelegacion, idProceso);

        //LOGGERBPM.info("Se obtuvieron {} datos", dataPage.getData() != null ? dataPage.getData().size() : 0);
        //dataPage = prepararRespuestaBandejas(dataPage);
        return dataPage;
    }

   


    public ConsultaBandejaLocal getConsultaBandejaEntity() {
        return consultaBandejaEntity;
    }

    public Logger getLogger() {
        return logger;
    }

}
