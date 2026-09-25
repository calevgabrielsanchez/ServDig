package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.SolicitudNssCorreoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;

import org.joda.time.DateTime;
import org.joda.time.Days;

@Stateless(mappedName = "solicitudNssCorreoServiceBusiness")
public class SolicitudNssCorreoServiceBusiness extends AbstractServiceBusiness
		implements SolicitudNssCorreoServiceBusinessRemote {

	@EJB
	private SolicitudNssCorreoServiceEntityLocal solicitudNssCorreoServiceEntity;
	
	
	@Override
	public void actualizarCurpACorreo(String correo, String curp)
			throws SolicitudNssCorreoException {
	
		try {
			solicitudNssCorreoServiceEntity.actualizarCurpACorreo(correo, curp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible actualizar el correo");
		}
		
	}
	
	@Override
	public void actualizarBajaCorreoPorCurp(String curp) throws SolicitudNssCorreoException {
	
		try {
			solicitudNssCorreoServiceEntity.actualizarBajaCorreoPorCurp(curp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible dar de baja los correos relacionados");
		}
		
	}
	
	@Override
	public void actualizarPorCodigo(SolicitudNssCorreo nssCorreo, int codigoActualizacion) throws SolicitudNssCorreoException {
		
		String correoCapturado = nssCorreo.getCorreo().getCorreo();
		String curp = nssCorreo.getCurp();
	
		try {
			if(codigoActualizacion == 0){
				solicitudNssCorreoServiceEntity.guardar(nssCorreo);	
			}else if (codigoActualizacion == 2){
				solicitudNssCorreoServiceEntity.actualizarCurpACorreo(correoCapturado, curp);	
			}else if (codigoActualizacion == 3){
				solicitudNssCorreoServiceEntity.actualizarBajaCorreoPorCurp(curp);
				solicitudNssCorreoServiceEntity.actualizarAltaCorreo(correoCapturado);
			}else if (codigoActualizacion == 4){
				solicitudNssCorreoServiceEntity.actualizarBajaCorreoPorCurp(curp);
				solicitudNssCorreoServiceEntity.guardar(nssCorreo);
			}else if (codigoActualizacion == 5){
				solicitudNssCorreoServiceEntity.actualizarBajaCorreoPorCurp(curp);
				solicitudNssCorreoServiceEntity.actualizarCurpACorreo(correoCapturado, curp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible dar de baja los correos relacionados");
		}
		
	}
	
	@Override
	public List<String> consultarUMF (ArrayList<Integer> numerosGenerados) throws SolicitudNssCorreoException{
	
		List<String> resultadosUMF;
		try {
			resultadosUMF = this.solicitudNssCorreoServiceEntity.consultarUMF(numerosGenerados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar las cl�nicas con los id's Generados");
		}
		
		return resultadosUMF;
		
	}
	
	@Override
	public String consultarSubdelegacion (Integer cveIdSubdelegacion) throws SolicitudNssCorreoException{
	
		String resultadoSubdelegacion;
		try {
			resultadoSubdelegacion = this.solicitudNssCorreoServiceEntity.consultarSubdelegacion(cveIdSubdelegacion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar la subdelegacion con el id " + cveIdSubdelegacion);
		}
		
		return resultadoSubdelegacion;
		
	}
	
	@Override
	public Map<String, String> consultarDatosUsuario(String curp) throws SolicitudNssCorreoException{
	
		Map<String, String> resultadoFinal;
		try {
			resultadoFinal = this.solicitudNssCorreoServiceEntity.consultarDatosUsuario(curp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar los datos del usuario");
		}
		
		return resultadoFinal;
		
	}

	@Override
	public SolicitudNssCorreo obtenerPorCorreo(String correo, Long idSolicitud) {

		this.log.debug("Se busca si el correo " + correo
				+ " ya tiene relacion con algun CURP");

		SolicitudNssCorreo nssCorreo = this.solicitudNssCorreoServiceEntity
				.obtenerPorCorreo(correo, idSolicitud);

		if (nssCorreo != null) {
			this.log.debug("Se encontro relacion del correo " + correo
					+ " con el CURP " + nssCorreo.getCurp());
		} else {
			this.log.debug("No se encontro registro del correo " + correo);
		}

		return nssCorreo;

	}
	
	@Override
	public SolicitudNssCorreo obtenerPorCurp(String curp, Long idSolicitud) {

		this.log.debug("Se busca si el curp " + curp
				+ " ya tiene relacion con algun correo");

		SolicitudNssCorreo nssCorreo = this.solicitudNssCorreoServiceEntity
				.obtenerPorCurp(curp, idSolicitud);

		if (nssCorreo != null) {
			this.log.debug("Se encontro relacion del correo " + nssCorreo.getCorreo().getCorreo()
					+ " con el CURP " + curp);
		} else {
			this.log.debug("No se encontro registro del CURP " + curp);
		}

		return nssCorreo;

	}

	@Override
	public void guardar(SolicitudNssCorreo nssCorreo)
			throws SolicitudNssCorreoException {

		this.log.debug("Se va a guardar la relacion correo ["
				+ nssCorreo.getCorreo().getCorreo() + "] con el CURP ["
				+ nssCorreo.getCurp() + "]");

		this.solicitudNssCorreoServiceEntity.guardar(nssCorreo);
	}

	@Override
	public String obtieneMedioContactoCorreoPersona(String idPersona) {

		this.log.debug("Se va a buscar medio de contacto correo para persona: ["
				+ idPersona + "]");

		return this.solicitudNssCorreoServiceEntity.obtieneMedioContactoCorreo(idPersona);
	}

	@Override
	public void registrarConsultaCorreoNSS(String correo, Long idSolicitud) {

		this.log.debug("Se va a registrar consulta/alta de NSS para el correo "
				+ correo);

		this.solicitudNssCorreoServiceEntity.registrarConsultaCorreoNSS(correo, idSolicitud);
	}
	
	@Override
	public void reiniciarConsultaCorreoNSS(String correo, Long idSolicitud) {
		
		this.log.debug("Se va a reiniciar el contador para el correo " + correo);
		
		this.solicitudNssCorreoServiceEntity.reiniciarConsultaCorreoNSS(correo, idSolicitud);
	}
	
	@Override
	public int validaCorreosRegistrados(SolicitudNssCorreo nssCorreo) throws SolicitudNssCorreoException {
		log.debug("------ENTRO A VALIDAR LOS CORREOS REGISTRADOS------");
		String curp = nssCorreo.getCurp();
		String correoCapturado = nssCorreo.getCorreo().getCorreo();

		Boolean requiereActualizacion = false;
		SolicitudNssCorreo nssEncontrado;
		String correoEncontrado = "";
		int codigoActualizacion = 0;
		List<SolicitudNssCorreo> nssCurpExistente = this.obtenerPorCurp(curp);
		
		
		List<SolicitudNssCorreo> nssCurpExistenteActivos = this.obtenerPorCurpCorreosActivos(curp);
		List<SolicitudNssCorreo> nssCorreoExistente = this.obtenerPorCorreo(correoCapturado);
		log.debug("los correos asociados a la curp son: " + nssCurpExistente);

		int comparacion = 0;
		if (nssCorreoExistente != null) {
			SolicitudNssCorreo curpExistente = nssCorreoExistente.get(0);
			comparacion = curp.compareToIgnoreCase(curpExistente.getCurp());
		} else {
			// No existe ninguna relaci�n del correo capturado
			this.log.debug("No existe registro de CURP-correo");
		}

		if (comparacion == 0) {
			if (nssCurpExistenteActivos != null) {
				if (nssCurpExistenteActivos.size() == 1) {
					nssEncontrado = nssCurpExistenteActivos.get(0);
					log.debug("---OBJETO DE CORREO RECUPERADO---: " + nssEncontrado);
					correoEncontrado = nssEncontrado.getCorreo().getCorreo();
					log.debug("El correo electr�nico encontrado es " + correoEncontrado);
					comparacion = correoEncontrado.compareToIgnoreCase(nssCorreo.getCorreo().getCorreo());
					if (comparacion != 0) {
						boolean correoCoincide = false;
						for (SolicitudNssCorreo solicitud : nssCurpExistente) {
							if (solicitud.getCorreo().getCorreo().equals(correoCapturado)) {
								correoCoincide = true;
								break;
							}
						}
						if (correoCoincide) {
							codigoActualizacion = 3;
							System.out.println("El correo capturado coincide con uno de los existentes en la lista.");
						} else {
							codigoActualizacion = 4;
							System.out
									.println("El correo capturado no coincide con ninguno de los existentes en la lista.");
						}
						
					}else {
						codigoActualizacion =1;
					}
				} else if (nssCurpExistenteActivos.size() > 1) {

					boolean listaCorreosCoinciden = todosLosCorreosCoinciden(nssCurpExistenteActivos, correoCapturado);

					if (listaCorreosCoinciden == !true) {
						
						boolean correoCoincide = false;
						for (SolicitudNssCorreo solicitud : nssCurpExistente) {
							if (solicitud.getCorreo().getCorreo().equals(correoCapturado)) {
								correoCoincide = true;
								break;
							}
						}
						if (correoCoincide) {
							codigoActualizacion = 3;
							System.out.println("El correo capturado coincide con uno de los existentes en la lista.");
						} else {
							codigoActualizacion = 4;
							System.out.println(
									"El correo capturado no coincide con ninguno de los existentes en la lista.");
						}
					}else {
						codigoActualizacion = 1;
						log.debug("los correos de la lista coinciden con el capturado");
					}
				}
			} else {
				log.debug("no existen correos asociados al CURP");
			}
		} else {
			throw new SolicitudNssCorreoException(
					"El correo electr�nico que captur� ya se encuentra registrado en el Instituto relacionado a una CURP distinta a la capturada.");
		}

		log.debug("el c�digo de actualizaci�n es: " + codigoActualizacion);

		return codigoActualizacion;
	}
	
    private boolean todosLosCorreosCoinciden(List<SolicitudNssCorreo> lista, String correoCapturado) {
    	
        // Compara el correo de cada elemento de la lista con correoParaComparar
        for (SolicitudNssCorreo solicitud : lista) {
            if (solicitud.getCorreo().getCorreo() == null || !solicitud.getCorreo().getCorreo().equals(correoCapturado)) {
                return false; 
            }
        }

        return true; 
    }

	
	@Override
	public int isConsultaRegistroNSSValid(SolicitudNssCorreo nssCorreo, boolean validaContadores)
			throws SolicitudNssCorreoException {
		
		int codigoOperacion = 0;
		
		/*
		 * Primer se busca si el correo capturado ya cuenta con relaci�n hacia
		 * alg�n CURP
		 */
		String correo = nssCorreo.getCorreo().getCorreo();
		SolicitudNssCorreo nssCorreoExistente = this.obtenerPorCorreo(correo, nssCorreo.getCveIdTipoSolicitud());
		
		if (nssCorreoExistente != null){
			//Si ya existe la relacion CURP-correo se procede a validar el numero de intentos por d�a y por tipo de servicio
			nssCorreoExistente = this.obtenerPorCorreo(correo, nssCorreo.getCveIdTipoSolicitud());
		}
		else {
			// No existe ninguna relaci�n del correo capturado
			this.log.debug("No existe registro de CURP-correo");
			codigoOperacion = 1;
		}

		int comparacion = 0;
		if (nssCorreoExistente != null) {
			/*
			 * Se encontr� relaci�n correo-curp, ahora se debe validar
			 * si el CURP capturado es igual al registrado
			 */
			String curpCapturado = nssCorreo.getCurp();
			String curpExistente = nssCorreoExistente.getCurp();

			comparacion = curpCapturado.compareToIgnoreCase(curpExistente);

		} else {
			// No existe ninguna relaci�n del correo capturado
			this.log.debug("No existe registro de CURP-correo");
			codigoOperacion = 1;

		}

			if ( comparacion == 0) {
				this.log.debug("Los CURPs son iguales, se procede a verificar las consultas por periodo");

				Properties prop = new Properties();

				try {
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					InputStream input = classLoader.getResourceAsStream("consulta-NSS-correo.properties");
					prop.load(input);

					int numDiasPeriodo = Integer.parseInt(prop.getProperty("num_dias_por_periodo_vigencia"));
					int numConsultasPeriodo = Integer.parseInt(prop.getProperty("num_consultas_periodo_vigencia"));

					this.log.debug("N\u00famero de d\u00edas por periodo: " + numDiasPeriodo);
					this.log.debug("N\u00famero de consultas maximas por periodo: " + numConsultasPeriodo);


					int diasDiferencia;
					Object [] datosPeriodo = this.solicitudNssCorreoServiceEntity.obtenerSolicitudPorCurpPeriodo(nssCorreo.getCurp(), numDiasPeriodo, nssCorreo.getCveIdTipoSolicitud());
					Integer numConsultasActuales = (Integer) datosPeriodo [0];

					Date fechaUltimaConsulta = (Date) datosPeriodo[1];
					this.log.debug("Fecha ultima consulta: " + fechaUltimaConsulta);

					/*
					 * Se checa si la diferencia entre la �ltima consulta y la
					 * fecha actual cae en los dias definidos para el perido
					 */
					diasDiferencia = Days.daysBetween(new DateTime(fechaUltimaConsulta), new DateTime(new Date())).getDays();
					this.log.debug("Hace " + diasDiferencia + "dia(s) se realiz&oacute; la ultima consulta");


					if (diasDiferencia >= numDiasPeriodo) {
						/*
						 * El periodo ha pasado, por lo tanto, la consulta es v�lida
						 * y se tiene que reiniciar el contador
						 */
						if(nssCorreoExistente != null){ // Si la CURP ya ha sido consultada con ese correo
								codigoOperacion = 2;
						}
					} else {
						/*
						 * Se esta dentro del periodo, por lo tanto, se valida
						 * si el numero de consultas es v�lido - Se cambia por el n�mero de solicitudes por CURP al d�a
						 */

						/*nssCorreoExistente.getNumConsultasPeriodo().intValue();*/

						this.log.debug("Numero de consultas a la fecha: " + numConsultasActuales);

						if (validaContadores) {
							if (numConsultasActuales < numConsultasPeriodo) {
								/*
								 * La consulta es v�lida, ya que es menor al m�ximo
								 * permitido por periodo, se debe incremetar el contador
								 */
								if(nssCorreoExistente != null){
									int diasDeDiferencia = Days.daysBetween(new DateTime(nssCorreoExistente.getFechaConsulta()), new DateTime(new Date())).getDays();

									if(diasDeDiferencia >= numDiasPeriodo){
										codigoOperacion = 2;
									} else {
										codigoOperacion = 3;
									}
								}

							} else {
								this.log.debug("La consulta no es valida ya que se ha alcanzado el maximo permitido por periodo");
								throw new SolicitudNssCorreoException(
										"El N\u00famero de operaciones por periodo ha sido alcanzada ("
												+ numConsultasPeriodo
												+ " operaci\u00f3n(es) cada "
												+ numDiasPeriodo + " d\u00eda(s)) "
												+ ", favor de intentar m\u00e1s tarde.");
							}
						} else {

							if(codigoOperacion != 1){
								codigoOperacion = 3;
							}
						}
					}
				} catch (FileNotFoundException e) {
					this.log.error(e);
				} catch (IOException e) {
					this.log.error(e);
				}
			} else {
				throw new SolicitudNssCorreoException(
						"El correo electr\u00f3nico que captur\u00f3; ya se encuentra registrado en el Instituto relacionado a una CURP distinta a la capturada.");
			}
 
		
		return codigoOperacion;
	}

	@Override
	public void guardarConfirmacion(String correo, String curp, String token) throws SolicitudNssCorreoException {

		SolicitudNssConfirmacion confirmacion = generarSolicitudNssConfirmacion(correo, curp, token, TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());	
			    if(confirmacion!=null)
				{
					this.solicitudNssCorreoServiceEntity.guardarConfirmacion(confirmacion);
				}
     }

	@Override
	public void guardarConfirmacion(String correo, String curp, String token, Long tipoSolicitud) throws SolicitudNssCorreoException {

		   SolicitudNssConfirmacion confirmacion = generarSolicitudNssConfirmacion(correo, curp, token, tipoSolicitud);	
			    if(confirmacion!=null)
				{
					this.solicitudNssCorreoServiceEntity.guardarConfirmacion(confirmacion);
				}
    }

	@Override
	public SolicitudNssConfirmacion recuperarConfirmacion(String curp, Long tipoSolicitud) throws TransformacionException {

		return	this.solicitudNssCorreoServiceEntity.buscarConfirmacionCorreo(curp, null ,tipoSolicitud);

	}

	@Override
	public SolicitudNssConfirmacion recuperarConfirmacion(String curp, String correo, Long tipoSolicitud) throws TransformacionException {

		return	this.solicitudNssCorreoServiceEntity.buscarConfirmacionCorreo(curp,  correo, tipoSolicitud);

	}

	@Override
	public void actualizaConfirmacion(String correo, String curp, String token) {

		SolicitudNssConfirmacion confirmacion = generarSolicitudNssConfirmacion(correo, curp, token, TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());	
	    if(confirmacion!=null)
        {
					this.solicitudNssCorreoServiceEntity.actualizarConfirmacionCorreo(confirmacion);
	    }
	}

	@Override
	public void actualizaConfirmacion(String correo, String curp, String token, Long tipoSolicitud) {

		 SolicitudNssConfirmacion confirmacion = generarSolicitudNssConfirmacion(correo, curp, token, tipoSolicitud);	
		 if(confirmacion!=null)
         {
					this.solicitudNssCorreoServiceEntity.actualizarConfirmacionCorreo(confirmacion);
		 }
	}

	@Override
	public void actualizaConfirmacionVigencia(SolicitudNssConfirmacion model) {

		model.setVigente(1);

		this.solicitudNssCorreoServiceEntity.actualizarConfirmacionCorreoVigencia(model);

	}
	
	@Override
	public List<String> getListaRfcPatronesFisica(List<Long> ids) throws SolicitudNssCorreoException{
		List<String> listaRfcPatrones = null;
		
		try {
			listaRfcPatrones = this.solicitudNssCorreoServiceEntity.getListaRfcPatronesFisica(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar las cl�nicas con los id's Generados");
		}
		
		return listaRfcPatrones;
	}
	
	@Override
	public List<String> getListaRfcPatronesMoral(List<Long> ids) throws SolicitudNssCorreoException{
		List<String> listaRfcPatrones = null;

		try {
			listaRfcPatrones = this.solicitudNssCorreoServiceEntity.getListaRfcPatronesMoral(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar las cl�nicas con los id's Generados");
		}
		
		return listaRfcPatrones;
	}
	
	@Override
	public List<String> getListaRegistroPatronal(List<Long> ids) throws SolicitudNssCorreoException{
		List<String> listaRfcPatrones = null;

		try {
			listaRfcPatrones = this.solicitudNssCorreoServiceEntity.getListaRegistroPatronal(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible consultar las cl�nicas con los id's Generados");
		}
		
		return listaRfcPatrones;
	}

	@Override
	public List<SolicitudNssCorreo> obtenerPorCorreo(String correo) {

		this.log.debug("Se buscan las asociaciones que el correo " + correo
				+ " tiene con las CURP");

		List<SolicitudNssCorreo> nssCorreo = this.solicitudNssCorreoServiceEntity
				.obtenerPorCorreo(correo);

		if (nssCorreo != null) {
			this.log.debug("Se encontro relacion del correo " + correo
					+ " con CURP: " + nssCorreo.size());
		} else {
			this.log.debug("No se encontro registro del correo " + correo);
		}

		return nssCorreo;

	}
	
	@Override
	public List<SolicitudNssCorreo> obtenerPorCurp(String curp) {

		this.log.debug("Se buscan las asociaciones de correos que tiene la curp " + curp);

		List<SolicitudNssCorreo> nssCorreo = this.solicitudNssCorreoServiceEntity
				.obtenerPorCurp(curp);

		if (nssCorreo != null) {
			this.log.debug("Se encontro relacion de la CURP " + curp
					+ " con los correos: " + nssCorreo.size());
		} else {
			this.log.debug("No se encontro registro de la CURP " + curp);
		}

		return nssCorreo;

	}
	
	@Override
	public List<SolicitudNssCorreo> obtenerPorCurpCorreosActivos(String curp) {

		this.log.debug("Se buscan las asociaciones de correos que tiene la curp " + curp);

		List<SolicitudNssCorreo> nssCorreo = this.solicitudNssCorreoServiceEntity
				.obtenerPorCurpCorreosActivos(curp);

		if (nssCorreo != null) {
			this.log.debug("Se encontro relacion de la CURP " + curp
					+ " con los correos Activos: " + nssCorreo.size());
		} else {
			this.log.debug("No se encontro registro de la CURP " + curp);
		}

		return nssCorreo;

	}

	private SolicitudNssConfirmacion generarSolicitudNssConfirmacion(String correo, String curp, String token, Long tipoSolicitud)
	{

		SolicitudNssConfirmacion confirmacion = null;
		boolean esCorreoValido = this.esCorreoValido(correo);
		if (!esCorreoValido) {
			log.warn(":: Error correo no valido, " + correo);
			
		}else{
		   confirmacion = new SolicitudNssConfirmacion();
		   log.info(":: Correo valido, " + correo);
		   confirmacion.setCorreo(new CorreoElectronico());
		   confirmacion.getCorreo().setCorreo(correo);
		   confirmacion.setCurp(curp);
		   confirmacion.setToken(token);
		   confirmacion.setCveIdTipoSolicitud(tipoSolicitud);
	       confirmacion.setFechaTokenActualizacion(new Date());
		   confirmacion.setVigente(0);
		}
    
		return confirmacion;
	}

	public boolean esDominioPermitido(String correo) {

		log.info("Correo recibido: " + correo);

		if (correo == null || !correo.contains("@")) {
			return false;
		}
		// Validar si el dominio esta bloqueado, si se encuentra en base de datos regresa false ya que no esta permitido
		return solicitudNssCorreoServiceEntity.consultaDominioCorreo(correo.substring(correo.indexOf("@") + 1).toLowerCase());
	}
	
	public boolean esIgualAlAnterior(String correo, String curp) {

		log.info("VERIFICANDO DUPLICIDAD DEL CORREO: " + correo);

		if (correo == null || !correo.contains("@")) {
			return false;
		}
		// Validar si el correo por actualizar no esta duplicado
		return solicitudNssCorreoServiceEntity.consultaCorreoDuplicado(correo.toLowerCase(), curp);
	}
	
	@Override
	public String getToken() throws SolicitudNssCorreoException{
	
		String token;
		try {
			token = this.solicitudNssCorreoServiceEntity.getToken();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible obtener el token");
		}
		
		return token;
		
	}
	
	@Override
	public void saveDitActualizacionCorreo(Long tramiteId,TramiteActualizacionCorreo xml) throws SolicitudNssCorreoException{
		log.debug("Entro al EJB correctamente " );
		try {
			this.solicitudNssCorreoServiceEntity.saveDitActualizacionCorreo(tramiteId, xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SolicitudNssCorreoException("No fue posible guardar el tramite de actualizacion de correo electronico");
		}
	}
    
    private boolean esCorreoValido(String correo){
		
		log.debug("::: En esCorreoValido, validando: " + correo);

	    if (correo == null || correo.trim().isEmpty()) {
	    	log.warn("Correo nulo o vacio");
	        return false;
	    }

	    int atIndex = correo.indexOf('@');

	    if (atIndex <= 0 || atIndex == correo.length() - 1) {
	    	log.warn("Correo con formato invalido: " + correo);
	        return false;
	    }

	    String parteLocal = correo.substring(0, atIndex).toUpperCase();
	    if (parteLocal.contains("-") || parteLocal.contains("+")) {
	    	log.info("El correo: " + correo + " contiene '+' o '-'");
	            return false;
	        }

	    int puntos = 0;
	    for (int i = 0; i < parteLocal.length(); i++) {
	        if (parteLocal.charAt(i) == '.') {
	            puntos++;
	            if (puntos > 2) {
	            	log.info("El correo tiene mas de dos puntos en la parte local: " + correo);
	                return false;
	            }
	        }
	    }

	    if (CURP_PATTERN.matcher(parteLocal).find()) {
	    	log.info("Correo con patron de CURP detectado: " + correo);
	        return false;
	    }


		if(CORREO_HEXADECIMAL_PATTERN.matcher(correo).find()){
			log.info("Correo con patron hexadecimal detectado: " + correo);
	        return false;
		}
	    
	    boolean esDominioPermitido;
	    
        try {
            esDominioPermitido = this.esDominioPermitido(correo);
		    if (!esDominioPermitido) {
		    	log.info(":: El dominio del correo electr&oacute;nico es inv&aacute;lido. Intente con otro, " + correo);
		    	return false;
		    }
		} catch (Exception e) {
			log.warn(":: Error correo no valido, " + correo, e);
			return false;
		}
	    
        log.info(":: Correo valido, " + correo);
        
	    return true ;
	}

	private static final Pattern CURP_PATTERN = Pattern.compile(
	        "[A-Z][AEIOU][A-Z]{2}\\d{6}[HM][A-Z]{5}[0-9A-Z]\\d"
	);
	
	private static final Pattern CORREO_HEXADECIMAL_PATTERN = Pattern.compile(
		"^[0-9a-f]{18,64}@[^@\\s]+$",
		Pattern.CASE_INSENSITIVE
	);

}
