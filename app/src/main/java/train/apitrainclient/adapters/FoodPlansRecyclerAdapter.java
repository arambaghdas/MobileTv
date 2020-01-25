package train.apitrainclient.adapters;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.Foods;
import com.pttrackershared.models.eventbus.Meals;

/*
* WorkoutsRecyclerAdapter acts as a controller between the @ViewHolder and @Workout model
* performs the conventional bridging in the specified format of RecyclerView and defining a Custom
* ViewHolder
*
* */
public class FoodPlansRecyclerAdapter extends RecyclerView.Adapter<FoodPlansRecyclerAdapter.ViewHolder> {

    private Context context;//context of the activity showing the @WorkoutsFragment
    private List<Meals> foodlist;
    private OnItemSelectedListener onItemSelectedListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mealNum)
        TextView tv_mealNum;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.rootLayout)
        CardView rootLayout;
        @BindView(R.id.ingridientsContainer)
        LinearLayout ingridientsContainer;

        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    public FoodPlansRecyclerAdapter(Context context, List<Meals> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_foodlist, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meals mealJsonModel = foodlist.get(position);

        if (position % 2 == 0){
            holder.rootLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            holder.rootLayout.setBackgroundColor(context.getResources().getColor(R.color.appbar));
        }

        holder.tv_mealNum.setText("Meal " + (position + 1));
        holder.tv_time.setText(mealJsonModel.getTime());
        String food = "";
        for (int i = 0; i < mealJsonModel.getFoods().size(); i++) {
            Foods foodJsonModel = mealJsonModel.getFoods().get(i);
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(context.getResources().getColor(R.color.background_blue));
            textView.setText(foodJsonModel.getQty() + foodJsonModel.getMu() + " " + foodJsonModel.getFoodname());
            holder.ingridientsContainer.addView(textView);
        }

        initItemClickedListener(holder.rootView, position);
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    private void initItemClickedListener(final View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);
                }
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
}
