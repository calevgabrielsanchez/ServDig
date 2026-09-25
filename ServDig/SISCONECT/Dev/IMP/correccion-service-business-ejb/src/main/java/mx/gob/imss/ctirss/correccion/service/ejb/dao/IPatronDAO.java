package mx.gob.imss.ctirss.correccion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.SacMunicipio;
import mx.gob.imss.ctirss.correccion.model.SatPatron;

public interface IPatronDAO<T extends AbstractModel> {
	
	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	public SatPatron saveOrUpdate(SatPatron model);

	public SacMunicipio obtenmunicipio(String cveMunicipio);

	public SatPatron getById(Long cvePK);
	
	public SatPatron getByRegistroPatronal(String registroPatronal);
}
