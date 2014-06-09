package org.gcviewer.e4.part;

import java.util.Date;

import com.tagtraum.perf.gcviewer.model.GCModel;

public interface IGraphPart {

	void showModel(GCModel model, boolean isDatestamp, Date startTime);

}
