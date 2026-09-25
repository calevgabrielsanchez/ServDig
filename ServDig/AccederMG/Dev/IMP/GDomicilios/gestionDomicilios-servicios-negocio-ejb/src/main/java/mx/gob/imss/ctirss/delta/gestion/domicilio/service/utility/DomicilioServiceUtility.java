/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamientoPK;
import mx.gob.imss.ctirss.delta.persistence.DgCatAdministracion;
import mx.gob.imss.ctirss.delta.persistence.DgCatAmbito;
import mx.gob.imss.ctirss.delta.persistence.DgCatDerechosTransito;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidadPK;
import mx.gob.imss.ctirss.delta.persistence.DgCatMargen;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;
import mx.gob.imss.ctirss.delta.persistence.DgCatTermGen;
import mx.gob.imss.ctirss.delta.persistence.DgCatTipoAsen;
import mx.gob.imss.ctirss.delta.persistence.DgCatTipoDom;
import mx.gob.imss.ctirss.delta.persistence.DgCatVialidad;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostalePK;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCamino;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCarretera;
import mx.gob.imss.ctirss.delta.persistence.DgVialidad;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitDomicilioSat;

import org.apache.commons.lang.StringUtils;

/**
 * @author vanderluk
 *
 */
@Stateless
public class DomicilioServiceUtility   extends AbstractServiceUtility implements DomicilioServiceUtilityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.DomicilioServiceUtilityLocal#transformarAsentamiento(mx.gob.imss.ctirss.delta.persistence.DgAsentamiento)
	 */
	@Override
	public Asentamiento transformarAsentamiento(DgAsentamiento entity)
			throws TransformacionException { 
		
		
		if( entity == null){
			throw new TransformacionException();
		}
		
		Asentamiento asentamiento = new Asentamiento();
		Localidad localidad = new Localidad();
		Municipio municipio = new Municipio();
		EntidadFederativa entidadFed = new EntidadFederativa();
		TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
		
		asentamiento.setClave(entity.getId().getCveAsen());
		asentamiento.setNombre(entity.getNomAsen());
		
		/*TODO  SE COMENTA LA SECCION DE LOCALIDA YA QUE SE SEPARO LA ENTIDAD
		DgCatLocalidad dgLocalidad = entity.getDgCatLocalidad();
		localidad.setClave(dgLocalidad.getId().getCveLoc());
		localidad.setNombre(entity.getNomAsen());
		**/
		
		
		DgCatMunicipio dgMunicipio = entity.getDgCatMunicipio();
		municipio.setClave(dgMunicipio.getId().getCveMun());
		municipio.setNombre(dgMunicipio.getNomMun());
		
		
		DgCatEstado dgEstado = dgMunicipio.getDgCatEstado();
		entidadFed.setClave(dgMunicipio.getId().getCveEnt());
		entidadFed.setNombre(dgEstado.getNomEnt());
		
		
		localidad.setMunicipio(municipio); 
		municipio.setEntidadFederativa(entidadFed);
		asentamiento.setLocalidad(localidad);
		
		
		DgCatTipoAsen dgTipoAsent = entity.getDgCatTipoAsen();
		tipoAsentamiento.setClave(dgTipoAsent.getCveTipoAsen());
		tipoAsentamiento.setDescripcion(dgTipoAsent.getNombre());
		
		asentamiento.setTipoAsentamiento(tipoAsentamiento);
		
		
		return asentamiento;
	}
	
	@Override
	public Localidad transformarLocalidad(DgCatLocalidad entity) throws TransformacionException{
		
		if( entity == null){
			throw new TransformacionException();
		}
		DgCatLocalidad dgLocalidad = entity;
		
		Localidad localidad = new Localidad();
		Municipio municipio = new Municipio();
		EntidadFederativa entidadFed = new EntidadFederativa();
		
		
		localidad.setClave(dgLocalidad.getId().getCveLoc());
		localidad.setNombre(entity.getNomLoc());
		
		DgCatMunicipio dgMunicipio = entity.getDgCatMunicipio();
		municipio.setClave(dgMunicipio.getId().getCveMun());
		municipio.setNombre(dgMunicipio.getNomMun());
		
		
		DgCatEstado dgEstado = dgMunicipio.getDgCatEstado();
		entidadFed.setClave(dgMunicipio.getId().getCveEnt());
		entidadFed.setNombre(dgEstado.getNomEnt());
		
		municipio.setEntidadFederativa(entidadFed);
		localidad.setMunicipio(municipio);
		
		return localidad;
		
	}
	

	@Override
	public CodigoPostal transformarCodigoPostal(DgCodigosPostale entity)
			throws TransformacionException {
		CodigoPostal codigoPostal = new CodigoPostal();
		
		
		codigoPostal.setCodigoPostal(entity.getId().getCodigo());
		
		return codigoPostal;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Domicilio transformarDomicilio(DgDomicilioGeografico entity)
			throws TransformacionException {
		
		if(entity == null){
			throw new TransformacionException();
		}


		Domicilio domicilio = new Domicilio();
		
		domicilio.setLongitud(entity.getRefLongitud());
		domicilio.setLatitud(entity.getRefLatitud());
		
		//Obtenemos el asentamiento
		DgAsentamiento dgAsentamiento = entity.getDgAsentamiento();
		if(dgAsentamiento == null){
			// Si no tiene asentamiento mandamos el error
			throw new TransformacionException();
		}
		Asentamiento asentamiento = new Asentamiento();
		asentamiento.setClave(dgAsentamiento.getId().getCveAsen());
		if (StringUtils.isNotBlank(dgAsentamiento.getNomAsen())) {
			asentamiento.setNombre(dgAsentamiento.getNomAsen().toUpperCase());
		} else {
			asentamiento.setNombre(dgAsentamiento.getNomAsen());
		}
		domicilio.setAsentamiento(asentamiento);
		
		//Datos del tipo de asentamiento
		
		DgCatTipoAsen catTipoAsen =  dgAsentamiento.getDgCatTipoAsen();
		TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
		tipoAsentamiento.setClave(catTipoAsen.getCveTipoAsen());
		if (StringUtils.isNotBlank(catTipoAsen.getNombre())) {
			tipoAsentamiento.setDescripcion(catTipoAsen.getNombre().toUpperCase());
		} else {
			tipoAsentamiento.setDescripcion(catTipoAsen.getNombre());
		}
		asentamiento.setTipoAsentamiento(tipoAsentamiento);
		
		//Datos de la localidad
		DgCatLocalidad dgLocalidad =  entity.getDgCatLocalidad();
		if(dgLocalidad == null){
			// Si no tiene localidad mandamos el error
			throw new TransformacionException();
		}
		
		//Ambito
		DgCatAmbito dgAmbito = dgLocalidad.getDgCatAmbito();
		if (dgAmbito != null) {
			TipoAmbito ambito = new TipoAmbito();
			ambito.setClave(dgAmbito.getAmbito());
			if (StringUtils.isNotBlank(dgAmbito.getNombre())) {
				ambito.setDescripcion(dgAmbito.getNombre().toUpperCase());
			} else {
				ambito.setDescripcion(dgAmbito.getNombre());
			}
		}
		
		Localidad localidad = new Localidad();
		localidad.setClave(dgLocalidad.getId().getCveLoc());
		if (StringUtils.isNotBlank(dgLocalidad.getNomLoc())) {
			localidad.setNombre(dgLocalidad.getNomLoc().toUpperCase());
		} else {
			localidad.setNombre(dgLocalidad.getNomLoc());
		}
		
		asentamiento.setLocalidad(localidad);
		//Datos del municipio
		DgCatMunicipio dgMunicipio =  dgLocalidad.getDgCatMunicipio();
		if(dgMunicipio == null){
			// Si no tiene municpio mandamos el error
			throw new TransformacionException();
		}
		
		Municipio municipio = new Municipio();
		municipio.setClave(dgMunicipio.getId().getCveMun());
		if (StringUtils.isNotBlank(dgMunicipio.getNomMun())) {
			municipio.setNombre(dgMunicipio.getNomMun().toUpperCase());
		} else {
			municipio.setNombre(dgMunicipio.getNomMun());
		}
		
		
		localidad.setMunicipio(municipio);
		
		//Entidad federativa
		DgCatEstado dgEstado =  dgMunicipio.getDgCatEstado();
		if(dgEstado == null){
			// Si no tiene entidad fed mandamos el error
			throw new TransformacionException();
		}
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave(dgEstado.getCveEnt());
		if (StringUtils.isNotBlank(dgEstado.getNomEnt())) {
			entidadFederativa.setNombre(dgEstado.getNomEnt().toUpperCase());
		} else {
			entidadFederativa.setNombre(dgEstado.getNomEnt());
		}
		
		municipio.setEntidadFederativa(entidadFederativa);
		
		
		//Codigo Postal
		DgCodigosPostale dgCodigo = entity.getDgCodigosPostale();
		CodigoPostal codigo = new CodigoPostal();
		codigo.setCodigoPostal(dgCodigo.getId().getCodigo());
		domicilio.setClave( new Long(entity.getDomicilioId()).intValue());
		domicilio.setCodigoPostal(codigo);
		asentamiento.setCodigoPostal(codigo);
		
		//Vialidad principal se cambio la forma de setear devido a la inclusion de domiciios carreteras y caminos
		Vialidad vialidadPrimaria = this.transformaVialidad(entity, entity.getDgVialidadByCveViaPrin());
		domicilio.setVialidadPrimaria(vialidadPrimaria);
		if (vialidadPrimaria != null) {
			if (StringUtils.isNotBlank(vialidadPrimaria.getNombre())) {
				domicilio.setCalle(vialidadPrimaria.getNombre().toUpperCase());
			} else {
				domicilio.setCalle(vialidadPrimaria.getNombre());
			}
		}
		
		if(vialidadPrimaria!= null && vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() >= 20000000) {
			domicilio.setTipoBusquedaVialidad(TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo());
		} else {
			domicilio.setTipoBusquedaVialidad(TipoBusquedaVialidadEnum.VIALIDAD.getCodigo());
		}
		
		//Vialidad referencia primaria
		Vialidad vialidadRefPrimaria = this.transformaVialidadRef( entity.getDgVialidadByCveViaRef1());
		domicilio.setVialidadReferenciaPrimaria(vialidadRefPrimaria);
		
		//Vialidad referencia secundaria
		Vialidad vialidadRefSecundaria = this.transformaVialidadRef( entity.getDgVialidadByCveViaRef2());
		domicilio.setVialidadReferenciaSecundaria(vialidadRefSecundaria);
		//Vialidad referencia posterior
		Vialidad vialidadRefPosterior = this.transformaVialidadRef( entity.getDgVialidadByCveViaRef3());
		domicilio.setVialidadReferenciaPosterior(vialidadRefPosterior);
		
		//seteo de propiedades de domicilioCarretera o domicilioCamino
		DomicilioCamino camino = this.convertirEntityToModelDomicilioCamino(entity.getDgDomiciliosCamino());
		if(camino != null) {
			domicilio.setTipoBusquedaVialidad(TipoBusquedaVialidadEnum.CAMINO.getCodigo());
			domicilio.setDomicilioCamino(camino);
		}
		DomicilioCarretera carretera = this.convertirEntituToModelDomicilioCarretera(entity.getDgDomiciliosCarretera());
		if(carretera != null) {
			domicilio.setTipoBusquedaVialidad(TipoBusquedaVialidadEnum.CARRETERA.getCodigo());
			domicilio.setDomicilioCarretera(carretera);
		}
		
		
		//Numero exterior
		domicilio.setNumExterior1(entity.getNumextnum());
		
		//Numero exterior anterior
		if(entity.getNumextAnt() != null){
			try{
				domicilio.setNumExterior2(new Integer(entity.getNumextAnt()));
			}catch (Exception e) {
				this.log.error(e);
			}
		}
		
		
		//Numero interior
		if(entity.getNumintnum() != null ){
			domicilio.setNumInterior(entity.getNumintnum());
		}
		//Numero interior alfanumerico		
		if(entity.getNumintalf() != null){
			domicilio.setNumInteriorAlf(entity.getNumintalf().toString());
		}
		//Numero exterior alfanumerico
		if(entity.getNumextalf() != null){
			domicilio.setNumExteriorAlf(entity.getNumextalf().toString());
		}
		
		if (StringUtils.isNotBlank(entity.getDescripc())) {
			domicilio.setDescripcion(entity.getDescripc().toUpperCase());
		} else {
			domicilio.setDescripcion(entity.getDescripc());
		}
		
		if(entity.getDgCatTipoDom() != null){
			TipoDomicilio tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(entity.getDgCatTipoDom().getCveTipoDom());
			if (StringUtils.isNotBlank(entity.getDgCatTipoDom().getDescripcion())) {
				tipoDomicilio.setDescripcion(entity.getDgCatTipoDom().getDescripcion().toUpperCase());
			} else {
				tipoDomicilio.setDescripcion(entity.getDgCatTipoDom().getDescripcion());
			}
			domicilio.setTipoDomicilio(tipoDomicilio);
		}
		
		
		return domicilio;
	}
	
	
	@Override
	public DgDomicilioGeografico transformarDomicilio(Domicilio modelo)
			throws TransformacionException {
		
		this.log.debug(" convirtiendo [" + modelo + "]");
		
		if(modelo == null){
			throw new TransformacionException();
		}
		
		
		DgDomicilioGeografico dgDomicilio = new DgDomicilioGeografico();
		
		Asentamiento asentamiento = modelo.getAsentamiento();
		Localidad localidad = asentamiento.getLocalidad();
		Municipio municipio = localidad.getMunicipio();
		EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
		
		//Entidad Federativa
		
		DgCatEstado dgEstado = new DgCatEstado();
		dgEstado.setCveEnt(entidadFederativa.getClave());
		
		
		
		//Municipio
				
				DgCatMunicipio dgMunicipio = new DgCatMunicipio();
				DgCatMunicipioPK pkMunicipio = new DgCatMunicipioPK();
				pkMunicipio.setCveEnt(entidadFederativa.getClave());
				pkMunicipio.setCveMun(municipio.getClave());
				dgMunicipio.setId(pkMunicipio);

		//Localidad
				
				DgCatLocalidad dgLocalidad = new DgCatLocalidad();
				DgCatLocalidadPK pkLocalidad = new DgCatLocalidadPK();
				pkLocalidad.setCveEnt(entidadFederativa.getClave());
				pkLocalidad.setCveMun(municipio.getClave());
				pkLocalidad.setCveLoc(localidad.getClave());
				pkLocalidad.setCvePeriodo(4);
				dgLocalidad.setId(pkLocalidad);
				dgLocalidad.setDgCatMunicipio(dgMunicipio);
				//seteo de atributos simples de la localidad
				dgDomicilio.setCveLoc(localidad.getClave());
				dgDomicilio.setCvePeriodo(4);
				
				
				
				
		//Asentamiento
				
				DgAsentamiento dgAsentamiento = new DgAsentamiento();
				DgAsentamientoPK pkAsentamiento = new DgAsentamientoPK();
				pkAsentamiento.setCveAsen(asentamiento.getClave());
				
				pkAsentamiento.setCveMun(municipio.getClave());
				pkAsentamiento.setCveEnt(entidadFederativa.getClave());
				/*TODO SE ELIMINA EL SETEO DE LOCALIDAD
				pkAsentamiento.setCveLoc(localidad.getClave());
				pkAsentamiento.setCvePeriodo(1);
				*/
				dgAsentamiento.setId(pkAsentamiento);
		
		
		
		//Vialidad primaria
		DgVialidad dgVialidadPrimaria = this.transformaVialidadToEntity(
				modelo.getVialidadPrimaria(), dgLocalidad);
		
		//Vialidad referencia primaria
		DgVialidad dgVialidadRefPri = this.transformaVialidadToEntity(
				modelo.getVialidadReferenciaPrimaria(), dgLocalidad);
		
		//Vialidad referencia secundaria
		DgVialidad dgVialidadRefSec = this.transformaVialidadToEntity( 
				modelo.getVialidadReferenciaSecundaria(), dgLocalidad);
		this.log.debug("Convirtiendo la Vialidad posterior..." + modelo.getVialidadReferenciaPosterior());
		//Vialidad posterior
		DgVialidad dgVialidadPosterior = this.transformaVialidadToEntity(
				modelo.getVialidadReferenciaPosterior(), dgLocalidad);
		
		this.log.debug("dgVialidadPosterior  " +dgVialidadPosterior );
		
		
		//seteo de propiedades de carreteras y caminos
		DgDomiciliosCarretera dgCarretera = this.convertirModelDomicilioCarreteraToEntity(modelo.getDomicilioCarretera());
		DgDomiciliosCamino dgCamino = this.convertirModelDomicilioCaminoToEntity(modelo.getDomicilioCamino());
		
		if(dgCamino != null){
			dgDomicilio.setDgDomiciliosCamino(dgCamino);
			dgDomicilio.setNomvial(dgCamino.getNomvial());
		}
		else if(dgCarretera != null){
			dgDomicilio.setDgDomiciliosCarretera(dgCarretera);
			dgDomicilio.setNomvial(dgCarretera.getNomvial());
		}
		else if(!StringUtils.isBlank(modelo.getCalle())){
			dgDomicilio.setNomvial(modelo.getCalle());
		}
		else
			dgDomicilio.setNomvial(modelo.getVialidadPrimaria().getNombre());
		
		dgDomicilio.setDgAsentamiento(dgAsentamiento);
		dgDomicilio.setDgCatLocalidad(dgLocalidad);
		if(dgVialidadPrimaria != null){
			dgDomicilio.setDgVialidadByCveViaPrin(dgVialidadPrimaria);
		}
		if(dgVialidadRefPri != null){
			dgDomicilio.setDgVialidadByCveViaRef1(dgVialidadRefPri);
		}
		if(dgVialidadRefSec != null){
			dgDomicilio.setDgVialidadByCveViaRef2(dgVialidadRefSec);
		}
		
		if(dgVialidadPosterior != null){
			this.log.debug("Seteando la Vialidad posterior..." + dgVialidadPosterior);
			dgDomicilio.setDgVialidadByCveViaRef3(dgVialidadPosterior);
		}else{
			dgDomicilio.setDgVialidadByCveViaRef3(null);
		}
		
//		
		//Datos complementarios
		if (modelo.getLatitud() != null) {
			dgDomicilio.setRefLatitud(modelo.getLatitud());
		}
		if (modelo.getLongitud() != null) {
			dgDomicilio.setRefLongitud(modelo.getLongitud());
		}
		
		
		dgDomicilio.setNumextnum(modelo.getNumExterior1());
		
		dgDomicilio.setNumextalf(modelo.getNumExteriorAlf());
		
		if(modelo.getNumExterior2() != null ){
			dgDomicilio.setNumextAnt(modelo.getNumExterior2().toString());
		}
		dgDomicilio.setNumintalf(modelo.getNumInteriorAlf());
//		if (modelo.getNumInterior() != null
//				&& !modelo.getNumInterior().isEmpty()) {
//			dgDomicilio.setNumintnum(new Integer(modelo.getNumInterior()));
//		}
		dgDomicilio.setNumintnum(modelo.getNumInterior());
		
		
		
		//Tipo de domicilio
		DgCatTipoDom dgCatTipoDom = new DgCatTipoDom();
		if ( modelo.getTipoDomicilio() != null && modelo.getTipoDomicilio().getClave() != null){
			this.log.debug("El domicilio tiene tipo de domicilio");
			dgCatTipoDom.setCveTipoDom(modelo.getTipoDomicilio().getClave());
		}else{
			this.log.warn("El domicilio no tiene tipo de domicilio, seteando el domicilio de default 1 -Urbano");
			dgCatTipoDom.setCveTipoDom(new Integer (1));
		}
		
		dgDomicilio.setDgCatTipoDom(dgCatTipoDom);
		//Codigo Postal
		CodigoPostal codigoPostal =  modelo.getCodigoPostal();
		if(codigoPostal == null){
			codigoPostal = modelo.getAsentamiento().getCodigoPostal();
		}
		
		
		
		if( codigoPostal != null ){
			
			if(codigoPostal.getCodigoPostal() == null ){
				/*No cuenta con codigo postal, debemos de revisar el codigo postal del asentamiento*/
				this.log.warn("El domicilio no tiene codigo postal directo, recuperando codigo del asentamiento");
				codigoPostal = modelo.getAsentamiento().getCodigoPostal();
				this.log.warn("Codigo postal del asentamiento :" + codigoPostal);
				
				
			}
			
			
			if(codigoPostal.getCodigoPostal() != null){
				
				this.log.error("Codigo postal especificado ..." + codigoPostal);
				DgCodigosPostale dgCodigosPostale = new DgCodigosPostale();
				DgCodigosPostalePK dgCodigosPostaleId = new DgCodigosPostalePK();
				
				dgCodigosPostaleId.setCodigo(codigoPostal.getCodigoPostal().toString());
				dgCodigosPostaleId.setCveAsen(asentamiento.getClave());
				dgCodigosPostaleId.setCveEnt(entidadFederativa.getClave());
				dgCodigosPostaleId.setCveMun(municipio.getClave());
				/*TODO SE ELIMINA LA LOCALIDAD QUE SE CAMBIO EL MAPEO
				dgCodigosPostaleId.setCveLoc(localidad.getClave());
				dgCodigosPostaleId.setCvePeriodo((long)1);
				//TODO: EL periodo no se esta manejando, se esta poniendo el default.
				*/
				
				dgCodigosPostale.setId(dgCodigosPostaleId);
				dgDomicilio.setDgCodigosPostale(dgCodigosPostale);
			}
		}

		/*Descripcion del domicilio.*/
		dgDomicilio.setDescripc(modelo.getDescripcion());
		
		if (modelo.getAsentamiento().getLocalidad().getMunicipio()
				.getEntidadFederativa().getClave() == null
				|| modelo.getAsentamiento().getLocalidad().getMunicipio()
						.getEntidadFederativa().getClave().isEmpty()) {
			this.log.warn("El domicilio no es valido , regresando NULO.");
			dgDomicilio = null;
		}

		this.log.debug("Domicilio Entity generado:[" + dgDomicilio + "]");
		return dgDomicilio;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	private Vialidad transformaVialidad(DgDomicilioGeografico entity, DgVialidad vialidadEntity ) throws TransformacionException{
		//Vialidad 
				
		
		Vialidad vialidad = null;
		if(entity != null){
			vialidad = new Vialidad();
			
			DgDomiciliosCarretera carretera = entity.getDgDomiciliosCarretera();
			DgDomiciliosCamino camino = entity.getDgDomiciliosCamino();
		
			if(camino != null){
				if (StringUtils.isNotBlank(camino.getNomvial())) {
					vialidad.setNombre(camino.getNomvial().toUpperCase());
				} else {
					vialidad.setNombre(camino.getNomvial());
				}
				//Tipo de vialidad
			}else if(carretera != null){
				if (StringUtils.isNotBlank(carretera.getNomvial())) {
					vialidad.setNombre(carretera.getNomvial().toUpperCase());
				} else {
					vialidad.setNombre(carretera.getNomvial());
				}
			}else if(vialidadEntity != null){
				vialidad.setClave(vialidadEntity.getCveVia().intValue());
				vialidad.setNombre(entity.getNomvial()!= null?entity.getNomvial() : vialidadEntity.getNomVia());
				
				if (StringUtils.isNotBlank(vialidad.getNombre())) {
					vialidad.setNombre(vialidad.getNombre().toUpperCase());
				}
				//Tipo de vialidad
				DgCatVialidad dgCatVialidad =  vialidadEntity.getDgCatVialidad();
				TipoVialidad tipoVialidad = new TipoVialidad();
				tipoVialidad.setClave(dgCatVialidad.getCveTipoVial().intValue());
				if (StringUtils.isNotBlank(dgCatVialidad.getDescripcion())) {
					tipoVialidad.setDescripcion(dgCatVialidad.getDescripcion().toUpperCase());
				} else {
					tipoVialidad.setDescripcion(dgCatVialidad.getDescripcion());
				}
				vialidad.setTipoVialidad(tipoVialidad);
			} else {
				return null;
			}
		}
				return vialidad;
	}
	
	
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	private Vialidad transformaVialidadRef(DgVialidad vialidadEntity ) throws TransformacionException{
		//Vialidad 
				
		
		Vialidad vialidad = null;
		if (vialidadEntity != null) {
			vialidad = new Vialidad();
			vialidad.setClave(vialidadEntity.getCveVia().intValue());
			if (StringUtils.isNotBlank(vialidadEntity.getNomVia())) {
				vialidad.setNombre(vialidadEntity.getNomVia().toUpperCase());
			} else {
				vialidad.setNombre(vialidadEntity.getNomVia());
			}

			// Tipo de vialidad
			DgCatVialidad dgCatVialidad = vialidadEntity.getDgCatVialidad();
			TipoVialidad tipoVialidad = new TipoVialidad();
			tipoVialidad.setClave(dgCatVialidad.getCveTipoVial().intValue());
			if (StringUtils.isNotBlank(dgCatVialidad.getDescripcion())) {
				tipoVialidad.setDescripcion(dgCatVialidad.getDescripcion().toUpperCase());
			} else {
				tipoVialidad.setDescripcion(dgCatVialidad.getDescripcion());
			}
			vialidad.setTipoVialidad(tipoVialidad);

		}
		return vialidad;
	}



	/**
	 * 
	 * @param vialidad
	 * @return
	 * @throws TransformacionException
	 */
	private DgVialidad transformaVialidadToEntity(Vialidad vialidad ,DgCatLocalidad dgCatLocalidad )throws TransformacionException{
		DgVialidad dgVialidad = null;
		
		if( vialidad != null){
			
			
			this.log.debug("El objeto de la vialidad NO ES NULO, debemos de validar que contenga datos validos.");
			
			
			dgVialidad = new DgVialidad();
			
			
			Integer cveVialidad = vialidad.getClave();
			this.log.debug(" transformando vialidad, clave {"  +  cveVialidad + "}");
			if(cveVialidad != null && cveVialidad.intValue() != -1){
				
				dgVialidad.setCveVia( cveVialidad);
				
				DgCatVialidad dgTipoVialidad = new DgCatVialidad();
				if(vialidad.getTipoVialidad() != null && vialidad.getTipoVialidad().getClave() != null){
					dgTipoVialidad.setCveTipoVial(new Integer(vialidad.getTipoVialidad().getClave()));
				}else{
					log.warn("La vialidad no tiene un tipo de vialidad especifico, seteando el tipo default.");
					dgTipoVialidad.setCveTipoVial(Integer.valueOf(1));
				}
				
				dgVialidad.setDgCatVialidad(dgTipoVialidad);
				
				//Ambito
				DgCatAmbito dgCatAmbito = new DgCatAmbito();
				dgCatAmbito.setAmbito(1);
				dgVialidad.setDgCatAmbito(dgCatAmbito);
				
				//Localidad
				dgVialidad.setDgCatLocalidad(dgCatLocalidad);
				
				
				
				
				//Nombre
				dgVialidad.setNomVia(vialidad.getNombre());
				
				
			}else{
				this.log.warn("No se recibio el valor de la clave de la vialidad, esto quiere decir que la vialidad no se guardara.");
				
				/* LUDS: Se comento por que no es un error en especifico, ya que algunas vialidades no son requeridas.
				throw new TransformacionException();*/
				return null;
			}
			
			
			
			
			
		}
		
		return dgVialidad;
	}

	@Override
	public DitDomicilioSat transformarDomicilioFiscal(Domicilio modelo)
			throws TransformacionException {
		
		this.log.debug(" convirtiendo [" + modelo + "]");

		if (modelo == null) {
			throw new TransformacionException();
		}
		
		Asentamiento asentamiento = modelo.getAsentamiento();
		Localidad localidad = asentamiento.getLocalidad();
		Municipio municipio = localidad.getMunicipio();
		EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
		
		Vialidad vialidadPrimaria = modelo.getVialidadPrimaria();
		Vialidad vialidadRefPrimaria = modelo.getVialidadReferenciaPrimaria();
		Vialidad vialidadRefSecundaria = modelo.getVialidadReferenciaSecundaria();

		DitDomicilioSat ditDomicilioSat = new DitDomicilioSat();
		ditDomicilioSat.setCalle(vialidadPrimaria != null ? vialidadPrimaria.getNombre() : null);
		ditDomicilioSat.setCodigo(modelo.getCodigoPostal().getCodigoPostal());
		ditDomicilioSat.setColonia(asentamiento.getNombre());
		ditDomicilioSat.setEntidadFederativa(entidadFederativa != null ? entidadFederativa.getNombre() : null);
		
		ditDomicilioSat.setVialidad(vialidadPrimaria != null ? vialidadPrimaria.getNombre() : null);
		ditDomicilioSat.setEntreCalle1(vialidadRefPrimaria != null ? vialidadRefPrimaria.getNombre() : null);
		ditDomicilioSat.setEntreCalle2(vialidadRefSecundaria != null ? vialidadRefSecundaria.getNombre() : null);
		
		ditDomicilioSat.setReferencia(modelo.getDescripcion());
		
		if(asentamiento.getTipoAsentamiento() != null){
			ditDomicilioSat.setInmueble(asentamiento.getTipoAsentamiento().getDescripcion());
		}
		
		ditDomicilioSat.setLocalidad(localidad.getNombre());
		ditDomicilioSat.setMunicipio(municipio.getNombre());
		
		StringBuffer numDomicilio = new StringBuffer();
		
		if(!StringUtils.isBlank(modelo.getNumExteriorAlf())){
			numDomicilio.append(modelo.getNumExteriorAlf());
		}
		if(modelo.getNumExterior1() != null ){
			numDomicilio.append(" ");
			numDomicilio.append(modelo.getNumExterior1());
		}
		ditDomicilioSat.setNumExterior(numDomicilio.toString());
		
		numDomicilio.delete(0, numDomicilio.length());
		
		if(!StringUtils.isBlank(modelo.getNumInteriorAlf())){
			numDomicilio.append(modelo.getNumInteriorAlf());
		}
		if(modelo.getNumInterior() != null ){
			numDomicilio.append(" ");
			numDomicilio.append(modelo.getNumInterior());
		}
		ditDomicilioSat.setNumInterior(numDomicilio.toString());
		
		
		if(modelo.getClave() != null && modelo.getClave() > 0){
			ditDomicilioSat.setCveIdDomicilio(new Long(modelo.getClave()));
		}

		this.log.debug("Domicilio Entity generado:[" + ditDomicilioSat + "]");
	
		return ditDomicilioSat;
	}

	@Override
	public DomicilioFiscal transformarDomicilioFiscal(DitDomicilioSat entity)
			throws TransformacionException {
		
		this.log.debug(" iniciando la transformacion del domicilioSat entity to model");
		
		if(entity == null){
			throw new TransformacionException();
		}
		
		DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
		
		domicilioFiscal.setClave(Long.valueOf(entity.getCveIdDomicilio())
				.intValue());
		
		CodigoPostal codigoPostal = null;
		if(!StringUtils.isEmpty(entity.getCodigo())){
			codigoPostal = new CodigoPostal();
			codigoPostal.setCodigoPostal(entity.getCodigo());
			domicilioFiscal.setCodigoPostal(codigoPostal);
		}
		
		domicilioFiscal.setCalle(entity.getCalle());
		domicilioFiscal.setColonia(entity.getColonia());
				
		Asentamiento asentamiento = new Asentamiento();
		Localidad localidad = new Localidad();
		Municipio municipio = new Municipio();
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		
		asentamiento.setNombre(entity.getColonia());
		
		entidadFederativa.setNombre(entity.getEntidadFederativa());
		municipio.setEntidadFederativa(entidadFederativa);
		municipio.setNombre(entity.getMunicipio());
		localidad.setMunicipio(municipio);
		localidad.setNombre(entity.getLocalidad());
		asentamiento.setLocalidad(localidad);
		asentamiento.setCodigoPostal(codigoPostal);
		
		TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
		tipoAsentamiento.setDescripcion(entity.getInmueble());
		asentamiento.setTipoAsentamiento(tipoAsentamiento);
		
		domicilioFiscal.setAsentamiento(asentamiento);
		
				
		if(!StringUtils.isBlank(entity.getVialidad())){
			Vialidad vialidadPrimaria = new Vialidad();
			vialidadPrimaria.setNombre(entity.getVialidad());
			domicilioFiscal.setVialidadPrimaria(vialidadPrimaria);
		}
				
		if(!StringUtils.isBlank(entity.getEntreCalle1())){
			Vialidad vialidadRefPrimaria = new Vialidad();
			vialidadRefPrimaria.setNombre(entity.getEntreCalle1());
			domicilioFiscal.setVialidadReferenciaPrimaria(vialidadRefPrimaria);
		}
		
		if(!StringUtils.isBlank(entity.getEntreCalle2())){
			Vialidad vialidadRefSecundaria = new Vialidad();
			vialidadRefSecundaria.setNombre(entity.getEntreCalle2());
			domicilioFiscal.setVialidadReferenciaSecundaria(vialidadRefSecundaria);
		}
		
		if(!StringUtils.isBlank(entity.getReferencia())){
			domicilioFiscal.setDescripcion(entity.getReferencia());
		}
		
		if(entity.getNumInterior() != null){
			domicilioFiscal.setNumInteriorAlf(entity.getNumInterior());
		}
		
		if (entity.getNumExterior() != null) {
			domicilioFiscal.setNumExteriorAlf(entity.getNumExterior());
		}
		
		
		return domicilioFiscal;
	}
	
	@Override
	public  String persisToModelDesDireccion(
			DgDomicilioGeografico entrada) throws TransformacionException {
		StringBuffer desDireccion=new StringBuffer("");
		
		try {
			String calle="";
			String numero="";
			String colAsent="";
			String  cp="";
			String delegacion="";
			String entidadFederativa="";
			String sl=" ";
			
			if(entrada!=null){
				try{
					calle=entrada.getDgVialidadByCveViaPrin().getNomVia();
				}catch(Exception e){
					log.error(e);
				}
				if(entrada.getNumextnum()!=null){
					numero=entrada.getNumextnum().toString();
				}
				if(entrada.getDgAsentamiento()!=null){
					colAsent=entrada.getDgAsentamiento().getNomAsen();
					try{
						delegacion=entrada.getDgAsentamiento().getDgCatMunicipio().getNomMun();
						if(entrada.getDgAsentamiento().getDgCatMunicipio().getDgCatEstado()!=null){
							entidadFederativa=entrada.getDgAsentamiento().getDgCatMunicipio().getDgCatEstado().getNomEnt();
						}
					
					}catch(Exception e){
						log.error(e);
					}
				}
				if(entrada.getDgCodigosPostale()!=null){
					cp=entrada.getDgCodigosPostale().getId().getCodigo();
				}
				
			}
			desDireccion.append("Calle:");
			desDireccion.append(calle);
			desDireccion.append(" Num.");
			desDireccion.append(numero);
			desDireccion.append(sl);
			desDireccion.append("Colonia/Asentamiento:");
			desDireccion.append(colAsent);
			desDireccion.append(sl);
			desDireccion.append("CP:");
			desDireccion.append(cp);
			desDireccion.append(sl);
			desDireccion.append("Delegacion:");
			desDireccion.append(delegacion);
			desDireccion.append(sl);
			desDireccion.append("EntidadFederativa:");
			desDireccion.append(entidadFederativa);
		} catch (Exception e) {
			log.debug("Error en persisToModelDesDireccion(DgDomicilioGeografico entrada): "+e);
			throw new TransformacionException();
		}
		
		return desDireccion.toString();
	}
	
	@Override
	public Delegacion convertirEntityToModelDelegacion(DicDelegacion entity) {
		Delegacion delegacion = new Delegacion();
		delegacion.setClave(entity.getClaveDelegacion());
		delegacion.setId(entity.getCveIdDelegacion());
		delegacion.setDescripcion(entity.getDesDeleg());
		Integer ciz = entity.getCveCiz() == null ? 1 : entity.getCveCiz();
		delegacion.setCiz(ciz);
		return delegacion;
	}

	@Override
	public Subdelegacion convertirEntityToModelSubdelegacion(
			DicSubdelegacion entity) {
		if(entity==null)
			return null;
		Subdelegacion model = new Subdelegacion();
		model.setClave(entity.getClaveSubdelegacion());
		model.setId(entity.getCveIdSubdelegacion());
		model.setDescripcion(entity.getDesSubdelegacion());
		model.setDelegacion(convertirEntityToModelDelegacion(entity.getDicDelegacion()));
		return model;
	}
	
	/**
	 * Metodo que transforma un objeto de persistencia a negocio de DomiciliosCamino
	 * @param entity DgDomiciliosCarretera
	 * @return DomicilioCarretera
	 */
	@Override
	public DomicilioCarretera convertirEntituToModelDomicilioCarretera(DgDomiciliosCarretera entity){
		if(entity==null)
			return null;
		DomicilioCarretera model = new DomicilioCarretera();
	
		TipoAdministracion administracion = new TipoAdministracion();
		DgCatAdministracion catAdministracion = entity.getDgCatAdministracion();
		administracion.setClave(catAdministracion.getCveCac());
		if (StringUtils.isNotBlank(catAdministracion.getNombre())) {
			administracion.setDescripcion(catAdministracion.getNombre().toUpperCase());
		} else {
			administracion.setDescripcion(catAdministracion.getNombre());
		}
		
		TipoDerechoTransito derechoTransito = new TipoDerechoTransito();
		DgCatDerechosTransito catDerecho = entity.getDgCatDerechosTransito();
		derechoTransito.setClave(catDerecho.getCveCdt());
		if (StringUtils.isNotBlank(catDerecho.getNombre())) {
			derechoTransito.setDescripcion(catDerecho.getNombre().toUpperCase());
		} else {
			derechoTransito.setDescripcion(catDerecho.getNombre());
		}

		TipoTerminoGeneral terminoGeneral = new TipoTerminoGeneral();
		DgCatTermGen catTerm = entity.getDgCatTermGen();
		terminoGeneral.setClave(catTerm.getCveTer());
		if (StringUtils.isNotBlank(catTerm.getNombre())) {
			terminoGeneral.setDescripcion(catTerm.getNombre().toUpperCase());
		} else {
			terminoGeneral.setDescripcion(catTerm.getNombre());
		}
		
		
		model.setAdministracion(administracion);
		model.setDerechoTransito(derechoTransito);
		model.setTerminoGeneral(terminoGeneral);
		
		if (StringUtils.isNotBlank(entity.getCadenamiento())) {
			model.setCadenamiento(entity.getCadenamiento().toUpperCase());
		} else {
			model.setCadenamiento(entity.getCadenamiento());
		}
		model.setCodigoCarretera(entity.getCodigo());
		
		if (StringUtils.isNotBlank(entity.getOrigen())) {
			model.setOrigen(entity.getOrigen().toUpperCase());
		} else {
			model.setOrigen(entity.getOrigen());
		}
		if (StringUtils.isNotBlank(entity.getDestino())) {
			model.setDestino(entity.getDestino().toUpperCase());
		} else {
			model.setDestino(entity.getDestino());
		}
		if (StringUtils.isNotBlank(entity.getNomvial())) {
			model.setNombreVialidad(entity.getNomvial().toUpperCase());
		} else {
			model.setNombreVialidad(entity.getNomvial());
		}
		return model;
	}
	
	/**
	 * Metodo que transforma un objeto de persistencia a negocio de DomiciliosCamino
	 * @param entity DgDomiciliosCamino
	 * @return DomicilioCamino
	 */
	@Override
	public DomicilioCamino convertirEntityToModelDomicilioCamino(DgDomiciliosCamino entity){
		if(entity==null)
			return null;
		DomicilioCamino model = new DomicilioCamino();
		
		
		TipoTerminoGeneral terminoGeneral = new TipoTerminoGeneral();
		DgCatTermGen catTerm = entity.getDgCatTermGen();
		terminoGeneral.setClave(catTerm.getCveTer());
		if (StringUtils.isNotBlank(catTerm.getNombre())) {
			terminoGeneral.setDescripcion(catTerm.getNombre().toUpperCase());
		} else {
			terminoGeneral.setDescripcion(catTerm.getNombre());
		}
		
		TipoMargen margen = new TipoMargen();
		DgCatMargen catMargen = entity.getDgCatMargen(); 
		margen.setClave(catMargen.getCveMargen());
		if (StringUtils.isNotBlank(catMargen.getDescripcion())) {
			margen.setDescripcion(catMargen.getDescripcion().toUpperCase());
		} else {
			margen.setDescripcion(catMargen.getDescripcion());
		}
		
		
		model.setTerminoGeneral(terminoGeneral);
		model.setMargen(margen);
		model.setCadenamiento(entity.getCadenamiento());
		
		if (StringUtils.isNotBlank(entity.getOrigen())) {
			model.setOrigen(entity.getOrigen().toUpperCase());
		} else {
			model.setOrigen(entity.getOrigen());
		}
		if (StringUtils.isNotBlank(entity.getDestino())) {
			model.setDestino(entity.getDestino().toUpperCase());
		} else {
			model.setDestino(entity.getDestino());
		}
		if (StringUtils.isNotBlank(entity.getNomvial())) {
			model.setNombreVialidad(entity.getNomvial().toUpperCase());
		} else {
			model.setNombreVialidad(entity.getNomvial());
		}

		
		return model;
		
	}
	
	private DgDomiciliosCamino convertirModelDomicilioCaminoToEntity(DomicilioCamino model){
		if(model==null || model.getTerminoGeneral()==null || model.getTerminoGeneral().getClave() == null
				|| model.getTerminoGeneral().getClave() == -1 || model.getMargen() == null || model.getMargen().getClave() == -1)
			return null;
		
		DgDomiciliosCamino entity = new DgDomiciliosCamino();
		
		DgCatTermGen catTerm = new DgCatTermGen();
		catTerm.setCveTer(model.getTerminoGeneral().getClave());
		
		DgCatMargen catMargen = new DgCatMargen();
		catMargen.setCveMargen(model.getMargen().getClave());
		
		entity.setDgCatTermGen(catTerm);
		entity.setDgCatMargen(catMargen);
		entity.setCadenamiento(model.getCadenamiento() != null ? model.getCadenamiento().toUpperCase() : null);
		entity.setOrigen(model.getOrigen() != null ? model.getOrigen().toUpperCase() : null);
		entity.setDestino(model.getDestino() != null ? model.getDestino().toUpperCase() : null);
		
		//ser arma el nombre de la vialidad con los datos anteriores
		StringBuffer bufferVial = new StringBuffer();
		bufferVial.append(model.getTerminoGeneral().getDescripcion() + " ");
		bufferVial.append(model.getOrigen() +"-" +model.getDestino() +" ");
		bufferVial.append(model.getMargen().getDescripcion() +" ");
		bufferVial.append(model.getCadenamiento());	
		entity.setNomvial(bufferVial.toString());
		
	
		
		return entity;
		
	}
	
	
	private DgDomiciliosCarretera convertirModelDomicilioCarreteraToEntity(DomicilioCarretera model){
		if(model==null || model.getAdministracion() == null || model.getAdministracion().getClave() == null || model.getDerechoTransito() == null
				|| (model.getAdministracion()!=null && model.getAdministracion().getClave() == -1)
				|| (model.getDerechoTransito()!=null && model.getDerechoTransito().getClave() == -1)){
			return null;
		}
		
		DgDomiciliosCarretera entity = new DgDomiciliosCarretera();
		
		DgCatAdministracion catAdministracion = new DgCatAdministracion();
		catAdministracion.setCveCac(model.getAdministracion().getClave());
		
		DgCatDerechosTransito catDerecho = new DgCatDerechosTransito();
		catDerecho.setCveCdt(model.getDerechoTransito().getClave());
		
		DgCatTermGen catTerm = new DgCatTermGen();
		catTerm.setCveTer(model.getTerminoGeneral().getClave());
		
		entity.setDgCatAdministracion(catAdministracion);
		entity.setDgCatDerechosTransito(catDerecho);
		entity.setDgCatTermGen(catTerm);
		entity.setCadenamiento(model.getCadenamiento());
		entity.setCodigo(model.getCodigoCarretera());
		entity.setOrigen(model.getOrigen() != null ? model.getOrigen().toUpperCase() : null);
		entity.setDestino(model.getDestino() != null ? model.getDestino().toUpperCase() : null);
		//ser arma el nombre de la vialidad con los datos anteriores
		StringBuffer bufferVial = new StringBuffer();
		bufferVial.append(model.getTerminoGeneral().getDescripcion() + " ");
		bufferVial.append(model.getAdministracion().getDescripcion() + " ");
		bufferVial.append(model.getDerechoTransito().getDescripcion() + " ");
		bufferVial.append(model.getCodigoCarretera() +" ");
		bufferVial.append(model.getOrigen() +"-" +model.getDestino() +" ");
		bufferVial.append(model.getCadenamiento());
		entity.setNomvial(bufferVial.toString());
		
		return entity;
	}
	
	
	@Override
	public List<MunicipioIMSS> convertirMunicipioIMSS (List<DicMunicipioImss> municipiosImss){
		List<MunicipioIMSS> municipios = new ArrayList<MunicipioIMSS>();
		for(DicMunicipioImss m:municipiosImss ){
			MunicipioIMSS municipio = new MunicipioIMSS();
			if(null != m.getDgCatEstado()){
				municipio.setDescEntidad(m.getDgCatEstado().getNomEnt());
				municipio.setRp(m.getCveMunicipio());
				municipios.add(municipio);
			}
		}
		return municipios;
	}
	
	

	
	
}
