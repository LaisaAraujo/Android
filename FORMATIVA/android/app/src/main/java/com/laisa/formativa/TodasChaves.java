package com.laisa.formativa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.List;

public class TodasChaves extends AppCompatActivity {
    private RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_chaves);

        List<Chaves> chaves = new BancoDeDados(this,"bd",1).mostrarTabela();
        recycle = findViewById(R.id.recyclerView);

        recycle.setAdapter(new AdapterChaves(chaves,getApplicationContext()));

        RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(layout);


    }
}
