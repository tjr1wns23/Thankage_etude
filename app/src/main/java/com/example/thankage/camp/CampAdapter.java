package com.example.thankage.camp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.thankage.R;
import com.example.thankage.model.Note;

import java.util.List;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.RecyclerViewAdapter> {

    final private Context context;
    final private List<Note> notes;
    final private ItemClickListener itemClickListener;

    public CampAdapter(Context context, List<Note> notes, ItemClickListener itemClickListener) {
        this.context = context;
        this.notes = notes;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note,
                parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Note note = notes.get(position);
        holder.tv_title.setText(note.getTitle());
        holder.tv_note.setText(note.getNote());
        holder.tv_date.setText(note.getDate());
        if (note.getImage_name().equals("no_image")) {
            holder.iv_image.setVisibility(View.GONE);
        } else {
            Glide.with(holder.iv_image).load("http://10.0.2.2/" + note.getImage_name())
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.iv_image);
            // Glide.with(this).load("http://10.0.2.2/" + image_name + "?" + new Date().getTime()).into(iv_image);
            // 주석과 같은 코드에서 수정. 주석 코드는 캐시가 계속 쌓여서 비효율적이다.
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class RecyclerViewAdapter extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView tv_title, tv_note, tv_date;
        ImageView iv_image;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.title);
            tv_note = itemView.findViewById(R.id.note);
            tv_date = itemView.findViewById(R.id.date);
            iv_image = itemView.findViewById(R.id.image);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
