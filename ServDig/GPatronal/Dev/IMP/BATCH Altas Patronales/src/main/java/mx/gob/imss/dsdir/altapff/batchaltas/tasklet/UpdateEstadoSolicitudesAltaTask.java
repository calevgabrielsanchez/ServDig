package mx.gob.imss.dsdir.altapff.batchaltas.tasklet;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Repository;
import mx.gob.imss.dsdir.altapff.batchaltas.enums.*;

import mx.gob.imss.dsdir.altapff.batchaltas.service.ActualizaEstadosService;


@Repository
public class UpdateEstadoSolicitudesAltaTask implements Tasklet {

	private static final Logger LOG = LogManager.getLogger(UpdateEstadoSolicitudesAltaTask.class);


	@Resource
	private ActualizaEstadosService service;


	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		try{
		LOG.info("########## OBTENIENDO SOLICITUDES EN PROCESO ##########");
		List<Long> solicitudesEnProceso = service.recuperarSolicitudesACancelar();
//		List<Long> solicitudesEnProceso = solicitudesRepository.findEnProcesoSolicitudes();
		LOG.info("########## OBTENIENDO SOLICITUDES A TERMINAR ##########");		
		List<Long> solicitdesAConcluir = service.recuperarSolicitudesATerminar();
//		List<Long> solicitdesAConcluir = solicitudesRepository.findEnProcesoSolicitudesConDocs();
		if( solicitudesEnProceso != null  && !solicitudesEnProceso.isEmpty() ){
			LOG.info("########## SE PROCEDE A LA ACTUALIZACION DE [{}] ARCHIVOS DE MOVIMIENTOS AFILIATORIOS ##########", solicitudesEnProceso.size());
			service.actualizaEstadoSolicitudes(solicitudesEnProceso, EstatusSolicitud.CANCELADA.getEstatus()); //3
			service.actualizaEstadoTramites(solicitudesEnProceso, EstatusTramite.CANCELADO.getEstatus()); //7
			
		}
		if(solicitdesAConcluir != null && !solicitdesAConcluir.isEmpty()){
			LOG.info("########## SE PROCEDE A LA ACTUALIZACION DE [{}] ARCHIVOS DE MOVIMIENTOS AFILIATORIOS ##########", solicitdesAConcluir.size());
			service.actualizaEstadoSolicitudes(solicitdesAConcluir, EstatusSolicitud.ATENDIDA.getEstatus());//2
			service.actualizaEstadoTramites(solicitdesAConcluir, EstatusTramite.CERRADO.getEstatus());	//2
		}
		
		}catch (Exception e){
			LOG.error("Error al ejecutar el ultimo step de solicitudes en proceso", e);
		}
		
		return RepeatStatus.FINISHED;
	}

}
