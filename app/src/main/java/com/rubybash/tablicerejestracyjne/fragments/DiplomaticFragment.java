package com.rubybash.tablicerejestracyjne.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;
import com.rubybash.tablicerejestracyjne.database.DatabaseAdapter;
import com.rubybash.tablicerejestracyjne.R;

/**
 * Created by michalzuk on 5/23/2017.
 */

public class DiplomaticFragment extends Fragment {


    private DatabaseAdapter databaseAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseAdapter = new DatabaseAdapter(getContext());
        databaseAdapter.open();

        databaseAdapter.dropDiplomaticTable();
        databaseAdapter.diplomaticData();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tablice, container, false);


        Cursor cursor = databaseAdapter.fetchDimplomaticLicensePlates();

        String[] columns = new String[]{
                databaseAdapter.KEY_SHORTCUT, databaseAdapter.KEY_COUNTRY
        };

        int rows[] = new int[]{
                R.id.diplomatic_shortcut, R.id.diplomatic_country
        };

        final SimpleCursorAdapter tabliceAdapter = new SimpleCursorAdapter(getContext(), R.layout.tablica_diplomatic, cursor, columns, rows, 0);

        final ListView listView = (ListView) rootView.findViewById(R.id.tabliceListView);
        listView.setAdapter(tabliceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                String tablicaShortcut = cursor.getString(cursor.getColumnIndexOrThrow("shortcut"));
                Toast.makeText(getContext(), tablicaShortcut, Toast.LENGTH_LONG).show();
            }
        });

        EditText filter = (EditText) rootView.findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tabliceAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        tabliceAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return databaseAdapter.fetchDiplomaticByShortcut(constraint.toString());
            }
        });



        return rootView;
    }

}
