package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FormaMigratoria;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalidadMigratoria;
import mx.gob.imss.ctirss.delta.persistence.DicPai;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadCaracMigrat;
import mx.gob.imss.ctirss.delta.persistence.DitFormaMigratoria;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;


public class FormaMigratoriaParser {
	
	static public DitFormaMigratoria modelToPersist(FormaMigratoria entrada){
		DitFormaMigratoria salida=null;
		
		if(entrada!=null){
			salida=new DitFormaMigratoria();
			salida.setNumeroDoc(entrada.getNumeroDoc());
			salida.setFecExpedicion(entrada.getFechaExpedicion());
			salida.setFecVencimiento(entrada.getFechaVencimiento());
			DicCalidadCaracMigrat calidad = new DicCalidadCaracMigrat(); 
			calidad.setCveIdCalidadCaracMigrat(new Long (entrada.getCalidadMigratoria().getCveIdCalidadCaracMigrat()));;
			salida.setDicCalidadCaracMigrat(calidad);
			DicPai pais = new DicPai();
			pais.setCveIdPais(entrada.getPaisOrigen().getIdPais());
			salida.setDicPai(pais);
//			DitDocumentoProbatorio docProb = new DitDocumentoProbatorio ();
//			docProb.setCveIdDocumentoProbatorio(new Long (entrada.getIdDocumentoProbatorio()));
//			salida.setDitDocumentoProbatorio(docProb);
			
			salida.setFecRegistroAlta(new Date ());
			salida.setFecRegistroActualizado(new Date ());
		
		}
		return salida;
	}
	
	static public FormaMigratoria persistToModel(DitFormaMigratoria entrada){
		FormaMigratoria salida=null;
		
		if(entrada!=null){
			salida=new FormaMigratoria();
			salida.setNumeroDoc(entrada.getNumeroDoc());
			salida.setFechaExpedicion(entrada.getFecExpedicion());
			salida.setFechaVencimiento(entrada.getFecVencimiento());
			CalidadMigratoria calidad = new CalidadMigratoria();
			calidad.setCveIdCalidadCaracMigrat(Long.toString(entrada.getDicCalidadCaracMigrat().getCveIdCalidadCaracMigrat()));
			salida.setCalidadMigratoria(calidad);
			Pais pais = new Pais();
			pais.setIdPais((int)(entrada.getDicPai().getCveIdPais()));
			salida.setPaisOrigen(pais);
			
			salida.setIdDocumentoProbatorio(entrada.getDitDocumentoProbatorio()!=null ? 
					entrada.getDitDocumentoProbatorio().getCveIdDocumentoProbatorio().intValue():null);

		}
	
		return salida;
	}

}
