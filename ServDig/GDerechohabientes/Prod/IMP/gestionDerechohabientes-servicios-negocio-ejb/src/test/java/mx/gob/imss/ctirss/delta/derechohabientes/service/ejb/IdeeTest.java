package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

//import mx.gob.imss.cit.de.dictaminacion.mdb.to.EliminarDictamenTO;
import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.IdeeServiceRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.IdsIntegranteGrupo;
import mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties;


public class IdeeTest{

	// Defines the JNDI context factory.
			 private final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
			 // Defines the JMS context factory.
			 private final static String JMS_FACTORY="jms/ConnectionFactory";
			 // Defines the queue.
			 private final static String QUEUE="jms/EliminarDictamenQueue";
			 //Server
			 public final static String HOST_JMS_SERVER ="t3://192.168.0.24:7001";

			 private QueueConnectionFactory qconFactory;
			 private QueueConnection qcon;
			 private QueueSession qsession;
			 private QueueSender qsender;
			 private Queue queue;
	
	
	@Test
	public void testIDee() {
		
		//IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		
		Calendar cal = Calendar.getInstance();
		cal.set(1953,5, 5);
		
		//6197750099_ROMA0510140UK0R5C6_DE LA ROSA_MORENO_ALEXIS_14/10/2005_M_14
		
		String nss = "1474530467|1";
		Integer mes = 0;
		Integer anio = 0;
	//	String IDEE = ideeService.generarIDEE(nss, 1, "ERASMO", "MARTINEZ", "CRUZ", cal.getTime(), mes, anio);
		
	//	System.out.println("El id generado con el cambio es: " + IDEE);
		
		
		try {
			this.init();
			//ObjectMessage message = qsession.createObjectMessage(objectMessage);
			TextMessage msg = qsession.createTextMessage("VAMOS  LA PLAYA OH OH HO");
			for(int i=0; i<=150;) {
				qsender.send(msg);
				i++;
			}
			//this.closeJMS();
		} catch (JMSException e) {
			System.out.print("Error al enviar mensaje JMS" + e);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					this.closeJMS();
					
				} catch (JMSException e) {
					System.out.print("ERRROR al cerrar la conneccion " + e.getMessage() );
				}
		}
		
		try {
			
			
		}catch(Exception e) {
			System.out.print("ERRROR " + e.getMessage() );
		}
		
		
		
		

	}
	
	@Test
	public void datosEmail() {
		EmailServiceRemote emailse = EjbLocator.getEmailService();
		
		MailProperties mailP;
		try {
			mailP = emailse.getDefaultMailProperties();
			System.out.print("El nombre dle host es " + mailP.getHost());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void generarIDEEsCL3() {
		IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		List<String> nsss = new ArrayList<String>();
		nsss.add("07355127528");
		nsss.add("07355127551");
		nsss.add("07355127577");
		nsss.add("07355127601");
		nsss.add("07355127627");
		nsss.add("07355127635");
		nsss.add("07355127700");
		nsss.add("07355127734");
		nsss.add("07355127767");
		nsss.add("07355127783");
		
		Map<String, String> idees = ideeService.generarYGuardarIDEEPorNSsCL3(nsss);
		
		for(String key: idees.keySet()) {
			System.out.println("Para el nss " + key + " se genero el idee " + idees.get(key));
		}

	}
	
	@Test
	public void actualizarIdeesPorPersonas() {
		List<IdsIntegranteGrupo> datosIdee = new ArrayList<IdsIntegranteGrupo>();
		IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		datosIdee.add(new IdsIntegranteGrupo(null, "48068738524", 48957647L, null, false));
		datosIdee.add(new IdsIntegranteGrupo(null, "48068738524", 284377340L, null, false));
		datosIdee.add(new IdsIntegranteGrupo(null, "48068738524", 41909751L, null, false));
		datosIdee.add(new IdsIntegranteGrupo(null, "48068738524", 41938189L, null, false));
		datosIdee.add(new IdsIntegranteGrupo(null, "48068738524", 103388709L, null, false));
		
		datosIdee.add(new IdsIntegranteGrupo(null, "07355127528", 273192543L, null, true));
		datosIdee.add(new IdsIntegranteGrupo(null, "07355127551", 273192544L, null, true));
		datosIdee.add(new IdsIntegranteGrupo(null, "07355127577", 273192545L, null, true));
		
		
		datosIdee = ideeService.generarIdees(datosIdee);
		
		if(datosIdee != null && !datosIdee.isEmpty()) {
			for(IdsIntegranteGrupo integrante: datosIdee) {
				System.out.println(integrante);
			}
		}
		
	}
	@Test
	public void generarIdeeNss() {
		IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		String idee = null;
		try {
		 idee = ideeService.generarYGuardarIDEEParaNSS("48068738524");
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("el idee generado fue en: " + idee);
	}
	
	@Test
	public void guardarActualizarIDEE() {
		IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		
		String idee = ideeService.generarYGuardarOActualizarIdeePorIdNssCL3(104716489L, 273192576L, null);
		System.out.println("el idee generado fue en: " + idee);
	}
	
	
	
	public static void leerDatosArchivoIdees() {
		IdeeServiceRemote ideeService = EjbLocator.getIdeeService();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			System.out.print("Se empieza a leer el archivo");
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File ("C:\\idees.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

//			6197750099_ROMA0510140UK0R5C6_DE LA ROSA_MORENO_ALEXIS_14/10/2005_M_14

			System.out.println("NSS \t Apellido P \t Apellido M \t Nombre \t Fecha de nacimiento \t Calidad \t Idee ACC \t Idee AU");
			// Lectura del fichero
			String linea;
			while((linea=br.readLine())!=null) {
				String[] parametros = linea.split("-");
				
				String nss = parametros[0];
				Integer calidad = new Integer(parametros[7]);
				Calendar cal = Calendar.getInstance();
				String[] fecha = parametros[5].split("/");
				cal.set(new Integer(fecha[2]),new Integer(fecha[1]) -1, new Integer(fecha[0]));
				Integer mes = 0;
				Integer anio = 0;
				String IDEE = ideeService.generarIDEE(nss, calidad, parametros[4], parametros[2], parametros[3], cal.getTime(), mes, anio);
				
				System.out.println(nss + " \t " +parametros[2]+" \t " +parametros[3]+" \t " +parametros[4]+" \t "+parametros[5] + " \t " + parametros[7] + " \t " + parametros[1] + " \t " + IDEE);
			}
				
		}
		catch(Exception e){
			System.out.println("Error " + e.getMessage());
		}finally{
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta 
			// una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}
	
	
	
	private static InitialContext getInitialContext()   throws NamingException {
	    Hashtable env = new Hashtable();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
	    env.put(Context.PROVIDER_URL, HOST_JMS_SERVER);
	    return new InitialContext(env);
}

public void init() throws NamingException, JMSException{
	    InitialContext ctx = getInitialContext();
 	    qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
	    qcon = qconFactory.createQueueConnection();
	    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    queue = (Queue) ctx.lookup(QUEUE);
	    qsender = qsession.createSender(queue);
	    qcon.start();
}

	public void closeJMS() throws JMSException {
    qsender.close();
    qsender = null;
    queue = null;
    qsession.close();
    qsession = null;
    qcon.close();
    qcon = null;
    qconFactory=null;
    
 }
	

}	
	
	
	
	
