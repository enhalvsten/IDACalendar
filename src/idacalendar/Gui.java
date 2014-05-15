package idacalendar;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

//import java.util.*;

public class Gui {
	private IDACalendar calendar;

	private JFrame guiFrame;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel activitiesPanel;
	private JList<Activity> activitiesList;
	private JComboBox<String> months;
	private JComboBox<String> years;
	private JComboBox<String> days;
	private JPanel dayPanel;
	private JLabel dateLbl;
	private JPanel actPanel;
	
	private JTextField startHourTxt;
	private JTextField startMinuteTxt;
	private JTextField endHourTxt;
	private JTextField endMinuteTxt;
	private JTextField nameTxt;
	private JTextField notesTxt;
	private JTextField locTxt;

	public static void main(String[] args) {
		new Gui();
	}

	public Gui() {
		calendar = new IDACalendar();
		create();
		update();
	}

	private void create() {
		JFrame guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Calendar");
		guiFrame.setSize(550, 400);
		guiFrame.setLocationRelativeTo(null);

		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		guiFrame.add(northPanel, BorderLayout.NORTH);

		createYears();
		createMonths();
		createDays();
		
		JButton setDay = new JButton("Set Day");
		northPanel.add(setDay);
		setDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.setCurrentDay(years.getSelectedIndex() 
					+ 2010, months.getSelectedIndex(), days.getSelectedIndex() + 1);
			update();
			}
		});
		
		JButton addAct = new JButton("+");
		northPanel.add(addAct);
		addAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { addActivity(null);
			}
		});
		
		JButton prevDay = new JButton("<");
		JPanel prevDayPanel= new JPanel(new FlowLayout());
		prevDayPanel.add(prevDay);
		JButton nextDay = new JButton(">");
		JPanel nextDayPanel= new JPanel(new FlowLayout());
		nextDayPanel.add(nextDay);
		guiFrame.add(prevDayPanel, BorderLayout.WEST);
		guiFrame.add(nextDayPanel, BorderLayout.EAST);
		prevDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.yesterday();
			update();
			}
		});
		nextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { calendar.tomorrow();
			update();
			}
		});

		centerPanel = new JPanel(new BorderLayout());
		activitiesPanel = new JPanel();
		dateLbl = new JLabel();
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		datePanel.add(dateLbl);
		centerPanel.add(datePanel, BorderLayout.NORTH);
		centerPanel.add(activitiesPanel, BorderLayout.CENTER);
		guiFrame.add(centerPanel, BorderLayout.CENTER);
		
		update();
		
		guiFrame.setVisible(true);
	}

	private void createYears() {
		String[] yearList = new String[10];
		for (int i = 0; i < 10; i++) {
			yearList[i] = 2010 + i + "";
		}
		JPanel yearPanel = new JPanel();
		JLabel yearLbl = new JLabel("Year:");
		years = new JComboBox<String>(yearList);
		years.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) { createDays();
			}
		});
		yearPanel.add(yearLbl);
		yearPanel.add(years);
		northPanel.add(yearPanel, 0);
	}
	
	private void createMonths() {
		String[] monthList = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		JPanel monthPanel = new JPanel();
		JLabel monthLbl = new JLabel("Month:");
		months = new JComboBox<String>(monthList);
		
		months.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) { createDays();
			}
		});
		monthPanel.add(monthLbl);
		monthPanel.add(months);
		northPanel.add(monthPanel, 1);
	}
	
	private void createDays() {
		if (days != null) {
			northPanel.remove(dayPanel);
			northPanel.updateUI();
		}
		
		int year = years.getSelectedIndex() + 2010;
		int month = months.getSelectedIndex();
		int daysInMonth = calendar.daysInMonth(year, month);
		
		String[] dayList = new String[daysInMonth];
		for (int i = 0; i < daysInMonth; i++) {
			dayList[i] = i + 1 + "";
		}
		dayPanel = new JPanel();
		JLabel dayLbl = new JLabel("Day:");
		days = new JComboBox<String>(dayList);
		dayPanel.add(dayLbl);
		dayPanel.add(days);
		northPanel.add(dayPanel, 2);
	}
	
	private void addActivity(final Activity a) {
		final JDialog newAct = new JDialog(guiFrame, true);
		newAct.setTitle("New Activity");
		newAct.setSize(260, 550);
		newAct.setLocationRelativeTo(null);
		
		JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel largeGrid = new JPanel(new GridLayout(2, 1));
		JPanel gridPanel = new JPanel(new GridLayout(5, 1));
		largeGrid.add(gridPanel);
		flowPanel.add(largeGrid);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton saveBtn = new JButton("Save");
		buttonPanel.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) { if (a != null) {
				calendar.removeActivity(a);
			}
				calendar.addActivity(startHourTxt.getText(), startMinuteTxt.getText(), 
					endHourTxt.getText(), endMinuteTxt.getText(), nameTxt.getText(), notesTxt.getText(), locTxt.getText());
			update();
			newAct.dispose();
			}
		});
		if (a != null) {
			JButton deleteBtn = new JButton("Delete");
			buttonPanel.add(deleteBtn);
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) { calendar.removeActivity(a);
				update();
				newAct.dispose();
				}
			});
		}
		gridPanel.add(buttonPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		JLabel nameLbl = new JLabel("Activity:");
		nameTxt = new JTextField();
		namePanel.add(nameLbl, BorderLayout.NORTH);
		namePanel.add(nameTxt, BorderLayout.CENTER);
		gridPanel.add(namePanel);
		
		JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel startPanel = new JPanel(new BorderLayout());
		JLabel startLbl = new JLabel("Start:");
		startHourTxt = new JTextField(3);
		startMinuteTxt = new JTextField(3);
		startPanel.add(startLbl, BorderLayout.NORTH);
		startPanel.add(startHourTxt, BorderLayout.WEST);
		startPanel.add(new JLabel(":"), BorderLayout.CENTER);
		startPanel.add(startMinuteTxt, BorderLayout.EAST);
		
		JPanel endPanel = new JPanel(new BorderLayout());
		JLabel endLbl = new JLabel("End:");
		endHourTxt = new JTextField(3);
		endMinuteTxt = new JTextField(3);
		endPanel.add(endLbl, BorderLayout.NORTH);
		endPanel.add(endHourTxt, BorderLayout.WEST);
		endPanel.add(new JLabel(":"), BorderLayout.CENTER);
		endPanel.add(endMinuteTxt, BorderLayout.EAST);
		timePanel.add(startPanel);
		timePanel.add(endPanel);
		gridPanel.add(timePanel);
		
		JPanel locPanel = new JPanel();
		locPanel.setLayout(new BorderLayout());
		JLabel locLbl = new JLabel("Location:");
		locTxt = new JTextField();
		locPanel.add(locLbl, BorderLayout.NORTH);
		locPanel.add(locTxt, BorderLayout.CENTER);
		gridPanel.add(locPanel);
		
		JPanel notesPanel = new JPanel();
		notesPanel.setLayout(new BorderLayout());
		JLabel notesLbl = new JLabel("Notes:");
		notesTxt = new JTextField(20);
		notesPanel.add(notesLbl, BorderLayout.NORTH);
		notesPanel.add(notesTxt, BorderLayout.CENTER);
		largeGrid.add(notesPanel);
		
		if (a != null) {
			nameTxt.setText(a.getTitle());
			locTxt.setText(a.getPlace());
			notesTxt.setText(a.getText());
			startHourTxt.setText(a.getStartHour() + "");
			startMinuteTxt.setText(a.getStartMinute() + "");
			endHourTxt.setText(a.getEndHour() + "");
			endMinuteTxt.setText(a.getEndMinute() + "");
		}
		
		newAct.add(flowPanel);
		newAct.setVisible(true);
	}
	
	private void update() {
		Activity[] acts = calendar.activitiesPerDay();
		if (actPanel != null) {
			actPanel.removeAll();
		}
		if (acts != null) {
			actPanel = new JPanel(new GridLayout(acts.length, 1));
			for (final Activity a : acts) {
				JButton actBtn = new JButton(a.getTitle() + " - " + a.getStartTime());
				actBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) { addActivity(a);
					}
				});
				actPanel.add(actBtn);
			}
			activitiesPanel.add(actPanel);
		}
		activitiesPanel.updateUI();
		int year = calendar.getCurrentYear();
		int month = calendar.getCurrentMonth();
		int day = calendar.getCurrentDay() - 1;
		if (year < 2020 && year >= 2010) {
			years.setSelectedIndex(year-2010);
		}
		months.setSelectedIndex(month);
		days.setSelectedIndex(day);
		
		dateLbl.setText(year + " - " + (month + 1) + " - " + (day + 1));
	}

}
