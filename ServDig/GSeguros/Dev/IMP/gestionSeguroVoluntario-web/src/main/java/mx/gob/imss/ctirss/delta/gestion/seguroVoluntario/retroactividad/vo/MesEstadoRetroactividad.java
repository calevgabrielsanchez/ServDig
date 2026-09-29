package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.retroactividad.vo;

public class MesEstadoRetroactividad {
    private String nombreMes;
    private int numeroMes; // 0 = Enero, 1 = Febrero, etc.
    private int anio;
    private boolean activo; // true si debe ir sombreado/marcado

    public MesEstadoRetroactividad(String nombreMes, int numeroMes, int anio, boolean activo) {
        this.nombreMes = nombreMes;
        this.numeroMes = numeroMes;
        this.anio = anio;
        this.activo = activo;
    }

    public String getNombreMes() { return nombreMes; }
    public int getNumeroMes() { return numeroMes; }
    public int getAnio() { return anio; }
    public boolean isActivo() { return activo; }
}
