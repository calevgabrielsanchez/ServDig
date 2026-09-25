package gob.imss.webservice.imss.riss.implementacion;

import gob.imss.webservice.imss.riss.cliente.ValidacionRissRequest;
import gob.imss.webservice.imss.riss.cliente.ValidacionRissResponse;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

public class ClienteWebserviceValidarRiss {

	Logger log = Logger.getLogger(ClienteWebserviceValidarRiss.class);
	private static int serviceTimeOut = 2000000;

	public RespuestaRifSat validarRiss(Beneficio beneficio) throws ClienteWebserviceImssRissException {

		this.log.debug("Entrada para validar el RISS -> " + beneficio);

		final ValidacionRissRequest request = new ValidacionRissRequest();
		request.setRfc(beneficio.getRfc());
		request.setCurp(beneficio.getCurp());

		if (beneficio.getFisica() != null
				&& StringUtils.isNotBlank(beneficio.getFisica().getNss())) {
			request.setNss(beneficio.getFisica().getNss());
		}
		
		if (beneficio.getCveAsignacion() != null) {
			request.setCveAsignacion(beneficio.getCveAsignacion());
		}

		if (!CollectionUtils.isEmpty(beneficio.getListaNRPsMod10y13())) {
			
			ValidacionRissRequest.RegistrosPatronales nrps = new ValidacionRissRequest.RegistrosPatronales();
			request.setRegistrosPatronales(nrps);
			
			for (String nrp : beneficio.getListaNRPsMod10y13()) {
				request.getRegistrosPatronales().getNrp().add(nrp);
			}

			this.log.debug("NRP a enviar para validarles RISS -> "
					+ request.getRegistrosPatronales());
		}

		request.setTipoPersona(beneficio.getTipoApartado());

		log.info("+++Request: "+request);

		final ValidacionRissResponse response = llamarValidacionesRissWS(request,false);

        log.info("+++Response: "+response);

		RespuestaRifSat respuestaRifSat = new RespuestaRifSat();
		respuestaRifSat.setIndicadorDerecho(response.isIndicadorDerechoBeneficio());
		respuestaRifSat.setTipoApartadoBeneficio(response.getTipoApartadoBeneficio());
		respuestaRifSat.setIndicadorC(response.isIndicadorApartadoC());
		respuestaRifSat.setFechaAltaRif(DateUtils.dateToDateConFormato(response.getFechaRif(), "dd-MM-yyyy"));
		respuestaRifSat.setMotivoDeRechazo(response.getMotivoDeRechazo());
		respuestaRifSat.setExito(response.getExito());
		respuestaRifSat.setClaveError(response.getClaveError());
		respuestaRifSat.setDescripcion(response.getDescripcion());
		
		return respuestaRifSat;
		
	}

	public RespuestaRifSat validarRiss(Beneficio beneficio,boolean esRenovacion) throws ClienteWebserviceImssRissException {

		this.log.debug("Entrada para validar el RISS -> " + beneficio);

		final ValidacionRissRequest request = new ValidacionRissRequest();
		request.setRfc(beneficio.getRfc());
		request.setCurp(beneficio.getCurp());

		if (beneficio.getFisica() != null
				&& StringUtils.isNotBlank(beneficio.getFisica().getNss())) {
			request.setNss(beneficio.getFisica().getNss());
		}

		if (beneficio.getCveAsignacion() != null) {
			request.setCveAsignacion(beneficio.getCveAsignacion());
		}

		if (!CollectionUtils.isEmpty(beneficio.getListaNRPsMod10y13())) {

			ValidacionRissRequest.RegistrosPatronales nrps = new ValidacionRissRequest.RegistrosPatronales();
			request.setRegistrosPatronales(nrps);

			for (String nrp : beneficio.getListaNRPsMod10y13()) {
				request.getRegistrosPatronales().getNrp().add(nrp);
			}

			this.log.debug("NRP a enviar para validarles RISS -> "
					+ request.getRegistrosPatronales());
		}

		request.setTipoPersona(beneficio.getTipoApartado());

		log.info("+++Request: "+request);

		final ValidacionRissResponse response = llamarValidacionesRissWS(request,esRenovacion);

		log.info("+++Response: "+response);

		RespuestaRifSat respuestaRifSat = new RespuestaRifSat();
		respuestaRifSat.setIndicadorDerecho(response.isIndicadorDerechoBeneficio());
		respuestaRifSat.setTipoApartadoBeneficio(response.getTipoApartadoBeneficio());
		respuestaRifSat.setIndicadorC(response.isIndicadorApartadoC());
		respuestaRifSat.setFechaAltaRif(DateUtils.dateToDateConFormato(response.getFechaRif(), "dd-MM-yyyy"));
		respuestaRifSat.setMotivoDeRechazo(response.getMotivoDeRechazo());
		respuestaRifSat.setExito(response.getExito());
		respuestaRifSat.setClaveError(response.getClaveError());
		respuestaRifSat.setDescripcion(response.getDescripcion());

		return respuestaRifSat;

	}

	private ValidacionRissResponse llamarValidacionesRissWS(
			final ValidacionRissRequest request, boolean esRenovacion) throws ClienteWebserviceImssRissException {
		ValidacionRissResponse response = null;

		try {
			this.log.debug("WebserviceValidarRiss. Entrada -> " + request);
			this.log.debug("WebserviceValidarRiss. Se ejecuta el thread del Cliente. "
					+ new Date());
			ValidacionRissServiceImpl thread = new ValidacionRissServiceImpl();
            thread.setEsRenovacion(esRenovacion);
			thread.setRequest(request);
			thread.start();

			this.log.debug("WebserviceValidarRiss. Se establece el tiempo del timeout del thread = "
					+ serviceTimeOut);
			thread.join(serviceTimeOut);
			this.log.debug("WebserviceValidarRiss. Se recupera el control del proceso desde el thread. "
					+ new Date());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				throw new ClienteWebserviceImssRissException();
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				this.log.debug("WebserviceValidarRiss. El thread sigue esperando la respuesa de RENAPO");
				thread.interrupt();
				this.log.debug("WebserviceValidarRiss. El thread se ha interrumpido y se generara un ClienteWebserviceImssRissException");
				throw new ClienteWebserviceImssRissException(10002);
			} else {
				this.log.debug("WebserviceValidarRiss. El thread termino satisfactoriamente las validaciones del RISS dentro del timeout especificado");
				response = thread.getResponse();

				if (response.getExito() != 0) {
					throw new ClienteWebserviceImssRissException(
							response.getDescripcion(), response.getClaveError());
				} 
			}
				
			thread = null;
		} catch (ClienteWebserviceImssRissException e) {
			throw e;
		} 
		catch (Exception e) {
			this.log.debug("WebserviceValidarRiss. Se genero un error al accesar el webservice");
			throw new ClienteWebserviceImssRissException();
		}

		this.log.debug("La respuesta de las validaciones RISS es -> "
				+ response);

		return response;
	}
}
