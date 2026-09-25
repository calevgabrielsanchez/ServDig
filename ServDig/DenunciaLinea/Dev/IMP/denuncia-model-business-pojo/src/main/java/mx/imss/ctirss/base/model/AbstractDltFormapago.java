package mx.imss.ctirss.base.model;


import java.sql.Blob;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.catalogos.model.DlcTipoconcepto;
import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.model.DltInfotrabajo;

import mx.imss.ctirss.framework.base.model.AbstractModel;



/**
 * The persistent class for the DLT_FORMAPAGO database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltFormapago extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AbstractDltFormapagoPK id;

	@Column(name="DES_ESPECIFIQUE")
	private String desEspecifique;
	
	@Column(name="NOM_DOCUMENTO")
	private String nomDocumento;


	@Column(name="REF_DOCUMENTO")
	@Lob
	private byte[] refDocumento;
	

	//bi-directional many-to-one association to DlcTipoconcepto
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_CONCEPTO", insertable=false, updatable=false)
	private DlcTipoconcepto dlcTipoconcepto;

	//bi-directional many-to-one association to DlcTiposformapago
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_FORMAPAGO", insertable=false, updatable=false)
	private DlcTiposformapago dlcTiposformapago;

	//bi-directional many-to-one association to DltInfotrabajo
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_INFOTRABAJO", insertable=false, updatable=false)
	private DltInfotrabajo dltInfotrabajo;

  

	public AbstractDltFormapagoPK getId() {
		return this.id;
	}

	public void setId(AbstractDltFormapagoPK id) {
		this.id = id;
	}
	
	public String getDesEspecifique() {
		return this.desEspecifique;
	}

	public void setDesEspecifique(String desEspecifique) {
		this.desEspecifique = desEspecifique;
	}

	public byte[ ]  getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(byte[ ]  refDocumento) {
		this.refDocumento = refDocumento;
	}

	public DlcTipoconcepto getDlcTipoconcepto() {
		return this.dlcTipoconcepto;
	}

	public void setDlcTipoconcepto(DlcTipoconcepto dlcTipoconcepto) {
		this.dlcTipoconcepto = dlcTipoconcepto;
	}
	
	public DlcTiposformapago getDlcTiposformapago() {
		return this.dlcTiposformapago;
	}

	public void setDlcTiposformapago(DlcTiposformapago dlcTiposformapago) {
		this.dlcTiposformapago = dlcTiposformapago;
	}
	
	public DltInfotrabajo getDltInfotrabajo() {
		return this.dltInfotrabajo;
	}

	public void setDltInfotrabajo(DltInfotrabajo dltInfotrabajo) {
		this.dltInfotrabajo = dltInfotrabajo;
	}

	public String getNomDocumento() {
		return nomDocumento;
	}

	public void setNomDocumento(String nomDocumento) {
		this.nomDocumento = nomDocumento;
	}

	
}