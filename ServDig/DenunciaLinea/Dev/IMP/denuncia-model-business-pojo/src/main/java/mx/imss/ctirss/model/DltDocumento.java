package mx.imss.ctirss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.imss.ctirss.base.model.AbstractDltDocumento;

@Entity
@Table(name="DLT_DOCUMENTO")
public class DltDocumento extends AbstractDltDocumento implements Serializable{
	private static final long serialVersionUID = 1L;
}
