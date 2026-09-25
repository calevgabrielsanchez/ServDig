/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;


/**
 * 
 * @author jponte
 */
@Stateless(name = "ejbTipoCombustibleDao", mappedName = "ejbTipoCombustibleDao")
public class EjbTipoCombustibleDao implements EjbTipoCombustibleDaoLocal {

	@Override
	public void altaTipoCombustible(final long iIdTipoCombustible,
			final String sDesTipoCombustible) {
	}

}
