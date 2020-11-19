package com.ishani.onelinepuzzle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ishani.onelinepuzzle.interfaces.LoginInterface;
import com.ishani.onelinepuzzle.interfaces.LoginListener;
import com.ishani.onelinepuzzle.interfaces.ReferralInterface;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

public class AndroidLauncher extends AndroidApplication {

	String link;

	FirebaseAuth mAuth;
	FirebaseUser user;

	String mInvitationUrl;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


// Initialize Firebase Auth
		mAuth = FirebaseAuth.getInstance();

		initGame();

		getReferralLink();

		FirebaseAnalytics.getInstance(this);

		/*FirebaseDynamicLinks.getInstance()
				.getDynamicLink(getIntent())
				.addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
					@Override
					public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
						// Get deep link from result (may be null if no link is found)
						Uri deepLink = null;
						if (pendingDynamicLinkData != null) {
							deepLink = pendingDynamicLinkData.getLink();
						}


						// Handle the deep link. For example, open the linked
						// content, or apply promotional credit to the user's
						// account.
						// ...

						// ...
					}
				})
				.addOnFailureListener(this, new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Log.w(TAG, "getDynamicLink:onFailure", e);
					}
				});*/

		//dynamic Link ends



	}

	public void initGame(){



		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarfishGame(/*new LoginInterface() {
			@Override
			public void loginAnonymously(LoginListener loginListener) {
				loginAnonymouslyz(loginListener);
			}
		}*/null, new ReferralInterface() {
			@Override
			public void setReferral() {
				setReferralzz();
			}

			@Override
			public void getReferral() {

			}
		}), config);
	}

	public void loginAnonymouslyz(final LoginListener loginListener){
		mAuth.signInAnonymously()
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInAnonymously:success");
							user = mAuth.getCurrentUser();
							//updateUI(user);
							//initGame();
							loginListener.isLoggedIn(true);
							//setReferral();


						} else {
							// If sign in fails, display a message to the user.
							Log.w(TAG, "signInAnonymously:failure", task.getException());
							Toast.makeText(AndroidLauncher.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							//updateUI(null);
							loginListener.isLoggedIn(false);
						}

						// ...
					}
				});
	}

	public void setReferralzz(){

		String uid = user.getUid();
		link = "https://onelinepuzzle.page.link/?invitedby=" + uid;

		FirebaseDynamicLinks.getInstance().createDynamicLink()
				.setLink(Uri.parse(link))
				.setDomainUriPrefix("https://onelinepuzzle.page.link")
				.setAndroidParameters(
						new DynamicLink.AndroidParameters.Builder("com.ishani.onelinepuzzle")
								.setMinimumVersion(125)
								.build())
				.setIosParameters(
						new DynamicLink.IosParameters.Builder("com.ishani.onelinepuzzle")
								.setAppStoreId("123456789")
								.setMinimumVersion("1.0.1")
								.build())
				.buildShortDynamicLink()
				.addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
					@Override
					public void onSuccess(ShortDynamicLink shortDynamicLink) {
						mInvitationUrl = shortDynamicLink.getShortLink().toString();

						System.out.println("ShortDynamicLink---->"+ shortDynamicLink.getShortLink());
						sendReferralLink();
						// ...
					}
				});
	}

	public void sendReferralLink(){
		String referrerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
		String subject = String.format("%s wants you to play MyExampleGame!", referrerName);
		String invitationLink = mInvitationUrl.toString();
		String msg = "Let's play MyExampleGame together! Use my referrer link: "
				+ invitationLink;
		String msgHtml = String.format("<p>Let's play MyExampleGame together! Use my "
				+ "<a href=\"%s\">referrer link</a>!</p>", invitationLink);

		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:")); // only email apps should handle this
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, msg);
		intent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}


	public void getReferralLink(){
		FirebaseDynamicLinks.getInstance()
				.getDynamicLink(getIntent())
				.addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
					@Override
					public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
						// Get deep link from result (may be null if no link is found)
						Uri deepLink = null;
						if (pendingDynamicLinkData != null) {
							deepLink = pendingDynamicLinkData.getLink();
						}
						//
						// If the user isn't signed in and the pending Dynamic Link is
						// an invitation, sign in the user anonymously, and record the
						// referrer's UID.
						//
						FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
						if (user == null
								&& deepLink != null
								&& deepLink.getBooleanQueryParameter("invitedby", false)) {
							String referrerUid = deepLink.getQueryParameter("invitedby");
							createAnonymousAccountWithReferrerInfo(referrerUid);
						}
					}
				});
	}

	private void createAnonymousAccountWithReferrerInfo(final String referrerUid) {
		FirebaseAuth.getInstance()
				.signInAnonymously()
				.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
					@Override
					public void onSuccess(AuthResult authResult) {
						// Keep track of the referrer in the RTDB. Database calls
						// will depend on the structure of your app's RTDB.
						FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
						DatabaseReference userRecord =
								FirebaseDatabase.getInstance().getReference()
										.child("users")
										.child(user.getUid());
						userRecord.child("referred_by").setValue(referrerUid);
						System.out.println("Anonymous Account created with Refferrer info");
					}
				});
	}
}
