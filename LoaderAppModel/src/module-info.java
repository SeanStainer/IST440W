module LoaderAppModel {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.xml.crypto;
    requires javafx.fxml;
	requires java.sql;
	
	opens Model to javafx.base;
    exports View;
}
