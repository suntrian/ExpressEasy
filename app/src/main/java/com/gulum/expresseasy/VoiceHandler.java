package com.gulum.expresseasy;

import android.content.Context;
import android.widget.TextView;

import com.baidu.voicerecognition.android.Candidate;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;

import java.util.List;

/**
 * Created by Administrator on 2016/1/31.
 */
public class VoiceHandler
{
    private Context mContext;

    private VoiceRecognitionClient mASREngine;
    private VoiceRecognitionConfig voiceConfig;
    private boolean isRecognition = false;
    private MyVoiceRecognitionListener mListener;
    private TextView mView;

    private static final String API_KEY = "qL44qiF5z9Qs1EzXrnGmUtnV";
    private static final String SECRET_KEY = "o872OvQI4OYUU256DthzfqQDFdgkNS9E";

    public VoiceHandler(Context context,TextView view)
    {
        this.mView = view;
        this.mContext = context;
        this.mASREngine = VoiceRecognitionClient.getInstance(mContext);
        mASREngine.setTokenApis(API_KEY,SECRET_KEY);
        voiceConfig = new VoiceRecognitionConfig();
        voiceConfig.setLanguage(VoiceRecognitionConfig.LANGUAGE_CHINESE);
        voiceConfig.setProp(VoiceRecognitionConfig.PROP_PHONE);
        voiceConfig.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K);
        voiceConfig.setUseDefaultAudioSource(true);

        mListener = new MyVoiceRecognitionListener();


    }

    public int startVoiceRecognition(){
        int code =  mASREngine.startVoiceRecognition(mListener,voiceConfig);
        switch (code){
            case VoiceRecognitionClient.START_WORK_RESULT_WORKING:
                break;
            case VoiceRecognitionClient.START_WORK_RESULT_RECOGNITING:
                break;
            default:
                break;
        }
        return code;
    }
    public void stopVoiceRecognition(){
        mASREngine.stopVoiceRecognition();
    }
    public void finishSpeech(){
        mASREngine.speakFinish();
    }

    private void updateRecogitionResult(Object result){
        if(result != null && result instanceof List){
            List results = (List)result;
            if(results.size() > 0 ){
                if(results.get(0) instanceof List){
                    List<List<Candidate>> sentences = (List<List<Candidate>>) result;
                    StringBuffer strBuffer = new StringBuffer();
                    for (List<Candidate> candidates : sentences){
                        if (candidates != null && candidates.size() > 0){
                            strBuffer.append(candidates.get(0).getWord());
                        }
                    }
                    mView.setText(strBuffer);
                }else {
                    mView.setText(results.get(0).toString());
                }

            }
        }
    }

    class MyVoiceRecognitionListener implements VoiceRecognitionClient.VoiceClientStatusChangeListener{
        @Override
        public void onError(int errorType, int errorCode)
        {
            switch (errorType){
                case VoiceRecognitionClient.ERROR_SERVER:
                    break;
                case VoiceRecognitionClient.ERROR_NETWORK:
                    break;
            }
        }

        @Override
        public void onNetworkStatusChange(int status, Object obj)
        {
            //do nothing
        }

        @Override
        public void onClientStatusChange(int status, Object obj)
        {
            switch (status){
                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                    isRecognition = true;
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    isRecognition = false;
                    updateRecogitionResult(obj);
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    updateRecogitionResult(obj);
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
                    isRecognition = false;
                    break;
                default:
                    break;
            }
        }
    }
}
