package myfxviewpart.views;

import javafx.scene.layout.Pane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class FxShape extends Pane {

	private Shape shape;
	private javafx.scene.shape.Shape fxShape = null;
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
			fxShape = new FxRectangle((Rectangle) ga);
		} else if (ga instanceof Polygon) {
			fxShape = new FxPolygon((Polygon) ga);
		} else if (ga instanceof Text) {
			fxShape = new FxText((Text) ga);
		} else {
			System.err.println("Not yet supported GA: "
					+ ga.getClass().getName());
			return;
		}

		getChildren().add(fxShape);

		// GA children
		EList<GraphicsAlgorithm> children = ga.getGraphicsAlgorithmChildren();
		for (GraphicsAlgorithm innerGa : children) {
			addGraphicsAlgorithm(innerGa);
		}
	}
}
