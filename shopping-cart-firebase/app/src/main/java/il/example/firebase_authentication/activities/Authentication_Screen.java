package il.example.firebase_authentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.example.firebase_authentication.R;
import il.example.firebase_authentication.data.Data;

public class Authentication_Screen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(Authentication_Screen.this, Main_Screen.class);
            startActivity(intent);
        }
    }


    public void loginFunc(View view) {
        String email = ((TextInputEditText)findViewById(R.id.email_id)).getText().toString();
        String password = ((TextInputEditText)findViewById(R.id.password)).getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Authentication_Screen.this,"Succefully Logged in",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Authentication_Screen.this, Main_Screen.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Authentication_Screen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register_func(View view) {

        String email = ((TextInputEditText)findViewById(R.id.email_id)).getText().toString();
        String password = ((TextInputEditText)findViewById(R.id.password)).getText().toString();
        String phone = ((TextInputEditText)findViewById(R.id.phone_number)).getText().toString();
        String name = ((TextInputEditText)findViewById(R.id.name_register)).getText().toString();
        Log.d("REGISTER_SCREEN",email);
        Log.d("REGISTER_SCREEN",name);
        if(email.isEmpty() || password.isEmpty() || phone.isEmpty() || name.isEmpty()){
            Toast.makeText(Authentication_Screen.this,"Empty fields, fill them!",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Authentication_Screen.this,"Succefully Created the user",Toast.LENGTH_SHORT).show();

                                //storing to the realtime database
                                DatabaseReference myRef = database.getReference("users");
                                Data newObject = new Data(user.getUid().toString(),email,password,phone,name);
                                myRef.child(user.getUid()).setValue(newObject);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(Authentication_Screen.this, "Couldn't make the user.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }
}