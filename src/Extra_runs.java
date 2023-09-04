import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.*;

public class Extra_runs {
    public static void main(String[] args) {
        String line = "/home/karan/IPL_Project/matches.csv";
        String line1 = "/home/karan/IPL_Project/deliveries.csv";
        //HashMap<String, Integer> mp = new HashMap<>();
        ArrayList<String> matchid= new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(line))) {
            br.readLine();
            String st;
            while ((st = br.readLine()) != null) {
                String[] match_id = st.split(",");
                String data = match_id[1];
                if (data.equals("2016")) {
                    matchid.add(match_id[0]);
                }
            }
            //System.out.println(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> team= new ArrayList<>();
        ArrayList<Integer> extra = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(line1))) {
            br.readLine();
            String st1;
            while ((st1= br.readLine()) != null) {
                String[] data = st1.split(",");
                for (int i = 0; i < matchid.size(); i++) {
                    if (matchid.get(i).equals(data[0])){
                        team.add(data[3]);
                    extra.add(Integer.parseInt(data[16]));
                        }
                    }

                }
            HashMap<String,Integer> team_extra_run = new HashMap<>();
            for(int i = 0 ; i<team.size();i++){
                team_extra_run.put(team.get(i),team_extra_run.getOrDefault(team.get(i),0)+extra.get(i));
            }
            for(String num : team_extra_run.keySet()){
                System.out.println("Team : " + num  + " Gives extra run :" + team_extra_run.get(num));
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
