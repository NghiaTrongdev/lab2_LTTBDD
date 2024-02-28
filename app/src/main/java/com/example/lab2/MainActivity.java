package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.lab2.databinding.ActivityMainBinding;
import com.example.lab2.databinding.ItemContainerBinding;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_CONTACT = 1;
    private ActivityMainBinding binding;
    private ArrayList<Contact> lst;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        lst = new ArrayList<>();
        contactAdapter = new ContactAdapter(lst);
        contactAdapter.setOnItemCheckedListener(new ContactAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(boolean isChecked) {
                if(isChecked){
                    binding.buttonDelete.setVisibility(View.VISIBLE);
                } else {
                    binding.buttonDelete.setVisibility(View.INVISIBLE);
                }
            }
        });
        binding.main.setAdapter(contactAdapter);

        // Cập nhật giao diện RecyclerView
        contactAdapter.notifyDataSetChanged();
        setContentView(binding.getRoot());
        setListener();
        setData();
        checkData();

    }
    private void setListener(){
        binding.buttonAdd.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("Data",lst);
            startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK) {
           if (data !=null && data.hasExtra("newContact")){
               Contact newContact = (Contact) data.getSerializableExtra("newContact");
               lst.add(newContact);


                Toast toast =  Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
               toast.show();
               contactAdapter.notifyDataSetChanged();
               checkData();
           }
        }
    }
    private void setData(){

    }
    private void checkData(){
        for (int i =0 ; i < lst.size();i++){
            Contact contact = lst.get(i);
            if(contact.Status){
                lst.remove(contact);
                i--;
            }
        }
    }

}
