<%@ include file="../general/taglibs.jsp" %>

<!--<script>
	$(document).ready(
		function() {
			$("#frameO").load("/portal-web/portal/frame");
		}	
	);
</script>
--><!-- Inicio del contenido del portal -->
<div id="portal" class="contenedor">
	<div class="row" style="width: 100% !important">
		<div  id="mediosdeacceso" class="cell portal_medios_acceso">
			<div id="login" class="row" style="height: 150px !important;">
					
						<h2>Opciones</h2>
						<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
						
						<div id="frameO">
							<iframe id="frameU" name="frameU" src="/portal-web/portal/frame" width="100%" height="100%"></iframe>
						</div>
			</div>
		</div>
		
		<div id="contenidodeportal" class="cell portal_informacion">
			<h2> <spring:message code="label.portal.bienvenido" /> </h2>
	        <p style="font-size: .9em;"> 
	        	<spring:message code="label.portal.informacion.sistema" />
	        </p>
	        
	        </br>
	        
	        <div id="imagen_portal">
	        	
	        	<img  src="<spring:url value="/static/resources/imagenes/tramites.jpg" htmlEscape="true" />" title="Portal IMSS"  />
	        </div>
	        
	          </br>
	        <div id="areas" >
				
	        </div>
	        
		</div>
		<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
		<div id="wizardRegistroUsuario"></div>
		<!-- div para la forma de firma -->
		<div id="firmaDigitalComponent"></div>
		<div id="doctosRequeridosTramite"></div>
		
	</div>
	
	
	
</div>


<div id="dialog-mensajes" title="Mensaje del sistema">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo">Para poder ingresar, debe estar registrado como usuario</label>
	</p>
</div>
<!--Script al final para que la pagina cargue mas rapido-->
<script type="text/javascript">
    $(document).ready(function() {
        if (self != top) {
            top.location = self.location
        }
    })
</script>

