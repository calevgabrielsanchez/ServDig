package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcTrabajadores;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRT_TRABAJADORES")
@OnSearchLlavePrimaria		(atributos={"cveTrabajador"})
public class CrcTrabajadores extends AbstractCrcTrabajadores{
	

	private String folioCorreccion;
	private Integer periodo;
	private String indicadorTrabajador;
	private BigDecimal indPruebasel;
	private BigDecimal indExcsaltop;
	private BigDecimal indAnatiempext;
	private BigDecimal indAnahon;
	private String  registroPatronal;
	private Integer tipoTrabajador;
	
	
	private String cveSubdelegcionOficial;


	public CrcTrabajadores(Long cveTrabajador, Long cveSolicitudCorr,
			String nuNss, String txRfc, String nombreAsegurado,
			String apPaternoAsegurado, String apMaternoAsegurado,
			BigDecimal indPruebasel, BigDecimal indExcsaltop,
			BigDecimal indAnatiempext, BigDecimal indAnahon) {
		super(cveTrabajador, cveSolicitudCorr, nuNss, txRfc, nombreAsegurado,
				apPaternoAsegurado, apMaternoAsegurado);
		this.indPruebasel = indPruebasel;
		this.indExcsaltop = indExcsaltop;
		this.indAnatiempext = indAnatiempext;
		this.indAnahon = indAnahon;
	}
	
	public CrcTrabajadores(Long cveTrabajador, Long cveSolicitudCorr,
			String nuNss, String txRfc, String nombreAsegurado,
			String apPaternoAsegurado, String apMaternoAsegurado,
			String folioCorreccion) {
		super(cveTrabajador, cveSolicitudCorr, nuNss, txRfc, nombreAsegurado,
				apPaternoAsegurado, apMaternoAsegurado);
		this.folioCorreccion = folioCorreccion;
		
	}
	
	public CrcTrabajadores(Long cveTrabajador, Long cveSolicitudCorr,
			String nuNss, String txRfc, String nombreAsegurado,
			String apPaternoAsegurado, String apMaternoAsegurado,
			String folioCorreccion,String registroPatronal) {
		super(cveTrabajador, cveSolicitudCorr, nuNss, txRfc, nombreAsegurado,
				apPaternoAsegurado, apMaternoAsegurado);
		this.folioCorreccion = folioCorreccion;
		this.registroPatronal = registroPatronal;
		
	}

	


	public CrcTrabajadores() {
		super();
	}



	@Transient
	public String getFolioCorreccion() {
		return folioCorreccion;
	}



	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}


	@Transient
	public Integer getPeriodo() {
		return periodo;
	}



	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}


	@Transient
	public String getIndicadorTrabajador() {
		return indicadorTrabajador;
	}



	public void setIndicadorTrabajador(String indicadorTrabajador) {
		this.indicadorTrabajador = indicadorTrabajador;
	}

	
	@Transient
	public BigDecimal getIndPruebasel() {
		return indPruebasel;
	}




	public void setIndPruebasel(BigDecimal indPruebasel) {
		this.indPruebasel = indPruebasel;
	}



	@Transient
	public BigDecimal getIndExcsaltop() {
		return indExcsaltop;
	}




	public void setIndExcsaltop(BigDecimal indExcsaltop) {
		this.indExcsaltop = indExcsaltop;
	}



	@Transient
	public BigDecimal getIndAnatiempext() {
		return indAnatiempext;
	}




	public void setIndAnatiempext(BigDecimal indAnatiempext) {
		this.indAnatiempext = indAnatiempext;
	}



	@Transient
	public BigDecimal getIndAnahon() {
		return indAnahon;
	}




	public void setIndAnahon(BigDecimal indAnahon) {
		this.indAnahon = indAnahon;
	}


	@Transient
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	
	  /*METODOS VIRTUALES*/
	
	@Transient
	public String getPruebaSelectiva() {
		String retVal ="";
		if(indPruebasel!=null&&indPruebasel.intValue()==1)
			retVal = "APLICA";
		return retVal;
	}
	
	@Transient
	public String getSalariosTopados() {
		String retVal ="";
		if(indExcsaltop!=null&&indExcsaltop.intValue()==1)
			retVal = "APLICA";
		return retVal;
	}
	
	@Transient
	public String getTiempoExtra() {
		String retVal ="";
		if(indAnatiempext!=null&&indAnatiempext.intValue()==1)
			retVal = "APLICA";
		return retVal;
	}
	
	@Transient
	public String getHonorarios() {
		String retVal ="";
		if(indAnahon!=null&&indAnahon.intValue()==1)
			retVal = "APLICA";
		return retVal;
	}
	
	public String imprimeObjeto(){
		return new StringBuffer().append("CrcTrabajadores{")
				.append("cveTrabajador").append(this.getCveTrabajador()).append(";\n")
				.append("nuNss").append(this.getNuNss()).append(";\n")
				.append("rfc").append(this.getTxRfc()).append(";\n")
				.append("nombre").append(this.getNombreAsegurado()).append(";\n")
				.append("aPaterno").append(this.getApPaternoAsegurado()).append(";\n")
				.append("aMaterno").append(this.getApMaternoAsegurado()).append(";\n")
				.append("crtSolicitudcorr").append(this.getCveSolicitudCorr()).append(";\n")
				.append("fechaReg").append(this.getFecFechareg()).append(";\n")
				.append("cveUsuario").append(this.getCveUsuario())
				.append("}")
				.toString();
	}

	/**
	 * Retorna el valor cveSubdelegcionOficial
	 * @return  cveSubdelegcionOficial
	 */
	@Transient
	public String getCveSubdelegcionOficial() {
		return cveSubdelegcionOficial;
	}

	/**
	 * Asigna el valor del cveSubdelegcionOficial al atributo cveSubdelegcionOficial
	 * @param cveSubdelegcionOficial 
	 */
	public void setCveSubdelegcionOficial(String cveSubdelegcionOficial) {
		this.cveSubdelegcionOficial = cveSubdelegcionOficial;
	}

	@Column(name = "IND_TRABAJADOR", length = 30)
	public Integer getTipoTrabajador() {
		return tipoTrabajador;
	}

	public void setTipoTrabajador(Integer tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	}

	
	
}
