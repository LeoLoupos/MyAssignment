package basics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import storage.Database;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;


public class Json {
    public static Location ct = new Location();
    public static Location cs = new Location();

    //ReadAll
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //ReadJson
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    //Crawl wikipedia cities. +  Remove false data.
    public static ArrayList<Location> Wiki() throws IOException{
        ArrayList<Location> arr = new ArrayList<>();
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_cities_in_Switzerland").get();
        Elements el = doc.select("tbody > tr");

        for (Element ele : el) {
            Elements xw = ele.select("td:lt(1)");
            Location ci = new Location();
            String tet = xw.text();
            if(tet.equals("")){
                continue;
            }
            ci.setName(tet);
            arr.add(ci);

            if(xw.text().equals("Zurzach 1")){
                break;
            }

        }

        for(Location es : arr){
            String na = es.getName();

            String tt = getWord(na);
            es.setName(tt);
            String gg = removeSaints(tt);
            es.setName(gg);
            String qq = removeOthers(gg);
            es.setName(qq);
            System.out.println(es.getName());
        }

        return arr;
    }







    //Single Crawling for gui purposes
    public static Location getLoc(String name) throws IOException{
        Location es = new Location();

        try {
            es.setName(name);
            JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/locations?query=" + es.getName().toString());
            System.out.println(json.toString());
            JSONArray stations = json.getJSONArray("stations");
            JSONObject person = stations.getJSONObject(0);
            es.setName((person.getString("name")));
            es.setId(person.getInt("id"));
            JSONObject p = person.getJSONObject("coordinate");
            es.setX(p.getBigDecimal("x").toString());
            es.setY(p.getBigDecimal("y").toString());
        } catch (Exception c) {

        }
        return  es;

    }

    public static ArrayList<Data> getData(Connection c) throws IOException{
        ArrayList<Data> dtar = new ArrayList<>();

        try {
            JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/connections?from=" + c.getIdf() + "&to=" + c.getIdt() + "&direct=1");
            JSONArray station = json.getJSONArray("connections");
            for (int i = 0; i < station.length(); i++) {
                Data dt = new Data();
                JSONObject test = station.getJSONObject(i);
                JSONObject sec = test.getJSONObject("from");
                JSONObject fi = test.getJSONObject("to");
                String depa = sec.getString("departure");
                String arriv = fi.getString("arrival");
                dt.setDepart(depa);
                dt.setArrival(arriv);
                dt.setId(c.getId());
                dtar.add(dt);
            }
        }catch (Exception ec){

        }
        return dtar;

    }








    // Update All Locations
    public static void UpdateLoc(ArrayList<Location> arr) throws IOException{
        int var = 0;
        long time = System.currentTimeMillis();
        long ending =0;

        for(Location es : arr){
            try {

                JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/locations?query=" + es.getName().toString());
                JSONArray stations = json.getJSONArray("stations");
                ending = System.currentTimeMillis() - time;

                JSONObject person = stations.getJSONObject(0);
                es.setId(person.getInt("id"));
                JSONObject p = person.getJSONObject("coordinate");
                es.setX(p.getBigDecimal("x").toString());
                es.setY(p.getBigDecimal("y").toString());
                var++;
                System.out.println(person.getInt("id") + " " + es.getName() + " " + ending + "__" + var);

            } catch (Exception c) {
                var++;
                System.out.println(es.getName()+"  Error  " + es.getId());
            }

            if (var == 300) {
                System.out.println("Full reqs wait!!" + var + "  " + ending);
                var = 0;
                long kjk = 60000 - ending;
                try {
                    Thread.sleep(kjk);
                }catch (Exception c) {

                }
                time = System.currentTimeMillis();
            } else if (ending >= 60000) {
                var = 0;
                time = System.currentTimeMillis();
                System.out.println("Full reqs wait!!" + var + "  " + ending);

            }

        }


    }


    //Double Check
    public static void DoubleCheck(ArrayList<Location> arr) throws IOException{
        int ch = 0;
        for(Location cc : arr){
            if(cc.getId()==0 || cc.getX().equals("") ||  cc.getY().equals("")){
                try {

                    JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/locations?query=" + cc.getName().toString());
                    JSONArray stations = json.getJSONArray("stations");
                    JSONObject person = stations.getJSONObject(0);
                    cc.setId(person.getInt("id"));
                    JSONObject p = person.getJSONObject("coordinate");
                    cc.setX(p.getBigDecimal("x").toString());
                    cc.setY(p.getBigDecimal("y").toString());
                    ch++;
                    System.out.println(person.getInt("id") + " " + cc.getName());
                }catch (Exception c) {

                }
            }
        }

        System.out.println("Doublecheck Done!   Num= " + ch);
    }


    //Update All Connections.
    public static ArrayList<Connection> UpdateCon(ArrayList<Location> arr,ArrayList<Connection> arrCon) throws IOException{
        long end =0;
        int counter = 0;
        long timer = System.currentTimeMillis();
        int auto = 0;
        int var = 0;

        for(int x=0;x<arr.size();x++) {
            var++;

            for (int y = 0; y<arr.size(); y++) {
                if (x == y ) {

                    continue;

                }
                Connection cc = new Connection();

                ct = arr.get(x);
                int id1 = ct.getId();
                cs = arr.get(y);
                int id2 = cs.getId();
                try {
                    JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/connections?from=" + id1 + "&to=" + id2 + "&direct=1");
                    JSONArray station = json.getJSONArray("connections");
                    //System.out.println(station.length());
                    if(json.has("connections")) {
                        JSONObject pers = station.getJSONObject(0);
                        JSONObject perss = pers.getJSONObject("from");
                        JSONObject we = perss.getJSONObject("station");

                        String strf = we.getString("name");
                        int idf = we.getInt("id");

                        JSONObject zx = pers.getJSONObject("to");
                        JSONObject xc = zx.getJSONObject("station");

                        String strt= xc.getString("name");
                        int idt = xc.getInt("id");

                        cc.setFromname(strf);
                        cc.setIdf(idf);
                        cc.setIdt(idt);
                        cc.setToname(strt);
                        cc.setValue(true);
                        cc.setId(auto);
                        System.out.println(strf  + "__" + strt  + "++" + auto +"\n");
                        auto++;

                        counter++;
                        end = System.currentTimeMillis() - timer;

                        Database.storeCon(cc);
                        arrCon.add(cc);
                    }



                } catch (Exception c) {
                    counter++;
                    cc.setToname(arr.get(y).getName());
                    cc.setFromname(arr.get(x).getName());
                    cc.setIdf(id1);
                    cc.setIdt(id2);
                    cc.setValue(false);
                    cc.setId(auto);
                    System.out.println(cc.getFromname() +"  Check error!" + auto);
                    auto++;
                    arrCon.add(cc);
                    end = System.currentTimeMillis() - timer;
                }

                if (counter == 300) {
                    System.out.println("Full reqs wait!! For CityCon" + counter + "  " + end);
                    counter = 0;
                    long kjk = 60000 - end;
                    try {
                        Thread.sleep(kjk);
                    }catch (Exception c) {

                    }
                    timer = System.currentTimeMillis();
                } else if (end >= 60000) {
                    System.out.println("Full reqs wait!! For CityCon" + counter + "  " + end);
                    counter = 0;
                    timer = System.currentTimeMillis();
                }

            }
        }

        return arrCon;
    }

    public static ArrayList<Connection> CleanCon(ArrayList<Connection> arrCon){
        ArrayList<Connection> newarr= new ArrayList<>();
        for(Connection cc : arrCon){
            if(cc.isValue()){
                newarr.add(cc);
            }

        }

        return newarr;
    }


    public static ArrayList<Data> getData(ArrayList<Connection> arrCon) throws IOException,SQLException {
        int realid = 0;
        ArrayList<Data> newarr= new ArrayList<>();
        for(Connection cc : arrCon){

            JSONObject json = readJsonFromUrl("http://transport.opendata.ch/v1/connections?from="+ cc.getIdf() +"&to="+ cc.getIdt()+"&direct=1");
            JSONArray station = json.getJSONArray("connections");
            for(int i=0;i<station.length();i++) {
                Data dt = new Data();
                JSONObject test = station.getJSONObject(i);
                JSONObject sec = test.getJSONObject("from");
                JSONObject fi = test.getJSONObject("to");
                String depa = sec.getString("departure");
                String arriv = fi.getString("arrival");
                dt.setDepart(depa);
                dt.setArrival(arriv);
                dt.setId(cc.getId());
                dt.setRealid(realid);
                realid++;
                Database.storeData(dt);
                System.out.println(dt.getId() + "  Done!!");
                newarr.add(dt);


            }
        }
        return newarr;
    }

    public static String getWord(String nexli){
        String[] strings;
        if(nexli.indexOf("1")>=0 || nexli.indexOf("2")>=0) {
            strings = nexli.split("\\s+");
            return strings[0];
        }else{
            return nexli;
        }
    }

    public static String removeSaints(String ch) {
        if (ch.indexOf("Saint") >= 0) {
            StringBuilder test = new StringBuilder(ch);
            int pot = ch.indexOf("Saint");
            StringBuilder ne = test.delete(pot + 1, pot + 4);
            return ne.toString();

        } else if(ch.indexOf("Riva San Vitale")>=0) {
            StringBuilder sb = new StringBuilder(ch);
            int pot = ch.indexOf("San");
            StringBuilder ne = sb.delete(pot + 1, pot + 2);
            return ne.toString();


        }else{
            return ch;
        }
    }

    public static String removeOthers(String xr){
        String[] strings;
        if(xr.indexOf("Illnau")>=0 || xr.indexOf("Rapperswil")>=0){
            strings = xr.split("-");
            return strings[0];
        }else{
            return xr;
        }
    }
}
