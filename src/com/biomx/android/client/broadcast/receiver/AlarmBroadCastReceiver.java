/**
 * 
 */
package com.biomx.android.client.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Raghu
 * 
 */
public class AlarmBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/*for (ConnectionService connectionService : DataHolder.connectionServices
				.values()) {
			try {
				if(connectionService.isConnected()){
					connectionService.sendCommand(BioMXCommand.GET_BATTQ);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

}
