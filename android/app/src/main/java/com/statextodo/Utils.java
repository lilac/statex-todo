package com.statextodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

/**
 * @author Junjun Deng 2016
 */
public class Utils
{
	public static void sendEvent(@NonNull ReactInstanceManager manager,
								 String eventName,
								 @NonNull Bundle bundle)
	{
		WritableMap map = Arguments.fromBundle(bundle);
		sendEvent(manager, eventName, map);
	}

	public static void sendEvent(@NonNull ReactInstanceManager manager,
								 @NonNull String eventName,
								 @Nullable WritableMap params)
	{
		ReactContext reactContext = manager.getCurrentReactContext();
		if(reactContext != null)
		{
			reactContext.getJSModule(RCTNativeAppEventEmitter.class)
				.emit(eventName, params);
		}
	}
}
