package org.gcviewer.e4.part;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.gcviewer.e4.Controller;

@Creatable
public class DebugMessagePart {
	Text debugText;
	@Inject Controller controller;
	
	
	public void createControls(Composite parent) {
		controller.debugMessage = this;
		debugText = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		debugText.setSize(100, 50);
		final GridData data = new GridData();
		data.heightHint = 50;
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		data.verticalSpan = 2;
		data.horizontalSpan = 2;
		debugText.setLayoutData(data);
	}

	public void logMessage(String message) {
		debugText.append(message);
		debugText.append("\n");
	}
}
