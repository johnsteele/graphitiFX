package org.eclipse.graphiti.fx.connection;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.mm.pictograms.Connection;

public class FxFreeFormConnection extends FxConnection<Polyline> {

	public FxFreeFormConnection(Connection connection, Pane connectionPane) {
		super(connection, new Polyline(), connectionPane);

		initialize();
	}

	@Override
	protected void setStart(ILocation location) {
		ObservableList<Double> points = getShape().getPoints();
		points.add(0, new Double(location.getX()));
		points.add(1, new Double(location.getY()));
	}

	@Override
	protected void setEnd(ILocation location) {
		ObservableList<Double> points = getShape().getPoints();
		getShape().getPoints().add(new Double(location.getX()));
		getShape().getPoints().add(new Double(location.getY()));
	}

}
