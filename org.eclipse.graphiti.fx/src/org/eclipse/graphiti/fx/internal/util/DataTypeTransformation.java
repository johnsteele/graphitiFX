package org.eclipse.graphiti.fx.internal.util;

import javafx.scene.paint.Color;

import org.eclipse.graphiti.util.IColorConstant;

public class DataTypeTransformation {

	public static Color toFxColor(IColorConstant color) {
		if (color != null) {
			return Color.color(new Double(color.getRed()) / 256d, new Double(color.getGreen() / 256d),
					new Double(color.getBlue() / 256d));
		} else {
			return getDefaultColor();
		}
	}

	public static Color toFxColor(org.eclipse.graphiti.mm.algorithms.styles.Color pictogramsColor) {
		if (pictogramsColor != null) {
			return Color.color(new Double(pictogramsColor.getRed()) / 256d,
					new Double(pictogramsColor.getGreen()) / 256d, new Double(pictogramsColor.getBlue()) / 256d);
		} else {
			return getDefaultColor();
		}
	}

	private static Color getDefaultColor() {
		return Color.color(new Double(100) / 256d, new Double(100) / 256d, new Double(100) / 256d);
	}
}
