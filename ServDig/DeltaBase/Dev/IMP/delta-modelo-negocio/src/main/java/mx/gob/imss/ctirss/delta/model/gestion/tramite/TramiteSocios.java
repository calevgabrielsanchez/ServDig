package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

@XmlRootElement
public class TramiteSocios extends Tramite {
	
	private static final long serialVersionUID = 7198838707004606665L;
	
	private Moral patron;
	private Fisica sociosFisico;
	private Moral socioMoral;
	private List<Socio> listaSocios;
	
	public Moral getPatron() {
		return patron;
	}
	public void setPatron(Moral patron) {
		this.patron = patron;
	}
	public Fisica getSociosFisico() {
		return sociosFisico;
	}
	public void setSociosFisico(Fisica sociosFisico) {
		this.sociosFisico = sociosFisico;
	}
	public Moral getSocioMoral() {
		return socioMoral;
	}
	public void setSocioMoral(Moral socioMoral) {
		this.socioMoral = socioMoral;
	}
	public List<Socio> getListaSocios() {
		return listaSocios;
	}
	public void setListaSocios(List<Socio> listaSocios) {
		this.listaSocios = listaSocios;
	}
	
}
