import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class No_of_matches_played {
        public static void main(String [] args) {
            String line = "/home/karan/IPL_Project/matches.csv";
            HashMap<String,Integer> No_ofMatchesplayed = new HashMap<>();

            try(BufferedReader br= new BufferedReader(new FileReader(line))){
                br.readLine();
                String st;
                while((st = br.readLine()) != null){
                    String[] data  = st.split(",");
                    String season= data[10];
                    if(No_ofMatchesplayed.containsKey(season)){
                        No_ofMatchesplayed.put(season,No_ofMatchesplayed.get(season)+1);
                    }
                    else{
                        No_ofMatchesplayed.put(season,1);
                    }
                }
                for(String num : No_ofMatchesplayed.keySet()){
                    System.out.println("Team: "+ num  + " Number of Matches Won:" + No_ofMatchesplayed.get(num));
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }


        }

}
