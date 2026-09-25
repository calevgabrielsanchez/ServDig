package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.serviciosDigitales;

import javax.ejb.Local;

@Local
public interface IServiciosDigiatlesEntityLocal {
	
	/**Metodo default por re acomodo de servicios
	 * 
	 * @param test
	 * @throws Exception
	 */
	void getMockTRest(String test) throws Exception;
	
	

}
