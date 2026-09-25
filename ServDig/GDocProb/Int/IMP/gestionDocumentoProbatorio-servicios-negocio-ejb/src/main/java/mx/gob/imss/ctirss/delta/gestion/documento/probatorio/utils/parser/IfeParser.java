package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Ife;
import mx.gob.imss.ctirss.delta.persistence.DitCredElector;

import org.apache.commons.lang.StringUtils;

public class IfeParser {

	public static DitCredElector modelToPersist(Ife entrada) {
		DitCredElector salida=null;
			if(entrada!=null){
				salida=new DitCredElector();
				salida.setNumAnioRegistro(new BigDecimal(entrada.getAnioRegistro()));
				if(entrada.getEmision() != null && StringUtils.isNotBlank(entrada.getEmision())) {
					System.out.println("El numero de emision es : " + entrada.getEmision());
					salida.setNumEmision(new BigDecimal(entrada.getEmision()));
				}
				salida.setRefCodigoSeguridad(entrada.getCodigoSeguridad());
				salida.setCveElector(entrada.getClaveElector());
				salida.setRefModeloCredencial(entrada.getTipoCredencial());
			}
		return salida;
	}
	
	public static Ife persistToModel(DitCredElector entrada) {
		Ife salida = null;
		
		if(entrada!= null) {
			salida = new Ife();
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setAnioRegistro(entrada.getNumAnioRegistro().toBigInteger());
			salida.setFolio(entrada.getRefFolio());
			salida.setClaveElector(entrada.getCveElector());
			salida.setCodigoSeguridad(entrada.getRefCodigoSeguridad());
			salida.setEmision(entrada.getNumEmision().toPlainString());
			if(entrada.getDgCatLocalidad()!=null){
				salida.setLocalidad(new Localidad());
				salida.getLocalidad().setNombre(entrada.getDgCatLocalidad().getNomLoc());
				salida.getLocalidad().setMunicipio(new Municipio());
				salida.getLocalidad().getMunicipio().setNombre(entrada.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
				salida.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
				salida.getLocalidad().getMunicipio().getEntidadFederativa().setNombre(entrada.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			}
			
			/*
			salida.setEstado(EntidadFederativaParser.persisToModel(entrada.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado()));
			salida.setMunicipio(new Municipio());
			salida.getMunicipio().setClave(entrada.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			salida.getMunicipio().setNombre(entrada.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			salida.setLocalidad(new Localidad());
			salida.getLocalidad().setClave(entrada.getDgCatLocalidad().getId().getCveLoc());
			salida.getLocalidad().setNombre(entrada.getDgCatLocalidad().getNomLoc());
			*/
			
		}
		return salida;
	}

}
