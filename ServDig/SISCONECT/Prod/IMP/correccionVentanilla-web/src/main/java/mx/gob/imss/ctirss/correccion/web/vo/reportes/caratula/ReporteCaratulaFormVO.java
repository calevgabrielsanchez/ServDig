package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;

public class ReporteCaratulaFormVO {

	private int tipoReporte;
	private int delegacion;
	private int subdelegacion;
	private String nombreDelegacion;
	private String nombreSubdelegacion;
	private String mensaje;
	private int anioReporte;
	private final String msgExito = ArchivosXLSCaratula.MENSAJE_REPORTE_GENERADO;
	
	private List<ItemVO> lsAniosFiscales;
	
	private List<ItemVO> lsTipoReporte;
	
	private List<ItemVO> lsDelegaciones;
	
	private List<ItemVO> lsSubDelegaciones;
	
	
	public ReporteCaratulaFormVO(){
		
		setLsTipoReporte(new ArrayList<ItemVO>());
		setLsAniosFiscales(new ArrayList<ItemVO>());
		setLsDelegaciones(new ArrayList<ItemVO>());
		setLsSubDelegaciones(new ArrayList<ItemVO>());
	}
	
	public int getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(int tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<ItemVO> getLsTipoReporte() {
		return lsTipoReporte;
	}

	public void setLsTipoReporte(List<ItemVO> lsTipoReporte) {
		this.lsTipoReporte = lsTipoReporte;
	}

	public String getMsgExito() {
		return msgExito;
	}

	public List<ItemVO> getLsAniosFiscales() {
		return lsAniosFiscales;
	}

	public void setLsAniosFiscales(List<ItemVO> lsAniosFiscales) {
		this.lsAniosFiscales = lsAniosFiscales;
	}

	public int getAnioReporte() {
		return anioReporte;
	}

	public void setAnioReporte(int anioReporte) {
		this.anioReporte = anioReporte;
	}

	public List<ItemVO> getLsDelegaciones() {
		return lsDelegaciones;
	}

	public void setLsDelegaciones(List<ItemVO> lsDelegaciones) {
		this.lsDelegaciones = lsDelegaciones;
	}

	public List<ItemVO> getLsSubDelegaciones() {
		return lsSubDelegaciones;
	}

	public void setLsSubDelegaciones(List<ItemVO> lsSubDelegaciones) {
		this.lsSubDelegaciones = lsSubDelegaciones;
	}

	public int getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(int delegacion) {
		this.delegacion = delegacion;
	}

	public int getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(int subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getNombreDelegacion() {
		return nombreDelegacion;
	}

	public void setNombreDelegacion(String nombreDelegacion) {
		this.nombreDelegacion = nombreDelegacion;
	}

	public String getNombreSubdelegacion() {
		return nombreSubdelegacion;
	}

	public void setNombreSubdelegacion(String nombreSubdelegacion) {
		this.nombreSubdelegacion = nombreSubdelegacion;
	}
	
	
	
	
}
