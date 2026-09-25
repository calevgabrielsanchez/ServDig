package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.utils.FechaUtils;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.enums.TipoCorteEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.task.CorteEstadisticoGeneralTask;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils.CorteEstadisticoProperties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class GeneracionEstadisticasJob {
	private static final double HRS_DIA_ANT = -24;
	private static final String URL_APPLICATION_PROPERTIES = "config/app/application.properties";
	private static final Logger LOG = Logger.getLogger(GeneracionEstadisticasJob.class);

	private static final String GENERAL_CONTEXT = "config/spring/general-context.xml";

	public static void main(String[] args) {
		Date fechaSistema = FechaUtils.getFechaSistema();

		ApplicationContext context = new ClassPathXmlApplicationContext(GENERAL_CONTEXT);
		CorteEstadisticoGeneralTask corteEstadisticoTask = (CorteEstadisticoGeneralTask) context.getBean("corteEstadisticoGeneralTask");

		Integer paramOpcion = Integer.decode(args[0]);
		LOG.debug("parametro dia " + paramOpcion);

		LOG.info("------------->Hora de ejecucion: " + fechaSistema);
		try {
			CorteEstadisticoProperties prop = obtenerPropiedadesEjecucion(context);

			if (paramOpcion.equals(TipoCorteEnum.CORTE_TOTAL_DIA_ANTERIOR.getCodigo())) {
				LOG.debug("Se realizar\u00E1 el corte del d\u00EDa anterior");
				Date fechaDiaAnterior = FechaUtils.sumaHoras(fechaSistema, HRS_DIA_ANT);
				prop.setFechaCorteGeneral(fechaDiaAnterior);

				corteEstadisticoTask.realizarCorteDiaAnterior(prop);
			} else if (paramOpcion.equals(TipoCorteEnum.CORTE_PARCIAL_DIA_ACTUAL.getCodigo())) {
				LOG.debug("Se realizar\u00E1 el corte parcial del d\u00EDa de hoy");
				prop.setFechaCorteGeneral(fechaSistema);

				corteEstadisticoTask.realizarCorteDiaActual(prop);
			} else {
				LOG.debug("Se realizar\u00E1 el corte de un rango espec\u00EDfico");
				corteEstadisticoTask.realizarCorteRango();
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	private static CorteEstadisticoProperties obtenerPropiedadesEjecucion(ApplicationContext context)
			throws IOException {
		CorteEstadisticoProperties corteEstadisticoProperties = new CorteEstadisticoProperties();

		// Obtieniendo propiedades de ejecucion
		Boolean porProperties = new Integer(getValorProperties("general.busqueda_properties")).equals(1);
		if (porProperties) {
			String location = getValorProperties("general.url_archivo");
			Properties prop = new Properties();

			try {
				Resource resource = context.getResource(location);
				InputStream inputStream = resource.getInputStream();
				prop.load(inputStream);
				inputStream.close();

				// Fecha y hora de corte
				corteEstadisticoProperties.setStrHoraCorte(prop.getProperty("canase.corte.hora"));

				String strToleranciaCorte = prop.getProperty("canase.corte.tolerancia");
				Double minutosTolerancia = Double.valueOf(strToleranciaCorte);
				corteEstadisticoProperties.setMinutosTolerancia(minutosTolerancia);

				// Correo
				corteEstadisticoProperties.setUrlMailServer(prop.getProperty("mail.smtp.host"));
				corteEstadisticoProperties.setUserMailServer(prop.getProperty("mail.smtp.user"));
				corteEstadisticoProperties.setPasswordMailServer(prop.getProperty("mail.smtp.password"));
				corteEstadisticoProperties.setRemitenteCorreo(prop.getProperty("mail.smtp.from"));
				corteEstadisticoProperties.setNombreRemitenteCorreo(prop.getProperty("mail.smtp.from.name"));
				corteEstadisticoProperties.setReplyTo(prop.getProperty("mail.smtp.replyto"));
				corteEstadisticoProperties.setReplyToName(prop.getProperty("mail.smtp.replyto.name"));

				// Destinatarios Correos
				String strLstDestinatariosAsigHoy = prop.getProperty("mail.smtp.subjects.asig.hoy");
				String correosAsigHoy[] = strLstDestinatariosAsigHoy.split(",");
				corteEstadisticoProperties.setDestCorteDiaActualAsignacion(correosAsigHoy);

				String strLstDestinatariosAsigAyer = prop.getProperty("mail.smtp.subjects.asig.ayer");
				String correosAsigAyer[] = strLstDestinatariosAsigAyer.split(",");
				corteEstadisticoProperties.setDestCorteDiaAnteriorAsignacion(correosAsigAyer);

				String strLstDestinatariosAsigRango = prop.getProperty("mail.smtp.subjects.asig.rango");
				String correosAsigRango[] = strLstDestinatariosAsigRango.split(",");
				corteEstadisticoProperties.setDestCorteRangoAsignacion(correosAsigRango);


				String strLstDestinatariosAltaPatHoy = prop.getProperty("mail.smtp.subjects.altapat.hoy");
				String correosAltaPatHoy[] = strLstDestinatariosAltaPatHoy.split(",");
				corteEstadisticoProperties.setDestCorteDiaActualAltaPat(correosAltaPatHoy);

				String strLstDestinatariosAltaPatAyer = prop.getProperty("mail.smtp.subjects.altapat.ayer");
				String correosAltaPatAyer[] = strLstDestinatariosAltaPatAyer.split(",");
				corteEstadisticoProperties.setDestCorteDiaAnteriorAltaPat(correosAltaPatAyer);

				String strLstDestinatariosAltaPatRango = prop.getProperty("mail.smtp.subjects.altapat.rango");
				String correosAltaPatRango[] = strLstDestinatariosAltaPatRango.split(",");
				corteEstadisticoProperties.setDestCorteRangoAltaPat(correosAltaPatRango);


				String strLstDestinatariosModifClasVentHoy = prop.getProperty("mail.smtp.subjects.modifclas.vent.hoy");
				String correosModifClasVentHoy[] = strLstDestinatariosModifClasVentHoy.split(",");
				corteEstadisticoProperties.setDestCorteDiaActualModifClasifVent(correosModifClasVentHoy);

				String strLstDestinatariosModifClasVentAyer = prop.getProperty("mail.smtp.subjects.modifclas.vent.ayer");
				String correosModifClasVentAyer[] = strLstDestinatariosModifClasVentAyer.split(",");
				corteEstadisticoProperties.setDestCorteDiaAnteriorModifClasifVent(correosModifClasVentAyer);

				String strLstDestinatariosModifClasVentRango = prop.getProperty("mail.smtp.subjects.modifclas.vent.rango");
				String correosModifClasVentRango[] = strLstDestinatariosModifClasVentRango.split(",");
				corteEstadisticoProperties.setDestCorteRangoModifClasifVent(correosModifClasVentRango);


				String strLstDestinatariosModifClasExtHoy = prop.getProperty("mail.smtp.subjects.modifclas.ext.hoy");
				String correosModifClasExtHoy[] = strLstDestinatariosModifClasExtHoy.split(",");
				corteEstadisticoProperties.setDestCorteDiaActualModifClasifExt(correosModifClasExtHoy);
				
				String strLstDestinatariosModifClasExtAyer = prop.getProperty("mail.smtp.subjects.modifclas.ext.ayer");
				String correosModifClasExtAyer[] = strLstDestinatariosModifClasExtAyer.split(",");
				corteEstadisticoProperties.setDestCorteDiaAnteriorModifClasifExt(correosModifClasExtAyer);

				String strLstDestinatariosModifClasExtRango = prop.getProperty("mail.smtp.subjects.modifclas.ext.rango");
				String correosModifClasExtRango[] = strLstDestinatariosModifClasExtRango.split(",");
				corteEstadisticoProperties.setDestCorteRangoModifClasifExt(correosModifClasExtRango);


				String strLstDestinatariosRissHoy = prop.getProperty("mail.smtp.subjects.riss.hoy");
				String correosRissHoy[] = strLstDestinatariosRissHoy.split(",");
				corteEstadisticoProperties.setDestCorteDiaActualAltaRiss(correosRissHoy);
				
				String strLstDestinatariosRissAyer = prop.getProperty("mail.smtp.subjects.riss.ayer");
				String correosRissAyer[] = strLstDestinatariosRissAyer.split(",");
				corteEstadisticoProperties.setDestCorteDiaAnteriorAltaRiss(correosRissAyer);
				
				String strLstDestinatariosRissRango = prop.getProperty("mail.smtp.subjects.riss.rango");
				String correosRissRango[] = strLstDestinatariosRissRango.split(",");
				corteEstadisticoProperties.setDestCorteRangoModifAltaRiss(correosRissRango);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return corteEstadisticoProperties;
	}

	private static String getValorProperties(String propiedad) {
		String propertie = null;
		Properties properties = new Properties();

		try {
			InputStream inputStream = new ClassPathResource(
					URL_APPLICATION_PROPERTIES).getInputStream();
			properties.load(inputStream);
			inputStream.close();

			propertie = properties.getProperty(propiedad);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return propertie;
	}
}
