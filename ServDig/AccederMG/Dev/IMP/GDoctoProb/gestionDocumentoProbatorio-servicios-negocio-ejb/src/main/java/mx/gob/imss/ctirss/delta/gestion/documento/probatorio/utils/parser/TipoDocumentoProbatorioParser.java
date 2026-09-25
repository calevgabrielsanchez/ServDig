package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDocumentoProbatorio;

public class TipoDocumentoProbatorioParser {

		public static DicTipoDocumentoProbatorio modelToPersist(TipoDocumentoProbatorio entrada){
			DicTipoDocumentoProbatorio salida=null;
			if(entrada !=null){
				salida=new DicTipoDocumentoProbatorio();
				salida.setCveIdTipoDocumentoProbator(entrada.getIdTipoDocumentoProbatorio());
				salida.setDesTipoDocumentoProbatorio(entrada.getDescripcion());
				
			}
			
			return salida;	
		}
		
		public static TipoDocumentoProbatorio persisToModel(DicTipoDocumentoProbatorio entrada){
			TipoDocumentoProbatorio salida=null;
			if(entrada!=null){
				salida = new TipoDocumentoProbatorio();
				salida.setDescripcion(entrada.getDesTipoDocumentoProbatorio());
				salida.setIdTipoDocumentoProbatorio(new Integer(""+entrada.getCveIdTipoDocumentoProbator()));
			}
			return salida;
		}
		
		public static List<TipoDocumentoProbatorio> persistToModelList(List<DicTipoDocumentoProbatorio> dicTipos) {
			List<TipoDocumentoProbatorio> tipos = null;
			
			if(dicTipos != null && !dicTipos.isEmpty()) {
				tipos = new ArrayList<TipoDocumentoProbatorio>();
				
				for(DicTipoDocumentoProbatorio dicTipo: dicTipos) {
					tipos.add(persisToModel(dicTipo));
				}
			}
			
			return tipos;
		}
}
