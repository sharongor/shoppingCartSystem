package il.example.firebase_authentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import il.example.firebase_authentication.R;
import il.example.firebase_authentication.RecyclerView.CartAdapter;
import il.example.firebase_authentication.RecyclerView.DummyData;
import il.example.firebase_authentication.data.DataCart;

public class Main_Screen extends AppCompatActivity {

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private ArrayList<DataCart> dataSet = new ArrayList<>();

    private CartAdapter adapter;

    private TextView nameConnected;
    String name="";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    int totalAmount=0;

    TextView totalTopay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        nameConnected = findViewById(R.id.current_id_logged_in);

        totalTopay = findViewById(R.id.total_to_pay);
        totalTopay.setText("Total: " + String.valueOf(0));

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String currentId = mAuth.getCurrentUser().getUid();
        setName(currentId);



        recyclerView = findViewById(R.id.res);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        for (int i=0;i<DummyData.nameArray.length;i++){
            dataSet.add(new DataCart(
                DummyData.nameArray[i],
                    DummyData.priceArray[i],
                    DummyData.drawableArray[i]
            ));
        }

        adapter = new CartAdapter(dataSet, Main_Screen.this,new CartAdapter.OnAmountChangedListener() {
            @Override
            public void onAmountChanged(Double amount) {
                // Format the amount with two decimal places
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedAmount = decimalFormat.format(amount);

                // Set the formatted amount to your TextView
                totalTopay.setText("Total to pay: $" + formattedAmount);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void logoutFunc(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Main_Screen.this, Authentication_Screen.class);
        startActivity(intent);
    }


    //Getting the name from the firestrore database
    public void setName(String currentId){
        // get a reference to the "users" node in the database - real time
        DatabaseReference usersRef = database.getReference("users");
        //Log.d("stamstam",""+usersRef);
        // get the reference to the current user's uid under the "users" node(his child)
        DatabaseReference currentUserRef = usersRef.child(currentId);
        // Log.d("stamstam",""+currentUserRef);

        //if found this user -> we have a listener for that
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name = snapshot.child("name").getValue(String.class);
                    nameConnected.setText("Hello " + name);
//                    Log.d("stamstam","im onDataChanged");
//                    Log.d("stamstam",name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Main_Screen.this,error.toException().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}