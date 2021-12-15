import java.io.*;
import java.io.File;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

/** FilePanel class: deals with file saving and uploading functionalities to allow the user to save their creation, or upload a previous project to make changes
*/
public class FilePanel{
	// max dimensions of the file that is written out 
	private final int MAXWIDTH = 8;
	private final int MAXHEIGHT = 8; 
	// allows the user to select a file
	private JFileChooser fileChooser;
	// intermediary step from converting the image seen on the screen to the outputte file
  private BufferedImage inputImg;
	// the 2d array of buttons from the canvas class
	private JButton[][] canvas = new JButton[MAXWIDTH][MAXHEIGHT];
	// panel that contains the fileButtons
	private JPanel filePanel;
	// buttons that open and save files
	private JButton openFileButton, saveFileButton;
  
  /** FilePanel constructor
      - creates the buttons used to open and save a file
      @param screenWidth: number value of the width of the screen (integer)
      @param screenHeight: number value of the height of the screen (integer)
      @param canvas: the user's canvas (their creation!)
  */
  public FilePanel(int screenWidth, int screenHeight, Canvas canvas){
    filePanel = new JPanel(new GridLayout(1, 2)); //grid layout to ensure the elements of the panel (buttons) in this case are in line with each other
		openFileButton = new JButton("Open File");
		openFileButton.addActionListener(new ButtonHandler());
		saveFileButton = new JButton("Save File");
		saveFileButton.addActionListener(new ButtonHandler());
    //adds the buttons to the panel itself
		filePanel.add(openFileButton);
		filePanel.add(saveFileButton);
		this.canvas = canvas.getCanvasArray(); //information about the canvas buttons 
  }
	
  /** uploadFile method:
      - allows the user to upload their file, given that it is a PNG, open it, and display it on the grid
  */
	private void uploadFile(){
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "z"));
		int returnValue = fileChooser.showOpenDialog(openFileButton);
		if (returnValue == JFileChooser.APPROVE_OPTION){
			try {
				inputImg = ImageIO.read(fileChooser.getSelectedFile());
			} catch (IOException e){
			
			}
		}
	}

// to save the file, a new image is created which will be written out to using the Graphics2D class, allowing for drawing 2d shapes and writing it out to the file. how the content of the screen is written to a file is seen below
	private void saveFile(){
		BufferedImage bufferedImage = new BufferedImage(MAXWIDTH, MAXHEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		// two for loops go row by row, column by column, getting the value of the background color of the button, which is translated into a pixel in the object of the Graphics2D class which has a size of 1 by 1.
		for(int y = 0; y < MAXHEIGHT; y++){
  		for(int x = 0; x < MAXWIDTH; x++){
				g2d.setColor(canvas[x][y].getBackground());
				g2d.fillRect(x, y, 2, 2);
      }
		}
		g2d.dispose();
		// a new file is created that is used to write out the 2d graphics to a png file that the user can open and look at and share
    File userSaveFile = new File("myPixelArt.png");
		try {
			ImageIO.write(bufferedImage, "png", userSaveFile);
		} catch (IOException e){
		}
	}

/** convertFiletoPixel method
    - converts the inputted image's pixel to color values, then sets the background buttons corresponding to the image area that those pixels represent to that color
*/
	public void convertFiletoPixel(){
  	for(int y = 0; y < inputImg.getHeight(); y++){
  		for(int x = 0; x < inputImg.getWidth(); x++){
    		int pixel = inputImg.getRGB(x, y);
    		Color color = new Color(pixel);
				canvas[x][y].setBackground(color);
  	}
	}
}

/** returnPanel method
    @return filePanel: of type JPanel
*/
	public JPanel returnPanel(){
		return filePanel;
	}

/** ButtonHandler: applies action listener to apply functionalities to the buttons of filePanel
*/
	private class ButtonHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
			JButton selectedButton = (JButton) e.getSource();
      //if the openFileButton is selected, the uploadFile and convertFiletoPixel methods are called to upload the file and convert it into our pixel format
			if (selectedButton == openFileButton){ 
          uploadFile();
					convertFiletoPixel();
			}
			
      //if the saveFileButton is selected, the button is saved
			if (selectedButton == saveFileButton){
				saveFile();
			}
		}
	}
}