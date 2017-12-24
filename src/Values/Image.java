package Values;

public class Image {

	private int height;
	private int width;
	private Pixel[][] pixels;
	private int[][] groups;
	
	private static Image instance = null;
	private Image(){};
	
	public static synchronized Image getInstance() {
		if (instance == null) {
			instance = new Image();
		}
		return instance;
	}
	
	public int getImageHeight() {
		return this.height;
	}
	
	public int getImageWidth() {
		return this.width;
	}
	
	public void setImageInfo(int width, int height) {
		this.height = height;
		this.width = width;
		this.pixels = new Pixel[width][height];
		this.groups = new int[width][height];
	}
	
	public void setPixel(int pointX, int pointY, Pixel pixel) {
		this.pixels[pointX][pointY] = pixel;
	}
	
	public void setPoxel(int pointX, int pointY, int red, int greed, int blue) {
		Pixel pixel = new Pixel();
		pixel.red = red;
		pixel.greed = greed;
		pixel.blue = blue;
		this.pixels[pointX][pointY] = pixel;
	}
	
	public Pixel getPixel(int pointX, int pointY) {
		return this.pixels[pointX][pointY];
	}
	
	public double getPixelRed(int pointX, int pointY) {
		if (this.pixels[pointX][pointY] != null) { 
			return this.pixels[pointX][pointY].red;
		} else {
			return -1;
		}
	}
	
	public double getPixelGreed(int pointX, int pointY) {
		if (this.pixels[pointX][pointY] != null) { 
			return this.pixels[pointX][pointY].greed;
		} else {
			return -1;
		}
	}
	
	public double getPixelBlue(int pointX, int pointY) {
		if (this.pixels[pointX][pointY] != null) { 
			return this.pixels[pointX][pointY].blue;
		} else {
			return -1;
		}
	}
	
	public void setGroup(int pointX, int pointY, int group) {
		this.groups[pointX][pointY] = group;
	}
	
	public int getGroup(int pointX, int pointY) {
		return this.groups[pointX][pointY];
	}
	
}
