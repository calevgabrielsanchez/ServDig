package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.util.SystemPropertyUtils;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class DetalleTest {

//	@Test
	public void obtenerDetalleSolicitudDictamen(){
		System.out.println("::: Inicio");
		try {
			SolicitudServiceBusinessRemote ejb = EJBLocator.getServiceBusinessMAC();
			
			SujetoObligado so = new SujetoObligado();
			so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			so.setNumeroRegistroPatronal("E461076113");
			Modalidad mo = new Modalidad();
			mo.setNumModalidad("10");
			so.setModalidad(mo);
			so.setDigVerificador("");
			
			so = ejb.obtenerDetalleSolicitudDictamen(so);

//			System.out.println(so.toString());
			
			DictamenDTO dictamen = new DictamenDTO();
			dictamen.setUsuario("VAGE480902L1A");
			dictamen.setIdEjercicio(new Long("11"));
			dictamen.setCveIdPatronDictamen(new Long("165721"));
			dictamen.setRfc("VAGE480902L1A");
			
			//solo si ya existe la solicitud
			dictamen.setIdSolicitud(new Long(1280120604));
			
			Long idSol = ejb.crearVistaDictamen(so,dictamen);
			
			System.out.println("::: Solicitud creada: " + idSol);
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		System.out.println("::: Fin");		
	}
	
	
	@Test
	public void bajaDeAnalisis(){
		
		Long idSol = new Long("1029486192");
		
		AnalisisClasificacionEmpresas analisis = new AnalisisClasificacionEmpresas();
		
		
		try {
			System.out.println(":::: Iniciando , obteniendo EJB");

			AnalisisServiceBusinessRemote ejb = EJBLocator.getAnalisisServiceBusiness();
			analisis = ejb.obtenerDetalleAnalisis(new BigDecimal(idSol));

			
			analisis.setCveIdSubdelegacion(new Long(54));

			System.out.println("Analisis:");
			System.out.println(analisis);

//			ejb.cancelaAnalisis(analisis);
			

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		System.out.println(":::: Termine");
	}	

}
