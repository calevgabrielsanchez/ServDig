package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public class TramitesAdministrativosServiceTest {

	@Test
	public void generaAcuseTramiteAdministrativo() {
		String folio ="146888243638640194438";
		
		SolicitudBusinessRemote solicitudBusinessRemote = EjbLocator.getSolicitudBusiness();
		DocumentosServiceRemote documentosServiceRemote = EjbLocator.getDocumentosService();
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folio);
		try {
			solicitud = solicitudBusinessRemote.consultarFolio(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(solicitud != null) {
			
			for(Tramite tramite: solicitud.getTramites()) {
				tramite.setFechaConclusion(new Date());
			}
			byte[] doctoGenerado = null;
			try {
				doctoGenerado = (byte[]) documentosServiceRemote.generarComprobanteTramiteAdministrativo(solicitud);
				
				FileOutputStream fos = null;
				
				try {
					fos = new FileOutputStream("d:\\pruebasReportes\\comprobanteAdministrativoBaja.pdf");
					fos.write(doctoGenerado);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
}
