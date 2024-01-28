package com.example.crashcart;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Mainmenu extends Activity implements OnClickListener, StateBase {
    //WE have 2 buttons. Start button and Back button
    // Start button, qhen press will go to another page.. maybe gamepage(next week)
    //back button, when press wil go back to splashpage (for now)

    //define buttons. buttons are objects.
    private ImageButton btn_start; //int a

    private ImageButton btn_shop; //int a
    private ImageButton btn_lead; //int a


    //FACEBOOK
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL = "email";

    private LoginButton btn_fbLogin;

    private ShareDialog share_Dialog;

    private int PICK_IMAGE_REQUEST = 1;

    ProfilePictureView profile_pic;


    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if(ShareDialog.canShow(SharePhotoContent.class)){
            System.out.println("photoShown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    //.setCaption("Take a look at my new score of " + GameSystem.Instance.GetIntFromSave("Score"))
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            share_Dialog.show(content);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle SaveInstanceState){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.crashcart",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
            
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }


        super.onCreate(SaveInstanceState);

        // FACEBOOK
        FacebookSdk.setApplicationId("227522383753330");
        FacebookSdk.isInitialized();

        setContentView(R.layout.mainmenu);
        //not sure if i need this to swicth to shop
        //setContentView(R.layout.shoppage);

        // FACEBOOK
        if(BuildConfig.DEBUG){
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        btn_fbLogin = (LoginButton) findViewById(R.id.fb_login_button);
        btn_fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        // FACEBOOK

        profile_pic = findViewById(R.id.picture);

        callbackManager = CallbackManager.Factory.create();

        share_Dialog = new ShareDialog(this);

//        if(ShareDialog.canShow(ShareLinkContent.class)){
//            ShareLinkContent linkContent - new ShareLinkContent.Builder()
//                    .setContentUrl(Uri.parse())
//        }
        share_Dialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>(){
            @Override
            public void onSuccess(Sharer.Result result) {
                shareScore();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e){}
        });

        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult){
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                loginResult.getAccessToken().getUserId();
            }
            @Override
            public void onCancel(){
                System.out.println("Login attempt cancelled.");
            }
            @Override
            public void onError(FacebookException e){
                System.out.println("Login attempt failed.");
            }
        });


        //NEVER import R *
        //when android studio tells you to import R, DO NOT do it
        //whenm u see this message, it will mean xml has an error.

        btn_start = (ImageButton) findViewById(R.id.btn_ride);
        btn_start.setOnClickListener(this);

        btn_shop = (ImageButton) findViewById(R.id.btn_shop);
        btn_shop.setOnClickListener(this);

        btn_lead = (ImageButton) findViewById(R.id.btn_leaderboard);
        btn_lead.setOnClickListener(this);

        // this allows the correct button to be assigned to the object name and
        // for this case button
        // setonclicklistener to the specified button so that we know
        // when the specific button is clicked / touch
        // it knows what to do

        StateManager.Instance.AddState(new Mainmenu());

//        //not sure if i need this to swicth to shop
//        StateManager.Instance.AddState(new ShopPage());
    }


    @Override
    //Invoke a callback method event in the view
    public void onClick(View v){
        //Intent = action to be perofrmed
        //intent is an object provides runtime binding
        //you have to create a new instance of this object to use it
        //we need to check if start or back button is clicked / touched on the screen
        // then after, decide what to do
        // if start button, it will go to another page example gamepage

        Intent intent = new Intent();

        if (v == btn_start){
            //intent -> to set to another class which is another page or screen to be launch.
            //Equal to change screen
            intent.setClass(this,GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Press Start button
            //Goes to the MainGameSceneState hence thats why need to set class to Game
        }
        if (v == btn_lead){
            intent.setClass(this,LeadPage.class);
            StateManager.Instance.ChangeState("LeadPage"); // Press shop button
        }
        if (v == btn_shop){

            //change GamePage.class to ShopPage
            intent.setClass(this,ShopPage.class);
            StateManager.Instance.ChangeState("ShopPage"); // Press shop button

        }

//        // exit button
//        else if (v == btn_quit){
//            this.finishAffinity();
//        }
        startActivity(intent);
    }

    //Other 3 methods to be written here based on the Android activity lifecycle
    @Override
    protected void onPause(){super.onPause();}

    @Override
    protected void onStop(){super.onStop();}

    @Override
    protected void onDestroy(){super.onDestroy();}

    @Override
    public String GetName(){ // State name for the Main menu
        return "Mainmenu";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {

    }

    @Override
    public void Update(float _dt) {

    }





}
