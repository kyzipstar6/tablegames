module tablegames {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.media;
	requires javafx.base;
	requires java.logging;
	
	opens poker to javafx.graphics, javafx.fxml;
}
