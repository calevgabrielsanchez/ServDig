/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.registro;
/**
 * Copyright 2006 Bosco Curtu
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. 
 * 
 * See the License for the specific language governing permissions and limitations 
 * under the License.
*/


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.ctirss.framework.base.controller.AbstractController;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping(value = "/captchaController")
public class CaptchaController extends MultiActionController implements InitializingBean{
	@Autowired
	private ImageCaptchaService captchaService;
	

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@RequestMapping("/captcha.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        
    	// get the session id that will identify the generated captcha. 
    	//the same id must be used to validate the response, the session id is a good candidate!
    	String captchaId = request.getSession().getId();
    	
    	// call the ImageCaptchaService getChallenge method
       	BufferedImage challenge =
                captchaService.getImageChallengeForID(captchaId,request.getLocale());
        
        // a jpeg encoder
        JPEGImageEncoder jpegEncoder =
                JPEGCodec.createJPEGEncoder(jpegOutputStream);
        jpegEncoder.encode(challenge);
     

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
        	response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
	}

	
	
	
	
	/*protected	void validateCaptcha(HttpServletRequest request, BindException errors){

	boolean isResponseCorrect = false;
	String captchaId = request.getSession().getId();
	String response = request.getParameter(captchaResponseParameterName);

	try { 
	if(response != null){
	isResponseCorrect = captchaService.validateResponseForID(captchaId, response);
	}
	} catch (CaptchaServiceException e) {

	}
	if(!isResponseCorrect){
	String objectName = "Captcha";
	String[] codes = {"invalid"};
	Object[] arguments = {};
	String defaultMessage = "Invalid image test entered!";
	ObjectError oe = new ObjectError(objectName, codes, arguments, defaultMessage);
	errors.addError(oe);
	} 
	}*/
	
	/**
	 * Set captcha service
	 * @param captchaService The captchaService to set.
	 */
	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;		
	}	
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if(captchaService == null){
			throw new RuntimeException("Image captcha service wasn`t set!");
		}
	}
}
