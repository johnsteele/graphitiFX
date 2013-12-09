package org.eclipse.graphiti.fx.shape;

import javafx.scene.layout.Pane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.fx.ga.FxEllipse;
import org.eclipse.graphiti.fx.ga.FxGraphicsAlgorithm;
import org.eclipse.graphiti.fx.ga.FxPolygon;
import org.eclipse.graphiti.fx.ga.FxPolyline;
import org.eclipse.graphiti.fx.ga.FxRectangle;
import org.eclipse.graphiti.fx.ga.FxText;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class FxShape extends Pane {

	private Shape shape;
	private FxGraphicsAlgorithm<?> fxGraphicsAlgorithm = null;
	private Pane shapePane;

	public FxShape(Shape shape) {
		super();
		this.shape = shape;

		GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();

		relocate(ga.getX(), ga.getY());
		setWidth(ga.getWidth());
		setHeight(ga.getHeight());

		addGraphicsAlgorithm(ga);

		shapePane = new Pane();
		getChildren().add(shapePane);

		if (shape instanceof ContainerShape) {
			EList<Shape> children = ((ContainerShape) shape).getChildren();
			for (Shape innerShape : children) {
				addShape(new FxShape(innerShape));
			}
		}
	}

	private void addShape(FxShape shape) {
		shapePane.getChildren().add(shape);
	}

	private void addGraphicsAlgorithm(GraphicsAlgorithm ga) {
		if (ga instanceof Rectangle) {
			fxGraphicsAlgorithm = new FxRectangle((Rectangle) ga);
		} else if (ga instanceof Polygon) {
			fxGraphicsAlgorithm = new FxPolygon((Polygon) ga);
		} else if (ga instanceof Polyline) {
			fxGraphicsAlgorithm = new FxPolyline((Polyline) ga);
		} else if (ga instanceof Text) {
			fxGraphicsAlgorithm = new FxText((Text) ga);
		} else if (ga instanceof Ellipse) {
			fxGraphicsAlgorithm = new FxEllipse((Ellipse) ga);
		} else {
			System.err.println("ERROR: Not yet supported GA: " + ga.getClass().getName());
			return;
		}

		getChildren().add(fxGraphicsAlgorithm.getShape());

		// GA children
		EList<GraphicsAlgorithm> children = ga.getGraphicsAlgorithmChildren();
		for (GraphicsAlgorithm innerGa : children) {
			addGraphicsAlgorithm(innerGa);
		}
	}
}
