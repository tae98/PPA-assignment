
/*
 * Assignment 4
 * London Property Management App
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JMenuItem; 

public class Assignment4 extends JFrame {
    
    private About about; 
    // program constants
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAX_PANELS = 4;
    private static final int MAX_STATISTCS = 8;
    private static final int NUM_STATISTIC_BOXES = 4;

    // from/to prices
    private String[] fromPrices = { "0", "50", "100", "150", "200", "250", "300", "350", "400", "450", "500", "550", "600", "650", "700", "750", "800", "850", "900", "950"};
    public String[] toPrices = { "50", "100", "150", "200", "250", "300", "350", "400", "450", "500", "550", "600", "650", "700", "750", "800", "850", "900", "950"};

    // prices combo boxes
    private JComboBox<String> fromPricesComboBox = new JComboBox<String>(fromPrices);
    private JComboBox<String> toPricesComboBox = new JComboBox<String>(toPrices);

    // left right buttons
    private JButton leftButton = new JButton("   <   ");
    private JButton rightButton = new JButton("   >   ");

    // prices
    private int minPrice = 0;
    private int maxPrice = 50;

    private WelcomePanel welcomePanel = new WelcomePanel();

    private MapPanel mapPanel = new MapPanel();
    private FinalPanel finalPanel = new FinalPanel();

    private StatisticsPanel statisticsPanel;

    private int panelIndex = 0;
    private JPanel lastPanel;
        
    // property listings read from csv files
    private ArrayList<AirbnbListing> listings;

    // selected listings by price
    private ArrayList<AirbnbListing> selected = new ArrayList<AirbnbListing>();

    // mapped by neighbor hood
    private HashMap<String, ArrayList<AirbnbListing>> neighborhoods = new HashMap<String, ArrayList<AirbnbListing>>();

    // house image
    private Image houseImage;
    private Image home; 

    // frame
    private JFrame frame;

    private Statistics statistics;

    // make Application Window
    public Assignment4() {
        // set frame
        frame = this;

        // set title
        setTitle("Assignment 4 London Property Management App");

        // set frame size
        setSize(WIDTH, HEIGHT);

        // exit on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // read listings from file
        listings = new AirbnbDataLoader().load();

        lookupProperties(minPrice, maxPrice);

        statistics = new Statistics(neighborhoods, selected);

        // load house image's
        try {
            houseImage = ImageIO.read(new File("house.png"));
        }

        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "cannot open house file house.png");
            System.exit(0);
        }
        
        JMenuBar menuBar = new JMenuBar(); 
        frame.setJMenuBar(menuBar) ;
        
        JMenu file = new JMenu ("File"); 
        menuBar.add(file); 
        
        JMenuItem about = new JMenuItem ("About"); 
        
        file.add(about); 
        
        
        class aboutAction implements ActionListener{
            public void actionPerformed (ActionEvent e){
                System.out.println("this is working");
             new About();    
            }
            
            
            
            
        }
        about.addActionListener(new aboutAction());
        // The top right of the frame should feature two dropdown boxes,
        // allowing a user to select a price range for the properties they want
        // to
        // see statistics about.

        // make top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.white);  
        // add combo boxes to top panel
        topPanel.add(new JLabel("Select price range from: "));
        topPanel.add(fromPricesComboBox);
        topPanel.add(new JLabel("to: "));
        topPanel.add(toPricesComboBox);

        // add top panel to frame
        add(topPanel, "North");

        // There should be the ability to move left and right through the panels
        // contained in
        // the centre of the frame using “back” and “forward” buttons

        // make bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.white);
        bottomPanel.add(leftButton, "West");
        bottomPanel.add(rightButton, "East");

        // add bottom panel to frame
        add(bottomPanel, "South");

        // add middle panel to frame;
        add(welcomePanel, "Center");
        lastPanel = welcomePanel;

        // make statistics panel
        statisticsPanel = new StatisticsPanel();

        // handle events

        // from prices combo box event handler
        fromPricesComboBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    minPrice = Integer.parseInt(fromPricesComboBox.getSelectedItem().toString());
                    welcomePanel.updatePrices();
                    mapPanel.repaint();
                }
            });

        // from prices combo box event handler
        toPricesComboBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    maxPrice = Integer.parseInt(toPricesComboBox.getSelectedItem().toString());
                    welcomePanel.updatePrices();
                    mapPanel.repaint();
                }
            });

        // left button
        leftButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {

                    // check from/to prices
                    if (minPrice > maxPrice) {
                        JOptionPane.showMessageDialog(frame, "from price greater than to price");
                        return;
                    }

                    if (panelIndex > 0) {
                        lookupProperties(minPrice, maxPrice);
                        panelIndex--;
                        showPanel();
                    }

                }

            });

        // right button
        rightButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {

                    // check from/to prices
                    if (minPrice > maxPrice) {
                        JOptionPane.showMessageDialog(frame, "from price greater than to price");
                        return;
                    }

                    if (panelIndex < MAX_PANELS - 1) {
                        lookupProperties(minPrice, maxPrice);
                        panelIndex++;
                        showPanel();
                    }

                }

            });

        // show frame
        setVisible(true);

    }

    // lookup propeties by price
    public void lookupProperties(int minPrice, int maxPrice) {
        selected.clear();
        neighborhoods.clear();

        for (AirbnbListing listing : listings) {
            if (listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                selected.add(listing);
                String neighborhood = listing.getNeighbourhood();

                if (!neighborhoods.containsKey(listing.getNeighbourhood())) {
                    neighborhoods.put(neighborhood, new ArrayList<AirbnbListing>());
                }

                neighborhoods.get(neighborhood).add(listing);
            }

        }

    }

    // show Panel
    private void showPanel() {
        if (panelIndex == 0) {
            remove(lastPanel);
            add(welcomePanel, "Center");
            lastPanel = welcomePanel;
        }

        else if (panelIndex == 1) {
            remove(lastPanel);
            add(mapPanel, "Center");
            lastPanel = mapPanel;
        }

        else if (panelIndex == 2) {
            remove(lastPanel);
            statisticsPanel.resetStatistics();
            add(statisticsPanel, "Center");
            lastPanel = statisticsPanel;
   
        }
        else if (panelIndex ==3){
            remove(lastPanel);
            add(finalPanel, "Center");
            lastPanel = finalPanel;
        }

        invalidate();
        validate();

        repaint();
    }

    // run app
    public static void main(String[] args) {

        // run app
        new Assignment4();

    }

    // welcome panel
    // first middle panel
    class WelcomePanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private JLabel labelMinPrice;
        private JLabel labelMaxPrice;

        public WelcomePanel() {
            setLayout(new BorderLayout());
            JOptionPane.showMessageDialog(null, "Welcome to our London property Management app\n 1. Select minimum price.\n 2. Select maximum price.\n 3. Press the > button located at the bottom.\n 4.On the map it will display all the house that are available.\n 5.Select the houses icon on the aera where you wish to select./n 6.After selecting on the icon it will display the host that are available.\n 7. Press the > button located at the bottom.\n 8.It will display the information about the search.", "Read This before you start!", JOptionPane.INFORMATION_MESSAGE);
            

            // print welcome mesage
            JLabel label = new JLabel("Welcome to London Property Management App", JLabel.CENTER);
            label.setFont(new Font("TimesRoman", Font.BOLD, 24));
            // p.add(label);
            add(label, "North");
        
        
        
            // show price selection
            JPanel p2 = new JPanel();
            p2.setBackground(Color.white); 
            p2.setLayout(new GridLayout(10, 0));
            p2.add(new JLabel(""));
            p2.add(new JLabel(""));
            p2.add(new JLabel(""));

            labelMinPrice = new JLabel("Minimum Selected Price: " + minPrice, JLabel.CENTER);
            labelMinPrice.setFont(new Font("TimesRoman", Font.BOLD, 18));
            p2.add(labelMinPrice);

            labelMaxPrice = new JLabel("Maximum Selected Price: " + maxPrice, JLabel.CENTER);
            labelMaxPrice.setFont(new Font("TimesRoman", Font.BOLD, 18));
            p2.add(labelMaxPrice);

            add(p2, "Center");
       
        
        }

        // show new prices
        public void updatePrices() {

            // show price selection
            labelMinPrice.setText("Minimum Selected Price: " + minPrice);

            labelMaxPrice.setText("Maximum Selected Price: " + maxPrice);
        }

    }

    // map panel
    class MapPanel extends JPanel implements MouseListener {

        private static final long serialVersionUID = 1L;
        private Image mapImage;

        public MapPanel() {

            try {
                mapImage = ImageIO.read(new File("london.png"));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "cannot open map file london.png");
                System.exit(0);
            }

            addMouseListener(this);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            if (mapImage != null) {
                g.drawImage(mapImage, 0, 0, width, height, this);

                // draw houses
                for (String neighborhood : neighborhoods.keySet()) {
                    AirbnbListing listing = neighborhoods.get(neighborhood).get(0);
                    double longitude = listing.getLongitude();
                    double latitude = listing.getLatitude();

                    double x = getXlocation(longitude, width);
                    double y = getYlocation(latitude, height);

                    int numListings = neighborhoods.get(neighborhood).size();
                    int size = (int) (numListings * .05);
                    g.drawImage(houseImage, (int) x, (int) y, size, size, null);
                } // end if
            } // end for

        } // end paint component

        // return x coordinate
        public double getXlocation(double longitude, int width) {
            // longitiude diff
            double xx = -.5; // west mpst
            double yy = .255; // east most
            double z = yy - xx;

            double x = -(xx - longitude) / z * width;

            return x;
        }

        // return y coordinate
        public double getYlocation(double latitude, int height) {

            // latitude diff
            double a = 51.66; // north most
            double b = 51.33; // south most
            double c = a - b;

            double y = (a - latitude) / c * height;

            return y;
        }

        @Override
        public void mouseClicked(MouseEvent evt) {
            // TODO Auto-generated method stub

            int x = evt.getX();
            int y = evt.getY();

            // find neighgbor hood
            int width = getWidth();
            int height = getHeight();

            for (String neighborhood : neighborhoods.keySet()) {
                AirbnbListing listing = neighborhoods.get(neighborhood).get(0);
                int x2 = (int) (getXlocation(listing.getLongitude(), width));
                int y2 = (int) (getYlocation(listing.getLatitude(), height));

                if (x > (x2 - 40) && x < (x2 + 40) && y > (y2 - 40) && y < (y2 + 40)) {
                    new PropertiesDialog(frame, listing.getNeighbourhood(), neighborhoods.get(neighborhood));
                    return;
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent evt) {

        }
        @Override
        public void mouseExited(MouseEvent evt) {

        }
        @Override
        public void mousePressed(MouseEvent evt) {

        }
        @Override
        public void mouseReleased(MouseEvent arg0) {

        }
    }

    // statistics panel
    class StatisticsPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        // array of statistic box
        public StatisticBox[] statisticBoxes = new StatisticBox[NUM_STATISTIC_BOXES];

        public StatisticsPanel() {

            setLayout(new GridLayout(NUM_STATISTIC_BOXES, 0));

            for (int i = 0; i < statisticBoxes.length; i++) {
                statisticBoxes[i] = new StatisticBox(this, i);
                add(statisticBoxes[i]);
            }

        }

        // reset statistic boxes
        public void resetStatistics() {
            for (int i = 0; i < statisticBoxes.length; i++) {
                statisticBoxes[i].setStatistics(i);

            }
        }

    }

    // statistic box class
    class StatisticBox extends JPanel {

        private static final long serialVersionUID = 1L;
        private JLabel statisticsLabel = new JLabel("Statistic", JLabel.CENTER);
        private JLabel statisticsValue = new JLabel("Value", JLabel.CENTER);
        private JButton leftStatisticButton = new JButton("<<");
        private JButton rightStatisticButton = new JButton(">>");
        private int statisticIndex = 0;
        private StatisticsPanel statisticsPanel;

        public StatisticBox(StatisticsPanel statisticsPanel, int index) {
            this.statisticsPanel = statisticsPanel;

            setLayout(new BorderLayout());

            statisticIndex = index;

            // statsitic info
            add(statisticsLabel, "North");
            add(statisticsValue, "Center");

            // right button
            add(rightStatisticButton, "East");
            rightStatisticButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {

                        statisticIndex = getNextStatisticIndex(statisticIndex);
                        showStatistic();

                    }

                });

            // left button
            add(leftStatisticButton, "West");
            leftStatisticButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {

                        statisticIndex = getPrevStatisticIndex(statisticIndex);
                        showStatistic();

                    }

                });

        }

        // find next avbaiable statistic index
        private int getNextStatisticIndex(int index) {
            int k = (index + 1) % MAX_STATISTCS;
            for (int i = 0; i < MAX_STATISTCS; i++) {
                boolean found = false;
                for (int j = 0; j < NUM_STATISTIC_BOXES; j++) {
                    if (k == statisticsPanel.statisticBoxes[j].statisticIndex)
                        found = true;
                }

                if (!found)
                    return k;

                k = (k + 1) % MAX_STATISTCS;
            }

            return index;
        }

        // find next avbaiable statistic index
        private int getPrevStatisticIndex(int index) {
            int k = ((index - 1) + MAX_STATISTCS) % MAX_STATISTCS;
            for (int i = 0; i < MAX_STATISTCS; i++) {
                boolean found = false;
                for (int j = 0; j < NUM_STATISTIC_BOXES; j++) {
                    if (k == statisticsPanel.statisticBoxes[j].statisticIndex)
                        found = true;
                }

                if (!found)
                    return k;

                k = ((k - 1) + MAX_STATISTCS) % MAX_STATISTCS;
            }

            return index;
        }

        // show Statistic
        public void showStatistic() {
            // average review score
            if (statisticIndex == 0) {
                statisticsLabel.setText("Average Review Score:");
                statisticsValue.setText(String.format("%.1f", statistics.getAverageReviewScore()));
            }

            // total number of available properties
            else if (statisticIndex == 1) {
                statisticsLabel.setText("Total number of available properties:");
                statisticsValue.setText(String.format("%d", statistics.getNumberProperties()));
            }

            // total number of homes apartments
            else if (statisticIndex == 2) {
                statisticsLabel.setText("Total number of homes and apartments:");
                statisticsValue.setText(String.format("%d", statistics.getTotalNumberOfHomesandApartments()));
            }

            // pricest neighbor hood
            else if (statisticIndex == 3) {
                statisticsLabel.setText("Pricest Neighbor hood");
                statisticsValue.setText(statistics.pricestNeighborHood());
            }

            // lowest cost neighbor hood
            else if (statisticIndex == 4) {
                statisticsLabel.setText("Lowest Cost Neighbor hood");
                statisticsValue.setText(statistics.lowestNeighborHood());
            }

            // lowest price
            else if (statisticIndex == 5) {
                statisticsLabel.setText("Lowest Price:");
                statisticsValue.setText(String.format("%d", statistics.getMinPrice()));
            }

            // highest price
            else if (statisticIndex == 6) {
                statisticsLabel.setText("Highest Price:");
                statisticsValue.setText(String.format("%d", statistics.getMaxPrice()));
            }

            // number of neighbor hoods
            else if (statisticIndex == 7) {
                statisticsLabel.setText("Number of Neighbor hoods:");
                statisticsValue.setText(String.format("%d", statistics.getNumberOfNeighborHoods()));
            }
        }

        // set statistic
        public void setStatistics(int index) {
            statisticIndex = index;
            showStatistic();
        }

    }
    
    class FinalPanel extends JPanel{
	    private static final long serialVersionUID = 1L;

	    public FinalPanel() {
	              
	        
	        setLayout(new BorderLayout());
	        // print messages
                JLabel label1 = new JLabel("Congratulations on successfully booking with our application!", JLabel.CENTER);
                label1.setFont(new Font("TimesRoman", Font.BOLD, 24));
                JLabel label2 = new JLabel("Thank you for using our application.", JLabel.CENTER);
                label2.setFont(new Font("TimesRoman", Font.BOLD, 24));
	        add(label1, "North");
	        add(label2, "Center");
	       
	   }
	   //public class AL extends JPanel implements ActionListener{
	  
	   }
    
    
}
