package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.arp;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rep.legal.RepresentanteLegalServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.sapi.model.business.ReportesARP;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ArpUtility {

	private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(ArpUtility.class);
    }

	public ReportesARP getDatosPersonaMoral(SujetoObligado sujetoObligado,
			DomicilioServiceBusinessRemote domicilioService,
			RepresentanteLegalServiceBusinessLocal representanteLegalService,
			MediosContactoServiceBusinessRemote mediosContactoService,
			PersonasAutorizadasServiceRemote personasAutorizadasService, Date fechaAltaSol) {

		LOG.debug("**********Obteniendo datos de la persona moral " + sujetoObligado.getNumeroRegistroPatronal());

		ReportesARP arp = new ReportesARP();

		//setea los valores de campos con guiones para los datos vacios
		arp = resetReportesARP(arp, 2);

		LOG.debug("**********Voy a setear AFIL01");

		//*******************************************************************
		//Fecha de presentacion y efecto
		//*******************************************************************
		arp.setFecPresentacion(fechaAltaSol != null ? fechaAltaSol : new Date());

		//*******************************************************************
		//Datos generales
		//*******************************************************************

		Moral moral = sujetoObligado.getMoral();
        if(sujetoObligado.getDatosICA()!=null && sujetoObligado.getDatosICA().getPersonaMoralEE()!=null)
            moral=sujetoObligado.getDatosICA().getPersonaMoralEE();
        // Se comenta a solicitud del usuario para que se imprima unicamente la razon social en el documento ARP
        /*String razonSocialTipoSociedad =
                moral.getTipoSociedad()!=null && StringUtils.isNotBlank(moral.getTipoSociedad().getDescripcionAbreviada())
                ? moral.getRazonSocial()+" "+moral.getTipoSociedad().getDescripcionAbreviada()
                : moral.getRazonSocial();*/
        arp.setNombre(moral.getRazonSocial());
        arp.setNomComercial(sujetoObligado.getNombreComercial());
        arp.setTipoSociedad(moral.getTipoSociedad().getDescripcionAbreviada());
        arp.setRfc(moral.getRfc());

		//*******************************************************************
		//Domicilio fiscal
		//*******************************************************************

		DomicilioFiscal domFis = null;

		try {
			// Se ejecuta el servicio para obtener el domicilio fiscal
			domFis = domicilioService.consultarDomicilioFiscalPersona(sujetoObligado.getMoral());
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}

		if(domFis != null){
			Asentamiento asentamiento = domFis.getAsentamiento();
			Localidad localidad = asentamiento.getLocalidad();
			arp.setCalle(domFis.getVialidadPrimaria().getNombre());
			if(domFis.getNumExteriorAlf() != null)
				arp.setNumExt(domFis.getNumExteriorAlf().toString().toUpperCase());
			if(domFis.getNumInteriorAlf() != null)
				arp.setNumInt(domFis.getNumInteriorAlf().toString().toUpperCase());
			if(domFis.getVialidadReferenciaPrimaria() != null && domFis.getVialidadReferenciaPrimaria().getNombre() != null
					&& domFis.getVialidadReferenciaPrimaria().getNombre().trim().length() > 0)
				arp.setEntrecalle1(domFis.getVialidadReferenciaPrimaria().getNombre());
			if(domFis.getVialidadReferenciaSecundaria() != null && domFis.getVialidadReferenciaSecundaria().getNombre() != null
					&& domFis.getVialidadReferenciaSecundaria().getNombre().trim().length() > 0)
			arp.setEntrecalle2(domFis.getVialidadReferenciaSecundaria().getNombre());
			arp.setColonia(domFis.getColonia());
			arp.setLocalidad(localidad.getNombre());
			arp.setMunicipio(localidad.getMunicipio().getNombre());
			arp.setEntidadaFederativa(localidad.getMunicipio().getEntidadFederativa().getNombre());
			if(asentamiento.getCodigoPostal().getCodigoPostal() != null && asentamiento.getCodigoPostal().getCodigoPostal().trim().length() > 0){
				String cp = asentamiento.getCodigoPostal().getCodigoPostal();
				if(cp.length() == 4)
					cp = "0" + cp;
				else if(cp.length() == 3)
					cp = "00" + cp;
				arp.setCp1(cp);
			}

			//obtenemos los medios de contacto para el domicilio fiscal
			Map<String, String> mediosContacto = obtenerMediosContacto("Fiscal", sujetoObligado.getMoral(), null, mediosContactoService);

			if(mediosContacto.get("telefonoFijo1") != null)
				arp.setTelefono1(mediosContacto.get("telefonoFijo1"));
			if(mediosContacto.get("telefonoFijo2") != null)
				arp.setTelefono2(mediosContacto.get("telefonoFijo2"));
			if(mediosContacto.get("correoElectronico") != null)
				arp.setCorreo(mediosContacto.get("correoElectronico"));
		}else{
			LOG.debug("********** No se encontro el domicilio fiscal");
		}


		LOG.debug("**********Voy a setear AFIL02");
		boolean isSindicato=false;
		//*******************************************************************
		//Escritura Constitutiva
		//*******************************************************************
		if(moral.getEscrituraConstitutiva() != null){
			if(StringUtils.isNotBlank(moral.getEscrituraConstitutiva().getNumEscritura()))
				arp.setNumEscritura(moral.getEscrituraConstitutiva().getNumEscritura());

			if(StringUtils.isNotBlank(moral.getEscrituraConstitutiva().getNumNotaria()))
				arp.setNumNotarira(moral.getEscrituraConstitutiva().getNumNotaria());
			if(moral.getEscrituraConstitutiva().getLugarExpedicion()!=null && StringUtils.isNotBlank(moral.getEscrituraConstitutiva().getLugarExpedicion().getNombre())){
				StringBuffer lugarExp = new StringBuffer();
				lugarExp.append(moral.getEscrituraConstitutiva().getLugarExpedicion().getNombre());
				if(moral.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa()!=null
						&& moral.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getNombre()!=null )
					lugarExp.append(", ").append(moral.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getNombre());
				arp.setLugarExpedicion(lugarExp.toString());
			}

			if(moral.getEscrituraConstitutiva().getFechaExpedicion()!=null)
				arp.setFechaExpedicion(moral.getEscrituraConstitutiva().getFechaExpedicion());

			if(StringUtils.isNotBlank(moral.getEscrituraConstitutiva().getFolioMercantil()))
				arp.setFolioMercantil(moral.getEscrituraConstitutiva().getFolioMercantil());

			if(StringUtils.isNotBlank(moral.getEscrituraConstitutiva().getNumeroRegistroPatronal()))
				arp.setNumeroReferencia(moral.getEscrituraConstitutiva().getNumeroRegistroPatronal());
//arp.setFechaDocumentoRegistro(sujetoObligado.getEscrituraConstitutiva().getFechaExpedicion());
//arp.setAutoridadLaboral("Autoridad Laboral");
		}else{
			LOG.debug("********** No se encontro escritura constitutiva");
		}

		if(moral.getRegistroSindicato()!=null){
			String numReferenciado=moral.getRegistroSindicato().getNumReferenciadocRegistro()!=null
					? moral.getRegistroSindicato().getNumReferenciadocRegistro().toString()
					: null;
			arp.setFechaDocumentoRegistro(moral.getRegistroSindicato().getFechaRegistro());
			arp.setAutoridadLaboral(moral.getRegistroSindicato().getAutoridadLaboral()!=null ?
					moral.getRegistroSindicato().getAutoridadLaboral().toUpperCase() : null);
			arp.setNumeroReferencia(numReferenciado);
		}


		//*******************************************************************
		//Socios
		//*******************************************************************
		List<Socio> socios = null;

		if(isSindicato)
			socios=new ArrayList<Socio>();
		else
			socios=sujetoObligado.getSocios();

		if(socios != null){
			LOG.debug("Se encontraron " + socios.size() + " socios");
			Socio socio = null;

			String[] atributosSocios = { "apPaterno", "apMaterno", "nombre",
					"rfc", "curp", "calle", "numExt", "numInt",
					"colonia", "localidad", "munDelegacion",
					"entFederativa", "codPost", "telfijo", "email" };

			int numMaxSocios = 4;
			int fin = 0;
			// Representa el numero de socio en el ARP
			int numSocio = 0;

			if (socios.size() > numMaxSocios) {
				LOG.debug("Los socios encontrados son mas de los permitidos en el ARP, solo se mostraran 4");
				fin = numMaxSocios;
			} else {
				fin = socios.size();
			}

			String nombreRazonSocial = null;
			String rfc = null;
			String curp = null;
			DomicilioFiscal domFiscalSocio = null;
			List<MedioContacto> mediosFiscalesSocio = null;

			for (int i = 0; i < fin; i++) {
				socio = socios.get(i);
				rfc = "";
				curp = "";
				numSocio = i + 1;

				Persona persona = socio.getPersona();

				if (persona != null) {
					if (persona instanceof Fisica) {
						Fisica socioFisica = (Fisica) persona;

						nombreRazonSocial = socioFisica.getNombreCompleto();
						rfc = socioFisica.getRfc();
						curp = socioFisica.getCurp();
						domFiscalSocio = socioFisica.getDomicilioFiscal();
						mediosFiscalesSocio = socioFisica.getMediosContactoFiscales();

					} else if (persona instanceof Moral) {
						Moral socioMoral = (Moral) persona;

						nombreRazonSocial = socioMoral.getRazonSocial();
						if(socioMoral.getTipoSociedad()!=null
								&& StringUtils.isNotBlank(socioMoral.getTipoSociedad().getDescripcionAbreviada())){
							nombreRazonSocial += " " + socioMoral.getTipoSociedad().getDescripcionAbreviada();
						}
						rfc = socioMoral.getRfc();
						domFiscalSocio = socioMoral.getDomicilioFiscal();
						mediosFiscalesSocio = socioMoral.getMediosContactoFiscales();
					}

					try {
						/*
						 * Para el nombre o razon social, en el jasper solo se hace
						 * uso del campo apPaterno#
						 */
						BeanUtils.setProperty(arp, atributosSocios[0] + numSocio, nombreRazonSocial);

						BeanUtils.setProperty(arp, atributosSocios[3] + numSocio, rfc);
						BeanUtils.setProperty(arp, atributosSocios[4] + numSocio, curp);

						if (domFiscalSocio != null) {


							if(domFiscalSocio.getCalle()!=null)
								BeanUtils.setProperty(arp, atributosSocios[5] + numSocio, domFiscalSocio.getCalle());
							if(domFiscalSocio.getNumExteriorAlf()!=null)
								BeanUtils.setProperty(arp, atributosSocios[6] + numSocio, domFiscalSocio.getNumExteriorAlf().replace("|", ""));
							if(domFiscalSocio.getNumInteriorAlf()!=null)
								BeanUtils.setProperty(arp, atributosSocios[7] + numSocio, domFiscalSocio.getNumInteriorAlf().replace("|", ""));
							if(domFiscalSocio.getColonia()!=null)
								BeanUtils.setProperty(arp, atributosSocios[8] + numSocio, domFiscalSocio.getColonia());
							if(domFiscalSocio.getAsentamiento()!=null && domFiscalSocio.getAsentamiento().getLocalidad() != null ){
								if(domFiscalSocio.getAsentamiento().getLocalidad().getNombre()!= null)
									BeanUtils.setProperty(arp, atributosSocios[9] + numSocio, domFiscalSocio.getAsentamiento().getLocalidad().getNombre());

								if(domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio()!=null){
									if(domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio().getNombre()!= null)
										BeanUtils.setProperty(arp, atributosSocios[10] + numSocio, domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio().getNombre());
									if(domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()!=null && domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() != null)
										BeanUtils.setProperty(arp, atributosSocios[11] + numSocio, domFiscalSocio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());

								}

							}

							if(domFiscalSocio.getCodigoPostal()!=null && domFiscalSocio.getCodigoPostal().getCodigoPostal()!= null)
								BeanUtils.setProperty(arp, atributosSocios[12] + numSocio, domFiscalSocio.getCodigoPostal().getCodigoPostal());

						}

						if (!CollectionUtils.isEmpty(mediosFiscalesSocio)) {
							for(MedioContacto medio : mediosFiscalesSocio) {
								TipoMedioContacto tipo = medio.getTipoMedioContacto();

								if ((tipo != null && tipo.getIdTipoMedioContacto().intValue() == TipoContactoEnum.TELEFONO_FIJO.getCodigo().intValue())
										|| (medio instanceof TelefonoFijo)) {
									BeanUtils.setProperty(arp, atributosSocios[13] + numSocio, medio.getDesFormaContacto());
								}

								if ((tipo != null && tipo.getIdTipoMedioContacto().intValue() == TipoContactoEnum.CORREO_ELECTRONICO.getCodigo().intValue())
										|| (medio instanceof CorreoElectronico)) {
									BeanUtils.setProperty(arp, atributosSocios[14] + numSocio, medio.getDesFormaContacto());
								}

							}
						}
					} catch (IllegalAccessException e) {
						LOG.error(e.getMessage());
					} catch (InvocationTargetException e) {
						LOG.error(e.getMessage());
					}
				}
			}
		}else{
			LOG.debug("********** No se encontraron socios");
		}

		//*******************************************************************************************************************************************************

		//*******************************************************************
		//Representante legal
		//*******************************************************************
		String nombreCompletoRl=null;
		// Se ejecuta servicio para obtener representante legal
		List<RepresentanteLegal> representanteLegalList = sujetoObligado.getRepresentantesLegales();

		if(representanteLegalList != null && representanteLegalList.size() > 0){
			RepresentanteLegal representanteLegal = representanteLegalList.get(0);

//			if(StringUtils.isNotBlank(sujetoObligado.getTipoPoder())
//					&& sujetoObligado.getTipoPoder().equals("Administracion"))
//				arp.setActosAdministracionRL("X");
//			else
//				arp.setActosAdministracionRL("");

			if(representanteLegal.getIndActAdmonDominio().intValue() == 1)
				arp.setActosAdministracionRL("X");
			arp.setApPaternoRL(representanteLegal.getPersonaFisica().getPrimerApellido());
			arp.setApMaternoRL(representanteLegal.getPersonaFisica().getSegundoApellido());
			arp.setNombreRL(representanteLegal.getPersonaFisica().getNombre());
			arp.setRfcRL(representanteLegal.getPersonaFisica().getRfc());
			arp.setCurpRL(representanteLegal.getPersonaFisica().getCurp());
			StringBuffer rlQueEjecutaAlta = new StringBuffer();

			Fisica pRl=representanteLegal.getPersonaFisica();
			if(!StringUtils.isBlank(pRl.getNombre()))
				rlQueEjecutaAlta.append(pRl.getNombre());
			if(!StringUtils.isBlank(pRl.getPrimerApellido()))
				rlQueEjecutaAlta.append(" "+pRl.getPrimerApellido());
			if(!StringUtils.isBlank(pRl.getSegundoApellido()))
				rlQueEjecutaAlta.append(" "+pRl.getSegundoApellido());


			nombreCompletoRl = rlQueEjecutaAlta.toString();


					//obtenemos los medios de contacto del representante legal
			Map<String, String> mediosContacto = obtenerMediosContacto("Personal", representanteLegal.getPersonaFisica(), null, mediosContactoService);

			if(mediosContacto.get("telefonoFijo1") != null)
				arp.setTelefono1RL(mediosContacto.get("telefonoFijo1"));
			if(mediosContacto.get("extension1") != null)
				arp.setExtencionRL(mediosContacto.get("extension1"));
			if(mediosContacto.get("telefonoMovil") != null)
				arp.setTelefono2RL(mediosContacto.get("telefonoMovil"));
			if(mediosContacto.get("correoElectronico") != null)
				arp.setCorreoRL(mediosContacto.get("correoElectronico"));
		}else{
			LOG.debug("********** No se encontro representante legal");
		}

		//*******************************************************************
		//Datos del centro de trabajo
		//*******************************************************************
		if(sujetoObligado.getClasificacion().getIndRegPatClase()!=null &&
				sujetoObligado.getClasificacion().getIndRegPatClase().intValue()==1){
			//Si es RPC, mandar Domicilio Fiscal como Centro de Trabajo
			arp.setCalleCT(arp.getCalle());
			arp.setNumExtCT(arp.getNumExt());
			arp.setNumIntCT(arp.getNumInt());
			arp.setEntrecalle1CT(arp.getEntrecalle1());
			arp.setEntrecalle2CT(arp.getEntrecalle2());
			arp.setColoniaCT(arp.getColonia());
			arp.setLocalidadCT(arp.getLocalidad());
			arp.setMunicipioCT(arp.getMunicipio());
			arp.setEntidadaFederativaCT(arp.getEntidadaFederativa());
			arp.setTelefono1CT(arp.getTelefono1());
			arp.setTelefono2CT((arp.getTelefono2()));
			arp.setCorreoCT(arp.getCorreo());
			arp.setCpCT(arp.getCp());
			arp.setCpCT1(arp.getCp1());
			arp.setExtencion1CT(arp.getExtencion1());
			arp.setExtencion2CT(arp.getExtencion2());
		}else{
			//No es RPC, obtener Centro de Trabajo
			CentroTrabajo centroTrabajo = sujetoObligado.getCntroTrabajo();
			String numeroExteriorCompleto = "";
			if(centroTrabajo != null){
				if(centroTrabajo.getVialidadPrimaria()!=null){
					LOG.debug("*********** LA CALLE ES ******** [" +centroTrabajo.getVialidadPrimaria().getNombre() +"]"
							+ "y la calle de [" + centroTrabajo.getCalle()+"]");
				}
				arp.setCalleCT(centroTrabajo.getVialidadPrimaria()!=null ? centroTrabajo.getVialidadPrimaria().getNombre()
						: (centroTrabajo.getCalle()!=null?centroTrabajo.getCalle():""));
				LOG.debug("*********** LA CALLE ES ARP SETEADA ES ******** [" +arp.getCalleCT()+"]");

				if(centroTrabajo.getNumExterior1() != null && centroTrabajo.getNumExterior1() != 0){
					numeroExteriorCompleto = centroTrabajo.getNumExterior1().toString()+" ";
				}
				if(centroTrabajo.getNumExteriorAlf()!=null)
					numeroExteriorCompleto += centroTrabajo.getNumExteriorAlf();


				if(StringUtils.isNotBlank(numeroExteriorCompleto))
					arp.setNumExtCT(numeroExteriorCompleto.toUpperCase());


				if(centroTrabajo.getNumInterior() != null && centroTrabajo.getNumInterior() != 0){
					String numInteriorCompleto = centroTrabajo.getNumInterior().toString();
					if(centroTrabajo.getNumInteriorAlf()!=null)
						numInteriorCompleto += centroTrabajo.getNumInteriorAlf();
					arp.setNumIntCT(numInteriorCompleto.toUpperCase());
				} else if(centroTrabajo.getNumInteriorAlf()!=null) {
					arp.setNumIntCT(centroTrabajo.getNumInteriorAlf());
				}

				if(centroTrabajo.getVialidadReferenciaPrimaria()!=null)
					if(StringUtils.isNotBlank(centroTrabajo.getVialidadReferenciaPrimaria().getNombre()))
						arp.setEntrecalle1CT(centroTrabajo.getVialidadReferenciaPrimaria().getNombre());
				if(centroTrabajo.getVialidadReferenciaSecundaria()!=null)
					if(StringUtils.isNotBlank(centroTrabajo.getVialidadReferenciaSecundaria().getNombre()))
						arp.setEntrecalle2CT(centroTrabajo.getVialidadReferenciaSecundaria().getNombre());

				if(centroTrabajo.getVialidadReferenciaPosterior()!=null && StringUtils.isNotBlank(centroTrabajo.getVialidadReferenciaPosterior().getNombre()))
					arp.setCallePostCT(centroTrabajo.getVialidadReferenciaPosterior().getNombre());


				if(centroTrabajo.getAsentamiento().getNombre()!=null)
					arp.setColoniaCT(centroTrabajo.getAsentamiento().getNombre().toUpperCase());


				arp.setLocalidadCT(centroTrabajo.getAsentamiento().getLocalidad().getNombre());
				arp.setMunicipioCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
				arp.setEntidadaFederativaCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());

				if(centroTrabajo.getCodigoPostal() != null && centroTrabajo.getCodigoPostal().getCodigoPostal() != null
						&& centroTrabajo.getCodigoPostal().getCodigoPostal().trim().length() > 0)
				arp.setCpCT1(centroTrabajo.getCodigoPostal().getCodigoPostal());

				//obtenemos los medios de contacto para el centro de trabajo
				if(sujetoObligado.getCveIdSujetoObligado()!=null){
					Map<String, String> mediosContacto = obtenerMediosContacto("CentroTrabajo", null, sujetoObligado.getCveIdSujetoObligado(), mediosContactoService);

					if(mediosContacto.get("telefonoFijo1") != null)
						arp.setTelefono1CT(mediosContacto.get("telefonoFijo1"));
					if(mediosContacto.get("extension1") != null)
						arp.setExtencion1CT(mediosContacto.get("extension1"));
					if(mediosContacto.get("telefonoFijo2") != null && !mediosContacto.get("telefonoFijo2").equals("||"))
						arp.setTelefono2CT(mediosContacto.get("telefonoFijo2"));
					if(mediosContacto.get("extension2") != null)
						arp.setExtencion2CT(mediosContacto.get("extension2"));
					if(mediosContacto.get("correoElectronico") != null)
						arp.setCorreoCT(mediosContacto.get("correoElectronico"));
				} else if(centroTrabajo.getMediosContacto() != null && !centroTrabajo.getMediosContacto().isEmpty()) {
					/**
					 * Este if es cuando se esta generando la visualizacion previa para que se muestre en el reporte lo capturado
					 */
					for(MedioContacto medio: centroTrabajo.getMediosContacto()) {
						Long idVista = medio.getIdVista();
						String descripcion = medio.getDesFormaContacto();
						if(idVista.equals(1L)) {
							String[] telefono = descripcion.split("\\|");
							arp.setTelefono1CT(""+telefono[0]+""+telefono[1] + "");
							if(telefono.length == 3) {
								arp.setExtencion1CT(telefono[2]);
							}
						} else if(idVista.equals(2L)) {
							String[] telefono = descripcion.split("\\|");
							String tel = telefono[0];
							if(telefono.length > 1) {
								tel = tel + "" + telefono[1];
							}
							arp.setTelefono2CT(""+tel);
							if(telefono.length == 3) {
								arp.setExtencion2CT(telefono[2]);
							}
						} else if(idVista.equals(3L)) {
							arp.setCorreoCT(descripcion);
						}
					}
				}
			}else{
				LOG.debug("********** No se encontro centro de trabajo");
			}
		}

		//*******************************************************************
		//Personas autorizadas
		//*******************************************************************
		Persona persona = new Persona();
		persona.setRfc(moral.getRfc());
		persona.setIdPersona(moral.getIdPersona());
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);

		//Ejecuta servicio para buscar las personas autorizadas
		List<PersonaAutorizada> personasAutorizadas = sujetoObligado.getPersonasAutorizadas();

		if(personasAutorizadas != null && !personasAutorizadas.isEmpty()){
			LOG.debug("******** Encontre " + personasAutorizadas.size()
					+ " personas Autorizadas, se imprimiran solo las que correspondan al RP " + sujetoObligado.getNumeroRegistroPatronal());

			PersonaAutorizada personaAutorizada = null;
			int cont = 1;

			for (Iterator<PersonaAutorizada> iterator = personasAutorizadas.iterator(); iterator.hasNext();) {
				personaAutorizada = iterator.next();

				if ( personaAutorizada != null) {

					Fisica pf = personaAutorizada.getFisica();
					//Map<String, String> mediosContacto = obtenerMediosContacto("Personal", pf, null, mediosContactoService);

					if(pf==null || (
							pf!=null && StringUtils.isBlank(pf.getRfc())
							)){
						cont +=1;
						continue;
					}

					if(cont == 1 ){
						arp.setApPaternoPA1(pf.getPrimerApellido());
						arp.setApMaternoPA1(pf.getSegundoApellido());
						arp.setNombrePA1(pf.getNombre());
						arp.setRfcPA1(pf.getRfc());
						arp.setCurpPA1(pf.getCurp());

						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA1(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA1(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA1(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA1(correo.getCorreo());
					}else if(cont == 2){
						arp.setApPaternoPA2(pf.getPrimerApellido());
						arp.setApMaternoPA2(pf.getSegundoApellido());
						arp.setNombrePA2(pf.getNombre());
						arp.setRfcPA2(pf.getRfc());
						arp.setCurpPA2(pf.getCurp());

						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA2(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA2(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA2(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA2(correo.getCorreo());

					}else if(cont == 3){
						arp.setApPaternoPA3(pf.getPrimerApellido());
						arp.setApMaternoPA3(pf.getSegundoApellido());
						arp.setNombrePA3(pf.getNombre());
						arp.setRfcPA3(pf.getRfc());
						arp.setCurpPA3(pf.getCurp());

						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA3(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA3(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA3(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA3(correo.getCorreo());


					}else{
						break;
					}

					cont += 1;
				}

			}

		}else{
			LOG.debug("********** No se encontraron personas autorizadas");
		}

		//*******************************************************************************************************************************************************
		LOG.debug("**********Voy a setear AFIL03");

		//*******************************************************************
		//Clasificacion actividad economica
		//*******************************************************************
		Clasificacion clas = sujetoObligado.getClasificacion();

		if(clas != null){
			if(StringUtils.isNotBlank(clas.getGiro()))
				arp.setGiro(clas.getGiro().trim());
			else
				arp.setGiro(clas.getGiro());

			if(clas.getIndPrestaServicioPersonal() != null){
				if(clas.getIndPrestaServicioPersonal().equals(2))
					arp.setPrestaServPersSi("X");
				else
					arp.setPrestaServPersNo("X");
			}
			if(clas.getNumCentrosTraba() != null)
				arp.setNoCentTrab(clas.getNumCentrosTraba().intValue());
			else
				arp.setNoCentTrab(null);

			if(clas.getIndRegPatClase() != null && clas.getIndRegPatClase().intValue()==1)
				arp.setSolRegPatronalClase("X");
			arp.setDivisionClave(clas.getFraccion().getGrupo().getDivision().getNumDivision());
			arp.setDivisionDes(clas.getFraccion().getGrupo().getDivision().getDescripcion());
			arp.setGrupoClave(arp.getDivisionClave() + clas.getFraccion().getGrupo().getNumGrupo());
			arp.setGrupoDes(clas.getFraccion().getGrupo().getDescripcion());
			String f = clas.getFraccion().getNumFraccion();
//			if(f.length() == 1)
//				f = "0" + f;
			arp.setFraccionClave(arp.getGrupoClave() + f);

			arp.setFraccionDes(clas.getFraccion().getDescripcionDetallada());
			arp.setClase(clas.getFraccion().getClase().getDescripcion());
			arp.setPrima(clas.getFraccion().getPrimaSRT().toString());

			arp.setFecEfecto(clas.getFecEfecto());
		}else{
			LOG.debug("********** No se encontro clasificacion");
		}

		//*******************************************************************
		//Principales productos elaborados
		//*******************************************************************

		List<Producto> productos = sujetoObligado.getProductos();
		if(productos != null){
			Producto producto = null;
			int cont = 1;
			for (Iterator<Producto> iterator = productos.iterator(); iterator.hasNext();) {
				producto = iterator.next();
				if(cont == 1)
					arp.setProductosOServicios1(producto.getDescripcion());
				else if(cont == 2)
					arp.setProductosOServicios2(producto.getDescripcion());
				else if(cont == 3)
					arp.setProductosOServicios3(producto.getDescripcion());
				else if(cont == 4)
					arp.setProductosOServicios4(producto.getDescripcion());
				else if(cont == 5)
					arp.setProductosOServicios5(producto.getDescripcion());
				else if(cont == 6)
					arp.setProductosOServicios6(producto.getDescripcion());
				else if(cont == 7)
					arp.setProductosOServicios7(producto.getDescripcion());
				else if(cont == 8)
					arp.setProductosOServicios8(producto.getDescripcion());
				else if(cont == 9)
					arp.setProductosOServicios9(producto.getDescripcion());
				else if(cont == 10)
					arp.setProductosOServicios10(producto.getDescripcion());
				else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontraron productos elaborados");
		}

		//*******************************************************************
		//Principales materias primas
		//*******************************************************************

		List<MateriaPrima> materiasPrimas = sujetoObligado.getMateriaPrimaMateriales();
		if(materiasPrimas != null){
			MateriaPrima materiaPrima = null;
			int cont = 1;
			for (Iterator<MateriaPrima> iterator = materiasPrimas.iterator(); iterator.hasNext();) {
				materiaPrima = iterator.next();
				if(cont == 1)
					arp.setMateriasPrimas1(materiaPrima.getDescripcion());
				else if(cont == 2)
					arp.setMateriasPrimas2(materiaPrima.getDescripcion());
				else if(cont == 3)
					arp.setMateriasPrimas3(materiaPrima.getDescripcion());
				else if(cont == 4)
					arp.setMateriasPrimas4(materiaPrima.getDescripcion());
				else if(cont == 5)
					arp.setMateriasPrimas5(materiaPrima.getDescripcion());
				else if(cont == 6)
					arp.setMateriasPrimas6(materiaPrima.getDescripcion());
				else if(cont == 7)
					arp.setMateriasPrimas7(materiaPrima.getDescripcion());
				else if(cont == 8)
					arp.setMateriasPrimas8(materiaPrima.getDescripcion());
				else if(cont == 9)
					arp.setMateriasPrimas9(materiaPrima.getDescripcion());
				else if(cont == 10)
					arp.setMateriasPrimas10(materiaPrima.getDescripcion());
				else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontraron materias primas");
		}

		//*******************************************************************
		//Maquinaria y equipo utilizado
		//*******************************************************************

		List<MaquinariaEquipo> maquinariaEquipos = sujetoObligado.getEquipos();
		if(maquinariaEquipos != null){
			MaquinariaEquipo maquinariaEquipo = null;
			int cont = 1;
			for (Iterator<MaquinariaEquipo> iterator = maquinariaEquipos.iterator(); iterator.hasNext();) {
				maquinariaEquipo = iterator.next();
				if(cont == 1){
					arp.setMaqEqpNU1(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom1(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso1(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo1(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot1(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 2){
					arp.setMaqEqpNU2(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom2(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso2(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo2(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot2(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 3){
					arp.setMaqEqpNU3(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom3(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso3(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo3(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot3(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 4){
					arp.setMaqEqpNU4(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom4(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso4(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo4(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot4(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 5){
					arp.setMaqEqpNU5(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom5(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso5(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo5(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot5(maquinariaEquipo.getDesCapacidadPotencia());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro maquinaria y equipo utilizado");
		}

		//*******************************************************************
		//Equipo de transporte utilizado
		//*******************************************************************

		List<EquipoTransporte> equiposTransporte = sujetoObligado.getEquiposTransporte();
		if(equiposTransporte != null){
			EquipoTransporte equipoTransporte = null;
			int cont = 1;
			for (Iterator<EquipoTransporte> iterator = equiposTransporte.iterator(); iterator.hasNext();) {
				equipoTransporte = iterator.next();
				if(cont == 1){
					arp.setEqpTrnNU1(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom1(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso1(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo1(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot1(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 2){
					arp.setEqpTrnNU2(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom2(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso2(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo2(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot2(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 3){
					arp.setEqpTrnNU3(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom3(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso3(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo3(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot3(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 4){
					arp.setEqpTrnNU4(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom4(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso4(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo4(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot4(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 5){
					arp.setEqpTrnNU5(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom5(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso5(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo5(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot5(equipoTransporte.getDesCapacidadPotencia());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro equipo de transporte utilizado");
		}

		//*******************************************************************
		//Procesos de trabajo de la actividad del patron
		//*******************************************************************
		if(sujetoObligado.getProceso().getDesInicial() != null)
			arp.setProcesoInicial1(sujetoObligado.getProceso().getDesInicial().trim());

		//*******************************************************************************************************************************************************
		LOG.debug("**********Voy a setear AFIL04");

		//*******************************************************************
		//Procesos de trabajo de la actividad del patron
		//*******************************************************************
		if(sujetoObligado.getProceso().getDesIntermedio() != null)
			arp.setProcesoIntermedio1(sujetoObligado.getProceso().getDesIntermedio().trim());
		if(sujetoObligado.getProceso().getDesFinal() != null)
			arp.setProcesoFinal1(sujetoObligado.getProceso().getDesFinal().trim());

		//*******************************************************************
		//Personal
		//*******************************************************************

		List<Personal> personalList = sujetoObligado.getPersonal();
		if(personalList != null){
			Personal personal = null;
			int cont = 1;
			for (Iterator<Personal> iterator = personalList.iterator(); iterator.hasNext();) {
				personal = iterator.next();
				if(cont == 1){
					arp.setPerNoTrab1(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup1(personal.getOficioOcupacion());
				}else if(cont == 2){
					arp.setPerNoTrab2(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup2(personal.getOficioOcupacion());
				}else if(cont == 3){
					arp.setPerNoTrab3(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup3(personal.getOficioOcupacion());
				}else if(cont == 4){
					arp.setPerNoTrab4(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup4(personal.getOficioOcupacion());
				}else if(cont == 5){
					arp.setPerNoTrab5(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup5(personal.getOficioOcupacion());
				}else if(cont == 6){
					arp.setPerNoTrab6(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup6(personal.getOficioOcupacion());
				}else if(cont == 7){
					arp.setPerNoTrab7(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup7(personal.getOficioOcupacion());
				}else if(cont == 8){
					arp.setPerNoTrab8(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup8(personal.getOficioOcupacion());
				}else if(cont == 9){
					arp.setPerNoTrab9(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup9(personal.getOficioOcupacion());
				}else if(cont == 10){
					arp.setPerNoTrab10(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup10(personal.getOficioOcupacion());
				}else if(cont == 11){
					arp.setPerNoTrab11(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup11(personal.getOficioOcupacion());
				}else if(cont == 12){
					arp.setPerNoTrab12(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup12(personal.getOficioOcupacion());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro personal");
		}

		//*******************************************************************
		//Actividades complementarias a la principal
		//*******************************************************************
		if(clas != null){
			if(clas.getIndTransportePropio() != null && clas.getIndTransportePropio().equals(1))
				arp.setConTransportePropio("X");
			if(clas.getIndTransporteAjeno() != null && clas.getIndTransporteAjeno().equals(1))
				arp.setSinTransportePropio("X");
			if(clas.getIndDistribuyeEntrega() != null && clas.getIndDistribuyeEntrega().equals(1))
				arp.setNoDistribuye("X");
			if(clas.getIndServiciosATerceros() != null && clas.getIndServiciosATerceros().equals(1))
				arp.setServiciosInstalacion("X");
		}else{
			LOG.debug("********** No se encontro clasificacion");
		}

		//*******************************************************************
		//Firma del patron y acuse de recibido
		//*******************************************************************
		if(sujetoObligado.getNumeroRegistroPatronal() != null && sujetoObligado.getNumeroRegistroPatronal().trim().length() > 0)
			arp.setNrp(sujetoObligado.getNumeroRegistroPatronal());
		arp.setDelegacion(sujetoObligado.getSubdelegacion().getDelegacion().getDescripcion());
		arp.setSubDelegacion(sujetoObligado.getSubdelegacion().getDescripcion());

		if(nombreCompletoRl != null && nombreCompletoRl.trim().length() > 0)
			arp.setNombreCompletoRl(nombreCompletoRl);

//		if(rfcRL != null && rfcRL.trim().length() > 0)
//			arp.setRfc(rfcRL);

		arp.setCadenaOriginal("cadenaOriginal");
		arp.setFirmaDigital("firmaDigital");

		//Imagenes del reporte
		arp.setLOGO_IMSS_HEADER_PARAM("logo_imss.jpg");
		arp.setLOGO_INFO_HEADER_PARAM("logo_info.jpg");

		LOG.debug("**********termine de llenar VO perona Moral");

		return arp;
	}

	public ReportesARP getDatosPersonaFisica(SujetoObligado sujetoObligado,
			DomicilioServiceBusinessRemote domicilioService,
			RepresentanteLegalServiceBusinessLocal representanteLegalService,
			MediosContactoServiceBusinessRemote mediosContactoService,
			PersonasAutorizadasServiceRemote personasAutorizadasService, Date fechaAltaSol) {

		LOG.debug("**********Obteniendo datos de la persona fisica " + sujetoObligado.getNumeroRegistroPatronal());

		ReportesARP arp = new ReportesARP();

		//setea los valores de campos con guiones para los datos vacios
		arp = resetReportesARP(arp, 1);

		LOG.debug("**********Voy a setear AFIL01");

		//*******************************************************************
		//Fecha de presentacion y efecto
		//*******************************************************************
		if(fechaAltaSol!=null){
			System.err.println("Se agrega fecha de presentacion:"+fechaAltaSol);
		}else{
			System.err.println("No habia fecha de presentacion para arp");
			fechaAltaSol = Calendar.getInstance().getTime();
		}
		arp.setFecPresentacion(fechaAltaSol);

		//*******************************************************************
		//Datos generales
		//*******************************************************************

		Fisica fisica = sujetoObligado.getFisica();
		if(sujetoObligado.getDatosICA()!=null && sujetoObligado.getDatosICA().getPersonaFisicaEE()!=null){
			fisica = sujetoObligado.getDatosICA().getPersonaFisicaEE();
			if(sujetoObligado.getDatosICA().getPersonaFisicaIMSS()!=null && fisica !=null){
				fisica.setIdPersona(sujetoObligado.getDatosICA().getPersonaFisicaIMSS().getIdPersona());
				fisica.setCveFisica(sujetoObligado.getDatosICA().getPersonaFisicaIMSS().getCveFisica());
			}
		}

		arp.setApMaterno(fisica.getSegundoApellido());
		arp.setApPaterno(fisica.getPrimerApellido());
		arp.setNombre(fisica.getNombre());
		arp.setCurp(fisica.getCurp());
		arp.setRfc(fisica.getRfc());


		if(sujetoObligado.getNombreComercial() != null && sujetoObligado.getNombreComercial().trim().length() > 0)
			arp.setNomComercial(sujetoObligado.getNombreComercial());

		String nombreCompletoRL = fisica.getNombre() + " " + fisica.getPrimerApellido() + " " + fisica.getSegundoApellido();

		//*******************************************************************
		//Domicilio fiscal
		//*******************************************************************

		DomicilioFiscal domFis = null;

		try {
			// Se ejecuta el servicio para obtener el domicilio fiscal
			domFis = domicilioService.consultarDomicilioFiscalPersona(fisica);
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}

		if(domFis != null){
			Asentamiento asentamiento = domFis.getAsentamiento();
			Localidad localidad = asentamiento.getLocalidad();
			arp.setCalle(domFis.getVialidadPrimaria().getNombre());
			if(domFis.getNumExteriorAlf() != null)
				arp.setNumExt(domFis.getNumExteriorAlf());
			if(domFis.getNumInteriorAlf() != null)
				arp.setNumInt(domFis.getNumInteriorAlf());
			if(domFis.getVialidadReferenciaPrimaria() != null && domFis.getVialidadReferenciaPrimaria().getNombre() != null
					&& domFis.getVialidadReferenciaPrimaria().getNombre().trim().length() > 0)
				arp.setEntrecalle1(domFis.getVialidadReferenciaPrimaria().getNombre());
			if(domFis.getVialidadReferenciaSecundaria() != null && domFis.getVialidadReferenciaSecundaria().getNombre() != null
					&& domFis.getVialidadReferenciaSecundaria().getNombre().trim().length() > 0)
				arp.setEntrecalle2(domFis.getVialidadReferenciaSecundaria().getNombre());
			arp.setColonia(domFis.getColonia());
			arp.setLocalidad(localidad.getNombre());
			arp.setMunicipio(localidad.getMunicipio().getNombre());
			arp.setEntidadaFederativa(localidad.getMunicipio().getEntidadFederativa().getNombre());
			if(asentamiento.getCodigoPostal().getCodigoPostal() != null && asentamiento.getCodigoPostal().getCodigoPostal().trim().length() > 0){
				String cp = asentamiento.getCodigoPostal().getCodigoPostal();
				if(cp.length() == 4)
					cp = "0" + cp;
				else if(cp.length() == 3)
					cp = "00" + cp;
				arp.setCp(cp);
			}

			//obtenemos los medios de contacto para el domicilio fiscal
			Map<String, String> mediosContacto = obtenerMediosContacto("Fiscal", sujetoObligado.getFisica(), null, mediosContactoService);

			if(mediosContacto.get("telefonoFijo1") != null)
				arp.setTelefono1(mediosContacto.get("telefonoFijo1"));
			if(mediosContacto.get("extension1") != null)
				arp.setExtencion1(mediosContacto.get("extension1"));
			if(mediosContacto.get("telefonoFijo2") != null)
				arp.setTelefono2(mediosContacto.get("telefonoFijo2"));
			if(mediosContacto.get("extension2") != null)
				arp.setExtencion2(mediosContacto.get("extension2"));
			if(mediosContacto.get("correoElectronico") != null)
				arp.setCorreo(mediosContacto.get("correoElectronico"));
		}else{
			LOG.debug("********** No se encontro el domicilio fiscal");
		}

		//*******************************************************************
		//Representante legal
		//*******************************************************************

		// Se quita por peticion de no imprimir representante legal

		List<RepresentanteLegal> representanteLegalList = sujetoObligado.getRepresentantesLegales();

		if(representanteLegalList != null && representanteLegalList.size() > 0){
			RepresentanteLegal representanteLegal = representanteLegalList.get(0);
//			if(StringUtils.isNotBlank(sujetoObligado.getTipoPoder())
//					&& sujetoObligado.getTipoPoder().equals("Administracion"))
//				arp.setActosAdministracionRL("X");
//			else
//				arp.setActosAdministracionRL("");
			if(representanteLegal.getIndActAdmonDominio().intValue() == 1)
				arp.setActosAdministracionRL("X");
			arp.setApPaternoRL(representanteLegal.getPersonaFisica().getPrimerApellido());
			arp.setApMaternoRL(representanteLegal.getPersonaFisica().getSegundoApellido());
			arp.setNombreRL(representanteLegal.getPersonaFisica().getNombre());
			arp.setRfcRL(representanteLegal.getPersonaFisica().getRfc());
			arp.setCurpRL(representanteLegal.getPersonaFisica().getCurp());

			StringBuffer rlQueEjecutaAlta=new StringBuffer();
			Fisica pRl=representanteLegal.getPersonaFisica();
			if(!StringUtils.isBlank(pRl.getNombre()))
				rlQueEjecutaAlta.append(pRl.getNombre());
			if(!StringUtils.isBlank(pRl.getPrimerApellido()))
				rlQueEjecutaAlta.append(" "+pRl.getPrimerApellido());
			if(!StringUtils.isBlank(pRl.getSegundoApellido()))
				rlQueEjecutaAlta.append(" "+pRl.getSegundoApellido());

			nombreCompletoRL = rlQueEjecutaAlta.toString();

			//obtenemos los medios de contacto del representante legal
			Map<String, String> mediosContacto = obtenerMediosContacto("Personal", representanteLegal.getPersonaFisica(), null, mediosContactoService);

			if(mediosContacto.get("telefonoFijo1") != null)
				arp.setTelefono1RL(mediosContacto.get("telefonoFijo1"));
			if(mediosContacto.get("extension1") != null)
				arp.setExtencionRL(mediosContacto.get("extension1"));
			if(mediosContacto.get("telefonoMovil") != null)
				arp.setTelefono2RL(mediosContacto.get("telefonoMovil"));
			if(mediosContacto.get("correoElectronico") != null)
				arp.setCorreoRL(mediosContacto.get("correoElectronico"));
		}else{
			LOG.debug("********** No se encontro representante legal");
		}

		//*******************************************************************
		//Datos del centro de trabajo
		//*******************************************************************
		if(sujetoObligado.getClasificacion().getIndRegPatClase()!=null &&
				sujetoObligado.getClasificacion().getIndRegPatClase().intValue()==1){
			//Si es RPC, mandar Domicilio Fiscal como Centro de Trabajo
			arp.setCalleCT(arp.getCalle());
			arp.setNumExtCT(arp.getNumExt());
			arp.setNumIntCT(arp.getNumInt());
			arp.setEntrecalle1CT(arp.getEntrecalle1());
			arp.setEntrecalle2CT(arp.getEntrecalle2());
			arp.setColoniaCT(arp.getColonia());
			arp.setLocalidadCT(arp.getLocalidad());
			arp.setMunicipioCT(arp.getMunicipio());
			arp.setEntidadaFederativaCT(arp.getEntidadaFederativa());
			arp.setTelefono1CT(arp.getTelefono1());
			arp.setTelefono2CT((arp.getTelefono2()));
			arp.setCorreoCT(arp.getCorreo());
			arp.setCpCT(arp.getCp());
			arp.setCpCT1(arp.getCp1());
			arp.setExtencion1CT(arp.getExtencion1());
			arp.setExtencion2CT(arp.getExtencion2());
		}else{
			//No es RPC, obtener Centro de Trabajo
			CentroTrabajo centroTrabajo = sujetoObligado.getCntroTrabajo();
			if(centroTrabajo != null){
				arp.setCalleCT(centroTrabajo.getVialidadPrimaria()!=null ? centroTrabajo.getVialidadPrimaria().getNombre()
					: (centroTrabajo.getCalle()!=null?centroTrabajo.getCalle():""));
				String numeroExtCompleto = "";
				if(centroTrabajo.getNumExterior1() != null && centroTrabajo.getNumExterior1()!=0)
					numeroExtCompleto = centroTrabajo.getNumExterior1().toString()+" ";
				if(centroTrabajo.getNumExteriorAlf()!=null)
					numeroExtCompleto += centroTrabajo.getNumExteriorAlf();

				if(StringUtils.isNotBlank(numeroExtCompleto))
					arp.setNumExtCT(numeroExtCompleto.toUpperCase());

				String numeroInteriorCompleto = "";
				if(centroTrabajo.getNumInterior() != null && centroTrabajo.getNumInterior() != 0)
					numeroInteriorCompleto = centroTrabajo.getNumInterior().toString()+" ";
				if(centroTrabajo.getNumInteriorAlf()!=null)
					numeroInteriorCompleto += centroTrabajo.getNumInteriorAlf();

				if(StringUtils.isNotBlank(numeroInteriorCompleto))
					arp.setNumIntCT(numeroInteriorCompleto.toUpperCase());

				if(centroTrabajo.getVialidadReferenciaPrimaria()!=null && centroTrabajo.getVialidadReferenciaPrimaria().getNombre() != null && centroTrabajo.getVialidadReferenciaPrimaria().getNombre().trim().length() > 0)
					arp.setEntrecalle1CT(centroTrabajo.getVialidadReferenciaPrimaria().getNombre());
				if(centroTrabajo.getVialidadReferenciaSecundaria()!=null && centroTrabajo.getVialidadReferenciaSecundaria().getNombre() != null && centroTrabajo.getVialidadReferenciaSecundaria().getNombre().trim().length() > 0)
					arp.setEntrecalle2CT(centroTrabajo.getVialidadReferenciaSecundaria().getNombre());

				if(centroTrabajo.getAsentamiento().getNombre()!=null)
					arp.setColoniaCT(centroTrabajo.getAsentamiento().getNombre().toUpperCase());
				arp.setLocalidadCT(centroTrabajo.getAsentamiento().getLocalidad().getNombre());
				arp.setMunicipioCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
				arp.setEntidadaFederativaCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());

				if(centroTrabajo.getCodigoPostal() != null && centroTrabajo.getCodigoPostal().getCodigoPostal() != null
						&& centroTrabajo.getCodigoPostal().getCodigoPostal().trim().length() > 0){
					String cp = centroTrabajo.getCodigoPostal().getCodigoPostal();
					if(cp.length() == 4)
						cp = "0" + cp;
					else if(cp.length() == 3)
						cp = "00" + cp;
					arp.setCpCT(cp);
				}

				//obtenemos los medios de contacto para el centro de trabajo
				Map<String, String> mediosContacto = obtenerMediosContacto("CentroTrabajo", null, sujetoObligado.getCveIdSujetoObligado(), mediosContactoService);

				if(mediosContacto.get("telefonoFijo1") != null)
					arp.setTelefono1CT(mediosContacto.get("telefonoFijo1"));
				if(mediosContacto.get("extension1") != null)
					arp.setExtencion1CT(mediosContacto.get("extension1"));
				if(mediosContacto.get("telefonoFijo2") != null && !mediosContacto.get("telefonoFijo2").equals("||"))
					arp.setTelefono2CT(mediosContacto.get("telefonoFijo2"));
				if(mediosContacto.get("extension2") != null)
					arp.setExtencion2CT(mediosContacto.get("extension2"));
				if(mediosContacto.get("correoElectronico") != null)
					arp.setCorreoCT(mediosContacto.get("correoElectronico"));

			}else{
				LOG.debug("********** No se encontro centro de trabajo");
			}
		}

		//*******************************************************************************************************************************************************
		LOG.debug("**********Voy a setear AFIL02");

		//*******************************************************************
		//Personas autorizadas
		//*******************************************************************

		Persona persona = new Persona();
		persona.setRfc(fisica.getRfc());
		persona.setIdPersona(fisica.getIdPersona());
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);

		//Ejecuta servicio para buscar las personas autorizadas
		List<PersonaAutorizada> personasAutorizadas = sujetoObligado.getPersonasAutorizadas();

		if(personasAutorizadas != null && !personasAutorizadas.isEmpty()){
			LOG.debug("******** Encontre " + personasAutorizadas.size()
					+ " personas Autorizadas, se imprimiran solo las que correspondan al RP " + sujetoObligado.getNumeroRegistroPatronal());
			PersonaAutorizada personaAutorizada = null;
			int cont = 1;

			for (Iterator<PersonaAutorizada> iterator = personasAutorizadas.iterator(); iterator.hasNext();) {
				personaAutorizada = iterator.next();

				if ( personaAutorizada != null ) {
					Fisica pf = personaAutorizada.getFisica();


					if(pf==null || (
							pf!=null && StringUtils.isBlank(pf.getRfc())
							)){
						cont +=1;
						continue;
					}
					//Map<String, String> mediosContacto = obtenerMediosContacto("Personal", pf, null, mediosContactoService);

					if(cont == 1){
						arp.setApPaternoPA1(pf.getPrimerApellido());
						arp.setApMaternoPA1(pf.getSegundoApellido());
						arp.setNombrePA1(pf.getNombre());
						arp.setRfcPA1(pf.getRfc());
						arp.setCurpPA1(pf.getCurp());

						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA1(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA1(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA1(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA1(correo.getCorreo());
					}else if(cont == 2){
						arp.setApPaternoPA2(pf.getPrimerApellido());
						arp.setApMaternoPA2(pf.getSegundoApellido());
						arp.setNombrePA2(pf.getNombre());
						arp.setRfcPA2(pf.getRfc());
						arp.setCurpPA2(pf.getCurp());
						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA2(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA2(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA2(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA2(correo.getCorreo());
					}else if(cont == 3){
						arp.setApPaternoPA3(pf.getPrimerApellido());
						arp.setApMaternoPA3(pf.getSegundoApellido());
						arp.setNombrePA3(pf.getNombre());
						arp.setRfcPA3(pf.getRfc());
						arp.setCurpPA3(pf.getCurp());
						TelefonoFijo telefonoFijo=pf.getTelefonoFijo();
						TelefonoMovil telefonoMovil=pf.getTelefonoMovil();
						CorreoElectronico correo=pf.getCorreoElectronico();

						if(telefonoFijo != null){
							arp.setTelefono1PA3(telefonoFijo.getNumero());
							if(StringUtils.isNotBlank(telefonoFijo.getExtension()))
								arp.setExtencionPA3(telefonoFijo.getExtension());
						}
						if(telefonoMovil != null)
							arp.setTelefono2PA3(telefonoMovil.getNumero());
						if(correo != null)
							arp.setCorreoPA3(correo.getCorreo());
					}else{
						break;
					}

					cont += 1;
				}

			}

		}else{
			LOG.debug("********** No se encontraron personas autorizadas");
		}

		//*******************************************************************
		//Clasificacion actividad economica
		//*******************************************************************
		Clasificacion clas = sujetoObligado.getClasificacion();

		if(clas != null){
			arp.setGiro(clas.getGiro());

			if(clas.getIndPrestaServicioPersonal() != null){
				if(clas.getIndPrestaServicioPersonal().equals(2))
					arp.setPrestaServPersSi("X");
				else
					arp.setPrestaServPersNo("X");
			}

			if(clas.getNumCentrosTraba() != null)
				arp.setNoCentTrab(clas.getNumCentrosTraba().intValue());
			else
				arp.setNoCentTrab(null);

			if(clas.getIndRegPatClase() != null && clas.getIndRegPatClase().intValue()==1)
				arp.setSolRegPatronalClase("X");
			arp.setDivisionClave(clas.getFraccion().getGrupo().getDivision().getNumDivision());
			arp.setDivisionDes(clas.getFraccion().getGrupo().getDivision().getDescripcion());
			arp.setGrupoClave(arp.getDivisionClave() + clas.getFraccion().getGrupo().getNumGrupo());
			arp.setGrupoDes(clas.getFraccion().getGrupo().getDescripcion());
			String f = clas.getFraccion().getNumFraccion();
//			if(f.length() == 1)
//				f = "0" + f;
			arp.setFraccionClave(arp.getGrupoClave() + f);
			arp.setFraccionDes(clas.getFraccion().getDescripcionDetallada());
			arp.setClase(clas.getFraccion().getClase().getDescripcion());
			arp.setPrima(clas.getFraccion().getPrimaSRT().toString());

			arp.setFecEfecto(clas.getFecEfecto());
		}else{
			LOG.debug("********** No se encontro clasificacion");
		}

		//*******************************************************************************************************************************************************
		LOG.debug("**********Voy a setear AFIL03");

		//*******************************************************************
		//Principales productos elaborados
		//*******************************************************************

		List<Producto> productos = sujetoObligado.getProductos();
		if(productos != null){
			Producto producto = null;
			int cont = 1;
			for (Iterator<Producto> iterator = productos.iterator(); iterator.hasNext();) {
				producto = iterator.next();
				if(cont == 1)
					arp.setProductosOServicios1(producto.getDescripcion());
				else if(cont == 2)
					arp.setProductosOServicios2(producto.getDescripcion());
				else if(cont == 3)
					arp.setProductosOServicios3(producto.getDescripcion());
				else if(cont == 4)
					arp.setProductosOServicios4(producto.getDescripcion());
				else if(cont == 5)
					arp.setProductosOServicios5(producto.getDescripcion());
				else if(cont == 6)
					arp.setProductosOServicios6(producto.getDescripcion());
				else if(cont == 7)
					arp.setProductosOServicios7(producto.getDescripcion());
				else if(cont == 8)
					arp.setProductosOServicios8(producto.getDescripcion());
				else if(cont == 9)
					arp.setProductosOServicios9(producto.getDescripcion());
				else if(cont == 10)
					arp.setProductosOServicios10(producto.getDescripcion());
				else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontraron productos elaborados");
		}

		//*******************************************************************
		//Principales materias primas
		//*******************************************************************

		List<MateriaPrima> materiasPrimas = sujetoObligado.getMateriaPrimaMateriales();
		if(materiasPrimas != null){
			MateriaPrima materiaPrima = null;
			int cont = 1;
			for (Iterator<MateriaPrima> iterator = materiasPrimas.iterator(); iterator.hasNext();) {
				materiaPrima = iterator.next();
				if(cont == 1)
					arp.setMateriasPrimas1(materiaPrima.getDescripcion());
				else if(cont == 2)
					arp.setMateriasPrimas2(materiaPrima.getDescripcion());
				else if(cont == 3)
					arp.setMateriasPrimas3(materiaPrima.getDescripcion());
				else if(cont == 4)
					arp.setMateriasPrimas4(materiaPrima.getDescripcion());
				else if(cont == 5)
					arp.setMateriasPrimas5(materiaPrima.getDescripcion());
				else if(cont == 6)
					arp.setMateriasPrimas6(materiaPrima.getDescripcion());
				else if(cont == 7)
					arp.setMateriasPrimas7(materiaPrima.getDescripcion());
				else if(cont == 8)
					arp.setMateriasPrimas8(materiaPrima.getDescripcion());
				else if(cont == 9)
					arp.setMateriasPrimas9(materiaPrima.getDescripcion());
				else if(cont == 10)
					arp.setMateriasPrimas10(materiaPrima.getDescripcion());
				else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontraron materias primas");
		}

		//*******************************************************************
		//Maquinaria y equipo utilizado
		//*******************************************************************

		List<MaquinariaEquipo> maquinariaEquipos = sujetoObligado.getEquipos();
		if(maquinariaEquipos != null){
			MaquinariaEquipo maquinariaEquipo = null;
			int cont = 1;
			for (Iterator<MaquinariaEquipo> iterator = maquinariaEquipos.iterator(); iterator.hasNext();) {
				maquinariaEquipo = iterator.next();
				if(cont == 1){
					arp.setMaqEqpNU1(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom1(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso1(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo1(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot1(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 2){
					arp.setMaqEqpNU2(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom2(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso2(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo2(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot2(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 3){
					arp.setMaqEqpNU3(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom3(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso3(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo3(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot3(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 4){
					arp.setMaqEqpNU4(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom4(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso4(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo4(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot4(maquinariaEquipo.getDesCapacidadPotencia());
				}else if(cont == 5){
					arp.setMaqEqpNU5(maquinariaEquipo.getNumUnidades().toString());
					arp.setMaqEqpNom5(maquinariaEquipo.getDesNombre());
					arp.setMaqEqpUso5(maquinariaEquipo.getDesUso());
					arp.setMaqEqpTpo5(maquinariaEquipo.getTipo().getDescripcion());
					arp.setMaqEqpCapPot5(maquinariaEquipo.getDesCapacidadPotencia());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro maquinaria y equipo utilizado");
		}

		//*******************************************************************
		//Equipo de transporte utilizado
		//*******************************************************************

		List<EquipoTransporte> equiposTransporte = sujetoObligado.getEquiposTransporte();
		if(equiposTransporte != null){
			EquipoTransporte equipoTransporte = null;
			int cont = 1;
			for (Iterator<EquipoTransporte> iterator = equiposTransporte.iterator(); iterator.hasNext();) {
				equipoTransporte = iterator.next();
				if(cont == 1){
					arp.setEqpTrnNU1(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom1(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso1(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo1(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot1(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 2){
					arp.setEqpTrnNU2(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom2(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso2(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo2(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot2(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 3){
					arp.setEqpTrnNU3(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom3(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso3(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo3(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot3(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 4){
					arp.setEqpTrnNU4(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom4(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso4(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo4(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot4(equipoTransporte.getDesCapacidadPotencia());
				}else if(cont == 5){
					arp.setEqpTrnNU5(equipoTransporte.getNumUnidades().toString());
					arp.setEqpTrnNom5(equipoTransporte.getDesNombre());
					arp.setEqpTrnUso5(equipoTransporte.getDesUso());
					arp.setEqpTrnTpo5(equipoTransporte.getTipoCombustible().getDesTipoCombustible());
					arp.setEqpTrnCapPot5(equipoTransporte.getDesCapacidadPotencia());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro equipo de transporte utilizado");
		}

		//*******************************************************************
		//Procesos de trabajo de la actividad del patron
		//*******************************************************************
		if(sujetoObligado.getProceso().getDesInicial() != null)
			arp.setProcesoInicial1(sujetoObligado.getProceso().getDesInicial().trim());

		//*******************************************************************************************************************************************************
		LOG.debug("**********Voy a setear AFIL04");

		//*******************************************************************
		//Procesos de trabajo de la actividad del patron
		//*******************************************************************
		if(sujetoObligado.getProceso().getDesIntermedio() != null)
			arp.setProcesoIntermedio1(sujetoObligado.getProceso().getDesIntermedio().trim());
		if(sujetoObligado.getProceso().getDesFinal() != null)
			arp.setProcesoFinal1(sujetoObligado.getProceso().getDesFinal().trim());

		//*******************************************************************
		//Personal
		//*******************************************************************

		List<Personal> personalList = sujetoObligado.getPersonal();
		if(personalList != null){
			Personal personal = null;
			int cont = 1;
			for (Iterator<Personal> iterator = personalList.iterator(); iterator.hasNext();) {
				personal = iterator.next();
				if(cont == 1){
					arp.setPerNoTrab1(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup1(personal.getOficioOcupacion());
				}else if(cont == 2){
					arp.setPerNoTrab2(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup2(personal.getOficioOcupacion());
				}else if(cont == 3){
					arp.setPerNoTrab3(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup3(personal.getOficioOcupacion());
				}else if(cont == 4){
					arp.setPerNoTrab4(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup4(personal.getOficioOcupacion());
				}else if(cont == 5){
					arp.setPerNoTrab5(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup5(personal.getOficioOcupacion());
				}else if(cont == 6){
					arp.setPerNoTrab6(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup6(personal.getOficioOcupacion());
				}else if(cont == 7){
					arp.setPerNoTrab7(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup7(personal.getOficioOcupacion());
				}else if(cont == 8){
					arp.setPerNoTrab8(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup8(personal.getOficioOcupacion());
				}else if(cont == 9){
					arp.setPerNoTrab9(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup9(personal.getOficioOcupacion());
				}else if(cont == 10){
					arp.setPerNoTrab10(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup10(personal.getOficioOcupacion());
				}else if(cont == 11){
					arp.setPerNoTrab11(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup11(personal.getOficioOcupacion());
				}else if(cont == 12){
					arp.setPerNoTrab12(personal.getNumTrabajadores().toString());
					arp.setPerOficOcup12(personal.getOficioOcupacion());
				}else
					break;

				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontro personal");
		}

		//*******************************************************************
		//Actividades complementarias a la principal
		//*******************************************************************
		if(clas != null){
			if(clas.getIndTransportePropio() != null && clas.getIndTransportePropio().equals(1))
				arp.setConTransportePropio("X");
			if(clas.getIndTransporteAjeno() != null && clas.getIndTransporteAjeno().equals(1))
				arp.setSinTransportePropio("X");
			if(clas.getIndDistribuyeEntrega() != null && clas.getIndDistribuyeEntrega().equals(1))
				arp.setNoDistribuye("X");
			if(clas.getIndServiciosATerceros() != null && clas.getIndServiciosATerceros().equals(1))
				arp.setServiciosInstalacion("X");
		}else{
			LOG.debug("********** No se encontro clasificacion");
		}

		//*******************************************************************
		//Firma del patron y acuse de recibido
		//*******************************************************************

		arp.setNombreCompletoRl(nombreCompletoRL);

		if(sujetoObligado.getNumeroRegistroPatronal() != null && sujetoObligado.getNumeroRegistroPatronal().trim().length() > 0)
			arp.setNrp(sujetoObligado.getNumeroRegistroPatronal());
		arp.setDelegacion(sujetoObligado.getSubdelegacion().getDelegacion().getDescripcion());
		arp.setSubDelegacion(sujetoObligado.getSubdelegacion().getDescripcion());

		arp.setRfc(fisica.getRfc());
		arp.setCurp(fisica.getCurp());
//		if(!tieneRL){
//			arp.setRfcRL(fisica.getRfc());
//			arp.setCurpRL(fisica.getCurp());
//		}
		//Imagenes del reporte
		arp.setLOGO_IMSS_HEADER_PARAM("logo_imss.jpg");
		arp.setLOGO_INFO_HEADER_PARAM("logo_info.jpg");

		LOG.debug("**********termine de llenar VO");

		return arp;
	}

	public ReportesARP getDatosPersonaTIP(SujetoObligado sujetoObligado,
			RepresentanteLegalServiceBusinessLocal representanteLegalService,
			PersonasAutorizadasServiceRemote personasAutorizadasService,
			DomicilioServiceBusinessRemote domicilioService) {

		LOG.debug("**********Obteniendo datos de la persona para generar la tip");

		ReportesARP arp = new ReportesARP();
		Fisica fisica = sujetoObligado.getFisica();
		Moral moral = sujetoObligado.getMoral();

		if(sujetoObligado.getDatosICA()!=null && sujetoObligado.getDatosICA().getPersonaFisicaEE()!=null)
			fisica = sujetoObligado.getDatosICA().getPersonaFisicaEE();
		if(sujetoObligado.getDatosICA()!=null && sujetoObligado.getDatosICA().getPersonaMoralEE()!=null)
			moral = sujetoObligado.getDatosICA().getPersonaMoralEE();

		Persona persona = new Persona();
		String nombre = null;
		String curp = null;
		String rfc = null;
		String lugarExpedicion = null;
		String nombreCompletoRL = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");

		//setea los valores de campos con guiones para los datos vacios
		arp = resetReportesARP(arp, 3); // el 3 es para identificar la TIP

		//*******************************************************************
		//Clasificacion actividad economica
		//*******************************************************************
		Clasificacion clas = sujetoObligado.getClasificacion();

		if(clas != null){
			arp.setDivisionClave(clas.getFraccion().getGrupo().getDivision().getNumDivision());
			arp.setDivisionDes(clas.getFraccion().getGrupo().getDivision().getDescripcion());
			arp.setGrupoClave(arp.getDivisionClave() + clas.getFraccion().getGrupo().getNumGrupo());
			arp.setGrupoDes(clas.getFraccion().getGrupo().getDescripcion());
			String f = clas.getFraccion().getNumFraccion();
//			if(f.length() == 1)
//				f = "0" + f;
			arp.setFraccionClave(arp.getGrupoClave() + f);
			arp.setFraccionDes(clas.getFraccion().getDescripcion());
			if(arp.getFraccionDes() == null || arp.getFraccionDes().trim().length() == 0)
				arp.setFraccionDes(clas.getFraccion().getDescripcionDetallada());
			arp.setClase(clas.getFraccion().getClase().getDescripcion());
			arp.setPrima(clas.getFraccion().getPrimaSRT().toString());
		}else{
			LOG.debug("********** No se encontro clasificacion");
		}


		//*******************************************************************
		//Datos del centro de trabajo
		//*******************************************************************
		if(sujetoObligado.getClasificacion().getIndRegPatClase()!=null &&
				sujetoObligado.getClasificacion().getIndRegPatClase().intValue()==1){
			//Si es RPC, mandar Domicilio Fiscal como Centro de Trabajo
			getDomicilioFiscal(domicilioService, arp, (sujetoObligado.getFisica()!=null
				? sujetoObligado.getFisica() : sujetoObligado.getMoral()));
			arp.setCalleCT(arp.getCalle());
			arp.setNumExtCT(arp.getNumExt());
			arp.setNumIntCT(arp.getNumInt());
			arp.setEntrecalle1CT(arp.getEntrecalle1());
			arp.setEntrecalle2CT(arp.getEntrecalle2());
			arp.setColoniaCT(arp.getColonia());
			arp.setLocalidadCT(arp.getLocalidad());
			arp.setMunicipioCT(arp.getMunicipio());
			arp.setEntidadaFederativaCT(arp.getEntidadaFederativa());
			arp.setCpCT(arp.getCp());
			arp.setCpCT1(arp.getCp1());
			arp.setExtencion1CT(arp.getExtencion1());
			arp.setExtencion2CT(arp.getExtencion2());
		}else{
			//No es RPC, obtener Centro de Trabajo
			CentroTrabajo centroTrabajo = sujetoObligado.getCntroTrabajo();
			if(centroTrabajo != null){
//				arp.setCalleCT(centroTrabajo.getCalle()!=null?centroTrabajo.getCalle(): (centroTrabajo.getVialidadPrimaria()!=null ? centroTrabajo.getVialidadPrimaria().getNombre()
//					:""));
				arp.setCalleCT(centroTrabajo.getVialidadPrimaria()!=null ? centroTrabajo.getVialidadPrimaria().getNombre()
						: (centroTrabajo.getCalle()!=null?centroTrabajo.getCalle():""));
				String numeroExtCompleto = "";
				if(centroTrabajo.getNumExterior1() != null && centroTrabajo.getNumExterior1() != 0)
					numeroExtCompleto = centroTrabajo.getNumExterior1().toString()+" ";
				if(centroTrabajo.getNumExteriorAlf()!=null)
					numeroExtCompleto += centroTrabajo.getNumExteriorAlf();
				arp.setNumExtCT(numeroExtCompleto.toUpperCase());

				String numeroInteriorCompleto = "";
				if(centroTrabajo.getNumInterior() != null && centroTrabajo.getNumInterior() != 0)
					numeroInteriorCompleto = centroTrabajo.getNumInterior().toString()+" ";
				if(centroTrabajo.getNumInteriorAlf()!=null)
					numeroInteriorCompleto += centroTrabajo.getNumInteriorAlf();
				arp.setNumIntCT(numeroInteriorCompleto.toUpperCase());

				if(centroTrabajo.getVialidadReferenciaPrimaria()!=null)
					arp.setEntrecalle1CT(centroTrabajo.getVialidadReferenciaPrimaria().getNombre());
				if(centroTrabajo.getVialidadReferenciaSecundaria()!=null)
					arp.setEntrecalle2CT(centroTrabajo.getVialidadReferenciaSecundaria().getNombre());
				if (centroTrabajo.getAsentamiento() != null) {
					arp.setColoniaCT(centroTrabajo.getAsentamiento().getNombre());
					arp.setLocalidadCT(centroTrabajo.getAsentamiento().getLocalidad().getNombre());
					arp.setMunicipioCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
					arp.setEntidadaFederativaCT(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				}

				if(centroTrabajo.getCodigoPostal() != null && centroTrabajo.getCodigoPostal().getCodigoPostal() != null
						&& centroTrabajo.getCodigoPostal().getCodigoPostal().trim().length() > 0){
					String cp = centroTrabajo.getCodigoPostal().getCodigoPostal();
					if(cp.length() == 4)
						cp = "0" + cp;
					else if(cp.length() == 3)
						cp = "00" + cp;
					arp.setCpCT(cp);
				}
			}else{
				LOG.debug("********** No se encontro centro de trabajo");
			}
		}

		//*******************************************************************
		//Representante legal
		//*******************************************************************
		String rfcRL=null;
		String curpRL=null;
		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.MORAL.getCodigo())){
			// Se ejecuta servicio para obtener representante legal
			List<RepresentanteLegal> representanteLegalList = sujetoObligado.getRepresentantesLegales();
			//TODO
			if(representanteLegalList != null && representanteLegalList.size() > 0){
				RepresentanteLegal representanteLegal = representanteLegalList.get(0);
				StringBuffer rlQueEjecutaAlta=new StringBuffer();
				Fisica pRl=representanteLegal.getPersonaFisica();
				if(!StringUtils.isBlank(pRl.getNombre()))
					rlQueEjecutaAlta.append(pRl.getNombre());
				if(!StringUtils.isBlank(pRl.getPrimerApellido()))
					rlQueEjecutaAlta.append(" "+pRl.getPrimerApellido());
				if(!StringUtils.isBlank(pRl.getSegundoApellido()))
					rlQueEjecutaAlta.append(" "+pRl.getSegundoApellido());

				rfcRL=pRl.getRfc();
				curpRL=pRl.getCurp();

				nombreCompletoRL = rlQueEjecutaAlta.toString();
			}else{
				LOG.debug("********** No se encontro representante legal");
			}
		}

		//llena los datos segun el tipo de persona
		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())){
			persona.setRfc(fisica.getRfc());
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		}else{
			persona.setRfc(moral.getRfc());
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		}

		List<PersonaAutorizada> personasAutorizadas = sujetoObligado.getPersonasAutorizadas();

		if (personasAutorizadas == null || personasAutorizadas.isEmpty()) {
			personasAutorizadas = personasAutorizadasService.getPersonasAutorizadasByPersona(persona);
		}

		if(personasAutorizadas != null && !personasAutorizadas.isEmpty()){
			LOG.debug("******** Encontre " + personasAutorizadas.size()
					+ " personas Autorizadas, se imprimiran solo las que correspondan al RP " + sujetoObligado.getNumeroRegistroPatronal());
			int cont = 1;
			for (PersonaAutorizada personaAutorizada:personasAutorizadas) {
				if(personaAutorizada != null){
					Fisica pf = personaAutorizada.getFisica();
					if(pf==null || (
							pf!=null && StringUtils.isBlank(pf.getRfc())
							)){
						cont +=1;
						continue;
					}
					//Coreccion por nombre inclompleto en documento INC488636
					if(cont == 1){
						StringBuffer nombrePA1=new StringBuffer();
						if(!StringUtils.isBlank(pf.getNombre())) {
							nombrePA1.append(pf.getNombre());
						}
						if(!StringUtils.isBlank(pf.getPrimerApellido())) {
							nombrePA1.append(" "+pf.getPrimerApellido());
						}
						if(!StringUtils.isBlank(pf.getSegundoApellido())) {
							nombrePA1.append(" "+pf.getSegundoApellido());
						}
						arp.setNombrePA1(nombrePA1.toString());
//						arp.setNombrePA1(pf.getNombre() + " " + pf.getPrimerApellido()!=null ? pf.getPrimerApellido() : "" + " " + pf.getSegundoApellido()!=null ? pf.getSegundoApellido() : "");
					}else if(cont == 2){
						StringBuffer nombrePA2=new StringBuffer();
						if(!StringUtils.isBlank(pf.getNombre())) {
							nombrePA2.append(pf.getNombre());
						}
						if(!StringUtils.isBlank(pf.getPrimerApellido())) {
							nombrePA2.append(" "+pf.getPrimerApellido());
						}
						if(!StringUtils.isBlank(pf.getSegundoApellido())) {
							nombrePA2.append(" "+pf.getSegundoApellido());
						}
						arp.setNombrePA2(nombrePA2.toString());
//						arp.setNombrePA2(pf.getNombre() + " " + pf.getPrimerApellido()!=null ? pf.getPrimerApellido() : "" + " " + pf.getSegundoApellido()!=null ? pf.getSegundoApellido() : "");
					}else if(cont == 3){
						StringBuffer nombrePA3=new StringBuffer();
						if(!StringUtils.isBlank(pf.getNombre())) {
							nombrePA3.append(pf.getNombre());
						}
						if(!StringUtils.isBlank(pf.getPrimerApellido())) {
							nombrePA3.append(" "+pf.getPrimerApellido());
						}
						if(!StringUtils.isBlank(pf.getSegundoApellido())) {
							nombrePA3.append(" "+pf.getSegundoApellido());
						}
						arp.setNombrePA3(nombrePA3.toString());
//						arp.setNombrePA3(pf.getNombre() + " " + pf.getPrimerApellido()!=null ? pf.getPrimerApellido() : "" + " " + pf.getSegundoApellido()!=null ? pf.getSegundoApellido() : "");
					}else{
						break;
					}
				}
				cont += 1;
			}
		}else{
			LOG.debug("********** No se encontraron personas autorizadas");
		}

		//llena los datos segun el tipo de persona
		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())){
			//Coreccion por nombre inclompleto en documento INC488636
			StringBuffer nombreB=new StringBuffer();
			if(!StringUtils.isBlank(fisica.getNombre())) {
				nombreB.append(fisica.getNombre());
			}
			if(!StringUtils.isBlank(fisica.getPrimerApellido())) {
				nombreB.append(" "+fisica.getPrimerApellido());
			}
			if(!StringUtils.isBlank(fisica.getSegundoApellido())) {
				nombreB.append(" "+fisica.getSegundoApellido());
			}
			nombre = nombreB.toString();
			//nombre = fisica.getNombre() + " " + fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "" + " " + fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "";
			curp = curpRL!=null ? curpRL : fisica.getCurp();
			rfc = rfcRL!=null ? rfcRL : fisica.getRfc();
		}else{
			nombre = moral.getRazonSocial();
			if(moral.getTipoSociedad()!=null
					&& StringUtils.isNotBlank(moral.getTipoSociedad().getDescripcionAbreviada()))
				nombre+=" "+moral.getTipoSociedad().getDescripcionAbreviada();

			rfc = rfcRL!=null ? rfcRL : "";
			curp= curpRL!=null ? curpRL : "";
		}

		String patDomicilio = arp.getCalleCT() + " " + arp.getNumExtCT() + " " + arp.getNumIntCT() + " LOCALIDAD " + arp.getLocalidadCT() + " C.P. " + arp.getCpCT();
		patDomicilio += " MUNICIPIO " +  arp.getMunicipioCT() + ", " + arp.getEntidadaFederativaCT();
		patDomicilio = patDomicilio.length()>=150? patDomicilio.substring(0,150) : patDomicilio; //limitamos el domicilio a 150 caracteres

		Date fechaAlta = new Date();
		lugarExpedicion = arp.getEntidadaFederativaCT() + " A " + sdf.format(fechaAlta) + " DE ";
		lugarExpedicion += getNombreMesDate(fechaAlta).toUpperCase()+ " DEL AÑO " + sdf3.format(fechaAlta);

		///////////////////////////////////////////////////////////////////////////////////////////////
		///Una vez que obtuvimos los valores necesarios seteamos los campos restantes para el jasper de la TIP
		///////////////////////////////////////////////////////////////////////////////////////////////

		if(sujetoObligado.getNumeroRegistroPatronal() != null && sujetoObligado.getNumeroRegistroPatronal().trim().length() > 0)
			arp.setNrp(sujetoObligado.getNumeroRegistroPatronal());
		arp.setNombre(nombre);
		arp.setDomicilio(patDomicilio);
		arp.setLugarExpedicion(lugarExpedicion);
		arp.setDelegacion(sujetoObligado.getSubdelegacion() != null ? sujetoObligado.getSubdelegacion().getDelegacion().getDescripcion() : "");
		arp.setSubDelegacion(sujetoObligado.getSubdelegacion() != null ? sujetoObligado.getSubdelegacion().getDescripcion() : "");
		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo()))
			arp.setNombreCompletoRl(nombre);
		else{
			if(nombreCompletoRL != null){
				arp.setNombreCompletoRl(nombreCompletoRL);
			}
		}
		arp.setCurp(curp);
		arp.setRfc(rfc);

		//Imagenes del reporte
		arp.setLOGO_IMSS_HEADER_PARAM("logo_imss.jpg");
		LOG.debug("**********termine de llenar VO");

		if(arp.getNombreCompletoRl() == null || arp.getNombreCompletoRl().trim().equals("null null null")){
			arp.setNombreCompletoRl(ponGuiones(20));
		}
		if(arp.getNombre() == null || arp.getNombre().trim().equals("null null null")){
			arp.setNombre(ponGuiones(20));
		}
		if(arp.getCurp() == null){
			arp.setCurp(ponGuiones(18));
		}
		if(arp.getRfc() == null){
			arp.setRfc(ponGuiones(18));
		}

		return arp;
	}

    private String getNombreMesDate(Date date){
        Locale loc_mx = new Locale("es", "MX");
        SimpleDateFormat sf = new SimpleDateFormat("MMMMMMMMMM",loc_mx);
        return sf.format(date).toString();
    }

	private Map<String, String> obtenerMediosContacto(String tipoMedio, Persona persona, Long idPatronSujetoObligado, MediosContactoServiceBusinessRemote mediosContactoService){
		List<MedioContacto> mediosContactoList = Collections.emptyList();
		Map<String, String> mediosContacto = new HashMap<String, String>();

		if(tipoMedio.equals("Personal")){
			try {
				if(persona.getIdPersona()!=null)
					mediosContactoList = mediosContactoService.consultarMedioDeContactoPersona(persona);
			} catch (PersonaSinMedioDeContactoException e) {
				e.getMessage();
			}
		}else if(tipoMedio.equals("CentroTrabajo")){
			CentroTrabajo centro = new CentroTrabajo();
			centro.setCveIdPatronSujetoObligado(idPatronSujetoObligado.longValue());
			mediosContactoList = mediosContactoService.consultarMedioContactoDeCentroTrabajo(centro);
		}else if(tipoMedio.equals("Fiscal")){
			try {
				mediosContactoList = mediosContactoService.consultarMediosFiscalesPersona(persona);
			} catch (PersonaSinMedioDeContactoException e) {
				e.getMessage();
			}
		}

		if(mediosContactoList != null && !mediosContactoList.isEmpty()){
			LOG.debug("******** Encontre " + mediosContactoList.size() + " medios de contacto para " + tipoMedio);
			TelefonoFijo telefonoFijo1 = new TelefonoFijo();
			TelefonoFijo telefonoFijo2 = new TelefonoFijo();
			TelefonoMovil telefonoMovil = new TelefonoMovil();
			CorreoElectronico correoElectronico = new CorreoElectronico();
			boolean telFijo1 = false;
			boolean telFijo2 = false;
			for(Object mCon : mediosContactoList){
				MedioContacto auxMedio = (MedioContacto)mCon;

				if(TipoMedioContacto.TIPO_TELEFONO_FIJO.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					if(!telFijo1){
						telefonoFijo1 = (TelefonoFijo)mCon;
						telFijo1 = true;
					}else{
						telFijo2 = true;
						telefonoFijo2 = (TelefonoFijo)mCon;
					}
				}
				if(TipoMedioContacto.TIPO_TELEFONO_MOVIL.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					telefonoMovil = (TelefonoMovil)mCon;
				}
				if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					correoElectronico = (CorreoElectronico)mCon;
				}
			}

			String tel = "";
			if(telFijo1){
				if(telefonoFijo1.getClaveLada() != null && telefonoFijo1.getClaveLada().trim().length() > 0)
					tel = telefonoFijo1.getClaveLada();
				if(telefonoFijo1.getNumero() != null)
					tel += telefonoFijo1.getNumero();
					LOG.debug("Agregando Telefono Fijo1: " + tel);
					mediosContacto.put("telefonoFijo1", tel);

				if(telefonoFijo1.getExtension() != null && telefonoFijo1.getExtension().trim().length() > 0){
					LOG.debug("Agregando Extension1: " + telefonoFijo1.getExtension());
					mediosContacto.put("extension1", telefonoFijo1.getExtension());
				}
			}else{
				LOG.debug("No habia tel fijo 1");
			}

			tel = "";

			if(telFijo2){
				if(telefonoFijo2.getClaveLada() != null && telefonoFijo2.getClaveLada().trim().length() > 0)
					tel = telefonoFijo2.getClaveLada();
				if(telefonoFijo2.getNumero() != null)
					tel += telefonoFijo2.getNumero();

				LOG.debug("Agregando Telefono Fijo2: " + tel);
				mediosContacto.put("telefonoFijo2", tel);

				if(telefonoFijo2.getExtension() != null && telefonoFijo2.getExtension().trim().length() > 0){
					LOG.debug("Agregando Extension2: " + telefonoFijo2.getExtension());
					mediosContacto.put("extension2", telefonoFijo2.getExtension());
				}
			}else{
				LOG.debug("No habia tel fijo 2");
			}
			if(telefonoMovil.getNumero() != null && telefonoMovil.getNumero().trim().length() > 0){
				LOG.debug("Agregando telefonoMovil: " + telefonoMovil.getNumero());
				mediosContacto.put("telefonoMovil", telefonoMovil.getNumero());
			}

			if(correoElectronico.getCorreo() != null && correoElectronico.getCorreo().trim().length() > 0){
				LOG.debug("Agregando correoElectronico: " + correoElectronico.getCorreo());
				mediosContacto.put("correoElectronico", correoElectronico.getCorreo());
			}
		}

		return mediosContacto;
	}

	private ReportesARP resetReportesARP(ReportesARP arp, int tipoPersona) {

		arp.setApMaterno(ponGuiones(20));
		arp.setApPaterno(ponGuiones(20));
		arp.setNombre(ponGuiones(20));
		arp.setNomComercial(ponGuiones(20));
		arp.setCurp(ponGuiones(18));
		arp.setRfc(ponGuiones(15));
		arp.setCalle(ponGuiones(20));
		arp.setNumExt(ponGuiones(10));
		arp.setNumInt(ponGuiones(10));
		arp.setEntrecalle1(ponGuiones(50));
		arp.setEntrecalle2(ponGuiones(50));
		arp.setColonia(ponGuiones(50));
		arp.setLocalidad(ponGuiones(50));
		arp.setMunicipio(ponGuiones(30));
		arp.setEntidadaFederativa(ponGuiones(30));
		arp.setCorreo(ponGuiones(20));

//		arp.setFecPresentacion(new Date());
//		arp.setFecEfecto(new Date());
		arp.setCp(ponGuiones(5));
		arp.setTelefono1(ponGuiones(10));
		arp.setTelefono2(ponGuiones(10));
		arp.setExtencion1(ponGuiones(10));
		arp.setExtencion2(ponGuiones(10));

		arp.setApMaternoRL(ponGuiones(20));
		arp.setApPaternoRL(ponGuiones(20));
		arp.setNombreRL(ponGuiones(20));
		arp.setRfcRL(ponGuiones(15));
		arp.setCurpRL(ponGuiones(18));
		arp.setTelefono2RL(ponGuiones(10));
		arp.setTelefono1RL(ponGuiones(10));
		arp.setExtencionRL(ponGuiones(10));
		arp.setEntrecalle1CT(ponGuiones(50));
		arp.setEntrecalle2CT(ponGuiones(50));
		arp.setCalleCT(ponGuiones(20));
		arp.setNumExtCT(ponGuiones(10));
		arp.setNumIntCT(ponGuiones(10));
		arp.setColoniaCT(ponGuiones(50));
		arp.setMunicipioCT(ponGuiones(50));
		arp.setEntidadaFederativaCT(ponGuiones(50));
		arp.setCpCT(ponGuiones(5));

		arp.setTelefono1CT(ponGuiones(10));
		arp.setTelefono2CT(ponGuiones(10));
		arp.setExtencion1CT(ponGuiones(10));
		arp.setExtencion2CT(ponGuiones(10));
		arp.setLocalidadCT(ponGuiones(20));

		arp.setFolioNRP(ponGuiones(10));
		arp.setActosAdministracionRL("");
		arp.setTiempoAlta(ponGuiones(10));

		arp.setCorreoCT(ponGuiones(20));
		arp.setCorreoRL(ponGuiones(20));

		if(tipoPersona == 3){
			arp.setNombrePA1(ponGuiones(90));
			arp.setNombrePA2(ponGuiones(90));
			arp.setNombrePA3(ponGuiones(90));
		}else{
			arp.setNombrePA1(ponGuiones(28));
			arp.setNombrePA2(ponGuiones(28));
			arp.setNombrePA3(ponGuiones(28));

		}

		arp.setApPaternoPA1(ponGuiones(28));
		arp.setApMaternoPA1(ponGuiones(28));
		arp.setRfcPA1(ponGuiones(15));
		arp.setCurpPA1(ponGuiones(18));
		arp.setTelefono1PA1(ponGuiones(10));
		arp.setExtencionPA1(ponGuiones(10));
		arp.setTelefono2PA1(ponGuiones(10));
		arp.setCorreoPA1(ponGuiones(20));

		arp.setApPaternoPA2(ponGuiones(28));
		arp.setApMaternoPA2(ponGuiones(28));
		arp.setRfcPA2(ponGuiones(15));
		arp.setCurpPA2(ponGuiones(18));
		arp.setTelefono1PA2(ponGuiones(10));
		arp.setExtencionPA2(ponGuiones(10));
		arp.setTelefono2PA2(ponGuiones(10));
		arp.setCorreoPA2(ponGuiones(20));

		arp.setApPaternoPA3(ponGuiones(28));
		arp.setApMaternoPA3(ponGuiones(28));
		arp.setRfcPA3(ponGuiones(15));
		arp.setCurpPA3(ponGuiones(18));
		arp.setTelefono1PA3(ponGuiones(10));
		arp.setExtencionPA3(ponGuiones(10));
		arp.setTelefono2PA3(ponGuiones(10));
		arp.setCorreoPA3(ponGuiones(20));

		arp.setGiro(ponGuiones(46));
//		arp.setPrestaServPersNo("X");
//		arp.setPrestaServPersSi("X");
//		arp.setNoCentTrab(1);
//		arp.setSolRegPatronalClase("X");
		arp.setDivisionClave(ponGuiones(2));
		arp.setDivisionDes(ponGuiones(29));
		arp.setGrupoClave(ponGuiones(2));
		arp.setGrupoDes(ponGuiones(29));
		arp.setFraccionClave(ponGuiones(2));
		arp.setFraccionDes(ponGuiones(29));
		arp.setClase(ponGuiones(2));
		arp.setPrima(ponGuiones(6));

		arp.setProductosOServicios1(ponGuiones(28));
		arp.setProductosOServicios2(ponGuiones(28));
		arp.setProductosOServicios3(ponGuiones(28));
		arp.setProductosOServicios4(ponGuiones(28));
		arp.setProductosOServicios5(ponGuiones(28));
		arp.setProductosOServicios6(ponGuiones(28));
		arp.setProductosOServicios7(ponGuiones(28));
		arp.setProductosOServicios8(ponGuiones(28));
		arp.setProductosOServicios9(ponGuiones(28));
		arp.setProductosOServicios10(ponGuiones(28));

		arp.setMateriasPrimas1(ponGuiones(28));
		arp.setMateriasPrimas2(ponGuiones(28));
		arp.setMateriasPrimas3(ponGuiones(28));
		arp.setMateriasPrimas4(ponGuiones(28));
		arp.setMateriasPrimas5(ponGuiones(28));
		arp.setMateriasPrimas6(ponGuiones(28));
		arp.setMateriasPrimas7(ponGuiones(28));
		arp.setMateriasPrimas8(ponGuiones(28));
		arp.setMateriasPrimas9(ponGuiones(28));
		arp.setMateriasPrimas10(ponGuiones(28));

		arp.setMaqEqpNU1(ponGuiones(28));
		arp.setMaqEqpNom1(ponGuiones(28));
		arp.setMaqEqpUso1(ponGuiones(28));
		arp.setMaqEqpTpo1(ponGuiones(28));
		arp.setMaqEqpCapPot1(ponGuiones(28));
		arp.setMaqEqpNU2(ponGuiones(28));
		arp.setMaqEqpNom2(ponGuiones(28));
		arp.setMaqEqpUso2(ponGuiones(28));
		arp.setMaqEqpTpo2(ponGuiones(28));
		arp.setMaqEqpCapPot2(ponGuiones(28));
		arp.setMaqEqpNU3(ponGuiones(28));
		arp.setMaqEqpNom3(ponGuiones(28));
		arp.setMaqEqpUso3(ponGuiones(28));
		arp.setMaqEqpTpo3(ponGuiones(28));
		arp.setMaqEqpCapPot3(ponGuiones(28));
		arp.setMaqEqpNU4(ponGuiones(28));
		arp.setMaqEqpNom4(ponGuiones(28));
		arp.setMaqEqpUso4(ponGuiones(28));
		arp.setMaqEqpTpo4(ponGuiones(28));
		arp.setMaqEqpCapPot4(ponGuiones(28));
		arp.setMaqEqpNU5(ponGuiones(28));
		arp.setMaqEqpNom5(ponGuiones(28));
		arp.setMaqEqpUso5(ponGuiones(28));
		arp.setMaqEqpTpo5(ponGuiones(28));
		arp.setMaqEqpCapPot5(ponGuiones(28));

		arp.setEqpTrnNU1("NO CUENTA CON EQUIPO DE TRANSPORTE");
		arp.setEqpTrnNom1("NO CUENTA CON EQUIPO DE TRANSPORTE");
		arp.setEqpTrnUso1("NO CUENTA CON EQUIPO DE TRANSPORTE");
		arp.setEqpTrnTpo1("NO CUENTA CON EQUIPO DE TRANSPORTE");
		arp.setEqpTrnCapPot1("NO CUENTA CON EQUIPO DE TRANSPORTE");
		arp.setEqpTrnNU2(ponGuiones(28));
		arp.setEqpTrnNom2(ponGuiones(28));
		arp.setEqpTrnUso2(ponGuiones(28));
		arp.setEqpTrnTpo2(ponGuiones(28));
		arp.setEqpTrnCapPot2(ponGuiones(28));
		arp.setEqpTrnNU3(ponGuiones(28));
		arp.setEqpTrnNom3(ponGuiones(28));
		arp.setEqpTrnUso3(ponGuiones(28));
		arp.setEqpTrnTpo3(ponGuiones(28));
		arp.setEqpTrnCapPot3(ponGuiones(28));
		arp.setEqpTrnNU4(ponGuiones(28));
		arp.setEqpTrnNom4(ponGuiones(28));
		arp.setEqpTrnUso4(ponGuiones(28));
		arp.setEqpTrnTpo4(ponGuiones(28));
		arp.setEqpTrnCapPot4(ponGuiones(28));
		arp.setEqpTrnNU5(ponGuiones(28));
		arp.setEqpTrnNom5(ponGuiones(28));
		arp.setEqpTrnUso5(ponGuiones(28));
		arp.setEqpTrnTpo5(ponGuiones(28));
		arp.setEqpTrnCapPot5(ponGuiones(28));

		arp.setProcesoInicial1(ponGuiones(92));

		arp.setProcesoIntermedio1(ponGuiones(92));
		arp.setProcesoFinal1(ponGuiones(92));

		arp.setPerNoTrab1(ponGuiones(28));
		arp.setPerOficOcup1(ponGuiones(28));
		arp.setPerNoTrab2(ponGuiones(28));
		arp.setPerOficOcup2(ponGuiones(28));
		arp.setPerNoTrab3(ponGuiones(28));
		arp.setPerOficOcup3(ponGuiones(28));
		arp.setPerNoTrab4(ponGuiones(28));
		arp.setPerOficOcup4(ponGuiones(28));
		arp.setPerNoTrab5(ponGuiones(28));
		arp.setPerOficOcup5(ponGuiones(28));
		arp.setPerNoTrab6(ponGuiones(28));
		arp.setPerOficOcup6(ponGuiones(28));
		arp.setPerNoTrab7(ponGuiones(28));
		arp.setPerOficOcup7(ponGuiones(28));
		arp.setPerNoTrab8(ponGuiones(28));
		arp.setPerOficOcup8(ponGuiones(28));
		arp.setPerNoTrab9(ponGuiones(28));
		arp.setPerOficOcup9(ponGuiones(28));
		arp.setPerNoTrab10(ponGuiones(28));
		arp.setPerOficOcup10(ponGuiones(28));
		arp.setPerNoTrab11(ponGuiones(28));
		arp.setPerOficOcup11(ponGuiones(28));
		arp.setPerNoTrab12(ponGuiones(28));
		arp.setPerOficOcup12(ponGuiones(28));

//		arp.setConTransportePropio("X");
//		arp.setSinTransportePropio("X");
//		arp.setNoDistribuye("X");
//		arp.setServiciosInstalacion("X");

		arp.setNrp(ponGuiones(11));
		arp.setDelegacion(ponGuiones(30));
		arp.setSubDelegacion(ponGuiones(30));
		arp.setRfc(ponGuiones(28));
		arp.setCurp(ponGuiones(18));
		arp.setCadenaOriginal(ponGuiones(50));
		arp.setFirmaDigital(ponGuiones(50));
		arp.setNivelEducativo(ponGuiones(50));

		//Imagenes del reporte
		arp.setLOGO_IMSS_HEADER_PARAM("logo_imss.jpg");
		arp.setLOGO_INFO_HEADER_PARAM("logo_info.jpg");


		//////////////////////////////////////////////////
		 ////////Campos PM
		/////////////////////////////////////////////////

		if(tipoPersona == 2){
			arp.setTipoSociedad(ponGuiones(20));

			arp.setApPaterno1(ponGuiones(30));
			arp.setCalle1(ponGuiones(20));
			arp.setNumExt1(ponGuiones(20));
			arp.setNumInt1(ponGuiones(20));
			arp.setColonia1(ponGuiones(20));
			arp.setLocalidad1(ponGuiones(10));
			arp.setMunDelegacion1(ponGuiones(10));
			arp.setEntFederativa1(ponGuiones(10));
			arp.setCodPost1(ponGuiones(5));
			arp.setTelfijo1(ponGuiones(10));
			arp.setEmail1(ponGuiones(20));

			arp.setApPaterno2(ponGuiones(30));
			arp.setCalle2(ponGuiones(20));
			arp.setNumExt2(ponGuiones(20));
			arp.setNumInt2(ponGuiones(20));
			arp.setColonia2(ponGuiones(20));
			arp.setLocalidad2(ponGuiones(10));
			arp.setMunDelegacion2(ponGuiones(10));
			arp.setEntFederativa2(ponGuiones(10));
			arp.setCodPost2(ponGuiones(5));
			arp.setTelfijo2(ponGuiones(10));
			arp.setEmail2(ponGuiones(20));

			arp.setApPaterno3(ponGuiones(30));
			arp.setCalle3(ponGuiones(20));
			arp.setNumExt3(ponGuiones(20));
			arp.setNumInt3(ponGuiones(20));
			arp.setColonia3(ponGuiones(20));
			arp.setLocalidad3(ponGuiones(10));
			arp.setMunDelegacion3(ponGuiones(10));
			arp.setEntFederativa3(ponGuiones(10));
			arp.setCodPost3(ponGuiones(5));
			arp.setTelfijo3(ponGuiones(10));
			arp.setEmail3(ponGuiones(20));

			arp.setApPaterno4(ponGuiones(30));
			arp.setCalle4(ponGuiones(20));
			arp.setNumExt4(ponGuiones(20));
			arp.setNumInt4(ponGuiones(20));
			arp.setColonia4(ponGuiones(20));
			arp.setLocalidad4(ponGuiones(10));
			arp.setMunDelegacion4(ponGuiones(10));
			arp.setEntFederativa4(ponGuiones(10));
			arp.setCodPost4(ponGuiones(5));
			arp.setTelfijo4(ponGuiones(10));
			arp.setEmail4(ponGuiones(20));

			arp.setNumEscritura(ponGuiones(10));
			arp.setNumNotarira(ponGuiones(10));
			arp.setLugarExpedicion(ponGuiones(10));
//			arp.setFechaExpedicion("fechaExpedicion");
			arp.setFolioMercantil(ponGuiones(10));
//			arp.setNumeroReferencia("numeroReferencia");
//			arp.setFechaDocumentoRegistro("fechaDocumentoRegistro");
		    arp.setAutoridadLaboral(ponGuiones(10));

		    arp.setNumeroReferencia(ponGuiones(10));
		}

		arp.setNombreCompletoRl(ponGuiones(20));
		arp.setDomicilio(ponGuiones(20));
		arp.setVigencia(ponGuiones(10));

		return arp;
	}

	private String ponGuiones(int numGuiones) {
		String cadena = "";
		for (int i = 0; i < numGuiones; i++)
			cadena = cadena + "-";
		return cadena;
	}


	public ReportesARP getDatosHarkcode(ReportesARP arp) {

		//********************************************************************
		LOG.debug("**********Voy a setear AFIL01");

		arp = new ReportesARP();

		arp.setApMaterno("Materno");
		arp.setApPaterno("Salinas");
		arp.setNombre("Juan");
		arp.setCurp("AQUIVALACURP");
		arp.setRfc("AQUIVAELRFC");
		arp.setCalle("Maclaquiloca");
		arp.setNumExt("snE");
		arp.setNumInt("snI");
		arp.setEntrecalle1("Jazminez");
		arp.setEntrecalle2("Calle crucero");
		arp.setColonia("El crucero");
		arp.setLocalidad("Tehuilotepec");
		arp.setMunicipio("Taxco de Alarcón");
		arp.setEntidadaFederativa("Guerrero");
		arp.setCorreo("correo@starmedia.com");

		arp.setApMaternoRL("maternoRL");
		arp.setApPaternoRL("paternnoRL");
		arp.setNombreRL("nombreRL");
		arp.setRfcRL("rfcRL");
		arp.setCurpRL("curpRL");
		arp.setTelefono2RL("telefono2RL");
		arp.setTelefono1RL("telefono1RL");
		arp.setExtencionRL("extencionRL");
		arp.setEntrecalle1CT("entrecalle1CT");
		arp.setEntrecalle2CT("entrecalle2CT");
		arp.setCalleCT("calleCT");
		arp.setNumExtCT("numExtCT");
		arp.setNumIntCT("numIntCT");
		arp.setColoniaCT("coloniaCT");
		arp.setMunicipioCT("municipioCT");
		arp.setEntidadaFederativaCT("entidadaFederativaCT");
		arp.setCpCT("1234");

		arp.setFecPresentacion(new Date());
		arp.setFecEfecto(new Date());
		arp.setCp("123456");
		arp.setTelefono1("telefono1");
		arp.setTelefono2("telefono2");
		arp.setExtencion1("extencion1");
		arp.setExtencion2("extencion2");

		arp.setTelefono1CT("telefono1CT");
		arp.setTelefono2CT("telefono2CT");
		arp.setExtencion1CT("extencion1CT");
		arp.setExtencion2CT("extencion2CT");
		arp.setLocalidadCT("localidadCT");
		arp.setFolioNRP("folioNRP");
		arp.setCorreoCT("correoCT");
		arp.setCorreoRL("correoRL");
		arp.setActosAdministracionRL("actosAdministracionRL");
		arp.setTiempoAlta("tiempoAlta");
		arp.setNomComercial("nomComercial");

		LOG.debug("**********Voy a setear AFIL02");

		arp.setApPaternoPA1("apPaternoPA1");
		arp.setApMaternoPA1("apMaternoPA1");
		arp.setNombrePA1("nombrePA1");
		arp.setRfcPA1("rfcPA1");
		arp.setCurpPA1("curpPA1");
		arp.setTelefono1PA1("telefono1PA1");
		arp.setExtencionPA1("extencionPA1");
		arp.setTelefono2PA1("telefono2PA1");
		arp.setCorreoPA1("correoPA1");

		arp.setApPaternoPA2("apPaternoPA2");
		arp.setApMaternoPA2("apMaternoPA2");
		arp.setNombrePA2("nombrePA2");
		arp.setRfcPA2("rfcPA2");
		arp.setCurpPA2("curpPA2");
		arp.setTelefono1PA2("telefono1PA2");
		arp.setExtencionPA2("extencionPA2");
		arp.setTelefono2PA2("telefono2PA2");
		arp.setCorreoPA2("correoPA2");

		arp.setApPaternoPA3("apPaternoPA3");
		arp.setApMaternoPA3("apMaternoPA3");
		arp.setNombrePA3("nombrePA3");
		arp.setRfcPA3("rfcPA3");
		arp.setCurpPA3("curpPA3");
		arp.setTelefono1PA3("telefono1PA3");
		arp.setExtencionPA3("extencionPA3");
		arp.setTelefono2PA3("telefono2PA3");
		arp.setCorreoPA3("correoPA3");


		arp.setGiro("giro");
		arp.setPrestaServPersNo("X");
		arp.setPrestaServPersSi("X");
		arp.setNoCentTrab(1);
		arp.setSolRegPatronalClase("X");
		arp.setDivisionClave("divisionClave");
		arp.setDivisionDes("divisionDes");
		arp.setGrupoClave("grupoClave");
		arp.setGrupoDes("grupoDes");
		arp.setFraccionClave("fraccionClave");
		arp.setFraccionDes("fraccionDes");
		arp.setClase("clase");
		arp.setPrima("prima");

		//********************************************************************
		LOG.debug("**********Voy a setear AFIL03");

		arp.setProductosOServicios1("productosOServicios1");
		arp.setProductosOServicios2("productosOServicios2");
		arp.setProductosOServicios3("productosOServicios3");
		arp.setProductosOServicios4("productosOServicios4");
		arp.setProductosOServicios5("productosOServicios5");
		arp.setProductosOServicios6("productosOServicios6");
		arp.setProductosOServicios7("productosOServicios7");
		arp.setProductosOServicios8("productosOServicios8");
		arp.setProductosOServicios9("productosOServicios9");
		arp.setProductosOServicios10("productosOServicios10");

		arp.setMateriasPrimas1("materiasPrimas1");
		arp.setMateriasPrimas2("materiasPrimas2");
		arp.setMateriasPrimas3("materiasPrimas3");
		arp.setMateriasPrimas4("materiasPrimas4");
		arp.setMateriasPrimas5("materiasPrimas5");
		arp.setMateriasPrimas6("materiasPrimas6");
		arp.setMateriasPrimas7("materiasPrimas7");
		arp.setMateriasPrimas8("materiasPrimas8");
		arp.setMateriasPrimas9("materiasPrimas9");
		arp.setMateriasPrimas10("materiasPrimas10");

		arp.setMaqEqpNU1("maqEqpNU1");
		arp.setMaqEqpNom1("maqEqpNom1");
		arp.setMaqEqpUso1("maqEqpUso1");
		arp.setMaqEqpTpo1("maqEqpTpo1");
		arp.setMaqEqpCapPot1("maqEqpCapPot1");
		arp.setMaqEqpNU2("maqEqpNU2");
		arp.setMaqEqpNom2("maqEqpNom2");
		arp.setMaqEqpUso2("maqEqpUso2");
		arp.setMaqEqpTpo2("maqEqpTpo2");
		arp.setMaqEqpCapPot2("maqEqpCapPot2");
		arp.setMaqEqpNU3("maqEqpNU3");
		arp.setMaqEqpNom3("maqEqpNom3");
		arp.setMaqEqpUso3("maqEqpUso3");
		arp.setMaqEqpTpo3("maqEqpTpo3");
		arp.setMaqEqpCapPot3("maqEqpCapPot3");
		arp.setMaqEqpNU4("maqEqpNU4");
		arp.setMaqEqpNom4("maqEqpNom4");
		arp.setMaqEqpUso4("maqEqpUso4");
		arp.setMaqEqpTpo4("maqEqpTpo4");
		arp.setMaqEqpCapPot4("maqEqpCapPot4");
		arp.setMaqEqpNU5("maqEqpNU5");
		arp.setMaqEqpNom5("maqEqpNom5");
		arp.setMaqEqpUso5("maqEqpUso5");
		arp.setMaqEqpTpo5("maqEqpTpo5");
		arp.setMaqEqpCapPot5("maqEqpCapPot5");

		arp.setEqpTrnNU1("eqpTrnNU1");
		arp.setEqpTrnNom1("eqpTrnNom1");
		arp.setEqpTrnUso1("eqpTrnUso1");
		arp.setEqpTrnTpo1("eqpTrnTpo1");
		arp.setEqpTrnCapPot1("eqpTrnCapPot1");
		arp.setEqpTrnNU2("eqpTrnNU2");
		arp.setEqpTrnNom2("eqpTrnNom2");
		arp.setEqpTrnUso2("eqpTrnUso2");
		arp.setEqpTrnTpo2("eqpTrnTpo2");
		arp.setEqpTrnCapPot2("eqpTrnCapPot2");
		arp.setEqpTrnNU3("eqpTrnNU3");
		arp.setEqpTrnNom3("eqpTrnNom3");
		arp.setEqpTrnUso3("eqpTrnUso3");
		arp.setEqpTrnTpo3("eqpTrnTpo3");
		arp.setEqpTrnCapPot3("eqpTrnCapPot3");
		arp.setEqpTrnNU4("eqpTrnNU4");
		arp.setEqpTrnNom4("eqpTrnNom4");
		arp.setEqpTrnUso4("eqpTrnUso4");
		arp.setEqpTrnTpo4("eqpTrnTpo4");
		arp.setEqpTrnCapPot4("eqpTrnCapPot4");
		arp.setEqpTrnNU5("eqpTrnNU5");
		arp.setEqpTrnNom5("eqpTrnNom5");
		arp.setEqpTrnUso5("eqpTrnUso5");
		arp.setEqpTrnTpo5("eqpTrnTpo5");
		arp.setEqpTrnCapPot5("eqpTrnCapPot5");

		arp.setProcesoInicial1("procesoInicial1");


		//********************************************************************
		LOG.debug("**********Voy a setear AFIL04");

		arp.setProcesoIntermedio1("procesoIntermedio1");
		arp.setProcesoFinal1("procesoFinal1");

		arp.setPerNoTrab1("perNoTrab1");
		arp.setPerOficOcup1("perOficOcup1");
		arp.setPerNoTrab2("perNoTrab1");
		arp.setPerOficOcup2("perOficOcup1");
		arp.setPerNoTrab3("perNoTrab1");
		arp.setPerOficOcup3("perOficOcup1");
		arp.setPerNoTrab4("perNoTrab1");
		arp.setPerOficOcup4("perOficOcup1");
		arp.setPerNoTrab5("perNoTrab1");
		arp.setPerOficOcup5("perOficOcup1");
		arp.setPerNoTrab6("perNoTrab1");
		arp.setPerOficOcup6("perOficOcup1");
		arp.setPerNoTrab7("perNoTrab1");
		arp.setPerOficOcup7("perOficOcup1");
		arp.setPerNoTrab8("perNoTrab1");
		arp.setPerOficOcup8("perOficOcup1");
		arp.setPerNoTrab9("perNoTrab1");
		arp.setPerOficOcup9("perOficOcup1");
		arp.setPerNoTrab10("perNoTrab1");
		arp.setPerOficOcup10("perOficOcup1");
		arp.setPerNoTrab11("perNoTrab1");
		arp.setPerOficOcup11("perOficOcup1");
		arp.setPerNoTrab12("perNoTrab1");
		arp.setPerOficOcup12("perOficOcup1");

		arp.setConTransportePropio("X");
		arp.setSinTransportePropio("X");
		arp.setNoDistribuye("X");
		arp.setServiciosInstalacion("X");

		arp.setNrp("nrp");
		arp.setDelegacion("delegacion");
		arp.setSubDelegacion("subDelegacion");
		arp.setRfc("rfc");
		arp.setCurp("curp");
		arp.setCadenaOriginal("cadenaOriginal");
		arp.setFirmaDigital("firmaDigital");

		//Imagenes del reporte
		arp.setLOGO_IMSS_HEADER_PARAM("logo_imss.jpg");
		arp.setLOGO_INFO_HEADER_PARAM("logo_info.jpg");


		/////////////////////////////////

		arp.setTipoSociedad("tipoSociedad");

		arp.setApPaterno1("apPaterno1");
		arp.setCalle1("calle1");
		arp.setNumExt1("numExt1");
		arp.setNumInt1("numInt1");
		arp.setColonia1("colonia1");
		arp.setLocalidad1("localidad1");
		arp.setMunDelegacion1("munDelegacion1");
		arp.setEntFederativa1("entFederativa1");
		arp.setCodPost1("codPost1");
		arp.setTelfijo1("telfijo1");
		arp.setEmail1("email1");

		arp.setNumEscritura("numEscritura");
		arp.setNumNotarira("numNotarira");
		arp.setLugarExpedicion("lugarExpedicion");
//		arp.setFechaExpedicion("fechaExpedicion");
		arp.setFolioMercantil("folioMercantil");
		arp.setNumeroReferencia("numeroReferencia");
//		arp.setFechaDocumentoRegistro("fechaDocumentoRegistro");
		arp.setAutoridadLaboral("autoridadLaboral");


		/////////////////////TIP/////////////////////
		arp.setNombreCompletoRl("nombreCompletoRl");
		arp.setDomicilio("domicilio");
		arp.setVigencia("vigencia");


		return arp;
	}

    public String getTiempoAlta(Date fechaIni, Date fechaFin){
	    Long segundosDeEspera= new Long (((fechaFin.getTime() - fechaIni.getTime()) / 1000)%60 );
	    Long minutosDeEspera = new Long ((((fechaFin.getTime() - fechaIni.getTime()) )/1000)/60 );
	    return minutosDeEspera.toString() + " Min. "+segundosDeEspera+" Seg.";
    }

    private DomicilioFiscal getDomicilioFiscal(DomicilioServiceBusinessRemote domicilioService,
    		ReportesARP arp, Persona persona){
    	DomicilioFiscal domFis = null;
    	try {
			// Se ejecuta el servicio para obtener el domicilio fiscal
			domFis = domicilioService.consultarDomicilioFiscalPersona(persona);
			if(domFis != null){
				Asentamiento asentamiento = domFis.getAsentamiento();
				Localidad localidad = asentamiento.getLocalidad();
				arp.setCalle(domFis.getVialidadPrimaria().getNombre());
				if(domFis.getNumExteriorAlf() != null)
					arp.setNumExt(domFis.getNumExteriorAlf());
				if(domFis.getNumInteriorAlf() != null)
					arp.setNumInt(domFis.getNumInteriorAlf());
				if(domFis.getVialidadReferenciaPrimaria() != null && domFis.getVialidadReferenciaPrimaria().getNombre() != null
						&& domFis.getVialidadReferenciaPrimaria().getNombre().trim().length() > 0)
					arp.setEntrecalle1(domFis.getVialidadReferenciaPrimaria().getNombre());
				if(domFis.getVialidadReferenciaSecundaria() != null && domFis.getVialidadReferenciaSecundaria().getNombre() != null
						&& domFis.getVialidadReferenciaSecundaria().getNombre().trim().length() > 0)
					arp.setEntrecalle2(domFis.getVialidadReferenciaSecundaria().getNombre());
				arp.setColonia(domFis.getColonia());
				arp.setLocalidad(localidad.getNombre());
				arp.setMunicipio(localidad.getMunicipio().getNombre());
				arp.setEntidadaFederativa(localidad.getMunicipio().getEntidadFederativa().getNombre());
				if(asentamiento.getCodigoPostal().getCodigoPostal() != null && asentamiento.getCodigoPostal().getCodigoPostal().trim().length() > 0){
					String cp = asentamiento.getCodigoPostal().getCodigoPostal();
					if(cp.length() == 4)
						cp = "0" + cp;
					else if(cp.length() == 3)
						cp = "00" + cp;
					arp.setCp(cp);
				}
			}else{
				LOG.debug("********** No se encontro el domicilio fiscal");
			}
		} catch (DomicilioNoLocalizadoException e) {
			e.printStackTrace();
		}
    	return domFis;
    }

}
