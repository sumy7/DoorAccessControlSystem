package com.sumy.dooraccesscontrolsystem.activity;

import java.util.ArrayList;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.activity.ActivitySplash.GuiderPageAdapter.PageButtonOption;

public class ActivitySplash extends BaseActivity {

    private static Class MAIN_CLASS = MainMenuActivity.class;

    private ViewPager viewPaper;
    private GuiderPageAdapter adapter;
    private ImageView imageView;
    private HorizontalScrollView scrollView;

    private Handler handler;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        // 判断是否第一次启动
        if (!isFirst()) {
            // 不是第一次启动，跳过引导
            startActivity(MAIN_CLASS);
            finish();
            return;
        }
        viewPaper = (ViewPager) findViewById(R.id.viewpager);
        imageView = (ImageView) findViewById(R.id.splash_imageview);
        scrollView = (HorizontalScrollView) findViewById(R.id.splash_scroll);
        adapter = new GuiderPageAdapter();
        PageButtonOption option1 = adapter.new PageButtonOption();
        option1.resID = R.id.paper_item_btn;
        option1.isFinish = false;
        option1.finishPage = 3;
        PageButtonOption option2 = adapter.new PageButtonOption();
        option2.resID = R.id.paper_item_btn;
        option2.isFinish = true;
        option2.finishCls = MAIN_CLASS;
        adapter.addPage(R.layout.paper_item, new String[] { "全新的界面设计",
                "给你耳目一新的视觉体验" }, new int[] { R.id.paper_item_title,
                R.id.paper_item_tv }, option1);
        adapter.addPage(R.layout.paper_item, new String[] { "丰富的验证方式",
                "你想用的验证方式这里都有" }, new int[] { R.id.paper_item_title,
                R.id.paper_item_tv }, option1);
        adapter.addPage(R.layout.paper_item, new String[] { "紧跟的时代潮流",
                "NFC二维码快速简便" }, new int[] { R.id.paper_item_title,
                R.id.paper_item_tv }, option1);
        adapter.addPage(R.layout.paper_item_finish, new String[] { "准备好了吗",
                "点击“开始使用”" }, new int[] { R.id.paper_item_title,
                R.id.paper_item_tv }, option2);
        viewPaper.setAdapter(adapter);
        // 监听页面滑动
        viewPaper.setOnPageChangeListener(new OnPageChangeListener() {

            /*
             * arg0有三种状态（0，1，2）
             * 
             * arg0 ==1 正在滑动
             * 
             * arg0==2 滑动完毕
             * 
             * arg0==0 静止状态
             */
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

            }

            /*
             * arg0 :当前页面及你点击滑动的页面
             * 
             * arg1:当前页面偏移的百分比
             * 
             * arg2:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // viewpaper的总宽度
                float widthOfPager = viewPaper.getWidth() * adapter.getCount();
                // 背景图片的宽度
                float widthOfImage = imageView.getWidth();
                // 移动的距离
                float moveWidthPager = widthOfPager - viewPaper.getWidth();
                float moveWidthImage = widthOfImage - viewPaper.getWidth();
                // 移动的比例
                float ratio = moveWidthImage / moveWidthPager;
                // 当前页移动的距离
                float currentPositionPager = arg0 * viewPaper.getWidth() + arg2;
                // scrollview要移动的距离
                scrollView.scrollTo((int) (currentPositionPager * ratio),
                        (int) scrollView.getY());
            }

            /*
             * arg0 当前选中的页面的Position
             */
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        // 创建handler，10秒后自动跳过引导页
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(MAIN_CLASS);
                showToast("自动跳过引导页");
                finish();
            }
        }, 10000);
    }

    /**
     * 滑动页控件适配器
     * 
     * @author sumy
     * 
     */
    class GuiderPageAdapter extends PagerAdapter {
        /**
         * 存储每个滑动页控件
         * 
         * @author sumy
         * 
         */
        class ViewHolder {
            View root;
            String[] from;
            int[] to;
        }

        /**
         * 欢迎页按钮的相关配置
         * 
         * @author sumy
         * 
         */
        public class PageButtonOption {
            int resID;
            boolean isFinish;
            Class finishCls;
            int finishPage;
        }

        private ArrayList<ViewHolder> viewList = new ArrayList<ViewHolder>();

        /*
         * 页面划出时销毁页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position).root);
        }

        /*
         * 滑动页面的数量
         */
        @Override
        public int getCount() {
            return viewList.size();
        }

        /*
         * 比较两个页面是否相等
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /*
         * 返回页面标题
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return viewList.get(position).from[0];
        }

        /*
         * 界面划入时候调用，添加划入的界面
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position).root);
            return viewList.get(position).root;
        }

        /**
         * 添加欢迎页面
         * 
         * @param resourceID
         *            欢迎页面布局文件的ID
         * @param from
         *            需要显示的文本
         * @param to
         *            需要显示的文本对应的位置
         * @param buttonOption
         *            按钮的资源的配置
         */
        public void addPage(int resourceID, String[] from, int[] to,
                final PageButtonOption buttonOption) {
            ViewHolder hold = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            hold.root = inflater.inflate(resourceID, null);
            hold.from = from;
            hold.to = to;
            for (int i = 0; i < from.length; i++) {
                ((TextView) hold.root.findViewById(to[i])).setText(from[i]);
            }
            if (buttonOption != null) {
                if (buttonOption.isFinish) {
                    hold.root.findViewById(buttonOption.resID)
                            .setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    startActivity(buttonOption.finishCls);
                                    finish();
                                }
                            });
                } else {
                    hold.root.findViewById(buttonOption.resID)
                            .setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    viewPaper
                                            .setCurrentItem(buttonOption.finishPage);
                                }
                            });
                }
            }
            viewList.add(hold);
        }
    }

}
