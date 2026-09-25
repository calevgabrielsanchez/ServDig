package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.ctirss.delta.framework.exceptions.TipoAclaracionCuentaIndividualException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronEntrada;

@Local
public interface CuentaIndividualUtilityLocal {


	CuentaIndividualVO convertEntityToModel(
			DitCtaIndNssCda cuentaIndividualEntity);

	InfoPatronEntrada crearPeticionPatron(String claveModalidad,
			String registroPatronal);

	List<PeriodosRegistroPatronal> crearPeriodos(List<CuentaIndividualVO> periodosTramite, List<String> listaNss) ;
	/**
	 * Verifica si la lista de periodos contiene el registro patronal
	 *
	 * @param list
	 *            lista de periodos sin agrupar
	 * @param rp
	 *            registro patronal
	 * @return true en caso de que si lo contenga
	 */
	boolean contieneRP(List<PeriodosRegistroPatronal> list, String rp);

	PeriodosRegistroPatronal crearEncabezadoRP(CuentaIndividualVO bandeja,
			List<String> listaNss);

	/**
	 * Obtener lista de nss
	 * 
	 * @param listaTramites
	 * @return lista de nss
	 */
	List<String> obtenerListaNss(List<DitDetalleNss> listaTramites);
	
	List<PeriodoCuentaIndividual> convertRespuestaWsToModeloDominio(RespuestaCuentaIndividual respuesta, Long idDetalleNss);
        
    void guardarPeriodos(CuentaIndividualNss cuentaIndividualNss);
    
    void actualizarPeriodos(CuentaIndividualNss cuentaIndividualNss);
    
    void guardarMovimientosAclaracionCuentaIndividual(String folio) throws TipoAclaracionCuentaIndividualException;
    
    CuentaIndividual findByFolio(String folioSolicitud);
    
    List<String> obtenerMovimientosAclaracion(String folio);
}
