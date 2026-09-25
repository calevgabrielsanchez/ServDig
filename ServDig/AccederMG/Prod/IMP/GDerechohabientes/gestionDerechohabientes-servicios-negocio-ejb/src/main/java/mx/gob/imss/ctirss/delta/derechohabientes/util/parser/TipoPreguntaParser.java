//package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;
//
//import mx.gob.imss.ctirss.delta.derechohabientes.model.negocio.TipoPregunta;
//import mx.gob.imss.ctirss.delta.persistence.DicTipoPregunta;
//
//public class TipoPreguntaParser {
//	public static DicTipoPregunta modelToPersist(TipoPregunta entrada){
//		DicTipoPregunta salida=null;
//		if(entrada!=null){
//			 salida = new DicTipoPregunta();
//			 salida.setDesTipoPregunta(entrada.getDescripcion());
//			 salida.setCveIdTipoPregunta(entrada.getIdTipoPregunta());
//		}
//		
//		return salida;	
//	}
//	
//	public static TipoPregunta persisToModel(DicTipoPregunta entrada){
//		TipoPregunta salida=null;
//		if(entrada!=null){
//			salida=new TipoPregunta();
//			salida.setDescripcion(entrada.getDesTipoPregunta() );
//			salida.setIdTipoPregunta(entrada.getCveIdTipoPregunta());
//		}
//		return salida;
//	}
//}
