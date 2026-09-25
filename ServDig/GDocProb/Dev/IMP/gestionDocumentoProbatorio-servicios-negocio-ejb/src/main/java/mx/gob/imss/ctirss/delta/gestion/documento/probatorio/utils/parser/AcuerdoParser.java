package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;

import mx.gob.imss.ctirss.delta.persistence.DitAcuerdo;

public class AcuerdoParser {

	public static DitAcuerdo modelToPersist(Acuerdo entrada) {
		DitAcuerdo salida=null;
		if(entrada!=null){
			salida=new DitAcuerdo();
			salida.setNumAcuerdo(entrada.getNoAcuerdo());
			salida.setRefInstanciaEmiteRes(entrada.getInstanciaEmiteRes());
		}
		return salida;
	}
	static public Acuerdo persistToModel(DitAcuerdo entrada){
		Acuerdo salida=null;
		if(entrada!=null){
			salida=new Acuerdo();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setNoAcuerdo(entrada.getNumAcuerdo());
			salida.setInstanciaEmiteRes(entrada.getRefInstanciaEmiteRes());
	
		}
		return salida;
	}

}
