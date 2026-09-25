<%@ include file="../../general/taglibs.jsp"%>

<div class="col-sm-12">
	<div class="pie row">
		<div id="clinicaOpcionesPie"  class="opciones col-sm-6">
			<div class="btn-group dropup" id="clinicaBotones" style="display:none">
				<a class="btn btn-primary" href="#">Acciones</a> <a
					class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>Finalizar Trámite</a></li>
					<li><a id="btnInicioCancelarTramiteClinica"><i class="glyphicon glyphicon-trash"></i>Cancelar Tr&aacute;mite</a></li>		
				</ul>
				
			</div>
			
		</div>
	
		<div class="controles col-sm-6">
			<div class="pull-right">
				<c:if test="${not empty botonRegresar}">
					<button class="btn btn-default" id="clinicaBotonesRegresar"><span class="glyphicon glyphicon-step-backward"></span> Regresar</button>
				</c:if>			
			</div>
		</div>
	</div>
</div>