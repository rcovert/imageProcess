package imageProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import software.amazon.awssdk.services.textract.model.Block;

public class SampleController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button extractA;

	@FXML
    private ListView<String> theList;

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

	private File theFile;
	private List<Block> myList;
	
	public ObservableList<String> items = FXCollections.observableArrayList();

	@FXML
	void doZoomIn(ActionEvent event) {
		// System.out.println("imageview: " + imageView.computeAreaInScreen());
		// System.out.println("image: " + imageView.getImage());

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
		// delete the current view and table before loading new one
		imageView.setImage(null);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		theFile = fileChooser.showOpenDialog(null);
		FileInputStream inputstream;
		try {
			// theBase64 = new String(encoded);
			// aws just uses unencoded byte buffer / no need to encode

			// close and reopen the stream to get back to start
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
		// now clear the items list
		items.remove(0, items.size());

	}

	@FXML
	void doExtractA(ActionEvent event) {
		// get analysis object
		AnalyzeDocumentAWS ada = new AnalyzeDocumentAWS();
		myList = ada.doAnalysis(theFile);
		
		Iterator<Block> blockIterator = myList.iterator();

		int ij = 0;
		String theString = null;
		
		while (blockIterator.hasNext()) {
			Block block = blockIterator.next();
			//System.out.println(++ij + " The block type is " + block.blockType().toString());
			//System.out.println("Confidence: " + block.confidence());
			//System.out.println("Text: " + block.text());
			theString = block.text();
			items.add(theString);
		}
		
		theList.setItems(items);
	}

	@FXML
	void initialize() {
		assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'FinalScene.fxml'.";
		assert zoomIn != null : "fx:id=\"zoomIn\" was not injected: check your FXML file 'FinalScene.fxml'.";
		assert zoomOut != null : "fx:id=\"zoomOut\" was not injected: check your FXML file 'FinalScene.fxml'.";
		System.out.println("Initialized");
	}

}