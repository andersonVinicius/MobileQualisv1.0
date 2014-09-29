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
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LinguagemArrayAdapter extends ArrayAdapter<JSONObject> {
	  private static final String tag = "LinguagemArrayAdapter";
	  private Context context;
	  private String directory;

	  // Elementos de cada linha da lista
	  private TextView txtIssn;
	  private TextView txtTitulo;
	  private TextView txtEstrato;
	  private TextView txtArea;
	  
	  private List<JSONObject> elements = new ArrayList<JSONObject>();

	  public LinguagemArrayAdapter(Context context, int textViewResourceId,
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
	      row = inflater.inflate(R.layout.item_lista_todos,parent, false);
	    }

	    // Get item
	    JSONObject category = getItem(position);

	    // Recuperando referencia dos elementos da interface
	    txtIssn = (TextView) row.findViewById(R.id.issn);
	    txtTitulo = (TextView) row.findViewById(R.id.titulo);
	    txtEstrato = (TextView) row.findViewById(R.id.estrato);
	    txtArea = (TextView) row.findViewById(R.id.Area);
	    
	    // Setando valores
	    txtIssn.setText(category.optString("issn", ""));
	    txtTitulo.setText(category.optString("titulo", ""));
	    txtEstrato.setText(category.optString("estrato", ""));
	    txtArea.setText(category.optString("area", ""));
	    return row;
	  }
	}