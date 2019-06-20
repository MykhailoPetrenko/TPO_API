/**
 *
 *  @author Petrenko Mykhailo S17006
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {


    private String kraj;
    private String linkDoValuty = "https://api.exchangeratesapi.io/latest?base=";
    private String linkDoZlotego = "http://api.nbp.pl/api/exchangerates/rates/";
    private String krajSK;

    private String currency;

    public Service(String kraj) {
        this.kraj = kraj;
        krajSK = setKrajSK(kraj);
        currency=curency();
    }


    public String getWeather(String miasto) {
        URL url;
        StringBuilder wynik= null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + miasto + ",country=country&mode=json&appid=4a8c5d2d4fcd1c82363f7145bea00cb1");
            BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            wynik= new StringBuilder();
            while((inputLine = in.readLine())!=null)
                wynik.append(inputLine);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(wynik);
        return wynik.toString();
    }

    public Double getRateFor(String valuta) {
        URL url;
        Double wynik=0.0;
        try {
            url = new URL(linkDoValuty+valuta);
            BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine; StringBuilder str= new StringBuilder();
            while((inputLine = in.readLine())!=null)
                str.append(inputLine);
            Pattern p= Pattern.compile("("+currency+"\":[\\d]+.?[\\d]+)");
            Matcher m=p.matcher(str.toString());
            m.find();
            String[] tmp = m.group(0).split(":");
            wynik= Double.parseDouble(tmp[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(wynik);
        return wynik;
    }

    public Double getNBPRate() {
        Double wynik=0.0;
        if(currency.equals("PLN"))
            wynik=1.0;
        else {
            BufferedReader in=null;
            URL url;
            try {
                url = new URL(linkDoZlotego+"a/" + currency);
                URL urlb= new URL(linkDoZlotego+"b/" + currency);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()!=404) {
                    in = new BufferedReader(new InputStreamReader(url.openStream()));

                }
                else if(((HttpURLConnection) urlb.openConnection()).getResponseCode()!= 404){
                    in = new BufferedReader(new InputStreamReader(urlb.openStream()));
                }

                String inputLine;
                StringBuilder str = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    str.append(inputLine);
                String[] tmp= str.toString().split(":");
                String wynikStr= tmp[tmp.length-1].replaceAll("[^\\d\\.]","");
                wynik= Double.parseDouble(wynikStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(wynik);
        return wynik;
    }



    public  String setKrajSK(String kraj){
        URL url;
        BufferedReader in;
        String wynik=null;
        try {
            url = new URL("http://country.io/names.json");
            in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            StringBuilder str= new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                str.append(inputLine);
            }
            Pattern p = Pattern.compile("(\"[A-Z]+\":\\s\""+kraj+"\")");
            Matcher m= p.matcher(str);
            m.find();
            wynik=m.group(0).substring(1,3);
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return wynik;
    }
    public String curency() {
        return Currency.getInstance(new Locale("", krajSK)).getCurrencyCode();
    }

    public String getCurrency() {
        return currency;
    }
}
