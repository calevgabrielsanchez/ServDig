<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="../../../general/taglibs.jsp"%>

<div class="encabezado"></div>
<div class="cuerpo">

	<div class="titulo">
		<span><spring:message
				code="label.widget.titulo.datos.fiscales" /> </span>
	</div>

	<div class="descripcion">
		<p>
			<spring:message code="label.widget.descripcion.medios.fiscales" />
		</p>
	</div>

	<div class="contenido">
		<c:choose>
			<c:when test="${not empty mediosFiscales }">
				<c:forEach items="${mediosFiscales}" var="medio" varStatus="indice">
					<address>
						<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
						${medio.desFormaContacto}<br>
					</address>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>No cuenta con medios de contacto fiscales.</p>
			</c:otherwise>
		</c:choose>
	</div>
	
	<br>
	<div class="descripcion">
		<p>
			<spring:message code="label.widget.descripcion.domicilios.fiscales" />
		</p>
	</div>

	<div class="contenido">
		<c:choose>
			<c:when test="${not empty domFiscal }">
				<input type="hidden" id="idDomiclioFiscalWidget" value="${domFiscal.clave}"/>
				<address>
					<c:if test="${not empty domFiscal.calle}">
						${domFiscal.calle}
					</c:if>
					<c:if test="${not empty domFiscal.numExteriorAlf || not empty domFiscal.numExterior1}"> 
						<br> ${domFiscal.numExteriorAlf} ${domFiscal.numExterior1},
					</c:if>
					<c:if test="${not empty domFiscal.numInteriorAlf || not empty domFiscal.numInterior}"> 
						${domFiscal.numInteriorAlf} ${domFiscal.numInterior}
					</c:if>
					<c:if test="${not empty domFiscal.colonia}">
						<br>${domFiscal.colonia}
					</c:if>
					<c:if test="${not empty domFiscal.asentamiento.localidad.municipio.nombre}">
						<br>${domFiscal.asentamiento.localidad.municipio.nombre}
					</c:if>
					<c:if test="${not empty domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}">
						<br>${domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
					</c:if>
					<c:if test="${not empty domFiscal.asentamiento.codigoPostal.codigoPostal}">
						C.P. ${domFiscal.asentamiento.codigoPostal.codigoPostal}
					</c:if>
				</address>
			</c:when>
			<c:otherwise>
				<p>No cuenta con domicilio fiscal.</p>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="estado"></div>

<div class="pie">
	<div class="opciones">
		<!-- 
		<div class="btn-group">
			<a class="btn btn-primary" href="#"><spring:message code="label.menus.opciones" /> </a> <a
				class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
				href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="#">Editar</a></li>
				
			</ul>
		</div>
		 -->
	</div>

	<div class="controles">
        <a class="btn btn-sm widget-tool widget-refresh"><i class="icon-refresh"></i></a>
		<a class="btn btn-sm widget-tool widget-resize"><i
			class="icon-resize-small"></i></a>
		<a class="btn btn-sm widget-tool widget-move"><i class="icon-move handle"></i></a>
	</div>
</div>
