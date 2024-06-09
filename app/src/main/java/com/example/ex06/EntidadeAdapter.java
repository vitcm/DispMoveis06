package com.example.ex06;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import android.util.Log;
public class EntidadeAdapter extends RecyclerView.Adapter<EntidadeAdapter.EntidadeViewHolder> {

    private List<Entidade> entidadesList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public EntidadeAdapter(List<Entidade> entidadesList) {
        this.entidadesList = entidadesList;
    }

    @NonNull
    @Override
    public EntidadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entidade, parent, false);
        return new EntidadeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntidadeViewHolder holder, int position) {
        Log.d("Entidade", " Entrei aqui " );
        final Entidade entidade = entidadesList.get(position);
        holder.textViewNome.setText(entidade.getNome());
        holder.textViewDescricao.setText(entidade.getDescricao());
        holder.textViewAvistamento.setText(dateFormat.format(entidade.getAvistamento()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EdicaoExclusaoActivity.class);
                Log.d("Entidade", " Entidade: " + entidade );
                intent.putExtra("nome", entidade.getNome());
                intent.putExtra("descricao", entidade.getDescricao());
                intent.putExtra("avistamento", dateFormat.format(entidade.getAvistamento()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entidadesList.size();
    }

    public static class EntidadeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome;
        TextView textViewDescricao;
        TextView textViewAvistamento;

        public EntidadeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewAvistamento = itemView.findViewById(R.id.textViewAvistamento);
        }
    }
}
