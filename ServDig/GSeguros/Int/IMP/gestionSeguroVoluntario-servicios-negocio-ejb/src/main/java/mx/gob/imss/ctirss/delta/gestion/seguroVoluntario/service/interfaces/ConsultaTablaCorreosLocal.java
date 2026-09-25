/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;
import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.persistence.DitBitCorreosSivro;

/**
 * Servicio para la consulta de parametros de ivro
 * @author NOVUTECK1
 *
 */
@Local
public interface ConsultaTablaCorreosLocal {
    
    /**
     * Obtiene la lista de ID Seguro IVRO con estatus 1 (enviar correo)
     * 
     * @return la lista de ID Seguro IVRO con estatus 1 (enviar correo)
     */
    List<DitBitCorreosSivro> getSeguroIVROEstatusEnvio() throws IvroException;

    void actualizaSeguroIVROPorCveSeguro(Long cveIdBitCorreosSivro, Integer estatus, String Descripcion) throws IvroException;
}
