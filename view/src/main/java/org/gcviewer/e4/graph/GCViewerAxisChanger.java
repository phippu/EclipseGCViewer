package org.gcviewer.e4.graph;

import org.eclipse.nebula.visualization.xygraph.figures.Axis;
import org.eclipse.nebula.visualization.xygraph.figures.IAxisListener;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.graphics.Color;
import org.gcviewer.e4.Controller;

public class GCViewerAxisChanger implements IAxisListener{
	Controller controller;
	
	public GCViewerAxisChanger(Controller controller) {
		super();
		this.controller = controller;
	}

	public void axisRangeChanged(Axis axis, Range old_range, Range new_range) {

		controller.axisChange(new_range.getLower(),new_range.getUpper());

	}

	@Override
	public void axisRevalidated(Axis axis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void axisForegroundColorChanged(Axis axis, Color oldColor,
			Color newColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void axisTitleChanged(Axis axis, String oldTitle, String newTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void axisAutoScaleChanged(Axis axis, boolean oldAutoScale,
			boolean newAutoScale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void axisLogScaleChanged(Axis axis, boolean old, boolean logScale) {
		// TODO Auto-generated method stub
		
	}
}
