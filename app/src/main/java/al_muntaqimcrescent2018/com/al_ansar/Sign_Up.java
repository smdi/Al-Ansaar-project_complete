package al_muntaqimcrescent2018.com.al_ansar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;

import developer.shivam.library.DiagonalView;

public class Sign_Up extends AppCompatActivity {

    ArrayList<String> emailA ,userNameA;

    ArrayAdapter<String > arrayAdapter;

    AutoCompleteTextView emailAc,passwordAc;

    boolean send,check;

    TextView signin,signup;

    private FadingTextView fadingTextView;

    Button get,login;
    private FirebaseAuth firebaseAuth;

    private boolean signinb = true ,signupb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//          this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign__up);

        getSupportActionBar().hide();

        SharedPreferences getSign = getSharedPreferences("Sign",MODE_PRIVATE);
        int sign = getSign.getInt("signIn",0);
        Toast.makeText(getApplicationContext() ,""+sign,Toast.LENGTH_SHORT).show();

        SharedPreferences getShare = getSharedPreferences("Email",MODE_PRIVATE);
        String mail = getShare.getString("email","example@gmail.com");
        Toast.makeText(getApplicationContext() ,""+mail,Toast.LENGTH_SHORT).show();



        if(sign == 1) {


                gotoHome(true ,mail,null);
        }

        else {

            initialise();
            displayDataInfo();

            AutoCompleteInitialise();

            initialiseSignupandInClicks();

            loginGetButtonInitialise();

            firebaseInitialiser();


        }

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser auth = firebaseAuth.getCurrentUser();

    }

    private void firebaseInitialiser() {

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void loginGetButtonInitialise() {



        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(signinb)

                {
                    Toast.makeText(getApplicationContext(), "old customer", Toast.LENGTH_SHORT).show();

                    String email = emailAc.getText().toString().trim();
                    String password = passwordAc.getText().toString().trim();

                    SharedPreferences.Editor editor = getSharedPreferences("Email", MODE_PRIVATE).edit();
                    editor.putString("email", email);
                    editor.apply();
                     checkDetails(email,password);

                }
                if(signupb)
                {
                    Toast.makeText(getApplicationContext(), "new customer", Toast.LENGTH_SHORT).show();
                    String email = emailAc.getText().toString().trim();
                    String password = passwordAc.getText().toString().trim();
                    createDetails(email ,password );

                }

            }
        });


    }

    private void gotoHome(boolean dets ,String email ,String username) {


        if(dets) {

            SharedPreferences.Editor editor = getSharedPreferences("Sign",MODE_PRIVATE).edit();
            editor.putInt("signIn",1);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            Toast.makeText(getApplicationContext(), " Enter valid details ", Toast.LENGTH_SHORT).show();
        }


    }

    private void initialiseSignupandInClicks() {

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext() ,"sign In",Toast.LENGTH_SHORT).show();
//                ac2UserName.setVisibility(View.GONE);
                login.setText("Sign In");

                signinb = true;
                signupb = false;

                signup.setVisibility(View.VISIBLE);
                signin.setVisibility(View.GONE);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext() ,"sign up",Toast.LENGTH_SHORT).show();
                login.setText("Sign Up");

                signinb = false;
                signupb = true;

                signup.setVisibility(View.GONE);
                signin.setVisibility(View.VISIBLE);

            }
        });

    }

    private void AutoCompleteInitialise() {

        int i =1 ;

        if(i==1)
        {
            i=2;
            arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,emailA);
            final AutoCompleteTextView actvGetEmail= (AutoCompleteTextView)findViewById(R.id.email);
            actvGetEmail.setThreshold(1);
            actvGetEmail.setAdapter(arrayAdapter);
            actvGetEmail.setTextColor(Color.BLACK);
            emailAc = actvGetEmail;
        }
        if(i==2)
        {
            i=1;
        }

    }

    private String getUser()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Toast.makeText(getApplicationContext(),"User name"+user.getDisplayName(),Toast.LENGTH_LONG).show();

      return   " "+user.getDisplayName();
    }



    private void initialise() {

        fadingTextView = (FadingTextView) findViewById(R.id.Qtext);
        fadingTextView.setTimeout(FadingTextView.SECONDS ,2);

        signin = (TextView) findViewById(R.id.sign_in);
        signup = (TextView) findViewById(R.id.Sign_up);
        login = (Button) findViewById(R.id.login);

        signup.setTextColor(Color.WHITE);
        signin.setTextColor(Color.WHITE);
        signup.setVisibility(View.VISIBLE);
        signin.setVisibility(View.GONE);

        passwordAc= (AutoCompleteTextView)findViewById(R.id.password);

        emailA = new ArrayList<String >();
        userNameA = new ArrayList<String>();
        get = (Button) findViewById(R.id.login);

    }

    private void createDetails(final String email, String password) {




        firebaseAuth.createUserWithEmailAndPassword(email ,password ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful())
                {


                    gotoHome(true,email,getUser());


                }
                else {

                    Toast.makeText(getApplicationContext(), " AL-ready registered ", Toast.LENGTH_SHORT).show();

                }


            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), " unable to load ", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void checkDetails(final String email, String password) {




    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {




            if(task.isSuccessful()){

                gotoHome( true,email,getUser());
            }
            else {

                Toast.makeText(getApplicationContext(), " Enter Valid Details ", Toast.LENGTH_SHORT).show();

            }


        }
    }).addOnFailureListener(this, new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

            Toast.makeText(getApplicationContext(), " unable to load ", Toast.LENGTH_SHORT).show();

        }
    });



    }

    private void displayDataInfo() {

        SharedPreferences getShare = getSharedPreferences("Email",MODE_PRIVATE);
        String mail = getShare.getString("email","example@gmail.com");
        Toast.makeText(getApplicationContext() ,""+mail,Toast.LENGTH_SHORT).show();
        emailA.add(""+mail+"");

    }




    private boolean emailChecker(String email) {


        return  true;
    }

}
