package mx.gob.imss.cit.cda.web.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CorreosRespuestaSINDOUtils {
	
	SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
	
	public String crearEncabezadoCorreos(){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<html>");
		stringBuilder.append("<link href='https://framework-gb.cdn.gob.mx/assets/styles/main.css' rel='stylesheet'>");
		stringBuilder.append("<body style='margin: 0; padding: 0; background: #F3F3F3;'>");
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' align='center' width='100%'>");		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center' style='margin: 0; padding: 0; background: #F3F3F3; padding: 27px 0px'>");
		
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' align='center' width='560px' style='font-family: Helvetica, Arial; font-size: 16px; color: #ffffff; background: #393C3E' class='header'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td height='76px' valign='middle' style='padding-left: 27px;'>");
		stringBuilder.append("<a href='//www.gob.mx/' style='vertical-align: middle;'>");
		stringBuilder.append("<img width='126' height='39' alt='gob.mx' src='http://serviciosdigitales.imss.gob.mx/delta/resources/imagenes/gobmx/logos/gobmxlogo.png' />");
		stringBuilder.append("</a>");
		stringBuilder.append("</td>");
		stringBuilder.append("<td height='76px' valign='middle' align='right' style='padding-right: 27px;'>");
		stringBuilder.append("<span style='color: #ffffff; font: normal 16px Helvetica, Arial; margin: 0px; padding: 0px; line-height: 16px;'>IMSS - ESCRITORIO VIRTUAL</span>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' align='center' width='560px' style='font-family: Helvetica, Arial; background: #ffffff;' bgcolor='#ffffff'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td width='560px;' valign='top' align='left' bgcolor='#ffffff' style='font-family: Helvetica, Arial; font-size: 16px; color: #5A5A5A; background: #fff; padding: 38px 27px 76px;'>");
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' style='color: #717171; font: normal 16px Helvetica, Arial; margin: 0px; padding: 0;' width='100%' class='content'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center' style='padding: 15px 0px;'>");
		stringBuilder.append("<h4 style='color: #5A5A5A; margin: 0px; padding: 0px; line-height: 30px; font-size: 24px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("IMSS Digital: Solicitud de Regularizaci&oacute;n y/o Correcci&oacute;n de Datos Personales del Asegurado");
		stringBuilder.append("</h4>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		return stringBuilder.toString();
	}
	
	public String crearPiePaginaCorreos(){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' class='note'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; font-style: italic; line-height: 20px; font-size: 12px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("Esta direcci&oacute;n de correo electr&oacute;nico no puede recibir respuestas");
		stringBuilder.append("</p>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' class='note'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; font-style: italic; line-height: 20px; font-size: 12px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("<strong>AVISO DE PRIVACIDAD: </strong>");		
		stringBuilder.append("Los datos personales recabados ser&aacute;n protegidos, tratados e incorporados en el sistema de datos personales denominado Cat&aacute;logo Nacional de Asegurados (CANASE) con fundamento en el Art&iacute;culo: "); 
        stringBuilder.append("22 de la Ley del Seguro Social, 3, 97, 113 y 117 de la Ley Federal de Transparencia y Acceso a la Informaci&oacute;n P&uacute;blica, cuya finalidad es proteger, contener, ordenar y clasificar los datos de los patrones y asegurados, ");
        stringBuilder.append("El Cat&aacute;logo Nacional de Asegurados (CANASE) est&aacute; registrado en el Listado de Sistemas de Datos Personales ante el Instituto Nacional de Transparencia, Acceso a la Informaci&oacute;n y Protecci&oacute;n de Datos Personales (www.inai.org.mx), ");
        stringBuilder.append("y ser&aacute;n transmitidos, al INFONAVIT y a la CONSAR con la finalidad de que el asegurado haga valer su derecho de ejercicio de cr&eacute;dito de vivienda y disposici&oacute;n del Seguro de Retiro, adem&aacute;s de otras prestaciones previstas en la Ley. ");
        stringBuilder.append("La Unidad Administrativa responsable del Sistema de Datos Personales es la Direcci&oacute;n de Incorporaci&oacute;n y Recaudaci&oacute;n del Seguro Social en coordinaci&oacute;n con la Direcci&oacute;n de Innovaci&oacute;n y Desarrollo Tecnol&oacute;gico del IMSS ");
        stringBuilder.append("y la direcci&oacute;n donde el interesado podr&aacute; ejercer los derechos de acceso y correcci&oacute;n ante la misma es la Subdelegaci&oacute;n de control que corresponde al domicilio del patr&oacute;n o del asegurado, en su caso. ");
        stringBuilder.append("Lo anterior se informa en cumplimiento del decimos&eacute;ptimo de los Lineamientos de Protecci&oacute;n de Datos Personales, publicados en el Diario Oficial de la Federaci&oacute;n el 30 de septiembre de 2005.");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("<strong>AVISO IMPORTANTE: </strong>");		
		stringBuilder.append("Este correo electr&oacute;nico y/o el material adjunto es para uso exclusivo de la persona o la entidad a la que expresamente se le ha enviado, "); 
		stringBuilder.append("el cual contiene informaci&oacute;n confidencial. ");
		stringBuilder.append("Si no es el destinatario leg&iacute;timo del mismo, por favor rep&oacute;rtelo inmediatamente a la cuenta del remitente y elim&iacute;nelo. ");
		stringBuilder.append("Cualquier revisi&oacute;n, almacenamiento, retransmisi&oacute;n, difusi&oacute;n o cualquier otro uso de este correo, ");
		stringBuilder.append("por personas o entidades distintas a las del destinatario leg&iacute;timo, queda expresamente prohibida. ");
		stringBuilder.append("Este correo electr&oacute;nico no pretende ni debe ser considerado como constitutivo de ninguna relaci&oacute;n legal, contractual o de otra &iacute;ndole similar.");
		stringBuilder.append("</p>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>"); 
		stringBuilder.append("</table>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' align='center' width='560px' style='font-family: Helvetica, Arial; font-size: 16px; color: #ffffff; background: #393C3E' class='mainFooter'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='left' height='76px' valign='middle' style='padding-left: 27px;'>");
		stringBuilder.append("<img width='63' height='19' alt='gob.mx' src='http://serviciosdigitales-stage.imss.gob.mx/delta/resources/imagenes/gobmx/logos/gobmxlogo.png' />");
		stringBuilder.append("</td>");
		stringBuilder.append("<td height='76px' valign='middle' align='right' style='padding-right: 27px;'>");
		stringBuilder.append("<img width='86' height='35' alt='gob.mx' src='http://serviciosdigitales-stage.imss.gob.mx/delta/resources/imagenes/gobmx/logos/logo_mexico.png' />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		
		stringBuilder.append("<table cellpadding='0' cellspacing='0' border='0' align='center' width='560px' style='font-family: Helvetica, Arial; line-height: 10px;' bgcolor='#F3F3F3' class='footer'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td bgcolor='#F3F3F3' align='center' style='padding: 15px 0 10px; font-size: 13px; color: #5A5A5A; margin: 0px; line-height: 1.2; font-family: Helvetica, Arial;' valign='top'>");
		stringBuilder.append("<p style='padding: 0px; font-size: 13px; color: #5A5A5A; margin: 0px; font-family: Helvetica, Arial; text-transform: uppercase; color: #5A5A5A;'>");
		stringBuilder.append("IMSS, M&eacute;xico - Algunos derechos reservados 2015");
		stringBuilder.append("</p>");
		stringBuilder.append("<p style='padding: 0px; font-size: 13px; color: #5A5A5A; margin: 0px 0px 8px 0px; font-family: Helvetica, Arial; text-transform: uppercase; color: #5A5A5A;'>");
		stringBuilder.append("<a href='http://www.imss.gob.mx/pages/privacidad.aspx' style='color: #12c;'>");
		stringBuilder.append("POL&Iacute;TICA DE PRIVACIDAD Y MANEJO DE DATOS PERSONALES");
		stringBuilder.append("</a>");
		stringBuilder.append(" - ");
		stringBuilder.append("<a target='_parent' href='http://www.imss.gob.mx/Pages/avisolegal.aspx' style='color: #12c;'>");
		stringBuilder.append("Aviso LEGAL");
		stringBuilder.append("</a>");
		stringBuilder.append("</p>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");
		
		return stringBuilder.toString();
	}
	
	public String contenidoCorreoArchivoNoLocalizado() {
	
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(crearEncabezadoCorreos());
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td style='padding: 15px 0px; text-align: center;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>A ");
		stringBuilder.append(fecha.format(new Date()));
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>Estimado(a): ");
		stringBuilder.append("<strong>");
		stringBuilder.append("[Administrador de IMSS Digital]");
		stringBuilder.append("</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("El archivo de respuesta de SINDO correspondiente a las solicitudes enviadas ");
		stringBuilder.append("con fecha ");
		stringBuilder.append("[fecha del envio] ");
		stringBuilder.append("no fue encontrado.");
		stringBuilder.append("<p/>");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		stringBuilder.append(crearPiePaginaCorreos());
		
		return stringBuilder.toString();
	}
	
	public String contenidoCorreoDiferenteNumMovimientos() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(crearEncabezadoCorreos());
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td style='padding: 15px 0px; text-align: center;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>A ");
		stringBuilder.append(fecha.format(new Date()));
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>Estimado(a): ");
		stringBuilder.append("<strong>");
		stringBuilder.append("[Administrador de IMSS Digital]");
		stringBuilder.append("</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("El n&uacute;mero de movimientos del archivo de respuesta de SINDO de las solicitudes enviadas ");
		stringBuilder.append("con fecha ");
		stringBuilder.append("[fecha del envio] ");
		stringBuilder.append("no corresponde con el n&uacute;mero de movimientos enviados ");
		stringBuilder.append("[Numero de movimientos enviados].");
		stringBuilder.append("<p/>");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		stringBuilder.append(crearPiePaginaCorreos());
		
		return stringBuilder.toString();
	}
	
public String contenidoCorreoMovimientoNoExitoso(String folio, String responsable, List<String> erroresSINDO ) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(crearEncabezadoCorreos());
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td style='padding: 15px 0px; text-align: center;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>A ");
		stringBuilder.append(fecha.format(new Date()));
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>Estimado(a): ");
		stringBuilder.append("<strong>");
		stringBuilder.append(responsable);
		stringBuilder.append("</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("Se identificaron errores al procesar los movimientos de la solicitud ");
		stringBuilder.append("<strong>");
		stringBuilder.append(folio);
		stringBuilder.append("</strong>");
		stringBuilder.append(" enviados para actualizaci&oacute;n en SINDO con fecha ");
		stringBuilder.append("<strong>");
		stringBuilder.append(fecha.format(new Date()));
		stringBuilder.append("</strong>");
		stringBuilder.append(".");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("Lista de movimientos rechazados y motivo del rechazo:");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<table class='table'>");
		stringBuilder.append("<thead><tr><th>NSS</th><th>Datos del cambio</th><th>Error en SINDO</th></tr></thead>");
		stringBuilder.append("<tbody>");
		//for lista errores
		for (String detalleError : erroresSINDO) {
			String[] tokens = detalleError.split(" ");
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(tokens[0]);
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(tokens[1]);
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(tokens[2]);
            stringBuilder.append("</td>");
			stringBuilder.append("</tr>");
			//stringBuilder.append("[NSS*] [Datos del periodo?] [Datos del cambio*] [Error en SINDO*]");
		}
		//end for
		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		stringBuilder.append(crearPiePaginaCorreos());
		String correo = stringBuilder.toString();
		
		return correo;
	}

	public String contenidoCorreoSinRespuestaSINDO() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(crearEncabezadoCorreos());
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td style='padding: 15px 0px; text-align: center;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>A ");
		stringBuilder.append(fecha.format(new Date()));
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>Estimado(a): ");
		stringBuilder.append("<strong>");
		stringBuilder.append("[Responsable]");
		stringBuilder.append("</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>");
		//ToDo: Cambiar el mensaje, se coloco el que se encuetra en el CU pero este es incorrecto
		stringBuilder.append("El n&uacute;mero de movimientos con respuesta de SINDO de la solicitud ");
		stringBuilder.append("[folio de la solicitud] ");
		stringBuilder.append("enviados con fecha ");
		stringBuilder.append("[fecha del env&iacute;o].");
		stringBuilder.append("no corresponde con el n&uacute;mero de movimientos enviados ");
		stringBuilder.append("[N&uacute;mero de movimientos enviados].");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("Lista de movimientos sin respuesta:");
		stringBuilder.append("[NSS] [Datos del periodo] [Datos del cambio]");
		stringBuilder.append("<p/>");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		stringBuilder.append(crearPiePaginaCorreos());
		
		return stringBuilder.toString();
	}
	
public String contenidoCorreoCertificado(String folio, String autorizador, String curp, String nombre, List<String> origenMovimiento ) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(crearEncabezadoCorreos());
		
		stringBuilder.append("<tr>");
		stringBuilder.append("<td style='padding: 15px 0px; text-align: center;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>A ");
		stringBuilder.append("Folio:");
		stringBuilder.append(folio);
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='justify' style='padding: 15px 0px; text-align: justify;' valign='top'>");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>Estimado(a): ");
		stringBuilder.append("<strong>");
		stringBuilder.append(autorizador);
		stringBuilder.append("</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("<br />");
		stringBuilder.append("<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>");
		stringBuilder.append("La solicitud ");
		stringBuilder.append(folio);
		stringBuilder.append(" ha sido enviada para que, en su caso, sea debidamente debidamente autorizada para el posterior envío al proceso de actualización en SINDO. ");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("Datos del Asegurado:");
		stringBuilder.append("</p>");
		stringBuilder.append("Curp:");
		stringBuilder.append("<strong>");
		stringBuilder.append(curp);
		stringBuilder.append("</strong>");
		stringBuilder.append("<br />");
		stringBuilder.append("Nombre:");
		stringBuilder.append("<strong>");
		stringBuilder.append(nombre);
		stringBuilder.append("</strong>");
		stringBuilder.append("<br />");
		stringBuilder.append("</p>");
		stringBuilder.append("<table class='table'>");
		stringBuilder.append("<thead><tr><th>NSS</th><th>Origen/Movimiento</th></tr></thead>");
		stringBuilder.append("<tbody>");
		for (String detalleError : origenMovimiento) {
			String[] tokens = detalleError.split(" ");
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append(tokens[0]);
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append(tokens[1]);
            stringBuilder.append("</td>");
			stringBuilder.append("</tr>");
			//stringBuilder.append("[NSS*] [Datos del periodo?] [Datos del cambio*] [Error en SINDO*]");
		}
		//end for
		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("<br />");
		stringBuilder.append("<br />");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		
		
		stringBuilder.append(crearPiePaginaCorreos());
		String correo = stringBuilder.toString();
		
		return correo;
	}
	
}
