package mx.imss.ctirss.catalogos.model;

import javax.persistence.*;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcStatus;


/**
 * The persistent class for the DLC_STATUS database table.
 * 
 */
@Entity
@Table(name="DLC_STATUS")
public class DlcStatus extends AbstractDlcStatus {
	private static final long serialVersionUID = 1L;

}