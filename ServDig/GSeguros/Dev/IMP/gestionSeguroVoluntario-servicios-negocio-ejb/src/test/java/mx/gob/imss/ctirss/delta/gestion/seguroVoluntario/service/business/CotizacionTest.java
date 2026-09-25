package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneraLineaCapturaRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.*;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroDateUtils;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CotizacionTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CotizacionTest.class); 
	
	private static final String URL_SERVICIO = "http://172.16.5.180:7001/GestionSUA/GeneraLCPagos";
	
	//Comentado porque sólo sirve para hacer Test localmente
	//private static final String URL_SERVICIO = "http://172.16.23.223/Cobranza/Compras/LineaCaptura";
	
	@Test
	public void testCotizacion(){
		Persona persona = new Persona();
		persona.setIdPersona(56249651l);
		
		try {
			DatosCalculoCuota dcc = getSeguroBusiness().datosCotizacionIndividual(persona);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			System.err.println(sdf.format(dcc.getFechaInicioCalculo().getTime()));
			System.err.println(sdf.format(dcc.getFechaFinCalculo().getTime()));
			
			System.err.println(dcc);
		} catch (IvroException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testActivaSegro(){
		ActualizacionCompra comprasPagadas = new ActualizacionCompra();
		
		DatosCompra compra = new DatosCompra();
		compra.setIdCompra(1058l);
		
		DatosCompra[] compras = new DatosCompra[]{compra};
		
		comprasPagadas.setCompras(compras);
		
		SeguroIvroServiceRemote seguroBusiness = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.STAGE);
		try {
			seguroBusiness.activaSeguro(comprasPagadas);
		} catch (IvroException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testConsultaSolicitudSeguro(){
		
		SolicitudSeguroIvroRemote seguroBusiness = EjbLocator.find(SolicitudSeguroIvroRemote.class, Ambiente.STAGE);
			try {
				Solicitud solicitud = seguroBusiness.consultarSolicitudSeguroPorFolio("14441710754773598825");
				System.err.println(solicitud.toString());
			} catch (SolicitudNoEncontradaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void testConsultaRechazo(){
		
		SolicitudSeguroIvroRemote seguroBusiness = EjbLocator.find(SolicitudSeguroIvroRemote.class, Ambiente.STAGE);
		boolean test = seguroBusiness.existeRechazo( 58537581l);
		System.err.println("Existe solicitud de rechazo: "+(test ? "si" : "no"));
		
	}

	
	
	static Context getContextoLocal(){
		Context ic = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "password123");
			
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}
	
	public static DatosCotizacionSeguroRemote getSeguroBusiness() {
		DatosCotizacionSeguroRemote ejb = null; // NOPMD
		try {
			ejb = (DatosCotizacionSeguroRemote) getContextoLocal()
					.lookup("datosCotizacionSeguroBusiness#mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.DatosCotizacionSeguroRemote");
		} catch (NamingException e) {
		e.printStackTrace();
		}
		return ejb;
	}
	
	@Test
	public void generaComprobanteSeguro(){
		
		Persona persona = new Persona();
		persona.setIdPersona(37490878l);
		SegurosIvro seguros = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaSegurosDomesticoPatron(persona);
		mx.gob.imss.digital.modelo.seguros.SeguroIvro segAnalizar = null;
		for(mx.gob.imss.digital.modelo.seguros.SeguroIvro seg : seguros.getSeguroIvro()){
			if (seg.getCveIdSeguroIvro().longValue()==3598750l) {
				segAnalizar = seg;
			}
		}
		
		mx.gob.imss.digital.modelo.seguros.SeguroIvro[] segurosIvro = new mx.gob.imss.digital.modelo.seguros.SeguroIvro[]{segAnalizar		};
		seguros.setSeguroIvro(segurosIvro);
		byte[] file = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.STAGE).generaComprobantes(seguros).getArchivo();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("c:\\despliegues\\comprobanteIvro.pdf");
			fos.write(file);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Prueba para generación de comprobante de seguro IVRO (Seguro individual)
	 */
	@Test
	public void generaComprobanteSeguroIVRO(){
		Persona persona = new Persona();
		persona.setIdPersona(25411983l);
		SegurosIvro seguros = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaUltimosSegurosIndividual(persona);
		mx.gob.imss.digital.modelo.seguros.SeguroIvro segAnalizar = null;
		for(mx.gob.imss.digital.modelo.seguros.SeguroIvro seg : seguros.getSeguroIvro()){
			if (seg.getCveIdSeguroIvro().longValue()==42909) {
				segAnalizar = seg;
			}
		}
		
		mx.gob.imss.digital.modelo.seguros.SeguroIvro[] segurosIvro = new mx.gob.imss.digital.modelo.seguros.SeguroIvro[]{segAnalizar		};
		seguros.setSeguroIvro(segurosIvro);
		byte[] file = EjbLocator.find(ComprobanteSeguroRemote.class, Ambiente.STAGE).generaComprobantes(seguros).getArchivo();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("c:\\despliegues\\comprobanteSeguroPersonalIVRO.pdf");
			fos.write(file);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Prueba para generación de comprobante de seguro IVRO (Seguro individual)
	 */
	@Test
	public void validaTrabajadorDomestico(){
		ValidaTrabajadorDomesticoRemote vtd = EjbLocator.find(ValidaTrabajadorDomesticoRemote.class, Ambiente.STAGE);
		RespuestaValidacionTrabajador resp = vtd.validaTrabajadorSeguroAnterior(null, null, "39137900989");
		System.err.println("acabe");
	}
	
	@Test
	public void consultaSeguroIvroIndividual(){
		ConsultaSeguroIvroServiceRemote csis = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE);
		Persona p = new Persona();
		p.setIdPersona(22680614L);
		SegurosIvro si = csis.buscaUltimosSegurosIndividual(p);
		System.err.println(si);
	}
	
	@Test
	public void consultaSeguroFamiliar(){
		ConsultaSeguroIvroServiceRemote csis = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE);
		Persona p = new Persona();
		p.setIdPersona(53406054l);
                SegurosIvro si = csis.buscaSegurosFamiliares(p);
                System.out.println("Se consulta la persona "+ si.getSeguroIvro()[0].getTitular().getNombreCompleto());
                System.out.println("Se consulta con id "+ si.getSeguroIvro()[0].getTitular().getIdPersona());
                System.out.println("Se consulta el seguro "+ si.getSeguroIvro()[0].getCveIdSeguroIvro());
		System.out.println("En renovacion "+si.getSeguroIvro()[0].getEnRenovacion());
                System.out.println("Es extemporanea "+si.getSeguroIvro()[0].getExtemporanea());
	}
	
	@Test
	public void consultaSeguroCVRO(){
		ConsultaSeguroIvroServiceRemote csis = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE);
		Persona p = new Persona();
		p.setIdPersona(53749271L);
		SegurosIvro si = csis.buscaSegurosCVRO(p);
		System.err.println(si);
	}
	
	@Test
	public void cotizaSeguroRenovacionDomestico(){
		DatosCotizacionSeguroRemote csis = EjbLocator.find(DatosCotizacionSeguroRemote.class, Ambiente.STAGE);
		Persona p = new Persona();
		p.setIdPersona(22680614L);
		
		SeguroIvro si = new SeguroIvro();
		si.setCveIdSeguroIvro(12716l);
		si.setTitular(new Fisica());
		si.getTitular().setIdPersona(215303968l);
		si.getTitular().setTipoPersona(new TipoPersona());
		si.getTitular().getTipoPersona().setIdTipoPersona(1l);
		try {
			DatosCalculoCuota dcc=csis.datosCotizacionDomesticoRenovacion(si);
			System.err.println("Titular: "+dcc.getIdEmpleador());
		} catch (IvroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(si);
	}
	
	/**
	 * Prueba de líneas de captura a partir de un seguro IVRO
	 */
	@Test
	public void lineasCapturaDesdeSeguroIvroTest() throws Exception{
		Persona persona = new Persona();
		persona.setIdPersona(25411983l);
		SegurosIvro seguros = EjbLocator.find(ConsultaSeguroIvroServiceRemote.class, Ambiente.STAGE).buscaUltimosSegurosIndividual(persona);
		GeneraLineaCapturaRemote proceso = EjbLocator.find(GeneraLineaCapturaRemote.class, Ambiente.STAGE);
		CompraServiceRemote compraService = EjbLocator.find(CompraServiceRemote.class, Ambiente.STAGE);
		
		mx.gob.imss.digital.modelo.seguros.SeguroIvro seguro = null;
		Pago[] pagos = null;
		Compra compra = null;
		
		for(mx.gob.imss.digital.modelo.seguros.SeguroIvro seguroActual : seguros.getSeguroIvro()){
			if(seguroActual.getCveIdSeguroIvro().longValue()==42909){
				seguro = seguroActual;
				compra = seguro.getCompra();
				if(compra != null){
					//Se realiza búsqueda con compraService porque ahí se trae el objeto pagos con valores
					compra = compraService.findCompraById(compra.getIdCompra());
					pagos = compra.getPagos();
					
					LOGGER.debug("Antes de generar lineas de captura");
					Pago[] lineas = proceso.generaLineasCaptura(pagos, URL_SERVICIO);
					LOGGER.debug("Después de generar lineas de captura");
					
					LOGGER.debug("lineas: "+lineas);
					for(Pago linea : lineas) {
						String fileName  = "c:\\despliegues\\lineasCaptura\\"+ linea.getLineaCaptura()+".pdf";
						LOGGER.debug("linea.getLineaCaptura(): ", linea.getLineaCaptura());
						FileOutputStream outputStream = new FileOutputStream(new File(fileName), true);
						outputStream.write(linea.getPdf());
						outputStream.close();
					}
				}
			}
		}
	}
	
	@Test
	public void asociaPagoAutomaticoSeguroTest() {
		
		SeguroIvroServiceRemote ejb = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.STAGE);
		
		SeguroIvro seguro = new SeguroIvro();
		seguro.setCveIdSeguroIvro(46011L);
		
		Compra compraNueva = new Compra();
		compraNueva.setIdCompra(7971L);
		
		seguro.setCompra(compraNueva);
		
		ejb.asociaPagoAutomaticoSeguro(seguro);
		
	}
	
	@Test
	public void obtenerSegurosCvroBajaMensualTest() {
		SeguroIvroServiceRemote ejb = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.STAGE);
		
		try {
			MovimientoTrabajadorSindo movimientos[] = ejb.bajaMensualSindoSeguroCvro();
			
			for (MovimientoTrabajadorSindo movimiento : movimientos) {
				System.out.println("MOV 02 -> " + ToStringBuilder.reflectionToString(movimiento, ToStringStyle.MULTI_LINE_STYLE));
			}
		} catch (IvroException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Realiza la prueba del método para vencer seguros por Mora
	 * 
	 */
	@Test
	public void venceSegurosMod40(){
		SeguroIvroServiceRemote ejb = EjbLocator.find(SeguroIvroServiceRemote.class, Ambiente.STAGE);
		
		DatosCompra datosCompra1 = new DatosCompra();
		datosCompra1.setIdCompra(Long.valueOf(8616));
		
		DatosCompra datosCompra2 = new DatosCompra();
		datosCompra2.setIdCompra(Long.valueOf(8621));
		
		DatosCompra[] arrayDatosCompra = new DatosCompra[2];
		arrayDatosCompra[0] = datosCompra1;
		arrayDatosCompra[1] = datosCompra2;
		
		ActualizacionCompra actualizacionCompra = new ActualizacionCompra();
		actualizacionCompra.setCompras(arrayDatosCompra);
		
		LOGGER.debug("Antes de realizar el vencimiento de los seguros por Mora");
		ejb.venceSegurosPorMoraMod40(actualizacionCompra);
		
		LOGGER.debug("Se realizó el vencimiento de los seguros por Mora");
	}
}
