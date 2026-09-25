package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau.Usuario;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISAUServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Path("v1/sau")
public class SAUServicesRest {

	private static Logger log = LoggerFactory.getLogger(SAUServicesRest.class);

	private ISAUServiciosDigitalesServiceRemote sauServiciosDigitalesServiceRemote;

	public SAUServicesRest() {
		this.getEjbSirocService();
	}

	@GET
	@Path("/buscarUsuarioPorUid/{uid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Usuario buscarUsuarioPorUid(@PathParam("uid") String uid)
			throws ServiciosRestException {
		try {
			return sauServiciosDigitalesServiceRemote.buscarUsuarioPorUid(uid);
		} catch (ServiciosRestException ex) {
			log.error(
					"ocurrio un error al consultar el aviso registro de obra por subdelegacion",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al inesperado al consultar el aviso registro de obra por subdelegacion",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el aviso de registor de obra por subdelegacion",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	@POST
	@Path("/buscarUsuario")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Usuario> buscarUsuarioPorUid(Usuario input)
			throws ServiciosRestException {
		try {
			return sauServiciosDigitalesServiceRemote.buscarUsuario(input);
		} catch (ServiciosRestException ex) {
			log.error(
					"ocurrio un error al consultar el aviso registro de obra por subdelegacion",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al inesperado al consultar el aviso registro de obra por subdelegacion",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el aviso de registor de obra por subdelegacion",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@POST
	@Path("/buscarUsuario/general")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Usuario> buscarUsuarioPorUidGeneral(Usuario input)
			throws ServiciosRestException {
		try {
			return sauServiciosDigitalesServiceRemote.buscarUsuarioGenral(input);
		} catch (ServiciosRestException ex) {
			log.error(
					"ocurrio un error al consultar el aviso registro de obra por subdelegacion",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al inesperado al consultar el aviso registro de obra por subdelegacion",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el aviso de registor de obra por subdelegacion",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	private void getEjbSirocService() {
		try {
			log.debug("llegue a instanciar el EJB");
			sauServiciosDigitalesServiceRemote = EjbLocator.getSAUService();
			System.out.println("sali instanciar el EJB");
		} catch (Exception e) {
			System.out.println("Ocurrio un error al quere recuperar el EJB"
					+ e.getMessage());
			throw new WebApplicationException(e.getCause(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
