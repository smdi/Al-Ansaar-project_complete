package al_muntaqimcrescent2018.com.al_ansar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Contact_Creator extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbreference;
    private EditText contactName ,profession ,mail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__creator);


        initialise();
      firbaseinitialise();
    }

    private void initialise() {
        contactName = (EditText) findViewById(R.id.editContactName);
        profession  = (EditText) findViewById(R.id.editProfession);
        mail        = (EditText) findViewById(R.id.editMail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.push,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.push :

                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Pushing Data ...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                Toast.makeText(getApplicationContext(),"pushing",Toast.LENGTH_SHORT).show();
                AndroidSystempusher();


                break;

        }

        return true;
    }

    private void AndroidSystempusher() {

        final String getContact = ""+contactName.getText();
        final String getMail    = ""+mail.getText();
        final String getProf    = ""+profession.getText();


        Toast.makeText(getApplicationContext() ,""+getContact ,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext() ,""+getMail ,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext() ,""+getProf ,Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = dbreference;
        ContactInitialiser contactInitialiser = new ContactInitialiser(getContact,getProf,getMail);
        databaseReference.push().setValue(contactInitialiser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"error" ,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        finish();

    }
    private void firbaseinitialise() {
        String fireDb = corrector( "Contact-list");
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbreference = firebaseDatabase.getReference().child(fireDb);

    }

    private String corrector(String master) {

        String fin = "";

        String mod = "" ,mod1 = "",mod2 = "",mod3 = "",mod4="",mod5 = "";



        if(master.contains("."))
        {
            mod = ""+master.replace(".","dot") ;
        }
        else{
            mod = master;
        }
        if(mod.contains("$"))
        {


            mod1 = ""+mod.replace("$","dollar");
        }
        else{
            mod1 = mod;
        }
        if(mod1.contains("["))
        {

            mod2 = ""+mod1.replace("[","lsb");
        }
        else{
            mod2 = mod1;
        }
        if(mod2.contains("]"))
        {

            mod3 = ""+mod2.replace("]","rsb");
        }
        else{
            mod3 = mod2;
        }
        if(mod3.contains("#"))
        {

            mod4 = ""+mod3.replace("#","hash");
        }
        else{
            mod4 = mod3;
        }
        if(mod4.contains("/"))
        {

            mod5 = ""+mod4.replace("/","fs");

            fin = mod5;
        }
        else{
            mod5 = mod4;

            fin = mod5;

        }


        System.out.println(""+fin);

        return   fin;
    }

}
