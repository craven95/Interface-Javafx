import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.*;


public class Main extends Application{
	
	
	public void start (Stage primaryStage) {

	UdpServer serveur = new UdpServer(); //this line create an new object : serveur
		
	               try{
	            	   BorderPane root = new BorderPane();
	            	   root.setTop(createToolbar());
	            	   root.setBottom (createStatusbar());
	            	   root.setCenter(createMainContent(serveur));
	            	   root.setOnMouseClicked( new EventHandler<MouseEvent >() {
	            			   public void handle ( MouseEvent e ) {
	            				   System.out.println( "mouseclick!"); //for testing MouseEvent
	            			   }
	            			   }
	            	   );

	            	   Scene scene = new Scene (root, 800, 400); //for the style
	            	   scene.getStylesheets().addAll(this.getClass().getResource("/style/scene.css").toExternalForm());	            	              	   
	            	   
	            	   primaryStage.setScene(scene);
	            	   primaryStage.setTitle("Demo JavaFX");
	            	   primaryStage.show();
	            	   } 
	               catch(Exception e) {
	            	   e.printStackTrace();
	               }
	           }
	
	private Node createToolbar() {
		return new ToolBar (new Button ( "New") , new Button ( "Open"), new Separator( ) , new Button ( "Clean ")) ; //a simple ToolBar
		}
	
	private Node createStatusbar ( ) {
		HBox statusbar = new HBox ( ) ;
		return statusbar ;
		}
	
	private Node createMainContent (UdpServer serveur) {
		Group g = new Group ( ) ;
 	    Text mon_text = new Text ("Bienvenue sur l'interface");
 	    g.getChildren().add(mon_text);
 	    
 	    
 	    GridPane gridPane = new GridPane();
	    gridPane.setMinSize( 400 , 200 );
	    gridPane.setVgap(5);
	    gridPane.setHgap(5);
	    gridPane.setAlignment( Pos.CENTER);
 	    
 	    // Here i wanted to create a collection of pictures.
	    //then with two buttons i can show the next or previous picture.
	    //Moreover i was able to add or reduce a glow effect. 
	    //I commented this part because this is not interessant for the real objectives.
	    
	    /*
 	    Image deux = new Image("./media/deux.jpg");
	    Image trois = new Image("./media/trois.jpg");
	    Image un = new Image("./media/image.png");
	    ImageView unView = new ImageView(un);
	    ImageView deuxView = new ImageView(deux);
	    ImageView troisView = new ImageView(trois);
	     	    
	    Glow glow = new Glow ( ) ;
	    
	    glow.setLevel(0.0);
	    unView.setEffect(glow ); 
	    deuxView.setEffect(glow ); 
	    troisView.setEffect(glow ); 
	    
	    LinkedList <ImageView> ma_list = new LinkedList<ImageView>();
	    ma_list.add(unView);
	    ma_list.add(deuxView);
	    ma_list.add(troisView);
	    ImageView image = unView;
	    gridPane.add( image , 3 , 1 );
	    */
	    
	    Text message = new Text();
	    message.setText("Zone de reception"); //we will write here any message we receive on the server.
	    gridPane.add( message , 2 , 2 );
	    
	    NumberAxis xAxis = new NumberAxis();
 	  	NumberAxis yAxis = new NumberAxis();

        LineChart<Number,Number> chart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        series.setName("Température");
        //For exemple i receive data from an temperature sensor.
        
        XYChart.Data<Number,Number> data = new XYChart.Data<Number,Number>(1,37);
        series.getData().add(data);
        //This line add a first data (37°) for the first mesure.
        chart.getData().addAll(series);
        
	    
	   // I created an object server but it hasn't benn launched. Indeed i have to create a button
       // This button will launch the server on click.
 	   Button Launch = new Button( "Lancement serveur "); 
 	   Launch.setOnMouseClicked(new EventHandler<MouseEvent>() { 
	        public void handle(MouseEvent actionEvent) { 
	        	   serveur.start(); //as a thread, the method start will execute the method run of my object UdpServer.
	        	   message.setText("En attente de reception..."); // the receive zone has been modified because the server is running.
				   }
			   }
	         );
 	  Button Stop = new Button( "Arret Serveur ");
 	  Stop.setOnMouseClicked(new EventHandler<MouseEvent>() { 
	        public void handle(MouseEvent actionEvent) { 
	        	   serveur.closeserveur(); // a button for closing the thread.
				   }
			   }
	         );
 	  
 	  	Button Actualisation = new Button( "Actualisation du texte");
 	  	Actualisation.setOnMouseClicked(new EventHandler<MouseEvent>() { 
 	  		public void handle(MouseEvent actionEvent) { 
	        	 String recu = serveur.getMessage();
	        	 List<Integer> datarecu = serveur.getData();
	        	 try {
	        	 for (int i = 0; i < datarecu.size(); i++) {
	        		 XYChart.Data<Number,Number> updateData = new XYChart.Data<Number,Number>(i,datarecu.get(i));
	        		 series.getData().addAll(updateData);
	        	 }
	        	 message.setText(recu);
	        	 System.out.println("ce que je reçois (normalement data) : ");
        		 System.out.println(datarecu);
				   }
 	  		
 	  	 catch(Exception exc) {
             System.out.println(datarecu);
         }
 	  		}
			   }
	         );
 	  	
 	  	
        

        // Chart design
        chart.setTitle("Suivi des capteurs");
        chart.relocate(20, 230);
        xAxis.setLabel("Temps (s)");
        yAxis.setLabel("Température en °");
 	  
 	 


	    gridPane.add( Launch , 1 , 1 );
	    gridPane.add( Stop , 1 , 2 );
	    gridPane.add( Actualisation , 2 , 1 );
	    
	    
	    g.getChildren().add(gridPane);
	    g.getChildren().add(chart);
	    
	   
		return g ;
		}
}