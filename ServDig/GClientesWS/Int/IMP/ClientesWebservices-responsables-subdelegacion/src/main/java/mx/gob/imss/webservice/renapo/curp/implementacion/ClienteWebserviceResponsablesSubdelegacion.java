package mx.gob.imss.webservice.renapo.curp.implementacion;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.webservice.renapo.curp.cliente.RecuperaResponsablesDelegacion;
import mx.gob.imss.webservice.renapo.curp.cliente.ResponsableDTO;
import mx.gob.imss.webservice.renapo.curp.cliente.ResponsablesDelegacionDTO;
import mx.gob.imss.webservice.renapo.curp.cliente.UsuarioInfoDTO;
import mx.gob.imss.webservice.renapo.curp.utility.ResponsablesDelegacionErrorWSEnum;

import org.apache.log4j.Logger;

public class ClienteWebserviceResponsablesSubdelegacion {

	Logger log = Logger.getLogger("cliente.webservices");
	private static final int SERVICE_TIME_OUT = 20000;
	
    public List<Fisica> buscarResponsables(
    				final int cveDelegacion,
    				final int cveSubdelegacion,
    				final String roles, 
    				final int modulo) throws ClienteWebserviceResponsablesSubdelegacionException{
        List<Fisica> personasFisicas = null;
        
        
    	//PREPARA EL FILTRO DE BUSQUEDA 
        
        final RecuperaResponsablesDelegacion recuperaResponsablesDelegacion = new RecuperaResponsablesDelegacion();
        
        recuperaResponsablesDelegacion.setCveDelegacion(cveDelegacion);
        recuperaResponsablesDelegacion.setCveSubdelegacion(cveSubdelegacion);
        recuperaResponsablesDelegacion.setRoles(roles);
        recuperaResponsablesDelegacion.setModulo(modulo);
        
        // CONSULTA 
        final ResponsablesDelegacionDTO respuesta = consultaWS(recuperaResponsablesDelegacion);
    
        //OBTIENE LOS DATOS BASICOS DE LA PERSONA ;
        personasFisicas = recuperaDatosBasicos(respuesta);
        
        return personasFisicas;        
    }

    public Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp) throws ClienteWebserviceResponsablesSubdelegacionException{
        // CONSULTA 
        final UsuarioInfoDTO respuesta = consultaDatosUsuario(curp);
    
        //OBTIENE LOS DATOS BASICOS DE LA PERSONA ;
        Usuario resultado = getUsuarioFromDTO(respuesta, curp);
        
        return resultado;
    }

    public ResponsablesDelegacionDTO consultaWS(final RecuperaResponsablesDelegacion entrada) throws ClienteWebserviceResponsablesSubdelegacionException  {
    	ResponsablesDelegacionDTO result = null;

		try {
			log.info("WebserviceResponsables. ClienteWebservice. Entrada. " + entrada);
			log.info("WebserviceResponsables. ClienteWebservice. Se ejecuta el thread del Cliente. " + new Date());
			DatosWebServiceResponsablesSubdelegacion thread = new DatosWebServiceResponsablesSubdelegacion();
			thread.setParametrosEntrada(entrada);
			thread.start();

			log.info("WebserviceResponsables. ClienteWebservice. Se establece el tiempo del timeout del thread = " + SERVICE_TIME_OUT);
			thread.join(SERVICE_TIME_OUT);
			log.info("WebserviceResponsables. ClienteWebservice. Se recupera el control del proceso desde el thread. " + new Date());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				throw new ClienteWebserviceResponsablesSubdelegacionException();
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				log.error("WebserviceResponsables. ClienteWebservice. El thread sigue esperando la respuesa");
				thread.interrupt();
				log.error("WebserviceResponsables. ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceResponsablesSubdelegacionException");
				throw new ClienteWebserviceResponsablesSubdelegacionException(ClienteWebserviceResponsablesSubdelegacionException.CODIGO_ERROR_THREAD_INTERRUPT);
			} else {
				log.info("WebserviceCurp. ClienteWebservice. El thread termino satisfactoriamente la consulta dentro del timeout especificado");
				result = thread.getRespuesta();
				/*
				 * Segun la especificacion del WS toda operacion
				 * exitosa tendra el estatus Consulta Exitosa
				 */
				if (result.getClave()!=ResponsablesDelegacionErrorWSEnum.EXITO.getClave()) {
					log.error("Codigo error -> " + result.getClave() + "] msg " + result.getMensaje()+"]");
					throw new ClienteWebserviceResponsablesSubdelegacionException(result.getMensaje(), ClienteWebserviceResponsablesSubdelegacionException.CODIGO_ERROR_WS);					
				}
			}

			thread = null;
		} catch (Exception e) {
			log.error("ClienteWebserviceResponsablesSubdelegacionException. ClienteWebservice. Se genero un error al accesar el webservice");
			throw new ClienteWebserviceResponsablesSubdelegacionException();
		}

		log.info("ResponsablesDelegacionDTO: " + result);
		return result;
    }
    
    private UsuarioInfoDTO consultaDatosUsuario(String entrada) throws ClienteWebserviceResponsablesSubdelegacionException{
    	UsuarioInfoDTO result = null;

		try {
			log.info("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. Entrada. " + entrada);
			log.info("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. Se ejecuta el thread del Cliente. " + new Date());
			DatosWebServiceConsultaDatosUsuario thread = new DatosWebServiceConsultaDatosUsuario();
			thread.setParametrosEntrada(entrada);
			thread.start();

			log.info("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. Se establece el tiempo del timeout del thread = " + SERVICE_TIME_OUT);
			thread.join(SERVICE_TIME_OUT);
			log.info("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. Se recupera el control del proceso desde el thread. " + new Date());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				throw new ClienteWebserviceResponsablesSubdelegacionException();
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				log.error("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. El thread sigue esperando la respuesa");
				thread.interrupt();
				log.error("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceResponsablesSubdelegacionException");
				throw new ClienteWebserviceResponsablesSubdelegacionException(ClienteWebserviceResponsablesSubdelegacionException.CODIGO_ERROR_THREAD_INTERRUPT);
			} else {
				log.info("WebserviceResponsables-consultaDatosUsuario. ClienteWebservice. El thread termino satisfactoriamente la consulta dentro del timeout especificado");
				result = thread.getRespuesta();
				
			}

			thread = null;
		} catch (Exception e) {
			log.error("consultaDatosUsuario ClienteWebserviceResponsablesSubdelegacionException. ClienteWebservice. Se genero un error al accesar el webservice");
			throw new ClienteWebserviceResponsablesSubdelegacionException();
		}

		log.info("UsuarioInfoDTO: " + result);
		return result;
    }
    
	private List<Fisica> recuperaDatosBasicos(
			ResponsablesDelegacionDTO respuesta) {
		List<Fisica> personas = new ArrayList<Fisica>();
		
		for(ResponsableDTO responsableDTO:respuesta.getResponsables()){
			Fisica fisica= new Fisica();
			fisica.setNombre(responsableDTO.getNombre());
			fisica.setPrimerApellido(responsableDTO.getPrimerApellido());
			fisica.setSegundoApellido(responsableDTO.getSegundoApellido());
			fisica.setCurp(responsableDTO.getCurp());
			CorreoElectronico correo = new CorreoElectronico();
			correo.setCorreo(responsableDTO.getCorreoElectronico());
			fisica.setCorreoElectronico(correo);
			personas.add(fisica);			
		}
		return personas;
	} 
	
	private Usuario getUsuarioFromDTO(UsuarioInfoDTO respuesta, String curp){
		Usuario user = null;
		if(respuesta != null){
			user = new Usuario();
			CorreoElectronico correo = new CorreoElectronico();
			Fisica fisica = new Fisica();
			fisica.setPrimerApellido(respuesta.getApellidoPaterno());
			fisica.setSegundoApellido(respuesta.getApellidoMaterno());
			fisica.setNombre(respuesta.getNombre());
			fisica.setCurp(curp);
			correo.setCorreo(respuesta.getCorreoElectronico());
			fisica.setCorreoElectronico(correo);
			user.setFisica(fisica);
		}
		return user;
	}
}
