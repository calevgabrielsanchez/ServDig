package mx.gob.imss.cit.dacvass.utils;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;

public class CaptchaUtilSD {

    private static final int WIDTH = 220;
    private static final int HEIGHT = 40;
    private static final int CIRCLES_TO_DRAW = 5;
    private static final int CHARS_TO_PRINT = 7;
    private static final ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
    private static final Logger logger = Logger.getLogger(CaptchaUtilSD.class);
    private static final String CAPTCHA_SESION = "captcha";

    public static boolean isValidCaptcha(String captchaId, String captchaValue) {

        boolean validate = false;

        try {
            validate = imageCaptchaService.validateResponseForID(captchaId, captchaValue);
        } catch (CaptchaServiceException e) {
            logger.error("Ocurrio un error al validar el captcha recibido {} "+ captchaValue, e);
        }

        return validate;
    }

    
    public static CaptchaSD generateCaptchaImage(String sessionId) throws CaptchaExceptionSD {

    	try {
        CaptchaSD cap = new CaptchaSD();

        logger.debug("id de session {}" + sessionId);
        // Stream de salida para la imagen del captcha
        final ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        // Guarda el identificador de sesión del usuario para validar el captcha
        cap.setCaptchaId(sessionId);

        // Creación de la imagen de captchacall the ImageCaptchaService
        // getChallenge method
        final BufferedImage challenge = imageCaptchaService.getImageChallengeForID(cap.getCaptchaId(), new Locale("es", "MX"));

        // Codificamos la imagen en JPEG
        ImageIO.write(challenge, "png", jpegOutputStream);

        // Guardamos la imagen como un flujo de bytes para pintarla en pantalla
        cap.setCaptchaImage(jpegOutputStream.toByteArray());

        return cap;
    	}catch (Exception e) {
			logger.error("ocurrio un error al generar el captcha", e);
			throw new CaptchaExceptionSD(e);
		}
    }

    public static CaptchaSD generaCaptchaToSesion(CaptchaSD captcha) throws CaptchaExceptionSD {
    	// logger.debug("llegando a generarl el captcha");
        StringBuilder finalString = new StringBuilder();
        if(captcha.getResponse() == null)
        	  throw new RuntimeException("El response es null");
        try {

            Color textColor = Color.BLACK;
            Color circleColor = Color.white;

            float horizMargin = 40.0f;
            float imageQuality = 1.0f;
            double rotationRange = 0.55;
            BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

            g.setColor(Color.orange);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            Font textFont = new Font("Arial", Font.PLAIN, 24); // getFont();
            g.setColor(circleColor);
            g.setStroke(new BasicStroke(3));
            for (int i = 0; i < CIRCLES_TO_DRAW; i++) {
                int circleRadius = (int) (Math.random() * HEIGHT / 2.0);
                int circleX = (int) (Math.random() * WIDTH - circleRadius);
                int circleY = (int) (Math.random() * HEIGHT - circleRadius);
                g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
            }
            g.setColor(textColor);
            g.setFont(textFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            int maxAdvance = fontMetrics.getMaxAdvance();
            int fontHeight = fontMetrics.getHeight();
            String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
            char[] chars = elegibleChars.toCharArray();
            float spaceForLetters = -horizMargin * 2 + WIDTH;
            float spacePerChar = spaceForLetters / (CHARS_TO_PRINT - 1.0f);

            for (int i = 0; i < CHARS_TO_PRINT; i++) {
                double randomValue = Math.random();
                int randomIndex = (int) Math.round(randomValue
                        * (chars.length - 1));
                char characterToShow = chars[randomIndex];
                finalString.append(characterToShow);
                int charWidth = fontMetrics.charWidth(characterToShow);
                int charDim = Math.max(maxAdvance, fontHeight);
                int halfCharDim = (int) (charDim / 2);
                BufferedImage charImage = new BufferedImage(charDim, charDim,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D charGraphics = charImage.createGraphics();
                charGraphics.translate(halfCharDim, halfCharDim);
                double angle = (Math.random() - 0.2) * rotationRange;
                charGraphics
                        .transform(AffineTransform.getRotateInstance(angle));
                charGraphics.translate(-halfCharDim, -halfCharDim);
                charGraphics.setColor(textColor);
                charGraphics.setFont(textFont);
                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
                charGraphics
                        .drawString(
                                "" + characterToShow,
                                charX,
                                (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics
                                        .getAscent()));
                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
                int y = (int) ((HEIGHT - charDim) / 2);

                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
                charGraphics.dispose();
            }

            Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
            ImageIO.setUseCache(false);

            if (iter.hasNext()) {
                ImageWriter writer = (ImageWriter) iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(imageQuality);
                writer.setOutput(ImageIO.createImageOutputStream(captcha.getResponse()
                        .getOutputStream()));
                IIOImage imageIO = new IIOImage(bufferedImage, null, null);
                writer.write(null, imageIO, iwp);
            }
            g.dispose();
            captcha.setCaptchaValue(finalString.toString());
            captcha.getRequest().getSession().setAttribute(CAPTCHA_SESION, finalString.toString());
            return captcha;
        } catch (IOException ioe) {
        	logger.error("error de IO al crear la imagen del captcha" , ioe);
            throw new CaptchaExceptionSD("No pude construir la imagen:"
                    + ioe.getMessage(), ioe);
        }catch(Exception e) {
        	logger.error("error al crear la imagen del captcha" , e);
            throw new CaptchaExceptionSD("No pude construir la imagen:"
                    + e.getMessage(), 	e);
        }
     
    }
    
    public static boolean validaCaptachaSesion(CaptchaSD captcha) throws CaptchaExceptionSD  {
    	// logger.debug("llegando a validar  el captcha");
    	boolean isCaptchaValido = false;
    	if(captcha == null )
       	  throw new CaptchaExceptionSD("El captcha no puede ser nulo");
    	if(captcha.getRequest() == null || captcha.getRequest().getSession() == null)
    		throw new CaptchaExceptionSD("EL request o la sesion son nulos");
    	String captchaSesion = (String)captcha.getRequest().getSession().getAttribute(CAPTCHA_SESION);
    	captcha.getRequest().getSession().removeAttribute(CAPTCHA_SESION);
    	if(StringUtils.isEmpty(captchaSesion)) {
    		throw new CaptchaExceptionSD("Este campo es obligatorio.");
    	}
    	if(StringUtils.isEmpty(captcha.getCaptchaValue())) {
    		throw new CaptchaExceptionSD("Este campo es obligatorio.");
    	}
    	if(captchaSesion.equals(captcha.getCaptchaValue()))
    		isCaptchaValido = true;
    	else {
    		throw new CaptchaExceptionSD("La informaci\u00F3n del captcha no coincide, favor de intentar nuevamente.");
    	}
    	return isCaptchaValido;
    	
    }
    
    
    
}
