package mx.imss.ctirss.web.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ValmdDTO implements Serializable{
	
	private String md1fechaInicio;
	
	private String md1fechaFin;
	
	private String md2fechaInicio;
	
	private String md2fechaFin;
	
	private BigDecimal md3ImporteImss;
	
	private BigDecimal md3ImporteReal;
	
	private String md4fechaFin;
	
	


	public BigDecimal getMd3ImporteImss() {
		return md3ImporteImss;
	}

	public void setMd3ImporteImss(BigDecimal md3ImporteImss) {
		this.md3ImporteImss = md3ImporteImss;
	}

	public BigDecimal getMd3ImporteReal() {
		return md3ImporteReal;
	}

	public void setMd3ImporteReal(BigDecimal md3ImporteReal) {
		this.md3ImporteReal = md3ImporteReal;
	}

	public String getMd1fechaInicio() {
		return md1fechaInicio;
	}

	public void setMd1fechaInicio(String md1fechaInicio) {
		this.md1fechaInicio = md1fechaInicio;
	}

	public String getMd1fechaFin() {
		return md1fechaFin;
	}

	public void setMd1fechaFin(String md1fechaFin) {
		this.md1fechaFin = md1fechaFin;
	}

	public String getMd2fechaInicio() {
		return md2fechaInicio;
	}

	public void setMd2fechaInicio(String md2fechaInicio) {
		this.md2fechaInicio = md2fechaInicio;
	}

	public String getMd2fechaFin() {
		return md2fechaFin;
	}

	public void setMd2fechaFin(String md2fechaFin) {
		this.md2fechaFin = md2fechaFin;
	}

	public String getMd4fechaFin() {
		return md4fechaFin;
	}

	public void setMd4fechaFin(String md4fechaFin) {
		this.md4fechaFin = md4fechaFin;
	}

	

}
