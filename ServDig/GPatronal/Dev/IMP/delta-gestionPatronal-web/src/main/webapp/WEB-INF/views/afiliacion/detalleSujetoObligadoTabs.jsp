<%@ include file="../general/taglibs.jsp"%>



<jsp:include page="estiloTabs.jsp" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/validateDeltaForm.js" htmlEscape="true" />"></script>

<div id="tabs_container">
      
      <!-- These are the tabs -->
      <ul id="tabs" class="tabs">
        <li id="tab_1_contents_" class="active"><a rel="#tab_1_contents" class="tab">Denominaci&oacute;n Raz&oacute;n Social</a></li>
        <li id="tab_2_contents_" ><a rel="#tab_2_contents" class="tab">Datos de Contacto</a></li>
        <li id="tab_3_contents_" ><a rel="#tab_3_contents" class="tab">Representante Legal</a></li>
        <c:if test="${!bFisica}">
	        <li id="tab_4_contents_" ><a rel="#tab_4_contents" class="tab">Socios</a></li>		
			<li id="tab_5_contents_" ><a rel="#tab_5_contents" class="tab">Acta Constitutiva</a></li>
			<li id="tab_6_contents_" ><a rel="#tab_6_contents" class="tab">Registro de Sindicato</a></li>
        </c:if>
      </ul>
      
      <!-- This is used so the contents don't appear to the 
           right of the tabs -->
      <div class="clear"></div>
      
      <!-- This is a div that hold all the tabbed contents -->
      <div class="tab_contents_container">
    
        <!-- Tab 1 Contents -->
        <div id="tab_1_contents" class="tab_contents tab_contents_active">
          <jsp:include page="datosGenerales.jsp" />
        </div>
    	<!-- Tab 2 Contents -->
        <div id="tab_2_contents" class="tab_contents">
          	<jsp:include page="contacto/datosContacto.jsp"></jsp:include>
        </div>
        <!-- Tab 3 Contents -->
        <div id="tab_3_contents" class="tab_contents">
          	<jsp:include page="representante_legal/fbRepLegal.jsp"></jsp:include>
        </div>
    <c:if test="${!bFisica}">
        <!-- Tab 3 Contents -->
        <div id="tab_4_contents" class="tab_contents">
        	<jsp:include page="socios/fbSocio.jsp"></jsp:include>			
        </div>
        
        <div id="tab_5_contents" class="tab_contents">			
        	<jsp:include page="escrituraConstitutiva.jsp" />
        </div>
        
        <div id="tab_6_contents" class="tab_contents">		
	    	<jsp:include page="registroSindicato.jsp" />
        </div>
    </c:if>
      </div>
 </div>
