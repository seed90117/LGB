package Values;

public class Parameter {

	private double threshold;// 門檻值
	private int codebook;// 編碼簿
	private static Parameter instance = null;
	private Parameter(){}
	
	public static synchronized Parameter getInstance() {
		if (instance == null) {
			instance = new Parameter();
		}
		return instance;
	}
	
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public void setCodebook(int codebook) {
		this.codebook = codebook;
	}
	
	public double getThreshold() {
		return this.threshold;
	}
	
	public int getCodebook() {
		return this.codebook;
	}
}
