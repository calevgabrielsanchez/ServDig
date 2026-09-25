package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.dao;

import java.util.List;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface DetBaseCotOmitidaDAO <T extends AbstractModel>{

	public List<CrcEjercicio> buscaEjercicio(CrcEjercicio model);
	public DatosSalidaPaginador<T> paginar(DatosEntradaPaginador<T> params);
	public List<T> buscaPercepciones(T model);
	public T calculaBalanza(T model);
	public T calculaMenos(T model);
	public ExcedentesTopadosVO calculaExcedenteTopado(ExcedentesTopadosVO excedentesTopadosVO);
	public T guardaDetBaseCotOm(T model);
	public T guardaDetBaseCotOmDet(T model);
	public T validaDetBaseCotOm(T model);
	
}
