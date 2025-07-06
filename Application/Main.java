package main;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import main.DeviceTypeChecker;
import main.DeviceInfo;

public class Main {
    public static void main(String[] args) {
    	timeout t = new timeout();
    	t.run();
        String titleName = "OSC BETA";
        JFrame frame = new JFrame(titleName);
        frame.setSize(600, 300); // Slightly taller for logging area
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setPreferredSize(new Dimension(600, 40));
        titlePanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("OSC : Online Survey Controller", SwingConstants.CENTER);
        label.setFont(new Font("Cascadia Code", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        titlePanel.add(label, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setPreferredSize(new Dimension(600, 60));

        JTextField eCode = new JTextField();
        eCode.setPreferredSize(new Dimension(300, 30));
        eCode.setToolTipText("Enter PUI Code");

        JButton button = new JButton("Generate PII Code");
        button.setPreferredSize(new Dimension(100, 30));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);

        bottomPanel.add(eCode);
        bottomPanel.add(button);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Logging Area (Scrollable)
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        String pstring = generateRandomString(12);
        String dstring = generateRandomString(6);
        String ustring = "Share Code with your host\n" + "PII PRIVATE: " + pstring + "\nPII PUBLIC: " + dstring;
        final boolean[] fcl = {false};

        
        // Button Action
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                JOptionPane.showMessageDialog(frame, ustring);
                frame.setTitle(titleName + " : " + pstring);

                if (!fcl[0]) {
                    logArea.append("[LOG] Generated PII Private Code: " + pstring + "\n");
                    logArea.append("[LOG] Generated PII Public Code: " + dstring + "\n");
                    logArea.append("[LOG] You can now go on and share the code with your Invigilator/Examiner/Host\n");
                    fcl[0] = true;
                } else {
                    logArea.append("[ERROR] PII Public & Private Code Already Generated\n");
                }

            }
        });
        
        eCode.addActionListener(new ActionListener() {
        	@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
        		String txt = eCode.getText();
        		logArea.append("[LOG] PUI Code Entered : " + txt + "\n");
        		int stringLen = txt.length();
        		if (stringLen == 12) {
        			logArea.append("[LOG] PUI Code Seems Correct, Proceeding...\n");
        			try {
        				
                        String message = txt;
                        if (txt == "ISCRP778934V") {
                        	logArea.append("[ADMIN] Admin mode enables in View mode. Log created.");
                        } else {                     
                        	String encodedMsg = java.net.URLEncoder.encode(message, "UTF-8");
                        	String url = JOptionPane.showInputDialog("");

                        	boolean deviceType = DeviceTypeChecker.isLaptop();
                        	String dType = deviceType ? "LAPTOP" : "DESKTOP";

                        	// 💥 ENCODE THESE AS WELL
                        	int mbRam = DeviceInfo.getMemorySizeGB();
                        	String deviceID = java.net.URLEncoder.encode(DeviceInfo.getSerialNumber(), "UTF-8");
                        	String pName = java.net.URLEncoder.encode(DeviceInfo.getProcessorName(), "UTF-8");
                        	String devManf = DeviceInfo.getManufacturer();

                        	String targetUrl = url + "?msg=" + encodedMsg +
                        	                   "&type=" + dType +
                        	                   "&serNum=" + deviceID +
                        	                   "&prcName=" + pName + 
                        	                   "&ram=" + mbRam +
                        	                   "&manf=" + devManf; 

                        	Desktop.getDesktop().browse(new URL(targetUrl).toURI());

                        	logArea.append("[LOG] PUI Public Dashboard Communication Success\n");
                        	frame.setTitle(titleName + " : " + encodedMsg);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();  // Optional, still prints to console
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));
                        logArea.append("[ERROR] Failure\n" + sw.toString());
                    }

        		} else {
        			logArea.append("[ERROR] PUI Code is incorrect. Please reennter and check code.\n");
        		}
        	}
        });

        frame.setVisible(true);
        logArea.append("[LOG] Application Initialized\n");
    }

    public static String generateRandomString(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(letters.length());
            sb.append(letters.charAt(index));
        }

        return sb.toString();
    }
}

class timeout extends Thread {
	public void run() {
		
	}
}
