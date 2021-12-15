import javax.swing.*;
import java.awt.*;

/** GUI class utilizes JFrame to create user interface, and provides an area for the user to view and use/interact with the project.
*/
public class GUI extends JFrame{
	// all the panels that need to be added to the frame
	JPanel artJPanel, multiToolPanel, filePanel, toolsJPanel, colorJPanel; 
	// intermediate steps to create the panels that are objects of their respective classes
	Canvas artPanel;
	ColorPanel colorPanel;
	Tool toolsPanel;
	GridBagConstraints c = new GridBagConstraints();
	// dimensions of the canvas
  private final int MAXHEIGHT = 8;
  private final int MAXWIDTH = 8;

  /** GUI constructor 
      - creates the GUI, adds all of the panels
  */
  public GUI(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)size.getWidth(); //width of screen
    int height = (int)size.getHeight(); //height of screen
		this.setSize(width, height); //sets size of GUI to be the full screen
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setTitle("Pixit"); //title of our project!
		this.setLayout(new GridBagLayout()); //layout uses grid bag layout to allow us to place elements of the GUI in their proper place more efficiently/specifically
	
    //creates the canvas, which lies within artPanel:
  	artPanel = new Canvas(MAXWIDTH, MAXHEIGHT, width, height);
		artJPanel = artPanel.returnPanel();
    //creates the multiToolPanel, which has all of the tool/color menues within:
		multiToolPanel = new JPanel(new GridBagLayout());
		multiToolPanel.setSize(width/4, height);
  
		filePanel = new FilePanel(width, height, artPanel).returnPanel();
		colorPanel = new ColorPanel(width, height);
		toolsPanel = new Tool(width, height);
		artPanel.setTools(toolsPanel);
		colorPanel.setToolPanel(toolsPanel);
		toolsPanel.setColorPanel(colorPanel);
		colorJPanel  = colorPanel.returnPanel();
		toolsJPanel = toolsPanel.returnPanel();
 
 // setting the layout of the panels in multiToolPanel that includes the filePanel, toolsJPanel, and the colorJPanel, putting it in the multiToolPanel using gridbaglayout with a 3 by 1 layout
		c.gridx = 0;
		c.gridy = 0;
		multiToolPanel.add(filePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		multiToolPanel.add(toolsJPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		multiToolPanel.add(colorJPanel, c);
// setting the layout of the panels in the frame that includes the artJPanel and multiToolPanel, putting it in the multiToolPanel using gridbaglayout with a 4 by 3 layout, with the artJPanel taing up 3/4 of the space in the frame while the multiToolPanel only takes up 1/4 of the screen
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		this.add(artJPanel, c);
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
    this.add(multiToolPanel, c);
		this.setVisible(true);
	}

}