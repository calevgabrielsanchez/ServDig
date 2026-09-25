/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ConcentradoServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.concentrado
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.concentrado;

import java.util.ArrayList;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ConcentradoServiceUtility extends AbstractServiceUtility 
		implements ConcentradoServiceUtilityLocal {

    private static final Logger log = LoggerFactory.getLogger(ConcentradoServiceUtility.class);

    public ArrayList<SumarizadoConcentrado> obtieneSumarizadoSubDelegacion(ArrayList<SubdelegacionesConcentrado> subDelegacionesVO,
			ArrayList<ElementoConcentrado> repDel)
			throws Exception {

		ArrayList<SumarizadoConcentrado> sum = new ArrayList<SumarizadoConcentrado>();
		SumarizadoConcentrado sumVO = null;
		SubdelegacionesConcentrado subDelVO = null;
		String tipoReg = null;
		
		int totalRegistros = 0;
		int ratificado = 0;
		int rectificado = 0;
		int improcedente = 0;
		int pendientes = 0;
		int baja = 0;
		int arp = 0;
		int psp = 0;
		int rpc = 0;

		int granTotal = 0;
		int totalRatificado = 0;
		int totalRectificado = 0;
		int totalImprocedente = 0;
		int totalBaja = 0;
		int totalPendientes = 0;
		int totalArp = 0;
		int totalPsp = 0;
		int totalRpc = 0;
		
		int idDel = 0;
		String del = "";
			
		for (int y = 0; y < subDelegacionesVO.size(); y++) {

			totalRegistros = 0;
			ratificado = 0;
			rectificado = 0;
			improcedente = 0;
			baja = 0;
			pendientes = 0;
			arp = 0;
			psp = 0;
			rpc = 0;

			subDelVO =  subDelegacionesVO.get(y);
					
			if (subDelVO.getSubDel() != null && subDelVO.getSubDel().trim().length() > 0) {

				idDel = subDelVO.getIdDel();
				del = subDelVO.getDel();
				sumVO = new SumarizadoConcentrado();
				ElementoConcentrado repVO = null;
				
				for (int f = 0; f < repDel.size(); f++) {
					
					repVO = repDel.get(f);

					if(subDelVO.getIdSubDel().intValue() == repVO.getIdSubDel().intValue()){
						totalRegistros += 1;
						
						if(repVO.getEstatus() == 5)
							ratificado += 1;
						else if(repVO.getEstatus() == 6)
							rectificado += 1;
						else if(repVO.getEstatus() == 1 || repVO.getEstatus() == 2 || repVO.getEstatus() == 3 || repVO.getEstatus() == 4
								|| repVO.getEstatus() == 7 || repVO.getEstatus() == 8 || repVO.getEstatus() == 10 || repVO.getEstatus() == 11
								|| repVO.getEstatus() == 12)
							pendientes += 1;
						else if(repVO.getEstatus() == 19)
							improcedente += 1;
						else if(repVO.getEstatus() == 20)
							baja += 1;						
						
						tipoReg = getTipoRegistro(repVO.getInd_marca_Clase(), repVO.getInd_serv_personal());
						
						if(tipoReg.equals("ARP"))
							arp += 1;
						else if(tipoReg.equals("PSP"))
							psp += 1;
						else  if(tipoReg.equals("RPC"))
							rpc += 1;
						
					} //if
					
				} //for
				
				sumVO.setTotalRegistros(totalRegistros);
				sumVO.setRatificado(ratificado);
				sumVO.setRectificado(rectificado);
				sumVO.setImprocedente(improcedente);
				sumVO.setBaja(baja);
				sumVO.setPendientes(pendientes);
				sumVO.setArp(arp);
				sumVO.setPsp(psp);
				sumVO.setRpc(rpc);
				sumVO.setIdDel(subDelVO.getIdDel());
				sumVO.setDel(subDelVO.getDel());
				sumVO.setIdSubDel(subDelVO.getIdSubDel());
				sumVO.setSubDel(subDelVO.getSubDel());				
				
				sum.add(sumVO);
				
				//suma de totales 					
				granTotal = granTotal + totalRegistros;
				totalRatificado = totalRatificado + ratificado;
				totalRectificado = totalRectificado + rectificado;
				totalImprocedente = totalImprocedente + improcedente;
				totalBaja = totalBaja + baja;
				totalPendientes = totalPendientes + pendientes;
				totalArp = totalArp +  arp;
				totalPsp = totalPsp + psp;
				totalRpc = totalRpc + rpc;
				

			} //if
			
		} //for			
			

		// se agregan los totales
		sumVO = new SumarizadoConcentrado();
		
		sumVO.setTotalRegistros(granTotal);
		sumVO.setRatificado(totalRatificado);
		sumVO.setRectificado(totalRectificado);
		sumVO.setImprocedente(totalImprocedente);
		sumVO.setPendientes(totalPendientes);
		sumVO.setBaja(totalBaja);
		sumVO.setArp(totalArp);
		sumVO.setPsp(totalPsp);
		sumVO.setRpc(totalRpc);
		sumVO.setIdDel(idDel);
		sumVO.setDel("TOTAL " + del);
		
		sum.add(sumVO);
		

		return sum;
	}
	
	private String getTipoRegistro(String marcaClase, String servPersonal) {
		
		if( (marcaClase == null || Integer.parseInt(marcaClase) == 0) && (servPersonal == null || Integer.parseInt(servPersonal) == 0) ){
			return "ARP";
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 1) && (marcaClase == null || Integer.parseInt(marcaClase) == 0) ){
			return "PSP";			
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 1) &&  (marcaClase!=null && Integer.parseInt(marcaClase) == 1) ){
			return "RPC";	
		}

		return "";

	}   
    
}
