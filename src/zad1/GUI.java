package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.*;

public class GUI extends JFrame {
    private JFXPanel wikiPage;
    private JButton JBznajdz;
    private JTextField JTkraj, JTmiasto, JTvaluta;
    private JLabel JLkraj, JLmiasto, JLvaluta, JlwpiszDane;
    private String temperatura, url="https://en.wikipedia.org/wiki/";
    private Double valutaDuPodanej, valutaDoZlotego;
    private JTextArea wynikoweDane;

    public GUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 650);
        setLayout(null);
        JlwpiszDane = new JLabel("Wpisz dane:");
        JlwpiszDane.setBounds(100, 0, 150, 30);
        add(JlwpiszDane);
        JLkraj = new JLabel("Podaj kraj:");
        JLkraj.setBounds(50,30,150,30);
        add(JLkraj);
        JTkraj = new JTextField();
        JTkraj.setBounds(50,55,150,30);
        add(JTkraj);
        JLmiasto = new JLabel("Podaj miasto:");
        JLmiasto.setBounds(240,30,150,30);
        add(JLmiasto);
        JTmiasto = new JTextField();
        JTmiasto.setBounds(240,55,150,30);
        add(JTmiasto);
        JLvaluta = new JLabel("Podal valute:");
        JLvaluta.setBounds(50,100,150,30);
        add(JLvaluta);
        JTvaluta = new JTextField();
        JTvaluta.setBounds(50,125,150,30);
        add(JTvaluta);
        JBznajdz = new JButton("Znajdz");
        JBznajdz.setBounds(240,125,150,30); 
        add(JBznajdz);
        wynikoweDane = new JTextArea();
        wynikoweDane.setBounds(50,180,500,300);
        wynikoweDane.setLineWrap(true);
        add(wynikoweDane);
        wikiPage = new JFXPanel();
        wikiPage.setBounds(580,30,650,500);
        add(wikiPage);
        setVisible(true);
        JBznajdz.addActionListener(e -> {
            wynikoweDane.setText("");
            if(!(JTkraj.getText().equals("")&&JTmiasto.getText().equals("")&&JTvaluta.getText().equals(""))){
                Service service= new Service(JTkraj.getText());
                temperatura=service.getWeather(JTmiasto.getText());
                valutaDuPodanej=service.getRateFor(JTvaluta.getText());
                valutaDoZlotego=service.getNBPRate();
                wynikoweDane.append(temperatura);
                wynikoweDane.append("\n1 " + service.getCurrency() + " do " + JTvaluta.getText() + ": " + valutaDuPodanej);
                wynikoweDane.append("\n1 " + service.getCurrency() + " do PLN: " +valutaDoZlotego);
            }
            if(!JTmiasto.getText().equals("")){
                Platform.runLater(() -> {
                    WebView webView = new WebView();
                    wikiPage.setScene(new Scene(webView));
                    webView.getEngine().load(url + JTmiasto.getText());
                });
            }
        });
    }
}
