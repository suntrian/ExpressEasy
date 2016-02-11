package com.gulum.expresseasy.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/2/6.
 */
public class Config
{
    private Context mContext;

    private int runTimes;
    private int inputType;
    private int checkUpdateFreq;
    private int smsType;
    private String smsTemplate;

    public static final String VERSIONURL = "";
    public static final String DBFILE = "expresseasy.db";


    public static final int FREQ_PER_WEEK = 7;
    public static final int FREQ_PER_MONTH = 30;
    public static final int FREQ_NEVER = 0;
    public static final int FREQ_PER_SEMIYEAR = 180;

    public static final int SMS_LOCAL = 0;
    public static final int SMS_SERVER = 1;

    public static final int INPUT_KEYBOARD = 0;
    public static final int INPUT_VOICE = 1;

    private final String [] itemNames = {"RUNTIMES","INPUTTYPE","UPDATEFREQ","SMSTYPE","SMSTEMPLATE"};
    SharedPreferences preferences;
    SharedPreferences.Editor prefEdit;
    public Config(final Context context){
        this.mContext = context;

        preferences = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        prefEdit = preferences.edit();

        runTimes = preferences.getInt(itemNames[0], 0);
        inputType = preferences.getInt(itemNames[1], 0);
        checkUpdateFreq = Integer.valueOf(preferences.getString(itemNames[2], "0"));
        smsType = Integer.valueOf(preferences.getString(itemNames[3],"0"));
        smsTemplate = preferences.getString(itemNames[4], "");
        incRunTimes();
    }

    public void incRunTimes(){
        setRunTimes(this.runTimes +1 );
    }

    public int getRunTimes()
    {
        return runTimes;
    }

    public void setRunTimes(int runTimes)
    {
        this.runTimes = runTimes;
        prefEdit.putInt(itemNames[0],runTimes);
        prefEdit.commit();
    }

    public int getInputType()
    {
        return inputType;
    }

    public void setInputType(int inputType)
    {
        this.inputType = inputType;
        prefEdit.putInt(itemNames[1],inputType);
        prefEdit.commit();
    }

    public int getCheckUpdateFreq()
    {
        return checkUpdateFreq;
    }

    public void setCheckUpdateFreq(int checkUpdateFreq)
    {
        this.checkUpdateFreq = checkUpdateFreq;
        prefEdit.putInt(itemNames[2],checkUpdateFreq);
        prefEdit.commit();
    }

    public int getSmsType()
    {
        return smsType;
    }

    public void setSmsType(int smsType)
    {
        this.smsType = smsType;
        prefEdit.putInt(itemNames[3],smsType);
        prefEdit.commit();
    }

    public String getSmsTemplate()
    {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate)
    {
        this.smsTemplate = smsTemplate;
        prefEdit.putString(itemNames[4],smsTemplate);
        prefEdit.commit();
    }

}
