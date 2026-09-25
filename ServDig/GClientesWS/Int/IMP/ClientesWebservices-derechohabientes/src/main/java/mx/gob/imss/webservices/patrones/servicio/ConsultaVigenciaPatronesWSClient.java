package mx.gob.imss.webservices.patrones.servicio;

import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.webservices.patrones.Message;
import mx.gob.imss.webservices.patrones.PatronActivoVo;
import mx.gob.imss.webservices.patrones.Respuesta;

public class ConsultaVigenciaPatronesWSClient {
	
	private String socketTimeoutException = "Ocurrió un error de comunicación al intentar recuperar la cabeza de grupo familiar";
	
	public List<Long> getIDsPatronesActivosPorAsignacionNSS(Long idAsignacionNSS)
			throws DerechohabientesWebSserviceException {

		try {
			List<Long> items = null;

			ConsPatronesVigXAsigServices service = new ConsPatronesVigXAsigServices();
			ConsPatronesVigXAsigService ws = service
					.getConsPatronesVigXAsigServiceSoap11();
			OperacionRequest request = new OperacionRequest();
			Message message = new Message();
			message.setCveAsignacionNSS(new BigInteger(idAsignacionNSS
					.toString()));
			request.setEntrada(message);

			OperacionResponse response = ws.operacion(request);
			Respuesta respuesta = response.getSalida();

			if (respuesta != null
					&& respuesta.getCodigo().equals("0")) {
				items = new ArrayList<Long>();
				for (PatronActivoVo patron : respuesta.getPatronesActivos()) {
					items.add(patron.getCveIdPatronGeneral().longValue());
				}
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigo() + ", " + respuesta.getMensaje() );
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información de patrones activos por asignacion");
			}
			
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}

	}
	
	public List<SujetoObligado> getPatronesVigentesPorAsignacionNSS(
			Long idAsignacionNSS) throws DerechohabientesWebSserviceException {
		try {
			List<SujetoObligado> items = null;

			ConsPatronesVigXAsigServices service = new ConsPatronesVigXAsigServices();
			ConsPatronesVigXAsigService ws = service
					.getConsPatronesVigXAsigServiceSoap11();
			OperacionRequest request = new OperacionRequest();
			Message message = new Message();
			message.setCveAsignacionNSS(new BigInteger(idAsignacionNSS
					.toString()));
			request.setEntrada(message);

			OperacionResponse response = ws.operacion(request);
			Respuesta respuesta = response.getSalida();

			if (respuesta != null
					&& respuesta.getCodigo().equals("0")) {
				items = new ArrayList<SujetoObligado>();
				for (PatronActivoVo patron : respuesta.getPatronesActivos()) {
					SujetoObligado item = new SujetoObligado();

					item.setCveIdSujetoObligado(patron.getCveIdPatronGeneral()
							.longValue());
					
					Municipio mun = new Municipio();
					mun.setClave( patron.getCveIdPatronGeneral().toString() );
					item.setPatronRelacionado(mun);
					
					Modalidad mod = new Modalidad();
					mod.setIdModalidad( patron.getCveIdModalidad().longValue() );
					item.setModalidad(mod);

					items.add(item);
				}
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigo() + ", " + respuesta.getMensaje() );
			}

			return items;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información de patrones vigentes por asignación");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}

	}
}
