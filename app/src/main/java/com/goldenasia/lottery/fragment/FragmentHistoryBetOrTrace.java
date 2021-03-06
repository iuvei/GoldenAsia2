package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.Bet;
import com.goldenasia.lottery.data.BetListCommand;
import com.goldenasia.lottery.data.BetListResponse;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryMenu;
import com.goldenasia.lottery.data.LotteryMenuCommand;
import com.goldenasia.lottery.data.Trace;
import com.goldenasia.lottery.data.TraceListCommand;
import com.goldenasia.lottery.data.TraceListResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.GameHistoryAdapter;
import com.goldenasia.lottery.view.adapter.HistoryBetPopupWindowAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by Gan on 2017/10/11.
 * 投注记录或者追号记录
 * 根据isBet字段来区分的
 * isBet=true是投注记录
 * isBet=false是追号记录
 */
public class FragmentHistoryBetOrTrace extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    private static final String TAG = FragmentHistoryBetOrTrace.class.getSimpleName();
    private static final int LOTTERY_LIST_COMMAND = 0;//彩票种类信息查询
    private static final int BET_LIST_COMMAND = 1;//投注订单列表
    private static final int TRACE_LIST_COMMAND = 2;//追号订单列表

    private static final int FIRST_PAGE = 1;//服务器分页从1开始

    private boolean isBet;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.lotteryRadioButton)
    RadioButton lotteryRadioButton;
    @BindView(R.id.timeRadioButton)
    RadioButton timeRadioButton;
    @BindView(R.id.stateRadioButton)
    RadioButton stateRadioButton;
    @BindView(R.id.empty_show)
    TextView empty_show;

    private GameHistoryAdapter adapter;
    private List items = new ArrayList();
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;
    private boolean isFirstTime = true;

    private BubblePopupWindow adapterPopupWindow;

    private HistoryBetPopupWindowAdapter mTransferAdapter;
    private List<Lottery> mHistoryBetPopupList = null;

    private int mSelectLotteryPosition = -1;//彩种选中位置
    private int mSelectTimePosition = 0;//时间选中位置
    private int mSelectStatePosition = 0;//状态选中位置

    private int mCurrentLotterySelectId = -1;//选择彩种ID
    private Date mCurrentTimeSelectStart;//起始时间
    private Date mCurrentTimeSelectEnd;//结束时间
    private int mCurrentStateSelect=-1;//状态

    private RadioButton mCheckedView;

    private final int LOTTERY_SELECT = 1;//选择彩种查询
    private final int TIME_SELECT = 2;//时间查询
    private final int STATE_SELECT = 3; //状态查询

    private int mCurrentQueryConditionType = -1;
    private ArrayList<Lottery> mLotteryList;

    //投注记录
    public static void launchBet(BaseFragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", true);
        FragmentLauncher.launch(fragment.getActivity(), FragmentHistoryBetOrTrace.class, bundle);
    }

    //追号记录
    public static void launchTrace(BaseFragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", false);
        FragmentLauncher.launch(fragment.getActivity(), FragmentHistoryBetOrTrace.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isBet = getArguments().getBoolean("isBet");
        return inflateView(inflater, container,isBet ? "投注记录" : "追号记录", R.layout.fragment_history_bet_or_trace);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup.setOnCheckedChangeListener(this);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            if (isBet) {
                loadBetList(false, FIRST_PAGE);
            } else {
                loadTraceList(false, FIRST_PAGE);
            }
        });
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && items.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger) {
                    if (isBet) {
                        loadBetList(false, page + 1);
                    } else {
                        loadTraceList(false, page + 1);
                    }
                }
            }
        });
        adapter = new GameHistoryAdapter(view.getContext());
        listView.setAdapter(adapter);

        mCurrentTimeSelectStart=TimeUtils.getBeginDateOfToday();
        mCurrentTimeSelectEnd= TimeUtils.getEndDateOfToday();
        if (page == FIRST_PAGE) {
//            if (isBet) {
//                loadBetList(isFirstTime, FIRST_PAGE);
//            } else {
//                loadTraceList(isFirstTime, FIRST_PAGE);
//            }
            //默认不加载缓存的数据
            if (isBet) {
                loadBetList(false, FIRST_PAGE);
            } else {
                loadTraceList(false, FIRST_PAGE);
            }
        }
        isFirstTime = false;


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 彩票种类信息查询
     */
    private void loadLotteryList() {
        LotteryMenuCommand command = new LotteryMenuCommand();
        command.setLotteryID(0);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryMenu>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LOTTERY_LIST_COMMAND, this);
    }

    private void loadBetList(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        BetListCommand command = new BetListCommand();
        if(mCurrentLotterySelectId!=-1) {
            command.setLottery_id(mCurrentLotterySelectId);
        }
        command.setStart_time(mCurrentTimeSelectStart);
        command.setEnd_time(mCurrentTimeSelectEnd);
        command.setCheck_prize_status(mCurrentStateSelect);
        command.setCurPage(page);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,
                BET_LIST_COMMAND, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof BetListResponse) {
                items.addAll(((BetListResponse) restResponse.getData()).getBets());
                totalCount = items.size();
                adapter.setData(items);
            } else {
                adapter.setData(null);
            }
        }
        restRequest.execute();
    }

    private void loadTraceList(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        TraceListCommand command = new TraceListCommand();
        if(mCurrentLotterySelectId!=-1) {
            command.setLottery_id(mCurrentLotterySelectId);
        }
        command.setStart_time(mCurrentTimeSelectStart);
        command.setEnd_time(mCurrentTimeSelectEnd);
        command.setStatus(mCurrentStateSelect);
        command.setCurPage(page);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,
                TRACE_LIST_COMMAND, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof TraceListResponse) {
                items.addAll(((TraceListResponse) restResponse.getData()).getTraces());
                totalCount = items.size();
                adapter.setData(items);
            } else {
                adapter.setData(null);
            }
        }
        restRequest.execute();
    }


    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        if (isBet) {
            Bet bet = (Bet) adapter.getItem(position);
            int lotteryId = bet.getLotteryId();
            switch (lotteryId) {
                /*case 15:
                    BetOrTraceDetailMmcFragment.launch(this, (Bet) adapter.getItem(position));
                    break;*/
                case 17:
                case 26:
                    BetOrTraceDetailLhcFragment.launch(this, (Bet) adapter.getItem(position));
                    break;
                default:
                    BetOrTraceDetailFragment.launch(this, (Bet) adapter.getItem(position));
                    break;
            }
            /*if (lotteryId == 15)
            {
                BetOrTraceDetailMmcFragment.launch(this, (Bet) adapter.getItem(position));
            } else if (lotteryId == 17 || lotteryId == 26)
            {
                BetOrTraceDetailLhcFragment.launch(this, (Bet) adapter.getItem(position));
            } else
            {
                BetOrTraceDetailFragment.launch(this, (Bet) adapter.getItem(position));
            }*/
        } else {
            Trace trace = (Trace) adapter.getItem(position);
            int lotteryId = trace.getLotteryId();
            switch (lotteryId) {
                /*case 15:
                    BetOrTraceDetailMmcFragment.launch(this, (Trace) adapter.getItem(position));
                    break;*/
                case 17:
                case 26:
                    BetOrTraceDetailLhcFragment.launch(this, (Trace) adapter.getItem(position));
                    break;
                default:
                    BetOrTraceDetailFragment.launch(this, (Trace) adapter.getItem(position));
                    break;
            }
            //BetOrTraceDetailFragment.launch(this, (Trace) adapter.getItem(position));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Lottery lottery = new Lottery();
        List<Lottery> lotteryList = new ArrayList<Lottery>();

        switch (checkedId) {
            case R.id.lotteryRadioButton:
                initPopupWindow2();

                mCheckedView=lotteryRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = LOTTERY_SELECT;

                //彩种选择的时候 时间和状态选择就不可点击了，彩种全部显示出来后才让时间和状态选择就可以点击
                timeRadioButton.setEnabled(false);
                stateRadioButton.setEnabled(false);

                if(mLotteryList!=null&&mLotteryList.size()>0){
                    mTransferAdapter.setData(mLotteryList, mSelectLotteryPosition);
                    adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up,10);

                    timeRadioButton.setEnabled(true);
                    stateRadioButton.setEnabled(true);
                }else{
                    loadLotteryList();//彩票种类信息查询
                }

                break;
            case R.id.timeRadioButton:
                initPopupWindow();

                mCheckedView=timeRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = TIME_SELECT;

                lotteryList = new ArrayList<Lottery>();
                String[] timeStates = new String[]{"今天", "昨天", "近7天"};
                for (int i = 0; i < timeStates.length; i++) {
                    lottery = new Lottery();
                    lottery.setLotteryId(i);
                    lottery.setCname(timeStates[i]);
                    lotteryList.add(lottery);
                }

                mTransferAdapter.setData(lotteryList, mSelectTimePosition);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);

                break;
            case R.id.stateRadioButton:
                initPopupWindow();

                mCheckedView=stateRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = STATE_SELECT;

                String[] states;

                if(isBet){
                    //状态 [-1:全部，0:未开奖，2:未中奖，1:已中奖，65535:已撤单]
                    states = new String[]{"全部状态", "已中奖", "未开奖", "未中奖", "撤单"};
                }else{
                    //状态[-1:全部,0:未开始,1:正在进行,2:已完成,3:已取消]
                    states = new String[]{"全部状态", "已完成", "进行中", "已取消"};
                }
                for (int i = 0; i < states.length; i++) {
                    lottery = new Lottery();
                    lottery.setLotteryId(i);
                    lottery.setCname(states[i]);
                    lotteryList.add(lottery);
                }

                mTransferAdapter.setData(lotteryList, mSelectStatePosition);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);

                break;
        }
    }

    private void initPopupWindow2() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history_bet_popupwindow2, null);
        BubbleLinearLayout  bubbleView = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);

        adapterPopupWindow = new BubblePopupWindow(rootView, bubbleView);
        adapterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mCheckedView.isChecked()) {
                    radioGroup.clearCheck();
                }
            }
        });
        adapterPopupWindow.setHeight(100);

        mHistoryBetPopupList = new ArrayList<Lottery>();

        mTransferAdapter = new HistoryBetPopupWindowAdapter(getContext(), mHistoryBetPopupList);
        mTransferAdapter.setOnIssueNoClickListener((Lottery lottery) -> {

            if (mCurrentQueryConditionType == LOTTERY_SELECT) {
                mCurrentLotterySelectId = lottery.getLotteryId();
                lotteryRadioButton.setText(lottery.getCname());
                mSelectLotteryPosition=mCurrentLotterySelectId;

            } else if (mCurrentQueryConditionType == TIME_SELECT) {
                selectPositionToTimeSelected(lottery.getLotteryId());
                timeRadioButton.setText(lottery.getCname());
                mSelectTimePosition=lottery.getLotteryId();

            } else if (mCurrentQueryConditionType == STATE_SELECT){
                selectPositionToStateSelected(lottery.getLotteryId());
                stateRadioButton.setText(lottery.getCname());
                mSelectStatePosition=lottery.getLotteryId();
            }else{

            }
            if (isBet) {
                loadBetList(false, FIRST_PAGE);
            } else {
                loadTraceList(false, FIRST_PAGE);
            }
            adapterPopupWindow.dismiss();
        });
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mTransferAdapter);
    }

    private void initPopupWindow() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history_bet_popupwindow, null);
        BubbleLinearLayout bubbleView = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        adapterPopupWindow = new BubblePopupWindow(rootView, bubbleView);
        adapterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mCheckedView.isChecked()) {
                    radioGroup.clearCheck();
                }
            }
        });

        mHistoryBetPopupList = new ArrayList<Lottery>();

        mTransferAdapter = new HistoryBetPopupWindowAdapter(getContext(), mHistoryBetPopupList);
        mTransferAdapter.setOnIssueNoClickListener((Lottery lottery) -> {

            if (mCurrentQueryConditionType == LOTTERY_SELECT) {
                mCurrentLotterySelectId = lottery.getLotteryId();
                lotteryRadioButton.setText(lottery.getCname());
                mSelectLotteryPosition=mCurrentLotterySelectId;

            } else if (mCurrentQueryConditionType == TIME_SELECT) {
                selectPositionToTimeSelected(lottery.getLotteryId());
                timeRadioButton.setText(lottery.getCname());
                mSelectTimePosition=lottery.getLotteryId();

            } else if (mCurrentQueryConditionType == STATE_SELECT){
                selectPositionToStateSelected(lottery.getLotteryId());
                stateRadioButton.setText(lottery.getCname());
                mSelectStatePosition=lottery.getLotteryId();
            }else{

            }
            if (isBet) {
                loadBetList(false, FIRST_PAGE);
            } else {
                loadTraceList(false, FIRST_PAGE);
            }
            adapterPopupWindow.dismiss();
        });
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mTransferAdapter);
    }


    private void selectPositionToStateSelected(int position) {
        if (isBet) {
            //状态 [-1:全部，0:未开奖，2:未中奖，1:已中奖，65535:已撤单]
            switch (position){
                case 0://全部
                    mCurrentStateSelect=-1;
                    break;
                case 1://已中奖
                    mCurrentStateSelect=1;
                    break;
                case 2://未开奖
                    mCurrentStateSelect=0;
                    break;
                case 3://未中奖
                    mCurrentStateSelect=2;
                    break;
                case 4://已撤单
                    mCurrentStateSelect=65535;
                    break;
                default:
                    mCurrentStateSelect=-1;
                    break;
            }
        } else {
            //状态[-1:全部,0:未开始,1:正在进行,2:已完成,3:已取消]
            switch (position){
                case 0://全部
                    mCurrentStateSelect=-1;
                    break;
                case 1://已完成
                    mCurrentStateSelect=2;
                    break;
                case 2://进行中
                    mCurrentStateSelect=1;
                    break;
                case 3://已取消
                    mCurrentStateSelect=3;
                    break;
                default:
                    mCurrentStateSelect=-1;
                    break;
            }
        }


    }

    private void selectPositionToTimeSelected(int position) {
        switch (position){
            case 0://"今天"
                mCurrentTimeSelectStart=TimeUtils.getBeginDateOfToday();
                mCurrentTimeSelectEnd=TimeUtils.getEndDateOfToday();
                break;
            case 1://"昨天"
                mCurrentTimeSelectStart=TimeUtils.getBeginDateOfYesterday();
                mCurrentTimeSelectEnd=TimeUtils.getEndDateOfYesterday();
                break;
            case 2://"近7天"
                mCurrentTimeSelectStart=TimeUtils.getLatelyDateOfSeven();
                mCurrentTimeSelectEnd=TimeUtils.getEndDateOfToday();
                break;
            default:
                ;
        }
    }


    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case LOTTERY_LIST_COMMAND: { //彩票种类信息查询
                    LotteryMenu lotteryMenu = (LotteryMenu) response.getData();

                    mLotteryList = new ArrayList<>();
                     ArrayList<Lottery> sscLotteryList = new ArrayList<>();
                     ArrayList<Lottery> syxwLotteryList = new ArrayList<>();
                     ArrayList<Lottery> ksLotteryList = new ArrayList<>();
                     ArrayList<Lottery> lowLotteryList = new ArrayList<>();
                     ArrayList<Lottery> othersLotteryList = new ArrayList<>();
                     ArrayList<Lottery> qwList = new ArrayList<>();
                     ArrayList<Lottery> pk10List = new ArrayList<>();
                    ArrayList<Lottery> markSix = new ArrayList<>();

                    Lottery  headLottery=new Lottery();
                    headLottery.setLotteryId(-1);
                    headLottery.setCname("选择彩种");
                    mLotteryList.add(0,headLottery);

                    if (lotteryMenu.getSsc() != null)
                        sscLotteryList = lotteryMenu.getSsc();
                    if (lotteryMenu.getSyxw() != null)
                        syxwLotteryList = lotteryMenu.getSyxw();
                    if (lotteryMenu.getKs() != null)
                        ksLotteryList = lotteryMenu.getKs();
                    if (lotteryMenu.getLow() != null)
                        lowLotteryList = lotteryMenu.getLow();
                    if (lotteryMenu.getOthers() != null)
                        othersLotteryList = lotteryMenu.getOthers();
                    if (lotteryMenu.getQw() != null)
                        qwList = lotteryMenu.getQw();
                    if (lotteryMenu.getPk10() != null)
                        pk10List = lotteryMenu.getPk10();
                    if (lotteryMenu.getMarkSix() != null)
                        markSix = lotteryMenu.getMarkSix();
                    mLotteryList.addAll(sscLotteryList);
                    mLotteryList.addAll(syxwLotteryList);
                    mLotteryList.addAll(ksLotteryList);
                    mLotteryList.addAll(lowLotteryList);
                    mLotteryList.addAll(othersLotteryList);
                    mLotteryList.addAll(qwList);
                    mLotteryList.addAll(pk10List);
                    mLotteryList.addAll(markSix);

                    mTransferAdapter.setData(mLotteryList, mSelectLotteryPosition);
                    adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up,1);

                    timeRadioButton.setEnabled(true);
                    stateRadioButton.setEnabled(true);
                    break;
                }

                case BET_LIST_COMMAND: { //投注订单列表
                    if (!isBet) {
                        break;
                    }
                    if (response.getData() == null || !(response.getData() instanceof BetListResponse)) {
                        items.clear();
                    } else {
                        totalCount = ((BetListResponse) response.getData()).getPackagesNumber();
                        if (page == FIRST_PAGE) {
                            items.clear();
                        }
                        items.addAll(((BetListResponse) response.getData()).getBets());
                    }
//                    adapter.setData(items);
                    if(items.size()==0){
                        refreshLayout.setVisibility(View.GONE);
                        empty_show.setVisibility(View.VISIBLE);
                    }else{
                        refreshLayout.setVisibility(View.VISIBLE);
                        empty_show.setVisibility(View.GONE);
                        adapter.setData(items);
                    }

                    break;
                }

                case TRACE_LIST_COMMAND: { //追号订单列表
                    if (isBet) {
                        break;
                    }
                    if (response.getData() == null || !(response.getData() instanceof TraceListResponse)) {
                        items.clear();
                    } else {
                        totalCount = ((TraceListResponse) response.getData()).getTracesNumber();
                        if (page == FIRST_PAGE) {
                            items.clear();
                        }
                        items.addAll(((TraceListResponse) response.getData()).getTraces());
                    }
                    if(items.size()==0){
                        refreshLayout.setVisibility(View.GONE);
                        empty_show.setVisibility(View.VISIBLE);
                    }else{
                        refreshLayout.setVisibility(View.VISIBLE);
                        empty_show.setVisibility(View.GONE);
                        adapter.setData(items);
                    }
                    break;
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if ((request.getId() == BET_LIST_COMMAND && isBet) || (request.getId() == TRACE_LIST_COMMAND &&
                    !isBet)) {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
                //解决由于条件切换 没有合适的数据 要清空listview数据
//                items.clear();
//                adapter.setData(items);
            }
        }
    };

}