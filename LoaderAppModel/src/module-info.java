module LoaderAppModel {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.xml.crypto;
    requires javafx.fxml;
	requires java.sql;
	requires com.opencsv;
//	requires commons.lang3;
	
	opens Model to javafx.base;
    exports View;
}
