<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/denuncia/initListaDen.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/denuncia/cargaInicial.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/imss/style.css"  />

<link type="text/css"    href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css"   href="<%=request.getContextPath()%>/resources/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" />


<div id="consultaCurp" class="form-comment">

    <table id="dtDenuncias"  style="width: 900px; text-align: center" title="Denuncias Presentadas">
             <!-- <thead>
                   <tr>
                                <th colspan="1" rowspan="1" width="2%" align="center"></th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Folio</th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Fecha de Registro</th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Nombre del Denunciante</th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Nombre del Patr&oacute;n Denunciado</th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Estatus</th>
                   </tr>
              </thead> -->
    </table>
    <table style="width: 800px; align: center">
    	<tr> <td> <br/></td> </tr>
		<tr> <td> <br/></td> </tr>
	
        <tr align="center">
        
        	<td>&nbsp;&nbsp;<button type="button"  name="btnAgregarDenuncia" id="btnAgregarDenuncia" class="mboton" onclick="agregaDenuncia();" >Agregar Denuncia</button>&nbsp;&nbsp;</td>
        	<td>&nbsp;&nbsp;<button type="button"  name="btnConsultarDenuncia" id="btnConsultarDenuncia" class="mboton" onclick="consultarDenuncia();">Consultar Denuncia</button>&nbsp;&nbsp;</td>
         
         </tr>
         	<tr> <td> <br/></td> </tr>
         		<tr> <td> <br/></td> </tr>
    </table>

</div>
<form  id="ndForm"  name="ndForm" action="<%=request.getContextPath()%>/denuncia/nueva.do"></form>