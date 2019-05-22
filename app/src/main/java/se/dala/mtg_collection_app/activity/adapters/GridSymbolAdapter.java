package se.dala.mtg_collection_app.activity.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import se.dala.mtg_collection_app.R;
import se.dala.mtg_collection_app.activity.CollectionHomeActivity;
import se.dala.mtg_collection_app.activity.views.CustomButton;
import se.dala.mtg_collection_app.model.ExpansionSymbol;

public class GridSymbolAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    Context context;
    Activity currentActivity;
    List<ExpansionSymbol> expansionSymbols;

    public GridSymbolAdapter(CollectionHomeActivity context, List<ExpansionSymbol> expansionSymbols) {
        this.expansionSymbols = expansionSymbols;
        this.context = context;
        this.currentActivity = context;
        this.layoutInflater = (LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return expansionSymbols.size();
    }

    @Override
    public Object getItem(int position) {
        return expansionSymbols.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_symbol_button, viewGroup,false);
            Button button = view.findViewById(R.id.buttontest);
        }
        ExpansionSymbol symbol = expansionSymbols.get(position);
        CustomButton button = view.findViewById(R.id.buttontest);
        button.setText(symbol.getSymbol());
        button.setTag(symbol.getName());
        return view;
    }
}
