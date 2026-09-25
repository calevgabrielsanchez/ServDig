package mx.imss.ctirss.catalogos.model;

import javax.persistence.*;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcPregunta;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcStatus;


/**
 * The persistent class for the DLC_STATUS database table.
 * 
 */
@Entity
@Table(name="DLC_PREGUNTA")
public class DlcPregunta extends AbstractDlcPregunta{
	private static final long serialVersionUID = 1L;

}