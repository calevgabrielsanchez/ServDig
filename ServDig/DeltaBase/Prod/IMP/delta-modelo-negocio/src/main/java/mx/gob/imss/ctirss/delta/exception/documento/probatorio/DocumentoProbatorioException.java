package mx.gob.imss.ctirss.delta.exception.documento.probatorio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DocumentoProbatorioException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public DocumentoProbatorioException(String message){
		super(message);
		
	}
	
	public DocumentoProbatorioException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public DocumentoProbatorioException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DocumentoProbatorioException 
	 */
	
	public static void throwException(String message) throws DocumentoProbatorioException{
		//Por favor no ensierren entre try catch
		throw new DocumentoProbatorioException(message);
	}
	
	public static void throwException(String message, String situacion) throws DocumentoProbatorioException{
		//Por favor no ensierren entre try catch
		throw new DocumentoProbatorioException(message, situacion);
	}
	

}
