package org.gcviewer.e4.graph;

import java.util.Calendar;
import java.util.Date;

import com.tagtraum.perf.gcviewer.model.GCEvent;

public class GCViewerUtil {

	public static double getTime(GCEvent gcEvent) {
		return gcEvent.getTimestamp();
	}

	public static double getTime(GCEvent gcEvent, boolean isDatestamp,
			Date startTime) {
		double time;
		if(isDatestamp) {
			if(gcEvent.getDatestamp()==null){
				System.out.println("no datestamp in log FALLBACK");
				Calendar cal = Calendar.getInstance();
				cal.setTime(startTime);
				cal.add(Calendar.MILLISECOND, (int)gcEvent.getTimestamp());
				time = cal.getTime().getTime();

			}else{
				 time = gcEvent.getDatestamp().getTime();

			}

		}else{
			 
			time = gcEvent.getTimestamp();
		}
		return time;
	}

}
