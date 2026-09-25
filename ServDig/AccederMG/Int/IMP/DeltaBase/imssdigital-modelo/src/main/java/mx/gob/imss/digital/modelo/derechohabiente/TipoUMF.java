package mx.gob.imss.digital.modelo.derechohabiente;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoUMF", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
@XmlRootElement(name = "tipoUMF", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
public class TipoUMF implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger idTipoUMF;
	private String descripcion;

	public BigInteger getIdTipoUMF() {
		return idTipoUMF;
	}

	public void setIdTipoUMF(BigInteger idTipoUMF) {
		this.idTipoUMF = idTipoUMF;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
