package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DomicilioGeograficoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DomicilioGeograficoServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;

/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "domicilioGeograficoService", mappedName = "domicilioGeograficoService")
public class DomicilioGeograficoService implements DomicilioGeograficoServiceRemote{

	
	@EJB
	private DomicilioGeograficoDaoLocal domicilioGeograficoDao;
	
	

	@Override
	public Domicilio getDomicilioGrupoFam(Asegurado asegurado) throws DerechohabientesBusinessException, Exception {
		Domicilio domicilio= domicilioGeograficoDao.getDomicilioGrupoFamiliar(asegurado);
		return domicilio;
	}

	@Override
	public Domicilio ubicarDomicilioGeografico(TipoDomicilio tipoDomicilio) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
