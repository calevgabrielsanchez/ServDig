<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Contenedor del estado vacio de la lista de patrones -->
<div class="container-fluid empty-state">
	<div class="row">
		<!-- Imagen -->
		<div class="col-xs-12 imagen">
			<i class="fa fa-file-text"></i>
		</div>
	</div>
	
	<div class="row">
		<!-- Titulo -->
		<div class="col-xs-12 titulo">
					<spring:message
						code="label.wizard.tramite.pasos.titulo" />
		</div>
	</div>
	<br>
	
	<!-- Opciones -->
	<div class="row opciones">
		<!-- Opcion -->
		<div class="col-xs-6">

			<div class=" opcion">
				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.retomar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.tramite.retomar.descripcion" />
					</p>
				</div>
			</div>
		</div>
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class="opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.captura.continuar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.tramite.captura.continuar.descripcion" />
					</p>
				</div>

			</div>
		</div>
		
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class="opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.finalizar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.tramite.finalizar.descripcion" />
					</p>
				</div>

			</div>
		</div>

	</div>

</div>