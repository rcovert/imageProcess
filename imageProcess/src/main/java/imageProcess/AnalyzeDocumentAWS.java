package imageProcess;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.AnalyzeDocumentRequest;
import software.amazon.awssdk.services.textract.model.AnalyzeDocumentResponse;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.FeatureType;

public class AnalyzeDocumentAWS {

	public AnalyzeDocumentAWS() {
		// TODO Auto-generated constructor stub
	}


	public void doAnalysis(File theFile) {
		// Call AnalyzeDocument	
		InputStream sourceStream;
		SdkBytes sourceBytes = null;
		// commit changes
		try {
			sourceStream = new FileInputStream(theFile);
			sourceBytes = SdkBytes.fromInputStream(sourceStream);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document myDoc = Document.builder()
				.bytes(sourceBytes)
				.build();
		

        List<FeatureType> featureTypes = new ArrayList<FeatureType>();
        featureTypes.add(FeatureType.FORMS);
        featureTypes.add(FeatureType.TABLES);
        
        AnalyzeDocumentRequest analyzeDocumentRequest = AnalyzeDocumentRequest.builder()
              .featureTypes(featureTypes)
              .document(myDoc)
              .build();
        
        Region region = Region.US_EAST_1;
        TextractClient textractClient = TextractClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        
        AnalyzeDocumentResponse analyzeDocument = textractClient.analyzeDocument(analyzeDocumentRequest);
        List<Block> docInfo = analyzeDocument.blocks();
        Iterator<Block> blockIterator = docInfo.iterator();
        
        int ij = 0;

        while(blockIterator.hasNext()) {
            Block block = blockIterator.next();
            System.out.println(++ij +" The block type is " +block.blockType().toString());
            System.out.println("Confidence: " + block.confidence());
            System.out.println("Text: " + block.text());
        }
        
        textractClient.close();

	}
	

}
