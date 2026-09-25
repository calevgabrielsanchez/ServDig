/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:TipoCausaAnalisisServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha:04/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.TipoCausaAnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import static mx.gob.imss.ctirss.delta.model.enums.TramiteTipoConclusionEnum.EN_LINEA;

@Stateless(name="tipoCausaAnalisisServiceBusiness", mappedName="tipoCausaAnalisisServiceBusiness")
public class TipoCausaAnalisisServiceBusiness implements TipoCausaAnalisisServiceBusinessRemote {
	
	@EJB
	private TipoCausaAnalisisServiceEntityLocal tipoCausaAnalisisServiceEntity;
	
	@Override
	public TipoCausaAnalisis consultaTipoCausa(Long tipoTramite, int tipoProceso){
		return tipoCausaAnalisisServiceEntity.consultaTipoCausa(tipoTramite, tipoProceso);
	}
	
	@Override
	public List<HistoricoDatosClem> consultaHistoricoTipoCausaPorAnalisis(Long cveIdAnalisis, Tramite tramite){
		final HistoricoDatosClem historicoDatosClem = new HistoricoDatosClem();
		final List<HistoricoDatosClem> historico = new ArrayList<HistoricoDatosClem>();
		final TipoCausaAnalisis tipoCausaAnalisis = consultaTipoCausa(Long.valueOf(tramite.getTipoTramite().getIdTipoTramite().intValue()),
				EN_LINEA.getCodigo());
		
		historicoDatosClem.setCveIdTipoCausa(tipoCausaAnalisis.getCveIdTipoCausa());
		historicoDatosClem.setDesCausa(tipoCausaAnalisis.getDesCausa());
		historicoDatosClem.setStpHistDatosClem(new Timestamp(tramite.getFechaTramite().getTime()));
		
		historico.add(historicoDatosClem);
		historico.addAll(tipoCausaAnalisisServiceEntity.consultaHistoricoTipoCausaPorAnalisis(cveIdAnalisis));
		return historico;
	}

	@Override
	public TipoCausaAnalisis consultaCausa(Long tipoCausa){
		TipoCausaAnalisis tipoCausaAnalisis=new TipoCausaAnalisis();
		tipoCausaAnalisis=tipoCausaAnalisisServiceEntity.consultaTipoTramite(tipoCausa);
   		return tipoCausaAnalisis;
	}

	@Override
	public void registraCausa(AnalisisClasificacionEmpresas analisisClasifEmp){
		tipoCausaAnalisisServiceEntity.registraCausa(analisisClasifEmp);
	}

	@Override
	public List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis, Tramite tramite){
		List<TipoCausaAnalisis> lstTipoCausaAnalisis=new ArrayList<TipoCausaAnalisis>(); 
		final TipoCausaAnalisis tipoCausaAnalisis = consultaTipoCausa(Long.valueOf(tramite.getTipoTramite().getIdTipoTramite().intValue()),
				EN_LINEA.getCodigo());
		
		lstTipoCausaAnalisis.add(tipoCausaAnalisis);
		lstTipoCausaAnalisis.addAll(consultaHistoricoTipoCausa(cveIdAnalisis));
		return lstTipoCausaAnalisis;
	}

    @Override
    public List<TipoCausaAnalisis> consultaHistoricoTipoCausa(Long cveIdAnalisis) {
        return tipoCausaAnalisisServiceEntity.consultaHistoricoTipoCausa(cveIdAnalisis);
    }
}
