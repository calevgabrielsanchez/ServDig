package mx.imss.estrados.service.ejb.dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface GenericDAO<T> {
	
	/**
     * Recupera todos los objetos del tipo <code>&lt;T&gt;</code> existentes 
     * en el repositorio de datos.
     * @return
     */
    List<T> getAll(Class<T> persistentClass);
    
    List<T> getByQuery(String query);
	
    T saveOrUpdate(T model);
    
    T eliminar(T model);
}
