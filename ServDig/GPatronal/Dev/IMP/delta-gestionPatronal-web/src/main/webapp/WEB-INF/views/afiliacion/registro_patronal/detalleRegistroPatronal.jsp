<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/reset.css" htmlEscape="true" />' />
<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/style.css" htmlEscape="true" />' />

<style>

#selectable .ui-selecting { background: #FECA40; }
#selectable .ui-selected { background: #F39814; color: white; }
#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }

#tabs_wrapper {
	width: 422px;
	background: white;
}
a:active {
	outline: none;
}
a:focus {
	-moz-outline-style: none;
}
#tabs_container2 {
	width: 100%;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;		
}
#tabs_container2 ul.tabs {
	background: white;
	list-style: none;
	padding: 5px 0 4px 0;
	font: arial;
	margin: 0 0 0 10px;
}
#tabs_container2 ul.tabs li {
	float: left;
}
#tabs_container2 ul.tabs li a {
	padding: 3px 10px;
	display: block;
	border-left: 1px solid #ccc;
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc;
	margin-right: 2px;
	text-decoration: none;color:white;
	background-color: #0A6659;
}
#tabs_container2 ul.tabs li.active a {
	text-decoration:none;color:#0A6659;
	background-color: #fff;
	padding-top: 4px;
}
div.tab_contents_container {
	border: 1px solid #ccc;
	border-top: none;
	padding: 10px;
	width: 900px;

}
div.tab_contents {
	display: none;
}
div.tab_contents_active {
	display: block;
}
div.clear {
	clear: both;
}

.etabs { margin: 0; padding: 0; }
.tab2 { display: inline-block; zoom:1; *display:inline; background: #eee; border: solid 1px #999; border-bottom: none; -moz-border-radius: 4px 4px 0 0; -webkit-border-radius: 4px 4px 0 0; }
.tab2 a { font-size: 14px; line-height: 2em; display: block; padding: 0 10px; outline: none; }
.tab2 a:hover { text-decoration: underline; }
.tab2.active { background: #fff; padding-top: 6px; position: relative; top: 1px; border-color: #666; }
.tab2 a.active { font-weight: bold; }
.tab2-container2 .panel-container2 { background: #fff; border: solid #666 1px; padding: 10px; -moz-border-radius: 0 4px 4px 4px; -webkit-border-radius: 0 4px 4px 4px; }

</style>

<script type="text/javascript">
	$(function(){	
		$('#tab-container2').tabs();
	});
	
	
	var context = "<%=request.getContextPath()%>";
	

</script>

<br>
<br>

<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="rowDetalleRegistroPatronalGeneral" style="width: 1000px;" >
					<jsp:include page="encabezadoDetalleRegistroPatronal.jsp"/>
					<div id="tab-container2" class="tab-container2" style="width: 900px !important">
						<div id="tab-contents">
					  		<ul class='etabs'>
					    		<li class='tab2'><a href="#tabs1-a">Centro de Trabajo</a></li>
					    		<li class='tab2'><a href="#tabs1-b">Clasificaci&oacute;n</a></li>    
					  		</ul>
					  		
					  		<div id="tabs1-a">    
									<jsp:include page="centroTrabajo.jsp" />
					  		</div>
					  		<div id="tabs1-b">
								<jsp:include page="resumenClasificacion.jsp" />
					  		</div>
					  		
					  	</div>
					</div>
					<form>
						<input type="button" id="btnRegresarDetalleSujetoObligado" class="mboton" style="width:200px;" 
									onclick="history.back();" value="Regresar">
					</form>
				</div>
			</div>
		</div>
	</div>
</div>