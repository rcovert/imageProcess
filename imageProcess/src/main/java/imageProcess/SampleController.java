package imageProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.Scene;

public class SampleController {

    @FXML
    private ResourceBundle resources;
    
    @FXML
    private Button extractA;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Button zoomIn;

    @FXML
    private Button zoomOut;
    
    @FXML
    private MenuItem openFile;
    
    public double getTheWidth() {
		return theWidth;
	}

	public void setTheWidth(double theWidth) {
		this.theWidth = theWidth;
	}

	public double getTheHeight() {
		return theHeight;
	}

	public void setTheHeight(double theHeight) {
		this.theHeight = theHeight;
	}

	private double theWidth;
    private double theHeight;
    
    private String theBase64;
    private ByteBuffer bb;

    private File theFile;

	@FXML
    void doZoomIn(ActionEvent event) {
    	//System.out.println("imageview: " + imageView.computeAreaInScreen());
    	//System.out.println("image: " + imageView.getImage());
    	
    	double iHeight = this.getTheHeight();
    	double iWidth = this.getTheWidth();
    	double newHeight = iHeight * 1.20;
    	double newWidth = iWidth * 1.20;
    	imageView.setFitHeight(newHeight);
    	imageView.setFitWidth(newWidth);
    	this.setTheHeight(newHeight);
    	this.setTheWidth(newWidth);
    }

    @FXML
    void doZoomOut(ActionEvent event) {
    	double iHeight = this.getTheHeight();
    	double iWidth = this.getTheWidth();
    	double newHeight = iHeight * .80;
    	double newWidth = iWidth * .80;
    	imageView.setFitHeight(newHeight);
    	imageView.setFitWidth(newWidth);
    	this.setTheHeight(newHeight);
    	this.setTheWidth(newWidth);
    }
    
    @FXML
    void doOpenFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	theFile = fileChooser.showOpenDialog(null);
    	FileInputStream inputstream;
		try {
			inputstream = new FileInputStream(theFile);
			int theSize = inputstream.available();
			System.out.println("the size: " + theSize);
			byte[] theBytes = new byte[theSize];
			inputstream.read(theBytes);
			// now read the inputstream into the byte array and encode
			//byte[] encoded = Base64.getEncoder().encode(theBytes);
			//theBase64 = new String(encoded);
			// aws just uses unencoded byte buffer / no need to encode

			
			// close and reopen the stream to get back to start
			inputstream.close();
			inputstream = new FileInputStream(theFile);
			// now put the image into the imageview
			Image image = new Image(inputstream);
			imageView.setImage(image);
			inputstream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		double iHeight = imageView.getImage().getHeight();
    	double iWidth = imageView.getImage().getWidth();
    	this.setTheHeight(iHeight);
    	this.setTheWidth(iWidth);
    	//System.out.println(iHeight);
    	//System.out.println(iWidth);
    	//System.out.println("base:  " + theBase64);

    }
    
    @FXML
    void doExtractA(ActionEvent event) {
    	// get analysis object
    	AnalyzeDocumentAWS ada = new AnalyzeDocumentAWS();
    	ada.doAnalysis(theFile);
    }

    @FXML
    void initialize() {
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'FinalScene.fxml'.";
        assert zoomIn != null : "fx:id=\"zoomIn\" was not injected: check your FXML file 'FinalScene.fxml'.";
        assert zoomOut != null : "fx:id=\"zoomOut\" was not injected: check your FXML file 'FinalScene.fxml'.";
        System.out.println("Initialized");      
    }

}