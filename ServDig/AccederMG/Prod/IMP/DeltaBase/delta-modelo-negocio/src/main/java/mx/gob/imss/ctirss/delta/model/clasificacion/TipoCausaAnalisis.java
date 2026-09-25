/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:TipoCausaAnalisis.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha:04/10/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;
import java.sql.Timestamp;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoCausaAnalisis extends AbstractModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7012808526040649126L;
	private long cveIdHistTipoCausa;
	private long cveIdTipoCausa;
	private String desCausa;
	private Long cveIdTipoTramite;
	
	//Estas dos variables se integraron debido al histórico de Causas
	private Long cveIdAnalisis;
	private Timestamp stmpFechaActualizado;
	
	public long getCveIdTipoCausa() {
		return cveIdTipoCausa;
	}
	public void setCveIdTipoCausa(long cveIdTipoCausa) {
		this.cveIdTipoCausa = cveIdTipoCausa;
	}
	public String getDesCausa() {
		return desCausa;
	}
	public void setDesCausa(String desCausa) {
		this.desCausa = desCausa;
	}
	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}
	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}
	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}
	public void setCveIdAnalisis(Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}
	public Timestamp getStmpFechaActualizado() {
		return stmpFechaActualizado;
	}
	public void setStmpFechaActualizado(Timestamp stmpFechaActualizado) {
		this.stmpFechaActualizado = stmpFechaActualizado;
	}
	public long getCveIdHistTipoCausa() {
		return cveIdHistTipoCausa;
	}
	public void setCveIdHistTipoCausa(long cveIdHistTipoCausa) {
		this.cveIdHistTipoCausa = cveIdHistTipoCausa;
	}
	@Override
	public String toString(){
		return "TipoCausaAnalisis [cveIdHistTipoCausa=" + cveIdHistTipoCausa
				+ ", cveIdTipoCausa=" + cveIdTipoCausa + ", desCausa="
				+ desCausa + ", cveIdTipoTramite=" + cveIdTipoTramite
				+ ", cveIdAnalisis=" + cveIdAnalisis
				+ ", stmpFechaActualizado=" + stmpFechaActualizado + "]";
	}
}