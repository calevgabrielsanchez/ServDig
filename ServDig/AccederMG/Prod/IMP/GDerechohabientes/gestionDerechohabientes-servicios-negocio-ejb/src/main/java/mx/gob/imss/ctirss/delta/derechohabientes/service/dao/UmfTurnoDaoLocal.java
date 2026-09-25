package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.math.BigInteger;

import javax.ejb.Local;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;


@Local
public interface UmfTurnoDaoLocal {

	/**
	 * Obtiene UmfTurno dado un criterio
	 * @param UmfTurno
	 * @return UmfTurno
	 * @throws Exception 
	 */
	public BigInteger getCapacidadUmfTurno(DitUmfTurno ditUmfTurno) throws Exception;
	public DicUmf getUmfById(Long idUmf) throws Exception;
}