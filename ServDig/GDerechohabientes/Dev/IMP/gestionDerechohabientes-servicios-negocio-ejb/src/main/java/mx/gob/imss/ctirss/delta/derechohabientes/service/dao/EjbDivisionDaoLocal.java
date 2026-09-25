/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;

/**
 * 
 * @author jponte
 */
@Local
public interface EjbDivisionDaoLocal {

	public void altaDivision(final Division datosDivision);

}
