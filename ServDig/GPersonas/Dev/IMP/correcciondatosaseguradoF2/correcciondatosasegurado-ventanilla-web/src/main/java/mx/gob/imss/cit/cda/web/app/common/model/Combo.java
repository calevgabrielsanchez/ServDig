package mx.gob.imss.cit.cda.web.app.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Combo extends BaseModel {

    private static final long serialVersionUID = 1068188753855472613L;
    private String key;
    private String value;

    public Combo() {
        super();
    }

    public Combo(String value, String key) {
        super();
        this.value = value;
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

}
