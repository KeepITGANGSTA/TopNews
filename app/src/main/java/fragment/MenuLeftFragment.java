package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwie.topnews.LoginActivity;
import com.bwie.topnews.R;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class MenuLeftFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    private Button btn_more_login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView=inflater.inflate(R.layout.left_menu_layout,container,false);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


    }

    /**
     * 初始化布局
     */
    private void initView() {
        btn_more_login = mRootView.findViewById(R.id.btn_More_login);
        btn_more_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_More_login:
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
