package controleufla.ufla.controleufla;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Sala> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Sala, List<Computador>> _listDataChild;

    public ExpandableListAdapter(Context context, List<Sala> listDataHeader,
                                 HashMap<Sala, List<Computador>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Computador computador = (Computador) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText("Computador nº: " + computador.getId());
        CheckBox emUso = (CheckBox)convertView.findViewById(R.id.emUso);
        emUso.setChecked(computador.isOcupado());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Sala headerTitle = (Sala) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView nomeSala = (TextView) convertView.findViewById(R.id.lblListHeader);
        TextView compTotal = (TextView) convertView.findViewById(R.id.compTotal);
        TextView compDisp = (TextView) convertView.findViewById(R.id.compDisp);

        nomeSala.setTypeface(null, Typeface.BOLD);
        nomeSala.setText(headerTitle.getNomeDaSala());
        compTotal.setText("Nº de computadores total: " + headerTitle.getNroDeComputadores());
        compDisp.setText("Nº de computadores disponiveis: " + (headerTitle.getNroDeComputadores()-headerTitle.getNroDeComputadoresEmUso()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}