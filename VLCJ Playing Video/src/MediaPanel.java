import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.test.basic.PlayerControlsPanel;
import uk.co.caprica.vlcj.test.basic.PlayerVideoAdjustPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


public class MediaPanel implements Runnable{

	private String STARTING_VIDEO_URL = "";
	
	public MediaPanel(String videoURL)
	{
		STARTING_VIDEO_URL = videoURL;
	}
	
	private void initialize(String videoURL)
	{
		//importing libraries into project to allow run in mac (not including VLCJ... Add that into code using local repository
		//in eclipse (or that's how I've done it)
		System.loadLibrary("jawt");
		NativeLibrary.addSearchPath(
	           RuntimeUtil.getLibVlcLibraryName(), "/Applications/VLC/VLC.app/Contents/MacOS/lib"
	       );
	       Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	      
	       //Create Main Window
//		    mediaPlayerComponent.getMediaPlayer().playMedia(Constant.PATH_ROOT + Constant.PATH_MEDIA + "tmp.mp4");
		    JFrame mainFrame = new JFrame();
		    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	       //Create embedded player
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	    EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerComponent.getMediaPlayer();

	    //Create video surface
	    Canvas videoSurface = new Canvas();
	    videoSurface.setBackground(Color.black);
	    videoSurface.setSize(800, 600);

	    //Setting up VLC
	    ArrayList<String> vlcArgs = new ArrayList<String>();
	    	//setting up initial args/VLC run settings
	    vlcArgs.add("--no-plugins-cache");
	    vlcArgs.add("--no-video-title-show");
	    vlcArgs.add("--no-snapshot-preview");
	    //initializing VLC interface
	    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(vlcArgs.toArray(new String[vlcArgs.size()]));
	    mediaPlayerFactory.setUserAgent("vlcj test player");
	    embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(videoSurface));
	    embeddedMediaPlayer.setPlaySubItems(true);
	    
	    //Create controls from predefined classes in VLCJ
	    final PlayerControlsPanel controlsPanel = new PlayerControlsPanel(embeddedMediaPlayer);
	    PlayerVideoAdjustPanel videoAdjustPanel = new PlayerVideoAdjustPanel(embeddedMediaPlayer);

	    //Add controls to the main frame
	    mainFrame.setLayout(new BorderLayout());
	    mainFrame.add(videoSurface, BorderLayout.CENTER);
	    mainFrame.add(controlsPanel, BorderLayout.SOUTH);
	    mainFrame.add(videoAdjustPanel, BorderLayout.EAST);


	    //create a button which will hide the panel when clicked.
	    mainFrame.pack();
	    mainFrame.setVisible(true);
	    if(videoURL != null)
	    	embeddedMediaPlayer.playMedia(videoURL); //can put any video stream you want including video capture device on UNIX machines
	    //Not too sure about how windows handles capture devices.
	    
	}
	@Override
	public void run() {
		// Start program in new thread
		initialize(STARTING_VIDEO_URL);
	}
	
}
