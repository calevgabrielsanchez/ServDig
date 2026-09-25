package mx.gob.imss.dsdir.altapff.batchaltas;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@ComponentScan({"mx.gob.imss.dsdir.altapff.batchaltas","mx.gob.imss.dsdir.altapff.batchaltas.dao","mx.gob.imss.dsdir.altapff.batchaltas.service","mx.gob.imss.dsdir.altapff.batchaltas.service.impl"})
@EnableBatchProcessing
public class BatchaltasApp implements CommandLineRunner {



	public static void main(String[] args) {
		ApplicationContext appCtx = SpringApplication.run(BatchaltasApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Ejecución de tarea batch para la cancelación de solicitudes EN PROCESO!");
	}

}
