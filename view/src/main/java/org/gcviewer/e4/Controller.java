package org.gcviewer.e4;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gcviewer.e4.part.DebugMessagePart;
import org.gcviewer.e4.part.IGraphPart;

import com.tagtraum.perf.gcviewer.imp.DataReaderException;
import com.tagtraum.perf.gcviewer.imp.EclipseDataReaderFacade;
import com.tagtraum.perf.gcviewer.model.GCModel;

public class Controller {

	private DebugMessagePart debugMessagePart;
	private GCModel model;
	private static Controller controller;
	public static Controller getInstance(){
		if(controller ==null)controller=new Controller();
		return controller;
	}
	private Controller() {
			System.out.println("controller constructed");
			loadFile(new File("/Users/philipp/Documents/jdigger_workstpace/gcviewer/test.txt"));
			
	}

	public void loadFile(File file){
		  EclipseDataReaderFacade dataReaderFacade = new EclipseDataReaderFacade();
		     try {
				model = dataReaderFacade.loadModel(file.getAbsolutePath(), false, null);
				System.out.println("MODEL START TIME: " +model.getFirstPauseTimeStamp());
				System.out.println("MODEL END TIME: " +model.getLastPauseTimeStamp());
				fireModelChange();

			} catch (DataReaderException e) {
				log("cannot load file" + file);
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
		if(debugMessagePart!=null){
		debugMessagePart.logMessage(message);
		debugMessagePart.logMessage(message);}
	}

	private List<IGraphPart> graphs =  Collections.synchronizedList(new ArrayList<IGraphPart>());
	
	public void registerGraph(IGraphPart memoryGraphPart) {
		graphs.add(memoryGraphPart);
		System.out.println("add graph part");

	}

	public void axisChange(double lower, double upper) {
System.out.println("Lower: " + lower + " Upper: "+ upper);		
	}
	public void registerDebugMessagePart(DebugMessagePart debugMessagePart) {
		this.debugMessagePart = debugMessagePart;
	}

	
}
