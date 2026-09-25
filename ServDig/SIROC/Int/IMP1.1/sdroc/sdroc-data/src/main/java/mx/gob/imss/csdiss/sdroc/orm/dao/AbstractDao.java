/**
 * Copyright (c) 2012 Itzat Solutions. All Rights Reserved
 */
package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;

/**
 * Interface contains access methods to metadata
 * 
 * @author Brian Hernández García.
 * @version 1.0.0
 */
public interface AbstractDao<Entity, ID extends Serializable>  {
	     
    public ID save(Entity entity);
    
    public void merge(Entity entity);
 
    public void delete(Entity entity);
	
    public List<Entity> findMany(Query query);
 
    public Entity findOne(Query query);
 
    public List<Entity> findAll();
 
    public Entity findByID(Long id);
 
}
