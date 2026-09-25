<%@ include file="../general/taglibs.jsp" %>

<!-- Inicio del contenido del gestionCobranza -->
<div id="gestionCobranza">
	<div class="row contenedor" style="width: 100% !important">
		<div  id="mediosdeacceso" class="cell gestionCobranza_medios_acceso">
			<div id="login" class="row">
					
						<h2><spring:message code="label.gestionCobranza.usuario.acceso" /></h2>
						<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
						
						<div>
						<form:form modelAttribute="usuario"
							action="${contextpath}/login/entrar" method="post" id="formlogin">
						
						
							<div id="usuario-contenedor">
								<form:errors path="usuario" cssClass="error" /> 
								<form:label path="usuario">
									<strong class="etiqueta"> 
									<spring:message code="label.usuario" />
									</strong>
								</form:label> 
								<form:input type="text" path="usuario" maxlength="10" />
							</div>
						
							<div id="password-contenedor">
								<form:errors path="password" cssClass="error" /> 
								<form:label path="password">
									<strong class="etiqueta">
									<spring:message code="label.password" />
									</strong>
								</form:label> 
								<form:input type="password" path="password" id="password" value="" maxlength="13" />
							</div>
							</br>
							<div class="derecha">
								<input type="submit" class="mboton" style="font-size: 10px !important;" value="<spring:message code="label.ingresar" />">
							</div>
							<div >
								<span>  <a style="color:#999;"> � Olvid&oacute; su Contrase&ntilde;a ? </a></span>
							</div>
						</form:form>
						</div>
			</div>
			<div id="registronuevousuario" class="row">
				<h2> <spring:message code="label.gestionCobranza.usuario.nuevo" /> </h2>
		        <p style="font-size: .9em;"> 
		        	<spring:message code="label.gestionCobranza.informacion.usuario.nuevo" />
		        </p>
		        </br>
		        <div class="derecha">
		        	<form id="formRegistroUsuarioNuevo" >
						<input type="submit" class="mboton" style="font-size: 10px !important;" value="<spring:message code="label.gestionCobranza.button.crear.cuenta.nueva" />">
					</form>
				</div>
			</div>
		</div>
		
		<div id="contenidodegestionCobranza" class="cell gestionCobranza_informacion">
			<h2> <spring:message code="label.gestionCobranza.bienvenido" /> </h2>
	        <p style="font-size: .9em;"> 
	        	<spring:message code="label.gestionCobranza.informacion.sistema" />
	        </p>
	        
	        </br>
	        
	        <div id="imagen_gestionCobranza">
	        	
	        	<img  src="<spring:url value="/static/resources/imagenes/tramites.jpg" htmlEscape="true" />" title="Portal IMSS"  />
	        </div>
	        
	          </br>
	        <div id="areas" >
	        		<div class="area">
					    
					    <div style="background:white; max-width:237px; max-height:170px;" 
					    	class="cuadro K margen_derecho">
					      
					      <div class="titulo_seccion">
					      FISCALIZACI&Oacute;N
					      </div>
							
					      <div class="lista_tipo_D">
					        <ul>
					          <li><a href="/tramites/catalogo">Cat�logo de Tr�mites</a></li>
					          <li><a href="/credencial">Nueva credencial </a></li>
					          <li><a href="/patrones/sua">Sistema �nico de Autodeterminaci�n</a></li>
					          <li><a href="/servicios/linea">Servicios en l�nea</a></li>
					          <li><strong><a href="/tramites">Ver m�s</a></strong><a href="/tramites">>></a></li>
					        </ul>
					      </div>
					    </div>
		  			</div>
		  			<div class="area">
					    
					    <div style="background:white; max-width:237px; max-height:170px;" 
					    	class="cuadro K margen_derecho">
					      
					      <div class="titulo_seccion">
					      	INCORPORACI&Oacute;N
					      </div>
							
					      <div class="lista_tipo_D">
					        <ul>
					          <li><a href="/tramites/catalogo">Cat�logo de Tr�mites</a></li>
					          <li><a href="/credencial">Nueva credencial </a></li>
					          <li><a href="/patrones/sua">Sistema �nico de Autodeterminaci�n</a></li>
					          <li><a href="/servicios/linea">Servicios en l�nea</a></li>
					          <li><strong><a href="/tramites">Ver m�s</a></strong><a href="/tramites">>></a></li>
					        </ul>
					      </div>
					    </div>
		  			</div>
		  			<div class="area">
					    
					    <div style="background:white; max-width:237px; max-height:170px;" 
					    	class="cuadro K margen_derecho">
					      
					      <div class="titulo_seccion">
					      	RECAUDACI&Oacute;N
					      </div>
							
					      <div class="lista_tipo_D">
					         <ul>
					          <li><a href="/tramites/catalogo">Cat�logo de Tr�mites</a></li>
					          <li><a href="/credencial">Nueva credencial </a></li>
					          <li><a href="/patrones/sua">Sistema �nico de Autodeterminaci�n</a></li>
					          <li><a href="/servicios/linea">Servicios en l�nea</a></li>
					          <li><strong><a href="/tramites">Ver m�s</a></strong><a href="/tramites">>></a></li>
					        </ul>
					      </div>
					    </div>
		  			</div>
	        </div>
	        
		</div>
	</div>
	
	
</div>