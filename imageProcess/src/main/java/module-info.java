module imageProcess {
	exports imageProcess;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires software.amazon.awssdk.core;
	requires software.amazon.awssdk.services.textract;
	requires software.amazon.awssdk.utils;
	requires software.amazon.awssdk.awscore;
	requires software.amazon.awssdk.regions;
	requires software.amazon.awssdk.auth;
	
	opens imageProcess to javafx.graphics, javafx.fxml;
}