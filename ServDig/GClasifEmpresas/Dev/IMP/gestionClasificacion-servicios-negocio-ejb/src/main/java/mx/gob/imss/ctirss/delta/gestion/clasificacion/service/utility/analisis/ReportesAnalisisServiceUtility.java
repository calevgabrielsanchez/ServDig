/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ReportesAnalisisServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.persistence.DivReporteAnalisis;

@Stateless
public class ReportesAnalisisServiceUtility extends AbstractServiceUtility implements ReportesAnalisisServiceUtilityLocal  {
	
	@Override
	public List<ReporteAnalisis> convertListOfEntitiesToListOfModel(
			List<DivReporteAnalisis> origen) throws Exception {
		
		List<ReporteAnalisis> models = new ArrayList<ReporteAnalisis>();
		ReporteAnalisis model = null;
		DivReporteAnalisis rep = null;
		
		for (int x = 0; x < origen.size(); x++) {
			rep = origen.get(x);		
			
			model = new ReporteAnalisis();
			model.setClaseD(rep.getDesClaseD()!=null ?rep.getDesClaseD():"");
			model.setClaseR(rep.getDesClaseR() !=null ? rep.getDesClaseR():"");
			model.setCveIdAnalisis(rep.getCveIdAnalisis() !=null ?String.valueOf(rep.getCveIdAnalisis()):"");
			model.setCveIdDelegacion(rep.getCveIdDelegacion()!=null ?String.valueOf(rep.getCveIdDelegacion()):"");
			model.setCveIdEstatus(rep.getCveIdEstatusAnalisis()!=null?String.valueOf(rep.getCveIdEstatusAnalisis()):"");
			model.setCveIdSubdelegacion(rep.getCveIdSubdelegacion()!=null?String.valueOf(rep.getCveIdSubdelegacion()):"");
			model.setCveIdTipoPersona(rep.getCveIdTipoPersona()!=null?String.valueOf(rep.getCveIdTipoPersona()):"");
			model.setTipoPersona(rep.getDesTipoPersona()!=null?rep.getDesTipoPersona():"");
			model.setDelegDesc(rep.getDesSubdelegacion() != null ? rep.getDesDeleg() :"");
			model.setDesCausasAnalisis(rep.getDesCausasAnalisis() != null?rep.getDesCausasAnalisis():"");

			model.setFecPresentacion(rep.getFecPresentacion());

			if(rep.getCveIdEstatusAnalisis() != null){				
				if(rep.getCveIdEstatusAnalisis().intValue() != EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()){ //si el mov no esta asignado
					if(rep.getCveIdEstatusAnalisis().intValue() == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave() 
							|| rep.getCveIdEstatusAnalisis().intValue() == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave()) //si el mov ya esta autorizado
						model.setFecAutorizacion(rep.getFecAnalisis());
					else 
						model.setFecAnalisis(rep.getFecAnalisis());					
				}				
			}
			
			model.setFolioResolucion(rep.getNumFolioResolucion() != null ? rep.getNumFolioResolucion() : "");
			model.setFraccionD(rep.getDesFraccionD() != null ? rep.getDesFraccionD() : "");
			model.setFraccionR(rep.getDesFraccionR() != null ? rep.getDesFraccionR() : "");
			model.setIndRpc(rep.getIndRegPatClase() != null ? String.valueOf(rep.getIndRegPatClase()) : "0");
			model.setIndPsp(rep.getIndPrestaServicioPersonal() != null ? String.valueOf(rep.getIndPrestaServicioPersonal()) : "0");
			if( new BigInteger(model.getIndPsp()).equals(Constantes.IND_NO_ACTIVO) && new BigInteger(model.getIndRpc()).equals(Constantes.IND_NO_ACTIVO) )
				model.setDesIndRpc(TipoRegistroEnum.ARP.getDescripcion());
			else if( new BigInteger(model.getIndPsp()).equals(Constantes.IND_ACTIVO) && new BigInteger(model.getIndRpc()).equals(Constantes.IND_ACTIVO) )
				model.setDesIndRpc(TipoRegistroEnum.RPC.getDescripcion());
			else if( new BigInteger(model.getIndPsp()).equals(Constantes.IND_ACTIVO) && new BigInteger(model.getIndRpc()).equals(Constantes.IND_NO_ACTIVO) )
				model.setDesIndRpc(TipoRegistroEnum.PSP.getDescripcion());
			else
				model.setDesIndRpc("--");

			model.setNombreComercial(rep.getNombreComercial() != null ? rep.getNombreComercial() : "");
			model.setRazonSocial(rep.getRazonSocial() != null ? rep.getRazonSocial() : "");
			model.setNombre(rep.getNombre() != null ? rep.getNombre() : "");
			model.setPrimaD(rep.getNumPrimaD());
			model.setPrimaR(rep.getNumPrimaR() != null ? rep.getNumPrimaR() : "");
			model.setRegPatron(rep.getRegPatronCompleto());
			model.setSdelegDesc(rep.getDesSubdelegacion() != null ? rep.getDesSubdelegacion() : "");
			model.setCveCiz(rep.getCveCiz() != null ? String.valueOf(rep.getCveCiz()): "");
			model.setIndModAut(rep.getIndModAut()!= null ? String.valueOf(rep.getIndModAut()): "");		
			model.setDesTipoTramite(rep.getDesTipoTramite() != null ? rep.getDesTipoTramite() : "");
			model.setDesComentario(rep.getDesComentario() != null ? rep.getDesComentario() : "");
			model.setContModClem(rep.getContModClem() != null ? rep.getContModClem() : "");
			models.add(model);
		}

		return models;
	}	
	
}
