package com.br.adapter;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.br.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoricoArrayAdapter extends ArrayAdapter<JSONObject> {
	private static final String tag = "LinguagemArrayAdapter";
	private Context context;
	private String directory;

	// Elementos de cada linha da lista
	private TextView txtTipo;
	private TextView txtParametro;
	private TextView txtDate;

	private List<JSONObject> elements = new ArrayList<JSONObject>();

	public HistoricoArrayAdapter(Context context, int textViewResourceId,
			List<JSONObject> objects) {
		super(context, textViewResourceId, objects);

		// Salva o contexto da aplica����o
		this.context = context;

		// Lista de elementos que ser��o utilizados para a contru����o da lista
		this.elements = objects;
	}

	public int getCount() {
		return this.elements.size();
	}

	public JSONObject getItem(int index) {
		return this.elements.get(index);
	}

	/*
	 * Setando os valores dos objetos em cada linha da lista
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// layout que ser�� utilizado na cria����o de cada linha da lista
			row = inflater.inflate(R.layout.registro, parent, false);
		}

		// Get item
		JSONObject category = getItem(position);

		// Recuperando referencia dos elementos da interface
		txtTipo = (TextView) row.findViewById(R.id.tipo);
		txtParametro = (TextView) row.findViewById(R.id.parametro);
		

		// Setando valores
		txtTipo.setText(category.optString("tipo", ""));
		txtParametro.setText(category.optString("parametro", ""));
		

		return row;
	}
}