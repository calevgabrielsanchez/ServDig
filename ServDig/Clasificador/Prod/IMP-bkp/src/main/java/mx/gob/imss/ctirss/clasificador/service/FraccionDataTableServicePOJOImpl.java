/**
 * Clase de servicio para el soporte de la fncionalidad del Data Table
 * de resultados de fracciones.
 */
package mx.gob.imss.ctirss.clasificador.service;

import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend;
import mx.gob.imss.ctirss.clasificador.model.controller.FraccionDataTableSend;
import mx.gob.imss.ctirss.clasificador.repository.FraccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lucio Duran Silva.
 *
 */
@Service(value="fraccionDataTableService")
public class FraccionDataTableServicePOJOImpl implements DataTableService {
	
	
	
	private FraccionRepository fraccionRepository;
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DataTableService#filter(mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend)
	 */
	public AbstractDataTableReply filter(AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		return this.fraccionRepository.obtenerFraccionesActivasPorGrupo(
				dtFraccion.getCveGrupo(),dtFraccion.getCveDivision(),  dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}

	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DataTableService#filter(mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend)
	 */
	public AbstractDataTableReply filterXPalabraAnterior(AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		
		
		return this.fraccionRepository.obtenerFraccionesActivasPorPalabraClaveAnterior(
				dtFraccion.getCveGrupo(), dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}

	
	
	
	


	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DataTableService#filterXNumeroAnterior(mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend)
	 */
	public AbstractDataTableReply filterXNumeroAnterior(
			AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		return this.fraccionRepository.obtenerFraccionesActivasPorNumeroAnterior(
				dtFraccion.getCveGrupo(), dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}


	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DataTableService#filterXPalabra(mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend)
	 */
	public AbstractDataTableReply filterXPalabra(
			AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		return this.fraccionRepository.obtenerFraccionesActivasPorPalabra(
				dtFraccion.getCveGrupo(), dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DataTableService#filterXNumero(mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend)
	 */
	public AbstractDataTableReply filterXNumero(
			AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		return this.fraccionRepository.obtenerFraccionesActivasPorNumero(
				dtFraccion.getCveGrupo(), dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}
	
	
	
	
	
	
	/**
	 * @param fraccionRepository the fraccionRepository to set
	 */
	@Autowired
	public void setFraccionRepository(FraccionRepository fraccionRepository) {
		this.fraccionRepository = fraccionRepository;
	}


	public AbstractDataTableReply filterXPalabraEnAnterior(
			AbstractDataTableSend dtParams) {
FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		
		
		return this.fraccionRepository.obtenerFraccionesInactivasPorPalabraClave(
				dtFraccion.getsSearch(),
				dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}


	public AbstractDataTableReply filterXNumeroEnAnterior(
			AbstractDataTableSend dtParams) {
		FraccionDataTableSend dtFraccion = (FraccionDataTableSend) dtParams;
		return this.fraccionRepository.obtenerFraccionesInactivasPorNumeroAnterior(
				dtFraccion.getsSearch(), dtFraccion.getiDisplayLength(), dtFraccion.getiDisplayStart());
	}





}
