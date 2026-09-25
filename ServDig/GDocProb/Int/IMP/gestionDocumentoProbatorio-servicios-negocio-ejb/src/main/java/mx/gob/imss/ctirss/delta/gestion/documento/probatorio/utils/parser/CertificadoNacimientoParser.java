package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;


import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoNacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DitCertificadoNacimiento;

public class CertificadoNacimientoParser {

	public static DitCertificadoNacimiento modelToPersist(
			CertificadoNacimiento entrada) {
		DitCertificadoNacimiento salida=null;
		if(entrada!=null){
			
			salida=new DitCertificadoNacimiento();
			if(entrada.getSexo()!=null){
				salida.setDicSexo(new DicSexo());
				salida.getDicSexo().setCveIdSexo(entrada.getSexo().getIdSexo().longValue());
			}	
			
			salida.setDesLugarAlumbramiento(entrada.getDesLugarAlumbramiento());
			salida.setFecAlumbramiento(entrada.getFechaAlumbramiento());
			salida.setRefFolio(entrada.getNoFolio());
		
		}
			
		return salida;
	}
	
	static public CertificadoNacimiento persistToModel(DitCertificadoNacimiento entrada){
		CertificadoNacimiento salida=null;
		if(entrada!=null){
			salida=new CertificadoNacimiento();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setDesLugarAlumbramiento(entrada.getDesLugarAlumbramiento());
			salida.setNoFolio(entrada.getRefFolio());
			salida.setFechaAlumbramiento(entrada.getFecAlumbramiento());
			
			if( entrada.getDicSexo() != null ){
				Sexo sexo = new Sexo();
				sexo.setDescripcion(entrada.getDicSexo().getDesSexo());
				sexo.setIdSexo(entrada.getDicSexo().getCveIdSexo().intValue());
				salida.setSexo(sexo);
			}
			
		}
		return salida;
	}
}
