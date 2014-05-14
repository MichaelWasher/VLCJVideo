import javax.swing.JFileChooser;



public class Program {
	
	//Basic Overview of Program
	public static void main(String[] args)
	{
		 // create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        
        // show open file dialog
        int result = fileChooser.showOpenDialog( null );

        if ( result == JFileChooser.APPROVE_OPTION ) // user chose a file
        {
           String mediaURL = null;
           
           // get the file as URL
		  mediaURL = fileChooser.getSelectedFile().toString();
		  new Thread(new MediaPanel(mediaURL)).start();
        }
	}
}

