package com.sciento.wumu.zhengjing.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.sciento.wumu.zhengjing.R;
import com.sciento.wumu.zhengjing.WholeActivity;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements SeekBar.OnSeekBarChangeListener , View.OnClickListener {

    private View view;

    private TextView tvTemperature;
    private TextView tvShock;
    private TextView tvTimer;

//    private SwitchCompat swBlu;
    private  ImageButton imgbtnBlu;
    private  TextView tvBleShow;
    private ImageView ivBattery;

    private SeekBar sbTemperature;
    private SeekBar sbShock;
    private SeekBar sbTimer;
    private int mTemperature ;
    private int mShock ;
    private int mTimer ;

    private ImageButton imgbtnAll;
    private boolean mAllEn = false;

    private BluetoothAdapter mBluetoothAdapter;
    private final String lockName = "zhengjing";
    private boolean isConnected = false;
    private static final UUID ZZR_UUID_BLE_SERVICE = UUID.fromString("0000FFF0-0000-1000-8000-00805f9b34fb");
    private static final UUID ZZR_UUID_BLE_CHAR = UUID.fromString("0000FFF2-0000-1000-8000-00805f9b34fb");
    private static final UUID ZZR_UUID_BLE_CHAR1 = UUID.fromString("0000FFF3-0000-1000-8000-00805f9b34fb");

    private final int MSG_GET_DATE = 0;

    TextView[] tvArrayRank = new TextView[7];

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        initEvent();

        BluetoothManager bluetoothManager = (BluetoothManager) getActivity()
                .getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        return view;
    }



    private void init() {
        tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        tvShock = (TextView) view.findViewById(R.id.tv_shock);
        tvTimer = (TextView) view.findViewById(R.id.tv_timer);
        ivBattery = (ImageView) view.findViewById(R.id.iv_dianliang);
        sbTemperature = (SeekBar) view.findViewById(R.id.sb_temperature);
        sbShock = (SeekBar) view.findViewById(R.id.sb_shock);
        sbTimer = (SeekBar) view.findViewById(R.id.sb_timer);
        imgbtnAll = (ImageButton)view.findViewById(R.id.imgbtn_all);
        sbTemperature.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
        sbShock.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
        sbTimer.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
        sbTemperature.getThumb().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
        sbShock.getThumb().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
        sbTimer.getThumb().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colormainpagebg), PorterDuff.Mode.SRC_IN);
//        swBlu = (SwitchCompat)view.findViewById(R.id.sw_blue);
        imgbtnBlu = (ImageButton)view.findViewById(R.id.imgbtn_blue);
        tvBleShow = (TextView)view.findViewById(R.id.tv_blushow) ;
        tvArrayRank[0] = (TextView)view.findViewById(R.id.tv_rank0);
        tvArrayRank[1] = (TextView)view.findViewById(R.id.tv_rank1);
        tvArrayRank[2] = (TextView)view.findViewById(R.id.tv_rank2);
        tvArrayRank[3] = (TextView)view.findViewById(R.id.tv_rank3);
        tvArrayRank[4] = (TextView)view.findViewById(R.id.tv_rank4);
        tvArrayRank[5] = (TextView)view.findViewById(R.id.tv_rank5);
        tvArrayRank[6] = (TextView)view.findViewById(R.id.tv_rank6);

    }

    private void initEvent() {
        sbTemperature.setOnSeekBarChangeListener(this);
        sbShock.setOnSeekBarChangeListener(this);
        sbTimer.setOnSeekBarChangeListener(this);
        imgbtnAll.setOnClickListener(this);
        imgbtnBlu.setOnClickListener(this);



    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_temperature:
                mTemperature = progress+40;
                break;
            case R.id.sb_shock:
                mShock = progress;
                reinState();
                tvArrayRank[mShock].setTextColor(Color.parseColor("#53D8D5"));
                break;
            case R.id.sb_timer:
                mTimer = progress+10;
                break;

        }
    }

    private void reinState() {
        for(int i =0; i<7;i++)
        {
            tvArrayRank[i].setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId())
        {
            case R.id.sb_temperature:


                updateStatus();
                sendInfo();
                break;
            case R.id.sb_shock:

                updateStatus();
                sendInfo();
                break;
            case R.id.sb_timer:


                updateStatus();
                tvTimer.setText(mTimer+"");
                sendInfo();
                break;
        }

    }

    private void sendInfo() {
        String  strSendInfo = "FE" + "08"
                + Integer.toHexString(mTemperature)
                + "0"+Integer.toHexString(mShock)
                +String.format("%02x",mTimer)
                + "0" + (mAllEn?1:0)
                + "00" +"00" + "00" + "00" + "00"+"00" + "EF";

        startWrite(ZZR_UUID_BLE_SERVICE.toString(), ZZR_UUID_BLE_CHAR.toString(), strSendInfo);
    }

    private void updateStatus()
    {
        if(mTimer < 13)
            mTimer = 10;
        else if(mTimer <18)
            mTimer = 15;
        else if(mTimer < 23)
            mTimer = 20;
        else if(mTimer < 28)
            mTimer = 25;
        else mTimer = 30;


        if(mTemperature < 43)
            mTemperature = 40;
        else if(mTemperature <48)
            mTemperature = 45;
        else if(mTemperature < 53)
            mTemperature = 50;
        else if(mTemperature < 58)
            mTemperature = 55;
        else mTemperature = 60;

        sbTemperature.setProgress(mTemperature-40);
        sbTimer.setProgress(mTimer-10);
        sbShock.setProgress(mShock);

        tvTemperature.setText(mTemperature+"°");
        tvShock.setText("F"+mShock);
        //tvTimer.setText(mTimer+"");




    }


    @Override
    public void onResume() {
        super.onResume();
        if(isConnected)
        {
           imgbtnBlu.setBackgroundResource(R.drawable.btn_blu_press);
            tvBleShow.setText(R.string.title_bluen);

        }
        if(mAllEn)
        {
            imgbtnAll.setBackgroundResource(R.drawable.btn_allswitch_press);
        }
    }

    private void connectNameDevice(final String deviceName) {

        WholeActivity.bleManager.scanNameAndConnect(deviceName, 20000, false, new BleGattCallback() {
            @Override
            public void onNotFoundDevice() {
                getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        tvBleShow.setText(R.string.title_bludis);
                        Toast.makeText(getActivity(), "没有发现设备", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onFoundDevice(BluetoothDevice device) {
                getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "发现设备", Toast.LENGTH_LONG).show();
                    }
                });
            }



            @Override
            public void onConnectSuccess(final BluetoothGatt gatt, int status) {

                gatt.discoverServices();
            }


            @Override
            public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
                //查找使用的uuid
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initConnect(lockName, gatt);
                        isConnected = true;
                        imgbtnBlu.setBackgroundResource(R.drawable.btn_blu_press);
                        tvBleShow.setText(R.string.title_bluen);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                startWrite(ZZR_UUID_BLE_SERVICE.toString(), ZZR_UUID_BLE_CHAR.toString(),
//                                                "FE0801330000000000000002EF");
//
//                                }
//
//
//                        }, 500);

                    }
                });

            }
            @Override
            public void onConnectFailure(BleException exception) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isConnected = false;
                        imgbtnBlu.setBackgroundResource(R.drawable.btn_blu_nor);
                        tvBleShow.setText(R.string.title_bludis);
                        WholeActivity.bleManager.closeBluetoothGatt();
                    }
                });
                WholeActivity.bleManager.handleException(exception);
            }


        });
    }
    //初始化
    private void initConnect(String deviceName, BluetoothGatt gatt) {
        WholeActivity.bleManager.getBluetoothState();
        if (gatt != null) {
            for (final BluetoothGattService service : gatt.getServices()) {
                for (final BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    ////识别uuid
                    if (characteristic.getUuid().equals(ZZR_UUID_BLE_CHAR1)) {
                        //开启接收notify
                        startNotify(service.getUuid().toString(), characteristic.getUuid().toString());

                    }
                }
            }
        }
    }
    //读蓝牙函数
    private void startNotify(String serviceUUID, final String characterUUID) {
        Log.i(TAG, "startNotify");
        boolean suc = WholeActivity.bleManager.notify(
                serviceUUID,
                characterUUID,
                new BleCharacterCallback() {
                    @Override
                    public void onSuccess(final BluetoothGattCharacteristic characteristic) {
                        Log.d(TAG, "notify success： " + '\n' + String.valueOf(HexUtil.encodeHex(characteristic.getValue())));
                        byte[] m_byte = characteristic.getValue();
                        if(m_byte.length == 13) {
                            Message message = Message.obtain();
                            message.what = MSG_GET_DATE;
                            Bundle dateBundle = new Bundle();
                            dateBundle.putByteArray("datearray", m_byte);
                            message.setData(dateBundle);
                            myHandler.sendMessage(message);
                        }


                    }

                    @Override
                    public void onFailure(BleException exception) {

                        WholeActivity.bleManager.handleException(exception);
                    }
                });

    }

    //写蓝牙函数
    private void startWrite(String serviceUUID, final String characterUUID, String writeData) {

        Log.i(TAG, "startWrite");
        boolean suc = WholeActivity.bleManager.writeDevice(
                serviceUUID,
                characterUUID,
                HexUtil.hexStringToBytes(writeData),
                new BleCharacterCallback() {
                    @Override
                    public void onSuccess(final BluetoothGattCharacteristic characteristic) {
                        Log.d(TAG, "write success: " + '\n' + String.valueOf(HexUtil.encodeHex(characteristic.getValue())));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onFailure(BleException exception) {
                        WholeActivity.bleManager.handleException(exception);
                    }
                });

        if (suc) {
            WholeActivity.bleManager.stopListenCharacterCallback(ZZR_UUID_BLE_CHAR1.toString());
            startNotify(serviceUUID.toString(), ZZR_UUID_BLE_CHAR1.toString());
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgbtn_all:

                if(!mAllEn)
                {
                    mAllEn = true;
                    sendInfo();
                    imgbtnAll.setBackgroundResource(R.drawable.btn_allswitch_press);
                }
                else
                {
                    mAllEn = false;
                    sendInfo();
                    imgbtnAll.setBackgroundResource(R.drawable.btn_allswitch_nor);
                }

                break;

            case R.id.imgbtn_blue:
                if(!isConnected)
                {
                    connectNameDevice(lockName);
                    tvBleShow.setText(R.string.title_bleing);
                }
        }

    }

    private Handler myHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_GET_DATE://对获取的数据进行处理
                    Bundle myBundle = msg.getData();
                    byte[] mbyte = myBundle.getByteArray("datearray");
//                    tvTemperature.setText(mbyte[2]+"°");
//                    tvShock.setText("F"+mbyte[3]);
//                    tvTimer.setText(mbyte[4]+"");
//
//                    sbTemperature.setProgress(mbyte[2]-40);
//                    sbShock.setProgress(mbyte[3]);
//                    sbTimer.setProgress(mbyte[4]-10);
                    mTemperature = mbyte[2];
                    mShock = mbyte[3];
                    mTimer = mbyte[4];
                    tvTimer.setText(mbyte[6]+"");
                    switch (mbyte[7])
                    {
                        case 1 :
                            imgbtnAll.setBackgroundResource(R.drawable.btn_allswitch_press);
                            mAllEn = true;
                            break;
                        case  0:
                            imgbtnAll.setBackgroundResource(R.drawable.btn_allswitch_nor);
                            mAllEn = false;
                            break;
                    }

                    updateStatus();
                    //update battery
                    updateBattery(mbyte[5]);

            }
            //super.handleMessage(msg);
        }
    };

    private void updateBattery(byte mbattery) {
        if (mbattery >= 0 && mbattery <= 100) {
            if (mbattery > 80)
                ivBattery.setImageResource(R.drawable.im_dianliang100);
            else if (mbattery > 60)
                ivBattery.setImageResource(R.drawable.im_dianliang80);
            else if (mbattery > 40)
                ivBattery.setImageResource(R.drawable.im_dianliang40);
            else if (mbattery > 20)
                ivBattery.setImageResource(R.drawable.im_dianliang40);
            else if (mbattery > 10)
                ivBattery.setImageResource(R.drawable.im_dianliang20);
            else if (mbattery >= 0)
                ivBattery.setImageResource(R.drawable.im_dianliang10);

        }
        else
        {
            Toast.makeText(getActivity(), "发送的数据格式不对", Toast.LENGTH_LONG).show();
        }
    }




}
