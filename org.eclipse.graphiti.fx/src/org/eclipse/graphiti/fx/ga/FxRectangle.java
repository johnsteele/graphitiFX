package org.eclipse.graphiti.fx.ga;

import org.eclipse.graphiti.mm.algorithms.Rectangle;

;

public class FxRectangle extends
		FxGraphicsAlgorithm<javafx.scene.shape.Rectangle> {

	private Rectangle rectangle;
	private javafx.scene.shape.Rectangle fxRectangle;

	public FxRectangle(org.eclipse.graphiti.mm.algorithms.Rectangle rectangle) {
		super(rectangle, new javafx.scene.shape.Rectangle());
		this.rectangle = rectangle;
		this.fxRectangle = getShape();
		
		initialize();
	}

	@Override
	protected void setX(double x) {
		fxRectangle.setX(x);
	}

	@Override
	protected void setY(double y) {
		fxRectangle.setY(y);
	}

	@Override
	protected void setWidth(double width) {
		fxRectangle.setWidth(width);
	}

	@Override
	protected void setHeight(double height) {
		fxRectangle.setHeight(height);
	}
}
