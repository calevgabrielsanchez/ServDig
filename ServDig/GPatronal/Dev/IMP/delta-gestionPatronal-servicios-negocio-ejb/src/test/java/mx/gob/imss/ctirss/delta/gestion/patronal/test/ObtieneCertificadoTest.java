package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

public class ObtieneCertificadoTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ObtieneCertificadoTest.class);
	}	

	
	
	
	@Test
	public void buscarPersonaFisicaPorDatosBasicosEnImss(){
		
		log.debug("::: Obteniendo EJB");
		PersonaBusinessRemote ejb =  EjbLocator.getPersonaBusinessRemote();
		
		// LEAL370613HSLXYS01-LUIS ANTONIO-LEÑERO-AYALA
		// MUVG370216HDFXZL02-GUILLERMO-MUÑOZ-VAZQUEZ 
		// GUHL921114HDFDRS06-LUIS RICARDO-GUDIÑO-HERNANDEZ
		
		String nombreComp = "MUVG370216HDFXZL02-GUILLERMO-MUÑOZ-VAZQUEZ";
		String[] nom = nombreComp.split("-");
		
		List<Fisica> lst = null;
		Fisica f = new Fisica();
		f.setCurp(nom[0]);
		f.setNombre(nom[1]);
		f.setPrimerApellido(nom[2]);
		f.setSegundoApellido(nom[3]);
		
		String nombreCompleto = (f.getNombre() != null ? f.getNombre() : "") + " " +
		(StringUtils.isEmpty(f.getPrimerApellido()) ? "" : f.getPrimerApellido()) + " " +
		(StringUtils.isEmpty(f.getSegundoApellido()) ? "" : f.getSegundoApellido());
		
		log.debug("::: Nombre a buscar: " + nombreCompleto);
		
		boolean existe_n = nombreCompleto.contains("\u00f1") || nombreCompleto.contains("\u00d1");
		log.debug("::: existe_n: " + existe_n);
		
		lst = ejb.buscarPersonaFisicaPorDatosBasicosEnImss(f);

		log.debug(":: Personas encontradas: " + lst.size());
		
		for (Iterator<Fisica> iterator = lst.iterator(); iterator.hasNext();) {
			Fisica fisica = iterator.next();
			log.debug(fisica.toString());
		}
		
		log.debug("::: FIN");
	}		
	
//	@Test
	public void obtienePFEscVirt(){
		
		//34601471  PEGS610513HDFXMR05  PF-7221425
		//36481167  PEHG870619HDFRRR09  PF-7305260
		//85424332  GOCM620226HDFNRN06  PF-7304798
		//6997843   fail
		//37402572 Nacho
		
		Long idPersona = new Long("37402572");
		
		log.debug("::: Obteniendo EJB");
		PersonaFisicaServiceBusinessRemote ejb =  EjbLocator.getPersonaServiceBusiness();
		
		try {
			log.debug("::: Consultando id: " + idPersona);
			Long id = ejb.obtenerIDPersonaFisicaEscVirtual(idPersona);
			log.debug("::: Id obtenido: " + id);
		} catch (PersonaFisicaNoEncontradaException e) {
			e.printStackTrace();
		}
		log.debug("::: FIN");
	}	
	
	//Exito
	//4574901  - 2
	//5645278  - 2
	
	
	//PF - 1
	//34601471  PEGS610513HDFXMR05  PF-7221425
	//36481167  PEHG870619HDFRRR09  PF-7305260
	//85424332  GOCM620226HDFNRN06  PF-7304798
	//6997843   fail
	//37402572 Nacho
	
	//PM - 2
	// 5148654 9292 SEDENA
	
	@Test
	public void obtieneCertificado(){
		
		try {
			
			log.debug("::: Obteniendo EJB");
			PersonaBusinessRemote personaBusiness = EjbLocator.getPersonaBusinessRemote();

			Persona p = new Persona();
			TipoPersona tp = new TipoPersona();
			
			Long idPersona = new Long("37402572");
			Long idTipoPersona = new Long("1");   //1-Fisica  2-Moral
			
			tp.setIdTipoPersona(idTipoPersona);
			p.setIdPersona(idPersona);
			p.setTipoPersona(tp);
			
			log.debug("Consultando datos de la FIEL, persona: " + idPersona);
			
			Fiel f = personaBusiness.obtenerDatosFiel(p);
			log.debug("::: Resultado: ");
			log.debug(f.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("::: FIN");
	}
	
	
}
