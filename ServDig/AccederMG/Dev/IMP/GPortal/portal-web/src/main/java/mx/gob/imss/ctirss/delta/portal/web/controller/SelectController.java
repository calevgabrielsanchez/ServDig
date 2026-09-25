package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/combo")
public class SelectController extends AbstractController {

	@Autowired
	private ISelectService componentComboService;

	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public @ResponseBody List<SelectBean> getSelectOptions(@RequestParam String clazEntityName,
			@RequestParam boolean mostrarSoloActivos,
			@RequestParam(required = false) String campoVigencia,
			@RequestParam(required = false) boolean mostrarSoloEntidades) {

		List<SelectBean> selectOpts = null;
		
		try {
			if (mostrarSoloActivos
					&& mostrarSoloEntidades) {
				selectOpts = this.componentComboService.getActiveEntitiesOptions(
						clazEntityName,mostrarSoloEntidades);
			}else if (mostrarSoloActivos && StringUtils.isBlank(campoVigencia)) {
				selectOpts = this.componentComboService
						.getActiveOptions(clazEntityName);
			} else if (mostrarSoloActivos
					&& StringUtils.isNotBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, campoVigencia);
			} else {
				selectOpts = this.componentComboService
						.getOptions(clazEntityName);
			}
		} catch (Exception e) {
			this.log.error("Error al obtener el contenido del combo simple:", e);
		}

		return selectOpts;
	}

	@RequestMapping(value = "/dependiente", method = RequestMethod.GET)
	public @ResponseBody List<SelectBean> getSelectOptions(@RequestParam String clazEntityName,
			@RequestParam String entityParentName,
			@RequestParam String valueParent,
			@RequestParam boolean mostrarSoloActivos,
			@RequestParam(required = false) String campoVigencia) {

		List<SelectBean> selectOpts = null;
		
		try {
			if (mostrarSoloActivos && StringUtils.isBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, entityParentName, valueParent);
			} else if (mostrarSoloActivos
					&& StringUtils.isNotBlank(campoVigencia)) {
				selectOpts = this.componentComboService.getActiveOptions(
						clazEntityName, entityParentName, valueParent,
						campoVigencia);
			} else {
				selectOpts = this.componentComboService.getOptions(
						clazEntityName, entityParentName, valueParent);
			}
		} catch (Exception e) {
			this.log.error(
					"Error al obtener el contenido del combo dependiente:", e);
		}
		
		return selectOpts;
	}
}