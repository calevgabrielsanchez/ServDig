/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:TipoCausaAnalisisServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha:04/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface TipoCausaAnalisisServiceBusinessRemote{
	TipoCausaAnalisis consultaTipoCausa(Long tipoTramite, int tipoProceso);
	
	List<HistoricoDatosClem> consultaHistoricoTipoCausaPorAnalisis(Long cveIdAnalisis, Tramite tramite);
	
	TipoCausaAnalisis consultaCausa(Long tipoCausa);
	
	void registraCausa(AnalisisClasificacionEmpresas analisisClasifEmp);
	
	List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis, Tramite tramite);

    List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis);

}
