package com.asus.zenbodialogsample;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

public class zenbo_welcome extends RobotActivity {

    public final static String TAG = "ZenboDialogSample";
    public final static String DOMAIN = "13207(test)";

    private static TextView mTextView;
    private String _sentence = "我想為你提出一些理財建議，你願意嗎";

    // to make sure if user has answered or not
    private boolean _answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_welcome);

        //mTextView = (TextView) findViewById(R.id.textView_introduction);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "lanuchHelloWolrd_Plan");

        // listen user utterance
        robotAPI.robot.speakAndListen(_sentence, new SpeakConfig().timeout(20));

        // show hint
        mTextView.setText(getResources().getString(R.string.dialog_example));

    }


    @Override
    protected void onPause() {
        super.onPause();

        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static RobotCallback robotCallback = new RobotCallback() {
        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);
        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
        }
    };


    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {
            String text;
            text = "onEventUserUtterance: " + jsonObject.toString();
            Log.d(TAG, text);
        }

        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);


            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("helloWorld")) {
                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "myCity1", null);
                Log.d(TAG, "Result City = " + sSluResultCity);

                if(sSluResultCity!= null) {
                    //mTextView.setText("You are now at " + sSluResultCity);
                }
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };


    public zenbo_welcome() {
        super(robotCallback, robotListenCallback);
    }
}
