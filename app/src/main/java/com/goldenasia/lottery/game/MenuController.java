package com.goldenasia.lottery.game;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.component.TagCloudView;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.data.MethodList;
import com.goldenasia.lottery.material.MethodQueue;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.view.TableMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于显示弹出菜单，选择玩法
 * Created by Alashi on 2016/2/17.
 */
public class MenuController {
    private static final String TAG = MenuController.class.getSimpleName();

    @BindView(R.id.tableMenu)
    TableMenu tableMenu;
    @BindView(R.id.preference)
    TagCloudView preference;

  /*  @Bind(R.id.game_preference_1) TextView preference1;
    @Bind(R.id.game_preference_2) TextView preference2;
    @Bind(R.id.game_preference_3) TextView preference3;*/

    private PopupWindow popupWindow;
    private Activity activity;

    private TableMenu.OnClickMethodListener onClickMethodListener;
    private Method currentMethod;
    private ArrayList<MethodList> methodList;
    private ChooserModel chooserModel;
    private int lotteryId;
    private boolean dataChanged;
    private List<String> tags = new ArrayList<>(0);
    private HashMap<Integer, Boolean> map = new HashMap<>(0);//记录选择的位置
    private MethodQueue limitedQueue;

    public MenuController(FragmentActivity activity, Lottery lottery) {
        this.activity = activity;
        this.lotteryId = lottery.getLotteryId();
    }

    private ChooserModel getChooserModel() {
        if (chooserModel == null) {
            chooserModel = ChooserModel.get(activity, "model_history_" + GoldenAsiaApp.getUserCentre().getUserID() +
                    "_" + lotteryId);
        }
        return chooserModel;
    }

    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
        if (tableMenu != null) {
            tableMenu.setCurrentMethod(currentMethod);
        }
    }

    public void setMethodList(ArrayList<MethodList> methodList) {
        this.methodList = methodList;
        dataChanged = true;
    }

    public boolean isShowing() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    public void show(View anchor) {
        if (methodList == null || methodList.size() == 0) {
            return;
        }
        if (popupWindow == null) {
            View topView = LayoutInflater.from(activity).inflate(R.layout.game_menu_layout, null);
            tableMenu = (TableMenu) topView.findViewById(R.id.tableMenu);
            ButterKnife.bind(this, topView);
            tableMenu.setOnClickMethodListener(onClickMethodListener);

            popupWindow = new PopupWindow(activity);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(topView);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setAnimationStyle(R.style.pulldown_in_out);
        }

        if (dataChanged) {
            dataChanged = false;
            getChooserModel().setMethodList(methodList);
            tableMenu.setMethodList(methodList);
            tableMenu.setCurrentMethod(currentMethod);
        }

        usePreference();

        preference.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                bindPositionView(position);
            }
        });

        popupWindow.showAsDropDown(anchor);
    }

    public void addPreference(Method method) {
        getChooserModel().addChoosedMethod(method);
    }

    /**
     * 定点标签记录和view变化
     **/
    private void bindPositionView(int position) {
        for (int i = 0; i < tags.size(); i++) {
            if (i == position) {
                map.put(i, true);
            } else {
                map.put(i, false);
            }
        }
        preference.setTagsByPosition(map, tags);
        for (int i = 0; i < tags.size(); i++) {
            if (map.get(i)) {
                preference.getChildAt(i).setBackgroundResource(R.drawable.shape_rec_button);
                if (onClickMethodListener != null) {
                    onClickMethodListener.onClickMethod(/*chooserModel.getMethodInfo(i).method*/limitedQueue.get(i));
                }
            }
        }
    }

    /**
     * 引用常用玩法
     */
    private void usePreference() {
        tags.clear();
        map.clear();
        /*for (int i = 0; i < 9; i++) {
            if(chooserModel.getMethodInfo(i)!=null){
                tags.add(chooserModel.getMethodInfo(i).method.getCname());
                map.put(i, false);
            }
        }*/
        limitedQueue = (MethodQueue) SharedPreferencesUtils.getObject(activity, "regular_methods",
                GoldenAsiaApp.getUserCentre().getUserID() + "_" + lotteryId);
        if (limitedQueue != null)
            for (int i = 0, size = limitedQueue.size(); i < size; i++) {
                tags.add(limitedQueue.get(i).getCname());
                map.put(i, false);
            }
        /*else
            for (int i = 0; i < 9; i++)
                if (chooserModel.getMethodInfo(i) != null)
                {
                    tags.add(chooserModel.getMethodInfo(i).method.getCname());
                    map.put(i, false);
                }*/

        int common = 0;
        for (Integer in : map.keySet()) {
            if (currentMethod != null && currentMethod.getMethodId() == chooserModel.getMethodInfo(in).method
                    .getMethodId()) {
                common = in;
            }
        }

        bindPositionView(common);
    }

    public void hide() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void setOnClickMethodListener(TableMenu.OnClickMethodListener onClickMethodListener) {
        this.onClickMethodListener = onClickMethodListener;
        if (tableMenu != null) {
            tableMenu.setOnClickMethodListener(onClickMethodListener);
        }
    }
}
