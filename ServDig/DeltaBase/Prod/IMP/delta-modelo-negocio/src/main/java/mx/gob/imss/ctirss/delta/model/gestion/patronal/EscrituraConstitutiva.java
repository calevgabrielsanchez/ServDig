package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

public class EscrituraConstitutiva extends AbstractModel {

	private static final long serialVersionUID = 1265859071646767977L;
	private Long cveEscrituraConstitutiva;
	private String numEscritura;
	private String numNotaria;
	private Municipio lugarExpedicion;
	private Date fechaExpedicion;
	private String folioMercantil;
	private Long cveIdPersonaMoral;
	private Long cveIdPatronSujetoObligado;
	private String numeroRegistroPatronal;
	
	private String seccion;
	private String partida;
	private String volumen;
	private String foja;
	
	
	
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public Long getCveEscrituraConstitutiva() {
		return cveEscrituraConstitutiva;
	}

	public void setCveEscrituraConstitutiva(Long cveEscrituraConstitutiva) {
		this.cveEscrituraConstitutiva = cveEscrituraConstitutiva;
	}

	public String getNumEscritura() {
		return numEscritura;
	}

	public void setNumEscritura(String numEscritura) {
		this.numEscritura = numEscritura;
	}

	public String getNumNotaria() {
		return numNotaria;
	}

	public void setNumNotaria(String numNotaria) {
		this.numNotaria = numNotaria;
	}



	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getFolioMercantil() {
		return folioMercantil;
	}

	public void setFolioMercantil(String folioMercantil) {
		this.folioMercantil = folioMercantil;
	}

	public Long getCveIdPersonaMoral() {
		return cveIdPersonaMoral;
	}

	public void setCveIdPersonaMoral(Long cveIdPersonaMoral) {
		this.cveIdPersonaMoral = cveIdPersonaMoral;
	}

	public Municipio getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(Municipio lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	
	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getPartida() {
		return partida;
	}

	public void setPartida(String partida) {
		this.partida = partida;
	}

	public String getVolumen() {
		return volumen;
	}

	public void setVolumen(String volumen) {
		this.volumen = volumen;
	}

	public String getFoja() {
		return foja;
	}

	public void setFoja(String foja) {
		this.foja = foja;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EscrituraConstitutiva [cveEscrituraConstitutiva=");
		builder.append(cveEscrituraConstitutiva);
		builder.append(", numEscritura=");
		builder.append(numEscritura);
		builder.append(", numNotaria=");
		builder.append(numNotaria);
		builder.append(", lugarExpedicion=");
		builder.append(lugarExpedicion);
		builder.append(", fechaExpedicion=");
		builder.append(fechaExpedicion);
		builder.append(", folioMercantil=");
		builder.append(folioMercantil);
		builder.append(", cveIdPersonaMoral=");
		builder.append(cveIdPersonaMoral);
		builder.append("]");
		return builder.toString();
	}

	



}
