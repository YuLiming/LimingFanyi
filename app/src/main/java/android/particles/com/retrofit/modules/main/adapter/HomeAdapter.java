package android.particles.com.retrofit.modules.main.adapter;

import android.content.Context;
import android.particles.com.retrofit.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by YLM on 2016/5/1.
 */
public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    private List<String> src;
    private List<String> yi;
    Context contexts;

    public HomeAdapter(Context context,List<String> srcs,List<String> fanyi)
    {
        contexts = context;
        src =srcs;
        yi = fanyi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                contexts).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position)
    {
        holder.yis.setText(yi.get(position));
        holder.src.setText(src.get(position));
    }
    @Override
    public int getItemCount()
    {
        return yi.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView src;
        TextView yis;
        public MyViewHolder(View view)
        {
            super(view);
            src = (TextView)view.findViewById(R.id.src);
            yis = (TextView)view.findViewById(R.id.yi);
        }
    }

}
