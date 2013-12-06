package myfxviewpart.views;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import myfxviewpart.DataTypeTransformation;

import org.eclipse.graphiti.services.Graphiti;

;

public class FxText extends Text {

	private org.eclipse.graphiti.mm.algorithms.Text text;

	public FxText(org.eclipse.graphiti.mm.algorithms.Text text) {
		super();
		this.text = text;

		setText(text.getValue());

		// Location in case of child GA
		if (text.getPictogramElement() == null) {
			// TODO text has no size --> locate correctly
			setX(text.getX() + text.getWidth() / 2);
			setY(text.getY() + text.getHeight() / 2);
		}

		// Background
		if (text.getFilled()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color backgroundColor = Graphiti
					.getGaService().getBackgroundColor(text, true);
			if (backgroundColor != null) {
				setFill(DataTypeTransformation.toFxColor(backgroundColor));
			}
			setOpacity(1d - Graphiti.getGaService().getTransparency(text, true));
		}

		// Foreground
		if (text.getLineVisible()) {
			org.eclipse.graphiti.mm.algorithms.styles.Color foregroundColor = Graphiti
					.getGaService().getForegroundColor(text, true);
			if (foregroundColor != null) {
				setStroke(DataTypeTransformation.toFxColor(foregroundColor));
			}
		}

		// Text specific
		// TODO alignment
		// setTextAlignment(TextAlignment.CENTER);
		org.eclipse.graphiti.mm.algorithms.styles.Font font = Graphiti
				.getGaService().getFont(text, true);
		setFont(Font.font(font.getName(), font.getSize()));

		// Line width
		setStrokeWidth(Graphiti.getGaService().getLineWidth(text, true));
	}
}
