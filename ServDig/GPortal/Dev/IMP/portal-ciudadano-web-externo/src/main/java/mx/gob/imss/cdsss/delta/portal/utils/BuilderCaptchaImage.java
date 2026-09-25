package mx.gob.imss.cdsss.delta.portal.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;

public class BuilderCaptchaImage {
	
	int width = 220;
	int height = 40;
	int circlesToDraw = 5;
	int charsToPrint = 7;
	
	ImageWriter writer = null;
	StringBuffer finalString = new StringBuffer();
		
	public String getImage(HttpServletResponse response) {
		try {
			
			Color textColor = Color.BLACK;
			Color circleColor = Color.white;
			
			float horizMargin = 20.0f;
			float imageQuality = 1.0f;
			double rotationRange = 0.55;
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
			
			g.setColor(Color.orange);
			g.fillRect(0, 0, width, height);
			Font textFont = new Font("Arial", Font.PLAIN, 24); //getFont();
			g.setColor(circleColor);
			for ( int i = 0; i < circlesToDraw; i++ ) {
				int circleRadius = (int) (Math.random() * height / 2.0);
				int circleX = (int) (Math.random() * width - circleRadius);
				int circleY = (int) (Math.random() * height - circleRadius);
				g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
			}
			g.setColor(textColor);
			g.setFont(textFont);
			FontMetrics fontMetrics = g.getFontMetrics();
			int maxAdvance = fontMetrics.getMaxAdvance();
			int fontHeight = fontMetrics.getHeight();
			String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
			char[] chars = elegibleChars.toCharArray();
			float spaceForLetters = -horizMargin * 2 + width;
			float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);

			for ( int i = 0; i < charsToPrint; i++ ) {
				double randomValue = Math.random();
				int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
				char characterToShow = chars[randomIndex];
				finalString.append(characterToShow);
				int charWidth = fontMetrics.charWidth(characterToShow);
				int charDim = Math.max(maxAdvance, fontHeight);
				int halfCharDim = (int) (charDim / 2);
				BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
				Graphics2D charGraphics = charImage.createGraphics();
				charGraphics.translate(halfCharDim, halfCharDim);
				double angle = (Math.random() - 0.5) * rotationRange;
				charGraphics.transform(AffineTransform.getRotateInstance(angle));
				charGraphics.translate(-halfCharDim,-halfCharDim);
				charGraphics.setColor(textColor);
				charGraphics.setFont(textFont);
				int charX = (int) (0.5 * charDim - 0.5 * charWidth);
				charGraphics.drawString("" + characterToShow, charX, 
						(int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));
				float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
				int y = (int) ((height - charDim) / 2);
				
				g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
				charGraphics.dispose();
			}
			
			Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
			if( iter.hasNext() ) {
				ImageWriter writer = (ImageWriter)iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(imageQuality);
				writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
				IIOImage imageIO = new IIOImage(bufferedImage, null, null);
				writer.write(null, imageIO, iwp);
			} 
			g.dispose();
		} catch (IOException ioe) {
			throw new RuntimeException("No pude construir la imagen" , ioe);
		}
		return finalString.toString();
	}
}
