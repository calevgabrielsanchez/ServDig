package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDI_DATOS_EXTRAS database table.
 * 
 */
@Entity
@Table(name="FDI_DATOS_EXTRAS")
public class FdiDatosExtra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DICTAMEN", nullable=false, precision=22)
	private long idDictamen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_OMISIONES")
	private Date fhOmisiones;

	@Column(name="ID_SUBTIPO", nullable=false, length=1)
	private String idSubtipo;

	@Column(name="NU_MENSUALIDADES", precision=22)
	private BigDecimal nuMensualidades;

	@Column(name="NU_PERSONAS", precision=22)
	private BigDecimal nuPersonas;

	@Column(name="TX_CONCEPTOS", length=250)
	private String txConceptos;

	@Column(name="TX_LIMITACIONES", length=250)
	private String txLimitaciones;

	@Column(name="TX_LUGAR_FECHA", length=250)
	private String txLugarFecha;

	@Column(name="TX_MOTIVOS", length=250)
	private String txMotivos;

	@Column(name="TX_OBSERVACIONES", length=300)
	private String txObservaciones;

	@Column(name="TX_REGISTROS_PATRONALES", length=250)
	private String txRegistrosPatronales;

	@Column(name="TX_RUBROS", length=250)
	private String txRubros;

	@Column(name="TX_TPOPERSONA", length=1)
	private String txTpopersona;

	//bi-directional one-to-one association to FdtPeriodo
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

    public FdiDatosExtra() {
    }

	public long getIdDictamen() {
		return this.idDictamen;
	}

	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}

	public Date getFhOmisiones() {
		return this.fhOmisiones;
	}

	public void setFhOmisiones(Date fhOmisiones) {
		this.fhOmisiones = fhOmisiones;
	}

	public String getIdSubtipo() {
		return this.idSubtipo;
	}

	public void setIdSubtipo(String idSubtipo) {
		this.idSubtipo = idSubtipo;
	}

	public BigDecimal getNuMensualidades() {
		return this.nuMensualidades;
	}

	public void setNuMensualidades(BigDecimal nuMensualidades) {
		this.nuMensualidades = nuMensualidades;
	}

	public BigDecimal getNuPersonas() {
		return this.nuPersonas;
	}

	public void setNuPersonas(BigDecimal nuPersonas) {
		this.nuPersonas = nuPersonas;
	}

	public String getTxConceptos() {
		return this.txConceptos;
	}

	public void setTxConceptos(String txConceptos) {
		this.txConceptos = txConceptos;
	}

	public String getTxLimitaciones() {
		return this.txLimitaciones;
	}

	public void setTxLimitaciones(String txLimitaciones) {
		this.txLimitaciones = txLimitaciones;
	}

	public String getTxLugarFecha() {
		return this.txLugarFecha;
	}

	public void setTxLugarFecha(String txLugarFecha) {
		this.txLugarFecha = txLugarFecha;
	}

	public String getTxMotivos() {
		return this.txMotivos;
	}

	public void setTxMotivos(String txMotivos) {
		this.txMotivos = txMotivos;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public String getTxRegistrosPatronales() {
		return this.txRegistrosPatronales;
	}

	public void setTxRegistrosPatronales(String txRegistrosPatronales) {
		this.txRegistrosPatronales = txRegistrosPatronales;
	}

	public String getTxRubros() {
		return this.txRubros;
	}

	public void setTxRubros(String txRubros) {
		this.txRubros = txRubros;
	}

	public String getTxTpopersona() {
		return this.txTpopersona;
	}

	public void setTxTpopersona(String txTpopersona) {
		this.txTpopersona = txTpopersona;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
}