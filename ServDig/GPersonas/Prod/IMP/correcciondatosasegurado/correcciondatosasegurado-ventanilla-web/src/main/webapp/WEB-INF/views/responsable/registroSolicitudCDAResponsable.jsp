
<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/responsable/registroSolicitudCDAResponsable.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="rutaController" value="${contextpath}/wizard/correccionDatosAsegurado/registroSolicitudCDAResponsable"/>
<c:set var="urlDomicilio" value="${contextpath}/wizard/correccionDatosAsegurado/capturarDomicilio"></c:set>
<c:set var="paginaAnterior" value="${contextpath}/atencionResponsable"></c:set>

<script type="text/javascript">
	var contextpath="${contextpath}";
</script>
<div id="info-paso" style="margin-bottom: 50px;">

     <div class="contenedor">
     
     <div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
	<c:if test="${not empty fisica.errorFormGeneral}">
		<div class="alert alert-danger">
			${fisica.errorFormGeneral}
		</div>
	</c:if>
        <h3>
            <spring:message code="label.registro.asegurado.por.responsable" />
        </h3>
        <hr class="red" style="margin-bottom: 20px;"/>

        
        <form:form modelAttribute="fisica" id="registroAseguradoDomicilioForm" action="${rutaController}" cssClass="form-horizontal" accept-charset="ISO-8859-1">
        	<form:errors cssClass="alert alert-danger" element="div"/>
            <div>
                <div class="form-group">
                    <label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label" style="text-align: left;">
                        <spring:message code="label.solicitud.curp"/><span class="required">*</span>
                    </label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <spring:message code="label.placeholder.asegurado.curp" var="placeHolderCURP"/>
                        <form:input path="curp" id="registroCurp" cssClass="form-control" maxlength="18" placeholder="${placeHolderCURP}"/>
						<span id="curpError" class="error hiddenElement"></span>
                        <form:errors id="curpError" path="curp" cssClass="error" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
                    </label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <spring:message code="label.consultaCurp"/>
                    </div>
                </div>
                
                <br/>
                <h4><spring:message code="label.seccion.datosBasicos"/></h4>
                <div class="form-group">
                    <label for="nombre" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.nombre"/><span class="required">*</span>
                    </label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <spring:message code="label.placeholder.asegurado.nombre" var="placeHolderNombre"/>
                        <form:input path="nombre" id="nombreInput" maxlength="100" cssClass="form-control" placeholder="${placeHolderNombre}"/>
                        <span id="nombreError" class="error hiddenElement"></span>
                        <form:errors path="nombre" cssClass="error" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="primerApellido" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.primer.apellido"/><span class="required">*</span></label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <spring:message code="label.placeholder.asegurado.primerApellido" var="placeHolderPrimerApellido"/>
                        <form:input path="primerApellido" id="primerApellidoInput" maxlength="100" cssClass="form-control" placeholder="${placeHolderPrimerApellido}"/>
                        <span id="primerApellidoError" class="error hiddenElement"></span>
                        <form:errors path="primerApellido" cssClass="error" />
                    </div>              
                </div>
                <div class="form-group">
                    <label for="segundoApellido" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.segundo.apellido"/></label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <spring:message code="label.placeholder.asegurado.segundoApellido" var="placeHolderSegundoApellido"/>
                        <form:input path="segundoApellido" id="segundoApellidoInput" maxlength="100" cssClass="form-control" placeholder="${placeHolderSegundoApellido}"/>
                        <span id="segundoApellidoError" class="error hiddenElement"></span>
                        <form:errors path="segundoApellido" cssClass="error" />
                    </div>              
                </div>
                <div class="form-group datepicker-group has-feedback">
                    <label for="fechaNacimiento" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.fecha.nacimiento"/><span class="required">*</span></label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                            <spring:message code="label.placeholder.asegurado.fechaNacimiento" var="placeHolderFechaNacimiento"/>
                            <form:input path="fechaNacimiento" id="fechaNacimiento" cssClass="form-control" placeholder="${placeHolderFechaNacimiento}"/>
                            <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                            <span id="fechaNacimientoError" class="error hiddenElement"></span>
                            <form:errors path="fechaNacimiento" cssClass="error" />
                    </div>          
                </div>
                <div class="form-group">
                    <label for="lugarNacimiento" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.lugar.nacimiento"/><span class="required">*</span></label>
                    <div class="col-md-4 col-sm-7 col-xs-12">
                        <combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="registroAseguradoDomicilioForm"
                                entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" cssClassname="form-control" mostrarSoloActivos = "true"/>
                         <span id="lugarNacimiento.claveError" class="error hiddenElement"></span>
                         <form:errors path="lugarNacimiento.clave" cssClass="error" campoRelacionado="lugarNacimiento.clave"/>
                    </div>          
                </div>

                <div class="form-group">
                    <label for="sexo.idSexo" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
                        <spring:message code="label.sexo"/><span class="required">*</span></label>
                    <div class="radio col-md-4 col-sm-7 col-xs-12">
                    	<div>
	                        <label>
	                            <input type="radio" name="sexo.idSexo" value="2" /> <spring:message code="label.sexo.femenino" />
	                        </label>
	                        <label>
	                            <input type="radio" name="sexo.idSexo" value="1"/> <spring:message code="label.sexo.masculino" />
	                        </label>
                        </div>
                        <span id="sexo.idSexoError" class="error hiddenElement"></span>
                        <form:errors path="sexo.idSexo" cssClass="error" />
                    </div>
                </div>
            </div>
            
            <div>
            	<input type="hidden" id="strTipoTramite" name="tramite" value="${tramite}"/>
            </div>
                
            <div class="row">
                <div class="col-sm-3 text-left" style="padding: 10px;">
                    <span id="labelCamposObligatoriosGeneral" class="required">*</span> <spring:message code="label.solicitud.camposObligatorios"/>
                </div>
                <div class="col-sm-5 text-right">
                	<%@ include file="../solicitud/regresar.jsp"%>
                    <button type="button" id="continuar" class="btn btn-primary"><spring:message code="label.continuar"/></button>
                </div>
            </div>  
    
        </form:form>
    </div>
</div>