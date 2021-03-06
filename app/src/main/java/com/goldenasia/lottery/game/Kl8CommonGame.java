package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class Kl8CommonGame extends Game {

    public Kl8CommonGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), false, true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
            if (i != size - 1 && pickNumbers.get(i).getCheckedNumber().size() != 0 && pickNumbers.get(i + 1)
                    .getCheckedNumber().size() != 0) {
                builder.append("_");
            }
        }
        return builder.toString();
    }

    public static View createInterestingLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_interesting, null, false);
    }

    public static View createTopPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column4, null, false);
    }

    public static View createBottomPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column5, null, false);
    }

    private static void createPicklayout(Game game, String[] name) {
        ViewGroup topLayout = game.getTopLayout();
        View titleView = createInterestingLayout(topLayout);
        topLayout.addView(titleView);
        game.initInterestingPanel(titleView);

        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view;
            if (i == 0)
                view = createTopPickLayout(game.getTopLayout());
            else
                view = createBottomPickLayout(game.getTopLayout());
            PickNumber pickNumber = new PickNumber(view, name[i]);
            pickNumber.setColumnAreaHideOrShow(false);
            game.addPickNumber(pickNumber);
            views[i] = view;
        }

        for (View view : views) {
            topLayout.addView(view);
        }

    }

    //任选
    public static void KNRX1(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX2(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX3(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX4(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX5(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX6(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }

    public static void KNRX7(Game game) {
        createPicklayout(game, new String[]{"上", "下"});
    }
}
