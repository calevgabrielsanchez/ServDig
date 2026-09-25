package mx.gob.imss.infraestructura.convertirdores;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@FacesConverter("ConverterGregorianToDate")
public class ConverterGregorianToDate implements Converter {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd H:mm:ss:SSS";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) { 
		return null;
	}
 
	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		
		XMLGregorianCalendar fechaXml = (XMLGregorianCalendar)value;
		java.util.Date fecha = fechaXml.toGregorianCalendar().getTime();
		
    	//CONFIGURA EL FORMATO DE FECHA
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    	String sFechaFormato = sdf.format(fecha);
		return sFechaFormato;
 
	}
	
	public XMLGregorianCalendar dateToGregorianCalendar(java.util.Date fecha){
		XMLGregorianCalendar fechaXml = null;
		try{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(fecha);
			fechaXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return fechaXml;	
	}	
	/**
	 * Metodo que genera las fechas de inicio y fin por default
	 * @param fechaInicio
	 * @param fechafin
	 */
	public Date  generaFechasIniciales(boolean inicio){
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 GregorianCalendar gr= new GregorianCalendar();
	        Date sinHoras;
	        Date fecha = null;
			try {
				gr.setTime( new Date());
				sinHoras = sdf.parse( sdf.format(new Date()));
				Date conHoras = new Date( sinHoras.getTime() + (24*60*60*1000)-1 );
	        if(inicio){
	        	fecha=sinHoras;
	        }else{
	        	fecha=conHoras;
	        }
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			return fecha;
	}
}
