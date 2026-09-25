package mx.gob.imss.ctirss.correccion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOPeriodosPresentadosObraVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface IObraDAO<T extends AbstractModel> {

	public SatObra validaObra(String numRegObra);
	public DatosSalidaPaginador<SegEOIncidenciasObraVO> buscaIncidencias(DatosEntradaPaginador<SegEOIncidenciasObraVO> params, Long numRegObra);
	public DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> buscaPeriodosPresentados(DatosEntradaPaginador<SegEOPeriodosPresentadosObraVO> params, Long numRegObra);
	
}
