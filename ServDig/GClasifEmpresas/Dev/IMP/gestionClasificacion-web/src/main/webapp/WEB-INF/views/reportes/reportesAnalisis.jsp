<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<!--Empieza contenido-->
<script>

/*Se ejecuta hasta que la pagina se carga complementamente*/
$(window).load(function(){
	fnInitCombosDelegacion();

	$('form#reportesAnalisisForm select#modulo').trigger('change');
	if (${grupoTramite == '2'}) {
	    fnSetComboTipoMovimiento($('select#tipoMovimiento'));
		$('select#tipoMovimiento').get(0).options.value = '${filtrosReportes.tipoMovimiento}';
	}

	$('select#tipoPersona').get(0).options.value = '${filtrosReportes.tipoPersona}';
	$('select#clasePropuesta').get(0).options.value = '${filtrosReportes.clasePropuesta}';
	$('select#estatus').get(0).options.value = '${filtrosReportes.estatus}';

	if('${filtrosReportes.esConsulta}' == 'true'){
		if('${filtrosReportes.delegacion}' != ''){
			$('select#delegacion').get(0).options.value = '${filtrosReportes.delegacion}';
		}else{
			$('select#delegacion').get(0).options.value = '';
		}
		
		if('${filtrosReportes.subDelegacion}' != ''){
			$('select#subDelegacion').get(0).options.value = '${filtrosReportes.subDelegacion}';
		}else{
			$('select#subDelegacion').get(0).options.value = '';
		}
	}

	if ('${filtrosReportes.tipoRegistro}' == '0'){
		$('#tipoRegistroArp').attr('checked', 'checked');
	}else if('${filtrosReportes.tipoRegistro}' == '2'){
		$('#tipoRegistroPsp').attr('checked', 'checked');
	}else if('${filtrosReportes.tipoRegistro}' == '1'){
		$('#tipoRegistroRpc').attr('checked', 'checked');
	}else{
		$('#tipoRegistroTodo').attr('checked', 'checked');
	}

	var esInscripcion = '${grupoTramite}';
	if(esInscripcion == '1'){
		document.getElementById("cveIdGrupoAnalisisCe").value = <%=GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave()%>;
	}

	$.ajaxSetup({async:true});
});
$(document).ready(function(){
	$( "#strPeriodoInicio" ).datepicker();
	$( "#strPeriodoInicio" ).datepicker( "option", "dateFormat", 'dd/mm/yy' ).val();
	
	$( "#strPeriodoFin" ).datepicker();
	$( "#strPeriodoFin" ).datepicker( "option", "dateFormat", 'dd/mm/yy' ).val();

	$("#strPeriodoInicio").val('${filtrosReportes.strPeriodoInicio}');
	$("#strPeriodoFin").val('${filtrosReportes.strPeriodoFin}');

	document.getElementById("cenefa").innerHTML = "Inicio» Reportes";

	$.ajaxSetup({async:false});
});

function fnClearFormErrors(){
	 $('span.error').each(function(i,v) {
	        var text = '';
	        $(this).text(text);
	    });
	    return true;
}

function fnInitCombosDelegacion(){
	
	/*inicializacion de los combos de delegacion y subdelegacion*/
	var rol = $("#rol").val();	

	if(rol == <%=CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo()%> ||  
			rol == <%=CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.NORMATIVO_DEL.getCodigo()%> 
	){
		/* Este tipo de rol son DELEGACIONALES, por lo tanto se debe de cargar el combo de las subdelegaciones
		 * 	de la delegacion que el usuario tiene asociado.
		 */
		fnSetComboDelegacion($('select#delegacion'));		
		
	}else if( rol == <%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo()%> ){
		/*
		 * Este rol es de tipo NORMATIVO por lo tanto ve todo
		 */
		 fnSetComboNormativo();

	 	 /*Inicializamos los combos de delegacion y subdelegacion*/
		 fnSetComboDelegacion($('select#delegacion'));
		 /**/
		 fnSetComboSubdelegacion($('select#subDelegacion'));
		
	}else{
		 /*
		 * Si no es ninguno de los dos anteriores entonces es usuario Subdelegacional	
		 */		 
		 /*Inicializamos los combos de delegacion y subdelegacion*/
		 fnSetComboDelegacion($('select#delegacion'));
		 /**/
		 fnSetComboSubdelegacion($('select#subDelegacion'));
		 $('#subDelegacion').attr('disabled','disabled'); 
		 
	}
			
}

var sIdSelectDelegacion ="#delegacionHolder";
var sIdSelectSubDelegacion ="#subdelegacionHolder";
var sIdDelegacionUsuario ="#delegacionUser";
var sIdSubdelegacionUsuario ="#subdelegacionUser";

/**
 * Funcion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol DELEGACIONAL, los cuales solo pueden seleccionar
 * la subdelegacion de acuerdo a la delegacion asignada
**/
function fnSetComboDelegacion( objSelect ){

	if('${filtrosReportes.delegacion}' != ''){
		var idDelegacion = '${filtrosReportes.delegacion}';
	}
	else{
		var idDelegacion = $(sIdDelegacionUsuario).val();
	}
    var options = objSelect.get(0).options;
 	for(index =0 ; index < options.length ; index++ ){
 		var option = options[index];
 		if(option.value == idDelegacion){
    		option.selected = 'selected';		
    	}
 	}
 	
	/* Mostramos el select de subdelegaciones*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");
	
	objSelect.change();
	fnSetComboSubdelegacion($('select#subDelegacion'))
}

/**
 * Función que elimina la opción de inscripción inicial del combo de tipos
 * de movimiento.
**/
function fnSetComboTipoMovimiento(objSelect){
	if (objSelect != null) {
		var idTipoMovimiento = ${tipoMovimientoGrupoTramite};
		var options = objSelect.get(0).options;
	    for(index =0 ; index < options.length ; index++ ){
     		var option = options[index];
     		if(option.value == idTipoMovimiento){
     			option.parentNode.removeChild(option);
        	}
     	}
	}
}

/**
 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol NORMATIVO, los cuales pueden ver tanto delegaciones como subdelegaciones
**/
function fnSetComboNormativo(){
	/*Mostramos los dos combos*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");
	
	$(sIdSelectDelegacion).removeClass("hiddenElement");
	$(sIdSelectDelegacion).addClass("showElement");		
}

/**
 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol SUBDELEGACIONAL, los cuales no pueden seleccionar
 * delegacion o subdelegacion.
**/
function fnSetComboSubdelegacion( objSelect ){
	if('${filtrosReportes.subDelegacion}' != ''){
		var idSubDelegacion = '${filtrosReportes.subDelegacion}';
	}
	else{
		var idSubDelegacion = $(sIdSubdelegacionUsuario).val();
	}
	
    var options = objSelect.get(0).options;
 	for(index =0 ; index < options.length ; index++ ){
 		var option = options[index];
 		if(option.value == idSubDelegacion){
    		option.selected = 'selected';
    	}
 	}
 	
	/* Mostramos el select de subdelegaciones*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");	
}

</script>

<c:set var="contextpath" value="<%=request.getContextPath() %>" />
<c:set var="grupoTramite" value="<%=session.getAttribute(\"grupoTramite\")%>" />
<c:set var="tipoMovimientoGrupoTramite" value="<%=session.getAttribute(\"tipoMovimientoGrupoTramite\")%>" />
<c:set var="msjException" value="${msjException}"/>

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol" value="${usuario.perfilUsuario}"/>
<input type="hidden" id="rol" name="rol" value="${rol.idPerfilUsuario}"/>
<!-- Delegacion y subdelegacion del usuario -->
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">

			<form:form modelAttribute="filtrosReportes" id="reportesAnalisisForm" action="${contextpath}/consulta/reportesAnalisis/generarReporte" method="post">
				<fieldset>
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>
					
					<div>
						<div><form:errors path="registroPatronal" cssClass="error" /></div>
						
						<c:if test="${msjException ne ''}">
							<div class="error">${msjException}</div>						
						</c:if>
						
					</div>
					
					<c:if test="${grupoTramite == '2'}">
						<fieldset class="fsInterno">
							<label class="wide"><spring:message code="label.detalle.tipo.modificacion" />:</label> 
							<select id="tipoMovimiento" name="tipoMovimiento">
								<option value=""><spring:message code="label.filtros.combos.todos" /></option>
									<c:forEach var="tipoTramite" items="${lstTipoTramite}">
										<option value="${tipoTramite.idTipoTramite}">${tipoTramite.descripcion}</option>
									</c:forEach>  
							</select> 
						</fieldset>
					</c:if>
					<c:if test="${grupoTramite == '1'}">
						<input type="hidden" id="tipoMovimientoHidden" name="tipoMovimiento" value="${tipoMovimientoGrupoTramite}" />
					</c:if>
					
					<input type="hidden" id="grupoTramite" name="grupoTramite" value="${grupoTramite}" />
					
					<fieldset class="fsInterno">
					    <label class="wide"><spring:message code="label.detalle.registro.patronal" />:</label> 
						<input name="registroPatronal" id="registroPatronal" style="width: 100px" type="text" maxlength="11; text-transform: uppercase;" value="${filtrosReportes.registroPatronal}"/>
					</fieldset>
					
					<fieldset class="fsInterno">
						<div>
							<div><form:errors path="strPeriodoInicio" cssClass="error" /></div>
							<div><form:errors path="strPeriodoFin" cssClass="error" /></div>
						</div>
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label> 
						<input name="strPeriodoInicio" id="strPeriodoInicio" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa" value="${filtrosReportes.strPeriodoInicio}"/>
						<label class="wide" for="strPeriodoFin" style="width: 20px"> a</label> 
						<input name="strPeriodoFin" id="strPeriodoFin" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa" value="${filtrosReportes.strPeriodoFin}"/>
					</fieldset>
					
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.filtros.busqueda.tipo.persona" />:</label> 
						<combo:creaCombo idHtml="tipoPersona" idHtmlContenedor="reportesAnalisisForm" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoPersona" mostrarSoloActivos="true" />
					</fieldset>
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.filtros.busqueda.clase.rectificada" />:</label> 
						<combo:creaCombo idHtml="clasePropuesta" idHtmlContenedor="reportesAnalisisForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DicClase" mostrarSoloActivos="true"/>
					</fieldset>
					
					<fieldset class="fsInterno">
						<label class="wide"><spring:message code="label.filtros.busqueda.tipo.registro" />:</label>
						
						<input id="tipoRegistroTodo"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="-1" 
							<%=request.getAttribute("tipoRegistro")!=null && request.getAttribute("tipoRegistro").toString().equalsIgnoreCase("-1")?"checked='checked'":""%>/>
						 <label
							for="tipoRegistroTodo"><spring:message code="label.filtros.busqueda.radio.todos" /></label> 
						 
						<input
							id="tipoRegistroArp" class="submit_no_margin" name="tipoRegistro" type="radio" value="0"
							<%=request.getAttribute("tipoRegistro")!=null && request.getAttribute("tipoRegistro").toString().equalsIgnoreCase("0")?"checked='checked'":""%>/>
							 
						<label
							for="tipoRegistroArp"><spring:message code="label.filtros.busqueda.radio.arp" /></label>
							
						<input id="tipoRegistroPsp"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="2"
							<%=request.getAttribute("tipoRegistro")!=null && request.getAttribute("tipoRegistro").toString().equalsIgnoreCase("2")?"checked='checked'":""%>/> 
						 <label
							for="tipoRegistroRpc"><spring:message code="label.filtros.busqueda.radio.psp" /></label>
							
						<input id="tipoRegistroRpc"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="1"
							<%=request.getAttribute("tipoRegistro")!=null && request.getAttribute("tipoRegistro").toString().equalsIgnoreCase("1")?"checked='checked'":""%>/> 
						 <label
							for="tipoRegistroRpc"><spring:message code="label.filtros.busqueda.radio.rpc" /></label> 
						
					</fieldset>
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.detalle.estatus" />:</label> 
						<select id="estatus" name="estatus">
							<option value=""><spring:message code="label.filtros.combos.todos" /></option>
								<c:forEach var="estatusAnalisis" items="${lstEstatusAnalisis}">
									<option value="${estatusAnalisis.cveIdEstatus}">${estatusAnalisis.desEstatus}</option>
								</c:forEach>  
						</select> 
					</fieldset>
					<fieldset class="fsInterno hiddenElement" id="delegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.delegacion" />:</label> 
						
						<combo:creaCombo entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion"
										 idHtml="delegacion"
										 idHtmlContenedor="reportesAnalisisForm"
										 mostrarSoloActivos="true"/>
						
					</fieldset>
					<fieldset class="fsInterno hiddenElement" id="subdelegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.subdelegacion" />:</label>
						<combo:creaCombo 		entidad			="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion" 
		                     					idHtml			="subDelegacion" 
		                     				  	entidadPadre	="dicDelegacion.cveIdDelegacion"
		                     				  	idHtmlPadre		="delegacion"
		                     				  	idHtmlContenedor="reportesAnalisisForm"
		                     				  	mostrarSoloActivos="true"/> 
					</fieldset>
										
					<input name="cveIdGrupoAnalisisCe" id="cveIdGrupoAnalisisCe" type="hidden" value="<%=GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave()%>"/>	
					<input name="esConsulta" id="esConsulta" type="hidden" value="${filtrosReportes.esConsulta}"/>
					<div style="text-align: right; float: right;">
						<input type="submit" value="Generar Reporte" class="mboton"  style="width: 150px;" onClick="fnClearFormErrors();"/>
					</div>
				</fieldset>
				
			</form:form>
		</div>
		
		<!--Aquí termina tu código-->
	</div>
	<!--Termino  contenido-->
</div>
