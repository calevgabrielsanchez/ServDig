<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="../general/taglibs.jsp"%>

<style>
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>

<%-- <script type="text/javascript" src="${staticResourcesPath}/js/clasificador/clasificacion.js"></script> --%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/clasificador.js" htmlEscape="true" />"></script>
    
<script>
	String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g,'') }
	var errPrima;
	var errOficio;
	var strFechaSurteEfecto;
	
	function asignaValores(){
		
		// Se comenta ya que para desechar tramite la fraccion puede ser la misma
/* 		fraccionActual=document.getElementById("cveIdFraccionAct").value;
		fraccionPropuesta=document.getElementById("cveFraccionId").innerHTML;
		console.log("Asignando fraccion, fraccionActual:: " + fraccionActual + ", fraccionPropuesta:: " + fraccionPropuesta);

		if(fraccionActual==fraccionPropuesta){
			alert("La Clasificación seleccionada es la misma que el Patrón ha declarado"
					+ "\nPor favor, seleccione una distinta");
		}else 
 */	
 
		fraccionPropuesta = document.getElementById("cveFraccionId").innerHTML;
		console.log("Asignando fraccion, fraccionPropuesta:: " + fraccionPropuesta);

		if(validaDatos()){
			errPrima=document.getElementById('errPrima');
			errOficio=document.getElementById('errOficio');
			strFechaSurteEfecto = document.getElementById('strFechaSurteEfecto');
			
			errPrima.style.display='none';
			errOficio.style.display='none';
			strFechaSurteEfecto.style.display='none';
			
			document.forms['desecharSolicitudForm'].clase.value=document.getElementById("cveClaseId").innerHTML;
			document.forms['desecharSolicitudForm'].cveIdFraccion.value = document.getElementById("cveFraccionId").innerHTML;
			document.forms['desecharSolicitudForm'].cveIdDivision.value = document.getElementById("cveDivisionId").innerHTML;
			document.forms['desecharSolicitudForm'].cveIdGrupo.value = document.getElementById("cveGrupoId").innerHTML;
			document.forms['desecharSolicitudForm'].primaSRTPro.value = document.getElementById('prima').value;
			document.forms['desecharSolicitudForm'].fechaSurteEfecto.value = document.getElementById('strFechaSurteEfecto').value;
			
			$.blockUI();
		    document.getElementById('desecharSolicitudForm').submit();
		}
	}

	function validaDatos(){
		var bool=true;
		prima=document.getElementById('prima');
		oficio = document.getElementById('oficio');
		errPrima = document.getElementById('errPrima');
		errOficio = document.getElementById('errOficio');
		strFechaSurteEfecto = document.getElementById('strFechaSurteEfecto'); 

		errPrima.style.display='none';
		errOficio.style.display='none';
		errstrFechaSurteEfecto.style.display='none';

		prima2=document.getElementById('prima').value;
		oficio2=document.getElementById('oficio').value;
		strFechaSurteEfecto2=document.getElementById('strFechaSurteEfecto').value;
		
		if(prima.value==null || prima2.trim()==""){
			errPrima.value="Campo Obligatorio";
			errPrima.style.display='block';
			bool=false;
		}else if(strFechaSurteEfecto.value==null || strFechaSurteEfecto2.trim()==""){
			errstrFechaSurteEfecto.value="Campo Obligatorio";
			errstrFechaSurteEfecto.style.display='block';
			bool=false;
		}else if(oficio.value==null || oficio2.trim()==""){
			errOficio.value="Campo Obligatorio";
			errOficio.style.display='block';
			bool=false;
		}else{
			
			console.log("::Se valida contenido de los datos");
						
			/* Valida archivo adjunto */
 			var inputFile  = $("input[name='oficio']");
			var file = inputFile.val();
		    var extensionFile = file.substring(file.lastIndexOf("."));
		    var fileSize;
		   
		    try {
		        fileSize = inputFile[0].files[0].size;
		        console.log("::: El archivo por adjuntar mide: " + fileSize);
		    } catch (e) {
		        fileSize = 0;
		    }

		    if (extensionFile !== ".pdf" && extensionFile !== ".PDF") {
		    	bool=false;
		    	alert("Formato de archivo invalido");
		    } else if (fileSize > 4000000) {
		    	bool=false;
		    	alert("El documento supera el tamaño permitido de 4MB");
		    }else if(parseFloat(prima2) < 0.5 || parseFloat(prima2) > 15.0){ //Valida valor de la prima
				bool=false;
				alert("El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0 ");
			}		    		    
			
		}
		return bool;		
	}
	
	function verDetalle(){
		var $formulario = $("#detalleSolicitudForm");
		$formulario.submit();
		$.blockUI();
	}	
	
	$(document).ready(function(){
		$( "#strFechaSurteEfecto" ).datepicker();
		$( "#strFechaSurteEfecto" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	});
		  

	$(function() {

		$("#dialog-form").dialog({
			autoOpen : false,
			height : 150,
			width : 350,
			modal : true,
			buttons : {
				"Si" : function() {
					asignaValores();
					$(this).dialog("close");
				},
				"No" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#desecharAceptar").button().click(function() {
			$("#dialog-form").dialog("open");
		});

		$("#regresarBtn").button().click(function() {
			verDetalle();
		});

	});
</script>

<!-- JS de la pagina -->

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/general.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>"/>
<c:set var="cveIdDelegacion" 		value="${cveIdDelegacion}"/>
<c:set var="cveIdSubdelegacion" 	value="${cveIdSubdelegacion}"/>

<form id="clasificacionForm">
	<input type="hidden" id="idDivision" value="vacio">
	<input type="hidden" id="idGrupo" value="vacio">
	<input type="hidden" id="idFraccion" value="vacio">
	<input type="hidden" id="clase" value="vacio">
	<input type="hidden" id="descActividadEconomicaDetectada" value="vacio">
</form>

<form id="formClasificacionSolicitud"><!-- Hiddens para el control y manejo de la clasificacion -->
	<input type="hidden" id="idDivisionb" name="idDivisionb" value="" />
	<input type="hidden" id="idGrupob" name="idGrupob" value="" />
	<input type="hidden" id="idFraccionb" name="idFraccionb" value="" />
	<input type="hidden" id="claseb" name="claseb" value="" />
	<input type="hidden" id="cveIdClasificacionb" name="cveIdClasificacionb" value="" />
</form>

<div align="center">
	<table style="width: 100%">	
		<thead>
			<tr valign="top" class="par">
				<td align="center" style="padding-bottom: .5em; padding-top: .5em;">
						<div class="separadorseccion">Desechar solicitud</div>
				</td>
			</tr>
		</thead>
		
		<tr><td colspan="1"><center><div id="msgAuxiliar" style="display:'none'; color:#8B0000;"></div></center></td></tr>
		
		<tr>
			<td align="center" style="padding-bottom: 10px;">
				<div id="wrapperIntsAnterior" class="ui-widget" style="width: 700px !important;">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .5em;" align="center">
					<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<strong>Seleccione la clasificaci&oacute;n inmediata anterior conforme al cat&aacute;logo para la clasificaci&oacute;n de las empresas en el Seguro de Riesgo de Trabajo:</strong> 
					<strong><a href="javascript:fnOpenClasificador();"> Aqu&iacute;</a></strong>
					<br><br>
					<strong>La clasificaci&oacute;n, prima y fecha surte efecto debe coincidir con la clasificaci&oacute;n inmediata anterior del patr&oacute;n</strong>
					</p>					
				</div>
				</div>
			</td>
		</tr>		
	</table>
	<div id="dgEliminarProductos"></div>
	<form:form id="desecharSolicitudForm" name="desecharSolicitudForm" 
        action="${contextpath}/desechar/${cveIdAnalisis}/confirmar" method="post" enctype="multipart/form-data">

		<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis" value="${cveIdAnalisis}"/>
		<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${cveIdSolicitud}"/>
		<input type="hidden" id="idTipoPersona" name="idTipoPersona" value="${tipoPersona}"/>
		<input type="hidden" id="regPatron" name="regPatron" value="${regPatronal}"/>
		<input type="hidden" id="cveIdDivision" name="cveIdDivision" value=""/>
		<input type="hidden" id="cveIdGrupo" name="cveIdGrupo" value=""/>
		<input type="hidden" id="cveIdFraccion" name="cveIdFraccion" value=""/>
		<input type="hidden" id="clase" name="clase" value=""/>
		<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${cveIdDelegacion}"/>
		<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${cveIdSubdelegacion}"/>
		<input type="hidden" id="idTipoTramite" name="idTipoTramite" value="${idTipoTramite}"/>
		<!-- primaSRTPro, fechaSurteEfecto se llena  al hacer submit -->
		<input type="hidden" id="primaSRTPro" name="primaSRTPro"/>
		<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto"/>
		<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${cveIdFraccionAct}"/>
		<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${primaSRTAct}"/>
		<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${primaSRTAnt}"/>
		<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${cveIdFraccionAnt}"/>
		
		<table style="width: 85%" cellpadding="0px;">
			<tr class="fielsetgris2">
				<td style="display: none;"><span>ids</span></td>
				<td valign="top"><span class="etiqueta"> Divisi&oacute;n:</span> <span id="idDivisionError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Grupo:</span><span id="idGrupoError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Fracci&oacute;n:</span><span id="idFraccionError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Clase:</span></td>
				<td valign="top"><span class="etiqueta"> Prima SRT:</span></td>	
			</tr>
			<tr>
				<td style="display: none;">
				<span class="dato" id="cveFraccionId">${clasificacion.fraccion.id}</span>
				<span class="dato" id="cveDivisionId">${clasificacion.fraccion.grupo.division.id}</span>
				<span class="dato" id="cveGrupoId">${clasificacion.fraccion.grupo.id}
				</span>
				<span class="dato" id="cveClaseId">${clasificacion.fraccion.clase.clave}</span>
				</td>
				<td valign="top"><span class="dato" id="cvedivisionDes">
					${clasificacion.fraccion.grupo.division.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cvegrupoDes">
					${clasificacion.fraccion.grupo.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cvefraccionDes">
					${clasificacion.fraccion.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cveclaseDes">
					${clasificacion.fraccion.clase.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cveprimaDes">
					${clasificacion.fraccion.primaSRT}
				</span></td>
			</tr>
			<tr><td colspan="5">&nbsp;</td></tr>		
		</table>		

		<table>
			<tr>
				<td>
					<label id="errPrima" class="wide" style="color:red; display: none;">Dato obligatorio</label>
					<label class="wide">Prima:&nbsp;&nbsp;</label>
				</td>
				<td align="left">								
					<input type="text" maxlength="8" id="prima" name="prima" placeholder="Captura la prima"
						style="border: 1px !important; width: 150px !important; height: 20px !important; 
						background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important;
						border-left-style: solid !important; border-right-style: solid !important; 
						border-bottom-style: solid !important; border-top-style: solid !important; 
						border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; 
						border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; 
						text-transform: uppercase !important;" />
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<td>
					<label id="errstrFechaSurteEfecto" class="wide" style="color:red; display: none;">Dato obligatorio</label>
					<label class="mwide">Fecha surte efecto:&nbsp;&nbsp;</label>
				</td>				
				<td>
					<input name="strFechaSurteEfecto" id="strFechaSurteEfecto" type="text" maxlength="10" title="dd/MM/aaaa" 
						style="border: 1px !important; width: 100px !important; height: 20px !important; 
						background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; 
						border-left-style: solid !important; border-right-style: solid !important; 
						border-bottom-style: solid !important; border-top-style: solid !important; 
						border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; 
						border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; 
						text-transform: uppercase !important;" />
				</td>
			</tr>							
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<td>
					<label id="errOficio" class="wide" style="color:red; display: none;">Dato obligatorio</label>
					<label class="mwide">Oficio:&nbsp;&nbsp;</label>
				</td>				
				<td>
					<input type="file" id="oficio" name="oficio" size="4000000" width="150px"
						style="border: 1px !important; width: 240px !important; height: 20px !important; 
						background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; 
						border-left-style: solid !important; border-right-style: solid !important; 
						border-bottom-style: solid !important; border-top-style: solid !important; 
						border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; 
						border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; 
						text-transform: uppercase !important;" />					
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="right">
					<input type="button" id="regresarBtn" name ="regresarBtn" class="mboton" style="width:120px;" 
						value="Regresar"/>
					<input type="button" id="desecharAceptar" name ="desecharAceptar" class="mboton" style="width:120px;" 
						value="Aceptar"/>
						
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
	</form:form>
</div>

 
<div id="dialog-form" title="Desechar tr&aacute;mite">
  <p class="validateTips">¿Est&aacute; seguro que desea desechar este tr&aacute;mite?</p>
</div>

<div >
	<form id="detalleSolicitudForm" action="${contextpath}/solicitud/verDetalle" method="POST">
		<input id="cveIdSolicitud" name="cveIdSolicitud" type="hidden" value="${cveIdSolicitud}"/>
		<input id="regPatronal" name="regPatronal" type="hidden" value="${regPatronal}"/>
		<input id="tipoPersona" name="tipoPersona" type="hidden" value="${tipoPersona}"/>
	</form>
</div>

<script type="text/javascript">
	$(function() {		
		$("input[name='prima']").numeric({
			maxDigits: 8,
			maxDecimalPlaces: 5,
			maxPreDecimalPlaces: 3					
		});
	});
</script>

<c:if var="popUp" test="${popUp==1}">
	<script>
		$.blockUI();
		fnOpenClasificador();
		$.unblockUI();
	</script>
</c:if>
