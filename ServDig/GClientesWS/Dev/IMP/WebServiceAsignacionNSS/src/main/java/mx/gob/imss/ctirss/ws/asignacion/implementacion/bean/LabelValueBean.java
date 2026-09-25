/*
 * Created on 5/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.bean;

/**
 * @author CAPACITACION
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LabelValueBean {

	/** Holds value of property label. */
	private String label;
	
	/** Holds value of property value. */
	private String value;
	
	/** Creates a new instance of LabelValueBean */
	
	private String descCorta;
	public LabelValueBean ()
		{label="";value="";}
	
	/** */
	public LabelValueBean (String label, String value)
		{this.label = label; this.value=value;}

	/** Getter for property label.
	 * @return Value of property label.
	 *
	 */
	public String getLabel ()
	{
		return this.label;
	}
	
	/** Setter for property label.
	 * @param label New value of property label.
	 *
	 */
	public void setLabel (String label)
	{
		this.label = label;
	}
	
	/** Getter for property value.
	 * @return Value of property value.
	 *
	 */
	public String getValue ()
	{
		return this.value;
	}
	
	/** Setter for property value.
	 * @param value New value of property value.
	 *
	 */
	public void setValue (String value)
	{
		this.value = value;
	}	
	
    /**
     * @return Returns the descCorta.
     */
    public String getDescCorta() {
        return descCorta;
    }
    /**
     * @param descCorta The descCorta to set.
     */
    public void setDescCorta(String descCorta) {
        this.descCorta = descCorta;
    }
}