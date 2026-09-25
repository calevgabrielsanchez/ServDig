package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AseguradoPension;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;


/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 25/04/2012
 */
@Local
public interface PatronDaoLocal {

	/**
	 * Utilizado para obtener el asegurado del Nss AsignacionNss
	 * @param idAsignacionNss
	 * @return Asegurado - idAsegurado
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	public List<Asegurado> getAseguradoList(long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	public Long getIdAsegurado(long idAsignacionNss, long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	public Asegurado getAsegurado(long idAsignacionNss, long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Utilizado para obtener informacion para saber si es un patron IMSS
	 * @param idAsignacionNss
	 * @return Objeto PatronSujetoObligado
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	public SujetoObligado getPatronSujeto(long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception;
	
	
	/**
	 * Utilizado para recuperar un numero de registro patronal
	 * @param idPatronSujetoObligado
	 * @return Registro Patronal
	 * @throws Exception 
	 */
	public String getRegistroPatronal(long idPatronSujetoObligado) throws Exception;
	
	public String getRegistroPatronalSinDV(long idPatronSujetoObligado) throws Exception;
	
	/**
	 * Utilizado para obtener la modalidad
	 * @param idModalidad
	 * @return NumModalidad
	 * @throws Exception 
	 */
	public String getModalidad(long idModalidad) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Utilizado para obtener un derechohabiente pensionado
	 * @param idAsegurado
	 * @return AseguradoPension
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	public AseguradoPension getPensionado(long idAsegurado) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Consulta que valida si un patron se encuentra en la tabla de patrones de instituciones educativas
	 * @param cveNRP
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	boolean isRegistroPatronalnstitucionEducativa(String cveNRP);
	
}
