package org.eclipse.graphiti.fx.connection;

import javafx.scene.shape.Shape;

import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.services.Graphiti;

public abstract class FxConnection<T extends Shape> {

	private Connection connection;
	private T shape;

	public FxConnection(Connection connection, T shape) {
		super();
		this.connection = connection;
		this.shape = shape;
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
	}

	protected abstract void setStart(ILocation location);

	protected abstract void setEnd(ILocation location);
}
