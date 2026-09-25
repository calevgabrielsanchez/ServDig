package mx.gob.imss.ctirss.correccion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOPeriodosPresentadosObraVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface IObraService <T extends AbstractModel>{
	
	public SatObra validaObra(String numRegObra);
	public  DatosSalidaPaginador<SegEOIncidenciasObraVO> buscaIncidencias(DatosEntradaPaginador<SegEOIncidenciasObraGenericoVO> params, Long numRegObra);
	public  DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> buscaPeriodosPresentados(DatosEntradaPaginador<SegEOPeriodosPresentadosObraVO> params, Long numRegObra);

}
