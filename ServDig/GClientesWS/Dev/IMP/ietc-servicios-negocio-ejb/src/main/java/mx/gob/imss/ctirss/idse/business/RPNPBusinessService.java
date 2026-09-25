package mx.gob.imss.ctirss.idse.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.idse.entity.IDSEServiceEntityLocal;
import mx.gob.imss.ctirss.idse.entity.IETCServiceEntityLocal;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.service.interfaces.RPNPServiceRemote;


@Stateless(mappedName="rpnpServiceTest", name="rpnpService")
public class RPNPBusinessService implements RPNPServiceRemote {

	@EJB
	IETCServiceEntityLocal ietcEntity;
	@EJB
	IDSEServiceEntityLocal idseEntity;
	
	@Override
	public void registrarAltaPatronalIDSE(RequerimientoIETCBean reqCertificacion,
			RequerimientoIDSEBean reqIdse) {
		registraCertificado(reqCertificacion);
		registrarPatronEnIdse(reqIdse);
	}
	
	
	private void registraCertificado(RequerimientoIETCBean reqCertificacion){
		ietcEntity.insertaCertificado(reqCertificacion);
	}
		
	private void registrarPatronEnIdse(RequerimientoIDSEBean reqIdse){
		idseEntity.insertarNuevoRegistroPatronal(reqIdse);
		
	}
}
