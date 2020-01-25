package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pttrackershared.models.eventbus.Exercise;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;

public class ExercisesNameRecyclerAdapter extends RecyclerView.Adapter<ExercisesNameRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Exercise> exerciseList;
    private OnItemSelectedListener onItemSelectedListener;

    /**
     * custom view holder inner class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_category)
        TextView tvCategory;

        @BindView(R.id.iv_exercise_image)
        ImageView ivExerciseImage;

        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    public ExercisesNameRecyclerAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercise, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        if (exercise != null) {
            holder.tvName.setText(exercise.getName());
            holder.tvCategory.setText(exercise.getCategory_name());
            if (exercise.getImageLink() != null && !exercise.getImageName().equalsIgnoreCase("")) {
                String imageName = exercise.getImageLink();
                User user = SharedPrefManager.getUser(context);
                if (user.getGender().equalsIgnoreCase("2")) {
                    Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                            imageName).
                            error(R.drawable.error_image).into(holder.ivExerciseImage);
                } else {
                    Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                            imageName).
                            error(R.drawable.error_image).into(holder.ivExerciseImage);
                }
            } else {
                holder.ivExerciseImage.setImageResource(R.drawable.error_image);
            }

            Log.d("Exercises", exercise.getExerciseId() + "");

            initItemClickedListener(holder.rootView, position);
        }

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
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
