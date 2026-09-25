<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


			<form:form name="myDescargaPromocionCritForm" method="post" id="descargaPromocionCritForm" action="descargaXls.do">
				
				<table bgcolor="#FFFFFF">
					<tr>
						<td align="center" >
							<img src="<%=request.getContextPath()%>/resources/images/logo_imssPeq.jpg">
						<td/>
					</tr>
					<tr>
						<td align="center">
						<div id="ProcesarMsg">
							<span style="font-family: sans-serif;font-size: 6 pt;">Su documento puede tardar uno o varios 
							                                   minutos en ser	generado, por favor confirme el proceso.</span>
						</div>
						<div id="CerrarMsg">
							<span style="font-family: sans-serif;font-size: 6 pt;">Su documento se está generando al finalizar cierre esta ventana.</span>
						</div>
						<td/>
					</tr>
					<tr>
					
						<td align="center">
						<div id="ProcesarBtn">
							<input type="submit" onclick="processReport()" class="boton" value="Continuar"/>
						</div>
						<div id="CerrarBtn">
							<input type="button" onclick="window.close()" class="boton" value="Cerrar Ventana"/>
						</div>
						</td>
					</tr>
					
				</table>
				
			
				
			</form:form>							    
<script>
	
	function processReport(){
				
		document.getElementById("ProcesarBtn").style.display ="none";
		document.getElementById("ProcesarMsg").style.display ="none";
		
		document.getElementById("CerrarBtn").style.display ="block";
		document.getElementById("CerrarMsg").style.display ="block";
		
	}
	
	function onLoadPage(){
		document.getElementById("ProcesarBtn").style.display ="block";
		document.getElementById("ProcesarMsg").style.display ="block";
		
		document.getElementById("CerrarBtn").style.display ="none";
		document.getElementById("CerrarMsg").style.display ="none";
		
	}

	onLoadPage();
</script>