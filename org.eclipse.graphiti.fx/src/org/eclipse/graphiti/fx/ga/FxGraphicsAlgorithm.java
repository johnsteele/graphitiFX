package org.eclipse.graphiti.fx.ga;

import javafx.scene.shape.Shape;

import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.services.Graphiti;

public abstract class FxGraphicsAlgorithm<T extends Shape> {

	private GraphicsAlgorithm graphicsAlgorithm;
	private T shape;

	public FxGraphicsAlgorithm(GraphicsAlgorithm graphicsAlgorithm, T shape) {
		super();
		this.graphicsAlgorithm = graphicsAlgorithm;
		this.shape = shape;
	}

	protected void initialize() {
		// Location in case of child GA
		if (graphicsAlgorithm.getPictogramElement() == null) {
			setX(graphicsAlgorithm.getX());
			setY(graphicsAlgorithm.getY());
		}

		// Dimensions
		setWidth(graphicsAlgorithm.getWidth());
		setHeight(graphicsAlgorithm.getHeight());

		// Background
		if (Graphiti.getGaService().isFilled(graphicsAlgorithm, true)) {
			org.eclipse.graphiti.mm.algorithms.styles.Color backgroundColor = Graphiti
					.getGaService().getBackgroundColor(graphicsAlgorithm, true);
			shape.setFill(DataTypeTransformation.toFxColor(backgroundColor));
			shape.setOpacity(1d - Graphiti.getGaService().getTransparency(
					graphicsAlgorithm, true));
		}

		// Foreground
		if (Graphiti.getGaService().isLineVisible(graphicsAlgorithm, true)) {
			org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = Graphiti
					.getGaService().getForegroundColor(graphicsAlgorithm, true);
			shape.setStroke(DataTypeTransformation.toFxColor(foregroundColor));
		}

		// Line width
		shape.setStrokeWidth(Graphiti.getGaService().getLineWidth(
				graphicsAlgorithm, true));
	}

	public T getShape() {
		return shape;
	}

	protected abstract void setX(double x);

	protected abstract void setY(double y);

	protected abstract void setWidth(double width);

	protected abstract void setHeight(double height);
}
