package org.eclipse.graphiti.fx.internal.util;

import org.eclipse.graphiti.util.IColorConstant;

import javafx.scene.paint.Color;

public class DataTypeTransformation {

	public static Color toFxColor(IColorConstant color) {
		return Color.color(new Double(color.getRed()) / 256d, new Double(color.getGreen() / 256d),
				new Double(color.getBlue() / 256d));
	}

	public static Color toFxColor(
			org.eclipse.graphiti.mm.algorithms.styles.Color pictogramsColor) {
		return Color.color(new Double(pictogramsColor.getRed()) / 256d,
				new Double(pictogramsColor.getGreen()) / 256d, new Double(
						pictogramsColor.getBlue()) / 256d);
	}
}
