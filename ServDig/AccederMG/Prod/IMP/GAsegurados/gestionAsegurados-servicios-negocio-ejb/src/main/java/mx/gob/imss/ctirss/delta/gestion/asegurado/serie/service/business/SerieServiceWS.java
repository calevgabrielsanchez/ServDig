package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConRPAsignado;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SujetoObligadoInexistente;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.AsignacionMasivaHandlerLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.EstadoAsignacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;

@WebService(name = "asignacionSerieService",
            portName = "asignacionSeriePort",
            serviceName = "asignacionSerieService",
            targetNamespace = "http://mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service/")
@Stateless
public class SerieServiceWS extends AbstractServiceBusiness implements
		SerieServiceWSRemote {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@EJB(mappedName = "serieServiceBusiness")
	private transient SerieServiceBusinessRemote serieServiceBusiness;
	@EJB(mappedName = "ruleServiceBusiness")
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@EJB
	private transient AsignacionMasivaHandlerLocal asignacionMasivaHandler;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@Override
	@WebMethod
	@WebResult(name="generarAseguradoResponse" , targetNamespace= "http://mx.gob.imss.ctirss.delta.model.asegurado")
	public EstadoAsignacion generarAsegurado(AltaDatosAsignacionNSSType altaDatosAsignacionNSSType) {
		EstadoAsignacion estado = new EstadoAsignacion();

		estado.setCurp(altaDatosAsignacionNSSType.getCurp());
		estado.setNombre(altaDatosAsignacionNSSType.getNombre());
		estado.setApellidoPaterno(altaDatosAsignacionNSSType.getApellidoPaterno());
		estado.setApellidoMaterno(altaDatosAsignacionNSSType.getApellidoMaterno());

		try {

			Fisica asegurado = new Fisica();
			asegurado.setNombre(altaDatosAsignacionNSSType.getNombre());
			asegurado.setPrimerApellido(altaDatosAsignacionNSSType.getApellidoPaterno());
			asegurado.setSegundoApellido(altaDatosAsignacionNSSType.getApellidoMaterno());
			asegurado.setCurp(altaDatosAsignacionNSSType.getCurp());

			//Procesamos la fecha de nacimiento
			StringBuffer brFecha = new StringBuffer();
			brFecha.append(altaDatosAsignacionNSSType.getDiaNacimiento());
			brFecha.append("/");
			brFecha.append(altaDatosAsignacionNSSType.getMesNacimiento());
			brFecha.append("/");
			brFecha.append(altaDatosAsignacionNSSType.getAnioNacimiento());
			Date fechaNacimiento = sdf.parse(brFecha.toString());
			asegurado.setFechaNacimiento(fechaNacimiento);
			Sexo sexo = new Sexo();
			sexo.setIdSexo(altaDatosAsignacionNSSType.getSexo());
			asegurado.setSexo(sexo);
			EntidadFederativa lugarNacimiento = new EntidadFederativa();
			lugarNacimiento.setClave(""+altaDatosAsignacionNSSType.getLugarNacimiento());
			asegurado.setLugarNacimiento(lugarNacimiento);

			String idPublicador = altaDatosAsignacionNSSType.getRegistroPatronal();
			log.info("---> Registro Patronal: " + idPublicador);
			SujetoObligado sujetoObligado = null;
			if (StringUtils.isNotBlank(idPublicador)) {
				String[] idPublicadorArray = idPublicador.split("-");

				if (idPublicadorArray != null && idPublicadorArray.length >= 3) {
					String idRegistroPatronal = idPublicadorArray[0];

					sujetoObligado = ruleServiceBusiness
							.validarNumeroDeRegistroPatronal(idRegistroPatronal);
				} else {
					throw new GestionPatronalBusinessException("registroPatronal nulo o vac\00EDo");
				}
			} else {
				throw new GestionPatronalBusinessException("registroPatronal nulo o vac\00EDo");
			}

			/*
			 * Por cada estudiante recibido se busca la UMF a traves del codigo
			 * postal recibido. Primero se busca el asentamiento relacionado al
			 * codigo postal y después la UMF a traves del asentamiento.
			 */
			CodigoPostal codigo = new CodigoPostal();
			codigo.setCodigoPostal(altaDatosAsignacionNSSType.getCodigoPostal());
			List<Asentamiento> asentamientos = null;

			try {
				// Se buscan los asentamientos
				asentamientos = this.domicilioServiceBusiness
						.getAsentamientoPorCodigoPosta(codigo);

				Asentamiento asentamiento = asentamientos.get(0);

				List<UnidadMedicaFamiliar> umfs = this.domicilioServiceBusiness
						.getUmfByAsentamientoDomicilio(asentamiento);

				// Se toma la primer UMF de las encontradas
				UnidadMedicaFamiliar umf = umfs.get(0);
				
				this.log.debug("Al estudiante [curp:" + asegurado.getCurp()
						+ "] se le asigno la UMF [id:" + umf.getIdUMF() + "]");
				
				asegurado.setUmf(umf);
			} catch (DomicilioNoLocalizadoException e) {
				this.log.error(e);
				this.log.warn("No se pudo localizar asentamiento relacionado al codigo postal ["
						+ codigo.getCodigoPostal() + "] del estudiante");
			} catch (UmfNoLocalizadaException e) {
				this.log.error(e);
				this.log.warn("No se encontraron UMFs para el codigo postal ["
						+ codigo.getCodigoPostal() + "] del estudiante");
			}
			
			Asegurado aseguradoAsignado = serieServiceBusiness
					.generarAsegurado(asegurado, sujetoObligado);

			estado.setFechaAlta(aseguradoAsignado.getFechaAlta());
			estado.setIdAsegurado(aseguradoAsignado.getIdAsegurado());
			estado.setNssAsignado(aseguradoAsignado.getAsignacionNSS().getNss());
			estado.setExitoAlta(true);
		} catch (ArgumentosInvalidosException e) {
			estado.setMensajeError(e.getMessage());
		} catch (DomicilioNoValidoException e) {
			estado.setMensajeError(e.getMessage());
		} catch (ClienteWebserviceSatRfcException e) {
			estado.setMensajeError(e.getMessage());
		} catch (ClienteWebserviceRenapoCurpException e) {
			estado.setMensajeError(e.getMessage());
		} catch (NivelDeAsignacionSerieIndefinidoException e) {
			estado.setMensajeError(e.getMessage());
		} catch (SeriesNoLocalizadasException e) {
			estado.setMensajeError(e.getMessage());
		} catch (ErrorAlActivarSerieException e) {
			estado.setMensajeError(e.getMessage());
		} catch (PersonaNoEncontradaException e) {
			estado.setMensajeError(e.getMessage());
		} catch (AseguradoConRPAsignado e) {
			estado.setMensajeError(e.getMessage());
		} catch (SujetoObligadoInexistente e) {
			estado.setMensajeError(e.getMessage());
		} catch (GestionPatronalBusinessException e) {
			estado.setMensajeError(e.getMessage());
		} catch (Exception e) {
			estado.setMensajeError(e.getMessage());
			estado.setErrorNoControlado(true);
		}

		return estado;
	}

	@Override
	@WebMethod
	@WebResult(name = "publicarResultadoAsignacionResponse", targetNamespace = "http://mx.gob.imss.ctirss.delta.model.asegurado")
	public Boolean publicarResultadoAsignacion(Integer numExitos,
			Integer numErrores, String idPublicador) {
		boolean isPublicacionExitosa = false;

		if (StringUtils.isNotBlank(idPublicador) && numExitos != null
				&& numErrores != null) {
			try {
				String[] idPublicadorArray = idPublicador.split("-");

				if (idPublicadorArray != null && idPublicadorArray.length >= 3) {
					String idRegistroPatronal = idPublicadorArray[0];
					Long cveIdUsuario = Long.valueOf(idPublicadorArray[1]);
					String usuario = idPublicadorArray[2];

					asignacionMasivaHandler.publicarResultadoParcialProcesamiento(numExitos,
									numErrores, idRegistroPatronal, cveIdUsuario,
									usuario);

					isPublicacionExitosa = true;
				}
			} catch (IOException e) {
				log.error(e);
			}
		}

		return isPublicacionExitosa;
	}

	@Override
	@WebMethod
	@WebResult(name = "publicarDetalleResultadoAsignacionResponse", targetNamespace = "http://mx.gob.imss.ctirss.delta.model.asegurado")
	public Boolean publicarDetalleResultadoAsignacion(EstadoAsignacion estado,
			String idPublicador) {
		boolean isPublicacionExitosa = false;

		if (StringUtils.isNotBlank(idPublicador)) {
			try {
				String[] idPublicadorArray = idPublicador.split("-");

				if (idPublicadorArray != null && idPublicadorArray.length >= 3) {
					String idRegistroPatronal = idPublicadorArray[0];
					Long cveIdUsuario = Long.valueOf(idPublicadorArray[1]);
					String usuario = idPublicadorArray[2];

					asignacionMasivaHandler.publicarRegistroIndividualProcesamiento(estado,
									idRegistroPatronal, cveIdUsuario, usuario);

					isPublicacionExitosa = true;
				}
			} catch (IOException e) {
				log.error(e);
			}
		}

		return isPublicacionExitosa;
	}

	@Override
	@WebMethod
	@WebResult(name = "publicarFinProcesamientoResponse", targetNamespace = "http://mx.gob.imss.ctirss.delta.model.asegurado")
	public Boolean publicarFinProcesamiento(String idPublicador) {
		boolean isPublicacionExitosa = false;

		if (StringUtils.isNotBlank(idPublicador)) {
			try {
				String[] idPublicadorArray = idPublicador.split("-");

				if (idPublicadorArray != null && idPublicadorArray.length >= 3) {
					String idRegistroPatronal = idPublicadorArray[0];
					Long cveIdUsuario = Long.valueOf(idPublicadorArray[1]);
					String usuario = idPublicadorArray[2];

					asignacionMasivaHandler.publicarFinProcesamiento(
							idRegistroPatronal, cveIdUsuario, usuario);
					isPublicacionExitosa = true;
				}
			} catch (IOException e) {
				log.error(e);
			}
		}

		return isPublicacionExitosa;
	}
}
