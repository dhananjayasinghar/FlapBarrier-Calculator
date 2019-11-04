package com.itc.calculator.ui;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.itc.calculator.exception.AppException;
import com.itc.calculator.service.FlapBarrierCalculator;

/**
 *
 * @author Dhananjay Samanta
 */
public class CalculatorUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	public CalculatorUI() {
		initComponents();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		JFrame f = new JFrame("Calculated Time");
		JDialog d = new JDialog(f, "Calculated Time");
		try {
			if (jTextArea1.getText() == null || jTextArea1.getText().isEmpty()) {
				throw new RuntimeException("ERROR");
			}
			String getValue = jTextArea1.getText().trim();
			String[] values = getValue.split("\n");
			String calculatedData = FlapBarrierCalculator
					.getCalculatedData(new ArrayList<String>(Arrays.asList(values)));
			JLabel l = new JLabel(calculatedData);
			d.add(l);

			d.setSize(400, 100);
			d.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
			JFrame f1 = new JFrame("frame");
			JDialog d1 = new JDialog(f1, "Error");

			String ex = null;
			if (!(e instanceof AppException)) {
				ex = "Check the Input \n Example: <PSID> <Name> mm/dd/yyyy hh:mm:ss AM/PM Valid Entry <Flap Barrier Name>";
			} else {
				ex = e.getMessage();
			}

			JLabel l1 = new JLabel(ex);
			d1.add(l1);
			d1.setSize(700, 100);
			d1.setVisible(true);
		}
	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jButton1 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		jButton1.setBackground(new java.awt.Color(153, 255, 0));
		jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18));
		jButton1.setText("Calculate");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(245, 245, 245)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(48, 48, 48)));

		pack();
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
}
