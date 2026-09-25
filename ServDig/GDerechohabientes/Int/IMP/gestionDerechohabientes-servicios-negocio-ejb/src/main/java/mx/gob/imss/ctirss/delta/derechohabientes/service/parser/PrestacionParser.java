package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Prestacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PrestacionPorModalidad;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPrestacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionModDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPrestacion;

@Stateless(name = "prestacionParser", mappedName = "prestacionParser")
public class PrestacionParser extends AbstractServiceUtility implements PrestacionParserLocal{

	@Override
	public Prestacion persistToModel(DicPrestacionDerechohab dicPrestacionDerechohab) {
		Prestacion prestacion = null;
		
		if(dicPrestacionDerechohab != null) {
			prestacion = new Prestacion();
			prestacion.setIdPrestacion(dicPrestacionDerechohab.getCveIdPrestacionDerechohab());
			prestacion.setNomPrestacion(dicPrestacionDerechohab.getNomPrestacionDerechohab());
			prestacion.setIndPensionado(dicPrestacionDerechohab.getIndServicioPensiona());
		}
		return prestacion;
	}

	@Override
	public List<Prestacion> persistToModelList(
			List<DicPrestacionDerechohab> listaPres) {
		List<Prestacion> prestaciones = null;
		
		if(listaPres != null && !listaPres.isEmpty()) {
			prestaciones = new ArrayList<Prestacion>();
			
			for(DicPrestacionDerechohab dicPres: listaPres) {
				prestaciones.add(this.persistToModel(dicPres));
			}
		}
		
		return prestaciones;
	}

	@Override
	public TipoPrestacion persistToModelTipoPres(
			DicTipoPrestacion dicTipoPrestacion) {
		TipoPrestacion tipoPrestacion = null;
		
		if(dicTipoPrestacion != null) {
			tipoPrestacion = new TipoPrestacion();
			tipoPrestacion.setIdTipoPrestacion(dicTipoPrestacion.getCveIdTipoPrestacion());
			tipoPrestacion.setDescTipoPrestacion(dicTipoPrestacion.getDescTipoPrestacion());
		}
		
		return tipoPrestacion;
	}

	@Override
	public PrestacionPorModalidad persistToModelPresPorMod(
			DicPrestacionModDerechohab dicPrestacionModDerechohab) {
		PrestacionPorModalidad presPorMod = null;
		
		if(dicPrestacionModDerechohab != null) {
			presPorMod = new PrestacionPorModalidad();
			presPorMod.setTipoPrestacion(this.persistToModelTipoPres(dicPrestacionModDerechohab.getDicTipoPrestacion()));
			presPorMod.setPrestacion(this.persistToModel(dicPrestacionModDerechohab.getDicPrestacionDerechohab()));
			presPorMod.setModalidad(new Modalidad());
			presPorMod.getModalidad().setIdModalidad(dicPrestacionModDerechohab.getDicModalidad().getCveIdModalidad());
			presPorMod.getModalidad().setDesCorta(dicPrestacionModDerechohab.getDicModalidad().getDesNomModalidadCorto());
		}
		
		return presPorMod;
	}

	@Override
	public List<PrestacionPorModalidad> persistToModelListPresPorMod(
			List<DicPrestacionModDerechohab> list) {
		List<PrestacionPorModalidad> listaPres = null;
		
		if(list!= null && !list.isEmpty()) {
			listaPres = new ArrayList<PrestacionPorModalidad>();
			
			for(DicPrestacionModDerechohab diP: list) {
				listaPres.add(this.persistToModelPresPorMod(diP));
			}
		}
		
		return listaPres;
	}

	
	
}
