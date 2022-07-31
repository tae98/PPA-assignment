
/**
 * 
 * Properties Dialog
 * List all Properties
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PropertiesDialog extends JDialog implements ActionListener, ListSelectionListener {


	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> propertiesList = new JList<>(model);

	// drop down menu
	String[] sortMenu = { "review score", "price", "host name" };
	JComboBox<String> sortMenuComboBox = new JComboBox<>(sortMenu);

	// ok button
	JButton buttonOK = new JButton("OK");

	// listings
	ArrayList<AirbnbListing> listings;

	// modal dialog
	public PropertiesDialog(JFrame parent, String title, ArrayList<AirbnbListing> listings) {
		super(parent, title, true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(500, 400);

		this.listings = listings;

		// position dialog
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		// set up sort menu
		JPanel menuPanel = new JPanel();
		menuPanel.add(new JLabel("sort by: "));
		menuPanel.add(sortMenuComboBox);
		add(menuPanel, BorderLayout.NORTH);

		sortMenuComboBox.setSelectedIndex(1);
		sortMenuComboBox.addActionListener(this);

		// fill jlist with properties
		propertiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		for (AirbnbListing listing : listings) {
			String reviews = "reviews: " + listing.getReviewsPerMonth();
			if (listing.getReviewsPerMonth() == -1)
				reviews = "no reviews";

			((DefaultListModel<String>) propertiesList.getModel()).addElement("host: " + listing.getHost_name() + " "
					+ listing.getPrice() + " " + reviews + " minimum nights: " + listing.getMinimumNights());
		}

		// add list box
		getContentPane().add(new JScrollPane(propertiesList), BorderLayout.CENTER);

		// ok button
		JPanel buttonPanel = new JPanel();

		buttonPanel.add(buttonOK);
		buttonOK.addActionListener(this);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		propertiesList.addListSelectionListener(this);

		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == sortMenuComboBox) {
			String sortType = (String) sortMenuComboBox.getSelectedItem();

			// sort by reviwe code
			if (sortType.equals("review score")) {
				Collections.sort(listings, new Comparator<AirbnbListing>() {
					@Override
					public int compare(AirbnbListing obj1, AirbnbListing obj2) {
						return (int) ((int) obj2.getReviewsPerMonth() - (int) obj1.getReviewsPerMonth());
					}
				});
			}

			// sort by price
			else if (sortType.equals("price")) {
				Collections.sort(listings, new Comparator<AirbnbListing>() {
					@Override
					public int compare(AirbnbListing obj1, AirbnbListing obj2) {
						return (int) ((int) obj1.getPrice() - (int) obj2.getPrice());
					}
				});
			}

			// sort by host name
			else if (sortType.equals("host name")) {
				Collections.sort(listings, new Comparator<AirbnbListing>() {
					@Override
					public int compare(AirbnbListing obj1, AirbnbListing obj2) {
						return obj1.getHost_name().compareTo(obj2.getHost_name());
					}
				});
			}

			// remove old listings
			((DefaultListModel<String>) propertiesList.getModel()).clear();

			// fill jlist with properties
			for (AirbnbListing listing : listings) {
				String reviews = "reviews: " + listing.getReviewsPerMonth();
				if (listing.getReviewsPerMonth() == -1)
					reviews = "no reviews";

				((DefaultListModel<String>) propertiesList.getModel()).addElement("host: " + listing.getHost_name()
						+ " " + listing.getPrice() + " " + reviews + " minimum nights: " + listing.getMinimumNights());
			}

		}

		// ok
		else if (source == buttonOK) {

			setVisible(false);
			dispose();
		}
	}

	// select property for display
	public void valueChanged(ListSelectionEvent e) {

		int index = propertiesList.getSelectedIndex();

		if (index >= 0) {
			AirbnbListing listing = listings.get(index);
			String s = listing.toString();
			s = s.replace(",", "\n");
			JTextArea textArea = new JTextArea(s);
			textArea.setColumns(50);
			textArea.setSize(textArea.getPreferredSize().width, 1);
			JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Listing", JOptionPane.WARNING_MESSAGE);
		}
	}

}
