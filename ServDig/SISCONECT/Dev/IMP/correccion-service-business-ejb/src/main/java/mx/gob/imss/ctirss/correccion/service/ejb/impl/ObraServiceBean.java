package mx.gob.imss.ctirss.correccion.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOPeriodosPresentadosObraVO;
import mx.gob.imss.ctirss.correccion.service.ejb.ObraServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.ObraDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.PatronDaoLocal;

@Stateless(name="obrasService", mappedName = "obrasService")
public class ObraServiceBean implements ObraServiceRemote{

	@EJB ObraDAOLocal daoObra;
	
	public SatObra validaObra(String numRegObra) {
		return daoObra.validaObra(numRegObra);
	}

	

	public DatosSalidaPaginador<SegEOIncidenciasObraVO> buscaIncidencias(DatosEntradaPaginador<SegEOIncidenciasObraGenericoVO> params, Long numRegObra) {
		return daoObra.buscaIncidencias(params, numRegObra);
	}


	public DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> buscaPeriodosPresentados(
			DatosEntradaPaginador<SegEOPeriodosPresentadosObraVO> params,
			Long numRegObra) {
		return daoObra.buscaPeriodosPresentados(params, numRegObra);
	}

}
