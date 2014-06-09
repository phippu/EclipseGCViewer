package org.gcviewer.e4.part;

import java.util.Date;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.Sample;
import org.eclipse.nebula.visualization.xygraph.figures.ToolbarArmedXYGraph;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.TraceType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.gcviewer.e4.Controller;
import org.gcviewer.e4.graph.GCViewerAxisChanger;
import org.gcviewer.e4.graph.GCViewerGraph;
import org.gcviewer.e4.graph.GCViewerTrace;
import org.gcviewer.e4.graph.GCViewerUtil;

import com.tagtraum.perf.gcviewer.imp.DataReaderException;
import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;

public class CollectionGraphPart implements IGraphPart {
	Composite parent;
	@Inject
	Controller controller;
	public GCViewerGraph xyGraph;

	Trace trace;

	public boolean isDateEnabled;

	@Inject
	public CollectionGraphPart(Composite parent) {
		this.parent = parent;
	}

	@PostConstruct
	public void createControls() throws DataReaderException {
		final LightweightSystem lws = new LightweightSystem(new Canvas(parent,
				SWT.NONE));
		xyGraph = new GCViewerGraph("Garbage Collection Time", "Time",
				"Duration [s]");
		xyGraph.primaryXAxis.addListener(new GCViewerAxisChanger(controller));
		final ToolbarArmedXYGraph toolbarArmedXYGraph = new ToolbarArmedXYGraph(
				xyGraph);
		lws.getRootFigure().add(toolbarArmedXYGraph);
		lws.setContents(xyGraph);
		controller.registerGraph(this);

	}

	@Override
	public void showModel(GCModel model, boolean isDatestamp, Date startTime) {

		xyGraph.clear();
		xyGraph.setDateEnabled(isDatestamp);
		final CircularBufferDataProvider traceProvider = new CircularBufferDataProvider(
				true);
		final Iterator<GCEvent> gcEvents = model.getGCEvents();
		while (gcEvents.hasNext()) {
			final GCEvent gcEvent = gcEvents.next();

			traceProvider.addSample(new Sample(GCViewerUtil.getTime(gcEvent,
					isDatestamp, startTime), gcEvent.getPause()));
		}

		final Trace trace = new GCViewerTrace("Collection Time",
				xyGraph.primaryXAxis, xyGraph.primaryYAxis, traceProvider);
		trace.setTraceType(TraceType.POINT);
		xyGraph.addTrace(trace);
		xyGraph.performAutoScale();
	}

}
