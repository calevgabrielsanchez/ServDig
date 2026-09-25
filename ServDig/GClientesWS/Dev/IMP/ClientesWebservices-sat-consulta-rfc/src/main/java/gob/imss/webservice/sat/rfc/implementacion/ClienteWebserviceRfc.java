package gob.imss.webservice.sat.rfc.implementacion;

import gob.imss.webservice.sat.rfc.cliente.Identificacion;
import gob.imss.webservice.sat.rfc.cliente.Regimenes;
import gob.imss.webservice.sat.rfc.cliente.SalidaSAT;
import gob.imss.webservice.sat.rfc.cliente.Ubicacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Regimen;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sat;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;


public class ClienteWebserviceRfc {
	
	Logger log = Logger.getLogger("cliente.webservices");

	private static int iServiceTimeOut = 30000;
	
	private final String RFC_NO_LOCALIZADO = "No se encontr\u00F3 RFC";
	private final String TIPO_TELEFONO_MOVIL= "M\u00F3vil";
	private final String TIPO_TELEFONO_FIJO= "Fijo";
	
    private transient final DateFormat formatoFechaRenapo = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
    private transient final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "mx"));

    private static final Map<String, Integer> FROM_DESC_TO_CVE_ENT_FED_NAC_MAP = new HashMap<String, Integer>();
    private static final String[] DESCRIPCION_ENT_FED_ARRY = new String[] { "", "AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TS", "TL", "VZ", "YN", "ZS", "NE", "SE" };

    static {
        Integer cveIndex = 0;
        // CONVERSION ENTIDAD FEDERATIVA
        for (String descripcionEntFed : DESCRIPCION_ENT_FED_ARRY) {
            FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, cveIndex);
            if (cveIndex == 33) {
                FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, 35);
            }
            if (cveIndex == 34) {
                FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, 99);
            }
            cveIndex++;
        }
    }
	
	
	public SalidaSAT getDatosRfc(String sRfc) throws ClienteWebserviceSatRfcException{
		SalidaSAT salida= null;
		try{
			System.out.println("WebserviceSatRfc. ClienteWebservice. RFC. " + sRfc);
			System.out.println("WebserviceSatRfc. ClienteWebservice. Se ejecuta el thread del Cliente. " + new Date());
			DatosWebserviceRfc thread = new DatosWebserviceRfc();
			thread.sRfc = sRfc;
			thread.start();
			
			System.out.println("WebserviceSatRfc. ClienteWebservice. Se establece el tiempo del timeout del thread = " + iServiceTimeOut);
			thread.join(iServiceTimeOut);
			System.out.println("WebserviceSatRfc. ClienteWebservice. Se recupera el control del proceso desde el thread. " + new Date());
			
			//VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.bErrorServicio){
				throw new ClienteWebserviceSatRfcException();				
			}
			
			//VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()){
				System.out.println("WebserviceSatRfc. ClienteWebservice. El thread sigue esperando la respuesa del SAT");
				thread.interrupt();
				System.out.println("WebserviceSatRfc. ClienteWebservice. El thread se ha interrumpido y se generara un ClienteWebserviceSatRfcException");
				throw new ClienteWebserviceSatRfcException();
			}
			else{
				System.out.println("WebserviceSatRfc. ClienteWebservice. El thread terminó satisfactoriamente la consulta al SAT dentro del timeout especificado");
				salida = thread.respuesta;
				System.out.println("WebserviceSatRfc. ClienteWebservice. salida. " + salida);
			}
			thread = null;
		}
		catch(Exception e){
			System.out.println("WebserviceSatRfc. ClienteWebservice. Se generó un error al accesar el webservice");
			throw new ClienteWebserviceSatRfcException();
		}
		return salida;
	}

	public Fisica buscarPersonaFisicaPorRfcEnSat(final String rfc)
			throws ClienteWebserviceSatRfcException {

		Fisica personaFisica = null;
		Sat sat = buscarPersonaPorRfcEnSat(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaFisica = new Fisica();
			personaFisica.setRfc(sat.getRfc());
			personaFisica.setRfcOriginal(sat.getRfcOriginal());
			personaFisica.setRfcSolicitado(sat.getRfcSolicitado());
			personaFisica.setRfcVigente(sat.getRfcVigente());
			personaFisica.setNombre(sat.getNombre());
			personaFisica.setPrimerApellido(sat.getPrimerApellido());
			personaFisica.setSegundoApellido(sat.getSegundoApellido());
			personaFisica.setFechaNacimiento(sat.getFechaNacimiento());
			personaFisica.setIndRIF(sat.isRif());
			personaFisica.setRegimenes(sat.getRegimenes());
			
			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaFisica.setDatosPersonaSAT(datosPersonaSAT);

			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			personaFisica.setSituacionesSAT(listSituacionesSAT);
			
			// 191807 271212 Se asigna el domicilio fiscal
			if (sat.getUbicacion() != null) {
				personaFisica.setDomicilioFiscal(sat.getUbicacion());
			}

			List<MedioContacto> listMediosContacto = getListMediosContactoEnSat(sat);
			personaFisica.setMediosContactoFiscales(listMediosContacto);

			// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
			if (sat.getCurp() != null && !sat.getCurp().equals("")) {
				personaFisica.setCurp(sat.getCurp());

				// SE INFIERE EL SEXO DE LA CURP
				final Integer cveSexo = personaFisica.getCurp().charAt(10) == 'H' ? 1
						: 2;
				personaFisica.getSexo().setIdSexo(cveSexo);
				personaFisica.getSexo().setDescripcion(
						cveSexo == 1 ? "HOMBRE" : "MUJER");

				// SE INFIERE LA ENTIDAD DE NACIMIENTO DE LA CURP
				final String cveEntidad = personaFisica.getCurp()
						.substring(11, 13);
				log.info("cveEntidad: " + cveEntidad);
				log.info("personaFisica.getIdEntidadNacimiento(): "
						+ personaFisica.getLugarNacimiento().getClave());

				personaFisica.getLugarNacimiento().setClave(
						FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(cveEntidad)
								.toString());
				personaFisica.getLugarNacimiento().setNombre("");

				personaFisica.setFechaNacimientoFormateada(DateUtils
						.dateToStringConFormato(sat.getFechaNacimiento(),
								formatoFechaRenapo));
			}// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
		}// VERIFICA SI ENCONTRO EL RFC EN EL SAT

		return personaFisica;
	}

	public Moral buscarPersonaMoralPorRfcEnSat(final String rfc)
			throws ClienteWebserviceSatRfcException {
		Moral personaMoral = null;
		Sat sat = buscarPersonaPorRfcEnSat(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaMoral = new Moral();
			personaMoral.setRfc(sat.getRfc());
			personaMoral.setRfcOriginal(sat.getRfcOriginal());
			personaMoral.setRfcSolicitado(sat.getRfcSolicitado());
			personaMoral.setRfcVigente(sat.getRfcVigente());
			personaMoral.setRazonSocial(sat.getRazonSocial());
			personaMoral.setFechaCreacion(sat.getFechaInicioOperaciones());

			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaMoral.setDatosPersonaSAT(datosPersonaSAT);

			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			personaMoral.setSituacionesSAT(listSituacionesSAT);

			TipoSociedad tipoSociedad = new TipoSociedad();
			tipoSociedad.setDescripcionAbreviada(sat.getDesTipoSociedad());

			// LOS CLIENTES QUE LLAMEN EL WEBSERVICE DEBEN IR A LA BASE DE DATOS
			// A BUSCAR EL TIPO DE SOCIEDAD
			// personaMoral.getTipoSociedad().setIdTipoSociedad(Utilerias.convertir(sat.getIdTipoSociedad()));

			personaMoral.setTipoSociedad(tipoSociedad);

			if (sat.getUbicacion() != null) {
				personaMoral.setDomicilioFiscal(sat.getUbicacion());
			}

			List<MedioContacto> listMediosContacto = getListMediosContactoEnSat(sat);
			personaMoral.setMediosContactoFiscales(listMediosContacto);
		}

		return personaMoral;
	}

	private List<MedioContacto> getListMediosContactoEnSat(Sat sat) {
		List<MedioContacto> listMediosContacto = new ArrayList<MedioContacto>();

		if (sat.getEmail() != null) {
			TipoMedioContacto tipoCorreo = new TipoMedioContacto();
			tipoCorreo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_CORREO_ELECTRONICO);

			MedioContacto medioContactoCorreo = new MedioContacto();
			medioContactoCorreo.setTipoMedioContacto(tipoCorreo);
			medioContactoCorreo.setDesFormaContacto(sat.getEmail());
			listMediosContacto.add(medioContactoCorreo);
		}

		if (sat.getTelefono1() != null) {
			MedioContacto medioContactoTelefono1 = new MedioContacto();
			medioContactoTelefono1.setDesFormaContacto(sat.getTelefono1());
			if (sat.getTipoTelefono1() != null) {
				medioContactoTelefono1.setTipoMedioContacto(sat.getTipoTelefono1());
			}

			listMediosContacto.add(medioContactoTelefono1);
		}

		if (sat.getTelefono2() != null) {
			MedioContacto medioContactoTelefono2 = new MedioContacto();
			medioContactoTelefono2.setDesFormaContacto(sat.getTelefono2());
			if (sat.getTipoTelefono2() != null) {
				medioContactoTelefono2.setTipoMedioContacto(sat.getTipoTelefono2());
			}

			listMediosContacto.add(medioContactoTelefono2);
		}

		return listMediosContacto;
	}

	/**
	 * @param rfc RFC de la persona a buscar
	 * @return Datos correspondientes a la Persona Fisica
	 * @throws ClienteWebserviceSatRfcException
	 */
	public Fisica obtenerDatosFiscalesFisicaPorRfc(final String rfc) throws ClienteWebserviceSatRfcException {
		Fisica personaFisica = null;
		Sat sat = buscarPersonaPorRfcEnSat(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaFisica = new Fisica();
			personaFisica.setRfc(sat.getRfc());
			personaFisica.setRfcOriginal(sat.getRfcOriginal());
			personaFisica.setRfcSolicitado(sat.getRfcSolicitado());
			personaFisica.setRfcVigente(sat.getRfcVigente());
			personaFisica.setNombre(sat.getNombre());
			personaFisica.setPrimerApellido(sat.getPrimerApellido());
			personaFisica.setSegundoApellido(sat.getSegundoApellido());
			personaFisica.setFechaNacimiento(sat.getFechaNacimiento());

			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaFisica.setDatosPersonaSAT(datosPersonaSAT);

			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			personaFisica.setSituacionesSAT(listSituacionesSAT);

			// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
			if (sat.getCurp() != null && !sat.getCurp().equals("")) {
				personaFisica.setCurp(sat.getCurp());

				// SE INFIERE EL SEXO DE LA CURP
				final Integer cveSexo = personaFisica.getCurp().charAt(10) == 'H' ? 1 : 2;
				personaFisica.getSexo().setIdSexo(cveSexo);
				personaFisica.getSexo().setDescripcion(cveSexo == 1 ? "HOMBRE" : "MUJER");

				// SE INFIERE LA ENTIDAD DE NACIMIENTO DE LA CURP
				final String cveEntidad = personaFisica.getCurp().substring(11, 13);
				log.info("cveEntidad: " + cveEntidad);
				log.info("personaFisica.getIdEntidadNacimiento(): " + personaFisica.getLugarNacimiento().getClave());

				personaFisica.getLugarNacimiento().setClave(FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(cveEntidad).toString());
				personaFisica.getLugarNacimiento().setNombre("");

				personaFisica.setFechaNacimientoFormateada(DateUtils.dateToStringConFormato(sat.getFechaNacimiento(), formatoFechaRenapo));
			}// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
		}// VERIFICA SI ENCONTRO EL RFC EN EL SAT

		return personaFisica;
	}

	/**
	 * @param rfc RFC de la persona a buscar
	 * @return Datos correspondientes a la Persona Moral
	 * @throws ClienteWebserviceSatRfcException
	 */
	public Moral obtenerDatosFiscalesMoralPorRfc(final String rfc) throws ClienteWebserviceSatRfcException {
		Moral personaMoral = null;
		Sat sat = buscarPersonaPorRfcEnSat(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaMoral = new Moral();
			personaMoral.setRfc(sat.getRfc());
			personaMoral.setRfcOriginal(sat.getRfcOriginal());
			personaMoral.setRfcSolicitado(sat.getRfcSolicitado());
			personaMoral.setRfcVigente(sat.getRfcVigente());
			personaMoral.setRazonSocial(sat.getRazonSocial());
			personaMoral.setFechaCreacion(sat.getFechaInicioOperaciones());

			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaMoral.setDatosPersonaSAT(datosPersonaSAT);

			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			personaMoral.setSituacionesSAT(listSituacionesSAT);

			TipoSociedad tipoSociedad = new TipoSociedad();
			tipoSociedad.setDescripcionAbreviada(sat.getDesTipoSociedad());

			// LOS CLIENTES QUE LLAMEN EL WEBSERVICE DEBEN IR A LA BASE DE DATOS A BUSCAR EL TIPO DE SOCIEDAD
			// personaMoral.getTipoSociedad().setIdTipoSociedad(Utilerias.convertir(sat.getIdTipoSociedad()));

			personaMoral.setTipoSociedad(tipoSociedad);
			personaMoral.setDomicilioFiscal(sat.getUbicacion());
		}

		return personaMoral;
	}

	private Sat buscarPersonaPorRfcEnSat(final String rfc) throws ClienteWebserviceSatRfcException {
		Sat sat = null; // NOPMD

		// CONSULTA SAT
		final SalidaSAT respuestaSat = getDatosRfc(rfc);

		if (respuestaSat != null) {
			final List<Identificacion> identificacion = respuestaSat.getIdentificacion();

//				if (identificacion != null) { 	/ el WS siempre regresa el codigo 0000 sea exitoso o no, y tambien regresa siempre un mensaje.
											// ademas no regresa ninguno de los 3 campos RFC!!! entonces la unica manera es 
											// filtrar ese mensaje
			if(!respuestaSat.getMensajeControl().getDescripcion().equals(RFC_NO_LOCALIZADO)){
				final Identificacion persona = identificacion.get(0);
				sat = new Sat();
				sat.setRfc(rfc);
				sat.setRfcOriginal(respuestaSat.getRFCOriginal());
				sat.setRfcSolicitado(respuestaSat.getRFCSolicitado());
				sat.setRfcVigente(respuestaSat.getRFCVigente());
				sat.setCurp(persona.getCURP());
				sat.setNombre(persona.getNombre());
				sat.setPrimerApellido(persona.getApPaterno());
				sat.setSegundoApellido(persona.getApMaterno());
				// sat.setNombreComercial(persona.getNomComercial());
				sat.setRazonSocial(persona.getRazonSoc());
				sat.setDesTipoSociedad(persona.getTSociedad());
				sat.setCSitCont(persona.getCSitCont());
				sat.setDSitCont(persona.getDSitCont());
					
				respuestaSat.getRegimen();

				if (StringUtils.isNotBlank(persona.getFNacimiento())) {
					try {
						sat.setFechaNacimiento(df.parse(persona.getFNacimiento()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de nacimiento de la persona fisica.", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFConstitucion())) {
					try {
						sat.setFechaConstitucion(df.parse(persona.getFConstitucion()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de constitucion de la persona fisica", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFIniOpers())) {
					try {
						sat.setFechaInicioOperaciones(df.parse(persona.getFIniOpers()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de inicio de operaciones", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFSitCont())) {
					try {
						sat.setFechaSitCont(df.parse(persona.getFSitCont()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de situacion cont.", e);
					}
				}
			}

			final List<Ubicacion> listUbicacion = respuestaSat
					.getUbicacion();

			if (listUbicacion != null && !listUbicacion.isEmpty()) {
				if (sat == null) {
					sat = new Sat();
				}

				final Ubicacion ubicacion = listUbicacion.get(0);

				DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
				domicilioFiscal.setDescripcion(ubicacion.getDReferencia());
				domicilioFiscal.setCalle(ubicacion.getCalle());
				domicilioFiscal.setColonia(ubicacion.getDColonia());

				if (StringUtils.isNotBlank(ubicacion.getNExterior())) {
					domicilioFiscal.setNumExteriorAlf(ubicacion.getNExterior());
				}
				
				if (StringUtils.isNotBlank(ubicacion.getNInterior())) {
					domicilioFiscal.setNumInteriorAlf(ubicacion.getNInterior());
				}

				CodigoPostal codigoPostal = new CodigoPostal();
				if (NumberUtils.isNumber(ubicacion.getCp())) {
					codigoPostal.setCodigoPostal(ubicacion.getCp());
				}
				domicilioFiscal.setCodigoPostal(codigoPostal);

				Vialidad vialidadReferenciaPrimaria = new Vialidad();
				vialidadReferenciaPrimaria.setNombre(ubicacion.getDEntreCalle1());
				domicilioFiscal.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);

				Vialidad vialidadReferenciaSecundaria = new Vialidad();
				vialidadReferenciaSecundaria.setNombre(ubicacion.getDEntreCalle2());
				domicilioFiscal.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);

				Vialidad vialidadPrimaria = new Vialidad();
				vialidadPrimaria.setNombre(ubicacion.getCalle());
				
				if(ubicacion.getDReferencia() != null) {
					Vialidad vialidadPosterior = new Vialidad();
					vialidadPosterior.setNombre(ubicacion.getDReferencia());
					domicilioFiscal.setVialidadReferenciaPosterior(vialidadPosterior);
				}
				

				TipoVialidad tipoVialidad = new TipoVialidad();
				if (NumberUtils.isNumber(ubicacion.getTVialidad())) {
					tipoVialidad.setClave(Integer.valueOf(ubicacion.getTVialidad()));
				}
				tipoVialidad.setDescripcion(ubicacion.getDVialidad());
				vialidadPrimaria.setTipoVialidad(tipoVialidad);
				domicilioFiscal.setVialidadPrimaria(vialidadPrimaria);
				
				Asentamiento asentamiento = new Asentamiento();
				asentamiento.setNombre(ubicacion.getDColonia());
				asentamiento.setClave(ubicacion.getCColonia());
				asentamiento.setCodigoPostal(codigoPostal);

				TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
				if (NumberUtils.isNumber(ubicacion.getTInmueble())) {
					tipoAsentamiento.setClave(Long.valueOf(ubicacion.getTInmueble()));
				}
				tipoAsentamiento.setDescripcion(ubicacion.getDInmueble());
				asentamiento.setTipoAsentamiento(tipoAsentamiento);

				Localidad localidad = new Localidad();
				if (StringUtils.isNotBlank(ubicacion.getCLocalidad())) {
					localidad.setClave(ubicacion.getCLocalidad());
				} else {
					localidad.setClave(ubicacion.getCColonia());
				}
				if (StringUtils.isNotBlank(ubicacion.getDLocalidad())) {
					localidad.setNombre(ubicacion.getDLocalidad());
				} else {
					localidad.setNombre(ubicacion.getDColonia());
				}

				Municipio municipio = new Municipio();
				municipio.setClave(ubicacion.getCMunicipio());
				municipio.setNombre(ubicacion.getDMunicipio());

				EntidadFederativa entidadFederativa = new EntidadFederativa();
				entidadFederativa.setClave(ubicacion.getCEntFed());
				entidadFederativa.setNombre(ubicacion.getDEntFed());
				municipio.setEntidadFederativa(entidadFederativa);

				localidad.setMunicipio(municipio);
				asentamiento.setLocalidad(localidad);
				domicilioFiscal.setAsentamiento(asentamiento);

				// Medios de Contacto
				String telefono1 = ubicacion.getTelefono1();
				String telefono2 = ubicacion.getTelefono2();
				String tipoTelefono1 = ubicacion.getTTel1();
				String tipoTelefono2 = ubicacion.getTTel2();
				String email = ubicacion.getEmail();

				if (StringUtils.isNotBlank(telefono1)) {
					sat.setTelefono1(telefono1);
				}
				if (StringUtils.isNotBlank(telefono2)) {
					sat.setTelefono2(telefono2);
				}
				if (StringUtils.isNotBlank(tipoTelefono1)) {
					if (tipoTelefono1.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono1(tipoTelefonoFijo);
					} else if (tipoTelefono1.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono1(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(tipoTelefono2) && StringUtils.isNotBlank(tipoTelefono2)) {
					if (tipoTelefono2.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono2(tipoTelefonoFijo);
					} else if (tipoTelefono2.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono2(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(email)) {
					sat.setEmail(email);
				}

				sat.setUbicacion(domicilioFiscal);
			}	
			
			List<Regimenes> listRegimen = respuestaSat.getRegimen();
			if (listRegimen != null && !listRegimen.isEmpty()) {
				if (sat == null) {
					sat = new Sat();
				}

				sat = obtenerRegimenes(sat, respuestaSat);
			}
		}

		log.info("sat: " + sat);
		return sat;
	}
	
	
	private Sat obtenerRegimenes(Sat sat, SalidaSAT salidaSat){
		List<Regimen> regimenList = new ArrayList<Regimen>();
		Boolean isRIF = false;
		for(Regimenes regimen : salidaSat.getRegimen()){
			Regimen nuevoRegimen = new Regimen();
			nuevoRegimen.setClaveRegimen(regimen.getCRegimen());
			log.error("Clave regimen: "+regimen.getCRegimen());
			log.error("Descripci�n regimen: "+regimen.getDRegimen());
			if(regimen.getCRegimen().equalsIgnoreCase("604"))
				isRIF = true;
			nuevoRegimen.setDescripcionRegimen(regimen.getDRegimen());
			nuevoRegimen.setFechaAltaReg(regimen.getFAltaReg());
			nuevoRegimen.setFechaBajaReg(regimen.getFBajaReg());
			nuevoRegimen.setFechaEfectoAReg(regimen.getFEfecAReg());
			nuevoRegimen.setFechaEfectoBReg(regimen.getFEfecBReg());
			regimenList.add(nuevoRegimen);
		}
		
		sat.setRegimenes(regimenList);
		sat.setRif(isRIF);
		
		return sat;
	}
	
	public Fisica buscarPFPorRfcEnSatSitCont(final String rfc)
			throws ClienteWebserviceSatRfcException {

		log.debug("::: En ClientesWebservices-sat-consulta-rfc.buscarPFPorRfcEnSatSitCont" );
		
		Fisica personaFisica = null;
		Sat sat = buscarPorRfcEnSatSitContPF(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaFisica = new Fisica();
			personaFisica.setRfc(sat.getRfc());
			personaFisica.setRfcOriginal(sat.getRfcOriginal());
			personaFisica.setRfcSolicitado(sat.getRfcSolicitado());
			personaFisica.setRfcVigente(sat.getRfcVigente());
			personaFisica.setNombre(sat.getNombre());
			personaFisica.setPrimerApellido(sat.getPrimerApellido());
			personaFisica.setSegundoApellido(sat.getSegundoApellido());
			personaFisica.setFechaNacimiento(sat.getFechaNacimiento());
			personaFisica.setIndRIF(sat.isRif());
			personaFisica.setRegimenes(sat.getRegimenes());
			
			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaFisica.setDatosPersonaSAT(datosPersonaSAT);
			
			//Se agregan en la lista las situaciones del contribuyente
			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(1)); //identificador para situacion del contribuyente
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			
			situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(2));//identificador para situacion del domicilio del contribuyente
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitDom());
			situacionSAT.setCveSituacionSAT(sat.getCSitDom());
			listSituacionesSAT.add(situacionSAT);			

			situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(3));//identificador para situacion del contribuyente con su domicilio
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitContDom());
			situacionSAT.setCveSituacionSAT(sat.getCSitContDom());
			listSituacionesSAT.add(situacionSAT);	
			
			personaFisica.setSituacionesSAT(listSituacionesSAT);			
			
			
			// 191807 271212 Se asigna el domicilio fiscal
			if (sat.getUbicacion() != null) {
				personaFisica.setDomicilioFiscal(sat.getUbicacion());
			}

			List<MedioContacto> listMediosContacto = getListMediosContactoEnSat(sat);
			personaFisica.setMediosContactoFiscales(listMediosContacto);

			// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
			if (sat.getCurp() != null && !sat.getCurp().equals("")) {
				personaFisica.setCurp(sat.getCurp());

				// SE INFIERE EL SEXO DE LA CURP
				final Integer cveSexo = personaFisica.getCurp().charAt(10) == 'H' ? 1
						: 2;
				personaFisica.getSexo().setIdSexo(cveSexo);
				personaFisica.getSexo().setDescripcion(
						cveSexo == 1 ? "HOMBRE" : "MUJER");

				// SE INFIERE LA ENTIDAD DE NACIMIENTO DE LA CURP
				final String cveEntidad = personaFisica.getCurp()
						.substring(11, 13);
				log.info("cveEntidad: " + cveEntidad);
				log.info("personaFisica.getIdEntidadNacimiento(): "
						+ personaFisica.getLugarNacimiento().getClave());

				personaFisica.getLugarNacimiento().setClave(
						FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get(cveEntidad)
								.toString());
				personaFisica.getLugarNacimiento().setNombre("");

				personaFisica.setFechaNacimientoFormateada(DateUtils
						.dateToStringConFormato(sat.getFechaNacimiento(),
								formatoFechaRenapo));
			}// VERIFICA SI EN EL SAT TIENEN REGISTRADA LA CURP
		}// VERIFICA SI ENCONTRO EL RFC EN EL SAT

		return personaFisica;
	}

	private Sat buscarPorRfcEnSatSitContPF(final String rfc) throws ClienteWebserviceSatRfcException {
		Sat sat = null; // NOPMD
		log.debug("::: En buscarPorRfcEnSatSitCont");
		// CONSULTA SAT
		final SalidaSAT respuestaSat = getDatosRfc(rfc);

		if (respuestaSat != null) {
			final List<Identificacion> identificacion = respuestaSat.getIdentificacion();

//				if (identificacion != null) { 	/ el WS siempre regresa el codigo 0000 sea exitoso o no, y tambien regresa siempre un mensaje.
											// ademas no regresa ninguno de los 3 campos RFC!!! entonces la unica manera es 
											// filtrar ese mensaje
			Identificacion persona = null;
			
			if(!respuestaSat.getMensajeControl().getDescripcion().equals(RFC_NO_LOCALIZADO)){
				log.debug("::: RFC localizado, " + rfc);
				persona = identificacion.get(0);
				sat = new Sat();
				sat.setRfc(rfc);
				sat.setRfcOriginal(respuestaSat.getRFCOriginal());
				sat.setRfcSolicitado(respuestaSat.getRFCSolicitado());
				sat.setRfcVigente(respuestaSat.getRFCVigente());
				sat.setCurp(persona.getCURP());
				sat.setNombre(persona.getNombre());
				sat.setPrimerApellido(persona.getApPaterno());
				sat.setSegundoApellido(persona.getApMaterno());
				// sat.setNombreComercial(persona.getNomComercial());
				sat.setRazonSocial(persona.getRazonSoc());
				sat.setDesTipoSociedad(persona.getTSociedad());
				
				//se guardan los datos de la situacion del contribuyente
				sat.setCSitCont(persona.getCSitCont());
				sat.setDSitCont(persona.getDSitCont());
				sat.setCSitDom(persona.getCSitDom());
				sat.setDSitDom(persona.getDSitDom());
				sat.setCSitContDom(persona.getCSitContDom());
				sat.setDSitContDom(persona.getDSitContDom());
					
				respuestaSat.getRegimen();

				if (StringUtils.isNotBlank(persona.getFNacimiento())) {
					try {
						sat.setFechaNacimiento(df.parse(persona.getFNacimiento()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de nacimiento de la persona fisica.", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFConstitucion())) {
					try {
						sat.setFechaConstitucion(df.parse(persona.getFConstitucion()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de constitucion de la persona fisica", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFIniOpers())) {
					try {
						sat.setFechaInicioOperaciones(df.parse(persona.getFIniOpers()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de inicio de operaciones", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFSitCont())) {
					try {
						sat.setFechaSitCont(df.parse(persona.getFSitCont()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de situacion cont.", e);
					}
				}
			}

			
			
			final List<Ubicacion> listUbicacion = respuestaSat
					.getUbicacion();

			if (listUbicacion != null && !listUbicacion.isEmpty()) {
				if (sat == null) {
					log.debug("::: Se reinicia objeto SAT A");
					sat = new Sat();
				}

				final Ubicacion ubicacion = listUbicacion.get(0);

				DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
				domicilioFiscal.setDescripcion(ubicacion.getDReferencia());
				domicilioFiscal.setCalle(ubicacion.getCalle());
				domicilioFiscal.setColonia(ubicacion.getDColonia());

				if (StringUtils.isNotBlank(ubicacion.getNExterior())) {
					domicilioFiscal.setNumExteriorAlf(ubicacion.getNExterior());
				}
				
				if (StringUtils.isNotBlank(ubicacion.getNInterior())) {
					domicilioFiscal.setNumInteriorAlf(ubicacion.getNInterior());
				}

				CodigoPostal codigoPostal = new CodigoPostal();
				if (NumberUtils.isNumber(ubicacion.getCp())) {
					codigoPostal.setCodigoPostal(ubicacion.getCp());
				}
				domicilioFiscal.setCodigoPostal(codigoPostal);

				Vialidad vialidadReferenciaPrimaria = new Vialidad();
				vialidadReferenciaPrimaria.setNombre(ubicacion.getDEntreCalle1());
				domicilioFiscal.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);

				Vialidad vialidadReferenciaSecundaria = new Vialidad();
				vialidadReferenciaSecundaria.setNombre(ubicacion.getDEntreCalle2());
				domicilioFiscal.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);

				Vialidad vialidadPrimaria = new Vialidad();
				vialidadPrimaria.setNombre(ubicacion.getCalle());
				
				if(ubicacion.getDReferencia() != null) {
					Vialidad vialidadPosterior = new Vialidad();
					vialidadPosterior.setNombre(ubicacion.getDReferencia());
					domicilioFiscal.setVialidadReferenciaPosterior(vialidadPosterior);
				}
				

				TipoVialidad tipoVialidad = new TipoVialidad();
				if (NumberUtils.isNumber(ubicacion.getTVialidad())) {
					tipoVialidad.setClave(Integer.valueOf(ubicacion.getTVialidad()));
				}
				tipoVialidad.setDescripcion(ubicacion.getDVialidad());
				vialidadPrimaria.setTipoVialidad(tipoVialidad);
				domicilioFiscal.setVialidadPrimaria(vialidadPrimaria);
				
				Asentamiento asentamiento = new Asentamiento();
				asentamiento.setNombre(ubicacion.getDColonia());
				asentamiento.setClave(ubicacion.getCColonia());
				asentamiento.setCodigoPostal(codigoPostal);

				TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
				if (NumberUtils.isNumber(ubicacion.getTInmueble())) {
					tipoAsentamiento.setClave(Long.valueOf(ubicacion.getTInmueble()));
				}
				tipoAsentamiento.setDescripcion(ubicacion.getDInmueble());
				asentamiento.setTipoAsentamiento(tipoAsentamiento);

				Localidad localidad = new Localidad();
				if (StringUtils.isNotBlank(ubicacion.getCLocalidad())) {
					localidad.setClave(ubicacion.getCLocalidad());
				} else {
					localidad.setClave(ubicacion.getCColonia());
				}
				if (StringUtils.isNotBlank(ubicacion.getDLocalidad())) {
					localidad.setNombre(ubicacion.getDLocalidad());
				} else {
					localidad.setNombre(ubicacion.getDColonia());
				}

				Municipio municipio = new Municipio();
				municipio.setClave(ubicacion.getCMunicipio());
				municipio.setNombre(ubicacion.getDMunicipio());

				EntidadFederativa entidadFederativa = new EntidadFederativa();
				entidadFederativa.setClave(ubicacion.getCEntFed());
				entidadFederativa.setNombre(ubicacion.getDEntFed());
				municipio.setEntidadFederativa(entidadFederativa);

				localidad.setMunicipio(municipio);
				asentamiento.setLocalidad(localidad);
				domicilioFiscal.setAsentamiento(asentamiento);

				// Medios de Contacto
				String telefono1 = ubicacion.getTelefono1();
				String telefono2 = ubicacion.getTelefono2();
				String tipoTelefono1 = ubicacion.getTTel1();
				String tipoTelefono2 = ubicacion.getTTel2();
				String email = ubicacion.getEmail();

				if (StringUtils.isNotBlank(telefono1)) {
					sat.setTelefono1(telefono1);
				}
				if (StringUtils.isNotBlank(telefono2)) {
					sat.setTelefono2(telefono2);
				}
				if (StringUtils.isNotBlank(tipoTelefono1)) {
					if (tipoTelefono1.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono1(tipoTelefonoFijo);
					} else if (tipoTelefono1.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono1(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(tipoTelefono2) && StringUtils.isNotBlank(tipoTelefono2)) {
					if (tipoTelefono2.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono2(tipoTelefonoFijo);
					} else if (tipoTelefono2.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono2(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(email)) {
					sat.setEmail(email);
				}

				sat.setUbicacion(domicilioFiscal);
			}	
			
			List<Regimenes> listRegimen = respuestaSat.getRegimen();
			if (listRegimen != null && !listRegimen.isEmpty()) {
				if (sat == null) {
					log.debug("::: Se reinicia objeto SAT B");
					sat = new Sat();
				}
				sat = obtenerRegimenes(sat, respuestaSat);
			}
		}

		log.info("sat: " + sat);
		return sat;
	}	

	public Moral buscarPMPorRfcEnSatSitCont(final String rfc)
			throws ClienteWebserviceSatRfcException {
		Moral personaMoral = null;
		Sat sat = buscarPorRfcEnSatSitContPM(rfc);

		// VERIFICA SI ENCONTRO EL RFC EN EL SAT
		if (sat != null) {
			personaMoral = new Moral();
			personaMoral.setRfc(sat.getRfc());
			personaMoral.setRfcOriginal(sat.getRfcOriginal());
			personaMoral.setRfcSolicitado(sat.getRfcSolicitado());
			personaMoral.setRfcVigente(sat.getRfcVigente());
			personaMoral.setRazonSocial(sat.getRazonSocial());
			personaMoral.setFechaCreacion(sat.getFechaInicioOperaciones());

			DatosPersonaSAT datosPersonaSAT = new DatosPersonaSAT();
			datosPersonaSAT.setFechaConstitucion(sat.getFechaConstitucion());
			datosPersonaSAT.setFechaInicioOperaciones(sat.getFechaInicioOperaciones());
			personaMoral.setDatosPersonaSAT(datosPersonaSAT);
			
			//Se agregan en la lista las situaciones del contribuyente
			List<SituacionSAT> listSituacionesSAT = new ArrayList<SituacionSAT>();
			SituacionSAT situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(1)); //identificador para situacion del contribuyente
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitCont());
			situacionSAT.setCveSituacionSAT(sat.getCSitCont());
			listSituacionesSAT.add(situacionSAT);
			
			situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(2));//identificador para situacion del domicilio del contribuyente
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitDom());
			situacionSAT.setCveSituacionSAT(sat.getCSitDom());
			listSituacionesSAT.add(situacionSAT);			

			situacionSAT = new SituacionSAT();
			situacionSAT.setIdSituacionSAT(new Long(3));//identificador para situacion del contribuyente con su domicilio
			situacionSAT.setFechaSituacion(sat.getFechaSitCont());
			situacionSAT.setDescripcion(sat.getDSitContDom());
			situacionSAT.setCveSituacionSAT(sat.getCSitContDom());
			listSituacionesSAT.add(situacionSAT);	
			
			personaMoral.setSituacionesSAT(listSituacionesSAT);			
			
			TipoSociedad tipoSociedad = new TipoSociedad();
			tipoSociedad.setDescripcionAbreviada(sat.getDesTipoSociedad());

			// LOS CLIENTES QUE LLAMEN EL WEBSERVICE DEBEN IR A LA BASE DE DATOS
			// A BUSCAR EL TIPO DE SOCIEDAD
			// personaMoral.getTipoSociedad().setIdTipoSociedad(Utilerias.convertir(sat.getIdTipoSociedad()));

			personaMoral.setTipoSociedad(tipoSociedad);

			if (sat.getUbicacion() != null) {
				personaMoral.setDomicilioFiscal(sat.getUbicacion());
			}

			List<MedioContacto> listMediosContacto = getListMediosContactoEnSat(sat);
			personaMoral.setMediosContactoFiscales(listMediosContacto);
		}

		return personaMoral;
	}	
	
	
	private Sat buscarPorRfcEnSatSitContPM(final String rfc) throws ClienteWebserviceSatRfcException {
		Sat sat = null; // NOPMD

		// CONSULTA SAT
		final SalidaSAT respuestaSat = getDatosRfc(rfc);

		if (respuestaSat != null) {
			final List<Identificacion> identificacion = respuestaSat.getIdentificacion();

//				if (identificacion != null) { 	/ el WS siempre regresa el codigo 0000 sea exitoso o no, y tambien regresa siempre un mensaje.
											// ademas no regresa ninguno de los 3 campos RFC!!! entonces la unica manera es 
											// filtrar ese mensaje
			if(!respuestaSat.getMensajeControl().getDescripcion().equals(RFC_NO_LOCALIZADO)){
				final Identificacion persona = identificacion.get(0);
				sat = new Sat();
				sat.setRfc(rfc);
				sat.setRfcOriginal(respuestaSat.getRFCOriginal());
				sat.setRfcSolicitado(respuestaSat.getRFCSolicitado());
				sat.setRfcVigente(respuestaSat.getRFCVigente());
				sat.setCurp(persona.getCURP());
				sat.setNombre(persona.getNombre());
				sat.setPrimerApellido(persona.getApPaterno());
				sat.setSegundoApellido(persona.getApMaterno());
				// sat.setNombreComercial(persona.getNomComercial());
				sat.setRazonSocial(persona.getRazonSoc());
				sat.setDesTipoSociedad(persona.getTSociedad());
				
				//se guardan los datos de la situacion del contribuyente
				sat.setCSitCont(persona.getCSitCont());
				sat.setDSitCont(persona.getDSitCont());
				sat.setCSitDom(persona.getCSitDom());
				sat.setDSitDom(persona.getDSitDom());
				sat.setCSitContDom(persona.getCSitContDom());
				sat.setDSitContDom(persona.getDSitContDom());				
									
				respuestaSat.getRegimen();

				if (StringUtils.isNotBlank(persona.getFNacimiento())) {
					try {
						sat.setFechaNacimiento(df.parse(persona.getFNacimiento()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de nacimiento de la persona fisica.", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFConstitucion())) {
					try {
						sat.setFechaConstitucion(df.parse(persona.getFConstitucion()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de constitucion de la persona fisica", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFIniOpers())) {
					try {
						sat.setFechaInicioOperaciones(df.parse(persona.getFIniOpers()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de inicio de operaciones", e);
					}
				}

				if (StringUtils.isNotBlank(persona.getFSitCont())) {
					try {
						sat.setFechaSitCont(df.parse(persona.getFSitCont()));
					} catch (ParseException e) {
						log.info("Error al convertir la fecha de situacion cont.", e);
					}
				}
			}

			final List<Ubicacion> listUbicacion = respuestaSat
					.getUbicacion();

			if (listUbicacion != null && !listUbicacion.isEmpty()) {
				if (sat == null) {
					sat = new Sat();
				}

				final Ubicacion ubicacion = listUbicacion.get(0);

				DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
				domicilioFiscal.setDescripcion(ubicacion.getDReferencia());
				domicilioFiscal.setCalle(ubicacion.getCalle());
				domicilioFiscal.setColonia(ubicacion.getDColonia());

				if (StringUtils.isNotBlank(ubicacion.getNExterior())) {
					domicilioFiscal.setNumExteriorAlf(ubicacion.getNExterior());
				}
				
				if (StringUtils.isNotBlank(ubicacion.getNInterior())) {
					domicilioFiscal.setNumInteriorAlf(ubicacion.getNInterior());
				}

				CodigoPostal codigoPostal = new CodigoPostal();
				if (NumberUtils.isNumber(ubicacion.getCp())) {
					codigoPostal.setCodigoPostal(ubicacion.getCp());
				}
				domicilioFiscal.setCodigoPostal(codigoPostal);

				Vialidad vialidadReferenciaPrimaria = new Vialidad();
				vialidadReferenciaPrimaria.setNombre(ubicacion.getDEntreCalle1());
				domicilioFiscal.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);

				Vialidad vialidadReferenciaSecundaria = new Vialidad();
				vialidadReferenciaSecundaria.setNombre(ubicacion.getDEntreCalle2());
				domicilioFiscal.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);

				Vialidad vialidadPrimaria = new Vialidad();
				vialidadPrimaria.setNombre(ubicacion.getCalle());
				
				if(ubicacion.getDReferencia() != null) {
					Vialidad vialidadPosterior = new Vialidad();
					vialidadPosterior.setNombre(ubicacion.getDReferencia());
					domicilioFiscal.setVialidadReferenciaPosterior(vialidadPosterior);
				}
				

				TipoVialidad tipoVialidad = new TipoVialidad();
				if (NumberUtils.isNumber(ubicacion.getTVialidad())) {
					tipoVialidad.setClave(Integer.valueOf(ubicacion.getTVialidad()));
				}
				tipoVialidad.setDescripcion(ubicacion.getDVialidad());
				vialidadPrimaria.setTipoVialidad(tipoVialidad);
				domicilioFiscal.setVialidadPrimaria(vialidadPrimaria);
				
				Asentamiento asentamiento = new Asentamiento();
				asentamiento.setNombre(ubicacion.getDColonia());
				asentamiento.setClave(ubicacion.getCColonia());
				asentamiento.setCodigoPostal(codigoPostal);

				TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
				if (NumberUtils.isNumber(ubicacion.getTInmueble())) {
					tipoAsentamiento.setClave(Long.valueOf(ubicacion.getTInmueble()));
				}
				tipoAsentamiento.setDescripcion(ubicacion.getDInmueble());
				asentamiento.setTipoAsentamiento(tipoAsentamiento);

				Localidad localidad = new Localidad();
				if (StringUtils.isNotBlank(ubicacion.getCLocalidad())) {
					localidad.setClave(ubicacion.getCLocalidad());
				} else {
					localidad.setClave(ubicacion.getCColonia());
				}
				if (StringUtils.isNotBlank(ubicacion.getDLocalidad())) {
					localidad.setNombre(ubicacion.getDLocalidad());
				} else {
					localidad.setNombre(ubicacion.getDColonia());
				}

				Municipio municipio = new Municipio();
				municipio.setClave(ubicacion.getCMunicipio());
				municipio.setNombre(ubicacion.getDMunicipio());

				EntidadFederativa entidadFederativa = new EntidadFederativa();
				entidadFederativa.setClave(ubicacion.getCEntFed());
				entidadFederativa.setNombre(ubicacion.getDEntFed());
				municipio.setEntidadFederativa(entidadFederativa);

				localidad.setMunicipio(municipio);
				asentamiento.setLocalidad(localidad);
				domicilioFiscal.setAsentamiento(asentamiento);

				// Medios de Contacto
				String telefono1 = ubicacion.getTelefono1();
				String telefono2 = ubicacion.getTelefono2();
				String tipoTelefono1 = ubicacion.getTTel1();
				String tipoTelefono2 = ubicacion.getTTel2();
				String email = ubicacion.getEmail();

				if (StringUtils.isNotBlank(telefono1)) {
					sat.setTelefono1(telefono1);
				}
				if (StringUtils.isNotBlank(telefono2)) {
					sat.setTelefono2(telefono2);
				}
				if (StringUtils.isNotBlank(tipoTelefono1)) {
					if (tipoTelefono1.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono1(tipoTelefonoFijo);
					} else if (tipoTelefono1.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono1(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(tipoTelefono2) && StringUtils.isNotBlank(tipoTelefono2)) {
					if (tipoTelefono2.equals(TIPO_TELEFONO_FIJO)) {
						TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
						tipoTelefonoFijo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
						sat.setTipoTelefono2(tipoTelefonoFijo);
					} else if (tipoTelefono2.equals(TIPO_TELEFONO_MOVIL)) {
						TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
						tipoTelefonoMovil.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
						sat.setTipoTelefono2(tipoTelefonoMovil);
					}
				}
				if (StringUtils.isNotBlank(email)) {
					sat.setEmail(email);
				}

				sat.setUbicacion(domicilioFiscal);
			}	
			
			List<Regimenes> listRegimen = respuestaSat.getRegimen();
			if (listRegimen != null && !listRegimen.isEmpty()) {
				if (sat == null) {
					sat = new Sat();
				}

				sat = obtenerRegimenes(sat, respuestaSat);
			}
		}

		log.info("sat: " + sat);
		return sat;
	}	
	
	
}
