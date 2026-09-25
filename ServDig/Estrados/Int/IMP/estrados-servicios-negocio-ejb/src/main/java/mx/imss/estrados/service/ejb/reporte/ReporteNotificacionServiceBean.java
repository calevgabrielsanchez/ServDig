package mx.imss.estrados.service.ejb.reporte;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegPortType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegService;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ObjectFactory;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ResultadoType;
import mx.gob.imss.ctirss.delta.model.firma.Archivo;
import mx.gob.imss.ctirss.delta.model.firma.PeticionGuardadoArchivosFirma;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaGuardadoArchivosFirma;
import mx.imss.estrados.commons.Constantes;
import mx.imss.estrados.service.interfaces.ReporteNotificacionServiceRemote;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Stateless(name = "reporteNotificacionServiceBean", mappedName = "reporteNotificacionServiceBean")
public class ReporteNotificacionServiceBean implements ReporteNotificacionServiceRemote {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ReporteNotificacionServiceBean.class);
	
	/**
	 * Metodo para generar diferentes tipos de reportes de las notificaciones como son: 
	 * Acuse de Registro, Acuse de Publicación, Acuse de Retiro
	 * @param jsonDocumentosAdjuntosDTO
	 * @return boolean
	 */
	@Override
	public boolean generarReportePDF(Map<String, Object> parametrosReporte, Integer tipoReporte) {
		byte[] byteArray = null;
		String rutaJasper = null;
		boolean flagEstado=false;
		
		if (parametrosReporte == null) {
			parametrosReporte = new HashMap<String, Object>();
		}
		
		parametrosLayout(parametrosReporte);
		
		BufferedImage imagenQR=generadorCodigoQrCadena(parametrosReporte.get("tramite").toString(), 250, 250);
		parametrosReporte.put("codigoQR", imagenQR);
		
		switch (tipoReporte) {
			case Constantes.TIPO_REPORTE_REGISTRO:
				rutaJasper = Constantes.RUTA_REPORTES + "plantillasReportes/reporteAcuseRegistro.jasper";
				byteArray = obtenerPDFByteArray(rutaJasper, parametrosReporte);
				if(byteArray!=null){
					guardarArchivoFirmado(parametrosReporte.get("tramite").toString(), generaArchivo(byteArray, Constantes.NOMBRE_ACUSE_REGISTRO));
				}
				break;
					
			case Constantes.TIPO_REPORTE_PUBLICADA:
				rutaJasper = Constantes.RUTA_REPORTES + "plantillasReportes/reporteAcusePublicacion.jasper";
				byteArray = obtenerPDFByteArray(rutaJasper, parametrosReporte);
				if(byteArray!=null){
					guardarArchivoFirmado(parametrosReporte.get("tramite").toString(), generaArchivo(byteArray, Constantes.NOMBRE_ACUSE_PUBLICACION));
				}
				break;
					     
			case Constantes.TIPO_REPORTE_RETIRO:
				rutaJasper = Constantes.RUTA_REPORTES + "plantillasReportes/reporteAcuseRetiro.jasper";
				byteArray = obtenerPDFByteArray(rutaJasper, parametrosReporte);
				if(byteArray!=null){
					guardarArchivoFirmado(parametrosReporte.get("tramite").toString(), generaArchivo(byteArray, Constantes.NOMBRE_ACUSE_RETIRO));
				}
				break;
		}
		try{
			File pdf = File.createTempFile("adjunto", ".pdf");
			OutputStream out = new FileOutputStream(pdf.getAbsolutePath());
			out.write(byteArray);
			out.close();
			
			// Se comenta linea para que se publiquen las notificaciones no publicadas y no envie el correo electronico
			enviarMail((String)parametrosReporte.get("correo"), (String)parametrosReporte.get("subject"), (String)parametrosReporte.get("cuerpo"), pdf.getAbsolutePath());
			
			
			pdf.deleteOnExit();
			logger.info("Correo enviado Exitosamente");
		} catch (IOException e) {
			logger.info("Error al enviar el correo");
			e.printStackTrace();
		}
		return flagEstado;
	}
	
	/**
	 * Metodo que nos sirve para mandar los parametros de los layout de los reportes
	 * @param parametrosReporte
	 */
	private void parametrosLayout(Map<String, Object> parametrosReporte) {
		parametrosReporte.put("rutaImagenGob", Constantes.RUTA_REPORTES + "imagenes/gobierno_federal.png");
//		parametrosReporte.put("rutaImagenIMSS", Constantes.RUTA_REPORTES + "imagenes/logo_imss.jpg");
		parametrosReporte.put("rutaImagenQR", Constantes.RUTA_REPORTES + "imagenes/codigo_qr.png");
	}
	
	/**
	 * Metodo que construye el reporte con los parametros enviados y es creado como un arreglo de bytes 
	 * @param rutaJasper
	 * @param parametrosReporte
	 * @return byte[]
	 */
	public byte[] obtenerPDFByteArray(String rutaJasper, Map<String, Object> parametrosReporte) {
		byte[] byteArray = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(rutaJasper);
			byteArray = JasperRunManager.runReportToPdf(inputStream, parametrosReporte, new JREmptyDataSource());
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			logger.error("ERROR: No se encontro el archivo jasper ", ex);
		} catch (JRException ex) {
			ex.printStackTrace();
			logger.error("ERROR: No se pudo crear el arreglo de byte del reporte ", ex);
		} finally {
            if (inputStream != null) {
                try {
                	inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logger.error("ERROR: No se pudo cerrar el inputStream ", ex);
                }
            }
        }
		return byteArray;
	}
	
	public int guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo) {
		String jsonParams= "";
		int estado=-1;
		ObjectMapper mapper = new ObjectMapper();
		PeticionGuardadoArchivosFirma peticion = new PeticionGuardadoArchivosFirma();
      
		List<Archivo> archivos = new ArrayList<Archivo>();
		archivos.add(archivo);
      
		peticion.setTramite(secuenciaNotaria);
		peticion.setArchivos(archivos);
      
		try {
			jsonParams = mapper.writeValueAsString(peticion);
		} catch (JsonGenerationException e) {
			System.out.println("Error en el parseo a JSON"+ e);        	   
			return estado;
		} catch (JsonMappingException e) {
			System.out.println("Error en el parseo a JSON" + e);
           	return estado;
		} catch (IOException e) {
			System.out.println("Error en el parseo a JSON " + e);
			return estado;
		}
      
		System.out.println("Asi quedo el archivo objeto JSON: " + jsonParams);
      
		ObjectFactory of = new ObjectFactory();
		RegistroSeguimientoRequestType peticionSeguimiento = of.createRegistroSeguimientoRequestType();
		peticionSeguimiento.setJsonParms(jsonParams);
      
		FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
		FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();

		RegistroSeguimientoResponseType respuestaPeticion = firmaElectronicaSegPortType.registroSeguimiento(peticionSeguimiento);

		ResultadoType resultado = respuestaPeticion.getResultado();
		estado=resultado.getCodigo();
		System.out.println("Resultado "+resultado.getCodigo());
		System.out.println("Codigo "+resultado.getTexto());
		System.out.println("Json "+respuestaPeticion.getJsonSalida());
		System.out.println("Salida");
		if(resultado.getCodigo() == 0) {
			try {
				RespuestaGuardadoArchivosFirma respuestaGuardado = mapper.readValue(respuestaPeticion.getJsonSalida(), RespuestaGuardadoArchivosFirma.class);
				System.out.println("Archivo Enviado exitosamente");
				System.out.println("Id del documento: "+ respuestaGuardado.getArchivos().get(0).getId());
				System.out.println("Nombre del archivo: " + respuestaGuardado.getArchivos().get(0).getNombre());
			} catch (JsonParseException e) {
				e.printStackTrace();
				System.out.println("Error en el parseo a JSON "+ e);
			} catch (JsonMappingException e) {
				e.printStackTrace();
				System.out.println("Error en el parseo a JSON " + e);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error en el parseo a JSON "+ e);
			}
			System.out.println("El archivo ha sido guardado correctamente");
		} else {
			System.out.println("Codigo: " + resultado.getCodigo());
			System.out.println("Descripcion: " + resultado.getTexto());
		} 
		return estado;
	}
	
	public Archivo generaArchivo(byte[] bytes,String nombreArchivo) {
		String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(bytes);
		Archivo archivo = new Archivo();
		archivo.setNombre(nombreArchivo);
		archivo.setBuffer(acusePdf);
		return archivo;
	}
	
	void enviaM(){
		 Properties props = new Properties();
	      props.put("mail.smtp.host", "relay.imss.gob.mx");
	      props.put("mail.smtp.auth", "false");
	      props.put("mail.smtp.port", "25");
	      Session session = Session.getDefaultInstance(props, null);
	      String from = "imss.digital.alerts@imss.gob.mx";
	      String to = "ariel.lopez@imss.gob.mx";
	      String subject = "Monitoreo Sesiones OpenAM";
	      Message msg = new MimeMessage(session);

	      try {
			msg.setFrom(new InternetAddress(from));
			 msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		      Address[] correo = new Address[10];
		      correo[0] = new InternetAddress("adolgo.meza@imss.gob.mx");
		  
		      msg.setRecipients(Message.RecipientType.CC, correo);
		      msg.setSubject(subject);
		      msg.setText("asdfasdf");
		      Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	     
	     

	     
	}
	
	
	void enviarMail(String email, String subject, String cuerpo, String rutaOriginal) {
		try{
		
			Properties props = new Properties();
	        //props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
	        props.setProperty("mail.smtp.port", "25");
	        props.setProperty("mail.smtp.user", "denuncia.enlinea@imss.gob.mx");
	        props.setProperty("mail.smtp.auth", "false");

	        Session session = Session.getDefaultInstance(props);
	
	        MimeBodyPart texto = new MimeBodyPart();
//	        texto.setText(cuerpo,"UTF-8");
	        texto.setContent(cuerpo, "text/html");
	        if (rutaOriginal==null) {
	        	rutaOriginal ="";
	        }
	        MimeBodyPart adjunto = new MimeBodyPart();
	        FileDataSource fds=new FileDataSource(rutaOriginal);
	        adjunto.setDataHandler(new DataHandler(fds));
	        adjunto.setFileName(fds.getName());
	        
	        MimeMultipart multiParte = new MimeMultipart();
	        multiParte.addBodyPart(texto);
	        if(rutaOriginal!=null && !rutaOriginal.equals("")) {
	        	multiParte.addBodyPart(adjunto);
	        }

	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("estrados.enlinea@imss.gob.mx"));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        message.setSubject(subject,"UTF-8");
	//      message.setFileName(arg0);
	        message.setContent(multiParte);
	        Transport.send(message, message.getAllRecipients());
	      //  t.connect("denuncia.enlinea@imss.gob.mx", "DeNuncI@_1*");
	      //  t.connect("", "");
	       // t.sendMessage(message, message.getAllRecipients());
	       // t.close();
	        System.out.println("Enviado");
		} catch(Exception ex) {
			ex.printStackTrace();
	    }
    }
	
	@Override
	public void enviarMailEliminacion(String email, String subject, String cuerpo) {
		try {
			enviarMail(email, subject, cuerpo, null);
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}


	  
	  public BufferedImage generadorCodigoQrUrl(String url, int tamanioWidth, int tamanioHeight) {
		BufferedImage image = null;
		BitMatrix matrix;
		Writer writer = new QRCodeWriter();
		try {
			// Genera una matriz de bytes de la url.
			matrix = writer.encode(url, BarcodeFormat.QR_CODE, tamanioWidth,
					tamanioHeight);
			// Genera la imagen de datos.
			image = new BufferedImage(tamanioWidth, tamanioHeight,
					BufferedImage.TYPE_INT_RGB);
			// Itera la matriz y dibujar los pixeles de la imagen.
			for (int y = 0; y < tamanioHeight; y++) {
				for (int x = 0; x < tamanioWidth; x++) {
					int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return image;
	}
	  
	  
	public BufferedImage generadorCodigoQrCadena(String cadena,
			int tamanioWidth, int tamanioHeight) {
		BufferedImage image = null;
		String iso88591charset = "ISO-8859-1";
		try {
			Charset charset = Charset.forName(iso88591charset);
			CharsetEncoder encoder = charset.newEncoder();
			byte[] bytes = null;
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(cadena));
			bytes = bbuf.array();
			String data = new String(bytes, iso88591charset);
			QRCodeWriter writer = new QRCodeWriter();
			// Genera una matriz de bytes de la cadena.
			BitMatrix matrix = writer.encode(data,
					com.google.zxing.BarcodeFormat.QR_CODE, tamanioWidth,
					tamanioHeight);
			// Genera la imagen de datos.
			image = new BufferedImage(tamanioWidth, tamanioHeight,
					BufferedImage.TYPE_INT_RGB);
			// Itera la matriz y dibujar los pixeles de la imagen.
			for (int y = 0; y < tamanioHeight; y++) {
				for (int x = 0; x < tamanioWidth; x++) {
					int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
			
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return image;
	}
	  
	
	public static void main(String[] args){
		System.out.println("");
	}
	  
}
