package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo;

public class DocumentosByte {

    protected String tipo;
    protected byte[] documento;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }
}