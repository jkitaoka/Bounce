package com.example.bounce;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class barNameAdapter extends FirebaseRecyclerAdapter<barname, barNameAdapter.barViewholder> {

    private LayoutInflater layoutInflater;


    public barNameAdapter(@NonNull FirebaseRecyclerOptions<barname> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here "barname.xml") with data in model class(here "barname.class")
    @Override
    protected void
    onBindViewHolder(@NonNull barViewholder holder, int position, @NonNull barname model)
    {

        // Add barname from model class (here "barname.class")to appropriate view in Card
        // view (here "bar.xml")
        holder.barname.setText(model.getBarName());

    }

    // Function to tell the class about the Card view (here "barname.xml")in which the data will be shown
    @NonNull
    @Override
    public barViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barname, parent, false);
        return new barNameAdapter.barViewholder(view);
    }

    // Sub Class to create references of the views in card view (here "barname.xml")
    class barViewholder extends RecyclerView.ViewHolder {
        TextView barname;
        public barViewholder(@NonNull View itemView) {
            super(itemView);
            barname = itemView.findViewById(R.id.barname);
        }
    }
}
