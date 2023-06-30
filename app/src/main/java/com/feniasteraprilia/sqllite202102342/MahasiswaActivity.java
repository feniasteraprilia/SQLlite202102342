package com.feniasteraprilia.sqllite202102342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MahasiswaActivity extends AppCompatActivity {

    EditText nim, nama, jeniskelamin, alamat, email;
    String ni, nma, jk, almt, em;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        nim = findViewById(R.id.edtnim);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.simpan);
        tampil = findViewById(R.id.tampil);
        hapus = findViewById(R.id.hapus);
        edit = findViewById(R.id.edit);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nim.getText().toString();
                String nma = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String almt = alamat.getText().toString();
                String em = email.getText().toString();

                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nma) || TextUtils.isEmpty(jk)
                        || TextUtils.isEmpty(almt) || TextUtils.isEmpty(em)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checkNim(ni);
                    if (checkkode == false) {
                        Boolean insert = db.insertDataMhs(ni, nma, jk, almt, em);
                        if (insert == true) {
                            Toast.makeText(MahasiswaActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MahasiswaActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataMhs();
                if (res.getCount() == 0) {
                    Toast.makeText(MahasiswaActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Nim Mahasiswa : " + res.getString(0) + "\n");
                    buffer.append("Nama Mahasiswa : " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin : " + res.getString(2) + "\n");
                    buffer.append("Alamat : " + res.getString(3) + "\n");
                    buffer.append("Email : " + res.getString(4) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MahasiswaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nim.getText().toString();
                Boolean cekHapusData = db.hapusDataMhs(kb);
                if (cekHapusData == true)
                    Toast.makeText(MahasiswaActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MahasiswaActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nim.getText().toString();
                String nma = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String almt = alamat.getText().toString();
                String em = email.getText().toString();
                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nma) || TextUtils.isEmpty(jk)
                        || TextUtils.isEmpty(almt) || TextUtils.isEmpty(em)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean edit = db.editDataMhs(ni, nma, jk, almt, em);
                    if (edit == true) {
                        Toast.makeText(MahasiswaActivity.this, "Data Berhasil Diedit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
