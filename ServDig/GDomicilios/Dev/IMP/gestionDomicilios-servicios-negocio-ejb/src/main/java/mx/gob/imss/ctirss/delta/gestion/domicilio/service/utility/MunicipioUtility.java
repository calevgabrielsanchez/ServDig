package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;

@Stateless( name = "municipioUtility", mappedName = "municipioUtility")
public class MunicipioUtility extends AbstractServiceUtility implements MunicipioUtilityLocal{

	@EJB
	private EntidadFederativaUtilityLocal entidadFederativaUtilityLocal;
	
	public Municipio persistToModel(DgCatMunicipio entrada) {
		Municipio salida = null;
		
		if(entrada != null) {
			salida = new Municipio();
			salida.setClave(entrada.getId().getCveMun());
			salida.setNombre(entrada.getNomMun());
			salida.setEntidadFederativa(entidadFederativaUtilityLocal.persisToModel(entrada.getDgCatEstado()));
		}
		
		return salida;
	}
	
	public List<Municipio> persistToModelList(List<DgCatMunicipio> entrada) {
		List<Municipio> salida = null;
		
		if(entrada != null ) {
			salida = new ArrayList<Municipio>();
			
			for(DgCatMunicipio municipio: entrada) {
				salida.add(persistToModel(municipio));
			}
		}
		
		return salida;
	}
}
