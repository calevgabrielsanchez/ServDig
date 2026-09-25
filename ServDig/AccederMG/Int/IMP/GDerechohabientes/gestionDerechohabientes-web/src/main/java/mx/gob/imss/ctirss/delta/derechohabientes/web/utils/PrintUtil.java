package mx.gob.imss.ctirss.delta.derechohabientes.web.utils;

import net.sf.jasperreports.engine.JasperPrint;

public final class PrintUtil {

	public static void imprimeReporte(JasperPrint print) {

		/*try {
			print.setOrientation(JasperReport.ORIENTATION_PORTRAIT);

			PrinterJob job = PrinterJob.getPrinterJob();

			PrintService service = PrintServiceLookup
					.lookupDefaultPrintService();
			

			job.setPrintService(service);
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			MediaSizeName mediaSizeName = MediaSize.findMedia(
					Float.parseFloat(String.valueOf(8.264)),
					Float.parseFloat(String.valueOf(11.694)),
					MediaPrintableArea.INCH);
			printRequestAttributeSet.add(mediaSizeName);
			printRequestAttributeSet.add(new Copies(1));
			JRPrintServiceExporter exporter;
			exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE, service);
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
					service.getAttributes());
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
					printRequestAttributeSet);
			exporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
					Boolean.FALSE);

			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
