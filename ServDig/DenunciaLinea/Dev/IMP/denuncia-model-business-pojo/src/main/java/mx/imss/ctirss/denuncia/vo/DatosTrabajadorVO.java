package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosTrabajadorVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DenuncianteVO trabajador;
	private DenuncianteVO beneficiario;
	private DenuncianteVO representanteLegal;
	private List<MotivoDenVO> motivosDenuncia;
	private String observaciones;
	
	public DatosTrabajadorVO(){
		this.motivosDenuncia=new ArrayList<MotivoDenVO>();
		this.motivosDenuncia.add(new MotivoDenVO());
		this.trabajador=new DenuncianteVO();
		this.beneficiario=new DenuncianteVO();
		this.representanteLegal=new DenuncianteVO();
	}
	
	
	public DenuncianteVO getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(DenuncianteVO trabajador) {
		this.trabajador = trabajador;
	}
	public DenuncianteVO getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(DenuncianteVO beneficiario) {
		this.beneficiario = beneficiario;
	}
	public DenuncianteVO getRepresentanteLegal() {
		return representanteLegal;
	}
	public void setRepresentanteLegal(DenuncianteVO representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public List<MotivoDenVO> getMotivosDenuncia() {
		return motivosDenuncia;
	}
	public void setMotivosDenuncia(List<MotivoDenVO> motivosDenuncia) {
		this.motivosDenuncia = motivosDenuncia;
	}
	public String getObservaciones() {
		return observaciones!=null?observaciones.toUpperCase():observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatosTrabajadorVO [trabajador=");	
		builder.append(trabajador);
		builder.append(", beneficiario=");
		builder.append(beneficiario);
		builder.append(", representanteLegal=");
		builder.append(representanteLegal);
		builder.append(", motivosDenuncia=");
		builder.append(motivosDenuncia);
		builder.append(", observaciones=");
		builder.append(observaciones);
		builder.append("]");
		return builder.toString();
	}
	
}
