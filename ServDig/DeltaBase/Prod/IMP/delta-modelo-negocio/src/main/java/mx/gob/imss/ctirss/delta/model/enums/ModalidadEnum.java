package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum ModalidadEnum implements Serializable{

    CERO(26,"00"), DOS(27,"02"), DIEZ(1,"10"), ONCE(2,"11"), DOCE(28,"12"), TRECE(3,"13"), CATORCE(4,"14"), QUINCE(5,"15"), 
    DIECISEIS(6,"16"),DIECISIETE(7,"17"), DIECIOCHO(29,"18"), DIECINUEVE(30,"19"), VEINTE(8,"20"), VEINTIUNO(9,"21"), VEINTIDOS(31,"22"), 
    VEINTITRES(32,"23"), VEINTICUATRO(33,"24"), VEINTICINCO(34,"25"), VEINTISEIS(35,"26"), VEINTISIETE(10,"27"), VEINTIOCHO(36,"28"),
    VEINTINUEVE(11,"29"), TREINTA(12,"30"),  TREINTAYUNO(13,"31"), TREINTAYDOS(14,"32"),TREINTAYTRES(15,"33"),TREINTAYCUATRO(16,"34"),
    TREINTAYCINCO(17,"35"),TREINTAYSEIS(18,"36"),TREINTAYOCHO(19,"38"),CUARENTA(20,"40"), CUARENTAYUNO(37,"41"), CUARENTAYDOS(21,"42"), 
    CUARENTAYTRES(22,"43"), CUARENTAYCUATRO(23,"44"), CUERANTEYCINCO(24,"45"), CUARETAYSEIS(25,"46"), SESENTAYUNO(38,"61"), 
    SETENTA(39,"70"), SETENTAYUNO(40,"71"), OCHENTAYSIETE(41,"87");
	
	private long id;
	private String numModalidad;
	
	ModalidadEnum(long id, String clave){
		this.id=id;
		this.numModalidad=clave;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getNumModalidad(){
		return this.numModalidad;
	}
	
    public static ModalidadEnum fromId(long id) {
        ModalidadEnum[] modalidades = values();
        for(ModalidadEnum modalidad:modalidades) {
            if (modalidad.getId() == id) {
                return modalidad;
            }
        }
        return null;
    }
}
