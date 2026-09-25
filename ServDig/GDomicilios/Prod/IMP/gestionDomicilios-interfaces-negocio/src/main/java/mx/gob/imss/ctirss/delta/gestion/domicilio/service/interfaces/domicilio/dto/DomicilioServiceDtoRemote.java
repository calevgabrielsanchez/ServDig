/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.dto;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.domicilio.Domicilio;
/**
 * Servicio para la consulta de domicilios a partir de un modelo plano 
 * @author NOVUTECK1
 *
 */
@Remote
public interface DomicilioServiceDtoRemote {

    /**
     * VAlida y regresa que una persona contenga domicilio particular
     * @param idPersona identificador de la paersona a cosultar su domicilio
     * @return el domicilio encontrado
     */
    Domicilio consultarDomicilio(Long idPersona);
    
    String getAreaGeograficaDeMunicipioIMSSbyEstadoMunCP(String cveEnt, String cveMun, String codigoPostal);

    String getZonaSalarialByIdSeguro(Long idSeguro);

}
