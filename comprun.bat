cd C:\Users\Public\Documents\java
javac --module-path   "dataplotter/lib/javafx-sdk-23.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -d C:/Users/Zip/Java/Workspaces/games/tablegames/bin C:/Users/Zip/Java/Workspaces/games/tablegames/src/poker/*.java

cd C:\Users\Zip\Java\Workspaces\games\tablegames && java --module-path "C:/Users/Public/Documents/java/dataplotter/lib/javafx-sdk-23.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.swing,javafx.media -cp ".;bin" poker.Posibilities

cmd /k
