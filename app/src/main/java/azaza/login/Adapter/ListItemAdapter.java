package azaza.login.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import azaza.login.Model.ListItemRecords;
import azaza.login.R;
import azaza.login.my_lib.DrawCountry;


/**
 * Created by Alex on 05.06.2015.
 */
public class ListItemAdapter extends ArrayAdapter<ListItemRecords> {

    private Activity context;
    private List<ListItemRecords> list;
    DrawCountry drawCountry;


    public ListItemAdapter(Activity context, List<ListItemRecords> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
        drawCountry = new DrawCountry(context);

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflator = context.getLayoutInflater();
        View view = inflator.inflate(R.layout.list_item, null);

        String rank = String.valueOf(list.get(position).getPosition());
        if(rank!="") {
            ((ImageView) view.findViewById(R.id.imageViewPoint)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.textViewListRank)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.textViewListRank)).setText(rank);
        }else {
            ((ImageView) view.findViewById(R.id.imageViewPoint)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.textViewListRank)).setVisibility(View.GONE);
        }
        ((TextView) view.findViewById(R.id.textViewListName)).setText(list.get(position).getUserName());
        ((TextView) view.findViewById(R.id.textViewListScoreValue)).setText(list.get(position).getResult());
        ((ImageView) view.findViewById(R.id.imageViewCountry)).setImageDrawable(drawCountry.getFlag(list.get(position).getCountry()));

        return view;
    }
}

