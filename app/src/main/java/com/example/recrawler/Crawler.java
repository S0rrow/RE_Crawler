package com.example.recrawler;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class Crawler extends AsyncTask<Void, Void, Void> {
    final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";

    private String id;
    private String pw;
    private boolean quest;
    private ArrayList<String> timeTableString = new ArrayList<>();
    IOException error;
    private boolean threadQuest = true;
    Crawler(String input_id, String input_pw){
        super();
        quest = true;
        id = input_id;
        pw = input_pw;
    }
    ArrayList<String> getTimeTable(){ return timeTableString; }
    boolean check(){ return quest; }
    boolean done(){return threadQuest;}

    public void debug(String debugString){
        System.out.println("\nDEBUG:"+debugString);
    }

    void crawl() throws IOException {
        String url = "http://hisnet.handong.edu";
        //String id = "jae9hell";
        //String pw = "legion98";
        Map<String, String> data = new HashMap<String, String>();
        Map<String, String> cookies;
        Map<String, String> headers;
        data.put("id", id);
        data.put("password", pw);
        data.put("language", "Korean");
        try {
            threadQuest = false;
            Connection.Response initialResponse = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent(USER_AGENT)
                    //.data(data)
                    .execute();
            cookies = initialResponse.cookies();

            cookies.put("_ga", "GA1.2.1288199000.1544150653");
            cookies.put("cookie_id", id);
            cookies.put("notice31", "check");
            cookies.put("notice29", "check");
            cookies.put("NSC_ijtofu_ttm", cookies.get("NSC_xfcqpsubm_WJQ"));
            headers = new HashMap<String, String>();
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            headers.put("Accept-Encoding", "gzip, deflate, br");
            headers.put("Accept-Language", "ko-KR");
            headers.put("Connection", "keep-alive");
            headers.put("Upgrade-Insecure-Requests", "1");
            headers.put("Host", "hisnet.handong.edu");
            Document initialDocument = initialResponse.parse();

            String loginAction = Jsoup.connect(initialDocument.selectFirst("frame[name='MainFrame']").absUrl("src"))
                    .get()
                    .select("form[name='login']")
                    .first()
                    .absUrl("action");
            Connection.Response response = Jsoup.connect(loginAction)
                    .cookies(cookies)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .header("User-Agent", USER_AGENT)
                    .header("Upgrade-Insecure-Requests", "1")
                    .data("id", id)
                    .data("password", pw)
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.POST)
                    .execute();
            response = Jsoup.connect("https://hisnet.handong.edu/for_student/main.php")
                    .headers(headers)
                    .cookies(cookies)
                    .referrer("https://hisnet.handong.edu/main.php")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .execute();
            Document main = response.parse();
            Elements el = main.select("a[href='/for_student/course/01.php']");

            Connection.Response timetable = Jsoup.connect(el.first().absUrl("href"))
                    .headers(headers)
                    .cookies(cookies)
                    .referrer("https://hisnet.handong.edu/main.php")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .execute();
            Document timetableDoc = timetable.parse();
            Elements timetableCells = timetableDoc.select("table[id='att_list']").select("td");
            String cache = "";
            String temp = "";
            for(int i = 0; i < timetableCells.size(); i++) {
                Element cell = timetableCells.get(i);
                String[] info = cell.text().split("\n");
                temp = "|"+ TextUtils.join(",",info)+"|";
                cache = cache+temp;
                //System.out.print(temp);
                if(i%7==6) {
                    timeTableString.add(cache);
                    //System.out.println();
                    cache = "";
                    temp = "";
                }
            }

            for(String test:timeTableString){
                System.out.println("cralwer test");
                System.out.println(test);
            }
            threadQuest = true;
        } catch (IOException e) {
            quest = false;
            e.printStackTrace();
            error = e;
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            crawl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
