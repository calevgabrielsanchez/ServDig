/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

import java.util.List;

import javax.ejb.Local;

/**
 * Validacion de vigencias trabajador
 * 
 * @author NOVUTECK1
 *
 */
@Local
public interface ValidaVigenciaLocal {

	/**
	 * valida la vigencia de un trabajador y si aplica cuestionario
	 * 
	 * @param vigenciaTrabajador datos de la vigencia del trabajador encontrado en almacen
	 * @return respuesta de la validacion de vigencias de un trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaTrabajador(VigenciaTrabajdor vigenciaTrabajador);
  RespuestaValidacionTrabajador validaVigenciaTrabajadorRenovacion(VigenciaTrabajdor vigenciaTrabajador);
  
  RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(VigenciaTrabajdor vigenciaTrabajador);
	/**
	 * valida si continuar la vigencia de un trabajador y si aplica cuestionario
	 *
	 * @param vigenciaTrabajador datos de la vigencia del trabajador encontrado en almacen
	 * @return respuesta de la continuidad de vigencia de un trabajador
	 */
	RespuestaValidacionTrabajador validaContinuarVigenciaTrabajador(VigenciaTrabajdor vigenciaTrabajador);

	/**
	 * valida la vigencia de un trabajador y si aplica cuestionario (modalidad
	 * 33)
	 * 
	 * @param vigenciaTrabajador datos de la vigencia del trabajador encontrado en almacen
	 * @return respuesta de la validacion de vigencias de un trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaSeguroFamiliar(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);
  RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);
	/**
	 * valida la vigencia de un trabajador y si aplica cuestionario (modalidad
	 * 40)
	 * 
	 * @param vigenciaTrabajador datos de la vigencia del trabajador encontrado en almacen
	 * @return respuesta de la validacion de vigencias de un trabajador
	 */
	RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria(VigenciaContVoluntaria vigenciaContVoluntaria);
  RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(VigenciaContVoluntaria vigenciaContVoluntaria);
  RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(List<PptPatronPlataforma> listPatronesPlataformasDig, VigenciaContVoluntaria vigenciaContVoluntaria);
  
	/**
	 * valida la vigencia de un trabajador de modalidad 40 si es necesario darlo de baja por que se haya agregado
	 * recientemente al régimen obligatorio (modalidad 40)
	 *
	 * @param VigenciaContVoluntaria datos de la vigencia del trabajador encontrado en almacen
	 * @return Boolean si ya cuenta con régimen obligatorio agregado
	 */
    Boolean revisaCancelaSeguroCambiadoMod40(VigenciaContVoluntaria vigenciaContVoluntaria);
}
