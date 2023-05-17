module com.example.code_clause_one {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.example.code_clause_one to javafx.fxml;
    exports com.example.code_clause_one;
}