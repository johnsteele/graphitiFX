package org.eclipse.graphiti.fx.ga;

import org.eclipse.graphiti.mm.algorithms.Ellipse;

public class FxEllipse extends FxGraphicsAlgorithm<javafx.scene.shape.Ellipse> {

	private Ellipse ellipse;
	private javafx.scene.shape.Ellipse fxEllipse;

	public FxEllipse(org.eclipse.graphiti.mm.algorithms.Ellipse ellipse) {
		super(ellipse, new javafx.scene.shape.Ellipse());
		this.ellipse = ellipse;
		this.fxEllipse = getShape();

		initialize();
	}

	@Override
	protected void setX(double x) {
		fxEllipse.setCenterX(x + ellipse.getWidth() / 2d);
	}

	@Override
	protected void setY(double y) {
		fxEllipse.setCenterY(y + ellipse.getHeight() / 2d);
	}

	@Override
	protected void setWidth(double width) {
		fxEllipse.setRadiusX(width / 2d);
	}

	@Override
	protected void setHeight(double height) {
		fxEllipse.setRadiusY(height / 2d);
	}
}
