import Controllers.Agenda;
import Views.Home.HomeControl;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Agenda agenda = Agenda.getInstance();
        
        HomeControl homeControl = new HomeControl(agenda);
        homeControl.start(new Stage());
    }

    
}
