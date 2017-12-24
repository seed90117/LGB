package Program;

import Values.Pixel;

public class Formula {
	public Pixel getCenterPixel(double red, double greed, double blue, double count) {
		Pixel pixel = new Pixel();
		pixel.red = red/count;
		pixel.greed = greed/count;
		pixel.blue = blue/count;
		return pixel;
	}
	
	public double getColorDistance(Pixel center, Pixel pixel) {
		double red = (center.red - pixel.red) * (center.red - pixel.red);
		double greed = (center.greed - pixel.greed) * (center.greed - pixel.greed);
		double blue = (center.blue - pixel.blue) * (center.blue - pixel.blue);
		return Math.sqrt(red + greed + blue);
	}
	
	public double calculateMse(Pixel center, Pixel pixel) {
		double red = (center.red - pixel.red) * (center.red - pixel.red);
		double greed = (center.greed - pixel.greed) * (center.greed - pixel.greed);
		double blue = (center.blue - pixel.blue) * (center.blue - pixel.blue);
		return red + greed + blue;
	}
}
