package android.particles.com.retrofit.modules.search.adapter;

import android.content.Context;
import android.particles.com.retrofit.R;
import android.particles.com.retrofit.modules.search.domin.Translation;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by YLM on 2016/11/23.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Translation.WebBean> mData = null;

    public SearchAdapter(Context context,List<Translation.WebBean> mData){
        this.context = context;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.search_item,viewGroup,false));
        return viewHolder;
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int position){
        viewHolder.key.setText(mData.get(position).getKey());
        viewHolder.value.setText(ListToString(mData.get(position).getValue()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView value;
        private TextView key;

        public ViewHolder(View view){
            super(view);
            key = (TextView)view.findViewById(R.id.search_item_key);
            value = (TextView)view.findViewById(R.id.search_item_value);
        }
    }
    private String ListToString(List<String> data){
        StringBuilder builder = new StringBuilder();
        for (String item:data){
            builder.append(item+',');
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
