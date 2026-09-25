package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;

@Stateless( name = "entidadFederativaUtility", mappedName = "entidadFederativaUtility")
public class EntidadFederativaUtility extends AbstractServiceUtility implements EntidadFederativaUtilityLocal{
	
	public DgCatEstado modelToPersist(EntidadFederativa entrada){
		DgCatEstado salida=null;
		if(entrada !=null){
			salida=new DgCatEstado();
			salida.setCveEnt(entrada.getClave());
			salida.setNomEnt(entrada.getNombre());
		}
		
		return salida;	
	}
	
	public EntidadFederativa persisToModel(DgCatEstado entrada){
		EntidadFederativa salida=null;
		if(entrada!=null){
			salida=new EntidadFederativa();
			salida.setClave(entrada.getCveEnt());
			salida.setNombre(entrada.getNomEnt());
		}
		return salida;
	}
	
	public List<EntidadFederativa> persistToModelList(List<DgCatEstado> entrada) {
		List<EntidadFederativa> salida = new ArrayList<EntidadFederativa>();
		
		for(DgCatEstado estado: entrada) {
			salida.add(persisToModel(estado));
		}
		
		return salida;
	}
	
}