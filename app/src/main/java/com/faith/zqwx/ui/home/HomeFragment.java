package com.faith.zqwx.ui.home;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.faith.base.util.BaseConstant;
import com.faith.base.util.BaseUIUtil;
import com.faith.base.util.BaseUtil;
import com.faith.zqwx.R;
import com.faith.zqwx.databinding.FragmentHomeBinding;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;
import com.jingewenku.abrahamcaijin.commonutil.AppLogMessageMgr;
import com.jingewenku.abrahamcaijin.commonutil.AppToastMgr;
import fun.inaction.camera.helper.CameraHelper;
import java.io.File;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textTakePic = binding.textTakePic;
        final TextView textPicList = binding.textPicList;
        final TextView textGenVideo = binding.textGenVideo;

        BaseUIUtil.bindClick(this, textTakePic, textPicList, textGenVideo);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_take_pic:
                String[] permissions = {permission.WRITE_EXTERNAL_STORAGE, permission.CAMERA};
                boolean checkPermission = BaseUtil.checkPermission(getActivity(),
                        permissions);
                if (checkPermission) {
                    takePic();
                } else {
                    BaseUtil.requestPermission(this, permissions);
                }
                break;
            case R.id.text_pic_list:
                jump2PicList();
                break;
            case R.id.text_gen_video:
                genVideo();
                break;
        }
    }

    private void takePic() {
        String nowTime = AppDateMgr.todayYyyyMmDdHhMmSs();
        String nowDate = AppDateMgr.todayHhMmSs();
        String dirName = getContext().getExternalFilesDir(null).getAbsolutePath() + File.separator+BaseConstant.PIC_DIRECTORY + File.separator + nowDate;
        File file = BaseUtil.createFile(dirName, nowTime);
        CameraHelper.takePhoto(getActivity(), file, success -> {
            if (!success) {
                AppLogMessageMgr.w("take pic failed");
                return;
            }
            Context context = getContext();
            if (context == null) {
                return;
            }
            AppToastMgr.shortToast(context, "take pic success");
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            takePic();
        }
    }

    private void jump2PicList() {

    }

    private void genVideo() {

    }
}