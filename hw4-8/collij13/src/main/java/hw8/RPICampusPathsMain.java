package hw8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import hw7.CampusPaths;
import hw4.Edge;

public class RPICampusPathsMain extends Application {
	// Declare CampusPaths instance
    private CampusPaths temp;
    // Data used for setting format of GUI
    private Double[] screen = {Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight()};
    private Double[] ratio = {screen[0] / 2048, screen[1] / 1920};
    private Double[] mainPane = {0.0,0.0};
    private Double[] mainPaneCenter = {0.0,0.0};
    // Input strings for the optimal path feature 
    private TextField buildingNameField1, buildingNameField2 = null;
    // initialize buttons golbally for access later on
    private Button clearButton, enterButton = null;
    // Current scale of the mapPane
    private static final double DEFAULT_ZOOM = 1.0;
    private static final double MAX_ZOOM = 10.0;

    public void setMapPane(Pane MapPane){
        MapPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, null)));

        mainPane[0] = MapPane.getWidth();
        mainPane[1] = MapPane.getHeight();
        mainPaneCenter[0] = mainPane[0] / 2;
        mainPaneCenter[1] = mainPane[1] / 2;

        // Set the translation of the pane
        MapPane.setTranslateX(mainPaneCenter[0] - 75);
        MapPane.setTranslateY(mainPaneCenter[1]);    
        
        // Iterate through graph to draw edges
        for (String a : temp.campus.graph.keySet()) {
            for (String b : temp.campus.graph.get(a).keySet()) {
                Double[] start = temp.coordinates.get(a);
                Double[] stop = temp.coordinates.get(b);
                Line edge = new Line(start[0] * ratio[0], start[1] * ratio[1], stop[0] * ratio[0], stop[1] * ratio[1]);
                edge.setStroke(Color.TURQUOISE);
                MapPane.getChildren().add(edge);
            }
        }  

        // Iterate through coordinates and represent nodes
        for (String key : temp.coordinates.keySet()) {
            Double[] coords = temp.coordinates.get(key);
            String name = temp.id_saved.get(key);

            if (!name.contains("Intersection")) {
                // Create a square for a building, centered at coords[0], coords[1]
                double squareSize = 10;
                Rectangle building = new Rectangle((coords[0] * ratio[0]) - squareSize / 2, (coords[1] * ratio[1]) - squareSize / 2, squareSize, squareSize);
                building.setFill(Color.DARKBLUE);

                // Create a tooltip to display the building name
                Tooltip buildingTooltip = new Tooltip(name);
                buildingTooltip.setStyle("-fx-font-size: 13px;");
                Tooltip.install(building, buildingTooltip);

                // Add the building to the mapPane
                MapPane.getChildren().add(building);
            } else {
                // Create a circle for an intersection
                Circle intersection = new Circle((coords[0] * ratio[0]), (coords[1] * ratio[1]), 4);
                intersection.setFill(Color.BLUE);
                Tooltip buildingTooltip = new Tooltip(name);
                buildingTooltip.setStyle("-fx-font-size: 11px;"); 
                Tooltip.install(intersection, buildingTooltip);
                MapPane.getChildren().add(intersection);
            }
        }
        return;
    }

    public void setInteractiveOverlay(Pane pane1, VBox inputPane, StackPane tempPane){
        HBox buttons= new HBox(20);
    	
    	// Create an enter button
        pane1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null)));
        pane1.setTranslateX(mainPaneCenter[0] - 75);
        pane1.setTranslateY(mainPaneCenter[1]);

        // Create text fields for typing building names
        buildingNameField1 = new TextField();
        buildingNameField1.setPromptText("Enter building name 1");
        buildingNameField1.setMaxWidth(500);
        buildingNameField2 = new TextField();
        buildingNameField2.setPromptText("Enter building name 2");
        buildingNameField2.setMaxWidth(500);

        // Creating Enter and Clear Buttons
        enterButton = new Button("Enter");
        enterButton.setPrefWidth(240);
        clearButton = new Button("Clear");
        clearButton.setPrefWidth(240);
        buttons.getChildren().addAll(enterButton,clearButton);

        // Add the text fields and the enter button to the HBox
        inputPane.getChildren().addAll(buildingNameField1, buildingNameField2,buttons);
        inputPane.setTranslateX(10);
        inputPane.setTranslateY(10);
        // Set inputPane's background to transparent
        inputPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null)));

        // Adding Buttons functionality 
        // Clear Button Function:
        clearButton.setOnAction(event -> {
        	pane1.getChildren().clear();
        	buildingNameField1.setText(null);
        	buildingNameField2.setText(null);
        	tempPane.requestFocus();
        });

        // Enter Buuton Function:
        enterButton.setOnAction(event -> {
            pane1.getChildren().clear();
            // Collect data from the text fields
            String buildingName1 = buildingNameField1.getText();
            String buildingName2 = buildingNameField2.getText();
            
            // Confirm strings now hold ids
            buildingName1 = CheckID(temp.id_saved, buildingName1);
            buildingName2 = CheckID(temp.id_saved, buildingName2);
                
            int invalidCount = 0;
                
            // Check the first building name
            if (buildingName1.equals("invalid") || temp.id_saved.get(buildingName1).contains("Intersection") || buildingName1 == null || buildingName1.equals("")) {
                System.out.println("First building is invalid");
                invalidCount++;
            }
                
            // Check the second building name
            if (buildingName2.equals("invalid") || temp.id_saved.get(buildingName2).contains("Intersection") || buildingName2 == null || buildingName2.equals("")) {
                System.out.println("Second building is invalid");
                invalidCount++;
            }

            if (invalidCount > 0) {
                // addinng text to pop up if an invalid building name or id is entered
                Text errorMessage = new Text(150, 150, "Invalid Input: Try Again!");
                errorMessage.setFill(Color.RED);
                pane1.getChildren().add(errorMessage); 
            } else {
                // add optimal path overlay
                try {
                    ArrayList<Edge<Double>> path = temp.campus.dijkstra(buildingName1, buildingName2).trail();
                    ArrayList<String> path_ids = new ArrayList<>();
                    path_ids.add(buildingName1);
                    for(Edge<Double> node: path) {
                        path_ids.add(CheckID(temp.id_saved,node.name()));
                    }
                    String prev = buildingName1;
                    for(String name: path_ids) {
                        Double[] points = temp.coordinates.get(name);
                        // Draw an edge in the optimal path
                        if(!prev.equals(name)) {
                            Double[] point2 = temp.coordinates.get(prev);
                            Line edge = new Line(point2[0] * ratio[0], point2[1] * ratio[1], points[0] * ratio[0], points[1] * ratio[1]);
                            edge.setStroke(Color.GREEN);
                            pane1.getChildren().add(edge);
                            
                        }
                        // Draw an intersection or building in the optimal path
                        if (!temp.id_saved.get(name).contains("Intersection")) {
                            // Create a square for a building, centered at coords[0], coords[1]
                            double squareSize = 10;
                            Rectangle building = new Rectangle((points[0] * ratio[0]) - squareSize / 2, (points[1] * ratio[1]) - squareSize / 2, squareSize, squareSize);
                            building.setFill(Color.GREEN);
                            Line lineL = null;
                            Line lineR = null;
                            if(name.equals(buildingName1)) {
                            	// Add an X to square to show where the path starts
                            	lineL = new Line((points[0] * ratio[0]) - squareSize / 2, (points[1] * ratio[1]) - squareSize / 2, (points[0] * ratio[0]) + squareSize / 2, (points[1] * ratio[1]) + squareSize / 2);
                            	lineR = new Line((points[0] * ratio[0]) + squareSize / 2, (points[1] * ratio[1]) - squareSize / 2, (points[0] * ratio[0]) - squareSize / 2, (points[1] * ratio[1]) + squareSize / 2);
                            	lineL.setStroke(Color.BEIGE);
                            	lineR.setStroke(Color.BEIGE);
                            }
                            // Add the building to the mapPane
                            if (lineL != null) {
                            	pane1.getChildren().addAll(building,lineL,lineR);
                            }else {
                            	pane1.getChildren().add(building);
                            }
                            
                        } else {
                            // Create a circle for an intersection
                            Circle intersection = new Circle((points[0] * ratio[0]), (points[1] * ratio[1]), 4);
                            intersection.setFill(Color.GREEN);
                            pane1.getChildren().add(intersection);
                        }
                        prev = name;
                    }   
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tempPane.requestFocus();
        });
        return;
    }

    public void mergeIntoRoot(Pane mapPane, Pane pane1, VBox inputPane, StackPane tempPane, StackPane rootPane){
        // create a temp pane to stack mapPane and pane1, then stack inputPane on top
        tempPane.getChildren().addAll(mapPane, pane1);
        tempPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, null)));
        rootPane.getChildren().addAll(tempPane, inputPane);
        rootPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, null)));       
        return; 
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Load the data files and initialize CampusPaths
        String file1 = "data/RPI_map_data_Nodes.csv";
        String file2 = "data/RPI_map_data_Edges.csv";
        temp = new CampusPaths(file1, file2);
        
        // Create panes for GUI Layers
        Pane mapPane = new Pane();
        Pane pane1 = new Pane();
        StackPane tempPane = new StackPane();
        StackPane rootPane = new StackPane();

        // Create a box for holding the text fields and the enter button
        VBox inputPane = new VBox(10);

        // Creates default map
        setMapPane(mapPane);

        // Adds buttons, text boxes, and the optimal path when valid inputs are entered
        // inputPane holds the text boxes and buttons
        // pane1 holds the optimal line between two buildings on campus
        setInteractiveOverlay(pane1,inputPane,tempPane);
        
        // Merges mapPane and pane1 into a single bigger pane
        mergeIntoRoot(mapPane, pane1, inputPane, tempPane, rootPane);

        // Create a scene with the rootPane
        Scene scene = new Scene(rootPane, screen[0], screen[1]);
        
        // Bind the width and height of mapPane to the width and height of the scene
        mapPane.prefWidthProperty().bind(scene.widthProperty());
        mapPane.prefHeightProperty().bind(scene.heightProperty());
        tempPane.setTranslateX(-15);

        inputPane.setOnKeyPressed(event -> {
        	if (event.getCode() == KeyCode.DOWN && inputPane.isFocused()) {
        		tempPane.requestFocus();
        		
        	}
        	
        });
        // handler for the scene / map
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                	tempPane.requestFocus();
                	tempPane.setTranslateY(tempPane.getTranslateY() - 20);
                    break;
                case UP:
                	tempPane.requestFocus();
                	tempPane.setTranslateY(tempPane.getTranslateY() + 20); 
                	tempPane.requestFocus();
                	break;
                    
                case RIGHT:
                	tempPane.requestFocus();
                	tempPane.setTranslateX(tempPane.getTranslateX() - 20); 
                    break;
                case LEFT:
                	tempPane.requestFocus();
                	tempPane.setTranslateX(tempPane.getTranslateX() + 20); 
                    break;
                case PAGE_UP:
                    zoom(tempPane, 0.9); // Zoom out
                    break;
                case PAGE_DOWN:
                    zoom(tempPane, 1.1); // Zoom in
                    break;
                case ESCAPE:
                	tempPane.requestFocus();
                    break;
                case ENTER:
                	inputPane.requestFocus();
                	enterButton.fire();
                	tempPane.requestFocus();
                    break;
                default:
                    // Do nothing for other keys
                    break;
            }
            tempPane.requestFocus();
        });

        // Set the scene on the stage and show it
        stage.setScene(scene);
        stage.setTitle("Rensselaer Polytechnic Institute Campus Path Finder");
        // default focus is on the map
        tempPane.requestFocus();
        stage.show();
    }

	public static String CheckID(HashMap<String, String> id, String given){
		if (id.containsKey(given)){
			return given;
		}else{
			// given is either a intersection or a building
			for(String x: id.keySet()) {
				if(id.get(x).equals(given)) {
					return x;
				}
			}
		}
		return "invalid";
	}
    
    private void zoom(Pane mapPane, double scaleFactor) {
        // Get current scale values for zoom
        double currentScaleX = mapPane.getScaleX();
        double currentScaleY = mapPane.getScaleY();
        // set new scales
        double newScaleX = currentScaleX * scaleFactor;
        double newScaleY = currentScaleY * scaleFactor;
        // Check if zooming out is within the Max zoom limit
        if (scaleFactor < 1.0 && newScaleX <= DEFAULT_ZOOM && newScaleY <= DEFAULT_ZOOM) {
            // Set scale to the default zoom level
            mapPane.setScaleX(DEFAULT_ZOOM);
            mapPane.setScaleY(DEFAULT_ZOOM);
            return;
        }
        
        // If zooming in is within the min zoom limit
        if (scaleFactor > 1.0 && newScaleX > MAX_ZOOM && newScaleY > MAX_ZOOM) {
            // Set scale to the maximum allowed scale
            mapPane.setScaleX(MAX_ZOOM);
            mapPane.setScaleY(MAX_ZOOM);
            return;
        }
        
        // Apply and the new scale and ajust pane accordingly
        mapPane.setScaleX(newScaleX);
        mapPane.setScaleY(newScaleY);
        mapPane.setTranslateX(mapPane.getTranslateX() * scaleFactor);
        mapPane.setTranslateY(mapPane.getTranslateY() * scaleFactor);
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
