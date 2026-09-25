/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

/**
 *
 * @author jponte
 */
@Local
public interface EjbTipoCombustibleDaoLocal {

	void altaTipoCombustible(long iIdTipoCombustible, String sDesTipoCombustible);
	
}
