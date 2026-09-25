package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.interfaces;

import java.util.List;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtBalanzaComp;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmitida;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

public interface DetBaseCotOmitidaService <T extends AbstractModel> {

	public List<CrtAnexosolcorrpat> buscaAnexosporidSolicitud(CrtSolicitudcorr solicitud);
	public List<CrcEjercicio> buscaEjercicio(CrcEjercicio ejercicio);
	public DatosSalidaPaginador<T> paginar(DatosEntradaPaginador<T> params);
	public CrtAnexosolcorrpat buscaAnexosById(CrtAnexosolcorrpat model);
	public List<T> buscaPercepciones(T model);
	public T calculaBalanza(T model);
	public T calculaMenos(T model);
	public ExcedentesTopadosVO calculaExcedenteTopado(ExcedentesTopadosVO excedentesTopadosVO);
	public T guardaDetBaseCotOm(T model);
	public T guardaDetBaseCotOmDet(T model);
	public T validaDetBaseCotOm(T model);
}
