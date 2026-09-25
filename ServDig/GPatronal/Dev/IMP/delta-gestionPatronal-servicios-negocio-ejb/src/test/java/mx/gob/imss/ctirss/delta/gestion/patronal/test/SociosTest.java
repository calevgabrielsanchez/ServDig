package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

public class SociosTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(SociosTest.class);
	}	

	@Test
	public void localizarSocioAlta(){
		try {
			log.debug("::: Obteniendo EJB - " + new Date());
			 SocioServiceBusinessRemote ejb = EjbLocator.getSociosService();
			 //NWM9709244W4 - 588596
			 //IKU210623IE4 - 5430758
			 //WMM8606236S2 - 5274303
			 Socio socio = new Socio();
			 socio.setRfcPersonaMoralPatron("IKU210623IE4"); //PM a la que se va asignar el socio
			 socio.setIdPersonaMoralPatron(new Long(5430758)); // id de la PM a la que se va asignar el socio
			 TipoPersona tp = new TipoPersona();
			 tp.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			 socio.setTipoSocio(tp);
			 
			 socio.setRfc("MOBB9910087TA"); //nuevo socio
			 
			 log.debug("::: Localizando socio nuevo");
			 socio = ejb.localizarSocioAlta(socio);
			 log.debug("::: Socio localizado");
			// System.out.println(socio);
			 
			 log.debug("::: FIN - " + new Date());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void localizarPM() {
		try {
			log.debug("::: Obteniendo EJB - " + new Date());
			IndividuoServiceBusinessRemote ejb = EJBLocator.getIndividuoServiceBusiness();
			Persona p = new Persona();
			p.setRfc("NWM9709244W4");
			boolean anterior = false;
			// NWM9709244W4 - 588596
			// IKU210623IE4 - 5430758
			log.debug("::: Buscando PM");
			Moral m = null;
			if (anterior) {
				log.debug("::: Consultando por metodo anterior");
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC(p);
			} else {
				log.debug("::: Consultando por metodo actual");
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC_AP(p);
			}

			System.out.println(m);
			log.debug("::: FIN - " + new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
