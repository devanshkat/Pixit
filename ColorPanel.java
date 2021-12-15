import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

/** ColorPanel class deals with the creation of default colors that user can select for their painting, as well as creating a custom color to paint with 
*/
public class ColorPanel {
	// width and height of the frame, which are used to set the sizes of the colorPanel, colorPickerPanel, and colorEnterPanel
	private int width;
	private int height;
	// colorPanel contains colorPickerPanel and colorEnterPanel. colorPickerPanel has the default colors and colorEnterPanel has the components to select a custom color
	private JPanel colorPanel, colorPickerPanel, colorEnterPanel;
	// instance of the tool class that allows the canvas class to what to do when one of its canvas buttons is selected, such as brush, which will change the color of an indivdual button, or bucket, which will change the all of the surrounding buttons that are the same color, or eraser, which sets the button's color as white
	private Tool tool;
	// array of all the default colors
	private final Color[] colorList = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta, Color.pink, Color.white, Color.black};
	// array of all the buttons that will have background color based on the default color above
	private JButton[] buttonList = new JButton[9];
	// makes only one of the buttons selectable, and in turn, one of the colors selectable
	private ButtonGroup colorGroup = new ButtonGroup();
	private GridBagConstraints c = new GridBagConstraints();
	// components of colorEnterPanel to get a custom color
	private JLabel redLabel, greenLabel, blueLabel;
	private JTextField redField, greenField, blueField;
	private JButton enterButton;
	// gets the color selected by the user
  private Color selectedColor;

/** ColorPanel constructor:
  - creates the actual color panel 
  @param width: the integer value of the width
  @param height: the integer value of the height
*/
	public ColorPanel (int width, int height){
		this.width = width;
		this.height = height;
		colorPanel = new JPanel(new GridLayout(2, 1)); //utilizes grid layout to ensure that the color buttons (which are added are placed in 
    colorPanel.setBackground(Color.MAGENTA);
		colorPanel.setSize(width/4, height/2);
		createColorPicker();
		colorPanel.add(colorPickerPanel);
		createColorEntry();
		colorPanel.add(colorEnterPanel);
		selectedColor = Color.red; //default color, as it is the first button in the color panel
	}

/** createColorPicker method:
    - creates the colorPicker panel and sets the size
*/
	private void createColorPicker(){
		colorPickerPanel = new JPanel(new GridLayout(3, 3)); //has a grid layout intended for the color value entry field labels and fields below, respectively
		colorPickerPanel.setSize(width/4, height/4); //size based on ratio
		addButtons();
	}

/** createColorEntry:
    -creates the colorEnterPanel, where color value fields and labels will lay within the grid layout of colorPickerPanel
*/
	private void createColorEntry(){
		colorEnterPanel = new JPanel(new GridBagLayout());
		colorEnterPanel.setSize(width/4, height/4);
		colorEnterPanel.setBackground(Color.white);
    
		redLabel = new JLabel("Red Value: #");
		greenLabel = new JLabel("Green Value: #");
		blueLabel = new JLabel("Blue Value: #");
  	
    //text fields for the user's custom color values (RGB)
		redField = new JTextField("                    ");
		greenField = new JTextField("                    ");
		blueField = new JTextField("                    ");

		enterButton = new JButton("Set Color");
		//button handling to add functionality to the button
		enterButton.addActionListener(new ButtonHandler());
    
		// setting the layout of the bottom part of the colorPanel that includes the above components, putting it in the panel using gridbaglayout with a 4 by 2 layout
		c.gridx = 0;
		c.gridy= 0;
		colorEnterPanel.add(redLabel, c);
		c.gridx = 1;
		c.gridy= 0;
		colorEnterPanel.add(redField, c);
		c.gridx = 0;
		c.gridy= 1;
		colorEnterPanel.add(greenLabel, c);
		c.gridx = 1;
		c.gridy= 1;
		colorEnterPanel.add(greenField, c);
		c.gridx = 0;
		c.gridy= 2;
		colorEnterPanel.add(blueLabel, c);
		c.gridx = 1;
		c.gridy= 2;
		colorEnterPanel.add(blueField, c);
		c.gridx = 0;
		c.gridy= 3;
		// allows the one component in the fourth row to take up the space that it takes that two normal components
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		colorEnterPanel.add(enterButton, c);
	}
	
  /** getSelectedColor method
      @return selectedColor: the user's selected color at the time (from the color menu)
  */
  public Color getSelectedColor(){
    return selectedColor;
  }

/** returnPanel method
    @return colorPanel: returns the colorPanel (type JPanel)
*/
	public JPanel returnPanel(){
		return colorPanel;
	}

/** addButtons method
    - adds buttons (from buttonList, the private instance variable of ColorPanel class) (default colors given to user), groups them, and adds the colored button to the ColorPickerPanel
*/
	private void addButtons(){
		for (int i = 0; i < colorList.length; i++){
			buttonList[i] = new JButton();
			buttonList[i].addActionListener(new ButtonHandler());//functionality to the buttons
			buttonList[i].setBackground(colorList[i]);
			colorGroup.add(buttonList[i]);
			colorPickerPanel.add(buttonList[i]);
		}
	}

/** setToolPanel method
    @param tool (of type Tool): any specific given tool
*/
	public void setToolPanel(Tool tool){
		this.tool = tool; 
	}

/** applies action listener to apply functionalities to the buttons, allows the buttons to be used and provide an event depended output given the button
*/
	private class ButtonHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
			JButton selectedButton = (JButton) e.getSource();
			//if the selected button is a button from the color menu, then the background of the tool button selected is changed to that color
      for (int i = 0; i<buttonList.length; i++){	
				if (selectedButton==buttonList[i]){
					selectedColor = colorList[i];
					tool.getSelectedTool().setBackground(selectedColor);
					i = buttonList.length;
				}
			}
			
      //if selected button is the enter button from the custom color picker, then it will set the background of the selected tool button to be that color, allowing us to later place that color on canvas in other methods, if applicable
			if (selectedButton == enterButton){
				selectedColor = new Color(Integer.valueOf(redField.getText()), Integer.valueOf(greenField.getText()), Integer.valueOf(blueField.getText()));
				tool.getSelectedTool().setBackground(selectedColor);
			}
		}
	}
	
}