package com.hrgk.notetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016/3/7.
 */
public class NotePage {
    private Context context;

    // 文章
    private Article article;
    private String mArticle;

    // 屏幕宽高
    private int screenWidth;
    private int screenHeight;
    // 屏幕中可显示文本的宽度
    private int visibleWidth;
    private int visibleHeight;

    //每行的高度
    private int lineHgight;
    // 字体大小
    private int fontSize;
    //字边距
    private int fontMargin = 20;
    //字总大小
    private int font;

    //每页多少行
    private int perPageLineCount;
    //每行多少字
    private int perLineWordCount;

    //总页数
    private int sumPage;

    //文章结尾
    private int endding;
    //截取的部分
    private String part;

    private List<String> lines; // 文章全部变成行
    private List<List<String>> pages;

    private Bitmap bitmap;
    private Bitmap lMap, rMap;


    public NotePage(Context context, Article article, int screenWidth, int screenHeight) {
        this.context = context;
        this.article = article;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        init(screenWidth, screenHeight);

    }

    private void init(int screenWidth, int screenHeight) {
        visibleWidth = screenWidth / 2;
        visibleHeight = screenHeight;

        //根据屏幕确定字体大小
        fontSize = adjustFontSize();
        font = fontSize + fontMargin;

        //每页多少行
        perPageLineCount = visibleHeight / (fontSize + fontMargin);

        //每行多少字
        perLineWordCount = visibleWidth / fontSize * 2;

        lines = new ArrayList<>();
        pages = new ArrayList<List<String>>();

        lMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
        rMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);


        Log.i("qqliLog", "screenWidth:" + screenWidth + "\nscreenHeight:" + screenHeight + "\nvisibleWidth:" + visibleWidth +
                "\nvisibleHeight" + visibleHeight + "\nfontSize:" + fontSize + "\nperPageLineCount:" + perPageLineCount + "\nperLineWordCount:" + perLineWordCount);

        mArticle = article.getContent();

        lines.add(article.getTitle());
        lines.add("");
        lines.add("2016-03-07 16:02:01");
        lines.add("");

        //截取文章，截完退出
        while (mArticle.length() != 0) {
            //第一个\n的位置
            int pos = mArticle.indexOf("\n");
            Log.i("qqliLog1", "pos:" + pos);
            //连着两个\n\n
            if (pos == 0) {
                //当前页最后，则直接换页
                if (lines.size() == perPageLineCount) {
                    pages.add(lines);
                    lines = new Vector<>();
                } else {
                    lines.add("");
                }

            } else {
                //截取第一个\n前的部分
                part = mArticle.substring(0, pos);
                Log.i("qqliLog1", "part:" + part);
                //如果有图片
                if (part.contains("<img:")) {
                    String temp = part;
                    String img;
                    part = temp.substring(0, temp.indexOf("<img:"));
                    temp = temp.substring(temp.indexOf("<img:"), temp.length());
                    img = temp.substring(temp.indexOf("<img:") + 5, temp.indexOf(">"));
                    if (temp.indexOf(">") + 1 != temp.length()) {
                        mArticle = temp.substring(temp.indexOf(">") + 1, temp.length()) + "\n" + mArticle;
                    }

                    /**
                     * 用8行显示图片， 图片6行，上下各一行
                     * 如果在当页结尾，则为7行，上一行
                     * 如果在当页开始，则为7行，下一行
                     *
                     */

                    //当页能放下
                    if (perPageLineCount - lines.size() > 8) {
                        lines.add("");
                        //<img:xxxx>去除<img  和  >
                        lines.add("img:" + img);
                        //当页结尾
                        if (perPageLineCount - lines.size() == 7) {
                            pages.add(lines);
                            lines = new Vector<>();
                        } else {
                            for (int i = 0; i < 6; i++) {
                                lines.add("");
                            }
                        }

                    }
                    //当页不能放下，到下一页
                    else {
                        pages.add(lines);
                        lines = new Vector<>();
                        //<img:xxxx>去除<img  和  >
                        lines.add("img:" + img);
                        for (int i = 0; i < 6; i++) {
                            lines.add("");
                        }
                    }

                }
                int index = 0;
                while (index != part.length()) {
                    //剩余的字能放满一行
                    if (part.length() - index > perLineWordCount) {
                        if (lines.size() == perPageLineCount) {
                            pages.add(lines);
                            lines = new Vector<>();
                        }
                        lines.add(part.substring(index, index + perLineWordCount));
                        index += perLineWordCount;
                        Log.i("qqliLog1", "part.length():" + part.length());
                        Log.i("qqliLog1", "index:" + index);
                    }
                    //剩余的字放不满一行
                    else {
                        if (lines.size() == perPageLineCount) {
                            pages.add(lines);
                            lines = new Vector<>();
                        }
                        lines.add(part.substring(index, part.length()));
                        index = part.length();
                        Log.i("qqliLog1", "part.length():" + part.length());
                        Log.i("qqliLog1", "index:" + index);
                    }
                }
            }
            //文章剩余第一个\n之后的部分
            mArticle = mArticle.substring(pos + 1, mArticle.length());
            Log.i("qqliLog1", "mArticle:" + mArticle);

        }

        //放最后几行
        if (lines.size() > 0) {
            pages.add(lines);
        }

        sumPage = pages.size();
        for (int i = 0; i < pages.size(); i++) {
            Log.i("qqliLog", "第" + i + "页内容: " + pages.get(i));
        }

    }

    public int getSumPage() {
        return sumPage;
    }

//    public void setBgBitmap(Bitmap bMap) {
//        bitmap = Bitmap.createScaledBitmap(bMap, screenWidth, screenHeight,
//                true);
//    }

    public void draw(Canvas canvas, Paint paint, int pageNum) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(fontSize);
        paint.setColor(0xff663301);

        List<String> showLeftLines = new ArrayList<>();
        showLeftLines = pages.get(pageNum);
        Log.i("qqliLog", "showLeftLines : " + showLeftLines);

        if (pageNum % 2 == 0) {
            bitmap = Bitmap.createScaledBitmap(lMap, screenWidth, screenHeight,
                    true);
        } else {
            bitmap = Bitmap.createScaledBitmap(rMap, screenWidth, screenHeight,
                    true);
        }
        canvas.drawBitmap(bitmap, 0, 0, null);

        for (int line = 0; line < showLeftLines.size(); line++) {
//            canvas.drawText(showLeftLines.get(line), 0, (line + 1) * font, paint);
            //画图
            if (showLeftLines.get(line).contains("img")) {

                Drawable d = ContextCompat.getDrawable(context,R.drawable.background);
                BitmapDrawable bd = (BitmapDrawable) d;
                Bitmap bitmap = bd.getBitmap();

                canvas.drawBitmap(bitmap, null, new Rect(font * 4, line * font, font * 9, (line + 6) * font), paint);
            } else {
                canvas.drawText(showLeftLines.get(line), 0, (line + 1) * font, paint);
            }
        }

    }

    public void drawRight(Canvas canvas, Paint paint, int pageNum) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(fontSize);
        paint.setColor(0xff663301);

        List<String> showRightLines = new ArrayList<>();
        showRightLines = pages.get(pageNum);
        Log.i("qqliLog", "showRightLines : " + showRightLines);

        if (pageNum % 2 == 0) {
            bitmap = Bitmap.createScaledBitmap(lMap, screenWidth, screenHeight,
                    true);
        } else {
            bitmap = Bitmap.createScaledBitmap(rMap, screenWidth, screenHeight,
                    true);
        }
        canvas.drawBitmap(bitmap, 0, 0, null);

        for (int line = 0; line < showRightLines.size(); line++) {

            //画图
            if (showRightLines.get(line).contains("img")) {

                Drawable d = ContextCompat.getDrawable(context,R.drawable.background);
                BitmapDrawable bd = (BitmapDrawable) d;
                Bitmap bitmap = bd.getBitmap();

                canvas.drawBitmap(bitmap, null, new Rect(font * 4, line * font, font * 9, (line + 6) * font), paint);
            } else {
                canvas.drawText(showRightLines.get(line), 0, (line + 1) * font, paint);
            }
        }

    }

    public void drawEmpty(Canvas canvas, Paint paint) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(fontSize);

        bitmap = Bitmap.createScaledBitmap(rMap, screenWidth, screenHeight,
                true);

        canvas.drawBitmap(bitmap, 0, 0, null);

        canvas.drawText("", 0, font, paint);

    }

    /**
     * 根据屏幕确定字体大小
     *
     * @return
     */
    public int adjustFontSize() {
        if (screenWidth <= 240) { // 240X320 屏幕
            return 10;
        } else if (screenWidth <= 320) { // 320X480 屏幕
            return 15;
        } else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕
            return 25;
        } else if (screenWidth <= 540) { // 540X960 屏幕
            return 30;
        } else if (screenWidth <= 800) { // 800X1280 屏幕
            return 40;
        } else { // 大于 800X1280
            return 50;
        }
    }
}
