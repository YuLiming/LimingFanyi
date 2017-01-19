package android.particles.com.retrofit.modules.history.adapter;

import android.particles.com.retrofit.R;
import android.particles.com.retrofit.modules.history.HistroyItem;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by YLM on 2016/11/20.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<HistroyItem> mData;

    public HistoryAdapter(List<HistroyItem> mData){
        this.mData = mData;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem,parent,false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder viewHolder,int position){
        viewHolder.query.setText(mData.get(position).query);
        viewHolder.translation.setText(mData.get(position).translation);

    }
    @Override
    public int getItemCount(){
        return mData.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView query;
        TextView translation;

        public HistoryViewHolder(View view){
            super(view);
            query = (TextView) view.findViewById(R.id.history_query);
            translation = (TextView)view.findViewById(R.id.history_translation);


        }
    }
}
