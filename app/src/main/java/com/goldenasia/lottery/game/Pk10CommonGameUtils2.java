package com.goldenasia.lottery.game;

import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gan on 2018/5/10.
 * 处理pk10冷热和遗漏数据的工具类
 */

public class Pk10CommonGameUtils2 {
    private String[] pickNumbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    //获得遗漏的集合
    public List<String> getYiLouList(String gameMethod, int digit) {
        switch (gameMethod) {
            case "QWMC"://猜前五 QWMC
                return yiLouSXDW(digit);
            case "HWMC"://猜后五 HWMC
                return yiLouSXDW(digit + 5);
            case "CCW"://猜车位 CCW
                return yiLouSXDW(digit);
            case "QEMZX"://前二名直选 QEMZX
                return yiLouSXDW(digit);
            case "HEMZX"://后二名直选 HEMZX
                return yiLouSXDW(digit + 8);
            case "QSMZX"://前三名直选 QSMZX
                return yiLouSXDW(digit);
            case "HSMZX"://后三名直选 HSMZX
                return yiLouSXDW(digit + 7);
            case "QSIMZX"://前四名直选 QSIMZX
                return yiLouSXDW(digit);
            case "HSIMZX"://后四名直选 HSIMZX
                return yiLouSXDW(digit + 6);
            case "QWMZX"://前五名直选 QWMZX
                return yiLouSXDW(digit);
            case "HWMZX"://后五名直选 HWMZX
                return yiLouSXDW(digit + 5);
        }
        return yiLouSXDW(digit);
    }

    private List<String> yiLouSXDW(int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < pickNumbers.length; i++) {
            list.add(getYiLouNumber(pickNumbers[i], digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getYiLouNumber(String number, int digit) {
        for (int i = 0; i < ConstantInformation.HISTORY_CODE_LIST.size(); i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit * 2, digit * 2 + 2).trim())) {//4 7 5 3 2 9 1 6 10 8
                return String.valueOf(i);
            }
        }
        return ConstantInformation.HISTORY_CODE_LIST.size() + "";
    }


    //============================冷热start============================================================
    public List<String> getLengReList(String gameMethod, int digit) {
        switch (gameMethod) {
            case "QWMC"://猜前五 QWMC
                return lengReSXDW(digit);
            case "HWMC"://猜后五 HWMC
                return lengReSXDW(digit + 5);
            case "CCW"://猜车位 CCW
                return lengReSXDW(digit);
            case "QEMZX"://前二名直选 QEMZX
                return lengReSXDW(digit);
            case "HEMZX"://后二名直选 HEMZX
                return lengReSXDW(digit + 8);
            case "QSMZX"://前三名直选 QSMZX
                return lengReSXDW(digit);
            case "HSMZX"://后三名直选 HSMZX
                return lengReSXDW(digit + 7);
            case "QSIMZX"://前四名直选 QSIMZX
                return lengReSXDW(digit);
            case "HSIMZX"://后四名直选 HSIMZX
                return lengReSXDW(digit + 6);
            case "QWMZX"://前五名直选 QWMZX
                return lengReSXDW(digit);
            case "HWMZX"://后五名直选 HWMZX
                return lengReSXDW(digit + 5);
        }
        return lengReSXDW(digit);
    }

    private List<String> lengReSXDW(int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < pickNumbers.length; i++) {
            list.add(getlengReNumber(pickNumbers[i], digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getlengReNumber(String number, int digit) {
        int count = 0;
        int minNumber = ConstantInformation.LENG_RE_COUNT > ConstantInformation.HISTORY_CODE_LIST.size() ? ConstantInformation.HISTORY_CODE_LIST.size() : ConstantInformation.LENG_RE_COUNT;
        for (int i = 0; i < minNumber; i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit * 2, digit * 2 + 2).trim())) {//4 7 5 3 2 9 1 6 10 8
                count++;
            }
        }
        return String.valueOf(count);
    }
    //============================冷热end============================================================
}
