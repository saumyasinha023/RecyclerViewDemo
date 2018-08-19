package frogermcs.io.recyclerviewanimations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;



class AdapterColor extends RecyclerView.Adapter<AdapterColor.RecycleHolder> {
    private ArrayList<Integer> colors = new ArrayList<>();
    static RecylerDemo recylerDemo;


    public AdapterColor(ArrayList<Integer> colorList, RecylerDemo recylerDemo) {
        this.colors = colorList;
        this.recylerDemo = recylerDemo;
    }

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        RecycleHolder recycleHolder = new RecycleHolder(view);
        return recycleHolder;
    }

    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
//        int color = colors.get(position);
//        holder.itemView.setBackgroundColor(color);
        holder.txtView.setText(""+position);

    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    static class RecycleHolder extends RecyclerView.ViewHolder {
        TextView txtView;

        public RecycleHolder(final View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recylerDemo.onListItemClicked(itemView);
                }
            });
        }
    }
}
