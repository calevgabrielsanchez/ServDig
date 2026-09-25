package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;

import java.util.Date;

@XmlRootElement
public class TramiteEscritoDesacuerdo extends Tramite {

	private static final long serialVersionUID = 1L;
	
	private Long idEscrito;
	private CausaDesacuerdo causaDesacuerdo;
	private String folioImpugnado;
	private String mail;
	private MotivosDesacuerdo motivosDesacuerdo;
	private Long registroProcesado;
	private String motivoDesacuerdo;
	private String folioRecepcion;
	private PatronRiesgosTrabajo patron;
	private Long anVigencia;
	private String claseAnterior;
	private String fracAnterior;
	private String primAnterior;
	private String trabajadorProm;
	private String fechNotRes;
	private String motivoDesacuerdo1;
	private String motivoDesacuerdo2;
	private String motivoDesacuerdo3;
	private String motivoDesacuerdo4;
	private String motivoDesacuerdo5;
	private String motivoDesacuerdo6;
	private String motivoDesacuerdo7;
	private String motivoDesacuerdo8;
	private String motivoDesacuerdo9;


	public TramiteEscritoDesacuerdo() {
		super();
		this.causaDesacuerdo = new CausaDesacuerdo();
		this.causaDesacuerdo.setMateriaDesacuerdo(new MateriaDesacuerdo());
	}
	
	public TramiteEscritoDesacuerdo(CausaDesacuerdo causaDesacuerdo,
			String folioImpugnado, String mail, MotivosDesacuerdo MotivosDesacuerdo,
			String motivoDesacuerdo) {
		this();
		this.causaDesacuerdo = causaDesacuerdo;
		this.folioImpugnado = folioImpugnado;
		this.mail = mail;
		this.motivosDesacuerdo = motivosDesacuerdo;
		this.motivoDesacuerdo = motivoDesacuerdo;
	}

	public TramiteEscritoDesacuerdo(Long idEscrito,
			CausaDesacuerdo materiaDeterminacion, String folioImpugnado,
			String mail, MotivosDesacuerdo motivosDesacuerdo, String motivoDesacuerdo) {
		this(materiaDeterminacion, folioImpugnado, mail, motivosDesacuerdo, motivoDesacuerdo);
		this.idEscrito = idEscrito;
	}

	public TramiteEscritoDesacuerdo(Long idEscrito, CausaDesacuerdo causaDesacuerdo,String folioImpugnado,String mail,
									MotivosDesacuerdo motivosDesacuerdo,Long registroProcesado,String motivoDesacuerdo,
									String folioRecepcion,PatronRiesgosTrabajo patron,Long anVigencia,String claseAnterior,
									String fracAnterior,String primAnterior,String trabajadorProm,String fechNotRes,
									String motivoDesacuerdo1,String motivoDesacuerdo2,String motivoDesacuerdo3,
									String motivoDesacuerdo4,String motivoDesacuerdo5,String motivoDesacuerdo6,
									String motivoDesacuerdo7,String motivoDesacuerdo8,String motivoDesacuerdo9) {
		this.idEscrito = idEscrito;
		this.causaDesacuerdo = causaDesacuerdo;
		this.folioImpugnado = folioImpugnado;
		this.mail = mail;
		this.motivosDesacuerdo = motivosDesacuerdo;
		this.registroProcesado = registroProcesado;
		this.motivoDesacuerdo = motivoDesacuerdo;
		this.folioRecepcion = folioRecepcion;
		this.patron = patron;
		this.anVigencia = anVigencia;
		this.claseAnterior = claseAnterior;
		this.fracAnterior = fracAnterior;
		this.primAnterior = primAnterior;
		this.trabajadorProm = trabajadorProm;
		this.fechNotRes = fechNotRes;
		this.motivoDesacuerdo1 = motivoDesacuerdo1;
		this.motivoDesacuerdo2 = motivoDesacuerdo2;
		this.motivoDesacuerdo3 = motivoDesacuerdo3;
		this.motivoDesacuerdo4 = motivoDesacuerdo4;
		this.motivoDesacuerdo5 = motivoDesacuerdo5;
		this.motivoDesacuerdo6 = motivoDesacuerdo6;
		this.motivoDesacuerdo7 = motivoDesacuerdo7;
		this.motivoDesacuerdo8 = motivoDesacuerdo8;
		this.motivoDesacuerdo9 = motivoDesacuerdo9;
	}

	public CausaDesacuerdo getCausaDesacuerdo() {
		return causaDesacuerdo;
	}

	public void setCausaDesacuerdo(CausaDesacuerdo causaDesacuerdo) {
		this.causaDesacuerdo = causaDesacuerdo;
	}

	public Long getIdEscrito() {
		return idEscrito;
	}

	public void setIdEscrito(Long idEscrito) {
		this.idEscrito = idEscrito;
	}

	public String getFolioImpugnado() {
		return folioImpugnado;
	}
	
	public void setFolioImpugnado(String folioImpugnado) {
		this.folioImpugnado = folioImpugnado;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public MotivosDesacuerdo getMotivosDesacuerdo() {return motivosDesacuerdo;}

	public void setMotivosDesacuerdo(MotivosDesacuerdo motivosDesacuerdo) {this.motivosDesacuerdo = motivosDesacuerdo;}

	public String getMotivoDesacuerdo() {
		return motivoDesacuerdo;
	}
	
	public void setMotivoDesacuerdo(String motivoDesacuerdo) {
		this.motivoDesacuerdo = motivoDesacuerdo;
	}

	public String getFolioRecepcion() {
		return folioRecepcion;
	}

	public void setFolioRecepcion(String folioRecepcion) {
		this.folioRecepcion = folioRecepcion;
	}

	public PatronRiesgosTrabajo getPatron() {
		return patron;
	}

	public void setPatron(PatronRiesgosTrabajo patron) {
		this.patron = patron;
	}

	public Long getRegistroProcesado() {
		return registroProcesado;
	}

	public void setRegistroProcesado(Long registroProcesado) {
		this.registroProcesado = registroProcesado;
	}

	public String toString() {
		String tramite = "idEscrito: " + this.idEscrito + ", folioImpugnado: " + this.folioImpugnado + ", motivosDesacuerdo: " + this.motivosDesacuerdo+
				", motivoDesacuerdo: " + this.motivoDesacuerdo ;
		return tramite;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getAnVigencia() {return anVigencia;}

	public void setAnVigencia(Long anVigencia) {this.anVigencia = anVigencia;}

	public String getClaseAnterior() {
		return claseAnterior;
	}

	public void setClaseAnterior(String claseAnterior) {
		this.claseAnterior = claseAnterior;
	}

	public String getFracAnterior() {
		return fracAnterior;
	}

	public void setFracAnterior(String fracAnterior) {
		this.fracAnterior = fracAnterior;
	}

	public String getPrimAnterior() {
		return primAnterior;
	}

	public void setPrimAnterior(String primAnterior) {
		this.primAnterior = primAnterior;
	}

	public String getTrabajadorProm() {
		return trabajadorProm;
	}

	public void setTrabajadorProm(String trabajadorProm) {
		this.trabajadorProm = trabajadorProm;
	}

	public String getFechNotRes() {
		return fechNotRes;
	}

	public void setFechNotRes(String fechNotRes) {
		this.fechNotRes = fechNotRes;
	}

	public String getMotivoDesacuerdo1() {return motivoDesacuerdo1;}

	public void setMotivoDesacuerdo1(String motivoDesacuerdo1) {this.motivoDesacuerdo1 = motivoDesacuerdo1;}

	public String getMotivoDesacuerdo2() {return motivoDesacuerdo2;}

	public void setMotivoDesacuerdo2(String motivoDesacuerdo2) {this.motivoDesacuerdo2 = motivoDesacuerdo2;}

	public String getMotivoDesacuerdo3() {return motivoDesacuerdo3;}

	public void setMotivoDesacuerdo3(String motivoDesacuerdo3) {this.motivoDesacuerdo3 = motivoDesacuerdo3;}

	public String getMotivoDesacuerdo4() {return motivoDesacuerdo4;}

	public void setMotivoDesacuerdo4(String motivoDesacuerdo4) {this.motivoDesacuerdo4 = motivoDesacuerdo4;}

	public String getMotivoDesacuerdo5() {return motivoDesacuerdo5;}

	public void setMotivoDesacuerdo5(String motivoDesacuerdo5) {this.motivoDesacuerdo5 = motivoDesacuerdo5;}

	public String getMotivoDesacuerdo6() {return motivoDesacuerdo6;}

	public void setMotivoDesacuerdo6(String motivoDesacuerdo6) {this.motivoDesacuerdo6 = motivoDesacuerdo6;}

	public String getMotivoDesacuerdo7() {return motivoDesacuerdo7;}

	public void setMotivoDesacuerdo7(String motivoDesacuerdo7) {this.motivoDesacuerdo7 = motivoDesacuerdo7;}

	public String getMotivoDesacuerdo8() {return motivoDesacuerdo8;}

	public void setMotivoDesacuerdo8(String motivoDesacuerdo8) {this.motivoDesacuerdo8 = motivoDesacuerdo8;}

	public String getMotivoDesacuerdo9() {return motivoDesacuerdo9;}

	public void setMotivoDesacuerdo9(String motivoDesacuerdo9) {this.motivoDesacuerdo9 = motivoDesacuerdo9;}
}
