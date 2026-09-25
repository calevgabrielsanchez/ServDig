<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html>


<div id="datosRatificacion" class="form-comment" align="left">
     <fieldset style="align:center"  style="width: 977px">
		<table style="width: 100%" align="center">	
          <tr align="center"><td align="center"><legend><strong>
									GRACIAS SU DENUNCIA FUE REGISTRADA EXITOSAMENTE</strong></leyend></td>
		  </tr>
		  <tr> <td> <br/></td> </tr>
		  <tr align="left">
			   <td colspan="2"><p style="text-align: justify;">
                                    <iframe id="izq"
                                        src="<%=request.getContextPath()%>/servlet/EnviaArchivoServlet"
                                        width="100%" height="600" scrolling="auto" frameborder="0"></iframe>
                                    
                            </p></td>
			<td align="left"></td>
		  </tr>
		  <tr> <td> <br/></td> </tr>
         
		   <tr> <td align="center"><button type="button"  id="buttonSiguiente" class="mboton"><span class="boton">Cerrar Ventana</span></button></td> </tr>
		</table>								
	 </fieldset>
</div>

