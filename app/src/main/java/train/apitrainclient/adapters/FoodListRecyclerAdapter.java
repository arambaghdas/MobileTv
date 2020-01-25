package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.Meals;

/*
* WorkoutsRecyclerAdapter acts as a controller between the @ViewHolder and @Workout model
* performs the conventional bridging in the specified format of RecyclerView and defining a Custom
* ViewHolder
*
* */
public class FoodListRecyclerAdapter extends RecyclerView.Adapter<FoodListRecyclerAdapter.ViewHolder> {

    private Context context;//context of the activity showing the @WorkoutsFragment
    private List<Meals> mealList;
    private OnItemSelectedListener onItemSelectedListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.food_plan_name)
        TextView tvName;

        @BindView(R.id.food_plan_calories)
        TextView tv_category;

//        @BindView(R.id.rl_main)
//        RelativeLayout next_btn;

        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    public FoodListRecyclerAdapter(Context context, List<Meals> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foodplan_card_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meals meal = mealList.get(position);
        holder.tvName.setText(meal.getName());

        holder.tv_category.setText("" + meal.getCalories());

//        initItemClickedListener(holder.next_btn, position);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

//    private void initItemClickedListener(final View view, final int position) {
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemSelectedListener != null) {
//                    onItemSelectedListener.onItemSelected(position);
//                }
//            }
//        });
//    }

//    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
//        this.onItemSelectedListener = onItemSelectedListener;
//    }

    //delegate to grasp the functionality of view tapped
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
}
