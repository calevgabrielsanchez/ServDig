package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa los parentescos definidos para la Incorporación al Seguro de
 * Saludo para la Familia (ISSF)
 */
public enum ParentescoIssfEnum implements Serializable {

    TITULAR(0, "TITULAR",1),
    CONYUGE(1, "CÓNYUGE",3),
    CONCUBINARIO(2, "CONCUBINA(RIO)",3),
    HIJO(3, "HIJO(A)",3),
    PADRE(4, "PADRE",3),
    MADRE(5, "MADRE",3),
    ABUELO(6, "ABUELO(A)",5),
    NIETO(7, "NIETO(A)",5),
    SOBRINO(8, "HIJO(A) DE MI HERMANO(A)",5),
    TIO(9, "HERMANO(A) DE MI PADRE/MADRE",5),
    HERMANO(10, "HERMANO(A) DEL TITULAR",5),
    PRIMO(11, "PRIMO(A) DEL TITULAR",5);

    private int id;
    private String descripcion;
    private int tipoDeTrabajador;

    private final static Map<String, ParentescoIssfEnum> hashNames = new HashMap<String, ParentescoIssfEnum>();
    private final static Map<Integer, ParentescoIssfEnum> hashCodes = new HashMap<Integer, ParentescoIssfEnum>();

    static {
        for (ParentescoIssfEnum parentesco : ParentescoIssfEnum.values()) {
            hashNames.put(parentesco.name(), parentesco);
            hashCodes.put(parentesco.getId(), parentesco);
        }
    }

    ParentescoIssfEnum(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    ParentescoIssfEnum(int id, String descripcion,int tipoTrabajador) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoDeTrabajador = tipoTrabajador;
    }

    public int getId() {
        return this.id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getTipoDeTrabajador() {
        return tipoDeTrabajador;
    }
    public static ParentescoIssfEnum obtenerEnumByName(String name) {
        return hashNames.get(name);
    }

    public static ParentescoIssfEnum obternerEnumById(Integer id) {
        return hashCodes.get(id);
    }

}
