package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;

public class EntidadFederativaParser {
	public static DgCatEstado modelToPersist(EntidadFederativa entrada){
		DgCatEstado salida=null;
		if(entrada !=null){
			salida=new DgCatEstado();
			salida.setCveEnt(entrada.getClave());
			salida.setNomEnt(entrada.getNombre());
		}
		
		return salida;	
	}
	
	public static EntidadFederativa persisToModel(DgCatEstado entrada){
		EntidadFederativa salida=null;
		if(entrada!=null){
			salida=new EntidadFederativa();
			salida.setClave(entrada.getCveEnt());
			salida.setNombre(entrada.getNomEnt());
		}
		return salida;
	}
	
	public static List<EntidadFederativa> persistToModelList(List<DgCatEstado> entrada) {
		List<EntidadFederativa> salida = new ArrayList<EntidadFederativa>();
		
		for(DgCatEstado estado: entrada) {
			salida.add(persisToModel(estado));
		}
		
		return salida;
	}
	
}