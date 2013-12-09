package org.eclipse.graphiti.fx.shape;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.fx.connection.FxConnection;
import org.eclipse.graphiti.fx.connection.FxFreeFormConnection;
import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.internal.util.LookManager;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.ILook;

public class FxDiagram extends Pane {

	private Pane backgroundPane;
	private Pane shapePane;
	private Pane connectionPane;

	Diagram diagram;

	public FxDiagram(Diagram diagram) {
		super();

		this.diagram = diagram;

		setPrefSize(2000, 1000); // TODO

		backgroundPane = new Pane();
		getChildren().add(backgroundPane);

		Canvas canvas = new Canvas(getPrefWidth(), getPrefHeight());
		backgroundPane.getChildren().add(canvas);

		paintGrid(canvas);

		shapePane = new Pane();
		getChildren().add(shapePane);

		EList<Shape> shapes = diagram.getChildren();
		for (Shape shape : shapes) {
			addShape(new FxShape(shape));
		}

		connectionPane = new Pane();
		getChildren().add(connectionPane);

		EList<Connection> connections = diagram.getConnections();
		for (Connection connection : connections) {
			addConnection(connection);
		}
	}

	private void addShape(FxShape shape) {
		shapePane.getChildren().add(shape);
	}

	private void addConnection(Connection connection) {
		FxConnection<?> fxConnection = null;
		if (connection instanceof FreeFormConnection) {
			fxConnection = new FxFreeFormConnection(connection, connectionPane);
		} else {
			System.err.println("ERROR: Not yet supported connection: " + connection.getClass().getName());
			return;
		}
		connectionPane.getChildren().add(fxConnection.getShape());
	}

	private void paintGrid(Canvas canvas) {

		GraphicsContext gc = canvas.getGraphicsContext2D();

		updateFromDiagram(gc);

		Color majorLineColor = getMajorLineColor();

		Color minorLineColor = getMinorLineColor();

		int gridX = diagram.getGridUnit();
		int gridY = diagram.getVerticalGridUnit();
		if (gridY == -1) {
			// No vertical grid unit set (or old diagram before 0.8): use
			// vertical grid unit
			gridY = gridX;
		}

		// TODO set diagram background color
		canvas.setStyle("-fx-background-color: white;");

		if (gridX > 0) {

			int i = 0;
			while (i % gridX != 0)
				i++;

			for (; i < canvas.getWidth(); i += gridX) {
				prepareG(gc, majorLineColor, minorLineColor, i, gridX);
				gc.strokeLine(i, 0, i, canvas.getHeight());
			}
		}

		if (gridY > 0) {
			int i = 0;
			while (i % gridY != 0) {
				i++;
			}

			for (; i < canvas.getHeight(); i += gridY) {
				prepareG(gc, majorLineColor, minorLineColor, i, gridY);
				gc.strokeLine(0, i, canvas.getWidth(), i);
			}
		}
	}

	private void updateFromDiagram(GraphicsContext gc) {
		GraphicsAlgorithm diagramGa = diagram.getGraphicsAlgorithm();

		org.eclipse.graphiti.mm.algorithms.styles.Color background = Graphiti.getGaService().getBackgroundColor(
				diagramGa, true);
		gc.setFill(DataTypeTransformation.toFxColor(background));
		org.eclipse.graphiti.mm.algorithms.styles.Color foreground = Graphiti.getGaService().getForegroundColor(
				diagramGa, true);
		gc.setStroke(DataTypeTransformation.toFxColor(foreground));
	}

	private Color getMajorLineColor() {
		return DataTypeTransformation.toFxColor(LookManager.getLook().getMajorGridLineColor());
	}

	private Color getMinorLineColor() {
		org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = null;
		if (diagram != null) {
			GraphicsAlgorithm diagramGa = diagram.getGraphicsAlgorithm();
			foregroundColor = Graphiti.getGaService().getForegroundColor(diagramGa, true);
		}
		if (foregroundColor == null) {
			return DataTypeTransformation.toFxColor(LookManager.getLook().getMinorGridLineColor());
		}
		return DataTypeTransformation.toFxColor(foregroundColor);
	}

	private void prepareG(GraphicsContext gc, Color gridColor, Color gridColorLight, int gridPosition, int gridSize) {

		int p = getGridLineAlternation() * gridSize;
		if (gridPosition % (p) == 0) {
			gc.setStroke(gridColor);
		} else {
			gc.setStroke(gridColorLight);
		}
	}

	private int getGridLineAlternation() {
		final ILook look = LookManager.getLook();
		int i = look.getMajorGridLineDistance() / look.getMinorGridLineDistance();
		return Math.max(1, i);
	}
}
