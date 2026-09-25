/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cesarAgustin
 *
 */
public class ModuloDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6259129462678995704L;
	
	private long cveIdModulo;
	private long cveAccesoModulo;
	private String desModulo;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private DepartamentoDTO dptoDTO;
	private AreaNormativaDTO areaNormDTO;
	private EstatusDTO estatusDTO;
	private boolean nuevoReg = true;
	
	public ModuloDTO(){
		dptoDTO = new DepartamentoDTO();
		areaNormDTO = new AreaNormativaDTO();
		estatusDTO = new EstatusDTO();
	}
	
	public String datosBitacora(){
		String cadena = "idModulo:" + this.cveIdModulo + "|cveAccesoModulo:" + this.cveAccesoModulo+
				"|descripcionmodulo:" + this.desModulo + "|fechaRegistroActualizado:" + this.getFecRegistroActualizado() +
				"|fecharegistroAlta:" + this.fecRegistroAlta + "|fecharegistrobaja:" + this.fecRegistroBaja + 
				"|depto:" + this.dptoDTO.getDesDepartamento() + "|areanormativa:" + this.getAreaNormDTO().getDesAreanorma();
		return cadena;
	}
	
	public ModuloDTO (Integer cveArea, String nombreArea, Integer cveDepartamento, String nombreDepartamento, Integer cveModulo, String nombreModulo)
	{
		areaNormDTO = new AreaNormativaDTO();
		dptoDTO = new DepartamentoDTO();
		areaNormDTO.setCveSsoareanorma(cveArea);
		areaNormDTO.setDesAreanorma(nombreArea);
		dptoDTO.setCveSsodepto(cveDepartamento);
		dptoDTO.setAreaNormativa(areaNormDTO);
		dptoDTO.setDesDepartamento(nombreDepartamento);
		this.cveIdModulo=cveModulo;
	    this.desModulo=nombreModulo;
	}
	
	public long getCveIdModulo() {
		return cveIdModulo;
	}
	public void setCveIdModulo(long cveIdModulo) {
		this.cveIdModulo = cveIdModulo;
	}
	public String getDesModulo() {
		return desModulo;
	}
	public void setDesModulo(String desModulo) {
		this.desModulo = desModulo;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public DepartamentoDTO getDptoDTO() {
		return dptoDTO;
	}
	public void setDptoDTO(DepartamentoDTO dptoDTO) {
		this.dptoDTO = dptoDTO;
	}
	public AreaNormativaDTO getAreaNormDTO() {
		return areaNormDTO;
	}
	public void setAreaNorm(AreaNormativaDTO areaNormDTO) {
		this.areaNormDTO = areaNormDTO;
	}
	public long getCveAccesoModulo() {
		return cveAccesoModulo;
	}
	public void setCveAccesoModulo(long cveAccesoModulo) {
		this.cveAccesoModulo = cveAccesoModulo;
	}

	public EstatusDTO getEstatusDTO() {
		return estatusDTO;
	}

	public void setEstatusDTO(EstatusDTO estatusDTO) {
		this.estatusDTO = estatusDTO;
	}

	public boolean isNuevoReg() {
		return nuevoReg;
	}

	public void setNuevoReg(boolean nuevoReg) {
		this.nuevoReg = nuevoReg;
	}

	public void setAreaNormDTO(AreaNormativaDTO areaNormDTO) {
		this.areaNormDTO = areaNormDTO;
	}
	
	
	
}
