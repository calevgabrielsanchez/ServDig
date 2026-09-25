package mx.gob.imss.dacvass.scheduler.cron.service.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.autopac.cancelacion.cfdi.ClienteAutoPacCancelacionCfdi;
import mx.gob.imss.ctirss.autopac.modelo.PeticionConsultaCfdiDto;
import mx.gob.imss.ctirss.autopac.modelo.RespuestaConsultaCfdiDto;
import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;
import mx.gob.imss.dacvass.scheduler.cron.remote.ProcesoCronConsultaCfdiServiceRemote;
import mx.gob.imss.dacvass.scheduler.cron.service.constantes.Constantes;
import mx.gob.imss.dacvass.scheduler.cron.service.entity.ConsultaEstatusCfdiEntityLocal;
import mx.gob.imss.dacvass.scheduler.cron.service.utility.PropertiesConfigUtils;
import mx.gob.imss.dacvass.scheduler.cron.service.utility.Utilidades;

@Stateless(name="procesoCronConsultaCfdiServiceBusiness", mappedName="procesoCronConsultaCfdiServiceBusiness")
public class ProcesoCronConsultaCfdiServiceBusiness implements ProcesoCronConsultaCfdiServiceRemote {
	
	private static final Logger log = LoggerFactory.getLogger(ProcesoCronConsultaCfdiServiceBusiness.class);
	
	protected static final String RFC_IMSS_EMISOR = PropertiesConfigUtils.getPropertyConfig(Constantes.RFC_IMSS, new Object());
	
	@EJB
	ConsultaEstatusCfdiEntityLocal consultaEstatusCfdiEntity;
	
	ClienteAutoPacCancelacionCfdi clienteAutoPacCancelacionCfdi;

	@Override
	public void consultaEstatusCfdi() {
		
		List<ProcOdiCompFiscDto> listProcOdiCompFiscDto = null;
		PeticionConsultaCfdiDto peticionConsultaCfdiDto = null;
		RespuestaConsultaCfdiDto respuestaConsultaCfdiDto = null;
		
		listProcOdiCompFiscDto = consultaEstatusCfdiEntity.obtenerUuidParaConsultar();
		
		if (listProcOdiCompFiscDto != null) {
			for (ProcOdiCompFiscDto procOdiCompFiscDto : listProcOdiCompFiscDto) {
				clienteAutoPacCancelacionCfdi = new ClienteAutoPacCancelacionCfdi();
				peticionConsultaCfdiDto = new PeticionConsultaCfdiDto();
				
				peticionConsultaCfdiDto.setUuid(procOdiCompFiscDto.getUuid());
				peticionConsultaCfdiDto.setRfcEmisor(Utilidades.escaparAmpersand(RFC_IMSS_EMISOR));
				peticionConsultaCfdiDto.setRfcReceptor(Utilidades.escaparAmpersand(procOdiCompFiscDto.getRfc()));
				peticionConsultaCfdiDto.setMonto(obtenerMonto(procOdiCompFiscDto));
				
				log.info("########## UUID ["+peticionConsultaCfdiDto.getUuid()+"] ##########");
				log.info("########## RFC Emisor ["+peticionConsultaCfdiDto.getRfcEmisor()+"] ##########");
				log.info("########## RFC Receptor ["+peticionConsultaCfdiDto.getRfcReceptor()+"] ##########");
				log.info("########## MONTO ["+peticionConsultaCfdiDto.getMonto()+"] ##########");
				
				try {
					
					respuestaConsultaCfdiDto = clienteAutoPacCancelacionCfdi.invocarServicioAutoPacConsultaEstatusCfdi(peticionConsultaCfdiDto);
					
					procOdiCompFiscDto.setCveCodigoRespuesta(obtenerCodigoRespuesta(respuestaConsultaCfdiDto));
					
					consultaEstatusCfdiEntity.actualizaCodigoRespuesta(procOdiCompFiscDto);
					
				} catch (MalformedURLException e) {
					log.info("########## ERROR EN LA CONSULTA DE ESTATUS DE CFDI MalformedURLException ##########");
					e.printStackTrace();
				} catch (RemoteException e) {
					log.info("########## ERROR EN LA CONSULTA DE ESTATUS DE CFDI RemoteException ##########");
					e.printStackTrace();
				}
			}
		} else {
			log.info("########## LA LISTA DE LOS UUID PARA CONSULTAR ESTA NULA ##########");
		}
	}
	
	public String obtenerMonto(ProcOdiCompFiscDto procOdiCompFiscDto) {
		BigDecimal total = new BigDecimal(0);
		
		total = total.add(procOdiCompFiscDto.getSubToImss());
		total = total.add(procOdiCompFiscDto.getRecImss());
		total = total.add(procOdiCompFiscDto.getActImss());
		total = total.add(procOdiCompFiscDto.getSubToRcv());
		total = total.add(procOdiCompFiscDto.getRecRcv());
		total = total.add(procOdiCompFiscDto.getActRcv());
		total = total.setScale(6, RoundingMode.HALF_EVEN);
		
		return total.toString();
	}
	
	public String obtenerCodigoRespuesta(RespuestaConsultaCfdiDto respuestaConsultaCfdiDto) {
		String codigo = "910";
		
		if (respuestaConsultaCfdiDto != null && respuestaConsultaCfdiDto.getCodigoEstatus() != null) {
			if (respuestaConsultaCfdiDto.getCodigoEstatus().equals(Constantes.COMPROBANTE_SATISFACTORIO)) {
				if (respuestaConsultaCfdiDto.getEstado().equals(Constantes.ESTADO_CANCELADO)) {
					if (respuestaConsultaCfdiDto.getEstatusCancelacion() == null || 
							respuestaConsultaCfdiDto.getEstatusCancelacion().isEmpty() || 
							respuestaConsultaCfdiDto.getEstatusCancelacion().equals(Constantes.SIN_ACEPTACION)) {
						codigo = "900";
					} else if (respuestaConsultaCfdiDto.getEstatusCancelacion().equals(Constantes.CON_ACEPTACION)) {
						codigo = "903";
					} else if (respuestaConsultaCfdiDto.getEstatusCancelacion().equals(Constantes.VENCIDO)) {
						codigo = "904";
					}
				} else if (respuestaConsultaCfdiDto.getEstado().equals(Constantes.ESTADO_VIGENTE)) {
					if (respuestaConsultaCfdiDto.getEstatusCancelacion().equals(Constantes.RECHAZADA) || respuestaConsultaCfdiDto.getEstatusCancelacion().isEmpty()) {
						codigo = "907";
					} else if (respuestaConsultaCfdiDto.getEstatusCancelacion().equals(Constantes.PROCESO)) {
						codigo = "911";
					}
				}
			} else if (respuestaConsultaCfdiDto.getCodigoEstatus().equals(Constantes.EXPRESION_INVALIDA)) {
				codigo = "908";
			} else if (respuestaConsultaCfdiDto.getCodigoEstatus().equals(Constantes.COMPROBANTE_NO_ENCONTRADO)) {
				codigo = "909";
			}
		} else {
			codigo = "911";
		}
		
		log.info("########## CODIGO DE RESPUESTA ["+codigo+"] ##########");
		return codigo;
	}
	
//	public static void main(String[] args) {
//		ProcesoCronConsultaCfdiServiceRemote procesoCronConsultaCfdiServiceRemote = new ProcesoCronConsultaCfdiServiceBusiness();
//		ProcOdiCompFiscDto procOdiCompFiscDto = new ProcOdiCompFiscDto();
//		
//		procOdiCompFiscDto.setSubToImss(new BigDecimal(1162.34));
//		procOdiCompFiscDto.setRecImss(new BigDecimal(0));
//		procOdiCompFiscDto.setActImss(new BigDecimal(0));
//		procOdiCompFiscDto.setSubToRcv(new BigDecimal(604.13));
//		procOdiCompFiscDto.setRecRcv(new BigDecimal(0));
//		procOdiCompFiscDto.setActRcv(new BigDecimal(0));
//		
//		String monto = procesoCronConsultaCfdiServiceRemote.obtenerMonto(procOdiCompFiscDto);
//		
//		System.out.println(monto);
//	}
	
//	public static void main(String[] args) {
//		
//		List<String> listUuidAceptacion = new ArrayList<String>();
//		listUuidAceptacion.add("a88d4c50-59a9-495c-b54c-b6053129edc5");
//		listUuidAceptacion.add("f093c1e9-b667-4701-9fea-880a29fde93b");
//		listUuidAceptacion.add("8b63d92a-3a73-4b79-810e-8e2deb63ccb0");
//		listUuidAceptacion.add("d8e4dd1c-c1b4-4ce6-85f6-11bc49121cf2");
//		listUuidAceptacion.add("c1cadef0-006f-4d3f-ab54-10a3d3705829");
//		listUuidAceptacion.add("b0352f86-9890-48f6-9538-296b6223e6d9");
//		listUuidAceptacion.add("1ccd9d2f-b91f-4829-a283-98b0f4f5f0c5");
//		listUuidAceptacion.add("293630f0-dc74-4c09-9032-2b870ccd9214");
//		
//		List<String> listUuidRechazo = new ArrayList<String>();
//		listUuidRechazo.add("a3c3a7e9-f0b2-4ce6-919b-2d94d98d22ab");
//		listUuidRechazo.add("b68eeb99-11e6-44fe-af89-e3d14833384a");
//		listUuidRechazo.add("d0cc9e3b-be94-401e-8351-8c630b4ba96c");
//		listUuidRechazo.add("604a6bc9-5581-4278-93fd-14f11a0a101f");
//		listUuidRechazo.add("65bd31d9-128a-4bb8-965d-3ab2ef161a94");
//		listUuidRechazo.add("2bfbfbab-c131-492f-8c4e-98b7a2947a59");
//		listUuidRechazo.add("16597579-542d-4b10-b9e6-0894a5469d40");
//		listUuidRechazo.add("e160865a-7dfe-4010-8a11-365db3f1bb5f");
//		
//		String uuidAceptacion = "1ccd9d2f-b91f-4829-a283-98b0f4f5f0c5";
//		
//		if (listUuidAceptacion.contains(uuidAceptacion)) {
//			System.out.println("########## EL UUID ["+uuidAceptacion+"] ESTA EN EL ARRAY LIST DE ACEPTACION ##########");
//		} else {
//			System.out.println("########## EL UUID ["+uuidAceptacion+"] NO ESTA EN EL ARRAY LIST DE ACEPTACION ##########");
//		}
//		
//		String uuidRechazo = "2bfbfbab-c131-492f-8c4e-98b7a2947a59";
//		
//		if (listUuidRechazo.contains(uuidRechazo)) {
//			System.out.println("########## EL UUID ["+uuidRechazo+"] ESTA EN EL ARRAY LIST DE RECHAZO ##########");
//		} else {
//			System.out.println("########## EL UUID ["+uuidRechazo+"] NO ESTA EN EL ARRAY LIST DE RECHAZO ##########");
//		}
//		
//	}
	
//	public static void main(String[] args) {
//		String rfc = "BV&0608042U1";
//		
//		System.out.println(rfc);
//		
//		String rfcEspacado = Utilidades.escaparAmpersand(rfc);
//		
//		System.out.println(rfcEspacado);
//	}

}
