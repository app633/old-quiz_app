package com.example.test_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private String questionNumStr;

    private CheckBox RandomCheckBox;
    private CheckBox FavoriteCheckBox;
    private CheckBox HumanCheckBox;
    private CheckBox AnimeCheckBox;
    private CheckBox SingerCheckBox;
    private CheckBox EntertainerCheckBox;
    private CheckBox IdolCheckBox;
    private CheckBox AthleteCheckBox;
    private CheckBox RemoveNicheCheckBox;
    private CheckBox OnlyNicheCheckBox;
    private CheckBox BaseballCheckBox;
    private CheckBox FootballCheckBox;
    private TextView hidden;
    private String tag;

    private String all_tag;
    private String entertainer_tag;
    private String idol_tag;
    private String human_tag;
    private String anime_tag;
    private String singer_tag;
    private String athlete_tag;
    private String removeNiche_tag;
    private String niche_tag;
    private String baseball_tag;
    private String football_tag;

    private ArrayList<String> tags_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RandomCheckBox = findViewById(R.id.isRandomCheckBox);
        FavoriteCheckBox = findViewById(R.id.isFavoriteCheckBox);
        HumanCheckBox = findViewById(R.id.isHumanCheckBox);
        AnimeCheckBox = findViewById(R.id.isAnimeCheckBox);
        SingerCheckBox = findViewById(R.id.isSingerCheckBox);
        EntertainerCheckBox = findViewById(R.id.isEntertainerCheckBox);
        IdolCheckBox = findViewById(R.id.isIdolCheckBox);
        AthleteCheckBox = findViewById(R.id.isAthleteCheckBox);
        RemoveNicheCheckBox = findViewById(R.id.isRemoveNicheCheckBox);
        OnlyNicheCheckBox = findViewById(R.id.isOnlyNicheCheckBox);
        BaseballCheckBox = findViewById(R.id.isBaseballCheckBox);
        FootballCheckBox = findViewById(R.id.isFootballCheckBox);

        all_tag = ((TextView)findViewById(R.id.random_hidden)).getText().toString();
        entertainer_tag = ((TextView)findViewById(R.id.entertainer_hidden)).getText().toString();
        idol_tag = ((TextView)findViewById(R.id.idol_hidden)).getText().toString();
        human_tag = ((TextView)findViewById(R.id.human_hidden)).getText().toString();
        anime_tag = ((TextView)findViewById(R.id.anime_hidden)).getText().toString();
        singer_tag = ((TextView)findViewById(R.id.singer_hidden)).getText().toString();
        athlete_tag = ((TextView)findViewById(R.id.athlete_hidden)).getText().toString();
        removeNiche_tag = ((TextView)findViewById(R.id.removeNiche_hidden)).getText().toString();
        niche_tag = ((TextView)findViewById(R.id.niche_hidden)).getText().toString();
        baseball_tag = ((TextView)findViewById(R.id.baseball_hidden)).getText().toString();
        football_tag = ((TextView)findViewById(R.id.football_hidden)).getText().toString();


        reflectOldConfig();

        Spinner spinner = findViewById(R.id.questionNumSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNumStr = (String)parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView hitNumText = findViewById(R.id.numberOfHit);
        hitNumText.setText(String.valueOf(hitNumberCheck(createTagList()))); //該当件数を表示



    }

    public void CheckBoxClick(View view){ //リファレンスに簡潔な方法が載ってた

        boolean checked = ((CheckBox)view).isChecked();

        switch(view.getId()){
            case R.id.isRandomCheckBox:
                if(checked){
                    FavoriteCheckBox.setChecked(false);
                    HumanCheckBox.setChecked(false);
                    AnimeCheckBox.setChecked(false);
                    SingerCheckBox.setChecked(false);
                    EntertainerCheckBox.setChecked(false);
                    IdolCheckBox.setChecked(false);
                    AthleteCheckBox.setChecked(false);
                    RemoveNicheCheckBox.setChecked(false);
                    OnlyNicheCheckBox.setChecked(false);
                    BaseballCheckBox.setChecked(false);
                    FootballCheckBox.setChecked(false);

                    tags_array.add(all_tag);
                    tags_array.remove(human_tag);
                    tags_array.remove(anime_tag);
                    tags_array.remove(singer_tag);
                    tags_array.remove(entertainer_tag);
                    tags_array.remove(idol_tag);
                    tags_array.remove(athlete_tag);
                    tags_array.remove(removeNiche_tag);
                    tags_array.remove(niche_tag);
                    tags_array.remove(baseball_tag);
                    tags_array.remove(football_tag);
                }else{
                    tags_array.remove(all_tag);
                }


                break;

            //case R.id.isFavoriteCheckBox:
            case R.id.isEntertainerCheckBox:

                if (checked){
                    tags_array.add(entertainer_tag);
                }else{
                    tags_array.remove(entertainer_tag);
                }

            case R.id.isIdolCheckBox:

                if(checked) {
                    RandomCheckBox.setChecked(false);
                    tags_array.remove(all_tag);
                    tags_array.add(idol_tag);
                }else{
                    tags_array.remove(idol_tag);
                }
                break;

            case R.id.isHumanCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    AnimeCheckBox.setChecked(false);

                    tags_array.add(human_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(anime_tag);
                }else{
                    tags_array.remove(human_tag);
                }


                break;

            case R.id.isAnimeCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    HumanCheckBox.setChecked(false);
                    SingerCheckBox.setChecked(false);
                    EntertainerCheckBox.setChecked(false);
                    IdolCheckBox.setChecked(false);
                    BaseballCheckBox.setChecked(false);
                    FootballCheckBox.setChecked(false);


                    tags_array.add(anime_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(human_tag);
                    tags_array.remove(singer_tag);
                    tags_array.remove(entertainer_tag);
                    tags_array.remove(idol_tag);
                    tags_array.remove(baseball_tag);
                    tags_array.remove(football_tag);
                }else{
                    tags_array.remove(anime_tag);
                }

                break;

            case R.id.isSingerCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    AthleteCheckBox.setChecked(false);
                    BaseballCheckBox.setChecked(false);
                    FootballCheckBox.setChecked(false);
                    tags_array.add(singer_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(athlete_tag);
                    tags_array.remove(baseball_tag);
                    tags_array.remove(football_tag);
                }else{
                    tags_array.remove(singer_tag);
                }

                break;

            case R.id.isAthleteCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    SingerCheckBox.setChecked(false);

                    tags_array.add(athlete_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(singer_tag);
                }else{
                    tags_array.remove(athlete_tag);
                }

                break;

            case R.id.isRemoveNicheCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    OnlyNicheCheckBox.setChecked(false);

                    tags_array.add(removeNiche_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(niche_tag);
                }else{
                    tags_array.remove(removeNiche_tag);
                }

                break;

            case R.id.isOnlyNicheCheckBox:


                if(checked){
                    RandomCheckBox.setChecked(false);
                    RemoveNicheCheckBox.setChecked(false);


                    tags_array.add(niche_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(removeNiche_tag);
                }else{
                    tags_array.remove(niche_tag);
                }

                break;

            case R.id.isBaseballCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    AnimeCheckBox.setChecked(false);
                    SingerCheckBox.setChecked(false);
                    EntertainerCheckBox.setChecked(false);
                    IdolCheckBox.setChecked(false);
                    FootballCheckBox.setChecked(false);


                    tags_array.add(baseball_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(anime_tag);
                    tags_array.remove(singer_tag);
                    tags_array.remove(entertainer_tag);
                    tags_array.remove(idol_tag);
                    tags_array.remove(football_tag);
                }else{
                    tags_array.remove(baseball_tag);
                }

                break;

            case R.id.isFootballCheckBox:

                if(checked){
                    RandomCheckBox.setChecked(false);
                    AnimeCheckBox.setChecked(false);
                    SingerCheckBox.setChecked(false);
                    EntertainerCheckBox.setChecked(false);
                    IdolCheckBox.setChecked(false);
                    BaseballCheckBox.setChecked(false);


                    tags_array.add(football_tag);
                    tags_array.remove(all_tag);
                    tags_array.remove(anime_tag);
                    tags_array.remove(singer_tag);
                    tags_array.remove(entertainer_tag);
                    tags_array.remove(idol_tag);
                    tags_array.remove(baseball_tag);
                }else{
                    tags_array.remove(football_tag);
                }

                break;
        }
        System.out.println("fffff");
        System.out.println(tags_array);

        String sendurl = createUrl();
        System.out.println(sendurl);
        TextView hitNumText = findViewById(R.id.numberOfHit);
        hitNumText.setText(String.valueOf(hitNumberCheck(createTagList()))); //該当件数を表示
    }


    //設定ファイル
    public void startQuiz(View view){

        //File tagConfig = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/tagConfig.txt"); //デバッグのため外部へ
        File tagConfig = new File(getApplicationContext().getFilesDir(),"/tagConfig.txt");
        tagConfig.delete();
        try{ //catch(Exception e)で大雑把にまとめて捕捉したら、こんなにtry-catchを書く必要はない でも一応
            FileOutputStream fos = new FileOutputStream(tagConfig);
            try{
                OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);

                try{
                    bw.write("Num:" + questionNumStr + "\n");

                    if(RandomCheckBox.isChecked()) bw.write("Ran:1\n");
                    else bw.write("Ran:0\n");

                    if(FavoriteCheckBox.isChecked()) bw.write("Fav:1\n");
                    else bw.write("Fav:0\n");

                    if(HumanCheckBox.isChecked()) bw.write("Hum:1\n");
                    else bw.write("Hum:0\n");

                    if(AnimeCheckBox.isChecked()) bw.write("Ani:1\n");
                    else bw.write("Ani:0\n");

                    if(SingerCheckBox.isChecked()) bw.write("Sin:1\n");
                    else bw.write("Sin:0\n");

                    if(EntertainerCheckBox.isChecked()) bw.write("Ent:1\n");
                    else bw.write("Ent:0\n");

                    if(IdolCheckBox.isChecked()) bw.write("Ido:1\n");
                    else bw.write("Ido:0\n");

                    if(AthleteCheckBox.isChecked()) bw.write("Ath:1\n");
                    else bw.write("Ath:0\n");

                    if(RemoveNicheCheckBox.isChecked()) bw.write("RNi:1\n");
                    else bw.write("RNi:0\n");

                    if(OnlyNicheCheckBox.isChecked()) bw.write("ONi:1\n");
                    else bw.write("ONi:0\n");

                    if(BaseballCheckBox.isChecked()) bw.write("Bse:1\n");
                    else bw.write("Bse:0\n");

                    if(FootballCheckBox.isChecked()) bw.write("Fot:1\n");
                    else bw.write("Fot:0\n");

                    bw.flush();
                    osw.close();
                    bw.close();

                }catch (IOException e){
                    Log.e("Buffered Writer ERROR:",e.getMessage());
                }
                fos.close();

            }catch(UnsupportedEncodingException e){
                Log.e("Encoding ERROR:",e.getMessage());
            }catch (IOException e2){
                Log.e("fos close IO ERROR:",e2.getMessage());
            }

        }catch(FileNotFoundException e){ //FileOutputStreamのチェック例外
            Log.e("File not found ERROR:",e.getMessage());
            try{
                tagConfig.createNewFile();
            }catch(IOException e2){
                Log.e("createNewFile ERROR:",e2.getMessage());
            }
        }

        Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
        startActivity(intent);
    }

    public String createUrl(){
        String[] tags = (String[]) tags_array.toArray(new String[tags_array.size()]);
        String send_url = "http://quiz.takbazinga.com:8080/quiz/tag?";
        //String send_url = "http://quiz.takbazinga.com:8080/quiz/tag?tag_id[]=8&tag_id[]=9";

        int cnt = 0;
        for (String tag : tags) {
            if (cnt == 0){
                send_url += "tag_id[]=" + tag;
            }else{
                send_url += "&tag_id[]=" + tag;
            }
            cnt++;
        }
        return send_url;
    }




    public void reflectOldConfig(){ //前回 書き込んだ設定ファイルを参照して反映させる

        Spinner spinner = findViewById(R.id.questionNumSpinner);
        //TextView debugText3 = findViewById(R.id.debugText3);

        File tagConfig = new File(getApplicationContext().getFilesDir(),"/tagConfig.txt");
        if(tagConfig.exists()) {
            try {
                FileInputStream fis = new FileInputStream(tagConfig);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String tmp;
                String qNum = null;
                while ((tmp = br.readLine()) != null) {
                    if (tmp.contains("Num")) {
                        qNum = tmp.substring(4);
                        //ここでspinner.setSelection((Integer.parseInt(qNum) / 5) - 1);を実行したら何故かタグの設定が反映されなかったので後ろへ
                    }

                    if (tmp.contains("Ran")) {
                        if ((int) (tmp.charAt(4)) == 49)
                            RandomCheckBox.setChecked(true); //半角数字1はUTF-8で0x31 つまり10進数49
                    }

                    if (tmp.contains("Fav")) {
                        if ((int) (tmp.charAt(4)) == 49) FavoriteCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Hum")) {
                        if ((int) (tmp.charAt(4)) == 49) HumanCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Ani")) {
                        if ((int) (tmp.charAt(4)) == 49) AnimeCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Sin")) {
                        if ((int) (tmp.charAt(4)) == 49) SingerCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Ent")) {
                        if ((int) (tmp.charAt(4)) == 49) EntertainerCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Ido")) {
                        if ((int) (tmp.charAt(4)) == 49) IdolCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Ath")) {
                        if ((int) (tmp.charAt(4)) == 49) AthleteCheckBox.setChecked(true);
                    }

                    if (tmp.contains("RNi")) {
                        if ((int) (tmp.charAt(4)) == 49) RemoveNicheCheckBox.setChecked(true);
                    }

                    if (tmp.contains("ONi")) {
                        if ((int) (tmp.charAt(4)) == 49) OnlyNicheCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Bse")) {
                        if ((int) (tmp.charAt(4)) == 49) BaseballCheckBox.setChecked(true);
                    }

                    if (tmp.contains("Fot")) {
                        if ((int) (tmp.charAt(4)) == 49) FootballCheckBox.setChecked(true);
                    }
                }
                spinner.setSelection((Integer.parseInt(qNum) / 5) - 1); //問題数をスピナーのどこが選択されているかを示すindexに直して格納
                fis.close();
                isr.close();
                br.close();
            } catch (Exception e) {
                Log.e("reflectOldConfig ERROR:", e.getMessage());
            }
        }
    }

    private int hitNumberCheck(ArrayList<String> tagList){ //該当件数を把握するための関数
        //タグ「全て」はtagList = nullとして扱う
        int hitCount = 0;
        try{
            File inputFile = new File(getApplicationContext().getFilesDir(),"/input.csv");
            FileInputStream fis = new FileInputStream(inputFile);
            InputStreamReader isr = new InputStreamReader(fis,"SHIFT-JIS");
            BufferedReader br = new BufferedReader(isr);

            String tmp;
            boolean tagFlag;
            boolean isRemoveNicheFlag = false;
            if(tagList != null) {
                isRemoveNicheFlag = tagList.contains("ニッチな問題を除く");
                if(isRemoveNicheFlag) tagList.remove("ニッチな問題を除く");
            }

            while((tmp = br.readLine()) != null){
                tagFlag = true;
                if(tagList != null){
                    if(isRemoveNicheFlag){
                        if(tmp.contains("ニッチ")) tagFlag = false;
                    }
                    for(String str: tagList){
                        tagFlag = tagFlag && (tmp.contains(str));
                    }
                }
                if(tagFlag) hitCount++;
            }
            fis.close(); isr.close(); br.close();
        }catch(Exception e){
            Log.e("hitNumberCheck error",e.getMessage());
            Toast.makeText(getApplicationContext(),"hitNumberCheck() error",Toast.LENGTH_LONG).show();
        }

        return hitCount;
    }

    private ArrayList<String> createTagList(){ //どのチェックボックスがチェックされているかを調べて、該当するタグをArrayListに入れる
        ArrayList<String> tagList = new ArrayList<>();

        if(!RandomCheckBox.isChecked()) {
            if(FavoriteCheckBox.isChecked()) tagList.add("@@");
            if(HumanCheckBox.isChecked()) tagList.add("人物");
            if(AnimeCheckBox.isChecked()) tagList.add("アニメ");
            if(SingerCheckBox.isChecked()) tagList.add("歌手");
            if(EntertainerCheckBox.isChecked()) tagList.add("芸人");
            if(IdolCheckBox.isChecked()) tagList.add("アイドル");
            if(AthleteCheckBox.isChecked()) tagList.add("スポーツ選手");
            if(RemoveNicheCheckBox.isChecked()) tagList.add("ニッチな問題を除く");
            if(OnlyNicheCheckBox.isChecked()) tagList.add("ニッチ");
            if(BaseballCheckBox.isChecked()) tagList.add("野球");
            if(FootballCheckBox.isChecked()) tagList.add("サッカー");
        }

        return tagList;
    }




}


