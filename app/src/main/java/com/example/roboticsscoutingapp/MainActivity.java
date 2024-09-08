package com.example.roboticsscoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText teamNumber;
    Button getDetails;
    TextView teamName;
    TextView organization;
    TextView address;
    
    TextView compOneName;
    TextView compOneWins;
    TextView compOneLosses;
    TextView compOneWP;
    TextView compOneAP;
    TextView compOneSP;
    TextView compOneAveragePoints;
    TextView compTwoName;
    TextView compTwoWins;
    TextView compTwoLosses;
    TextView compTwoWP;
    TextView compTwoAP;
    TextView compTwoSP;
    TextView compTwoAveragePoints;
    
    TextView skillsCompOne;
    TextView skillsCompOneDriver;
    TextView skillsCompOneDriverScore;
    TextView skillsCompOneDriverRank;
    TextView skillsCompOneDriverAttempts;
    TextView skillsCompOneProg;
    TextView skillsCompOneProgScore;
    TextView skillsCompOneProgAttempts;
    
    TextView skillsCompTwo;
    TextView skillsCompTwoDriver;
    TextView skillsCompTwoDriverScore;
    TextView skillsCompTwoDriverRank;
    TextView skillsCompTwoDriverAttempts;
    TextView skillsCompTwoProg;
    TextView skillsCompTwoProgScore;
    TextView skillsCompTwoProgAttempts;

    TextView awardsComp1;
    TextView awardsComp1Title;
    TextView awardsComp2;
    TextView awardsComp2Title;

    TextView driver1;
    TextView prog1;

    TextView driver2;
    TextView prog2;
    
    
    
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driver1 = findViewById(R.id.id_skillsType);
        prog1 = findViewById(R.id.id_skillsType2);

        driver2 = findViewById(R.id.id_skillsType21);
        prog2 = findViewById(R.id.id_skillsType22);

        teamNumber = findViewById(R.id.id_teamNumber);
        getDetails = findViewById(R.id.id_getDetails);
        teamName = findViewById(R.id.id_robotTeamName);
        organization = findViewById(R.id.id_organization);
        address = findViewById(R.id.id_teamAddress);
        compOneName = findViewById(R.id.id_competition1);
        compOneWins = findViewById(R.id.id_comp1Wins);
        compOneLosses = findViewById(R.id.id_comp1Losses);
        compOneWP = findViewById(R.id.id_comp1WP);
        compOneAP = findViewById(R.id.id_comp1AP);
        compOneSP = findViewById(R.id.id_comp1SP);
        compOneAveragePoints = findViewById(R.id.id_comp1AverageScore);
        compTwoName = findViewById(R.id.id_comp2);
        compTwoWins = findViewById(R.id.id_comp2Wins);
        compTwoLosses = findViewById(R.id.id_comp2Losses);
        compTwoWP = findViewById(R.id.id_comp2WP);
        compTwoAP = findViewById(R.id.id_comp2AP);
        compTwoSP = findViewById(R.id.id_comp2SP);
        compTwoAveragePoints = findViewById(R.id.id_comp2AverageScore);

        skillsCompOne = findViewById(R.id.id_skillsComp1);
        skillsCompOneDriver = findViewById(R.id.id_skillsType);
        skillsCompOneDriverScore = findViewById(R.id.id_Driver1Score);
        skillsCompOneDriverRank = findViewById(R.id.id_Driver1Rank);
        skillsCompOneDriverAttempts = findViewById(R.id.id_Driver1Attempts);
        skillsCompOneProg = findViewById(R.id.id_skillsType2);
        skillsCompOneProgScore = findViewById(R.id.id_prog1Score);
        skillsCompOneProgAttempts = findViewById(R.id.id_prog1Attempts);

        skillsCompTwo = findViewById(R.id.id_skillsComp2);
        skillsCompTwoDriver = findViewById(R.id.id_skillsType21);
        skillsCompTwoDriverScore = findViewById(R.id.id_Driver2Score);
        skillsCompTwoDriverRank = findViewById(R.id.id_Driver2Rank);
        skillsCompTwoDriverAttempts = findViewById(R.id.id_Driver2Attempts);
        skillsCompTwoProg = findViewById(R.id.id_skillsType22);
        skillsCompTwoProgScore = findViewById(R.id.id_prog2Score);
        skillsCompTwoProgAttempts = findViewById(R.id.id_prog2Attempts);

        awardsComp1 = findViewById(R.id.id_awardComp1);
        awardsComp1Title = findViewById(R.id.id_awardTitle1);
        awardsComp2 = findViewById(R.id.id_awardComp2);
        awardsComp2Title = findViewById(R.id.id_awardTitle2);







        teamNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNumber.setText("");
            }
        });

        getDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTeamNumber = teamNumber.getText().toString();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(teamNumber.getWindowToken(), 0);

                if (inputTeamNumber != null) {
                    Log.d("TAG", "Getting details for: [" + inputTeamNumber + "]");
                    DownloadDataAsync downloadData = new DownloadDataAsync();
                    downloadData.execute(inputTeamNumber);
                }
            }
        });
    }

    private class DownloadDataAsync extends AsyncTask<String, Void, String> {

        private  String downloadPayload(String inputUrl) throws IOException {
            String read = "";
            String payload = "";
            Log.d("Download", "Downloading from URL " + inputUrl);
            URL url = new URL(inputUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYjliZDgwNzNiZDUyYzY1NWM0OTJjYjc0NGFmZDM1YjY3OTcyOGQ0OTAwMDY5M2FmOTU0Y2I4ZGYzMDViMDNkMTgyNjJmMmUxODEyYjIxMGQiLCJpYXQiOjE2NzY4MzI0MjguNjk3NDAxLCJuYmYiOjE2NzY4MzI0MjguNjk3NDAzOSwiZXhwIjoyNjIzNjAzNjI4LjY4OTA3NjksInN1YiI6IjExMjg2NSIsInNjb3BlcyI6W119.UxpSgeB0eVmiqEax9RxWD-UTNFO3xl9FukQILkx5UU5mXrDlqwyWMOP2huCjcnZHpUBgdJl3-kD2Td0dkozO1gNULlgQmZIUglWkycgYXAUjzKzl9vSa1xVuPG_CPpx6Al1PnE55joMs3aOd8V2YV8ddQq_qwpf3oY4gNdbjPtnspAUFdRaHzU3Qzx33Chn7hQBecwBoyXD_k2Wjms2UJKnc6hvNON-Nu2YaGwzP3Kc8swDmcA--j_rLGL5QVjETQ9r-cSabKnSuc0MlyclfQYi4JbYQRoi0akbFvwvg_EhXTNyPC4fUPY896QnRlGfJ5HP6Rk8O2z72Bs86sAKhp5Agwe4E-QeV1-FL6t8i7vUVyxMgM7YZtXRFljZ7leWeXKilt39cRKDPbsRGaBiDY-WSmSrqcorCZi4DXDxm7kq-Os1OLc4d0MEU9R0FsWCRsdT47K-IzCPeQpVyVHTEmQHMAMRlF30WMUUHyj5NUaNb0Tw331YyxY9n9gSuiMLCIuRbugAAKw5LrIkVLk5UX_ghKG2UGCHjFoJ_t5T5dz8HS4KhtUFUgw0WkaUG2w3GFhffF-30ROAWjb_JQh01b3kHxRKmmSxmD_bbJX38cuOs7JUeQUCt5bTgmnHo5UVdSQCLTt0rPqDLhiKhMnW-gSeL46ng0egyhGH2mysZ0oU");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((read = bufferedReader.readLine()) != null) {
                payload += read;
            }
            inputStream.close();
            return payload;
        }

        protected String doInBackground(String... params) {
            String inputTeamNumber = params[0];
            String urlTeam = "https://www.robotevents.com/api/v2/events/50654/teams";
            int teamId, outTeamId;
            String teamNumber, teamName, robotName, organization;
            String city, region, postcode, address;

            String outTeamNumber, outTeamName, outRobotName, outOrganization, outAddress;

            Map<String, String[]> teamMap = new HashMap<String, String[]>();
            String[] teamDetailsArray;
            try {
                do {
                    String payloadTeam = downloadPayload(urlTeam);
                    JSONObject jsonData = new JSONObject(payloadTeam);
                    /* Get teamDetailsArray from the json payload and put to teamMap here */
                    JSONArray dataList = jsonData.getJSONArray("data");
                    for (int i = 0; i< dataList.length(); i++) {
                        JSONObject teamDetailsObject = dataList.getJSONObject(i);
                        teamId = teamDetailsObject.getInt("id");
                        teamNumber = teamDetailsObject.getString("number");
                        //teamList.add(teamNumber);
                        teamName = teamDetailsObject.getString("team_name");
                        robotName = teamDetailsObject.getString("robot_name");
                        organization = teamDetailsObject.getString("organization");
                        JSONObject location = teamDetailsObject.getJSONObject("location");
                        city = location.getString("city");
                        region = location.getString("region");
                        postcode =  location.getString("postcode");
                        address = city + ", " + region + " " + postcode;
                        teamDetailsArray = new String[]{String.valueOf(teamId), teamName, robotName, organization, address};
                        teamMap.put(teamNumber, teamDetailsArray);
                    }

                    JSONObject meta = jsonData.getJSONObject("meta");
                    urlTeam = meta.getString("next_page_url");
                } while (urlTeam != "null");
            } catch (FileNotFoundException e) {
                return "404";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String teamInfo;

            if(!teamMap.containsKey(inputTeamNumber)){
                Log.d("Info", "" + "not found");
                return "team not found";

            }

            outTeamId = Integer.parseInt(teamMap.get(inputTeamNumber)[0]);
            outTeamName = teamMap.get(inputTeamNumber)[1];
            //outRobotName = teamMap.get(inputTeamNumber)[2];
            outOrganization = teamMap.get(inputTeamNumber)[3];
            outAddress = teamMap.get(inputTeamNumber)[4];
            //outTeamNumber, outTeamName, outRobotName, outOrganization, outAddress

            teamInfo = inputTeamNumber + "|" + outTeamId + "|" + outTeamName + "|" + outOrganization + "|" + outAddress;
            Log.d("Team Info", "" + teamInfo);

            /* Iterator tmIterator = teamMap.entrySet().iterator();

            while (tmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) tmIterator.next();
                Log.d ( "Map2", mapElement.getKey() + ": " + mapElement.getValue());
            } */


            // Ranking Info
            String rankingInfo = "";
            String urlSpecRanking = "https://www.robotevents.com/api/v2/teams/"+outTeamId+"/rankings?page=1";
            try {
                String payloadRanking = downloadPayload(urlSpecRanking);
                Log.d("Ranking", "" + payloadRanking);
                // Read the first 2 objects and Parse json object.
                JSONObject jsonDataRanking = new JSONObject(payloadRanking);
                JSONArray dataListRanking = jsonDataRanking.getJSONArray("data");
                int maxDataList = dataListRanking.length();
                if (maxDataList > 2) {
                    maxDataList = 2;
                }
                for (int j = 0; j < maxDataList; j++) {
                    JSONObject rankingObject = dataListRanking.getJSONObject(j);
                    String eventName = rankingObject.getJSONObject("event").getString("name");
                    int wins = rankingObject.getInt("wins");
                    int losses = rankingObject.getInt("losses");
                    int ties = rankingObject.getInt("ties");
                    int wp = rankingObject.getInt("wp");
                    int ap = rankingObject.getInt("ap");
                    int sp = rankingObject.getInt("sp");
                    int highScore = rankingObject.getInt("high_score");
                    long averagePoints = rankingObject.getLong("average_points");
                    int totalPoints = rankingObject.getInt("total_points");
                    rankingInfo += eventName + "#" + wins + "#" + losses + "#" + ties + "#" + wp + "#" + ap + "#" + sp + "#" + highScore + "#" + averagePoints + "#" + totalPoints + '|'  ;
                }

                Log.d("Ranking Info", "" + rankingInfo);

            } catch (FileNotFoundException e) {
                return "404";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Awards Info
            String awardsInfo = "";
            int numRecsAwards = 2;
            String urlSpecAwards = "https://www.robotevents.com/api/v2/teams/"+outTeamId+"/awards?page=1";
            try {
                String payloadAwards = downloadPayload(urlSpecAwards);
                Log.d("Awards", "" + payloadAwards);
                JSONObject jsonData = new JSONObject(payloadAwards);
                JSONObject meta = jsonData.getJSONObject("meta");
                int totalRecs = meta.getInt("total");
                int numRecsPerPage = meta.getInt("per_page");
                int pageToStart = (int) Math.ceil((double) (totalRecs - numRecsAwards + 1)/numRecsPerPage);
                int recToStart = ((totalRecs - numRecsAwards) - (pageToStart - 1) * numRecsPerPage ) + 1;
                int recToEnd = recToStart + numRecsAwards - 1; if (recToEnd > numRecsPerPage) { recToEnd = numRecsPerPage; }
                Log.d("Awards", "totalRecs, numRecsPerPage, pageToStart, recToStart, recToEnd : " + totalRecs + "," + numRecsPerPage + "," + pageToStart + "," + recToStart + "," + recToEnd);
                urlSpecAwards = "https://www.robotevents.com/api/v2/teams/"+outTeamId+"/awards?page="+pageToStart;
                do {
                    payloadAwards = downloadPayload(urlSpecAwards);
                    jsonData = new JSONObject(payloadAwards);
                    /* Get teamDetailsArray from the json payload and put to teamMap here */
                    JSONArray dataListAwards = jsonData.getJSONArray("data");
                    Log.d("Awards", "recToStart, recToEnd " + recToStart + "," + recToEnd);
                    for (int i=recToStart-1; i < recToEnd; i++ ) {
                        JSONObject awardObject = dataListAwards.getJSONObject(i);
                        String eventName = awardObject.getJSONObject("event").getString("name");
                        String eventTitle = awardObject.getString("title");
                        JSONArray winnersArray = awardObject.getJSONArray("teamWinners");
                        String teamPlace = "";
                        for (int j = 0; j < winnersArray.length(); j++) {
                            JSONObject winnerObject = winnersArray.getJSONObject(j);
                            String awardTeamName = winnerObject.getJSONObject("team").getString("name");
                            if (awardTeamName.equals(inputTeamNumber)) {
                                teamPlace = String.valueOf(j+1) + " Place";
                                break;
                            }
                        }

                        numRecsAwards --;
                        awardsInfo +=  eventName + "#" + eventTitle + "#" + teamPlace + "|";
                    }
                    meta = jsonData.getJSONObject("meta");
                    urlSpecAwards = meta.getString("next_page_url");
                    recToStart = 1;
                    recToEnd = numRecsAwards;
                } while (urlSpecAwards != "null");

            } catch (FileNotFoundException e) {
                return "404";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Awards Info", "awardsInfo: " + awardsInfo);


            // Skills Info
            String skillsInfo = "";
            int numRecsSkills = 4;
            String urlSpecSkills = "https://www.robotevents.com/api/v2/teams/"+outTeamId+"/skills?page=1";
            try {
                String payloadSkills = downloadPayload(urlSpecSkills);
                Log.d("Skills", "" + payloadSkills);
                JSONObject jsonData = new JSONObject(payloadSkills);
                JSONObject meta = jsonData.getJSONObject("meta");
                int totalRecs = meta.getInt("total");
                int numRecsPerPage = meta.getInt("per_page");
                int pageToStart = (int) Math.ceil((double) (totalRecs - numRecsSkills + 1)/numRecsPerPage);
                int recToStart = ((totalRecs - numRecsSkills) - (pageToStart - 1) * numRecsPerPage ) + 1;
                int recToEnd = recToStart + numRecsSkills - 1; if (recToEnd > numRecsPerPage) { recToEnd = numRecsPerPage; }
                Log.d("Skills", "totalRecs, numRecsPerPage, pageToStart, recToStart, recToEnd : " + totalRecs + "," + numRecsPerPage + "," + pageToStart + "," + recToStart + "," + recToEnd);
                urlSpecSkills = "https://www.robotevents.com/api/v2/teams/"+outTeamId+"/skills?page="+pageToStart;
                do {
                    payloadSkills = downloadPayload(urlSpecSkills);
                    jsonData = new JSONObject(payloadSkills);
                    /* Get teamDetailsArray from the json payload and put to teamMap here */
                    JSONArray dataListSkills = jsonData.getJSONArray("data");
                    Log.d("Skills", "recToStart, recToEnd " + recToStart + "," + recToEnd);
                    for (int i=recToStart-1; i < recToEnd; i++ ) {
                        JSONObject skillsObject = dataListSkills.getJSONObject(i);
                        String skillsType = skillsObject.getString("type");
                        String eventName = skillsObject.getJSONObject("event").getString("name");
                        String eventCode = skillsObject.getJSONObject("event").getString("code");
                        String seasonName = skillsObject.getJSONObject("season").getString("name");
                        int rank = skillsObject.getInt("rank");
                        int score = skillsObject.getInt("score");
                        int attempts = skillsObject.getInt("attempts");
                        numRecsSkills --;
                        skillsInfo += skillsType + "#" + eventName + "#" + eventCode + "#" + seasonName + "#" + rank + "#" + score + "#" + attempts + "|";
                    }
                    meta = jsonData.getJSONObject("meta");
                    urlSpecSkills = meta.getString("next_page_url");
                    recToStart = 1;
                    recToEnd = numRecsSkills;
                } while (urlSpecSkills != "null");

            } catch (FileNotFoundException e) {
                return "404";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Skills Info", "SkillsInfo: " + skillsInfo);

            Log.d("Returning ", "allInfo: " + teamInfo + "$" + rankingInfo + "$" + awardsInfo + "$" + skillsInfo);
            return teamInfo + "$" + rankingInfo + "$" + awardsInfo + "$" + skillsInfo;
        }

        @Override
        protected void onPostExecute(String allInfo) {

            if(allInfo.equals("team not found")){
                Toast.makeText(MainActivity.this, "Team Not At This Competition", Toast.LENGTH_LONG).show();
            }

            String [] allInfoArray = allInfo.split("\\$");
            String teamInfo = allInfoArray[0];
            String [] teamInfoArray = teamInfo.split("\\|");
            Log.d("Array", "" + teamInfoArray);
            teamName.setText("Team Name: " + teamInfoArray[2]);
            //robotName.setText("Robot Name: " + teamInfoArray[3]);
            organization.setText("School: " + teamInfoArray[3]);
            address.setText("Address: " + teamInfoArray[4]);

            String rankingInfo = allInfoArray[1];
            String [] compRankArray = rankingInfo.split("\\|");
            String [] compRankDetails1 = compRankArray[0].split("\\#");
            Log.d("Array", "" + compRankDetails1);
            compOneName.setText("Name: " + compRankDetails1[0]);
            compOneWins.setText("W: " + compRankDetails1[1]);
            compOneLosses.setText("L: " + compRankDetails1[2]);
            compOneWP.setText("WP: " + compRankDetails1[4]);
            compOneAP.setText("AP: " + compRankDetails1[5]);
            compOneSP.setText("SP: " + compRankDetails1[6]);
            compOneAveragePoints.setText("Average Score: " + compRankDetails1[8]);


            String [] compRankDetails2 = compRankArray[1].split("\\#");
            compTwoName.setText("Name: " + compRankDetails2[0]);
            compTwoWins.setText("W: " + compRankDetails2[1]);
            compTwoLosses.setText("L: " + compRankDetails2[2]);
            compTwoWP.setText("WP: " + compRankDetails2[4]);
            compTwoAP.setText("AP: " + compRankDetails2[5]);
            compTwoSP.setText("SP: " + compRankDetails2[6]);
            compTwoAveragePoints.setText("Average Score: " + compRankDetails2[8]);

            String skillsInfo = allInfoArray[3];
            String [] skillsRankArray = skillsInfo.split("\\|");
            String [] skills2Driver = skillsRankArray[0].split("\\#");
            //skillsCompOneDriver.setText(skills1Driver[0]);
            skillsCompTwo.setText("Name: "+ skills2Driver[1]);
            skillsCompTwoDriverScore.setText("Score: " + skills2Driver[5]);
            skillsCompTwoDriverRank.setText("Rank: " + skills2Driver[4]);
            skillsCompTwoDriverAttempts.setText("Attempts: " + skills2Driver[6]);

            driver1.setText("Driver");
            prog1.setText("Prog");

            driver2.setText("Driver");
            prog2.setText("Prog");

            String [] skills2Prog = skillsRankArray[1].split("\\#");
            skillsCompTwoProgScore.setText("Score: " + skills2Prog[5]);
            skillsCompTwoProgAttempts.setText("Attempts: " + skills2Prog[6]);
            String [] skills1Driver = skillsRankArray[2].split("\\#");
            //skillsCompOneDriver.setText(skills1Driver[0]);
            skillsCompOne.setText("Name: "+ skills1Driver[1]);
            skillsCompOneDriverScore.setText("Score: " + skills1Driver[5]);
            skillsCompOneDriverRank.setText("Rank: " + skills1Driver[4]);
            skillsCompOneDriverAttempts.setText("Attempts: " + skills1Driver[6]);

            String [] skills1Prog = skillsRankArray[3].split("\\#");
            skillsCompOneProgScore.setText("Score: " + skills1Prog[5]);
            skillsCompOneProgAttempts.setText("Attempts: " + skills1Prog[6]);

            String awardsInfo = allInfoArray[2];
            String [] awardDetails = awardsInfo.split("\\|");
            String [] awardsComp1Details = awardDetails[1].split("\\#");

            awardsComp1.setText("Name: " + awardsComp1Details[0]);
            awardsComp1Title.setText("Award: " + awardsComp1Details[1]);

            String [] awardsComp2Details = awardDetails[0].split("\\#");
            awardsComp2.setText("Name: " + awardsComp2Details[0]);
            awardsComp2Title.setText("Award: " + awardsComp2Details[1]);

        }
    }

}
