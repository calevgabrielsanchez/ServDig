/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:TipoCausaAnalisisServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;

@Local
public interface TipoCausaAnalisisServiceEntityLocal{
	TipoCausaAnalisis consultaTipoCausa(Long tipoTramite, int tipoProceso);
	
	TipoCausaAnalisis consultaTipoTramite(Long tipoCausa);
	
	List<HistoricoDatosClem> consultaHistoricoTipoCausaPorAnalisis(Long cveIdAnalisis);
	
	void registraCausa(AnalisisClasificacionEmpresas analisisClasifEmp);
	
	List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis);
}
