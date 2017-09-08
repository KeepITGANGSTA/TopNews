package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.SolverVariable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.library.ChannelActivity;
import com.bwie.topnews.R;

import java.util.ArrayList;
import java.util.List;

import bean.TypeBean;
import fragment.NewsFragment;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class HorizontalScrol extends LinearLayout implements ViewPager.OnPageChangeListener{

    private Context context;
    private int size;
    private ViewPager mViewPager;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout lin_type;
    private List<TypeBean> typeList;
    private List<TextView> topList=new ArrayList<>();
    private FragmentManager fragmentManager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageView imageView;

    public HorizontalScrol(Context context) {
        super(context);
        init(context,null);
    }

    public HorizontalScrol(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public HorizontalScrol(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);
    }

    private void init(final Context context, AttributeSet attributeSet){
        this.context=context;
        TypedArray typedArray=context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalScrol);
        //size=typedArray.getDimensionPixelSize(R.styleable.HorizontalScrol_textSize,15);
        typedArray.recycle();
        View view= LayoutInflater.from(context).inflate(R.layout.scroll_view,this,true);
/*        imageView = view.findViewById(R.id.iv_channel);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ChannelActivity.startChannelActivity(context,);
            }
        });*/
        mViewPager=view.findViewById(R.id.mViewPager);
        mViewPager.addOnPageChangeListener(this);
        horizontalScrollView=view.findViewById(R.id.mScroll);
        lin_type=view.findViewById(R.id.linearLayout_type);
    }

    /**
     * 获取数据
     * @param list
     *
     */
    public void loadData(List<TypeBean> list, FragmentManager fragmentManager){
        lin_type.removeAllViews();
        this.typeList=list;
        for (int i = 0; i < typeList.size(); i++) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+typeList.get(i).type_id);
        }
        this.fragmentManager=fragmentManager;
        draw();
    }

    /**
     * 绘制UI
     */
    private void draw(){
        drawScroll();
        draViewPager();
    }

    /**
     * 绘制ViewPager
     */
    private void draViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        mViewPager.setAdapter(viewPagerAdapter);
    }

    /**
     * 绘制头条类型
     */
    private void drawScroll() {
        for (int i = 0; i < typeList.size(); i++) {
            final TextView tv= (TextView) LayoutInflater.from(context).inflate(R.layout.type_tv,null);
            tv.setText(typeList.get(i).type);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(finalI);
                    moveItemToCenter(tv);
                }
            });
            lin_type.addView(tv);
            topList.add(tv);
        }
        // 设置为默认选中
        topList.get(0).setSelected(true);
    }

    /**
     * 移动view对象到中间
     *
     * @param tv
     */
    private void moveItemToCenter(TextView tv) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int[] locations = new int[2];
        tv.getLocationInWindow(locations);
        int rbWidth = tv.getWidth();
        horizontalScrollView.smoothScrollBy((locations[0] + rbWidth / 2 - screenWidth / 2),
                0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (lin_type!=null && lin_type.getChildCount()>0){
            for (int i = 0; i < lin_type.getChildCount(); i++) {
                if (i==position){
                    lin_type.getChildAt(i).setSelected(true);
                }else {
                    lin_type.getChildAt(i).setSelected(false);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * ViewPager适配器
     */
    class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=new NewsFragment();
            Bundle bundle=new Bundle();
            bundle.putString("type",typeList.get(position).type_id);

            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return typeList.size();
        }
    }



}
