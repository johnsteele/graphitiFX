package org.eclipse.graphiti.fx.connection;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.fx.ga.FxEllipse;
import org.eclipse.graphiti.fx.ga.FxGraphicsAlgorithm;
import org.eclipse.graphiti.fx.ga.FxPolygon;
import org.eclipse.graphiti.fx.ga.FxPolyline;
import org.eclipse.graphiti.fx.ga.FxText;
import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.services.Graphiti;

public abstract class FxConnection<T extends Shape> {

	private Connection connection;
	private T shape;
	private Pane connectionPane;

	public FxConnection(Connection connection, T shape, Pane connectionPane) {
		super();
		this.connection = connection;
		this.shape = shape;
		this.connectionPane = connectionPane;
	}

	public T getShape() {
		return shape;
	}

	protected void initialize() {
		// Start
		Anchor startAnchor = connection.getStart();
		ILocation startLocation = Graphiti.getLayoutService().getLocationRelativeToDiagram(startAnchor);
		setStart(startLocation);

		// End
		Anchor endAnchor = connection.getEnd();
		ILocation endLocation = Graphiti.getLayoutService().getLocationRelativeToDiagram(endAnchor);
		setEnd(endLocation);

		// Foreground
		if (Graphiti.getGaService().isLineVisible(connection.getGraphicsAlgorithm(), true)) {
			org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = Graphiti.getGaService()
					.getForegroundColor(connection.getGraphicsAlgorithm(), true);
			shape.setStroke(DataTypeTransformation.toFxColor(foregroundColor));
		}

		// Line width
		shape.setStrokeWidth(Graphiti.getGaService().getLineWidth(connection.getGraphicsAlgorithm(), true));
		
		// Decorators
		EList<ConnectionDecorator> decorators = connection.getConnectionDecorators();
		for (ConnectionDecorator decorator : decorators) {
			GraphicsAlgorithm ga = decorator.getGraphicsAlgorithm();

			double location = decorator.getLocation();
			ILocation offsetLocation = null;
			if (location == 0d) {
				offsetLocation = startLocation;
			} else if (location == 1d) {
				offsetLocation = endLocation;
			} else {
				System.err.println("ERROR: Not yet supported location on connection: " + location);
				continue;
			}

			FxGraphicsAlgorithm<?> fxGraphicsAlgorithm = null;
			IDimension size = Graphiti.getGaService().calculateSize(ga);
			if (ga instanceof Polygon) {
				fxGraphicsAlgorithm = new FxPolygon((Polygon) ga);
			} else if (ga instanceof Polyline) {
				fxGraphicsAlgorithm = new FxPolyline((Polyline) ga);
			} else if (ga instanceof Text) {
				fxGraphicsAlgorithm = new FxText((Text) ga);
			} else if (ga instanceof Ellipse) {
				fxGraphicsAlgorithm = new FxEllipse((Ellipse) ga);
				// centerOffset = new LocationImpl(-(ga.getWidth() / 2) - 1,
				// -(ga.getHeight() / 2) - 1);
			} else {
				System.err.println("ERROR: Not yet supported GA: " + ga.getClass().getName());
				continue;
			}
			
			Node node = (Node) fxGraphicsAlgorithm.getShape();
			node.relocate(new Double(offsetLocation.getX()) - new Double(size.getWidth()) / 2d - 1d, new Double(
					offsetLocation.getY()) - new Double(size.getHeight()) / 2d - 1d);

			connectionPane.getChildren().add(fxGraphicsAlgorithm.getShape());
		}
	}

	protected abstract void setStart(ILocation location);

	protected abstract void setEnd(ILocation location);
}
