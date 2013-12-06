/*
 Copyright (C) 2009-2013 QAXML PTY LTD.
 */
package com.search4j.ui.dialog.simple;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

import com.search4j.model.Search4JModel;

public abstract class ViewSearchBar implements ActionListener  {

	private static final String COMMAND_SEARCH_NEXT = "COMAND_SEARCH_NEXT";

	private static final String COMAND_SEARCH_PREV = "COMAND_SEARCH_PREV";

	private static final String COMMAND_REGEXP = "REGEXP";

	private static final String COMMAND_WHOLE_WORD = "WHOLE_WORD";

	private static final String COMMAND_MATCH_CASE = "MATCH_CASE";

	private static final String COMMAND_COUNT = "COUNT";

	private JPanel panel;

	private JTextField textSearch;

	private JToggleButton matchCase;

	private JToggleButton wholeWord;

	private JToggleButton regexp;

	private Search4JModel searchModel;

	public ViewSearchBar() {

		panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panel.add(new JLabel("Search:"), BorderLayout.WEST);

		// Text field
		textSearch = new JTextField();
		textSearch.addActionListener(this);
		textSearch.setActionCommand(COMMAND_SEARCH_NEXT);
		textSearch.setFont(textSearch.getFont().deriveFont(14.0f));
		textSearch.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (e.getSource() instanceof JTextComponent) {
					JTextComponent textComponent = (JTextComponent) e.getSource();
					textComponent.selectAll();
				}
			}
		});

		panel.add(textSearch, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.EAST);

		// Next
		JButton btnNext = new JButton("Next", new ImageIcon(getClass().getResource("/icons/ui/dialog/simple/down.png")));
		btnNext.addActionListener(this);
		btnNext.setActionCommand(COMMAND_SEARCH_NEXT);
		btnPanel.add(btnNext);

		// Previous
		JButton btnPrevious = new JButton("Previous", new ImageIcon(getClass().getResource("/icons/ui/dialog/simple//up.png")));
		btnPrevious.addActionListener(this);
		btnPrevious.setActionCommand(COMAND_SEARCH_PREV);
		btnPanel.add(btnPrevious);

		// Count
		JButton btnCount = new JButton("Count", new ImageIcon(getClass().getResource("/icons/ui/dialog/simple/search.png")));
		btnCount.addActionListener(this);
		btnCount.setActionCommand(COMMAND_COUNT);
		btnPanel.add(btnCount);

		matchCase = new JToggleButton("Match case");
		matchCase.addActionListener(this);
		matchCase.setActionCommand(COMMAND_MATCH_CASE);
		btnPanel.add(matchCase);

		wholeWord = new JToggleButton("Whole word only");
		wholeWord.addActionListener(this);
		wholeWord.setActionCommand(COMMAND_WHOLE_WORD);
		btnPanel.add(wholeWord);

		regexp = new JToggleButton("Regular expression");
		regexp.setActionCommand(COMMAND_REGEXP);
		regexp.addActionListener(this);
		btnPanel.add(regexp);

		this.textSearch.requestFocus();

	}

	public JComponent getComponent() {
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		wrapper.add(panel, BorderLayout.CENTER);
		return wrapper;
	}

	private void resetQuery() {

		String query = this.textSearch.getText();
		boolean matchCase = this.matchCase.isSelected();
		boolean wholeWord = this.wholeWord.isSelected();
		boolean regexp = this.regexp.isSelected();

		this.searchModel = new Search4JModel(query, wholeWord, matchCase, regexp);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.resetQuery();

		if (COMMAND_SEARCH_NEXT.equals(e.getActionCommand())) {
//			controller.findNextOccurence();
		} else if (COMMAND_COUNT.equals(e.getActionCommand())) {
//			controller.countOccurences();
		} else if (COMAND_SEARCH_PREV.equals(e.getActionCommand())) {
//			controller.findPreviousOccurence();
		} else if (COMMAND_REGEXP.equals(e.getActionCommand())) {
			this.matchCase.setSelected(false);
			this.wholeWord.setSelected(false);
		} else if (COMMAND_MATCH_CASE.equals(e.getActionCommand()) || COMMAND_WHOLE_WORD.equals(e.getActionCommand())) {
			this.regexp.setSelected(false);
		}

		((JComponent) e.getSource()).requestFocus();
	}

	public void focus() {
		this.textSearch.requestFocus();
	}

}
