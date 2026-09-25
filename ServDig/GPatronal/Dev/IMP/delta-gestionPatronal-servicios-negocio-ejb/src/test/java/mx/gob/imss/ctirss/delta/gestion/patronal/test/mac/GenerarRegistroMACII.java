package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClemVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;


public class GenerarRegistroMACII {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(GenerarRegistroMACII.class);
	}
	


	
	@Test
	public void cancelarAnalisis(){
		
		Long idSol = new Long("1065891418");
		
		Solicitud solicitud = new Solicitud();		
		try {
			System.out.println(":::: Iniciando , obteniendo EJB");
			solicitud = EJBLocator.getServiceBusiness().consultarSolicitudPorId(idSol);			
			System.out.println("IdSolicitud: " + solicitud.getSolicitudId() + "-" + " : "
					+ solicitud.getTipoSolicitud().getDescripcion() + ", "
					+ solicitud.getEstadoSolicitud().getDescripcion() + ", " + solicitud.getFechaActualizacion());

			SujetoObligado so = obtieneSujetoObligado(solicitud.getTramites());
			if(solicitud.getSujetoObligado() == null){
				System.out.println("::Agregando sujeto obligado");
				solicitud.setSujetoObligado(so);				
			}
			System.out.println("Registro patronal: " + so.getNumeroRegistroPatronal());
			mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote ejb = EJBLocator.getServiceBusinessMAC();
			ejb.cancelarAnalisisPorRegistroPatronal(so.getNumeroRegistroPatronal(), EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), solicitud);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		System.out.println(":::: Termine");
	}	
	
	private SujetoObligado obtieneSujetoObligado(List<Tramite> tramites){
		Tramite tramite = null;
		TramiteSujetoObligado tso = null;
		for (Iterator<Tramite> iterator = tramites.iterator(); iterator.hasNext();) {
			tramite = iterator.next();
			System.out.println("IdTramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			System.out.println("Tramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			if(tramite instanceof TramiteSujetoObligado){
				tso = (TramiteSujetoObligado)tramite;
				System.out.println("IdTramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			}else{
				System.out.println("NO es instancia de TramiteSujetoObligado");
			}
		}		
		if(tso.getSujetoObligado() == null)
			this.log.debug("********** sujetoObligado es NULLLLLLLLLLLLLLL");
		
		return tso.getSujetoObligado();
	}
	
}
