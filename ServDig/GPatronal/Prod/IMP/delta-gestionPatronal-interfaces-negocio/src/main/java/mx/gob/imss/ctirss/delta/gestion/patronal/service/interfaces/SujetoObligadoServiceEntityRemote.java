package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * 
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jorge Ventura Hernandez Almazan
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoServiceEntityRemote.java
 *  @Paquete:  mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces
 *  
 */
@Remote
public interface SujetoObligadoServiceEntityRemote {

	
	/**
	 * Obtiene todos los sujetos obligados a los cuales la 
	 * persona con el identificador proporcionado representa
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param cveIdPersona
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalRemote(Long cveIdPersona);
	

}
