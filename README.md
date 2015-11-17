# AirSDK使用说明

-------------------
## 使用环境
### 引入aar库
将`airsdk-release.aar`库文件，放入工程`libs`目录。
在项目`build.gradle`中增加以下内容：
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile(name: 'airsdk-release', ext: 'aar')
}
```

### 增加权限
在`AndroidManifest.xml`中增加如下权限：
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```



## 接口调用
### 初始化
在Application类中初始化库文件：
```
Airimos.init(getApplicationContext());
```

### 用户登录、登出
#### 登录
```
public static void login(String deviceServiceAddr, int deviceServicePort, String username, String password, final com.uniview.airimos.airsdk.listener.OnLoginListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| deviceServiceAddr | 设备IP地址 | 
| deviceServicePort | 设备端口，默认情况下为80，如有映射即为映射到设备80端口的端口 |
| username | 设备登录名 |
| listener| 登录结果回调 OnLoginListener |

``` 
public interface OnLoginListener
{
    public void onLoginFailed(ErrorInfo error);
    public void onLoginComplete(String session, List<ChannelInfo> channels);
} 
```
> 登录成功后，回调得到设备session和通道列表



#### 登出
```
public static void logout(String deviceSession, final com.uniview.airimos.airsdk.listener.OnLogoutListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| deviceSession | 登录返回的session | 
| listener| OnLogoutListener 登出结果回调 |


### 实况
#### 启动
```
public static void playLive(final String session, String channel, final OnPlayListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| channel | 通道编码 | 
| listener| OnPlayListener 启动结果回调 |

#### 停止
```
 public static void stopPlayLive(String session, String playSession, final OnStopListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| playSession| 启动时得到的播放session | 
| listener| OnStopListener 停止结果回调 |



### 回放
#### 查询
```
public static void queryRecords(String session, String channel, String beginTime, String endTime, final QueryRecordListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| channel | 通道编码 | 
| beginTime| 查询起始时间，格式 "yyyy-mm-dd hh:mm:ss" | 
| endTime |  查询结束时间，格式 "yyyy-mm-dd hh:mm:ss" | 
| listener| QueryRecordListener 查询结果回调 |

>注：起始时间与结束时间需在一天内，最大为"00:00:00"到"23:59:59"


#### 启动
```
public static void playRecord(final String session, String channel, RecordInfo record, String time, final OnPlayListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| channel | 通道编码 | 
| record| 需要播放的回访记录项 | 
| time | 需要播放的时间， 请确保该时间在record的范围内 | 
| listener| OnPlayListener 启动结果回调 |

#### 停止
```
public static void stopPlayRecord(String session, String playSession, final OnStopListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| playSession| 启动时得到的播放session | 
| listener| OnStopListener 停止结果回调 |



### 云台
```
public static void ptzCtrl(String session, String channel, int ptzCommand, final PtzCtrlListener listener)
```

| 参数      |    说明  |
| :-------- | :--------|
| session | 登录返回的session | 
| channel| 通道编码 | 
| ptzCommand| 云台命令 | 
| listener| PtzCtrlListener 控制结果回调 |

云台命令：
```
   public static final class PTZ_CMD
   {
       /* 近聚焦停止 */
       public static final int FOCUS_NEAR_STOP = 0x0201;

       /* 近聚焦 */
       public static final int FOCUS_NEAR = 0x0202;

       /* 远聚焦停止 */
       public static final int FOCUS_FAR_STOP = 0x0203;

       /* 远聚焦 */
       public static final int FOCUS_FAR = 0x0204;

       /* 放大停止 */
       public static final int ZOOM_TELE_STOP = 0x0301;

       /* 放大 */
       public static final int ZOOM_TELE = 0x0302;

       /* 缩小停止 */
       public static final int ZOOM_WIDE_STOP = 0x0303;

       /* 缩小 */
       public static final int ZOOM_WIDE = 0x0304;

       /* 向上停止 */
       public static final int UP_STOP = 0x0401;

       /* 向上 */
       public static final int UP = 0x0402;

       /* 向下停止 */
       public static final int DOWN_STOP = 0x0403;

       /* 向下 */
       public static final int DOWN = 0x0404;

       /* 向右停止 */
       public static final int RIGHT_STOP = 0x0501;

       /* 向右 */
       public static final int RIGHT = 0x0502;

       /* 向左停止 */
       public static final int LEFT_STOP = 0x0503;

       /* 向左 */
       public static final int LEFT = 0x0504;

       /* 左上停止 */
       public static final int LEFT_UP_STOP = 0x0701;

       /* 左上 */
       public static final int LEFT_UP = 0x0702;

       /* 左下停止 */
       public static final int LEFT_DOWN_STOP = 0x0703;

       /* 左下 */
       public static final int LEFT_DOWN = 0x0704;

       /* 右上停止 */
       public static final int RIGHT_UP_STOP = 0x0801;

       /* 右上 */
       public static final int RIGHT_UP = 0x0802;

       /* 右下停止 */
       public static final int RIGHT_DOWN_STOP = 0x0803;

       /* 右下 */
       public static final int RIGHT_DOWN = 0x0804;

       /* 全部停止 */
       public static final int ALL_STOP = 0x0901;
   }
```

##Demo
https://github.com/airimos/airsdk
