/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 *
 * @author gibrann
 */
public class SubDelegacionNoLocalizadaException extends AbstractException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3729226650951987323L;

	/**
	 * 
	 */


	private static final Integer CODIGO = 0;
	
	private static final String SITUACION = "Su Subdelegacion no ha sido localizada";
	
	
	public SubDelegacionNoLocalizadaException(){
		super(SITUACION , CODIGO);
	}
    
}
