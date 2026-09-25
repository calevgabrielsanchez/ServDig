package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrtEjertrabajador;

@Entity
@Table(name = "CRT_EJERTRABAJADOR")
public class CrtEjertrabajador extends AbstractCrtEjertrabajador{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -520940391953387902L;
	
	private CrcTrabajadores crcTrabajadores;

	@Transient
	public CrcTrabajadores getCrcTrabajadores() {
		return crcTrabajadores;
	}

	public void setCrcTrabajadores(CrcTrabajadores crcTrabajadores) {
		this.crcTrabajadores = crcTrabajadores;
	}

	public CrtEjertrabajador(BigDecimal cveEjertrab, Long cveAcexoCorrPat,
			Long cveEjercicio, BigDecimal cveTrabajador, Date fecIngreso,
			BigDecimal nuAntiguedadAnios, String txDepartamento,
			Long cveCategoria, BigDecimal impSalariodiario,
			BigDecimal indPruebasel, BigDecimal indExcsaltop,
			BigDecimal indAnatiempext, BigDecimal indAnahon,
			String txActividad, Date fecFechareg, String cveUsuario,
			CrcTrabajadores crcTrabajadores) {
		super(cveEjertrab, cveAcexoCorrPat, cveEjercicio, cveTrabajador,
				fecIngreso, nuAntiguedadAnios, txDepartamento, cveCategoria,
				impSalariodiario, indPruebasel, indExcsaltop, indAnatiempext,
				indAnahon, txActividad, fecFechareg, cveUsuario);
		this.crcTrabajadores = crcTrabajadores;
	}

	public CrtEjertrabajador() {
		super();
	}
	
	

}
