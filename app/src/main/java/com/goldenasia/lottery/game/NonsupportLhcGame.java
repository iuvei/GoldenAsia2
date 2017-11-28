package com.goldenasia.lottery.game;

import android.util.Log;
import android.widget.Toast;

import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.data.Method;

/**
 * 不支持的游戏
 * Created by Alashi on 2016/2/17.
 */
public class NonsupportLhcGame extends LhcGame
{
    public NonsupportLhcGame(Method method)
    {
        super(method);
    }

    @Override
    public void onInflate()
    {
        Log.w("NonsupportLhcGame", "onInflate: " + GsonHelper.toJson(method));
        Toast.makeText(topLayout.getContext(), "不支持的六合彩游戏类型", Toast.LENGTH_LONG).show();
    }
}