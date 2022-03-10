// JavaFX DrawPad SketchPane GUI making module. Has all necessary imports and commands to launch the application window in JavaFX environment.

// Importing necessary packages here (mainly from JavaFX)

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.*;
import javafx.collections.*;


public class SketchPane extends BorderPane {

    // Getting elements loaded

    private ComboBox<String> fillColorCombo,strokeWidthCombo,strokeColorCombo;
    private ArrayList<Shape> shapeList;
    private ArrayList<Shape> tempList;
    private Rectangle rectangle;
    private double x1,y1;
    private Label fillColorLabel,strokeColorLabel,strokeWidthLabel;
    private Circle circle;
    private RadioButton radioButtonLine,radioButtonRectangle,radioButtonCircle;
	private String []colorLabels,strokeWidth;
    private Color currentStrokeColor,currentFillColor;
    private int currentStrokeWidth;
    private Line line;
    private Button undoButton,eraseButton;
    private Pane sketchCanvas;
    private Color []colors;

    // Setting up the windows

	public SketchPane() {

        undoButton = new Button("Undo");
        eraseButton = new Button("Erase");

        undoButton.setOnAction(new ButtonHandler());
        eraseButton.setOnAction(new ButtonHandler());
        
        shapeList = new ArrayList<>();
        tempList = new ArrayList<>();
        
        // Declaring Colours and names

        colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME} ;
        colorLabels = new String[] {"black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime"};

        // Filling Color

        fillColorLabel = new Label("Fill Color:");
        fillColorCombo = new ComboBox<>(FXCollections.observableArrayList(colorLabels));

        fillColorCombo.setOnAction(new ColorHandler());
        fillColorCombo.setValue("black");

        strokeColorLabel = new Label("Stroke Color:");
        strokeColorCombo = new ComboBox<>(FXCollections.observableArrayList(colorLabels));

        strokeColorCombo.setOnAction(new ColorHandler());
        strokeColorCombo.setValue("black");

        strokeWidthLabel = new Label("Stroke Width:");
        strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13"};  
        strokeWidthCombo = new ComboBox<>(FXCollections.observableArrayList(strokeWidth));

        strokeWidthCombo.setOnAction(new WidthHandler());
        strokeWidthCombo.setValue("1");
        
        ToggleGroup tg = new ToggleGroup();
        radioButtonLine = new RadioButton("Line");

        radioButtonLine.setToggleGroup(tg);
        radioButtonLine.setSelected(true);

        radioButtonRectangle = new RadioButton("Rectangle");
        radioButtonRectangle.setToggleGroup(tg);
        radioButtonCircle = new RadioButton("Circle");
        radioButtonCircle.setToggleGroup(tg);
        
        sketchCanvas = new Pane();
        sketchCanvas.setStyle("-fx-background-color: white");
       
        // Hboxes initialization

        HBox hb2 = new HBox(20);
        hb2.setMinSize(20,40);
        hb2.setStyle("-fx-background-color: lightgrey");
        hb2.getChildren().add(radioButtonRectangle);
        hb2.getChildren().add(radioButtonCircle);
        hb2.getChildren().add(undoButton);
        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().add(eraseButton);
        hb2.getChildren().add(radioButtonLine);

        HBox hb1 = new HBox(20);
        hb1.setMinSize(20,40);
        hb1.getChildren().add(fillColorLabel);
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().add(strokeColorLabel);
        hb1.getChildren().add(strokeColorCombo);
        hb1.getChildren().add(fillColorCombo);
        hb1.setStyle("-fx-background-color: lightgrey");
        hb1.getChildren().add(strokeWidthLabel);
        hb1.getChildren().add(strokeWidthCombo);
        
        this.setTop(hb1);
        this.setCenter(sketchCanvas);
        this.setBottom(hb2);
        
        sketchCanvas.setOnMousePressed(new MouseHandler());
        sketchCanvas.setOnMouseReleased(new MouseHandler());
        sketchCanvas.setOnMouseDragged(new MouseHandler());

        x1 = y1 = 0;

        currentStrokeColor = currentFillColor = Color.BLACK;
        currentStrokeWidth = 1;

    }

    // Handler for mouse

	private class MouseHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

            // All Conditional Statements as per question

			if (radioButtonRectangle.isSelected()) {

				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

        			x1 = event.getX();
					y1 = event.getY();
					rectangle = new Rectangle();
					rectangle.setX(x1);
					rectangle.setY(y1);
					shapeList.add(rectangle);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(rectangle);

				}

				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

        			if (event.getX() - x1 < 0) {

						rectangle.setX(event.getX());

                    }

					if (event.getY() - y1 < 0) {

						rectangle.setY(event.getY());
					    rectangle.setWidth(Math.abs(event.getX() - x1));
					    rectangle.setHeight(Math.abs(event.getY() - y1));

                    }

				}

				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

        			rectangle.setFill(currentFillColor);
					rectangle.setStroke(currentStrokeColor);
					rectangle.setStrokeWidth(currentStrokeWidth);

				}
			}
                
                else if (radioButtonCircle.isSelected()) {

				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
        			
                    x1 = event.getX();
					y1 = event.getY();

					circle = new Circle();
					circle.setCenterX(x1);
					circle.setCenterY(y1);

                    circle.setFill(Color.WHITE);
					circle.setStroke(Color.BLACK);	
					shapeList.add(circle);
                    sketchCanvas.getChildren().add(circle);

				}

				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    
                    circle.setRadius(Math.abs(event.getX() - x1));

				}

				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

                    circle.setFill(currentFillColor);	
                    circle.setStroke(currentStrokeColor);
					circle.setStrokeWidth(currentStrokeWidth);

				}
			}
                else if (radioButtonLine.isSelected()) {

				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
        			
                    x1 = event.getX();
					y1 = event.getY();

					line = new Line();
					line.setStartX(x1);
					line.setStartY(y1);

					shapeList.add(line);
					line.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(line);

				}

				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                    line.setEndX(event.getX());
                    line.setEndY(event.getY());

				}

				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

					line.setStroke(currentStrokeColor);
					line.setStrokeWidth(currentStrokeWidth);

				}
			}

		}
	}

    // Handler for button
		
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
		
          Button b=(Button)event.getSource();

             if(b == undoButton){

                if(!shapeList.isEmpty()){

                    Shape s=shapeList.get(shapeList.size()-1);
                    shapeList.remove(s);
                    sketchCanvas.getChildren().remove(s);

                 } else{

                    tempList.clear();
                    sketchCanvas.getChildren().addAll(shapeList);
                    shapeList.addAll(tempList);

                }

              } else {

                if(!shapeList.isEmpty()) {

                tempList.clear();
                tempList.addAll(shapeList);
                shapeList.clear();
                sketchCanvas.getChildren().clear();

                }

            }
		}
	}

    // Handler for color

	private class ColorHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

                        @SuppressWarnings("unchecked")
                        ComboBox<String> c=(ComboBox<String>)event.getSource();
                        int colorIndex=c.getSelectionModel().getSelectedIndex();

                        if(c == fillColorCombo){

                            currentFillColor=colors[colorIndex];

                        } else {

                            currentStrokeColor=colors[colorIndex];

                        }

		}
	}

    // Handler for Width
	
	private class WidthHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event){

                        currentStrokeWidth=Integer.parseInt(strokeWidthCombo.getValue());
                        System.out.println("Stroke Width: " + currentStrokeWidth);

		}
	}

    // Math Calculations functions to return the values to original function
	
    private double getDistance(double x1, double y1, double x2, double y2)  {

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

    }

}