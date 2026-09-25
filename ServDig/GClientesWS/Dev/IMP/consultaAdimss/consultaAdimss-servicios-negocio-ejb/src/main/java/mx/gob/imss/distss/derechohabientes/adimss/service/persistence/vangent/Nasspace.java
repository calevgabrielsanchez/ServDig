package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the NASSPACES database table.
 * 
 */
@Entity
@Table(name="NASSPACES")
@NamedQuery(name="Nasspace.findAll", query="SELECT n FROM Nasspace n")
public class Nasspace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String location;

	private BigDecimal availablespace;

	private String filesystem;

	private BigDecimal totalspace;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String usagepercentage;

	private BigDecimal usedspace;

	public Nasspace() {
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getAvailablespace() {
		return this.availablespace;
	}

	public void setAvailablespace(BigDecimal availablespace) {
		this.availablespace = availablespace;
	}

	public String getFilesystem() {
		return this.filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

	public BigDecimal getTotalspace() {
		return this.totalspace;
	}

	public void setTotalspace(BigDecimal totalspace) {
		this.totalspace = totalspace;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUsagepercentage() {
		return this.usagepercentage;
	}

	public void setUsagepercentage(String usagepercentage) {
		this.usagepercentage = usagepercentage;
	}

	public BigDecimal getUsedspace() {
		return this.usedspace;
	}

	public void setUsedspace(BigDecimal usedspace) {
		this.usedspace = usedspace;
	}

}