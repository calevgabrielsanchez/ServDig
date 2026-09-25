package mx.gob.imss.ultimospatrones.ws;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;



public class InfoUltimosPatronesAseguradoWSClient {
	
	
	private String socketTimeoutException = "Ocurrió un error de comunicación al intentar recuperar la cabeza de grupo familiar";

	public List<DetallePeriodoMovimientoAfiliatorioPatron> consultaUltimosPatronesAsegurado(String strNss) throws  DerechohabientesWebSserviceException{
		List <DetallePeriodoMovimientoAfiliatorioPatron> lstMovimiento = null;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			System.out.println("llamada consultaUltimosPatronesAsegurado");
			
			WsUltimosPatronesService servicio = new WsUltimosPatronesService();
			WsUltimosPatrones ws = servicio.getWsUltimosPatronesPort();
			
			RespuestaUltimosPatrones respuesta = ws.getUltimosPatrones(strNss);
			System.out.println("pasando la llamada respuesta codigo [" + respuesta.getCODIGO().getValue()+ "] mensaje ["+ respuesta.getMENSAJE().getValue()+"]" );
			Integer intRespuesta =  respuesta.getCODIGO().getValue();
			
			if(intRespuesta.intValue() == 0){
				lstMovimiento = new ArrayList<DetallePeriodoMovimientoAfiliatorioPatron>(); 
				List<UltimosPatronesVO> patrones =respuesta.getUltimosPatronesVO();
				System.out.println("tamaño del arreglo" + patrones.size());
				for(UltimosPatronesVO  patronMovimiento : patrones  ){
					System.out.println("el valor del  UltimosPatronesVO es [" +patronMovimiento.toString());
					DetallePeriodoMovimientoAfiliatorioPatron detalleMovimiento = new DetallePeriodoMovimientoAfiliatorioPatron();
					SujetoObligado patronSujeto = new SujetoObligado();
					PeriodoMovimientoAfiliatorio movimiento = new PeriodoMovimientoAfiliatorio();
					patronSujeto.setNumeroRegistroPatronal(patronMovimiento.getRegPatron());
					patronSujeto.setModalidad(new Modalidad());
					if(patronMovimiento.getCveIdModalidad()!= null){
						patronSujeto.getModalidad().setIdModalidad(patronMovimiento.getCveIdModalidad().longValue());
					}
					patronSujeto.getModalidad().setNumModalidad(patronMovimiento.getCveModal());
					if(patronMovimiento.getCveIdPatronGeneral()!= null){
						detalleMovimiento.setCveIdPatronGeneral(patronMovimiento.getCveIdPatronGeneral().longValue());
						System.out.println("el valor del idPatronGeneralDelServicio [" +detalleMovimiento.getCveIdPatronGeneral()+ "] y el que itera es [" +patronMovimiento.getCveIdPatronGeneral().longValue()+"]" );
					}
					if(StringUtils.isNotEmpty(patronMovimiento.getFecAlta())){
						movimiento.setFechaInicioMovimiento(format.parse(patronMovimiento.getFecAlta()));
					}
					if(StringUtils.isNotEmpty(patronMovimiento.getFecAlta())){
						movimiento.setFechaFinalMovimiento(format.parse(patronMovimiento.getFecBaja()));
					}
					movimiento.setNss(strNss);
					movimiento.setNrp(patronMovimiento.getRegPatron()+ patronMovimiento.getCveModal()+"");
					detalleMovimiento.setPeriodoMovimientoAfiliatorio(movimiento);
					detalleMovimiento.setSujetoObligado(patronSujeto);
					lstMovimiento.add(detalleMovimiento);
				}
			
			}else{
				 throw new DerechohabientesWebSserviceException(respuesta.getMENSAJE().getValue(), respuesta.getCODIGO().getValue()+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de ultimos patrones asegurado "
						+ "con NSS " + strNss + " ");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}
		
		return lstMovimiento;
	}

}
