<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="cuestionario" uri="http://www.serviciosdigitales.imss.gob.mx/tags/cuestionario"%>

<style>
	div#cuestionarioContainer {
		margin: 30px 0px;
	}
	
	.seccionCuestionario div.panel {
    	margin: 0 15px 20px;
	}
	
	.seccionCuestionario div.panel.pregDependiente {
		border: none;
		box-shadow: none;
		margin-bottom: 0;
	}
	
	.seccionCuestionario div.panel-heading {
	    font-weight: 600;
	    text-transform: uppercase;
	}
		
	.seccionCuestionario div.panel-body {
	    padding: 5px 15px 15px;
	}
	
	.seccionCuestionario select.form-control {
	    margin-top: 10px;
	}
		
	div.pregDependienteContainer {
		border-bottom: 1px lightgray solid;		
	}
	
	p.pregDependiente {
		text-transform: uppercase;
	}
	
	.panel-heading .required, p.pregDependiente .required  {
	    margin-right: 7px;
	}
	
	.panel-heading .error, p.pregDependiente .error {
	    font-size: small;
	    font-weight: normal;
    	margin-top: 5px;
	}
	
	label.disabled {
		color: #959595;
	}
</style>

<div id="cuestionarioContainer">
	<c:choose>
		<c:when test="${not empty cuestionario.errorFormGeneral }">
			<div class="alert alert-danger">
				Error al generarl el cuestionario: 
				<strong>${cuestionario.errorFormGeneral}</strong>
			</div>
		</c:when>
		<c:otherwise>
			<cuestionario:armar cuestionario="${cuestionario }"/>
		</c:otherwise>
	</c:choose>	
</div>