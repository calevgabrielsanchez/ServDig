<%@ include file="../../../general/taglibs.jsp"%>

<div class="row">
	<div class="col-sm-12">
		<ul class="wizard-steps">
			<li id="li1" class="completed">
				<h5>Paso 1</h5> 
				<span>Domicilio</span>
			</li>
			<li id="li2" class="${2<=param.paso?"completed":""}">
				<h5>Paso 2</h5> 
				<span>Cuestionario</span>
			</li>
			<li id="li2" class="${3<=param.paso?"completed":""}">
				<h5>Paso 3</h5> 
				<span>Familiares</span>
			</li>
			<li id="li2" class="${4<=param.paso?"completed":""}">
				<h5>Paso 4</h5> 
				<span>Confirmaci&oacute;n</span>
			</li>
			<li id="li2" class="${5<=param.paso?"completed":""}">
				<h5>Paso 5</h5> 
				<span>Tr&aacute;mite finalizado</span>
			</li>
			<li class="${5<=param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
		</ul>
	</div>
</div>