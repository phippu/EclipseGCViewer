package org.gcviewer.e4.graph;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;

public class GCViewerGraph extends XYGraph {

	Set<Trace> traces = new HashSet<Trace>();

	public GCViewerGraph(String title, String xAxisTitle, String yAxisTitle) {
		super();
		setFocusTraversable(true);
		setRequestFocusEnabled(true);
		setTitle(title);
		setFont(XYGraphMediaFactory.getInstance().getFont(
				XYGraphMediaFactory.FONT_TAHOMA));
		primaryXAxis.setTitle(xAxisTitle);
		primaryYAxis.setTitle(yAxisTitle);
		primaryYAxis.setAutoScale(true);
		primaryXAxis.setAutoScale(true);
		primaryXAxis.setShowMajorGrid(true);
		primaryYAxis.setShowMajorGrid(true);
		primaryXAxis.setAutoScaleThreshold(0);
	}

	public void addTrace(Trace trace) {
		traces.add(trace);
		System.out.println("add trace");
		super.addTrace(trace);
	}

	public void clear() {

		for (final Trace trace : traces) {
			primaryYAxis.removeTrace(trace);
			primaryXAxis.removeTrace(trace);
			removeTrace(trace);
		}
		traces.clear();
	}

	public void setDateEnabled(boolean isDatestamp) {
		if(isDatestamp){
			this.primaryXAxis.setDateEnabled(true);

		}else{
			this.primaryXAxis.setDateEnabled(false);

		}		
	}

}
