package com.example.notepada;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepada.db.DataBaseNote;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    private Context context;
    private int layout;
    private List<Note>getAllNote;

    public Adapter(Context context, int layout, List<Note> getAllNote) {
        this.context = context;
        this.layout = layout;
        this.getAllNote = getAllNote;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.dteTV.setText(getAllNote.get(position).getDate());
        holder.nteTv.setText(getAllNote.get(position).getNoteText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopUpMenu(view ,position);
                return false;
            }
        });
    }

    private void showPopUpMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.edit:
                        editNote(position);
                        break;
                    case R.id.delete:
                        deleteItem(position);
                        break;
                }
                return false;
            }
        });
    }

    private void editNote(int position) {
        long id = getAllNote.get(position).getId();
        Note note = DataBaseNote.getInstance(context).getNoteDao().getNoteId(id);
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("note",note);
        context.startActivity(intent);
    }

    private void deleteItem(int position) {

        Note note = getAllNote.get(position);
        int deleteRowIt = DataBaseNote.getInstance(context).getNoteDao().deleteNote(note);
        getAllNote.remove(note);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return getAllNote.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView dteTV,nteTv;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dteTV = itemView.findViewById(R.id.noteDateTV);
            nteTv = itemView.findViewById(R.id.noteTV);
        }
    }

    //search filter
    public void getFilter(ArrayList<Note> newArrayList){
        getAllNote = new ArrayList<>();
        getAllNote.addAll(newArrayList);
        notifyDataSetChanged();
    }
}
