package mx.gob.imss.dsdir.altapff.batchaltas;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import mx.gob.imss.dsdir.altapff.batchaltas.tasklet.UpdateEstadoSolicitudesAltaTask;

@Configuration
public class JobConfiguration {
	  @Autowired
	  private JobBuilderFactory jobBuilderFactory;
	  
	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  
	  
	  @Autowired
	  private UpdateEstadoSolicitudesAltaTask updateEstadoSolicitudesAltaTask;
	  
	  @Qualifier("secondaryDatasource")
	  @Autowired
	  private DataSource dataSource;
	  

	  
		@Bean
		public Step stepActualiza(){
			return stepBuilderFactory.get("stepActualizaSolicitudesAlta")
					.tasklet(updateEstadoSolicitudesAltaTask)
					.build();
		}


		@Bean
		public Job actualizaJob(){
			return jobBuilderFactory.get("actualizaJobAltas")
					.start(stepActualiza())
					.build();		
		}
		
		
	  
}
