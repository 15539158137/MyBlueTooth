package react.com.mybluetooth;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    //对于蓝牙连接所需的操作
    BluetoothDevice mBluetoothDevice;
    BluetoothGattCallback mBluetoothGattCallback;
    BluetoothGatt mBluetoothGatt;
    BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    BluetoothGattService bluetoothGattService;
//关于名字和类型的展示
    TextView name;
    TextView type;
    BluetoothLeScanner bluetoothLeScanner;
    android.os.Handler mhandle;
    String isSearch;
    //蓝牙扫描适配器
    BluetoothAdapter myBluetoothAdapter;
    //蓝牙扫描后的回执
    BluetoothAdapter.LeScanCallback mBluetoothScanCallback;
    //搜索到的蓝牙设备
    List<BluetoothDevice> mBluetoothDeviceList;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isSearch = "0";
        mhandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        name= (TextView) findViewById(R.id.name);
        type= (TextView) findViewById(R.id.type);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBluetoothPermissionAndSearch();
            }
        });
findViewById(R.id.stopbutton).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(bluetoothLeScanner!=null){
            bluetoothLeScanner.stopScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                }
            });
        }
        if(myBluetoothAdapter!=null){
            myBluetoothAdapter.stopLeScan(mBluetoothScanCallback);
        }
       if(mBluetoothGatt!=null){
           mBluetoothGatt.disconnect();
       }
       name.setText("卡片ID：未连接");
        type.setText("卡片类型：未连接");
//让线程中断
        canRun=false;
        isSearch="0";
        progressBar.setVisibility(View.GONE);
    }
});
//连接的callback
        mBluetoothGattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                Log.e("连接撞他发生改变", "--------");
                if (newState == 2) {
                    Log.e("连接撞他发生改变", "---连接上-----");
                    mBluetoothGatt = gatt;//拿到GATT服务器
                    mBluetoothGatt.discoverServices();

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.e("连接撞他发生改变", "---没有连接上-----");

                } else {
                    Log.e("连接撞他发生改变", "---其他连接异常-----");

                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                //0000fff0-0000-1000-8000-00805f9b34fb
                //0000fff1-0000-1000-8000-00805f9b34fb
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    List<BluetoothGattService> listForAllService = mBluetoothGatt.getServices();
                    for (BluetoothGattService service : listForAllService) {
                        Log.e("一个服务", service.getUuid().toString());
                        if (service.getUuid().toString().equals("0000fff0-0000-1000-8000-00805f9b34fb")) {
                            Log.e("找到这个服务了", "=====");
                            bluetoothGattService =service;
                            List<BluetoothGattCharacteristic> listForCharacters = service.getCharacteristics();
                            for (BluetoothGattCharacteristic characteristic : listForCharacters) {
                                if (characteristic.getUuid().toString().equals("0000fff1-0000-1000-8000-00805f9b34fb")) {
                                    Log.e("找到这个属性了", "=====");
                                    mBluetoothGattCharacteristic = characteristic;
                                    boolean s = mBluetoothGatt.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
                                    Log.e("设置通知", s + "===");
                                    BluetoothGattDescriptor descriptor = mBluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                                    ;
                                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    //  mBluetoothGatt.writeDescriptor(descriptor);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(MainActivity.this,"连接完成，请刷卡!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            writeData();
                                        }
                                    });

                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (status == 0) {
                    Log.e("写入成功", "写入成功");

                } else {
                    Log.e("写入失败", "写入失败");
                }
                super.onCharacteristicWrite(gatt, characteristic, status);

            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                Log.e("监听到蓝牙数值变化", "==================");
                byte[] receiveDateBytes = characteristic.getValue();
                final String receiveDateString = MyUtils.bytesToHexString(receiveDateBytes);
                Log.e("收到的数据", receiveDateString + "===");
                if(receiveDateString.startsWith("aa0501")){
                    Log.e("获取到卡片的UID",receiveDateString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //ab ac ad ae
                            //需要转化为ae ad ac ab
                            //不足10位就补0
                            String origin=receiveDateString.substring(6,receiveDateString.length());
                            String one=origin.substring(0,2);
                            String two=origin.substring(2,4);
                            String three=origin.substring(4,6);
                            String four=origin.substring(6,8);
                            String total=four+three+two+one;
                            Log.e("得到的16进制",total);
                         BigInteger id=  new BigInteger(total,16);
                          //  name.setText("卡片ID:"+receiveDateString.substring(6,receiveDateString.length()));
                            name.setText("卡片ID:"+id);
                        }
                    });

                }
                if(receiveDateString.startsWith("aa0202")){
                    Log.e("获取到卡片的类型",receiveDateString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(receiveDateString.equals("aa020201")){
                                type.setText("卡片类型: M1卡");
                            }else if(receiveDateString.equals("aa020202")){
                                type.setText("卡片类型: UL卡");
                            }else if(receiveDateString.equals("aa020202")){
                                type.setText("卡片类型: ISO14443-B卡");
                            }else {
                                type.setText("卡片类型: ACPU卡");
                            }

                        }
                    });

                }
            }

            @Override
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorRead(gatt, descriptor, status);
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                super.onReadRemoteRssi(gatt, rssi, status);
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                super.onMtuChanged(gatt, mtu, status);
            }
        };
        //蓝牙搜索到设备的绘制
        mBluetoothScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                Log.e("搜索到一个蓝牙", bluetoothDevice.getName() + "==" + bluetoothDevice.getAddress());
                //这里需要加入对蓝牙产品的判断
                if (bluetoothDevice.getName().equals("BLE_NFC")) {

                    mBluetoothDevice = bluetoothDevice;
                    myBluetoothAdapter.stopLeScan(mBluetoothScanCallback);
                    isSearch = "0";
                    //然后开始去查看服务
                    mBluetoothDevice.connectGatt(MainActivity.this, true, mBluetoothGattCallback);
                    Log.e("开始建立GATT", "==========");

                    if (mBluetoothDeviceList.size() == 0) {
                        mBluetoothDeviceList.add(bluetoothDevice);

                    } else {
                        if (mBluetoothDeviceList.contains(bluetoothDevice)) {
//0216000001000101017e000001000101020304050607
                        } else {
                            mBluetoothDeviceList.add(bluetoothDevice);

                        }
                    }
                } else {
                }

            }
        };
    }

    //获取6.0权限
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void checkBluetoothPermissionAndSearch() {
        myBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
        } else {
            if (isSearch.equals("1")) {
                Toast.makeText(getApplicationContext(), "正在搜索，请稍等...", Toast.LENGTH_SHORT).show();
            } else {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 200);

                    } else {
                        Toast.makeText(getApplicationContext(), "have permiss", Toast.LENGTH_LONG).show();
                        scanAfter5();
                        isSearch = "1";
                        mBluetoothDeviceList = new ArrayList<>();
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= 21) {
                        scanAfter5();
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        myBluetoothAdapter.startLeScan(mBluetoothScanCallback);
                    }
                    mBluetoothDeviceList = new ArrayList<>();
                    isSearch = "1";
                }
            }
        }
    }

    //5.0以上的扫描方法
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scanAfter5() {
        progressBar.setVisibility(View.VISIBLE);
        //bluetoothscan
        bluetoothLeScanner = myBluetoothAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result)
                ;
                BluetoothDevice bluetoothDevice = result.getDevice();
                Log.e("搜索到一个蓝牙5.0之后", bluetoothDevice.getName() + "==" + bluetoothDevice.getAddress());
                //这里需要加入对蓝牙产品的判断
                if (bluetoothDevice.getName().equals("BLE_NFC")) {

                    mBluetoothDevice = bluetoothDevice;
                bluetoothLeScanner.stopScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                    }
                });
                    isSearch = "0";
                    //然后开始去查看服务
                    mBluetoothDevice.connectGatt(MainActivity.this, true, mBluetoothGattCallback);
                    Log.e("开始建立GATT", "==========");

                    if (mBluetoothDeviceList.size() == 0) {
                        mBluetoothDeviceList.add(bluetoothDevice);

                    } else {
                        if (mBluetoothDeviceList.contains(bluetoothDevice)) {
//0216000001000101017e000001000101020304050607
                        } else {
                            mBluetoothDeviceList.add(bluetoothDevice);

                        }
                    }
                } else {
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("收到蓝牙打开的绘制", "收到蓝牙打开的绘制");
        checkBluetoothPermissionAndSearch();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        myBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        scanAfter5();
        isSearch = "1";
        mBluetoothDeviceList = new ArrayList<>();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    Thread thread;
    boolean canRun;
    private void writeData() {
        canRun=true;
         thread=
        new Thread() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                while (canRun) {
                    Log.e("开始写入", "=====");
                    //先去获取卡的类型
                    byte[] getType = {(byte) 0xAA, 0x01, 0x02};
                    mBluetoothGattCharacteristic.setValue(getType);
                    mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //这个指令是获取卡的uid
                    byte[] data = {(byte) 0xAA, 0x01, 0x01};
                    mBluetoothGattCharacteristic.setValue(data);
                    mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        thread.start();
    }
}
