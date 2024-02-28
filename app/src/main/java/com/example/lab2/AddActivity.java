package com.example.lab2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.lab2.databinding.ActivityAddBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private ArrayList<Contact> lst;
    private ActivityAddBinding binding;
    private String imageEndcoded;

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
                Contact contact = new Contact(newId,name,phone,imageEndcoded,false);
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
        binding.imageAdd.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // cờ xin cấp quyền đọc dữ liệu URI từ intent
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }
    private boolean isValid(){

        return true;
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
    }
    private String encodeImage(Bitmap bitmap){
        // khai báo chiểu rộng của hình ảnh sau khi né
        int previewWidth = 150;
        // tính toán chiều cao hình ảnh cho phù hợp
        int previewHeight = bitmap.getHeight() * previewWidth/bitmap.getWidth();
        // Thực hiện co dãn hình ảnh theo kích thước mình đã khai báo
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);

        // khai báo đối tượng lưu trữ hình ảnh sau khi nén
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // thực hiện việc nén ảnh sau khi scale thành mã bit
        previewBitmap.compress(Bitmap.CompressFormat.JPEG ,50,byteArrayOutputStream );
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                // nếu chọn thành công sẽ tiếp tục thực thi , ví dụ như chọn được ảnh,
                // còn nếu tràn bộ nhớ hoặc có lỗi thì đoạn if sẽ false
                if (result.getResultCode() == RESULT_OK){

                    // Check xem người dùng có chọn được ảnh không
                    if(result.getData() != null){
                        // lấy URI của ảnh được chọn
                        Uri imageUri = result.getData().getData();
                        try {

                            // Mở một luồng đầu vào từ URI hình ảnh thu được bằng getContentResolver
                            // cho phép truy cập dữ liệu từ các content provider khác
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);

                            // Giải mã luồng đầu vào thành một đối tượng
                            // bitmap đại diện cho hình ảnh được chọn
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageAdd.setImageBitmap(bitmap);

                            // Ẩn văn bản
                            binding.textAdd.setVisibility(View.GONE);

                            // Mã hoá hình ảnh
                            imageEndcoded = encodeImage(bitmap);

                        } catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

}