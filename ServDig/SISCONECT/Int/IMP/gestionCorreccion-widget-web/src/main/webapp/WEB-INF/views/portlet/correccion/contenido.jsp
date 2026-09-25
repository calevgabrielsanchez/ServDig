<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="../../general/taglibs.jsp"%>

<div id="clasificacionWrapper" style="width: 100%; margin: 0 auto;">
	<table id="tblClasificacionDetalle" style="width: 100%;" class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>Raz&oacute;n Social</th>
				<th>Tr&aacute;mite</th>
				<th>Fecha de registro</th>
				<th>Ref Notaria</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:forEach items="${tramites}" var="item">
					<tr>
						<td>${item.desRazonSocial}</td>
						<td>${item.desMensaje}</td>
						<td>${item.fecFechaReg}</td>
						<td>${item.idTramiteRefNotaria}</td>
					</tr>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>