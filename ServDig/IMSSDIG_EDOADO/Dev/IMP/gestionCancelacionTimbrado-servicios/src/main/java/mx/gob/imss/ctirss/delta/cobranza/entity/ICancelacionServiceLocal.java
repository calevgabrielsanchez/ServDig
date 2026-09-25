/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.ProcOdiCompFisc;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;

/**
 * @author vanderluk
 *
 */
@Local
public interface ICancelacionServiceLocal {
	
	
	/**
	 * Metodo que obtiene los registros de timbre a cancelar a partir de la fecha de proceso.
	 * @param fechaProceso
	 * @return 
	 * @throws NoExistenDatosParaCancelarException
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public List<ProcOdiCompFisc> obtenerRegistrosParaCancelar(String fechaProceso) throws NoExistenDatosParaCancelarException, ErrorAlConsultarTimbradosException;
	
	
	/**
	 * Obtiene el numero de registros a procesar por fecha de proceso.
	 * @param fechaProceso
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public long getNumeroRegistrosTimbrados(String fechaProceso)throws ErrorAlConsultarTimbradosException;
	
	
	/**
	 * 
	 * @param fechaProceso
	 * @param numeroLote
	 * @return
	 * @throws NoExistenDatosParaCancelarException
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public List<ProcOdiCompFisc> obtenerRegistrosParaCancelarPorLote(String fechaProceso , int numeroLote) throws NoExistenDatosParaCancelarException, ErrorAlConsultarTimbradosException;
	
	
	/**
	 * 
	 * @param registroCFDI
	 * @param 
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public void actualizaFolios(RegistroCFDI[] registroCFDI) throws ErrorAlConsultarTimbradosException;

	/**
	 * Metodo que obtiene los folios por fecga y codigo de repsuesta 9001
	 * @param fechaProceso
	 * @return 
	 * @throws NoExistenDatosParaCancelarException
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public List<ProcOdiCompFisc> obtenerFoliosParaCancelarPorUUID(String fechaProceso) throws NoExistenDatosParaCancelarException,ErrorAlConsultarTimbradosException;
	
	
	/**
	 * Metodo que actualiza los estatus de cancelacion del SAT
	 * @param fechaProceso
	 * @return 
	 * @throws NoExistenDatosParaCancelarException
	 * @throws ErrorAlConsultarTimbradosException
	 */
	public void actualizaRegistroCFDICancelado(RegistroCFDI registroCFDI) throws ErrorAlConsultarTimbradosException;
	
}
