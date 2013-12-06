package org.eclipse.graphiti.fx.ga;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;

import javafx.scene.shape.Shape;

public abstract class FxGraphicsAlgorithm extends Shape {

	private GraphicsAlgorithm graphicsAlgorithm;
	
	public FxGraphicsAlgorithm(GraphicsAlgorithm graphicsAlgorithm) {
		super();
		this.graphicsAlgorithm = graphicsAlgorithm;
		
		// Location in case of child GA
		if (graphicsAlgorithm.getPictogramElement() == null) {
			setX(graphicsAlgorithm.getX());
			setY(graphicsAlgorithm.getY());
		}

	}

	public abstract void setX(double x);
	
	public abstract  void setY(double y);

}
