package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfdiRelacionados")
public class CfdiRelacionadosDTO implements Serializable {

	private static final long serialVersionUID = -7816611193868316813L;

	private String tipoRelacion;
	private CfdiRelacionadoDTO[] listCfdiRelacionadoDTOs;

	public String getTipoRelacion() {
		return tipoRelacion;
	}

	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}

	public CfdiRelacionadoDTO[] getListCfdiRelacionadoDTOs() {
		return listCfdiRelacionadoDTOs;
	}

	public void setListCfdiRelacionadoDTOs(
			CfdiRelacionadoDTO[] listCfdiRelacionadoDTOs) {
		this.listCfdiRelacionadoDTOs = listCfdiRelacionadoDTOs;
	}

}
