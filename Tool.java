import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

/**Tool class deals with the creation of tools and their properties; tools that we have included in this project are brush (brushTool), bucket (bucketTool) and eraser (eraserTool)
*/
public class Tool extends JFrame{
  // panel that contains the components used for the tools
	private JPanel toolsPanel;
	// components used for the tool panel
	private JButton brushTool, bucketTool, eraserTool;
	private JButton selectedTool;
	// colorPanel object that is used to retreive the currently selected color
	private ColorPanel colorPanel;
	// max dimensions for the canvas buttons used to check the bounds when using the bucket tool
  private final int MAXWIDTH = 8;
  private final int MAXHEIGHT = 8;

/** Tool constructor
    -creates the panel that the tool buttons lay on
    @param width: number value of width, integer
    @param height: number value of height, integer
*/
  public Tool(int width, int height){
		toolsPanel = new JPanel(new GridLayout(3, 1)); //ensures that the tools are displayed in a grid, all stacked on each other
		toolsPanel.setBackground(Color.white); 
		toolsPanel.setSize(width/4, height/2); //ratio of the screen (using parameters) that the panel takes up
    //adds buttons for each of the tools with their respective images, adding an action listener for functionality, and then adding it to the grid (toolPanel)
		brushTool = new JButton(new ImageIcon("toolIMG/Brush.PNG"));
		brushTool.addActionListener(new ButtonHandler());
		toolsPanel.add(brushTool);
		brushTool.setBackground(Color.white);
		bucketTool = new JButton(new ImageIcon("toolIMG/Bucket.PNG"));
		bucketTool.addActionListener(new ButtonHandler());
		toolsPanel.add(bucketTool);
	  bucketTool.setBackground(Color.white);
		eraserTool = new JButton(new ImageIcon("toolIMG/Eraser.PNG"));
    eraserTool.addActionListener(new ButtonHandler());
		toolsPanel.add(eraserTool);
		eraserTool.setBackground(Color.white);
    //groups button together
		ButtonGroup toolGroup = new ButtonGroup();
		brushTool.setSelected(true);
		toolGroup.add(brushTool);
    toolGroup.add(eraserTool);
    toolGroup.add(bucketTool);
  }

  /** returnPanel method
      returns the toolsPanel, the JPanel in which all of the tool buttons are in
  */
	public JPanel returnPanel(){
  	return toolsPanel;
	}

  /** brush method
      - changes the color of the button that the user clicks on the canvas to their selected color
      @param selectedButton: user's selected button
  */
  public void brush(JButton selectedButton){
    selectedButton.setBackground(colorPanel.getSelectedColor());
  }

// this method is called when the bucket tool is used and clicked on one of the canvas buttons. using recursive calls, the method changes the color of the button clicked, then checks the buttons around it to see if they are a) in bounds and b) the same color as the button that was orignally clicked. if both of these are true, then a recursive call is made to all of the surrounding buttons, and the process is repeated
  public void bucket(JButton selectedButton, JButton[][] canvasArray, Color originalColor){
    for (int y = 0; y < canvasArray.length; y++){
      for (int x = 0; x < canvasArray[0].length; x++) {
      	if ((canvasArray[x][y] == selectedButton) && (originalColor==(selectedButton.getBackground()))){
          canvasArray[x][y].setBackground(colorPanel.getSelectedColor());
					if (x != 0){
						if (y != 0){
							bucket(canvasArray[x-1][y-1], canvasArray, originalColor);
						}
							bucket(canvasArray[x-1][y], canvasArray, originalColor);
						if (y != (MAXHEIGHT - 1)){
							bucket(canvasArray[x-1][y+1], canvasArray, originalColor);
						}
					}
					if (y != 0){
						bucket(canvasArray[x][y-1], canvasArray, originalColor);
					}
					if (y != (MAXHEIGHT - 1)){
						bucket(canvasArray[x][y+1], canvasArray, originalColor);	
          }
					
					if (x != (MAXWIDTH - 1)){
						if (y != 0){
							bucket(canvasArray[x+1][y-1], canvasArray, originalColor);
						}
						bucket(canvasArray[x+1][y], canvasArray, originalColor);
						if (y != (MAXHEIGHT - 1)){
							bucket(canvasArray[x+1][y+1], canvasArray, originalColor);
						}     
          }
				}
			}
		}
	}
	
  /** getSelectedTool method
      @return selectedTool: the user's selected tool button
  */
	public JButton getSelectedTool(){
		return selectedTool;
	}

  /** getBrush method
      @return brushTool: the brush button
  */
	public JButton getBrush(){
		return brushTool;
	}

  /** getBucket method
      @return bucketTool: the bucket button
  */
  public JButton getBucket(){
    return bucketTool;
  }

  /** getEraser method
      @return eraserTool: the eraser button
  */
  public JButton getEraser(){
    return eraserTool;
  }
	
  /** setColorPanel method
      - initializes the colorPanel instance variable to be the parameter
      @param colorPanel: of type colorPanel
  */
	public void setColorPanel(ColorPanel colorPanel){
		this.colorPanel = colorPanel;
	}
  
  /** getColorPanel method
      @return colorPanel: of type ColorPanel
  */
  public ColorPanel getColorPanel(){
		return colorPanel;
	}

 /** ButtonHandler: applies action listener to apply functionalities to the tools, allowing them to fulfill their purpose
*/
  private class ButtonHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
			//chain of if statements, if the user's clicked tool is a given tool, that tool's button background is changed (to give the user an idea of what color they select) and the other tool button backgrounds are changed to a default white
      JButton clickedTool = (JButton) e.getSource();if (clickedTool == brushTool){
				selectedTool = (JButton) e.getSource();
				brushTool.setBackground(colorPanel.getSelectedColor());
				bucketTool.setBackground(Color.white);
				eraserTool.setBackground(Color.white);
			} else if (clickedTool == bucketTool){
				selectedTool = (JButton) e.getSource();
				bucketTool.setBackground(colorPanel.getSelectedColor());
				brushTool.setBackground(Color.white);
				eraserTool.setBackground(Color.white);
      } else if (clickedTool == eraserTool){
				selectedTool = (JButton) e.getSource();
				bucketTool.setBackground(Color.white);
				brushTool.setBackground(Color.white);
      }
    }
	}
  
}

