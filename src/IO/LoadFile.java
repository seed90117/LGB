package IO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import Values.Image;
import Values.Pixel;

public class LoadFile {

	private BufferedImage image = null;
	
	private static String WINDOWS = "D:\\Algorithm_Data\\Image Processing Image";
	private static String MAC = "//Users//kevin//Documents//OneDrive//Algorithm_Data//Image Processing Image";

	public String loadfile(JFileChooser open, JPanel show, boolean isMac)
	{
		String path =null;
		String loadPath = LoadFile.MAC;
		String fileName = "";
		if (isMac)
			loadPath = LoadFile.MAC;
		else
			loadPath = LoadFile.WINDOWS;
		
		//*****預設路徑*****//
		open.setCurrentDirectory(new File(loadPath));
		
		//*****設定Title*****//
		open.setDialogTitle("Choose dataset");
		
		//*****是否按下Load*****//
		if(open.showDialog(open, "Load") == JFileChooser.APPROVE_OPTION)
		{
			//*****取得路徑*****//
			File filepath = open.getSelectedFile();
			fileName = filepath.getName();
			
			//*****路徑轉為String*****//
			path = filepath.getPath().toString();
			
			//*****讀取檔案*****//
			try {
				this.image = ImageIO.read(new File(path));
				setImageInfomation();
				DrawPanel drawPanel = new DrawPanel();
				drawPanel.showImage(show, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fileName;
		} else {
			return "";
		}
	}
	
	private void setImageInfomation() {
		Image imageInfo = Image.getInstance();
		imageInfo.setImageInfo(this.image.getWidth(), this.image.getHeight());
		int width = imageInfo.getImageWidth();
		int height = imageInfo.getImageHeight();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				Pixel pixel = new Pixel();
				pixel.red = (this.image.getRGB(x, y) & 0xFF0000) >> 16;
				pixel.greed = (this.image.getRGB(x, y) & 0xFF00) >> 8;
				pixel.blue = (this.image.getRGB(x, y) & 0xFF);
				imageInfo.setPixel(x, y, pixel);
			}
		}
	}
}
