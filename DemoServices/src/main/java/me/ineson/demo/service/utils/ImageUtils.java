/**
 * 
 */
package me.ineson.demo.service.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author peter
 *
 */
public class ImageUtils {

  public static final Dimension getDimentions( byte image[]) {
	try {
	    BufferedImage bufferedImage = ImageIO.read( new ByteArrayInputStream( image));
		  int width          = bufferedImage.getWidth();
		  int height         = bufferedImage.getHeight();
		  return new Dimension( width, height);
	} catch (IOException e) {
		throw new IllegalStateException( e);
	}
  }
}
