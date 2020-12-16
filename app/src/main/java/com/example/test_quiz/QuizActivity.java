package com.example.test_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import android.os.Handler;
import org.json.JSONObject;

public class QuizActivity extends AppCompatActivity { //クイズ出題画面のアクティビティー

    private int nowQuizNum = 0;
    private int requiredQuestionNum = 0;
    private ArrayList<ArrayList<String>> quizList = new ArrayList<>();
    private int randomNum;

    private Button answerButton;
    private Button nextQuizButton;
    private TextView pseudonymText;
    private TextView memberText;
    private TextView nowNumberText;
    private JSONArray quizData;
    private int key;
    private int max_num;
    private String image_common_dir = "http://quiz.takbazinga.com/images/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        answerButton = findViewById(R.id.answerCheckButton);
        nextQuizButton = findViewById(R.id.nextQuizButton);
        pseudonymText = findViewById(R.id.pseudonymText);
        memberText = findViewById(R.id.memberText);
        nowNumberText = findViewById(R.id.nowNumberText);


        //shared preferenceでなんとかできないか
        File tagConfig = new File(getApplicationContext().getFilesDir(),"/tagConfig.txt"); //タグ設定ファイル
        ArrayList<String> tagList = new ArrayList<>();
        boolean isRandomFlag = false;
        boolean isRemoveNicheFlag = false;




        //------------start----------------
        String quiz_data = getIntent().getStringExtra("QUIZ_DATA");

        try {
            quizData = new JSONArray(quiz_data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        key = 0;
        max_num = quizData.length();
        //------------end----------------



        //File inputFile = new File(MyApplication.getMyAppContext().getFilesDir(),"/input.csv"); //内部ストレージ
        //Log.e("path",MyApplication.getMyAppContext().getFilesDir().getPath());
        //File inputFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/input.csv");
        int questionNum = 0;


        //System.out.println(questionNum);
        questionNum = max_num;
        System.out.println("hello. world");
        System.out.println(questionNum);

        //Toast.makeText(getApplicationContext(),"該当問題が存在しません",Toast.LENGTH_LONG).show();

        if(questionNum == 0){
            Toast.makeText(getApplicationContext(),"該当問題が存在しません",Toast.LENGTH_LONG).show();
        }else{
            //Collections.shuffle(quizList);
            if(questionNum < requiredQuestionNum) requiredQuestionNum = questionNum;
            //TextView debugText2 = findViewById(R.id.debugText2);
            //debugText2.setText("questionNum:" + questionNum);
            //showQuiz();

            //--------------start--------------
            JSONObject quiz = null;
            try {
                quiz = quizData.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                showImageFromWeb(image_common_dir + quiz.getString("photo1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //--------------end--------------

        }
        TextView nowNumberText = findViewById(R.id.nowNumberText);
        nowNumberText.setText((nowQuizNum + 1) + "問目");

    }

    public void nextQuizClick(View view){ //次の問題へボタンが押されたときの動作
        //key = keys.next();
        key++;
        if(nowQuizNum != (requiredQuestionNum - 1)){
            nowQuizNum++;

            answerButton.setText("答えを表示");
            pseudonymText.setText("");
            memberText.setText("");
            nowNumberText.setText((nowQuizNum + 1) + "問目");
            if(nowQuizNum == (requiredQuestionNum - 1)) nextQuizButton.setText("終了");

            //------------start----------------
            JSONObject quiz = null;
            try {
                quiz = quizData.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                showImageFromWeb(image_common_dir + quiz.getString("photo1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //------------end----------------

        }else{ //終了時の処理
            LinearLayout layout = findViewById(R.id.quizActivityLayout);
            layout.removeAllViews(); //画面のコンポーネントを全消去

            TextView endText = new TextView(this);
            endText.setText(" 終了！");
            endText.setTextSize(50);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //(width,height)
            //layoutParams.setMargins(0,200,0,0);
            endText.setLayoutParams(layoutParams);
            layout.addView(endText);
            layout.setGravity(Gravity.CENTER);
            //Log.e("タグ","画面遷移");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 遅らせて実行したい処理
                    Intent intent = new Intent(MyApplication.getMyAppContext(),MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //画面遷移にアニメーションを
                }
            }, 800); // 遅らせたい時間(ミリ秒)


        }
    }


    public void beforeQuizClick(View view){ //前の問題へボタンが押されたときの動作
        //key = keys.previous();
        key--;
        if(nowQuizNum != 0){
            nowQuizNum--;

            answerButton.setText("答えを表示");
            nextQuizButton.setText("次の問題へ");
            pseudonymText.setText("");
            memberText.setText("");
            nowNumberText.setText((nowQuizNum + 1) + "問目");

            //------------start----------------
            JSONObject quiz = null;
            try {
                quiz = quizData.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                showImageFromWeb(image_common_dir + quiz.getString("photo1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //------------end----------------
        }
    }

    public void showQuiz(){
        String url1 = "";

        final Handler handler = new Handler();
        ImageView questionImageView = findViewById(R.id.questionImage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL( url1 );
                    InputStream is = (InputStream)url.getContent();
                    Drawable image = Drawable.createFromStream(is, "");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            questionImageView.setImageDrawable(image);


                        }
                    });

                    is.close();

                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
    }

    public void showImageFromWeb(String image_path){
        final Handler handler = new Handler();
        ImageView questionImageView = findViewById(R.id.questionImage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL( image_path );
                    InputStream is = (InputStream)url.getContent();
                    Drawable image = Drawable.createFromStream(is, "");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            questionImageView.setImageDrawable(image);

                        }
                    });

                    is.close();

                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
    }

    public void __showQuiz(){
        //お気に入りの描画
        ImageButton favoriteButton = findViewById(R.id.favorite);
        if(quizList.get(nowQuizNum).get(7).contains("@@")) favoriteButton.setBackgroundResource(R.drawable.a_favorite);
        else favoriteButton.setBackgroundResource(R.drawable.a_not_favorite);

        //問題画像を表示
        String link;
        Random random = new Random();
        randomNum = random.nextInt(2); //0または1になる（画像は2パターン用意）
        if(randomNum == 0){
            link = quizList.get(nowQuizNum).get(4);
        }else link = quizList.get(nowQuizNum).get(5);



        ImageView questionImageView = findViewById(R.id.questionImage);
        System.out.println(link);
        int id = getResources().getIdentifier(link,"drawable",getPackageName());

        System.out.println("aaaaaa");
        System.out.println(id);


        //この辺だろう
        questionImageView.setImageResource(id);
        //最初drawableの下に他のフォルダを作って問題の画像を入れようとしたけど、drawableの下のフォルダは認識されないらしい
        //左のツリーにも表示されないし、getResources().getIdentifierの戻り値も0になった


        if(id == 0) {
            String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + quizList.get(nowQuizNum).get(4) + ".jpg";
            //Log.e("path",path);

            Bitmap bmp = BitmapFactory.decodeFile(path);
            if(bmp == null){
                path = MyApplication.getMyAppContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" +quizList.get(nowQuizNum).get(4) + ".png";
                bmp = BitmapFactory.decodeFile(path);
            }
            questionImageView.setImageBitmap(bmp);
        }


//        ImageAsyncTask async = new ImageAsyncTask(); //画像読み込みのため非同期処理
//        async.execute(link);
    }

    public void answerButtonClick(View view) throws JSONException { //答えを表示ボタンの動作
        //ここにクイズ情報を入れていく

        JSONObject quiz = quizData.getJSONObject(key);
        answerButton.setText(quiz.getString("name"));
        pseudonymText.setText(quiz.getString("wayToCall"));
        memberText.setText(quiz.getString("company"));
        //showImageFromWeb(image_common_dir + quiz.getString("photo1"));

//        answerButton.setText(quizList.get(nowQuizNum).get(1));
//        pseudonymText.setText(quizList.get(nowQuizNum).get(2));
//        memberText.setText(quizList.get(nowQuizNum).get(3));
    }

    public void favoriteButtonClick(View view){ //お気に入り登録ボタンの動作
        ImageButton favoriteButton = findViewById(R.id.favorite);

        int id = Integer.valueOf(quizList.get(nowQuizNum).get(0)) + 1;

        try{
            //File outFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/input.csv");
            File outFile = new File(MyApplication.getMyAppContext().getFilesDir(),"/input.csv"); //内部ストレージから読み込む

            InputStreamReader isr = new InputStreamReader(new FileInputStream(outFile),"SHIFT-JIS");
            BufferedReader br = new BufferedReader(isr);

            File tmpExFile = new File(MyApplication.getMyAppContext().getFilesDir(),"/tmpExFile.csv"); //書き写すための一時ファイル

            //File toFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/toFile.csv");
            //toFile.delete();
            //FileOutputStream fos2 = new FileOutputStream(toFile);
            //OutputStreamWriter osw2 = new OutputStreamWriter(fos2,"SHIFT-JIS");
            //BufferedWriter bw2 = new BufferedWriter(osw2);

            String tmp;
            FileOutputStream fos = new FileOutputStream(tmpExFile,true); //書き移すためのFileOutputStream
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"SHIFT-JIS"));
            int count = 0;
            //ボタンのクリックを受けてファイルのお気に入りの部分を書き換える
            while((tmp = br.readLine()) != null) {
                if(count != id) {
                    try {
                        bw.write(tmp + "\n");
                        bw.flush();
                        //bw2.write(tmp + "\n");
                        //bw2.flush();
                    } catch (Exception e) {
                        Log.e("favButton writeERROR", e.getMessage());
                        Toast.makeText(getApplicationContext(), "favButton ERROR", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(tmp.contains("@@")) {
                        favoriteButton.setBackgroundResource(R.drawable.a_not_favorite);
                        bw.write(tmp.substring(0,tmp.length() - 3) + "\n");
                        bw.flush();
                        //bw2.write(tmp.substring(0,tmp.length() - 3) + "\n");
                        //bw2.flush();

                        //お気に入りボタン表示を反映させるためだけに、もう一度isFavoriteFlagを読み込み直すのは馬鹿らしいのでquizListを操作
                    }else{
                        favoriteButton.setBackgroundResource(R.drawable.a_favorite);
                        bw.write(tmp + "、@@\n");
                        bw.flush();
                        //bw2.write(tmp + "、@@\n");
                        //bw2.flush();
                    }
                }
                count++;
            }
            bw.close();
            fos.close();
            isr.close();
            br.close();

            //fos2.close(); osw2.close(); bw2.close();

            outFile.delete(); //元のinputファイルを消して、お気に入り設定を改めたtmpExFileを新しくinputファイルにする
            tmpExFile.renameTo(new File(MyApplication.getMyAppContext().getFilesDir(),"/input.csv"));


        }catch(Exception e){
            Log.e("favButton ERROR",e.getMessage());
            Toast.makeText(getApplicationContext(),"favButton ERROR",Toast.LENGTH_LONG).show();
        }

    } //ここまでお気に入り登録ボタンの動作


    public void imageReloadButtonClick(View view) throws JSONException { //画像切り替えボタンの動作
        ImageView questionImageView = findViewById(R.id.questionImage);

        JSONObject quiz = null;
        try {
            quiz = quizData.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String link;
        String imgPath;
        if(randomNum == 0) { //画像の参照を反転させる
            //link = quizList.get(nowQuizNum).get(5);
            randomNum = 1;
            imgPath = quiz.getString("photo1");
            //imgPath = quizData.getJSONObject(key).getString( "photo1");
        }else{
            //link = quizList.get(nowQuizNum).get(4);
            //imgPath = quizData.getJSONObject(key).getString("photo2");
            randomNum = 0;
            imgPath = quiz.getString("photo2");
        }
        //int id = getResources().getIdentifier(link,"drawable",getPackageName());

        showImageFromWeb(image_common_dir + imgPath);
        //questionImageView.setImageResource(id);
        //最初drawableの下に他のフォルダを作って問題の画像を入れようとしたけど、drawableの下のフォルダは認識されないらしい
        //左のツリーにも表示されないし、getResources().getIdentifierの戻り値も0になった


    }




//    public class ImageAsyncTask extends AsyncTask<String,String,Bitmap> { //ネット上の画像を非同期で読み込むためのAsyncTask　というかAsyncTask継承してないと
//                                                                          //たぶんネットにつなげなかった（android.os.NetworkOnMainThreadException）
//                                                                          //あとネットにつなぐのにManifestファイルへPermission書き込みが必要だった
//        Bitmap bmp = null;
//        @Override
//        protected Bitmap doInBackground(String...params){ //ジェネリクスだから型は自由だけど、変数名はparams固定だった
//
//            try { //この辺のtry catchはチェック例外のため
//                URL url = new URL(params[0]);
//                try {
//                    InputStream is = url.openStream();
//                    bmp = BitmapFactory.decodeStream(is);
//                    is.close();
//                }catch(IOException e){
//                    Log.e("URL OpenStream ERROR:",e.getMessage());
//                }
//            }catch(MalformedURLException e){
//                Log.e("URL ERROR:",e.getMessage());
//            }
//            return bmp;
//
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result){ //非同期処理終了後にUIスレッドで行う処理
//            if(bmp != null){
//                ImageView questionImageView = findViewById(R.id.questionImage);
//                questionImageView.setImageBitmap(bmp);
//            }else{
//                TextView debugText2 = findViewById(R.id.debugText2);
//                debugText2.setText("bmp = null");
//            }
//        }
//    }









}
