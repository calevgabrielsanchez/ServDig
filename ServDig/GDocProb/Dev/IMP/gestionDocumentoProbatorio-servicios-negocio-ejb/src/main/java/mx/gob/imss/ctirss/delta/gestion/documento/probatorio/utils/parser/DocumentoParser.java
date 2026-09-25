package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.persistence.DicDocumento;




public class DocumentoParser {
	public static Documento persisToModel(DicDocumento entrada){
		Documento salida=null;
		if(entrada !=null){
			salida = new Documento();
			System.out.println("Id del documento: " + entrada.getCveIdDocumento());
			salida.setCveIdDocumento(entrada.getCveIdDocumento());
			System.out.println("Descripcion del documento: " + entrada.getDesDocumento());
			salida.setDesDocumento(entrada.getDesDocumento());
			
		}
		
		return salida;	
	}
	public static DicDocumento persistToModel(Documento entrada) {
		DicDocumento salida=null;
		if(entrada !=null){
			salida = new DicDocumento();
			salida.setCveIdDocumento(entrada.getCveIdDocumento());
			salida.setDesDocumento(entrada.getDesDocumento());
			
		}	
		return salida;	
	}
	
	
	
	public static List<Documento> PersistToModelList(List<DicDocumento> entradaList){
		List<Documento> salidaList=new ArrayList<Documento>();
		for(DicDocumento entrada:entradaList){
			salidaList.add(DocumentoParser.persisToModel(entrada));
		}
		return salidaList;
	}

	

}