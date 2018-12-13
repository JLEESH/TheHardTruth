/*
 * Copyright (C) 2018 leeseungha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package thehardtruth;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;

/**
 * The main frame of the application.
 */
public final class TheHardTruth extends JFrame {
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 600;
    private static final String FRAME_TITLE = "The Hard Truth";
    private static final int LIST_HEIGHT = 400;
    private static final int LIST_WIDTH = 200;
    private static final int FIELD_HEIGHT = 20;
    private static final int FIELD_WIDTH = 100;
    private static final Font SMALLER_FONT = new Font(UIManager.getFont("Button.font").getFontName(), Font.PLAIN, 9);
    
    private DefaultListModel<String> times;
    private final DecimalFormat format = new DecimalFormat("#0.00");
    private final DecimalFormat longerFormat = new DecimalFormat("#0.00000");
    private SolveHandler solveHandler;
    private Statistics statistics;
    private double testTime;
    private double customConfidenceLevel;
    private double customPredictiveConfidenceLevel;
    
    private JLabel timeLabel;
    private JLabel errorLabel;
    private JButton loadTimesButton;
    private JButton saveTimesButton;
    private JButton addTimeButton;
    private JButton deleteTimeButton;
    private JButton deleteAllTimesButton;
    private JButton changeTimeButton;
    private JButton refreshStatsButton;
    private JList<Solve> timeList;
    private JScrollPane timeScrollPane;
    
    //Solve components.
    private JLabel timeEditLabel;
    private JFormattedTextField timeEditField;
    private JLabel timeTakenLabel;
    private JFormattedTextField timeTakenField;
    private JLabel timeMeasureLabel;
    
    //Statistics components.
    private JLabel sampleSizeLabel;
    private JLabel sampleMeanLabel;
    private JLabel sampleVarianceLabel;
    private JLabel sampleStdDevLabel;
    private JLabel semLabel;
    private JLabel testTimeLabel;
    private JFormattedTextField testTimeField;
    private JLabel tValueLabel;
    private JLabel pValueLabel;
    private JLabel confidenceIntervalLabel;
    private JLabel predictionIntervalLabel;
    private JLabel customConfidenceLevelLabel;
    private JFormattedTextField customConfidenceLevelField;
    private JLabel customPredictiveConfidenceLevelLabel;
    private JFormattedTextField customPredictiveConfidenceLevelField;
    private JLabel customConfidenceIntervalLabel;
    private JLabel customPredictionIntervalLabel;
    
    /**
     * Initialises the frame without displaying it.
     */
    public TheHardTruth() {
        initFrame();
    }
    
    /**
     * Actual initialisation occurs here.
     */
    private void initFrame() {
        //Initialising variables and components.
        times = new DefaultListModel<>();
        solveHandler = new SolveHandler();
        statistics = null;
        testTime = 10;
        customConfidenceLevel = 0.99;
        customPredictiveConfidenceLevel = 0.99;
        
        timeLabel = new JLabel("Times: ");
        errorLabel = new JLabel("");
        loadTimesButton = new JButton("Load Times");
        saveTimesButton = new JButton("Save Times");
        addTimeButton = new JButton("Add Time");
        deleteTimeButton = new JButton("Delete Time");
        deleteAllTimesButton = new JButton("Delete All Times");
        changeTimeButton = new JButton("Change Time");
        refreshStatsButton = new JButton("Refresh Statistics");
        timeList = new JList(times);
        timeScrollPane = new JScrollPane(timeList);
        
        //Initialising Solve components.
        timeEditLabel = new JLabel("Solve Time: ");
        timeEditField = new JFormattedTextField(format);
        timeTakenLabel = new JLabel("Time taken: ");
        timeTakenField = new JFormattedTextField(format);
        timeMeasureLabel = new JLabel("Press space to start timing.");//Timer begins when the spacebar is released.
        
        //Initialising statistics components.
        sampleSizeLabel = new JLabel("Number of Solves: ");
        sampleMeanLabel = new JLabel("Sample Mean: ");
        sampleVarianceLabel = new JLabel("Sample Variance: ");
        sampleStdDevLabel = new JLabel("Sample Standard Deviation: ");
        semLabel = new JLabel("Standard Error of Mean: ");
        testTimeLabel = new JLabel("Time to Test: ");
        testTimeField = new JFormattedTextField(format);
        testTimeField.setText(format.format(testTime));
        tValueLabel = new JLabel("t-value: ");
        pValueLabel = new JLabel("p-value: ");
        confidenceIntervalLabel = new JLabel("Confidence Interval (95%): ");
        predictionIntervalLabel = new JLabel("Prediction Interval (95%): ");
        customConfidenceLevelLabel = new JLabel("Desired level of confidence: ");
        customConfidenceLevelField = new JFormattedTextField(format);
        customConfidenceLevelField.setText(format.format(customConfidenceLevel));
        customPredictiveConfidenceLevelLabel = new JLabel("Desired level of predictive confidence: ");
        customPredictiveConfidenceLevelField = new JFormattedTextField(format);
        customPredictiveConfidenceLevelField.setText(format.format(customPredictiveConfidenceLevel));
        customConfidenceIntervalLabel = new JLabel("Confidence Interval (custom): ");
        customPredictionIntervalLabel = new JLabel("Prediction Interval (custom): ");
        
        //Setting component properties.
        timeList.setFixedCellHeight(20);
        timeList.setFixedCellWidth(200);
        
        timeScrollPane.setPreferredSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
        testTimeField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        timeEditField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        timeTakenField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        customPredictiveConfidenceLevelField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        customConfidenceLevelField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        
        addTimeButton.setFont(SMALLER_FONT);
        deleteTimeButton.setFont(SMALLER_FONT);
        deleteAllTimesButton.setFont(SMALLER_FONT);
        changeTimeButton.setFont(SMALLER_FONT);
        
        timeTakenField.setEditable(false);
        
        //Adding components.
        add(timeLabel);
        add(errorLabel);
        add(loadTimesButton);
        add(saveTimesButton);
        add(addTimeButton);
        add(deleteTimeButton);
        add(deleteAllTimesButton);
        add(changeTimeButton);
        add(refreshStatsButton);
        add(timeScrollPane);
        
        //Adding Solve components.
        add(timeEditLabel);
        add(timeEditField);
        add(timeTakenLabel);
        add(timeTakenField);
        add(timeMeasureLabel);
        
        //Adding statistics components.
        add(sampleSizeLabel);
        add(sampleMeanLabel);
        add(sampleVarianceLabel);
        add(sampleStdDevLabel);
        add(semLabel);
        add(testTimeLabel);
        add(testTimeField);
        add(tValueLabel);
        add(pValueLabel);
        add(confidenceIntervalLabel);
        add(predictionIntervalLabel);
        add(customConfidenceLevelLabel);
        add(customConfidenceLevelField);
        add(customPredictiveConfidenceLevelLabel);
        add(customPredictiveConfidenceLevelField);
        add(customConfidenceIntervalLabel);
        add(customPredictionIntervalLabel);
        
        //Setting properties of the main frame.
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
        setFocusable(true);
        
        //To give back focus to the main frame.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
            }
        });
        
        //To add timing functions.
        //Times are truncated instead of rounded per cubing norms
        //(cf. truncation of times produced by StackMat ProTimer Gen 3).
        addKeyListener(new KeyAdapter() {
            private boolean isTiming = false;
            private boolean spaceStop = false;
            double startTime;//For code readability.
            double timeTaken;
            
            private Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeTaken = Double.parseDouble(format.format((System.currentTimeMillis() - startTime) / 1000));
                    timeMeasureLabel.setText(format.format(timeTaken));
                }
            });
            
            @Override
            public void keyReleased(KeyEvent e) {
                if (spaceStop == false) {
                    if (isTiming == false && e.getKeyCode() == KeyEvent.VK_SPACE) {
                        timer.start();
                        startTime = System.currentTimeMillis();
                        System.out.println("Timing started.");
                        isTiming = true;
                    }
                } else {
                    spaceStop = false;
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (isTiming) {
                    timer.stop();
                    System.out.println("Timing stopped.");
                    isTiming = false;
                    
                    timeTakenField.setText(format.format(timeTaken));
                    solveHandler.addSolve(new Solve(timeTaken));
                    times.addElement(format.format(timeTaken));
                    
                    refreshStats();
                    errorLabel.setText("Solve complete. Time added.");
                    System.out.println("Solve complete. Time added.");
                    timeTaken = 0;
                    
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        spaceStop = true;
                    }
                }
            }
        });
        
        //Layout settings.
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        //Layout constraints.
        layout.putConstraint(SpringLayout.NORTH, timeScrollPane, 5, SpringLayout.SOUTH, timeLabel);
        layout.putConstraint(SpringLayout.NORTH, loadTimesButton, 5, SpringLayout.SOUTH, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, errorLabel, 5, SpringLayout.SOUTH, loadTimesButton);
        layout.putConstraint(SpringLayout.WEST, saveTimesButton, 5, SpringLayout.EAST, loadTimesButton);
        layout.putConstraint(SpringLayout.NORTH, saveTimesButton, 5, SpringLayout.SOUTH, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, changeTimeButton, 1, SpringLayout.NORTH, timeScrollPane);
        layout.putConstraint(SpringLayout.WEST, changeTimeButton, 5, SpringLayout.EAST, timeEditField);
        layout.putConstraint(SpringLayout.NORTH, addTimeButton, 5, SpringLayout.SOUTH, timeEditField);
        layout.putConstraint(SpringLayout.WEST, addTimeButton, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, deleteTimeButton, 5, SpringLayout.SOUTH, timeEditField);
        layout.putConstraint(SpringLayout.WEST, deleteTimeButton, 5, SpringLayout.EAST, addTimeButton);
        layout.putConstraint(SpringLayout.NORTH, deleteAllTimesButton, 5, SpringLayout.SOUTH, timeEditField);
        layout.putConstraint(SpringLayout.WEST, deleteAllTimesButton, 5, SpringLayout.EAST, deleteTimeButton);
        layout.putConstraint(SpringLayout.NORTH, refreshStatsButton, 15, SpringLayout.SOUTH, errorLabel);
        
        //Layout constraints for Solve components.
        layout.putConstraint(SpringLayout.NORTH, timeEditLabel, 5, SpringLayout.NORTH, timeScrollPane);
        layout.putConstraint(SpringLayout.WEST, timeEditLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, timeEditField, 3, SpringLayout.NORTH, timeScrollPane);
        layout.putConstraint(SpringLayout.WEST, timeEditField, 3, SpringLayout.EAST, timeEditLabel);
        layout.putConstraint(SpringLayout.NORTH, timeTakenLabel, 100, SpringLayout.SOUTH, loadTimesButton);
        layout.putConstraint(SpringLayout.NORTH, timeTakenField, 100 - 3, SpringLayout.SOUTH, loadTimesButton);
        layout.putConstraint(SpringLayout.WEST, timeTakenField, 3, SpringLayout.EAST, timeTakenLabel);
        layout.putConstraint(SpringLayout.NORTH, timeMeasureLabel, 5, SpringLayout.SOUTH, timeTakenLabel);
        
        //Layout constraints for statistics components.
        layout.putConstraint(SpringLayout.NORTH, sampleSizeLabel, 30, SpringLayout.SOUTH, addTimeButton);
        layout.putConstraint(SpringLayout.WEST, sampleSizeLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, sampleMeanLabel, 5, SpringLayout.SOUTH, sampleSizeLabel);
        layout.putConstraint(SpringLayout.WEST, sampleMeanLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, sampleVarianceLabel, 5, SpringLayout.SOUTH, sampleMeanLabel);
        layout.putConstraint(SpringLayout.WEST, sampleVarianceLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, sampleStdDevLabel, 5, SpringLayout.SOUTH, sampleVarianceLabel);
        layout.putConstraint(SpringLayout.WEST, sampleStdDevLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, semLabel, 5, SpringLayout.SOUTH, sampleStdDevLabel);
        layout.putConstraint(SpringLayout.WEST, semLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, testTimeLabel, 5, SpringLayout.SOUTH, semLabel);
        layout.putConstraint(SpringLayout.WEST, testTimeLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, testTimeField, 3, SpringLayout.SOUTH, semLabel);
        layout.putConstraint(SpringLayout.WEST, testTimeField, 3, SpringLayout.EAST, testTimeLabel);
        layout.putConstraint(SpringLayout.NORTH, tValueLabel, 5, SpringLayout.SOUTH, testTimeLabel);
        layout.putConstraint(SpringLayout.WEST, tValueLabel, 5, SpringLayout.EAST, timeScrollPane);
        layout.putConstraint(SpringLayout.NORTH, pValueLabel, 5, SpringLayout.SOUTH, tValueLabel);
        layout.putConstraint(SpringLayout.WEST, pValueLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        //Layout constraints for interval components.
        layout.putConstraint(SpringLayout.NORTH, confidenceIntervalLabel, 30, SpringLayout.SOUTH, pValueLabel);
        layout.putConstraint(SpringLayout.WEST, confidenceIntervalLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        layout.putConstraint(SpringLayout.NORTH, predictionIntervalLabel, 5, SpringLayout.SOUTH, confidenceIntervalLabel);
        layout.putConstraint(SpringLayout.WEST, predictionIntervalLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        layout.putConstraint(SpringLayout.NORTH, customConfidenceLevelLabel, 5, SpringLayout.SOUTH, predictionIntervalLabel);
        layout.putConstraint(SpringLayout.WEST, customConfidenceLevelLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        layout.putConstraint(SpringLayout.NORTH, customConfidenceLevelField, 3, SpringLayout.SOUTH, predictionIntervalLabel);
        layout.putConstraint(SpringLayout.WEST, customConfidenceLevelField, 3, SpringLayout.EAST, customConfidenceLevelLabel);
        
        layout.putConstraint(SpringLayout.NORTH, customPredictiveConfidenceLevelLabel, 5, SpringLayout.SOUTH, customConfidenceLevelLabel);
        layout.putConstraint(SpringLayout.WEST, customPredictiveConfidenceLevelLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        layout.putConstraint(SpringLayout.NORTH, customPredictiveConfidenceLevelField, 3, SpringLayout.SOUTH, customConfidenceLevelLabel);
        layout.putConstraint(SpringLayout.WEST, customPredictiveConfidenceLevelField, 3, SpringLayout.EAST, customPredictiveConfidenceLevelLabel);
        
        layout.putConstraint(SpringLayout.NORTH, customConfidenceIntervalLabel, 5, SpringLayout.SOUTH, customPredictiveConfidenceLevelLabel);
        layout.putConstraint(SpringLayout.WEST, customConfidenceIntervalLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        layout.putConstraint(SpringLayout.NORTH, customPredictionIntervalLabel, 5, SpringLayout.SOUTH, customConfidenceIntervalLabel);
        layout.putConstraint(SpringLayout.WEST, customPredictionIntervalLabel, 5, SpringLayout.EAST, timeScrollPane);
        
        
        //Adding EventListeners.
        loadTimesButton.addActionListener((ActionEvent evt) -> {
            loadTimes();
        });
        
        saveTimesButton.addActionListener((ActionEvent evt) -> {
            saveTimes();
        });
        
        addTimeButton.addActionListener((ActionEvent evt) -> {
            addTime();
        });
        
        deleteTimeButton.addActionListener((ActionEvent evt) -> {
            deleteTime();
        });
        
        deleteAllTimesButton.addActionListener((ActionEvent evt) -> {
            deleteAllTimes();
        });
        
        changeTimeButton.addActionListener((ActionEvent evt) -> {
            changeTime();
        });
        
        refreshStatsButton.addActionListener((ActionEvent evt) -> {
            refreshStats();
        });
        
        timeList.addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                int timeIndex = timeList.getSelectedIndex();
                if (timeIndex != -1) {
                    System.out.println(format.format(solveHandler.getSolve(timeIndex).getTime()) + " selected.");
                    timeEditField.setText(format.format(solveHandler.getSolve(timeIndex).getTime()));
                }
            }
        });
        
        //Packing.
        pack();
    }
    
    /**
     * Starts up the frame.
     */
    public void startFrame() {
        setVisible(true);
    }
    
    /**
     * Prompts the user to load a times file.
     */
    private void loadTimes() {
        if (!times.isEmpty()) {
            if (JOptionPane.showConfirmDialog(null, "This operation results in existing data being discarded. Are you sure?") != JOptionPane.OK_OPTION) {
                errorLabel.setText("Loading operation cancelled.");
                System.out.println("Loading operation cancelled.");
                return;
            }
        }
        
        JFileChooser fileChooser = new JFileChooser();
        File file;
        BufferedReader bufferedReader;
        String rawTimes;
        
        //Check if user cancelled the file selection.
        if (fileChooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            errorLabel.setText("File selection aborted.");
            System.out.println("File selection aborted.");
            return;
        }
        
        //Try to initialise a BufferedReader object.
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            errorLabel.setText("Failed to create BufferedReader.");
            System.out.println("Failed to create BufferedReader.");
            return;
        }
        
        //Read the timings from the selected file.
        //File format: all timings are separated by tabs on a single line.
        try {
            rawTimes = bufferedReader.readLine();
        } catch (IOException e) {
            errorLabel.setText("Error while reading from file.");
            System.out.println("Error while reading from file.");
            
            //Close the BufferedReader is there was an error.
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                errorLabel.setText("Error while closing bufferedReader.");
                System.out.println("Error while closing bufferedReader.");
            }
            return;
        }
        
        //Check if no timings can be found in the file.
        //Note that no further checks on whether the file is correctly formatted are performed.
        if (rawTimes == null)
        {
            errorLabel.setText("No timings found in file.");
            System.out.println("No timings found in file.");
            
            //Close the BufferedReader is there was an error.
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                errorLabel.setText("Error while closing bufferedReader.");
                System.out.println("Error while closing bufferedReader.");
            }
            return;
        }
        
        //Attempt to create a SolveHandler object through an array of Solve objects and update timeList.
        String[] rawTimesSeparated = rawTimes.split("\t");
        Solve[] solves = new Solve[rawTimesSeparated.length];
        
        for (int i = 0 ; i < rawTimesSeparated.length; ++i)
        {
            solves[i] = new Solve(Double.parseDouble(rawTimesSeparated[i]));
        }
        
        solveHandler = new SolveHandler(solves);
        
        times.removeAllElements();
        
        for (int i = 0 ; i < rawTimesSeparated.length; ++i)
        {
            times.addElement(format.format(solves[i].getTime()));
        }
        
        //Close the BufferedReader.
        try {
            bufferedReader.close();
        } catch (IOException e2) {
            errorLabel.setText("Error while closing bufferedReader.");
            System.out.println("Error while closing bufferedReader.");
        }
        
        refreshStats();
        errorLabel.setText("Times loaded.");
        System.out.println("Times loaded.");
    }
    
    /**
     * Prompts the user to save data to a times file.
     */
    private void saveTimes() {
        String fileName = JOptionPane.showInputDialog("Please enter the full path and the name of the file you want to save the times to: ", "C:\\");
        BufferedWriter writer;
        
        if (fileName == null) {
            errorLabel.setText("Destination selection aborted.");
            System.out.println("Destination selection aborted.");
            return;
        }
        
        if (new File(fileName).exists()) {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite the file?") != JOptionPane.OK_OPTION) {
                errorLabel.setText("Saving operation cancelled.");
                System.out.println("Saving operation cancelled.");
                return;
            }
        }
        
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            errorLabel.setText("Error while creating BufferedWriter.");
            System.out.println("Error while creating BufferedWriter.");
            return;
        }
        
        for (int i = 0; i < solveHandler.getSize(); ++i) {
            try {
                writer.write(format.format(solveHandler.getSolve(i).getTime()) + "\t");
            } catch (IOException e) {
                errorLabel.setText("Error while writing to file.");
                System.out.println("Error while writing to file.");
                try {
                    writer.close();
                } catch (IOException e2) {
                    errorLabel.setText("Error while closing BufferedWriter.");
                    System.out.println("Error while closing BufferedWriter.");
                }
                return;
            }
        }
        
        try {
            writer.close();
        } catch (IOException e) {
            errorLabel.setText("Error while closing BufferedWriter.");
            System.out.println("Error while closing BufferedWriter.");
        }
        
        errorLabel.setText("Times saved.");
        System.out.println("Times saved.");
    }
    
    /**
     * Adds a time to the SolveHandler and the DefaultListModel.
     */
    private void addTime() {
        String timeEditFieldText;
        try {
            timeEditFieldText = timeEditField.getText();
        } catch (NullPointerException e) {
            errorLabel.setText("NullPointerException occurred while adding time.");
            System.out.println("NullPointerException occurred while adding time.");
            return;
        }
        
        Double newTime;
        if (!timeEditFieldText.equals("")) {
            newTime = Double.parseDouble(timeEditFieldText);
        } else {
            errorLabel.setText("Text field empty. No times added.");
            System.out.println("Text field empty. No times added.");
            return;
        }
        
        solveHandler.addSolve(new Solve(newTime));
        times.addElement(format.format(newTime));
        
        refreshStats();
        errorLabel.setText("Time added.");
        System.out.println("Time added.");
    }
    
    /**
     * Deletes a time from the SolveHandler and the DefaultListModel.
     */
    private void deleteTime() {
        int timeIndex = timeList.getSelectedIndex();
        if (timeIndex != -1) {
            solveHandler.deleteSolve(timeIndex);
            times.remove(timeIndex);
            
            refreshStats();
            errorLabel.setText("Time deleted.");
            System.out.println("Time deleted.");
        } else {
            errorLabel.setText("No time to delete selected.");
            System.out.println("No time to delete selected.");
        }
    }
    
    /**
     * Deletes all times from the SolveHandler and the DefaultListModel.
     */
    private void deleteAllTimes() {
        solveHandler = new SolveHandler();
        times.removeAllElements();
        
        refreshStats();
        errorLabel.setText("All times deleted.");
        System.out.println("All times deleted.");
    }
    
    /**
     * Changes a time in the SolveHandler and the DefaultListModel.
     */
    private void changeTime() {
        int timeIndex = timeList.getSelectedIndex();
        if (timeIndex != -1) {
            String timeEditFieldText;
            try {
                timeEditFieldText = timeEditField.getText();
            } catch (NullPointerException e) {
                errorLabel.setText("NullPointerException occurred while changing time.");
                System.out.println("NullPointerException occurred while changing time.");
                return;
            }

            Double newTime;
            if (!timeEditFieldText.equals("")) {
                newTime = Double.parseDouble(timeEditFieldText);
            } else {
                errorLabel.setText("Text field empty. Time not changed.");
                System.out.println("Text field empty. Time not changed.");
                return;
            }
            
            System.out.println(format.format(solveHandler.getSolve(timeIndex).getTime()) + " changed to " + newTime + ".");
            solveHandler.setTime(timeIndex, newTime);
            times.setElementAt(format.format(newTime), timeIndex);
            
            refreshStats();
            errorLabel.setText("Time changed.");
            System.out.println("Time changed.");
        } else {
            errorLabel.setText("No time to change selected.");
            System.out.println("No time to change selected.");
        }
    }
    
    /**
     * Refreshes statistics components.
     */
    private void refreshStats() {
        if (solveHandler.getSolveTimes().length <= 1) {
            sampleSizeLabel.setText("Number of Solves: " + solveHandler.getSolveTimes().length);
            sampleMeanLabel.setText("Sample Mean: ");
            sampleVarianceLabel.setText("Sample Variance: ");
            sampleStdDevLabel.setText("Sample Standard Deviation: ");
            semLabel.setText("Standard Error of Mean: ");
            tValueLabel.setText("t-value: ");
            pValueLabel.setText("p-value: ");
            
            errorLabel.setText("No statistics available.");
            System.out.println("No statistics available.");
            return;
        }
        
        statistics = new Statistics(solveHandler.getSolveTimes());
        sampleSizeLabel.setText("Number of Solves: " + solveHandler.getSolveTimes().length);
        sampleMeanLabel.setText("Sample Mean: " + format.format(statistics.getMean()));
        sampleVarianceLabel.setText("Sample Variance: " + format.format(statistics.getVariance()));
        sampleStdDevLabel.setText("Sample Standard Deviation: " + format.format(statistics.getStdDev()));
        semLabel.setText("Standard Error of Mean: " + format.format(statistics.getSem()));
        
        try {
            testTime = Double.parseDouble(testTimeField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorLabel.setText("Please enter a valid time.");
            System.out.println("Please enter a valid time.");
            return;
        }
        
        tValueLabel.setText("t-value: " + longerFormat.format(statistics.getTValue(testTime)));
        pValueLabel.setText("p-value: " + longerFormat.format(statistics.getPValue(testTime)));
        
        //Update intervals.
        double[] confidenceIntervalArray = statistics.getConfidenceInterval();
        confidenceIntervalLabel.setText("Confidence Interval (95%): " + format.format(confidenceIntervalArray[0]) + " - " + format.format(confidenceIntervalArray[1]));
        double[] predictionIntervalArray = statistics.getPredictionInterval();
        predictionIntervalLabel.setText("Prediction Interval (95%): " + format.format(predictionIntervalArray[0]) + " - " + format.format(predictionIntervalArray[1]));
        
        try {
            customConfidenceLevel = Double.parseDouble(customConfidenceLevelField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorLabel.setText("Please enter a valid confidence level.");
            System.out.println("Please enter a valid confidence level.");
            return;
        }
        try {
            customPredictiveConfidenceLevel = Double.parseDouble(customPredictiveConfidenceLevelField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorLabel.setText("Please enter a valid predictive confidence level.");
            System.out.println("Please enter a valid predictive confidence level.");
            return;
        }
        double[] customConfidenceIntervalArray = statistics.getConfidenceInterval(customConfidenceLevel);
        customConfidenceIntervalLabel.setText("Confidence Interval (custom): " + format.format(customConfidenceIntervalArray[0]) + " - " +  format.format(customConfidenceIntervalArray[1]));
        double[] customPredictiveConfidenceIntervalArray = statistics.getPredictionInterval(customPredictiveConfidenceLevel);
        customPredictionIntervalLabel.setText("Prediction Interval (custom): " + format.format(customPredictiveConfidenceIntervalArray[0]) + " - " +  format.format(customPredictiveConfidenceIntervalArray[1]));
        
        //Message.
        errorLabel.setText("Statistics calculated.");
        System.out.println("Statistics calculated.");
    }
}