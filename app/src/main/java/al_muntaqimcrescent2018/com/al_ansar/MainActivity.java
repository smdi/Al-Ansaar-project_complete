package al_muntaqimcrescent2018.com.al_ansar;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String fullName = "";
    String email =  "";
    private FadingTextView longText;
    private CircleImageView navImage;
    private TextView emailTv;
    private static final int RC_File_CHOOSER = 2;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialse();
        displaySelectedItem(R.id.hb);


    }

    private void initialse() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String emailOfUser = user.getEmail();

        email = ""+emailOfUser;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        webView = (WebView) findViewById(R.id.watsapp);
        navImage =(CircleImageView) headerView.findViewById(R.id.CircularImageOntop);
        longText = (FadingTextView) headerView.findViewById(R.id.onback1);
        longText.setTimeout(FadingTextView.SECONDS ,2);
        emailTv = (TextView) headerView.findViewById(R.id.textView);

        setClicks();
        char chari  =    getTheChar(email);
        String fadad[] = {""+chari,""+chari};
        longText.setTexts(fadad);
        emailTv.setText(email);

    }

    private void setClicks() {


        longText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewAnim(longText,R.id.onback1);
            }
        });

        emailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewAnim(emailTv,R.id.textView);
            }
        });

        navImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewAnim(navImage,R.id.CircularImageOntop);
            }
        });
    }

    private void setAnimation(int id) {
        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(findViewById(id));
    }

    public void getViewAnim(View view , final int id) {

        ViewAnimator
                .animate( view)
                .thenAnimate(view)
                .scale(.2f, 1f, 1f)
                .accelerate()
                .duration(1000)
                .start().onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {

                setAnimation(id);
            }
        });
    }

    public char  getTheChar(String fullName) {


        String []spliti = fullName.split("\\s");
        String Name;
        int getl = spliti.length;

        if( getl > 0) {


            Name = spliti[spliti.length - 1];
            System.out.println("" + Name.charAt(0));

            return Name.charAt(0);
        }
        else
        {
            System.out.println("" +fullName.charAt(0));

            return  fullName.charAt(0);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {



            Toast.makeText(getApplicationContext() ,"sign-out" ,Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = getSharedPreferences("Sign",MODE_PRIVATE).edit();
            editor.putInt("signIn",0);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(),Sign_Up.class);
            startActivity(intent);
            FirebaseAuth.getInstance().signOut();

            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedItem(int id)
    {
        Fragment fragment = null;

        SharedPreferences preferences = getSharedPreferences("downbutton",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        SharedPreferences preferencesi = getSharedPreferences("EventHome",MODE_PRIVATE);
        SharedPreferences.Editor editori = preferencesi.edit();


        switch(id)
        {

            case R.id.hb :

                  fragment = new Home();
                  editori.putInt("event",0);
                  editori.commit();
                break;

            case R.id.monthly_bayans :

                fragment = new MonthlyBayans();
                editor.putInt("down",0);
                editor.commit();
                break;

            case R.id.downloads:

                fragment = new Downloads();
                editor.putInt("down",1);
                editor.commit();
                break;
            case R.id.comingEvents :
                fragment = new UpComingEvents();
                editori.putInt("event",1);
                editori.commit();
                break;

            case R.id.joingroup :

                Toast.makeText(getApplicationContext() ,"processing request" ,Toast.LENGTH_SHORT).show();

                webView.loadUrl("https://chat.whatsapp.com/IFYGuOQouNUF1pdD3g1uXR");

                break;
            case R.id.nav_share :

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Al-Ansar");
                String sAux = "\nAl-Ansaar App link\n\n";
//                    sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));

                break;
            case R.id.contact_us :

                fragment = new Contact();

                break;

        }

        if(fragment != null)
        {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedItem(id);

        return true;
    }
}
