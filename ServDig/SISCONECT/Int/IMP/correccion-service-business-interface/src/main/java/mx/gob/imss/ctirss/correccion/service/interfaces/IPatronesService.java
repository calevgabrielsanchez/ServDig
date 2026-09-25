package mx.gob.imss.ctirss.correccion.service.interfaces;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.SatPatron;

public interface IPatronesService <T extends AbstractModel>{
	
	
	public SatPatron validaRegistroPatronalWS(String registroPatronal, boolean esPirncipal);
	
	public SatPatron validaRegistroPatronalWS(String registroPatronal, Long subdelegacion);

	public SatPatron getById(Long cvePk);
	
	public int generaDigitoVerificador(String nrp);
	
	public SatPatron getByRegistroPatronal(String registroPatronal);
	
	public SatPatron saveOrUpdatePatron(SatPatron patron);
}
