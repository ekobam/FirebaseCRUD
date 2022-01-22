package com.example.a19111036_ekobambangnuryanto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton tblData;
    RecyclerView recyclerView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("dataku");

    List<Dataku> list = new ArrayList<>();

    AdapterDataku adapterDataku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tblData = findViewById(R.id.tbl_data);
        recyclerView = findViewById(R.id.resaikel_viu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tblData.setOnClickListener(v -> showDialogTambahData());

        bacaData();
    }

    private void bacaData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                //Map<String, Objects> map = (Map<String, Objects>) dataSnapshot.getValue();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dataku dataku = snapshot.getValue(Dataku.class);
                    list.add(dataku);
                }
                adapterDataku = new AdapterDataku(MainActivity.this, list);
                recyclerView.setAdapter(adapterDataku);

                setClick();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void setClick() {
        adapterDataku.setOnCallBack(dataku -> hapusData(dataku));
    }

    private void hapusData(Dataku dataku) {
        myRef.child(dataku.getKunci()).removeValue((error, ref) -> Toast.makeText(getApplicationContext(), " ID Barang " + dataku.getId() + " telah dihapus", Toast.LENGTH_SHORT).show());
    }

    private void showDialogTambahData() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_leot);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        EditText etId = dialog.findViewById(R.id.et_id);
        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etType = dialog.findViewById(R.id.et_type);
        EditText etPrice = dialog.findViewById(R.id.et_price);
        Button tbltambah = dialog.findViewById(R.id.tbl_tambah);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_Keluar);
        tblKeluar.setOnClickListener(v -> dialog.dismiss());

        tbltambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etId.getText())) {
                    errorMsg();
                } else if (TextUtils.isEmpty(etName.getText())) {
                    errorMsg();
                } else if (TextUtils.isEmpty(etPrice.getText())) {
                    errorMsg();
                } else if (TextUtils.isEmpty(etType.getText())) {
                    errorMsg();
                } else {
                    createData(etId.getText().toString(), etName.getText().toString(), etType.getText().toString(), etPrice.getText().toString());

                    dialog.dismiss();
                }
            }

            private void errorMsg() {
                Toast.makeText(getApplicationContext(), "Form Harus Di Isi", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void createData(String id, String name, String type, String price) {
        String kunci = myRef.push().getKey();
        Dataku dataku = new Dataku(kunci, id, name, type, price);

        myRef.child(kunci).setValue(dataku).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "ID Barang : " + dataku.getId() + " Berhasil Disimpan ", Toast.LENGTH_SHORT).show());
    }

}