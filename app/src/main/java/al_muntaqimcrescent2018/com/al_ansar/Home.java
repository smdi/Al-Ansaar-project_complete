package al_muntaqimcrescent2018.com.al_ansar;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Imran on 28-01-2018.
 */

public class Home extends Fragment {

    private FloatingActionButton fab;
    private static final String email = "smdimran838@gmail.com";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbreference;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private ProgressBar progressBar;

    private ChildEventListener childEventListener;


    View viewfor;

    ArrayList<HomeInitialiser> listViewH;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View root =  inflater.inflate(R.layout.home,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewfor = view;

        getActivity().setTitle("Al-Ansaar"+"\t\t\t"+"آل الأنصار");

       progressBar = (ProgressBar) view.findViewById(R.id.progressBarHome);
       progressBar.setVisibility(View.VISIBLE);

        initialise(view);
        initialiseClicks(view);

        GetFireBaseLoad  getLoad = new GetFireBaseLoad();
        getLoad.execute();


    }

    private void initialise(View view) {



        fab = (FloatingActionButton) view.findViewById(R.id.fab_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String emailUser = user.getEmail();

        if(email.equals(emailUser)) {

            fab.setVisibility(View.VISIBLE);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.text_home);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listViewH = new ArrayList();


    }
    private class GetFireBaseLoad extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {


            String fireDb = corrector( "Al-Ansar-Home") ;
            String fireStr =  corrector("Al-Ansar-homeStorage");

            firebaseDatabase = FirebaseDatabase.getInstance();
            dbreference = firebaseDatabase.getReference().child(fireDb);
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference().child(fireStr);


            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    HomeInitialiser homeInitialiser = dataSnapshot.getValue(HomeInitialiser.class);

                    listViewH.add(homeInitialiser);

                    adapter = new EventAdapter(getActivity(),listViewH);

                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            dbreference.addChildEventListener(childEventListener);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
        }
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

    private void initialiseClicks(View view) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("chooser",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "home float", Toast.LENGTH_SHORT).show();

                editor.putInt("choose",0);
                editor.commit();
                Intent intent = new Intent(getActivity(),chooser.class);
                startActivity(intent);
            }
        });
     }
    private void describer() {

        Toast.makeText(getActivity(), "Description", Toast.LENGTH_SHORT).show();
    }


}
