package Program;

import java.util.Random;

import Values.Image;
import Values.Parameter;
import Values.Pixel;

public class MainMethod {
	
	private Image image = null;
	private Parameter parameter = null;
	private Pixel[] codebook = null;

	public void MainProgram() {
		// 設定參數
		setParameter();
		// 尋找群星點
		initialCenterPoint();
		double lastMse = 0;
		double newMse = grouping();
		// 終止條件
		while (!isTermination(lastMse, newMse)) {
			// 更新MSE
			lastMse = newMse;
			// 尋找群星點
			findCenterPoint();
			// 分群
			newMse = grouping();
		}
	}
	
	private void setParameter() {
		this.image = Image.getInstance();
		this.parameter = Parameter.getInstance();
	}
	
	private boolean isTermination(double lastMse, double newMse) {
		double mse = Math.abs(newMse - lastMse);
		if (mse <= parameter.getThreshold()) {
			return true;
		} else {
			return false;
		}
	}

	private void initialCenterPoint() {
		int codebookSize = this.parameter.getCodebook();
		int width = this.image.getImageWidth();
		int height = this.image.getImageHeight();
		this.codebook = new Pixel[codebookSize];
		Random random = new Random();
		int[][] check = new int[codebookSize][2];
		for (int i=0; i<codebookSize; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			while (isRepeat(check, i, randomWidth, randomHeight)) {
				randomWidth = random.nextInt(width);
				randomHeight = random.nextInt(height);
			}
			check[i][0] = randomWidth;
			check[i][1] = randomHeight;
			this.codebook[i] = this.image.getPixel(randomWidth,randomHeight);
		}
	}
	
	private void findCenterPoint() {
		int codebookSize = this.parameter.getCodebook();
		int width = this.image.getImageWidth();
		int height = this.image.getImageHeight();
		Pixel[] valueRGB = new Pixel[codebookSize];
		int[] count = new int[codebookSize];
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				int group = this.image.getGroup(x, y);
				if (valueRGB[group] == null) {
					valueRGB[group] = new Pixel();
				}
				valueRGB[group].red += this.image.getPixelRed(x, y);
				valueRGB[group].greed += this.image.getPixelGreed(x, y);
				valueRGB[group].blue += this.image.getPixelBlue(x, y);
				count[group]++;
			}
		}
		Formula formula = new Formula();
		for (int i=0; i<codebookSize; i++) {
			this.codebook[i] = formula.getCenterPixel(valueRGB[i].red, 
					  valueRGB[i].greed, 
					  valueRGB[i].blue, 
					  count[i]);
		}
	}

	private double grouping() {
		Formula formula = new Formula();
		double mse = 0;
		int codebookSize = this.parameter.getCodebook();
		int width = this.image.getImageWidth();
		int height = this.image.getImageHeight();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				int selectCodebook = compareCenter(x, y);
				this.image.setGroup(x, y, selectCodebook);
				// 計算MSE
				mse += formula.calculateMse(this.codebook[selectCodebook], this.image.getPixel(x, y));
			}
		}
		return mse/codebookSize;
	}
	
	private int compareCenter(int x, int y) {
		Formula formula = new Formula();
		int codebookSize = this.parameter.getCodebook();
		double minDistance = -1;
		int selectCodebook = 0;
		Pixel pixel = this.image.getPixel(x, y);
		// 與所有群星點比較
		for (int i=0; i<codebookSize; i++) {
			double colorDistance = formula.getColorDistance(this.codebook[i], pixel);
			if (minDistance == -1 || minDistance > colorDistance) {
				minDistance = colorDistance;
				selectCodebook = i;
			}
		}
		return selectCodebook;
	}

	public void clearAll() {
		this.codebook = null;
		this.image = null;
		this.parameter = null;
	}
	
	public Pixel[] getFinalCodebook() {
		return this.codebook;
	}

	private boolean isRepeat(int[][] check, int max, int width, int height) {
		boolean isRepeat = false;
		for (int i=0; i<max; i++) {
			if (this.image.getPixelRed(check[i][0], check[i][1]) == this.image.getPixelRed(width, height) &&
				this.image.getPixelGreed(check[i][0], check[i][1]) == this.image.getPixelGreed(width, height) &&
				this.image.getPixelBlue(check[i][0], check[i][1]) == this.image.getPixelBlue(width, height)) {
				isRepeat = true;
			}
		}
		return isRepeat;
	}
}
