package mx.gob.imss.cit.gestion.solicitud.flujo.service.entity;

/**
 * 
 * @author Alex Peña Softtek
 * 
 */

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.EstadoTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoCDALocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "flujoTrabajoCDAEntity", mappedName = "flujoTrabajoCDAEntity")
public class FlujoTrabajoCDAEntity implements FlujoTrabajoCDALocal {

	private static final Logger LOGGERBPM = LoggerFactory.getLogger(FlujoTrabajoCDAEntity.class);
	
	 /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    protected EntityManager entityManager;
	
	 /**
     * Metodo para buscar una tarea de usuario por Curp e instancia
     */
    @SuppressWarnings("unchecked")
	public List<TareaUsuario> buscarTareaUsuarioByCurpInst(final Long idInstancia, String Curp) {
        
    	LOGGERBPM.info("buscar tarea usuario por instancia y curp instancia: " + idInstancia + "curp: " + Curp );
    	
    	 StringBuffer sql = new StringBuffer();
         
         sql.append("SELECT t FROM TareaUsuario t ");
         sql.append("WHERE t.instancia = :idInstancia ");
         sql.append("and t.usuario = :CurpUsuario ");
         sql.append("order by t.idTareaUsuario desc  ");
         Query query = entityManager.createQuery(sql.toString());
         query.setParameter("idInstancia", idInstancia);
         query.setParameter("CurpUsuario", Curp);
         
         return query.getResultList();
         
         }

	@Override
	public EstadoTarea obtenerEstadoTarea(Long idEstadoTarea) {
		
		return entityManager.find(EstadoTarea.class, idEstadoTarea);
		
	}


	
}
