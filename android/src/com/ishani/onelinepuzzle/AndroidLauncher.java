package com.ishani.onelinepuzzle;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//added

		FirebaseInstanceId.getInstance().getInstanceId()
				.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
					@Override
					public void onComplete(@NonNull Task<InstanceIdResult> task) {
							if(task.isSuccessful()){
								String token = task.getResult().getToken();
								System.out.println("Token ------>" + token);
							}else{
								System.out.println(task.getException().getMessage());
							}
					}
				});


		//ends added

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarfishGame(), config);
	}
}
