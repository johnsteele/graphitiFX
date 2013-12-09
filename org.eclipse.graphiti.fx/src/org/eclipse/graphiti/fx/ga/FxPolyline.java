package org.eclipse.graphiti.fx.ga;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;

public class FxPolyline extends FxGraphicsAlgorithm<javafx.scene.shape.Polyline> {

	private Polyline polyline;
	private javafx.scene.shape.Polyline fxPolyline;

	public FxPolyline(Polyline polyline) {
		super(polyline, new javafx.scene.shape.Polyline());
		this.polyline = polyline;
		this.fxPolyline = getShape();

		initialize();
	}

	@Override
	protected void initialize() {
		super.initialize();

		// Points
		EList<Point> points = polyline.getPoints();
		ArrayList<Double> list = new ArrayList<>(points.size());
		for (Point point : points) {
			list.add(new Double(point.getX()));
			list.add(new Double(point.getY()));
		}
		fxPolyline.getPoints().addAll(list);
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
