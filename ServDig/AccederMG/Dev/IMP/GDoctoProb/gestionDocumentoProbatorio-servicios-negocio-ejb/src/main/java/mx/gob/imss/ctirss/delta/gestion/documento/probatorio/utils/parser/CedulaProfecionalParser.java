package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.persistence.DitCedulaProfesional;

public class CedulaProfecionalParser {

	public static DitCedulaProfesional modelToPersist(CedulaProfesional entrada) {
		DitCedulaProfesional salida=null;
		if(entrada!=null){
			salida=new DitCedulaProfesional();
			salida.setNomProfesion(entrada.getProfesion());
			salida.setNumCedula(entrada.getCedula());
		}
		return salida;
	}
	static public CedulaProfesional persistToModel(DitCedulaProfesional entrada){
		CedulaProfesional salida=null;
		if(entrada!=null){
			salida=new CedulaProfesional();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setCedula(entrada.getNumCedula());
			salida.setProfesion(entrada.getNomProfesion());
			
		}
		return salida;
	}

}
