package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DicDiasFestivo;


/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Local
public interface DiasFestivosDaoLocal {

	public List<DicDiasFestivo> findDiasFestivos() throws Exception;
}
