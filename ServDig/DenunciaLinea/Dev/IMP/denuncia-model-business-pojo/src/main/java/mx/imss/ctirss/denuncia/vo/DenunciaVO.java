package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;

import javax.swing.text.StyledEditorKit.BoldAction;

public class DenunciaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long cveDenuncia;
	private Long tipoDenunciante;
	private String folioDenuncia;
	private Integer tipoUsuario;
	private DatosTrabajadorVO datosTrabajadorVO;
	private DatosPatronVO datosPatronVO;
	private DatosCentroTrabajoVO datosCentroTrabajoVO;	
	private boolean finalizado;
	private boolean funcionario;
	private String aclaraciones;
	private Integer cveEstatus;
	private Integer cveSubdelegacion;
	private boolean consultaAcuse;
	private String fechaPresentacion;
	//private Integer sexoTrabajador;
	
	
	public DenunciaVO(){
		this.datosCentroTrabajoVO=new DatosCentroTrabajoVO();
		this.datosPatronVO=new DatosPatronVO();
		this.datosTrabajadorVO=new DatosTrabajadorVO();
	}
	
	
	public DatosCentroTrabajoVO getDatosCentroTrabajoVO() {
		return datosCentroTrabajoVO;
	}
	public void setDatosCentroTrabajoVO(DatosCentroTrabajoVO datosCentroTrabajoVO) {
		this.datosCentroTrabajoVO = datosCentroTrabajoVO;
	}
	public DatosPatronVO getDatosPatronVO() {
		return datosPatronVO;
	}
	public void setDatosPatronVO(DatosPatronVO datosPatronVO) {
		this.datosPatronVO = datosPatronVO;
	}
	public DatosTrabajadorVO getDatosTrabajadorVO() {
		return datosTrabajadorVO;
	}
	public void setDatosTrabajadorVO(DatosTrabajadorVO datosTrabajadorVO) {
		this.datosTrabajadorVO = datosTrabajadorVO;
	}
	public Long getCveDenuncia() {
		return cveDenuncia;
	}
	public void setCveDenuncia(Long cveDenuncia) {
		this.cveDenuncia = cveDenuncia;
	}


	public Long getTipoDenunciante() {
		return tipoDenunciante;
	}


	public void setTipoDenunciante(Long tipoDenunciante) {
		this.tipoDenunciante = tipoDenunciante;
	}

	
	public boolean isFinalizado() {
		return finalizado;
	}


	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DenunciaVO [cveDenuncia=");
		builder.append(cveDenuncia);
		builder.append(", tipoDenunciante=");
		builder.append(tipoDenunciante);
		builder.append(", datosTrabajadorVO=");
		builder.append(datosTrabajadorVO);
		builder.append(", datosPatronVO=");
		builder.append(datosPatronVO);
		builder.append(", datosCentroTrabajoVO=");
		builder.append(datosCentroTrabajoVO);
		builder.append("]");
		return builder.toString();
	}


	public Integer getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public String getAclaraciones() {
		
		return aclaraciones!=null ? aclaraciones.toUpperCase():aclaraciones;
	}


	public void setAclaraciones(String aclaraciones) {
		this.aclaraciones = aclaraciones;
	}


	public String getFolioDenuncia() {
		return folioDenuncia!=null ? folioDenuncia.toUpperCase():folioDenuncia;
	}


	public void setFolioDenuncia(String folioDenuncia) {
		this.folioDenuncia = folioDenuncia;
	}


	public boolean isFuncionario() {
		return funcionario;
	}


	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}


	public Integer getCveEstatus() {
		return cveEstatus;
	}


	public void setCveEstatus(Integer cveEstatus) {
		this.cveEstatus = cveEstatus;
	}
	
	
	
	//public Integer getSexoTrabajador() {
	//	return sexoTrabajador;
	//}


	//public void setSexoTrabajador(Integer sexoTrabajador) {
	//	this.sexoTrabajador = sexoTrabajador;
	//}


	public Integer getCveSubdelegacion() {
		return cveSubdelegacion;
	}


	public void setCveSubdelegacion(Integer cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}


	public boolean isConsultaAcuse() {
		return consultaAcuse;
	}


	public void setConsultaAcuse(boolean consultaAcuse) {
		this.consultaAcuse = consultaAcuse;
	}


	public String getFechaPresentacion() {
		return fechaPresentacion;
	}


	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}


		
}
