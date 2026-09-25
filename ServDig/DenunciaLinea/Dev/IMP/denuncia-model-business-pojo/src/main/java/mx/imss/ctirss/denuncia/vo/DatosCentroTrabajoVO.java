package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosCentroTrabajoVO  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fechaInicio;
	private String fechaTermino;
	private String actividadTrabajador;
	private int tieneContrato;
	private String numeroContrato;
	private String nombreJefeInm;
	private String horarioLabores;
	private String salario;
	private String vacaciones;
	private FormaPagoVO pagoPeriodoSal;	
	private String diasVacaciones;
	private String aguinaldoAnual;
	private String diasAguinaldo;
	private String gratificacion;
	private String comisiones;
	private String baseComision;	
	private FormaPagoVO pagoComprobantePago;	
	private List<FormaPagoVO> formasPago;
	private int tuvoRiesgoTrabajo;
	private String fechaRiesgoTrabajo;
	private String observaciones;
	private Long idDomicilio;
	private String desDomicilio;
	
	
	public DatosCentroTrabajoVO(){
		this.formasPago=new ArrayList<FormaPagoVO>();
		this.pagoComprobantePago=new FormaPagoVO();
		this.pagoPeriodoSal=new FormaPagoVO();
	}
	
	public String getDesDomicilio() {
		return desDomicilio!=null ?desDomicilio.toUpperCase():desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaTermino() {
		return fechaTermino;
	}
	public void setFechaTermino(String fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
	public String getActividadTrabajador() {
		return actividadTrabajador!=null ?actividadTrabajador.toUpperCase():actividadTrabajador;
	}
	public void setActividadTrabajador(String actividadTrabajador) {
		this.actividadTrabajador = actividadTrabajador;
	}
	public int getTieneContrato() {
		return tieneContrato;
	}
	public void setTieneContrato(int tieneContrato) {
		this.tieneContrato = tieneContrato;
	}
	public String getNombreJefeInm() {
		return nombreJefeInm!=null ? nombreJefeInm.toUpperCase():nombreJefeInm;
	}
	public void setNombreJefeInm(String nombreJefeInm) {
		this.nombreJefeInm = nombreJefeInm;
	}
	public String getHorarioLabores() {
		return horarioLabores!=null ? horarioLabores.toUpperCase():horarioLabores;
	}
	public void setHorarioLabores(String horarioLabores) {
		this.horarioLabores = horarioLabores;
	}
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
	public String getVacaciones() {
		return vacaciones;
	}
	public void setVacaciones(String vacaciones) {
		this.vacaciones = vacaciones;
	}
	
	public String getDiasVacaciones() {
		return diasVacaciones;
	}
	public void setDiasVacaciones(String diasVacaciones) {
		this.diasVacaciones = diasVacaciones;
	}
	public String getAguinaldoAnual() {
		return aguinaldoAnual;
	}
	public void setAguinaldoAnual(String aguinaldoAnual) {
		this.aguinaldoAnual = aguinaldoAnual;
	}
	public String getDiasAguinaldo() {
		return diasAguinaldo;
	}
	public void setDiasAguinaldo(String diasAguinaldo) {
		this.diasAguinaldo = diasAguinaldo;
	}
	public String getGratificacion() {
		return gratificacion;
	}
	public void setGratificacion(String gratificacion) {
		this.gratificacion = gratificacion;
	}
	public String getComisiones() {
		return comisiones;
	}
	public void setComisiones(String comisiones) {
		this.comisiones = comisiones;
	}
	public String getBaseComision() {
		return baseComision;
	}
	public void setBaseComision(String baseComision) {
		this.baseComision = baseComision;
	}

	public List<FormaPagoVO> getFormasPago() {
		return formasPago;
	}
	public void setFormasPago(List<FormaPagoVO> formasPago) {
		this.formasPago = formasPago;
	}
	public int getTuvoRiesgoTrabajo() {
		return tuvoRiesgoTrabajo;
	}
	public void setTuvoRiesgoTrabajo(int tuvoRiesgoTrabajo) {
		this.tuvoRiesgoTrabajo = tuvoRiesgoTrabajo;
	}
	public String getObservaciones() {
		return observaciones!=null ? observaciones.toUpperCase():observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	

	public FormaPagoVO getPagoPeriodoSal() {
		return pagoPeriodoSal;
	}

	public void setPagoPeriodoSal(FormaPagoVO pagoPeriodoSal) {
		this.pagoPeriodoSal = pagoPeriodoSal;
	}

	public FormaPagoVO getPagoComprobantePago() {
		return pagoComprobantePago;
	}

	public void setPagoComprobantePago(FormaPagoVO pagoComprobantePago) {
		this.pagoComprobantePago = pagoComprobantePago;
	}

	public String getFechaRiesgoTrabajo() {
		return fechaRiesgoTrabajo;
	}

	public void setFechaRiesgoTrabajo(String fechaRiesgoTrabajo) {
		this.fechaRiesgoTrabajo = fechaRiesgoTrabajo;
	}

	
	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Long getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatosCentroTrabajoVO [fechaInicio=");
		builder.append(fechaInicio);
		builder.append(", fechaTermino=");
		builder.append(fechaTermino);
		builder.append(", actividadTrabajador=");
		builder.append(actividadTrabajador);
		builder.append(", tieneContrato=");
		builder.append(tieneContrato);
		builder.append(", nombreJefeInm=");
		builder.append(nombreJefeInm);
		builder.append(", horarioLabores=");
		builder.append(horarioLabores);
		builder.append(", salario=");
		builder.append(salario);
		builder.append(", vacaciones=");
		builder.append(vacaciones);
		builder.append(", diasVacaciones=");
		builder.append(diasVacaciones);
		builder.append(", aguinaldoAnual=");
		builder.append(aguinaldoAnual);
		builder.append(", diasAguinaldo=");
		builder.append(diasAguinaldo);
		builder.append(", gratificacion=");
		builder.append(gratificacion);
		builder.append(", comisiones=");
		builder.append(comisiones);
		builder.append(", baseComision=");
		builder.append(baseComision);
		builder.append(", formasPago=");
		builder.append(formasPago);
		builder.append(", tuvoRiesgoTrabajo=");
		builder.append(tuvoRiesgoTrabajo);
		builder.append(", fechaRiesgoTrabajo=");
		builder.append(fechaRiesgoTrabajo);
		builder.append(", observaciones=");
		builder.append(observaciones);
		builder.append("]");
		return builder.toString();
	}
	
}
