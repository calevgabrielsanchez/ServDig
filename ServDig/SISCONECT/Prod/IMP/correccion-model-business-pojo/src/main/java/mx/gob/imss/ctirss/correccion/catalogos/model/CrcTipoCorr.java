package mx.gob.imss.ctirss.correccion.catalogos.model;


import javax.persistence.*;
import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcTipoCorr;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CRC_TIPOCORR database table.
 * 
 */
@Entity
@Table(name="CRC_TIPOCORR")
@OnSearchLlavePrimaria(atributos="cveTipocorr")
@ComponentComboCampoDescripcion(atributo="txDescripcion")
public class CrcTipoCorr extends AbstractCrcTipoCorr {
	private static final long serialVersionUID = 1L;

	
}