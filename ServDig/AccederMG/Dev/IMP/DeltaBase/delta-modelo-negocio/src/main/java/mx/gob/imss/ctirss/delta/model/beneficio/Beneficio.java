package mx.gob.imss.ctirss.delta.model.beneficio;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class Beneficio extends AbstractModel {

	private static final long serialVersionUID = -243381322192251500L;

	private Integer idBeneficio;

	// Valores inciales para preparar el beneficio
	private boolean esPatron;
	private Fisica fisica;
	private List<SujetoObligado> listaSujetosObligados;

	private Date inicioVigencia;
	private Date finVigencia;
	private Date fechaBaja;
	private EstadoBeneficio estadoBeneficio;
	private TipoBeneficio tipoBeneficio;
	private MotivoCancelacionBeneficio motivoCancelacion;
	private DescuentoBeneficio descuentoActual;
	private List<DescuentoBeneficio> listaDescuentosBeneficio;

	private RespuestaRifSat respuestaRifSat;
	// Atributos para consulta WSBeneficioRissService
	private String rfc;
	private String curp;
	private boolean indicadorApartadoC;
	private List<String> listaNRPsMod10y13;
	private Long idSolicitud;
	private String tipoApartado;
	private Long cveAsignacion;
	private Integer idBeneficioPatronExistente;
	
	
	
	public Integer getIdBeneficio() {
		return idBeneficio;
	}

	public void setIdBeneficio(Integer idBeneficio) {
		this.idBeneficio = idBeneficio;
	}

	public boolean getEsPatron() {
		return esPatron;
	}

	public void setEsPatron(boolean esPatron) {
		this.esPatron = esPatron;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	public List<SujetoObligado> getListaSujetosObligados() {
		return listaSujetosObligados;
	}

	public void setListaSujetosObligados(
			List<SujetoObligado> listaSujetosObligados) {
		this.listaSujetosObligados = listaSujetosObligados;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFinVigencia() {
		return finVigencia;
	}

	public void setFinVigencia(Date finVigencia) {
		this.finVigencia = finVigencia;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public EstadoBeneficio getEstadoBeneficio() {
		return estadoBeneficio;
	}

	public void setEstadoBeneficio(EstadoBeneficio estadoBeneficio) {
		this.estadoBeneficio = estadoBeneficio;
	}

	public TipoBeneficio getTipoBeneficio() {
		return tipoBeneficio;
	}

	public void setTipoBeneficio(TipoBeneficio tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	public MotivoCancelacionBeneficio getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(
			MotivoCancelacionBeneficio motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public DescuentoBeneficio getDescuentoActual() {
		return descuentoActual;
	}

	public void setDescuentoActual(DescuentoBeneficio descuentoActual) {
		this.descuentoActual = descuentoActual;
	}

	public List<DescuentoBeneficio> getListaDescuentosBeneficio() {
		return listaDescuentosBeneficio;
	}

	public void setListaDescuentosBeneficio(
			List<DescuentoBeneficio> listaDescuentosBeneficio) {
		this.listaDescuentosBeneficio = listaDescuentosBeneficio;
	}

	public RespuestaRifSat getRespuestaRifSat() {
		return respuestaRifSat;
	}

	public void setRespuestaRifSat(RespuestaRifSat respuestaRifSat) {
		this.respuestaRifSat = respuestaRifSat;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public boolean isIndicadorApartadoC() {
		return indicadorApartadoC;
	}

	public void setIndicadorApartadoC(boolean indicadorApartadoC) {
		this.indicadorApartadoC = indicadorApartadoC;
	}

	public List<String> getListaNRPsMod10y13() {
		return listaNRPsMod10y13;
	}

	public void setListaNRPsMod10y13(List<String> listaNRPsMod10y13) {
		this.listaNRPsMod10y13 = listaNRPsMod10y13;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getTipoApartado() {
		return tipoApartado;
	}

	public void setTipoApartado(String tipoApartado) {
		this.tipoApartado = tipoApartado;
	}

	public Long getCveAsignacion() {
		return cveAsignacion;
	}

	public void setCveAsignacion(Long cveAsignacion) {
		this.cveAsignacion = cveAsignacion;
	}

	public Integer getIdBeneficioPatronExistente() {
		return idBeneficioPatronExistente;
	}

	public void setIdBeneficioPatronExistente(Integer idBeneficioPatronExistente) {
		this.idBeneficioPatronExistente = idBeneficioPatronExistente;
	}

}
