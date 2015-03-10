package com.uniview.airimos.airdemo;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uniview.airimos.airsdk.Airimos;
import com.uniview.airimos.airsdk.bean.ChannelInfo;
import com.uniview.airimos.airsdk.bean.ErrorInfo;
import com.uniview.airimos.airsdk.bean.RecordInfo;
import com.uniview.airimos.airsdk.def.Constants;
import com.uniview.airimos.airsdk.listener.OnLoginListener;
import com.uniview.airimos.airsdk.listener.OnPlayListener;
import com.uniview.airimos.airsdk.listener.QueryRecordListener;
import com.uniview.airimos.airsdk.widget.PlayView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
{
    private PlayView mPlayView;

    private String mDeviceSession;
    private List<ChannelInfo> mDeviceChannels;

    private String mPlaySession;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayView = (PlayView) findViewById(R.id.playview);

        final EditText inputIp = (EditText) findViewById(R.id.input_ip);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Airimos.login(inputIp.getText().toString(), 80, "admin", "admin", new OnLoginListener()
                {
                    @Override
                    public void onLoginFailed(ErrorInfo error)
                    {
                        Toast.makeText(MainActivity.this, "登录失败，" + error.getErrorDesc(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoginComplete(String session, List<ChannelInfo> channels)
                    {
                        mDeviceSession = session;

                        mDeviceChannels = new ArrayList<ChannelInfo>(channels);

                        Toast.makeText(MainActivity.this, "登录成功，" + mDeviceSession, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button btnPlay = (Button) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Airimos.playLive(mDeviceSession, mDeviceChannels.get(0).getResCode(), new OnPlayListener()
                {
                    @Override
                    public void onPlayFailed(ErrorInfo error)
                    {
                    }

                    @Override
                    public void onPlayComplete(String playSession)
                    {
                        Log.d("111", playSession);

                        mPlayView.play(playSession);

                        mPlaySession = playSession;
                    }
                });
            }
        });

        Button btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPlayView.stop();
                Airimos.stopPlayLive(mDeviceSession, mPlaySession, null);
            }
        });


        final Button left = (Button) findViewById(R.id.btn_ptz_left);
        left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (left.getText().equals("停"))
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.LEFT_STOP, null);
                    left.setText("左");
                }
                else
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.LEFT, null);
                    left.setText("停");
                }
            }
        });

        final Button right = (Button) findViewById(R.id.btn_ptz_right);
        right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (right.getText().equals("停"))
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.RIGHT_STOP, null);
                    right.setText("右");
                }
                else
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.RIGHT, null);
                    right.setText("停");
                }
            }
        });


        final Button up = (Button) findViewById(R.id.btn_ptz_up);
        up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (up.getText().equals("停"))
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.UP_STOP, null);
                    up.setText("上");
                }
                else
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.UP, null);
                    up.setText("停");
                }
            }
        });

        final Button down = (Button) findViewById(R.id.btn_ptz_down);
        down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (down.getText().equals("停"))
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.DOWN_STOP, null);
                    down.setText("下");
                }
                else
                {
                    Airimos.ptzCtrl(mDeviceSession, mDeviceChannels.get(0).getResCode(), Constants.PTZ_CMD.DOWN, null);
                    down.setText("停");
                }
            }
        });

        final Button btnQueryRecord = (Button) findViewById(R.id.btn_query_record);
        btnQueryRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Airimos.queryRecords(mDeviceSession, mDeviceChannels.get(0).getResCode(), "2015-03-06 00:00:00", "2015-03-06 23:59:59", new QueryRecordListener()
                {
                    @Override
                    public void onQueryFailed(ErrorInfo error)
                    {

                    }

                    @Override
                    public void onQueryComplete(List<RecordInfo> records)
                    {
                        Log.d("123", "onQueryComplete:" + records.toString());

                        Airimos.playRecord(mDeviceSession, mDeviceChannels.get(0).getResCode(), records.get(0), "2015-03-06 08:00:00", new OnPlayListener()
                        {
                            @Override
                            public void onPlayFailed(ErrorInfo error)
                            {

                            }

                            @Override
                            public void onPlayComplete(String playSession)
                            {
                                mPlayView.play(playSession);

                                mPlaySession = playSession;
                            }
                        });
                    }
                });
            }
        });

        final Button btnStopPlayRecord = (Button) findViewById(R.id.btn_stop_play_record);
        btnStopPlayRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPlayView.stop();
                Airimos.stopPlayRecord(mDeviceSession, mPlaySession, null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
