package mx.gob.imss.ctirss.delta.persistence;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_CLASIFICACION_PROPUESTA database table.
 * 
 */
@Entity
@Table(name="DIT_CLASIFICACION_PROPUESTA")
public class DitClasificacionPropuesta implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "DIT_CLASIFICACION_PROPUESTA_GENERATOR", sequenceName = "SEQ_DITCLASIFICACIONPROPUESTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_CLASIFICACION_PROPUESTA_GENERATOR")
	@Column(name="CVE_ID_CLASIFICACION_PROPUESTA")
	private long cveIdClasificacionPropuesta;

	@Column(name="DES_ACTIVIDAD_DETECTADA")
	private String desActividadDetectada;
	
	//Campo para guardar la prima sugerida
	@Column(name="PRIMA_SUGERIDA")
	private BigDecimal primaSugerida;

	//bi-directional many-to-one association to DicDivision
    @ManyToOne
	@JoinColumn(name="CVE_ID_DIVISION")
	private DicDivision dicDivision;

	//bi-directional many-to-one association to DicFraccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_FRACCION")
	private DicFraccion dicFraccion;

	//bi-directional many-to-one association to DicGrupo
    @ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO")
	private DicGrupo dicGrupo;

	//bi-directional many-to-one association to DitAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

    public DitClasificacionPropuesta() {
    }

	public long getCveIdClasificacionPropuesta() {
		return this.cveIdClasificacionPropuesta;
	}

	public void setCveIdClasificacionPropuesta(long cveIdClasificacionPropuesta) {
		this.cveIdClasificacionPropuesta = cveIdClasificacionPropuesta;
	}

	public String getDesActividadDetectada() {
		return this.desActividadDetectada;
	}

	public void setDesActividadDetectada(String desActividadDetectada) {
		this.desActividadDetectada = desActividadDetectada;
	}
	
	public BigDecimal getPrimaSugerida() {
		return primaSugerida;
	}
	
	public void setPrimaSugerida(BigDecimal primaSugerida) {
		this.primaSugerida = primaSugerida;
	}

	public DicDivision getDicDivision() {
		return this.dicDivision;
	}

	public void setDicDivision(DicDivision dicDivision) {
		this.dicDivision = dicDivision;
	}
	
	public DicFraccion getDicFraccion() {
		return this.dicFraccion;
	}

	public void setDicFraccion(DicFraccion dicFraccion) {
		this.dicFraccion = dicFraccion;
	}
	
	public DicGrupo getDicGrupo() {
		return this.dicGrupo;
	}

	public void setDicGrupo(DicGrupo dicGrupo) {
		this.dicGrupo = dicGrupo;
	}
	
	public DitAnalisisCe getDitAnalisisCe() {
		return this.ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}
	
}