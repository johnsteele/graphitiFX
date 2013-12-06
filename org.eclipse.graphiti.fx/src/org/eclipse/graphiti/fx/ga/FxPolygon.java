package org.eclipse.graphiti.fx.ga;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.styles.Point;

public class FxPolygon extends FxGraphicsAlgorithm<javafx.scene.shape.Polygon> {

	private Polygon polygon;
	private javafx.scene.shape.Polygon fxPolygon;

	public FxPolygon(Polygon polygon) {
		super(polygon, new javafx.scene.shape.Polygon());
		this.polygon = polygon;
		this.fxPolygon = getShape();

		initialize();
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		// Points
		EList<Point> points = polygon.getPoints();
		ArrayList<Double> list = new ArrayList<>(points.size());
		for (Point point : points) {
			list.add(new Double(point.getX()));
			list.add(new Double(point.getY()));
		}
		fxPolygon.getPoints().addAll(list);
	}

	@Override
	protected void setX(double x) {
		// Nothing to do
	}

	@Override
	protected void setY(double y) {
		// Nothing to do
	}

	@Override
	protected void setWidth(double width) {
		// Nothing to do
	}

	@Override
	protected void setHeight(double height) {
		// Nothing to do
	}
}
