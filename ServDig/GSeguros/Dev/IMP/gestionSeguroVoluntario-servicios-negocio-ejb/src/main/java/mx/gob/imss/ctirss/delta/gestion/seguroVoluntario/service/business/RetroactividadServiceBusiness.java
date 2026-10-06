package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.Modalidad40RestLocal;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.Modalidad40RestLocalImpl;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaConsultaResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.RetroactividadServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.MapeoRetroUtil;


@Stateless(name = "retroactividadServiceBusiness", mappedName = "retroactividadServiceBusiness")
public class RetroactividadServiceBusiness implements RetroactividadServiceRemote{
	
    /**
     * Logger de la clase
     */
    private static final Logger log = LoggerFactory
            .getLogger(RetroactividadServiceBusiness.class);

	@Override
	public ValidaRetroactividadResponse obtenerInfoInicialRetroactividad(ValidaRetroactividadRequest request) {
		ValidaRetroactividadResponse response = new ValidaRetroactividadResponse();
		mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadRequest requestRest = new mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadRequest();

		requestRest.setNss(request.getNss());
		requestRest.setUsuario("MODALIDAD40");
	
		try {
			Modalidad40RestLocal modalidad40RestLocal = new Modalidad40RestLocalImpl();
			
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadResponse responseRest =	modalidad40RestLocal.validaRetroactividad(requestRest);
			
			log.info("responseRest: "+responseRest);
			
			if(responseRest!=null ) {
				
				log.info("codigo: "+responseRest.getCodigo());
				log.info("Descripcion :"+responseRest.getDescripcion());
				
				if(responseRest.getVrDto()!= null) {
					log.info("******RESPUESTA: "+responseRest.getVrDto().toString());
					
					response.setCodigo(responseRest.getCodigo());
					response.setDescripcion(responseRest.getDescripcion());
					response.setVrDto(MapeoRetroUtil.mapeoResponseDTO(responseRest.getVrDto()));
					
				}else {
					log.error("sin Objeto de respuesta");
				}
			}else {
				log.error("Ocurrio un error en el servicio");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCodigo("400");
			response.setDescripcion("Error en el servicio: "+e.getMessage());
		}
		
		return response;
	}

	@Override
	public CalculoPagosResponse calculoPagosRetroactividad(CalculoPagosRequest request) {
		
		CalculoPagosResponse response = new CalculoPagosResponse();
		
		mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosRequest requestRest = new mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosRequest();
		
		
		try {
			Modalidad40RestLocal modalidad40RestLocal = new Modalidad40RestLocalImpl();
			
			requestRest.setIdCalculo(request.getIdCalculo());
			requestRest.setNss(request.getNss());
			requestRest.setMunicipioInegi(request.getMunicipioInegi());
			requestRest.setEntidadInegi(request.getEntidadInegi());
			requestRest.setSalarioElegido(request.getSalarioElegido());
			requestRest.setOrigenCalculo(request.getOrigenCalculo());
			requestRest.setUsuario(request.getUsuario());
			
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosResponse responseRest =	modalidad40RestLocal.calculoPagos(requestRest);
			
			log.info("responseRest: "+responseRest);
			
			if(responseRest!=null ) {
				
				log.info("codigo: "+responseRest.getCodigo());
				log.info("Descripcion :"+responseRest.getDescripcion());
				
				if(responseRest.getVrDto()!= null) {
					log.info("******RESPUESTA: "+responseRest.getVrDto().toString());
					
					response.setCodigo(responseRest.getCodigo());
					response.setDescripcion(responseRest.getDescripcion());
					response.setVrDto(MapeoRetroUtil.mapeoResponseDTO(responseRest.getVrDto()));
					
				}else {
					log.error("sin Objeto de respuesta");
				}
			}else {
				log.error("Ocurrio un error en el servicio");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCodigo("400");
			response.setDescripcion("Error en el servicio: "+e.getMessage());
		}
		
		
		return response;
	}
	
    
	@Override
	public GeneracionMultilineaResponse generaMultilineaRetroactividad(GeneracionMultilineaRequest request) {

		GeneracionMultilineaResponse response = new GeneracionMultilineaResponse();
		try {
			
			Modalidad40RestLocal modalidad40RestLocal = new Modalidad40RestLocalImpl();
			
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaRequest requestRest = new mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaRequest();
			
			requestRest.setIdCalculo(request.getIdCalculo());
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaResponse responseRest =	modalidad40RestLocal.generacionMultilinea(requestRest);
			
			log.info("responseRest: "+responseRest);
			
			if(responseRest!=null ) {
				
				log.info("codigo: "+responseRest.getCodigo());
				log.info("Descripcion :"+responseRest.getDescripcion());
				
				if(responseRest.getVrDto()!= null) {
					log.info("******RESPUESTA: "+responseRest.getVrDto().toString());
					
					response.setCodigo(responseRest.getCodigo());
					response.setDescripcion(responseRest.getDescripcion());
					response.setVrDto(MapeoRetroUtil.mapeoResponseDTO(responseRest.getVrDto()));
					
				}else {
					log.error("sin Objeto de respuesta");
				}
			}else {
				log.error("Ocurrio un error en el servicio");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCodigo("400");
			response.setDescripcion("Error en el servicio: "+e.getMessage());
		}
		
		
		return response;
	}
	
	@Override
	public  GeneracionMultilineaConsultaResponse consultaMultilineaRetroactividad(String nss) {
		
		GeneracionMultilineaConsultaResponse response = new GeneracionMultilineaConsultaResponse();
		try {
			Modalidad40RestLocal modalidad40RestLocal = new Modalidad40RestLocalImpl();
			
			mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaConsultaResponse responseRest =	modalidad40RestLocal.generacionMultilineaConsulta(nss);
			
			log.info("responseRest: "+responseRest);
			
			if(responseRest!=null ) {
				
				log.info("codigo: "+responseRest.getCodigo());
				log.info("Descripcion :"+responseRest.getDescripcion());
				
				if(responseRest.getVrDto()!= null) {
					log.info("******RESPUESTA: "+responseRest.getVrDto().toString());
					
					response.setCodigo(responseRest.getCodigo());
					response.setDescripcion(responseRest.getDescripcion());
					response.setVrDto(MapeoRetroUtil.mapeoResponseDTO(responseRest.getVrDto()));
					
				}else {
					log.error("sin Objeto de respuesta");
				}
			}else {
				log.error("Ocurrio un error en el servicio");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCodigo("400");
			response.setDescripcion("Error en el servicio: "+e.getMessage());
		}
		
		
		return response;
	}
}
