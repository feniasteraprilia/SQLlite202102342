package com.feniasteraprilia.sqllite202102342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BukuActivity extends AppCompatActivity {
    EditText kode, judul, pengarang, penerbit, isbn;

    Button simpan, tampil, hapus, edit;

    DBHelper DB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kode = findViewById(R.id.kodebuku);
        judul = findViewById(R.id.judulbuku);
        pengarang = findViewById(R.id.pengarang);
        penerbit = findViewById(R.id.penerbit);
        isbn = findViewById(R.id.isbn);
        DB = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = kode.getText().toString();
                String jdl = judul.getText().toString();
                String pg = pengarang.getText().toString();
                String pt = penerbit.getText().toString();
                String in = isbn.getText().toString();
                if (TextUtils.isEmpty(kb) || TextUtils.isEmpty(jdl) || TextUtils.isEmpty(pg) || TextUtils.isEmpty(in))
                    Toast.makeText(BukuActivity.this, "", Toast.LENGTH_SHORT).show();
                else {
                    if (!kb.equals("")) {
                        Boolean checkkode = DB.checkkodeBuku(kb);
                        if (checkkode == false) {
                            Boolean insert = DB.insertDataBuku(kb, jdl, pg, pt, in);
                            if (insert == true) {
                                Toast.makeText(BukuActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BukuActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(BukuActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(BukuActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.tampilDataBuku();
                if (res.getCount()==0){
                    Toast.makeText(BukuActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Kode Buku : "+ res.getString(0) + "\n");
                    buffer.append("Judul : "+ res.getString(1) + "\n");
                    buffer.append("Pengarang : "+ res.getString(2) + "\n");
                    buffer.append("Penerbit : "+ res.getString(3) + "\n");
                    buffer.append("No. ISBN : "+ res.getString(4) + "\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BukuActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Buku");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Kb = kode.getText().toString();
                Boolean cekHapusData = DB.hapusDataBuku(Kb);
                if (cekHapusData == true)
                    Toast.makeText(BukuActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(BukuActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = kode.getText().toString();
                String jdl = judul.getText().toString();
                String pg = pengarang.getText().toString();
                String pt = penerbit.getText().toString();
                String in = isbn.getText().toString();
                if (TextUtils.isEmpty(kb) || TextUtils.isEmpty(jdl) || TextUtils.isEmpty(pg)
                        || TextUtils.isEmpty(pt) || TextUtils.isEmpty(in)) {
                    Toast.makeText(BukuActivity.this, "Semua Field Wajib Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean edit = DB.editDataBuku(kb, jdl, pg, pt, in);
                    if (edit == true) {
                        Toast.makeText(BukuActivity.this, "Data Berhasil Diedit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BukuActivity.this, "Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
