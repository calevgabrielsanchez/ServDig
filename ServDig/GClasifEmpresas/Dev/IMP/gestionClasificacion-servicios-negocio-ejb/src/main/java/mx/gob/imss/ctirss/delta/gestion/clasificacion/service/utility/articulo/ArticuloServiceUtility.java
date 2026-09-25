/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ArticuloServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.articulo
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.articulo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.ModelAccessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.persistence.DitArticulo;

@Stateless
public class ArticuloServiceUtility extends AbstractServiceUtility 
		implements ArticuloServiceUtilityLocal {

	@Override
	public ArticuloModel convertirEntityToModel(DitArticulo articulo)
			throws ModelAccessException {
		ArticuloModel response = new ArticuloModel(); 
		try{
			System.out.println(articulo.getDesInciso());
			response.setDesFraccion(articulo.getDesFraccion());
			if(articulo.getDesInciso()!=null){
				response.setDesInciso(articulo.getDesInciso());
			}
			response.setNumArticulo(articulo.getNumArticulo());
			response.setCveIdArticulo(new BigDecimal(articulo.getCveIdArticulo()));
		}catch (Exception exc) {
			log.error("Error:"+exc.getMessage());
			throw new ModelAccessException(exc.getMessage()); 
		}
		
		return response;
	}

	@Override
	public DitArticulo convertirModelToEntity(ArticuloModel articulo)
			throws ModelAccessException {
		DitArticulo response = new DitArticulo();
		try{
			response.setDesFraccion(articulo.getDesFraccion());
			response.setDesInciso(articulo.getDesInciso());
			response.setNumArticulo(articulo.getNumArticulo());
		}catch (Exception exc) {
			log.error("Error:"+exc.getMessage());
			throw new ModelAccessException(exc.getMessage()); 
		}
		return response;
	}

	@Override
	public List<ArticuloModel> convertirEntityToModel(List<DitArticulo> articulo)throws ModelAccessException {
		List<ArticuloModel> lstArticuloModel=new ArrayList<ArticuloModel>();
		ArticuloModel articuloModel=null;
		for(DitArticulo ditArticulo:articulo){
			articuloModel=new ArticuloModel();
			articuloModel.setCveIdArticulo(BigDecimal.valueOf(ditArticulo.getCveIdArticulo()));
			articuloModel.setNumArticulo(ditArticulo.getNumArticulo());
			articuloModel.setDesFraccion(ditArticulo.getDesFraccion());
			articuloModel.setDesInciso(ditArticulo.getDesInciso());
			articuloModel.setCveIdClem(BigDecimal.valueOf(ditArticulo.getDitDatosClem().getCveIdClem()));
			lstArticuloModel.add(articuloModel);
		}
		return lstArticuloModel;
	}
}