package train.apitrainclient.adapters;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.FequsModel;

/*
* WorkoutsRecyclerAdapter acts as a controller between the @ViewHolder and @Workout model
* performs the conventional bridging in the specified format of RecyclerView and defining a Custom
* ViewHolder
*
* */
public class HelpFeedbackRecyclerAdapter extends RecyclerView.Adapter<HelpFeedbackRecyclerAdapter.ViewHolder> {

    private Context context;//context of the activity showing the @WorkoutsFragment
    List<FequsModel> feqsList=new ArrayList<>();
    private OnItemSelectedListener onItemSelectedListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.iv_arrow)
        ImageView iv_arrow;
        @BindView(R.id.tv_text)
        TextView tv_text;
        @BindView(R.id.checkbox_openclose)
        CheckBox checkbox_openclose;


        View rootView;
        boolean opened;
        public ViewHolder(View view) {
            super(view);
            rootView = view;
            opened = false;
            //injecting the views in the view holder
            ButterKnife.bind(this, view);
        }
    }

    public HelpFeedbackRecyclerAdapter(Context context, List<FequsModel> feqsList) {
        this.context = context;
        this.feqsList = feqsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.help_feedback_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FequsModel fequsModel = feqsList.get(position);

        holder.tv_name.setText(fequsModel.getQuestion());
        holder.tv_text.setText(fequsModel.getAnswer());
        holder.iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkbox_openclose.isChecked()){
                    holder.checkbox_openclose.setChecked(false);
                    holder.tv_text.setVisibility(View.GONE);
                    holder.iv_arrow.setRotation(180);
                }else {
                    holder.checkbox_openclose.setChecked(true);
                    holder.tv_text.setVisibility(View.VISIBLE);
                    holder.iv_arrow.setRotation(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return feqsList.size();
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

    //delegate to grasp the functionality of view tapped
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
}
