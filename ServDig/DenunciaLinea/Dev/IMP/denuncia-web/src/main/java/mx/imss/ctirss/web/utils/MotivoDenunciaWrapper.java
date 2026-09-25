package mx.imss.ctirss.web.utils;

import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;

public class MotivoDenunciaWrapper {
      private boolean isSelected;
      public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public DlcMotivodenuncia getDlcMotivodenuncia() {
		return dlcMotivodenuncia;
	}
	public void setDlcMotivodenuncia(DlcMotivodenuncia dlcMotivodenuncia) {
		this.dlcMotivodenuncia = dlcMotivodenuncia;
	}
	private DlcMotivodenuncia dlcMotivodenuncia;
      
      
}
