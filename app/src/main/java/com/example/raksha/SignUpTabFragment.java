package com.example.raksha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignUpTabFragment extends Fragment {
    float v=0;
    TextView pass;
    TextView email;
    TextView forgetPass;
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.Username);
        pass = root.findViewById(R.id.Password);
        forgetPass = root.findViewById(R.id.forgotPasswordTextView);
        login = root.findViewById(R.id.loginButton);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        login.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();

        return root;
    }
}
