import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Total_economy{
    public static void main(String[] args) {
        String line = "/home/karan/IPL_Project/matches.csv";
        String line1 = "/home/karan/IPL_Project/deliveries.csv";
        //HashMap<String, Integer> mp = new HashMap<>();
        ArrayList<String> matchid = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(line))) {
            br.readLine();
            String st;
            while ((st = br.readLine()) != null) {
                String[] match_id = st.split(",");
                String data = match_id[1];
                if(data.equals("2015")) {
                    matchid.add(match_id[0]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Double> bowlerOver= new HashMap<>();
        HashMap<String,Integer> totalRun = new HashMap<>();
         try (BufferedReader br = new BufferedReader(new FileReader(line1))) {
             br.readLine();
             String st1;
             while ((st1 = br.readLine()) != null) {
                 String[] match_id1 = st1.split(",");
                 for(int i = 0; i < matchid.size(); i++) {
                     if (matchid.get(i).equals(match_id1[0])) {
                         bowlerOver.put(match_id1[8], bowlerOver.getOrDefault(match_id1[8], 0.0) + 1.0);
                         totalRun.put(match_id1[8], totalRun.getOrDefault(match_id1[8], 0) + Integer.parseInt(match_id1[17]));
                     }
                 }

             }
             for (String st : totalRun.keySet()) {
                  System.out.println("Bowler  : " + st + " Has a economy of : " + String.format("%.2f" ,totalRun.get(st)/(bowlerOver.get(st)/6)));
                }
         }
         catch (IOException e) {
            e.printStackTrace();
        }

    }

    }
