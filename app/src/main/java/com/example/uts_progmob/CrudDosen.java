package com.example.uts_progmob;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CrudDosen extends AppCompatActivity {

    private EditText txtNama, txtNidn, txtAlamat, txtGelar, txtEmail, txtFoto;
    private ImageView imgDosen;
    private ProgressDialog progressDialog;
    private static final int GALLERY_REQUEST_CODE = 58;
    private static final int FILE_ACCESS_REQUEST_CODE = 59;
    private String stringImg= "";
    private boolean isUpdate = false;
    private String idDosen = "";

    EditText nama, nidn, alamat, email, gelar, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_dosen);

        nama = (EditText) findViewById(R.id.editTextNama);
        nidn = (EditText) findViewById(R.id.editTextNidn);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        email = (EditText) findViewById(R.id.editTextEmail);
        gelar = (EditText) findViewById(R.id.editTextGelar);
        foto = (EditText) findViewById(R.id.editTextFoto);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},FILE_ACCESS_REQUEST_CODE);
        }
        txtNama = findViewById(R.id.editTextNama);
        txtNidn = findViewById(R.id.editTextNidn);
        txtAlamat = findViewById(R.id.editTextAlamat);
        txtEmail = findViewById(R.id.editTextEmail);
        txtGelar = findViewById(R.id.editTextGelar);
        txtFoto = findViewById(R.id.editTextFoto);

        final Button btnUpload = findViewById(R.id.btnUploadFoto);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        /*Button btnSimpan = (Button) findViewById(R.id.btnSimpan);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        android.telecom.Call<List<com.example.uts_progmob.Model.DataDosen>> call = service.getDosenAll("72160071");
        call.enqueue(new android.telecom.Call.Callback<List<com.example.uts_progmob.Model.DataDosen>>());
        @Override
        public void onResponse
        (Call < List < DataDosen >> call, Response < List < DataDosen >> Response){
            progressDialog.dismiss();*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FILE_ACCESS_REQUEST_CODE : if (grantResults.length > 0 && grantResults [0] == PERMISSION_GRANTED) {
                //Permission Granted
            }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE :
                    Uri selectedImage = data.getData();
                    imgDosen.setImageURI(selectedImage);

                    String [] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    txtFoto.setText(imgDecodableString);
                    cursor.close();

                    Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    stringImg = Base64.encodeToString(b, Base64.DEFAULT);
                    break;
            }
    }
}