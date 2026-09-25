<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/autenticacionTramites.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/authenticate.js"></script>

 <c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="col-sm-12">
	<div class="row">
		<div class="col-md-6 col-sm-12">
			<div class="separadorseccion">
				<h2>Acceso a trámites digitales</h2>
			</div>
		</div>
		<div class="col-md-6  col-sm-12">
			<div class="pull-right" style="padding: 20px 10px">
				<img alt="" src="${staticResourcesPath}/imagenes/logoescri.png" width="120px" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 m-b-lg">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div style="margin-bottom: 10px" id="login">
								<label class="">Usuario: </label> 
								<input id="usuario" name="usuario"  type="text"  value="SAEM860110HDFNSR01"/>
						
					</div>
					<div style="margin-bottom: 10px" id="login">
								<label class="">Contraseña: </label> 
								<input id="pass" name="pass"  type="password" value="10000000000100000004"/>
						
					</div>
								
				</div>
			</div>
		</div>		
	</div>
</div>


			<form:form action="${contextpath}/menutramites/ingresar" method="get"
							id="formlogin">	
							<button type="button" id="enviarForm"
								class="btn btn-primary btn-block" style="width:450px" onclick="javascript:auntenticaLogin()">
								<spring:message code="label.ingresar" />
							</button>
						</form:form>



