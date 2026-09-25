package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MedicoEspecialidadParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.MedicoFamiliarParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TurnoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.persistence.DicConsultorioUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsultorioTurno;

@Stateless(name = "medicoEnTurnoParserService", mappedName = "medicoEnTurnoParserService")
public class MedicoEnTurnoParserService extends AbstractServiceUtility
		implements MedicoEnTurnoParserServiceLocal {

	@Override
	public DitUmfConsTurnoMedico modelToPersist(MedicoEnTurno entrada)
			throws DerechohabientesBusinessException {
		DitUmfConsTurnoMedico salida=null;
		if(entrada !=null){
			try {
				salida =new DitUmfConsTurnoMedico();
				salida.setCveIdUmfConsTurnoMed(entrada.getIdMedicoContultorioTurno());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_MEDICO_EN_TURNO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_EN_TURNO+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}

	@Override
	public MedicoEnTurno persisToModel(DitUmfConsTurnoMedico entrada)
			throws DerechohabientesBusinessException {
		MedicoEnTurno salida=null;
		if(entrada!=null){
			try {
				salida=new MedicoEnTurno();
				log.debug("El id de la relacion es: " + entrada.getCveIdUmfConsTurnoMed());
				salida.setIdMedicoContultorioTurno(entrada.getCveIdUmfConsTurnoMed());
				salida.setConsultorio(new Consultorio());
				DitUmfConsultorioTurno ditUmfCT = entrada.getDitUmfConsultorioTurno();
				log.debug("El objeto dit umf cons turno es: " + ditUmfCT);
				DicConsultorioUmf dicConUmf = ditUmfCT.getDicConsultorioUmf();
				log.debug("el objeto consultorio es: " + dicConUmf);
				salida.getConsultorio().setIdConsultorio(dicConUmf.getCveNumConsultorio().longValue());
				salida.getConsultorio().setDescripcion(dicConUmf.getDesConsultorio());
				if(entrada.getDitMedicoEspecialidad() != null) {
					salida.setMedicoFamiliar(MedicoFamiliarParser.persisToModel(entrada.getDitMedicoEspecialidad().getDicMedico()));
					salida.getMedicoFamiliar().setIdMedicoEspecialidad(entrada.getDitMedicoEspecialidad().getCveIdMedicoEspecialidad());
					salida.setMedicoEspecialidad(MedicoEspecialidadParser.persisToModel(entrada.getDitMedicoEspecialidad().getDicEspecialidadMedico()));
				}
				salida.setTurno(TurnoParser.persisToModel(ditUmfCT.getDicTurno()));
				salida.setUnidadMedicaFamiliar(UnidadMedicaFamiliarParser.persisToModel(dicConUmf.getDicUmf()));
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_EN_TURNO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

	@Override
	public List<MedicoEnTurno> persisToModelList(
			List<DitUmfConsTurnoMedico> entrada)
			throws DerechohabientesBusinessException {
		List<MedicoEnTurno> salida =null;
				
		if(entrada!=null && entrada.size()>0 ){
			salida= new ArrayList<MedicoEnTurno>();
			
			for (DitUmfConsTurnoMedico ditMedicoEnTurno : entrada) {
				salida.add(persisToModel(ditMedicoEnTurno));
			}
			
		}
		
		
		return salida;
	}

}
