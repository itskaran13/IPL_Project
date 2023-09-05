package io.mountblue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import io.mountblue.Match;
import io.mountblue.Delivery;


public class Main {
    public static final int ID = 0;
    public static final int SEASON = 1;
    public static final int CITY = 2;
    public static final int DATE = 3;
    public static final int TEAM1 = 4;
    public static final int TEAM2 = 5;
    public static final int TOSS_WINNER = 6;
    public static final int TOSS_DECISION = 7;
    public static final int RESULT = 8;
    public static final int DL_APPLIED = 9;
    public static final int WINNER = 10;
    public static final int WIN_BY_RUNS = 11;
    public static final int WIN_BY_WICKETS = 12;
    public static final int PLAYER_OF_MATCH = 13;
    public static final int VENUE = 14;
    public static final int UMPIRE1 = 15;
    public static final int UMPIRE2 = 16;
    public static final int UMPIRE3 = 17;

    public static final int MATCH_ID = 0;
    public static final int INNING = 1;
    public static final int BATTING_TEAM = 2;
    public static final int BOWLING_TEAM = 3;
    public static final int OVER = 4;
    public static final int BALL = 5;
    public static final int BATSMAN = 6;
    public static final int NON_STRIKER = 7;
    public static final int BOWLER = 8;
    public static final int IS_SUPER_OVER = 9;
    public static final int WIDE_RUNS = 10;
    public static final int BYE_RUNS = 11;
    public static final int LEGINBYE_RUNS = 12;
    public static final int NOBALL_RUNS = 13;
    public static final int PENALTY_RUNS = 14;
    public static final int BATSMAN_RUNS = 15;
    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;
    public static final int PLAYER_DISMISSED = 18;
    public static final int DISMISSAL_KIND = 19;
    public static final int FIELDER = 20;

    public static void main(String[] args) {

        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveryData();

        HashMap<String, Integer> numberOfMatchesPlayed = numberMatch(matches);
        for (Map.Entry<String, Integer> entry : numberOfMatchesPlayed.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        HashMap<String, Integer> numberOfMatchesWon = numberMatchWon(matches);
        for (Map.Entry<String, Integer> entry : numberOfMatchesWon.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue());


        }
        HashMap<String, Integer> extraRun = extraRunsByTeams(matches, deliveries);
        for (Map.Entry<String, Integer> entry : extraRun.entrySet()) {
            //System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        HashMap<String,Double> totalEconomyOfBowler = economyOfBowlers(matches,deliveries);
        for (Map.Entry<String, Double> entry : totalEconomyOfBowler.entrySet()) {
            System.out.println(entry.getKey() + ": " + String.format("%.2f", entry.getValue()));
        }
    }

    public static HashMap<String, Integer> extraRunsByTeams(List<Match> matches, List<Delivery> deliveries) {
        ArrayList<String> matchid = new ArrayList<>();
        for (Match num : matches) {
            String data = num.getSeason();
            if (data.equals("2016")) {
                matchid.add(num.getId());
            }
        }
        ArrayList<String> team = new ArrayList<>();
        ArrayList<Integer> extra = new ArrayList<>();
        for (int i = 0; i < matchid.size(); i++) {
            for (Delivery num : deliveries) {

                if (matchid.get(i).equals(num.getMatchId())) {
                    team.add(num.getBowlingTeam());
                    extra.add(Integer.parseInt(num.getExtraRuns()));
                }
            }
        }

        HashMap<String, Integer> team_extra_run = new HashMap<>();
        for (int i = 0; i < team.size(); i++) {
            team_extra_run.put(team.get(i), team_extra_run.getOrDefault(team.get(i), 0) + extra.get(i));
        }
        return team_extra_run;
    }

    public static HashMap<String, Integer> numberMatch(List<Match> matches) {
        HashMap<String, Integer> No_matches = new HashMap<>();
        for (Match num : matches) {
            String str = num.getSeason();
            if (No_matches.containsKey(str)) {
                No_matches.put(str, No_matches.get(str) + 1);
            } else {
                No_matches.put(str, 1);
            }
        }
        return No_matches;
    }


    public static HashMap<String, Integer> numberMatchWon(List<Match> matches) {
        HashMap<String, Integer> NumberOfMatchesWon = new HashMap<>();

        for (Match str : matches) {
            String num = str.getWinner();
            if (NumberOfMatchesWon.containsKey(num)) {
                NumberOfMatchesWon.put(num, NumberOfMatchesWon.get(num) + 1);
            } else {
                NumberOfMatchesWon.put(num, 1);
            }
        }

        return NumberOfMatchesWon;
    }

    public static HashMap<String,Double> economyOfBowlers(List<Match> matches, List<Delivery> deliveries) {
        ArrayList<String> matchid = new ArrayList<>();
        for (Match num : matches) {
            String data = num.getSeason();
            if (data.equals("2015")) {
                matchid.add(num.getId());
            }
        }
        HashMap<String, Double> bowlerballs = new HashMap<>();
        HashMap<String, Integer> totalRun = new HashMap<>();
        for (int i = 0; i < matchid.size(); i++) {
            for (Delivery num : deliveries) {
                if (matchid.get(i).equals(num.getMatchId())) {
                    bowlerballs.put(num.getBowler(), bowlerballs.getOrDefault(num.getBowler(), 0.0) + 1.0);
                    totalRun.put(num.getBowler(), totalRun.getOrDefault(num.getBowler(), 0) + Integer.parseInt(num.getTotalRuns()));
                }
            }
        }
        HashMap<String , Double> totaleconomy = new HashMap<>();
        for(String num : bowlerballs.keySet()){
            totaleconomy.put(num,totaleconomy.getOrDefault(num,0.0)+ (totalRun.get(num))/(bowlerballs.get(num)/6));

        }
        return totaleconomy;
    }

public static List<Match> getMatchesData() {
    List<Match> matches = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("/home/karan/IPL_Project1/matches.csv"))){
        br.readLine();
        String st;
        while((st = br.readLine()) != null)  {
            String[] data = st.split(",");
            Match match = new Match();
            match.setId(data[ID].trim());
            match.setSeason(data[SEASON].trim());
            match.setCity(data[CITY].trim());
            match.setDate(data[DATE]);
            match.setTeam1(data[TEAM1].trim());
            match.setTeam2(data[TEAM2].trim());
            match.setTossWinner(data[TOSS_WINNER].trim());
            match.setTossDecision(data[TOSS_DECISION].trim());
            match.setResult(data[RESULT].trim());
            match.setDlApplied(data[DL_APPLIED].trim());
            match.setWinner(data[WINNER].trim());
            match.setWinByRuns(data[WIN_BY_RUNS].trim());
            match.setWinByWickets(data[WIN_BY_WICKETS].trim());
            match.setPlayerOfMatch(data[PLAYER_OF_MATCH].trim());
            match.setVenue(data[VENUE].trim());
            matches.add(match);
        }
}
    catch(IOException e){
        e.printStackTrace();
    }
    return matches;
}
    public static List<Delivery> getDeliveryData() {
        List<Delivery> deliveries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/karan/IPL_Project1/deliveries.csv"))){

            br.readLine();
            String st;
            while((st = br.readLine()) != null){
                String[] data = st.split(",");
                Delivery delivery = new Delivery();
                delivery.setMatchId(data[MATCH_ID].trim());
                delivery.setInning(data[INNING].trim());
                delivery.setBattingTeam(data[BATTING_TEAM ].trim());
                delivery.setBowlingTeam(data[BOWLING_TEAM]);
                delivery.setOver(data[OVER].trim());
                delivery.setBall(data[BALL].trim());
                delivery.setBatsman(data[BATSMAN].trim());
                delivery.setNonStriker(data[NON_STRIKER].trim());
                delivery.setBowler(data[BOWLER].trim());
                delivery.setIsSuperOver(data[IS_SUPER_OVER].trim());
                delivery.setWideRuns(data[WIDE_RUNS].trim());
                delivery.setByeRuns(data[BYE_RUNS].trim());
                delivery.setLegByeRuns(data[LEGINBYE_RUNS].trim());
                delivery.setNoBallRuns(data[NOBALL_RUNS].trim());
                delivery. setPenaltyRuns(data[PENALTY_RUNS].trim());
                delivery.setBatsmanRuns(data[BATSMAN_RUNS].trim());
                delivery.setExtraRuns(data[EXTRA_RUNS].trim());
                delivery.setTotalRuns(data[TOTAL_RUNS].trim());


                deliveries.add(delivery);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return deliveries;
    }
}