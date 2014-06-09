package org.gcviewer.e4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.gcviewer.e4.part.DebugMessagePart;
import org.gcviewer.e4.part.IGraphPart;

import com.tagtraum.perf.gcviewer.imp.DataReaderException;
import com.tagtraum.perf.gcviewer.imp.DataReaderFacade;
import com.tagtraum.perf.gcviewer.imp.EclipseDataReaderFacade;
import com.tagtraum.perf.gcviewer.model.GCModel;

@Creatable
@Singleton
public class Controller {

	public DebugMessagePart debugMessage;
	public GCModel model;

	public Controller() {
			System.out.println("controller constructed");
			loadFile("/Users/philipp/Documents/jdigger_workstpace/gcviewer/test.txt");
			
	}

	public void loadFile(String fileName){
		  EclipseDataReaderFacade dataReaderFacade = new EclipseDataReaderFacade();
		     try {
				model = dataReaderFacade.loadModel(fileName, false, null);
				fireModelChange();

			} catch (DataReaderException e) {
				log("cannot load file" + fileName);
				e.printStackTrace();
			}
	}

	Date startTime;
	boolean isDatestamp;
	public void setStartDate(Date time) {
		this.startTime=time;
			System.out.println("time set to  " +time);
			fireModelChange();

	}

	public void isDatestamp(boolean b) {
		isDatestamp=b;
		fireModelChange();
		
	}

	private void fireModelChange(){
		System.out.println("model changed");
		for (IGraphPart graph : graphs) {
			graph.showModel(model,isDatestamp,startTime);
		}
	}
	public boolean isDatestamp() {
		return isDatestamp;
	}
	
	public void printDetailedInformation() {
				model.printDetailedInformation();
				log(String.format("Throughput: %f", model.getThroughput()));
				log(String.format("Footprint: %d", model.getFootprintAfterFullGC().getMax()));

	}


	public void log(String message) {
		debugMessage.logMessage(message);
		debugMessage.logMessage(message);
	}

	List<IGraphPart> graphs = new ArrayList<IGraphPart>();
	
	public void registerGraph(IGraphPart memoryGraphPart) {
		graphs.add(memoryGraphPart);
		System.out.println("add graph part");
		fireModelChange();

	}

	public void axisChange(double lower, double upper) {
System.out.println("Lower: " + lower + " Upper: "+ upper);		
	}

	
}
