package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="DIT_ACUERDO_DH")
public class DitAcuerdoDH implements Serializable {
	
	private static final long serialVersionUID = 3667604963311903369L;
		
		@Id
		@SequenceGenerator(name = "SEQ_DITACUERDODH", sequenceName = "SEQ_DITACUERDODH")
	    @GeneratedValue(generator = "SEQ_DITACUERDODH")
		@Column(name="CVE_ID_ACUERDO_DH")
		private Long cveIdAcuerdoDH;

		//REF_NUM_ACUERDO_DH
		
		@Column(name="REF_NUM_ACUERDO_DH", nullable=false, length=255)
		private String refNumAcuerdoDH;
		
		public Long getCveIdAcuerdoDH() {
			return cveIdAcuerdoDH;
		}

		public void setCveIdAcuerdoDH(Long cveIdAcuerdoDH) {
			this.cveIdAcuerdoDH = cveIdAcuerdoDH;
		}

		public String getRefNumAcuerdoDH() {
			return refNumAcuerdoDH;
		}

		public void setRefNumAcuerdoDH(String refNumAcuerdoDH) {
			this.refNumAcuerdoDH = refNumAcuerdoDH;
		}

		public Date getFecFinAcuerdo() {
			return fecFinAcuerdo;
		}

		public void setFecFinAcuerdo(Date fecFinAcuerdo) {
			this.fecFinAcuerdo = fecFinAcuerdo;
		}

		public Date getFecInicioAcuerdo() {
			return fecInicioAcuerdo;
		}

		public void setFecInicioAcuerdo(Date fecInicioAcuerdo) {
			this.fecInicioAcuerdo = fecInicioAcuerdo;
		}

		public DicEstadoAcuerdoDH getDicEstadoAcuerdoDH() {
			return dicEstadoAcuerdoDH;
		}

		public void setDicEstadoAcuerdoDH(DicEstadoAcuerdoDH dicEstadoAcuerdoDH) {
			this.dicEstadoAcuerdoDH = dicEstadoAcuerdoDH;
		}

		@Temporal( TemporalType.TIMESTAMP)
		@Column(name="FEC_FIN_ACUERDO", nullable=true)
		private Date fecFinAcuerdo;

	    @Temporal( TemporalType.TIMESTAMP)
		@Column(name="FEC_INICIO_ACUERDO", nullable=false)
		private Date fecInicioAcuerdo;

	    @Temporal( TemporalType.TIMESTAMP)
		@Column(name="FEC_REGISTRO_ACTUALIZADO")
		private Date fecRegistroActualizado;

	    @Temporal( TemporalType.TIMESTAMP)
		@Column(name="FEC_REGISTRO_ALTA", nullable=false)
		private Date fecRegistroAlta;

	    @Temporal( TemporalType.TIMESTAMP)    
		@Column(name="FEC_REGISTRO_BAJA")
		private Date fecRegistroBaja;

	    
	  //bi-directional many-to-one association to DicCaracter
	    @ManyToOne
		@JoinColumn(name="CVE_ID_ESTADO_ACUERDO_DH")
		private DicEstadoAcuerdoDH dicEstadoAcuerdoDH;
	    
	    

	  //bi-directional one-to-one association to DitDocumentoProbatorio
	    @OneToOne
	  	@JoinColumn(name="CVE_ID_TRAMITE")
	  	private DitTramite ditTramite;
	  	
	  	@OneToOne
	  	@JoinColumns({
			@JoinColumn(name="CVE_ID_PERSONA_INTEGRANTE", referencedColumnName="CVE_ID_PERSONA_INTEGRANTE"),
			@JoinColumn(name="CVE_ID_ASIGNACION_NSS", referencedColumnName="CVE_ID_ASIGNACION_NSS")		
			})
	  	private DitGrupoFamiliar ditGrupoFamiliar;
	  	
	  	@Column(name="REF_OBSERVACION", length = 500)
	  	private String refObservacion;
	  	
		public DitTramite getDitTramite() {
			return ditTramite;
		}

		public void setDitTramite(DitTramite ditTramite) {
			this.ditTramite = ditTramite;
		}
		

		public Date getFecRegistroActualizado() {
			return this.fecRegistroActualizado;
		}

		public void setFecRegistroActualizado(Date fecRegistroActualizado) {
			this.fecRegistroActualizado = fecRegistroActualizado;
		}

		public Date getFecRegistroAlta() {
			return this.fecRegistroAlta;
		}

		public void setFecRegistroAlta(Date fecRegistroAlta) {
			this.fecRegistroAlta = fecRegistroAlta;
		}

		public Date getFecRegistroBaja() {
			return this.fecRegistroBaja;
		}

		public void setFecRegistroBaja(Date fecRegistroBaja) {
			this.fecRegistroBaja = fecRegistroBaja;
		}

	

		/**
		 * @return the ditGrupoFamiliar
		 */
		public DitGrupoFamiliar getDitGrupoFamiliar() {
			return ditGrupoFamiliar;
		}

		/**
		 * @param ditGrupoFamiliar the ditGrupoFamiliar to set
		 */
		public void setDitGrupoFamiliar(DitGrupoFamiliar ditGrupoFamiliar) {
			this.ditGrupoFamiliar = ditGrupoFamiliar;
		}

		public String getRefObservacion() {
			return refObservacion;
		}

		public void setRefObservacion(String refObservacion) {
			this.refObservacion = refObservacion;
		}
		
	}