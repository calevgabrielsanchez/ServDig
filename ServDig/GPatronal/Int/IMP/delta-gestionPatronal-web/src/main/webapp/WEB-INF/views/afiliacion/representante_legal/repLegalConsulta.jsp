<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/repLegal.js" htmlEscape="true" />"></script>

<div id="representanteLegal"   style="height: 100%">

<!-- JSP principal del modulo de representante Legal -->

<c:set var="cveIdPatronSujetoObligado"  value="${representanteLegal.cveIdPatronSujetoObligado}" ></c:set>

<div style="display:none;">
	<form:form modelAttribute="representanteLegal"  id="representanteLegalFormPaginar">
			<form:hidden path="cveIdPatronSujetoObligado" id="cveIdPatronSujetoObligado"/>
	</form:form>
</div>
	
	<table style="width:100%; ">
	<tr>
		<td>
		
<div id="lista" style="width:  100%;" >

	<div id="tabla">
		<table id="tbRepresentanteLegal"  style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
		<thead>
		</thead>
		<tbody style="width: 100%;  ">
		</tbody>
		<tfoot>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</tfoot>
	</table>
	</div>
</div>
		</td>
	</tr>
	
</table>



</div>



<div id="dgNuevoRepresentanteLegal" title="Agregar elemento"  style="background-color: white !important; ">
	
	<jsp:include page="nuevo.jsp"></jsp:include>
</div>

<div id="dgEliminarRepresentanteLegal" title="¿Eliminar elemento?">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;">
	</span>El registro seleccionado será eliminado, ¿Esta Ud. seguro?</p>
        </br>
        <span id="errorNegocioLabel"  class=" hiddenElement error"></span>
</div>

<div id="dgErrorSinSeleccionRepresentanteLegal" title="Debe seleccionar un elemento">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;">
	</span>No se ha seleccionado ningun registro para la acción.</p>
</div>


<div id="dgModificarRepresentanteLegal" title="Modificar elemento" style="background-color: white !important; ">
	
	<jsp:include page="modificar.jsp"></jsp:include>
</div>


