package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaFisicaMoral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaSua;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosHistoriaLaboral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.PersonaGruposFamilares;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ResolucionPension;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.asegurado.AseguradoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.patron.IPatronServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserDatosPersonaToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

@Stateless(name = "consultaPersonaService", mappedName = "consultaPersonaService")
public class ConsultaPersonaService extends AbstractServiceBusiness implements  IConsultaPersonaServiceRemote{

	private static final Logger log = LoggerFactory
			.getLogger(ConsultaPersonaService.class);

	@EJB( mappedName = "serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;

	@EJB(mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;

	@EJB(mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarService;


	@EJB(mappedName = "personaFisicaServiceBusiness")
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	@EJB(mappedName = "consultaServiciosExternosService")
	private IConsultaServiciosExternosServiceRemote consultaServiciosExternosService;

	@EJB(mappedName = "solicitudService")
	private SolicitudServiceRemote solicitudService;

	@EJB(mappedName = "serviciosPersonaBusiness")
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;

	@EJB(mappedName="individuoServiceBusiness")
	private IndividuoServiceBusinessRemote individuoServiceBusiness;
	@EJB(mappedName="representanteLegalServiceBusiness")
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;
	@EJB(mappedName = "personasAutorizadasService")
	private PersonasAutorizadasServiceRemote personasAutorizadasService;
	
	@EJB(name = "aseguradoServiciosDigitalesService")
	private IAseguradoServiciosDigitalesServiceRemote aseguradoSericeRest;

	@EJB
	private IPatronServiceEntityLocal patronServiceEntity;
	@EJB
	private AseguradoServiceEntityLocal aseguradoServiceEntity;
	
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

	private static final String MARCA_BAJA = "B";
	private static final int LONGITUD_RFC_PF = 13;
	@Override
	public DatosPersona getInfoPersonaServiciosDigitales(String curp) throws Exception

	{
		System.out.println("inicia la llamada");
		log.debug("LOGER entrando al servicio con CURP {} " , curp);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);

		DatosPersona objPersonaBdtu = new DatosPersona();

		Fisica personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);

		objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersonaRenapo(personaRenapo); 
		Fisica fisicaIMSS = null;

		try {
			fisicaIMSS = serviceBusiness.validacionesNSS(personaRenapo, false);			 
		}catch (PersonaConNSSException e) {
			fisicaIMSS = e.getFisica();
		}catch (Exception e) {
			log.debug("ocurrio un error al validar la  integridad de la persona", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
					"error al validar la informacion de la persona" + e.getMessage(), "error al validar la informacion de la persona" + e.getMessage()), e);
		}

		if(fisicaIMSS != null) {
			this.setDatosPersona(objPersonaBdtu, fisicaIMSS);
		}

		log.debug("inicia la llamada a servicio de pensiones");
		try {
			List<ResolucionPension> lstResulucionPension = null;
			lstResulucionPension = consultaServiciosExternosService.getConsultaInfoPension(curp);
			if(lstResulucionPension != null)
				objPersonaBdtu.setDatosPensionado(lstResulucionPension);

		}catch (ServiciosRestException e) {
			log.error("ocurrio un error al consulta la informaci�n de la pension" ,e);
			/*
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ucurrio un erro al consultar la informaci�n de la pension", "Ucurrio un erro al consultar la informaci�n no cachado"), e);
			 */
		}

		log.debug("ya me voy con el objeto" +objPersonaBdtu.toString());
		return objPersonaBdtu;
	}

	@Override
	public Persona getPersonaServiciosDigitalesNSSRenapoCurp(String curp, String nss ) throws ServiciosRestException{

		log.debug("LLOGER entrando al servicio con CURP {} " + curp);
		nss = ValidacionesComunesUtil.validaEstructuraNSS(nss);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);
		try {
			Fisica personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
			if(personaRenapo == null)
				throw new CURPNoLocalizadoEnEntidadExternaException("La CURP no se localizo en RENAPO");

			if(!StringUtils.isEmpty(personaRenapo.getCveEstatusRenapo()) && personaRenapo.getCveEstatusRenapo().substring(0,1).equals(MARCA_BAJA))
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
						"La CURP se encuentra en un estatus Baja", personaRenapo.getEstatusRenapo()));
			Persona objPersonaBdtu = new Persona();
			objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersona(personaRenapo, objPersonaBdtu); 
			AsignacionNSS asinganacion = grupoFamiliarService.getAsignacionNssSinPersona(nss, false);
			String formato = "dd/MM/yyyy";
			SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
			if(asinganacion.getFechaNacimiento()!= null)
				asinganacion.setFechaNacimiento(formatoFecha.parse(formatoFecha.format(asinganacion.getFechaNacimiento())));

			if(!personaFisicaServiceBusiness.comparaDatosBasicosAseguradoMesAnioNacRENAPO(personaRenapo, asinganacion))
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
						"Los datos estadisticos no coinciden entre RENAPO y BDTU", "Los datos estadisticos no coinciden entre RENAPO y BDTU"));


			if(asinganacion != null) {
				//se reemplaza la persona de renapo por la persona del IMSS
				objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersona(asinganacion, objPersonaBdtu);
				objPersonaBdtu.setNss(asinganacion.getNss());
				objPersonaBdtu.setCveIdPersona(asinganacion.getIdPersona());
				objPersonaBdtu.setRfc(asinganacion.getRfc());

				//se agregan seteos para datos que posiblemente pueden o no venir de la BDTU
				if(StringUtils.isEmpty(objPersonaBdtu.getCurp()))
					objPersonaBdtu.setCurp(personaRenapo.getCurp());
				if(objPersonaBdtu.getFechaNacimiento() == null)
					objPersonaBdtu.setFechaNacimiento(personaRenapo.getFechaNacimiento());
			}


			return  objPersonaBdtu;	

		}catch(ClienteWebserviceRenapoCurpException e) {
			System.out.println("error en el cliente de RENAPO" + e.getMessage());
			log.error("error en el cliente de RENAPO" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo503, ErrorResponseBean.codigo503Descripcion,
					"El servicio de renapo no esta disponible", "El servicio de renapo no esta disponible"), e);
		}catch(CURPNoLocalizadoEnEntidadExternaException e) {
			System.out.println("error CURP no localizada en RENAPO" + e.getMessage());
			log.error("error CURP no localizada en RENAPO", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"La CURP no se localizo en RENAPO", "La CURP no se localizo en RENAPO"), e);
		}catch(ErrorComparacionDatosRENAPOException e) {
			System.out.println("los datos de RENAPO no coinciden" + e.getMessage());
			log.error("los datos de RENAPO no coinciden" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"�	Los datos estad�sticos no coinciden entre RENAPO y BDTU, por favor verifica.", "�	Los datos estad�sticos no coinciden entre RENAPO y BDTU, por favor verifica."), e);
		} catch (DerechohabientesBusinessException e) {
			System.out.println("no se localizo el NSS" + e.getMessage());
			log.error("no se localizo el NSS" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El NSS no fue localizado en BDTU , por favor verifica.", "el NSS no fue localizado en BDTU , por favor verifica."), e);
		}catch (ServiciosRestException e){
			System.out.println("error eseprado de comparacion" );
			log.error("error esperado de comparacion" );
			throw e;
		} catch (Exception e) {
			System.out.println("error no cachado" + e.getMessage());
			log.error("error no cachado" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado", "Ocurrio un error inesperado"), e);
		} 


	}



	private void setDatosPersona(DatosPersona objPersonaBdtu , Fisica asegurado) {
		objPersonaBdtu.setNss(asegurado.getNss());
		objPersonaBdtu.setCveIdPersona(asegurado.getIdPersona());
		objPersonaBdtu.setRfc(asegurado.getRfc());

		if(objPersonaBdtu.getRfc()!= null) {
			try {
				if(asegurado.getIdPersona() != null){
					Fisica fisicaComplementaria = personaBusiness.getDatosComplementariosPersonaFisica(asegurado.getIdPersona());
					objPersonaBdtu.setDatosPersonaFisica(ParserDatosPersonaToRest.setDatosPersonaFisica(fisicaComplementaria));
				}

			}catch(Exception e) {
				System.out.println("error al buscar info de SAT" + e.getMessage());
				log.error("ocurrio un error al consultar la informaci�n de SAT de la perons {" +objPersonaBdtu.getRfc() +"}", e );
			}
		}
		DatosAsegurado datosAseg = null;
		if(objPersonaBdtu.getNss()!= null) {
			datosAseg = new DatosAsegurado();
			datosAseg.setNss(asegurado.getNss());
			datosAseg.setCveIdAsignacionNss(asegurado.getCveIdAsignacionNSS());
			System.out.println("el idAsignacion que viene del servicio de fisica ["+ asegurado.getCveIdAsignacionNSS()+ "]");
			/*
			try {
			GrupoFamiliar gpoFam = grupoFamiliarService.getGrupoFamiliar(objPersonaBdtu.getNss(), true);

			System.out.println("se recupera el objeto asignacion ["+ datosAseg.getCveIdAsignacionNss()+ "]");
			datosAseg.setDatosGrupoFamiliar(this.setDatosGrupoFamiliar(gpoFam));
			}catch (Exception e) {
				System.out.println("error al buscar grupo familiar del asegurado" + e.getMessage());
				log.error("ocurrio un error al consultar la info del grupo familiar de asegurado NSS [" +objPersonaBdtu.getNss()+ "]", e);
			}
			 */

			try {
				List<GrupoFamiliar> lstGrupoFam = grupoFamiliarService.findGrupoFamiliar(datosAseg.getCveIdAsignacionNss());
				if(lstGrupoFam != null && !lstGrupoFam.isEmpty()) {
					ArrayList<DatosGrupoFamiliar> lstDatosIntegrantes = new ArrayList<DatosGrupoFamiliar>();
					//DatosGrupoFamiliar lstDatosIntegrantes [] =  new DatosGrupoFamiliar[lstGrupoFam.size()];
					//	int contador = 0;
					for(GrupoFamiliar integrante : lstGrupoFam) {
						try {
							if(integrante.getParentesco().getIdParentesco().longValue() != ParentescoEnum.ASEGURADO.getId()
									&& integrante.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
								lstDatosIntegrantes.add(ParserDatosPersonaToRest.setDatosGrupoFamiliar(integrante));
							}

							//		lstDatosIntegrantes[contador] =ParserDatosPersonaToRest.setDatosGrupoFamiliar(integrante);
							//		contador ++;

						}catch(Exception e) {
							System.out.println("error al setear los grupos familares del los beneficiarios " + e.getMessage());
							log.error("ocurrio un error al setear los grupos familiares del beneficiarios " + datosAseg.getCveIdAsignacionNss(), e);
						}
					}
					datosAseg.setDatosGrupoFamiliarBeneficiarios(lstDatosIntegrantes);
				}
			}catch(Exception e) {
				System.out.println("error al buscar grupo familiar de beneficiarios" + e.getMessage());
				log.error("ocurrio un error al consultar a los integrantes del grupo familiar de asegurado cveIdAsignacion [" +
						datosAseg.getCveIdAsignacionNss()+ "]", e);
			}

			try {
				AsignacionNSS asignacion = new AsignacionNSS();

				asignacion.setIdAsignacionNSS(asegurado.getCveIdAsignacionNSS());

				//System.out.println("el idAsignacion que viene en el asignacion es  ["+ asignacion.getIdAsignacionNSS()+ "]JC CHARLY");
				List<SujetoObligado> lstPatronesVigentes =  grupoFamiliarService.getPatronesAsegurado(asignacion);
				ArrayList<DatosHistoriaLaboral> historiaList  =null;
				if(lstPatronesVigentes != null && !lstPatronesVigentes.isEmpty()) {
					historiaList  =new ArrayList<DatosHistoriaLaboral>();
					System.out.println(" la lista de patrones no es nula " + lstPatronesVigentes.size());
					for (SujetoObligado patron : lstPatronesVigentes) {
						System.out.println(" iterando a los patrones  " + patron.getNumeroRegistroPatronal() );
						try {
							DatosHistoriaLaboral historiaLab  = ParserDatosPersonaToRest.setDatosHistoriaLaboral(patron);
							historiaLab.setPatronVigente(1L);
							historiaList.add(historiaLab);
						}catch(Exception e) {
							System.out.println("error al setear los patrones " + e.getMessage());
							log.error("ocurrio un error al setear los patrones " + patron.getNumeroRegistroPatronal() , e);

						};
					}
					System.out.println(" la lista de historia laboral quedo como " + historiaList.get(0).getNumeroRegistroPatronal());
					datosAseg.setDatosHistoriaLaboral(historiaList);
				}
			}catch(Exception e) {
				System.out.println("error al buscar patrones vigentes" + e.getMessage());
				log.error("ocurrio un error al consultar a los patrones vigentes del asegurado cveIdAsignacion [" +
						datosAseg.getCveIdAsignacionNss()+ "]", e);
			}
			objPersonaBdtu.setDatosAsegurado(datosAseg);
		}
		if(asegurado.getIdPersona() != null) {
			try {
				List<GrupoFamiliar> lstGrupo =  grupoFamiliarService.getGruposFamiliaresPorPersona(asegurado.getIdPersona(), true);
				if(lstGrupo != null && !lstGrupo.isEmpty()) {
					List<DatosGrupoFamiliar> lstDatosGrupo = new ArrayList<DatosGrupoFamiliar>();
					for(GrupoFamiliar grupo: lstGrupo) { 
						try {
							if((grupo.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId()
									|| grupo.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId())
									&& datosAseg != null) {
								datosAseg.setDatosGrupoFamiliar(ParserDatosPersonaToRest.setDatosGrupoFamiliar(grupo));
							}else {
								lstDatosGrupo.add(ParserDatosPersonaToRest.setDatosGrupoFamiliar(grupo));
							}
						}catch(Exception e) {
							System.out.println("error al setear los grupos familares del aseguradp " + e.getMessage());
							log.error("ocurrio un error al setear los grupos familiares del asegurado " + asegurado.getIdPersona(), e);
						}
					}
					objPersonaBdtu.setDatosGrupoFamiliarBeneficiario(lstDatosGrupo);
				}
			}catch(Exception e) {
				log.error("ocurrio un error al consultar la info de los grupo familares de la persona con IDPersona [" +objPersonaBdtu.getNss()+"]", e);
			}
		}
	}


	/**
	 * Metodo que busca la informaci�n de un beneficiario incluyendo su vigencia y datos generales
	 * @param cveIdAsignacionNSS
	 * @param cveIdPersona
	 * @return GrupoFamiliar
	 * @throws ServiciosRestException
	 */
	@Override
	public DerechohabienteDTO getIntegranteGrupoFamiliar(Long cveIdAsignacionNSS, Long cveIdPersona) throws ServiciosRestException{

		try {
			GrupoFamiliar integrante = grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(cveIdAsignacionNSS, cveIdPersona);
			if(integrante == null )
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se ecnotro al beneficiario con idAsignacion " + cveIdAsignacionNSS +
						" y cveIdPersona" + cveIdPersona , null));
			return ParserDatosPersonaToRest.setDatosDerechohabiente(integrante);
		}catch (ServiciosRestException e) {
			throw  e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar al integrante del grupo familiar idAsignacion " 
					+ cveIdAsignacionNSS  + " idPersona " +  cveIdPersona, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error la consultar al beneficiario", e.getMessage()),e);
		}

	}

	@Override
	public PersonaSua getPersonaSuaRenapoComplementoSD(String curp, boolean indConsultaRegistroProtalFiel, boolean indConsultaCorreoElectronico) 
			throws ServiciosRestException{
		log.debug("log entrando al servicio con CURP {} " + curp);
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);

		Fisica personaTramiteSD = null;
		PersonaSua personaSua = new PersonaSua();
		personaSua.setUsuarioRegistradoPortalFiel(false);
		try {

			Fisica personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
			if(personaRenapo == null)
				throw new CURPNoLocalizadoEnEntidadExternaException("La CURP no se localizo en RENAPO");

			if(!StringUtils.isEmpty(personaRenapo.getCveEstatusRenapo()) && personaRenapo.getCveEstatusRenapo().substring(0,1).equals(MARCA_BAJA))
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
						"La CURP se encuentra en un estatus Baja", personaRenapo.getEstatusRenapo()));
			Persona objPersonaBdtu = new Persona();

			objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersona(personaRenapo, objPersonaBdtu);

			if(indConsultaRegistroProtalFiel) {			
				personaTramiteSD = null;//personaBusiness.getFisicaBySolicitudRegistroPortalConFiel(curp);
				if(personaTramiteSD!= null) {
					objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersona(personaTramiteSD, objPersonaBdtu);
					personaSua.setUsuarioRegistradoPortalFiel(true);
				}
			}
			BeanUtils.copyProperties(objPersonaBdtu, personaSua);

			if(personaSua.getNss() == null) {
				List<Fisica> lstPersonaBDTU = personaBusiness.buscarPersonaFisicaPorCurpEnImss(curp);
				if(lstPersonaBDTU != null && !lstPersonaBDTU.isEmpty()) {
					for(Fisica fisicaBdtu: lstPersonaBDTU ) {
						if(fisicaBdtu.getNss()!= null)
							if(!personaSua.isUsuarioRegistradoPortalFiel()) {
								objPersonaBdtu = ParserDatosPersonaToRest.setDatosPersona(fisicaBdtu, objPersonaBdtu);
								BeanUtils.copyProperties(objPersonaBdtu, personaSua);
							}
						personaSua.setNss(fisicaBdtu.getNss());
						break;
					}
				}
			}
			if(indConsultaCorreoElectronico) {
				/**
			List<MedioContacto> mediosContacto = consultarMedioDeContactoPersona(persona);

			for (MedioContacto medio : mediosContacto) {
				if (medio instanceof CorreoElectronico) {
					contadorCorreos++;
				}
			}**/
			}
			return  personaSua;	

		}catch(ClienteWebserviceRenapoCurpException e) {

			log.error("error en el cliente de RENAPO" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo503, ErrorResponseBean.codigo503Descripcion,
					"El servicio de renapo no esta disponible", "El servicio de renapo no esta disponible"), e);
		}catch(CURPNoLocalizadoEnEntidadExternaException e) {

			log.error("error CURP no localizada en RENAPO", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"La CURP no se localizo en RENAPO", "La CURP no se localizo en RENAPO"), e);

		}catch (ServiciosRestException e){
			log.error("error esperado de comparacion" );
			throw e;
		} catch (Exception e) {
			log.error("error no cachado" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado", "Ocurrio un error inesperado"), e);
		} 

	}

	@Override
	public PersonaGruposFamilares getPersonaGruposFamilares(String curp)throws ServiciosRestException{
		log.debug("llegue al metodo de  getPersonaGruposFamilares con CURP" + curp );
		ValidacionesComunesUtil.validaEstructuraCurp(curp);
		PersonaGruposFamilares personaGrupo = new PersonaGruposFamilares() ;

		try {
			Fisica personaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
			if(personaRenapo == null)
				throw new CURPNoLocalizadoEnEntidadExternaException("La CURP no se localizo en RENAPO");
			List<Fisica> lstPersonaBDTU = personaBusiness.buscarPersonaFisicaPorCurpEnImss(curp);
			if(lstPersonaBDTU!= null && !lstPersonaBDTU.isEmpty()) {
				List<GrupoFamiliar> lstGrupo = new ArrayList<GrupoFamiliar>();
				for(Fisica fiscaBdtu : lstPersonaBDTU) {
					List<GrupoFamiliar> lstGrupoTmp = null;
					lstGrupoTmp = grupoFamiliarService.getGruposFamiliaresPorPersona(fiscaBdtu.getIdPersona(), null,null);
					if(lstGrupoTmp != null && !lstGrupoTmp.isEmpty())
						lstGrupo.addAll(lstGrupoTmp);
				}

				personaGrupo =(PersonaGruposFamilares) ParserDatosPersonaToRest.setDatosPersona(lstPersonaBDTU.get(0), personaGrupo);
				ArrayList<DerechohabienteDTO> lstDerechohabienteDTO = new ArrayList<DerechohabienteDTO>();
				for(GrupoFamiliar integrante : lstGrupo) {
					try {
						lstDerechohabienteDTO.add(ParserDatosPersonaToRest.setDatosDerechohabiente(integrante));
					}catch(Exception e) {
						log.error("ocurrio un error al setear los grupos familiares del beneficiarios " , e);
					}
				}
				personaGrupo.setListDerechohabienteDTO(lstDerechohabienteDTO);

			}else
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro informacion en el IMSS con la CURP " + curp,"No se encontro informacion en el IMSS con la CURP " + curp ));
			Persona objPersonaBdtu = new Persona();

		}catch(ClienteWebserviceRenapoCurpException e) {
			log.error("error en el cliente de RENAPO" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo503, ErrorResponseBean.codigo503Descripcion,
					"El servicio de renapo no esta disponible", "El servicio de renapo no esta disponible"), e);
		}catch(ServiciosRestException e) {
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error no identificado al consultar a la persona" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, 
					ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error no especificado en la consulta de la persona " + e.getMessage(), 
					"Ocurrio un error no especificado en la consulta de la persona " + e.getMessage()),e);
		}
		return personaGrupo;

	}

	@Override
	public PersonaFisicaMoral getPersonaFisicaMoralByRfc(String rfc) throws ServiciosRestException {
		rfc = ValidacionesComunesUtil.validaEstructuraRfc(rfc);
		try {
			mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona persona = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
			persona.setRfc(rfc);
			if (rfc.length() == LONGITUD_RFC_PF) {
				return ParserDatosPersonaToRest.setDatosPersonaFisicaCompleto(
						individuoServiceBusiness.consultarPersonaFisicaIMSSPorRFC(persona));
			}else {
				return ParserDatosPersonaToRest.setDatosPersonaMoral(
						individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(persona));
			}
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a la persona fisica o moral " + rfc, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a la persona fisica o moral " + rfc +" " +e.getMessage(), 
					"ocurrio un error al consultar a la persona fisica o moral " + rfc +" " +e.getMessage()),e);
		}
	}

	@Override
	public List<RepresentanteLegal> getRepresentanteLegal(Long cveIdPersona, Long cveIdTipoPersona)
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaObjetoNulo(cveIdPersona, "el cveIdPersona no puede ser nulo");
		ValidacionesComunesUtil.validaObjetoNulo(cveIdTipoPersona, "el cveIdTipoPersona no puede ser nulo");
		try {
			TipoPersonaEnum tipoPersona = null;

			if (TipoPersonaEnum.FISICA.getId() == cveIdTipoPersona.longValue()) {
				tipoPersona = TipoPersonaEnum.FISICA;
			} else {
				tipoPersona = TipoPersonaEnum.MORAL;
			}
			return representanteLegalServiceBusiness.obtenerRepresentantesLegalesPorPersona(cveIdPersona, tipoPersona);
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a los representantes legales " + cveIdPersona, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a los representantes legales " + cveIdPersona +" " +e.getMessage(), 
					"ocurrio un error al consultar a los representantes legales " + cveIdPersona +" " +e.getMessage()),e);
		}
	}

	@Override
	public List<mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona> getPersonaRepresentada(Long cveIdPersona)
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaObjetoNulo(cveIdPersona, "el cveIdPersona no puede ser nulo");
		try {
			return representanteLegalServiceBusiness.obtenerPersonasRepresentadasPorRepresentanteLegal(cveIdPersona);
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas representadas" + cveIdPersona, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a las personas representadas " + cveIdPersona +" " +e.getMessage(), 
					"ocurrio un error al consultar a las personas representadas " + cveIdPersona +" " +e.getMessage()),e);
		}
	}

	@Override
	public List<PersonaAutorizada> getPersonaAutorizada(Long cveIdPersona, Long cveIdTipoPersona)
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaObjetoNulo(cveIdPersona, "el cveIdPersona no puede ser nulo");
		ValidacionesComunesUtil.validaObjetoNulo(cveIdTipoPersona, "el cveIdTipoPersona no puede ser nulo");
		try {
			mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona persona = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
			persona.setIdPersona(cveIdPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(cveIdTipoPersona);
			persona.setTipoPersona(tipoPersona);
			return personasAutorizadasService.getPersonasAutorizadasByPersona(persona);
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas autorizadas " + cveIdPersona, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a las personas autorizadas " + cveIdPersona +" " +e.getMessage(), 
					"ocurrio un error al consultar a las personas autorizadas " + cveIdPersona +" " +e.getMessage()),e);
		}
	}


	@Override
	public List<String> getRfcPersonaRepresentadaByRfc(String rfc) throws ServiciosRestException{
		rfc = ValidacionesComunesUtil.validaEstructuraRfc(rfc);
		try {
			return patronServiceEntity.getRfcPersonaRepresentadaByRfc(rfc);
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas representadas por rfc " + rfc, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar las empresas representadas por rfc " + rfc +" " +e.getMessage(), 
					"ocurrio un error al consultar las empresas representadas por rfc " + rfc +" " +e.getMessage()),e);
		}
	}

	@Override
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliares(String curp, boolean conVigencia)
			throws ServiciosRestException {
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);

		try {
			List<DatosBasicosPersonaGrupoFamiliar> lstGrupos = aseguradoServiceEntity.getDatosBasicosPersonaGruposFamiliares(curp);
			ValidacionesComunesUtil.validaListaNulaVacia(lstGrupos, "No se encontro informacion con la CURP enviada " + curp);
			if(!conVigencia)
				return lstGrupos;
			List<DatosBasicosPersonaGrupoFamiliar> lstGruposVigencia = new ArrayList<DatosBasicosPersonaGrupoFamiliar>();
			for(DatosBasicosPersonaGrupoFamiliar integrante: lstGrupos) {
				log.debug("\n Se buscara al integrante con los datos de la bdtu en el ws: \n - IdAsignacionNss:  " + integrante.getCveIdAsignacionNss() + 
						"\n  IdPersona: " + integrante.getCveIdPersona());
				try {
					if(integrante.getCveIdAsignacionNssGrupo()!= null) {
						GrupoFamiliar integranteAux = grupoFamiliarService.getVigenciaYservicioMedicoWsIntegranteGf(
								integrante.getNumNssGrpoFamiliar(), integrante.getCveIdAsignacionNssGrupo().longValue()
								,integrante.getCveIdPersona().longValue());
						if(integranteAux != null) {
							integrante.setEstadoDerechohabiente(integranteAux.getEstadoDerechohabiente());
							EstadoDerechohabienteEnum estadiEnum = 
									EstadoDerechohabienteEnum.getById(integranteAux.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
							integrante.getEstadoDerechohabiente().setDescripcion(estadiEnum.name());
							if(integranteAux.getAgregadoMedico() != null)
								integrante.setRefAgregadoMedico(integranteAux.getAgregadoMedico());
							log.debug("con derecho " + integranteAux.getConDerechoSm());
							log.debug("el objeto es  " + integranteAux);
							integrante.setDerechoServicioMedico(integranteAux.getConDerechoSm());
							lstGruposVigencia.add(integrante);
						}
					}else {
						CabezaGrupoFamiliar cabeza = grupoFamiliarService.getCabezaWS(integrante.getCveIdAsignacionNssGrupo().longValue());
						if(cabeza != null) {
							integrante.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
							EstadoDerechohabienteEnum estadiEnum = 
									EstadoDerechohabienteEnum.getById(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
							integrante.getEstadoDerechohabiente().setDescripcion(estadiEnum.name());
							integrante.setDerechoServicioMedico(cabeza.getConDerechoSm());
							lstGruposVigencia.add(integrante);
						}
					}
				} catch(Exception e) {
					log.error("OCurrio un error al consultar la vigencia por derechohabiente:  " + integrante.getCveIdAsignacionNss() + 
							"\n  IdPersona: " + integrante.getCveIdPersona(), e);
					lstGruposVigencia.add(integrante);
				}
			}
			ValidacionesComunesUtil.validaListaNulaVacia(lstGruposVigencia, "No se encontro informacion con la CURP enviada " + curp);
			return lstGruposVigencia;
		}catch(ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas getDatosBasicosPersonaGruposFamiliares por CURP" + curp, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar la informacion del la persona y sus grupos familares " + curp +" " +e.getMessage(), 
					"ocurrio un error al consultar la informacion del la persona y sus grupos familares " + curp +" " +e.getMessage()),e);
		}
	}

	@Override
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliaresByIdee(String idee,
			boolean conVigencia) throws ServiciosRestException {
		ValidacionesComunesUtil.validaStringNuloOVacio(idee, "el idee no puede ser nulo o vacio");
		try {
			List<DatosBasicosPersonaGrupoFamiliar> lstGrupos = aseguradoServiceEntity.getDatosBasicosPersonaGruposFamiliaresByIdee(idee);
			ValidacionesComunesUtil.validaListaNulaVacia(lstGrupos, "No se encontro informacion con el IDEE enviado " + idee);
			if(!conVigencia)
				return lstGrupos;
			List<DatosBasicosPersonaGrupoFamiliar> lstGruposVigencia = new ArrayList<DatosBasicosPersonaGrupoFamiliar>();
			for(DatosBasicosPersonaGrupoFamiliar integrante: lstGrupos) {
				log.debug("\n Se buscara al integrante con los datos de la bdtu en el ws: \n - IdAsignacionNss:  " + integrante.getCveIdAsignacionNss() + 
						"\n  IdPersona: " + integrante.getCveIdPersona());
				try {
					if(integrante.getCveIdAsignacionNssGrupo()!= null) {
						GrupoFamiliar integranteAux = grupoFamiliarService.getVigenciaYservicioMedicoWsIntegranteGf(
								integrante.getNumNssGrpoFamiliar(), integrante.getCveIdAsignacionNssGrupo().longValue()
								,integrante.getCveIdPersona().longValue());
						if(integranteAux != null) {
							integrante.setEstadoDerechohabiente(integranteAux.getEstadoDerechohabiente());
							EstadoDerechohabienteEnum estadiEnum = 
									EstadoDerechohabienteEnum.getById(integranteAux.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
							integrante.getEstadoDerechohabiente().setDescripcion(estadiEnum.name());
							if(integranteAux.getAgregadoMedico() != null)
								integrante.setRefAgregadoMedico(integranteAux.getAgregadoMedico());
							log.debug("con derecho " + integranteAux.getConDerechoSm());
							log.debug("el objeto es  " + integranteAux);
							integrante.setDerechoServicioMedico(integranteAux.getConDerechoSm());
							lstGruposVigencia.add(integrante);
						}
					}else {
						CabezaGrupoFamiliar cabeza = grupoFamiliarService.getCabezaWS(integrante.getCveIdAsignacionNssGrupo().longValue());
						if(cabeza != null) {
							integrante.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
							EstadoDerechohabienteEnum estadiEnum = 
									EstadoDerechohabienteEnum.getById(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
							integrante.getEstadoDerechohabiente().setDescripcion(estadiEnum.name());
							integrante.setDerechoServicioMedico(cabeza.getConDerechoSm());
							lstGruposVigencia.add(integrante);
						}
					}
				} catch(Exception e) {
					log.error("Ocurrio un error al consultar la vigencia por derechohabiente:  " + integrante.getCveIdAsignacionNss() + 
							"\n  IdPersona: " + integrante.getCveIdPersona(), e);
					lstGruposVigencia.add(integrante);
				}
			}
			ValidacionesComunesUtil.validaListaNulaVacia(lstGruposVigencia, "No se encontro informacion con el IDEE enviado " + idee);
			return lstGruposVigencia;
		}catch(ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas getDatosBasicosPersonaGruposFamiliaresByIdee por IDEE " + idee, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar la informacion del la persona y sus grupos familares por idee " + idee +" " +e.getMessage(), 
					"ocurrio un error al consultar la informacion del la persona y sus grupos familares por idee " + idee +" " +e.getMessage()),e);
		}

	}

	@Override
	public DatosBasicosPersonaGfHistLab getDatosBasicosPersonaAseguradoGFHistLab(ConsultaPersonaGfHistLab personaBusqueda)
			throws ServiciosRestException {
		boolean isBusquedaCurp = false;
		boolean isBusquedaRfc = false;
		boolean isBusquedaNss = false;
		boolean isBusquedaVacia = true;
		DatosBasicosPersonaGfHistLab datos = null;
		if(!ValidacionesComunesUtil.isStringNuloVacio(personaBusqueda.getRefCurp()))
			isBusquedaCurp =true;
		if(!ValidacionesComunesUtil.isStringNuloVacio(personaBusqueda.getRefRfc()))
			isBusquedaRfc =true;
		if(!ValidacionesComunesUtil.isStringNuloVacio(personaBusqueda.getNumNss()))
			isBusquedaNss =true;
		if(isBusquedaCurp || isBusquedaRfc || isBusquedaNss)
			isBusquedaVacia = false;
		if(isBusquedaVacia)
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"Todos los parametros de busqueda son nulos o vacios", "Todos los parametros de busqueda son nulos o vacios"));
		if(isBusquedaCurp)
			personaBusqueda.setRefCurp(ValidacionesComunesUtil.validaEstructuraCurp(personaBusqueda.getRefCurp()));
		if(isBusquedaRfc)
			personaBusqueda.setRefRfc(ValidacionesComunesUtil.validaEstructuraRfc(personaBusqueda.getRefRfc()));
		if(isBusquedaNss)
			personaBusqueda.setNumNss(ValidacionesComunesUtil.validaEstructuraNSS(personaBusqueda.getNumNss()));
		try {
			log.debug("pase las validaciones de la busqueda");
			datos = aseguradoServiceEntity.getDatosBasicosPersonaAseguradoGF(personaBusqueda);
			ValidacionesComunesUtil.validaObjetoRespuestaNulo(datos, "No se encontro informacion con los parametros de busqueda");
			if(datos.getCveIdAsignacionNss() != null) {
				try {
				log.debug("se va a consultar la info de la cabeza gf " + datos.getCveIdAsignacionNss());
				CabezaGrupoFamiliar cgf = grupoFamiliarService.cabezaGrupoFamiliar(datos.getCveIdAsignacionNss().longValue());
				if(cgf != null) {
					ParentescoEnum parentesco = ParentescoEnum.obternerEnumById(cgf.getCalidadParentesco().getIdParentesco());
					datos.setCalidad(parentesco.name());
					datos.setEstadoDerechohabiente(cgf.getEstadoDerechohabiente().getDescripcion());
					datos.setDerechoServicioMedico(cgf.getConDerechoSm());
					datos.setFechaUltimoMovAfiliacion(cgf.getFechaUltimoMovAfiliacion());
					datos.setTipoMvtoAsegurado(cgf.getTipoMovtoAsegurado().getDesTipoMvtoAsegurado());
					if(cgf.getPatronSujetoObligado() != null)
						datos.setRegistroPatronal(cgf.getPatronSujetoObligado().getNumeroRegistroPatronal()+
								cgf.getPatronSujetoObligado().getModalidad().getNumModalidad() +
								cgf.getPatronSujetoObligado().getDigVerificador());
				}
				
				}catch(Exception e) {
					log.error("error al consultar el ws cabeza gf" + datos.getCveIdAsignacionNss() , e);
					throw e;
				}

			}
			if(datos.getClavePresupuestal() !=null) {
				try {
					log.debug("se va a consultar la informacion en WS de vigencia por NSS " + datos.getNumNssPersona());
					GrupoFamiliar gf = grupoFamiliarService.getInfoAsegurado(datos.getNumNssPersona());
					if(gf != null) {
						datos.setTipoPension(gf.getAsignacionNSS().getTipoPension());
						datos.setCveDelegacion(gf.getMedicoEnTurno().getUnidadMedicaFamiliar()
								.getSubdelegacion().getDelegacion().getDescripcion());
						datos.setCveUmf(gf.getMedicoEnTurno().getUnidadMedicaFamiliar()
								.getDescripcion());
					}
				}catch(Exception e) {
					log.error("error al consultar el ws de info por nss en almacenes " + datos.getNumNssPersona() , e);
					throw e;
				}
			}
			if(datos.getDomicilioId() != null) {
				try {
					Domicilio domicilioDelta = new Domicilio();
					domicilioDelta.setClave(datos.getDomicilioId().intValue());
					domicilioDelta =domicilioServiceBusinessRemote.consultarDomicilio(domicilioDelta);
					datos.setDomicilio(domicilioDelta);
				}catch(Exception e) {
					log.error("error al consultar el domicilio de la persona con idDomicilio " + datos.getDomicilioId() , e);
					throw e;
				}
			}
			if(personaBusqueda.isConsultaHistoriaLaboral()) {
				log.debug("se va a consultar la historia laboral " + datos.getNumNssPersona());
				try {
				datos.setLstCuentaIndividual(
						aseguradoSericeRest.consultarMovimientosCuentaIndividual(datos.getNumNssPersona())
						);
				}catch(Exception e) {
					log.error("error al consultar la cienta individial" + datos.getNumNssPersona() , e);
					throw e;
				}
			}
			return datos;
		}catch(ServiciosRestException e) {
			log.error("error de negocio ya manejado" , e);
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a las personas getDatosBasicosPersonaAseguradoGF  " + personaBusqueda, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar la informacion del la persona " + personaBusqueda +" " +e.getMessage(), 
					"ocurrio un error al consultar la informacion del la persona " + personaBusqueda +" " +e.getMessage()),e);

		}
	}

}
