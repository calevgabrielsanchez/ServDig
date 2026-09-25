package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class DatosValidacionCartaNoAdeudoWrapper extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private RespuestaOpinion32DEnum respuestaOpinion;
	private List<SujetoObligado> patrones;
	private List<SujetoObligado> patronesVigentes;
	private List<SujetoObligado> patronesHuelga;
	private List<SujetoObligado> patronesBaja;
	private List<AdeudoFiscal> adeudoCreditosImss;
	private List<AdeudoFiscal> adeudoCreditosRcv;
	private List<AdeudoFiscal> adeudoBaja251Imss;
	private List<AdeudoFiscal> adeudoBaja251Rcv;
	private List<AdeudoFiscal> adeudoHuelgaImss;
	private List<AdeudoFiscal> adeudoHuelgaRcv;
	private int numTrabajadores;
	private boolean tieneAdeudos;

	public RespuestaOpinion32DEnum getRespuestaOpinion() {
		return respuestaOpinion;
	}

	public void setRespuestaOpinion(RespuestaOpinion32DEnum respuestaOpinion) {
		this.respuestaOpinion = respuestaOpinion;
	}

	public List<SujetoObligado> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<SujetoObligado> patrones) {
		this.patrones = patrones;
	}

	public List<SujetoObligado> getPatronesVigentes() {
		return patronesVigentes;
	}

	public void setPatronesVigentes(List<SujetoObligado> patronesVigentes) {
		this.patronesVigentes = patronesVigentes;
	}

	public List<SujetoObligado> getPatronesHuelga() {
		return patronesHuelga;
	}

	public void setPatronesHuelga(List<SujetoObligado> patronesHuelga) {
		this.patronesHuelga = patronesHuelga;
	}

	public List<SujetoObligado> getPatronesBaja() {
		return patronesBaja;
	}

	public void setPatronesBaja(List<SujetoObligado> patronesBaja) {
		this.patronesBaja = patronesBaja;
	}

	public List<AdeudoFiscal> getAdeudoCreditosImss() {
		return adeudoCreditosImss;
	}

	public void setAdeudoCreditosImss(List<AdeudoFiscal> adeudoCreditosImss) {
		this.adeudoCreditosImss = adeudoCreditosImss;
	}

	public List<AdeudoFiscal> getAdeudoCreditosRcv() {
		return adeudoCreditosRcv;
	}

	public void setAdeudoCreditosRcv(List<AdeudoFiscal> adeudoCreditosRcv) {
		this.adeudoCreditosRcv = adeudoCreditosRcv;
	}

	public List<AdeudoFiscal> getAdeudoBaja251Imss() {
		return adeudoBaja251Imss;
	}

	public void setAdeudoBaja251Imss(List<AdeudoFiscal> adeudoBaja251Imss) {
		this.adeudoBaja251Imss = adeudoBaja251Imss;
	}

	public List<AdeudoFiscal> getAdeudoBaja251Rcv() {
		return adeudoBaja251Rcv;
	}

	public void setAdeudoBaja251Rcv(List<AdeudoFiscal> adeudoBaja251Rcv) {
		this.adeudoBaja251Rcv = adeudoBaja251Rcv;
	}

	public List<AdeudoFiscal> getAdeudoHuelgaImss() {
		return adeudoHuelgaImss;
	}

	public void setAdeudoHuelgaImss(List<AdeudoFiscal> adeudoHuelgaImss) {
		this.adeudoHuelgaImss = adeudoHuelgaImss;
	}

	public List<AdeudoFiscal> getAdeudoHuelgaRcv() {
		return adeudoHuelgaRcv;
	}

	public void setAdeudoHuelgaRcv(List<AdeudoFiscal> adeudoHuelgaRcv) {
		this.adeudoHuelgaRcv = adeudoHuelgaRcv;
	}

	public int getNumTrabajadores() {
		return numTrabajadores;
	}

	public void setNumTrabajadores(int numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public boolean isTieneAdeudos() {
		return tieneAdeudos;
	}

	public void setTieneAdeudos(boolean tieneAdeudos) {
		this.tieneAdeudos = tieneAdeudos;
	}

}
