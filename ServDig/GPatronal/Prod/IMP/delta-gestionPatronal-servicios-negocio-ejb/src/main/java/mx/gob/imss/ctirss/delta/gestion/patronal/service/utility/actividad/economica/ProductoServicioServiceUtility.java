package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Stateless
public class ProductoServicioServiceUtility extends AbstractServiceUtility
		implements ProductoServicioServiceUtilityLocal {

	@Override
	public DitProducto convertirModelToEntity(Producto model){
		
		DitProducto entity = new DitProducto();
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		
		System.out.println("SujetoObligado producto servicio entity 1: "+model);

		System.out.println("SujetoObligado producto servicio entity 2: "+model.getSujetoObligado());
		System.out.println("Entity: "+entity);
		System.out.println("Model Id: "+model.getId());
		if(model.getId()!=null){
			
			entity.setCveIdProducto(model.getId());
		}
		
		entity.setDesProducto(model.getDescripcion());
		System.out.println("SujetoObligado producto servicio entity 3: "+model.getSujetoObligado());
		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		
		entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		
		return entity;
	}

	@Override
	public Producto convertirEntityToModel(DitProducto entity) throws Exception {
		Producto model = new Producto();
		model.setId(entity.getCveIdProducto());
		model.setDescripcion(entity.getDesProducto());
		SujetoObligado so = new SujetoObligado();
		so.setCveIdSujetoObligado(entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		model.setSujetoObligado(so);
		return model;
	}

	@Override
	public List<Producto> convertListOfEntitiesToListOfModel(
			List<DitProducto> origen) throws Exception {
		List<Producto> models = new ArrayList<Producto>();
		for(DitProducto entity : origen){
			Producto model = convertirEntityToModel(entity);
			models.add(model);
		}
		return models;
		
	}

	@Override
	public List<DitProducto> convertListOfModelToListOfEntity(
			List<Producto> origen) {
		List<DitProducto> ditProductos = Collections.emptyList();
		
		if(origen != null){
			ditProductos = new ArrayList<DitProducto>();
		}
		for(Producto producto : origen){
			DitPatronSujetoObligado so = new DitPatronSujetoObligado();
			DitProducto ditProducto = new DitProducto();
			if(producto.getId()!=null){
				ditProducto.setCveIdProducto(producto.getId());
			}
			ditProducto.setDesProducto(producto.getDescripcion());
			so.setCveIdPatronSujetoObligado(producto.getSujetoObligado().getCveIdSujetoObligado());
			ditProducto.setFecRegistroAlta(Calendar.getInstance().getTime());
			ditProductos.add(ditProducto);
		}
		return ditProductos;
	}

}
