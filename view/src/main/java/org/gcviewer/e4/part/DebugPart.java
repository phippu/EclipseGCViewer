package org.gcviewer.e4.part;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.gcviewer.e4.Controller;

public class DebugPart {
	final Map<String,File> fileMap = new HashMap<String,File>();

	Controller controller = Controller.getInstance(); 
	@Inject DebugMessagePart debugMessagePart;
	@PostConstruct
	public void createControls(Composite parent) {
		//final DebugMessagePart debugMessage = new DebugMessagePart();

		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		final FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);

		// File
		final Label fileNameText = new Label(parent, SWT.PUSH);
		fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 2, 1));
		fileNameText.setText("Directory:");
		final List fileList = new List(parent, SWT.SINGLE | SWT.V_SCROLL
				| SWT.BORDER);
		fileList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2,
				1));
		fileList.setSize(0, 20);
		fileList.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (fileList.getSelectionCount() == 1) {
					final String selectedFile = fileList.getSelection()[0];
					controller.loadFile(fileMap.get(selectedFile));
				}
			}
		});
		final Button open = new Button(parent, SWT.OPEN | SWT.NONE);
		open.setText("open");
		open.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				final String fileName = dialog.open();
				if (fileName != null) {
					File file = new File(fileName);
					fileMap.put(file.getName(), file);
					fileList.add(file.getName());
					controller.loadFile(file);
				}
			}
		});

		final Button remove = new Button(parent, SWT.OPEN | SWT.NONE);
		remove.setText("remove");
		remove.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fileList.getSelectionIndex() > -1 && fileList.getSelectionCount() == 1) {
					fileMap.remove(fileList.getSelection()[0]);					
					fileList.remove(fileList.getSelectionIndex());
				}
			}
		});

		// Start Date Part
		final Group dateMode = new Group(parent, SWT.SHADOW_IN | SWT.PUSH
				| SWT.FILL);
		dateMode.setLayout(new RowLayout(SWT.VERTICAL));
		final Button radio1 = new Button(dateMode, SWT.RADIO);
		final Button radio2 = new Button(dateMode, SWT.RADIO);
		radio1.setText("Datestamp");
		radio2.setText("Timestamp");

		radio1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				controller.isDatestamp(true);
			}
		});
		radio2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				controller.isDatestamp(false);
			}
		});
		final Label l = new Label(parent, SWT.NONE);
		final Text startDate = new Text(parent, SWT.MULTI | SWT.BORDER);
		startDate.setText("19.1.2014 19:00:00");
		startDate.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				final Display display = Display.getCurrent();
				final org.eclipse.swt.graphics.Color color = display
						.getSystemColor(SWT.COLOR_RED);
				startDate.setBackground(color);
			}
		});
		startDate.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, true,
				false, 1, 1));
		final Button setDate = new Button(parent, SWT.PUSH);
		setDate.setText("set");
		setDate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1,
				1));
		setDate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				final String date = startDate.getText();
				if (date != null) {
					final SimpleDateFormat sf = new SimpleDateFormat(
							"dd.MM.yyyy HH:mm:ss");

					try {
						final Date time = sf.parse(date);
						controller.setStartDate(time);
						final Display display = Display.getCurrent();
						final org.eclipse.swt.graphics.Color color = display
								.getSystemColor(SWT.COLOR_GREEN);
						startDate.setBackground(color);
					} catch (final ParseException e1) {
						controller.log("Cannot parse " + date);
						e1.printStackTrace();
					}
				}
			}
		});
		// END date Part

		// START button part
		final Button button = new Button(parent, SWT.PUSH);
		
		button.setText("Print Model Info");

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				controller.printDetailedInformation();
				
			}

		});

	

		// Debug Messages
debugMessagePart.createControls(parent);

	}
}
