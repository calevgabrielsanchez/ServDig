package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.Page;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ConsultaModel;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ActualizarObraInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ConsultaObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.DetalleRegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraGeneralExtPrto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocInput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObrasSimilaresInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.SirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siroc.ISirocServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISirocServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AgendarCitaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Stateless(name = "sirocServiciosDigitalesService", mappedName = "sirocServiciosDigitalesService")
public class SirocServiciosDigitalesService implements
		ISirocServiciosDigitalesServiceRemote {

	private static Logger log = LoggerFactory
			.getLogger(SirocServiciosDigitalesService.class);
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@EJB
	private ISirocServiceEntityLocal sirocServiceEntity;
	
	@EJB(mappedName="sujetoObligadoServiceBusiness")
	SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	@EJB(mappedName = "agendarCitaService")
	private AgendarCitaServiceRemote agendarCitaService;

	@Override
	public DetalleRegistroObra getRegistroObraByNumRegistro(
			String numRegistroObra) throws ServiciosRestException {
		log.debug("llegue al metodo getRegistroObraByNumRegistro con registro de obra"
				+ numRegistroObra);
		ValidacionesComunesUtil.validaIdCatalogo(numRegistroObra,
				"EL atribuo numRegistroObra no puede ser nulo ");
		try {
			DetalleRegistroObra registroObra = sirocServiceEntity
					.getRegistroObraByNumRegistro(numRegistroObra);
			if (registroObra != null)
				log.debug("regrese de la consulta del servicio con valores"
						+ registroObra.getRegistroPatronal());
			return registroObra;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda del registro de obra "
							+ numRegistroObra, e);
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de registro de obra"
							+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public List<AvisoUbicacionObra> consultaAvisoRegistroObraByDelegSubDel(
			Long cveIdDelegacion, Long cvdIdSubDelegacion)
			throws ServiciosRestException {
		log.debug(
				"llegando al servicio consultaAvisoRegistroObraByDelegSubDel subDel {cveIdSubDelegacion}",
				cvdIdSubDelegacion);
		ValidacionesComunesUtil.validaIdCatalogo(cveIdDelegacion,
				"El atributo cveIdDelegacion no puede ser nulo");
		ValidacionesComunesUtil.validaIdCatalogo(cvdIdSubDelegacion,
				"El atributo cvdIdSubDelegacion no puede ser nulo");
		try {
			List<AvisoUbicacionObra> registroObra = sirocServiceEntity
					.consultaAvisoRegistroObraByDelegSubDel(cveIdDelegacion,
							cvdIdSubDelegacion);
			return registroObra;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda consultaAvisoRegistroObraByDelegSubDel "
							+ cvdIdSubDelegacion, e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consulta de Aviso de registro de obra por subdelegacion"
									+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public AvisoUbicacionObraDetalle getAvisoRegistroObra(
			String cveRegistroAvisoObra) throws ServiciosRestException {
		log.debug(
				"llegando al servicio getAvisoRegistroObra subDel {cveRegistroAvisoObra}",
				cveRegistroAvisoObra);
		ValidacionesComunesUtil.validaIdCatalogo(cveRegistroAvisoObra,
				"El atributo cveRegistroAvisoObra no puede ser nulo");
		try {
			AvisoUbicacionObraDetalle registroObra = sirocServiceEntity
					.getAvisoRegistroObra(cveRegistroAvisoObra);
			return registroObra;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda getAvisoRegistroObra "
							+ cveRegistroAvisoObra, e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consulta de Aviso de registro de obra por ID"
									+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public List<RegistroObra> consultaRegistroObraByCPColonia(
			String codigoPostal, String colonia) throws ServiciosRestException {
		log.debug(
				"llegando al servicio consultaRegistroObraByCPColonia codigoPostal {codigoPostal}",
				codigoPostal);
		ValidacionesComunesUtil.validaIdCatalogo(codigoPostal,
				"El atributo codigoPostal no puede ser nulo o vacio");
		ValidacionesComunesUtil.validaIdCatalogo(colonia,
				"El atributo codigoPostal no puede ser nulo o vacio");
		try {
			List<RegistroObra> registroObra = sirocServiceEntity
					.consultaRegistroObraByCPColonia(codigoPostal, colonia);
			return registroObra;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda consultaRegistroObraByCPColonia "
							+ codigoPostal, e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal"
									+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public Page<ObraGeneralExtPrto> obrasRegistradasPRTO(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException {
		log.debug(
				"llegando al servicio consultaRegistroObraByCPColonia codigoPostal {idDelegacion}, {idSubDelegacion}",
				((ObraGeneralExtPrto) input.getModel()).getIdDelegacion(),
				((ObraGeneralExtPrto) input.getModel()).getIdSubDelegacion());
		try {
			Page<ObraGeneralExtPrto> page = null;
			if (input.getCampoAproximacion() != null
					&& !input.getCampoAproximacion().isEmpty()) {
				page = sirocServiceEntity.obrasRegistradasPRTOAproximacion(
						input, quitarDiasHabilesFecha(5));
			} else {
				page = sirocServiceEntity.obrasRegistradasPRTO(input,
						quitarDiasHabilesFecha(5));
			}
			if (page.getContent() != null) {
				for (ObraGeneralExtPrto obra : page.getContent()) {
					obra.setNumDias(numeroDias(obra.getFecFinObra()));
				}
			}
			else {
				page = new Page<ObraGeneralExtPrto>();
			}
			return page;

		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda obrasRegistradasPRTO ",
					e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal"
									+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public ObraGeneralExtPrto actualizarObra(ActualizarObraInputSiroc input)
			throws ServiciosRestException {
		log.debug(
				"llegando al servicio actualizarObra {input.getMarcaPrtoObra()}, {input.getNumeroRegistroObra()}",
				input.getMarcaPrtoObra(), input.getNumeroRegistroObra());
		ObraGeneralExtPrto obraGeneralExtPrto = null;
		try {
			if (sirocServiceEntity.existeMarcaPRTO(input
					.getNumeroRegistroObra())) {

				obraGeneralExtPrto = sirocServiceEntity
						.actualizaMarcaPRTO(input.getNumeroRegistroObra(),
								input.getMarcaPrtoObra());
			} else {
				obraGeneralExtPrto = sirocServiceEntity
						.insertaMarcaPRTO(input.getNumeroRegistroObra(),
								input.getMarcaPrtoObra());
			}
			return obraGeneralExtPrto;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda obrasRegistradasPRTO ",
					e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal"
									+ e.getMessage(), e.getMessage()));
		}
	}
	
	@Override
	public ObraGeneralExtPrto detalleObraPRTO(String numObra)
			throws ServiciosRestException {
		log.debug(
				"llegando al servicio obrasRegistradasPRTO numObra {numObra}",
				numObra);
		try {
			ObraGeneralExtPrto obras = sirocServiceEntity
					.detalleObraPRTO(numObra);		
			if (obras != null) {
				
				SujetoObligado sujeto = new SujetoObligado();
				sujeto.setNumeroRegistroPatronal(obras.getRegistroPatronal().substring(0,10));
				SujetoObligado sujetoRespuesta = sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
				String rfc = sujetoRespuesta.getMoral() != null  ? sujetoRespuesta.getMoral().getRfc() : sujetoRespuesta.getFisica().getRfc();
				String nombreRazonSocial = sujetoRespuesta.getMoral() != null  ? sujetoRespuesta.getMoral().getRazonSocial() : sujetoRespuesta.getFisica().getNombreCompleto();
				StringBuilder sb = new StringBuilder();
				sb.append(
						sujetoRespuesta.getClasificacion().getFraccion().getGrupo().getDivision().getNumDivision());
				sb.append(sujetoRespuesta.getClasificacion().getFraccion().getGrupo().getNumGrupo());
				if (Integer.valueOf(sujetoRespuesta.getClasificacion().getFraccion().getNumFraccion()) < 10) {
					sb.append(0);
				}
				sb.append(sujetoRespuesta.getClasificacion().getFraccion().getNumFraccion());
				obras.setRfc(rfc);
				obras.setNombreRazonSocial(nombreRazonSocial);
				obras.setPrima(sujetoRespuesta.getClasificacion().getPrimaSRTActual());
				obras.setFraccion(sb.toString());
				obras.setClase(sujetoRespuesta.getClasificacion().getFraccion().getClase().getDescripcion());
				obras.setBaja(sujetoRespuesta.getDescSituacionBaja());
			}
			return obras;
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda obrasRegistradasPRTO ",
					e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal"
									+ e.getMessage(), e.getMessage()));
		}
	}
	@Override
 	public Page<ConsultaObraDetalle> getConsultaObra(ObrasSimilaresInputSiroc<ObraSirocInput>datObra) throws ServiciosRestException {
		log.debug("Llegando al servicio getConsultaObra");
		try {
			Page<ConsultaObraDetalle> registroObra = sirocServiceEntity.getConsultaObra(datObra);
			return registroObra;
		} catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda getConsultaObra....... ", e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra********"
									+ e.getMessage(), e.getMessage()));
		}
	}
	
	//Consulta para AVISO DE OBRAS  Aviso de Ubicación de Obras
	public Page<RegistroObraDetalle> consultaUbicacionObra(ObrasSimilaresInputSiroc<ObraSirocInput> datObra) throws ServiciosRestException {
		log.debug("Llegando al servicio consultaUbicacionObra");
		try {
			Page<RegistroObraDetalle> ubicasionObra = sirocServiceEntity.consultaUbicacionObra(datObra);
			return ubicasionObra;
		} catch (Exception e) {
			log.error("Ocurrio un erro	 al realizar la busqueda de la consulta de Ubicacion de Obra....... ", e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en consulta de Ubicacion de Obra********"
									+ e.getMessage(), e.getMessage()));
		}
	}
	
	@Override
	public Page<ObraGeneralExtPrto> obrasSimilaresCP(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException {
		log.debug("llegando al servicio obrasSimilares codigoPostal {cp}",
				((ObraGeneralExtPrto) input.getModel()).getCodigoPostal());
		try {
			Page<ObraGeneralExtPrto> page = sirocServiceEntity.obrasSimilaresCP(input);
			if (page.getContent() != null) {
				return page;
			}
			else {
				return new Page<ObraGeneralExtPrto>();
			}
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda obrasRegistradasPRTO ",
					e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal"
									+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public Page<ObraGeneralExtPrto> obrasSimilaresCPColonia(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException {
		log.debug(
				"llegando al servicio obrasSimilares codigoPostal {}, colonia {}",
				((ObraGeneralExtPrto) input.getModel()).getCodigoPostal(),
				((ObraGeneralExtPrto) input.getModel()).getColonia());
		try {
			Page<ObraGeneralExtPrto> page = sirocServiceEntity.obrasSimilaresCPColonia(input);
			if (page.getContent() != null) {
				return page;
			}
			else {
				return new Page<ObraGeneralExtPrto>();
			}
		} catch (Exception e) {
			log.error(
					"Ocurrio un erro al realizar la busqueda obrasRegistradasPRTO ",
					e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consultar los registros de obra por codigo postal y colonia"
									+ e.getMessage(), e.getMessage()));
		}
	}
	
	public static void main(String[] args) {
		SirocServiciosDigitalesService sirocServiciosDigitalesService = new SirocServiciosDigitalesService();
		
		sirocServiciosDigitalesService.quitarDiasHabilesFecha(6);
	}
	
	private Date quitarDiasHabilesFecha(int numeroDias) {
		Calendar fechaActual = Calendar.getInstance();		
		
		try {
			List<Date> dias = agendarCitaService.getFechasInhabiles();
			boolean inhabil = false;
			Integer i = 0;
			while (i <= numeroDias) {
				fechaActual.add(Calendar.DATE, -1);
				inhabil = false;
				if (fechaActual.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& fechaActual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					for (Date dia : dias) {
						if (DATE_FORMAT.format(dia).equals(DATE_FORMAT.format(fechaActual.getTime()))) {
							inhabil = true;
							break;
						}
					}
					if (!inhabil) {
						i = i + 1;
					}
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return fechaActual.getTime();
	}
	
	private Date agregarDiasHabiles(Date fechaInicial, int numeroDias) {
		Calendar fechaActual = Calendar.getInstance();		
		fechaActual.setTime(fechaInicial);
		try {
			List<Date> dias = agendarCitaService.getFechasInhabiles();
			boolean inhabil = false;
			Integer i = 0;
			while (i <= numeroDias) {
				fechaActual.add(Calendar.DATE, 1);
				inhabil = false;
				if (fechaActual.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& fechaActual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					for (Date dia : dias) {
						if (DATE_FORMAT.format(dia).equals(DATE_FORMAT.format(fechaActual.getTime()))) {
							inhabil = true;
							break;
						}
					}
					if (!inhabil) {
						i = i + 1;
					}
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return fechaActual.getTime();
	}
	
	private Integer numeroDias(Date fechaFinDate) {
		Integer numDias = 0;
		try {
			List<Date> dias = agendarCitaService.getFechasInhabiles();
			boolean inhabil = false;
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.setTime(agregarDiasHabiles(fechaFinDate, 6));
			while (fechaFin.compareTo(fechaActual) <= 0) {
				fechaFin.add(Calendar.DATE, 1);
				inhabil = false;
				if (fechaFin.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& fechaFin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					for (Date dia : dias) {
						if (DATE_FORMAT.format(dia).equals(DATE_FORMAT.format(fechaFin.getTime()))) {
							inhabil = true;
							break;
						}
					}
					if (!inhabil) {
						numDias = numDias + 1;
					}
				}
			}

		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return numDias;
	}
	
	public List<Date> getDiasInhabilesPorAnio(Long numAnio)
			throws ServiciosRestException {
		List<Date> lstDiasPorAnio = new ArrayList<Date>();
		try {
			List<Date> lstDias = agendarCitaService.getFechasInhabiles();
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");

			if (lstDias != null && !lstDias.isEmpty()) {
				lstDiasPorAnio = new ArrayList<Date>();
				for (Date dia : lstDias) {

					if (numAnio.intValue() == Integer.parseInt(formatYear
							.format(dia)))
						lstDiasPorAnio.add(dia);
				}
			}
			return lstDiasPorAnio;
		} catch (Exception e) {
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar los dias inhabiles ",
					e.getMessage()), e);

		}

	}

	@Override
	public List<ConsultaModel> tipoPatron() throws ServiciosRestException {
		
			log.debug("llegando al servicio tipoPatron");
			
			try {
				List<ConsultaModel> tipoPatron = sirocServiceEntity.tipoPatron();
				return tipoPatron;
			} catch (Exception e) {
				log.error("Ocurrio un erro al realizar la busqueda tipoPatron ", e);
				throw new ServiciosRestException(
						new ErrorResponseBean(
								ErrorResponseBean.codigo500,
								ErrorResponseBean.codigo500Descripcion,
								"Ocurrio un error inesperado en la consulta de tipoPatron()"
										+ e.getMessage(), e.getMessage()));
			}
		}
	
	@Override
	public List<ConsultaModel> estatusObra() throws ServiciosRestException {
		
			log.debug("llegando al servicio estatusObra");
			
			try {
				List<ConsultaModel> tipoPatron = sirocServiceEntity.estatusObra();
				return tipoPatron;
			} catch (Exception e) {
				log.error("Ocurrio un erro al realizar la busqueda estatusObra ", e);
				throw new ServiciosRestException(
						new ErrorResponseBean(
								ErrorResponseBean.codigo500,
								ErrorResponseBean.codigo500Descripcion,
								"Ocurrio un error inesperado en la consulta de estatusObra()"
										+ e.getMessage(), e.getMessage()));
			}
		}
	
	@Override
	public List<ConsultaModel> rocTipoIncidencia() throws ServiciosRestException {
		
			log.debug("llegando al servicio rocTipoIncidencia");
			
			try {
				List<ConsultaModel> tipoPatron = sirocServiceEntity.rocTipoIncidencia();
				return tipoPatron;
			} catch (Exception e) {
				log.error("Ocurrio un erro al realizar la busqueda rocTipoIncidencia ", e);
				throw new ServiciosRestException(
						new ErrorResponseBean(
								ErrorResponseBean.codigo500,
								ErrorResponseBean.codigo500Descripcion,
								"Ocurrio un error inesperado en la consulta de rocTipoIncidencia()"
										+ e.getMessage(), e.getMessage()));
			}
		}
	
	@Override
	public SirocOutput rocDetalleObra(String numObra) throws ServiciosRestException {
		
			log.debug("llegando al servicio rocDetalleObra");
			
			try {
				SirocOutput detalleObra = sirocServiceEntity.rocDetalleObra( numObra);
				return detalleObra;
			} catch (Exception e) {
				log.error("Ocurrio un erro al realizar la busqueda rocDetalleObra ", e);
				throw new ServiciosRestException(
						new ErrorResponseBean(
								ErrorResponseBean.codigo500,
								ErrorResponseBean.codigo500Descripcion,
								"Ocurrio un error inesperado en la consulta de rocDetalleObra()"
										+ e.getMessage(), e.getMessage()));
			}
		}

	@Override
	public List<ObraSirocOutput> getObrasHijasByRegObra(List<String> input) throws Exception {
		log.debug("Llegando al servicio getObrasHijasByRegObra");
		try {
			List<ObraSirocOutput> ubicasionObra = sirocServiceEntity.getObrasHijasByRegObra(input);
			return ubicasionObra;
		} catch (Exception e) {
			log.error("Ocurrio un erro	 al realizar la busqueda de la consulta de getObrasHijasByRegObra....... ", e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en consulta de getObrasHijasByRegObra********"
									+ e.getMessage(), e.getMessage()));
		}
	}

}
