package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AseguradoVigentePermisoCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AsignacionNssDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolave;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolaveCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoCuentaIndividual;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoPensionado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoCL;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoMarcaAfiliatoria;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.vigencia.MgtInfincasegvig;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.asegurado.AseguradoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.vigencia.VigenciaServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.CuentaIndividualUtil;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserDatosPersonaToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserDomicilioGeograficoToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.CuentaIndividualVo;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual_Service;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;



@Stateless(name = "aseguradoServiciosDigitalesService", mappedName = "aseguradoServiciosDigitalesService")
public class AseguradoServiciosDigitalesService extends AbstractServiceBusiness implements IAseguradoServiciosDigitalesServiceRemote {

	private static final Logger log = LoggerFactory
			.getLogger(AseguradoServiciosDigitalesService.class);

	@EJB
	private VigenciaServiceEntityLocal vigenciaServiceEntity;

	@EJB
	private AseguradoServiceEntityLocal aseguradoEntity;

	@EJB(mappedName = "patronServiciosDigitalesService")
	private IPatronServiciosDigitalesServiceRemote patronServiciosDigitalesService;
	@EJB(mappedName = "consultaPersonaService")
	private IConsultaPersonaServiceRemote consultaPersonaService;


	@EJB( mappedName = "serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;

	@EJB(mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;

	@EJB(mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarService;


	@EJB(mappedName = "personaFisicaServiceBusiness")
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;


	@EJB(mappedName = "solicitudService")
	private SolicitudServiceRemote solicitudService;


	@Override
	public List<AseguradoCuentaIndividual> consultarMovimientosCuentaIndividual  (
			String nss) throws ServiciosRestException {

		nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
		final WSNssCuentaIndividual_Service service = new WSNssCuentaIndividual_Service();
		final WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();

		final String DATE_PATTERN = "yyyy-MM-dd";
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
		final String[] strArrayElementosExclucion = {"fechaInicioMovimiento","fechaFinalMovimiento",
				"fechaRecepcionMovimiento", "fechaActualizacion", "fechaCarga"}; 

		try{

			List<CuentaIndividualVo> periodos = port.getCuentaIndividual(nss).getCuentaIndividual();
			ValidacionesComunesUtil.validaListaNulaVacia(periodos, "No se encontraron registros en la cuenta individual"
					+ " para el NSS " + nss);
			List<AseguradoCuentaIndividual> lstCuentaIndividual = new ArrayList<AseguradoCuentaIndividual>();
			for (CuentaIndividualVo cuenta : periodos) {
				AseguradoCuentaIndividual cuentaIndVo = new AseguradoCuentaIndividual();
				BeanUtils.copyProperties(cuenta,cuentaIndVo, strArrayElementosExclucion);
				try {
					cuentaIndVo.setFechaInicioMovimiento(dateFormat.parse(cuenta.getFechaInicioMovimiento()));		
				}catch (Exception e) {
					log.error("ocurrio un error al transformar la setFechaFinalMovimiento ", e);
				}
				try {
					cuentaIndVo.setFechaFinalMovimiento(dateFormat.parse(cuenta.getFechaFinalMovimiento()));		
				}catch (Exception e) {
					log.error("ocurrio un error al transformar la fechaFinalMovimiento ", e);
				}
				try {
					cuentaIndVo.setFechaRecepcionMovimiento(dateFormat.parse(cuenta.getFechaRecepcionMovimiento()));		
				}catch (Exception e) {
					log.error("ocurrio un error al transformar la setFechaRecepcionMovimiento ", e);
				}
				try {
					cuentaIndVo.setFechaActualizacion(dateFormat.parse(cuenta.getFechaActualizacion()));		
				}catch (Exception e) {
					log.error("ocurrio un error al transformar la fechaActualizacion ", e);
				}
				try {
					cuentaIndVo.setFechaCarga(dateFormat.parse(cuenta.getFechaCarga()));		
				}catch (Exception e) {
					log.error("ocurrio un error al transformar la fechaCarga ", e);
				}
				//log.debug("el bean ya lleno quedo como " + cuentaIndVo.toString() );

				lstCuentaIndividual.add(cuentaIndVo);

			}
			CuentaIndividualUtil.ordenarCuentasIndividuales(lstCuentaIndividual);

			return lstCuentaIndividual;
		}catch (ServiciosRestException e) {
			throw e;
		}catch(Exception e){
			log.error("ocurrio un error en el ws de almacenes de cuenta individual nss" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el ws de almacenes de cuenta individual nss" +  nss + e.getMessage(),
					"ocurrio un error en el ws de almacenes de cuenta individual nss" +  nss + e.getMessage()),e);
		}

	}


	/**
	 *Consulta la información de un grupo familiar incluyendo la vigencia 
	 * @param nss
	 * @return List<GrupoFamiliar>
	 * @throws ServiciosRestException
	 */
	@Override
	public List<DerechohabienteDTO> getGrupoFamiliarByNSS(String nss)throws ServiciosRestException{

		try {
			nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
			List<GrupoFamiliar> lstGrupoFamiliar = 	grupoFamiliarService.findDatosBasicosIntegrantesGrupoByNumNss(nss, true, true, true);
			if(lstGrupoFamiliar == null || lstGrupoFamiliar.isEmpty()) {
				log.error("la consulta de grupo familiar llego vacia");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se ecnotro al informacion del grupo familiar con NSS " + nss , "No se ecnotro al informacion del grupo familiar con NSS " + nss ));
			}
			log.debug("regrese del servicio de vigencia con derechohabientes" + lstGrupoFamiliar.size() );
			return  ParserDatosPersonaToRest.setDatosDerechohabienteList(lstGrupoFamiliar);
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {

			log.error("ocurrio un erro al consultar el grupo familiar " + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar al grupo familiar con NSS " +  nss , e.getMessage()),e);
		}

	}

	/**
	 * Consulta la información del asegurado del grupo familiar son el bdtu sin vigencia
	 * @@param nss, filtroBajaLogica true si la fecha de baja debe ser nula, false no se aplica filtro
	 * @return DerechohabienteDTO con la información del asegurado
	 * @throws ServiciosRestException
	 */
	@Override
	public DerechohabienteDTO getAseguradoGrupoFamiliarByNSSSinVigencia(String nss,  boolean filtroBajaLogica) throws ServiciosRestException {

		try {
			nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
			//GrupoFamiliar gpoFamiliar = grupoFamiliarService.getGrupoFamiliar(nss,false);
			AsignacionNssDTO aseguradBDTU = aseguradoEntity.getDitAsignacionNSS(nss, filtroBajaLogica);
			if(aseguradBDTU == null ) {
				log.debug("no se encontro información con el NSS" + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro  informacion del Asegurado con el NSS " + nss , "No se encontro  informacion del Asegurado con el NSS " + nss ));
			}
			log.debug("regrese del servicio de vigencia con derechohabientes" + aseguradBDTU.getCveIdAsignacionNSS());
			GrupoFamiliar gpoFamiliar = grupoFamiliarService.getIntegranteGrupoFamiliarSinVigencia(aseguradBDTU.getCveIdAsignacionNSS().longValue(),
					aseguradBDTU.getCveIdPersona().longValue());
			if(gpoFamiliar != null)
				return  ParserDatosPersonaToRest.setDatosDerechohabiente(gpoFamiliar);
			else {
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro  informacion de la adscipción del Asegurado con el NSS  " + nss , "No se encontro informacion de la adscipción del Asegurado con el NSS " + nss ));
			}
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			System.out.println("ocurrio un erro al consultar el grupo familiar " + nss + e);
			log.error("ocurrio un erro al consultar el grupo familiar " + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar al grupo familiar con NSS " +  nss , e.getMessage()),e);
		}


	}

	@Override
	public Boolean validaRelacionLaboralAseguradoPatron(String nss, String nrp) throws ServiciosRestException {

		boolean isPatronValido = false;
		try {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			nrp =ValidacionesComunesUtil.validaEstructuraNRP(nrp);


			/* Validacion datos del asegurado */
			AsignacionNSS asegurado= serviceBusiness.obtenerAseguradoPorNss(nss);

			if(asegurado == null ) {
				log.debug("la consulta de grupo familiar llego vacia");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro al informacion del Asegurado con el NSS " + nss , "No se encontro  informacion del Asegurado con el NSS " + nss ));
			}
			asegurado.setCveIdAsignacionNSS(asegurado.getIdAsignacionNSS());
			List<SujetoObligado> lstPatronesVigentes = grupoFamiliarService.getPatronesAsegurado(asegurado);
			if (lstPatronesVigentes != null && !lstPatronesVigentes.isEmpty()) {
				log.debug("La lista de patrones no es nula continua validacion. No. patrones: " + lstPatronesVigentes.size());
				for (SujetoObligado patron : lstPatronesVigentes) {
					String nrpCompleto = patron.getNumeroRegistroPatronal()+
							patron.getModalidad().getNumModalidad();
					if (nrp.equals(nrpCompleto)) {
						isPatronValido = true;
						break;
					}
				}
			}
			return isPatronValido;
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar el grupo familiar " + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar los patrones vigentes con NSS " +  nss , e.getMessage()),e);
		}

	}

	@Override
	public DerechohabienteSinolave getAseguradoVigenteSinolave(String nss) throws ServiciosRestException {
		try {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			/* Validacion datos del asegurado */
			AsignacionNSS asegurado = serviceBusiness.obtenerAseguradoPorNss(nss);
			MgtInfincasegvig vigenciaBDTU = vigenciaServiceEntity.getMgtInfincasegvig(nss);

			GrupoFamiliar grupoAsegurado = null;
			if(asegurado == null || vigenciaBDTU == null ) {
				log.debug("no se localizo en NSS en BDTU " + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encotro al informacion del Asegurado con el NSS " + nss , "No se ecnotro al informacion del Asegurado con el NSS " + nss ));
			}
			asegurado.setCveIdAsignacionNSS(asegurado.getIdAsignacionNSS());

			log.debug("regrese del servicio de vigencia con derechohabientes" + asegurado.getNss() );
			grupoAsegurado = grupoFamiliarService.getIntegranteGrupoFamiliarSinVigencia(asegurado.getIdAsignacionNSS(),
					asegurado.getIdPersona());
			try {
				return  ParserDatosPersonaToRest.setDatosDerechohabienteSinolave(asegurado,vigenciaBDTU, grupoAsegurado );
			}catch (Exception e) {
				log.error("ocurrio un error en el parser de setDatosDerechohabienteSinolave" ,e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error en el parser de setDatosDerechohabienteSinolave " +  nss  +" "+ e.getMessage() , e.getMessage()),e);
			}

		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar el grupo familiar " + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar al asegurado getAseguradoVigenteSINOLAVE " +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}
	}

	@Override
	public String getFechaBajaDitAsignacionNss(String nss) throws ServiciosRestException {
		log.debug("llegue al metodo para consultar la fecha de baja del NSS");
		try {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			AsignacionNssDTO asegurado = aseguradoEntity.getDitAsignacionNSS(nss, false);
			if(asegurado != null)
				return asegurado.getFecRegistroBaja();
			else
				return null;
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar la fecha registro baja del asegurado" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro al consultar la fecha registro baja del asegurado " +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}


	}

	@Override
	public String isAseguradoPasoAlCambioAlPendiente(String nss) throws ServiciosRestException {
		log.debug("llegue al metodo para consultar paso o cambio al isAseguradoPasoAlCambioAlPendiente" + nss);
		try {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			return aseguradoEntity.isAseguradoPasoAlCambioAlPendiente(nss);
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar paoo al cambio al del asegurado" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro al consultar paoo al cambio al del asegurado " +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}
	}

	@Override
	public AseguradoPensionado getAseguradoPensionado(String nss) throws ServiciosRestException{
		log.debug("llegue al metodo para consultar al asegurado con pension" + nss);
		try {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			AseguradoPensionado aseguradoPension = new AseguradoPensionado();
			try {
				DerechohabienteDTO dh=this.getAseguradoGrupoFamiliarByNSSSinVigencia(nss, false);
				BeanUtils.copyProperties(dh, aseguradoPension);
				aseguradoPension.setNss(nss);
			}catch(ServiciosRestException e) {
				if(!e.getErrorBean().getCode().equals(ErrorResponseBean.codigo404)) {
					throw e;
				}else {
					AsignacionNssDTO aseguradoDTO =aseguradoEntity.getDitAsignacionNSS(nss, false);
					ValidacionesComunesUtil.validaObjetoRespuestaNulo(aseguradoDTO, "No se localizo el NSS en la BDTU");
					Fisica personaBDTU = personaBusiness.getPersonaFisica(aseguradoDTO.getCveIdPersona().longValue());
					if(personaBDTU == null)
						throw new ServiciosRestException(new ErrorResponseBean(
								ErrorResponseBean.codigo404,ErrorResponseBean.codigo404Descripcion,
								"No se encontro  informacion de la persona en BDTU con NSS  "+ nss,
										"No se encontro  informacion de la persona en BDTU con NSS  "+ nss));
						
					ParserDatosPersonaToRest.setDatosPersona(personaBDTU, aseguradoPension);
					aseguradoPension.setNss(nss);
					aseguradoPension.setCveIdAsignacionNssGrupoFamiliar(aseguradoDTO.getCveIdAsignacionNSS().longValue());
				}
			}
			aseguradoPension.setTipoPension(aseguradoEntity.getTipoPensionAsegurado(nss));
			return aseguradoPension;
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar al asegruado pensioando" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro al consultar al asegruado pensioando" +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}
	}

	@Override
	public Persona getPersonaServiciosDigitalesByNSS(String nss) throws ServiciosRestException {
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		try {
			Persona objPersonaBdtu = new Persona();
			AsignacionNSS asegurado = serviceBusiness.obtenerAseguradoPorNss(nss);
			
			if(asegurado == null) {
				log.debug("no se localizo el NSS en BDTU " + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encotro la informacion del Asegurado con el NSS " + nss , "No se ecnotro la informacion del Asegurado con el NSS " + nss ));
			}
			return ParserDatosPersonaToRest.setDatosPersona(asegurado, objPersonaBdtu);
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error la consultar la infomración del asegurado " + nss, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar la infomración del asegurado", e.getMessage()),e);
		}


	}

	@Override
	public  mx.gob.imss.digital.modelo.domicilio.Domicilio consultaDomicilioAseguradoGrupoFamiliar(String nss) throws ServiciosRestException{
		PersonaDomicilio perDom = null;
		nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
		try {
			AsignacionNSS asignacion = solicitudService.getAsignacionNssPorNss(nss);
			if(asignacion != null)
				perDom = grupoFamiliarService.findDomicilioByIdPersona(asignacion.getIdPersona());
				if(perDom != null)
					return  ParserDomicilioGeograficoToRest.parserDomicilioDeltaToDomicilioDigital(perDom.getDomicilio());
		}catch (Exception e) {
			log.error("error al buscar el domiciolo del asegurado[" +nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo400Descripcion,
					"ocurrio un error la consultar el domicilio del asegurado", e.getMessage()) ,e);
		}

		return null;
	}


	@Override
	public List<DatosGeneralesAsegurado> getDatosGeneralesAseguradoByNss(List<String> lstNss)
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaListaNoVacia(lstNss, 1000L, "La lista no puede ser nula o tener mas de 1000 elementos");
		List<String> lstNssOk = new ArrayList<String>(); 
		for(String nss : lstNss) {
			nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
			lstNssOk.add(nss);
		}
		try {
			return aseguradoEntity.getDatosGeneralesAseguradoByNss(lstNssOk);		
		}catch (Exception e) {
			log.error("ocurrio un error la consultar la lista deasegurados " + lstNss, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar la lista de asegurados ", e.getMessage()),e);
		}

	}

	@Override
	public DatosGeneralesAsegurado getDatosGeneralesAseguradoByNss(
			Long idPersona) throws ServiciosRestException {
		try {
			return aseguradoEntity.getDatosGeneralesAseguradoByNssDitPersona(idPersona);
		} catch (Exception e) {
			log.error("ocurrio un error la consultar la lista deasegurados "
					+ idPersona, e);
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar la lista de asegurados ",
					e.getMessage()), e);
		}

	}




	@Override
	public DatosGeneralesAsegurado getDatosGeneralesAseguradoByNss(String nss) throws ServiciosRestException {
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		try {
			List<String> lstNss = new ArrayList<String>();
			lstNss.add(nss);
			List<DatosGeneralesAsegurado> lstResultado = aseguradoEntity.getDatosGeneralesAseguradoByNss(lstNss);
			if(lstResultado != null && !lstResultado.isEmpty())
				return lstResultado.get(0);
			else
				return null;
		}catch (Exception e) {
			log.error("ocurrio un error la consultar la infomración del asegurado " + nss, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar la infomración del asegurado", e.getMessage()),e);
		}
	}


	@Override
	public DatosGeneralesAsegurado getCanaseDatosGeneralesAseguradoByNss(String nss) throws ServiciosRestException {
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		try {
			List<String> lstNss = new ArrayList<String>();
			lstNss.add(nss);
			List<DatosGeneralesAsegurado> lstResultado = aseguradoEntity.getCanseDatosGeneralesAseguradoByNss(lstNss);
			if(lstResultado != null && !lstResultado.isEmpty())
				return lstResultado.get(0);
			else
				return null;
		}catch (Exception e) {
			log.error("ocurrio un error la consultar la infomración del asegurado en canase BDTI" + nss, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar la infomración del asegurado en canase BDTI", e.getMessage()),e);
		}
	}

	@Override
	public DatosGeneralesAseguradoMarcaAfiliatoria getMarcaAfiliatoria(
			String nss) throws ServiciosRestException {
		nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
		DatosGeneralesAseguradoMarcaAfiliatoria derechohabienteDTO = new DatosGeneralesAseguradoMarcaAfiliatoria();
		try {
			DatosGeneralesAseguradoCL cveIdPersonaCubetaDos = aseguradoEntity
					.getCubetaDosAseguradoByNss(nss);
			DatosGeneralesAseguradoCL cveIdPersonaCubetaUno = aseguradoEntity
					.getCubetaUnoAseguradoByNss(nss);
			DatosGeneralesAseguradoCL cveIdPersonaBaja = aseguradoEntity
					.getAseguradoBajaByNss(nss);
			boolean cubetaUno = cveIdPersonaCubetaUno != null;
			boolean cubetaDos = cveIdPersonaCubetaDos != null;
			boolean esBaja = cveIdPersonaBaja != null;
			if (cubetaUno || cubetaDos || esBaja) {
				if (cubetaUno) {
					derechohabienteDTO = ParserDatosPersonaToRest
							.setDatosDerechohabiente(cveIdPersonaCubetaUno);

				} else if (cubetaDos) {
					derechohabienteDTO = ParserDatosPersonaToRest
							.setDatosDerechohabiente(cveIdPersonaCubetaDos);

				} else if (esBaja) {
					derechohabienteDTO = ParserDatosPersonaToRest
							.setDatosDerechohabiente(cveIdPersonaBaja);

				}
				derechohabienteDTO.setCubeta(cubetaUno || cubetaDos);
				derechohabienteDTO.setBajaNss(esBaja);
				log.debug("regrese del servicio de vigencia con derechohabientes"
						+ nss);
			} else {
				throw new ServiciosRestException(new ErrorResponseBean(
						ErrorResponseBean.codigo404,
						ErrorResponseBean.codigo404Descripcion,
						"No se encontro  informacion de la adscipción del Asegurado con el NSS  "
								+ nss,
								"No se encontro informacion de la adscipción del Asegurado con el NSS "
										+ nss));
			}

		} catch (Exception e) {
			log.error(
					"ocurrio un error la consultar la infomración del asegurado en canase BDTI"
							+ nss, e);
			throw new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un error la consultar la infomración del asegurado en canase BDTI",
							e.getMessage()), e);
		}

		return derechohabienteDTO;
	}

	@Override
	public DerechohabienteSinolaveCovid getAseguradoVigenteSinolaveCovid(String nss, String curp)  throws ServiciosRestException {
		DerechohabienteSinolaveCovid aseguradoSinolaveCovid = new DerechohabienteSinolaveCovid();
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);

		boolean coincideCurp = true;
		try {

			/* Validacion datos del asegurado */
			AsignacionNSS asegurado = serviceBusiness.obtenerAseguradoPorNss(nss);
			MgtInfincasegvig vigenciaBDTU = vigenciaServiceEntity.getMgtInfincasegvig(nss);

			GrupoFamiliar grupoAsegurado = null;
			if(asegurado == null || vigenciaBDTU == null ) {
				log.debug("no se localizo en NSS en BDTU " + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encotro al informacion del Asegurado con el NSS " + nss , "No se ecnotro al informacion del Asegurado con el NSS " + nss ));
			}
			//validacion de CURP contra NSS y si no tiene o son diferentes ir a renapo para consultar la informacion de la persona
			if((asegurado.getCurp() == null || !asegurado.getCurp().equals(curp)) &&
					(vigenciaBDTU.getRefCurp() == null || !vigenciaBDTU.getRefCurp().equals(curp))){
				log.debug("no coincide la curp enviada con los daots en bdtu se valida contra renapo" + curp);
				Persona personaRenapoBdtu = consultaPersonaService.getPersonaServiciosDigitalesNSSRenapoCurp(curp, nss);
				if(asegurado.getFechaNacimiento() == null) {
					asegurado.setFechaNacimiento(personaRenapoBdtu.getFechaNacimiento());  
				}
				coincideCurp= false;
			}
			asegurado.setCveIdAsignacionNSS(asegurado.getIdAsignacionNSS());

			log.debug("voy a consultar al grupo familiar con NSS " + asegurado.getNss() );
			grupoAsegurado = grupoFamiliarService.getIntegranteGrupoFamiliarSinVigencia(asegurado.getIdAsignacionNSS(),
					asegurado.getIdPersona());
			try {

				DerechohabienteSinolave sinolave= 
						ParserDatosPersonaToRest.setDatosDerechohabienteSinolave(asegurado,vigenciaBDTU, grupoAsegurado );
				BeanUtils.copyProperties(sinolave, aseguradoSinolaveCovid, new String[]{"datosGeneralesPatron"});

				if(!coincideCurp)
					aseguradoSinolaveCovid.setCurp(curp);
			}catch (ServiciosRestException e) {
				throw e;
			}catch (Exception e) {
				log.error("ocurrio un error en el parser de setDatosDerechohabienteSinolave" ,e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error en el parser de setDatosDerechohabienteSinolave " +  nss  +" "+ e.getMessage() , e.getMessage()),e);
			}
			if(aseguradoSinolaveCovid.getRegistroPatronal() != null) {
				DatosGeneralesPatron datosPatron = patronServiciosDigitalesService.getDatosGeneralesPatron
						(aseguradoSinolaveCovid.getRegistroPatronal());
				if(datosPatron != null) {
					aseguradoSinolaveCovid.setDatosGeneralesPatron(datosPatron);
					if(datosPatron.getRfc() != null)				
						aseguradoSinolaveCovid.setDatosEmpresaPermisoConvid(
								patronServiciosDigitalesService.getDatosEmpresaPermisoCovid(datosPatron.getRfc()));
				}
			}

			return aseguradoSinolaveCovid;
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar los datos del asegurado sinolave covid" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar al asegurado getAseguradoVigenteSINOLAVE " +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}
	}

	@Override
	public AseguradoVigentePermisoCovid getAseguradoVigentePermisoCovid(String nss, String curp)  throws ServiciosRestException {
		AseguradoVigentePermisoCovid aseguradoSinolaveCovid = new AseguradoVigentePermisoCovid();
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);

		boolean coincideCurp = true;
		try {

			/* Validacion datos del asegurado */
			AsignacionNSS asegurado = serviceBusiness.obtenerAseguradoPorNss(nss);
			MgtInfincasegvig vigenciaBDTU = vigenciaServiceEntity.getMgtInfincasegvig(nss);

			GrupoFamiliar grupoAsegurado = null;

			ValidacionesComunesUtil.validaObjetoRespuestaNulo(asegurado, "No se encotro informacion del Asegurado con el NSS " + nss );
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(vigenciaBDTU, "No se encotro informacion del Asegurado con el NSS " + nss );

			asegurado.setCveIdAsignacionNSS(asegurado.getIdAsignacionNSS());
			log.debug("pase la consulta del asegurado voy por patrones vigentes");
			//validacion de CURP contra NSS y si no tiene o son diferentes ir a renapo para consultar la informacion de la persona
			if((asegurado.getCurp() == null || !asegurado.getCurp().equals(curp)) &&
					(vigenciaBDTU.getRefCurp() == null || !vigenciaBDTU.getRefCurp().equals(curp))){
				log.debug("no coincide la curp enviada con los datos en bdtu se valida contra renapo" + curp);
				try {
					Persona personaRenapoBdtu = consultaPersonaService.getPersonaServiciosDigitalesNSSRenapoCurp(curp, nss);
					if(asegurado.getFechaNacimiento() == null) {
						asegurado.setFechaNacimiento(personaRenapoBdtu.getFechaNacimiento());  
					}
					coincideCurp= false;
				}catch (Exception e) {
					throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
							"No concide la CURP capturada con la CURP asociada al NSS", "No concide la CURP capturada con la CURP asociada al NSS"));
				}

			}


			log.debug("voy a consultar al grupo familiar con NSS " + asegurado.getNss() );
			grupoAsegurado = grupoFamiliarService.getIntegranteGrupoFamiliarSinVigencia(asegurado.getIdAsignacionNSS(),
					asegurado.getIdPersona());
			try {
				DerechohabienteSinolave sinolave= 
						ParserDatosPersonaToRest.setDatosDerechohabienteSinolave(asegurado,vigenciaBDTU, grupoAsegurado );
				BeanUtils.copyProperties(sinolave, aseguradoSinolaveCovid, new String[]{"datosGeneralesPatron"});

				if(!coincideCurp)
					aseguradoSinolaveCovid.setCurp(curp);
			}catch (ServiciosRestException e) {
				throw e;
			}catch (Exception e) {
				log.error("ocurrio un error en el parser de setDatosDerechohabienteSinolave" ,e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error en el parser de setDatosDerechohabienteSinolave " +  nss  +" "+ e.getMessage() , e.getMessage()),e);
			}
			if(aseguradoSinolaveCovid.getRegistroPatronal() != null) {
				aseguradoSinolaveCovid.setLstDatosGeneralesPatron(patronServiciosDigitalesService.getDatosGeneralesPatronPernisoCovid(
						asegurado.getIdAsignacionNSS()));
			}
			return aseguradoSinolaveCovid;
		}catch (ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar los datos del asegurado sinolave covid" + nss , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar al asegurado getAseguradoVigenteSINOLAVE " +  nss +" "+ e.getMessage() , e.getMessage()),e);
		}
	}
	
	@Override
	public GrupoFamiliar getIntegranteGrupoFamiliarByCurp(String nss, String curp) throws ServiciosRestException {
		nss =ValidacionesComunesUtil.validaEstructuraNSS(nss);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);
		try {
			
			AsignacionNssDTO aseguradBDTU = aseguradoEntity.getDitAsignacionNSS(nss, false);
			if(aseguradBDTU == null ) {
				log.debug("no se encontro información con el NSS" + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro  informacion del Asegurado con el NSS " + nss , "No se encontro  informacion del Asegurado con el NSS " + nss ));
			}
			List<GrupoFamiliar> lstGrupoFamiliar = grupoFamiliarService.getIntegrantesPorCurp(aseguradBDTU.getCveIdAsignacionNSS().longValue(), curp, false);
			if(lstGrupoFamiliar != null && !lstGrupoFamiliar.isEmpty())
				return lstGrupoFamiliar.get(0);
			else
				return null; 
		}catch (Exception e) {
			log.error("ocurrio un error al consultar al integrante del grupo familiar nss " + nss + " curp " + curp,  e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar al integrante del grupo familiar nss " + nss + " curp " + curp+e.getMessage(), 
					"ocurrio un error al consultar al integrante del grupo familiar nss " + nss + " curp " + curp+e.getMessage()),e);
		}
	}


}
