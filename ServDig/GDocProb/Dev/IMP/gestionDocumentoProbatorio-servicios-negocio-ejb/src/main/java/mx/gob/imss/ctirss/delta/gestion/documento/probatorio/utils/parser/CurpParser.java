package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;

import mx.gob.imss.ctirss.delta.persistence.DitCurp;


public class CurpParser {

	public static DitCurp modelToPersist(CURP entrada) {
		DitCurp salida =null;
		if(entrada!=null){
			salida=new DitCurp();
			salida.setCveCrip(entrada.getCrip());
			salida.setCveCurp(entrada.getCurp());
			if(entrada.getMunicipio()!=null){
				salida.setDgCatMunicipio(new DgCatMunicipio());
				salida.getDgCatMunicipio().setId(new DgCatMunicipioPK());
				salida.getDgCatMunicipio().getId().setCveMun(entrada.getMunicipio().getClave());
				salida.getDgCatMunicipio().getId().setCveEnt(entrada.getMunicipio().getEntidadFederativa().getClave());
			}
			salida.setFecInscripcion(entrada.getFechaInscripcion());
			salida.setNumActa(entrada.getNoActa());
			if(entrada.getAnioRegistro()!=null){
				salida.setNumAnio(new BigDecimal(entrada.getAnioRegistro()));
			}
			if(entrada.getNoFoja()==null){
				entrada.setNoFoja("N/A");
			}
			salida.setNumFoja(entrada.getNoFoja());
			salida.setNumLibro(entrada.getNoLibro());
			salida.setNumTomo(entrada.getNoTomo());
			salida.setRefFolio(entrada.getRefFolio());
			salida.setNumTipoDocRenapo(new Long(0));
			salida.setNumFolioExtranjero(entrada.getNumFolioExtranjero());
			if(entrada.getNumTipoDocumento()==null){
				entrada.setNumTipoDocumento(new Long(1));
			}
			salida.setNumTipoDocRenapo(entrada.getNumTipoDocumento());
			
		}
		
		return salida;
	}
	public static CURP persisToModel(DitCurp entrada){
		CURP salida=null;
		if(entrada!=null){
			salida=new CURP();
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setCrip(entrada.getCveCrip());
			salida.setCurp(entrada.getCveCurp());
			salida.setFechaInscripcion(entrada.getFecInscripcion());
			salida.setNoActa(entrada.getNumActa());
			salida.setAnioRegistro(entrada.getNumAnio().longValue());
			salida.setNumFolioExtranjero(entrada.getNumFolioExtranjero());
			salida.setNoLibro(entrada.getNumLibro());
			salida.setNoTomo(entrada.getNumTomo());
			salida.setRefFolio(entrada.getRefFolio());
			salida.setNoFoja(entrada.getNumFoja());
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
