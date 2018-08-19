package frogermcs.io.recyclerviewanimations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Random;

import butterknife.OnCheckedChanged;

public class RecylerDemo extends AppCompatActivity {

    private RecyclerView recycleView;
    private RadioButton deleteRadio;
    private RadioButton addRadio;
    private RadioButton modifyRadio;
    private CheckBox predictCheck;
    private CheckBox customCheck;
    private AdapterColor colorAdapter;
    ArrayList<Integer> colorList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recyler_demo);
        recycleView=(RecyclerView) findViewById(R.id.recyclerView);
        deleteRadio=(RadioButton)findViewById(R.id.deleteRadio);
        addRadio=(RadioButton)findViewById(R.id.addRadio);
        modifyRadio=(RadioButton)findViewById(R.id.modifyRadio);
        predictCheck=(CheckBox)findViewById(R.id.predictCheckbox);
        customCheck=(CheckBox)findViewById(R.id.customCheckbox);
        colorList.addAll(ColorsHelper.COLORS);
            recycleView.setLayoutManager(new GridLayoutManager(this,2){
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return predictCheck.isChecked();
            }
        });
        colorAdapter=new AdapterColor(colorList,this);
        recycleView.setAdapter(colorAdapter);
        setupRecyclerViewAnimator();
    }

    public void onListItemClicked(View itemView) {
        int itemAdapterPosition=recycleView.getChildAdapterPosition(itemView);
        if(itemAdapterPosition==RecyclerView.NO_POSITION){
            return;
        }
        if(deleteRadio.isChecked()){
            colorList.remove(itemAdapterPosition);
            colorAdapter.notifyItemRemoved(itemAdapterPosition);
        }
        if(addRadio.isChecked()){
            colorList.add(itemAdapterPosition,ColorsHelper.COLORS.get(itemAdapterPosition));
            colorAdapter.notifyItemInserted(itemAdapterPosition);
        }
        if(modifyRadio.isChecked()){
            colorList.set(itemAdapterPosition, ColorsHelper.getRandomColor());
            colorAdapter.notifyItemChanged(itemAdapterPosition);
        }
    }

    @OnCheckedChanged(R.id.customCheckbox)
    public void onCustomAnimatorCheckedChange() {
        setupRecyclerViewAnimator();
    }

    private void setupRecyclerViewAnimator() {
            if (customCheck.isChecked()) {
                recycleView.setItemAnimator(new MyItemAnimator());
            } else {
                recycleView.setItemAnimator(new DefaultItemAnimator());
            }
        }


}
