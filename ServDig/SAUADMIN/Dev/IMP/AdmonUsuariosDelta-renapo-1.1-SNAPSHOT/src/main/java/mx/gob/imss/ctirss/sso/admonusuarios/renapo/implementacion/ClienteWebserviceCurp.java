package mx.gob.imss.ctirss.sso.admonusuarios.renapo.implementacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoActa;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ClavesRenapo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SexoEnum;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.CurpKioscosBean;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.ParametrosConsulta;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ClienteWebserviceCurp {

	private final Logger log = Logger.getLogger(ClienteWebserviceCurp.class);

	private static int serviceTimeOut = 30000;

	private final transient DateFormat formatoFechaRenapo = 
			new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));

	public Fisica buscarPersonaFisicaPorDatosBasicosEnRenapo(String sNombres, String sPrimerApellido,
			String sSegundoApellido, int iIdSexo, Date fechaNacimiento, int iIdEntidadFederativa)
			throws ClienteWebserviceRenapoCurpException {
		Fisica fisica = null;
		ParametrosConsulta entrada = new ParametrosConsulta();
		entrada.setNombres(sNombres);
		entrada.setApellidoPaterno(sPrimerApellido);
		entrada.setApellidoMaterno(sSegundoApellido);
		if (iIdSexo == SexoEnum.HOMBRE.getCodigo().intValue()) {
			entrada.setSexo("H");
		} else if (iIdSexo == SexoEnum.MUJER.getCodigo().intValue()) {
			entrada.setSexo("M");
		} else if (iIdSexo == SexoEnum.NO_BINARIO.getCodigo().intValue()) {
			entrada.setSexo("X");
		}
		entrada.setFechNac(this.formatoFechaRenapo.format(fechaNacimiento));
		for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) ClavesRenapo
				.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.entrySet()) {
			if (((Integer) entry.getValue()).intValue() == iIdEntidadFederativa) {
				entrada.setCveEntidadNac(entry.getKey());
				break;
			}
		}
		if (entrada.getCveEntidadNac() == null) {
			entrada.setCveEntidadNac("");
		}
		CurpKioscosBean respuesta = consultaCURP(entrada);
		fisica = recuperaDatosBasicosRenapo(respuesta);
		recuperaInformacionDocumentoProbatorio(respuesta, fisica);
		return fisica;
	}

	public Fisica buscarPersonaFisicaPorCurpEnRenapo(String curp) throws ClienteWebserviceRenapoCurpException {
		Fisica fisica = null;
		CurpKioscosBean respuesta = consultaDatosCURP(curp);
		fisica = recuperaDatosBasicosRenapo(respuesta);
		recuperaInformacionDocumentoProbatorio(respuesta, fisica);
		return fisica;
	}
	
	private CurpKioscosBean consultaDatosCURP(String entrada) throws ClienteWebserviceRenapoCurpException {
		CurpKioscosBean response = null;
		try {
			this.log.info("::: ClienteWebserviceCurp. CURP: " + entrada + ", timestamp... " + new Date());
			DatosWebServiceCurp thread = new DatosWebServiceCurp();
			thread.setCurp(entrada);
			thread.start();
			this.log.info("::: ClienteWebserviceCurp. Se establece el tiempo del timeout del thread = "
					+ serviceTimeOut + ", thread name: " + thread.getName() + ", thread id: "
					+ Thread.currentThread().getId());
			thread.join(serviceTimeOut);
			this.log.info("::: ClienteWebserviceCurp. Se recupera el control del proceso desde el thread. "
					+ new Date() + ", CURP: " + entrada);
			if (thread.isExisteErrorServicio()) {
				this.log.error("::: Error por el thread...");
				throw new ClienteWebserviceRenapoCurpException();
			}
			if (thread.isAlive()) {
				this.log.error("::: ClienteWebserviceCurp. El thread sigue esperando la respuesta de RENAPO. CURP: " + entrada);
				thread.interrupt();
				this.log.error("::: ClienteWebserviceCurp. El thread se ha interrumpido y se generara un ClienteWebserviceRenapoCurpException. "
						+ "CURP: " + entrada);
				throw new ClienteWebserviceRenapoCurpException(Integer.valueOf(10002));
			}

			this.log.info("::: ClienteWebserviceCurp. El thread termino satisfactoriamente la consulta a RENAPO dentro del timeout especificado. "
					+ "CURP: "+ entrada);
			response = thread.getRespuesta();

			this.log.info("... Respuesta ::: " + response);
			this.log.info("... Respuesta.estatus ::: " + response.getStatusOper());
			this.log.info("... Respuesta.codigo  ::: " + response.getCodigoError());
			this.log.info("... Respuesta.mensaje ::: " + response.getMessage());

			if (!response.getStatusOper().equals("EXITOSO")) {
				this.log.error("::: Codigo error RENAPO => [" + response.getCodigoError() + "] msg [" + response.getMessage() + "]");
				if (response.getCodigoError() == -1)
					throw new ClienteWebserviceRenapoCurpException(
							"El servicio Web de RENAPO no se encuentra disponible.", Integer.valueOf(10003));
				if (response.getCodigoError() == 6) {
					this.log.error("... Se obtuvo codigo de error 6");
				} else {
					throw new ClienteWebserviceRenapoCurpException(response.getMessage(), Integer.valueOf(10003));
				}
			}
			thread = null;
		} catch (Exception ex) {
			this.log.error("::: ClienteWebserviceCurp. Se genero un error al accesar el webservice... " + ex.getMessage());
			ex.printStackTrace();
			throw new ClienteWebserviceRenapoCurpException(Integer.valueOf(10002));
		}
		this.log.debug("CurpKioscosBean: " + response);
		return response;
	}

	public CurpKioscosBean consultaCURP(ParametrosConsulta entrada) throws ClienteWebserviceRenapoCurpException {
		CurpKioscosBean response = null;
		try {
			this.log.info("::: ClienteWebservice. Entrada: " + entrada + ", timestamp... " + new Date());
			DatosWebServiceParametrosCurp thread = new DatosWebServiceParametrosCurp();
			thread.setParametrosEntrada(entrada);
			thread.start();
			this.log.info("::: ClienteWebservice. Se establece el tiempo del timeout del thread = "
					+ serviceTimeOut + ", thread name: " + thread.getName() + ", thread id: "
					+ Thread.currentThread().getId());
			thread.join(serviceTimeOut);
			this.log.info("::: ClienteWebservice. Se recupera el control del proceso desde el thread. "
					+ new Date());
			if (thread.isExisteErrorServicio()) {
				this.log.error("::: Error por el thread...");
				throw new ClienteWebserviceRenapoCurpException();
			}
			if (thread.isAlive()) {
				this.log.error("::: ClienteWebservice. El thread sigue esperando la respuesa de RENAPO");
				thread.interrupt();
				this.log.error("::: ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceRenapoCurpException");
				throw new ClienteWebserviceRenapoCurpException(Integer.valueOf(10002));
			}
			this.log.info("::: ClienteWebservice. El thread termino satisfactoriamente la consulta a RENAPO dentro del timeout especificado");
			response = thread.getRespuesta();

			this.log.info("... Respuesta ::: " + response);
			this.log.info("... Respuesta.estatus ::: " + response.getStatusOper());
			this.log.info("... Respuesta.codigo  ::: " + response.getCodigoError());
			this.log.info("... Respuesta.mensaje ::: " + response.getMessage());

			if (!response.getStatusOper().equals("EXITOSO")) {
				this.log.error("::: Codigo error RENAPO => [" + response.getCodigoError() + "] msg [" + response.getMessage() + "]");
				if (response.getCodigoError() == -1)
					throw new ClienteWebserviceRenapoCurpException(
							"El servicio Web de RENAPO no se encuentra disponible.", Integer.valueOf(10003));
				if (response.getCodigoError() == 6) {
					this.log.error("... Se obtuvo codigo de error 6");
				} else {
					throw new ClienteWebserviceRenapoCurpException(response.getMessage(), Integer.valueOf(10003));
				}
			}
			thread = null;
		} catch (Exception ex) {
			this.log.error("::: ClienteWebservice. Se genero un error al accesar el webservice... " + ex.getMessage());
			ex.printStackTrace();
			throw new ClienteWebserviceRenapoCurpException(Integer.valueOf(10002));
		}
		this.log.debug("CurpKioscosBean: " + response);
		return response;
	}

	private Fisica recuperaDatosBasicosRenapo(CurpKioscosBean respuesta) {
		Fisica persona = null;
		if (respuesta != null && respuesta.getMessage().equals("")) {
			persona = new Fisica();
			persona.setNombre(respuesta.getNombres());
			persona.setPrimerApellido(respuesta.getApellido1());
			persona.setSegundoApellido(respuesta.getApellido2());
			persona.setCurp(respuesta.getCurp());
			if (respuesta.getSexo().equalsIgnoreCase("HOMBRE")) {
				persona.getSexo().setIdSexo(SexoEnum.HOMBRE.getCodigo());
			} else if (respuesta.getSexo().equalsIgnoreCase("MUJER")) {
				persona.getSexo().setIdSexo(SexoEnum.MUJER.getCodigo());
			} else if (respuesta.getSexo().equalsIgnoreCase("NO BINARIO")) {
				persona.getSexo().setIdSexo(SexoEnum.NO_BINARIO.getCodigo());
			}
			persona.getSexo().setDescripcion(respuesta.getSexo());
			persona.setFechaNacimientoFormateada(respuesta.getFechNac());
			String cveEntFedNac = ((Integer) ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP
					.get(respuesta.getCveEntidadNac())).toString();
			persona.getLugarNacimiento().setClave(StringUtils.leftPad(cveEntFedNac, 2, '0'));
			persona.setEstatusRenapo(respuesta.getDesEstatusCURP());
			persona.setCveEstatusRenapo(respuesta.getEstatusCURP());
			persona.getLugarNacimiento().setNombre(respuesta.getDesEntidadNac());
			if (respuesta.getFechNac() != null && !respuesta.getFechNac().equals("")) {
				try {
					persona.setFechaNacimiento(this.formatoFechaRenapo.parse(respuesta.getFechNac()));
				} catch (ParseException pe) {
					this.log.error("... Error parse: " + pe.getMessage());
					pe.printStackTrace();
				}
			}
		}
		return persona;
	}

	private Fisica recuperaInformacionDocumentoProbatorio(CurpKioscosBean respuesta, Fisica persona) {
		if (persona != null) {
			asignarPais(respuesta, persona);
			if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
					.ACTA_NACIMIENTO.getValor().intValue()) {
				TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
				tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(TipoDocumentoProbatorioRenapoEnum
						.ACTA_NACIMIENTO.getValor());
				tipoDocumentoProbatorio.setDescripcion(TipoDocumentoProbatorioRenapoEnum
						.ACTA_NACIMIENTO.getDescripcion());
				Nacimiento actaNacimiento = new Nacimiento();
				actaNacimiento.setAnio(Integer.valueOf(respuesta.getAnioReg()));
				actaNacimiento.setTomo(String.valueOf(respuesta.getTomo()));
				actaNacimiento.setCrip(String.valueOf(respuesta.getCRIP()));
				actaNacimiento.setNoFoja(String.valueOf(respuesta.getFoja()));
				actaNacimiento.setNoLibro(String.valueOf(respuesta.getLibro()));
				actaNacimiento.setNoActa(String.valueOf(respuesta.getNumActa()));
				actaNacimiento.setNoJuzgado("0");
				Municipio municipio = new Municipio();
				corregirMunicipioInvalidoRENAPO(respuesta, municipio);
				actaNacimiento.setMunicipio(municipio);
				TipoActa tipoActa = new TipoActa();
				tipoActa.setIdTipoActa(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor());
				tipoActa.setDescripcion(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
				actaNacimiento.setTipoActa(tipoActa);
				Documento documento = new Documento();
				documento.setCveIdDocumento(DocumentosEnum.ACTA_NACIMIENTO.getId());
				documento.setDesDocumento(DocumentosEnum.ACTA_NACIMIENTO.getDescripcion());
				DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
				documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId());
				documentoPorTipo.setDocumento(documento);
				actaNacimiento.setDocumentoPorTipo(documentoPorTipo);
				persona.setActaNacimiento(actaNacimiento);

			} else {
				CURP docProbatorio = new CURP();
				docProbatorio.setCurp(persona.getCurp());
				EntidadFederativa entidadFederativa = new EntidadFederativa();
				entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getCveEntidadNac()), 2, '0'));
				entidadFederativa.setNombre(
						((Integer) ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(entidadFederativa.getClave()))
								.toString());
				if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.DOCUMENTO_MIGRATORIO.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(TipoDocumentoProbatorioRenapoEnum
							.DOCUMENTO_MIGRATORIO.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum
							.DOCUMENTO_MIGRATORIO.getDescripcion());
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getNumRegExtranjeros()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.DOCUMENTO_MIGRATORIO.getId());
					documento.setDesDocumento(DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setDocumentoMigratorio(docProbatorio);

				} else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.CARTA_NATURALIZACION.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(TipoDocumentoProbatorioRenapoEnum
							.CARTA_NATURALIZACION.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(
							TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getDescripcion());
					docProbatorio.setAnioRegistro(Long.valueOf(respuesta.getAnioReg()));
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getFolioCarta()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.CARTA_NATURALIZACION.getId());
					documento.setDesDocumento(DocumentosEnum.CARTA_NATURALIZACION.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setCartaNaturalizacion(docProbatorio);

				} else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.NUMERO_UNICO_DE_EXTRANJERO.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(
							TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(
							TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId());
					documento.setDesDocumento(DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setNumeroUnicoExtranjero(docProbatorio);

				} else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(TipoDocumentoProbatorioRenapoEnum
							.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(
							TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());
					docProbatorio.setAnioRegistro(Long.valueOf(respuesta.getAnioReg()));
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getFolioCarta()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId());
					documento.setDesDocumento(DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setCertificadoNacionalidadMexicana(docProbatorio);

				} else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(
							TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(
							TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId());
					documento.setDesDocumento(DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setOficioSolicitanteRefugiado(docProbatorio);

				} else if (respuesta.getDocProbatorio() == TipoDocumentoProbatorioRenapoEnum
						.FORMA_MIGRATORIA_TURISTA.getValor().intValue()) {
					docProbatorio.setNumTipoDocumento(Long.valueOf(
							TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().longValue()));
					docProbatorio.setDescripcionTipoDocumento(
							TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
					docProbatorio.setNumFolioExtranjero(String.valueOf(respuesta.getCRIP()));
					Documento documento = new Documento();
					documento.setCveIdDocumento(DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId());
					documento.setDesDocumento(DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion());
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId());
					documentoPorTipo.setDocumento(documento);
					docProbatorio.setDocumentoPorTipo(documentoPorTipo);
					persona.setFormaMigratoriaTurista(docProbatorio);
				}
			}
		}
		return persona;
	}

	private void corregirMunicipioInvalidoRENAPO(CurpKioscosBean respuesta, Municipio municipio) {
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		if (respuesta.getNumEntidadReg() > 32) {
			entidadFederativa.setNombre("ENTIDAD INVALIDA RENAPO");
			entidadFederativa.setClave("97");
			municipio.setNombre("MUNICIPIO INVALIDO RENAPO");
			municipio.setClave("999");
		} else if (respuesta.getCveMunicipioReg() == 999) {
			entidadFederativa.setNombre(respuesta.getDesEntidadRegistro());
			entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getNumEntidadReg()), 2, '0'));
			municipio.setNombre("MUNICIPIO INVALIDO RENAPO");
			municipio.setClave("999");
		} else {
			entidadFederativa.setNombre(respuesta.getDesEntidadRegistro());
			entidadFederativa.setClave(StringUtils.leftPad(String.valueOf(respuesta.getNumEntidadReg()), 2, '0'));
			municipio.setNombre(respuesta.getDesMunicipio());
			municipio.setClave(StringUtils.leftPad(String.valueOf(respuesta.getCveMunicipioReg()), 3, '0'));
		}
		municipio.setEntidadFederativa(entidadFederativa);
	}

	private void asignarPais(CurpKioscosBean respuesta, Fisica persona) {
		persona.setPais(new Pais());
		if (respuesta.getNacionalidad().equalsIgnoreCase("MEX")) {
			persona.getPais().setNacionalidad("MEXICANA");
			persona.getPais().setIdPais(Integer.valueOf(1));
		} else {
			persona.getPais().setNacionalidad("EXTRANJERO");
			persona.getPais().setIdPais(Integer.valueOf(2));
		}
		persona.getPais().setDescripcion(respuesta.getNacionalidad());
	}

}
