package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RotAvisoObra;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface AvisoObraDao extends AbstractDao<RotAvisoObra, Long> {

	List<RotAvisoObra> findByRfc(String cveRfc);
	/**
	 * findByCveRegistroAvisoObra
	 * @param cveRegistroAvisoObra
	 * @param rfc
	 * @param registroPatronal
	 * @return
	 */
	RotAvisoObra findByCveRegistroAvisoObra(String cveRegistroAvisoObra);
	RotAvisoObra findByCveRegistroAvisoObra(String cveRegistroAvisoObra, String rfc, String registroPatronal);
	
	/**
	 * Actualiza el status de la obra a cerrado
	 * @param cveAvisoObra
	 */
	void updateEstatusAvisoObra(Long cveAvisoObra);
	/**
	 * getCveAvisoObra 
	 * @return sequence
	 */
	String getCveAvisoObra();
}
