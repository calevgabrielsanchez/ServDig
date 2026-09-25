<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgGastosBorrar">
	<div id="wrapperDialogBorrar">
		<div id="dialog-confirm" title="¿Eliminar el registro?">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
				<label>¿Confirma que desea eliminar el registro seleccionado?</label>
			</p>
		</div>
		<form:form modelAttribute="crcGastos" action="/catalogo/gastos/eliminar.do" method="post" id="gastosFormBorrar">
		 	<form:hidden path="cveGasto" />
		</form:form>		
	</div>
</div>