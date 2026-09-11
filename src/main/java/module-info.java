module br.senac.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens br.senac.hotel to javafx.fxml;
    exports br.senac.hotel;
}