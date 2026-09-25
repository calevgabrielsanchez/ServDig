/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.seguro.PatronPlataformasDigitales;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

import javax.ejb.Remote;

/**
 * @author NOVUTECK1
 *
 */
@Remote
public interface ValidaVigenciaRemote {

	/**
	 * Valida si un trabajador es valido para contratar un seguro ivro y si
	 * alica o no cuestionario
	 * 
	 * @param vigenciaTrabajador os datos de vigencia del trabajador
	 * @return respuesta del trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaTrabajador(VigenciaTrabajdor vigenciaTrabajador);
  
  RespuestaValidacionTrabajador validaVigenciaTrabajadorRenovacion(VigenciaTrabajdor vigenciaTrabajador);
  
  RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(VigenciaTrabajdor vigenciaTrabajador);

	/**
	 * Valida si un trabajador es valido para continuar un seguro ivro y si
	 * aplica o no cuestionario
	 *
	 * @param vigenciaTrabajador los datos de vigencia del trabajador
	 * @return respuesta del trabajador
	 */
	RespuestaValidacionTrabajador validaContinuarVigenciaTrabajador(VigenciaTrabajdor vigenciaTrabajador);

	/**
	 * Valida si un trabajador es valido para contratar un seguro familiar y si
	 * alica o no cuestionario
	 * 
	 * @param vigenciaTrabajador os datos de vigencia del trabajador
	 * @return respuesta del trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaSeguroFamiliar(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);
  
  RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);

	/**
	 * Valida si un trabajador es valido para realizar una continuacion
	 * voluntaria y si alica o no cuestionario
	 * 
	 * @param vigenciaTrabajador os datos de vigencia del trabajador
	 * @return respuesta del trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria(VigenciaContVoluntaria vigenciaContVoluntaria);
  
	
	RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(VigenciaContVoluntaria vigenciaContVoluntaria);
  

    /**
     * Revisa si la vigencia recibida que es de una persona con seguro modalidad 40
     * no ha adquirido alguna modalidad de regimen obligatorio recientemente
     * en caso de que si se cancela el seguro y se regresa los movimientos
     * para notificar
     *
	 * @param seguro
	 */
    Boolean revisaCancelaSeguroCambiadoMod40(VigenciaContVoluntaria vigenciaContVoluntaria);
    
    
    /**
     * Se realiza la funcion para la consulta de todos los patrones que se encuentren en Plataformas Digitales
     * @return
     */
    List<PatronPlataformasDigitales> consultaPatronesPlataformasDigitales();
}
