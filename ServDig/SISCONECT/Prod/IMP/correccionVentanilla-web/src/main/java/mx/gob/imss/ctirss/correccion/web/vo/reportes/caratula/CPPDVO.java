package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CPPDVO {

	  private String nombre;
	  private String idTipo;
	  private String descripcion;
	  private String descOrigen;
	  private String criterioSeleccion;
	  private String folio;
	  private String afil15;
	  private String cvePatron;
	  private String ope;
	  private String nop;
	  private String aop;
	  private String sp;
	  private String oi;
	  private String pr;
	  private String cr;
	  private String pai;
	  private String c;
	  private String periodoDel;
	  private String periodoAl;
	  private String porcentajeAvance;
	  private String porcentajeRegularizado;
	  private String noConvenio;
	  private String noParcialidades;
	  private String copconvsp;
	  private String tcopsp;
	  private String tcopact;
	  private String tcoprec;
	  private String tcoptotal;
	  private String tcopMultas;
	  private String trabRevisados;
	  private String trabOmisos;
	  private String trabSubddeclarados;
	  private String tcopspPagada;
	  private String tcopPendientePago;
	  private String rcvconvsp;
	  private String trcvsp;
	  private String trcvact;
	  private String trcvrec;
	  private String trcvTotal;
	  private String observaciones;
	
	
	public CPPDVO(){}
	
	public CPPDVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				
		int i=0;
		   nombre= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   idTipo= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   descripcion= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   descOrigen= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   criterioSeleccion= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   folio= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   afil15= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   cvePatron= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   ope= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   nop= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   aop= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   sp= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   oi= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   pr= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   cr= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   pai= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   c= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   periodoDel= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   periodoAl= (obj[i]!=null ? formater.format((Date)obj[i]):" "); i++;
		   porcentajeAvance= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   porcentajeRegularizado= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   noConvenio= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   noParcialidades= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   copconvsp= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcopsp= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcopact= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcoprec= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcoptotal= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcopMultas= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trabRevisados= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trabOmisos= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trabSubddeclarados= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcopspPagada= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   tcopPendientePago= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   rcvconvsp= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trcvsp= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trcvact= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trcvrec= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   trcvTotal= (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
		   observaciones= (obj[i]!=null ? String.valueOf(obj[i]):" "); 

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescOrigen() {
		return descOrigen;
	}

	public void setDescOrigen(String descOrigen) {
		this.descOrigen = descOrigen;
	}

	public String getCriterioSeleccion() {
		return criterioSeleccion;
	}

	public void setCriterioSeleccion(String criterioSeleccion) {
		this.criterioSeleccion = criterioSeleccion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getAfil15() {
		return afil15;
	}

	public void setAfil15(String afil15) {
		this.afil15 = afil15;
	}

	public String getCvePatron() {
		return cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	public String getOpe() {
		return ope;
	}

	public void setOpe(String ope) {
		this.ope = ope;
	}

	public String getNop() {
		return nop;
	}

	public void setNop(String nop) {
		this.nop = nop;
	}

	public String getAop() {
		return aop;
	}

	public void setAop(String aop) {
		this.aop = aop;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getOi() {
		return oi;
	}

	public void setOi(String oi) {
		this.oi = oi;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getPeriodoDel() {
		return periodoDel;
	}

	public void setPeriodoDel(String periodoDel) {
		this.periodoDel = periodoDel;
	}

	public String getPeriodoAl() {
		return periodoAl;
	}

	public void setPeriodoAl(String periodoAl) {
		this.periodoAl = periodoAl;
	}

	public String getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(String porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public String getPorcentajeRegularizado() {
		return porcentajeRegularizado;
	}

	public void setPorcentajeRegularizado(String porcentajeRegularizado) {
		this.porcentajeRegularizado = porcentajeRegularizado;
	}

	public String getNoConvenio() {
		return noConvenio;
	}

	public void setNoConvenio(String noConvenio) {
		this.noConvenio = noConvenio;
	}

	public String getNoParcialidades() {
		return noParcialidades;
	}

	public void setNoParcialidades(String noParcialidades) {
		this.noParcialidades = noParcialidades;
	}

	public String getCopconvsp() {
		return copconvsp;
	}

	public void setCopconvsp(String copconvsp) {
		this.copconvsp = copconvsp;
	}

	public String getTcopsp() {
		return tcopsp;
	}

	public void setTcopsp(String tcopsp) {
		this.tcopsp = tcopsp;
	}

	public String getTcopact() {
		return tcopact;
	}

	public void setTcopact(String tcopact) {
		this.tcopact = tcopact;
	}

	public String getTcoprec() {
		return tcoprec;
	}

	public void setTcoprec(String tcoprec) {
		this.tcoprec = tcoprec;
	}

	public String getTcoptotal() {
		return tcoptotal;
	}

	public void setTcoptotal(String tcoptotal) {
		this.tcoptotal = tcoptotal;
	}

	public String getTcopMultas() {
		return tcopMultas;
	}

	public void setTcopMultas(String tcopMultas) {
		this.tcopMultas = tcopMultas;
	}

	public String getTrabRevisados() {
		return trabRevisados;
	}

	public void setTrabRevisados(String trabRevisados) {
		this.trabRevisados = trabRevisados;
	}

	public String getTrabOmisos() {
		return trabOmisos;
	}

	public void setTrabOmisos(String trabOmisos) {
		this.trabOmisos = trabOmisos;
	}

	public String getTrabSubddeclarados() {
		return trabSubddeclarados;
	}

	public void setTrabSubddeclarados(String trabSubddeclarados) {
		this.trabSubddeclarados = trabSubddeclarados;
	}

	public String getTcopspPagada() {
		return tcopspPagada;
	}

	public void setTcopspPagada(String tcopspPagada) {
		this.tcopspPagada = tcopspPagada;
	}

	public String getTcopPendientePago() {
		return tcopPendientePago;
	}

	public void setTcopPendientePago(String tcopPendientePago) {
		this.tcopPendientePago = tcopPendientePago;
	}

	public String getRcvconvsp() {
		return rcvconvsp;
	}

	public void setRcvconvsp(String rcvconvsp) {
		this.rcvconvsp = rcvconvsp;
	}

	public String getTrcvsp() {
		return trcvsp;
	}

	public void setTrcvsp(String trcvsp) {
		this.trcvsp = trcvsp;
	}

	public String getTrcvact() {
		return trcvact;
	}

	public void setTrcvact(String trcvact) {
		this.trcvact = trcvact;
	}

	public String getTrcvrec() {
		return trcvrec;
	}

	public void setTrcvrec(String trcvrec) {
		this.trcvrec = trcvrec;
	}

	public String getTrcvTotal() {
		return trcvTotal;
	}

	public void setTrcvTotal(String trcvTotal) {
		this.trcvTotal = trcvTotal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}



	
}
