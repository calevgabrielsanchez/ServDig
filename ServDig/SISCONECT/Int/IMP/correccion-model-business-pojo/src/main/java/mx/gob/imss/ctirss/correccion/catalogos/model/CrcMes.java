package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrcMes;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name="CRC_MES")
@OnSearchLlavePrimaria (atributos={"cveMes"})
public class CrcMes extends AbstractCrcMes{

}
