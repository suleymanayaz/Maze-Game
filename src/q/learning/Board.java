/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q.learning;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author AYAZ
 */
public class Board extends JFrame implements ActionListener {

    int boardx = 25, boardy = 25;
    JPanel oyun, settings, generate, sonuc, sonuclar, oyuncular;
    JButton start, reset, grafik1, grafik2;
    JTextField startLoc, finishLoc;
    JLabel startLocLab, finishLocLab;
    Oyun oyunBoard;
    int deger;
    int[] Y;
    OyunUI oyunUI;
    File file = new File("C:\\Users\\AYAZ\\Documents\\NetBeansProjects\\Q-Learning\\engel.txt");
    ArrayList<ArrayList<Integer>> gecici1;
    ArrayList<Double> gecici2;
    ArrayList<ArrayList<Point>> gecici3;
    JPanel grafik;
    ChartPanel chartPanel, chartPanel2;

    public static void main(String[] args) {
        new Board();
    }

    public Board() {
        //setLayout(new FlowLayout(0, 100, 0));
        this.setLayout(new GridLayout(1, 3));
        this.setTitle("Q-Learning");
        oyun = new JPanel();
        oyunBoard = new Oyun(boardx, boardy);
        oyunUI = new OyunUI(oyunBoard);
        oyun.add(oyunUI);
        createSettings();
        oyun.add(settings);
        this.add(oyun);
        createGraph();
        this.add(grafik);
        this.setMinimumSize(new Dimension(1920, 1080));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createGraph() {
        grafik = new JPanel();
        XYSeries series = new XYSeries("Graph");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.setAutoWidth(true);
        JFreeChart chart = ChartFactory.createXYBarChart("Episode via cost Graph", "Episode", false, "Cost", dataset, PlotOrientation.VERTICAL, false, false, false);
        JFreeChart chart2 = ChartFactory.createXYBarChart("Episode via cost Step", "Episode", false, "Cost", dataset, PlotOrientation.VERTICAL, false, false, false);
        chartPanel = new ChartPanel(chart);
        chartPanel2 = new ChartPanel(chart2);
        grafik.add(chartPanel);
        grafik.add(chartPanel2);

    }

    public void createSettings() {
        settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        settings.setBackground(Color.YELLOW);
        generate = new JPanel();
        generate.setLayout(new FlowLayout());
        start = new JButton("Start");
        start.addActionListener(this);
        String string1 = "Number";
        startLoc = new JTextField(string1, 5);
        startLocLab = new JLabel("Start Location :  ");
        finishLoc = new JTextField(string1, 5);
        finishLocLab = new JLabel("Finish Location : ");
        reset = new JButton("Reset");
        reset.addActionListener(this);
        grafik1 = new JButton("Grafik");
        grafik1.addActionListener(this);
        JPanel butonlar = new JPanel();
        butonlar.add(start);
        butonlar.add(reset);
        butonlar.add(grafik1);
        JPanel settingLocationStart = new JPanel();
        settingLocationStart.setLayout(new FlowLayout());
        settingLocationStart.add(startLocLab);
        settingLocationStart.add(startLoc);
        settingLocationStart.add(finishLocLab);
        settingLocationStart.add(finishLoc);

        JPanel settingIn = new JPanel();
        settingIn.setLayout(new BoxLayout(settingIn, BoxLayout.Y_AXIS));
        settingIn.add(settingLocationStart);

        settingIn.add(butonlar);
        generate.add(settingIn);
        generate.setBorder(BorderFactory.createTitledBorder("Settings"));
        settings.add(generate);

    }

    public void fileWrite() {
        try {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < oyunBoard.getLines(); i++) {
                for (int j = 0; j < oyunBoard.getCols(); j++) {
                    if (oyunBoard.getGrid()[i][j].isDuvar()) {
                        fw.write("\n(" + i + "," + j + ",K)" + "\r\n");
                    } else if (oyunBoard.getGrid()[i][j].p.equals(oyunBoard.getStart())) {
                        fw.write("\n(" + i + "," + j + ",M)" + "\r\n");
                    } else if (oyunBoard.getGrid()[i][j].p.equals(oyunBoard.getFinish())) {
                        fw.write("\n(" + i + "," + j + ",Y)" + "\r\n");
                    } else {
                        fw.write("\n(" + i + "," + j + ",B)" + "\r\n");
                    }
                }
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("HATAA");
        }
    }

    public JFreeChart grafikAyarlari(ArrayList<Double> maliyetler) {
        int counter = 0;
        XYSeries series = new XYSeries("sasa");
        for (Double ar : maliyetler) {
            counter++;
            series.add(counter, ar);

        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.setAutoWidth(true);
        JFreeChart chart = ChartFactory.createXYBarChart("Episode via cost Graph", "Episode", false, "Cost", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(deger, new BasicStroke(0.1f));
        renderer.setBaseShapesFilled(false);
        renderer.setDrawOutlines(false);
        plot.getDomainAxis().setUpperMargin(0);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        return chart;
    }

    public JFreeChart grafik2Ayarlari(ArrayList<ArrayList<Point>> pointler) {
        int counter = 0;
        XYSeries series = new XYSeries("Graph");
        for (ArrayList<Point> ar : pointler) {
            counter++;
            series.add(counter, (double) ar.size());
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.setAutoWidth(true);

        JFreeChart chart = ChartFactory.createXYBarChart("Episode via cost Step", "Episode", false, "Steps", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(deger, new BasicStroke(0.5f));
        renderer.setBaseShapesFilled(false);
        renderer.setDrawOutlines(false);
        plot.getDomainAxis().setUpperMargin(0);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        return chart;
    }

    public void resetBackground() {
        for (int i = 0; i < this.oyunBoard.getLines(); i++) {
            for (int j = 0; j < this.oyunBoard.getCols(); j++) {
                if (oyunBoard.getGrid()[i][j].isDuvar()) {
                    continue;
                } else {
                    oyunUI.getGridUI()[i][j].setBackground(oyunUI.dbg);
                }

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == start) {
            System.out.println(startLoc.getText());
            System.out.println(finishLoc.getText());
            resetBackground();
            boolean giris = true;
            String startLocString = startLoc.getText();
            String finishLocString = finishLoc.getText();
            if (startLocString.equals("Number") || finishLocString.equals("Number") || startLocString.equals("") || finishLocString.equals("")) {
                Frame frameForPopUp = new JFrame();
                JOptionPane.showMessageDialog(frameForPopUp, "Start  or finish input invalid  location!!");
                giris = false;
            }
            if (giris) {
                Point startP = findGrid(Integer.parseInt(startLocString));
                Point finishP = findGrid(Integer.parseInt(finishLocString));
                if (startP == null || finishP == null || oyunBoard.getGrid()[startP.x][startP.y].isDuvar() || oyunBoard.getGrid()[finishP.x][finishP.y].isDuvar()) { // if leri ayırabilrisn.
                    Frame frameForPopUp = new JFrame();
                    JOptionPane.showMessageDialog(frameForPopUp, "Start  or finish input invalid  location!!");
                } else {
                    int startLocX = startP.x;
                    int startLocY = startP.y;
                    int finishLocX = finishP.x;
                    int finishLocY = finishP.y;
                    oyunBoard.setStart(new Point(startLocX, startLocY));
                    oyunBoard.setFinish(new Point(finishLocX, finishLocY));
                    oyunUI.grid[startLocX][startLocY].setBackground(Color.blue);
                    oyunUI.grid[finishLocX][finishLocY].setBackground(Color.green);
                    // ----------------------------------------------------- //
                    fileWrite();
                    // ----------------------------------------------------- //
                    Q_Learning q = new Q_Learning(oyunBoard);

                    q.run();
                    q.printResult();
                    //ArrayList <Point> pointAr = q.showPolicy();
                    //oyunUI.cizme(q.pointler);
                    q.showPolicy();
                    oyunUI.yolcizme(q.Y);
                    gecici1 = q.kazanclar;
                    gecici2 = q.maliyetler;
                    gecici3 = q.pointler;
                    Y = q.Y;
                }
            }
        } else if (source == reset) {
            oyun.removeAll();
            oyunBoard = new Oyun(boardx, boardy);
            oyunUI = new OyunUI(oyunBoard);
            oyun.add(oyunUI);
            oyun.add(settings);
            grafik.removeAll();
            grafik.add(chartPanel);
            grafik.add(chartPanel2);
            grafik.repaint();
            grafik.revalidate();
            oyun.repaint();
            oyun.revalidate();

        } else {
            ChartPanel chartPanel2 = new ChartPanel(grafikAyarlari(gecici2));
            grafik.removeAll();
            grafik.add(chartPanel2);
            System.out.println("Birinci Tablo Hazır!");
            chartPanel2 = new ChartPanel(grafik2Ayarlari(gecici3));
            grafik.add(chartPanel2);
            this.repaint();
            this.revalidate();
            System.out.println("İkinci Tablo Hazır!");
        }
    }

    public Point findGrid(int s) {
        Point p = null;
        for (int i = 0; i < oyunBoard.getLines(); i++) {
            for (int j = 0; j < oyunBoard.getCols(); j++) {
                if (oyunBoard.getGrid()[i][j].getSira() == s) {
                    p = new Point(i, j);
                }
            }
        }
        return p;
    }

}
