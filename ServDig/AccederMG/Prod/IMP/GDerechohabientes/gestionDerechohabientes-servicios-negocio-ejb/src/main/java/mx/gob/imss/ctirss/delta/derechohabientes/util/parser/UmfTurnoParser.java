package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurnoPK;

public class UmfTurnoParser {
	
	private static final Logger logger = Logger.getLogger(UmfTurnoParser.class);

	public static DitUmfTurno modelToPersist(UMFTurno entrada) throws DerechohabientesBusinessException{
		DitUmfTurno salida=null;
		if(entrada!=null){
			try {
				salida=new DitUmfTurno();
				salida.setId(new DitUmfTurnoPK());
				if(entrada.getTurno() != null)
					salida.getId().setCveIdTurno(entrada.getTurno().getIdTurno());
				salida.getId().setCveIdUmf(entrada.getUnidadMedicaFamiliar().getIdUMF());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_UMF_TURNO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_UMF_TURNO+" | "+e.getMessage());
			}	
			
		
		}
		
		return salida;	
	}
	
	public static UMFTurno persisToModel(DitUmfTurno entrada) throws DerechohabientesBusinessException{
		UMFTurno salida=null;
		if(entrada!=null){
			try {
				salida=new UMFTurno();
				salida.setNoCita(new BigInteger(""+entrada.getNumCita()));
				salida.setTurno(TurnoParser.persisToModel(entrada.getDicTurno()));
				salida.setUnidadMedicaFamiliar(UnidadMedicaFamiliarParser.persisToModel(entrada.getDicUmf()));
				salida.setIdUmf(entrada.getId().getCveIdUmf());	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_UMF_TURNO+" | "+e.getMessage());
			}	
			
		}
		return salida;
	}

}
