<%@ include file="../general/taglibs.jsp"%>

<div class="row"  style="margin-top:20px">
	<div class="col-sm-12">
		<c:set var="etiqueta" value="${param.tipoTramite != 48 ? 'Paso ':''}"></c:set>
		<ul class="${param.tipoTramite != 48 ? "wizard-steps" : "wizard-steps-extensive"}">
			<li id="li1" class="${1<=param.paso?"completed":""}">
				<h5>${etiqueta}1</h5> 
				<span>Iniciar</span>
			</li>
			<c:if test="${param.tipoTramite == 48 }">
				<li id="li2" class="${2<=param.paso?"completed":""}">
					<h5>${etiqueta}2</h5> 
					<span>Datos beneficiario</span>
				</li>
			</c:if>
			
			<li id="li${param.tipoTramite == 48 ? 3: 2 }" class="${(param.tipoTramite == 48 ? 3: 2 )<=param.paso?"completed":""}">
				<h5>${etiqueta}${param.tipoTramite == 48 ? 3: 2 }</h5> 
				<span>Capturar direcci&oacute;n</span>
			</li>
			<li id="li${param.tipoTramite == 48 ? 4: 3 }" class="${(param.tipoTramite == 48 ? 4: 3 )<=param.paso?"completed":""}">
				<h5>${etiqueta}${param.tipoTramite == 48 ? 4: 3 }</h5> 
				<span>Seleccionar cl&iacute;nica</span>
			</li>
			<li id="li${param.tipoTramite == 48 ? 5: 4 }" class="${(param.tipoTramite == 48 ? 5: 4)<=param.paso?"completed":""}">
				<h5>${etiqueta}${param.tipoTramite == 48 ? 5: 4 }</h5> 
				<span>Tr&aacute;mite finalizado</span> 
			</li>
			<li class="${4<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
		</ul>
	</div>
</div>