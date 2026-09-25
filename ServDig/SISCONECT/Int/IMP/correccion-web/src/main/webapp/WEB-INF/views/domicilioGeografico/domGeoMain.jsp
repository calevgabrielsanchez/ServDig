<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/domiciliosInegi/domiciliosInegi.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Domicilios Geograficos</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgDomInegi"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogDomInegi" style="background-color: #f2fff2;">	
			<form:form modelAttribute="dgDomicilioInegiAux" action="/domiciliosGeograficos/consultar.do"
						method="post" id="domiciliosInegiForm">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
	
	
							</td>
						</tr>
					</table>
										
				</fieldset>
			</form:form>							    
	  	</div>
	</div>
			
			
			<div id="registroDomInegi">
				<jsp:include page="domGeoData.jsp" />
			</div>
			
			<div id="registroDomInegi">
				<jsp:include page="domGeoRegistro.jsp" />
			</div>
			
			
			<div id="domInegiButtons">
				<jsp:include page="domGeoButtons.jsp" />
			</div>
			
			
			<br>	    	    
</div>	    
	
