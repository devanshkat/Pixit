import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

/** Canvas class utilizes JFrame to create the user's canvas, and allow the user to paint/edit their desired image.
*/
public class Canvas extends JFrame{
	// the jpanel that contains the buttons on the canvas
	private JPanel canvas;
	// the array that contains all the buttons that show up on the screen
  private JButton[][] canvasArray; 
	// instance of the tool class that allows the canvas class to what to do when one of its canvas buttons is selected, such as brush, which will change the color of an indivdual button, or bucket, which will change the all of the surrounding buttons that are the same color, or eraser, which sets the button's color as white
	private Tool tool;

/** Canvas constructor:
    - Creates the canvas
    @param rows: number value (integer) of rows of the canvas 
    @param columns: number value (integer) of columns of the canvas 
    @param screenWidth: the value of the width of the screen (integer), used to properly display the canvas ratio in relation to the screen and other elements of the interface
    @param screenHeight: the value of the height of the screen (integer), used similarly to screenWidth

*/
	public Canvas (int rows, int columns, int screenWidth, int screenHeight){
		canvas = new JPanel(new GridLayout(rows, columns)); //uses grid layout to ensure the buttons are placed in a grid format as intended
		canvas.setBackground(Color.white); //default color
		canvas.setSize(3*screenWidth/4, screenHeight); //allows canvas to be 3/4 of the screen width, and take up the entire height
    canvasArray = new JButton[rows] [columns]; //2D array of buttons
    ButtonHandler handler = new ButtonHandler(); //adds functionality to the buttons; see methods below
   //creates plain, white buttons using a 2D array according to specified row and height values passed in parameters
    for (int y = 0; y < rows; y++){
			for(int x = 0; x <columns; x++){
      	canvasArray[x][y] = new JButton("        "); 
      	canvasArray[x][y].setBackground(Color.white);
				canvasArray[x][y].setForeground(Color.white);
      	canvasArray[x][y].addActionListener(handler);
      	canvas.add(canvasArray[x][y]); 
			}
		}
	}
 
 /** returnPanel method
    @return canvas: JPanel that contains the user's canvas grid of buttons, allows access to the grid when returnPanel is called
 */
	public JPanel returnPanel(){
		return canvas;
	}

/** getCanvasArray method
    @return canvasArray: the 2D array of buttons, allows us to access information about what button was clicked (via the x and y coordinates on the grid) 
*/
  public JButton[][] getCanvasArray(){
    return canvasArray;
  }
  
/** setTools method
    @param tool: refers to a tool (brush, bucket, or eraser of type Tool
*/
	public void setTools(Tool tool){
		this.tool = tool;
	}

/** ButtonHandler: applies action listener to apply functionalities to the buttons of the canvas, allows it to be used as a canvas
*/
  private class ButtonHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
				JButton clickedButton = (JButton) e.getSource(); //the button the user clicks on the canvas is going to be clicked button
        
        //chain of if statements that change/alter the color of the button depending on the tool that is selected at the time; if no tool or the eraser is selected, it is set to a default white background
				if (tool.getSelectedTool()==tool.getBrush()){
				tool.brush(clickedButton);
			} else if(tool.getSelectedTool()==tool.getBucket()){
				tool.bucket(clickedButton, canvasArray, clickedButton.getBackground());
			} else {
				clickedButton.setBackground(Color.white);
			}
    }
	}
}