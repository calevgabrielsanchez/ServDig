<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../general/taglibs.jsp"%>

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>

<script>

	/*Se ejecuta hasta que la pagina se carga complementamente*/
	$(window).load(function(){
		fnInitCombosDelegacion();

		$("#strPeriodoInicio").val('${filtrosBitacoras.strPeriodoInicio}');
		$("#strPeriodoFin").val('${filtrosBitacoras.strPeriodoFin}');

		if('${filtrosBitacoras.esConsulta}' == 'true'){
			if('${filtrosBitacoras.delegacion}' != ''){
				$('select#delegacion').get(0).options.value = '${filtrosBitacoras.delegacion}';
			}else{
				$('select#delegacion').get(0).options.value = '';
			}

			if('${filtrosBitacoras.subDelegacion}' != ''){
				$('select#subDelegacion').get(0).options.value = '${filtrosBitacoras.subDelegacion}';
			}else{
				$('select#subDelegacion').get(0).options.value = '';
			}						
		}
		
		var esInscripcion = '${grupoTramite}';
		if(esInscripcion == '1'){
			document.getElementById("cveIdGrupoAnalisisCe").value = <%=GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave()%>;
		}
		
		$.ajaxSetup({async:true});
	});
	
	$(document).ready(function(){
		$( "#strPeriodoInicio" ).datepicker();
		$( "#strPeriodoInicio" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
		$( "#strPeriodoFin" ).datepicker();
		$( "#strPeriodoFin" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );

		document.getElementById("cenefa").innerHTML = "Inicio» Bit&aacute;cora";

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
			 * Este rol es de tipo NORMATIVO Nacional por lo tanto ve todo
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
			fnSetComboSubdelegacion($('select#subDelegacion'))

			document.getElementById('subDelegacion').disabled=true;	
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
		if('${filtrosBitacoras.delegacion}' != ''){
			var idDelegacion = '${filtrosBitacoras.delegacion}';
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
		fnSetComboSubdelegacion($('select#subDelegacion'));
	}

	/**
	 * Funcion que setea las opciones de combo de delegacion y subdelegacion para los
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
		if('${filtrosBitacoras.subDelegacion}' != ''){
			var idSubDelegacion = '${filtrosBitacoras.subDelegacion}';
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
<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol" value="${usuario.perfilUsuario}"/>
<input type="hidden" id="rol" name="rol" value="${rol.idPerfilUsuario}"/>
<!-- Delegacion y subdelegacion del usuario -->
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>
<c:set var="msjException" value="${msjException}"/>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">
			
			<form:form modelAttribute="filtrosBitacoras" id="reportesBitacorasForm" action="${contextpath}/consulta/reportesBitacoras/generarReporte" method="post">
				
				<fieldset>
					
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>
					
					<fieldset class="fsInterno">
						<form:errors path="cveIdAnalisis" cssClass="error"></form:errors>	
						<c:if test="${msjException ne ''}">
							<div class="error">${msjException}</div>						
						</c:if>											 							 				
					</fieldset>
					
					<fieldset class="fsInterno">
						<label class="wide"><spring:message code="label.detalle.usuario" />:</label> 
						<input name="usuario" id="usuario" style="width: 100px" type="text" value="${filtrosBitacoras.usuario}"/>
					</fieldset>
					
					<fieldset class="fsInterno">
						<div>
							<div><form:errors path="strPeriodoInicio" cssClass="error" /></div>
							<div><form:errors path="strPeriodoFin" cssClass="error" /></div>
						</div>
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label>
						<input name="strPeriodoInicio" id="strPeriodoInicio" value="${filtrosBitacoras.strPeriodoInicio}" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/> 
						<label class="wide" for="strPeriodoFin" style="width: 20px"> a</label>
						<input name="strPeriodoFin" id="strPeriodoFin" value="${filtrosBitacoras.strPeriodoFin}" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/>
					</fieldset>
					
					<fieldset class="fsInterno hiddenElement" id="delegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.delegacion" />:</label> 
						<combo:creaCombo idHtml="delegacion" idHtmlContenedor="reportesBitacorasForm" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion" mostrarSoloActivos="true"/>
					</fieldset>
					
					<fieldset class="fsInterno hiddenElement" id="subdelegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.subdelegacion" />:</label>
						<combo:creaCombo 		entidad			="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion" 
		                     					idHtml			="subDelegacion" 
		                     				  	entidadPadre	="dicDelegacion.cveIdDelegacion"
		                     				  	idHtmlPadre		="delegacion"
		                     				  	idHtmlContenedor="reportesBitacorasForm"
		                     				  	mostrarSoloActivos="true"/>  
					</fieldset>
											
					<input name="cveIdGrupoAnalisisCe" id="cveIdGrupoAnalisisCe" type="hidden" value="<%=GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave()%>"/>
					<input name="esConsulta" id="esConsulta" type="hidden" value="${filtrosBitacoras.esConsulta}"/>
					<div style="text-align: right; float: right;">
						<input type="submit" value="Generar Bit&aacute;cora" class="mboton" style="width: 160px;" onClick="fnClearFormErrors();"/>
					</div>
				</fieldset>
				
			</form:form>
		</div>		

		<!--Aquí termina tu código-->
	</div>
	<!--Termino  contenido-->
</div>

