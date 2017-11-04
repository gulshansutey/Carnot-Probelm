package gulshansutey.carnotproblem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import gulshansutey.carnotproblem.R;
import gulshansutey.carnotproblem.models.InfoModel;

/**
 * Created by asus on 11/4/2017.
 */

public class ItemInfoGridADP extends RecyclerView.Adapter<ItemInfoGridADP.VH> {

    private Context context;
    private List<InfoModel> infoModelList;

    public ItemInfoGridADP(Context context, List<InfoModel> infoModelList) {
        this.context=context;
        this.infoModelList= infoModelList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(View.inflate(context, R.layout.adapter_item_info_grid,null));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
              holder.tv_start.setText(context.getString(R.string.start)+"\n"+infoModelList.get(position).getStartMillis());
              holder.tv_end.setText(context.getString(R.string.end)+"\n"+infoModelList.get(position).getEndMillis());
              holder.tv_start_save.setText(context.getString(R.string.start_save)+"\n"+infoModelList.get(position).getSaveStartMillis());
              holder.tv_end_save.setText(context.getString(R.string.end_save)+"\n"+infoModelList.get(position).getSaveEndMillis());
    }

    @Override
    public int getItemCount() {
        return infoModelList.size();
    }

      class VH extends RecyclerView.ViewHolder {
        private TextView tv_start,tv_end,tv_start_save,tv_end_save;
        private VH(View itemView) {
            super(itemView);
            tv_start=itemView.findViewById(R.id.tv_start);
            tv_end=itemView.findViewById(R.id.tv_end);
            tv_start_save=itemView.findViewById(R.id.tv_start_save);
            tv_end_save=itemView.findViewById(R.id.tv_end_save);
        }
    }
}
