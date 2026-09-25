<%@ include file="../../../general/taglibs.jsp"%>
<ul class="wizard-steps">
		<li class="${1<=param.paso?"completed":""}">
			<h5>
				Paso 1
			</h5> <span>Verificar domicilio</span>
		</li>
		<li class="${2<=param.paso?"completed":""}">
			<h5>
				Paso 2
			</h5> <span>Iniciar tr&aacute;mite</span>
		</li>
		<li class="${3<=param.paso?"completed":""}">
			<h5>
				Paso 3
			</h5> <span>Confirmar tus datos</span>
		</li>
		<li class="${4<=param.paso?"completed":""}">
			<h5>
				Paso 4
			</h5> <span>Recibir resultado</span>
		</li>
		<li class="${4==param.paso?"success":""}"><i class="glyphicon glyphicon-ok"></i></li>
	</ul>