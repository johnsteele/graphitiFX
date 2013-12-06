package myfxviewpart.views;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.services.Graphiti;

import myfxviewpart.DataTypeTransformation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

;

public class FxRectangle extends Rectangle {

	private org.eclipse.graphiti.mm.algorithms.Rectangle rectangle;

	public FxRectangle(org.eclipse.graphiti.mm.algorithms.Rectangle rectangle) {
		super();
		this.rectangle = rectangle;

		// Location in case of child GA
		if (rectangle.getPictogramElement() == null) {
			setX(rectangle.getX());
			setY(rectangle.getY());
		}

		// Dimensions
		setWidth(rectangle.getWidth());
		setHeight(rectangle.getHeight());

		// Background
		if (rectangle.getFilled()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color backgroundColor = Graphiti
					.getGaService().getBackgroundColor(rectangle, true);
			if (backgroundColor != null) {
				setFill(DataTypeTransformation.toFxColor(backgroundColor));
			}
			setOpacity(1d - Graphiti.getGaService().getTransparency(rectangle, true));
		}

		// Foreground
		if (rectangle.getLineVisible()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = Graphiti
					.getGaService().getForegroundColor(rectangle, true);
			if (foregroundColor != null) {
				setStroke(DataTypeTransformation.toFxColor(foregroundColor));
			}
		}

		// Line width
		setStrokeWidth(Graphiti.getGaService().getLineWidth(rectangle, true));
	}
}
