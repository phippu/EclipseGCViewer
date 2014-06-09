package org.gcviewer.e4.graph;

import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Axis;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;

public class GCViewerTrace extends Trace{

	public GCViewerTrace(String name, Axis xAxis, Axis yAxis,
			IDataProvider dataProvider) {
		super(name, xAxis, yAxis, dataProvider);
		this.setName(name);
		this.setLineWidth(1);
		this.setPointStyle(PointStyle.POINT);
		this.setPointSize(4);
		this.setBaseLine(BaseLine.NEGATIVE_INFINITY);
		this.setAreaAlpha(100);
		this.setAntiAliasing(true);
		this.setErrorBarEnabled(false);
		this.setYErrorBarType(ErrorBarType.BOTH);
		this.setXErrorBarType(ErrorBarType.NONE);
		this.setErrorBarCapWidth(3);
	}

}
