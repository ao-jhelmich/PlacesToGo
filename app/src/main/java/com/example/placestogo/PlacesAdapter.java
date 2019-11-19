package com.example.placestogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private List<Place> places;

    public PlacesAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_place, parent, false);

        return new ViewHolder(contactView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(place.getName());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        LinearLayout ll;

        ViewHolder(View itemView, final Context context) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.place_name);
            ll = itemView.findViewById(R.id.llPlaces);

            ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ((MainActivity) context).userItemClick(getAdapterPosition());
                }
            });
        }
    }
}
