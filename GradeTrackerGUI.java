import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GradeTrackerGUI extends JFrame {
    private ArrayList<Double> grades = new ArrayList<>();
    private JTextField gradeInput;
    private JTextArea displayArea;
    private JLabel resultLabel;

    public GradeTrackerGUI() {
        // Window Setup
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Input
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Grade:"));
        gradeInput = new JTextField(5);
        JButton addButton = new JButton("Add Grade");
        inputPanel.add(gradeInput);
        inputPanel.add(addButton);

        // Center: List of entered grades
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Bottom: Calculations & Reset
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        resultLabel = new JLabel("Average: 0 | High: 0 | Low: 0", SwingConstants.CENTER);
        JButton calculateButton = new JButton("Calculate Statistics");
        bottomPanel.add(calculateButton);
        bottomPanel.add(resultLabel);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Logic: Add Button Action ---
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double val = Double.parseDouble(gradeInput.getText());
                    if (val >= 0 && val <= 100) {
                        grades.add(val);
                        displayArea.append("Grade added: " + val + "\n");
                        gradeInput.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter a grade between 0-100");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });

        // --- Logic: Calculate Button Action ---
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (grades.isEmpty()) {
                    resultLabel.setText("No grades entered yet!");
                    return;
                }
                double total = 0, high = grades.get(0), low = grades.get(0);
                for (double g : grades) {
                    total += g;
                    if (g > high) high = g;
                    if (g < low) low = g;
                }
                double avg = total / grades.size();
                resultLabel.setText(String.format("Avg: %.2f | High: %.1f | Low: %.1f", avg, high, low));
            }
        });
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new GradeTrackerGUI().setVisible(true);
        });
    }
}