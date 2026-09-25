<%@ include file="../general/taglibs.jsp" %>

<div class="contenedor">

<div style="height: 550px !important; padding: 20px;" class="row">
	
	<div style="padding: 20px; width: 70%; "  class="cell" >
		<h2> Registro de Persona</h2>
		        <p style="font-size: .9em;"> 
		        	Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sitamet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo utodio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate
		        </p>
		        </br> 
		        <h3> Beneficios</h3>
		        <p style="font-size: .9em;"> 
		        	Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sitamet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo utodio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate
		        </p>
		        </br> 
	
	</div>
	
	<div style="padding: 20px; width: 30%;" class="cell contenedor-gris">
				<div class="row">
					<h2> Pasos</h2>
			       		        <p style="font-size: .9em;"> 
		        	Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sitamet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo utodio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate
		        </p>
		        </div>
		        <div class="row">
		        	
		        	<ol>
		        		<li> Registro Basico</li>
		        		<li> Registro de Domicilio</li>
		        		<li> Registro de medios de contacto</li>
		        	</ol>
		        	
		        </div>
		        <br><br><br><br>
		        <div class="row">
		        <c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		        	<form action="${contextpath}/tramite/capturar" >
		        		<input type="submit" value="Iniciar Solicitud" class="mboton"/>
		        	</form>
		        </div>
	
	
	</div>
</div>



</div>
	
