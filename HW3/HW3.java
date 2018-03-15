
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import javax.swing.DefaultComboBoxModel;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JScrollPane;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.HashMap;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.text.Format;
import java.text.ParseException;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.util.Calendar;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

public class hw3 extends AbstractFormatter {
    int count1 = 0;
    private JFrame mainFrame;
    int count2 = 0;
    public HashMap<Integer, String> userContainer = new HashMap();
    DefaultTableModel tableR;
    DefaultTableModel table1;
    JTable mainTableBusiness;
    JButton buttonForSubmitting;
    String progQuery = "";
    public ArrayList<String> subCatListProg = new ArrayList();
    public ArrayList<String> catListProg = new ArrayList();
    DefaultTableModel tableToDisplay;
    DefaultTableModel userTableValues;
    private JList tag_list;
    JPanel BusinessTablePanel1;
    public HashMap<Integer, String> bisunessContainer = new HashMap();
    JTable bTable;
    String qery1 = "";
    String query2 = "";
    JPanel BusinessTablePanel;
    JTable tableBusinessDisplay;
    String progQueryPan2 = "";
    JTable UserReviewTable;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    hw3 window = new hw3();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

 
    public hw3() {
        initialize();
    }

    int indexing = 0;
    int[] array = new int[100];
    int[] listOfCats = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private JTextField queryOutput_2;
    JTextField checkinTextField;
    private JTextField votesTextField;
    private JTextField reviewTexts;
    private JTextField qe1;
    private JTextField ratingValues;
    private JTextField starTexts;
    private JTextField friendTexts;
    String[] catOfProf = { "Active Life", "Arts & Entertainment", "Automotive", "Beauty & Spas", "Cafes", "Car Rental",
            "Convenience Stores", "Dentists", "Department Stores", "Doctors", "Drugstores", "Education",
            "Event Planning & Services", "Flowers & Gifts", "Food", "Grocery", "Hardware Stores", "Health & Medical",
            "Home & Garden", "Home Services", "Hospitals", "Hotels & Travel", "Medical Centers", "Nightlife",
            "Nurseries & Gardening", "Restaurants", "Shopping", "Transportation" };



    private void changeCatPanel(JPanel val1, int bins, String[] val2) {
        subCatListProg.clear();
        ArrayList<String> categoryName = new ArrayList<String>();
        PreparedStatement preparedStmnt = null;
        Connection conn = null;
        int noOfSubcat = 0;
        try {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya", "Saumya023");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String midQuery = "";
        for (int ind = 0; ind < bins; ind++) {
            midQuery = midQuery + "'" + val2[ind] + "'" + ",";
        }
        midQuery = midQuery.replaceAll(",$", "");
        qery1 = "select distinct(SubCatName) from TBusinessSubCategory where BId in (select b.BId from TBUSINESSCATEGORY b where b.CatName  in ("
                + midQuery + ")) order by SubCatName   ";
        query2 = "select distinct(BId) from TBusinessSubCategory where BId in (select b.BId from TBUSINESSCATEGORY b,TBusinessSubCategory c where b.BId=c.BId and b.CatName in ("
                + midQuery + ") ";
        try {
            preparedStmnt = conn.prepareStatement(qery1);
            ResultSet resultSet;
            resultSet = preparedStmnt.executeQuery();

            while (resultSet.next()) {
                String subcat = resultSet.getString(1);
                categoryName.add(subcat);
                noOfSubcat += 1;
            }
            conn.close();
            resultSet.close();
            preparedStmnt.close();

            JScrollPane scroll3 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll3.setBounds(10, 10, 330, 200);
            val1.add(scroll3);
            JCheckBox[] scroll2val = new JCheckBox[noOfSubcat];
            JPanel scrollablePan = new JPanel();
            scrollablePan.setLayout(new BoxLayout(scrollablePan, BoxLayout.Y_AXIS));
            int h2 = 20;
            for (int i = 0; i < noOfSubcat; i++) {
                scroll2val[i] = new JCheckBox(categoryName.get(i));
                scroll2val[i].setBounds(10, h2, 150, 20);
                scrollablePan.add(scroll2val[i]);
                h2 += 20;
            }
            scroll3.setViewportView(scrollablePan);

            int i = 0;
            for (i = 0; i < noOfSubcat; i++) {
                Object x = scroll2val[i];
                int pi = i;
                scroll2val[i].addItemListener((ItemListener) new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {

                        if (((AbstractButton) x).isSelected() == true) {
                            subCatListProg.add(((AbstractButton) x).getText());

                        } else {
                            subCatListProg.remove(((AbstractButton) x).getText());

                        }
                    }
                });
            }

        } catch (Exception e) {
        }

    }

    private void openReviewFrame(int n) {

        mainTableBusiness.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ResultSet resultSet;
                Connection connection = null;
                try {

                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya",
                            "Saumya023");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (e.getClickCount() == 1 && n == count1 - 1) {
                    JFrame frame = new JFrame("Business Review");
                    JPanel mainWindow = new JPanel();
                    mainWindow.setLayout(new FlowLayout());
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    bTable = new JTable();
                    bTable.setBounds(952, 75, 938, 691);

                    tableR = new DefaultTableModel();
                    bTable.setModel(tableR);
                    tableR.addColumn("Business Name");
                    tableR.addColumn("Text");
                    tableR.addColumn("Stars");
                    JScrollPane reviewResultPane = new JScrollPane(bTable);
                    mainWindow.add(reviewResultPane);

                    String[] rowDetail = new String[3];
                    try {
                        CallableStatement call = connection
                                .prepareCall("SELECT bid,reviewtext,stars FROM Treview where BId=?");
                        call.setString(1, bisunessContainer.get(row));
                        resultSet = call.executeQuery();
                        while (resultSet.next()) {
                            rowDetail = new String[] { resultSet.getString("bid"), resultSet.getString("reviewtext"),
                                    resultSet.getString("STARS") };

                            tableR.addRow(rowDetail);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.getContentPane().add(mainWindow);
                    frame.setSize(1000, 1000);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
        });

    }

    private void openUserReviewFrame(int variableFunc) {

        tableBusinessDisplay.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEve) {
                ResultSet resultSet;
                Connection connection = null;
                try {

                    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya",
                            "Saumya023");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (mouseEve.getClickCount() == 1 && variableFunc == count2 - 1) {
                    JFrame mainWindowDisplay = new JFrame("Review");
                    JPanel subPanel = new JPanel();
                    subPanel.setLayout(new FlowLayout());
                    JTable tableVersion = (JTable) mouseEve.getSource();
                    int row = tableVersion.getSelectedRow();

                    UserReviewTable = new JTable();
                    UserReviewTable.setBounds(952, 75, 938, 691);

                    table1 = new DefaultTableModel();
                    UserReviewTable.setModel(table1);
                    table1.addColumn("Username");
                    table1.addColumn("Text");
                    table1.addColumn("Star");
                    JScrollPane reviewResultPane = new JScrollPane(UserReviewTable);
                    subPanel.add(reviewResultPane);

                    String[] rowObj = new String[3];
                    try {
                        CallableStatement call = connection
                                .prepareCall("select USERID,REVIEWTEXT,STARS from Treview where userid=?");
                        call.setString(1, userContainer.get(row));
                        resultSet = call.executeQuery();
                        while (resultSet.next()) {
                            rowObj = new String[] { resultSet.getString("USERID"), resultSet.getString("REVIEWTEXT"),
                                    resultSet.getString("STARS") };
                            table1.addRow(rowObj);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    mainWindowDisplay.getContentPane().add(subPanel);
                    mainWindowDisplay.setSize(987, 1020);
                    mainWindowDisplay.setLocationRelativeTo(null);
                    mainWindowDisplay.setVisible(true);
                }
            }
        });

    }

    public void autoFunction(int statement, int button, JPanel panel) {
        if (statement == -1) {
            listOfCats[button] = 0;
            int catInd = 0;
            indexing -= 1;
            String[] openCategories = new String[indexing];
            for (int ind = 0; ind < 28; ind++) {
                if (listOfCats[ind] == 1) {
                    openCategories[catInd] = catOfProf[ind];
                    catInd += 1;
                }
            }
            panel.removeAll();
            catListProg.clear();
            for (String e : openCategories) {
                catListProg.add(e);
            }
            if (catListProg.size() != 0) {
                changeCatPanel(panel, indexing, openCategories);
                panel.revalidate();
                panel.repaint();
            } else {
                buttonForSubmitting.setEnabled(false);
                changeCatPanel(panel, indexing, openCategories);
                panel.revalidate();
                panel.repaint();

            }
        } else {
            listOfCats[button] = 1;
            indexing += 1;
            int indexingCat = 0;
            String[] openCategories = new String[indexing];
            for (int ind = 0; ind < 28; ind++) {
                if (listOfCats[ind] == 1) {
                    openCategories[indexingCat] = catOfProf[ind];
                    indexingCat += 1;
                }
            }
            panel.removeAll();
            catListProg.clear();
            for (String e : openCategories) {
                catListProg.add(e);
            }
            changeCatPanel(panel, indexing, openCategories);
            panel.revalidate();
            panel.repaint();
        }

    }
    private void initialize() {

        JTabbedPane MainPan = new JTabbedPane();
        int no_of_cat = 0;
        ArrayList<String> allCategories = new ArrayList<String>();
        PreparedStatement preparedStmnt = null;
        Connection connection = null;
        try {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya", "Saumya023");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String queryE = "SELECT  DISTINCT(CatName) FROM TBUSINESSCATEGORY where CatName in ('Active Life','Arts & Entertainment','Automotive','Car Rental','Cafes','Beauty & Spas','Convenience Stores','Dentists','Doctors','Drugstores','Department Stores','Education','Event Planning & Services','Flowers & Gifts','Food','Health & Medical','Home Services','Home & Garden','Hospitals','Hotels & Travel','Hardware Stores','Grocery','Medical Centers','Nurseries & Gardening','Nightlife','Restaurants','Shopping','Transportation') order by CatName ";
            preparedStmnt = connection.prepareStatement(queryE);
            ResultSet resultSet;
            resultSet = preparedStmnt.executeQuery();

            while (resultSet.next()) {
                String subcat = resultSet.getString(1);
                allCategories.add(subcat);
                no_of_cat += 1;
            }
            connection.close();
            resultSet.close();
            preparedStmnt.close();

            mainFrame = new JFrame();
            mainFrame.setBounds(100, 100, 1024, 768);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel_body = new JPanel();
            panel_body.setBackground(new Color(255, 228, 181));
            panel_body.setBorder(new LineBorder(new Color(0, 0, 0), 2));
            panel_body.setBounds(712, 16, 643, 343);
            mainFrame.getContentPane().add(MainPan);
            MainPan.add(panel_body);
            panel_body.setLayout(null);

            JPanel panForCateg = new JPanel();
            panForCateg.setBorder(new LineBorder(new Color(0, 0, 0)));
            panForCateg.setBounds(10, 10, 364, 237);
            panel_body.add(panForCateg);
            panForCateg.setLayout(null);

            tableToDisplay = new DefaultTableModel();
            tableToDisplay.addColumn("Business Name");
            tableToDisplay.addColumn("City");
            tableToDisplay.addColumn("State");
            tableToDisplay.addColumn("Stars");

            JScrollPane scroll2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll2.setBounds(10, 10, 330, 200);
            panForCateg.add(scroll2);
            JCheckBox[] saum = new JCheckBox[no_of_cat];

            JPanel sScrollPanel = new JPanel();
            sScrollPanel.setLayout(new BoxLayout(sScrollPanel, BoxLayout.Y_AXIS));
            int h = 20;
            for (int i = 0; i < no_of_cat; i++) {
                saum[i] = new JCheckBox(allCategories.get(i));
                saum[i].setBounds(10, h, 150, 20);
                sScrollPanel.add(saum[i]);
                h += 20;
            }
            scroll2.setViewportView(sScrollPanel);

            JPanel panForSubCat = new JPanel();
            panForSubCat.setBorder(new LineBorder(new Color(0, 0, 0)));
            panForSubCat.setBounds(166, 377, 413, 237);
            panel_body.add(panForSubCat);
            panForSubCat.setLayout(null);

            JPanel panForCheckin = new JPanel();
            panForCheckin.setBackground(Color.GRAY);
            panForCheckin.setLayout(null);
            panForCheckin.setBorder(new LineBorder(new Color(0, 0, 0)));
            panForCheckin.setBounds(10, 377, 146, 237);
            panel_body.add(panForCheckin);

            JComboBox fromBox = new JComboBox();
            fromBox.setModel(new DefaultComboBoxModel(new String[] { "Select", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday", "Sunday" }));
            fromBox.setSelectedIndex(0);
            fromBox.setBounds(10, 31, 76, 21);
            panForCheckin.add(fromBox);

            JLabel lblNewLabel = new JLabel("From");
            lblNewLabel.setBounds(10, 11, 45, 13);
            panForCheckin.add(lblNewLabel);

            JComboBox toDetailBox = new JComboBox();
            toDetailBox.setModel(new DefaultComboBoxModel(new String[] { "Select", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday", "Sunday" }));
            toDetailBox.setSelectedIndex(0);
            toDetailBox.setBounds(10, 82, 76, 21);
            panForCheckin.add(toDetailBox);

            JLabel lblTo = new JLabel("To");
            lblTo.setBounds(10, 62, 45, 13);
            panForCheckin.add(lblTo);

            JLabel pBoxes = new JLabel("No. of Checkins");
            pBoxes.setBounds(10, 128, 84, 13);
            panForCheckin.add(pBoxes);

            JComboBox checkBoxing = new JComboBox();
            checkBoxing.setToolTipText("cdc");
            checkBoxing.setModel(new DefaultComboBoxModel(new String[] { "> < =", ">", "<", "=" }));
            checkBoxing.setSelectedIndex(0);

            checkBoxing.setBounds(10, 148, 84, 21);
            panForCheckin.add(checkBoxing);

            checkinTextField = new JTextField();
            checkinTextField.setBounds(10, 191, 96, 19);
            panForCheckin.add(checkinTextField);
            checkinTextField.setColumns(10);
            JComboBox fromHourBox = new JComboBox();
            fromHourBox.setModel(
                    new DefaultComboBoxModel(new String[] {"select", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
            fromHourBox.setBounds(96, 31, 40, 21);
            panForCheckin.add(fromHourBox);
            JComboBox selectedHour = new JComboBox();
            selectedHour.setModel(
                    new DefaultComboBoxModel(new String[] {"select", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
            selectedHour.setSelectedIndex(0);
            selectedHour.setBounds(96, 82, 40, 21);
            panForCheckin.add(selectedHour);
            JLabel lblHour = new JLabel("Hour");
            lblHour.setBounds(96, 11, 45, 13);
            panForCheckin.add(lblHour);
            JPanel panForReview = new JPanel();
            panForReview.setLayout(null);
            panForReview.setBorder(new LineBorder(new Color(0, 0, 0)));
            panForReview.setBounds(408, 11, 171, 323);
            panel_body.add(panForReview);
            JLabel label = new JLabel("From");
            label.setBounds(10, 11, 45, 13);
            panForReview.add(label);
            JComboBox rBoxSel = new JComboBox();
            rBoxSel.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
            rBoxSel.setBounds(10, 148, 84, 21);
            panForReview.add(rBoxSel);
            JPanel userPanel = new JPanel();
            userPanel.setBackground(new Color(245, 222, 179));
            userPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
            userPanel.setForeground(Color.BLACK);
            MainPan.addTab("User", null, userPanel, null);
            MainPan.addTab("Business", null, panel_body, null);
            JPanel userDetailPanel = new JPanel();
            userDetailPanel.setBackground(Color.GRAY);
            userDetailPanel.setBounds(84, 242, 433, 307);
            userPanel.add(userDetailPanel);
            userDetailPanel.setLayout(null);
            JLabel label_2 = new JLabel("Hour");
            label_2.setBounds(96, 62, 45, 13);
            panForCheckin.add(label_2);
            JDateChooser fromDate = new JDateChooser();
            fromDate.setDateFormatString("yyyy-MM-dd");
            fromDate.setBounds(10, 25, 119, 27);
            panForReview.add(fromDate);
            JLabel label_1 = new JLabel("To");
            label_1.setBounds(10, 62, 45, 13);
            panForReview.add(label_1);
            JDateChooser chooser = new JDateChooser();
            chooser.setDateFormatString("yyyy-MM-dd");
            chooser.setBounds(124, 32, 129, 27);
            userDetailPanel.add(chooser);
            JLabel lblStarRating = new JLabel("Star Rating");
            lblStarRating.setBounds(10, 128, 84, 13);
            panForReview.add(lblStarRating);
            ratingValues = new JTextField();
            ratingValues.setColumns(10);
            ratingValues.setBounds(10, 191, 96, 19);
            panForReview.add(ratingValues);

            JDateChooser endDate = new JDateChooser();
            endDate.setDateFormatString("yyyy-MM-dd");
            endDate.setBounds(10, 85, 119, 27);
            panForReview.add(endDate);

            JComboBox comboBox_10 = new JComboBox();
            comboBox_10.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
            comboBox_10.setBounds(10, 248, 84, 21);
            panForReview.add(comboBox_10);

            votesTextField = new JTextField();
            votesTextField.setColumns(10);
            votesTextField.setBounds(10, 294, 96, 19);
            panForReview.add(votesTextField);

            JLabel lblVotes = new JLabel("Votes");
            lblVotes.setBounds(10, 225, 45, 13);
            panForReview.add(lblVotes);

            JLabel lblValues = new JLabel("Values");
            lblValues.setBounds(10, 279, 45, 13);
            panForReview.add(lblValues);

            buttonForSubmitting = new JButton("Search");

            buttonForSubmitting.addActionListener(new ActionListener() {
                Format formatter;
                public String stringDatelast;
                public String stringDateBeg;
                public String businessQuery;
                public String mainQueryForExecution;

                public void actionPerformed(ActionEvent arg0) {
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                    if (fromDate.getDate() == null) {
                        stringDateBeg = "0";
                    } else {
                        stringDateBeg = (formatter.format(fromDate.getDate()));
                    }
                    if (endDate.getDate() == null) {
                        stringDatelast = "0";
                    } else {
                        stringDatelast = (formatter.format(endDate.getDate()));
                    }
                    int valueGreater = rBoxSel.getSelectedIndex();
                    String repeatingVal = "";
                    if (valueGreater == 0) {
                        repeatingVal = ">";
                    }
                    if (valueGreater == 1) {
                        repeatingVal = "<";
                    } if (valueGreater == 2) {
                        repeatingVal = "=";
                    }
                    int starvalue = 0;
                    String makerBoxText = ratingValues.getText();
                    if (!makerBoxText.equals("")) {
                        try {
                            starvalue = Integer.parseInt(makerBoxText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        starvalue = -1;
                    }
                    String underVote = "";

                    int valGreater = comboBox_10.getSelectedIndex();
                    if (valGreater == 0) {
                        underVote = ">";
                    }
                    if (valGreater == 1) {
                        underVote = "<";
                    } if (valGreater == 2) {
                        underVote = "=";
                    }
                    int votes = 0;
                    String boxText = votesTextField.getText();
                    if (!boxText.equals("")) {
                        try {
                            votes = Integer.parseInt(boxText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        votes = -1;
                    }

                    boolean reviewOrnot = false;

                    String endDatestring;
                    if (endDate.getDate() == null) {
                        endDatestring = "0";
                    } else {
                        endDatestring = (formatter.format(endDate.getDate()));
                    }

                    int startIndexTime = fromHourBox.getSelectedIndex();
                    int endIndexTime = selectedHour.getSelectedIndex();

                    String boxCheckin = checkinTextField.getText();
                    int deterCheckin = 0;
                    if (!boxCheckin.equals("")) {
                        try {
                            deterCheckin = Integer.parseInt(boxCheckin);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        deterCheckin = 0;
                    }

                    String value = "";
                    int checkValues = checkBoxing.getSelectedIndex();

                    if (checkValues == 1) {
                        value = ">";
                    }
                    if (checkValues == 2) {
                        value = "<";
                    } if(checkValues == 3) {
                        value = "=";
                    }
                    String finalCheckinQuery = "";
                    if (stringDateBeg.equals("0") || stringDatelast.equals("0")) {
                    }

                    int categorieslen = catListProg.size();
                    int lenOfSubCat = subCatListProg.size();
                    String catquery = "";
                    String revQuery = "";
                    if (categorieslen == 0) {
                        JOptionPane.showMessageDialog(mainFrame, "Error", "A plain message", JOptionPane.PLAIN_MESSAGE);
                    } else {

                        String minq1 = "";
                        for (int ind = 0; ind < catListProg.size(); ind++) {
                            if (ind != catListProg.size() - 1) {
                                minq1 = minq1 + "(SELECT BId FROM TBUSINESSCATEGORY WHERE CatName  =" + "'"
                                        + catListProg.get(ind) + "'" + ")";
                                minq1 += "AND BId IN";
                            } else {
                                minq1 = minq1 + "(SELECT BId FROM TBUSINESSCATEGORY WHERE CatName  =" + "'"
                                        + catListProg.get(ind) + "'" + ")";
                            }
                        }

                        String mainQ1 = minq1;
                        String minq2 = "";
                        String mainQ2 = "";
                        if (subCatListProg.size() > 0) {
                            minq2 = "AND BId IN";
                            for (int catIndex = 0; catIndex < subCatListProg.size(); catIndex++) {
                                if (catIndex != subCatListProg.size() - 1) {
                                    minq2 = minq2 + "(SELECT BId FROM TBusinessSubCategory WHERE SubCatName  =" + "'"
                                            + subCatListProg.get(catIndex) + "'" + ")";
                                    minq2 += "AND BId IN";
                                } else {
                                    minq2 = minq2 + "(SELECT BId FROM TBusinessSubCategory WHERE SubCatName  =" + "'"
                                            + subCatListProg.get(catIndex) + "'" + ")";
                                }
                            }
                            mainQ2 = minq2;
                        }

                        int i = fromBox.getSelectedIndex();
                        ArrayList<String> daysLists = new ArrayList();
                        String storage = "";
                        String[] x = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                                "Sunday" };
                        int endIndexDays = toDetailBox.getSelectedIndex();
                        if (i > 0) {
                            for (int daysArr = i - 1; daysArr < 100; daysArr++) {
                                if (x[daysArr % 7] == x[endIndexDays - 1]) {
                                    daysLists.add(x[daysArr % 7]);
                                    break;
                                }
                                daysLists.add(x[daysArr % 7]);
                            }

                            for (int h = 1; h < daysLists.size() - 1; h++) {
                                storage += "'" + daysLists.get(h) + "',";
                            }
                            storage = storage.replaceAll(",$", "");

                        }

                        String sq3 = "";
                        String mainQ3 = "";
                        String tmp = "";
                        if (fromBox.getSelectedIndex() != 0 && toDetailBox.getSelectedIndex() != 0
                                && fromHourBox.getSelectedIndex() != 0 && selectedHour.getSelectedIndex() != 0) {
                            if (i == endIndexDays) {
                                sq3 = " AND BId  IN (SELECT BId  FROM TCheckin WHERE CheckDay = " + "'" + x[i - 1]
                                        + "'" + " AND CheckHour BETWEEN " + (startIndexTime - 1) + " AND "
                                        + (endIndexTime - 1) + ")";
                            } else {
                                sq3 = "AND BId  IN (SELECT BId  FROM TCheckin WHERE CheckDay  =" + "'" + x[i - 1]
                                        + "'" + " AND CheckHour >=" + (startIndexTime - 1) + "\n" + "UNION \n"
                                        + "SELECT BId FROM TCheckin WHERE CheckDay  IN " + "(" + storage + ")" + "\n"
                                        + "UNION \n" + "SELECT BId FROM TCheckin WHERE CheckDay =" + "'"
                                        + x[endIndexDays - 1] + "'" + " AND  CheckHour < =" + (endIndexTime - 1) + ")";

                            }
                            mainQ3 = sq3;
                        } else if (fromBox.getSelectedIndex() == 0 && toDetailBox.getSelectedIndex() == 0
                                && fromHourBox.getSelectedIndex() != 0 && selectedHour.getSelectedIndex() != 0) {
                            sq3 = " AND BId IN (SELECT BId FROM TCheckin WHERE CheckHour BETWEEN "
                                    + (startIndexTime - 1) + " AND " + (endIndexTime - 1) + ")";
                            mainQ3 = sq3;
                        } else if (fromBox.getSelectedIndex() != 0 && toDetailBox.getSelectedIndex() != 0
                                && fromHourBox.getSelectedIndex() == 0 && selectedHour.getSelectedIndex() == 0) {
                            sq3 = " AND BId IN (SELECT BId FROM TCheckin WHERE CheckDay IN " + "(" + storage + ")"
                                    + ")";
                            mainQ3 = sq3;
                        }
                        String sq4 = "";
                        String mainQ4 = "";
                        if (checkBoxing.getSelectedIndex() != 0) {
                            sq4 = value;
                            mainQ4 = " AND BId IN (SELECT BId FROM TCheckin WHERE CheckCount " + sq4 + " "
                                    + deterCheckin + ")";
                        }
                        String mainQ5 = "";
                        String currentDate = "2019-12-30";
                        if (!stringDateBeg.equals("0") && starvalue == -1 && votes == -1) {
                            if (stringDatelast.equals("0")) {
                                mainQ5 = "and BId IN (SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + currentDate + "'" + ")";
                            } else {
                                mainQ5 = "and BId in (SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + stringDatelast + "'" + ")";
                            }
                        } else if (stringDateBeg.equals("0") && starvalue == -1 && votes != -1) {
                            mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE (coolvote+funnyvote+usefulvote) "
                                    + underVote + " " + votes + ")";
                        } else if (starvalue != -1 && stringDateBeg.equals("0") &&  votes == -1) {
                            mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE Stars " + repeatingVal + " "
                                    + starvalue + ")";
                        } else if (starvalue != -1 && !stringDateBeg.equals("0") && votes == -1) {
                            if (stringDatelast.equals("0")) {
                                mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + currentDate + "'" + " AND Stars "
                                        + repeatingVal + " " + starvalue + ")";
                            } else
                                mainQ5 = "AND BId IN ( SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + stringDatelast + "'" + " AND Stars "
                                        + repeatingVal + " " + starvalue + ")";
                        } else if (!stringDateBeg.equals("0") && starvalue == -1 && votes != -1) {
                            if (stringDatelast.equals("0")) {
                                mainQ5 = "AND BId IN ( SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + currentDate + "'"
                                        + " AND (funnyvote+coolvote+usefulvote) " + underVote + " " + votes + ")";
                            } else
                                mainQ5 = "AND BId IN ( SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + stringDatelast + "'"
                                        + " AND (funnyvote+coolvote+usefulvote) " + underVote + " " + votes + ")";
                        } else if (!stringDateBeg.equals("0") && starvalue != -1 && votes != -1) {
                            if (!stringDatelast.equals("0"))
                                mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + stringDatelast + "'" + " AND Stars "
                                        + repeatingVal + " " + starvalue + " AND (coolvote+funnyvote+usefulvote) "
                                        + underVote + " " + votes + ")";
                            else
                                mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE ReviewDate BETWEEN " + "'"
                                        + stringDateBeg + "'" + " AND " + "'" + currentDate + "'" + " AND Stars "
                                        + repeatingVal + " " + starvalue + " AND (coolvote+funnyvote+usefulvote) "
                                        + underVote + " " + votes + ")";

                        } else if (stringDateBeg.equals("0") && starvalue != -1 && votes != -1)
                            mainQ5 = "AND BId IN (SELECT BId FROM TReview WHERE Stars " + repeatingVal + " "
                                    + starvalue + " AND (funnyvote+coolvote+usefulvote) " + underVote + " " + votes
                                    + ")";
                        mainQueryForExecution = "SELECT DISTINCT b.BName as BName, b.City as City, b.State as State, b.Stars as Stars, b.BId as IDBusiness "
                                + "FROM TBusiness b \r\n" + "WHERE BId IN  ";
                        mainQueryForExecution += mainQ1 + "\r\n" + mainQ2 + "\r\n" + mainQ3 + "\r\n"
                                + mainQ4 + "\r\n" + mainQ5;

                        businessQuery = "";
                    }

                    if (mainTableBusiness.getRowCount() > 0) {
                        for (int i = mainTableBusiness.getRowCount() - 1; i > -1; i--) {
                            tableToDisplay.removeRow(i);
                        }

                    }
                    qe1.setText("");
                    qe1.setText(qe1.getText() + mainQueryForExecution);
                    String[] replaceS = new String[4];
                    int i = 0;
                    String FinalQuery = mainQueryForExecution;
                    try {
                        bisunessContainer.clear();
                        PreparedStatement preparedStatment = null;
                        Connection connection = null;
                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya",
                                "Saumya023");
                        preparedStatment = connection.prepareStatement(FinalQuery);
                        ResultSet resultset = preparedStatment.executeQuery(FinalQuery);
                        while (resultset.next()) {
                            replaceS = new String[] { resultset.getString("BName"), resultset.getString("City"), resultset.getString("State"),
                                    resultset.getString("Stars") };
                            bisunessContainer.put(i++, resultset.getString("IDBusiness"));
                            tableToDisplay.addRow(replaceS);
                        }
                        preparedStatment.close();
                        resultset.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    openReviewFrame(count1);
                    count1++;

                }
            });
            userTableValues = new DefaultTableModel();
            userTableValues.addColumn("Name");
            userTableValues.addColumn("Yelping Date");
            userTableValues.addColumn("Stars");
            tableToDisplay = new DefaultTableModel();
            tableToDisplay.addColumn("Business");
            tableToDisplay.addColumn("City");
            tableToDisplay.addColumn("State");
            tableToDisplay.addColumn("Star");
            buttonForSubmitting.setBounds(118, 631, 373, 41);
            panel_body.add(buttonForSubmitting);
            JPanel panelBusinessTables = new JPanel();
            panelBusinessTables.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
            panel_body.add(panelBusinessTables);
            panelBusinessTables.setVisible(true);
            panelBusinessTables.setBounds(606, 10, 332, 699);
            userPanel.setLayout(null);
            panelBusinessTables.setLayout(null);
            JPanel userPanel_2 = new JPanel();
            userPanel_2.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
            userPanel.add(userPanel_2);
            userPanel_2.setVisible(true);
            userPanel_2.setBounds(641, 21, 341, 669);
            userPanel_2.setLayout(null);
            JScrollPane panelForBusiness_scrollable = new JScrollPane();
            panelForBusiness_scrollable.setBounds(10, 10, 300, 680);
            panelBusinessTables.add(panelForBusiness_scrollable);
            mainTableBusiness = new JTable();
            panelForBusiness_scrollable.setViewportView(mainTableBusiness);
            mainTableBusiness.setModel(tableToDisplay);
            mainTableBusiness.setBorder(new LineBorder(new Color(0, 0, 0)));
            JScrollPane panelScrollDown = new JScrollPane();
            panelScrollDown.setBounds(10, 10, 300, 649);
            userPanel_2.add(panelScrollDown);
            tableBusinessDisplay = new JTable();
            panelScrollDown.setViewportView(tableBusinessDisplay);
            tableBusinessDisplay.setModel(userTableValues);
            tableBusinessDisplay.setBorder(new LineBorder(new Color(0, 0, 0)));

            JComboBox reviewBox = new JComboBox();
            reviewBox.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
            reviewBox.setBounds(115, 90, 108, 27);
            userDetailPanel.add(reviewBox);

            JComboBox starBoxDrop = new JComboBox();
            starBoxDrop.setBackground(Color.LIGHT_GRAY);
            starBoxDrop.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
            starBoxDrop.setBounds(115, 186, 108, 27);
            userDetailPanel.add(starBoxDrop);

            JComboBox friendBox = new JComboBox();
            friendBox.setModel(new DefaultComboBoxModel(new String[] { ">", "<", "=" }));
            friendBox.setBounds(115, 144, 108, 32);
            userDetailPanel.add(friendBox);

            JComboBox andBox = new JComboBox();
            andBox.setModel(new DefaultComboBoxModel(new String[] { "AND", "OR" }));
            andBox.setBounds(115, 244, 108, 27);
            userDetailPanel.add(andBox);
            reviewTexts = new JTextField(0);
            reviewTexts.setBounds(291, 91, 108, 20);
            userDetailPanel.add(reviewTexts);
            reviewTexts.setColumns(10);
            friendTexts = new JTextField();
            friendTexts.setColumns(10);
            friendTexts.setBounds(291, 139, 108, 20);
            userDetailPanel.add(friendTexts);
            starTexts = new JTextField();
            starTexts.setColumns(10);
            starTexts.setBounds(291, 190, 108, 20);
            userDetailPanel.add(starTexts);
            JLabel picked = new JLabel("Value");
            picked.setBounds(257, 142, 45, 13);
            userDetailPanel.add(picked);
            JLabel lblNewLabel_1 = new JLabel("Value");
            lblNewLabel_1.setBounds(257, 94, 45, 13);
            userDetailPanel.add(lblNewLabel_1);
            JLabel lblNewLabel_2 = new JLabel("Member Since");
            lblNewLabel_2.setBounds(10, 32, 95, 27);
            userDetailPanel.add(lblNewLabel_2);
            JLabel label_4 = new JLabel("Value");
            label_4.setBounds(257, 193, 45, 13);
            userDetailPanel.add(label_4);
            JLabel lblNoOfFriends = new JLabel("Friends");
            lblNoOfFriends.setBounds(10, 142, 95, 13);
            userDetailPanel.add(lblNoOfFriends);
            JLabel lblReviewCount = new JLabel("Reviews");
            lblReviewCount.setBounds(10, 94, 95, 13);
            userDetailPanel.add(lblReviewCount);
            JLabel lblAvgStar = new JLabel("Average Stars");
            lblAvgStar.setBounds(10, 193, 75, 13);
            userDetailPanel.add(lblAvgStar);
            JButton userMainButton = new JButton("Execute Query");
            userMainButton.setBounds(156, 147, 278, 39);
            userPanel.add(userMainButton);
            JLabel selectedLabel = new JLabel("Select");
            selectedLabel.setBounds(10, 248, 45, 13);
            userDetailPanel.add(selectedLabel);
            queryOutput_2 = new JTextField();
            queryOutput_2.setBounds(84, 578, 459, 78);
            userPanel.add(queryOutput_2);
            queryOutput_2.setColumns(10);
            userMainButton.addActionListener(new ActionListener() {
                Format formatter;

                public void actionPerformed(ActionEvent arg0) {

                    formatter = new SimpleDateFormat("yyyy-MM");
                    String mainCat;
                    String friendsInTable = "";
                    int reviewVal=reviewBox.getSelectedIndex();
                    int friendVal = friendBox.getSelectedIndex();
                    String reviewInTable="";

                    if (reviewVal == 0) {
                        reviewInTable = ">";
                    }
                    if (reviewVal == 1) {
                        reviewInTable = "<";
                    } if (reviewVal == 1) {
                        reviewInTable = "=";
                    }
                    if (chooser.getDate() != null) {
                        mainCat = (formatter.format(chooser.getDate()));
                    } else {
                        mainCat = "0";
                    }
                    if (friendVal == 0) {
                        friendsInTable = ">";
                    }
                    if (friendVal == 1) {
                        friendsInTable = "<";
                    } if (friendVal == 1) {
                        friendsInTable = "=";
                    }

                    String boxRev = friendTexts.getText();

                    int mainVal = 0;

                    String FriendTextBox = friendTexts.getText();
                    if (!FriendTextBox.equals("")) {
                        try {
                            mainVal = Integer.parseInt(boxRev);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        mainVal = -1;
                    }

                    String reviewText=reviewTexts.getText();
                    int reviewTextBox = 0;

                    if (!reviewText.equals("")) {
                        try {
                            reviewTextBox = Integer.parseInt(reviewText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        reviewTextBox = -1;
                    }

                    int valGreater = friendBox.getSelectedIndex();
                    String starValues = "";
                    if (valGreater == 0) {
                        starValues = ">";
                    }
                    if (valGreater == 1) {
                        starValues = "<";
                    } if (valGreater == 2) {
                        starValues = "=";
                    }
                    String boxText = starTexts.getText();

                    float averageStars = 0.f;

                    String StarTextBox = starTexts.getText();
                    if (!StarTextBox.equals("")) {
                        try {
                            averageStars = Float.parseFloat(boxText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        averageStars = -1;
                    }

                    int xv = andBox.getSelectedIndex();
                    String selectionQueryType = "";
                    if (xv != 0) {
                        selectionQueryType = "OR";
                    } else {
                        selectionQueryType = "AND";
                    }

                    if (tableBusinessDisplay.getRowCount() > 0) {
                        for (int i = tableBusinessDisplay.getRowCount() - 1; i > -1; i--) {
                            userTableValues.removeRow(i);
                        }
                    }

                    progQueryPan2 = "SELECT y.userid as userid,y.uname as username, y.yelpingsince as Yelping_Since,(SELECT avg(stars) from treview where userid=y.userid) as avgstars FROM YUser y  where y.YELPINGSINCE >="
                            + " " + "'" + mainCat + "'" + " " + selectionQueryType + "  "
                            + "(SELECT count(f.userid) from friends f where f.userid=y.userid)" + " " + friendsInTable
                            + mainVal + " " + selectionQueryType + " "
                            + "(SELECT avg(stars) from treview where userid=y.userid)" + starValues + averageStars+" "+selectionQueryType+"(select count(userid) from Treview pm where pm.userid=y.userid)"+reviewInTable+reviewTextBox;
                    String[] rowObj = new String[3];
                    int i = 0;
                    String FinalQuery = progQueryPan2;
                    queryOutput_2.setText("");
                    queryOutput_2.setText(queryOutput_2.getText() + progQueryPan2);
                    try {
                        PreparedStatement preparedStatement = null;
                        Connection connection = null;
                        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:saumdb", "saumya",
                                "Saumya023");
                        preparedStatement = connection.prepareStatement(FinalQuery);
                        ResultSet resultSet = preparedStatement.executeQuery(FinalQuery);
                        while (resultSet.next()) {
                            rowObj = new String[] { resultSet.getString("USERNAME"),
                                    resultSet.getString("Yelping_Since"), resultSet.getString("avgstars") };
                            userContainer.put(i++, resultSet.getString("userid"));
                            userTableValues.addRow(rowObj);
                        }
                        preparedStatement.close();
                        resultSet.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    openUserReviewFrame(count2);
                    count2++;
                }
            });

            buttonForSubmitting.setEnabled(false);

            qe1 = new JTextField();
            qe1.setBounds(10, 258, 364, 108);
            panel_body.add(qe1);
            qe1.setColumns(10);

            int i = 0;
            for (i = 0; i <= no_of_cat-1; i++) {
                Object x = saum[i];
                int maker = i;
                saum[i].addItemListener((ItemListener) new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {

                        if (((AbstractButton) x).isSelected() == true) {
                            buttonForSubmitting.setEnabled(true);
                            autoFunction(1, maker, panForSubCat);
                        } else {
                            autoFunction(-1, maker, panForSubCat);
                        }
                    }
                });
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}