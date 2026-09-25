package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
			throw new SolicitudNssCorreoException("No fue posible consultar las clínicas con los id's Generados");
		}
		
		return resultadosUMF;
		
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

		if (nssCorreoExistente != null) {
			nssCorreoExistente = this.obtenerPorCorreo(correoCapturado);
		} else {
			this.log.debug("No existe registro de CURP-correo");
		}

		int comparacion = 0;
		if (nssCorreoExistente != null) {
			SolicitudNssCorreo curpExistente = nssCorreoExistente.get(0);
			comparacion = curp.compareToIgnoreCase(curpExistente.getCurp());
		} else {
			// No existe ninguna relación del correo capturado
			this.log.debug("No existe registro de CURP-correo");
		}

		if (comparacion == 0) {
			if (nssCurpExistenteActivos != null) {
				if (nssCurpExistenteActivos.size() == 1) {
					nssEncontrado = nssCurpExistenteActivos.get(0);
					log.debug("---OBJETO DE CORREO RECUPERADO---: " + nssEncontrado);
					correoEncontrado = nssEncontrado.getCorreo().getCorreo();
					log.debug("El correo electrónico encontrado es " + correoEncontrado);
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
						log.debug("los correos de la lista coinciden con el capturado");
					}
				}
			} else {
				log.debug("no existen correos asociados al CURP");
			}
		} else {
			throw new SolicitudNssCorreoException(
					"El correo electrónico que capturó ya se encuentra registrado en el Instituto relacionado a una CURP distinta a la capturada.");
		}

		log.debug("el código de actualización es: " + codigoActualizacion);

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
		 * Primer se busca si el correo capturado ya cuenta con relaciï¿½n hacia
		 * algï¿½n CURP
		 */
		String correo = nssCorreo.getCorreo().getCorreo();
		SolicitudNssCorreo nssCorreoExistente = this.obtenerPorCorreo(correo, nssCorreo.getCveIdTipoSolicitud());
		
		if (nssCorreoExistente != null){
			//Si ya existe la relacion CURP-correo se procede a validar el numero de intentos por dï¿½a y por tipo de servicio
			nssCorreoExistente = this.obtenerPorCorreo(correo, nssCorreo.getCveIdTipoSolicitud());
		}
		else {
			// No existe ninguna relaciï¿½n del correo capturado
			this.log.debug("No existe registro de CURP-correo");
			codigoOperacion = 1;
		}

		int comparacion = 0;
		if (nssCorreoExistente != null) {
			/*
			 * Se encontrï¿½ relaciï¿½n correo-curp, ahora se debe validar
			 * si el CURP capturado es igual al registrado
			 */
			String curpCapturado = nssCorreo.getCurp();
			String curpExistente = nssCorreoExistente.getCurp();

			comparacion = curpCapturado.compareToIgnoreCase(curpExistente);

		} else {
			// No existe ninguna relaciï¿½n del correo capturado
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

					this.log.debug("N&uacute;mero de dï¿½as por periodo: " + numDiasPeriodo);
					this.log.debug("N&uacute;mero de consultas maximas por periodo: " + numConsultasPeriodo);


					int diasDiferencia;
					Object [] datosPeriodo = this.solicitudNssCorreoServiceEntity.obtenerSolicitudPorCurpPeriodo(nssCorreo.getCurp(), numDiasPeriodo, nssCorreo.getCveIdTipoSolicitud());
					Integer numConsultasActuales = (Integer) datosPeriodo [0];

					Date fechaUltimaConsulta = (Date) datosPeriodo[1];
					this.log.debug("Fecha ï¿½ltima consulta: " + fechaUltimaConsulta);

					/*
					 * Se checa si la diferencia entre la ï¿½ltima consulta y la
					 * fecha actual cae en los dias definidos para el perido
					 */
					diasDiferencia = Days.daysBetween(new DateTime(fechaUltimaConsulta), new DateTime(new Date())).getDays();
					this.log.debug("Hace " + diasDiferencia + "d&iacute;a(s) se realiz&oacute; la &uacute;ltima consulta");


					if (diasDiferencia >= numDiasPeriodo) {
						/*
						 * El periodo ha pasado, por lo tanto, la consulta es vï¿½lida
						 * y se tiene que reiniciar el contador
						 */
						if(nssCorreoExistente != null){ // Si la CURP ya ha sido consultada con ese correo
								codigoOperacion = 2;
						}
					} else {
						/*
						 * Se esta dentro del periodo, por lo tanto, se valida
						 * si el numero de consultas es vï¿½lido - Se cambia por el nï¿½mero de solicitudes por CURP al dï¿½a
						 */

						/*nssCorreoExistente.getNumConsultasPeriodo().intValue();*/

						this.log.debug("Nï¿½mero de consultas a la fecha: " + numConsultasActuales);

						if (validaContadores) {
							if (numConsultasActuales < numConsultasPeriodo) {
								/*
								 * La consulta es vï¿½lida, ya que es menor al mï¿½ximo
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
								this.log.debug("La consulta no es vï¿½lida ya que se ha alcanzado el mï¿½ximo permitido por periodo");
								throw new SolicitudNssCorreoException(
										"El n&uacute;mero de operaciones por periodo ha sido alcanzada ("
												+ numConsultasPeriodo
												+ " operaci&oacute;n(es) cada "
												+ numDiasPeriodo + " d&iacute;a(s)) "
												+ ", favor de intentar m&aacute;s tarde.");
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
						"El correo electr&oacute;nico que captur&oacute; ya se encuentra registrado en el Instituto relacionado a una CURP distinta a la capturada.");
			}
 
		
		return codigoOperacion;
	}

	@Override
	public void guardarConfirmacion(String correo, String curp, String token) throws SolicitudNssCorreoException {

		this.solicitudNssCorreoServiceEntity
				.guardarConfirmacion(generarSolicitudNssConfirmacion(correo, curp, token, TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue()));
	}

	@Override
	public void guardarConfirmacion(String correo, String curp, String token, Long tipoSolicitud) throws SolicitudNssCorreoException {

		this.solicitudNssCorreoServiceEntity.guardarConfirmacion(generarSolicitudNssConfirmacion(correo, curp, token,tipoSolicitud));
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

		this.solicitudNssCorreoServiceEntity.actualizarConfirmacionCorreo(
				generarSolicitudNssConfirmacion(correo, curp, token, TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue()));

	}

	@Override
	public void actualizaConfirmacion(String correo, String curp, String token, Long tipoSolicitud) {

		this.solicitudNssCorreoServiceEntity.actualizarConfirmacionCorreo(generarSolicitudNssConfirmacion(correo, curp, token, tipoSolicitud));

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
			throw new SolicitudNssCorreoException("No fue posible consultar las clínicas con los id's Generados");
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
			throw new SolicitudNssCorreoException("No fue posible consultar las clínicas con los id's Generados");
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
			throw new SolicitudNssCorreoException("No fue posible consultar las clínicas con los id's Generados");
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

	private SolicitudNssConfirmacion generarSolicitudNssConfirmacion(String correo, String curp, String token, Long tipoSolicitud){

		SolicitudNssConfirmacion confirmacion = new SolicitudNssConfirmacion();
		confirmacion.setCorreo(new CorreoElectronico());
		confirmacion.getCorreo().setCorreo(correo);
		confirmacion.setCurp(curp);
		confirmacion.setToken(token);
		confirmacion.setCveIdTipoSolicitud(tipoSolicitud);
		confirmacion.setFechaTokenActualizacion(new Date());
		confirmacion.setVigente(0);

		return confirmacion;
	}
}
