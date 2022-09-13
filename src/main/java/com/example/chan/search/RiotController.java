package com.example.chan.search;

import com.example.chan.champion.Champion;
import com.example.chan.champion.ChampionService;
import com.example.chan.champion.Championmastery;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@RequiredArgsConstructor
@Controller
public class RiotController {
    @Value("${RIOT_API_KEY}")
    private String API_KEY;
    private final ChampionService championService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value="/search", method= RequestMethod.POST)
    public String searchSummoner1(Model model, @RequestParam("id1")String names) throws UnsupportedEncodingException {
        String SummonerName = names.replaceAll(" ", "%20");
        return "redirect:/search/" + URLEncoder.encode(SummonerName, "UTF-8");
    }

    @RequestMapping(value = "/search/{id}")
    public String searchSummoner2(Model model, @PathVariable("id") String names){
        String SummonerName = names.replaceAll(" ", "%20");
        Summoner temp= getsummonerjson(SummonerName);
        //System.out.println(temp.getId());
        List<Championmastery> chlist = getchampmas1(temp.getId());
        //System.out.println(chlist);

        model.addAttribute("ChampionmasteryList", chlist);
        model.addAttribute("summoner", temp);
        model.addAttribute("imgURL", "https://opgg-static.akamaized.net/images/profile_icons/profileIcon"+temp.getProfileIconId()+".jpg?image=q_auto&image=q_auto,f_webp,w_auto&v=1661751970709");

        return "SearchResult";
    }

    @GetMapping("/search")
    public String searchSummoner3(Model model, @RequestParam String id2) {
        String SummonerName = id2.replaceAll(" ", "%20");
        Summoner temp= getsummonerjson(SummonerName);
        if(temp != null){
            List<Championmastery> chlist = getchampmas1(temp.getId());
            List<String> matches = getmatchs(temp.getPuuid());
            model.addAttribute("matches", matches);
            model.addAttribute("ChampionmasteryList", chlist);
            model.addAttribute("summoner", temp);
            model.addAttribute("imgURL", "https://opgg-static.akamaized.net/images/profile_icons/profileIcon"+temp.getProfileIconId()+".jpg?image=q_auto&image=q_auto,f_webp,w_auto&v=1661751970709");
        }
        else{
            return "index";
        }

        //makechampiondb();

        return "SearchResult";
    }

    public Summoner getsummonerjson(String SummonerName){
        BufferedReader br = null;
        Summoner temp= null;
        try {
            String urlstr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + SummonerName + "?api_key=" + API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }

            JsonParser jsonParser = new JsonParser();
            JsonObject k = (JsonObject) jsonParser.parse(result);
            int profileIconId = k.get("profileIconId").getAsInt();
            String name = k.get("name").getAsString();
            String puuid = k.get("puuid").getAsString();
            long summonerLevel = k.get("summonerLevel").getAsLong();
            long revisionDate = k.get("revisionDate").getAsLong();
            String id = k.get("id").getAsString();
            String accountId = k.get("accountId").getAsString();
            temp = new Summoner(profileIconId, name, puuid, summonerLevel, revisionDate, id, accountId);
            //System.out.println(temp);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return temp;
    }

    public List<Championmastery> getchampmas1(String ids){
        BufferedReader br = null;
        List<Championmastery> chlist = new ArrayList<Championmastery>();
        try {
            String urlstr = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + ids + "?api_key=" + API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonArray k = (JsonArray) jsonParser.parse(result);
            for(int i = 0 ; i < 5 ; i++){
                Championmastery tp = null;
                JsonObject object = (JsonObject) k.get(i);
                int championId = object.get("championId").getAsInt();
                int championLevel = object.get("championLevel").getAsInt();
                int championPoints = object.get("championPoints").getAsInt();
                Champion cha = getchampname2(championId);
                tp = new Championmastery(cha.getKname(), cha.getEname(), championId, championLevel, championPoints);
                //System.out.println(tp);
                chlist.add(tp);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return chlist;
    }

    public String getchampname(int id){
        BufferedReader br = null;
        String chamname = null;
        try {
            String urlstr = "https://ddragon.leagueoflegends.com/cdn/10.6.1/data/ko_KR/champion.json";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }
            //System.out.println(result);
            JSONObject jObject = new JSONObject(result);
            JSONObject chamobject = jObject.getJSONObject("data");
            Iterator i = chamobject.keys();
            while(i.hasNext())
            {
                String b = i.next().toString();
                JSONObject cham = chamobject.getJSONObject(b);
                if(cham.getInt("key") == id){
                    chamname = b;
                    break;
                }

            }
//            for(JsonElement job:k){
//                JsonObject k2 = (JsonObject) job;
//            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return chamname;
    }
    public Champion getchampname2(int id){
        Optional<Champion> champion = this.championService.findChamname(id);
        if(champion.isPresent()) {
            Champion cham = champion.get();
            return cham;
        }
        return null;
    }
    public List<String> getmatchs(String puuid){
        BufferedReader br = null;
        List<String> matchs = new ArrayList<String>();
        try {
            String urlstr = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?queue=450&start=0&count=20&api_key=" + API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonArray k = (JsonArray) jsonParser.parse(result);
            for(JsonElement s:k){
                matchs.add(s.getAsString());
            }
            System.out.println(matchs);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return matchs;
    }

    public void makechampiondb(){
        BufferedReader br = null;
        String chamname = null;
        try {
            String urlstr = "https://ddragon.leagueoflegends.com/cdn/10.6.1/data/ko_KR/champion.json";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8")); // 여기에 문자열을 받아와라.
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }
            //System.out.println(result);
            JSONObject jObject = new JSONObject(result);
            JSONObject chamobject = jObject.getJSONObject("data");
            Iterator i = chamobject.keys();
            while(i.hasNext())
            {
                String b = i.next().toString();
                JSONObject cham = chamobject.getJSONObject(b);
                Champion champ = new Champion();
                champ.setId(cham.getInt("key"));
                champ.setEname(cham.getString("id"));
                champ.setKname(cham.getString("name"));
                this.championService.create(champ);
            }
//            for(JsonElement job:k){
//                JsonObject k2 = (JsonObject) job;
//            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return;
    }


}
