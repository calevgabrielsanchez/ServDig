package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo.ICatalogoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IDomicilioServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserDomicilioGeograficoToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.Localidad;


@Stateless(name = "domicilioServiciosDigitalesService", mappedName = "domicilioServiciosDigitalesService")
public class DomicilioServiciosDigitalesService extends AbstractServiceBusiness implements IDomicilioServiciosDigitalesServiceRemote{
	
	private static final Logger log = LoggerFactory
            .getLogger(DomicilioServiciosDigitalesService.class);
	
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	@EJB
	private ICatalogoServiceEntityLocal catalogoServiceEntity;
	
	private static final String MSG_GENERAL_ERRROR_DOM = "Ocurrio un error en el servicio de domicilios de IMSS DIGITAL";
	
	

	@Override
	public Domicilio registraDomicilio(Domicilio domicilioRecortado) throws ServiciosRestException {
	
		mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio = this.validaComplementaDomicilioDelta(domicilioRecortado);
		try {
			
			domicilio =	domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
		}catch (Exception e) {
			log.error("ocurrio un error en registraDomicilio" , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocurrio un error al guardar el domiclio recorado en SD ");
		}
		domicilioRecortado.setIdDomicilio(domicilio.getClave().longValue());
		return domicilioRecortado;
	}

	@Override
	public Domicilio consultaDomicilio(Long cveDomicilioGeografico) throws ServiciosRestException {
		
		if(cveDomicilioGeografico == null) {
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"La clave del domicilio no puede ser nula", "La clave del domicilio no puede ser nula"));
		}
		mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta = new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();
		domicilioDelta.setClave(cveDomicilioGeografico.intValue());
		Domicilio domicilio = null;
		try {
			domicilioDelta =domicilioServiceBusinessRemote.consultarDomicilio(domicilioDelta);
			domicilio = ParserDomicilioGeograficoToRest.parserDomicilioDeltaToDomicilioDigital(domicilioDelta);
		}catch(Exception e){
			log.error("ocurrio un error en regisconsultaDomiciliotraDomicilio" , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "courrio un error al querer recuperar el domiclio en SD" + cveDomicilioGeografico );
		}
		
		return domicilio;
	}

	@Override
	public void actualizaDomicilio(Domicilio domicilioRecortado) throws ServiciosRestException {
		
		if(domicilioRecortado == null || domicilioRecortado.getIdDomicilio() == null) {
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El domicilio a actualizar debe contar con CLAVE", "El domicilio a actualizar debe contar con CLAVE"));
		}
		
		mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio = this.validaComplementaDomicilioDelta(domicilioRecortado);
		domicilio.setClave(domicilioRecortado.getIdDomicilio().intValue());
		try {
			domicilioServiceBusinessRemote.modificarDomicilio(domicilio);
		}catch (Exception e) {
			log.error("ocurrio un error en actualizaDomicilio" , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocurrio un error al querer modificar el domiclio recorado en SD" + domicilioRecortado.getIdDomicilio());
		}
		
		
	}
	
	
	private mx.gob.imss.ctirss.delta.model.domicilio.Domicilio  validaComplementaDomicilioDelta (Domicilio domicilioDig ) throws ServiciosRestException {
		
		mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio = null;
			if(domicilioDig == null)
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"El domicilio no puede ser nulo", "El domicilio no puede ser nulo"));
			
			try {
				//domicilio = domicilioServiceBusinessRemote.complementarLocalidadDomicilioRecortado(domicilioDig);
				domicilio = domicilioServiceBusinessRemote.complementarLocalidadDomicilioDigRecortado(domicilioDig);
			} catch (DomicilioNoValidoException e) {
				log.error("Ocurrio un ileegal argument exception domicilio" ,e);
				e.printStackTrace();
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"Los datos del domicilio son invalidos", e.getMessage()), e);
			} catch (DomicilioNoLocalizadoException e) {
				log.error("No se encontro la localizad" ,e);
				e.printStackTrace();
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"Los datos del domicilio son invalidos no se ubica la localidad", "Los datos del domicilio son invalidos no se ubica la localidad"), e);
			}
			return domicilio;
		}
	
	@Override
	public List<mx.gob.imss.digital.modelo.domicilio.Asentamiento> getAsentamientoPorCodigoPostal(String codigoPostal) throws ServiciosRestException{
		List<mx.gob.imss.digital.modelo.domicilio.Asentamiento> lstAsentamientosFinal = null;
		if(StringUtils.isEmpty(codigoPostal) || codigoPostal.length()!= 5) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El codigo postal no puede ser nulo o no cumple con la estructura ", "El codigo postal no puede ser nulo o no cumple con la estructura"));
		CodigoPostal cp = new CodigoPostal();
		cp.setCodigoPostal(codigoPostal);		
		try {
			List<Asentamiento> lstAsentamientos = catalogoServiceEntity.getAsentamientoPorCodigoPosta(codigoPostal);
			
			if(lstAsentamientos != null && !lstAsentamientos.isEmpty() ) {
				lstAsentamientosFinal = new ArrayList<mx.gob.imss.digital.modelo.domicilio.Asentamiento>();
				for(Asentamiento asen : lstAsentamientos) {
					asen.setCodigoPostal(cp);
					lstAsentamientosFinal.add(ParserDomicilioGeograficoToRest.parserAsentamientoDeltaToDigital(asen));
				}
			}
		}catch(DomicilioNoLocalizadoException e) {
			log.debug("no se encontraron asentamientos con el codigo postal ");
		}catch (Exception e) {
			log.debug("ocurio un error el metodo getAsentamientoPorCodigoPosta ", e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocuriro un error al consultar los asentamientos por CP" + codigoPostal);
			
		}
		
		return lstAsentamientosFinal;
	}
	
	
	@Override
	public mx.gob.imss.ctirss.delta.model.domicilio.Domicilio registraDomicilioDeltaInegi(
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta) throws ServiciosRestException {
		log.debug("llegue al servicio para guardar el domiclio inegi");
		ValidacionesComunesUtil.validaObjetoNulo(domicilioDelta, "EL objeto domicilio no puede ser nulo");
		try {
			domicilioDelta = domicilioServiceBusinessRemote.registrarDomicilio(domicilioDelta);
			log.debug("regrese de guardar el domicilio con id " + domicilioDelta.getClave());
		}catch (Exception e) {
			log.error("ocurrio un error en registraDomicilio Inegi" , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocurrio un error al guardar el domiclio ingegi en Serviciso Digitales");
		}
		
		return domicilioDelta;
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.domicilio.Domicilio consultaDomicilioNormaInegi(Long cveDomicilioGeografico)
			throws ServiciosRestException {
		ValidacionesComunesUtil.validaObjetoNulo(cveDomicilioGeografico, "La clave del domicilio no puede ser nula");
		try {
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta = new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();
			domicilioDelta.setClave(cveDomicilioGeografico.intValue());
			return domicilioServiceBusinessRemote.consultarDomicilio(domicilioDelta);
		}catch(Exception e){
			log.error("ocurrio un error en consultaDomiciliotraDomicilio" , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "courrio un error al querer recuperar el domiclio en SD" + cveDomicilioGeografico );
		}
		
		
		
	}

	@Override
	public List<Localidad> getLocalidadPorMunicipio(MunicipioInegi municipio) throws ServiciosRestException {
		List<mx.gob.imss.digital.modelo.domicilio.Localidad> lstLocalidadFinal = null;
		if(municipio == null || StringUtils.isEmpty(municipio.getCveEntidadFed()) || StringUtils.isEmpty(municipio.getCveMunicipio())) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El municipio no puede ser nulo o no cuenta con entidad federativa ", "El municipio no puede ser nulo o no cuenta con entidad federativa"));
		try {
			List<mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad> lstLocalidades = catalogoServiceEntity.getLocalidadesPorMunicipio(municipio);
			
			if(lstLocalidades != null && !lstLocalidades.isEmpty() ) {
				lstLocalidadFinal = new ArrayList<mx.gob.imss.digital.modelo.domicilio.Localidad>();
				for(mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad  loc : lstLocalidades) {
					lstLocalidadFinal.add(ParserDomicilioGeograficoToRest.parserLocalidadDeltaToDigital(loc));
				}
			}
		}catch (Exception e) {
			log.debug("ocurio un error el metodo getLocalidadPorMunicipio ", e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocuriro un error al consultar las localidades por municipio " 
					+ municipio.getCveMunicipio() + "entidad " + municipio.getCveEntidadFed());
			
		}
		return lstLocalidadFinal;
	}
	
	@Override
	public List<Localidad> getLocalidadPorCodigoPostal(String codigoPostal) throws ServiciosRestException{
	
		if(StringUtils.isEmpty(codigoPostal) || codigoPostal.length()!= 5) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El codigo postal no puede ser nulo o no cumple con la estructura ", "El codigo postal no puede ser nulo o no cumple con la estructura"));
		CodigoPostal cp = new CodigoPostal();
		cp.setCodigoPostal(codigoPostal);
		MunicipioInegi municipio = null;
		try {
			List<Asentamiento> lstAsentamientos = catalogoServiceEntity.getAsentamientoPorCodigoPosta(codigoPostal);
			if(lstAsentamientos != null && !lstAsentamientos.isEmpty()) {
				Asentamiento ase = lstAsentamientos.get(0);
				municipio = new MunicipioInegi();
				municipio.setCveMunicipio(ase.getMunicipio().getClave());
				municipio.setCveEntidadFed(ase.getMunicipio().getEntidadFederativa().getClave());
			}
			if(municipio != null){
				return getLocalidadPorMunicipio(municipio);
			}
		}catch (ServiciosRestException e) {
			log.error("error ya tratado getLocalidadPorCodigoPostal ", e);
			throw e;
		}catch (Exception e) {
			log.debug("ocurio un error el metodo getLocalidadPorCodigoPostal ", e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocuriro un error al consultar las localidades por  CP " + codigoPostal);
			
		}
		return null;
	}
		
	
}
