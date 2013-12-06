package org.eclipse.graphiti.fx.ga;

import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.services.Graphiti;


public class FxText extends FxGraphicsAlgorithm<javafx.scene.text.Text> {

	private Text text;
	private javafx.scene.text.Text fxText;

	public FxText(Text text) {
		super(text, new javafx.scene.text.Text());
		this.text = text;
		this.fxText = getShape();

		initialize();
		
		fxText.setText(text.getValue());
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		// TODO alignment
		// setTextAlignment(TextAlignment.CENTER);
		org.eclipse.graphiti.mm.algorithms.styles.Font font = Graphiti
				.getGaService().getFont(text, true);
		fxText.setFont(Font.font(font.getName(), font.getSize()));
	}

	@Override
	protected void setX(double x) {
		// TODO text has no size --> locate correctly
		fxText.setX(text.getX() + text.getWidth() / 2);
	}

	@Override
	protected void setY(double y) {
		// TODO text has no size --> locate correctly
		fxText.setY(text.getY() + text.getHeight() / 2);
	}

	@Override
	protected void setWidth(double width) {
		// Text has no width
	}

	@Override
	protected void setHeight(double height) {
		// Text has no height
	}
}
