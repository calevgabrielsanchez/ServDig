package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentosByteVO", propOrder = { "id", "listDocumentos" })
public class DocumentosByteVO {

    @XmlElement(name="id")
    private String id;

    @XmlElement(nillable = true, name="listDocumentos")
    private List<DocumentosByte> listDocumentos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DocumentosByte> getListDocumentos() {
        if (listDocumentos == null) {
            listDocumentos = new ArrayList<DocumentosByte>();
        }
        return listDocumentos;
    }
}
