package ru.mamapapa.task8;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static ru.mamapapa.task8.EditNoteActivity.ACTION;
import static ru.mamapapa.task8.EditNoteActivity.NOTE_ID;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final int MAX_LENGTH = 20;
    private List<Note> items = new ArrayList<>();
    private static float textSize = 0.0f;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new Holder(inflater.inflate(R.layout.item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
                String action = EditNoteActivity.Action.EDIT.name();
                intent.putExtra(ACTION, action);
                intent.putExtra(NOTE_ID, (String) textView.getTag());
                v.getContext().startActivity(intent);
            });
        }

        public void bind(Note note) {
            String title = note.getTitle();
            textView.setText(title.length() > MAX_LENGTH ? title.substring(0, MAX_LENGTH) + "..." : title);
            textView.setTag(note.getId());
            if (textSize != 0.0f) {
                textView.setTextSize(textSize);
            }
        }
    }

    public void addItems(List<Note> notes) {
        items.addAll(notes);
    }

    public void clear() {
        items.clear();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }
}
