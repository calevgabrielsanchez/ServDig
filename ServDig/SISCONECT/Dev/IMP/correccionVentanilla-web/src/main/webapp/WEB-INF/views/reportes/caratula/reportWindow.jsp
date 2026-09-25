<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


			<form:form modelAttribute="modeloReporte" name="myReportForm"
						method="post" id="reportesCaratulaForm" action="reportes.do">
				
				<table bgcolor="#FFFFFF">
					<tr>
						<td align="center" >
							<img src="<%=request.getContextPath()%>/resources/images/logo_imssPeq.jpg">
						<td/>
					</tr>
					<tr>
						<td align="center">
						<div id="ProcesarMsg">
							<span style="font-family: sans-serif;font-size: 6 pt;">Su reporte puede tardar uno o varios 
							                                   minutos en ser	generado, por favor confirme el proceso.</span>
						</div>
						
						<div id="seleccionAnio" style="display:none">
							<form:select path="anioReporte" id="anioReporte">
								<form:options items="${modeloReporte.lsAniosFiscales}" itemValue="id" itemLabel="label"/>												
							</form:select>
						</div>
						
						<div id="CerrarMsg">
							<span style="font-family: sans-serif;font-size: 6 pt;">Su reporte se está generando al finalizar cierre esta ventana.</span>
						</div>
						<td/>
					</tr>
					<tr>
					
						<td align="center">
						<div id="ProcesarBtn">
							<input type="button" onclick="processReport()" class="boton" value="Continuar"/>
						</div>
						<div id="CerrarBtn">
							<input type="button" onclick="window.close()" class="boton" value="Cerrar Ventana"/>
						</div>
						</td>
					</tr>
					
				</table>
				
				<form:hidden path="tipoReporte"/>
				<form:hidden path="delegacion"/>
				<form:hidden path="subdelegacion"/>
				<form:hidden path="nombreDelegacion"/>
				<form:hidden path="nombreSubdelegacion"/>
				<form:hidden path="mensaje" id="mensaje"/>
				
			</form:form>							    
<script>
	
	function processReport(){
// 		if((document.forms[0].tipoReporte.value==2 
// 				|| document.forms[0].tipoReporte.value==3
// 				|| document.forms[0].tipoReporte.value==4) && document.forms[0].anioReporte.value=='0'){
// 			alert('Favor de seleccionar el periodo');
// 			return false;			
// 		}else{
		 document.getElementById("ProcesarBtn").style.display ="none";
		 document.getElementById("ProcesarMsg").style.display ="none";
		 document.getElementById("seleccionAnio").style.display ="none";
		
		 document.getElementById("CerrarBtn").style.display ="block";
		 document.getElementById("CerrarMsg").style.display ="block";
		 document.forms[0].submit();
		 return true;
// 		}
		
	}
	
	function onLoadPage(){
		document.getElementById("ProcesarBtn").style.display ="block";
		document.getElementById("ProcesarMsg").style.display ="block";
		
		document.getElementById("CerrarBtn").style.display ="none";
		document.getElementById("CerrarMsg").style.display ="none";
		if(document.forms[0].tipoReporte.value!=2 && document.forms[0].tipoReporte.value!=3 && document.forms[0].tipoReporte.value!=4){		
			document.getElementById("seleccionAnio").style.display ="none";
		}
		
	}

	onLoadPage();
</script>