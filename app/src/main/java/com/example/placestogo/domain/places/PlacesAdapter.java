package com.example.placestogo.domain.places;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placestogo.MainActivity;
import com.example.placestogo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private List<Place> places;
    private Context context;

    public PlacesAdapter(Context context) {
        this.places = new ArrayList<>();
        this.context = context;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        Log.d("Adapter", "set places");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Adapter", "test");

        LayoutInflater inflater = LayoutInflater.from(this.context);

        View contactView = inflater.inflate(R.layout.item_place, parent, false);

        return new ViewHolder(contactView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ place.getPhoto() +"&key="+ this.context.getString(R.string.google_api_key);
        TextView textView = holder.textView;
        ImageView imageView = holder.imageView;
        Log.d("Adapter", place.getName());
        textView.setText(place.getName());

        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        LinearLayout ll;

        ViewHolder(View itemView, final Context context) {
            super(itemView);

            textView = itemView.findViewById(R.id.place_name);
            imageView = itemView.findViewById(R.id.place_photo);

            ll = itemView.findViewById(R.id.llPlaces);

            ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ((MainActivity) context).userItemClick(getAdapterPosition());
                }
            });
        }
    }
}
