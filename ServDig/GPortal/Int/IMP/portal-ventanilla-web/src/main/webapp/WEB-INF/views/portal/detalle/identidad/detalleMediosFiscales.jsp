<%@ include file="../../../general/taglibs.jsp"%>

<fieldset>
	<legend>MEDIOS DE CONTACTO FISCALES</legend>
	<c:choose>
		<c:when test="${not empty mediosContactoFiscales }">
			<div>
				<c:forEach items="${mediosContactoFiscales}" var="medio" varStatus="indice">
					<address>
						<strong>${medio.tipoMedioContacto.descripcion}</strong>
						<br>
						${medio.desFormaContacto}
						<br>
					</address>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p>No cuenta con medios de contacto fiscales.</p>
		</c:otherwise>
	</c:choose>
</fieldset>