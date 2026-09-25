<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>
		
		
		
 <script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>

<div id="cuerpo_principal" class="form-comment">	

     <fieldset style="align:center"  style="width: 977px">
		<table style="width: 100%" align="center">	
		<tr>
		<td width="900px" align="left"><img
			src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/logoSideli.jpg"
			width="900" ></td>		
	</tr>
		  <tr align="center">
		     <td style="background-color:#FFAE38; font-weight: bolder;"class="titulo_sistema">			
				 BIENVENIDO AL SISTEMA DE ATENCI&Oacute;N A DENUNCIAS DE TRABAJADORES POR IRREGULARIDADES EN SU INSCRIPCI&Oacute;N AL SEGURO SOCIAL
			</td>
		  </tr>		  
		   <tr>
			 <td align="left" style="width: 900px">	
			    <div id="filtros">
		          <jsp:include page="mensajeIntro.jsp" />
	            </div>
			</td>
		  </tr>	
		  <tr> <td> <br/></td> </tr>
		  <tr align="center"> <td>  <button type="button"  id="buttonSiguiente" class="mboton" onclick="irInicio();"> <span class="boton"> Continuar </span> </button></td> </tr>
		</table>								
	 </fieldset>
<form action="<%=request.getContextPath()%>/registro/opciones.do"  method="get" id="wlForm" name="wlForm" ></form>
</div>
