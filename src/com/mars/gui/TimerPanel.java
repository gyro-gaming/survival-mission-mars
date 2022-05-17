package com.mars.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TimerPanel extends JPanel {

    private JTextField targetHours;
    private JTextField targetMins;
    private JTextField targetSeconds;
    private Instant futureTime;
    private Timer timer;
    private JLabel countDown;

    public TimerPanel() {

        setLayout(new GridBagLayout());

        targetHours = new JTextField("01", 2);
        targetMins = new JTextField("00", 2);
        targetSeconds = new JTextField("00", 2);

        JPanel targetPanel = new JPanel(new GridBagLayout());
//        targetPanel.add(targetHours);
//        targetPanel.add(new JLabel(":"));
//        targetPanel.add(targetMins);
//        targetPanel.add(new JLabel(":"));
//        targetPanel.add(targetSeconds);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(8, 8, 8, 8);

        add(targetPanel, gbc);

        JButton btn = new JButton("Start");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                futureTime = LocalDateTime.now()
                        .plusHours(Long.parseLong(targetHours.getText()))
                        .plusMinutes(Long.parseLong(targetMins.getText()))
                        .plusSeconds(Long.parseLong(targetSeconds.getText()))
                        .atZone(ZoneId.systemDefault()).toInstant();

                if (timer != null) {
                    timer.stop();
                }

                countDown.setText("---");
                timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Duration duration = Duration.between(Instant.now(), futureTime);
                        if (duration.isNegative()) {
                            timer.stop();
                            timer = null;
                            countDown.setText("00:00:00");
                        } else {
                            String formatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
                            countDown.setText(formatted);
                        }
                    }
                });
                timer.start();
            }
        });

        add(btn, gbc);

        countDown = new JLabel("---");
        add(countDown, gbc);
    }
}
