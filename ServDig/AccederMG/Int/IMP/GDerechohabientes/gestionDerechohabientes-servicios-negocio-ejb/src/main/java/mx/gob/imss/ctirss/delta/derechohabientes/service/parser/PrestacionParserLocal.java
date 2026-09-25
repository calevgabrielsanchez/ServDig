package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Prestacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PrestacionPorModalidad;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPrestacion;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionModDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPrestacion;

@Local
public interface PrestacionParserLocal {

	Prestacion persistToModel(DicPrestacionDerechohab dicPrestacionDerechohab);
	List<Prestacion> persistToModelList(List<DicPrestacionDerechohab> listaPres);
	TipoPrestacion persistToModelTipoPres(DicTipoPrestacion dicTipoPrestacion);
	PrestacionPorModalidad persistToModelPresPorMod(DicPrestacionModDerechohab dicPrestacionModDerechohab);
	List<PrestacionPorModalidad> persistToModelListPresPorMod(List<DicPrestacionModDerechohab> list);
}
