package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.responsable.model.Bitacora;
import mx.gob.imss.cit.cda.web.app.responsable.model.Estatus;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

@Component
public class ReadBitacoraUtils {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
	
	@Autowired
	private DeltaUtils deltaUtils;
	
	private static final String USUARIO_INTERNET_BITACORA ="ASEGURADO";
	private static final String USUARIO_ERROR_SINDO = "SINDO";
	
	public Bitacora convertBitacora(DataPage dataPage) {
		Bitacora bitacora = new Bitacora();
		
		Page<Estatus> gridEstatus = new Page<Estatus>();
		
		gridEstatus.setData(obtenerGridBitacora(dataPage, bitacora));
		gridEstatus.setTotalOfRecords(dataPage.getTotalOfRecords());
		gridEstatus.setPageSize(dataPage.getPageSize());
		gridEstatus.setCurrentPage(dataPage.getCurrentPage());
		
		bitacora.setGridEstatus(gridEstatus);
		
		logReadBitacoraUtils(dataPage, bitacora);
		
		return bitacora;
	}
	
	@SuppressWarnings("unchecked")
	private List<Estatus> obtenerGridBitacora(DataPage dataPage, Bitacora bitacora) {
		List<ObservacionesSubdelegacion> observacionesSubdelegacion = (List<ObservacionesSubdelegacion>)dataPage.getData();
		List<Estatus> list = new ArrayList<Estatus>();
		if (observacionesSubdelegacion != null && !observacionesSubdelegacion.isEmpty()) {
			for (ObservacionesSubdelegacion observaciones : observacionesSubdelegacion) {
				list.add(procesaEstatus(observaciones));
			}
		}
		
		logReadBitacoraUtils(dataPage, bitacora);
		return list;

	}
	
	private Estatus procesaEstatus(ObservacionesSubdelegacion observaciones) {
		Estatus estatus = new Estatus();
		estatus.setAsignado(StringUtils.isNotBlank(observaciones.getAsignado())
				? nombrePersona(observaciones.getAsignado()) : "");
		estatus.setUsuario(StringUtils.isNotBlank(observaciones.getUsuario())
				? nombrePersona(observaciones.getUsuario()) : "");
		estatus.setFechaModificacion(observaciones.getFechaActualizacion() != null
				? deltaUtils.convertirDateToStringMask(observaciones.getFechaActualizacion(), "") : "");
		estatus.setEstatus(observaciones.getCveEstado() != null
				? EstadoNegocioEnum.obtenerDescripcionNegocio(observaciones.getCveEstado()) : "");
		estatus.setObservacion(
				StringUtils.isNotBlank(observaciones.getDetalle()) ? observaciones.getDetalle().toUpperCase() : "");
		
		return estatus;
	}
	
	
	private String nombrePersona(String curp){
		String nombreCompleto = (curp.equalsIgnoreCase(USUARIO_INTERNET_BITACORA)||curp.equalsIgnoreCase(USUARIO_ERROR_SINDO))?curp:"" ;
		if(!nombreCompleto.equalsIgnoreCase(USUARIO_INTERNET_BITACORA)&&!curp.equalsIgnoreCase(USUARIO_ERROR_SINDO)){
			try {			
				Usuario nombre = ((ResponsablesDelegacionRemote) responsablesDelegacionBusiness).recuperaUsuarioEsquemaSeguridadByCURP(curp);
				if(nombre != null && nombre.getFisica() != null && nombre.getFisica().getNombreCompleto() != null){
					nombreCompleto = nombre.getFisica().getNombreCompleto();
				}
			} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				Log.error("---CDA--- ErrorClienteWebserviceResponsablesSubdelegacionException {} ",e);
			}
		}
		return nombreCompleto;
	}
	
	private void logReadBitacoraUtils(DataPage dataPage, Bitacora bitacora){
		log.debug("CDA -- Bitacora: " + bitacora.toString());
		log.debug("CDA --- Page Size: " + Integer.toString(dataPage.getPageSize()));
		log.debug("CDA --- Total of pages: " + Long.toString(dataPage.getTotalOfPages()));
		log.debug("CDA --- Total of records: " + Long.toString(dataPage.getTotalOfRecords()));
	}
}
