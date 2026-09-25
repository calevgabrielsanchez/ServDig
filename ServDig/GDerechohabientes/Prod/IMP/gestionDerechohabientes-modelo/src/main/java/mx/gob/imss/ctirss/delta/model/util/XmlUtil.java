package mx.gob.imss.ctirss.delta.model.util;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Solicitud;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitud;
/**
 * Clase de utileria para convertir objetos y XML
 * @author CGARCIA
 *
 */
public class XmlUtil {

	/**
	 * Convierte un Objeto en un String con la estructura de un XML
	 * @param listaClases
	 * @param object
	 * @return
	 * @throws JAXBException
	 */
	public String ObjectToXml(@SuppressWarnings("rawtypes") Class[] listaClases, Object object)
			throws JAXBException {
		String xml = "";
		try {
			final JAXBContext jaxbCtx = JAXBContext.newInstance(listaClases,
					null);
			final Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final Writer writer = new StringWriter();
			
			marshaller.marshal(object, writer);
			xml = writer.toString();
			//System.out.println(writer.toString());
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new JAXBException("Error al convertir a xml el objeto" + e.getMessage());
		}

		return xml;
	}

	/**
	 * Convierte un String con estructura xml en un Objeto
	 * @param listaClases
	 * @param xml
	 * @return
	 * @throws JAXBException
	 */
	public Object xmlToObject(@SuppressWarnings("rawtypes") Class[] listaClases, String xml)
			throws JAXBException {
		Object object = null;
		try {
			final JAXBContext jaxbCtx = JAXBContext.newInstance(listaClases,
					null);
			final Reader reader = new StringReader(xml);
			final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

			object = unmarshaller.unmarshal(reader);

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new JAXBException("Error al convertir el xml a objeto " + e.getMessage());
		}
		return object;
	}
	
	/**
	 * Main para probar los metodos.
	 * @param args
	 */
	
	/*
	public static void main(String [] args){
		XmlUtil util =  new XmlUtil();
		
		//Persona
		Persona personaF = new Fisica();
        personaF.setIdPersona(1L);
        //Persona Domicilio
        PersonaDomicilio pd =  new PersonaDomicilio();
        pd.setFechaRegistroActualizado(new Date());
        pd.setFechaRegistroAlta(new Date());
        pd.setFechaRegistroBaja(new Date());
         //Domicilio
         Domicilio d =  new  Domicilio();
         d.setClave(1);
          //Codigo postal
          CodigoPostal cp =  new CodigoPostal();
          cp.setCodigoPostal("123456");
         d.setCodigoPostal(cp);
         d.setNumExterior1(12);
         d.setNumExteriorAlf("A-12");
         
        pd.setDomicilio(d);
        
        List<PersonaDomicilio> pesonaDomicilios =  new ArrayList<PersonaDomicilio>(); 
        pesonaDomicilios.add(pd);
        
        personaF.setPersonaDomicilio(pesonaDomicilios);
        
        Nacimiento actaNacimiento = new Nacimiento();
        actaNacimiento.setAnio(new Integer("2000"));
        actaNacimiento.setIdDocumentoProbatorio(23);
        
        ((Fisica) personaF).setNombre("Juan");
        ((Fisica) personaF).setPrimerApellido("Perez");
        
        
        //Solicitud
        Solicitud s =  new Solicitud();
        EstadoSolicitud es =  new EstadoSolicitud();
        es.setDescripcion("Solicitud Activa");
        es.setIdEstadoSolicitud(2l);
        s.setEstadoSolicitud(es);
        s.setFechaCita(new Date());
        s.setFechaRegistroActalizado(new Date());
        s.setFechaRegistroAlta(new Date());
        s.setFechaRegistroBaja(new Date());
        s.setFechaSolicitud(new Date());
        s.setIdSolicitud(1l);
        s.setNoFolioSolicitud("123456");
        s.setObservacion("Observaciones");
        
        //Tramite
        final Tramite tramite = new Tramite();
        tramite.setPersona((Fisica)personaF);
        tramite.setSolicitud(s);
        
        
        String xml = "";
        @SuppressWarnings("rawtypes")
		Class[] listaClases =  {Tramite.class};
        
        //Prueba Objeto a xml
		try{
			xml = util.ObjectToXml(listaClases, tramite);
		}catch(Exception e){
			e.printStackTrace();
		}
//		System.out.println(xml);
		
		
		
		//Prueba XML a Object
		Tramite t =  null;
		try{
			t = (Tramite)util.xmlToObject(listaClases, xml);
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		System.out.println("Tramite "+ t);
	}
*/
}
