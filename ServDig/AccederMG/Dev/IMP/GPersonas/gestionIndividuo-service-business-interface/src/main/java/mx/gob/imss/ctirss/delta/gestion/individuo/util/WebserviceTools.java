package mx.gob.imss.ctirss.delta.gestion.individuo.util;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class WebserviceTools {

	public static String getStringXml(Object tipoObjeto) throws JAXBException {
		//OBTENEMOS EL DOCUMENTO XML DE ENTRADA
		JAXBContext contextoRespuesta = JAXBContext.newInstance(tipoObjeto.getClass());
		final Marshaller marshaller = contextoRespuesta.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		StringWriter sEntradaXml = new StringWriter();
		marshaller.marshal(tipoObjeto, sEntradaXml);
		return sEntradaXml.toString();
	}

	public static Object getXml(String sXml, Class<?> tipoObjeto) throws JAXBException {
		Object resultado = null;
		StringReader parametrosXml = new StringReader(sXml);
		JAXBContext contexto = JAXBContext.newInstance(tipoObjeto);
		Unmarshaller um = contexto.createUnmarshaller();
		resultado = um.unmarshal(parametrosXml);
		return resultado;
	}

	// METODO PARA XML GREGORIAN CALENDAR
	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date fecha) {
		XMLGregorianCalendar respuesta = null;
		try {
			if (fecha != null) {
				Calendar c = new GregorianCalendar();
				c.setTime(fecha);
				int month = c.get(Calendar.MONTH) + 1;
				int day = c.get(Calendar.DAY_OF_MONTH);
				int year = c.get(Calendar.YEAR);
				int hour = c.get(Calendar.HOUR);
				int minute = c.get(Calendar.MINUTE);
				int second = c.get(Calendar.SECOND);
				int milis = c.get(Calendar.MILLISECOND);
				DatatypeFactory df = DatatypeFactory.newInstance();
				respuesta = df.newXMLGregorianCalendar(year, month, day, hour, minute, second, milis, c.getTimeZone().getOffset(fecha.getTime()) / (60 * 60 * 1000));
			}
		}
		catch (DatatypeConfigurationException ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}
}
