package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob.HistPatronesConvenioImssrcv;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaCreditosSiscobServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;

@Service
@Path("v1/creditos/patrones")
public class CreditosPatronSiscobServiceRest {
	
	private static final Logger log = LoggerFactory
            .getLogger(CreditosPatronSiscobServiceRest.class); 
	
	private IConsultaCreditosSiscobServiceRemote creditosPatronService;
	
	public CreditosPatronSiscobServiceRest() {
		this.getEjbCreditosPatronService();
	}
	
	@GET
	@Path("/siscob/cptCreinc14ImssRcv/{numNrp}/{numCredito}/{periodoCredito}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<CptCreinc14ImssRcv> getCptCreinc14ImssRcv (@PathParam("numNrp") String numNrp,
			@PathParam("numCredito") Long numCredito, @PathParam("periodoCredito") Long  periodoCredito) {
		try {
			return creditosPatronService.getCptCreinc14ImssRcv(numNrp, numCredito,periodoCredito );
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/siscob/histPatronesConvenioImssrcv/{numNrp}/{numCredito}/{periodoCredito}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HistPatronesConvenioImssrcv> getHistPatronesConvenioImssrcv (@PathParam("numNrp") String numNrp,
			@PathParam("numCredito") Long numCredito, @PathParam("periodoCredito") Long  periodoCredito) {
		try {
			return creditosPatronService.getHistPatronesConvenioImssrcv(numNrp, numCredito,periodoCredito );
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	
	@GET
	@Path("/siscob/hCopEstadoCuenta/{numNrp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HCopEstadoCuenta> getHCopEstadoCuenta (@PathParam("numNrp") String numNrp) {
		try {
			return creditosPatronService.getHCopEstadoCuenta(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/siscob/hRcvEstadoCuenta/{numNrp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<HRcvEstadoCuenta> getHRcvEstadoCuenta(@PathParam("numNrp") String numNrp) {
		try {
			return creditosPatronService.getHRcvEstadoCuenta(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	public void getEjbCreditosPatronService() {
		try {
			System.out.println("llegue a instanciar el EJB");
			creditosPatronService = EjbLocator.getCreditosPatronService();
			System.out.println("sali instanciar el EJB");
			}catch (Exception e) {
				System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}

}
