<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/navegadorUtils.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/delta/denuncia/registro.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<div id="cuerpo_principal" class="form-comment">    

     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">
        	<tr>
				<td width="900px" align="left"><img
				src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/logoSideli.jpg"
				width="750" ></td>		
			</tr>  
          <tr align="center">
             <td style="background-color:#137B62;"class="titulo_sistema">  
             <h2 style="background-color:#137B62;color:white">           
                 BIENVENIDO AL SISTEMA DE ATENCI&Oacute;N A DENUNCIAS DE TRABAJADORES POR IRREGULARIDADES EN SU INSCRIPCI&Oacute;N AL SEGURO SOCIAL
            </h2>
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
