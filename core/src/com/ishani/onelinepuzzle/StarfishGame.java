package com.ishani.onelinepuzzle;

import com.ishani.onelinepuzzle.interfaces.LoginInterface;
import com.ishani.onelinepuzzle.interfaces.LoginListener;
import com.ishani.onelinepuzzle.interfaces.ReferralInterface;
import com.ishani.onelinepuzzle.screen.MenuScreen;

public class StarfishGame extends BaseGame {

    public static ReferralInterface referralInterface;
    public static LoginInterface loginInterface;

    public StarfishGame(LoginInterface loginInterface, ReferralInterface referralInterface){
        this.loginInterface = loginInterface;
        this.referralInterface = referralInterface;

    }

    @Override
    public void create() {

        setActiveScreen(new MenuScreen());
        if(loginInterface !=null){
        loginInterface.loginAnonymously(new LoginListener() {
            @Override
            public void isLoggedIn(boolean isLogin) {
                if(isLogin){
                    referralInterface.setReferral();
                }
            }
        });
        }


    }
}
