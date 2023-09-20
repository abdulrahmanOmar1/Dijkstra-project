package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;


public class Main extends Application {

	static ArrayList<Country> Countrys;
	static Country sourceCity = null;
	static Country destinationCity = null;
	Pane root = new Pane();
	ComboBox<Label> source = new ComboBox<Label>();
	ComboBox<Label> Target = new ComboBox<Label>();
	static float mapHieght=715;
    static float mapWidth=1200;

	@Override
	public void start(Stage stage) throws FileNotFoundException {
		Stage primaryStage = new Stage();

		Scene scene = new Scene(root, 1580, 715);
		primaryStage.setTitle("Project 3");
		root.setStyle("-fx-background-color: 			#000080	;\r\n");
		initialize();
		Label names[] = new Label[Countrys.size()];
		Label s = new Label("Sorce:");
		s.setFont(new Font(30));
		s.setTextFill(Color.BEIGE);
		Label d = new Label("Target:");
		d.setFont(new Font(30));
		d.setTextFill(Color.BEIGE);
		source.setStyle("-fx-background-color: #87CEFA;\r\n");
		Target.setStyle("-fx-background-color: #87CEFA;\r\n");
		for (int i = 0, j = 0; i < names.length; i++, j++) {
			names[i] = new Label();
			names[i].setFont(new Font(20));
			names[i].setTextFill(Color.BLACK);
			names[i].setText(Countrys.get(i).name);
			source.getItems().add(names[i]);
			names[j] = new Label();
			names[j].setFont(new Font(20));
			names[j].setTextFill(Color.BLACK);
			names[j].setText(Countrys.get(j).name);
			Target.getItems().add(names[j]);
		}
		source.setTranslateX(1370);
		source.setTranslateY(50);
		source.setPrefSize(180, 50);
		Target.setTranslateX(1370);
		Target.setTranslateY(150);
		Target.setPrefSize(180, 50);
		s.setTranslateX(1200);
		s.setTranslateY(50);
		d.setTranslateX(1200);
		d.setTranslateY(150);

		source.setOnAction(e -> {
			sourceCity=Dijkstra.allNodes.get(source.getValue().getText());
			if(sourceCity!=null) {
			sourceCity.getTest().setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		Target.setOnAction(i->{
			destinationCity=Dijkstra.allNodes.get(Target.getValue().getText());
			if(destinationCity!=null) {
			destinationCity.getTest().setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});

		Button run = new Button("Run");
		run.setFont(new Font(30));
		run.setTranslateX(1290);
		run.setTranslateY(220);
		run.setMinWidth(170);
		run.setMinHeight(80);
		run.setAlignment(Pos.CENTER);
		run.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(25), Insets.EMPTY)));

		TextArea path = new TextArea();
		path.setTranslateX(1280);
		path.setTranslateY(320);
		path.setMinSize(270, 220);
		path.setMaxSize(270, 220);
		path.setEditable(false);
		path.setStyle("-fx-background-color: #87CEEB;");

		
		Label p=new Label("Path:");
		p.setFont(new Font(30));
		p.setTranslateX(1210);
		p.setTranslateY(320);
		p.setTextFill(Color.BEIGE);


		TextField t1 = new TextField();
		t1.setTranslateX(1350);
		t1.setTranslateY(570);
		t1.setPrefSize(190, 50);
		t1.setEditable(false);
		t1.setFont(new Font(20));
		t1.setStyle("-fx-background-color: #87CEEB;");
		
		Label t=new Label("Distance:");
		t.setFont(new Font(30));
		t.setTranslateX(1200);
		t.setTranslateY(570);
		t.setTextFill(Color.BEIGE);


		run.setOnAction(e -> {
			int v=0,w=0;
			for(int i=0;i<Countrys.size();i++) {
				if(sourceCity.getFullName().equals(Countrys.get(i).getFullName()))
					v=i;
				if(destinationCity.getFullName().equals(Countrys.get(i).getFullName()))
					w=i;
			}
			if (sourceCity != null && destinationCity != null) {
				Dijkstra graph = new Dijkstra(Countrys, Countrys.get(v), Countrys.get(w));
				graph.generateDijkstra();
				drawPathOnMap(graph.pathTo(Countrys.get(w)));
				root.getChildren().add(group);
				path.setText(graph.getPathString());
				t1.setText(graph.distanceString +" KM");
			}
		});
		
		Button reset=new Button("Reset");
		reset.setPrefSize(150, 60);
		reset.setAlignment(Pos.CENTER);
		reset.setTranslateX(1330);
		reset.setTranslateY(640);
		reset.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
		reset.setFont(new Font(30));
		
		reset.setOnAction(action ->{
			sourceCity.getTest().setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			destinationCity.getTest().setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			sourceCity=new Country();
			destinationCity=new Country();
			group.getChildren().clear();
			root.getChildren().remove(group);
			source.setValue(new Label(""));
			Target.setValue(new Label(""));
			path.setText(null);
			t1.setText(null);
		});

		root.getChildren().addAll(source, Target, run, path, t1, s, d,reset,p,t);
		primaryStage.setScene(scene);// set the scene
		primaryStage.show();
	}

	public void initialize() {
		Image image1 = new Image("C:\\Users\\omard\\Downloads\\image.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(mapHieght);
		imageView1.setFitWidth(mapWidth);
		imageView1.setVisible(true);
		root.getChildren().add(imageView1);
		for (int i = 0; i < Countrys.size(); i++) {

			Button b = new Button();
			Countrys.get(i).setTest(b);
			b.setUserData(Countrys.get(i));
			b.setTranslateX(getX(Countrys.get(i).x));// 600(600-40)
			b.setTranslateY(getY(Countrys.get(i).y));// 357.5(357.5+100)

			b.setMinWidth(10);
			b.setMinHeight(10);
			b.setMaxWidth(10);
			b.setMaxHeight(10);
			b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			b.setOnAction(event -> {
				b.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				if (sourceCity == null) {
					sourceCity = (Country) b.getUserData();
					Label l = new Label();
					l.setFont(new Font(20));
					l.setTextFill(Color.BLACK);
					l.setText(sourceCity.name);
					source.setValue(l);
				} else if (destinationCity == null && sourceCity != null) {
					destinationCity = (Country) b.getUserData();
					Label l = new Label();
					l.setFont(new Font(20));
					l.setTextFill(Color.BLACK);
					l.setText(destinationCity.name);
					Target.setValue(l);
				}
			});

			Label lb = new Label(Countrys.get(i).name);
			lb.setFont(new Font(10));
			lb.setTextFill(Color.BLACK);
			lb.setTranslateX(getX(Countrys.get(i).x));
			lb.setTranslateY(getY(Countrys.get(i).y)-10);

			root.getChildren().add(b);
			root.getChildren().add(lb);
		}

	}

	Group group=new Group();
	
	private void drawPathOnMap(Country[] cities) {
        for (int i = 0; i < cities.length - 1; i++) {
            Line line = new Line(getX(cities[i].x) + 5,getY(cities[i].y) + 5,
                    getX(cities[i+1].x) + 5,getY(cities[i+1].y) + 5);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            group.getChildren().add(line);
        }

    }
	private float getX(float xCountry) {
        float div=mapWidth/1200;
        return ((3.3334f*xCountry)-45)*div+mapWidth/2;
    }
    private float getY(float yCountry) {
        float div=mapHieght/715;
        return ((-3.97222f*yCountry)-22.5f)*div+mapHieght/2;
    }

	public static void main(String[] args) throws FileNotFoundException {
		Countrys = Dijkstra.readFile();
		
		launch(args);
	}
}
