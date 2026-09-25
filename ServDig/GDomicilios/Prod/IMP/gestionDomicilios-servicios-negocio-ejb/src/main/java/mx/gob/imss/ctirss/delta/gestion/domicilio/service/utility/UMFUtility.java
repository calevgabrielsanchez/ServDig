package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicClavePresupuestal;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;

@Stateless( name = "umfUtility", mappedName = "umfUtility")
public class UMFUtility extends AbstractServiceUtility implements UMFUtilityLocal{
	
	@EJB
	private SubdelegacionUtilityLocal subdelegacionUtilityLocal;
	
	public DicUmf modelToPersist(UnidadMedicaFamiliar entrada){
		DicUmf salida=null;
		if(entrada !=null){
			try {
				salida=new DicUmf();
				salida.setCveIdUmf(entrada.getIdUMF());
				salida.setNomUnidad(entrada.getDescripcion());
				salida.setNomCorto(entrada.getNombreCorto());
				salida.setIndGeneracionCita(entrada.isGeneracionCita());
				salida.setNumConsultorio(entrada.getNoConsultorio());
				salida.setNumEconom(entrada.getNoEconomico());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_UNIDAD_MEDICA_FAMILIAR, e);
			}
			
		}
		
		return salida;	
	}
	
	public UnidadMedicaFamiliar persisToModel(DicUmf entrada){
		UnidadMedicaFamiliar salida=null;
		if(entrada!=null){
			try {
				salida=new UnidadMedicaFamiliar();
				salida.setIdUMF(entrada.getCveIdUmf());
				salida.setDescripcion(entrada.getNomUnidad());
				salida.setNombreCorto(entrada.getNomCorto());
				salida.setGeneracionCita(entrada.getIndGeneracionCita());
				salida.setNoConsultorio(entrada.getNumConsultorio());
				salida.setNoEconomico(entrada.getNumEconom());
				Subdelegacion subdelegacion = subdelegacionUtilityLocal.persisToModel(entrada.getDicSubdelegacion());
				salida.setSubdelegacion(subdelegacion);
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
			}
			
		}
		return salida;
	}
	
	public List<UnidadMedicaFamiliar> persisToModelList(List<DicUmf> entrada){
		
		List<UnidadMedicaFamiliar> salida=null;
		UnidadMedicaFamiliar umf = null;
		if(entrada!=null && entrada.size()>0){
			salida = new ArrayList<UnidadMedicaFamiliar>();
			for (DicUmf dicUmf : entrada) {
				umf = persisToModel(dicUmf);
				salida.add(umf);
			}
			
		}
		return salida;
	}
	
	public ClavePresupuestal convertEntityToModelClavePresupuestal(DicClavePresupuestal entity){
		if(entity==null)
			return null;
		
		ClavePresupuestal cvePresupuestal=new ClavePresupuestal();
		cvePresupuestal.setClavePresupuestal(entity.getCvePresupuestal());
		cvePresupuestal.setClavePresupuestalRecortada(entity.getCvePresupuestalRecortada());
		cvePresupuestal.setDescripcion(entity.getDesClavePresupuestal());
		cvePresupuestal.setIdClavePresupuestal(entity.getCveIdClavePresupuestal());
		return cvePresupuestal;
	}
	
}