/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 */

package mx.gob.imss.csdiss.sdroc.dto;

import java.io.Serializable;

/**
 * Clase base para los Data Transfer Object del sistema.
 * 
 * @author Brian Hernandez Garcia
 * @version 1.0
 */
public class TramiteBaseDTO implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -312003503259201788L;

	private Long cveIdTramite;
	private String numSeqNotaria;
	private String folio;	
	private String refMotivoAct;
	private String refAcuseInc;
	private String refSelloDigital;
	private String refCadenaOriginal;	
	
	public TramiteBaseDTO(){
		
	}

	

	public TramiteBaseDTO(Long cveIdTramite, String numSeqNotaria, String folio, String refMotivoAct,
			String refAcuseInc, String refSelloDigital, String refCadenaOriginal) {
		super();
		this.cveIdTramite = cveIdTramite;
		this.numSeqNotaria = numSeqNotaria;
		this.folio = folio;
		this.refMotivoAct = refMotivoAct;
		this.refAcuseInc = refAcuseInc;
		this.refSelloDigital = refSelloDigital;
		this.refCadenaOriginal = refCadenaOriginal;
	}



	public String getRefMotivoAct() {
		return this.refMotivoAct;
	}

	public void setRefMotivoAct(String refMotivoAct) {
		this.refMotivoAct = refMotivoAct;
	}

	public String getRefAcuseInc() {
		return this.refAcuseInc;
	}

	public void setRefAcuseInc(String refAcuseInc) {
		this.refAcuseInc = refAcuseInc;
	}
	
	public String getRefSelloDigital() {
		return refSelloDigital;
	}

	public void setRefSelloDigital(String refSelloDigital) {
		this.refSelloDigital = refSelloDigital;
	}

	public String getRefCadenaOriginal() {
		return refCadenaOriginal;
	}

	public void setRefCadenaOriginal(String refCadenaOriginal) {
		this.refCadenaOriginal = refCadenaOriginal;
	}	
	
	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public String getNumSeqNotaria() {
		return numSeqNotaria;
	}

	public void setNumSeqNotaria(String numSeqNotaria) {
		this.numSeqNotaria = numSeqNotaria;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}
