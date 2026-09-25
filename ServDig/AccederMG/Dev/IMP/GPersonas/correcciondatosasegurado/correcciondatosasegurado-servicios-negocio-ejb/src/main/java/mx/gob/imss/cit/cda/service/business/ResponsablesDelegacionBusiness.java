package mx.gob.imss.cit.cda.service.business;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceResponsablesSubdelegacion;
import mx.gob.imss.webservice.renapo.curp.utility.RolesResponsablesSubdelegacionEnum;

@Stateless(name = "responsablesDelegacionBusiness", mappedName = "responsablesDelegacionBusiness")
public class ResponsablesDelegacionBusiness extends AbstractServiceUtility
		implements ResponsablesDelegacionRemote {

	private static final int MODULO_CDA = 14;
	
	@Override
	public List<Fisica> consultarResponsablesDelegacion(int cveDelegacion,
			int cveSubdelegacion)
			throws ClienteWebserviceResponsablesSubdelegacionException {
		ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();

		return clienteWebserviceResponsablesSubdelegacion.buscarResponsables(
				cveDelegacion, cveSubdelegacion,
				RolesResponsablesSubdelegacionEnum.RESPONSABLE.getClave(),
				MODULO_CDA);
	}

	@Override
	public List<Fisica> consultarAutorizadoresDelegacion(int cveDelegacion,
			int cveSubdelegacion)
			throws ClienteWebserviceResponsablesSubdelegacionException {
		ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();

		return clienteWebserviceResponsablesSubdelegacion.buscarResponsables(
				cveDelegacion, cveSubdelegacion,
				RolesResponsablesSubdelegacionEnum.AUTORIZADOR.getClave(),
				MODULO_CDA);
	}
	
	@Override
	public Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp)
			throws ClienteWebserviceResponsablesSubdelegacionException {
		ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();

		return clienteWebserviceResponsablesSubdelegacion.recuperaUsuarioEsquemaSeguridadByCURP(curp);
	}
	

}
