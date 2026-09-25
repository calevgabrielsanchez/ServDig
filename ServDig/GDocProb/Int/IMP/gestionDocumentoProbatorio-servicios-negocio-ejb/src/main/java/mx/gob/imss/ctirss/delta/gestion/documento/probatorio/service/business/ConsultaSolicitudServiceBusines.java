package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import javax.ejb.Stateless;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.solicitud.MatriculaSolicitud;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DoctoReqTramiteParser;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import mx.gob.imss.ctirss.delta.persistence.admonusuarios.entidades.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;

@Stateless(name = "consultaMatriculaServiceBusiness", mappedName = "consultaMatriculaServiceBusiness")
public class ConsultaSolicitudServiceBusines extends AbstractServiceEntity implements MatriculaSolicitud {

	@Override
	public String consultaMatricula(String curp) throws DocumentoProbatorioException {
		
		try {
			return this.consultar(curp);
		} catch (IOException e) {
			log.error("ERROR AL CONSULTAR LA MATRICULA POR WS:: " + e);
			throw new DocumentoProbatorioException(e.getMessage());
		}

	}

	public String consultar(String curp) throws MalformedURLException,RemoteException {
		final ClienteConsultaCurpSiap ws= new ClienteConsultaCurpSiap();
		try {
			UsuarioNominaResponse  usuarioNominaResponse = ws.invocarServicioConsultaCurpSiap(curp, "");
			if (usuarioNominaResponse !=null) {
				
				String response = usuarioNominaResponse.getNombre() + " - "+ usuarioNominaResponse.getMatricula();
				log.error("RESPONSE :: " + response);
				return response;
			}
			else {
				return "---";
			}
			
		} catch (MalformedURLException e) {
			log.error("FALLO AL CONSULTAR LA MATRICULA:: " + e.getMessage());
			return "---";
		} catch (RemoteException e) {
			log.error("FALLO AL CONSULTAR LA MATRICULA:: " + e.getMessage());
			return "---";
		}

	}
}
