package com.mars.timer;

import java.util.Timer;
import java.util.TimerTask;

class TimerSwing extends javax.swing.JFrame{

    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel timeLeft;
    private javax.swing.JLabel timerName;
    int counter = 10;
    Boolean isIt = false;

    public TimerSwing() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {

        timerName = new javax.swing.JLabel();
        timeLeft = new javax.swing.JLabel();
        btnStop = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        timerName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        timerName.setText("Timer : ");

        timeLeft.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        timeLeft.setText("00:00");

        btnStop.setText("Stop");
        btnStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStopMouseClicked(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.setActionCommand("Start");
        btnStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStartMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnStart)
                                                .addGap(47, 47, 47)
                                                .addComponent(btnStop))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(timerName)
                                                .addGap(18, 18, 18)
                                                .addComponent(timeLeft)))
                                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(timerName)
                                        .addComponent(timeLeft))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnStart)
                                        .addComponent(btnStop))
                                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnStartMouseClicked(java.awt.event.MouseEvent evt) {
        Timer timer = new Timer(); // new timer
        counter = 10; // setting the counter to 10 sec
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeLeft.setText(Integer.toString(counter)); // the timer lable to counter
                counter --;
                if (counter == -1) {
                    timer.cancel();
                } else if(isIt) {
                    timer.cancel();
                    isIt = false;
                }
            }
        };
         timer.scheduleAtFixedRate(task, 1000, 1000); //timer.scheduleAtFixedRate(task, delay, period)
    }

    private void btnStopMouseClicked(java.awt.event.MouseEvent evt){
        isIt = true; // changing the boolean isIt to True, which will stop the timer
    }

}