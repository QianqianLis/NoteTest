package com.hrgk.notetest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private NotePage notepage;
    private Article article;

    private Bitmap LeftBitmap, rightBitmap;//创建第一页和第二页的空位图
    private Canvas leftCanvas, rightCanvas;//创建第一页和第二页的空画布

    private ImageView item_layout_leftImage, item_layout_rightImage;
    private Button btn_leftImage, btn_rightImage;

    private int sumPage;
    private int currentLeftPage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        initArticle();

        initBitmap();

        initBtnListener();

    }

    private void initBtnListener() {
        item_layout_leftImage.setOnClickListener(this);
        item_layout_rightImage.setOnClickListener(this);
        btn_leftImage.setOnClickListener(this);
        btn_rightImage.setOnClickListener(this);
    }

    private void initBitmap() {
        //屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        Log.i("qqliLog", "width=" + w + "\nheight=" + h);

        //初始化两张位图
        LeftBitmap = Bitmap.createBitmap(w / 2, h, Bitmap.Config.ARGB_8888);
        rightBitmap = Bitmap.createBitmap(w / 2, h, Bitmap.Config.ARGB_8888);

        //初始化两张画布
        leftCanvas = new Canvas(LeftBitmap);
        rightCanvas = new Canvas(rightBitmap);

        //将一章的书分成一页一页的
        notepage = new NotePage(MainActivity.this, article, w / 2, h);

        sumPage = notepage.getSumPage();

        Log.i("qqliLog", "MainActivity获得的粽叶书" + sumPage);

        currentLeftPage = 0;

        if(sumPage == 1) {
            notepage.draw(leftCanvas, new Paint(), currentLeftPage);
            item_layout_leftImage.setImageBitmap(LeftBitmap);
            notepage.drawEmpty(rightCanvas, new Paint());
            item_layout_rightImage.setImageBitmap(rightBitmap);
        }else {
            notepage.draw(leftCanvas, new Paint(), currentLeftPage);
            item_layout_leftImage.setImageBitmap(LeftBitmap);
            notepage.drawRight(rightCanvas, new Paint(), currentLeftPage + 1);
            item_layout_rightImage.setImageBitmap(rightBitmap);
        }
        Log.i("qqliLog", "currentLeftPage = " + currentLeftPage);

    }

    private void initView() {
        item_layout_leftImage = (ImageView) findViewById(R.id.item_layout_leftImage);
        item_layout_rightImage = (ImageView) findViewById(R.id.item_layout_rightImage);
        btn_leftImage = (Button) findViewById(R.id.btn_leftImage);
        btn_rightImage = (Button) findViewById(R.id.btn_rightImage);

    }

    private void initArticle() {
        /**
         * 初始化章节内容
         */
        article = new Article();
        article.setTitle("测试");
        article.setContent("第一章星空中的青铜巨棺 生命是世间最伟大的奇迹。 " +
                "\n四方上下曰宇。" +
                "\n宇虽有实，而无定处可求。" +
                "\n往古来今曰宙。" +
                "\n宙虽有增长，不知其始之所至。 " +
                "\n浩瀚的宇宙，无垠的星空，许多科学家推测，<img:xx>地球可能是唯一的生命源地。 " +
                "\n人类其实很孤独。在苍茫的天宇中，虽然有亿万星辰，但是却很难寻到第二颗生命源星。 " +
                "\n不过人类从来没有放弃过探索，自上世纪以来已经发射诸多太空探测器。 " +
                "\n旅行者二号是一艘无人外太空探测器，于一九七七年在美国肯尼迪航天中心发射升空。 " +
                "\n它上面携带<img:xx>着一张主题为“向宇宙致意”的镀金唱片，里面包含一些流行音乐和用地球五十五种语言录制的问候辞，以冀有一天被可能存在的外星文明拦截和收到。 " +
                "\n从上世纪七十年代到现在，旅行者二号一直在孤独的旅行，在茫茫宇宙中它如一粒尘埃般渺小。 " +
                "\n同时代的外太空探测器，大多或已经发生故障，或已经中断讯号联系，永远的消失在了枯寂的宇宙中。 " +
                "\n三十几年过去了，科技在不断发展，人类已经研制出更加先进的外太空探测器，也许不久的将来对星空的探索会取得更进一步的发展。 " +
                "\n但纵然如此，在相当长的时间内，新型外太空探测器依然无法追上旅行者二号的步伐。 " +
                "\n三十三年过去了，旅行者二号距<img:xx>离地球已经有一百四十亿公里。 " +
                "\n此时此刻，它已经达到第三宇宙速度，轨道再也不能引导其飞返太阳系，成为了一艘星际太空船。 " +
                "\n黑暗与冰冷的宇宙中，星辰点点，犹如一颗颗晶莹的钻石镶嵌在黑幕上。 " +
                "\n旅行者二号太空探测器虽然正在极速飞行，但是在幽冷与无垠的宇宙中却像是一只小小的蚁虫在黑暗的大地上缓缓爬行。 " +
                "\n三十多年过去后，就在今日这一刻，旅行者二号有了惊人的发现！ " +
                "\n在枯寂的宇宙中，九具庞大的尸体静静的横在那里…… " +
                "\n二零一零年五月二十二日，美国宇航局接收到旅行者二号传送回的一组神秘的数据信息，经过艰难的破译与还原，他们看到了一幅不可思议的画面。 " +
                "\n在这一刻宇航局主监控室内所有人同时变色，最后如木雕泥塑般一动不动，他们震惊到了无以复加的地步！ " +
                "\n直至过了很久众人才回过神<img:xx>来，而后主监控室内一下子沸腾了。 " +
                "\n“上帝，我看到了什么？” " +
                "\n“这怎么可能，无法相信！” " +
                "\n…… " +
                "\n旅行者二号早已不受引导，只能单一的前进，传送回这组神秘的数据信息后，在那片漆黑的宇宙空间匆匆而过，驶向更加幽暗与深远的星域。" +
                "\n 由于那片星空太遥远，纵然有了重大发现，捕捉到了一幅震撼性的画面，人类目前也无能为力。 " +
                "\n这组神秘信息并没有对外公布。而不久后，旅行者二号发生了故障，中断了与地球的讯号传送。 " +
                "\n也许至此可以画上一个句<img:xx>号了，不过有时候事情往往会出乎人们的预料。 " +
                "\n无论是对星空的观测与探索，还是进行生命与物理的科学研究，空间站都具有得天独厚的优越环境。 \n" +
                "从一九七一年苏联首先发射载人空间站成功，到目前为止全世界已发射了九个空间站。 \n" +
                "二零一零年六月十一日，此时此刻，绕地而行的国际空间站内，几名宇航员同时变了颜色，瞳孔急骤收缩。 \n" +
                "时至今日，神的存在，早已被否定。如果还有人继续信仰<img:xx>，那也只是因心灵空虚而寻找一份寄托而已。 \n" +
                "但是就在这一刻，几名宇航精英的思想受到了强烈的的冲击，他们看到了一幅不可思议的画面。 \n" +
                "在国际空间站外，冰冷与黑暗并存的宇宙中，九条庞然大物一动不动，仿佛亘古就已横在那里，让人感觉到无尽的苍凉与久远，那竟然是九具龙尸！ \n" +
                "与古代神话传说中的龙一般无二。 每具龙尸都长达百米，犹如铁水浇铸而成，充满了震撼性的力感。 \n" +
                "九具龙尸皆是五爪黑<img:xx>龙，除却龙角晶莹剔透、紫光闪闪外，龙身通体呈黑色，乌光烁烁，鳞片在黑暗中闪烁着点点神秘的光华。 \n" +
                "龙，传说中的存在，与神并立，凌驾于自然规律之上。但是，科学发展到现在，还有谁会相信龙真的存在？ \n" +
                "国际空间站内的几名宇航员，思想受到了强烈的冲击，眼前所见让他们感觉不可思议！ \n" +
                "枯寂的宇宙中，冰冷的龙尸似不可摧毁的钢铁长城，甚至能够感觉到尸身中所蕴含的恐怖力量。 \n" +
                "只是，它们已经失去了生气，永远的安息在了幽<img:xx>冷的宇宙空间中。 \n" +
                "“那是……” \n" +
                "被深深震撼过后，几名宇航精英的瞳孔再次急骤收缩，他们看到了更为让人震惊的画面。 \n" +
                "九具龙尸都长达百米，在尾端皆绑缚着碗口粗的黑色铁索，连向九具龙尸身后那片黑暗的宇宙空间，在那里静静的悬着一口长达二十米的青铜棺椁。 \n" +
                "巨索千锤百炼，粗长而又坚固，点点乌光令它显得阴寒无比。 \n" +
                "青铜巨棺古朴无华，上面有一些模糊的古老图案，充满了岁月的沧桑感，也不知道在宇宙中漂浮多少年了。 \n" +
                "九龙拉棺！ 在这漆黑而又冰冷的宇宙中，九具龙尸与青铜巨棺被粗长的黑色铁索连在一起，显得极其震撼。<img:xx>\n");
    }

    @Override
    public void onClick(View v) {
        /**
         * 向前翻页
         */
        if (v.getId() == R.id.item_layout_leftImage || v.getId() == R.id.btn_leftImage) {
            if (currentLeftPage == 0) {
                Toast.makeText(MainActivity.this, "已经是第一页了！", Toast.LENGTH_SHORT).show();
            } else {
                toBeforeBitmap(currentLeftPage);
            }
            /**
             * 向后翻页
             */
        } else if (v.getId() == R.id.item_layout_rightImage || v.getId() == R.id.btn_rightImage) {
            //最后一页在右边
            if (sumPage % 2 == 0) {
                //最后一页在右边
                if (currentLeftPage == sumPage - 2) {
                    Toast.makeText(MainActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT).show();
                } else {
                    toNextTwoPage(currentLeftPage);
                }
            }
            //最后一页在左边
            else {
                if (currentLeftPage == sumPage - 1) {
                    Toast.makeText(MainActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT).show();
                } else if (currentLeftPage == sumPage - 3) {
                    currentLeftPage = currentLeftPage + 2;
                    notepage.draw(leftCanvas, new Paint(), currentLeftPage);
                    item_layout_leftImage.setImageBitmap(LeftBitmap);
                    notepage.drawEmpty(rightCanvas, new Paint());
                    item_layout_rightImage.setImageBitmap(rightBitmap);
                } else {
                    toNextTwoPage(currentLeftPage);
                }
            }

        }
    }

    private void toBeforeBitmap(int thisPage) {
        currentLeftPage = thisPage - 2;
        pageChange(currentLeftPage);
        Log.i("qqliLog", "currentLeftPage = " + currentLeftPage);
    }

    private void toNextTwoPage(int thisPage) {
        currentLeftPage = thisPage + 2;
        pageChange(this.currentLeftPage);
        Log.i("qqliLog", "currentLeftPage = " + currentLeftPage);
    }

    private void pageChange(int thisPage) {
        notepage.draw(leftCanvas, new Paint(), thisPage);
        item_layout_leftImage.setImageBitmap(LeftBitmap);
        notepage.drawRight(rightCanvas, new Paint(), thisPage + 1);
        item_layout_rightImage.setImageBitmap(rightBitmap);
    }


}
