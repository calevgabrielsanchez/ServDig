/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CalidadParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.FisicaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.RazonRegistroParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UsuarioParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless(name = "registroParserService", mappedName = "registroParserService")
public class RegistroParserService extends AbstractServiceUtility implements RegistroParserServiceLocal{

	@Override
	public DitRegistroDerechohabiente modelToPersist(TramiteRegistroDerechohabiente entrada) throws DerechohabientesBusinessException{
		DitRegistroDerechohabiente salida=null;
		if(entrada!=null){
			try {
				 salida = new DitRegistroDerechohabiente();
				 salida.setCveIdRegDerechohabiente(entrada.getIdRegistroDerechohabiente());
				 salida.setDitTramite(new DitTramite());			 
				 salida.getDitTramite().setCveIdTramite(entrada.getTramiteId());
				 salida.setDicRazonRegistro(new DicRazonRegistro());
				 if(entrada.getRazonRegistro()!=null)
					 salida.getDicRazonRegistro().setCveRazonRegistro(entrada.getRazonRegistro().getIdRazonRegistro());
				
				 salida.setDicCalidadParentesco(new DicCalidadParentesco());
				 salida.getDicCalidadParentesco().setCveIdCalidadParentesco(entrada.getParentesco().getIdParentesco());
				 
				 if(entrada.getFechaRegistroActualizacion()!=null)
					 salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
				 
				 if(entrada.getFechaPresentacion()!=null)
					 salida.setFecRegistroAlta(entrada.getFechaPresentacion());
				 
				 if(entrada.getFechaConclusion()!=null)
					 salida.setFecRegistroBaja(entrada.getFechaConclusion());
				 
				 if(entrada.getEvaluacionCuestionario()!=null)
					 salida.setNumEvaluacionCuestionario(BigDecimal.valueOf(entrada.getEvaluacionCuestionario().longValue()));

				 if(entrada.getDomicilioIdAsegurado()!=null)
					 salida.setDomicilioIdAsegurado(entrada.getDomicilioIdAsegurado());

				 if(entrada.getDomicilioIdBeneficiario()!=null)
					 salida.setDomicilioIdBeneficiario(entrada.getDomicilioIdBeneficiario());

				 if(entrada.getCveIdUmf()!=null)
					 salida.setCveIdUmf(entrada.getCveIdUmf());

				 if(entrada.getCveIdDelegacion()!=null)
					 salida.setCveIdDelegacion(entrada.getCveIdDelegacion());

				 if(entrada.getCveIdSubdelegacion()!=null)
					 salida.setCveIdSubdelegacion(entrada.getCveIdSubdelegacion());

				 if(entrada.getCveEstadoAsegurado()!=null)
					 salida.setCveEstadoAsegurado(entrada.getCveEstadoAsegurado());

				 if(entrada.getCveEstadoBeneficiario()!=null)
					 salida.setCveEstadoBeneficiario(entrada.getCveEstadoBeneficiario());

				 if(entrada.getIndConcubinarioMismoSexo()!=null)
					 salida.setIndConcubinarioMismoSexo(entrada.getIndConcubinarioMismoSexo());

				 if(entrada.getIndConyugeMismoSexo()!=null)
					 salida.setIndConyugeMismoSexo(entrada.getIndConyugeMismoSexo());
					 
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_REGISTRO_DERECHOHABIENTE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_REGISTRO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			 		
		}
		
		return salida;	
	}
	
	@Override
	public TramiteRegistroDerechohabiente persisToModel(DitRegistroDerechohabiente entrada) throws DerechohabientesBusinessException{
		TramiteRegistroDerechohabiente salida=null;
		if(entrada!=null){
			try {
				salida=new TramiteRegistroDerechohabiente();
				salida.setIdRegistroDerechohabiente(entrada.getCveIdRegDerechohabiente());
				if(entrada.getNumEvaluacionCuestionario()!=null)
					salida.setEvaluacionCuestionario(BigInteger.valueOf(entrada.getNumEvaluacionCuestionario().longValue()));
				if(entrada.getDitTramite().getDitTramitePersonaFisica() != null) {
					if(!entrada.getDitTramite().getDitTramitePersonaFisica().isEmpty())
						salida.setFisica(FisicaParser.persisToModel(entrada.getDitTramite().getDitTramitePersonaFisica().get(0).getDitPersona()));
				}
				salida.setParentesco(CalidadParentescoParser.persisToModel(entrada.getDicCalidadParentesco()));
				
				salida.setRazonRegistro(RazonRegistroParser.persisToModel(entrada.getDicRazonRegistro()));
				
				salida.setTramiteId(entrada.getDitTramite().getCveIdTramite());
				
				if(entrada.getFecRegistroActualizado()!=null)
					 salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
				 
				 if(entrada.getFecRegistroAlta()!=null)
					 salida.setFechaPresentacion(entrada.getFecRegistroAlta());
				 
				 if(entrada.getFecRegistroBaja()!=null)
					 salida.setFechaConclusion(entrada.getFecRegistroBaja());
				 salida.setUsuario(UsuarioParser.persisToModel(entrada.getDitUsuario()));
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_REGISTRO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	@Override
	public List<TramiteRegistroDerechohabiente> persisToModelList(List<DitRegistroDerechohabiente> entrada) throws DerechohabientesBusinessException{
		
		
		List<TramiteRegistroDerechohabiente> salida=null;
		if(entrada!=null && entrada.size() > 0){
			salida = new ArrayList<TramiteRegistroDerechohabiente>();
			for (DitRegistroDerechohabiente ditRegistroDerechohabiente : entrada) {
				salida.add(persisToModel(ditRegistroDerechohabiente));
			}			
		}
		return salida;
	}
}
