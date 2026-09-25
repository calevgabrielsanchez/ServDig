<%@ include file="taglibs.jsp" %>

<head>
	<style type="text/css">
		h2 {
		    color: #157164;
		    font-size: 24px;
		    font-weight: bold;
		    line-height: 1em;
		    margin-bottom: 0.5em;
		    margin-top: 0;
		}
	</style>

	<script type="text/javascript">
	
		$(document).ready(function() {
					        
			// 191807 170412
			var iTotalDisplayRecords;
			
			$('#registroPersonaMoral').click(function() {
				$('form#orquestadorMoralForm').submit();
			});
					
		});
		
	</script>
</head>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/general.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/limpiarFormulario.js" htmlEscape="true" />"></script>

<div id="orquestador">

	<div class="row contenedor" style="width: 100% !important">
		<div id="contenidodeportal" class="cell portal_titulo">
		
			<img style="margin-right: 20px;" align="left" width="300px" height="150px" src="<spring:url value="/static/resources/imagenes/tramites2.jpg" htmlEscape="true" />" title="Portal IMSS"  />
		
			<h2><spring:message code="label.orquestador.titulo" /></h2>
	        <p><spring:message code="label.orquestador.subtitulo.moral" /></p>
	       	<br/>
	       	
	       	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	       	
			<form id="orquestadorMoralForm" action="${contextpath}/persona/moral/registroPopup">
        		<div class="area">
				    <div style="background:white; max-width:237px; max-height:170px;" class="cuadro K margen_derecho">
				      
				    	<div class="titulo_seccion">REGISTRO DE PERSONAS</div>
				    	<div class="lista_tipo_69">
					        <ul>
					        	<li>
									<a id="registroPersonaMoral" style="cursor: pointer;">Persona Moral</a>
					        	</li>
					        </ul>
				  		</div>
				    </div>
				    
	  			</div><!-- area -->
			</form>
		</div><!-- contenidodeportal -->
	</div><!-- row contenedor -->

	<div id="areas" style="margin: 10px">
		<span id="errorNegocioLabel" class="error hiddenElement"></span>
		<div class="area">	
        	<p><spring:message code="label.orquestador.descripcion.tabla" /></p>
			<table id="personasMoralesSolicitudTable"></table>
		</div>
	</div>
	<br />
	
</div><!-- orquestador -->

<script type="text/javascript">
<!--
var dtPersonasSolicitud;

$(document).ready(function(){
	
	// Configuracion del datateibol que muestra las personas morales a dar de alta en la solicitdu. Esta es para el rol de VENTANILLA, o sea que aqui si 
	// necesitamos que aparezca la columna 'Calificacion'
	if('<c:out value="${role}" />' == "ventanilla"){
		dtPersonasSolicitud = $('#personasMoralesSolicitudTable').dataTable({
			sScrollX: "100%",
			sScrollY: "10%",
			bJQueryUI : true,
	        bFilter : false,
	        bInfo: false,
	        bSort: false,
	        "bPaginate": false,
	        "bAutoWidth" : true,
	        "bServerSide" : true,
	        "aoColumns" : [
	                       { 
	                           "sTitle" : "RFC",
	                           "mDataProp" : "rfc",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Raz&oacute;n Social",
	                           "mDataProp" : "razonSocial",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Tipo Sociedad",
	                           "mDataProp" : "tipoSociedad.descripcionAbreviada",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Acta Constitutiva",
	                           "mDataProp" : "actaConstitutiva",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Fecha de Creaci&oacute;n",
	                           "mDataProp" : "fechaCreacion",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Calificaci&oacute;n",
	                           "sClass":"dtJustifyClassColumn"
	                       }
	], "aoColumnDefs": [
						{
						    "fnRender": function ( oObj ) {
						       var arCalificaciones =  oObj.aData['personaCalificaciones'];
						       var retVal = arCalificaciones[0].calificacion.descripcion;
						       return retVal;
						    },
						    "aTargets": [ 5 ]
						}
	],

	"bProcessing" : true,
	"sAjaxSource" : context_path + '/persona/moral/registro-persona-solicitud-lst',
	"fnServerData" : function(sSource, aoData, fnCallback) {    
	    fnHideErrores("#areas");
	    fnHideErrores("#personasMoralesSolicitudTable");
	    aoData.push({
	        "name" : "sSearch",
	        "value" : ''
	    });
	    
	    var wrapper = new Object();
	    wrapper.aoData = aoData;

	    $.postJSON(sSource, wrapper, function(data) {
	    	
	    	// 191807 170412
	    	iTotalDisplayRecords = data.iTotalDisplayRecords;
	    	
	        fnCallback(data);
	    }).error(function(data) {
	        fnProcesarErrores(data, "#personasMoralesSolicitudTable");
	        fnCallback(dataEmpty);
	    });
	    
	}

	});
		
	}//if
	
	// Configuracion del datateibol que muestra las personas morales a dar de alta en la solicitdu. Esta es para el rol de INTERNET, o sea que aqui no necesitamos
	// que aparezca la columna 'Calificacion'
	else{
		dtPersonasSolicitud = $('#personasMoralesSolicitudTable').dataTable({
			sScrollX: "100%",
			sScrollY: "10%",
			bJQueryUI : true,
	        bFilter : false,
	        bInfo: false,
	        bSort: false,
	        "bPaginate": false,
	        "bAutoWidth" : true,
	        "bServerSide" : true,
	        "aoColumns" : [
	                       { 
	                           "sTitle" : "RFC",
	                           "mDataProp" : "rfc",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Raz&oacute;n Social",
	                           "mDataProp" : "razonSocial",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Tipo Sociedad",
	                           "mDataProp" : "tipoSociedad.descripcionAbreviada",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Acta Constitutiva",
	                           "mDataProp" : "actaConstitutiva",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       {
	                           "sTitle" : "Fecha de Creaci&oacute;n",
	                           "mDataProp" : "fechaCreacion",
	                           "sClass":"dtJustifyClassColumn"
	                       }
						],

	"bProcessing" : true,
	"sAjaxSource" : context_path + '/persona/moral/registro-persona-solicitud-lst',
	"fnServerData" : function(sSource, aoData, fnCallback) {    
	    fnHideErrores("#areas");
	    fnHideErrores("#personasMoralesSolicitudTable");
	    aoData.push({
	        "name" : "sSearch",
	        "value" : ''
	    });
	    
	    var wrapper = new Object();
	    wrapper.aoData = aoData;

	    $.postJSON(sSource, wrapper, function(data) {
	    	
	    	// 191807 170412
	    	iTotalDisplayRecords = data.iTotalDisplayRecords;
	    	
	        fnCallback(data);
	    }).error(function(data) {
	        fnProcesarErrores(data, "#personasMoralesSolicitudTable");
	        fnCallback(dataEmpty);
	    });
	    
	}

	});
		
	}//else
		
});//ready

//-->
</script>
