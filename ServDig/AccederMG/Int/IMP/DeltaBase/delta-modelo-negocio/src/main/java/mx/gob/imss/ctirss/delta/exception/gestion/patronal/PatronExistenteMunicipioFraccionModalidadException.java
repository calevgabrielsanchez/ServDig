package mx.gob.imss.ctirss.delta.exception.gestion.patronal;


import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
public class PatronExistenteMunicipioFraccionModalidadException extends AbstractException {
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Actualmente cuenta con un registro patronal con la misma actividad seleccionada, por favor seleccione una nueva clasificación";
	private static final Integer codigo = new Integer (110);
	
	public PatronExistenteMunicipioFraccionModalidadException(){
		super(situacion , codigo);
	}
	
	public PatronExistenteMunicipioFraccionModalidadException(String message){
		super(message , codigo);
	}

}