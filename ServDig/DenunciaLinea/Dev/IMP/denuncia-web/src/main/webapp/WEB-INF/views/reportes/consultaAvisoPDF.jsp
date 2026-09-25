<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	
	
	<tr>
		<td><form action="/sincronizacion" id="acuseS" method="post" name="acuseS"
				enctype="multipart/form-data">
				<input type="hidden" name="dispatch" /> <input type="hidden"
					name="documento" value="<%=request.getAttribute("documento")%>" />
				<input type="hidden" name="nombreArchivo"
					value="<%=request.getAttribute("nombreArchivo")%>" />
				<table class="tabla" width="100%" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<tbody align="center">
						<tr class="impar">
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr class="impar">
							
							<td colspan="2"><p style="text-align: justify;">
 									<iframe id="der" 
										src="<%=request.getContextPath()%>/reporte/EnviaServletDer.do" 
										width="100%" height="600" scrolling="auto" frameborder="0"></iframe> 
									
 							</p></td> 
						</tr>
						</p>
						</td>
						</tr>

					</tbody>
				</table>
				
			</form>
		</td>
	</tr>
</table>

