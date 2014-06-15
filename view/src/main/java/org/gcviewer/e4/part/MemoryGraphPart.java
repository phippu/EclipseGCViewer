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

public class MemoryGraphPart implements IGraphPart {
	private static final int BUFFER_SIZE = 1024*1024;
	Composite parent;
	Controller controller = Controller.getInstance(); 
	public GCViewerGraph xyGraph;

	Trace trace;

	public boolean isDateEnabled;

	@Inject
	public MemoryGraphPart(Composite parent) {
		this.parent = parent;
	}

	LightweightSystem lws ;
	
	@PostConstruct
	public void createControls() throws DataReaderException {
		 this.lws = new LightweightSystem(new Canvas(parent,
				SWT.NONE));
		 xyGraph = new GCViewerGraph("Memory Usage", "Time", "MB");
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
		
		final CircularBufferDataProvider trace1Provider = new CircularBufferDataProvider(
				true);
		final CircularBufferDataProvider trace2Provider = new CircularBufferDataProvider(
				true);
		trace1Provider.setBufferSize(BUFFER_SIZE);
		trace2Provider.setBufferSize(BUFFER_SIZE);

		final Iterator<GCEvent> gcEvents = model.getGCEvents();
	
int n=0;
while (gcEvents.hasNext()) {
			final GCEvent gcEvent = gcEvents.next();
			double l =GCViewerUtil.getTime(gcEvent,isDatestamp,startTime);
			trace1Provider.addSample(new Sample(l,
					gcEvent.getPreUsed() / 1024));
			trace2Provider.addSample(new Sample(l,
					gcEvent.getTotal() / 1024));
n++;
			// traceProvider.addSample(new Sample(GCViewerUtil.getTime(gcEvent),
			// gcEvent.getPause()));
		}
		System.out.println("SAMPLES ADDED: " +n);

		final Trace trace1 = new GCViewerTrace("Memory Used",
				xyGraph.primaryXAxis, xyGraph.primaryYAxis, trace1Provider);
		final Trace trace2 = new GCViewerTrace("Memory Available",
				xyGraph.primaryXAxis, xyGraph.primaryYAxis, trace2Provider);
		xyGraph.addTrace(trace1);
		xyGraph.addTrace(trace2);
		xyGraph.performAutoScale();
	}

	

}
