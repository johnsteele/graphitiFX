package myfxviewpart.views;

import java.util.ArrayList;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;
import myfxviewpart.DataTypeTransformation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.services.Graphiti;

;

public class FxPolygon extends Polygon {

	private org.eclipse.graphiti.mm.algorithms.Polygon polygon;

	public FxPolygon(org.eclipse.graphiti.mm.algorithms.Polygon polygon) {
		super();
		this.polygon = polygon;

//		// Location in case of child GA
//		if (polygon.getPictogramElement() == null) {
//			setX(polygon.getX());
//			setY(polygon.getY());
//		}

		// Points
		EList<Point> points = polygon.getPoints();
		ArrayList<Double> list = new ArrayList<>(points.size());
		for (Point point : points) {
			list.add(new Double(point.getX()));
			list.add(new Double(point.getY()));
		}
		getPoints().addAll(list);

		// Background
		if (polygon.getFilled()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color backgroundColor = Graphiti
					.getGaService().getBackgroundColor(polygon, true);
			if (backgroundColor != null) {
				setFill(DataTypeTransformation.toFxColor(backgroundColor));
			}
			setOpacity(1d - Graphiti.getGaService().getTransparency(polygon, true));
		}

		// Foreground
		if (polygon.getLineVisible()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = Graphiti
					.getGaService().getForegroundColor(polygon, true);
			if (foregroundColor != null) {
				setStroke(DataTypeTransformation.toFxColor(foregroundColor));
			}
		}
		
		// Line width
		setStrokeWidth(Graphiti.getGaService().getLineWidth(polygon, true));
		
		// Other
		setStrokeLineJoin(StrokeLineJoin.ROUND);
	}
}
