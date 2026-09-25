<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgGrupoCategoriaBorrar">
	<div id="wrapperDialogBorrar">
		<div id="dialog-confirm" title="¿Eliminar el registro?">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
				<label>¿Confirma que desea eliminar el registro seleccionado?</label>
			</p>
		</div>
		<form:form modelAttribute="crcGrupoCategoria" action="/catalogo/grupoCategoria/eliminar.do" method="post" id="grupoCategoriaFormBorrar">
		 	<form:hidden path="cveGrupoCategoria" />
		</form:form>		
	</div>
</div>