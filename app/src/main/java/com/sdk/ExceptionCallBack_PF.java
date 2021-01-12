package com.sdk;

public class ExceptionCallBack_PF {
    /*
     * Callback function to receive alarm information
     * @param [IN] iUserID              	ID User login ID
     * @param [IN] iChannelID           	Channel number
     * @param [IN] iAlarmType          	Alarm type
     * @param [IN] tAlarmTime           	Alarm time
     * @param [IN] strName              	Alarm source name
     * @param [IN] iBufLen             	Length of structure for alarm information
     * @note*/
    public void exceptionCallBack(long iUserID, int iExceptionType, long iExceptionHandle) {

        if(iUserID == NetDEVSDK.lpUserID)
        {
            if(0x8001 == iExceptionType) {
                System.out.println("Android Report exception info , Exception Type :" + iExceptionType);
                AlarmCallBack_PF pfAlarmCallBack = new AlarmCallBack_PF();
                NetDEVSDK.NETDEV_Android_SetAlarmCallBack(NetDEVSDK.lpUserID, pfAlarmCallBack, 0);
            }
        }
    }
}
