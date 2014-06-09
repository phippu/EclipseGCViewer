package foo;

import org.junit.Test;

import com.tagtraum.perf.gcviewer.model.GCModel;

public class TestFile {
	@Test
	public void loadGCModel(){
		GCModel gc = new GCModel(true);
	}
}
