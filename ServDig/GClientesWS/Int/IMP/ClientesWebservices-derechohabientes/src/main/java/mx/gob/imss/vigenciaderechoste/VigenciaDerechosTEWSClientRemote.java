package mx.gob.imss.vigenciaderechoste;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosTEDTO;

import javax.ejb.Remote;
import java.util.AbstractMap;

@Remote
public interface VigenciaDerechosTEWSClientRemote {

    /**
     * Constancia de vigencia de grupo familiar por nss
     *
     * @param nss
     * @return
     */
    ComprobanteVigenciaDerechosTEDTO getInfo(String nss) throws DerechohabientesWebSserviceException;

    /**
     * Constancia de vigencia de grupo familiar por nss
     *
     * @param nss
     * @param cpId
     * @return
     */
    ComprobanteVigenciaDerechosTEDTO getInfo(String nss, String cpId) throws DerechohabientesWebSserviceException;

    GrupoFamiliarTE getInfoAsegurado(String nss) throws DerechohabientesWebSserviceException;

    String getAgregadoMedico(String nss, Long idPersona) throws DerechohabientesWebSserviceException;

    AbstractMap.SimpleEntry<Integer, String> validarConsistencia(String nss);

}
