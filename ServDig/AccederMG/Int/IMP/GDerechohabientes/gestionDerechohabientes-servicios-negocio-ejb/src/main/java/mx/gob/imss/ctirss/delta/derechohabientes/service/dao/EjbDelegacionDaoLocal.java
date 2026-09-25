/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;

/**
 * 
 * @author jponte
 */
@Local
public interface EjbDelegacionDaoLocal {

	public void altaDelegacion(Delegacion delegacionDatos);

}
