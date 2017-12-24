package IO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Values.Image;
import Values.Pixel;

public class DrawPanel {

	private Graphics g;
	
	public void showImage(JPanel show, BufferedImage image) {
		this.g = show.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image, 0, 0, null);
	}
	
	public void showImage(JPanel show, Pixel[] codebook) {
		Image image = Image.getInstance();
		this.g = show.getGraphics();
		int width = image.getImageWidth();
		int height = image.getImageHeight();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				g.setColor(new Color((int)codebook[image.getGroup(x, y)].red, 
									 (int)codebook[image.getGroup(x, y)].greed, 
									 (int)codebook[image.getGroup(x, y)].blue));
				g.fillRect(x, y, 1, 1);
			}
		}
	}
}
