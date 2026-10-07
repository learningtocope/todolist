import java.awt.*;
import java.io.File;
import javax.swing.*;

public class ToDoListFrontend extends JFrame {

    private JTextField taskField;
    private JComboBox<String> priorityBox;
    private JPanel taskPanel;

    public ToDoListFrontend() {

        // Window
        setTitle("To-Do List");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout(15, 10));
        headerPanel.setBackground(new Color(245, 245, 245));

        // Photo / Logo
        JLabel imageLabel;

        File imageFile = new File("todo.png");

        if (imageFile.exists()) {
            ImageIcon icon = new ImageIcon("todo.png");

            Image image = icon.getImage().getScaledInstance(
                    70, 70, Image.SCALE_SMOOTH
            );

            imageLabel = new JLabel(new ImageIcon(image));
        } else {
            imageLabel = new JLabel("✓");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 50));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        headerPanel.add(imageLabel, BorderLayout.WEST);

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("TO-DO LIST");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Organize your tasks and stay productive");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        headerPanel.add(titlePanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // =========================
        // INPUT AREA
        // =========================

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(Color.GRAY);
        inputPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 210, 210)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                )
        );

        taskField = new JTextField();
        taskField.setFont(new Font("Arial", Font.PLAIN, 16));
        taskField.setToolTipText("Enter your task");

        priorityBox = new JComboBox<>(
                new String[]{"Low", "Medium", "High"}
        );

        priorityBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton addButton = new JButton("ADD TASK");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);

        JPanel controlsPanel = new JPanel(new BorderLayout(10, 10));
        controlsPanel.setBackground(Color.GRAY);

        controlsPanel.add(taskField, BorderLayout.CENTER);
        controlsPanel.add(priorityBox, BorderLayout.EAST);
        controlsPanel.add(addButton, BorderLayout.SOUTH);

        inputPanel.add(controlsPanel, BorderLayout.CENTER);

        // =========================
        // TASK LIST
        // =========================

        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Your Tasks")
        );

        // =========================
        // BUTTON ACTION
        // =========================

        addButton.addActionListener(e -> addTask());

        taskField.addActionListener(e -> addTask());

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 245));

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Pure Java Swing • To-Do List Frontend"
        );

        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));

        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // =========================
    // ADD TASK
    // =========================

    private void addTask() {

        String taskText = taskField.getText().trim();

        if (taskText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a task.",
                    "Empty Task",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String priority = (String) priorityBox.getSelectedItem();

        createTask(taskText, priority);

        taskField.setText("");
        priorityBox.setSelectedIndex(0);
    }

    // =========================
    // CREATE TASK PANEL
    // =========================

    private void createTask(String taskText, String priority) {

        JPanel task = new JPanel(new BorderLayout(10, 5));

        task.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 65)
        );

        task.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 10, 10, 10
                        )
                )
        );

        task.setBackground(Color.WHITE);

        // Task checkbox
        JCheckBox checkBox = new JCheckBox(taskText);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 15));
        checkBox.setBackground(Color.WHITE);

        // Priority label
        JLabel priorityLabel = new JLabel(priority);
        priorityLabel.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        // Complete button
        JButton completeButton = new JButton("Complete");
        completeButton.setFocusPainted(false);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 5, 0
        ));

        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(priorityLabel);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        task.add(checkBox, BorderLayout.CENTER);
        task.add(buttonPanel, BorderLayout.EAST);

        // Complete action
        completeButton.addActionListener(e -> {

            checkBox.setSelected(true);
            checkBox.setFont(
                    new Font("Arial", Font.ITALIC, 15)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Task completed!",
                    "Completed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Checkbox action
        checkBox.addActionListener(e -> {

            if (checkBox.isSelected()) {
                checkBox.setFont(
                        new Font("Arial", Font.ITALIC, 15)
                );
            } else {
                checkBox.setFont(
                        new Font("Arial", Font.PLAIN, 15)
                );
            }
        });

        // Delete action
        deleteButton.addActionListener(e -> {

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this task?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (answer == JOptionPane.YES_OPTION) {
                taskPanel.remove(task);
                taskPanel.revalidate();
                taskPanel.repaint();
            }
        });

        taskPanel.add(task);
        taskPanel.add(Box.createVerticalStrut(8));

        taskPanel.revalidate();
        taskPanel.repaint();
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ToDoListFrontend app = new ToDoListFrontend();

            app.setVisible(true);
        });
    }
}