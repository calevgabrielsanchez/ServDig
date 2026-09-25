package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the "PARAMETERS" database table.
 * 
 */
@Entity
@Table(name="\"PARAMETERS\"")
@NamedQuery(name="Parameter.findAll", query="SELECT p FROM Parameter p")
public class Parameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ParameterPK id;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String createuser;

	private String parametercategory;

	private String parameterdescription;

	private String parametername;

	private String parametersubcategory;

	private String parametervalue;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String updateuser;

	//bi-directional many-to-one association to Application
	@ManyToOne
	@JoinColumn(name="IDAPPLICATIONS")
	private Application application;

	public Parameter() {
	}

	public ParameterPK getId() {
		return this.id;
	}

	public void setId(ParameterPK id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getParametercategory() {
		return this.parametercategory;
	}

	public void setParametercategory(String parametercategory) {
		this.parametercategory = parametercategory;
	}

	public String getParameterdescription() {
		return this.parameterdescription;
	}

	public void setParameterdescription(String parameterdescription) {
		this.parameterdescription = parameterdescription;
	}

	public String getParametername() {
		return this.parametername;
	}

	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public String getParametersubcategory() {
		return this.parametersubcategory;
	}

	public void setParametersubcategory(String parametersubcategory) {
		this.parametersubcategory = parametersubcategory;
	}

	public String getParametervalue() {
		return this.parametervalue;
	}

	public void setParametervalue(String parametervalue) {
		this.parametervalue = parametervalue;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}