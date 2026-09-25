/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.test;


import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPoder;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;

/**
 * @author vanderluk
 *
 */
public class RepresentanteLegalTest {

	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(RepresentanteLegalTest.class);
	}	
	
	@Test
	public void terminaTramiteRL() {
		
		Long idSol = new Long("1203810463");
		
		try {
			log.debug(":: COnsultando solicitud");
			SolicitudServiceBusinessRemote solEJB = EjbLocator.getSolicitudServiceBusiness();
			Solicitud solicitud = solEJB.consultarSolicitudPorId(new Long(idSol));
		
			FirmaElectronica firmaElectronica = null;
			actualizarTipoPoderRepresentante(solicitud, 2);
			
			log.debug(":: Terminando solicitud de alta de empresa representada");
			solicitud.setFirmaElectronica(null);
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(1);
			SolicitudBusinessRemote sol = EjbLocator.getSolicitudBusinessRemote();
			sol.finalizarCapturaSolicitud(solicitud, firmaElectronica);
			
		} catch(AbstractException e) {
			e.printStackTrace();
		}
		log.debug(":: FIN");
		
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
				log.debug("::: Consultando por metodo anterior, " + new Date());
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC(p);
			} else {
				log.debug("::: Consultando por metodo actual, " + new Date());
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC_AP(p);
			}

			System.out.println(m);
			log.debug("::: FIN - " + new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Solicitud actualizarTipoPoderRepresentante(Solicitud solicitud, Integer idTipoPoder){
		TramiteRepresentanteLegal tramiteRL =(TramiteRepresentanteLegal)solicitud.getTramites().get(0);
		tramiteRL.getFisica().setTipoPoder(new TipoPoder());
		tramiteRL.getFisica().getTipoPoder().setIdTipoPoder(idTipoPoder);
		solicitud.getTramites().set(0,tramiteRL);
		return solicitud;
	}
	
	
	/**
	 * Test method for {@link mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal.RepresentanteLegalServiceEntity#persistir(mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal)}.
	 */
//	@SuppressWarnings("static-access")
	//@Test
//	public void testPersistir() {
//		RepresentanteLegalServiceBusinessRemote service = null;
//		
//		System.err.println("Testereando persistir de Representonto legal...");
//		
//		try {
//		service	= (RepresentanteLegalServiceBusinessRemote) this
//					.getCtx()
//					.lookup("representanteLegalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote");
//			
//			
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		RepresentanteLegal model = new RepresentanteLegal();
//		model.setFecRegistroActualizado(new Date());
//		model.setFecRegistroAlta(new Date());
//		model.setFecRegistroBaja(new Date());
//		model.setIndActAdmonDominio(new BigDecimal(1));
//		model.setIndEstatus(new BigDecimal(1));
//		model.setCveIdRlDomicilioList(null);
//		model.setCveIdRlFacultadList(null);
//		
//		
//		
//		try {
//			service.agregarRepresentanteLegal(model);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.err.println("Testereando persistir de Representonto legal... DONE!");
//		
//		
//	}

}
