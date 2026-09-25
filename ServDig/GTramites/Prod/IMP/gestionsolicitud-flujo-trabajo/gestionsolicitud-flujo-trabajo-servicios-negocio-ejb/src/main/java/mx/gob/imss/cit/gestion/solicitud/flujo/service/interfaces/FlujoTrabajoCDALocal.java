package mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces;

/**
 * 
 * @author Alex Peña Softtek
 * 
 */

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoTarea;


@Local
public interface FlujoTrabajoCDALocal {
	
	
	 /**
     * Metodo para buscar una tarea de usuario por Curp e instancia
     */
    List <TareaUsuario> buscarTareaUsuarioByCurpInst(final Long idInstancia, String Curp);
       
   
    EstadoTarea obtenerEstadoTarea(final Long idEstadoTarea);
    
}
