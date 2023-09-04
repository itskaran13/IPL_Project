import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class No_of_matchesWon {
    public static void main(String [] args) {
        String line = "/home/karan/IPL_Project/matches.csv";
        HashMap<String,Integer> No_ofmatches = new HashMap<>();

        try(BufferedReader br= new BufferedReader(new FileReader(line))){
            br.readLine();
            String st;
            while((st = br.readLine()) != null){
                String[] data  = st.split(",");
                String season= data[1];
                if(No_ofmatches.containsKey(season)){
                    No_ofmatches.put(season,No_ofmatches.get(season)+1);
                }
                else{
                    No_ofmatches.put(season,1);
                }
            }
            for(String num : No_ofmatches.keySet()){
                System.out.println("Year : "+num  + " Number of Matches Played :" + No_ofmatches.get(num));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }
}