package mx.gob.imss.ctirss.delta.utilities.base.model;

public class EditorCronModel extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String opcionDia;
	private String[] listOpcionDiaSemana;

	public String getOpcionDia() {
		return opcionDia;
	}

	public void setOpcionDia(String opcionDia) {
		this.opcionDia = opcionDia;
	}

	public String[] getListOpcionDiaSemana() {
		return listOpcionDiaSemana;
	}

	public void setListOpcionDiaSemana(String[] listOpcionDiaSemana) {
		this.listOpcionDiaSemana = listOpcionDiaSemana;
	}
}
