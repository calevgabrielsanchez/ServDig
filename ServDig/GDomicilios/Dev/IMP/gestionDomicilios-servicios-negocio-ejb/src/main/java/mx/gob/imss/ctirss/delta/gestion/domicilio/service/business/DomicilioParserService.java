package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioParserServiceRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;

@Stateless(name = "domicilioParserService",mappedName = "domicilioParserService")
public class DomicilioParserService extends AbstractServiceBusiness implements DomicilioParserServiceLocal, DomicilioParserServiceRemote {

	@Override
	public Domicilio convertirDomicilioDigtoDomicilioRecortado(
			mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio) {
		Domicilio domicilioCon = null;
		
		if(domicilio != null) {
			domicilioCon = new Domicilio();
			if(StringUtils.isNotBlank(domicilio.getCodigoPostal())){
				domicilioCon.setCodigoPostal(new CodigoPostal());
				domicilioCon.getCodigoPostal().setCodigoPostal(domicilio.getCodigoPostal());
			}
			
			domicilioCon.setLocalidad(this.convertirLocalidadDigtoLocalidad(domicilio.getLocalidad()));
			domicilioCon.setAsentamiento(this.convertirAsentamientoDigtoAsentamiento(domicilio.getAsentamiento()));
			domicilioCon.setVialidadPrimaria(this.convertirVialidadDigToVialidad(domicilio.getVialidadPrimaria()));
			domicilioCon.setCalle(domicilio.getCalle());
			domicilioCon.setNumExteriorAlf(domicilio.getNumExteriorAlf());
			domicilioCon.setNumInteriorAlf(domicilio.getNumInteriorAlf());
			domicilioCon.setNumExterior1(domicilio.getNumExterior1());
			domicilioCon.setNumInterior(domicilio.getNumInterior());
			
			log.debug("El numero exterior es: " + domicilioCon.getNumExteriorAlf());
			
		}
		
		return domicilioCon;
	}
	
	

	@Override
	public Asentamiento convertirAsentamientoDigtoAsentamiento(
			mx.gob.imss.digital.modelo.domicilio.Asentamiento asent) {
		Asentamiento asentamiento = null;
		
		if(asent != null) {
			asentamiento = new Asentamiento();
			asentamiento.setClave(asent.getClave());
			asentamiento.setNombre(asent.getNombre());
			asentamiento.setLocalidad(this.convertirLocalidadDigtoLocalidad(asent.getLocalidad()));
			asentamiento.setMunicipio(this.convertirMunicipioDigToMunicipio(asent.getMunicipio()));
			asentamiento.setTipoAsentamiento(this.convertirTipoAsenDigToTipoAsen(asent.getTipoAsentamiento()));
			if(!StringUtils.isBlank(asent.getCodigoPostal())) {
				asentamiento.setCodigoPostal(new CodigoPostal());
				asentamiento.getCodigoPostal().setCodigoPostal(asent.getCodigoPostal());
			}
			
		}
		
		return asentamiento;
	}



	@Override
	public Localidad convertirLocalidadDigtoLocalidad(
			mx.gob.imss.digital.modelo.domicilio.Localidad localidad) {
		
		Localidad local = null;
		if(localidad != null) {
			local = new Localidad();
			local.setClave(localidad.getClave());
			local.setNombre(localidad.getNombre());
			local.setMunicipio(this.convertirMunicipioDigToMunicipio(localidad.getMunicipio()));
		}
		
		return local;
	}

	@Override
	public EntidadFederativa convertirEntidadFederativaDigToEntidad(
			mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidad) {
		EntidadFederativa entidadF = null;
		
		if(entidad != null) {
			entidadF = new EntidadFederativa();
			entidadF.setClave(entidad.getClave());
			entidadF.setNombre(entidad.getNombre());
		}
		
		return entidadF;
	}

	@Override
	public Municipio convertirMunicipioDigToMunicipio(
			mx.gob.imss.digital.modelo.domicilio.Municipio municipio) {
	
		Municipio municipioI = null;
		
		if(municipio != null) {
			municipioI = new Municipio();
			municipioI.setClave(municipio.getClave());
			municipioI.setNombre(municipio.getNombre());
			municipioI.setEntidadFederativa(this.convertirEntidadFederativaDigToEntidad(municipio.getEntidadFederativa()));
		}
		
		return municipioI;
	}

	@Override
	public Vialidad convertirVialidadDigToVialidad(
			mx.gob.imss.digital.modelo.domicilio.Vialidad vialidad) {
		Vialidad vialidadI = null;
		if(vialidad != null) {
			vialidadI = new Vialidad();
			vialidadI.setClave(vialidad.getClave());
			vialidadI.setNombre(vialidad.getNombre());
			vialidadI.setTipoVialidad(this.convertirTipovialiadDigToTipo(vialidad.getTipoVialidad()));
		}
		return vialidadI;
	}

	@Override
	public TipoVialidad convertirTipovialiadDigToTipo(
			mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialiad) {
		TipoVialidad tipo = null;
		
		if(tipoVialiad != null) {
			tipo = new TipoVialidad();
			tipo.setClave(tipoVialiad.getClave());
			tipo.setDescripcion(tipoVialiad.getDescripcion());
		}
		
		return tipo;
	}

	@Override
	public TipoAsentamiento convertirTipoAsenDigToTipoAsen(
			mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAse) {
		TipoAsentamiento tipoAsentamiento = null;
		
		if(tipoAse != null) {
			tipoAsentamiento = new TipoAsentamiento();
			tipoAsentamiento.setClave(tipoAse.getClave());
			tipoAsentamiento.setDescripcion(tipoAse.getDescripcion());
		}
		
		return tipoAsentamiento;
	}

	
}
