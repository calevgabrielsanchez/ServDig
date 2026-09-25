package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.dictamen;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ConsultaReporteException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.dictamen.DictamenEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Stateless(name = "dictamenServiceBusiness", mappedName="dictamenServiceBusiness")
public class DictamenServiceBusiness extends AbstractServiceBusiness implements DictamenServiceBusinessRemote {

	@EJB
	private DictamenEntityLocal dictamenEntityLocal;
	
	@Override
	public List<EjercicioDictamen> getPeriodosDictamen() {
		
		return dictamenEntityLocal.getPeriodosDictamen();
	}

	@Override
	public List<DictamenDTO> buscarDictamentes(Long idDelegacion, Long idSubdelegacion, Long idPeriodo) { 
		
		return dictamenEntityLocal.buscarDictamentes(idDelegacion, idSubdelegacion, idPeriodo);
	}

	@Override
	public DatosSalidaPaginador<DictamenDTO> consultarDictamentesPaginado(
			DatosEntradaPaginador<DictamenDTO> datosEntrada) {
		// TODO Auto-generated method stub
		return dictamenEntityLocal.consultarDictamentesPaginado(datosEntrada);
	}

	@Override
	public List<ReporteAnalisis> consultarReporteAnalisisDictamen(
			DictamenDTO filtros, Integer rol) throws ConsultaReporteException {
		
		List<ReporteAnalisis> lista = new ArrayList<ReporteAnalisis>();
		
		log.debug("INICIO PARA CONSULTAR EL REPORTE DE ANALISIS EFECTUADOS");
				
		//si el usuario no es de nivel central se verifica el campo delegacion
		if( rol != CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue() && (filtros.getIdDelegacion() == null || filtros.getIdDelegacion().intValue() <= 0)){
			
			log.debug("************** La consulta no puede realizarce, no se ha recibido el campo de la delegacion, filtro.getDelegacion(): " + filtros.getDelegacion());
			throw new ConsultaReporteException("La consulta no se puede realizar, no se ha identificado la delegaci�n del usuario", 1780);
			
		}else if(filtros.getDelegacion() == null || filtros.getIdDelegacion().intValue() <= 0){
			log.debug("El usuario es normativo de nivel central");
			log.debug("La consulta es nacional, se valida el horario permitido para el reporte");

			/*String msj = Utiles.validaHorarioReportesNormativo();

			if(!msj.equals("true"))	{
				log.debug("************** " + msj);
				throw new ConsultaReporteException(msj, 1780);
			}*/
		}
		
		List<DictamenDTO> dictamenes = dictamenEntityLocal.buscarDictamentes(filtros);
		
		if(dictamenes != null && !dictamenes.isEmpty()){
			for(DictamenDTO dictamen: dictamenes) {
				ReporteAnalisis reporte=new ReporteAnalisis();
				reporte.setRegPatron(dictamen.getRegistroPatronal());		
				reporte.setRazonSocial(dictamen.getNombreRS());
				reporte.setDelegDesc(dictamen.getDelegacion());
				reporte.setSdelegDesc(dictamen.getSubdelegacion());
				reporte.setEstatus(dictamen.getStatus());
				
				lista.add(reporte);
			}
			
		}
		
		
		return lista;
	}
	
	@Override
	public Clasificacion getClasificacionDictamen(Long idPatronDictamen,String regPatronal){
		
		return dictamenEntityLocal.getClasificacionDictamen(idPatronDictamen, regPatronal);
	}

	@Override
    public String getEjercicioFiscal(Long idEjercicio){  
        return dictamenEntityLocal.getEjercicioFiscal(idEjercicio);
    }
	
}