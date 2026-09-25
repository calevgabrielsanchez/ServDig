/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;


import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.NivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DitConstanciaEstudio;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNivelEducativo;

/**
 * @author ghdolores
 *
 */
public class ConstanciaEstudioParser {

	public static DitConstanciaEstudio modelToPersist(ConstanciaEstudio entrada){
		DitConstanciaEstudio salida=null;
		DitDetalleNivelEducativo detalleNivelEducativo=null;
		if(entrada !=null){
			salida=new DitConstanciaEstudio();
			salida.setCveEscuela(entrada.getClaveEscuela());
			if(entrada.getIdDocumentoProbatorio()!=null && entrada.getIdDocumentoProbatorio()>0)
			salida.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
			salida.setFecFinPeriodo(entrada.getFechaFinPeriodo());
			salida.setFecInicioPeriodo(entrada.getFechaInicioPeriodo());
			salida.setNomEscuela(entrada.getNombreEscuela());
			salida.setNumIncorporacion(entrada.getNoIncorporacion());
			salida.setRefGradoEscolar(entrada.getGradoEscolar());
			
			
			if(entrada.getDetalleNivelEducativo()!=null){
				
				detalleNivelEducativo=new DitDetalleNivelEducativo();
				detalleNivelEducativo.setCveIdDetalleNivelEducativo(entrada.getDetalleNivelEducativo().getIdDetalleNivelEducativo());
			}
			salida.setDetalleNivelEducativo(detalleNivelEducativo);
		}
		
		return salida;	
	}
	
	public static ConstanciaEstudio persisToModel(DitConstanciaEstudio entrada){
		ConstanciaEstudio salida=null;
		if(entrada!=null){
			salida=new ConstanciaEstudio();
			salida.setClaveEscuela(entrada.getCveEscuela());
			salida.setFechaFinPeriodo(entrada.getFecFinPeriodo());
			salida.setFechaInicioPeriodo(entrada.getFecInicioPeriodo());
			salida.setGradoEscolar(entrada.getRefGradoEscolar());
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setNoIncorporacion(entrada.getNumIncorporacion());
			salida.setNombreEscuela(entrada.getNomEscuela());
			if(entrada.getDetalleNivelEducativo()!=null){
				salida.setDetalleNivelEducativo(new DetalleNivelEducativo());
				salida.getDetalleNivelEducativo().setNivelEducativo(new NivelEducativo());
				salida.getDetalleNivelEducativo().getNivelEducativo()
													.setDesNivelEducativo(entrada.getDetalleNivelEducativo()
														.getDicNivelEducativo().getDesNivelEducativo());
				salida.getDetalleNivelEducativo().setTipoNivelEducativo(new TipoNivelEducativo());
				salida.getDetalleNivelEducativo().getTipoNivelEducativo()
													.setDesNivelEducativo(entrada.getDetalleNivelEducativo()
															.getDicTipoNivelEducativo().getDesTipoNivelEducativo());
			}
		}
		return salida;
	}
	
}
