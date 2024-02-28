package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab2.databinding.ActivityAddBinding;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private ArrayList<Contact> lst;
    private ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Data")) {
            lst = (ArrayList<Contact>) intent.getSerializableExtra("Data");
            // Sử dụng danh sách lst ở đây nếu cần thiết
        }

        setContentView(binding.getRoot());
        setListener();

    }
    private void setListener(){
        binding.buttonAdd.setOnClickListener(v->{
            if (isValid()){
                String name = binding.inputName.getText().toString().trim();
                String phone = binding.inputPhone.getText().toString().trim();

                int recentId = lst.size();
                int newId = recentId+1;
                Contact contact = new Contact(newId,name,phone,false);
                binding.inputName.getText().clear();
                binding.inputPhone.getText().clear();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newContact",contact);

                // Đặt kết quả là RESULT_OK và gửi dữ liệu trở lại MainActivity
                setResult(Activity.RESULT_OK, resultIntent);
                // Kết thúc AddActivity

                finish();
            } else {
                return;
            }

        });
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
    private boolean isValid(){
//        String name = binding.inputName.getText().toString().trim();
//        String phone = binding.inputPhone.getText().toString().trim();
//
//        if(phone.isEmpty()){
//            showToast("Please enter a phone number.");
//            return false;
//        }
//
//
//        for (char c : phone.toCharArray()) {
//            if (!Character.isDigit(c)) {
//                showToast("Phone number should contain only digits.");
//                return false;
//            }
//        }
//



        return true;
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
    }
}