package InterFace;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import IO.DrawPanel;
import IO.LoadFile;
import Program.MainMethod;
import Values.Parameter;

@SuppressWarnings("serial")
public class MainView extends JFrame {

	/**
	 * M10456012
	 * Kevin Yen
	 * kelly10056040@gmail.com
	 */
	private boolean isLoad= false;
	private String[] codebook = new String[]{"2","4","8","16","32","64","128","256","512"};
	private LoadFile loadFile;
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告物件*****//
	JLabel inputImageLabel = new JLabel("Original Image");
	JLabel outputImageLabel = new JLabel("Compression Image");
	JLabel codebookLabel = new JLabel("Codebook");
	JLabel thresholdLabel = new JLabel("Threshold");
	JLabel imageLabel = new JLabel("Image: ");
	JLabel imageNameLabel = new JLabel("");
	JLabel timeLabel = new JLabel("Running Time: ");
	
	JComboBox<String> codebookComboBox = new JComboBox<String>(this.codebook);
	JTextField thresholdTextField = new JTextField("0.0001");
	JCheckBox isMacCheckBox = new JCheckBox("Using Mac");
	JPanel inputImagePanel = new JPanel();
	JPanel outputImagePanel = new JPanel();
	JButton loadButton = new JButton("Load Image");
	JButton startButton = new JButton("Start");
	JFileChooser open = new JFileChooser();
	
	MainView()
	{
		//*****設定介面*****//
		this.setSize(1350, 600);
		this.setLayout(null);
		this.setTitle("LBG");
		
		//*****設定物件位置大小*****//
		inputImageLabel.setBounds(10, 10, 200, 30);
		inputImagePanel.setBounds(10, 40, 512, 512);
		outputImageLabel.setBounds(540, 10, 200, 30);
		outputImagePanel.setBounds(540, 40, 512, 512);
		codebookLabel.setBounds(1070, 40, 80, 30);
		codebookComboBox.setBounds(1160, 40, 100, 30);
		thresholdLabel.setBounds(1070, 80, 100, 30);
		thresholdTextField.setBounds(1160, 80, 100, 30);
		isMacCheckBox.setBounds(1070, 120, 100, 30);
		loadButton.setBounds(1070, 160, 180, 30);
		startButton.setBounds(1070, 200, 180, 30);
		imageLabel.setBounds(1070, 240, 100, 30);
		imageNameLabel.setBounds(1055, 270, 285, 30);
		timeLabel.setBounds(1070, 310, 200, 30);
		
		//*****設定Panel*****//
		inputImagePanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		outputImagePanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		//*****物件加入介面*****//
		cp.add(inputImageLabel);
		cp.add(inputImagePanel);
		cp.add(outputImageLabel);
		cp.add(outputImagePanel);
		cp.add(codebookLabel);
		cp.add(thresholdLabel);
		cp.add(codebookComboBox);
		cp.add(thresholdTextField);
		cp.add(isMacCheckBox);
		cp.add(loadButton);
		cp.add(startButton);
		cp.add(imageLabel);
		cp.add(imageNameLabel);
		cp.add(timeLabel);
		
		//*****設定Frame*****//
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//*****讀檔按鈕*****//
		loadButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!isLoad) {
					loadFile = new LoadFile();
				}
				imageNameLabel.setText(loadFile.loadfile(open, inputImagePanel, isMacCheckBox.isSelected()));
				if (!imageNameLabel.getText().equals("")) {
					isLoad = true;
				} else {
					isLoad = false;
				}
			}});
		
		//*****開始按鈕*****//
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				long startTime = 0;
				long endTime = 0;
				if (isLoad) {
					startTime = System.currentTimeMillis();
					if (setParameter()) {
						MainMethod mainMethod = new MainMethod();
						mainMethod.MainProgram();
						endTime = System.currentTimeMillis();
						timeLabel.setText("Running Time: " + getRunTime(startTime, endTime) + " s");
						DrawPanel drawPanel = new DrawPanel();
						drawPanel.showImage(outputImagePanel, mainMethod.getFinalCodebook());
						mainMethod.clearAll();
					}
				}
			}});
		
	}
	
	public static void main(String[] args) {
		new MainView();
	}
	
	private boolean setParameter() {
		try {
			Parameter parameter = Parameter.getInstance();
			parameter.setThreshold(Double.parseDouble(thresholdTextField.getText()));
			parameter.setCodebook(Integer.parseInt(codebook[codebookComboBox.getSelectedIndex()]));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private double getRunTime(long start, long end) {
		double startTime = start;
		double endTime = end;
		return (endTime - startTime)/1000;
	}
}
