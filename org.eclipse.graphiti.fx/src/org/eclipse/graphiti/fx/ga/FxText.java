package org.eclipse.graphiti.fx.ga;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import org.eclipse.graphiti.fx.internal.util.DataTypeTransformation;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.services.Graphiti;

import com.sun.javafx.scene.control.skin.LabelSkin;

public class FxText extends FxGraphicsAlgorithm<javafx.scene.text.Text> {

	private Text text;
	private javafx.scene.text.Text fxText;

	public FxText(Text text) {
		super(text, new javafx.scene.text.Text());
		this.text = text;
		this.fxText = getShape();

		initialize();

		fxText.setText(text.getValue());
		fxText.setTextOrigin(VPos.CENTER);
		fxText.snapshot(null, null);
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
		// fxText.setTextOrigin(HPos.LEFT);
		fxText.setX(new Double(text.getX()) + (new Double(text.getWidth()) / 2d)
				- (new LabelSkinAccess(text.getValue()).computePrefWidth(1) / 2d));
	}

	@Override
	protected void setY(double y) {
		// TODO text has no size --> locate correctly
		fxText.setY(new Double(text.getY()) + (new Double(text.getHeight()) / 2d));
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

/*
 * TODO Workaround for not existing API to calculate width of string, see https://forums.oracle.com/thread/2341245
 */
class LabelSkinAccess extends LabelSkin {

	public LabelSkinAccess(String text) {
		super(new Label(text));
	}
	
	
	@Override
	public double computePrefWidth(double arg0) {
		// Public rewrite
		return super.computePrefWidth(arg0);
	}
}
