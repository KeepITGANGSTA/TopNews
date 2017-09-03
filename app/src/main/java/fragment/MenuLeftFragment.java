package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.topnews.LoginActivity;
import com.bwie.topnews.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 李英杰 on 2017/8/30.
 */

public class MenuLeftFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    private Button btn_more_login;
    private ImageView imageView;

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
        imageView = mRootView.findViewById(R.id.leftMenu_qq);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "qqdenglu", Toast.LENGTH_SHORT).show();
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ,umAuthListener);
            }
        });
    }

    /**
     * 有盟授权回调
     */
    UMAuthListener umAuthListener=new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Toast.makeText(getContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String name = map.get("name");
            String gender=map.get("gender");
            String iconurl = map.get("iconurl");
            Toast.makeText(getContext(), name+","+gender, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            String message = throwable.getMessage();
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
        }
    };

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
