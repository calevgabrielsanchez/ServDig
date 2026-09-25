package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamientoPK;

@Stateless(name = "asentamientoUtility", mappedName = "asentamientoUtility")
public class AsentamientoUtility extends AbstractServiceUtility implements AsentamientoUtilityLocal{
	
	@EJB
	private EntidadFederativaUtilityLocal entidadFederativaUtilityLocal;
	
	public DgAsentamiento modelToPersist(Asentamiento entrada){
		DgAsentamiento salida=null;
		if(entrada !=null){
			salida=new DgAsentamiento();
			
			//obtener asentamiento
			DgAsentamientoPK asentamientoPK=new DgAsentamientoPK();
			asentamientoPK.setCveAsen(getCveAsen(entrada));
			asentamientoPK.setCveEnt(getCveEnt(entrada));
			asentamientoPK.setCveMun(getCveMun(entrada));
			salida.setId(asentamientoPK);
		
		}
		
		return salida;	
	}
	
	public Asentamiento persisToModel(DgAsentamiento entrada){
		Asentamiento salida=null;
		if(entrada!=null){
			salida=new Asentamiento();
			salida.setClave(entrada.getId().getCveAsen());
			salida.setNombre(entrada.getNomAsen());
			Localidad localidad = new Localidad();
			Municipio municipio = new Municipio();
			
			municipio.setClave(entrada.getDgCatMunicipio().getId().getCveMun());
			municipio.setNombre(entrada.getDgCatMunicipio().getNomMun());
			EntidadFederativa entidadFederativa = new EntidadFederativa();
			entidadFederativa = entidadFederativaUtilityLocal.persisToModel(entrada.getDgCatMunicipio().getDgCatEstado());
			municipio.setEntidadFederativa(entidadFederativa);
			localidad.setMunicipio(municipio);
			salida.setLocalidad(localidad);
			//TODO localidad
			
		}
		return salida;
	}
	
	private String getCveAsen(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	
	private String getCveEnt(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getLocalidad().getMunicipio().getEntidadFederativa().getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}

	private String getCveMun(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getLocalidad().getMunicipio().getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	
	
}
