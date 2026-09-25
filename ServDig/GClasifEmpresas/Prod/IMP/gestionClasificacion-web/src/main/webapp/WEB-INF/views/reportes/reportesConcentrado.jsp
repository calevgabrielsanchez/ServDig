<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../general/taglibs.jsp"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum"%>

<script>

	/*Se ejecuta hasta que la pagina se carga complementamente*/
	$(window).load(function(){		
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

		$("#strPeriodoInicio").val('${filtrosConcentrado.strPeriodoInicio}');
		$("#strPeriodoFin").val('${filtrosConcentrado.strPeriodoFin}');

		$.ajaxSetup({async:false});
	});

	function fnClearFormErrors(){
		 $('span.error').each(function(i,v) {
		        var text = '';
		        $(this).text(text);
		    });
	
		 return true;
	}

</script>

<c:set var="contextpath" value="<%=request.getContextPath() %>" />
<c:set var="msjException" value="${msjException}"/>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">
			
			<form:form modelAttribute="filtrosConcentrado" id="reportesConcentradoForm" action="${contextpath}/consulta/concentradoNacional/generarConcentrado" method="post">
				
				<fieldset>
					
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>
					
					<fieldset class="fsInterno">
						<form:errors path="cveIdGrupoAnalisisCe" cssClass="error"></form:errors>	
						<c:if test="${msjException ne ''}">
							<div class="error">${msjException}</div>						
						</c:if>											 							 				
					</fieldset>
										
					<fieldset class="fsInterno">
						<div>
							<div><form:errors path="strPeriodoInicio" cssClass="error" /></div>
							<div><form:errors path="strPeriodoFin" cssClass="error" /></div>
						</div>
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label>
						<input name="strPeriodoInicio" id="strPeriodoInicio" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa" value="${filtrosConcentrado.strPeriodoInicio}"/> 
						<label class="wide" for="strPeriodoFin" style="width: 20px"> a</label>
						<input name="strPeriodoFin" id="strPeriodoFin" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa" value="${filtrosConcentrado.strPeriodoFin}"/>
					</fieldset>					
											
					<input name="cveIdGrupoAnalisisCe" id="cveIdGrupoAnalisisCe" type="hidden" value="<%=GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave()%>"/>
					<div style="text-align: right; float: right;">
						<input type="submit" value="Generar Reporte" class="mboton" style="width: 160px;" onClick="fnClearFormErrors();"/>
					</div>
					
				</fieldset>
				
			</form:form>
		</div>		

		<!--Aquí termina tu código-->
	</div>
	<!--Termino  contenido-->
</div>

