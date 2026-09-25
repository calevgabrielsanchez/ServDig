package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;



import java.util.Date;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;
import mx.gob.imss.ctirss.delta.persistence.DitActa;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;



public class ActaParser {
	private static final String DEFAULT_ENTERO="0";
	static public DitActa modelToPersist(Acta entrada){
		
		DitActa salida=null;
		if(entrada!=null){
			salida=new DitActa();
			salida.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
			if(entrada.getFechaSuceso() == null){
				entrada.setFechaSuceso(new Date());
			}
			salida.setFecSuceso(entrada.getFechaSuceso());
			if(entrada.getNoActa()==null){
				entrada.setNoActa(DEFAULT_ENTERO);
			}
			salida.setNumActa(entrada.getNoActa().toString());
			
			if(entrada.getNoFoja()==null){
				entrada.setNoFoja(DEFAULT_ENTERO);
			}
			salida.setNumFoja(entrada.getNoFoja().toString());
			
			if(entrada.getNoJuzgado()==null){
				entrada.setNoJuzgado("00");
			}
			salida.setNumJuzgado(entrada.getNoJuzgado());
			
			
			salida.setNumLibro(entrada.getNoLibro().toString());
			salida.setRefNumTomo(entrada.getTomo());
			
			if(entrada.getMunicipio()!=null){
				salida.setDgCatMunicipio(new DgCatMunicipio());
				salida.getDgCatMunicipio().setId(new DgCatMunicipioPK());
				salida.getDgCatMunicipio().getId().setCveMun(entrada.getMunicipio().getClave());
				salida.getDgCatMunicipio().getId().setCveEnt(entrada.getMunicipio().getEntidadFederativa().getClave());
			}
			
			if(entrada.getIdDocumentoProbatorio() != null && entrada.getIdDocumentoProbatorio() > 0){
				salida.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
				salida.setDitDocumentoProbatorio(new DitDocumentoProbatorio());
				salida.getDitDocumentoProbatorio().setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
			}

		}
		return salida;
	}
	
	static public Acta persistToModel(DitActa entrada){
		Acta salida=null;
		if(entrada!=null){
			salida=new Acta();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setFechaSuceso(entrada.getFecSuceso());
			salida.setNoActa(entrada.getNumActa());
			salida.setNoFoja(entrada.getNumFoja());
			salida.setNoJuzgado(entrada.getNumJuzgado());
			salida.setNoLibro(entrada.getNumLibro());
			salida.setTomo(entrada.getRefNumTomo());
			if(entrada.getDgCatMunicipio()!=null){
				salida.setMunicipio(new Municipio());
				salida.getMunicipio().setNombre(entrada.getDgCatMunicipio().getNomMun());
				salida.getMunicipio().setEntidadFederativa(new EntidadFederativa());
				salida.getMunicipio().getEntidadFederativa().setNombre(entrada.getDgCatMunicipio().getDgCatEstado().getNomEnt());
			}
			
		}
		return salida;
	}

}
